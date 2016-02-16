/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.CamposFormContribFacade;
import py.com.konecta.redcobros.ejb.FormContribFacade;
import py.com.konecta.redcobros.ejb.FormularioImpuestoFacade;
import py.com.konecta.redcobros.ejb.TransaccionFacade;
import py.com.konecta.redcobros.entities.CamposFormContrib;
import py.com.konecta.redcobros.entities.BoletaPagoContrib;
import py.com.konecta.redcobros.entities.EstadoTransaccion;
import py.com.konecta.redcobros.entities.FormContrib;
import py.com.konecta.redcobros.entities.FormularioImpuesto;
import py.com.konecta.redcobros.entities.Transaccion;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
public class CONTROL_FORMULARIOS extends HttpServlet {

    public static String ID_TERMINAL = "ID_TERMINAL";
    public static String ID_TRANSACCION = "ID_TRANSACCION";
    public static String ID_RED = "ID_RED";
    public static String ID_GESTION = "ID_GESTION";
    public static String ID_SUCURSAL = "ID_SUCURSAL";
    public static String ID_RECAUDADOR = "ID_RECAUDADOR";
    public static String ID_BANCO = "ID_BANCO";
    public static String ID_FORM_CONTRIB = "ID_FORM_CONTRIB";
    public static String DESCRIPCION_TERMINAL = "DESCRIPCION_TERMINAL";
    public static String DESCRIPCION_GESTION = "DESCRIPCION_GESTION";
    public static String NUMERO_IMPUESTO = "NUMERO_IMPUESTO";
    public static String DESCRIPCION_SUCURSAL = "DESCRIPCION_SUCURSAL";
    public static String DESCRIPCION_RECAUDADOR = "DESCRIPCION_RECAUDADOR";
    public static String CODIGO_RECAUDADOR = "CODIGO_RECAUDADOR";
    public static String CODIGO_TERMINAL = "CODIGO_TERMINAL";
    public static String CODIGO_CAJA_SET = "CODIGO_CAJA_SET";
    public static String CONSECUTIVO = "CONSECUTIVO";
    public static String FECHA_COBRO = "FECHA_COBRO";
    public static String NUMERO_ORDEN = "NUMERO_ORDEN";
    public static String NUMERO_OT = "NUMERO_OT";
    public static String RUC = "RUC";
    public static String MONTO_TOTAL = "MONTO_TOTAL";
    public static String MONTO_PAGADO = "MONTO_PAGADO";
    public static String FORMA_PAGO = "FORMA_PAGO";
    public static String NUMERO_CHEQUE = "NUMERO_CHEQUE";
    public static String DESCRIPCION_BANCO = "DESCRIPCION_BANCO";
    public static String RECHAZADO = "RECHAZADO";
    public static String FECHA_RECIBIDO = "FECHA_RECIBIDO";
    public static String FECHA_CONTROLADO = "FECHA_CONTROLADO";
    public static String FECHA_CERTIFICACION = "FECHA_CERTIFICACION";
    public static String ENVIADO = "ENVIADO";
    public static String VALIDO = "VALIDO";
    public static String NUMERO_FORMULARIO = "NUMERO_FORMULARIO";
    public static String FECHA_DESDE = "FECHA_DESDE";
    public static String FECHA_HASTA = "FECHA_HASTA";
    public static String FILTRO_RECEPCIONADOS = "FILTRO_RECEPCIONADOS";
    public static String FILTRO_TODOS_RECEPCIONADOS = "FILTRO_TODOS_RECEPCIONADOS";
    public static String FILTRO_CONTROLADOS = "FILTRO_CONTROLADOS";
    public static String FILTRO_TODOS_CONTROLADOS = "FILTRO_TODOS_CONTROLADOS";
    public static String FILTRO_CHEQUE = "FILTRO_CHEQUE";
    public static String FILTRO_EFECTIVO = "FILTRO_EFECTIVO";
    public static String FILTRO_ACEPTADOS = "FILTRO_ACEPTADOS";
    public static String FILTRO_RECHAZADOS = "FILTRO_RECHAZADOS";
    public static String FILTRO_TODOS_ACEPTADOS_RECHAZADOS = "FILTRO_TODOS_ACEPTADOS_RECHAZADOS";
    public static String FILTRO_TODOS_FORMA_PAGO = "FILTRO_TODOS_FORMA_PAGO";
    public static String CAMPO_VALOR = "CAMPO_VALOR";
    public static String FILTRO_NRO_ORDEN = "FILTRO_NRO_ORDEN";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");
            String opcion = request.getParameter("op");
            Long idRed = (request.getParameter(CONTROL_FORMULARIOS.ID_RED) != null && !request.getParameter(CONTROL_FORMULARIOS.ID_RED).isEmpty()) ? new Long(request.getParameter(CONTROL_FORMULARIOS.ID_RED)) : null;
            Long idBanco = (request.getParameter(CONTROL_FORMULARIOS.ID_BANCO) != null && !request.getParameter(CONTROL_FORMULARIOS.ID_BANCO).isEmpty()) ? new Long(CONTROL_FORMULARIOS.ID_BANCO) : null;
            Long idGestion = (request.getParameter(CONTROL_FORMULARIOS.ID_GESTION) != null && !request.getParameter(CONTROL_FORMULARIOS.ID_GESTION).isEmpty()) ? new Long(request.getParameter(CONTROL_FORMULARIOS.ID_GESTION)) : null;
            Long idRecaudador = (request.getParameter(CONTROL_FORMULARIOS.ID_RECAUDADOR) != null && !request.getParameter(CONTROL_FORMULARIOS.ID_RECAUDADOR).isEmpty()) ? new Long(request.getParameter(CONTROL_FORMULARIOS.ID_RECAUDADOR)) : null;
            Long idSucursal = (request.getParameter(CONTROL_FORMULARIOS.ID_SUCURSAL) != null && !request.getParameter(CONTROL_FORMULARIOS.ID_SUCURSAL).isEmpty()) ? new Long(request.getParameter(CONTROL_FORMULARIOS.ID_SUCURSAL)) : null;
            Long idTerminal = (request.getParameter(CONTROL_FORMULARIOS.ID_TERMINAL) != null && !request.getParameter(CONTROL_FORMULARIOS.ID_TERMINAL).isEmpty()) ? new Long(request.getParameter(CONTROL_FORMULARIOS.ID_TERMINAL)) : null;
            Date fechaDesde = (request.getParameter(CONTROL_FORMULARIOS.FECHA_DESDE) != null && !request.getParameter(CONTROL_FORMULARIOS.FECHA_DESDE).isEmpty()) ? spdf.parse(request.getParameter(CONTROL_FORMULARIOS.FECHA_DESDE)) : null;
            Date fechaHasta = (request.getParameter(CONTROL_FORMULARIOS.FECHA_HASTA) != null && !request.getParameter(CONTROL_FORMULARIOS.FECHA_HASTA).isEmpty()) ? spdf.parse(request.getParameter(CONTROL_FORMULARIOS.FECHA_HASTA)) : null;
            Date fechaRecibido = (request.getParameter(CONTROL_FORMULARIOS.FECHA_RECIBIDO) != null && !request.getParameter(CONTROL_FORMULARIOS.FECHA_RECIBIDO).isEmpty()) ? spdf.parse(request.getParameter(CONTROL_FORMULARIOS.FECHA_RECIBIDO)) : null;
            Date fechaControlado = (request.getParameter(CONTROL_FORMULARIOS.FECHA_CONTROLADO) != null && !request.getParameter(CONTROL_FORMULARIOS.FECHA_CONTROLADO).isEmpty()) ? spdf.parse(request.getParameter(CONTROL_FORMULARIOS.FECHA_CONTROLADO)) : null;
            //  Date fechaCertificacion = (request.getParameter(CONTROL_FORMULARIOS.FECHA_CERTIFICACION) != null && !request.getParameter(CONTROL_FORMULARIOS.FECHA_CERTIFICACION).isEmpty()) ? spdf.parse(request.getParameter(CONTROL_FORMULARIOS.FECHA_CERTIFICACION)) : null;
            String rechazado = (request.getParameter(CONTROL_FORMULARIOS.RECHAZADO) != null) ? request.getParameter(CONTROL_FORMULARIOS.RECHAZADO) : "";
            String enviado = (request.getParameter(CONTROL_FORMULARIOS.ENVIADO) != null) ? request.getParameter(CONTROL_FORMULARIOS.ENVIADO) : "";
            String valido = (request.getParameter(CONTROL_FORMULARIOS.VALIDO) != null) ? request.getParameter(CONTROL_FORMULARIOS.VALIDO) : "";
            Boolean recepcionados = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_RECEPCIONADOS) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_RECEPCIONADOS)) : false);
            Boolean todosRecepcionados = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_TODOS_RECEPCIONADOS) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_TODOS_RECEPCIONADOS)) : true);
            Boolean controlados = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_CONTROLADOS) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_CONTROLADOS)) : false);
            Boolean todosControlados = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_TODOS_CONTROLADOS) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_TODOS_CONTROLADOS)) : true);
            Boolean todosFormaPago = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_TODOS_FORMA_PAGO) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_TODOS_FORMA_PAGO)) : true);
            Boolean filtroEfectivo = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_EFECTIVO) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_EFECTIVO)) : true);
            Boolean filtroCheque = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_CHEQUE) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_CHEQUE)) : true);
            Boolean filtroAceptado = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_ACEPTADOS) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_ACEPTADOS)) : true);
            Boolean filtroRechazados = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_RECHAZADOS) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_RECHAZADOS)) : true);
            Boolean todosAceptadosRechazados = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_TODOS_ACEPTADOS_RECHAZADOS) != null ? new Boolean(request.getParameter(CONTROL_FORMULARIOS.FILTRO_TODOS_ACEPTADOS_RECHAZADOS)) : true);
            Long filtroNroOrden = (request.getParameter(CONTROL_FORMULARIOS.FILTRO_NRO_ORDEN) != null && !request.getParameter(CONTROL_FORMULARIOS.FILTRO_NRO_ORDEN).isEmpty() ? Long.parseLong(request.getParameter(CONTROL_FORMULARIOS.FILTRO_NRO_ORDEN)) : null);
            if (opcion.equalsIgnoreCase("LISTAR")) {
                FormContrib entity = new FormContrib();

                List<FormContrib> lista;
                Integer total;

                if (request.getParameter("start") != null && request.getParameter("limit") != null) {
                    Integer primerResultado = new Integer(request.getParameter("start"));
                    Integer cantResultados = new Integer(request.getParameter("limit"));
                    if (filtroNroOrden != null) {
                        entity.setNumeroOrden(filtroNroOrden);
                        lista = formContribFacade.list(entity);
                        total = lista.size();
                    } else {
                        lista = formContribFacade.getControlFormulario(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta, primerResultado, cantResultados);
                        total = formContribFacade.getTotalControlFormulario(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta);
                    }
                    JSONObject jsonRespuesta = new JSONObject();
                    JSONArray json = new JSONArray();
                    CamposFormContrib cfcExample = new CamposFormContrib();
                    for (FormContrib e : lista) {
                        JSONObject jsoneFormContrib = new JSONObject();
                        // jsoneFormContrib.put(CONTROL_FORMULARIOS.FORMA_PAGO, (e.getTransaccion() != null) ? e.getTransaccion().getTipoPago().getDescripcion() : "-");
                        //jsoneFormContrib.put(CONTROL_FORMULARIOS.FECHA_COBRO, (e.getTransaccion() != null) ? spdf.format(e.getTransaccion().getFechaHoraSistema()) : "-");
                        //jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_CHEQUE, (e.getTransaccion() != null && e.getTransaccion().getNumeroCheque() != null) ? e.getTransaccion().getNumeroCheque() : "-");
                        //jsoneFormContrib.put(CONTROL_FORMULARIOS.MONTO_PAGADO, (e.getTransaccion() != null) ? UtilesSet.formatearNumerosSeparadorMiles(e.getTransaccion().getBoletaPagoContrib().getTotal(), true) : "-");
                        //jsoneFormContrib.put(CONTROL_FORMULARIOS.DESCRIPCION_BANCO, (e.getTransaccion() != null && e.getTransaccion().getEntidadFinanciera() != null) ? e.getTransaccion().getEntidadFinanciera().getDescripcion() : "-");
                        //System.out.println("***"+e.getFechaControlado().toString().trim()+"**");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.ID_FORM_CONTRIB, e.getIdFormContrib());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.CODIGO_TERMINAL, e.getUsuarioTerminalCarga().getTerminal().getCodigoTerminal());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.DESCRIPCION_GESTION, (e.getGrupo() != null) ? e.getGrupo().getGestion().getNroGestion() : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_FORMULARIO, e.getFormulario().getNumeroFormulario());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_ORDEN, (e.getNumeroOrden() != null) ? e.getNumeroOrden() : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.CONSECUTIVO, (e.getConsecutivo() != null) ? e.getConsecutivo() : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.RUC, e.getContribuyente().getRucNuevo());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.MONTO_TOTAL, UtilesSet.formatearNumerosSeparadorMiles(e.getTotalPagar(), true));
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.FECHA_CONTROLADO, (e.getFechaControlado() != null) ? spdf.format(e.getFechaControlado()) : "");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.FECHA_RECIBIDO, (e.getFechaRecibido() != null) ? spdf.format(e.getFechaRecibido()) : "");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.FECHA_CERTIFICACION, (e.getFechaCertificadoSet() != null) ? spdf.format(e.getFechaCertificadoSet()) : "");

                        cfcExample.setFormContrib(e);

                        String campoValor = "<table  frame='hsides'><tr><th>Campo</th><th>Valor</th></tr>";
                        for (CamposFormContrib oo : camposFormContribFacade.list(cfcExample)) {
                            campoValor += "<tr>" + "<td>" + oo.getNumeroCampo() + "</td>" + "<td>" + oo.getValor() + "</td>" + "</tr>";
                        }
                        campoValor += "</table>";
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.CAMPO_VALOR, campoValor);
                        // jsoneFormContrib.put(CONTROL_FORMULARIOS.RECHAZADO, (e.getRechazado() != null) ? e.getRechazado() : "N");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.ENVIADO, (e.getEnviado() != null) ? e.getEnviado() : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.ENVIADO, (e.getEnviado() != null) ? e.getEnviado() : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.VALIDO, (e.getCamposValidos() != null) ? e.getCamposValidos() == 1 ? "S" : "N" : "-");
                        json.add(jsoneFormContrib);
                    }
                    jsonRespuesta.put("FORMULARIOS", json);
                    jsonRespuesta.put("TOTAL", total);
                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());

                } else {
                    JSONObject jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("success", false);
                    out.println(jsonRespuesta.toString());
                }

            } else if (opcion.equalsIgnoreCase("LISTAR_BOLETAS")) {
                BoletaPagoContrib bpEntity = new BoletaPagoContrib();
                Transaccion trans = new Transaccion();
                List<Transaccion> lista;
                Integer total;
                if (request.getParameter("start") != null && request.getParameter("limit") != null) {

                    Integer primerResultado = new Integer(request.getParameter("start"));
                    Integer cantResultados = new Integer(request.getParameter("limit"));

                    if (filtroNroOrden != null) {

                        bpEntity.setNumeroOrden(filtroNroOrden);
                        trans.setBoletaPagoContrib(bpEntity);
                        lista = transaccionFacade.list(trans);
                        total = lista.size();

                    } else {
                        lista = transaccionFacade.getControlFormularioBoletas(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, fechaDesde, fechaHasta, filtroAceptado, filtroRechazados, todosAceptadosRechazados, primerResultado, cantResultados);
                        total = transaccionFacade.getTotalControlFormularioBoletas(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, filtroAceptado, filtroRechazados, todosAceptadosRechazados, fechaDesde, fechaHasta);
                    }
                    JSONObject jsonRespuesta = new JSONObject();
                    JSONArray json = new JSONArray();
                    FormularioImpuesto ejemploFI = new FormularioImpuesto();
                    for (Transaccion e : lista) {
                        JSONObject jsoneFormContrib = new JSONObject();
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.ID_TRANSACCION, e.getIdTransaccion());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.CODIGO_TERMINAL, e.getGestion().getTerminal().getCodigoTerminal());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.DESCRIPCION_GESTION, e.getBoletaPagoContrib().getGrupo().getGestion().getNroGestion());//Numero de Gestión de la boleta de Pago.
                        if (e.getBoletaPagoContrib().getFormContrib() != null) {
                            ejemploFI.setNumeroFormulario(e.getBoletaPagoContrib().getFormContrib().getFormulario().getNumeroFormulario());
                            jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_IMPUESTO, formularioImpuestoFacade.get(ejemploFI).getImpuesto());
                        } else {
                            jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_IMPUESTO, "-");
                        }
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_ORDEN, e.getBoletaPagoContrib().getNumeroOrden());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.CONSECUTIVO, (e.getBoletaPagoContrib() != null) ? e.getBoletaPagoContrib().getConsecutivo() : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.RUC, e.getBoletaPagoContrib().getContribuyente().getRucNuevo());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.MONTO_TOTAL, (e.getFormContrib() != null) ? UtilesSet.formatearNumerosSeparadorMiles(e.getFormContrib().getTotalPagar(), true) : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.FORMA_PAGO, e.getTipoPago().getDescripcion());
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.FECHA_COBRO, spdf.format(e.getFechaHoraSistema()));
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.NUMERO_CHEQUE, (e.getNumeroCheque() != null && !e.getNumeroCheque().isEmpty()) ? e.getNumeroCheque() : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.MONTO_PAGADO, UtilesSet.formatearNumerosSeparadorMiles(e.getBoletaPagoContrib().getTotal(), true));
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.DESCRIPCION_BANCO, (e.getEntidadFinanciera() != null) ? e.getEntidadFinanciera().getDescripcion() : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.RECHAZADO, (e.getEstadoTransaccion() != null && e.getEstadoTransaccion().getIdEstadoTransaccion().longValue() == (EstadoTransaccion.RECHAZADO)) ? "S" : (e.getEstadoTransaccion() != null && e.getEstadoTransaccion().getIdEstadoTransaccion().longValue() == (EstadoTransaccion.ACEPTADO)) ? "N" : "-");
                        jsoneFormContrib.put(CONTROL_FORMULARIOS.ENVIADO, (e.getBoletaPagoContrib() != null && e.getBoletaPagoContrib().getFormContrib() != null && e.getBoletaPagoContrib().getFormContrib().getEnviado() != null) ? e.getBoletaPagoContrib().getFormContrib().getEnviado() : "-");
                        json.add(jsoneFormContrib);


                    }
                    jsonRespuesta.put("FORMULARIOS", json);
                    jsonRespuesta.put("TOTAL", total);
                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());

                } else {
                    JSONObject jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("success", false);
                    out.println(jsonRespuesta.toString());
                }

            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                FormContrib entidad = formContribFacade.get(new Long(request.getParameter(CONTROL_FORMULARIOS.ID_FORM_CONTRIB)));

                entidad.setFechaRecibido(fechaRecibido);
                entidad.setFechaControlado(fechaControlado);

                /*if (rechazado.equalsIgnoreCase("N") || rechazado.equalsIgnoreCase("S")) {
                // entidad.setRechazado(rechazado.toUpperCase());
                }*/
                if (enviado.equalsIgnoreCase("N") && !(entidad.getEnviado().equalsIgnoreCase("N"))) {
                    entidad.setEnviado(enviado.toUpperCase());
                } else if (enviado.equalsIgnoreCase("S") && !(entidad.getEnviado().equalsIgnoreCase("S"))) {
                    entidad.setEnviado(enviado.toUpperCase());
                } else if (enviado.equals("-")) {
                    entidad.setEnviado("");
                }
                if (valido.equals("1")) {
                    entidad.setCamposValidos(1);
                } else if (valido.equals("2")) {
                    entidad.setCamposValidos(2);
                }
                formContribFacade.update(entidad);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR_BOLETAS")) {
                // FormContrib entidad = formContribFacade.get(new Long(request.getParameter(CONTROL_FORMULARIOS.ID_FORM_CONTRIB)));
                Transaccion entidad = transaccionFacade.get(new Long(request.getParameter(CONTROL_FORMULARIOS.ID_TRANSACCION)));

                if (rechazado.equalsIgnoreCase("N") || rechazado.equalsIgnoreCase("S")) {
                    if (rechazado.equalsIgnoreCase("S") && !(entidad.getEstadoTransaccion().getIdEstadoTransaccion().longValue() == EstadoTransaccion.RECHAZADO)) {
                        //Se rechaza el pago
                        entidad = transaccionFacade.anularTransaccionGestion(entidad.getIdTransaccion());

                    } else if (rechazado.equalsIgnoreCase("N") && !(entidad.getEstadoTransaccion().getIdEstadoTransaccion().longValue() == EstadoTransaccion.ACEPTADO)) {
                        //Se acepta el pago
                        entidad.setEstadoTransaccion(new EstadoTransaccion(EstadoTransaccion.ACEPTADO));
                        entidad.setFlagAnulado("N");
                        transaccionFacade.update(entidad);

                    // transaccionFacade.merge(entidad);
                    }
                }
                if (enviado.equalsIgnoreCase("N") || enviado.equalsIgnoreCase("S") || enviado.equals("-")) {
                    if (entidad.getBoletaPagoContrib() != null && entidad.getBoletaPagoContrib().getFormContrib() != null) {
                        FormContrib fc = formContribFacade.get(entidad.getBoletaPagoContrib().getFormContrib().getIdFormContrib());
                        if (enviado.equals("-")) {
                            fc.setEnviado("");
                        } else {
                            fc.setEnviado(enviado.toUpperCase());
                        }
                        formContribFacade.merge(fc);
                    //entidad.getBoletaPagoContrib().getFormContrib().setEnviado(enviado.toUpperCase());
                    }
                }
                //transaccionFacade.merge(entidad);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE_BOLETAS")) {
                Transaccion entity = transaccionFacade.get(new Long(request.getParameter(CONTROL_FORMULARIOS.ID_FORM_CONTRIB)));

                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();

                jsonDetalle.put(CONTROL_FORMULARIOS.ID_TRANSACCION, entity.getIdTransaccion());
                // jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_TERMINAL, entity.getUsuarioTerminal().getTerminal().getDescripcion());
                // jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_GESTION, (entity.getGrupo() != null) ? entity.getGrupo().getGestion().getNroGestion() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.NUMERO_FORMULARIO, (entity.getFormContrib() != null) ? entity.getFormContrib().getFormulario().getNumeroFormulario() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.NUMERO_ORDEN, entity.getBoletaPagoContrib().getNumeroOrden());
                jsonDetalle.put(CONTROL_FORMULARIOS.CONSECUTIVO, entity.getBoletaPagoContrib().getConsecutivo());
                jsonDetalle.put(CONTROL_FORMULARIOS.RUC, entity.getBoletaPagoContrib().getContribuyente().getRucNuevo());
                jsonDetalle.put(CONTROL_FORMULARIOS.MONTO_TOTAL, (entity.getFormContrib() != null) ? UtilesSet.formatearNumerosSeparadorMiles(entity.getFormContrib().getTotalPagar(), true) : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.FORMA_PAGO, entity.getTipoPago().getDescripcion());
                jsonDetalle.put(CONTROL_FORMULARIOS.FECHA_COBRO, spdf.format(entity.getFechaHoraSistema()));
                jsonDetalle.put(CONTROL_FORMULARIOS.NUMERO_CHEQUE, (entity.getNumeroCheque() != null && !entity.getNumeroCheque().isEmpty()) ? entity.getNumeroCheque() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.MONTO_PAGADO, UtilesSet.formatearNumerosSeparadorMiles(entity.getBoletaPagoContrib().getTotal(), true));
                jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_BANCO, (entity.getEntidadFinanciera() != null) ? entity.getEntidadFinanciera().getDescripcion() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.CODIGO_TERMINAL, entity.getGestion().getTerminal().getCodigoTerminal());
                jsonDetalle.put(CONTROL_FORMULARIOS.CODIGO_CAJA_SET, entity.getGestion().getTerminal().getCodigoCajaSet());
                jsonDetalle.put(CONTROL_FORMULARIOS.NUMERO_OT, entity.getBoletaPagoContrib().getNumeroOt().getNumero());
                jsonDetalle.put(CONTROL_FORMULARIOS.RECHAZADO, (entity.getEstadoTransaccion() != null && entity.getEstadoTransaccion().getIdEstadoTransaccion().equals(EstadoTransaccion.RECHAZADO)) ? "S" : (entity.getEstadoTransaccion() != null && entity.getEstadoTransaccion().getIdEstadoTransaccion().equals(EstadoTransaccion.ACEPTADO)) ? "N" : "-");

