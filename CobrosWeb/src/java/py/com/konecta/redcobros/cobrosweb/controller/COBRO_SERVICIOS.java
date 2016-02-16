/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.netbeans.xml.schema.common.EstadoTransaccion;
import org.netbeans.xml.schema.common.ElementoMoneda;
import org.netbeans.xml.schema.common.RespuestaCobranza;
import org.netbeans.xml.schema.common.RespuestaConsultaCodigoBarras;
import py.com.documenta.onlinemanager.ws.HashMapContainer.Map.Entry;
import py.com.documenta.onlinemanager.ws.MapValueContainer;
import py.com.documenta.onlinemanager.ws.OlDetalleReferencia;
import py.com.documenta.onlinemanager.ws.OlResponseConsulta;
import py.com.documenta.onlinemanager.ws.Telefonia;
import py.com.documenta.ws.Exception_Exception;
import py.com.documenta.ws.RedCobroService;
import py.com.documenta.ws.RedCobro;
import py.com.documenta.ws.Autenticacion;
import py.com.documenta.ws.DefinicionFacturador;
import py.com.documenta.ws.FormaPago;
import py.com.documenta.ws.DefinicionServicio;
import py.com.documenta.ws.HashMapContainer;
import py.com.documenta.ws.PropiedadServicio;
import py.com.konecta.redcobros.cobrosweb.utiles.DateUtils;

import py.com.konecta.redcobros.utiles.UtilesSet;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;
import py.com.konecta.redcobros.ejb.EntidadFinancieraFacade;
import py.com.konecta.redcobros.ejb.FacturadorFacade;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.TransaccionRc;
import py.com.konecta.redcobros.entities.UsuarioTerminal;
import py.com.konecta.redcobros.entities.ParametroSistema;
import py.com.konecta.redcobros.ejb.GestionFacade;
import py.com.konecta.redcobros.ejb.MonedaFacade;
import py.com.konecta.redcobros.ejb.TransaccionRcFacade;
import py.com.konecta.redcobros.ejb.UsuarioFacade;
import py.com.konecta.redcobros.ejb.ParametroSistemaFacade;
import py.com.konecta.redcobros.ejb.RetenionesCargillFacade;
import py.com.konecta.redcobros.ejb.RuteoServicioFacade;
import py.com.konecta.redcobros.ejb.ServicioRcFacade;
import py.com.konecta.redcobros.ejb.SucursalFacade;
import py.com.konecta.redcobros.entities.*;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;
import py.com.konecta.redcobros.utiles.StringUtils;

/**
 *
 * @author konecta
 */
public class COBRO_SERVICIOS extends HttpServlet {

    private static final Object bloqueo = new Object();
    @EJB
    private GestionFacade gestionFacade;
    @EJB
    private TransaccionRcFacade transaccionRcFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    @EJB
    private ServicioRcFacade servicioRcFacade;
    @EJB
    private FacturadorFacade facturadorFacade;
    @EJB
    private EntidadFinancieraFacade entidadFinancieraFacade;
    @EJB
    private RuteoServicioFacade ruteoServicioFacade;
    @EJB
    private RetenionesCargillFacade retCarFacade;
    @EJB
    private MonedaFacade monedaFacade;
    @EJB
    private SucursalFacade sucursalFacade;
    public static String ID_ENTIDAD = "ID_ENTIDAD";
    public static String TIPO_DE_PAGO = "TIPO_DE_PAGO";
    public static String NRO_DE_CHEQUE = "NRO_DE_CHEQUE";
    public static String ID_TRANSACCION = "ID_TRANSACCION";
    public static String CODIGO_DE_BARRA = "CODIGO_DE_BARRA";
    public static String SERVICIO = "SERVICIO";
    public static String NRO_REFERENCIA = "NRO_REFERENCIA";
    public static String NRO_DE_PAGO = "NRO_DE_PAGO";
    public static String PROPIEDAD_SERVICIO = "PROPIEDAD_SERVICIO";
    public static String MONTO = "MONTO_CARGAR";
    public static String NRO_TELEFONO = "NRO_TELEFONO";
    public static String CON_TARJETA = "CON_TARJETA";
    public static String MULTIPLE = "MULTIPLE";
    public static String MOTIVO_ANULACION = "motivoAnulacion";
    public static String ID_FACTURADOR = "ID_FACTURADOR";
    public static String NOMBRE = "NOMBRE";
    public static String NRO_RETENCION = "NRO_RETENCION";
    public static String TIPO_SERVICIO = "TIPO_SERVICIO";
    public static String DEP_EXT = "DEP_EXT";
    public static String FECHA_CHEQUE = "FECHA_CHEQUE";
    public static String MONEDA = "MONEDA";
    public static String PG = "PG";
    private static String TIPO_DOC = "TIPO_DOC";
    private static String ID_DOCUMENTO = "ID_DOCUMENTO";
    private static String NOMBRES = "NOMBRES";
    private static String APELLIDOS = "APELLIDOS";
    private static String CIUDAD = "CIUDAD";
    private static String TELEFONO = "TELEFONO";
    private static String MOVIL = "MOVIL";
    private static String COD_CLIENTE = "COD_CLIENTE";
    private static String COD_REMESA = "COD_REMESA";
    private static String NRO_CUENTA = "NRO_CUENTA";
    private static String ENTIDAD_FINAN = "ENTIDAD_FINAN";
    private static String MONTO_BASE = "MONTO_BASE";
    private static String COMISION = "COMISION";
    private static String PREG_SEG = "PREG_SEG";
    private static String RESP_SEG = "RESP_SEG";
    private static String TABLA = "TABLA";
    private static String ID_TABLA = "ID_TABLA";
    private static String MORE = "MORE";
    private static String CARGILL = "CARGILL";
    private static String DESCRIPCION = "DESCRIPCION";

    static final Logger logger = Logger.getLogger(COBRO_SERVICIOS.class.getName());

