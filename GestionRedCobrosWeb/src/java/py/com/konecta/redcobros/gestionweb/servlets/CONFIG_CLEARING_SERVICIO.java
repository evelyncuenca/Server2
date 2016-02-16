/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.ConfigClearingServicioFacade;
import py.com.konecta.redcobros.entities.ConfigClearingServicio;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.ServicioRc;
import py.com.konecta.redcobros.entities.TipoClearing;

/**
 *
 * @author konecta
 */
public class CONFIG_CLEARING_SERVICIO extends HttpServlet {

    @EJB
    private ConfigClearingServicioFacade configClearinServicioFacade;
    public static String ID_CONFIG_CLEARING_SERVICIO = "ID_CONFIG_CLEARING_SERVICIO";
    public static String DESDE = "DESDE";
    public static String HASTA = "HASTA";
    public static String VALOR = "VALOR";
    public static String ID_RED = "RED";
    public static String RED = "RED";
    public static String SERVICIO = "SERVICIO";
    public static String ID_TIPO_CLEARING = "ID_TIPO_CLEARING";
    public static String ID_SERVICIO_RC = "ID_SERVICIO_RC";
    public static String TIPO_CLEARING = "TIPO_CLEARING";
    public static String SERVICIO_RC = "SERVICIO_RC";
    public static String MONTO = "MONTO";
    public static String HABILITADO = "HABILITADO";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String opcion = request.getParameter("op");
            Double desde = request.getParameter(CONFIG_CLEARING_SERVICIO.DESDE) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.DESDE).isEmpty() ? new Double(request.getParameter(CONFIG_CLEARING_SERVICIO.DESDE)) : null;
            Double hasta = request.getParameter(CONFIG_CLEARING_SERVICIO.HASTA) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.HASTA).isEmpty() ? new Double(request.getParameter(CONFIG_CLEARING_SERVICIO.HASTA)) : null;
            Double valor = request.getParameter(CONFIG_CLEARING_SERVICIO.VALOR) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.VALOR).isEmpty() ? new Double(request.getParameter(CONFIG_CLEARING_SERVICIO.VALOR)) : null;
            Long idRed = request.getParameter(CONFIG_CLEARING_SERVICIO.ID_RED) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.ID_RED).isEmpty() ? new Long(request.getParameter(CONFIG_CLEARING_SERVICIO.ID_RED)) : null;
            Integer idServicioRc = request.getParameter(CONFIG_CLEARING_SERVICIO.ID_SERVICIO_RC) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.ID_SERVICIO_RC).isEmpty() ? new Integer(request.getParameter(CONFIG_CLEARING_SERVICIO.ID_SERVICIO_RC)) : null;
            Long idTipoClearing = request.getParameter(CONFIG_CLEARING_SERVICIO.ID_TIPO_CLEARING) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.ID_TIPO_CLEARING).isEmpty() ? new Long(request.getParameter(CONFIG_CLEARING_SERVICIO.ID_TIPO_CLEARING)) : null;
            BigDecimal monto = request.getParameter(CONFIG_CLEARING_SERVICIO.MONTO) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.MONTO).isEmpty() ? new BigDecimal(request.getParameter(CONFIG_CLEARING_SERVICIO.MONTO)) : null;
            String habilitado = request.getParameter(CONFIG_CLEARING_SERVICIO.HABILITADO) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.HABILITADO).isEmpty() ? request.getParameter(CONFIG_CLEARING_SERVICIO.HABILITADO) : null;
            Long idConfigClearingServicios = request.getParameter(CONFIG_CLEARING_SERVICIO.ID_CONFIG_CLEARING_SERVICIO) != null && !request.getParameter(CONFIG_CLEARING_SERVICIO.ID_CONFIG_CLEARING_SERVICIO).isEmpty() ? new Long(request.getParameter(CONFIG_CLEARING_SERVICIO.ID_CONFIG_CLEARING_SERVICIO)) : null;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            JSONObject jsonRespuesta = new JSONObject();
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                try {
                    ConfigClearingServicio newConfigClearingServicio = new ConfigClearingServicio();
                    newConfigClearingServicio.setDesde(desde);
                    newConfigClearingServicio.setHasta(hasta);
                    newConfigClearingServicio.setValor(valor);
                    Red red = new Red();
                    red.setIdRed(idRed);
                    newConfigClearingServicio.setRed(red);
                    if (idServicioRc != null) {
                        ServicioRc src = new ServicioRc();
                        src.setIdServicio(idServicioRc);
                        newConfigClearingServicio.setServicioRc(src);
                    }
                    TipoClearing tipoClearing = new TipoClearing();
                    tipoClearing.setIdTipoClearing(idTipoClearing);
                    newConfigClearingServicio.setTipoClearing(tipoClearing);
                    newConfigClearingServicio.setMonto(monto);
                    newConfigClearingServicio.setHabilitado(habilitado);
                    configClearinServicioFacade.merge(newConfigClearingServicio);
                    jsonRespuesta.put("success", true);
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonRespuesta.put("success", false);
                }

                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                try {
                    configClearinServicioFacade.delete(idConfigClearingServicios);
                    jsonRespuesta.put("success", true);
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonRespuesta.put("success", false);
                }
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                try {
                    ConfigClearingServicio configClearingServicio = new ConfigClearingServicio();
                    configClearingServicio.setIdConfigClearingServicio(idConfigClearingServicios);
                    configClearingServicio.setDesde(desde);
                    configClearingServicio.setHasta(hasta);
                    configClearingServicio.setValor(valor);
                    Red red = new Red();
                    red.setIdRed(idRed);
                    configClearingServicio.setRed(red);
                    if (idServicioRc != null) {
                        ServicioRc src = new ServicioRc();
                        src.setIdServicio(idServicioRc);
                        configClearingServicio.setServicioRc(src);
                    }
                    TipoClearing tipoClearing = new TipoClearing();
                    tipoClearing.setIdTipoClearing(idTipoClearing);
                    configClearingServicio.setTipoClearing(tipoClearing);
                    configClearingServicio.setMonto(monto);
                    configClearingServicio.setHabilitado(habilitado);
                    configClearinServicioFacade.update(configClearingServicio);
                    jsonRespuesta.put("success", true);
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonRespuesta.put("success", false);
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {//cambiar el LISTAR por DETALLE

                ConfigClearingServicio ejemplo = new ConfigClearingServicio();

                List<ConfigClearingServicio> lConfigClearingServicios = configClearinServicioFacade.list(ejemplo, Integer.parseInt(primerResultado),
                        Integer.parseInt(cantResultados));

                JSONArray jsonConfigClearingServicios = new JSONArray();
                for (ConfigClearingServicio e : lConfigClearingServicios) {
                    JSONObject jsonConfigClearingServicio = new JSONObject();
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.DESDE, e.getDesde() != null ? e.getDesde() : "");
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.HASTA, e.getHasta() != null ? e.getHasta() : "");
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.MONTO, e.getMonto() != null ? e.getMonto() : "");
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.ID_CONFIG_CLEARING_SERVICIO, e.getIdConfigClearingServicio());
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.RED, e.getRed().getDescripcion());
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.HABILITADO, e.getHabilitado());
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.SERVICIO_RC, e.getServicioRc() != null ? e.getServicioRc().getDescripcion() : "");
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.TIPO_CLEARING, e.getTipoClearing().getDescripcion());
                    jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.VALOR, e.getValor());
                    jsonConfigClearingServicios.add(jsonConfigClearingServicio);
                }

                jsonRespuesta.put("CONFIG_CLEARING_SERVICIO", jsonConfigClearingServicios);
                jsonRespuesta.put("TOTAL", configClearinServicioFacade.total(ejemplo));

                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("DETALLE")) {//cambiar el LISTAR por DETALLE

                ConfigClearingServicio ejemplo = new ConfigClearingServicio();

                ejemplo.setIdConfigClearingServicio(idConfigClearingServicios);

                ConfigClearingServicio configClearing = configClearinServicioFacade.get(ejemplo);

                JSONObject jsonConfigClearingServicio = new JSONObject();
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.DESDE, configClearing.getDesde() != null ? configClearing.getDesde() : "");
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.HASTA, configClearing.getHasta() != null ? configClearing.getHasta() : "");
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.MONTO, configClearing.getMonto() != null ? configClearing.getMonto() : "");
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.ID_CONFIG_CLEARING_SERVICIO, configClearing.getIdConfigClearingServicio());
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.RED, configClearing.getRed().getDescripcion());
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.ID_RED, configClearing.getRed().getIdRed());
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.HABILITADO, configClearing.getHabilitado());
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.SERVICIO_RC, configClearing.getServicioRc() != null ? configClearing.getServicioRc().getDescripcion() : "");
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.TIPO_CLEARING, configClearing.getTipoClearing().getDescripcion());
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.ID_SERVICIO_RC, configClearing.getServicioRc() != null ? configClearing.getServicioRc().getIdServicio() : "");
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.ID_TIPO_CLEARING, configClearing.getTipoClearing().getIdTipoClearing());
                jsonConfigClearingServicio.put(CONFIG_CLEARING_SERVICIO.VALOR, configClearing.getValor());

                jsonRespuesta.put("data", jsonConfigClearingServicio);

                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());

            } else {
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
                out.println(jsonRespuesta.toString());
            }
        } finally {
            //out.close();
        }
    }

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
