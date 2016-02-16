/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.documenta.set.main.WsSet;
import py.com.documenta.set.pojo.ddjj.consultar.ConsultarDeclaracionJuradaRequest;
import py.com.documenta.set.pojo.ddjj.consultar.ConsultarDeclaracionJuradaResponse;
import py.com.documenta.set.pojo.ddjj.consultar.DetalleConsulta;
import py.com.documenta.set.pojo.ddjj.guardar.DetalleDDJJ;
import py.com.documenta.set.pojo.ddjj.guardar.GuardarDeclaracionJuradaRequest;
import java.text.SimpleDateFormat;
import javax.persistence.Query;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.entities.*;
import py.com.konecta.redcobros.utiles.Utiles;
import py.com.konecta.redcobros.utiles.UtilesSet;
import static py.com.konecta.redcobros.utiles.UtilesSet.toXml;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FormContribFacadeImpl extends GenericDaoImpl<FormContrib, Long> implements FormContribFacade {

    @Resource
    private SessionContext context;
    @EJB
    private TerminalFacade terminalF;
    @EJB
    private ServicioFacade servicioF;
    @EJB
    private GrupoFacade grupoF;
    @EJB
    private RecaudadorFacade recF;
    @EJB
    private CamposFormContribFacade cfcF;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    private static final Logger logger = Logger.getLogger(FormContribFacadeImpl.class.getName());

    /**
     * **********REPORTE*************************
     */
//     public void generarClearingDistribucion(ClearingFacturador clearing,
//            List<Map<String, Object>> listaMapa) throws Exception {
//        Map<String, Object> mapa;
//        Long idRecaudador;
//        Double monto = null;
//        DistribucionClearingFacturador distribucionClearingFacturador;
//        for (int i = 0; i < listaMapa.size(); i++) {
//            mapa = (Map<String, Object>) listaMapa.get(i);
//            idRecaudador = (Long) mapa.get("idRecaudador");
//            monto = (Double) mapa.get("montoTotal");
//            if (monto.doubleValue() == 0.0) {
//                continue;
//            }
//            distribucionClearingFacturador = new DistribucionClearingFacturador();
//            distribucionClearingFacturador.setClearingFacturador(clearing);
//            distribucionClearingFacturador.setRecaudador(new Recaudador(idRecaudador));
//            distribucionClearingFacturador.setMonto(monto);
//            clearing.getDistribucionClearingFacturadorCollection().add(distribucionClearingFacturador);
//        }
//    }
    /**
     *
     * @param idRed
     * @param idRecaudador
     * @param idSucursal
     * @param idTerminal
     * @param idGestion
     * @param recepcionados
     * @param todosRecepcionados
     * @param controlados
     * @param todosControlados
     * @param fechaDesde
     * @param fechaHasta
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> listaReporteRecContEnv(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean recepcionados, Boolean todosRecepcionados, Boolean controlados, Boolean todosControlados, Date fechaDesde, Date fechaHasta) throws Exception {
        List<Map<String, Object>> lista = null;
        Criteria c = this.obtenerCriteriaReporteRecContEnv(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta);

        // c.createCriteria("usuarioTerminal", "ut").createCriteria("terminal", "t");
        // c.createCriteria("grupo", "gr").createCriteria("gestion", "ge");
        c.createCriteria("formulario", "fr");
        c.createCriteria("contribuyente", "ct");
        ProjectionList pl = Projections.projectionList();
        ///Projections
        pl.add(Projections.property("t.codigoTerminal"), "codigoTerminal");
        pl.add(Projections.property("ge.nroGestion"), "descripcionGestion");
        pl.add(Projections.property("fr.numeroFormulario"), "numeroFormulario");
        pl.add(Projections.property("numeroOrden"), "numeroOrden");
        pl.add(Projections.property("consecutivo"), "consecutivo");
        pl.add(Projections.property("ct.rucNuevo"), "rucNuevo");
        pl.add(Projections.property("totalPagar"), "totalPagar");
        pl.add(Projections.property("fechaControlado"), "fechaControlado");
        pl.add(Projections.property("fechaRecibido"), "fechaRecibido");
        pl.add(Projections.property("fechaCertificadoSet"), "fechaCertificacion");
        pl.add(Projections.property("enviado"), "enviado");
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        lista = (List<Map<String, Object>>) c.list();
        return lista;
    }

    private Criteria obtenerCriteriaReporteRecContEnv(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean recepcionados, Boolean todosRecepcionados, Boolean controlados, Boolean todosControlados, Date fechaDesde, Date fechaHasta) throws Exception {
        //  Criteria c = getCriteriaControlFormulario(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta);
        Criteria c = getCriteriaControlFormulario(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta);

        return c;
    }

    /**
     * *************************************
     */
    private Criteria getCriteriaERA(Long idRed, Date fecha) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        //List<FormContrib> formulariosERA =
        Criteria c = hem.getSession().createCriteria(FormContrib.class, "fc");
        c.add(Restrictions.eq("certificado", "S"));
        c.add(Restrictions.eq("camposValidos", 1));
        c.add(Restrictions.eq("fechaCertificadoSet", fecha));
        //c.add(Restrictions.sqlRestriction("trim({alias}.FECHA_HORA)=trim(?)",fecha,Hibernate.DATE)).
        Criteria c1 = c.createCriteria("grupo").add(Restrictions.eq("cerrado", "S"));
        Criteria c12 = c1.createCriteria("gestion");
        Criteria c121 = c12.createCriteria("terminal");
        c121.createCriteria("sucursal").
                createCriteria("recaudador").
                createCriteria("red").
                add(Restrictions.eq("idRed", idRed));
        c121.addOrder(Order.asc("codigoCajaSet"));
        //c12.createCriteria("usuario").addOrder(Order.asc("codigo"));
        c.addOrder(Order.asc("consecutivo"));
        return c;
    }

    @Override
    public List<FormContrib> formulariosERA(Long idRed, Date fecha) {
        return (List<FormContrib>) this.getCriteriaERA(idRed, fecha).list();
    }

    @Override
    public Integer totalFormulariosERA(Long idRed, Date fecha) {
        Criteria c = this.getCriteriaERA(idRed, fecha);
        ProjectionList pl = Projections.projectionList();
        c.setProjection(Projections.rowCount());
        List result = c.list();
        return (Integer) result.get(0);
    }

    @Override
    public boolean existeFormulariosERA(Long idRed, Date fecha) {
        Criteria c = this.getCriteriaERA(idRed, fecha);
        c.setProjection(Projections.rowCount());
        Integer cantidad = (Integer) c.list().get(0);
        return cantidad > 0;

    }

    @Override
    public Integer cantidadFormulariosClearing(Date fecha, Long idRed) {
        return (Integer) ((List<Map<String, Object>>) this.obtenerCriteria(fecha, idRed, true, null, null).list()).get(0).get("cantidad");
    }

    @Override
    public List<Map<String, Object>> formulariosClearing(Date fecha, Long idRed,
            List<Long> listaRecaudadores,
            Map<Long, List<Long>> mapaRecaudadorSucursales) {
        return this.obtenerCriteria(fecha, idRed, false, listaRecaudadores, mapaRecaudadorSucursales).list();
    }

    @Override
    public boolean anularDigitacion(FormContrib formulario, Terminal t) {

        if (((formulario.getGrupo() == null) || (formulario.getGrupo() != null && formulario.getGrupo().getProcesado().equalsIgnoreCase("N"))) && formulario.getCamposValidos() == 1 && (formulario.getCertificado().equalsIgnoreCase("S"))) {
            formulario.setCamposValidos(2);
            //Anulado
            formulario.setCertificado("A");
            try {
                formulario = this.merge(formulario, t);
                return true;
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                return false;

            }

        } else {
            return false;
        }
    }

    @Override
    public List<Long> getReferencias(Sucursal sucursal, String query) {

        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        UsuarioTerminal ut = new UsuarioTerminal();

        ut.setTerminal(new Terminal());
        ut.getTerminal().setSucursal(sucursal);

        Criteria c = hem.getSession().createCriteria(FormContrib.class);
        Conjunction conj = Restrictions.conjunction();
        Criterion criteria = Restrictions.isNull("migrado");
        Criterion criteria1 = Restrictions.eq("migrado", "N");
        Criterion criteria2 = Restrictions.eq("camposValidos", 1);
        Criterion criteria6 = Restrictions.eq("pagado", 2);
        Criterion criteria5 = Restrictions.eq("fechaPresentacion", new Date());
        //Criterion criteria6 = Restrictions.eq("usuarioTerminal", ut);
        Criterion queryCriterion = null;
        if (query != null && query.matches("[0-9]+")) {
            queryCriterion = Restrictions.eq("idFormContrib", new Long(query));
        }

        Criterion criteria3 = Restrictions.eq("certificado", "N");
        //Criterion criteria4 = Restrictions.gt("totalPagar", 0);

        conj.add(criteria);
        //conj.add(criteria1);

        if (queryCriterion != null) {
            c.add(queryCriterion);
        }

        //c.add(criteria1);
        c.add(criteria2);
        c.add(criteria6);
        c.add(criteria5);
        c.add(criteria3);
        c.add(Restrictions.or(conj, criteria1));

        c.createCriteria("usuarioTerminalCarga").
                createCriteria("terminal").
                createCriteria("sucursal").
                add(Restrictions.eq("idSucursal", sucursal.getIdSucursal()));
        c.addOrder(Order.asc("idFormContrib"));
        c.setProjection(Projections.projectionList().add(Projections.property("idFormContrib")));
        return (List<Long>) c.list();

    }

    private Criteria getCriteriaControlFormulario(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean recepcionados, Boolean todosRecepcionados, Boolean controlados, Boolean todosControlados, Date fechaDesde, Date fechaHasta) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c1 = hem.getSession().createCriteria(FormContrib.class);
        c1.add(Restrictions.isNotNull("grupo"));

        if (fechaDesde != null && fechaHasta != null) {
            c1.add(Restrictions.between("fechaCertificadoSet", Utiles.primerMomentoFecha(fechaDesde), Utiles.primerMomentoFecha(fechaHasta)));
        } else if (fechaDesde != null) {
            c1.add(Restrictions.eq("fechaCertificadoSet", Utiles.primerMomentoFecha(fechaDesde)));
        }

        //  if (idRed != null) {
        Criteria cTerminal = c1.createCriteria("usuarioTerminalCarga", "ut").createCriteria("terminal", "t");
        Criteria cSucursal = cTerminal.createCriteria("sucursal");
        Criteria cRecaudador = cSucursal.createCriteria("recaudador");
        Criteria cRed = cRecaudador.createCriteria("red");
        if (idRed != null) {
            cRed.add(Restrictions.eq("idRed", idRed));

        }

        if (idRecaudador != null) {
            cRecaudador.add(Restrictions.eq("idRecaudador", idRecaudador));

        }
        if (idSucursal != null) {
            cSucursal.add(Restrictions.eq("idSucursal", idSucursal));
        }

        if (idTerminal != null) {
            cTerminal.add(Restrictions.eq("idTerminal", idTerminal));
        }

        //   }
        Criteria cGrupo = c1.createCriteria("grupo", "gr");
        Criteria cGestion = cGrupo.createCriteria("gestion", "ge");
        if (idGestion != null) {
            cGestion.add(Restrictions.eq("idGestion", idGestion));
        }
        if (recepcionados && !todosRecepcionados) {
            c1.add(Restrictions.isNotNull("fechaRecibido"));
        }

        if (!recepcionados && !todosRecepcionados) {
            c1.add(Restrictions.isNull("fechaRecibido"));
        }

        if (controlados && !todosControlados) {
            c1.add(Restrictions.isNotNull("fechaControlado"));
        }

        if (!controlados && !todosControlados) {
            c1.add(Restrictions.isNull("fechaControlado"));
        }
        c1.addOrder(Order.asc("consecutivo"));
        return c1;
    }

    @Override
    public List<FormContrib> getControlFormulario(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean recepcionados, Boolean todosRecepcionados, Boolean controlados, Boolean todosControlados, Date fechaDesde, Date fechaHasta, Integer start, Integer limit) {
        Criteria c = this.getCriteriaControlFormulario(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta);
        if (start != null && limit != null) {
            c.setFirstResult(start);
            c.setMaxResults(limit);
        }
        return c.list();
    }

    @Override
    public Integer getTotalControlFormulario(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean recepcionados, Boolean todosRecepcionados, Boolean controlados, Boolean todosControlados, Date fechaDesde, Date fechaHasta) {
        Criteria c = this.getCriteriaControlFormulario(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta);
        c.setProjection(Projections.rowCount());
        return (Integer) c.list().get(0);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public FormContrib certificarFormulario(Long idFormContrib, Integer era, UsuarioTerminal ut, Long idRed, Long idSucursal) throws Exception {
        try {
            Date fecha = new Date();
            FormContrib formContrib = this.get(idFormContrib);

            if (formContrib.getCertificado().equalsIgnoreCase("N")) {
                if (formContrib.getTotalPagar() == 0) {
                    //monto cero
                    formContrib.setPagado(1);
                }

                Servicio s = new Servicio();
                s.setCodigoTransaccional("DDJJ-SET");
                Map<String, Object> mapaServicio = this.servicioF.get(s, new String[]{"idServicio",
                    "tamanhoGrupo"});
                s.setTamanhoGrupo((Integer) mapaServicio.get("tamanhoGrupo"));
                s.setIdServicio((Long) mapaServicio.get("idServicio"));
                //s = this.servicioF.get(s);

                Grupo g = this.grupoF.obtenerGrupo(ut, s);
                formContrib.setGrupo(g);
                formContrib.setPosGrupo(g.getProximaPosicion());
                formContrib.setFechaCertificadoSet(ut.getTerminal().getFechaConsecutivoSet());
                formContrib.setFechaHoraRealCertificado(fecha);

                if (recF.comprobarHabilitacionRecaudadorServicio(s.getIdServicio(), g.getGestion().getTerminal().getSucursal().getRecaudador().getIdRecaudador())) {
                    Long numeroOrdenConERA = UtilesSet.ERANumeroOrden(era.longValue(),
                            this.recF.obtenerProximoValorOrden(ut.getTerminal().getSucursal().getRecaudador().getIdRecaudador()));
                    formContrib.setNumeroOrden(numeroOrdenConERA);

                    //consecutivo
                    // usuario en terminal
                    Long consecutivoActual = ut.getTerminal().getConsecutivoActualForm();
                    formContrib.setConsecutivo(consecutivoActual);
                    ut.getTerminal().setConsecutivoActualForm(consecutivoActual + 1);
                    terminalF.merge(ut.getTerminal());
                    formContrib.setCodigoHash(formContrib.toStringHash());
                    formContrib.setCertificado("S");
                    formContrib = super.merge(formContrib);
                } else {
                    throw new Exception("El Servicio no se encuentra Habilitado en el Recaudador");

                }

            } else if (formContrib.getCertificado().equalsIgnoreCase("A")) {
                throw new Exception("El formulario fue anulado");
            }

            /**
             * ******ACAAAAAAAAAAAAAAAA CODIGO PARA ONLINE SE TIENE TODO EN
             * ESTE PUNTO XD idFormContrib****
             */
            WsSet wsSet = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            ParametroSistema p = new ParametroSistema();
            p.setNombreParametro("SET_ONLINE");
            ParametroSistema setOnline = parametroSistemaFacade.get(p);
            String mensaje = "OK";
            logger.log(Level.INFO, "FLAAAAG DE SET-ONLINE {0}", setOnline);
            if (setOnline != null) {//agregar && idTipoPago.equals(1L)
                logger.info("ENTRE FLUJO NUEVO SET");
                List<ValorEtiquetaSet> listValores = this.getValorEtiqueta(idFormContrib); // este es un entity especial que trae el par <etiqueta,valor> para el tema del set

                GuardarDeclaracionJuradaRequest req = new GuardarDeclaracionJuradaRequest();
                req.setFormulario(formContrib.getFormulario().getNumeroFormulario().toString());
                req.setVersion(formContrib.getFormulario().getVersion().toString());
                req.setFechaDocumento(formatter.format(formContrib.getFechaPresentacion()));
                req.setEra(era.toString());
                String idTerminal = String.valueOf(ut.getTerminal().getIdTerminal());
                String sucursal = idSucursal.toString().length() > 3 ? idSucursal.toString().substring(idSucursal.toString().length() - 3) : idSucursal.toString();
                req.setSucursalEra(sucursal);
                String cajero = idTerminal.length() > 3 ? idTerminal.substring(idTerminal.length() - 3) : idTerminal;
                req.setCajero(cajero);
                req.setConsecutivo(ut.getTerminal().getConsecutivoActualForm().toString());

                req.setHash("0");
                String detalle = "";
                for (ValorEtiquetaSet valor : listValores) {
                    detalle += "{" + valor.getEtiqueta() + "}" + valor.getValor() + "{/" + valor.getEtiqueta() + "}";
                }
                DetalleDDJJ detalleddjj = new DetalleDDJJ();
                detalleddjj.setC(detalle);
                detalleddjj.setNumeroDocumento(era.toString() + formContrib.getIdFormContrib().toString());
                detalleddjj.setNumeroCedula(formContrib.getRuc());
                detalleddjj.setRuc(formContrib.getRuc());
                detalleddjj.setDv(formContrib.getDigitoVerificador());
                req.setDetalle(detalleddjj);
                String xmlDDJJ = toXml(req);

                p = new ParametroSistema();
                p.setNombreParametro("SET_USER");
                String usuario = parametroSistemaFacade.get(p).getValor();

                p.setNombreParametro("SET_PWD");
                String pwd = parametroSistemaFacade.get(p).getValor();

                p.setNombreParametro("SET_URL_ERA");
                String urlEra = parametroSistemaFacade.get(p).getValor();

                p.setNombreParametro("SET_URL_PAGO");
                String urlPago = parametroSistemaFacade.get(p).getValor();

                p.setNombreParametro("SET_URL_SESSION");
                String urlSession = parametroSistemaFacade.get(p).getValor();

                p.setNombreParametro("SET_CONN");
                Integer connTO = 20;
                try {
                    String connToSTR = parametroSistemaFacade.get(p).getValor();
                    if (connToSTR == null) {
                        logger.log(Level.WARNING, "No existe connTO SET");
                    } else {
                        connTO = Integer.parseInt(connToSTR);
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "", e);
                }

                p.setNombreParametro("SET_READ");
                Integer readTO = 90;
                try {
                    String readToSTR = parametroSistemaFacade.get(p).getValor();
                    if (readToSTR == null) {
                        logger.log(Level.WARNING, "No existe readTO SET");
                    } else {
                        readTO = Integer.parseInt(readToSTR);
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "", e);
                }
                wsSet = new WsSet(urlSession, urlEra, urlPago, usuario, pwd, connTO, readTO);
                boolean timeoutDDJJ = false;
                try {
                    wsSet.guardarDDJJ(xmlDDJJ, UtilesSet.calcularCodigoHash(xmlDDJJ));
                    logger.info(xmlDDJJ);
                } catch (Exception e) {
                    timeoutDDJJ = true;
                }
                if (!timeoutDDJJ) {
                    /*Aca il llamado al ws*/
                    ConsultarDeclaracionJuradaRequest requestConsultaDDJJ = new ConsultarDeclaracionJuradaRequest();
                    DetalleConsulta detalleConsultaDDJJ = new DetalleConsulta();
                    detalleConsultaDDJJ.setNumeroDocumento(era.toString() + formContrib.getIdFormContrib().toString());
                    requestConsultaDDJJ.setDetalle(detalleConsultaDDJJ);
                    requestConsultaDDJJ.setFormulario(formContrib.getFormulario().getNumeroFormulario().toString());
                    requestConsultaDDJJ.setEra(era.toString());
                    requestConsultaDDJJ.setVersion(formContrib.getFormulario().getVersion().toString());
                    String xmlConsultaDDJJ = toXml(requestConsultaDDJJ);
                    /*Aca il llamado al ws*/
                    logger.info(xmlConsultaDDJJ);
                    ConsultarDeclaracionJuradaResponse resp = wsSet.consultarDDJJ(xmlConsultaDDJJ, UtilesSet.calcularCodigoHash(xmlConsultaDDJJ));
                    logger.info(resp.toString());
                    formContrib.setFlgPagoOnline(resp.getEstado().equals("ACEPTADO") ? 2 : 3);
                    logger.log(Level.INFO, "FLAG ACEPTADO {0}", resp.getEstado());
                    this.merge(formContrib);

                    if (!resp.getEstado().equals("ACEPTADO")) {
                        logger.info("ENTRE 1");
                        try {
                            mensaje = resp.getErrores().getErrores().get(0).getDescripcion();
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Error con set", resp.toString());
                            mensaje = "No se pudo realizar la presentacion";
                        }
//                    context.setRollbackOnly();
//                    return respuesta;
                    }
                } else {
                    mensaje = "No se pudo realizar la presentacion, problemas de comunicacion con la SET";
                }
            } else {
                logger.info("NO ESTA SET ONLINE");
            }

            logger.log(Level.INFO, "Mensaje {0}", mensaje);
            if (!mensaje.equals("OK")) {
                throw new Exception(mensaje);
            }

            return formContrib;
        } catch (Exception e) {
            //abortar transaccion

            context.setRollbackOnly();
            throw e;
        }
    }

    private boolean comprobarHabilitacion(Terminal t) {
        Servicio s = new Servicio();
        s.setCodigoTransaccional("DDJJ-SET");
        //s = this.servicioF.get(s);
        Map<String, Object> mapa = this.servicioF.get(s, new String[]{"idServicio"});
        s.setIdServicio((Long) mapa.get("idServicio"));
        return recF.comprobarHabilitacionRecaudadorServicio(s.getIdServicio(), t.getSucursal().getRecaudador().getIdRecaudador());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void save(FormContrib fc, Terminal t) throws Exception {
        try {
            if (comprobarHabilitacion(t)) {
                super.save(fc);
            } else {
                throw new Exception("El Servicio no se encuentra Habilitado en el Recaudador");
            }
        } catch (Exception e) {
            //abortar transaccion
            context.setRollbackOnly();
            throw e;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public FormContrib merge(FormContrib fc, Terminal t) throws Exception {
        return this.merge(fc, null, t);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public FormContrib merge(FormContrib fc, List<CamposFormContrib> campos, Terminal t) throws Exception {
        try {
            if (comprobarHabilitacion(t)) {
                fc = super.merge(fc);
                if (campos != null) {
                    for (CamposFormContrib campo : campos) {
                        campo.setFormContrib(fc);
                        cfcF.merge(campo);
                    }
                }
                return fc;
            } else {
                throw new Exception("El Servicio no se encuentra Habilitado en el Recaudador");
            }
        } catch (Exception e) {
            //abortar transaccion
            context.setRollbackOnly();
            throw e;
        }
    }
// SE UTILIZABA CUANDO ERA REMOTE
//    @Override
//    @Deprecated
//    public FormContrib merge(FormContrib fc) {
//       throw new UnsupportedOperationException("No se soporta la operacion guardar con un solo parametro");
//    }

//    @Override
//    @Deprecated
//    public void save(FormContrib fc) {
//        throw new UnsupportedOperationException("No se soporta la operacion guardar con un solo parametro");
//    }
    private Criteria obtenerCriteria(Date fecha, Long idRed, boolean soloCantidad,
            List<Long> listaRecaudadores,
            Map<Long, List<Long>> mapaRecaudadorSucursales) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(FormContrib.class);
        c.add(Restrictions.eq("certificado", "S"));
        c.add(Restrictions.eq("camposValidos", 1));
        c.add(Restrictions.eq("fechaCertificadoSet", fecha));
        Criteria cGrupo = c.createCriteria("grupo");
        Criteria cSucursal = cGrupo.add(Restrictions.eq("cerrado", "S")).createCriteria("gestion").createCriteria("terminal").createCriteria("sucursal", "suc");
        if (listaRecaudadores != null) {
            Disjunction disjunction = Restrictions.disjunction();
            List<Long> listaSucursales;
            for (Long idRec : listaRecaudadores) {
                listaSucursales = mapaRecaudadorSucursales.get(idRec);
                disjunction.add(Restrictions.and(
                        Restrictions.eq("recaudador", new Recaudador(idRec)),
                        Restrictions.in("idSucursal", listaSucursales)));
            }
            cSucursal.add(disjunction);
        }
        Criteria cRecaudador = cSucursal.createCriteria("recaudador", "rec");
        cRecaudador.add(Restrictions.eq("red", new Red(idRed))).createCriteria("red", "r");
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.rowCount(), "cantidad");
        if (!soloCantidad) {
            if (listaRecaudadores == null) {
                pl.add(Projections.groupProperty("rec.idRecaudador"), "idRecaudador");
                pl.add(Projections.groupProperty("suc.idSucursal"), "idSucursal");
            } else {
                //agrupamos solo por rec ya que excluimos sucursales
                pl.add(Projections.groupProperty("rec.idRecaudador"), "idRecaudador");
            }//no agrupamos nada, ultimo nivel
        }
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return c;
    }

    public List<ValorEtiquetaSet> getValorEtiqueta(Long idFormContrib) {
        String sql = "SELECT ROWNUM AS ID,"
                + "       'C' || CFC.NUMERO_CAMPO AS ETIQUETA,"
                + "       CFC.VALOR AS VALOR"
                + "  FROM CAMPOS_FORM_CONTRIB CFC"
                + " WHERE CFC.FORM_CONTRIB = :id";
        Query query = getEm().createNativeQuery(sql, ValorEtiquetaSet.class);
        query.setParameter("id", idFormContrib);
        List<ValorEtiquetaSet> response = (List<ValorEtiquetaSet>) query.getResultList();
        return response;

    }
}