    private void writeOnUserFile(String pathFile, Long codExtUsuario, String idTrx, Boolean indicador) {
        try {
            TransaccionRc transRc = transaccionRcFacade.get(new BigDecimal(idTrx));
            Utiles.writeToFile(pathFile, codExtUsuario, transRc, indicador);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    private static RedCobroService service = null;
    private static final ReentrantLock lock = new ReentrantLock();

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

    private List<Integer> getDetalleIndex(String tokens[], int servicio) {
        List<Integer> listDetail = new ArrayList<Integer>();
        int src, seleccion;
        for (int i = 1; i < tokens.length; i++) {
            src = Integer.parseInt(tokens[i].substring(0, 1)) - 1;
            seleccion = Integer.parseInt(tokens[i].substring(1, tokens[i].indexOf("#")));
            if (servicio == src) {
                listDetail.add(seleccion);
            }
        }
        return listDetail;
    }

    private List<Integer> getServiceIndex(String tokens[]) {
        List<Integer> listService = new ArrayList<Integer>();
        int servicio;
        for (int i = 1; i < tokens.length; i++) {
            servicio = Integer.parseInt(tokens[i].substring(0, tokens[i].indexOf("#")).substring(0, 1)) - 1;
            if (!listService.contains(servicio)) {
                listService.add(servicio);
            }

        }
        return listService;
    }

    private boolean isDuplicatedTrx(HttpSession session) {
        synchronized (COBRO_SERVICIOS.bloqueo) {
            Long timeLastTrx = (Long) session.getAttribute("timeLastTrx");
            if (timeLastTrx == null) {
                timeLastTrx = new Date().getTime() / 1000;
                session.setAttribute("timeLastTrx", timeLastTrx);
                logger.log(Level.INFO, "TimeLastTrx:[{0}]", timeLastTrx);
                return false;
            } else {
                Long timeNewTrx = new Date().getTime() / 1000;
                logger.log(Level.INFO, "TimeNewTrx:[{0}]-TimeLastTrx:[{1}]", new Object[]{timeNewTrx, timeLastTrx});
                if (timeNewTrx - timeLastTrx > 5) {
                    session.setAttribute("timeLastTrx", timeNewTrx);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private OlResponseConsulta responseToProcess(OlResponseConsulta respConsulta, String montoRecibido, String tokens[], String serverName) {
        OlResponseConsulta respProcesar = new OlResponseConsulta();

        respProcesar.setCambioMontoPermitido(respConsulta.isCambioMontoPermitido());
        respProcesar.setCodRetorno(respConsulta.getCodRetorno());
        respProcesar.setMensajeOperacion(respConsulta.getMensajeOperacion());
        respProcesar.setReferenciaConsulta(respConsulta.getReferenciaConsulta());

        int servicioIndex = 0;
        List<OlDetalleReferencia> listSelected = new ArrayList<OlDetalleReferencia>();
        int indexSeleccionado = 0;
        for (Integer src : getServiceIndex(tokens)) {
            for (Integer det : getDetalleIndex(tokens, src)) {
                OlDetalleReferencia selected = respConsulta.getDetalleServicios().get(src).getDetalleReferencias().get(det);

                String moneda[] = tokens[indexSeleccionado + 1].split("#");
                indexSeleccionado++;
                selected.setIdMoneda(Integer.parseInt(moneda[1]));

                py.com.documenta.onlinemanager.ws.HashMapContainer.Map.Entry e = new py.com.documenta.onlinemanager.ws.HashMapContainer.Map.Entry();
                MapValueContainer mapVC = new MapValueContainer();
                mapVC.setValue(serverName);
                e.setKey("SERVER_APP");
                e.setValue(mapVC);

                if (selected.getHashMapContainer() == null) {
                    selected.setHashMapContainer(new py.com.documenta.onlinemanager.ws.HashMapContainer());
                    selected.getHashMapContainer().setMap(new py.com.documenta.onlinemanager.ws.HashMapContainer.Map());
                    selected.getHashMapContainer().getMap().getEntry().add(e);
                }
                selected.getHashMapContainer().getMap().getEntry().add(e);

                if (Integer.parseInt(moneda[1]) == 600
                        && Integer.parseInt(moneda[2]) > 0) {
                    selected.setTasa(Integer.parseInt(moneda[2]));
                    selected.setMonto(selected.getMonto().multiply(new BigDecimal(moneda[2])));

                }
                if (respConsulta.isCambioMontoPermitido() && montoRecibido != null) {//cuando permite cambiar monto
                    selected.setMonto(new BigDecimal(montoRecibido));
                }
                listSelected.add(selected);
            }
            respProcesar.getDetalleServicios().add(respConsulta.getDetalleServicios().get(src));
            respProcesar.getDetalleServicios().get(servicioIndex).getDetalleReferencias().clear();
            respProcesar.getDetalleServicios().get(servicioIndex).getDetalleReferencias().addAll(listSelected);
            listSelected.clear();
            servicioIndex++;

        }

        return respProcesar;
    }

    private String removeNonNumericCharMultiple(String barCode) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; barCode.length() > i; i++) {
            if (String.valueOf(barCode.charAt(i)).matches("[0-9]")) {
                strBuilder.append(barCode.charAt(i));
            }
        }
        return strBuilder.toString();
    }

    private String removeNonNumericChar(String barCode) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; barCode.length() > i; i++) {
            if (String.valueOf(barCode.charAt(i)).matches("[A-Za-z0-9]") || String.valueOf(barCode.charAt(i)).matches("[ ;#.-]")) {//barCode.charAt(i) == ' ' || barCode.charAt(i) == ';' || barCode.charAt(i) == '#' || barCode.charAt(i) == '-') {
                strBuilder.append(barCode.charAt(i));
            }
        }
        return strBuilder.toString();
    }

    private void resolveServiceMultiple(RedCobro rcService, String codigosDeBarra, Autenticacion auth, PrintWriter out) {
        try {
            JSONObject jsonRespuesta = new JSONObject();

            JSONArray jsonFacturas = new JSONArray();
            String listCodigoDeBarras[] = codigosDeBarra.replaceFirst("#", "").split("#");
            for (String it : listCodigoDeBarras) {
                JSONObject jsonItem = new JSONObject();
                RespuestaConsultaCodigoBarras respuestaCB = rcService.validarCodigoBarras(it, auth);
                String mensajeID = String.valueOf(respuestaCB.getEstado().getIdLogTransaccion());

                int retorno = respuestaCB.getEstado().getCodigoRetorno();
                if (retorno == 0) {
                    ServicioRc servicio = servicioRcFacade.get(respuestaCB.getIdServicio());
                    jsonItem.put("SERVICIO", servicio.getDescripcion());//MENSAJE = Referencia: 1423185000 Monto: 145,860 Gs
                    jsonItem.put("ID_SERVICIO", servicio.getIdServicio());
                    jsonItem.put("NRO_REFERENCIA", respuestaCB.getReferenciaPago());
                    jsonItem.put("MONTO", respuestaCB.getMonto());
                    jsonItem.put("DESCRIPCION", "Código de barras correcto");
                    jsonItem.put("CORRECTO", "S");
                    jsonItem.put("FORMA_PAGO", "EFECTIVO");

                } else {
                    jsonItem.put("ID_SERVICIO", "-");
                    jsonItem.put("SERVICIO", "-");
                    jsonItem.put("NRO_REFERENCIA", "-");
                    jsonItem.put("MONTO", "-");
                    jsonItem.put("DESCRIPCION", respuestaCB.getEstado().getDescripcion());
                    jsonItem.put("CORRECTO", "N");
                }
                jsonItem.put("CODIGO_DE_BARRA", it);
                jsonItem.put("ID_TRANSACCION", mensajeID);
                jsonFacturas.add(jsonItem);
            }
            jsonRespuesta.put("FACTURAS", jsonFacturas);
            jsonRespuesta.put("TOTAL", listCodigoDeBarras.length);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } catch (Exception_Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void procesarServicioMultiple(PrintWriter out, RedCobro rcService, String ids, String idsServicios, String montos, String formaPago, String entidad, String nroCheque, Autenticacion auth, String pathFile, Long codExtUsuario, String server) {

        String listIds[] = ids.replaceFirst("#", "").split("#");
        String listIdServicios[] = idsServicios.replaceFirst("#", "").split("#");
        String listFormaPago[] = formaPago.replaceFirst("#", "").split("#");
        String listIdEntidad[] = entidad.replaceFirst("#", "").split("#");
        String listNroCheque[] = nroCheque.replaceFirst("#", "").split("#");
        String listMonto[] = montos.replaceFirst("#", "").replaceAll(",", "").split("#");
        String cadenaImpresionTicket = "";
        String ticketPantalla = "";
        String ticket;

        RespuestaConsultaCodigoBarras respuestaCB;
        EstadoTransaccion estado;

        JSONObject jsonRespuesta = new JSONObject();
        JSONArray jsonFacturas = new JSONArray();

        try {
            for (int i = 0; i < listIds.length; i++) {
                JSONObject jsonItem = new JSONObject();
                FormaPago tipoDePago = new FormaPago();
                if (listFormaPago[i].equals("EFECTIVO")) {//efectivo
                    tipoDePago.setTipoPago(1);
                } else {
                    tipoDePago.setTipoPago(2);
                    if (listIdEntidad[i] != null && !listIdEntidad[i].isEmpty()) {
                        if (listFormaPago[i].equals("CHEQUE_OTROS")) {
                            tipoDePago.setIdBanco(new BigDecimal(listIdEntidad[i]));
                        } else {// CHEQUE LOCAL
                            tipoDePago.setIdBanco(new BigDecimal("3"));//Seteamos que el banco es Continental
                        }
                    }
                    if (listNroCheque[i] != null && !listNroCheque[i].isEmpty()) {
                        tipoDePago.setNroCheque(listNroCheque[i]);
                    }
                }

                respuestaCB = new RespuestaConsultaCodigoBarras();
                estado = new EstadoTransaccion();
                estado.setIdLogTransaccion(Long.valueOf(listIds[i]));
                respuestaCB.setEstado(estado);
                respuestaCB.setIdServicio(Integer.valueOf(listIdServicios[i]));

                ElementoMoneda monedaPago = new ElementoMoneda();
                monedaPago.setIdMoneda(600);
                monedaPago.setTasa(1);
                respuestaCB.setMonedas(new RespuestaConsultaCodigoBarras.Monedas());
                respuestaCB.getMonedas().getMoneda().add(monedaPago);
                respuestaCB.setMonto(new BigDecimal(listMonto[i]));

                HashMapContainer container = new HashMapContainer();
                HashMapContainer.Map.Entry serverName = new HashMapContainer.Map.Entry();
                serverName.setKey("SERVER_APP");
                serverName.setValue(server);

                container.setMap(new HashMapContainer.Map());
                container.getMap().getEntry().add(serverName);

                RespuestaCobranza respCobranza = rcService.pagoCodigoBarras(respuestaCB, auth, tipoDePago, container);

                int retorno = respCobranza.getEstado().getCodigoRetorno();
                if (retorno != 0) {
                    jsonItem.put("ID_TRANSACCION", "-");
                    jsonItem.put("TICKET_PRINTER", "-");
                    jsonItem.put("TICKET_SCREEN", "-");
                    jsonItem.put("DESCRIPCION", respCobranza.getEstado().getDescripcion());
                    jsonItem.put("CORRECTO", "N");
                } else {

                    writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
                    ticket = respCobranza.getTicket();
                    String mensajeImpresora = ticket.replaceAll("\n", ";;;");
                    String[] ticketImpresora = mensajeImpresora.split(";;;");
                    String cadenaImpresion = "";
                    ticket = ticket.replaceAll("\n", "<br>");

                    for (String ooo : ticketImpresora) {
                        cadenaImpresion += 1 + ";;" + "N" + ";;" + ooo + ";;;";
                    }
                    cadenaImpresionTicket += cadenaImpresion;
                    ticketPantalla += ticket + "</br>";

                    jsonItem.put("ID_TRANSACCION", respCobranza.getIdTransaccion());
                    jsonItem.put("TICKET_PRINTER", cadenaImpresionTicket);
                    jsonItem.put("TICKET_SCREEN", ticketPantalla);
                    jsonItem.put("DESCRIPCION", "Pago realizado");
                    jsonItem.put("CORRECTO", "S");
                }
                jsonFacturas.add(jsonItem);
                cadenaImpresionTicket = "";
                ticketPantalla = "";

//                if (urlNexT[0].equalsIgnoreCase("error")) {
//                    mensajeID = Utiles.getElementValue(Utiles.getElementValue(Utiles.getElementValue(urlPS, "[?]", 1), "&", 0), "=", 1);
//                    mensajeError = rcService.getMensajeGenerico(Long.parseLong(mensajeID), auth);
//                    request.setAttribute("mensajeError", mensajeError);
//                } else {
//                    isOk = true;
//                    mensajeID = Utiles.getElementValue(Utiles.getElementValue(Utiles.getElementValue(urlPS, "[?]", 1), "&", 1), "=", 1);
//                    writeOnUserFile(pathFile, codExtUsuario, mensajeID, false);
//                    ticket = rcService.getTicket(Long.parseLong(mensajeID), auth);
//                    String mensajeImpresora = ticket.replaceAll("\n", ";;;");
//                    String[] ticketImpresora = mensajeImpresora.split(";;;");
//                    String cadenaImpresion = "";
//                    ticket = ticket.replaceAll("\n", "<br>");
//
//                    for (String ooo : ticketImpresora) {
//                        cadenaImpresion += 1 + ";;" + "N" + ";;" + ooo + ";;;";
//                    }
//                    cadenaImpresionTicket += cadenaImpresion;
//                    ticketPantalla += ticket + "</br>";
//                }
            }
            jsonRespuesta.put("FACTURAS", jsonFacturas);
            jsonRespuesta.put("TOTAL", listIds.length);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
//            if (isOk) {
//                urlPS = "ticket.jsp";
//                request.removeAttribute("ticketImpresora");
//                request.setAttribute("ticketImpresora", cadenaImpresionTicket);
//                request.removeAttribute("mensaje");
//                request.setAttribute("mensaje", ticketPantalla);
//                request.removeAttribute("servicio");
//
//            } else {
//                urlPS = "error.jsp";
//                request.removeAttribute("mensajeError");
//                request.setAttribute("mensajeError", mensajeError);
//            }
//            rd = request.getRequestDispatcher(urlPS);
//            rd.forward(request, response);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
//            request.setAttribute("mensajeError", "No se pudo completar la operación.");
//            request.getRequestDispatcher("error.jsp");
//            try {
////                rd.forward(request, response);
//            } catch (ServletException ex1) {
//                logger.log(Level.SEVERE, null, ex1);
//            } catch (IOException ex1) {
//                logger.log(Level.SEVERE, null, ex1);
//            }
        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Integer modo = 1;
        try {
            HttpSession session = request.getSession();
            String server = request.getRequestURL().toString();
            String opcion = request.getParameter("op");
            Boolean depExt = request.getParameter(COBRO_SERVICIOS.DEP_EXT) != null && !request.getParameter(COBRO_SERVICIOS.DEP_EXT).isEmpty() ? true : false;
            Boolean isPG = request.getParameter(COBRO_SERVICIOS.PG) != null && !request.getParameter(COBRO_SERVICIOS.PG).isEmpty() ? true : false;
            Boolean isMORE = request.getParameter(COBRO_SERVICIOS.MORE) != null && !request.getParameter(COBRO_SERVICIOS.MORE).isEmpty() ? true : false;
            Boolean isCargilRet = request.getParameter(COBRO_SERVICIOS.CARGILL) != null && !request.getParameter(COBRO_SERVICIOS.CARGILL).isEmpty() ? true : false;
            Boolean cobroTarjeta = request.getParameter("ct") != null && !request.getParameter("ct").isEmpty() ? true : false;
            Boolean conNroTarjeta = request.getParameter(COBRO_SERVICIOS.CON_TARJETA) != null && !request.getParameter(COBRO_SERVICIOS.CON_TARJETA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.CON_TARJETA).equalsIgnoreCase("true") ? true : false : false;
            Boolean isMultiple = request.getParameter(COBRO_SERVICIOS.MULTIPLE) != null && !request.getParameter(COBRO_SERVICIOS.MULTIPLE).isEmpty() ? true : false;
            String idEntidad = request.getParameter(COBRO_SERVICIOS.ID_ENTIDAD) != null && !request.getParameter(COBRO_SERVICIOS.ID_ENTIDAD).isEmpty() ? request.getParameter(COBRO_SERVICIOS.ID_ENTIDAD) : null;
            String tipoDePago = request.getParameter(COBRO_SERVICIOS.TIPO_DE_PAGO) != null && !request.getParameter(COBRO_SERVICIOS.TIPO_DE_PAGO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.TIPO_DE_PAGO) : null;
            String nroDeCheque = request.getParameter(COBRO_SERVICIOS.NRO_DE_CHEQUE) != null && !request.getParameter(COBRO_SERVICIOS.NRO_DE_CHEQUE).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NRO_DE_CHEQUE) : null;
            String idTransaccional = request.getParameter(COBRO_SERVICIOS.ID_TRANSACCION) != null && !request.getParameter(COBRO_SERVICIOS.ID_TRANSACCION).isEmpty() ? request.getParameter(COBRO_SERVICIOS.ID_TRANSACCION) : null;
            String codigoDeBarra = request.getParameter(COBRO_SERVICIOS.CODIGO_DE_BARRA) != null && !request.getParameter(COBRO_SERVICIOS.CODIGO_DE_BARRA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.CODIGO_DE_BARRA) : null;
            String idServicio = request.getParameter(COBRO_SERVICIOS.SERVICIO) != null && !request.getParameter(COBRO_SERVICIOS.SERVICIO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.SERVICIO) : null;
            String nroReferenciaPago = request.getParameter(COBRO_SERVICIOS.NRO_REFERENCIA) != null && !request.getParameter(COBRO_SERVICIOS.NRO_REFERENCIA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NRO_REFERENCIA) : null;
            String motivoAnulacion = request.getParameter(COBRO_SERVICIOS.MOTIVO_ANULACION) != null && !request.getParameter(COBRO_SERVICIOS.MOTIVO_ANULACION).isEmpty() ? request.getParameter(COBRO_SERVICIOS.MOTIVO_ANULACION) : null;
            String nroDePago = request.getParameter(COBRO_SERVICIOS.NRO_DE_PAGO) != null && !request.getParameter(COBRO_SERVICIOS.NRO_DE_PAGO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NRO_DE_PAGO) : null;
            String propService = request.getParameter(COBRO_SERVICIOS.PROPIEDAD_SERVICIO) != null && !request.getParameter(COBRO_SERVICIOS.PROPIEDAD_SERVICIO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.PROPIEDAD_SERVICIO) : null;
            String montoRecibido = request.getParameter(COBRO_SERVICIOS.MONTO) != null && !request.getParameter(COBRO_SERVICIOS.MONTO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.MONTO).replace(",", "") : null;
            String nroTelefono = request.getParameter(COBRO_SERVICIOS.NRO_TELEFONO) != null && !request.getParameter(COBRO_SERVICIOS.NRO_TELEFONO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NRO_TELEFONO) : null;
            String descripcion = request.getParameter(COBRO_SERVICIOS.DESCRIPCION) != null && !request.getParameter(COBRO_SERVICIOS.DESCRIPCION).isEmpty() ? request.getParameter(COBRO_SERVICIOS.DESCRIPCION) : null;
            if (!(depExt || isMORE) && nroTelefono != null) {
                nroTelefono = removeNonNumericCharMultiple(nroTelefono);
            }

            String idFacturador = request.getParameter(COBRO_SERVICIOS.ID_FACTURADOR) != null && !request.getParameter(COBRO_SERVICIOS.ID_FACTURADOR).isEmpty() ? request.getParameter(COBRO_SERVICIOS.ID_FACTURADOR) : null;
            String nombre = request.getParameter(COBRO_SERVICIOS.NOMBRE) != null && !request.getParameter(COBRO_SERVICIOS.NOMBRE).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NOMBRE) : null;
            String tipoServicio = request.getParameter(COBRO_SERVICIOS.TIPO_SERVICIO) != null && !request.getParameter(COBRO_SERVICIOS.TIPO_SERVICIO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.TIPO_SERVICIO) : null;
            String fechaCheque = request.getParameter(COBRO_SERVICIOS.FECHA_CHEQUE) != null && !request.getParameter(COBRO_SERVICIOS.FECHA_CHEQUE).isEmpty() ? request.getParameter(COBRO_SERVICIOS.FECHA_CHEQUE) : null;
            String comision = request.getParameter(COBRO_SERVICIOS.COMISION) != null && !request.getParameter(COBRO_SERVICIOS.COMISION).isEmpty() ? request.getParameter(COBRO_SERVICIOS.COMISION).replaceAll(",", "") : null;
            String[] datosMoneda = request.getParameter(COBRO_SERVICIOS.MONEDA) != null && !request.getParameter(COBRO_SERVICIOS.MONEDA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.MONEDA).split("#") : null;
            if (nombre != null) {
                session.setAttribute("nombreTicket", nombre);
            }

            Long codExtUsuario = (Long) session.getAttribute("codExtUsuario");
            String conciliar = (String) session.getAttribute("conciliarCaja");
            String pathFile = "";
            RuteoServicio ruteoRC = (RuteoServicio) session.getAttribute("ruteoServicio");
            try {
                if (conciliar != null && conciliar.equalsIgnoreCase("S")) {
                    ParametroSistema paramFile = new ParametroSistema();
                    paramFile.setNombreParametro("pathFileUser");
                    pathFile = parametroSistemaFacade.get(paramFile).getValor();
                } else {
                    codExtUsuario = null;//para que no escriba en archivo
                }
                if (ruteoRC == null) {
                    ruteoRC = ruteoServicioFacade.get(2L);
                    session.setAttribute("ruteoServicio", ruteoRC);
                }

                logger.log(Level.INFO, "URL:{0}", ruteoRC.getUrlHost());
            } catch (Exception e) {
                logger.log(Level.SEVERE, null, e);
            }

            RedCobro rcService = getWSManager(ruteoRC.getUrlHost(), "http://ws.documenta.com.py/", "RedCobroService", ruteoRC.getConexionTo(), ruteoRC.getReadTo());

            JSONObject jsonRespuesta = new JSONObject();

            Gestion gestion = (Gestion) session.getAttribute("gestion");
            if (gestion == null) {
                Map<String, HashMap<String, String>> tasks = new HashMap<String, HashMap<String, String>>();
                tasks = (Map<String, HashMap<String, String>>) session.getAttribute("TASKS");
                UsuarioTerminal ut = (UsuarioTerminal) session.getAttribute("objUsuarioTerminal");
                gestion = gestionFacade.obtenerGestionDelDia(ut.getIdUsuarioTerminal(), tasks.get("OPERACIONES").get("APERTURA_SIMPLE") != null);
                session.setAttribute("gestion", gestion);
            }
            Autenticacion auth = new Autenticacion();

            auth.setClave(session.getAttribute("contrasenha").toString());
            auth.setHash(UtilesSet.getSha1(session.getAttribute("contrasenha").toString()));
            auth.setIdGestion(new BigDecimal(gestion.getIdGestion()));
            auth.setIdUsuario(session.getAttribute("idUsuario").toString());
            String backTO = request.getParameter("BACK_TO") != null && !request.getParameter("BACK_TO").isEmpty() ? request.getParameter("BACK_TO") : "CB";

            if (codigoDeBarra != null && !codigoDeBarra.isEmpty() && !isMultiple) {
                codigoDeBarra = this.removeNonNumericChar(codigoDeBarra);
//                if (!codigoDeBarra.substring(0, 1).matches("[0-9]")) {
//                    codigoDeBarra = codigoDeBarra.substring(1);
//                }
//                if (!codigoDeBarra.substring(codigoDeBarra.length() - 1).matches("[0-9]")) {
//                    codigoDeBarra = codigoDeBarra.substring(0, codigoDeBarra.length() - 1);
//                }
            }
            if (cobroTarjeta) {
                backTO = "PAGO_TARJETA";
                if (opcion.equalsIgnoreCase("RESOLVER_SERVICIO")) {
                    if (conNroTarjeta) {//CON NRO TARJETA
                        codigoDeBarra = "000" + codigoDeBarra + "00000000" + UtilesSet.cerosIzquierda(new Long(montoRecibido), 10);
                    } else {//CON CB
                        codigoDeBarra = codigoDeBarra.concat(UtilesSet.cerosIzquierda(new Long(montoRecibido), 10));
                        if (codigoDeBarra.length() == 38) {
                            codigoDeBarra = codigoDeBarra.substring(1);
                        }
                    }
                }

            }

            request.setAttribute("backTO", backTO);
            if (opcion.equalsIgnoreCase("RESOLVER_SERVICIO")) {
                RequestDispatcher rd;
                String urlRS;
                if (!isMultiple) {
                    if (codigoDeBarra != null) {
                        RespuestaConsultaCodigoBarras respuesta = rcService.validarCodigoBarras(codigoDeBarra, auth);
                        int retorno = respuesta.getEstado().getCodigoRetorno();
                        if (retorno == 0) {
                            urlRS = "confirmacion.jsp";
                            session.setAttribute("respuestaCB", respuesta);
                        } else {
                            urlRS = "error.jsp";
                            session.setAttribute("mensajeError", respuesta.getEstado().getDescripcion());
                        }
                    } else {
                        session.removeAttribute("mensajeError");
                        session.setAttribute("mensajeError", "Codigo de barras invalido");
                        urlRS = "error.jsp";
                    }
                    rd = request.getRequestDispatcher(urlRS);
                    rd.forward(request, response);
                } else {
                    this.resolveServiceMultiple(rcService, codigoDeBarra, auth, out);
                }

            } else if (opcion.equalsIgnoreCase("PROCESAR_SERVICIO")) {

                session.setAttribute("panel", 4);

                RequestDispatcher rd;
                String urlPS;
                if (!isMultiple) {
                    if (!isDuplicatedTrx(session)) {
                        if (tipoDePago != null) {

                            FormaPago formaDePago = new FormaPago();
                            if (tipoDePago.equals("1") || tipoDePago.equals("2")) {
                                if (tipoDePago.equals("1")) {//efectivo
                                    formaDePago.setTipoPago(1);
                                } else {
                                    formaDePago.setTipoPago(2);
                                    if (idEntidad != null) {
                                        formaDePago.setIdBanco(BigDecimal.valueOf(Long.parseLong(idEntidad)));
                                    }
                                    if (nroDeCheque != null) {
                                        formaDePago.setNroCheque(nroDeCheque);
                                    }
                                    if (fechaCheque != null) {
                                        formaDePago.setFechaCheque(fechaCheque);
                                    }
                                }
                                HashMapContainer container = new HashMapContainer();
                                boolean adicional = false;
                                if (session.getAttribute("nombreTicket") != null) {//Si viene el nombre para el ticket                                
                                    HashMapContainer.Map.Entry entry = new HashMapContainer.Map.Entry();
                                    container.setMap(new HashMapContainer.Map());
                                    entry.setKey("CLIENTE");
                                    entry.setValue(nombre);
                                    container.getMap().getEntry().add(entry);
                                    adicional = true;
                                }

                                HashMapContainer.Map.Entry serverName = new HashMapContainer.Map.Entry();
                                serverName.setKey("SERVER_APP");
                                serverName.setValue(server);
                                if (adicional) {
                                    container.getMap().getEntry().add(serverName);
                                } else {
                                    container.setMap(new HashMapContainer.Map());
                                    container.getMap().getEntry().add(serverName);
                                }

                                RespuestaConsultaCodigoBarras respuestaCB = (RespuestaConsultaCodigoBarras) session.getAttribute("respuestaCB");

                                Integer idMoneda = Integer.valueOf(datosMoneda[0]);
                                Integer tasa = Integer.valueOf(datosMoneda[1]);

                                ElementoMoneda monedaPago = new ElementoMoneda();
                                monedaPago.setIdMoneda(idMoneda);
                                monedaPago.setTasa(tasa);
                                respuestaCB.getMonedas().getMoneda().clear();
                                respuestaCB.getMonedas().getMoneda().add(monedaPago);

                                if (idMoneda == 600 && tasa > 0) {
                                    respuestaCB.setMonto(respuestaCB.getMonto().multiply(new BigDecimal(tasa)));
                                }

                                RespuestaCobranza respCobranza = rcService.pagoCodigoBarras(respuestaCB, auth, formaDePago, container);
                                if (respCobranza != null) {
                                    int retorno = respCobranza.getEstado().getCodigoRetorno();
                                    urlPS = retorno == 0 ? "ticketCyP.jsp" : "errorCyP.jsp";
                                    session.setAttribute("respuestaCobranza", respCobranza);
                                    if (retorno == 0) {
                                        writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
                                    }
                                    session.setAttribute("servicio", respuestaCB.getEstado().getDescripcion());
                                } else {
                                    urlPS = "error.jsp";
                                    session.setAttribute("mensajeError", "Error en el pago..Favor intentelo de vuelta en unos minutos..");
                                }
                            } else {
                                urlPS = "error.jsp";
                                session.setAttribute("mensajeError", "No existe forma de Pago");
                            }
                        } else {
                            urlPS = "error.jsp";
                            session.setAttribute("mensajeError", "No se envio forma de pago");
                        }
                    } else {
                        urlPS = "error.jsp";
                        session.setAttribute("mensajeError", "Posible duplicación, favor verificar reporte de cobranza.");
                    }
                    rd = request.getRequestDispatcher(urlPS);
                    rd.forward(request, response);
                } else {
                    this.procesarServicioMultiple(out, rcService, idTransaccional, idServicio, montoRecibido, tipoDePago, idEntidad, nroDeCheque, auth, pathFile, codExtUsuario, server);
                }

            } else if (opcion.equalsIgnoreCase("REIMPRIMIR_CS")) {
                try {
                    if (idTransaccional != null
                            && usuarioFacade.esSupervisor((Long) session.getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {

                        String ticket = rcService.reimpresion(Long.parseLong(idTransaccional), auth);
                        String ticketCopy = ticket;
                        String mensajeImpresora = ticket.replaceAll("\n", ";;;").replaceAll("###", "");
                        String[] ticketImpresora = mensajeImpresora.split(";;;");
                        String cadenaImpresionTicket = "";

                        if (ticketCopy.contains("###")) {
                            ticket = ticketCopy.substring(0, ticket.indexOf("###")) + ticketCopy.substring(ticket.lastIndexOf("###"), ticket.length());
                        }

                        ticket = ticket.replaceAll("\n", "<br>");

                        for (String ooo : ticketImpresora) {
                            cadenaImpresionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
                        }
                        jsonRespuesta.put("ticketImpresora", cadenaImpresionTicket);
                        jsonRespuesta.put("ticketPantalla", ticket);
                        jsonRespuesta.put("success", true);

                    } else {
                        jsonRespuesta.put("success", false);
                        jsonRespuesta.put("motivo", Utiles.CLAVE_INCORRECTA);
                    }
                    out.println(jsonRespuesta.toString());
                } catch (Exception e) {
                    logger.log(Level.SEVERE, null, e);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "Error al procesar número de transacción.");
                    out.println(jsonRespuesta.toString());
                }
            } else if (opcion.equalsIgnoreCase("TICKET")) {
                try {
                    if (idTransaccional != null
                            && usuarioFacade.esSupervisor((Long) session.getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {

                        String ticket = rcService.getTicket(Long.parseLong(idTransaccional), auth);
                        String ticketCopy = ticket;
                        String mensajeImpresora = ticket.replaceAll("\n", ";;;").replaceAll("###", "");
                        String[] ticketImpresora = mensajeImpresora.split(";;;");
                        String cadenaImpresionTicket = "";

                        if (ticketCopy.contains("###")) {
                            ticket = ticketCopy.substring(0, ticket.indexOf("###")) + ticketCopy.substring(ticket.lastIndexOf("###"), ticket.length());
                        }

                        ticket = ticket.replaceAll("\n", "<br>");

                        for (String ooo : ticketImpresora) {
                            cadenaImpresionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
                        }
                        jsonRespuesta.put("ticketImpresora", cadenaImpresionTicket);
                        jsonRespuesta.put("ticketPantalla", ticket);
                        jsonRespuesta.put("success", true);

                    } else {
                        jsonRespuesta.put("success", false);
                        jsonRespuesta.put("motivo", Utiles.CLAVE_INCORRECTA);
                    }
                    out.println(jsonRespuesta.toString());
                } catch (Exception e) {
                    logger.log(Level.SEVERE, null, e);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "Error al procesar número de transacción.");
                    out.println(jsonRespuesta.toString());
                }
            } else if (opcion.equalsIgnoreCase("ANULAR_TRANSACCION_CS")) {

                if (idTransaccional != null
                        && usuarioFacade.esSupervisor((Long) session.getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {

                    TransaccionRc transRc = transaccionRcFacade.get(new BigDecimal(idTransaccional));

                    if (transRc.getIdGestion().getIdGestion().equals(gestion.getIdGestion())
                            && transRc.getIdTipoOperacion().getIdTipoOperacion() == 1) {

                        String urlTA = rcService.anulacion(Long.parseLong(idTransaccional), motivoAnulacion, auth);
                        String mensajeID;

                        String[] urlNexT = urlTA.split(".jsp");
                        if (urlNexT[0].equalsIgnoreCase("error")) {
                            mensajeID = Utiles.getElementValue(Utiles.getElementValue(Utiles.getElementValue(urlTA, "[?]", 1), "&", 0), "=", 1);
                            String mensajeError = rcService.getMensajeGenerico(Long.parseLong(mensajeID), auth);
                            session.removeAttribute("mensajeError");
                            session.setAttribute("mensajeError", mensajeError);
                        } else {
                            mensajeID = Utiles.getElementValue(Utiles.getElementValue(urlTA, "[?]", 1), "=", 1);
                            writeOnUserFile(pathFile, codExtUsuario, mensajeID, true);
                            String ticket = rcService.getTicket(Long.parseLong(mensajeID), auth);
                            String mensajeImpresora = ticket.replaceAll("\n", ";;;");
                            String[] ticketImpresora = mensajeImpresora.split(";;;");
                            String cadenaImpresionTicket = "";

                            ticket = ticket.replaceAll("\n", "<br>");

                            for (String ooo : ticketImpresora) {
                                cadenaImpresionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
                            }
                            request.removeAttribute("ticketImpresora");
                            request.setAttribute("ticketImpresora", cadenaImpresionTicket);
                            request.removeAttribute("mensaje");
                            request.setAttribute("mensaje", ticket);
                        }
                        RequestDispatcher rd;
                        rd = request.getRequestDispatcher(urlTA);
                        rd.forward(request, response);
                    } else {
                        jsonRespuesta.put("success", false);
                        jsonRespuesta.put("motivo", "Número de transacción Incorrecta.");
                    }

                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", Utiles.CLAVE_INCORRECTA);
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("GET_SERVICIOS")) {

                Integer primerResultado = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? new Integer(request.getParameter("start")) : null;
                Integer cantResultados = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? new Integer(request.getParameter("limit")) : null;

                int contServ = 0;
                int cantResult = 0;
                int i = 0;
                List<DefinicionFacturador> listDefConsultables = (List<DefinicionFacturador>) session.getAttribute("ListSrcConsultables");
                List<DefinicionFacturador> listDefManual = (List<DefinicionFacturador>) session.getAttribute("ListSrcManual");

                if (propService.equalsIgnoreCase("CONSULTABLE")) {

                    if (listDefConsultables != null && !listDefConsultables.isEmpty()) {
                        listDefConsultables = (List<DefinicionFacturador>) session.getAttribute("ListSrcConsultables");
                    } else {
                        listDefConsultables = rcService.getServicios(auth, PropiedadServicio.CONSULTABLE);
                        session.setAttribute("ListSrcConsultables", listDefConsultables);
                    }
                    JSONArray json = new JSONArray();
                    for (DefinicionFacturador defFac : listDefConsultables) {
                        if (idFacturador != null) {
                            if (defFac.getIdFacturador().equals(new BigDecimal(idFacturador))) {
                                List<DefinicionServicio> listaDefServicio = defFac.getLsDefinicionServicios();
                                for (DefinicionServicio defSer : listaDefServicio) {
                                    JSONObject jsonServicio = new JSONObject();
                                    if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                                        if (defSer.getDescripcion().toLowerCase().contains(request.getParameter("query").toLowerCase())) {
                                            jsonServicio.put("DESCRIPCION_SERVICIO", defSer.getDescripcion());
                                            jsonServicio.put("ID_SERVICIO", defSer.getIdServicio());
                                            jsonServicio.put("TAG_REF", defSer.getTagReferencia());
                                            json.add(jsonServicio);
                                        }
                                    } else {
                                        jsonServicio.put("DESCRIPCION_SERVICIO", defSer.getDescripcion());
                                        jsonServicio.put("ID_SERVICIO", defSer.getIdServicio());
                                        jsonServicio.put("TAG_REF", defSer.getTagReferencia());
                                        json.add(jsonServicio);
                                    }
                                    contServ++;
                                }
                                break;
                            }
                        } else {
                            JSONObject jsonFacturador = new JSONObject();
                            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                                if (defFac.getDescripcion().toLowerCase().contains(request.getParameter("query").toLowerCase())) {
                                    jsonFacturador.put("DESCRIPCION_FACTURADOR", defFac.getDescripcion());
                                    jsonFacturador.put("ID_FACTURADOR", defFac.getIdFacturador());
                                    json.add(jsonFacturador);
                                }
                            } else {
                                if (primerResultado != null && cantResultados != null) {
                                    if (primerResultado <= i && cantResultados > cantResult) {
                                        jsonFacturador.put("DESCRIPCION_FACTURADOR", defFac.getDescripcion());
                                        jsonFacturador.put("ID_FACTURADOR", defFac.getIdFacturador());
                                        json.add(jsonFacturador);
                                        cantResult++;
                                    }
                                } else {
                                    jsonFacturador.put("DESCRIPCION_FACTURADOR", defFac.getDescripcion());
                                    jsonFacturador.put("ID_FACTURADOR", defFac.getIdFacturador());
                                    json.add(jsonFacturador);
                                }
                                contServ++;
                                i++;
                            }

                        }
                    }
                    if (idFacturador != null) {
                        jsonRespuesta.put("SERVICIO", json);
                        jsonRespuesta.put("TOTAL", contServ);
                        jsonRespuesta.put("success", true);
                    } else {
                        jsonRespuesta.put("FACTURADOR", json);
                        jsonRespuesta.put("TOTAL", contServ);
                        jsonRespuesta.put("success", true);
                    }
                    out.println(jsonRespuesta.toString());
                } else if (propService.equalsIgnoreCase("TELEFONIAS")) {
                    if (listDefManual != null && !listDefManual.isEmpty()) {
                        listDefManual = (List<DefinicionFacturador>) session.getAttribute("ListSrcManual");
                    } else {
                        listDefManual = rcService.getServicios(auth, PropiedadServicio.MANUAL);
                        session.setAttribute("ListSrcManual", listDefManual);
                    }
                    JSONArray json = new JSONArray();
                    for (DefinicionFacturador defFac : listDefManual) {
                        List<DefinicionServicio> listDefSer = defFac.getLsDefinicionServicios();
                        for (DefinicionServicio defServ : listDefSer) {
                            JSONObject jsonService = new JSONObject();
                            jsonService.put("DESCRIPCION_SERVICIO", defServ.getDescripcion());
                            jsonService.put("ID_SERVICIO", defServ.getIdServicio());
                            jsonService.put("TAG_REFERENCIA", defServ.getTagReferencia());
                            json.add(jsonService);
                        }
                    }
                    jsonRespuesta.put("SERVICIO", json);
                    jsonRespuesta.put("TOTAL", listDefManual.size());
                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());
                }

            } else if (opcion.equalsIgnoreCase("RESOLVER_SERVICIO_CONSULTA")) {

                OlResponseConsulta respConsulta = rcService.resolverServicioConsulta(new BigDecimal(idFacturador), idServicio != null ? new Integer(idServicio) : null, this.removeNonNumericChar(nroReferenciaPago), auth);

                RequestDispatcher rd;
                String url;
                if (respConsulta.getCodRetorno() == 0) {
                    url = "confirmacionConsulta.jsp";
                    session.setAttribute("panel", "2");
                } else {
                    url = "errorCyP.jsp";
                    session.setAttribute("error", 1);
                    session.setAttribute("panel", "1");
                }

                if (session.getAttribute("respuestaConsulta") != null) {
                    session.removeAttribute("respuestaConsulta");
                }

                Facturador facturador = facturadorFacade.get(new Long(idFacturador));

                session.setAttribute("respuestaConsulta", respConsulta);
                session.setAttribute("facturador", facturador.getDescripcion());

                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);

            } else if (opcion.equalsIgnoreCase("PROCESAR_CONSULTA")) {
                String url = "";
                RequestDispatcher rd;
                if (!isDuplicatedTrx(session)) {
                    String[] tokens = nroDePago.split(";;;");

                    OlResponseConsulta respConsulta = (OlResponseConsulta) session.getAttribute("respuestaConsulta");
                    OlResponseConsulta respProcesar = responseToProcess(respConsulta, montoRecibido, tokens, server);

                    FormaPago formaDePago = new FormaPago();

                    if (tipoDePago.equals("1") || tipoDePago.equals("2")) {
                        if (tipoDePago.equals("1")) {//efectivo
                            formaDePago.setTipoPago(1);
                        } else {
                            formaDePago.setTipoPago(2);
                            if (idEntidad != null) {
                                formaDePago.setIdBanco(BigDecimal.valueOf(Long.parseLong(idEntidad)));
                            }
                            if (nroDeCheque != null) {
                                formaDePago.setNroCheque(nroDeCheque);
                            }
                            if (fechaCheque != null) {
                                formaDePago.setFechaCheque(fechaCheque);
                            }

                        }
                        //respProcesar.getDetalleServicios().get(0).getDetalleReferencias().get(0).getHashMapContainer()
                        if (respProcesar.getDetalleServicios().get(0).getIdServicio() == 152) {
                            py.com.documenta.onlinemanager.ws.HashMapContainer.Map.Entry e = new py.com.documenta.onlinemanager.ws.HashMapContainer.Map.Entry();

                            String moneda = respProcesar.getDetalleServicios().get(0).getDetalleReferencias().get(0).getIdMoneda().toString();
                            MapValueContainer mapVC = new MapValueContainer();
                            mapVC.setValue(moneda);
                            e.setKey("MONEDA");
                            e.setValue(mapVC);
                            respProcesar.getDetalleServicios().get(0).getDetalleReferencias().get(0).getHashMapContainer().getMap().getEntry().add(e);

                        }

                        RespuestaCobranza respCobranza = rcService.procesarConsulta(auth, respProcesar, formaDePago);
                        String servicioDescripcion = servicioRcFacade.get(respConsulta.getDetalleServicios().get(0).getIdServicio()).getDescripcion();

                        session.setAttribute("respuestaCobranza", respCobranza);
                        session.setAttribute("servicio", servicioDescripcion);
                        session.setAttribute("panel", "1");

                        if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                            url = "ticketCyP.jsp";
                            writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
                        } else {
                            url = "errorCyP.jsp";
                        }
                    } else {
                        url = "error.jsp";
                        session.setAttribute("mensajeError", "No existe forma de Pago");
                    }
                } else {
                    url = "error.jsp";
                    session.setAttribute("mensajeError", "Posible duplicación, favor verificar reporte de cobranza.");
                }
                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (opcion.equalsIgnoreCase("SERVICIO_TELEFONIA")) {
                Telefonia telefonia = rcService.resolverServicioTelefonia(nroTelefono, tipoServicio);
                if (telefonia != null) {
                    jsonRespuesta.put("servicio", telefonia.getIdServicio());
                    jsonRespuesta.put("descripcion", telefonia.getServicio());
                    jsonRespuesta.put("montoMin", telefonia.getMontoMinimo());
                    jsonRespuesta.put("montoMax", telefonia.getMontoMaximo());
                    jsonRespuesta.put("success", true);
                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "Nro. Invalido.");
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("REALIZAR_COBRANZA")) {
                String url;
                RequestDispatcher rd;
                if (!isDuplicatedTrx(session)) {

                    OlDetalleReferencia detReferencia = new OlDetalleReferencia();
                    detReferencia.setAcumulado(BigDecimal.ZERO);
                    detReferencia.setMonto(new BigDecimal(montoRecibido));
                    detReferencia.setReferenciaPago(nroTelefono);
                    detReferencia.setIdMoneda(600);//GS
                    detReferencia.setTasa(1);

                    FormaPago formaDePago = new FormaPago();

                    if (tipoDePago.equals("1") || tipoDePago.equals("2")) {
                        HashMapContainer hMap = null;
                        if (tipoDePago.equals("1")) {//efectivo
                            formaDePago.setTipoPago(1);
                        } else {
                            formaDePago.setTipoPago(2);
                            if (idEntidad != null) {
                                EntidadFinanciera ejemplo = new EntidadFinanciera();
                                ejemplo.setDescripcion(idEntidad);
                                formaDePago.setIdBanco(new BigDecimal(entidadFinancieraFacade.get(ejemplo).getIdEntidadFinanciera()));
                            }
                            if (nroDeCheque != null) {
                                formaDePago.setNroCheque(nroDeCheque);
                            }
                            if (fechaCheque != null) {
                                formaDePago.setFechaCheque(fechaCheque);
                            }
                            if (idEntidad == null && nroDeCheque == null) {
                                formaDePago.setTipoPago(1);
                            }
                        }

                        if (idServicio.equals("153")) {//Retenciones Cargill
                            hMap = getMapCargill(request);
                            String moneda = request.getParameter("MONEDA");
                            detReferencia.setIdMoneda(Integer.valueOf(moneda));
                        }

                        if (depExt) {//Deposito Extraccion
                            hMap = getMapDepExtCU(request, nroTelefono);
                            String[] datos = nroTelefono.split(";");
                            detReferencia.setReferenciaPago(datos[0]);
                        }

                        if (isPG) {
                            hMap = getMapPG(request, idServicio);
                            detReferencia.setReferenciaPago(nroTelefono + "-" + DateUtils.getFormattedDate(new Date(), "mmss"));
                            if (comision != null) {
                                detReferencia.setComision(new BigDecimal(comision));
                            }
                        }

                        if (isMORE) {
                            hMap = getMapMORE(request);
                            detReferencia.setReferenciaPago(nroTelefono);
                        }
                        //-----------------Guardar el servidor-------------------------
                        HashMapContainer.Map.Entry serverName = new HashMapContainer.Map.Entry();
                        serverName.setKey("SERVER_APP");
                        serverName.setValue(server);
                        if (!(depExt || isPG || isMORE || isCargilRet)) {
                            hMap = new HashMapContainer();
                            hMap.setMap(new HashMapContainer.Map());
                        }
                        hMap.getMap().getEntry().add(serverName);
                        //-------------------------------------------------------------
                        logger.log(Level.SEVERE, "idservicio:{0}", idServicio);
                        logger.log(Level.SEVERE, "referencia:{0}", nroTelefono);
                        logger.log(Level.SEVERE, "tipoPago:{0}", tipoDePago);
                        logger.log(Level.SEVERE, "monto:{0}", montoRecibido);

                        RespuestaCobranza respCobranza = rcService.realizarCobranzaManual(new Integer(idServicio), detReferencia, auth, formaDePago, null, hMap);

                        if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                            url = "ticketCyP.jsp";
                            writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
                            switch (Integer.valueOf(idServicio)) {
                                case 18:
                                case 42:
                                case 43:
                                case 39:
                                case 26:
                                    session.setAttribute("printTicket", false);
                            }
                        } else {
                            url = "errorCyP.jsp";
                        }
                        String servicioDescripcion = servicioRcFacade.get(new Integer(idServicio)).getDescripcion();

                        session.setAttribute("respuestaCobranza", respCobranza);
                        session.setAttribute("servicio", servicioDescripcion);
                        session.setAttribute("idServicio", idServicio);
                        session.setAttribute("panel", "1");

                    } else {
                        url = "error.jsp";
                        session.setAttribute("mensajeError", "No existe forma de Pago");
                    }
                } else {
                    url = "error.jsp";
                    session.setAttribute("mensajeError", "Posible duplicación, favor verificar reporte de cobranza.");
                }
                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (opcion.equalsIgnoreCase("COBRO_DESDE_ARCHIVO")) {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                String codigoBarrasTigo = "";
                if (isMultipart) {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        InputStream uploadedStream = item.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
                        String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
                        uploadedStream.close();

                        for (String linea : lineas) {
                            codigoBarrasTigo = "01474"
                                    + linea.substring(6, 8)//tipoComprobante
                                    + linea.substring(8, 15)//Numero Comprobante o Contrato
                                    // + linea.substring(69, 79)//nroCuenta
                                    + "0000"//fill
                                    + linea.substring(79, 87)//fecha
                                    + linea.substring(15, 25) //monto
                                    + "00"; //fill
                        }
                        break;
                    }
                    jsonRespuesta.put("codigoBarras", codigoBarrasTigo);
                    jsonRespuesta.put("success", true);
                } else {
                    jsonRespuesta.put("motivo", "Archivo incorrecto");
                    jsonRespuesta.put("success", false);
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("CONSULTA_MORE_MT")) {
                OlResponseConsulta respConsulta = rcService.resolverServicioConsulta(new BigDecimal(idFacturador), idServicio != null ? new Integer(idServicio) : null, this.removeNonNumericChar(nroReferenciaPago), auth);
                if (respConsulta.getCodRetorno() == 0) {
                    List<OlDetalleReferencia> listDetalles = respConsulta.getDetalleServicios().get(0).getDetalleReferencias();

                    JSONArray jsonArrayDetMore = new JSONArray();
                    for (OlDetalleReferencia e : listDetalles) {
                        JSONObject jsonDetMore = new JSONObject();
                        jsonDetMore.put("ID_ORDEN_PAGO", e.getReferenciaPago());
                        jsonDetMore.put("IMPORTE", e.getMonto());

                        py.com.documenta.onlinemanager.ws.HashMapContainer hmap = e.getHashMapContainer();
                        if (hmap != null) {
                            List<py.com.documenta.onlinemanager.ws.HashMapContainer.Map.Entry> lEntry = hmap.getMap().getEntry();
                            for (py.com.documenta.onlinemanager.ws.HashMapContainer.Map.Entry item : lEntry) {
                                if (item.getKey().equals("BENEFACTOR")) {
                                    jsonDetMore.put("BENEFACTOR", item.getValue().getValue());
                                } else if (item.getKey().equals("BNFICIARIO")) {
                                    jsonDetMore.put("BENEFICIARIO", item.getValue().getValue());
                                } else if (item.getKey().equals("MENSAJE")) {
                                    jsonDetMore.put("MENSAJE", item.getValue().getValue());
                                } else if (item.getKey().equals("MONEDA")) {
                                    jsonDetMore.put("MONEDA", item.getValue().getValue());
                                } else if (item.getKey().equals("ORIGEN")) {
                                    jsonDetMore.put("PAIS_ORIGEN", item.getValue().getValue());
                                } else if (item.getKey().equals("DESTINO")) {
                                    jsonDetMore.put("PAIS_DESTINO", item.getValue().getValue());
                                }
                            }
                        }
                        jsonArrayDetMore.add(jsonDetMore);
                    }
                    jsonRespuesta.put("ORDEN_PAGO", jsonArrayDetMore);
                    jsonRespuesta.put("TOTAL", listDetalles.size());
                    jsonRespuesta.put("success", true);
                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("mensaje", respConsulta.getMensajeOperacion());
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("FAST_BOX")) {
                RequestDispatcher rd;
                String urlRS = "";
                boolean ok = true;
                if (nroReferenciaPago != null) {
                    if (nroReferenciaPago.length() < 9) {
                        nroReferenciaPago = StringUtils.rpad(nroReferenciaPago, "#", 9);
                    }
                    codigoDeBarra = nroReferenciaPago;
                    if (nombre != null) {
                        if (nombre.length() < 40) {
                            nombre = StringUtils.lpad(nombre, "#", 40);
                        }
                        codigoDeBarra += nombre;

                        if (nroDePago != null) {
                            if (nroDePago.length() < 40) {
                                nroDePago = StringUtils.lpad(nroDePago, "#", 40);
                            }
                            codigoDeBarra += nroDePago;

                            if (descripcion != null) {
                                if (descripcion.length() < 90) {
                                    descripcion = StringUtils.lpad(descripcion, "#", 90);
                                }
                                codigoDeBarra += descripcion;
                                if (montoRecibido != null) {
                                    if (montoRecibido.length() < 8) {
                                        montoRecibido = StringUtils.lpad(montoRecibido, "0", 8);
                                    }
                                    codigoDeBarra += montoRecibido;
                                } else {
                                    session.removeAttribute("mensajeError");
                                    session.setAttribute("mensajeError", "No existe Monto");
                                    urlRS = "error.jsp";
                                    ok = false;
                                }

                            } else {
                                session.removeAttribute("mensajeError");
                                session.setAttribute("mensajeError", "No existe descripción del producto");
                                urlRS = "error.jsp";
                                ok = false;
                            }

                        } else {
                            if (codigoDeBarra.charAt(0) == '0') {
                                session.removeAttribute("mensajeError");
                                session.setAttribute("mensajeError", "No existe Nro. de Guia");
                                urlRS = "error.jsp";
                                ok = false;
                            } else {
                                session.removeAttribute("mensajeError");
                                session.setAttribute("mensajeError", "No existe Tienda de Compra");
                                urlRS = "error.jsp";
                                ok = false;
                            }
                        }
                    } else {
                        session.removeAttribute("mensajeError");
                        session.setAttribute("mensajeError", "No existe Nombre y Apellido del Cliente");
                        urlRS = "error.jsp";
                        ok = false;
                    }
                } else {
                    session.removeAttribute("mensajeError");
                    session.setAttribute("mensajeError", "Error en el Nro. de Casilla");
                    urlRS = "error.jsp";
                    ok = false;
                }

                if (codigoDeBarra != null && ok) {
                    RespuestaConsultaCodigoBarras respuesta = rcService.validarCodigoBarras(codigoDeBarra, auth);
                    int retorno = respuesta.getEstado().getCodigoRetorno();
                    if (retorno == 0) {
                        urlRS = "confirmacion.jsp";
                        session.setAttribute("respuestaCB", respuesta);
                    } else {
                        urlRS = "error.jsp";
                        session.setAttribute("mensajeError", respuesta.getEstado().getDescripcion());
                    }
                } else if (ok) {
                    session.removeAttribute("mensajeError");
                    session.setAttribute("mensajeError", "Error al procesar el Servicio");
                    urlRS = "error.jsp";
                }
                rd = request.getRequestDispatcher(urlRS);
                rd.forward(request, response);

            } else if (opcion.equalsIgnoreCase("ICED")) {
                RequestDispatcher rd;
                String urlRS = "";
                boolean ok = true;
                if (nroReferenciaPago != null) {
                    if (nroReferenciaPago.length() < 10) {
                        nroReferenciaPago = StringUtils.rpad(nroReferenciaPago, "#", 10);
                    }
                    codigoDeBarra = nroReferenciaPago;
                    if (nombre != null) {
                        if (nombre.length() < 40) {
                            nombre = StringUtils.lpad(nombre, "#", 40);
                        }
                        codigoDeBarra += nombre;

                        if (descripcion != null) {
                            if (descripcion.length() < 90) {
                                descripcion = StringUtils.lpad(descripcion, "#", 90);
                            }
                            codigoDeBarra += descripcion;
                            if (montoRecibido != null) {
                                if (montoRecibido.length() < 8) {
                                    montoRecibido = StringUtils.lpad(montoRecibido, "0", 8);
                                }
                                codigoDeBarra += montoRecibido;
                            } else {
                                session.removeAttribute("mensajeError");
                                session.setAttribute("mensajeError", "No existe Monto");
                                urlRS = "error.jsp";
                                ok = false;
                            }

                        } else {
                            session.removeAttribute("mensajeError");
                            session.setAttribute("mensajeError", "No existe descripción del Curso");
                            urlRS = "error.jsp";
                            ok = false;
                        }

                    } else {
                        session.removeAttribute("mensajeError");
                        session.setAttribute("mensajeError", "No existe Nombre y Apellido del Cliente");
                        urlRS = "error.jsp";
                        ok = false;
                    }
                } else {
                    session.removeAttribute("mensajeError");
                    session.setAttribute("mensajeError", "Error en el Nro. de Casilla");
                    urlRS = "error.jsp";
                    ok = false;
                }

                if (codigoDeBarra != null && ok) {
                    RespuestaConsultaCodigoBarras respuesta = rcService.validarCodigoBarras(codigoDeBarra, auth);
                    int retorno = respuesta.getEstado().getCodigoRetorno();
                    if (retorno == 0) {
                        urlRS = "confirmacion.jsp";
                        session.setAttribute("respuestaCB", respuesta);
                    } else {
                        urlRS = "error.jsp";
                        session.setAttribute("mensajeError", respuesta.getEstado().getDescripcion());
                    }
                } else if (ok) {
                    session.removeAttribute("mensajeError");
                    session.setAttribute("mensajeError", "Error al procesar el Servicio");
                    urlRS = "error.jsp";
                }
                rd = request.getRequestDispatcher(urlRS);
                rd.forward(request, response);
                /**
                 * ************************************************************************************
                 */
            } else if (opcion.equalsIgnoreCase("CARGILL")) {
                RequestDispatcher rd;
                String urlRS = "";
                boolean ok = true;
                String nroDoc = request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO) != null && !request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO) : null;
                String moneda = request.getParameter(COBRO_SERVICIOS.MONEDA) != null && !request.getParameter(COBRO_SERVICIOS.MONEDA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.MONEDA) : null;
                String razonSocial = request.getParameter(COBRO_SERVICIOS.NOMBRE) != null && !request.getParameter(COBRO_SERVICIOS.NOMBRE).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NOMBRE) : null;
                String nroBoleta = request.getParameter(COBRO_SERVICIOS.NRO_RETENCION) != null && !request.getParameter(COBRO_SERVICIOS.NRO_RETENCION).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NRO_RETENCION) : null;
                RetenionesCargill retCar = new RetenionesCargill();
                retCar.setIdRetencion(retCarFacade.getNextId());
                retCar.setFechaIngreso(Calendar.getInstance().getTime());
                if (nroReferenciaPago != null) {
                    retCar.setIdCliente(new BigInteger(nroReferenciaPago));
                    if (nroDoc != null) {
                        retCar.setRuc(nroDoc);
                        if (montoRecibido != null) {
                            retCar.setMonto(new BigDecimal(montoRecibido));
                            if (moneda != null) {
                                retCar.setMoneda(new Short(moneda));
                                if (razonSocial != null) {
                                    retCar.setRazonSocial(razonSocial);
                                    if (nroBoleta != null) {
                                        retCar.setNroRetencion(nroBoleta);
                                    } else {
                                        session.removeAttribute("mensajeError");
                                        session.setAttribute("mensajeError", "No existe Nro. de Boleta");
                                        urlRS = "error.jsp";
                                        ok = false;
                                    }
                                } else {
                                    session.removeAttribute("mensajeError");
                                    session.setAttribute("mensajeError", "No existe Razon Social");
                                    urlRS = "error.jsp";
                                    ok = false;
                                }
                            } else {
                                session.removeAttribute("mensajeError");
                                session.setAttribute("mensajeError", "No existe Moneda");
                                urlRS = "error.jsp";
                                ok = false;
                            }

                        } else {
                            session.removeAttribute("mensajeError");
                            session.setAttribute("mensajeError", "No existe Monto");
                            urlRS = "error.jsp";
                            ok = false;
                        }

                    } else {
                        session.removeAttribute("mensajeError");
                        session.setAttribute("mensajeError", "No existe RUC del Cliente");
                        urlRS = "error.jsp";
                        ok = false;
                    }
                } else {
                    session.removeAttribute("mensajeError");
                    session.setAttribute("mensajeError", "Error en el Nro. de Cliente");
                    urlRS = "error.jsp";
                    ok = false;
                }
                if (ok) {

                    retCarFacade.save(retCar);
                    RespuestaCobranza respCobranza = new RespuestaCobranza();
                    EstadoTransaccion estado = new EstadoTransaccion();
                    estado.setCodigoRetorno(0);
                    estado.setDescripcion("Retenciones Cargill");
                    Moneda currency = monedaFacade.get(Long.parseLong(moneda));

                    respCobranza.setEstado(estado);
                    respCobranza.setIdTransaccion(retCar.getIdRetencion());
                    respCobranza.setTicket(makeTicketRetencion(request, session, razonSocial, retCar.getIdRetencion().toString(), montoRecibido, currency.getAbreviatura()));

                    urlRS = "ticketCyP.jsp";
                    session.setAttribute("respuestaCobranza", respCobranza);
                    writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
                    session.setAttribute("servicio", "Retenciones Cargill");

                }
                rd = request.getRequestDispatcher(urlRS);
                rd.forward(request, response);

            }else if (opcion.equalsIgnoreCase("PURPURA")) {
                    String urlRS;
                    jsonRespuesta = new JSONObject();
                    if (!isDuplicatedTrx(session)) {
                        Integer medioPago = 1;
                        FormaPago formaDePago = new FormaPago();
                        formaDePago.setTipoPago(medioPago);
                        Integer idServicioPurpura = 195;
                        BigDecimal factu  = null;
                        factu = BigDecimal.valueOf(120);

                        //String[] tokens = ";;;100#600#1".split(";;;");

                        //OlResponseConsulta respConsulta = (OlResponseConsulta) session.getAttribute("respuestaConsulta");
                        //OlResponseConsulta respProcesar = responseToProcess(respConsulta, montoRecibido, tokens, server);

                        //OlResponseConsulta respProcesar = new OlResponseConsulta();
                        String nroTel = request.getParameter("NRO_TELEFONO") != null && !request.getParameter("NRO_TELEFONO").isEmpty() ? request.getParameter("NRO_TELEFONO") : null;
                        String ruc = request.getParameter("RUC") != null && !request.getParameter("RUC").isEmpty() ? request.getParameter("RUC") : null;
                        String monto = request.getParameter("MONTO_CARGAR") != null && !request.getParameter("MONTO_CARGAR").isEmpty() ? request.getParameter("MONTO_CARGAR") : null;

    //                    respProcesar.setCambioMontoPermitido(true);
    //                    respProcesar.setCodRetorno(0);
    //                    respProcesar.setMensajeOperacion("Consulta Correcta");
    //                    respProcesar.setReferenciaConsulta(ruc +";"+nroTel+";"+monto);
                        String consulta = ruc +";"+nroTel+";"+monto;
                        OlResponseConsulta respConsulta = rcService.resolverServicioConsulta(/*new BigDecimal(idFacturador)*/factu, new Integer(idServicioPurpura), consulta, auth);
                        if (respConsulta.getCodRetorno() == 0) {
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, respConsulta.getCodRetorno().toString());
                        /*****************************************************************************/
                        for (OlDetalleReferencia detalleRef : respConsulta.getDetalleServicios().get(0).getDetalleReferencias()) {
                            List<Entry> entryList = detalleRef.getHashMapContainer().getMap().getEntry();

                            String te = null;
                            String cedu = null;

                            for (Entry entry : entryList) {
                                if (entry.getKey().equals("MONT")) {
                                    monto = entry.getValue().getValue();
                                }

                                if (entry.getKey().equals("TEL")) {
                                    te = entry.getValue().getValue();
                                }
                                if (entry.getKey().equals("CED")) {
                                    cedu = entry.getValue().getValue();
                                }

                            }
                            jsonRespuesta = new JSONObject();
                            jsonRespuesta.put("monto", monto);
                            jsonRespuesta.put("cedula", cedu);

                            jsonRespuesta.put("olDetalleReferencia", detalleRef);
                        }

                        if (session.getAttribute("respuestaConsulta") != null) {
                            session.removeAttribute("respuestaConsulta");
                        }

                        //Facturador facturador = facturadorFacade.get(new Long(Long.parseLong(String.valueOf(factu))));

                        session.setAttribute("respuestaConsulta", respConsulta);
                        session.setAttribute("facturador", "Purpura Promo");

                        jsonRespuesta.put("success", true);
                        out.println(jsonRespuesta.toString());
                        /*****************************************************************************/
                        String token[] = ";;;100#600#1".split(";;;");
                        OlResponseConsulta respConsulta1 = (OlResponseConsulta) session.getAttribute("respuestaConsulta");
                        OlResponseConsulta respProcesar1 = responseToProcess(respConsulta1, montoRecibido, token, server);

                        RespuestaCobranza respCobranza = rcService.procesarConsulta(auth, respProcesar1, formaDePago);

                        String servicioDescripcion = servicioRcFacade.get(respProcesar1.getDetalleServicios().get(0).getIdServicio()).getDescripcion();

                        if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                            session.setAttribute("respuestaCobranza", respCobranza);
                            session.setAttribute("servicio", servicioDescripcion);
                            session.setAttribute("panel", "1");
                            respCobranza.setTicket(makeTicketPurpura(request, session, ruc, monto,  respCobranza.getIdTransaccion().toString()));
                            urlRS = "ticketCyP.jsp";
                            RequestDispatcher rd = request.getRequestDispatcher(urlRS);
                            rd.forward(request, response);
                            //jsonRespuesta.put("success", true);
                        } else {
                            if (respCobranza.getEstado().getDescripcion().contains("El cliente no completo el formulario de aceptacion.")) {
                                jsonRespuesta.put("estado", 20);
                                jsonRespuesta.put("mensaje", respCobranza.getEstado().getDescripcion());
                                jsonRespuesta.put("success", false);
                            } else {
                                jsonRespuesta.put("mensaje", respCobranza.getEstado().getDescripcion());
                                jsonRespuesta.put("success", false);
                            }
                        }
                       
                            

                    } else {
                        //jsonRespuesta.put("success", false);
                        //jsonRespuesta.put("estado", -1);
                        //jsonRespuesta.put("mensaje", "Posible duplicación, favor verificar reporte de cobranza.");
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, null);
                        RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
                        request.setAttribute("backTO", "");
                        request.getSession().setAttribute("mensajeError", "Lo sentimos!\n"
                        + "Ha sucedido algo inesperado\n"
                        + "Favor contacte con Documenta\n"
                        + "Mail:sau@documenta.com.py");
                rd.forward(request, response);

                    }
                out.println(jsonRespuesta.toString());
              }
                
            } else if (opcion.equalsIgnoreCase("CONSULTAR_ENVIO_PRACTIGIRO")) {
                String cedulaOrigen = (String) request.getParameter("CEDULA_ORIGEN");
                String cedulaDestino = (String) request.getParameter("CEDULA_DESTINO");
                String telefonoDestino = (String) request.getParameter("TELEFONO_DESTINO");
                String monto = (String) request.getParameter("MONTO").replaceAll("\\.", "");
                logger.log(Level.INFO, "Consulta Practigiro [cedulaOrigen=" + cedulaOrigen + ", cedulaDestino=" + cedulaDestino + ",telefonoDestino=" + telefonoDestino + ", monto=" + monto + "]");
                idFacturador = "5000";
                idServicio = "5001";
                nroReferenciaPago = cedulaOrigen + ";" + cedulaDestino + ";" + telefonoDestino + ";" + monto;
                OlResponseConsulta respConsulta = rcService.resolverServicioConsulta(new BigDecimal(idFacturador), new Integer(idServicio), this.removeNonNumericChar(nroReferenciaPago), auth);
                if (respConsulta.getCodRetorno() == 0) {
                    logger.log(Level.INFO, respConsulta.getCodRetorno().toString());
                    List<Entry> entryList = respConsulta.getDetalleServicios().get(0).getDetalleReferencias().get(0).getHashMapContainer().getMap().getEntry();

                    String comisionEnvioPG = null;

                    for (Entry entry : entryList) {
                        if (entry.getKey().equals("MONTO_COM")) {
                            comisionEnvioPG = entry.getValue().getValue();
                        }
                    }
                    jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("comision", comisionEnvioPG);

                    if (session.getAttribute("respuestaConsulta") != null) {
                        session.removeAttribute("respuestaConsulta");
                    }

                    Facturador facturador = facturadorFacade.get(new Long(idFacturador));

                    session.setAttribute("respuestaConsulta", respConsulta);
                    session.setAttribute("facturador", facturador.getDescripcion());

                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());
                } else {
                    jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("success", false);
                    out.println(jsonRespuesta.toString());
                }
                logger.log(Level.INFO, "OUT: [" + jsonRespuesta + "]");
            } else if (opcion.equalsIgnoreCase("REALIZAR_ENVIO_PRACTIGIRO")) {
                String url;
                RequestDispatcher rd;
                if (!isDuplicatedTrx(session)) {
                    Integer medioPago = Integer.parseInt(request.getParameter("FORMA_PAGO"));
                    String nroCheque = request.getParameter("NRO_CHEQUE");
                    String fechaChequePG = request.getParameter("NRO_CHEQUE");
                    String entidadCheque = request.getParameter("ENTIDAD_CHEQUE");
                    FormaPago formaDePago = new FormaPago();
                    formaDePago.setTipoPago(medioPago);
                    if (medioPago != 1) {
                        formaDePago.setFechaCheque(fechaChequePG);
                        formaDePago.setNroCheque(nroCheque);
                        formaDePago.setIdBanco(new BigDecimal(entidadCheque));
                    }

                    String[] tokens = ";;;100#600#1".split(";;;");

                    OlResponseConsulta respConsulta = (OlResponseConsulta) session.getAttribute("respuestaConsulta");
                    OlResponseConsulta respProcesar = responseToProcess(respConsulta, montoRecibido, tokens, server);

                    RespuestaCobranza respCobranza = rcService.procesarConsulta(auth, respProcesar, formaDePago);
                    String servicioDescripcion = servicioRcFacade.get(respProcesar.getDetalleServicios().get(0).getIdServicio()).getDescripcion();

                    session.setAttribute("respuestaCobranza", respCobranza);
                    session.setAttribute("servicio", servicioDescripcion);
                    session.setAttribute("panel", "1");

                    if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                        url = "ticketCyP.jsp";
                        writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
                    } else {
                        url = "errorCyP.jsp";
                    }
                } else {
                    url = "error.jsp";
                    session.setAttribute("mensajeError", "Posible duplicación, favor verificar reporte de cobranza.");
                }
                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (opcion.equalsIgnoreCase("CONSULTAR_RETIRO_PRACTIGIRO")) {
                String cedula = (String) request.getParameter("CEDULA");
                String telefono = (String) request.getParameter("TELEFONO");
                String pin = (String) request.getParameter("PIN");
                logger.log(Level.INFO, "Consulta Practigiro [cedula=" + cedula + ",telefono=" + telefono + ",pin = " + pin + "]");
                idFacturador = "5000";
                idServicio = "5002";
                nroReferenciaPago = cedula + ";" + telefono + ";" + pin;
                OlResponseConsulta respConsulta = rcService.resolverServicioConsulta(new BigDecimal(idFacturador), new Integer(idServicio), this.removeNonNumericChar(nroReferenciaPago), auth);
                if (respConsulta.getCodRetorno() == 0) {
                    logger.log(Level.INFO, respConsulta.getCodRetorno().toString());

                    for (OlDetalleReferencia detalleRef : respConsulta.getDetalleServicios().get(0).getDetalleReferencias()) {
                        List<Entry> entryList = detalleRef.getHashMapContainer().getMap().getEntry();
                        String monto = null;
                        String idRemesa = null;

                        for (Entry entry : entryList) {
                            if (entry.getKey().equals("MONTO")) {
                                monto = entry.getValue().getValue();
                            }

                            if (entry.getKey().equals("ID_REMESA")) {
                                idRemesa = entry.getValue().getValue();
                            }

                        }
                        jsonRespuesta = new JSONObject();
                        jsonRespuesta.put("monto", monto);
//                        jsonRespuesta.put("idRemesa", idRemesa);
//                        jsonRespuesta.put("olDetalleReferencia", detalleRef);
                    }

                    if (session.getAttribute("respuestaConsulta") != null) {
                        session.removeAttribute("respuestaConsulta");
                    }

                    Facturador facturador = facturadorFacade.get(new Long(idFacturador));

                    session.setAttribute("respuestaConsulta", respConsulta);
                    session.setAttribute("facturador", facturador.getDescripcion());

                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());
                } else {
                    jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("success", false);
                    out.println(jsonRespuesta.toString());
                }
                logger.log(Level.INFO, "OUT: [" + jsonRespuesta + "]");
            } else if (opcion.equalsIgnoreCase("REALIZAR_RETIRO_PRACTIGIRO")) {
                jsonRespuesta = new JSONObject();
                if (!isDuplicatedTrx(session)) {
                    Integer medioPago = 1;
                    FormaPago formaDePago = new FormaPago();
                    formaDePago.setTipoPago(medioPago);

                    String[] tokens = ";;;100#600#1".split(";;;");

                    OlResponseConsulta respConsulta = (OlResponseConsulta) session.getAttribute("respuestaConsulta");
                    OlResponseConsulta respProcesar = responseToProcess(respConsulta, montoRecibido, tokens, server);

                    RespuestaCobranza respCobranza = rcService.procesarConsulta(auth, respProcesar, formaDePago);
                    String servicioDescripcion = servicioRcFacade.get(respProcesar.getDetalleServicios().get(0).getIdServicio()).getDescripcion();

                    if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                        session.setAttribute("respuestaCobranza", respCobranza);
                        session.setAttribute("servicio", servicioDescripcion);
                        session.setAttribute("panel", "1");
                        jsonRespuesta.put("success", true);
                    } else {
                        if (respCobranza.getEstado().getDescripcion().contains("El cliente no completo el formulario de aceptacion.")) {
                            jsonRespuesta.put("estado", 20);
                            jsonRespuesta.put("mensaje", respCobranza.getEstado().getDescripcion());
                            jsonRespuesta.put("success", false);
                        } else {
                            jsonRespuesta.put("mensaje", respCobranza.getEstado().getDescripcion());
                            jsonRespuesta.put("success", false);
                        }
                    }

                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("estado", -1);
                    jsonRespuesta.put("mensaje", "Posible duplicación, favor verificar reporte de cobranza.");

                }
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("CONSULTAR_ENVIO_PRACTIGIRO_2")) {
                String cedulaOrigen = (String) request.getParameter("CEDULA_ORIGEN");
                String cedulaDestino = (String) request.getParameter("CEDULA_DESTINO");
                String telefonoDestino = (String) request.getParameter("TELEFONO_DESTINO");
                String monto = (String) request.getParameter("MONTO").replaceAll("\\.", "");
                logger.log(Level.INFO, "Consulta Practigiro [cedulaOrigen=" + cedulaOrigen + ", cedulaDestino=" + cedulaDestino + ",telefonoDestino=" + telefonoDestino + ", monto=" + monto + "]");
                idFacturador = "5000";
                idServicio = "5021";
                nroReferenciaPago = cedulaOrigen + ";" + cedulaDestino + ";" + telefonoDestino + ";" + monto;
                OlResponseConsulta respConsulta = rcService.resolverServicioConsulta(new BigDecimal(idFacturador), new Integer(idServicio), this.removeNonNumericChar(nroReferenciaPago), auth);
                if (respConsulta.getCodRetorno() == 0) {
                    logger.log(Level.INFO, respConsulta.getCodRetorno().toString());
                    List<Entry> entryList = respConsulta.getDetalleServicios().get(0).getDetalleReferencias().get(0).getHashMapContainer().getMap().getEntry();

                    String comisionEnvioPG = null;

                    for (Entry entry : entryList) {
                        if (entry.getKey().equals("MONTO_COM")) {
                            comisionEnvioPG = entry.getValue().getValue();
                        }
                    }
                    jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("comision", comisionEnvioPG);

                    if (session.getAttribute("respuestaConsulta") != null) {
                        session.removeAttribute("respuestaConsulta");
                    }

                    Facturador facturador = facturadorFacade.get(new Long(idFacturador));

                    session.setAttribute("respuestaConsulta", respConsulta);
                    session.setAttribute("facturador", facturador.getDescripcion());

                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());
                } else {
                    jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("success", false);
                    out.println(jsonRespuesta.toString());
                }
                logger.log(Level.INFO, "OUT: [" + jsonRespuesta + "]");
            } else if (opcion.equalsIgnoreCase("REALIZAR_ENVIO_PRACTIGIRO_2")) {
                jsonRespuesta = new JSONObject();
                String url;
                RequestDispatcher rd;
                if (!isDuplicatedTrx(session)) {
                    Integer medioPago = Integer.parseInt(request.getParameter("FORMA_PAGO"));
                    String nroCheque = request.getParameter("NRO_CHEQUE");
                    String fechaChequePG = request.getParameter("NRO_CHEQUE");
                    String entidadCheque = request.getParameter("ENTIDAD_CHEQUE");
                    FormaPago formaDePago = new FormaPago();
                    formaDePago.setTipoPago(medioPago);
                    if (medioPago != 1) {
                        formaDePago.setFechaCheque(fechaChequePG);
                        formaDePago.setNroCheque(nroCheque);
                        formaDePago.setIdBanco(new BigDecimal(entidadCheque));
                    }

                    String[] tokens = ";;;100#600#1".split(";;;");

                    OlResponseConsulta respConsulta = (OlResponseConsulta) session.getAttribute("respuestaConsulta");
                    OlResponseConsulta respProcesar = responseToProcess(respConsulta, montoRecibido, tokens, server);

                    RespuestaCobranza respCobranza = rcService.procesarConsulta(auth, respProcesar, formaDePago);
                    String servicioDescripcion = servicioRcFacade.get(respProcesar.getDetalleServicios().get(0).getIdServicio()).getDescripcion();

                    session.setAttribute("respuestaCobranza", respCobranza);

                    if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                        session.setAttribute("respuestaCobranza", respCobranza);
                        session.setAttribute("servicio", servicioDescripcion);
                        session.setAttribute("panel", "1");
                        jsonRespuesta.put("success", true);
                    } else {
                        jsonRespuesta.put("mensaje", respCobranza.getEstado().getDescripcion());
                        jsonRespuesta.put("success", false);
                    }
                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("estado", -1);
                    jsonRespuesta.put("mensaje", "Posible duplicación, favor verificar reporte de cobranza.");
                }
