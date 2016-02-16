/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.EntidadPolitica;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.HabilitacionFactRed;
import py.com.konecta.redcobros.entities.HabilitacionFactRedPK;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.RedRecaudadorNumeroOrden;
import py.com.konecta.redcobros.entities.Servicio;

/**
 *
 * @author konecta
 */
@Stateless
@WebService
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RedFacadeImpl extends GenericDaoImpl<Red, Long> implements RedFacade {

    private static Object bloqueo = new Object();
    @EJB
    private RecaudadorFacade recaudadorFacade;
    @EJB
    private FacturadorFacade facturadorFacade;
    @EJB
    private HabilitacionFactRedFacade habilitacionFacturadorRedFacade;
    @EJB
    private RedRecaudadorNumeroOrdenFacade redRecNumeroOrdenFacade;
    @EJB
    private EntidadPoliticaFacade epFacade;
    @EJB
    private EntidadFacade entidadFacade;
    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @WebMethod
    public Long[] getProximoRangoOrden(@WebParam Long idRecaudador) throws Exception {
        try {
            synchronized (RedFacadeImpl.bloqueo) {
                Recaudador rec = this.recaudadorFacade.getLocked(idRecaudador);
                Red red = this.getLocked(rec.getRed().getIdRed());
                Long rangoValores[] = new Long[2];
                rangoValores[0] = red.getNumeroOrdenProximo();
                //incluye el extremo
                rangoValores[1] = rangoValores[0] + (rec.getNumeroOrdenTamRango() - 1);
                red.setNumeroOrdenProximo(rangoValores[1] + 1);
                this.merge(red);
                RedRecaudadorNumeroOrden rrno = new RedRecaudadorNumeroOrden();
                rrno.setFechaHora(new Date());
                rrno.setNumeroInicial(rangoValores[0]);
                rrno.setNumeroFinal(rangoValores[1]);
                rrno.setRecaudador(rec);
                rrno.setRed(rec.getRed());
                this.redRecNumeroOrdenFacade.merge(rrno);
                return rangoValores;
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            e.printStackTrace();
            return new Long[0];
        }
    }


    /*
     * @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) public void
     * agregarRecaudadores(Integer idRed, String[] idRecaudadores, String[]
     * idCuentas) throws Exception { try { HabilitacionRecaudadorRed ejemplo =
     * new HabilitacionRecaudadorRed(); ejemplo.setRed(new Red(idRed)); //se
     * trae todos los recaudadores de esta red List<Recaudador>
     * listaRecaudadoresHabilitados =
     * this.obtenerRecaudadoresHabilitados(idRed); //aqui se cargara los
     * recaudadores recibidos List<Recaudador> listaRecaudadoresRecibidos = new
     * ArrayList<Recaudador>(); Map<Integer, Integer> mapaRecaudadorCuenta = new
     * HashMap(); for (int i = 0; i < idRecaudadores.length; i++) { Recaudador
     * recaudador = new Recaudador();
     * recaudador.setIdRecaudador(Integer.parseInt(idRecaudadores[i]));
     * listaRecaudadoresRecibidos.add(recaudador);
     * mapaRecaudadorCuenta.put(recaudador.getIdRecaudador(),
     * Integer.parseInt(idCuentas[i])); } //en esta lista se cargara los que
     * deberan agregarse //que dueron los recibidos y que no estan habilitados
     * actualmente List<Recaudador> listaRecaudadoresParaAgregar = new
     * ArrayList<Recaudador>(); //copiamos todos los recibidos
     * listaRecaudadoresParaAgregar.addAll(listaRecaudadoresRecibidos);
     * //sacamos los que ya estan habilitados //y la lista ya tendra solo
     * aquellos que habra que agregar
     * listaRecaudadoresParaAgregar.removeAll(listaRecaudadoresHabilitados);
     *
     * //en esta lista se cargara los que deberan borrarse //que estan
     * habilitados actualmente pero que no estan entre los recibidos //por tanto
     * hay que borrarlos List<Recaudador> listaRecaudadoresParaBorrar = new
     * ArrayList<Recaudador>(); //copiamos todos los recibidos
     * listaRecaudadoresParaBorrar.addAll(listaRecaudadoresHabilitados);
     * //sacamos los que ya estan habilitados //y la lista ya tendra solo
     * aquellos que habra que agregar
     * listaRecaudadoresParaBorrar.removeAll(listaRecaudadoresRecibidos);
     *
     * //agregamos los nuevos for (Recaudador r : listaRecaudadoresParaAgregar)
     * { HabilitacionRecaudadorRed hab = new HabilitacionRecaudadorRed();
     * HabilitacionRecaudadorRedPK habPK = new HabilitacionRecaudadorRedPK();
     * habPK.setIdRed(idRed); habPK.setIdRecaudador(r.getIdRecaudador());
     * hab.setHabilitacionRecaudadorRedPK(habPK); hab.setCuenta(new
     * Cuenta(mapaRecaudadorCuenta.get(r.getIdRecaudador())));
     * habilitacionRecaudadorRedFacade.save(hab); }
     *
     * //borramos los que ya no estan HabilitacionRecaudadorRedPK habPK = new
     * HabilitacionRecaudadorRedPK(); habPK.setIdRed(idRed); for (Recaudador r :
     * listaRecaudadoresParaBorrar) {
     * habPK.setIdRecaudador(r.getIdRecaudador());
     * this.habilitacionRecaudadorRedFacade.delete(habPK); } } catch (Exception
     * e) { context.setRollbackOnly(); e.printStackTrace(); throw e; }
    }
     */
    public List<Recaudador> obtenerRecaudadoresNoHabilitados(Long idRed) {
        List<Recaudador> recaudadores = this.recaudadorFacade.list();
        List<Recaudador> recaudadoresHabilitados = this.obtenerRecaudadoresHabilitados(idRed);
        recaudadores.removeAll(recaudadoresHabilitados);
        return recaudadores;
    }

    public List<Recaudador> obtenerRecaudadoresHabilitados(Long idRed) {
        List<Recaudador> listaRecaudadores = new ArrayList<Recaudador>();
        listaRecaudadores.addAll(this.get(idRed).getRecaudadorCollection());
        return listaRecaudadores;
    }

    /*
     * public List<HabilitacionRecaudadorRed>
     * obtenerHabilitacionesRecaudadorRed(Integer idRed) {
     * HabilitacionRecaudadorRed ejemplo=new HabilitacionRecaudadorRed();
     * ejemplo.setRed(new Red(idRed)); return
     * this.habilitacionRecaudadorRedFacade.list(ejemplo);
    }
     */
    public boolean comprobarHabilitacionRecaudador(Long idRed, Long idRecaudador) {
        Recaudador rec = new Recaudador();
        rec.setIdRecaudador(idRecaudador);
        rec.setRed(new Red(idRed));
        return this.recaudadorFacade.total(rec) == 1;
    }

    public boolean comprobarHabilitacionFacturador(Long idRed, Long idFacturador) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
       Integer cantidad=(Integer)hem.getSession().createCriteria(HabilitacionFactRed.class)
                .add(Restrictions.eq("red", new Red(idRed)))
                .add(Restrictions.eq("facturador", new Facturador(idFacturador)))
                .setProjection(Projections.rowCount())
                .list().get(0);
       return cantidad>0;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarFacturadores(Long idRed, String[] idFacturadores, String[] idCuentas) throws Exception {
        try {
            HabilitacionFactRed ejemplo = new HabilitacionFactRed();
            ejemplo.setRed(new Red(idRed));
            //se trae todos los facturadores de esta red
            List<Facturador> listaFacturadoresHabilitados = this.obtenerFacturadoresHabilitados(idRed);
            //aqui se cargara los facturadores recibidos
            List<Facturador> listaFacturadoresRecibidos = new ArrayList<Facturador>();
            //Map<Long, Long> mapaFacturadorCuenta = new HashMap();
            Map<Long, String> mapaFacturadorCuenta = new HashMap<Long, String>();
            for (int i = 0; i < idFacturadores.length; i++) {
                Facturador facturador = new Facturador();
                facturador.setIdFacturador(new Long(idFacturadores[i]));
                listaFacturadoresRecibidos.add(facturador);
                //mapaFacturadorCuenta.put(facturador.getIdFacturador(), new Long(idCuentas[i]));
                mapaFacturadorCuenta.put(facturador.getIdFacturador(), idCuentas[i]);
            }
            //en esta lista se cargara los que deberan agregarse
            //que dueron los recibidos y que no estan habilitados actualmente
            List<Facturador> listaFacturadoresParaAgregar = new ArrayList<Facturador>();
            //copiamos todos los recibidos
            listaFacturadoresParaAgregar.addAll(listaFacturadoresRecibidos);
            //sacamos los que ya estan habilitados
            //y la lista ya tendra solo aquellos que habra que agregar
            listaFacturadoresParaAgregar.removeAll(listaFacturadoresHabilitados);

            //en esta lista se cargara los que deberan borrarse
            //que estan habilitados actualmente pero que no estan entre los recibidos
            //por tanto hay que borrarlos
            List<Facturador> listaFacturadoresParaBorrar = new ArrayList<Facturador>();
            //copiamos todos los recibidos
            listaFacturadoresParaBorrar.addAll(listaFacturadoresHabilitados);
            //sacamos los que ya estan habilitados
            //y la lista ya tendra solo aquellos que habra que agregar
            listaFacturadoresParaBorrar.removeAll(listaFacturadoresRecibidos);

            //agregamos los nuevos
            for (Facturador f : listaFacturadoresParaAgregar) {
                HabilitacionFactRed hab = new HabilitacionFactRed();
                HabilitacionFactRedPK habPK = new HabilitacionFactRedPK();
                habPK.setRed(idRed);
                habPK.setFacturador(f.getIdFacturador());
                hab.setHabilitacionFactRedPK(habPK);
                //hab.setCuenta(new Cuenta(mapaFacturadorCuenta.get(f.getIdFacturador())));
                hab.setNumeroCuenta(mapaFacturadorCuenta.get(f.getIdFacturador()));
                habilitacionFacturadorRedFacade.save(hab);
            }

            //borramos los que ya no estan
            HabilitacionFactRedPK habPK = new HabilitacionFactRedPK();
            habPK.setRed(idRed);
            for (Facturador f : listaFacturadoresParaBorrar) {
                habPK.setFacturador(f.getIdFacturador());
                this.habilitacionFacturadorRedFacade.delete(habPK);
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarEntidadesPoliticas(Long idRed, String[] idEntidades, String[] idCuentas) throws Exception {
        try {
            List<Entidad> lista = new ArrayList<Entidad>();
            Map<Long, String> mapaEntidadCuenta = new HashMap<Long, String>();
            for (int i = 0; i < idEntidades.length; i++) {
                Entidad entidad = new Entidad();
                entidad.setIdEntidad(new Long(idEntidades[i]));
                lista.add(entidad);
                mapaEntidadCuenta.put(entidad.getIdEntidad(), idCuentas[i]);
            }
            Red red = new Red(idRed);
            for (Entidad f : lista) {
                EntidadPolitica ep = new EntidadPolitica();
                ep.setEntidad(f);
                ep.setRed(red);
                ep.setNumeroCuenta(mapaEntidadCuenta.get(f.getIdEntidad()));
                epFacade.save(ep);
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
    }

    public List<Facturador> obtenerFacturadoresNoHabilitados(Long idRed) {
        List<Facturador> facturadores = this.facturadorFacade.list();
        List<Facturador> facturadoresHabilitados = this.obtenerFacturadoresHabilitados(idRed);
        facturadores.removeAll(facturadoresHabilitados);
        return facturadores;
    }

    public List<Facturador> obtenerFacturadoresHabilitados(Long idRed) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        List<Facturador> facturadores =
                (List<Facturador>) hem.getSession().createCriteria(HabilitacionFactRed.class).add(Restrictions.eq("red", new Red(idRed))).setProjection(Projections.projectionList().add(Projections.property("facturador"))).list();
        return facturadores;
    }

    public List<Entidad> obtenerEntidadesYaPoliticas(Long idRed) {
        List<Entidad> entidades = this.criteriaEntidadesPoliticas(idRed).setProjection(Projections.projectionList().add(Projections.property("entidad"))).list();
        return entidades;
    }

    public List<EntidadPolitica> obtenerEntidadesPoliticas(Long idRed) {
        List<EntidadPolitica> entidadesList = this.criteriaEntidadesPoliticas(idRed).list();
        return entidadesList;
    }

    public List<Entidad> obtenerEntidadesNoPoliticas(Long idRed) {
        List<Entidad> entidades = this.entidadFacade.list();
        List<Entidad> entidadesPoliticas = this.obtenerEntidadesYaPoliticas(idRed);
        entidades.removeAll(entidadesPoliticas);
        return entidades;
    }

    private Criteria criteriaEntidadesPoliticas(Long idRed) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        return hem.getSession().createCriteria(EntidadPolitica.class).add(Restrictions.eq("red", new Red(idRed)));

    }

    public List<HabilitacionFactRed> obtenerHabilitacionesFactRed(Long idRed) {
        HabilitacionFactRed ejemplo = new HabilitacionFactRed();
        ejemplo.setRed(new Red(idRed));
        return this.habilitacionFacturadorRedFacade.list(ejemplo);
    }

    public List<Servicio> obtenerServiciosPorRed(Long idRed, Integer start, Integer limit) {
        Criteria c = this.obtenerCriteriaServiciosPorRed(idRed);
        if (start != null && limit != null) {
            c.setFirstResult(start);
            c.setMaxResults(limit);
        }
        return c.list();
    }

     public Integer cantidadServiciosPorRed(Long idRed) {
        Criteria c=this.obtenerCriteriaServiciosPorRed(idRed);
        c.setProjection(Projections.rowCount());
        return (Integer)c.list().get(0);
    }

    private Criteria obtenerCriteriaServiciosPorRed(Long idRed) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c1 = hem.getSession().createCriteria(Recaudador.class).setProjection(Projections.property("idRecaudador")).createCriteria("red").add(Restrictions.eq("idRed", idRed));
        List<Long> listaRecaudadores = (List<Long>) c1.list();
        Criteria c2 = hem.getSession().createCriteria(Servicio.class).createCriteria("recaudadorCollection").add(Restrictions.in("idRecaudador", listaRecaudadores));
        return c2;
    }
    /*
     * public List<Red> obtenerRedes(String mac) { HibernateEntityManager hem =
     * (HibernateEntityManager) this.getEm().getDelegate(); Criteria c=
     * hem.getSession().createCriteria(HabilitacionRecaudadorRed.class);
     * c.createCriteria("recaudador"). createCriteria("sucursalCollection").
     * createCriteria("terminalCollection"). add(Restrictions.eq("hash",mac));
     * c.setProjection(Projections.projectionList().add(Projections.property("red")));
     * return (List<Red>) c.list();
    }
     */
}
