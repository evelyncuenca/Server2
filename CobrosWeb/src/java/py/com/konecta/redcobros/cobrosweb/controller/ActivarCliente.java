/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.json.simple.JSONObject;
import org.netbeans.xml.schema.common.EstadoTransaccion;
import org.netbeans.xml.schema.common.RespuestaCobranza;
import py.com.konecta.accounteracti.Response;
import py.com.konecta.accounteracti.ServiceActivacion;
import py.com.konecta.accounteracti.ServiceActivacion_Service;
import py.com.konecta.redcobros.cobrosweb.utiles.DateUtils;
import py.com.konecta.redcobros.ejb.ParametroSistemaFacade;
import py.com.konecta.redcobros.ejb.SucursalFacade;
import py.com.konecta.redcobros.entities.ParametroSistema;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.entities.UsuarioTerminal;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
public class ActivarCliente extends HttpServlet {

    private static final long FILE_SIZE_LIMIT = 20 * 1024 * 1024;

    @EJB
    private SucursalFacade sucursalFacade;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        String proc = (String) request.getParameter("proc");
        if (proc.equals("1")) {
            try {
                DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
                ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
                fileUpload.setSizeMax(FILE_SIZE_LIMIT);
                List<FileItem> items = fileUpload.parseRequest(request);
                byte[] cedulaFrontal = null;
                byte[] cedulaReverso = null;

                HashMap mapValues = new HashMap();
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Recibido del Formulario:");
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Nombre: " + item.getFieldName());
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Valor: " + item.getString());
                        mapValues.put(item.getFieldName(), item.getString());

                    } else {
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Archivo Recibido:");
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Nombre: " + item.getName());
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Tamaño: " + item.getSize());
                        if (item.getSize() > FILE_SIZE_LIMIT) {
                            response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "El archivo excede el limite");
                            return;
                        }
                        if (item.getFieldName().equals("CEDULA_FRONTAL")) {
                            cedulaFrontal = item.get();
                            try {
                            } catch (Exception e) {
                                Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                                json.put("success", false);
                                json.put("motivo", "Frontal Cedula no cargado");
                            }
                        }
                        if (item.getFieldName().equals("CEDULA_REVERSO")) {
                            cedulaReverso = item.get();
                            try {
                            } catch (Exception e) {
                                Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                                json.put("success", false);
                                json.put("motivo", "Reverso Cedula no cargado");
                            }
                        }
                        if (!item.isInMemory()) {
                            item.delete();
                        }
                    }
                }

                //if (cedulaFrontal == null || cedulaReverso == null) {
                //    json.put("success", false);
                //} else {
                    json.put("success", true);
                //}

                try {
                    ParametroSistema ejemplo = new ParametroSistema();
                    ejemplo.setNombreParametro("URL_WS_ACTIVACION");
                    ParametroSistema ps = parametroSistemaFacade.get(ejemplo);

                    ServiceActivacion port = getWSManager(ps.getValor(), null, null);
                    py.com.konecta.accounteracti.Pojo pojo = new py.com.konecta.accounteracti.Pojo();

                    pojo.setSexo(mapValues.get("SEXO").toString());
                    pojo.setPrimerNombre(mapValues.get("PRIMER_NOMBRE").toString());
                    pojo.setSegundoNombre(mapValues.get("SEGUNDO_NOMBRE").toString());
                    pojo.setPrimerApellido(mapValues.get("PRIMER_APELLIDO").toString());
                    pojo.setSegundoApellido(mapValues.get("SEGUNDO_APELLIDO").toString());
                    pojo.setNumeroDocumento(mapValues.get("NRO_DOC").toString());
                    pojo.setFechaNacimiento(mapValues.get("FECHA_NAC").toString());
                    pojo.setNumeroRuc(mapValues.get("RUC").toString());
                    pojo.setDireccion(mapValues.get("DIRECCION").toString());
                    pojo.setTelefonoFijo(mapValues.get("TELEFONO_FIJO").toString());
                    pojo.setTelefonoMovil(mapValues.get("TELEFONO_MOVIL").toString());
                    pojo.setBarrio(mapValues.get("BARRIO").toString());
                    pojo.setEmail(mapValues.get("EMAIL").toString());

                    pojo.setCiudad(mapValues.get("CIUDAD").toString());
                    pojo.setCodigoPostal(mapValues.get("CODIGO_POSTAL").toString());
                    pojo.setDepartamento(mapValues.get("DEPARTAMENTO").toString());
                    pojo.setEstadoCivil(mapValues.get("ESTADO_CIVIL").toString());
                    pojo.setPiso(mapValues.get("PISO").toString());

                    pojo.setCedulaFrontal(cedulaFrontal);
                    pojo.setCedulaReversa(cedulaReverso);

                    py.com.konecta.accounteracti.Response result = port.activarCliente(pojo);
                    if (result.getStatus() == 0) {
                        RespuestaCobranza respCobranza = new RespuestaCobranza();
                        respCobranza.setTicket(makeTicket(request.getSession(), mapValues));
                        String servicioDescripcion = "KYC";
                        request.getSession().setAttribute("respuestaCobranza", respCobranza);
                        request.getSession().setAttribute("servicio", servicioDescripcion);
                    } else {
                        request.getSession().setAttribute("mensajeError", result.getMensaje());
                        json.put("success", false);
                        json.put("motivo", result.getMensaje());
                    }
                    request.getSession().setAttribute("panel", "1");

                    Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Result KYC = " + result.getStatus() + ";" + result.getMensaje());
                } catch (Exception ex) {
                    Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

                response.setContentType("text/html;charset=UTF-8");
                out.println(json.toString());

            } catch (Exception e) {
                Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        } else if (proc.equals("3")) {
            ParametroSistema ejemplo = new ParametroSistema();
            ejemplo.setNombreParametro("URL_WS_ACTIVACION");
            ParametroSistema ps = parametroSistemaFacade.get(ejemplo);
            ServiceActivacion port = getWSManager(ps.getValor(), null, null);
            Response r = port.validarExisteCuenta("TEMP_TELEFONO", request.getParameter("CEDULA"));
            json = new JSONObject();
            json.put("status", r.getStatus());
            json.put("success", true);
            out.println(json.toString());
        } else if (proc.equals("4")) {
            try {
                DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
                ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
                fileUpload.setSizeMax(FILE_SIZE_LIMIT);
                List<FileItem> items = fileUpload.parseRequest(request);
                byte[] cedulaFrontal = null;
                byte[] cedulaReverso = null;

                HashMap mapValues = new HashMap();
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Recibido del Formulario:");
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Nombre: " + item.getFieldName());
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Valor: " + item.getString());
                        mapValues.put(item.getFieldName(), item.getString());

                    } else {
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Archivo Recibido:");
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Nombre: " + item.getName());
                        Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Tamaño: " + item.getSize());
                        if (item.getSize() > FILE_SIZE_LIMIT) {
                            response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "El archivo excede el limite");
                            return;
                        }
                        if (item.getFieldName().equals("CEDULA_FRONTAL")) {
                            cedulaFrontal = item.get();
                            try {
                            } catch (Exception e) {
                                Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                                json.put("success", false);
                                json.put("motivo", "Frontal Cedula no cargado");
                            }
                        }
                        if (item.getFieldName().equals("CEDULA_REVERSO")) {
                            cedulaReverso = item.get();
                            try {
                            } catch (Exception e) {
                                Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                                json.put("success", false);
                                json.put("motivo", "Reverso cedula no cargado");
                            }
                        }
                        if (!item.isInMemory()) {
                            item.delete();
                        }
                    }
                }

                //if (cedulaFrontal == null || cedulaReverso == null) {
                //    json.put("success", false);
                //} else {
                    json.put("success", true);
                //}

                try {
                    ParametroSistema ejemplo = new ParametroSistema();
                    ejemplo.setNombreParametro("URL_WS_ACTIVACION");
                    ParametroSistema ps = parametroSistemaFacade.get(ejemplo);

                    ServiceActivacion port = getWSManager(ps.getValor(), null, null);
                    py.com.konecta.accounteracti.Pojo pojo = new py.com.konecta.accounteracti.Pojo();

                    pojo.setSexo(mapValues.get("SEXO").toString());
                    pojo.setPrimerNombre(mapValues.get("PRIMER_NOMBRE").toString());
                    pojo.setSegundoNombre(mapValues.get("SEGUNDO_NOMBRE").toString());
                    pojo.setPrimerApellido(mapValues.get("PRIMER_APELLIDO").toString());
                    pojo.setSegundoApellido(mapValues.get("SEGUNDO_APELLIDO").toString());
                    pojo.setNumeroDocumento(mapValues.get("NRO_DOC").toString());
                    pojo.setFechaNacimiento(mapValues.get("FECHA_NAC").toString());
                    pojo.setNumeroRuc(mapValues.get("RUC").toString());
                    pojo.setDireccion(mapValues.get("DIRECCION").toString());
                    pojo.setTelefonoFijo(mapValues.get("TELEFONO_FIJO").toString());
                    pojo.setTelefonoMovil(mapValues.get("TELEFONO_MOVIL").toString());
                    pojo.setBarrio(mapValues.get("BARRIO").toString());
                    pojo.setEmail(mapValues.get("EMAIL").toString());

                    pojo.setCiudad(mapValues.get("CIUDAD").toString());
                    pojo.setCodigoPostal(mapValues.get("CODIGO_POSTAL").toString());
                    pojo.setDepartamento(mapValues.get("DEPARTAMENTO").toString());
                    pojo.setEstadoCivil(mapValues.get("ESTADO_CIVIL").toString());
                    pojo.setPiso(mapValues.get("PISO").toString());

                    pojo.setCedulaFrontal(cedulaFrontal);
                    pojo.setCedulaReversa(cedulaReverso);

                    py.com.konecta.accounteracti.Response result = port.crearCuenta(pojo);
                    if (result.getStatus() == 0) {
                        RespuestaCobranza respCobranza = new RespuestaCobranza();
                        respCobranza.setTicket(makeTicket(request.getSession(), mapValues));
                        EstadoTransaccion estado = new EstadoTransaccion();
                        estado.setCodigoRetorno(0);
                        estado.setDescripcion("OK");
                        respCobranza.setEstado(estado);
                        String servicioDescripcion = "KYC";
                        request.getSession().setAttribute("respuestaCobranza", respCobranza);
                        request.getSession().setAttribute("servicio", servicioDescripcion);
                        request.getSession().setAttribute("printTicket", true);
                    } else {
                        request.getSession().setAttribute("mensajeError", result.getMensaje());
                        json.put("success", false);
                        json.put("motivo", result.getMensaje());
                    }
                    request.getSession().setAttribute("panel", "1");

                    Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.INFO, "Result KYC = " + result.getStatus() + ";" + result.getMensaje());
                } catch (Exception ex) {
                    Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

                response.setContentType("text/html;charset=UTF-8");
                out.println(json.toString());

            } catch (Exception e) {
                Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        } else {
            String url = null;
            if (request.getSession().getAttribute("respuestaCobranza") != null) {
                url = "ticketCyP.jsp";
            } else {
                url = "error.jsp";
            }
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    private String makeTicket(HttpSession session, HashMap map) {
        String patron = "FORMULARIO DE DECLARACION DE OPERACIONES\n"
                + "@REC_NAME\n"
                + "RUC: @RUC / TEL: @TEL\n"
                + "DIRECCION: @DIRECCION\n"
                + "Terminal: @TERMINAL \n"
                + "Suc: @SUC_NAME\n"
                + "Usuar: @USER\n\n"
                + "Comprobante de KYC\n"
                + "Servicio: KYC\n\n\n";

        String ticketBody = "Primer Nombre: " + map.get("PRIMER_NOMBRE") + "\n";
        ticketBody += "Segundo Nombre: " + map.get("SEGUNDO_NOMBRE") + "\n";
        ticketBody += "Primer Apellido: " + map.get("PRIMER_APELLIDO") + "\n";
        ticketBody += "Segundo Apellido: " + map.get("SEGUNDO_APELLIDO") + "\n";
        ticketBody += "Nro Doc. : " + (map.get("NRO_DOC") != null ? new MonedaBilletera(map.get("NRO_DOC").toString()) : "");
        ticketBody += "\n";
        ticketBody += "Fecha Nacimiento: " + map.get("FECHA_NAC") + "\n";
        ticketBody += "Ruc: " + (map.get("RUC") != null ? new MonedaBilletera(map.get("RUC").toString()) : "");
        ticketBody += "\n";
        ticketBody += "Sexo: " + map.get("SEXO") + "\n";
        ticketBody += "Direccion: " + map.get("DIRECCION") + "\n";
        ticketBody += "Telefono Fijo: " + map.get("TELEFONO_FIJO") + "\n";
        ticketBody += "Telefono Movil: " + map.get("TELEFONO_MOVIL") + "\n";
        if (map.get("EMAIL") != null) {
            if (!map.get("EMAIL").toString().isEmpty()) {
                ticketBody += "Email: " + map.get("EMAIL") + "\n";
            }
        }
        ticketBody += "Lugar de Nacimiento: " + map.get("DEPARTAMENTO") + "\n";
        ticketBody += "Tipo Documento: " + map.get("PISO") + "\n";
        ticketBody += "Profesion: " + map.get("CODIGO_POSTAL") + "\n";
        ticketBody += "\nFirma: ...........................\n\n\n";

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

    private static ServiceActivacion_Service service = null;
    private static ReentrantLock lock = new ReentrantLock();

    public static ServiceActivacion getWSManager(String url, String uri, String localPart) {
        ServiceActivacion pexSoap;

        URL wsdlURL = ServiceActivacion_Service.class.getClassLoader().getResource("schema/ServiceActivacion.wsdl");
        try {
            if (service == null) {
                try {
                    lock.lock();
                    if (service == null) {
                        service = new ServiceActivacion_Service(wsdlURL, new QName("http://accounteracti.konecta.com.py/", "ServiceActivacion"));
                    }
                } catch (Exception ex) {
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception ex) {
        }

        pexSoap = service.getServiceActivacionPort();
        BindingProvider provider = (BindingProvider) pexSoap;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        return pexSoap;
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
