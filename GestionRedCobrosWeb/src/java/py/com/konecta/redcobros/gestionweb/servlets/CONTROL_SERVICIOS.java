/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.documenta.ws.Autenticacion;
import py.com.documenta.ws.RedCobro;
import py.com.documenta.ws.RedCobroService;
import py.com.konecta.redcobros.ejb.RegistroImpWebFacade;
import py.com.konecta.redcobros.ejb.RuteoServicioFacade;
import py.com.konecta.redcobros.ejb.TransaccionRcFacade;
import py.com.konecta.redcobros.entities.RegistroImpWeb;
import py.com.konecta.redcobros.entities.RuteoServicio;
import py.com.konecta.redcobros.entities.TransaccionRc;
import py.com.konecta.redcobros.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class CONTROL_SERVICIOS extends HttpServlet {

    @EJB
    private TransaccionRcFacade transaccionRcFacade;
    @EJB
    private RuteoServicioFacade ruteoServicioFacade;
    @EJB
    private RegistroImpWebFacade registroImpWebFacade;
    public static String ID_TRANSACCION = "ID_TRANSACCION";
    public static String CODIGO_TERMINAL = "CODIGO_TERMINAL";
    public static String DESCRIPCION_GESTION = "DESCRIPCION_GESTION";
    public static String SERVICIO_DESCRIPCION = "SERVICIO_DESCRIPCION";
    public static String ID_GESTION = "ID_GESTION";
    public static String NRO_BOLETA = "NRO_BOLETA";
    public static String FORMA_PAGO = "FORMA_PAGO";
    public static String FECHA_COBRO = "FECHA_COBRO";
    public static String REFERENCIA_PAGO = "REFERENCIA_PAGO";
    public static String ANULADO = "ANULADO";
    public static String ID_RED = "ID_RED";
    public static String ID_SUCURSAL = "ID_SUCURSAL";
    public static String ID_RECAUDADOR = "ID_RECAUDADOR";
    public static String FECHA_DESDE = "FECHA_DESDE";
    public static String FECHA_HASTA = "FECHA_HASTA";
    public static String ID_TERMINAL = "ID_TERMINAL";
    public static String FILTRO_CHEQUE = "FILTRO_CHEQUE";
    public static String FILTRO_EFECTIVO = "FILTRO_EFECTIVO";
    public static String MONTO_TOTAL = "MONTO_TOTAL";
    public static String USUARIO = "USUARIO";
    public static String MOTIVO = "MOTIVO";
    private static RedCobroService service = null;
    private static ReentrantLock lock = new ReentrantLock();

    public static RedCobro getWSManager(String url, String uri, String localPart,
            int connTo, int readTo) {
        RedCobro pexSoap;

        URL wsdlURL = RedCobroService.class.getClassLoader().getResource("schema/RedCobroService.wsdl");
        try {
            if (service == null) {
                try {
                    lock.lock();
                    if (service == null) {
                        service = new RedCobroService(wsdlURL, new QName(uri, localPart));
                    }
                } catch (Exception ex) {
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception ex) {
        }

        pexSoap = service.getRedCobroPort();
        BindingProvider provider = (BindingProvider) pexSoap;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        provider.getRequestContext().put("com.sun.xml.ws.connect.timeout", connTo * 1000);
        provider.getRequestContext().put("com.sun.xml.ws.request.timeout", readTo * 1000);

        return pexSoap;
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");
            String opcion = request.getParameter("op");
            Long idRed = (request.getParameter(CONTROL_SERVICIOS.ID_RED) != null && !request.getParameter(CONTROL_SERVICIOS.ID_RED).isEmpty()) ? new Long(request.getParameter(CONTROL_SERVICIOS.ID_RED)) : null;
            Long idTransaccional = (request.getParameter(CONTROL_SERVICIOS.ID_TRANSACCION) != null && !request.getParameter(CONTROL_SERVICIOS.ID_TRANSACCION).isEmpty()) ? new Long(request.getParameter(CONTROL_SERVICIOS.ID_TRANSACCION)) : null;
            Long idGestion = (request.getParameter(CONTROL_SERVICIOS.ID_GESTION) != null && !request.getParameter(CONTROL_SERVICIOS.ID_GESTION).isEmpty()) ? new Long(request.getParameter(CONTROL_SERVICIOS.ID_GESTION)) : null;
            Long idRecaudador = (request.getParameter(CONTROL_SERVICIOS.ID_RECAUDADOR) != null && !request.getParameter(CONTROL_SERVICIOS.ID_RECAUDADOR).isEmpty()) ? new Long(request.getParameter(CONTROL_SERVICIOS.ID_RECAUDADOR)) : null;
            Long idSucursal = (request.getParameter(CONTROL_SERVICIOS.ID_SUCURSAL) != null && !request.getParameter(CONTROL_SERVICIOS.ID_SUCURSAL).isEmpty()) ? new Long(request.getParameter(CONTROL_SERVICIOS.ID_SUCURSAL)) : null;
            Long idTerminal = (request.getParameter(CONTROL_SERVICIOS.ID_TERMINAL) != null && !request.getParameter(CONTROL_SERVICIOS.ID_TERMINAL).isEmpty()) ? new Long(request.getParameter(CONTROL_SERVICIOS.ID_TERMINAL)) : null;
            Date fechaDesde = (request.getParameter(CONTROL_SERVICIOS.FECHA_DESDE) != null && !request.getParameter(CONTROL_SERVICIOS.FECHA_DESDE).isEmpty()) ? spdf.parse(request.getParameter(CONTROL_SERVICIOS.FECHA_DESDE)) : null;
            Date fechaHasta = (request.getParameter(CONTROL_SERVICIOS.FECHA_HASTA) != null && !request.getParameter(CONTROL_SERVICIOS.FECHA_HASTA).isEmpty()) ? spdf.parse(request.getParameter(CONTROL_SERVICIOS.FECHA_HASTA)) : null;
            String anulado = (request.getParameter(CONTROL_SERVICIOS.ANULADO) != null && !request.getParameter(CONTROL_SERVICIOS.ANULADO).isEmpty()) ? request.getParameter(CONTROL_SERVICIOS.ANULADO) : null;
            String motivo = (request.getParameter(CONTROL_SERVICIOS.MOTIVO) != null && !request.getParameter(CONTROL_SERVICIOS.MOTIVO).isEmpty()) ? request.getParameter(CONTROL_SERVICIOS.MOTIVO) : null;
            Boolean filtroEfectivo = (request.getParameter(CONTROL_SERVICIOS.FILTRO_EFECTIVO) != null && !request.getParameter(CONTROL_SERVICIOS.FILTRO_EFECTIVO).isEmpty() ? true : false);
            Boolean filtroCheque = (request.getParameter(CONTROL_SERVICIOS.FILTRO_CHEQUE) != null && !request.getParameter(CONTROL_SERVICIOS.FILTRO_CHEQUE).isEmpty() ? true : false);

            //  HttpSession session = request.getSession();

            JSONObject jsonRespuesta = new JSONObject();

            if (opcion.equalsIgnoreCase("LISTAR")) {

                List<TransaccionRc> lista;
                Integer total;

                if (request.getParameter("start") != null && request.getParameter("limit") != null) {
                    Integer primerResultado = new Integer(request.getParameter("start"));
                    Integer cantResultados = new Integer(request.getParameter("limit"));
                    boolean isIdTrx = false;
                    String estadoTrx = "";
                    if (idTransaccional != null) {
                        isIdTrx = true;
                        TransaccionRc entity = new TransaccionRc();
                        entity.setIdTransaccion(new BigDecimal(idTransaccional));
                        lista = transaccionRcFacade.list(entity);
                        total = lista.size();

                        if (lista.get(0).getIdEstadoTransaccion().getIdEstadoTransaccion() == 22L) {
                            if (lista.get(0).getAnulado().equalsIgnoreCase("N")) {
                                estadoTrx = "N";
                            } else {
                                estadoTrx = "S";
                            }
                        } else {
                            estadoTrx = "NO_APROBADO";
                        }
                    } else {
                        lista = transaccionRcFacade.getControlServicios(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, fechaDesde, fechaHasta, primerResultado, cantResultados);
                        total = transaccionRcFacade.getTotalControlServicio(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, fechaDesde, fechaHasta);
                    }
                    JSONArray json = new JSONArray();

                    for (TransaccionRc e : lista) {
                        JSONObject jsonTransaccionRc = new JSONObject();
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.ID_TRANSACCION, e.getIdTransaccion());
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.CODIGO_TERMINAL, e.getIdGestion().getTerminal().getDescripcion());
                        //jsonTransaccionRc.put(CONTROL_SERVICIOS.DESCRIPCION_GESTION, e.getIdGestion().getNroGestion());
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.SERVICIO_DESCRIPCION, e.getIdServicio().getDescripcion());
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.NRO_BOLETA, e.getNroBoleta());
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.FORMA_PAGO, e.getIdTipoPago().getDescripcion());
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.FECHA_COBRO, (e.getFechaIngreso() != null) ? spdf.format(e.getFechaIngreso()) : "");
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.REFERENCIA_PAGO, e.getReferenciaPago());
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.ANULADO, isIdTrx ? estadoTrx : e.getAnulado());
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.MONTO_TOTAL, e.getMonto());
                        jsonTransaccionRc.put(CONTROL_SERVICIOS.USUARIO, e.getIdGestion().getUsuario().getNombreUsuario());

                        json.add(jsonTransaccionRc);
                    }
                    jsonRespuesta.put("TRANSACCIONES", json);
                    jsonRespuesta.put("TOTAL", total);
                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());

                } else {
                    jsonRespuesta.put("success", false);
                    out.println(jsonRespuesta.toString());
                }

            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                RuteoServicio ruteoRC = ruteoServicioFacade.get(2L);
                RedCobro rcService = getWSManager(ruteoRC.getUrlHost(), "http://ws.documenta.com.py/", "RedCobroService", ruteoRC.getConexionTo(), ruteoRC.getReadTo());

                TransaccionRc transRc = transaccionRcFacade.get(new BigDecimal(idTransaccional));

                if (anulado != null) {
                    if (transRc.getIdTipoOperacion().getIdTipoOperacion() == 1
                            && !anulado.equalsIgnoreCase("N")
                            && transRc.getIdEstadoTransaccion().getIdEstadoTransaccion() == 22L) {

                        try {
                            HttpSession session = request.getSession();
                            Long idUsuario = (Long) session.getAttribute("idUsuario");

                            Autenticacion auth = new Autenticacion();
                            auth.setIdGestion(new BigDecimal(1));
                            auth.setIdUsuario(transRc.getIdGestion().getUsuario().getIdUsuario().toString());


                            String url = rcService.anulacion(idTransaccional, "ANULACION DESDE GESTION[" + idUsuario.toString() + "]", auth);

                            String[] urlNexT = url.split(".jsp");
                            if (urlNexT[0].equalsIgnoreCase("error")) {
                                String mensajeID = Utiles.getElementValue(Utiles.getElementValue(Utiles.getElementValue(url, "[?]", 1), "&", 0), "=", 1);
                                String mensajeError = rcService.getMensajeGenerico(Long.parseLong(mensajeID), auth);
                                jsonRespuesta.put("motivo", mensajeError);
                                jsonRespuesta.put("success", false);
                            } else {
                                try {
                                    RegistroImpWeb log = new RegistroImpWeb();
                                    log.setDescripcion("ANULACION TRX["+idTransaccional+"]");
                                    log.setMotivo(motivo);
                                    log.setUsuario(idUsuario.toString());
                                    log.setEvento("ANULACION");
                                    log.setFecha(new Date());
                                    registroImpWebFacade.save(log);
                                } catch (Exception ex) {
                                    Logger.getLogger(CONTROL_SERVICIOS.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                jsonRespuesta.put("success", true);
                                jsonRespuesta.put("motivo", "Anulación Exitosa");
                            }

                        } catch (Exception ex) {
                            Logger.getLogger(CONTROL_SERVICIOS.class.getName()).log(Level.SEVERE, null, ex);
                            jsonRespuesta.put("motivo", "No se pudo anular la transacción");
                            jsonRespuesta.put("success", false);
                        }
                    } else {
                        jsonRespuesta.put("success", false);
                        jsonRespuesta.put("motivo", "Transacción Inválida para Anulación");
                    }
                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se envio estado de Anulación");
                }
                out.println(jsonRespuesta.toString());
            }
        } finally {
            //out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CONTROL_SERVICIOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CONTROL_SERVICIOS.class.getName()).log(Level.SEVERE, null, ex);
        }
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