//                rd = request.getRequestDispatcher(url);
//                rd.forward(request, response);

                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("CONSULTAR_RETIRO_PRACTIGIRO_2")) {
                String cedula = (String) request.getParameter("CEDULA");
                String telefono = (String) request.getParameter("TELEFONO");
                String pin = (String) request.getParameter("PIN");
                logger.log(Level.INFO, "Consulta Practigiro [cedula=" + cedula + ",telefono=" + telefono + ",pin = " + pin + "]");
                idFacturador = "5000";
                idServicio = "5022";
                nroReferenciaPago = cedula + ";" + telefono + ";" + pin;
                OlResponseConsulta respConsulta = rcService.resolverServicioConsulta(new BigDecimal(idFacturador), new Integer(idServicio), this.removeNonNumericChar(nroReferenciaPago), auth);
                if (respConsulta.getCodRetorno() == 0) {
                    logger.log(Level.INFO, respConsulta.getCodRetorno().toString());

                    for (OlDetalleReferencia detalleRef : respConsulta.getDetalleServicios().get(0).getDetalleReferencias()) {
                        List<Entry> entryList = detalleRef.getHashMapContainer().getMap().getEntry();
                        String monto = null;
                        String idRemesa = null;

                        for (Entry entry : entryList) {
                            if (entry.getKey().equals("MONTO")) {
                                monto = entry.getValue().getValue();
                            }

                            if (entry.getKey().equals("ID_REMESA")) {
                                idRemesa = entry.getValue().getValue();
                            }

                        }
                        jsonRespuesta = new JSONObject();
                        jsonRespuesta.put("monto", monto);
//                        jsonRespuesta.put("idRemesa", idRemesa);
//                        jsonRespuesta.put("olDetalleReferencia", detalleRef);
                    }

                    if (session.getAttribute("respuestaConsulta") != null) {
                        session.removeAttribute("respuestaConsulta");
                    }

                    Facturador facturador = facturadorFacade.get(new Long(idFacturador));

                    session.setAttribute("respuestaConsulta", respConsulta);
                    session.setAttribute("facturador", facturador.getDescripcion());

                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());
                } else {
                    jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("success", false);
                    out.println(jsonRespuesta.toString());
                }
                logger.log(Level.INFO, "OUT: [" + jsonRespuesta + "]");
            } else if (opcion.equalsIgnoreCase("REALIZAR_RETIRO_PRACTIGIRO_2")) {
                jsonRespuesta = new JSONObject();
                if (!isDuplicatedTrx(session)) {
                    Integer medioPago = 1;
                    FormaPago formaDePago = new FormaPago();
                    formaDePago.setTipoPago(medioPago);

                    String[] tokens = ";;;100#600#1".split(";;;");

                    OlResponseConsulta respConsulta = (OlResponseConsulta) session.getAttribute("respuestaConsulta");
                    OlResponseConsulta respProcesar = responseToProcess(respConsulta, montoRecibido, tokens, server);

                    RespuestaCobranza respCobranza = rcService.procesarConsulta(auth, respProcesar, formaDePago);
                    String servicioDescripcion = servicioRcFacade.get(respProcesar.getDetalleServicios().get(0).getIdServicio()).getDescripcion();

                    if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                        session.setAttribute("respuestaCobranza", respCobranza);
                        session.setAttribute("servicio", servicioDescripcion);
                        session.setAttribute("panel", "1");
                        jsonRespuesta.put("success", true);
                    } else {
                        if (respCobranza.getEstado().getDescripcion().contains("El cliente no completo el formulario de aceptacion.")) {
                            jsonRespuesta.put("estado", 20);
                            jsonRespuesta.put("mensaje", respCobranza.getEstado().getDescripcion());
                            jsonRespuesta.put("success", false);
                        } else {
                            jsonRespuesta.put("mensaje", respCobranza.getEstado().getDescripcion());
                            jsonRespuesta.put("success", false);
                        }
                    }

                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("estado", -1);
                    jsonRespuesta.put("mensaje", "Posible duplicación, favor verificar reporte de cobranza.");

                }
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("TICKET_RETIRO_PRACTIGIRO")) {
//                String url;
//                RequestDispatcher rd;
//                RespuestaCobranza respCobranza = (RespuestaCobranza) session.getAttribute("respuestaCobranza");
//                if (respCobranza.getEstado().getCodigoRetorno() == 0) {
//                    session.setAttribute("panel", "1");
//                    session.setAttribute("servicio", "Practigiros");
//                    url = "ticketCyP.jsp";
//                    writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
//                } else {
//                    url = "errorCyP.jsp";
//                }
//                rd = request.getRequestDispatcher(url);
//                rd.forward(request, response);
                String url;
                RequestDispatcher rd;
                RespuestaCobranza respCobranza = (RespuestaCobranza) session.getAttribute("respuestaCobranza");

                /**
                 * la cosa esta viene asi..
                 * elticketcompleto|||elmensaje|||tipoRestriccion|||codError
                 * tenemos que hacer un split
                 */
                if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                    String mensajeRestriccion = null;
                    String tipoRestriccion = null;
                    String codError = null;

                    try {
                        String ticketSplit[] = respCobranza.getTicket().split("\\|\\|\\|");
                        respCobranza.setTicket(ticketSplit[0]);
                        mensajeRestriccion = ticketSplit[1];
                        tipoRestriccion = ticketSplit[2];
                        codError = ticketSplit[3];

                    } catch (Exception e) {
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, null, e);
                    }

                    request.getSession().setAttribute("mensajeRestriccionPractigiros", mensajeRestriccion);
                    request.getSession().setAttribute("tipoRestriccionPractigiros", tipoRestriccion);
                    request.getSession().setAttribute("codErrorPractigiros", codError);

                    url = "ticketCyP.jsp";
                    writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
                } else {
                    url = "errorCyP.jsp";
                }
                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            /**
             * *********************************************************************************
             */
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
            request.setAttribute("backTO", "");
            request.getSession().setAttribute("mensajeError", "Lo sentimos!\n"
                    + "Ha sucedido algo inesperado\n"
                    + "Favor contacte con Documenta\n"
                    + "Mail:sau@documenta.com.py");

            rd.forward(request, response);
