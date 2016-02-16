/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.FormContrib;
import py.com.konecta.redcobros.entities.Grupo;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.Servicio;
import py.com.konecta.redcobros.entities.UsuarioTerminal;
import py.com.konecta.redcobros.utiles.Utiles;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GrupoFacadeImpl extends GenericDaoImpl<Grupo, Long> implements GrupoFacade {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")

    private static final int MAXIMO_NUMERO_CAJA = 500;
    @Resource
    private SessionContext context;
    @EJB
    private GestionFacade gestionF;
    @EJB
    private FormContribFacade formContribFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Grupo obtenerGrupo(UsuarioTerminal ut, Servicio servicio) throws Exception {
        try {

            /****REGISTRO CAJA - GESTION****/
            /**DEBE HABER UN REGISTRO DE CAJA DEL DIA ACTUAL SI O SI***/
            /**PORQUE ESO SE DEBIO HABER CREADO LUEGO DE LOGUEARSE EN EL DIA***/
            Date fechaHora = new Date();
            Gestion g, ejemploGestion = new Gestion();
            ejemploGestion.setFechaApertura(fechaHora);
            ejemploGestion.setCerrado("N");
            ejemploGestion.setTerminal(ut.getTerminal());
            ejemploGestion.setUsuario(ut.getUsuario());
            if (this.gestionF.total(ejemploGestion) > 0) {
                g = this.gestionF.get(ejemploGestion);
            } else {
                ejemploGestion.setFechaApertura(null);
                if (this.gestionF.total(ejemploGestion) > 0) {
                    throw new Exception("Tiene registro de caja anteriores abiertos, inicie sesion de vuelta");
                } else {
                    throw new Exception("Debe abrir caja");
                }
            }

            //buscamos si existe una grupo para el registro de caja actual
            //que sea del servicio en cuestion, del usuario_terminal, y que
            //no este cerrado
            Grupo ejemploGrupo = new Grupo();
            ejemploGrupo.setGestion(g);
            ejemploGrupo.setServicio(servicio);
            ejemploGrupo.setCerrado("N");
            Grupo grupo;
            if (this.total(ejemploGrupo) == 1) {
                //usamos el siguiente numero disponible de la grupo
                grupo = this.get(ejemploGrupo);
                grupo.setProximaPosicion(grupo.getProximaPosicion() + 1);
                grupo = this.merge(grupo);
            } else {
                //no existe, creamos un nuevo grupo
                grupo = new Grupo();
                grupo.setServicio(servicio);
                //grupo.setTerminal(transaccion.getUsuarioTerminal().getTerminal());
                grupo.setFecha(fechaHora);
                grupo.setHoraInicio(fechaHora);
                grupo.setGestion(g);
                grupo.setCerrado("N");
                grupo.setProcesado("N");
                grupo.setProximaPosicion(1);
                grupo = this.merge(grupo);
            }
            if (servicio.getTamanhoGrupo().intValue() != 0 &&
                    grupo.getProximaPosicion() >= servicio.getTamanhoGrupo()) {
                grupo.setCerrado("S");
                grupo.setHoraCierre(fechaHora);
                grupo.setTotalOperaciones(grupo.getProximaPosicion());
                grupo = this.merge(grupo);
            }
            return grupo;
        } catch (Exception e) {
            context.setRollbackOnly();
            throw e;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void cerrarGruposRed(Long idRed, Date fecha) throws Exception {
        try {
            Date fechaActual = new Date();
            HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
            Criteria c = hem.getSession().createCriteria(Grupo.class);
            c.add(Restrictions.eq("cerrado", "N"));
            if (idRed != null) {
                c.createCriteria("gestion").
                        createCriteria("terminal").
                        createCriteria("sucursal").
                        createCriteria("recaudador").
                        add(Restrictions.eq("red", new Red(idRed)));
            }
            Criterion c1 = Restrictions.eq("fecha", fecha);
            c.add(c1);
            List<Grupo> grupos =
                    (List<Grupo>) c.list();
            for (Grupo g : grupos) {
                if (g.getCerrado().equalsIgnoreCase("N")) {
                    g.setHoraCierre(fechaActual);
                    g.setCerrado("S");
                    g.setTotalOperaciones(g.getProximaPosicion());
                    this.merge(g);
                }
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            throw e;
        }
    }

    private Criteria numeroCajaDeGruposDDJJ(Long idRed, Date fechaIni, Date fechaFin, Integer numeroCaja, Boolean detallado) {

        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(Grupo.class, "g");
        c.add(Restrictions.eq("g.cerrado", "S"));
        c.add(Restrictions.gt("g.totalOperaciones", 0));
        //cGestion.add(Restrictions.eq("fechaApertura", fecha));
        c.createCriteria("servicio").add(Restrictions.eq("codigoTransaccional", "DDJJ-SET"));
        Criteria cGestion = c.createCriteria("gestion");
        Criteria cTerminal = cGestion.createCriteria("terminal", "term");
        Criteria cSucursal = cTerminal.createCriteria("sucursal", "suc");
        cSucursal.createCriteria("recaudador", "rec").
                add(Restrictions.eq("red", new Red(idRed)));

        Criteria cFormContrib = c.createCriteria("formContribCollection", "fc");
        c.add(Restrictions.between("fc.fechaCertificadoSet", fechaIni, fechaFin));
        c.add(Restrictions.eq("fc.camposValidos", 1));

        //  if (!proyectarNumeroCaja) {
        cSucursal.addOrder(Order.asc("codigoSucursalSet"));
        cTerminal.addOrder(Order.asc("codigoCajaSet"));
        if (numeroCaja == null) {
            //para procesar y calcular numero de caja
            c.addOrder(Order.asc("g.idGrupo"));
        } else {
            if (detallado) {
                cFormContrib.addOrder(Order.asc("consecutivo"));
                cFormContrib.createCriteria("formulario", "f");
                cFormContrib.createCriteria("contribuyente", "contrib");
            }
        }
        // }
        ProjectionList pl = Projections.projectionList();
        if (numeroCaja != null) {
            c.add(Restrictions.eq("g.numeroCaja", numeroCaja));
            if (detallado) {
                pl.add(Projections.property("rec.descripcion"), "recaudador");
                pl.add(Projections.property("suc.descripcion"), "sucursal");
                pl.add(Projections.property("suc.codigoSucursalSet"), "codigoSucursalSet");
                pl.add(Projections.property("term.codigoCajaSet"), "codigoCajaSet");
                pl.add(Projections.property("f.numeroFormulario"), "numeroFormulario");
                pl.add(Projections.property("fc.numeroOrden"), "numeroOrden");
                pl.add(Projections.property("contrib.rucNuevo"), "ruc");
                pl.add(Projections.property("fc.consecutivo"), "consecutivo");
                pl.add(Projections.property("fc.fechaCertificadoSet"), "fechaCertificadoSet");
            } else {
                pl.add(Projections.groupProperty("rec.descripcion"), "recaudador");
                pl.add(Projections.groupProperty("suc.descripcion"), "sucursal");
                pl.add(Projections.groupProperty("suc.codigoSucursalSet"), "codigoSucursalSet");
                pl.add(Projections.groupProperty("term.codigoCajaSet"), "codigoCajaSet");
                pl.add(Projections.count("g.totalOperaciones"), "totalOperaciones");
            }
        } else {
            /*if (proyectarNumeroCaja) {
            //pl.add(Projections.groupProperty("g.numeroCaja"), "numeroCaja");
            pl.add(Projections.sum("g.totalOperaciones"), "totalOperaciones");
            c.addOrder(Order.asc("g.numeroCaja"));
            } else {*/
            pl.add(Projections.property("suc.codigoSucursalSet"), "codigoSucursalSet");
            pl.add(Projections.property("term.codigoCajaSet"), "codigoCajaSet");
            pl.add(Projections.property("g.idGrupo"), "idGrupo");
        //pl.add(Projections.property("g.totalOperaciones"), "totalOperaciones");
        //}
        }
        c.setProjection(Projections.distinct(pl));
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return c;
    }

    private Criteria totalNumeroCajaDeGruposDDJJ(Long idRed, Date fechaIni, Date fechaFin) {

        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(Grupo.class, "g");
        c.add(Restrictions.eq("g.cerrado", "S"));
        c.add(Restrictions.gt("g.totalOperaciones", 0));
        //cGestion.add(Restrictions.eq("fechaApertura", fecha));
        c.createCriteria("servicio").add(Restrictions.eq("codigoTransaccional", "DDJJ-SET"));
        Criteria cGestion = c.createCriteria("gestion");
        Criteria cTerminal = cGestion.createCriteria("terminal", "term");
        Criteria cSucursal = cTerminal.createCriteria("sucursal", "suc");
        cSucursal.createCriteria("recaudador", "rec").
                add(Restrictions.eq("red", new Red(idRed)));

        Criteria cFormContrib = c.createCriteria("formContribCollection", "fc");
        c.add(Restrictions.between("fc.fechaCertificadoSet", fechaIni, fechaFin));
        c.add(Restrictions.eq("fc.camposValidos", 1));

        ProjectionList pl = Projections.projectionList();

        pl.add(Projections.groupProperty("g.numeroCaja"), "numeroCaja");
        pl.add(Projections.count("g.totalOperaciones"), "totalOperaciones");
        c.addOrder(Order.asc("g.numeroCaja"));

        c.setProjection(Projections.distinct(pl));
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        return c;

    }

    @Override
    public List<Map<String, Object>> reporteNumeroCajaDeGruposDDJJ(Long idRed, Date fechaIni, Date fechaFin) throws Exception {
        List<Map<String, Object>> lista = this.totalNumeroCajaDeGruposDDJJ(idRed, fechaIni, fechaFin).list();
        Integer numeroCaja;
        List<Map<String, Object>> listaDetalle;
        for (Map<String, Object> mapa : lista) {
            numeroCaja = (Integer) mapa.get("numeroCaja");
            listaDetalle = this.numeroCajaDeGruposDDJJ(idRed, fechaIni, fechaFin, numeroCaja, false).list();
            for (Map<String, Object> mapaDetalle : listaDetalle) {
                mapaDetalle.put("recaudadorSucursal",
                        mapaDetalle.get("recaudador") + " - " +
                        mapaDetalle.get("sucursal"));
            }
            mapa.put("detalle",
                    new JRMapCollectionDataSource((Collection)listaDetalle));
        }
        return lista;
    }

    @Override
    public List<Map<String, Object>> reporteNumeroCajaDeGruposDDJJDetallado(Long idRed, Date fechaIni, Date fechaFin) throws Exception {
        List<Map<String, Object>> lista = this.totalNumeroCajaDeGruposDDJJ(idRed, fechaIni, fechaFin).list();
        Integer numeroCaja;
        List<Map<String, Object>> listaDetalle;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Map<String, Object> mapa : lista) {
            numeroCaja = (Integer) mapa.get("numeroCaja");
            listaDetalle = this.numeroCajaDeGruposDDJJ(idRed, fechaIni, fechaFin, numeroCaja, true).list();
            for (Map<String, Object> mapaDetalle : listaDetalle) {
                mapaDetalle.put("recaudadorSucursal",
                        mapaDetalle.get("recaudador") + " - " +
                        mapaDetalle.get("sucursal"));
                mapaDetalle.put("fechaCertificadoSetString",
                        sdf.format(mapaDetalle.get("fechaCertificadoSet")));
            }
            mapa.put("detalle",
                    new JRMapCollectionDataSource((Collection)listaDetalle));
        //mapa.put("totalOperaciones", listaDetalle.size());
        }
        return lista;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public synchronized void calcularNumeroCajaDeGruposDDJJ(Long idRed, Date fechaIni, Date fechaFin) throws Exception {
        try {
            Criteria c = this.numeroCajaDeGruposDDJJ(idRed, fechaIni, fechaFin, null, false);
            List<Map<String, Object>> listaMapa = c.list();
            int numeroCaja = 1, acumulado = 0;
            Long idGrupo;
            String hqlUpdateGrupo = "update Grupo set numeroCaja = :numeroCaja, " +
                    " totalOperaciones = :totalOperaciones  where idGrupo = :idGrupo";
            Integer declaracionesEnGrupo;
            for (Map<String, Object> mapa : listaMapa) {
                idGrupo = (Long) mapa.get("idGrupo");
                FormContrib fcEjemplo = new FormContrib();
                fcEjemplo.setCamposValidos(1);
                fcEjemplo.setGrupo(new Grupo(idGrupo));
                declaracionesEnGrupo = formContribFacade.total(fcEjemplo);
                if (acumulado + declaracionesEnGrupo <= MAXIMO_NUMERO_CAJA) {
                    acumulado += declaracionesEnGrupo;
                } else {
                    acumulado = declaracionesEnGrupo;
                    numeroCaja++;
                }
                Query query = this.getSession().createQuery(hqlUpdateGrupo);
                query.setLong("idGrupo", idGrupo);
                query.setInteger("numeroCaja", numeroCaja);
                query.setInteger("totalOperaciones", declaracionesEnGrupo);
                query.executeUpdate();
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            throw e;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void cerrarGruposRedFacturador(Long idRed, Long idFacturador) throws Exception {
        try {
            Date fechaActual = new Date();
            HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
            Criteria c = hem.getSession().createCriteria(Grupo.class);
            c.add(Restrictions.eq("cerrado", "N"));
            if (idFacturador != null) {
                c.createCriteria("servicio").
                        add(Restrictions.eq("facturador", new Facturador(idFacturador)));
            }
            if (idRed != null) {
                c.createCriteria("registroCaja").
                        createCriteria("terminal").
                        createCriteria("sucursal").
                        createCriteria("recaudador").
                        createCriteria("habilitacionRecaudadorRedCollection").
                        add(Restrictions.eq("red", new Red(idRed)));
            }

            List<Grupo> grupos =
                    (List<Grupo>) c.list();
            for (Grupo g : grupos) {
                if (g.getCerrado().equalsIgnoreCase("N")) {
                    g.setHoraCierre(fechaActual);
                    g.setCerrado("S");
                    g.setTotalOperaciones(g.getProximaPosicion());
                    this.merge(g);
                }
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            throw e;
        }
    }

    /******************/
    private Criteria getCriteriaGruposUsuarioFecha(Date fechaDesde, Date fechaHasta, Long idUsuario) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c1 = hem.getSession().createCriteria(Grupo.class);
        // c1.add(Restrictions.isNotNull("grupo"));

        if (fechaDesde != null && fechaHasta != null) {

            c1.add(Restrictions.between("fecha", Utiles.primerMomentoFecha(fechaDesde), Utiles.primerMomentoFecha(fechaHasta)));
        } else if (fechaDesde != null) {
            c1.add(Restrictions.eq("fecha", Utiles.primerMomentoFecha(fechaDesde)));
        }

        if (idUsuario != null) {
            Criteria cGestion = c1.createCriteria("gestion", "gestion");
            Criteria cUsuario = cGestion.createCriteria("usuario", "usuario");
            cUsuario.add(Restrictions.eq("idUsuario", idUsuario));
        }

        c1.addOrder(Order.asc("idGrupo"));
        return c1;
    }

    @Override
    public List<Grupo> getGruposUsuarioFecha(Date fechaDesde, Date fechaHasta, Long idUsuario, Integer start, Integer limit) {
        Criteria c = this.getCriteriaGruposUsuarioFecha(fechaDesde, fechaHasta, idUsuario);
        if (start != null && limit != null) {
            c.setFirstResult(start);
            c.setMaxResults(limit);
        }
        return c.list();
    }

    @Override
    public Integer getTotalGruposUsuarioFecha(Date fechaDesde, Date fechaHasta, Long idUsuario) {
        Criteria c = this.getCriteriaGruposUsuarioFecha(fechaDesde, fechaHasta, idUsuario);
        c.setProjection(Projections.rowCount());
        return (Integer) c.list().get(0);
    }
    /******************/
}
