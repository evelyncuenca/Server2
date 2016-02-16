/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.netbeans.xml.schema.common.RespuestaCobranza;
import py.com.documenta.onlinemanager.ws.OlDetalleReferencia;
import py.com.documenta.ws.Autenticacion;
import py.com.documenta.ws.FormaPago;
import py.com.documenta.ws.HashMapContainer;
import py.com.documenta.ws.RedCobro;
import py.com.documenta.ws.RedCobroService;
import py.com.konecta.redcobros.cobrosweb.utiles.DateUtils;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;
import py.com.konecta.redcobros.ejb.GestionFacade;
import py.com.konecta.redcobros.ejb.ParametroSistemaFacade;
import py.com.konecta.redcobros.ejb.RuteoServicioFacade;
import py.com.konecta.redcobros.ejb.SucursalFacade;
import py.com.konecta.redcobros.ejb.TransaccionRcFacade;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.ParametroSistema;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.RuteoServicio;
import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.entities.TransaccionRc;
import py.com.konecta.redcobros.entities.UsuarioTerminal;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
public class AdelantoEfectivo extends HttpServlet {

    private static final long FILE_SIZE_LIMIT = 20 * 1024 * 1024;

    @EJB
    private RuteoServicioFacade ruteoServicioFacade;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    private static final ReentrantLock lock = new ReentrantLock();
    @EJB
    private TransaccionRcFacade transaccionRcFacade;
    private static RedCobroService service = null;
    @EJB
    private SucursalFacade sucursalFacade;
    @EJB
    private GestionFacade gestionFacade;
    static final Logger logger = Logger.getLogger(AdelantoEfectivo.class.getName());

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
        logger.info("proc = " + proc);
        try {
            if (conciliar != null && conciliar.equalsIgnoreCase("S")) {
                ParametroSistema paramFile = new ParametroSistema();
                paramFile.setNombreParametro("pathFileUser");
                pathFile = parametroSistemaFacade.get(paramFile).getValor();
            } else {
                codExtUsuario = null;//para que no escriba en archivo
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        if (proc.equals("1")) {
            try {
                DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
                ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
                fileUpload.setSizeMax(FILE_SIZE_LIMIT);
                List<FileItem> items = fileUpload.parseRequest(request);
                byte[] archivoXLS = null;

                for (FileItem item : items) {
                    if (item.isFormField()) {
                        logger.info("Recibido del Formulario:");
                        logger.log(Level.INFO, "Nombre: {0}", item.getFieldName());
                        logger.log(Level.INFO, "Valor: {0}", item.getString());
                    } else {
                        logger.info("Archivo Recibido:");
                        logger.log(Level.INFO, "Nombre: {0}", item.getName());
                        logger.log(Level.INFO, "Tama\u00f1o: {0}", item.getSize());
                        if (item.getSize() > FILE_SIZE_LIMIT) {
                            response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "El archivo excede el limite");
                            return;
                        }
                        if (item.getFieldName().equals("ARCHIVO_XLS")) {
                            archivoXLS = item.get();
                            request.getSession().setAttribute("archivo", archivoXLS);
                        }
                        if (!item.isInMemory()) {
                            item.delete();
                        }
                    }
                }

                if (archivoXLS == null) {
                    json.put("success", false);
                } else {
                    json.put("success", true);
                }
                response.setContentType("text/html;charset=UTF-8");
                out.println(json.toString());

            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } else if (proc.equals("2")) {
            byte[] archivoXLS = (byte[]) request.getSession().getAttribute("archivo");
            Integer start;
            Integer limit;
            try {
                start = Integer.parseInt(request.getParameter("start"));
            } catch (Exception e) {
                start = 0;
            }
            try {
                limit = Integer.parseInt(request.getParameter("limit"));
            } catch (Exception e) {
                limit = 5;
            }
            Integer end = start + limit;
            InputStream is = null;
            BufferedReader bfReader = null;
            try {
                Integer idServicio = 5004;
                is = new ByteArrayInputStream(archivoXLS);
                bfReader = new BufferedReader(new InputStreamReader(is));
                String temp = null;
                int i = 0;
                JSONArray jArray = new JSONArray();
                while ((temp = bfReader.readLine()) != null) {
                    if (i >= start && i < end) {
                        try {
                            String[] values = temp.split(";;");
                            JSONObject responseJSON = new JSONObject();
                            responseJSON.put("idCliente", i + 1);
                            responseJSON.put("nombresApellidos", values[0] + " " + values[1] + " " + values[2] + " " + values[3]);
                            responseJSON.put("cedula", values[4]);
                            responseJSON.put("fechaNac", values[5] + "-" + values[6] + "-" + values[7]);
                            responseJSON.put("direccion", values[9]);
                            responseJSON.put("sexo", values[15]);
                            responseJSON.put("telefonoFijo", values[16]);
                            responseJSON.put("telefonoMovil", values[17]);
                            responseJSON.put("monto", values[18]);
                            responseJSON.put("status", "Pendiente");
                            jArray.add(responseJSON);
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, e.getMessage(), e);
                            json.put("success", false);
                        }
                    }
                    i++;
                }
                json = new JSONObject();
                json.put("success", true);
                json.put("total", i);
                json.put("clientes", jArray);
                out.println(json.toString());
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                json.put("success", false);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                    json.put("success", false);
                }
            }

        } else if (proc.equals("3")) {
            String[] array = new String[]{"primerNombre", "segundoNombre", "primerApellido", "segundoApellido", "numeroDocumento", "diaNacimiento", "mesNacimiento", "anhoNacimiento", "estadoCivil", "direccion", "departamento", "ciudad", "barrio", "piso", "codigoPostal", "sexo", "telefonoFijo", "telefonoMovil", "monto"};
            InputStream is = null;
            BufferedReader bfReader = null;
            try {
                byte[] archivoXLS = (byte[]) request.getSession().getAttribute("archivo");
                Integer idServicio = 5004;
                is = new ByteArrayInputStream(archivoXLS);
                bfReader = new BufferedReader(new InputStreamReader(is));
                String temp = null;
                while ((temp = bfReader.readLine()) != null) {
                    try {
                        String[] values = temp.split(";;");
                        int i = 0;
                        logger.log(Level.INFO, "Longitud: {0}", values.length);
                        if (values.length == array.length) {

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

                            String monto = "";
                            String telefonoMovil = "";

                            for (String str : array) {
                                logger.info(values[i]);
                                if (str.equals("telefonoMovil")) {
                                    telefonoMovil = values[i].trim();
                                }

                                if (str.equals("monto")) {
                                    monto = values[i].trim();
                                } else {
                                    HashMapContainer.Map.Entry mapEntry = new HashMapContainer.Map.Entry();
                                    mapEntry.setKey(str);
                                    mapEntry.setValue(values[i].trim());
                                    hMap.getMap().getEntry().add(mapEntry);
                                }
                                i++;
                            }

                            HashMapContainer.Map.Entry mapEntry = new HashMapContainer.Map.Entry();
                            mapEntry.setKey("SERVER_APP");
                            mapEntry.setValue(server);
                            hMap.getMap().getEntry().add(mapEntry);

                            OlDetalleReferencia detReferencia = new OlDetalleReferencia();
                            detReferencia.setAcumulado(BigDecimal.ZERO);
                            detReferencia.setMonto(new BigDecimal(monto));
                            detReferencia.setReferenciaPago(telefonoMovil);
                            detReferencia.setIdMoneda(600);//GS
                            detReferencia.setTasa(1);

                            RespuestaCobranza respCobranza = rcService.realizarCobranzaManual(idServicio, detReferencia, auth, formaDePago, null, hMap);
                            respCobranza = new RespuestaCobranza();
                            respCobranza.setTicket(makeTicket(request.getSession()));
                            request.getSession().setAttribute("respuestaCobranza", respCobranza);

                        } else {
                            json.put("success", false);
                        }
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, e.getMessage(), e);
                        json.put("success", false);
                    }

                }

            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                json.put("success", false);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                    json.put("success", false);
                }
            }
        } else {
            String servicioDescripcion = "Acreditaciones Masivas";
            request.getSession().setAttribute("servicio", servicioDescripcion);
            request.getSession().setAttribute("panel", "1");
            RequestDispatcher rd = request.getRequestDispatcher("ticketCyP.jsp");
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

    private String makeTicket(HttpSession session) {
        String patron = "CONSTANCIA DE RECEPCION\n"
                + "@REC_NAME\n"
                + "RUC: @RUC / TEL: @TEL\n"
                + "DIRECCION: @DIRECCION\n"
                + "Terminal: @TERMINAL \n"
                + "Suc: @SUC_NAME\n"
                + "Usuar: @USER\n\n"
                + "Comprobante de KYC\n"
                + "Servicio: KYC Banco Continental\n\n\n";

        String ticketBody = "";

        String footer = "FECHA: @FECHA - @HORA \n"
                + "        **** @RED ****        \n";

        Long idSucursal = (Long) session.getAttribute("idSucursal");
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

    private void writeOnUserFile(String pathFile, Long codExtUsuario, String idTrx, Boolean indicador) {
        try {
            TransaccionRc transRc = transaccionRcFacade.get(new BigDecimal(idTrx));
            Utiles.writeToFile(pathFile, codExtUsuario, transRc, indicador);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
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
