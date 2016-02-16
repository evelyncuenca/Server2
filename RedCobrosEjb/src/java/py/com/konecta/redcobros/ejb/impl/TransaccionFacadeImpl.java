/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.documenta.set.main.WsSet;
import py.com.documenta.set.pojo.ddjj.consultar.ConsultarDeclaracionJuradaRequest;
import py.com.documenta.set.pojo.ddjj.consultar.ConsultarDeclaracionJuradaResponse;
import py.com.documenta.set.pojo.ddjj.consultar.DetalleConsulta;
import py.com.documenta.set.pojo.ddjj.guardar.DetalleDDJJ;
import py.com.documenta.set.pojo.ddjj.guardar.GuardarDeclaracionJuradaRequest;
import py.com.documenta.set.pojo.ot.guardar.GuardarOTRequest;
import py.com.documenta.set.pojo.ot.guardar.GuardarOTResponse;
import py.com.documenta.set.pojo.pago.guardar.GuardarPagoRequest;
import py.com.documenta.set.pojo.pago.guardar.GuardarPagoResponse;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.ejb.core.*;
import py.com.konecta.redcobros.entities.*;
import py.com.konecta.redcobros.utiles.Utiles;
import py.com.konecta.redcobros.utiles.UtilesSet;
import static py.com.konecta.redcobros.utiles.UtilesSet.toXml;