//            JSONObject jsonRespuesta = new JSONObject();
//            jsonRespuesta.put("success", false);
//            jsonRespuesta.put("motivo", e.getMessage());
//            out.println(jsonRespuesta.toString());

        } finally {
            try {
                //out.close();
            } finally {
            }
        }
    }

    private HashMapContainer getMapCargill(HttpServletRequest request) {
        String nroCliente = request.getParameter("NRO_CLIENTE") != null && !request.getParameter("NRO_CLIENTE").isEmpty() ? request.getParameter("NRO_CLIENTE") : null;
        String ruc = request.getParameter("RUC") != null && !request.getParameter("RUC").isEmpty() ? request.getParameter("RUC") : null;
        String razonSocial = request.getParameter("RAZON_SOCIAL") != null && !request.getParameter("RAZON_SOCIAL").isEmpty() ? request.getParameter("RAZON_SOCIAL") : null;
        String nroBoleta = request.getParameter("NRO_BOLETA") != null && !request.getParameter("NRO_BOLETA").isEmpty() ? request.getParameter("NRO_BOLETA") : null;
        String timbrado = request.getParameter("TIMBRADO") != null && !request.getParameter("TIMBRADO").isEmpty() ? request.getParameter("TIMBRADO") : null;
        String fechaBoleta = request.getParameter("FECHA_BOLETA") != null && !request.getParameter("FECHA_BOLETA").isEmpty() ? request.getParameter("FECHA_BOLETA") : null;

        HashMapContainer hMap = new HashMapContainer();
        hMap.setMap(new HashMapContainer.Map());

        HashMapContainer.Map.Entry nroClienteEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry rucEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry razonSocialEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry nroBoletaEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry timbradoEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry fechaBoletaEM = new HashMapContainer.Map.Entry();

        if (nroCliente != null) {
            nroClienteEM.setKey("NRO_CLIENT");
            nroClienteEM.setValue(nroCliente);
            hMap.getMap().getEntry().add(nroClienteEM);
        }
        if (ruc != null) {
            rucEM.setKey("RUC");
            rucEM.setValue(ruc);
            hMap.getMap().getEntry().add(rucEM);
        }
        if (razonSocial != null) {
            razonSocialEM.setKey("RAZON_SOC");
            razonSocialEM.setValue(razonSocial);
            hMap.getMap().getEntry().add(razonSocialEM);
        }
        if (nroBoleta != null) {
            nroBoletaEM.setKey("NRO_BOLETA");
            nroBoletaEM.setValue(nroBoleta);
            hMap.getMap().getEntry().add(nroBoletaEM);
        }
        if (timbrado != null) {
            timbradoEM.setKey("TIMBRADO");
            timbradoEM.setValue(timbrado);
            hMap.getMap().getEntry().add(timbradoEM);
        }
        if (fechaBoleta != null) {
            fechaBoletaEM.setKey("FECHA_BOL");
            fechaBoletaEM.setValue(fechaBoleta);
            hMap.getMap().getEntry().add(fechaBoletaEM);
        }

        return hMap;
    }

    private HashMapContainer getMapDepExtCU(HttpServletRequest request, String datosDeposito) {
        HashMapContainer hMap = new HashMapContainer();
        hMap.setMap(new HashMapContainer.Map());
        String[] datos = datosDeposito.split(";");

        /*
         * hMap = new HashMapContainer(); hMap.setMap(new
         * HashMapContainer.Map());
         */
        HashMapContainer.Map.Entry ciCliente = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry pin = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry ciDepositante = new HashMapContainer.Map.Entry();

        ciCliente.setKey("CI");
        ciCliente.setValue(datos[1]);

        pin.setKey("PIN");
        pin.setValue(datos[2]);

        ciDepositante.setKey("CI_DEP");
        ciDepositante.setValue(datos[3]);

        if (datos.length == 5) {
            HashMapContainer.Map.Entry nroSocio = new HashMapContainer.Map.Entry();
            nroSocio.setKey("NRO_SOCIO");
            nroSocio.setValue(datos[4]);
            hMap.getMap().getEntry().add(nroSocio);
        }
        hMap.getMap().getEntry().add(ciCliente);
        hMap.getMap().getEntry().add(pin);
        hMap.getMap().getEntry().add(ciDepositante);
        return hMap;
    }

    private HashMapContainer getMapMORE(HttpServletRequest request) {
        String tipoDocumento = request.getParameter(COBRO_SERVICIOS.TIPO_DOC) != null && !request.getParameter(COBRO_SERVICIOS.TIPO_DOC).isEmpty() ? request.getParameter(COBRO_SERVICIOS.TIPO_DOC) : null;
        String nroDoc = request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO) != null && !request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO) : null;
        String fechaNac = request.getParameter("FECHA_NAC") != null && !request.getParameter("FECHA_NAC").isEmpty() ? request.getParameter("FECHA_NAC") : null;
        String fechaExp = request.getParameter("FECHA_EXP") != null && !request.getParameter("FECHA_EXP").isEmpty() ? request.getParameter("FECHA_EXP") : null;

        HashMapContainer hMap = new HashMapContainer();
        hMap.setMap(new HashMapContainer.Map());

        HashMapContainer.Map.Entry tipoDocEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry idDocEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry fechaNacEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry fechaExpEM = new HashMapContainer.Map.Entry();

        tipoDocEM.setKey("TIPO_DOC");
        tipoDocEM.setValue(tipoDocumento);

        idDocEM.setKey("NRO_DOC");
        idDocEM.setValue(nroDoc);

        fechaNacEM.setKey("FECHA_NAC");
        fechaNacEM.setValue(fechaNac);

        fechaExpEM.setKey("FECHA_EXP");
        fechaExpEM.setValue(fechaExp);

        hMap.getMap().getEntry().add(tipoDocEM);
        hMap.getMap().getEntry().add(idDocEM);
        hMap.getMap().getEntry().add(fechaNacEM);
        hMap.getMap().getEntry().add(fechaExpEM);

        return hMap;
    }

    private HashMapContainer getMapPG(HttpServletRequest request, String idServicio) {

        String tipoDocumento = request.getParameter(COBRO_SERVICIOS.TIPO_DOC) != null && !request.getParameter(COBRO_SERVICIOS.TIPO_DOC).isEmpty() ? request.getParameter(COBRO_SERVICIOS.TIPO_DOC) : null;
        String idDocumento = request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO) != null && !request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.ID_DOCUMENTO) : null;
        String nombres = request.getParameter(COBRO_SERVICIOS.NOMBRES) != null && !request.getParameter(COBRO_SERVICIOS.NOMBRES).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NOMBRES) : null;
        String apellidos = request.getParameter(COBRO_SERVICIOS.APELLIDOS) != null && !request.getParameter(COBRO_SERVICIOS.APELLIDOS).isEmpty() ? request.getParameter(COBRO_SERVICIOS.APELLIDOS) : null;
        Integer ciudad = request.getParameter(COBRO_SERVICIOS.CIUDAD) != null && !request.getParameter(COBRO_SERVICIOS.CIUDAD).isEmpty() ? new Integer(request.getParameter(COBRO_SERVICIOS.CIUDAD)) : null;
        String telefono = request.getParameter(COBRO_SERVICIOS.TELEFONO) != null && !request.getParameter(COBRO_SERVICIOS.TELEFONO).isEmpty() ? request.getParameter(COBRO_SERVICIOS.TELEFONO) : null;
        String movil = request.getParameter(COBRO_SERVICIOS.MOVIL) != null && !request.getParameter(COBRO_SERVICIOS.MOVIL).isEmpty() ? request.getParameter(COBRO_SERVICIOS.MOVIL) : null;
        String codCliente = request.getParameter(COBRO_SERVICIOS.COD_CLIENTE) != null && !request.getParameter(COBRO_SERVICIOS.COD_CLIENTE).isEmpty() ? request.getParameter(COBRO_SERVICIOS.COD_CLIENTE) : null;
        String codigoRemesa = request.getParameter(COBRO_SERVICIOS.COD_REMESA) != null && !request.getParameter(COBRO_SERVICIOS.COD_REMESA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.COD_REMESA) : null;
        String nroCuenta = request.getParameter(COBRO_SERVICIOS.NRO_CUENTA) != null && !request.getParameter(COBRO_SERVICIOS.NRO_CUENTA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.NRO_CUENTA) : null;
        String entidad = request.getParameter(COBRO_SERVICIOS.ENTIDAD_FINAN) != null && !request.getParameter(COBRO_SERVICIOS.ENTIDAD_FINAN).isEmpty() ? request.getParameter(COBRO_SERVICIOS.ENTIDAD_FINAN) : null;
        String comision = request.getParameter(COBRO_SERVICIOS.COMISION) != null && !request.getParameter(COBRO_SERVICIOS.COMISION).isEmpty() ? request.getParameter(COBRO_SERVICIOS.COMISION).replaceAll(",", "") : null;
        String montoBase = request.getParameter(COBRO_SERVICIOS.MONTO_BASE) != null && !request.getParameter(COBRO_SERVICIOS.MONTO_BASE).isEmpty() ? request.getParameter(COBRO_SERVICIOS.MONTO_BASE).replaceAll(",", "") : null;
        String pregSeguridad = request.getParameter(COBRO_SERVICIOS.PREG_SEG) != null && !request.getParameter(COBRO_SERVICIOS.PREG_SEG).isEmpty() ? request.getParameter(COBRO_SERVICIOS.PREG_SEG) : null;
        String respSeguridad = request.getParameter(COBRO_SERVICIOS.RESP_SEG) != null && !request.getParameter(COBRO_SERVICIOS.RESP_SEG).isEmpty() ? request.getParameter(COBRO_SERVICIOS.RESP_SEG) : null;
        String tabla = request.getParameter(COBRO_SERVICIOS.TABLA) != null && !request.getParameter(COBRO_SERVICIOS.TABLA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.TABLA) : null;
        String idTabla = request.getParameter(COBRO_SERVICIOS.ID_TABLA) != null && !request.getParameter(COBRO_SERVICIOS.ID_TABLA).isEmpty() ? request.getParameter(COBRO_SERVICIOS.ID_TABLA) : null;
        Long idSucursal = (Long) request.getSession().getAttribute("idSucursal");

        HashMapContainer hMap = new HashMapContainer();
        hMap.setMap(new HashMapContainer.Map());

        HashMapContainer.Map.Entry sucursalEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry tipoDocEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry idDocEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry entidadFinancEM = new HashMapContainer.Map.Entry();
        HashMapContainer.Map.Entry nroCuentaEM = new HashMapContainer.Map.Entry();

        sucursalEM.setKey("SUCURSAL");//afuera
        sucursalEM.setValue(idSucursal.toString());

        tipoDocEM.setKey("TIPO_DOC");//afuera
        tipoDocEM.setValue(tipoDocumento);

        idDocEM.setKey("ID_DOC");//afuera
        idDocEM.setValue(idDocumento);

        if (entidad != null) {
            entidadFinancEM.setKey("ENT_FINAN");//afuera
            entidadFinancEM.setValue(entidad);
            hMap.getMap().getEntry().add(entidadFinancEM);
        }
        if (nroCuenta != null) {
            nroCuentaEM.setKey("NRO_CUENTA");//afuera
            nroCuentaEM.setValue(nroCuenta);
            hMap.getMap().getEntry().add(nroCuentaEM);
        }

        hMap.getMap().getEntry().add(sucursalEM);
        hMap.getMap().getEntry().add(tipoDocEM);
        hMap.getMap().getEntry().add(idDocEM);

        if (idServicio.equals("131")) {//envio PG
            HashMapContainer.Map.Entry nombreEM = new HashMapContainer.Map.Entry();
            HashMapContainer.Map.Entry apellidoEM = new HashMapContainer.Map.Entry();
            HashMapContainer.Map.Entry comisionEM = new HashMapContainer.Map.Entry();
            HashMapContainer.Map.Entry montoBaseEM = new HashMapContainer.Map.Entry();
            HashMapContainer.Map.Entry idTablaEM = new HashMapContainer.Map.Entry();
            HashMapContainer.Map.Entry tablaEM = new HashMapContainer.Map.Entry();

            tablaEM.setKey("TABLA_COM");
            tablaEM.setValue(tabla);

            idTablaEM.setKey("ID_TABLA");
            idTablaEM.setValue(idTabla);

            comisionEM.setKey("COMISION");
            comisionEM.setValue(comision);

            montoBaseEM.setKey("MONTO_BASE");
            montoBaseEM.setValue(montoBase);

            nombreEM.setKey("NOMBRE");
            nombreEM.setValue(nombres);

            apellidoEM.setKey("APELLIDO");
            apellidoEM.setValue(apellidos);

            if (telefono != null) {
                HashMapContainer.Map.Entry telefonoEM = new HashMapContainer.Map.Entry();
                telefonoEM.setKey("TELEFONO");
                telefonoEM.setValue(telefono);
                hMap.getMap().getEntry().add(telefonoEM);
            }
            if (movil != null) {
                HashMapContainer.Map.Entry movilEM = new HashMapContainer.Map.Entry();
                movilEM.setKey("MOVIL");
                movilEM.setValue(movil);
                hMap.getMap().getEntry().add(movilEM);
            }
            if (ciudad != null) {
                HashMapContainer.Map.Entry ciudadEM = new HashMapContainer.Map.Entry();
                ciudadEM.setKey("CIUDAD");
                ciudadEM.setValue(ciudad.toString());
                hMap.getMap().getEntry().add(ciudadEM);
            }

            if (codCliente != null) {
                HashMapContainer.Map.Entry codClienteEM = new HashMapContainer.Map.Entry();
                codClienteEM.setKey("CLIENTE");
                codClienteEM.setValue(codCliente);
                hMap.getMap().getEntry().add(codClienteEM);
            }

            if (pregSeguridad != null) {
                HashMapContainer.Map.Entry pregSeguridadEM = new HashMapContainer.Map.Entry();
                pregSeguridadEM.setKey("PREG_SEG");
                pregSeguridadEM.setValue(pregSeguridad);
                hMap.getMap().getEntry().add(pregSeguridadEM);
            }

            if (respSeguridad != null) {
                HashMapContainer.Map.Entry respSeguridadEM = new HashMapContainer.Map.Entry();
                respSeguridadEM.setKey("RESP_SEG");
                respSeguridadEM.setValue(respSeguridad);
                hMap.getMap().getEntry().add(respSeguridadEM);
            }

            hMap.getMap().getEntry().add(nombreEM);
            hMap.getMap().getEntry().add(apellidoEM);
            hMap.getMap().getEntry().add(comisionEM);
            hMap.getMap().getEntry().add(montoBaseEM);
            hMap.getMap().getEntry().add(tablaEM);
            hMap.getMap().getEntry().add(idTablaEM);

        } else if (idServicio.equals("132")) {//retiro PG
            HashMapContainer.Map.Entry codRemesaEM = new HashMapContainer.Map.Entry();
            codRemesaEM.setKey("COD_REMESA");
            codRemesaEM.setValue(codigoRemesa);

            hMap.getMap().getEntry().add(codRemesaEM);
        }
        return hMap;
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
    private String makeTicketPurpura(HttpServletRequest request, HttpSession session, String cliente,
            String monto, String trx) {
        String patron = "CONSTANCIA DE RECEPCION\n"
                + "@REC_NAME\n"
                + "CI: @RUC / TEL: @TEL\n"
                + "DIRECCION: @DIRECCION\n"
                + "Terminal: @TERMINAL Suc: @SUC_NAME\n"
                + "Trans: " +trx+ " Usuar: @USER\n\n"
                + "Comprobante Pago\n"
                + "Servicio: Promocion Purpura\n"
                + "Importe: " + monto + " " + "\n";

        String ticketBody = "Razon Social: " + cliente + "\n";

        String footer = "FECHA: @FECHA - @HORA XXX\n"
                + "**ESTE ES SU COMPROBANTE, CONSERVELO**\n"
                + "        **** @RED ****        \n";
        /////////RELLENO
        Long idSucursal = (Long) request.getSession().getAttribute("idSucursal");
        UsuarioTerminal ut = (UsuarioTerminal) session.getAttribute("objUsuarioTerminal");

        Sucursal suc = sucursalFacade.get(idSucursal);
        Recaudador rec = suc.getRecaudador();
        Red red = suc.getRecaudador().getRed();

        patron = patron.replace("@REC_NAME", rec.getDescripcion());
        patron = patron.replace("@RUC", rec.getEntidad().getRucCi());
        patron = patron.replace("@TEL", suc.getTelefono());
        patron = patron.replace("@DIRECCION", suc.getDireccion());
        patron = patron.replace("@TERMINAL", ut.getTerminal().getCodigoTerminal().toString());
        patron = patron.replace("@SUC_NAME", suc.getDescripcion());
        patron = patron.replace("@USER", ut.getUsuario().getNombreUsuario());

        Date fecha = new Date();
        footer = footer.replace("@FECHA", DateUtils.getFormattedDate(fecha, "dd/MM/yyyy"));
        footer = footer.replace("@HORA", DateUtils.getFormattedDate(fecha, "HH:mm:ss"));
        footer = footer.replace("@RED", red.getDescripcion());

        ///////// RETURN
        patron += ticketBody + footer;
        return patron;
    }
    private String makeTicketRetencion(HttpServletRequest request, HttpSession session, String cliente,
            String idRetencion, String monto, String moneda) {
        String patron = "CONSTANCIA DE RECEPCION\n"
                + "@REC_NAME\n"
                + "RUC: @RUC / TEL: @TEL\n"
                + "DIRECCION: @DIRECCION\n"
                + "Terminal: @TERMINAL Suc: @SUC_NAME\n"
                + "Trans: " + idRetencion + " Usuar: @USER\n\n"
                + "Comprobante de Retencion\n"
                + "Servicio: Retenciones Cargill\n"
                + "Importe: " + monto + " " + moneda + "\n";

        String ticketBody = "Razon Social: " + cliente + "\n";

        String footer = "FECHA: @FECHA - @HORA XXX\n"
                + "**ESTE ES SU COMPROBANTE, CONSERVELO**\n"
                + "        **** @RED ****        \n";
        /////////RELLENO
        Long idSucursal = (Long) request.getSession().getAttribute("idSucursal");
        UsuarioTerminal ut = (UsuarioTerminal) session.getAttribute("objUsuarioTerminal");

        Sucursal suc = sucursalFacade.get(idSucursal);
        Recaudador rec = suc.getRecaudador();
        Red red = suc.getRecaudador().getRed();

        patron = patron.replace("@REC_NAME", rec.getDescripcion());
        patron = patron.replace("@RUC", rec.getEntidad().getRucCi());
        patron = patron.replace("@TEL", suc.getTelefono());
        patron = patron.replace("@DIRECCION", suc.getDireccion());
        patron = patron.replace("@TERMINAL", ut.getTerminal().getCodigoTerminal().toString());
        patron = patron.replace("@SUC_NAME", suc.getDescripcion());
        patron = patron.replace("@USER", ut.getUsuario().getNombreUsuario());

        Date fecha = new Date();
        footer = footer.replace("@FECHA", DateUtils.getFormattedDate(fecha, "dd/MM/yyyy"));
        footer = footer.replace("@HORA", DateUtils.getFormattedDate(fecha, "HH:mm:ss"));
        footer = footer.replace("@RED", red.getDescripcion());

        ///////// RETURN
        patron += ticketBody + footer;
        return patron;
    }
}