//                jsonDetalle.put(CONTROL_FORMULARIOS.ID_BANCO, (entity.getTransaccion() != null && entity.getTransaccion().getEntidadFinanciera() != null) ? entity.getTransaccion().getEntidadFinanciera().getIdEntidadFinanciera() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.FECHA_CONTROLADO, "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.FECHA_RECIBIDO, "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.CODIGO_RECAUDADOR, entity.getGestion().getTerminal().getSucursal().getRecaudador().getCodigoRecaudador());
                jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_RECAUDADOR, entity.getGestion().getTerminal().getSucursal().getRecaudador().getDescripcion());
                jsonDetalle.put(CONTROL_FORMULARIOS.ENVIADO, (entity.getFormContrib() != null && entity.getFormContrib().getEnviado() != null) ? entity.getFormContrib().getEnviado() : "-");
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                FormContrib entity = formContribFacade.get(new Long(request.getParameter(CONTROL_FORMULARIOS.ID_FORM_CONTRIB)));

                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();

                jsonDetalle.put(CONTROL_FORMULARIOS.ID_FORM_CONTRIB, entity.getIdFormContrib());
                // jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_TERMINAL, entity.getUsuarioTerminal().getTerminal().getDescripcion());
                // jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_GESTION, (entity.getGrupo() != null) ? entity.getGrupo().getGestion().getNroGestion() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.NUMERO_FORMULARIO, entity.getFormulario().getNumeroFormulario());
                jsonDetalle.put(CONTROL_FORMULARIOS.NUMERO_ORDEN, (entity.getNumeroOrden() != null) ? entity.getNumeroOrden() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.CONSECUTIVO, (entity.getConsecutivo() != null) ? entity.getConsecutivo() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.RUC, entity.getContribuyente().getRucNuevo());
                jsonDetalle.put(CONTROL_FORMULARIOS.MONTO_TOTAL, UtilesSet.formatearNumerosSeparadorMiles(entity.getTotalPagar(), true));
                jsonDetalle.put(CONTROL_FORMULARIOS.FORMA_PAGO, (entity.getTransaccion() != null) ? entity.getTransaccion().getTipoPago().getDescripcion() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.FECHA_COBRO, (entity.getTransaccion() != null) ? spdf.format(entity.getTransaccion().getFechaHoraSistema()) : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.NUMERO_CHEQUE, (entity.getTransaccion() != null && entity.getTransaccion().getNumeroCheque() != null) ? entity.getTransaccion().getNumeroCheque() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.MONTO_PAGADO, (entity.getTransaccion() != null) ? UtilesSet.formatearNumerosSeparadorMiles(entity.getTransaccion().getBoletaPagoContrib().getTotal(), true) : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_BANCO, (entity.getTransaccion() != null && entity.getTransaccion().getEntidadFinanciera() != null) ? entity.getTransaccion().getEntidadFinanciera().getDescripcion() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.CODIGO_TERMINAL, entity.getUsuarioTerminalCarga().getTerminal().getCodigoTerminal());
                jsonDetalle.put(CONTROL_FORMULARIOS.CODIGO_CAJA_SET, entity.getUsuarioTerminalCarga().getTerminal().getCodigoCajaSet());
                jsonDetalle.put(CONTROL_FORMULARIOS.NUMERO_OT, (entity.getTransaccion() != null && entity.getTransaccion().getBoletaPagoContrib() != null) ? entity.getTransaccion().getBoletaPagoContrib().getNumeroOt().getNumero() : "-");
                // jsonDetalle.put(CONTROL_FORMULARIOS.RECHAZADO, (entity.getRechazado() != null && !entity.getRechazado().isEmpty()) ? entity.getRechazado() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_BANCO, (entity.getTransaccion() != null && entity.getTransaccion().getEntidadFinanciera() != null) ? entity.getTransaccion().getEntidadFinanciera().getDescripcion() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.ID_BANCO, (entity.getTransaccion() != null && entity.getTransaccion().getEntidadFinanciera() != null) ? entity.getTransaccion().getEntidadFinanciera().getIdEntidadFinanciera() : "-");
                jsonDetalle.put(CONTROL_FORMULARIOS.FECHA_CONTROLADO, (entity.getFechaControlado() != null) ? spdf.format(entity.getFechaControlado()) : "");
                jsonDetalle.put(CONTROL_FORMULARIOS.FECHA_RECIBIDO, (entity.getFechaRecibido() != null) ? spdf.format(entity.getFechaRecibido()) : "");
                jsonDetalle.put(CONTROL_FORMULARIOS.CODIGO_RECAUDADOR, entity.getUsuarioTerminalCarga().getTerminal().getSucursal().getRecaudador().getCodigoRecaudador());
                jsonDetalle.put(CONTROL_FORMULARIOS.DESCRIPCION_RECAUDADOR, entity.getUsuarioTerminalCarga().getTerminal().getSucursal().getRecaudador().getDescripcion());
                jsonDetalle.put(CONTROL_FORMULARIOS.ENVIADO, (entity.getEnviado() != null) ? entity.getEnviado() : "-");
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());

            } else {
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
                out.println(jsonRespuesta.toString());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
           //out.close();
        }
    }
    @EJB
    private FormContribFacade formContribFacade;
    @EJB
    private FormularioImpuestoFacade formularioImpuestoFacade;
    @EJB
    private TransaccionFacade transaccionFacade;
    @EJB
    private CamposFormContribFacade camposFormContribFacade;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