/**
 *
 * @author Klaus
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TransaccionFacadeImpl extends GenericDaoImpl<Transaccion, Long> implements TransaccionFacade {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private LogHistoricoTransFacade logHistTransF;
    @EJB
    private LogHistoricoFacade logHistF;
    @EJB
    private EstadoTransaccionFacade estaTransF;
    @EJB
    private TerminalFacade termF;
    @EJB
    private UsuarioTerminalFacade usuaTermF;
    @EJB
    private ServicioFacade servF;
    @EJB
    private RecaudadorFacade recF;
    @EJB
    private RedFacade redF;
    @EJB
    private TipoPagoFacade tipoPagoF;
    @EJB
    private MonedaFacade moneF;
    @EJB
    private TransaccionFacade transF;
    @EJB
    private FormContribFacade formContribFacade;
    @EJB
    private BoletaPagoContribFacade boletaPagoContribFacade;
    @EJB
    private ContribuyentesFacade contribuyentesFacade;
    @EJB
    private FormularioImpuestoFacade formularioImpuestoFacade;
    @EJB
    private ImpuestoFacade impuestoFacade;
    @Resource
    private SessionContext context;
    @EJB
    private GestionFacade gestionFacade;
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private NumeroOtFacade numeroOtFacade;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;

    private static final Logger logger = Logger.getLogger(TransaccionFacadeImpl.class.getName());

//    public List<Map<String, Object>> listaReporteRecContEnv(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Boolean todosFormaPago, Date fechaDesde, Date fechaHasta) throws Exception {
//        List<Map<String, Object>> lista = null;
//        Criteria c = this.obtenerCriteriaReporteRecContEnv(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, fechaDesde, fechaHasta);
//
//        /*****************/
//        /*****************/
//        c.createCriteria("tipoPago", "tipoPago");
//        c.createCriteria("gestion").createCriteria("terminal");
//
//        //c.createCriteria("gestion", "ges").createCriteria("terminal", "ter");
//        // Criteria cBoletaContrib =  c.createCriteria("boletaPagoContrib", "bltPgCnt");
//        //Criteria cFormContrib =  c.createCriteria("formContrib", "formContrib");
//        //cBoletaContrib.createCriteria("grupo", "bltPgCntGrp").createCriteria("gestion", "bltPgCntGe");
//        //c.createCriteria("boletaPagoContrib", "bltPgCnt").createCriteria("grupo", "bltPgCntGrp").createCriteria("gestion", "bltPgCntGe");
//        //cBoletaContrib.createCriteria("formContrib", "bltPgFormContrib").createCriteria("formulario", "bltFormulario");
//        //c.createCriteria("grupo", "gr").createCriteria("gestion", "ge");
//        //c.createCriteria("formulario", "fr");
//        //c.createCriteria("contribuyente", "ct");
//
//        ProjectionList pl = Projections.projectionList();
//        ///Projections
//        pl.add(Projections.property("tipoPago.descripcion"), "tipoPagoDescripcion");
//        pl.add(Projections.property("fechaHoraSistema"), "fechaHoraSistema");
//        pl.add(Projections.property("numeroCheque"), "numeroCheque");
//        pl.add(Projections.property("terminal.codigoTerminal"), "codigoTerminal");
//        c.setProjection(pl);
//        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//        lista = (List<Map<String, Object>>) c.list();
//
//
//        for (Map<String, Object> mapaClearing : lista) {
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.DESCRIPCION_GESTION, e.getBoletaPagoContrib().getGrupo().getGestion().getNroGestion());//Numero de Gestión de la boleta de Pago.
//            if (e.getBoletaPagoContrib().getFormContrib() != null) {
//                ejemploFI.setNumeroFormulario(e.getBoletaPagoContrib().getFormContrib().getFormulario().getNumeroFormulario());
//                jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_IMPUESTO, formularioImpuestoFacade.get(ejemploFI).getImpuesto());
//            } else {
//                jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_IMPUESTO, "-");
//            }
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_ORDEN, e.getBoletaPagoContrib().getNumeroOrden());
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.CONSECUTIVO, (e.getBoletaPagoContrib() != null) ? e.getBoletaPagoContrib().getConsecutivo() : "-");
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.RUC, e.getBoletaPagoContrib().getContribuyente().getRucNuevo());
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.MONTO_TOTAL, (e.getFormContrib() != null) ? UtilesSet.formatearNumerosSeparadorMiles(e.getFormContrib().getTotalPagar(), true) : "-");
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.MONTO_PAGADO, UtilesSet.formatearNumerosSeparadorMiles(e.getBoletaPagoContrib().getTotal(), true));
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.DESCRIPCION_BANCO, (e.getEntidadFinanciera() != null) ? e.getEntidadFinanciera().getDescripcion() : "-");
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.RECHAZADO, (e.getEstadoTransaccion() != null && e.getEstadoTransaccion().getIdEstadoTransaccion().equals(EstadoTransaccion.RECHAZADO)) ? "S" : (e.getEstadoTransaccion() != null && e.getEstadoTransaccion().getIdEstadoTransaccion().equals(EstadoTransaccion.ACEPTADO)) ? "N" : "-");
//            jsoneFormContrib.put(CONTROL_FORMULARIOS.ENVIADO, (e.getFormContrib() != null && e.getFormContrib().getEnviado() != null) ? e.getFormContrib().getEnviado() : "-");
//
//            if () /****************************************/
//            {
//                idConfiguracionComisional = (Long) mapaClearing.get("idConfiguracionComisional");
//            }
//            cc = this.configComFacade.get(idConfiguracionComisional);
//            if (cc.getRecaudador() != null && cc.getSucursal() != null) {
//                descripcionCC = cc.getRecaudador().getDescripcion() + " - " + cc.getSucursal().getDescripcion();
//            } else if (cc.getRecaudador() != null) {
//                descripcionCC = cc.getRecaudador().getDescripcion();
//            } else {
//                descripcionCC = "--";
//            }
//            mapaClearing.put("configuracionComisional", descripcionCC);
//            idDistribucionClearing = (Long) mapaClearing.get("idDistribucionClearing");
//            listaDC = (List<Map<String, Object>>) this.dcdFacade.listaReporte(idDistribucionClearing);
//            mapaClearing.put("distribucionClearingDetalle",
//                    new JRMapCollectionDataSource(listaDC));
//        }
//
//
//        return lista;
//    }
//
//    private Criteria obtenerCriteriaReporteRecContEnv(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Boolean todosFormaPago, Date fechaDesde, Date fechaHasta) throws Exception {
//        //  Criteria c = getCriteriaControlFormulario(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta);
//        Criteria c = this.getCriteriaControlFormularioBoletas(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, fechaDesde, fechaHasta);
//
//        return c;
//    }
    /**
     * **************************
     */
    private Criteria getCriteriaControlFormularioBoletas(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Boolean todosFormaPago, Date fechaDesde, Date fechaHasta, Boolean filtroAceptado, Boolean filtroRechazados, Boolean todosAceptadosRechazados) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c1 = hem.getSession().createCriteria(Transaccion.class);
        //  Criteria cTransaccion = c1.createCriteria("transaccion");

        c1.add(Restrictions.isNotNull("boletaPagoContrib"));//Solo las que tienen boleta

        if (fechaDesde != null && fechaHasta != null) {
            c1.add(Restrictions.between("fechaHoraSistema", Utiles.primerMomentoFecha(fechaDesde), Utiles.ultimoMomentoFecha(fechaHasta)));

        } else if (fechaDesde != null) {

            c1.add(Restrictions.between("fechaHoraSistema", Utiles.primerMomentoFecha(fechaDesde), Utiles.ultimoMomentoFecha(fechaDesde)));
        }
        Transaccion tra = new Transaccion();
        //tra.getGestion().getTerminal().getSucursal().getRecaudador().getR

        if (idRed != null) {
            Criteria cBoleta = c1.createCriteria("boletaPagoContrib");
            Criteria cGestion = c1.createCriteria("gestion");
            Criteria cTerminal = cGestion.createCriteria("terminal");
            Criteria cSucursal = cTerminal.createCriteria("sucursal");
            Criteria cRecaudador = cSucursal.createCriteria("recaudador");
            Criteria cRed = cRecaudador.createCriteria("red");
            // cRed = cRecaudador.createCriteria("red");
            cBoleta.addOrder(Order.asc("consecutivo"));
            cRed.add(Restrictions.eq("idRed", idRed));

            if (idRecaudador != null) {
                cRecaudador.add(Restrictions.eq("idRecaudador", idRecaudador));

            }
            if (idSucursal != null) {
                cSucursal.add(Restrictions.eq("idSucursal", idSucursal));
            }

            if (idTerminal != null) {
                cTerminal.add(Restrictions.eq("idTerminal", idTerminal));
            }

            if (idGestion != null) {
                cGestion.add(Restrictions.eq("idGestion", idGestion));
            }

        }

        if (filtroCheque && !todosFormaPago) {
            c1.createCriteria("tipoPago").add(Restrictions.eq("idTipoPago", TipoPago.CHEQUE));
        } else if (filtroEfectivo && !todosFormaPago) {
            c1.createCriteria("tipoPago").add(Restrictions.eq("idTipoPago", TipoPago.EFECTIVO));
        }
        if (filtroAceptado && !todosAceptadosRechazados) {
            c1.createCriteria("estadoTransaccion").add(Restrictions.eq("idEstadoTransaccion", EstadoTransaccion.ACEPTADO));
        } else if (filtroRechazados && !todosAceptadosRechazados) {
            c1.createCriteria("estadoTransaccion").add(Restrictions.eq("idEstadoTransaccion", EstadoTransaccion.RECHAZADO));
        }

        //cG.addOrder(Order.asc("consecutivo"));
        return c1;
    }

    @Override
    public List<Transaccion> getControlFormularioBoletas(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Boolean todosFormaPago, Date fechaDesde, Date fechaHasta, Boolean filtroAceptado, Boolean filtroRechazados, Boolean todosAceptadosRechazados, Integer start, Integer limit) {
        Criteria c = this.getCriteriaControlFormularioBoletas(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, fechaDesde, fechaHasta, filtroAceptado, filtroRechazados, todosAceptadosRechazados);
        if (start != null && limit != null) {
            c.setFirstResult(start);
            c.setMaxResults(limit);
        }
        return c.list();
    }

    @Override
    public Integer getTotalControlFormularioBoletas(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Boolean todosFormaPago, Boolean filtroAceptado, Boolean filtroRechazados, Boolean todosAceptadosRechazados, Date fechaDesde, Date fechaHasta) {
        Criteria c = this.getCriteriaControlFormularioBoletas(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, fechaDesde, fechaHasta, filtroAceptado, filtroRechazados, todosAceptadosRechazados);
        c.setProjection(Projections.rowCount());
        return (Integer) c.list().get(0);
    }

    /**
     *
     * @param idTransaccionRedOrigen
     * @param idRed
     * @param idRecaudador
     * @param idSucursal
     * @param idTerminal
     * @param idUsuario
     * @param nombreUsuario
     * @param contrasenha
     * @param fechaHoraRed
     * @param fechaHoraTerminal
     * @param idTipoPago
     * @param tasaCambio
     * @param idTipoMoneda
     * @param montoTotal
     * @param idFormContrib
     * @param era
     * @param numeroCheque
     * @param idEntidadFinanciera
     * @param fechaCheque
     * @param session
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Respuesta doTransacctionSetPagoFormulario(Long idTransaccionRedOrigen, Long idRed, Long idRecaudador,
            Long idSucursal, String idTerminal, String idUsuario, String nombreUsuario,
            String contrasenha, String fechaHoraRed, String fechaHoraTerminal, Long idTipoPago, Double tasaCambio,
            Long idTipoMoneda, Double montoTotal, Long idFormContrib, Long era, Integer numeroCheque, Long idEntidadFinanciera, String fechaCheque, HttpSession session) {

        Respuesta respuesta = null;

        //ALAMBRE
        String codigoTransaccional = "PAGO-SET";
        logger.info("Iniciando Transaccion Pago Formulario");

        try {
            respuesta = doTransacction(idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario, nombreUsuario, contrasenha, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, numeroCheque, idEntidadFinanciera, fechaCheque);
            if (respuesta.getIdRespuesta() > 1000) {
                context.setRollbackOnly();
                return respuesta;
            }
            Transaccion transaccion = this.get(respuesta.getIdTransaccion());
            //TipoPago tipoPago = new TipoPago();
            //tipoPago = tipoPagoFacade.get(idTipoPago);
            transaccion.setTipoPago(new TipoPago(idTipoPago));
            FormContrib formContribTransa = formContribFacade.get(idFormContrib);
            formContribTransa.setTransaccion(transaccion);
            formContribTransa.setPagado(1);
            formContribTransa.setFlgPagoOnline(1);
            formContribTransa = formContribFacade.merge(formContribTransa, termF.get(new Long(idTerminal)));
            UsuarioTerminal ut = new UsuarioTerminal();
            ut.setUsuario(new Usuario(new Long(idUsuario)));
            ut.setTerminal(new Terminal(new Long(idTerminal)));
            ut = this.usuaTermF.get(ut);
            if (formContribTransa.getCertificado().equals("N")) {
                formContribTransa = formContribFacade.certificarFormulario(idFormContrib, era.intValue(), ut, idRed, idSucursal);
            }

            // Se liga la historia del log con una transaccion
            respuesta.getLht().setTransaccion(new Transaccion(respuesta.getIdTransaccion()));
            logHistTransF.merge(respuesta.getLht());

            /**
             * *********BOLETA PAGO PARA PAGO CON FORMULARIO****************
             */
            //se copia todo lo necesario desde form contrib
            BoletaPagoContrib boletaPagoContribTransa = new BoletaPagoContrib();
            boletaPagoContribTransa.setContribuyente(formContribTransa.getContribuyente());
            boletaPagoContribTransa.setTransaccion(transaccion);
            boletaPagoContribTransa.setFechaHoraRecepcion(respuesta.getFechaHoraCore());
            boletaPagoContribTransa.setFechaCobro(ut.getTerminal().getFechaConsecutivoSet());
            //boletaPagoContribTransa.setFormulario(formContribTransa.getIdFormulario().getNumeroFormulario());
            //boletaPagoContribTransa.setVersion(formContribTransa.getIdFormulario().getVersion());
            boletaPagoContribTransa.setFormulario(97);
            boletaPagoContribTransa.setVersion(1);
            String periodo = formContribTransa.getPeriodoFiscal();
            if (periodo != null && !periodo.isEmpty()) {
                periodo = periodo.replaceAll("/", "");
                if (periodo.length() == 8) {
                    Integer fechaPeriodo
                            = new Integer(periodo.substring(4)
                                    + periodo.substring(2, 4)
                                    + periodo.substring(0, 2));
                    if (fechaPeriodo < 20070101) {
                        boletaPagoContribTransa.setPeriodo(periodo);
                    } else {
                        boletaPagoContribTransa.setPeriodo(null);
                    }
                } else if (periodo.length() == 6) {
                    Integer fechaPeriodo = new Integer(periodo.substring(2) + periodo.substring(0, 2));
                    if (fechaPeriodo < 200701) {
                        boletaPagoContribTransa.setPeriodo(periodo);
                    } else {
                        boletaPagoContribTransa.setPeriodo(null);
                    }
                } else {
                    Integer fechaPeriodo = new Integer(periodo);
                    if (fechaPeriodo < 2006) {
                        boletaPagoContribTransa.setPeriodo(periodo);
                    } else {
                        boletaPagoContribTransa.setPeriodo(null);
                    }
                }
            } else {
                boletaPagoContribTransa.setPeriodo(null);
            }

            boletaPagoContribTransa.setTotal(montoTotal);
            boletaPagoContribTransa.setNumeroResolucion(0L);
            boletaPagoContribTransa.setGrupo(respuesta.getGrupo());
            boletaPagoContribTransa.setPosGrupo(respuesta.getGrupo().getProximaPosicion());
            //para obtener el impuesto a partir del formulario que se esta pagando
            FormularioImpuesto ejemploFI = new FormularioImpuesto();
            ejemploFI.setNumeroFormulario(formContribTransa.getFormulario().getNumeroFormulario());
            Integer numeroImpuesto = this.formularioImpuestoFacade.get(ejemploFI).getImpuesto();
            Impuesto ejemploI = new Impuesto();
            ejemploI.setNumero(numeroImpuesto);
            boletaPagoContribTransa.setImpuesto(impuestoFacade.get(ejemploI));
            //seteamos el numero de orden
            Long numeroOrdenConERA = UtilesSet.ERANumeroOrden(era, this.recF.obtenerProximoValorOrden(idRecaudador));
            boletaPagoContribTransa.setNumeroOrden(numeroOrdenConERA);
            //consecutivo
            //consecutivo
            Long consecutivoActual = transaccion.getGestion().getTerminal().getConsecutivoActualForm();
            boletaPagoContribTransa.setConsecutivo(consecutivoActual);
            transaccion.getGestion().getTerminal().setConsecutivoActualForm(consecutivoActual + 1);
            termF.merge(transaccion.getGestion().getTerminal());

            //seteamos el hash
