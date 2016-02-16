/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.entities.UsuarioTerminal;

/**
 *
 * @author konecta
 */
public class reportes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private ReportesFacade reportesF;
    @EJB
    private TicketTransaccionFacade ticketF;
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private FormContribFacade formContribFacade;
    @EJB
    private BoletaPagoContribFacade boletaPagoContribFacade;
    @EJB
    private TransaccionFacade transaccionFacade;

    public boolean controlFechaIniMenorIgualFechaFin(String fechaFin, String fechaIni) throws ParseException {
        SimpleDateFormat formatterFecha = new SimpleDateFormat("ddMMyyyy");

        try {
            if (formatterFecha.parse(fechaFin).getTime() >= formatterFecha.parse(fechaIni).getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            return false;
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            byte[] result = null;
            JSONObject json = new JSONObject();
            JSONObject jsonFinal = new JSONObject();
            String op = request.getParameter("op");
            String fechaFin = request.getParameter("fechaFin") != null && !request.getParameter("fechaFin").isEmpty() ? request.getParameter("fechaFin") : null;
            String fechaIni = request.getParameter("fechaIni") != null && !request.getParameter("fechaIni").isEmpty() ? request.getParameter("fechaIni") : null;

            //Integer idRed = (Integer) request.getSession().getAttribute("idRed");
            //Integer idTerminal = (Integer) request.getSession().getAttribute("idTerminal");
//            Integer idTerminal = null;
//            if (request.getParameter("tipoConsulta") != null && request.getParameter("tipoConsulta").equalsIgnoreCase("1")) {
//                idTerminal = (Integer) request.getSession().getAttribute("idTerminal");
//            }
            //Integer idUsuario = (Integer) request.getSession().getAttribute("idUsuario");
            Long idTerminal = request.getParameter("idTerminal") != null && !request.getParameter("idTerminal").isEmpty() ? new Long(request.getParameter("idTerminal")) : null;
            Long idUsuario = request.getParameter("idUsuario") != null && !request.getParameter("idUsuario").isEmpty() ? new Long(request.getParameter("idUsuario")) : null;
            Long idRed = request.getParameter("idRed") != null && !request.getParameter("idRed").isEmpty() ? new Long(request.getParameter("idRed")) : null;
            Long idFacturador = request.getParameter("idFacturador") != null && !request.getParameter("idFacturador").isEmpty() ? new Long(request.getParameter("idFacturador")) : null;
            Long idSucursal = request.getParameter("idSucursal") != null && !request.getParameter("idSucursal").isEmpty() ? new Long(request.getParameter("idSucursal")) : null;
            Long idRecaudador = request.getParameter("idRecaudador") != null && !request.getParameter("idRecaudador").isEmpty() ? new Long(request.getParameter("idRecaudador")) : null;
            Long idGestion = request.getParameter("idGestion") != null && !request.getParameter("idGestion").isEmpty() ? new Long(request.getParameter("idGestion")) : null;
            Long idRol = request.getParameter("idRol") != null && !request.getParameter("idRol").isEmpty() ? new Long(request.getParameter("idRol")) : null;
            Long idGrupo = request.getParameter("idGrupo") != null && !request.getParameter("idGrupo").isEmpty() ? new Long(request.getParameter("idGrupo")) : null;
            String codigoServicio = request.getParameter("codigoServicio") != null && !request.getParameter("codigoServicio").isEmpty() ? request.getParameter("codigoServicio") : null;
            Long idServicio = request.getParameter("idServicio") != null && !request.getParameter("idServicio").isEmpty() ? new Long(request.getParameter("idServicio")) : null;
            String estadoTransaccion = request.getParameter("estadoTransaccion") != null ? request.getParameter("estadoTransaccion").equalsIgnoreCase("S") || request.getParameter("estadoTransaccion").equalsIgnoreCase("N") ? request.getParameter("estadoTransaccion") : null : null;
            Integer tipoPago = request.getParameter("tipoPago") != null && !request.getParameter("tipoPago").isEmpty() ? Integer.parseInt(request.getParameter("tipoPago")) : null;
            String formatoDescarga = request.getParameter("formatoDescarga") != null && !request.getParameter("formatoDescarga").isEmpty() ? request.getParameter("formatoDescarga") : null;
            String tipoReporte = request.getParameter("tipoReporte") != null && !request.getParameter("tipoReporte").isEmpty() ? request.getParameter("tipoReporte") : null;
            String zona = request.getParameter("zona") != null && !request.getParameter("zona").isEmpty() ? request.getParameter("zona") : null;
            Integer diasAnteriores = request.getParameter("diasAnteriores") != null && !request.getParameter("diasAnteriores").isEmpty() ? Integer.valueOf(request.getParameter("diasAnteriores")) : 1;
            Long nroOt = request.getParameter("nroOt") != null && !request.getParameter("nroOt").isEmpty() ? new Long(request.getParameter("nroOt")) : null;
            Integer idMoneda = request.getParameter("idMoneda") != null && !request.getParameter("idMoneda").isEmpty() ? Integer.valueOf(request.getParameter("idMoneda")) : null;
            if (op.equals("DDJJ-DIA")) {
                //Integer idRed = Integer.parseInt(request.getParameter("idRed"));
                String fecha = request.getParameter("fecha");
                result = reportesF.declaracionesDelDia(idRed, fecha, formatoDescarga);

                response.setContentType("application/pdf");
                response.addHeader(
                        "Content-Disposition",
                        "attachment; filename=DDJJ-" + idRed + "-" + fecha + ".pdf");

                response.getOutputStream().write(result);

            } else if (op.equals("CntForm-BolRechazadasNoRechazadas")) {

                SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaDesde = (request.getParameter("fechaDesde") != null && !request.getParameter("fechaDesde").isEmpty()) ? spdf.parse(request.getParameter("fechaDesde")) : null;
                Date fechaHasta = (request.getParameter("fechaHasta") != null && !request.getParameter("fechaHasta").isEmpty()) ? spdf.parse(request.getParameter("fechaHasta")) : null;
                Boolean todosFormaPago = (request.getParameter("todosFormaPago") != null ? new Boolean(request.getParameter("todosFormaPago")) : true);
                Boolean filtroEfectivo = (request.getParameter("filtroEfectivo") != null ? new Boolean(request.getParameter("filtroEfectivo")) : true);
                Boolean filtroCheque = (request.getParameter("filtroCheque") != null ? new Boolean(request.getParameter("filtroCheque")) : true);

                Boolean filtroAceptado = (request.getParameter("filtroAceptados") != null ? new Boolean(request.getParameter("filtroAceptados")) : true);
                Boolean filtroRechazados = (request.getParameter("filtroRechazados") != null ? new Boolean(request.getParameter("filtroRechazados")) : true);
                Boolean todosAceptadosRechazados = (request.getParameter("todosAceptadosRechazados") != null ? new Boolean(request.getParameter("todosAceptadosRechazados")) : true);

                List<Map<String, Object>> lista = boletaPagoContribFacade.listaReporteRechazadosNoRechazados(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, fechaDesde, fechaHasta, filtroAceptado, filtroRechazados, todosAceptadosRechazados);
                result = reportesF.reporteRechazadosNoRechazados(lista, idRed, formatoDescarga);

                procesarResultadoReportes(result, "CntForm-BolRechazadasNoRechazadas-" + "-" + spdf.format(new Date()), formatoDescarga, response);

//                response.setContentType("application/pdf");
//                response.addHeader(
//                        "Content-Disposition",
//                        "attachment; filename=CntForm-BolRechazadasNoRechazadas-" + "-" + spdf.format(new Date()) + ".pdf");
//
//                response.getOutputStream().write(result);
            } else if (op.equals("CntForm-RecCntEnv")) {

                SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");
                // String rechazado = (request.getParameter(CONTROL_FORMULARIOS.RECHAZADO) != null) ? request.getParameter(CONTROL_FORMULARIOS.RECHAZADO) : "";
                // String enviado = (request.getParameter(CONTROL_FORMULARIOS.ENVIADO) != null) ? request.getParameter(CONTROL_FORMULARIOS.ENVIADO) : "";
                Boolean recepcionados = (request.getParameter("recepcionados") != null ? new Boolean(request.getParameter("recepcionados")) : false);
                Boolean todosRecepcionados = (request.getParameter("todosRecepcionados") != null ? new Boolean(request.getParameter("todosRecepcionados")) : true);
                Boolean controlados = (request.getParameter("controlados") != null ? new Boolean(request.getParameter("controlados")) : false);
                Boolean todosControlados = (request.getParameter("todosControlados") != null ? new Boolean(request.getParameter("todosControlados")) : true);
                Date fechaDesde = (request.getParameter("fechaDesde") != null && !request.getParameter("fechaDesde").isEmpty()) ? spdf.parse(request.getParameter("fechaDesde")) : null;
                Date fechaHasta = (request.getParameter("fechaHasta") != null && !request.getParameter("fechaHasta").isEmpty()) ? spdf.parse(request.getParameter("fechaHasta")) : null;

                String fecha = request.getParameter("fecha");
                List<Map<String, Object>> lista = formContribFacade.listaReporteRecContEnv(idRed, idRecaudador, idSucursal, idTerminal, idGestion, recepcionados, todosRecepcionados, controlados, todosControlados, fechaDesde, fechaHasta);
                result = reportesF.reporteControlFormulariosRecepControlEnv(lista, idRed, formatoDescarga);

                procesarResultadoReportes(result, "CntForm-RecCntEnv-" + "-" + spdf.format(new Date()), formatoDescarga, response);

//                response.setContentType("application/pdf");
//                response.addHeader(
//                        "Content-Disposition",
//                        "attachment; filename=CntForm-RecCntEnv-" + "-" + spdf.format(new Date()) + ".pdf");
//
//                response.getOutputStream().write(result);
            } else if (op.equals("TPC")) {
                //Integer idRed = Integer.parseInt(request.getParameter("idRed"));
                //String fecha = request.getParameter("fecha");
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                Date fechaDesde = sdf.parse(fechaIni);
                Date fechaHasta = sdf.parse(fechaFin);
                String tipo = request.getParameter("TIPO");
                List<Map<String, Object>> lista;
                this.grupoFacade.calcularNumeroCajaDeGruposDDJJ(idRed, fechaDesde, fechaHasta);
                if (tipo.equalsIgnoreCase("ROTULO_DETALLE")) {
                    lista = this.grupoFacade.reporteNumeroCajaDeGruposDDJJDetallado(idRed, fechaDesde, fechaHasta);
                } else {
                    lista = this.grupoFacade.reporteNumeroCajaDeGruposDDJJ(idRed, fechaDesde, fechaHasta);
                }
                reportes.procesarResultadoReportes(
                        this.reportesF.reporteTapaCaja(
                                lista,
                                idRed,
                                new SimpleDateFormat("dd/MM/yyyy").format(fechaDesde),
                                tipo.equalsIgnoreCase("ROTULO") || tipo.equalsIgnoreCase("ROTULO_DETALLE"),
                                tipo.equalsIgnoreCase("ROTULO_DETALLE"), formatoDescarga),
                        "ReporteTapaCaja-" + fechaIni + "-" + fechaFin + "_" + tipo + "." + formatoDescarga,
                        formatoDescarga,
                        response);

            } else if (op.equalsIgnoreCase("REPORTE_ERROR_DIGITACION")) {

                if ((request.getParameter("idFormulario") != null) && (request.getParameter("mensajeErrorServlet") != null)) {

                    String[] respuestaComponentes;
                    Integer modo;
                    String cabeceraCertificacion = new String();
                    String bodyCertificacion = new String();

                    String cabeceraTicket = "";
                    Date date = new Date();
                    Format formatterFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String fecha = formatterFechaHora.format(date);
                    cabeceraCertificacion = "     R E P O R T E D E  E R R O R E S           \n\n";
                    UsuarioTerminal ut = (UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal");
                    cabeceraCertificacion += ut.getTerminal().getSucursal().getRecaudador().getDescripcion() + "\n\n";
                    cabeceraCertificacion += "RUC: " + ut.getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi() + "    -    TEL.:" + ut.getTerminal().getSucursal().getTelefono() + "\n\n";
                    cabeceraCertificacion += "DIRECCION: " + ut.getTerminal().getSucursal().getDireccion() + "\n\n";
                    cabeceraCertificacion += " \n\n";
                    bodyCertificacion += "Terminal: " + ut.getTerminal().getCodigoTerminal() + "     SUC: " + ut.getTerminal().getSucursal().getDescripcion() + "\n\n";
                    bodyCertificacion += "USUARIO: " + ut.getUsuario().getPersona().getApellidos() + ", " + ut.getUsuario().getPersona().getNombres() + " (" + ut.getUsuario().getCodigo() + ")" + "\n\n";

                    bodyCertificacion += "Fecha y Hora: " + fecha + "\n\n";

                    bodyCertificacion += "FORMULARIO: " + request.getParameter("idFormulario") + "\n\n";
                    bodyCertificacion += request.getParameter("mensajeErrorServlet") + "\n\n";

                    //String cabeceraTicketPantalla = "REPORTE DE ERRORES\n\n" + "0100000010000xxx\n\n" + "RECAU DE PRUEBA - SUC. DE PRUEBA\n\n" + "RUC: 80000001 - TEL: 490-476\n\n" + " DIRECCION: Direccion Prueba\n\n" + "*** FORMULARIO: " + request.getParameter("idFormulario") + " ***\n\n" + request.getParameter("mensajeErrorServlet") + "\n\n" + fecha + "\n\n";
                    String cabeceraTicketPantalla = cabeceraCertificacion + bodyCertificacion;
                    respuestaComponentes = cabeceraTicketPantalla.split("\n\n");
                    modo = 1;
                    for (String ooo : respuestaComponentes) {

                        cabeceraTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
                    }
                    jsonFinal.put("success", true);
                    jsonFinal.put("ticketError", cabeceraTicket);
                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "Los parametros no son correctos");

                }
                response.getWriter().println(jsonFinal);
                response.getWriter().close();
            } else if (op.equalsIgnoreCase("AUTORIZAR_REPORTE")) {

                if (request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (request.getSession().getAttribute("contrasenha").equals(py.com.konecta.redcobros.gestionweb.utiles.Utiles.getSha1(request.getParameter("PASSWORD"))))) {

                    jsonFinal.put("success", true);

                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "No esta autorizado a realizar la operacion.");

                }
                response.getWriter().println(jsonFinal);
                response.getWriter().close();
            } else if (op.equals("BP-ORDEN")) {

                Long numeroOrden = request.getParameter("numeroOrden") != null ? Long.parseLong(request.getParameter("numeroOrden")) : null;
                if (numeroOrden == null) {
                    throw new RuntimeException("El parámetro Número de Orden no puede ser nulo");
                }

                /*
                 * if (idUsuario == null) { throw new RuntimeException("El
                 * parámetro Id de Usuario no puede ser nulo"); }
                 */
                result = reportesF.boletasPagoPorOrden(numeroOrden, idUsuario, idRed, formatoDescarga);

                procesarResultadoReportes(result, "BP-ORDEN-" + numeroOrden + "." + formatoDescarga, formatoDescarga, response);
            } else if (op.equals("DDJJ-ORDEN")) {

                Long numeroOrden = Long.parseLong(request.getParameter("numeroOrden"));
                result = reportesF.DDJJPorOrden(numeroOrden, idUsuario, idRed, formatoDescarga);

                procesarResultadoReportes(result, "DDJJ-ORDEN-" + numeroOrden + "." + formatoDescarga, formatoDescarga, response);

            } else if (op.equals("COB-RESUM")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {

                    result = reportesF.cobranzaResumido(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);

                    procesarResultadoReportes(result, "COB-RESUM." + formatoDescarga, formatoDescarga, response);

                }

            } else if (op.equals("TAPA-LOTE-GROUP")) {

                idGrupo = request.getParameter("GRUPO") != null && !request.getParameter("GRUPO").isEmpty() ? new Long(request.getParameter("GRUPO")) : null;
                SimpleDateFormat spDateFormat = new SimpleDateFormat("ddMMyyyy");
                if (fechaIni == null) {
                    fechaIni = spDateFormat.format(new Date());
                }
                idTerminal = null;
                idSucursal = null;
                codigoServicio = null;
                estadoTransaccion = null;

                result = reportesF.tapaLoteGrupoResumido(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGrupo, formatoDescarga);

                procesarResultadoReportes(result, "TAPA-LOTE-GROUP." + formatoDescarga, formatoDescarga, response);

            } else if (op.equals("TAPA-LOTE-GROUP-DET")) {
                idGrupo = request.getParameter("GRUPO") != null && !request.getParameter("GRUPO").isEmpty() ? new Long(request.getParameter("GRUPO")) : null;
                SimpleDateFormat spDateFormat = new SimpleDateFormat("ddMMyyyy");
                if (fechaIni == null) {
                    fechaIni = spDateFormat.format(new Date());
                }
                idTerminal = null;
                idSucursal = null;
                codigoServicio = null;
                estadoTransaccion = null;

                result = reportesF.tapaLoteGrupo(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGrupo, formatoDescarga);
                procesarResultadoReportes(result, "TAPA-LOTE-GROUP-DET." + formatoDescarga, formatoDescarga, response);

            } else if (op.equals("CIERRE-RESUMIDO")) {
                idGestion = request.getParameter("GESTION") != null && !request.getParameter("GESTION").isEmpty() ? new Long(request.getParameter("GESTION")) : null;

                SimpleDateFormat spDateFormat = new SimpleDateFormat("ddMMyyyy");
                if (fechaIni == null) {
                    fechaIni = spDateFormat.format(new Date());
                }

                // idUsuario = (Long) request.getSession().getAttribute("idUsuario");
                // idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
                // idSucursal = (Long) request.getSession().getAttribute("idSucursal");
                result = reportesF.reporteCierreResumido(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
                procesarResultadoReportes(result, "CIERRE-RESUMIDO." + formatoDescarga, formatoDescarga, response);

            } else if (op.equals("CIERRE-DETALLADO")) {
                // idGestion = request.getParameter("GESTION") != null && !request.getParameter("GESTION").isEmpty() ? new Long(request.getParameter("GESTION")) : null;
                SimpleDateFormat spDateFormat = new SimpleDateFormat("ddMMyyyy");
                if (fechaIni == null) {
                    fechaIni = spDateFormat.format(new Date());
                }

                // idUsuario = (Long) request.getSession().getAttribute("idUsuario");
                // idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
                // idSucursal = (Long) request.getSession().getAttribute("idSucursal");
                String nombre = null;
                if (tipoReporte.equalsIgnoreCase("CHEQUE")) {
                    nombre = "REPORTE-CHEQUES-COBRADOS-DDJJ.";
                    result = reportesF.reporteChequesCobradosDDJJ(idRed, idUsuario, idGestion, idTerminal, idSucursal, idRecaudador, fechaIni, formatoDescarga);
                } else {
                    nombre = "CIERRE-DETALLADO.";
                    result = reportesF.reporteCierreDetallado(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
                }

                procesarResultadoReportes(result, nombre + formatoDescarga, formatoDescarga, response);

            } else if (op.equals("COB-DET")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.cobranzaDetallado(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);

                    procesarResultadoReportes(result, "COB-DET." + formatoDescarga, formatoDescarga, response);
//                response.setContentType("application/pdf");
//                response.addHeader(
//                        "Content-Disposition",
//                        "attachment; filename=COB-DET.pdf");
//                response.getOutputStream().write(result);
                }

            } else if (op.equals("COB-DET-EFECT")) {

                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.cobranzaDetalladoEfectivo(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
                    procesarResultadoReportes(result, "COB-DET-EFECT." + formatoDescarga, formatoDescarga, response);

//                response.setContentType("application/pdf");
//                response.addHeader(
//                        "Content-Disposition",
//                        "attachment; filename=COB-DET-EFECT.pdf");
//                response.getOutputStream().write(result);
                }

            } else if (op.equals("COB-DET-CHEQUE")) {

                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {

                    result = reportesF.cobranzaDetalladoCheque(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
                    procesarResultadoReportes(result, "COB-DET-CHEQUE." + formatoDescarga, formatoDescarga, response);
//                response.setContentType("application/pdf");
//                response.addHeader(
//                        "Content-Disposition",
//                        "attachment; filename=COB-DET-CHEQUE.pdf");
//                response.getOutputStream().write(result);
                }

            } else if (op.equals("REPORTE-FORMULARIO-OT")) {

                String tipoPagoString;
                if (tipoPago < 3) {
                    tipoPagoString = ((Integer) 1).equals(tipoPago) ? "EFECTIVO" : "CHEQUES";
                    result = reportesF.reporteFormularioOt(idRed, fechaIni, /*
                             * nroOt,
                             */ tipoPago);
                    procesarResultadoReportes(result, "FORMULARIO_OT_" + tipoPagoString + "_" + fechaIni + ".pdf", "pdf", response);
                } else {
                    if (!((Integer) 3).equals(tipoPago)) {
                        result = reportesF.reporteDetalladoTransferenciaDGR(idRed, fechaIni, formatoDescarga);
                    } else {
                        result = reportesF.reporteTransferenciaDGR(fechaIni);
                    }
                    procesarResultadoReportes(result, "TRANSFERENCIA_DGR_" + fechaIni + "." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("RESUMEN-CLEARING-COMISION")) {
//                String tipoPagoString = ((Integer)1).equals(tipoPago)?"EFECTIVO":"CHEQUES";

                result = reportesF.resumenClearingComision(idRed, idRecaudador, idSucursal, fechaIni, fechaFin, formatoDescarga);
                procesarResultadoReportes(result, "RESUMEN_CLEARING_COMISION_" + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
//                response.setContentType("application/pdf");
//                response.addHeader(
//                        "Content-Disposition",
//                        "attachment; filename=COB-DET-CHEQUE.pdf");
//                response.getOutputStream().write(result);
            } else if (op.equals("RESUMEN_DE_TRANSACCIONES")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.reporteRecaudacion(idRed, fechaIni, fechaFin, idRecaudador, idSucursal, formatoDescarga, tipoReporte);
                    procesarResultadoReportes(result, "RESUMEN_DE_TRANSACCIONES_" + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("REPORTE_RESUMEN_FACTURADOR")) {
                String isCorte = request.getParameter("isCorte") != null && !request.getParameter("isCorte").isEmpty() ? request.getParameter("isCorte") : null;
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    try {

                        result = reportesF.reporteResumenFacturador(idRed, fechaIni, fechaFin, idRecaudador, idFacturador, idSucursal, isCorte, formatoDescarga, tipoReporte);
                    } catch (Exception ex) {
                        Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    procesarResultadoReportes(result, "RESUMEN_DE_FACTURADORES_" + fechaIni + "_" + fechaFin + "_" + isCorte + "." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("REPORTE-TERMINALES-ABIERTAS")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.reporteTerminalesAbiertas(fechaIni, fechaFin, idRecaudador, idSucursal, formatoDescarga);
                    procesarResultadoReportes(result, "REPORTE_TERMINALES_ABIERTAS" + fechaIni + "_" + fechaFin + "_" + "." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("CIERRE-CS")) {
                SimpleDateFormat spDateFormat = new SimpleDateFormat("ddMMyyyy");
                if (fechaIni == null) {
                    fechaIni = spDateFormat.format(new Date());
                }

//                idUsuario = (Long) request.getSession().getAttribute("idUsuario");
//                idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
//                idSucursal = (Long) request.getSession().getAttribute("idSucursal");
                if (request.getParameter("tipoCierre").equalsIgnoreCase("CIERRE-RESUMIDO")) {
                    result = reportesF.reporteCierreResumidoCS(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, idFacturador, formatoDescarga, false);
                } else {
                    result = reportesF.reporteCierreDetalladoCS(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, idFacturador, formatoDescarga);
                }
                procesarResultadoReportes(result, request.getParameter("tipoCierre") + "." + formatoDescarga, formatoDescarga, response);

            } else if (op.equals("COB-CS")) {
                if (tipoReporte != null) {
                    if (tipoReporte.equalsIgnoreCase("DETALLADO")) {
                        if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                            result = reportesF.cobranzaDetalladoCS(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga, null, false, idMoneda);
                            procesarResultadoReportes(result, "COB-DET-CS." + formatoDescarga, formatoDescarga, response);
                        }
                    } else {
                        if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                            result = reportesF.cobranzaResumidoCS(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga, idMoneda);
                            procesarResultadoReportes(result, "COB-RESUM-CS." + formatoDescarga, formatoDescarga, response);
                        }
                    }
                }
            } else if (op.equals("COB-EFECT-CS")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.cobranzaDetalladoEfectivoCS(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga, idMoneda);
                    procesarResultadoReportes(result, "COB-DET-EFECT_CS." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("COB-CHEQUE-CS")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.cobranzaDetalladoChequeCS(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga, idMoneda);
                    procesarResultadoReportes(result, "COB-DET-CHEQUE_CS." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("COB-POR-SERVICIO")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.cobranzaDetalladoPorServicioCS(idRed, idRecaudador, idSucursal, idFacturador, codigoServicio, fechaIni, fechaFin, zona, formatoDescarga);
                    procesarResultadoReportes(result, "COB-DET-SERVICIO_CS." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("COB-PARA-FAC")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.cobranzaDetalladoParaFacturador(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
                    procesarResultadoReportes(result, "COB-DET-PARA-FAC." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("REPORTE_MOVIMIENTO_FACTURADORES")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.reporteMovimientoFacturadores(fechaIni, fechaFin, formatoDescarga);
                    procesarResultadoReportes(result, "MOVIMIENTO_FAC." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("REPORTE_COMISION_CS")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    result = reportesF.reporteComisionServicios(idRed, idRecaudador, idFacturador, idSucursal, idServicio, tipoPago, fechaIni, fechaFin, tipoReporte, formatoDescarga);
                    procesarResultadoReportes(result, "REPORTE_COMISION" + tipoReporte + "." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("REPORTES_CIERRE_USUARIOS")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    try {
                        result = reportesF.reporteCierreUsuariosSet(idRed, fechaIni, fechaFin, idSucursal, idRecaudador, formatoDescarga, tipoReporte);
                    } catch (Exception ex) {
                        Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fechaFin == null) {
                        fechaFin = fechaIni;
                    }
                    procesarResultadoReportes(result, "CIERRE_USUARIO_" + tipoReporte + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equals("CLEARING-CS")) {
                try {
                    Long idEntidad = request.getParameter("idEntidad") != null && !request.getParameter("idEntidad").isEmpty() ? new Long(request.getParameter("idEntidad")) : null;;
                    result = reportesF.reporteClearingCS(idEntidad, fechaIni, fechaFin, tipoReporte, formatoDescarga);
                    procesarResultadoReportes(result, "CLEARING_CS_" + tipoReporte + "_" + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
                } catch (Exception ex) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
            } /*
             * else if (op.equals("FACTURACION-CS")) { try { result =
             * reportesF.reporteFacturacionCS(idRed, idRecaudador, tipoPago,
             * tipoReporte, fechaIni, fechaFin, diasAnteriores,
             * formatoDescarga); procesarResultadoReportes(result,
             * "FACTURACION_CS_" + tipoReporte + fechaIni + "_" + fechaFin + "."
             * + formatoDescarga, formatoDescarga, response); } catch (Exception
             * ex) {
             * Logger.getLogger(reportes.class.getName()).log(Level.SEVERE,
             * null, ex); } }
             */ else if (op.equals("REPORTES_SAN_CRISTOBAL")) {
                if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                    try {
                        result = reportesF.reporteSanCristobal(idRed, fechaIni, fechaFin, idSucursal, idRecaudador, idServicio, formatoDescarga);
                    } catch (Exception ex) {
                        Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fechaFin == null) {
                        fechaFin = fechaIni;
                    }
                    procesarResultadoReportes(result, "REPORTE_SAN_CRISTOBAL_" + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
                }
            } else if (op.equalsIgnoreCase("REPORTE_ADMINISTRACION")) {
                try {
                    result = reportesF.reporteFacturacionAdministracion(fechaIni, fechaFin, idRecaudador, tipoPago, idRed, diasAnteriores, idServicio, idFacturador, tipoReporte, formatoDescarga);
                } catch (Exception ex) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
                procesarResultadoReportes(result, "REPORTE_ADMINISTRACION_" + tipoReporte + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
            } else if (op.equalsIgnoreCase("REPORTE_COMISION_FAC_REC")) {
                try {
                    if (tipoReporte.equalsIgnoreCase("DETALLADO")) {
                        if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                            String entidad = request.getParameter("entidad") != null && !request.getParameter("entidad").isEmpty() ? request.getParameter("entidad") : null;
                            result = reportesF.reporteComisionParaFacRec(idRed, idRecaudador, idSucursal, idFacturador, codigoServicio, fechaIni, fechaFin, zona, entidad, null, formatoDescarga);
                            procesarResultadoReportes(result, "REPORTE_COMISION_" + entidad + "_" + tipoReporte + "." + formatoDescarga, formatoDescarga, response);
                        }
                    } else {
                        result = reportesF.reporteFacturacionCS(idRed, idRecaudador, tipoPago, tipoReporte, fechaIni, fechaFin, diasAnteriores, formatoDescarga);
                        procesarResultadoReportes(result, "REPORTE_COMISION_" + tipoReporte + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (op.equalsIgnoreCase("SOLICITUD_TRANSACCION")) {
                try {
                    Long idTransaccion = new Long(request.getParameter("transaccion"));
                    String nombre = "TransacN_" + idTransaccion + "_ticket.pdf";
                    /**
                     * *************************************************************
                     */
                    result = ticketF.generarTicketTransaccion(idTransaccion);
                    procesarResultadoReportes(result, nombre, "pdf", response);
                    Logger.getLogger(reportes.class.getName()).log(Level.INFO, "Generando el archivo: {0}", nombre);

                } catch (Exception e) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, e);
                    //jsonFinal.put("success", false);
                    //jsonFinal.put("motivo", e.getMessage());
                }

            } else if (op.equals("REPORTE-DIGITACION")) {
                String certificado = request.getParameter("certificado") != null && !request.getParameter("certificado").isEmpty() ? request.getParameter("certificado") : null;

                try {
                    result = reportesF.reporteDigitacion(idRed, idRecaudador, idSucursal, idUsuario, certificado, fechaIni, fechaFin, formatoDescarga);
                } catch (Exception ex) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
                procesarResultadoReportes(result, "REPORTE-DIGITACION." + formatoDescarga, formatoDescarga, response);
            } else if (op.equals("REPORTE-DESEMBOLSO")) {
                try {
                    result = reportesF.reportePractiRetiro(fechaIni, fechaFin, formatoDescarga);
                } catch (Exception ex) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
                procesarResultadoReportes(result, "REPORTE-DESEMBOLSO." + formatoDescarga, formatoDescarga, response);
            } else {
                json.put("success", false);
                json.put("motivo", "No existe la opcion pedida");
                response.getWriter().println(json);

            }

        } catch (Exception e) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void procesarResultadoReportes(byte[] result, String fileName, String tipoArchivo, HttpServletResponse response) throws IOException {

        if (result != null) {
            if (tipoArchivo.equals("pdf")) {
                response.setContentType("application/pdf");

            } else if (tipoArchivo.equals("xls")) {
                response.setContentType("application/vnd.ms-excel");

            } else if (tipoArchivo.equals("text")) {
                response.setContentType("application/text");
            }
            response.addHeader(
                    "Content-Disposition",
                    "attachment; filename=" + fileName);
            response.getOutputStream().write(result);
            //response.getOutputStream().close();

        } else {
            PrintWriter out = response.getWriter();
            out.print("<script> parent.alertNoExisteResuladoReporte();</script>");
            //out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
