/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.GestionFacade;
import py.com.konecta.redcobros.ejb.ParametroSistemaFacade;
import py.com.konecta.redcobros.ejb.ReportesFacade;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.ParametroSistema;
import py.com.konecta.redcobros.entities.UsuarioTerminal;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
public class reportes extends HttpServlet {

    @EJB
    private ReportesFacade reportesF;
    @EJB
    private GestionFacade gestionFacade;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public boolean controlFechaIniMenorIgualFechaFin(String fechaFin, String fechaIni) {
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
        //    PrintWriter out = response.getWriter();
        String op = request.getParameter("op");
        JSONObject json = new JSONObject();
        JSONObject jsonFinal = new JSONObject();
        byte[] result = null;
        Long idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
        Long idRed = (Long) request.getSession().getAttribute("idRed");
        Boolean idGesRedCW = request.getParameter("Ges") != null && !request.getParameter("Ges").isEmpty() ? true : false;

        if (idGesRedCW) {//para habilitar el filtro por recaudador y red
            idRecaudador = request.getParameter("idRecaudador") != null && !request.getParameter("idRecaudador").isEmpty() ? Long.parseLong(request.getParameter("idRecaudador")) : null;
            idRed = request.getParameter("idRed") != null && !request.getParameter("idRed").isEmpty() ? Long.parseLong(request.getParameter("idRed")) : null;
        }

        String fechaFin = request.getParameter("fechaFin") != null && !request.getParameter("fechaFin").isEmpty() ? request.getParameter("fechaFin") : null;
        String fechaIni = request.getParameter("fechaIni") != null && !request.getParameter("fechaIni").isEmpty() ? request.getParameter("fechaIni") : null;

        Long idTerminal = null;
        if (request.getParameter("tipoConsulta") != null && request.getParameter("tipoConsulta").equalsIgnoreCase("2")) {
            idTerminal = (Long) request.getSession().getAttribute("idTerminal");
        }
        Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
        Long idFacturador = request.getParameter("idFacturador") != null && !request.getParameter("idFacturador").isEmpty() ? new Long(request.getParameter("idFacturador")) : null;
        Long idSucursal = request.getParameter("idSucursal") != null && !request.getParameter("idSucursal").isEmpty() ? new Long(request.getParameter("idSucursal")) : null;

        Long idGestion = request.getParameter("idGestion") != null && !request.getParameter("idGestion").isEmpty() ? new Long(request.getParameter("idGestion")) : null;
        Long idGrupo = request.getParameter("idGrupo") != null && !request.getParameter("idGrupo").isEmpty() ? new Long(request.getParameter("idGrupo")) : null;
        String codigoServicio = request.getParameter("codigoServicio") != null && !request.getParameter("codigoServicio").isEmpty() ? request.getParameter("codigoServicio") : null;
        //String estadoTransaccion = request.getParameter("estadoTransaccion") != null && !request.getParameter("estadoTransaccion").isEmpty() ? request.getParameter("estadoTransaccion").equalsIgnoreCase("S") || request.getParameter("estadoTransaccion").equalsIgnoreCase("N")?request.getParameter("estadoTransaccion"):null : null;
        String estadoTransaccion = request.getParameter("estadoTransaccion") != null ? request.getParameter("estadoTransaccion").equalsIgnoreCase("S") || request.getParameter("estadoTransaccion").equalsIgnoreCase("N") ? request.getParameter("estadoTransaccion") : null : null;
        String formatoDescarga = request.getParameter("formatoDescarga") != null && !request.getParameter("formatoDescarga").isEmpty() ? request.getParameter("formatoDescarga") : "pdf";
        String tipoReporte = request.getParameter("tipoReporte") != null && !request.getParameter("tipoReporte").isEmpty() ? request.getParameter("tipoReporte") : null;
        String zona = request.getParameter("zona") != null && !request.getParameter("zona").isEmpty() ? request.getParameter("zona") : null;
        Integer idMoneda = request.getParameter("idMoneda") != null && !request.getParameter("idMoneda").isEmpty() ? Integer.valueOf(request.getParameter("idMoneda")) : null;
        
        if (op.equalsIgnoreCase("REPORTE_ERROR_DIGITACION")) {

            if ((request.getParameter("idFormulario") != null) && (request.getParameter("mensajeErrorServlet") != null)) {

                String[] respuestaComponentes;
                Integer modo;
                String cabeceraCertificacion = new String();
                String bodyCertificacion = new String();
                String pieCertificacion = new String();

                String cabeceraTicket = "";
                Date date = new Date();
                Format formatterFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String fecha = formatterFechaHora.format(date);
                cabeceraCertificacion = "     R E P O R T E  D E  E R R O R E S          \n\n";
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
                pieCertificacion += "   F I N  D E L  R E P O R T E          \n\n";

                String cabeceraTicketPantalla = cabeceraCertificacion + bodyCertificacion + pieCertificacion;
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

            if (request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (request.getSession().getAttribute("contrasenha").equals(UtilesSet.getSha1(request.getParameter("PASSWORD"))))) {
                // if (usuarioF.esSupervisor((Integer) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                jsonFinal.put("success", true);

            } else {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", "No esta autorizado a realizar la operación.");

            }
            response.getWriter().println(jsonFinal);
            response.getWriter().close();
        } else if (op.equals("BP-ORDEN")) {
            Long numeroOrden = request.getParameter("numeroOrden") != null ? Long.parseLong(request.getParameter("numeroOrden")) : null;
            if (numeroOrden == null) {
                throw new RuntimeException("El parámetro Número de Orden no puede ser nulo");
            }
            if (idUsuario == null) {
                throw new RuntimeException("El parámetro Id de Usuario no puede ser nulo");
            }

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
            idRecaudador = null;
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
            idRecaudador = null;
            idSucursal = null;
            codigoServicio = null;
            estadoTransaccion = null;

            result = reportesF.tapaLoteGrupo(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGrupo, formatoDescarga);
            procesarResultadoReportes(result, "TAPA-LOTE-GROUP-DET." + formatoDescarga, formatoDescarga, response);

        } else if (op.equals("RESUMEN-CLEARING-COMISION")) {
            try {
                result = reportesF.resumenClearingComision(idRed, null, null, fechaIni, fechaFin, formatoDescarga);
            } catch (Exception ex) {
                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
            procesarResultadoReportes(result, "RESUMEN_CLEARING_COMISION_" + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
        } else if (op.equals("REPORTE-GESTOR")) {
            try {
                Long ci = request.getParameter("ci") != null && !request.getParameter("ci").isEmpty() ? new Long(request.getParameter("ci")) : null;
                result = reportesF.reporteGestor(fechaIni, fechaFin, idSucursal, ci, formatoDescarga);
            } catch (Exception ex) {
                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
            procesarResultadoReportes(result, "REPORTE_GESTOR_" + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
        } else if (op.equals("CIERRE-RESUMIDO")) {
            idGestion = request.getParameter("GESTION") != null && !request.getParameter("GESTION").isEmpty() ? new Long(request.getParameter("GESTION")) : null;
            SimpleDateFormat spDateFormat = new SimpleDateFormat("ddMMyyyy");
            if (fechaIni == null) {
                fechaIni = spDateFormat.format(new Date());
            }

            idUsuario = (Long) request.getSession().getAttribute("idUsuario");
            idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
            idSucursal = (Long) request.getSession().getAttribute("idSucursal");

            result = reportesF.reporteCierreResumido(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
            procesarResultadoReportes(result, "CIERRE-RESUMIDO." + formatoDescarga, formatoDescarga, response);

        } else if (op.equals("CIERRE-DETALLADO")) {
            idGestion = request.getParameter("GESTION") != null && !request.getParameter("GESTION").isEmpty() ? new Long(request.getParameter("GESTION")) : null;
            SimpleDateFormat spDateFormat = new SimpleDateFormat("ddMMyyyy");
            if (fechaIni == null) {
                fechaIni = spDateFormat.format(new Date());
            }

            idUsuario = (Long) request.getSession().getAttribute("idUsuario");
            idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
            idSucursal = (Long) request.getSession().getAttribute("idSucursal");
            String nombre = null;
            if (tipoReporte.equalsIgnoreCase("CHEQUE")) {
                idTerminal = (Long) request.getSession().getAttribute("idTerminal");
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
            }

        } else if (op.equals("COB-DET-EFECT")) {

            if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                result = reportesF.cobranzaDetalladoEfectivo(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
                procesarResultadoReportes(result, "COB-DET-EFECT." + formatoDescarga, formatoDescarga, response);
            }

        } else if (op.equals("COB-DET-CHEQUE")) {

            if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                result = reportesF.cobranzaDetalladoCheque(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
                procesarResultadoReportes(result, "COB-DET-CHEQUE." + formatoDescarga, formatoDescarga, response);
            }

        } else if (op.equals("CIERRE-CS")) {
            //idGestion = request.getParameter("GESTION") != null && !request.getParameter("GESTION").isEmpty() ? new Long(request.getParameter("GESTION")) : null;
            SimpleDateFormat spDateFormat = new SimpleDateFormat("ddMMyyyy");
            if (fechaIni == null) {
                fechaIni = spDateFormat.format(new Date());
            }

            idUsuario = (Long) request.getSession().getAttribute("idUsuario");
            idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
            idSucursal = (Long) request.getSession().getAttribute("idSucursal");
            Boolean tmu = request.getParameter("tmu") != null && request.getParameter("tmu").equalsIgnoreCase("on") ? true : false;
            if (request.getParameter("tipoCierre").equalsIgnoreCase("CIERRE-RESUMIDO")) {
                result = reportesF.reporteCierreResumidoCS(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, idFacturador, formatoDescarga, tmu);
                if (tmu) {
                    String reporte = new String(result);

                    String reporteCierre = reporte.replaceAll("\n", ";;;");
                    String[] reporteCierreImpresora = reporteCierre.split(";;;");
                    String cadenaImpresionTicket = "";

                    for (String ooo : reporteCierreImpresora) {
                        cadenaImpresionTicket += 1 + ";;" + "N" + ";;" + ooo + ";;;";
                    }

                    json.put("reporteCierrePantalla", reporte.replace("\n", "<br>"));
                    json.put("reporteCierreImpresora", cadenaImpresionTicket);
                    json.put("success", true);
                    response.getWriter().println(json.toString());
                }
            } else {
                result = reportesF.reporteCierreDetalladoCS(idRed, idUsuario, idTerminal, idSucursal, idRecaudador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, idFacturador, formatoDescarga);
            }
            if (!tmu) {
                procesarResultadoReportes(result, request.getParameter("tipoCierre") + "." + formatoDescarga, formatoDescarga, response);
            }

        } else if (op.equals("COB-CS")) {
            if (tipoReporte != null) {
                if (request.getParameter("idSupervisor") != null && !request.getParameter("idSupervisor").isEmpty()) {
                    idUsuario = null;
                }
                if (tipoReporte.equalsIgnoreCase("DETALLADO")) {
                    Boolean tmu = request.getParameter("tmu") != null && request.getParameter("tmu").equalsIgnoreCase("on") ? true : false;
                    if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                        Gestion gestion = null;
                        String tipoPago = null;
                        if (tmu) {
                            tipoPago = request.getParameter("tipoPago") != null && !request.getParameter("tipoPago").isEmpty() ? request.getParameter("tipoPago") : null;
                            if (tipoPago != null) {
                                tipoPago = tipoPago.contains("CHEQUE") ? "C" : tipoPago.contains("EFECT") ? "E" : null;
                            }
                            HttpSession session = request.getSession();
                            gestion = (Gestion) session.getAttribute("gestion");
                            if (gestion == null) {
                                Map<String, HashMap<String, String>> tasks = new HashMap<String, HashMap<String, String>>();
                                tasks = (Map<String, HashMap<String, String>>) session.getAttribute("TASKS");
                                UsuarioTerminal ut = (UsuarioTerminal) session.getAttribute("objUsuarioTerminal");
                                try {
                                    gestion = gestionFacade.obtenerGestionDelDia(ut.getIdUsuarioTerminal(), tasks.get("OPERACIONES").get("APERTURA_SIMPLE") != null);
                                    idGestion = gestion.getIdGestion();
                                } catch (Exception ex) {
                                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                idGestion = gestion.getIdGestion();
                            }
                        }
                        result = reportesF.cobranzaDetalladoCS(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga, tipoPago, tmu, idMoneda);
                        if (tmu) {
                            if (result != null) {
                                String reporte = new String(result);

                                String reporteCierre = reporte.replaceAll("\n", ";;;");
                                String[] reporteCierreImpresora = reporteCierre.split(";;;");
                                String cadenaImpresionTicket = "";

                                for (String ooo : reporteCierreImpresora) {
                                    cadenaImpresionTicket += 1 + ";;" + "N" + ";;" + ooo + ";;;";
                                }

                                json.put("reporteCobranzaPantalla", reporte.replace("\n", "<br>"));
                                json.put("reporteCobranzaImpresora", cadenaImpresionTicket);
                                json.put("success", true);
                            } else {
                                json.put("success", false);
                            }

                            response.getWriter().println(json.toString());
                        } else {
                            procesarResultadoReportes(result, "COB-DET-CS." + formatoDescarga, formatoDescarga, response);
                        }
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
        } else if (op.equals("RESUMEN_DE_TRANSACCIONES")) {
            if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                try {
                    result = reportesF.reporteRecaudacion(idRed, fechaIni, fechaFin, idRecaudador, idSucursal, formatoDescarga, tipoReporte);
                } catch (Exception ex) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
                procesarResultadoReportes(result, "RESUMEN_DE_TRANSACCIONES_" + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
            }
        } else if (op.equals("REPORTES_CIERRE_USUARIOS")) {
            if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                try {
                    Long idRECAUDADOR = (Long) request.getSession().getAttribute("idRecaudador");
                    result = reportesF.reporteCierreUsuariosSet(idRed, fechaIni, fechaFin, idSucursal, idRECAUDADOR, formatoDescarga, tipoReporte);
                } catch (Exception ex) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                }

                procesarResultadoReportes(result, "CIERRE_USUARIO_" + tipoReporte + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
            }
        } else if (op.equals("REPORTE_RESUMEN_FACTURADOR")) {
            if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                try {
                    result = reportesF.reporteResumenFacturador(idRed, fechaIni, fechaFin, idRecaudador, idFacturador, idSucursal, "SIN_CORTE", formatoDescarga, tipoReporte);
                } catch (Exception ex) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
                procesarResultadoReportes(result, "RESUMEN_DE_FACTURADORES_" + fechaIni + "_" + fechaFin + "." + formatoDescarga, formatoDescarga, response);
            }
        } else if (op.equals("COB-PARA-FAC")) {
            idUsuario = request.getParameter("idUsuario") != null && !request.getParameter("idUsuario").isEmpty() ? new Long(request.getParameter("idUsuario")) : null;
            idTerminal = request.getParameter("idTerminal") != null && !request.getParameter("idTerminal").isEmpty() ? new Long(request.getParameter("idTerminal")) : null;
            if ((fechaFin == null && fechaIni != null) || ((fechaFin != null && fechaIni != null) && controlFechaIniMenorIgualFechaFin(fechaFin, fechaIni))) {
                result = reportesF.cobranzaDetalladoParaFacturador(idRed, idRecaudador, idSucursal, idUsuario, idTerminal, idFacturador, codigoServicio, estadoTransaccion, fechaIni, fechaFin, idGestion, formatoDescarga);
                procesarResultadoReportes(result, "COB-DET-PARA-FAC." + formatoDescarga, formatoDescarga, response);
            }
        } else if (op.equals("REPORTE-DIGITACION")) {
            String certificado = request.getParameter("certificado") != null && !request.getParameter("certificado").isEmpty() ? request.getParameter("certificado") : null;
            idSucursal = (Long) request.getSession().getAttribute("idSucursal");
            try {
                result = reportesF.reporteDigitacion(idRed, idRecaudador, idSucursal, idUsuario, certificado, fechaIni, fechaFin, formatoDescarga);
            } catch (Exception ex) {
                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
            procesarResultadoReportes(result, "REPORTE-DIGITACION." + formatoDescarga, formatoDescarga, response);
        } else if (op.equals("DOWNLOAD_USER_FILE")) {
            Long codExtUsuario = (Long) request.getSession().getAttribute("codExtUsuario");
            ParametroSistema paramFile = new ParametroSistema();
            paramFile.setNombreParametro("pathFileUser");
            String pathFile = parametroSistemaFacade.get(paramFile).getValor();
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            String nombreArchivoUsuario = codExtUsuario.toString() + sdf.format(today) + ".txt";
            File userFile;
            FileReader fr = null;
            //BufferedReader br = null;

            try {
                userFile = new File(pathFile + nombreArchivoUsuario);
                fr = new FileReader(userFile);
                BufferedReader entrada = new BufferedReader(fr);

                String file = "";
                String aux;
                while ((aux = entrada.readLine()) != null) {
                    file += aux + "\n";
                }
                procesarResultadoReportes(file.getBytes(), "datos.txt", "txt", response);
                if (!userFile.delete()) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, "No se pudo borrar el archivo de usuario:{0}", pathFile + nombreArchivoUsuario);
                }
            } catch (Exception e) {
                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, e2);
                }
            }

        } else {
            json.put("success", false);
            json.put("motivo", "No existe la opcion pedida");
            response.getWriter().println(json);

        }
    }

    private void procesarResultadoReportes(byte[] result, String fileName, String tipoArchivo, HttpServletResponse response) throws IOException {

        if (result != null) {
            if (tipoArchivo.equals("pdf")) {
                response.setContentType("application/pdf");

            } else if (tipoArchivo.equals("xls")) {
                response.setContentType("application/vnd.ms-excel");

            } else if (tipoArchivo.equals("txt")) {
                response.setContentType("text/plain");
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