//            boletaPagoContribTransa.setCodigoHash(boletaPagoContribTransa.toStringHash());
            boletaPagoContribTransa.setFormContrib(formContribTransa);
            boletaPagoContribTransa = boletaPagoContribFacade.merge(boletaPagoContribTransa);

            /**
             * *******FIN GENERAR BOLETA PAGO A PARTIR DE PAGO CON
             * FORMULARIO*************************
             */
            transaccion.setBoletaPagoContrib(boletaPagoContribTransa);
            transaccion.setFormContrib(formContribTransa);
            // transaccion.setFlagAnulado("N");
            transaccion = merge(transaccion);

            //transaccion.setTicket(UtilesSet.generarTicketTransa(transaccion));
            // transaccion.setCertificacion(UtilesSet.generarCertificacionFormularioTransa(transaccion));
            // transF.merge(transaccion);
            respuesta = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.MSG_OK.longValue(), respuesta.getFechaHoraCore(),
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, respuesta.getLht());
            respuesta.setIdTransaccion(transaccion.getIdTransaccion());
            //respuesta.setTicket(transaccion.getTicket());
            // respuesta.setCertificacion(transaccion.getCertificacion());

        } catch (Exception e) {
            //abortar transaccion
            logger.log(Level.SEVERE, e.getMessage(), e);
            respuesta = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_TRANS_NO_ENLAZADA_AL_SERVICIO.longValue(), respuesta.getFechaHoraCore(),
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, respuesta.getLht());

            context.setRollbackOnly();
        } finally {

            return respuesta;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Respuesta doTransacctionSetBoletaPago(Long idTransaccionRedOrigen, Long idRed, Long idRecaudador,
            Long idSucursal, String idTerminal, String idUsuario, String nombreUsuario,
            String contrasenha, String fechaHoraRed, String fechaHoraTerminal, Long idTipoPago, Double tasaCambio,
            Long idTipoMoneda, Double montoTotal, String ruc, String dv, String periodo, Long idImpuesto, Long idFormulario, Long versionFormulario, Long era, Long nroResolucion, Integer numeroCheque, Long idEntidadFinanciera, String fechaCheque, HttpSession session) {

        Respuesta respuesta = null;
        Contribuyentes contribuyente = null;
        String codigoTransaccional = "PAGO-SET";
        try {

            respuesta = doTransacction(idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario, nombreUsuario, contrasenha, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, numeroCheque, idEntidadFinanciera, fechaCheque);
            if (respuesta.getIdRespuesta() > 1000) {
                context.setRollbackOnly();
                return respuesta;
            }
            Transaccion transaccion = this.get(respuesta.getIdTransaccion());
            //TipoPago tipoPago = new TipoPago();
            //tipoPago = tipoPagoFacade.get(idTipoPago);
            transaccion.setTipoPago(new TipoPago(idTipoPago));
            BoletaPagoContrib boletaPagoContribTransa = new BoletaPagoContrib();
            Contribuyentes contribuyenteExample = new Contribuyentes();
            contribuyenteExample.setRucNuevo(ruc);
            contribuyenteExample.setDigitoVerificador(dv);
            //lContribuyente = contribuyentesFacade.list(contribuyenteExample);
            contribuyente = contribuyentesFacade.get(contribuyenteExample);
            boletaPagoContribTransa.setContribuyente(contribuyente);
            boletaPagoContribTransa.setTransaccion(transaccion);
            boletaPagoContribTransa.setFechaHoraRecepcion(respuesta.getFechaHoraCore());
            boletaPagoContribTransa.setFechaCobro(transaccion.getGestion().getTerminal().getFechaConsecutivoSet());
            boletaPagoContribTransa.setRuc(ruc);
            boletaPagoContribTransa.setDv(dv);
            //Formulario formularioExample = new Formulario();
            //formularioExample.setIdFormulario(idFormulario);
            //boletaPagoContribTransa.setFormulario(idFormulario);
            //boletaPagoContribTransa.setVersion(versionFormulario);
            boletaPagoContribTransa.setFormulario(97);
            boletaPagoContribTransa.setVersion(1);

            if (periodo != null && !periodo.isEmpty()) {
                periodo = periodo.replaceAll("/", "");
                if (periodo.length() == 8) {
                    Integer fechaPeriodo
                            = new Integer(periodo.substring(4)
                                    + periodo.substring(2, 4)
                                    + periodo.substring(0, 2));
                    if (fechaPeriodo < 20070101) {
                        boletaPagoContribTransa.setPeriodo(periodo);
                    } else {
                        boletaPagoContribTransa.setPeriodo(null);
                    }
                } else if (periodo.length() == 6) {
                    Integer fechaPeriodo = new Integer(periodo.substring(2) + periodo.substring(0, 2));
                    if (fechaPeriodo < 200701) {
                        boletaPagoContribTransa.setPeriodo(periodo);
                    } else {
                        boletaPagoContribTransa.setPeriodo(null);
                    }
                } else {
                    Integer fechaPeriodo = new Integer(periodo);
                    if (fechaPeriodo < 2006) {
                        boletaPagoContribTransa.setPeriodo(periodo);
                    } else {
                        boletaPagoContribTransa.setPeriodo(null);
                    }
                }
            } else {
                boletaPagoContribTransa.setPeriodo(null);
            }

            boletaPagoContribTransa.setTotal(montoTotal);
            boletaPagoContribTransa.setNumeroResolucion(nroResolucion);
            Impuesto impuesto = impuestoFacade.get(idImpuesto);
            boletaPagoContribTransa.setImpuesto(impuesto);
            //seteamos el numero de orden
            Long numeroOrdenConERA = UtilesSet.ERANumeroOrden(era, this.recF.obtenerProximoValorOrden(idRecaudador));
            boletaPagoContribTransa.setNumeroOrden(numeroOrdenConERA);
            //consecutivo
            Long consecutivoActual = transaccion.getGestion().getTerminal().getConsecutivoActualForm();
            boletaPagoContribTransa.setConsecutivo(consecutivoActual);
            transaccion.getGestion().getTerminal().setConsecutivoActualForm(consecutivoActual + 1);
            termF.merge(transaccion.getGestion().getTerminal());
            //seteamos el hash
            boletaPagoContribTransa.setCodigoHash(boletaPagoContribTransa.toStringHash());
            boletaPagoContribTransa.setGrupo(respuesta.getGrupo());
            boletaPagoContribTransa.setPosGrupo(respuesta.getGrupo().getProximaPosicion());
            boletaPagoContribTransa = boletaPagoContribFacade.merge(boletaPagoContribTransa);
            WsSet wsSet = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            ParametroSistema p = new ParametroSistema();
            p.setNombreParametro("SET_ONLINE");
            ParametroSistema setOnline = parametroSistemaFacade.get(p);
            if (setOnline != null) {//&& idTipoPago.equals(1L)
                GuardarPagoRequest requestPago = new GuardarPagoRequest();
                requestPago.setEra(era.toString());
                requestPago.setTipoInformacion("2");//siempre es asi
                String sucursal = idSucursal.toString().length() > 3 ? idSucursal.toString().substring(idSucursal.toString().length() - 3) : idSucursal.toString();
                requestPago.setSucursalEra(sucursal);
                String cajero = idTerminal.length() > 3 ? idTerminal.substring(idTerminal.length() - 3) : idTerminal;
                requestPago.setCajero(cajero);

//                String consecutivo = (String) session.getAttribute("consecutivo");
//                if (consecutivo == null) {
//                    consecutivo = boletaPagoContribTransa.getConsecutivo().toString();
//                } else {
//                    consecutivo = String.valueOf(Integer.parseInt(consecutivo) + 1);
//                }
//                session.setAttribute("consecutivo", consecutivo);
//                requestPago.setConsecutivo(consecutivo);
                requestPago.setConsecutivo(consecutivoActual.toString());
                requestPago.setFormulario("97");//siempre es asi
                requestPago.setVersion(boletaPagoContribTransa.getVersion().toString());
                requestPago.setFechaDocumento(formatter.format(new Date()));
                requestPago.setImpuesto(boletaPagoContribTransa.getImpuesto().getNumero().toString());
                requestPago.setHash("0");//siempre es asi

                String ilOt = "";
                if (boletaPagoContribTransa.getNumeroOt() == null) {
                    logger.log(Level.WARNING, "No esta seteado el numero OT SE BUSCARA EN BD");
                    SimpleDateFormat parseDate = new SimpleDateFormat("dd/MM/yyyy");
                    String date = parseDate.format(new Date());
                    Date d = parseDate.parse(date);
                    NumeroOt numeroOt = numeroOtFacade.obtenerNumeroOT(idRed, 47, idTipoPago, d);
                    if (numeroOt == null) {
                        throw new Exception("Numero OT no existe");
                    } else {
                        ilOt = numeroOt.getEraNumeroOtDv();
                    }
                    boletaPagoContribTransa.setNumeroOt(numeroOt);
                } else {
                    ilOt = boletaPagoContribTransa.getNumeroOt().getEraNumeroOtDv();
                }
                String nroOT = ilOt;
                logger.log(Level.INFO, "NRO: OT:{0}", nroOT);
                requestPago.setNumeroOt(nroOT);

                String nroDocumento = boletaPagoContribTransa.getIdBoletaPagoContrib().toString();
                while ((nroDocumento.length() + 2) < 11) {
                    nroDocumento = "0" + nroDocumento;
                }
                nroDocumento = "47" + nroDocumento;
                requestPago.setNumeroDocumento(nroDocumento);
                requestPago.setNumeroDocumentoPagar(boletaPagoContribTransa.getNumeroResolucion().toString());
                requestPago.setRuc(boletaPagoContribTransa.getRuc());
                requestPago.setDv(boletaPagoContribTransa.getDv());
                requestPago.setValorPagado(String.valueOf(boletaPagoContribTransa.getTotal().intValue()));
                requestPago.setPeriodo(boletaPagoContribTransa.getPeriodo());

                String xmlPago = toXml(requestPago);
                logger.info(xmlPago);

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
                Integer connTO = 90;
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

                boolean timeout = false;
                try {
                    wsSet.guardarPago(xmlPago, UtilesSet.calcularCodigoHash(xmlPago));
                } catch (Exception e) {
                    //exploto la set por timeout
                    timeout = true;
                    boletaPagoContribTransa.setFlgPagoOnline(1);
                }
                if (!timeout) {
                    GuardarPagoResponse responseGP = wsSet.consultarPago(xmlPago, UtilesSet.calcularCodigoHash(xmlPago));
                    logger.info(responseGP.toString());
                    boletaPagoContribTransa.setFlgPagoOnline(responseGP.getEstado().equals("ACEPTADO") ? 2 : 3);
                    logger.info("FLAG ACEPTADO " + responseGP.getEstado());
                    boletaPagoContribFacade.update(boletaPagoContribTransa);
                    if (!responseGP.getEstado().equals("ACEPTADO")) {
                        logger.info("ENTRE 2");
                        respuesta.setIdRespuesta(-1L);
                        try {
                            respuesta.setDescRespuesta(responseGP.getErrores() != null ? responseGP.getErrores().getErrores().get(0).getDescripcion() : "Error indeterminado");
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Error con set", respuesta);
                            respuesta.setDescRespuesta("No se pudo realizar el pago");
                        }
//                    context.setRollbackOnly();
                        return respuesta;
                    }
                } else {
                    respuesta.setIdRespuesta(-1L);
                    respuesta.setDescRespuesta("No se problemas de conectividad con la SET");

                }
            }
            //boletaPagoContribFacade.save(boletaPagoContribTransa);
            // Se liga la historia del log con una transaccion
            respuesta.getLht().setTransaccion(new Transaccion(respuesta.getIdTransaccion()));
            logHistTransF.merge(respuesta.getLht());
            transaccion.setBoletaPagoContrib(boletaPagoContribTransa);
            transaccion.setEstadoTransaccion(new EstadoTransaccion(EstadoTransaccion.ACEPTADO));//Por default los pagos son aceptados!

            transaccion = transF.merge(transaccion);

            // transaccion.setTicket(UtilesSet.generarTicketTransa(transaccion));
            //transaccion.setCertificacion(UtilesSet.generarCertificacionBoletaPagoTransa(transaccion));
            respuesta = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.MSG_OK.longValue(), respuesta.getFechaHoraCore(),
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, respuesta.getLht());
            respuesta.setIdTransaccion(transaccion.getIdTransaccion());
            //respuesta.setTicket(transaccion.getTicket());
            //respuesta.setCertificacion(transaccion.getCertificacion());
        } catch (Exception e) {
            if (contribuyente == null) {
                respuesta = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_NO_EXISTE_EL_CONTRIBUYENTE.longValue(), respuesta.getFechaHoraCore(),
                        py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                        idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                        nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, respuesta.getLht());
            } else {
                respuesta = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_TRANS_NO_ENLAZADA_AL_SERVICIO.longValue(), respuesta.getFechaHoraCore(),
                        py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                        idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                        nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, respuesta.getLht());
            }
            logger.log(Level.SEVERE, e.getMessage(), e);
            //abortar transaccion
            context.setRollbackOnly();

        } finally {
            return respuesta;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Transaccion anularTransaccionGestion(Long idTransaccion) throws Exception {

        try {
            Transaccion t = this.get(idTransaccion);
            if (t == null) {
                throw new Exception("La transaccion no existe");
            }

//            UsuarioTerminal ut = usuaTermF.get(idUsuarioTerminal);
//            if (!t.getGestion().getUsuario().getIdUsuario().equals(ut.getUsuario().getIdUsuario())) {
//                throw new Exception("La transaccion no corresponde a su usuario");
//            }
            if (t.getFlagAnulado().equalsIgnoreCase("N")) {
                if (t.getBoletaPagoContrib() != null) {
                    //if (t.getBoletaPagoContrib().getGrupo().getProcesado().equalsIgnoreCase("N") && t.getBoletaPagoContrib().getGrupo().getGestion().getCerrado().equalsIgnoreCase("N")) {
                    FormContrib fc = t.getBoletaPagoContrib().getFormContrib();
                    if (fc != null) {
                        fc.setPagado(2);
                        fc.setTransaccion(null);
                        this.formContribFacade.merge(fc, t.getGestion().getTerminal());
                    }
                    t.setFlagAnulado("S");
                    t.setEstadoTransaccion(new EstadoTransaccion(new Long(EstadoTransaccion.RECHAZADO)));//Se marca como rechaza la transaccion
                    return this.merge(t);
                    //} else {
                    //  throw new Exception("La transaccion pertenece a una gestion procesada");
                    //}
                } else {
                    throw new Exception("La transaccion no tiene soporte de anulacion");
                }
            } else {
                throw new Exception("La transaccion ya esta anulada");
            }
        } catch (Exception e) {
            //abortar transaccion
            logger.log(Level.SEVERE, e.getMessage(), e);
            context.setRollbackOnly();
            throw e;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void anularTransaccion(Long idTransaccion, Long idUsuarioTerminal) throws Exception {
        try {
            Transaccion t = this.get(idTransaccion);
            if (t == null) {
                throw new Exception("La transaccion no existe");
            }

            UsuarioTerminal ut = usuaTermF.get(idUsuarioTerminal);

            if (!t.getGestion().getUsuario().getIdUsuario().equals(ut.getUsuario().getIdUsuario())) {
                throw new Exception("La transaccion no corresponde a su usuario");
            }

            if (t.getFlagAnulado().equalsIgnoreCase("N")) {
                if (t.getBoletaPagoContrib() != null) {
                    if (t.getBoletaPagoContrib().getGrupo().getProcesado().equalsIgnoreCase("N") && t.getBoletaPagoContrib().getGrupo().getGestion().getCerrado().equalsIgnoreCase("N")) {
                        FormContrib fc = t.getBoletaPagoContrib().getFormContrib();
                        if (fc != null) {
                            fc.setPagado(2);
                            fc.setTransaccion(null);
                            this.formContribFacade.merge(fc, t.getGestion().getTerminal());
                        }
                        t.setFlagAnulado("S");
                        t.setEstadoTransaccion(new EstadoTransaccion(new Long(EstadoTransaccion.RECHAZADO)));
                        this.merge(t);
                    } else {
                        throw new Exception("La transaccion pertenece a una gestion procesada");
                    }
                } else {
                    throw new Exception("La transaccion no tiene soporte de anulacion");
                }
            } else {
                throw new Exception("La transaccion ya esta anulada");
            }
        } catch (Exception e) {
            //abortar transaccion
            logger.log(Level.SEVERE, e.getMessage(), e);
            context.setRollbackOnly();
            throw e;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private Respuesta doTransacction(Long idTransaccionRedOrigen, String codigoTransaccional, Long idRed, Long idRecaudador,
            Long idSucursal, String idTerminal, String idUsuario, String nombreUsuario,
            String contrasenha, String fechaHoraRed, String fechaHoraTerminal, Long idTipoPago, Double tasaCambio,
            Long idTipoMoneda, Double montoTotal, Integer numeroCheque, Long idEntidadFinanciera, String fechaCheque) {

        TimeZone.setDefault(TimeZone.getTimeZone("America/Asuncion"));
        Date fechaHoraCore = Calendar.getInstance().getTime();

        Respuesta resp;

        // Se crea el log historico que podria ligarse a una transaccion en caso de exito
        LogHistoricoTrans lht = logHistF.insertarLogTrans();

        resp = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_PEDIDO_TRANSACCION_RECIBIDO_EXITOSAMENTE.longValue(), fechaHoraCore,
                py.com.konecta.redcobros.ejb.core.Parametros.IN_CORE, idTransaccionRedOrigen, codigoTransaccional,
                idRed, idRecaudador, idSucursal, idTerminal, idUsuario, nombreUsuario, fechaHoraRed, fechaHoraTerminal,
                idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);

        EstadoTransaccion es = estaTransF.get(py.com.konecta.redcobros.ejb.core.Parametros.ESTADO_INICIAL.longValue());
        if (es == null) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_EST_TRAN_NO_ENCONTRADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        // usuario en terminal
        UsuarioTerminal ut = new UsuarioTerminal();
        ut.setTerminal(new Terminal(new Long(idTerminal)));
        ut.setUsuario(new Usuario(new Long(idUsuario)));
        try {
            ut = this.usuaTermF.get(ut);
        } catch (Exception exc) {
            ut = null;
            logger.log(Level.SEVERE, exc.getMessage(), exc);
        }
        //UsuarioTerminal ut = usuaTermF.obtenerUsuarioTerminal(ter, usu);
        if (ut == null) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_USU_TERM_NO_ENCONTRADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        Usuario usu = ut.getUsuario();
        /*if (!usu.getIdUsuario().) {
         return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_USU_NO_ENCONTRADO, fechaHoraCore,
         py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
         idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
         nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
         }*/

        Terminal ter = ut.getTerminal();
        /*if (ter == null) {
         return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_TERM_NO_ENCONTRADA, fechaHoraCore,
         py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
         idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal,  idTerminal, idUsuario,
         nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
         } */

        //valido usuario: nombreuserio y contrasenha
        if (!(usu.getNombreUsuario().equals(nombreUsuario) && usu.getContrasenha().equals(contrasenha))) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_USU_NOMB_CONT_NO_ENCONTRADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        Sucursal suc = ter.getSucursal();
        if (!suc.getIdSucursal().equals(idSucursal)) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_SUC_NO_ENCONTRADA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        //Recaudador rec = recF.get(idRecaudador);
        Recaudador rec = suc.getRecaudador();
        if (!rec.getIdRecaudador().equals(idRecaudador)) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_REC_NO_ENCONTRADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        //Red red = redF.get(idRed);
        Red red = rec.getRed();
        if (!red.getIdRed().equals(idRed)) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_RED_NO_ENCONTRADA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        // recaudador en red
        if (!redF.comprobarHabilitacionRecaudador(idRed, idRecaudador)) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_REC_RED_NO_ENCONTRADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);

        }

        Servicio serv = new Servicio();
        serv.setCodigoTransaccional(codigoTransaccional);
        try {
            serv = this.servF.get(serv);
        } catch (Exception exc) {
            serv = null;
            logger.log(Level.SEVERE, exc.getMessage(), exc);
        }

        if (serv == null) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_NO_EXISTE_SERVICIO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        if (!redF.comprobarHabilitacionFacturador(idRed, serv.getFacturador().getIdFacturador())) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_REC_RED_NO_HABILITADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        // habilitacion de SERVICIOS EN RECAUDADOR
        //if (!redF.obtenerFacturadoresHabilitados(idRed).contains(is.getCodificacionServicio().getServicio().getFacturador())) {
        /*HabilitacionServRecaudadorPK hsrExamplePK = new HabilitacionServRecaudadorPK();
         hsrExamplePK.setIdRecaudador(rec.getIdRecaudador());
         hsrExamplePK.setIdRed(red.getIdRed());
         hsrExamplePK.setIdServicio(serv.getIdServicio());
         HabilitacionServRecaudador hsr = hsrF.get(hsrExamplePK);*/
        if (!recF.comprobarHabilitacionRecaudadorServicio(serv.getIdServicio(), idRecaudador)) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_SERV_REC_NO_HABILITADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        TipoPago tipoPago = tipoPagoF.get(idTipoPago);
        if (tipoPago == null) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_TIPO_PAGO_NO_ENCONTRADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        } else if (tipoPago.getSoportado() != 'S') {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_TIPO_PAGO_NO_SOPORTADO.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        Moneda mon = moneF.get(idTipoMoneda);
        if (mon == null) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_MON_NO_ENCONTRADA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        } else if (!mon.getSoportado().equalsIgnoreCase("S")) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_MON_NO_SOPORTADA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        Date fhr = Util.yyyyMMddHHmmssToDate(fechaHoraRed);
        if (fhr == null) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_FEC_HOR_RED_INCORRECTA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        Date fht = Util.yyyyMMddHHmmssToDate(fechaHoraTerminal);
        if (fht == null) {
            return this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_FEC_HOR_TER_INCORRECTA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
        }

        //se registra la grupo de la transaccion
        Grupo grupo = null;
        try {
            grupo = this.grupoFacade.obtenerGrupo(ut, serv);
        } catch (Exception e) {
            /**
             * ****CREAR ERROR NO SE PUDO REGISTRAR LA GESTION DE LA
             * TRANSACCION******
             */
            resp = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_TRANS_INCORRECTA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
            resp.setFechaHoraCore(fechaHoraCore);
            resp.setDescRespuesta(e.getMessage());
            return resp;
        }

        boolean trans_ok = true;
        Transaccion tran = null;

        try {
            // Se crea un transaccion

            tran = new Transaccion();
            tran.setEstadoTransaccion(es);//Para qué esto?....Sacar algun dia!
            tran.setGestion(grupo.getGestion());
            tran.setMoneda(mon);
            tran.setMontoTotal(montoTotal);
            tran.setFechaHoraSistema(fechaHoraCore);
            tran.setFechaHoraRed(fhr);
            tran.setFechaHoraTerminal(fht);
            tran.setComentario("");
            if (fechaCheque != null) {
                tran.setFechaCheque(new SimpleDateFormat("ddMMyyyy").parse(fechaCheque));
            }
            tran.setCodigoTransaccional(codigoTransaccional);
            tran.setFlagAnulado("N");
            tran.setMigrado('N');
            Gestion gestion = grupo.getGestion();
            tran.setPosicionGestion(gestion.getProximaPosicion());
            gestion.setProximaPosicion(gestion.getProximaPosicion() + 1);
            this.gestionFacade.merge(gestion);

            if (idEntidadFinanciera != -1) {
                tran.setEntidadFinanciera(new EntidadFinanciera(idEntidadFinanciera));
                tran.setNumeroCheque(numeroCheque + "");
            }

            tran = transF.merge(tran);

        } catch (Exception e) {
            trans_ok = false;
        }

        if (trans_ok) {
            resp = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.MSG_TRANSACCION_REGISTRADA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
            resp.setFechaHoraCore(fechaHoraCore);

        } else {
            resp = this.logHistF.insertLogHistoricoResp(py.com.konecta.redcobros.ejb.core.Parametros.ERROR_TRANS_INCORRECTA.longValue(), fechaHoraCore,
                    py.com.konecta.redcobros.ejb.core.Parametros.OUT_CORE,
                    idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador, idSucursal, idTerminal, idUsuario,
                    nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio, idTipoMoneda, montoTotal, lht);
            resp.setFechaHoraCore(fechaHoraCore);
            return resp;
        }

        // Respueta final
        resp.setIdTransaccion(tran.getIdTransaccion());
        resp.setGrupo(grupo);
        return resp;
    }

    @Override
    public void test() {
        this.guardarOT();
    }

    @Schedule(second = "0", minute = "45", hour = "23", persistent = false)
    public void guardarOT() {
        logger.log(Level.INFO, "GUARDAR OT");
        ParametroSistema p = null;

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
        Integer connTO = 90;
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

            WsSet wsSet = new WsSet(urlSession, urlEra, urlPago, usuario, pwd, connTO, readTO);

            logger.info("Guardando OT");
            SimpleDateFormat parseDate = new SimpleDateFormat("dd/MM/yyyy");
            String date = parseDate.format(new Date());
            Date d = parseDate.parse(date);
            logger.info("EFECTIVO");
            NumeroOt numeroOt = numeroOtFacade.obtenerNumeroOT(1L, 47, 1L, d);

            String fechaOT = parseDate.format(numeroOt.getFecha());
            String nroOT = numeroOt.getEraNumeroOtDv();

            BoletaPagoContrib ejemplo = new BoletaPagoContrib();
            ejemplo.setNumeroOt(numeroOt);
            ejemplo.setFlgPagoOnline(2);
            Transaccion t = new Transaccion();
            t.setTipoPago(new TipoPago(1L));
            ejemplo.setTransaccion(t);
            List<BoletaPagoContrib> listaBoletas = boletaPagoContribFacade.list(ejemplo);
            if (listaBoletas.size() > 0) {
                Double monto = 0.0;
                for (BoletaPagoContrib b : listaBoletas) {
                    monto += b.getTotal();
                }
                GuardarOTRequest requestOT = new GuardarOTRequest();
                requestOT.setNumeroDocumento(nroOT);
                requestOT.setTipoOt("1"); // es asi ver docu
                requestOT.setImporteTransferir(String.valueOf(monto.intValue()));
                requestOT.setCuentaBCP("460");// es asi ver docu
                requestOT.setEra("47");// es siempre asi
                requestOT.setFormulario("910");
                requestOT.setVersion("1");// esperemos siempre funcione xD
                requestOT.setHash("0");//este es asi ver nomas docu
                requestOT.setFechaDocumento(fechaOT);

                String xmlOT = UtilesSet.toXml(requestOT);
                logger.info(xmlOT);

                GuardarOTResponse responseOT = wsSet.guardarOT(xmlOT, UtilesSet.calcularCodigoHash(xmlOT));
                if (responseOT.getEstado().equals("RECHAZADO")) {
                    logger.log(Level.WARNING, "OT RECHAZADA, OT RECHAZADA, ");
                }
            } else {

                for (int i = 0; i < 10; i++) {
                    logger.log(Level.WARNING, "No existen pagos para la set");
                }
            }

//            borrar desde aqui
            logger.info("CHEQUE");
            numeroOt = numeroOtFacade.obtenerNumeroOT(1L, 47, 2L, d);

            fechaOT = parseDate.format(numeroOt.getFecha());
            nroOT = numeroOt.getEraNumeroOtDv();

            ejemplo = new BoletaPagoContrib();
            ejemplo.setNumeroOt(numeroOt);
            ejemplo.setFlgPagoOnline(2);
            t = new Transaccion();
            t.setTipoPago(new TipoPago(2L));
            ejemplo.setTransaccion(t);
            listaBoletas = boletaPagoContribFacade.list(ejemplo);
            if (listaBoletas.size() > 0) {
                Double monto = 0.0;
                for (BoletaPagoContrib b : listaBoletas) {
                    monto += b.getTotal();
                }
                GuardarOTRequest requestOT = new GuardarOTRequest();
                requestOT.setNumeroDocumento(nroOT);
                requestOT.setTipoOt("1"); // es asi ver docu
                requestOT.setImporteTransferir(String.valueOf(monto.intValue()));
                requestOT.setCuentaBCP("460");// es asi ver docu
                requestOT.setEra("47");// es siempre asi
                requestOT.setFormulario("921");
                requestOT.setVersion("1");// esperemos siempre funcione xD
                requestOT.setHash("0");//este es asi ver nomas docu
                requestOT.setFechaDocumento(fechaOT);

                String xmlOT = UtilesSet.toXml(requestOT);
                logger.info(xmlOT);

                GuardarOTResponse responseOT = wsSet.guardarOT(xmlOT, UtilesSet.calcularCodigoHash(xmlOT));
                if (responseOT.getEstado().equals("RECHAZADO")) {
                    logger.log(Level.WARNING, "OT RECHAZADA, OT RECHAZADA, ");
                }
            } else {

                for (int i = 0; i < 10; i++) {
                    logger.log(Level.WARNING, "No existen pagos para la set");
                }
            }
            /*hasta aqui*/

        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }

    }
}
