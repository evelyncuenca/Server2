/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import org.json.simple.JSONObject;
import org.netbeans.xml.schema.common.RespuestaCobranza;
import py.com.documenta.onlinemanager.ws.OlDetalleReferencia;
import py.com.documenta.ws.Autenticacion;
import py.com.documenta.ws.FormaPago;
import py.com.documenta.ws.HashMapContainer;
import py.com.documenta.ws.RedCobro;
import py.com.documenta.ws.RedCobroService;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;
import py.com.konecta.redcobros.ejb.GestionFacade;
import py.com.konecta.redcobros.ejb.ParametroSistemaFacade;
import py.com.konecta.redcobros.ejb.RuteoServicioFacade;
import py.com.konecta.redcobros.ejb.SucursalFacade;
import py.com.konecta.redcobros.ejb.TransaccionRcFacade;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.ParametroSistema;
import py.com.konecta.redcobros.entities.RuteoServicio;
import py.com.konecta.redcobros.entities.TransaccionRc;
import py.com.konecta.redcobros.entities.UsuarioTerminal;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
public class DesembolsoMasivo extends HttpServlet {

    private static final long FILE_SIZE_LIMIT = 20 * 1024 * 1024;

    @EJB
    private RuteoServicioFacade ruteoServicioFacade;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    private static ReentrantLock lock = new ReentrantLock();
    @EJB
    private TransaccionRcFacade transaccionRcFacade;
    private static RedCobroService service = null;
    @EJB
    private SucursalFacade sucursalFacade;
    @EJB
    private GestionFacade gestionFacade;

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
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        String proc = (String) request.getParameter("proc");
        HttpSession session = request.getSession();

        String conciliar = (String) session.getAttribute("conciliarCaja");
        Long codExtUsuario = (Long) session.getAttribute("codExtUsuario");
        String pathFile = "";
        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "proc = " + proc);
        try {
            if (conciliar != null && conciliar.equalsIgnoreCase("S")) {
                ParametroSistema paramFile = new ParametroSistema();
                paramFile.setNombreParametro("pathFileUser");
                pathFile = parametroSistemaFacade.get(paramFile).getValor();
            } else {
                codExtUsuario = null;//para que no escriba en archivo
            }

        } catch (Exception e) {
            Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        if (proc.equals("1")) {
            try {
                String cedula = (String) request.getParameter("CEDULA");
                String telefono = (String) request.getParameter("TELEFONO");
                String pin = (String) request.getParameter("PIN");
                String monto = ((String) request.getParameter("MONTO")).replaceAll("\\.", "");
                Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "values " + cedula + ";" + telefono + ";" + pin + ";" + monto);

                Integer idServicio = 5006;

                RuteoServicio ruteoRC = (RuteoServicio) request.getSession().getAttribute("ruteoServicio");

                if (ruteoRC == null) {
                    ruteoRC = ruteoServicioFacade.get(2L);
                    request.getSession().setAttribute("ruteoServicio", ruteoRC);
                }

                RedCobro rcService = getWSManager(ruteoRC.getUrlHost(), "http://ws.documenta.com.py/", "RedCobroService", ruteoRC.getConexionTo(), ruteoRC.getReadTo());

                FormaPago formaDePago = new FormaPago();
                formaDePago.setTipoPago(1);
                String server = request.getRequestURL().toString();
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

                HashMapContainer hMap = new HashMapContainer();
                hMap.setMap(new HashMapContainer.Map());

                HashMapContainer.Map.Entry mapEntry = new HashMapContainer.Map.Entry();
                mapEntry.setKey("CEDULA");
                mapEntry.setValue(cedula);
                hMap.getMap().getEntry().add(mapEntry);

                mapEntry = new HashMapContainer.Map.Entry();
                mapEntry.setKey("TELEFONO");
                mapEntry.setValue(telefono);
                hMap.getMap().getEntry().add(mapEntry);

                mapEntry = new HashMapContainer.Map.Entry();
                mapEntry.setKey("PIN");
                mapEntry.setValue(pin);
                hMap.getMap().getEntry().add(mapEntry);

                mapEntry = new HashMapContainer.Map.Entry();
                mapEntry.setKey("MONTO_COM");
                mapEntry.setValue(monto);
                hMap.getMap().getEntry().add(mapEntry);

                mapEntry = new HashMapContainer.Map.Entry();
                mapEntry.setKey("SERVER_APP");
                mapEntry.setValue(server);
                hMap.getMap().getEntry().add(mapEntry);

                OlDetalleReferencia detReferencia = new OlDetalleReferencia();
                detReferencia.setAcumulado(BigDecimal.ZERO);
                detReferencia.setMonto(new BigDecimal(monto));
                detReferencia.setReferenciaPago(telefono + ";" + cedula);
                detReferencia.setIdMoneda(600);//GS
                detReferencia.setTasa(1);

                RespuestaCobranza respCobranza = rcService.realizarCobranzaManual(idServicio, detReferencia, auth, formaDePago, null, hMap);
                request.getSession().setAttribute("respuestaCobranza", respCobranza);
                json.put("success", true);
            } catch (Exception e) {
                Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                json.put("success", false);
            } finally {
                out.println(json.toString());
            }
        } else {
            String servicioDescripcion = "Acreditaciones Masivas";
            request.getSession().setAttribute("servicio", servicioDescripcion);
            request.getSession().setAttribute("panel", "1");
            RespuestaCobranza respCobranza = (RespuestaCobranza) request.getSession().getAttribute("respuestaCobranza");
            String url;
            if (respCobranza.getEstado().getCodigoRetorno() == 0) {
                url = "ticketCyP.jsp";
                writeOnUserFile(pathFile, codExtUsuario, respCobranza.getIdTransaccion().toString(), false);
            } else {
                url = "errorCyP.jsp";
            }
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    public static RedCobro getWSManager(String url, String uri, String localPart, int connTo, int readTo) {
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

    private void writeOnUserFile(String pathFile, Long codExtUsuario, String idTrx, Boolean indicador) {
        try {
            TransaccionRc transRc = transaccionRcFacade.get(new BigDecimal(idTrx));
            Utiles.writeToFile(pathFile, codExtUsuario, transRc, indicador);
        } catch (Exception ex) {
            Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
