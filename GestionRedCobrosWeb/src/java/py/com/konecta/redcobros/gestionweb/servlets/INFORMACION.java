/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.InformacionFacade;
import py.com.konecta.redcobros.entities.Informacion;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.entities.Terminal;

/**
 *
 * @author konecta
 */
public class INFORMACION extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private InformacionFacade informacionFacade;
    public static String ID_INFORMACION = "ID_INFORMACION";
    public static String RED = "RED";
    public static String RECAUDADOR = "RECAUDADOR";
    public static String SUCURSAL = "SUCURSAL";
    public static String TERMINAL = "TERMINAL";
    public static String MENSAJE = "MENSAJE";
    public static String FECHA_INI = "FECHA_INI";
    public static String FECHA_FIN = "FECHA_FIN";
    public static String ACTIVO = "ACTIVO";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String opcion = request.getParameter("op");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Integer idInformacion = request.getParameter(INFORMACION.ID_INFORMACION) != null && !request.getParameter(INFORMACION.ID_INFORMACION).isEmpty() ? Integer.valueOf(request.getParameter(INFORMACION.ID_INFORMACION)) : null;
            Long idRed = request.getParameter(INFORMACION.RED) != null && !request.getParameter(INFORMACION.RED).isEmpty() ? Long.valueOf(request.getParameter(INFORMACION.RED)) : null;
            Long idRecaudador = request.getParameter(INFORMACION.RECAUDADOR) != null && !request.getParameter(INFORMACION.RECAUDADOR).isEmpty() ? Long.valueOf(request.getParameter(INFORMACION.RECAUDADOR)) : null;
            Long idSucursal = request.getParameter(INFORMACION.SUCURSAL) != null && !request.getParameter(INFORMACION.SUCURSAL).isEmpty() ? Long.valueOf(request.getParameter(INFORMACION.SUCURSAL)) : null;
            Long idTerminal = request.getParameter(INFORMACION.TERMINAL) != null && !request.getParameter(INFORMACION.TERMINAL).isEmpty() ? Long.valueOf(request.getParameter(INFORMACION.TERMINAL)) : null;
            String mensaje = request.getParameter(INFORMACION.MENSAJE) != null && !request.getParameter(INFORMACION.MENSAJE).isEmpty() ? request.getParameter(INFORMACION.MENSAJE) : null;
            Date fechaIni = null;
            Date fechaFin = null;
            try {
                fechaIni = request.getParameter(INFORMACION.FECHA_INI) != null && !request.getParameter(INFORMACION.FECHA_INI).isEmpty() ? sdf.parse(request.getParameter(INFORMACION.FECHA_INI)) : null;
                fechaFin = request.getParameter(INFORMACION.FECHA_FIN) != null && !request.getParameter(INFORMACION.FECHA_FIN).isEmpty() ? sdf.parse(request.getParameter(INFORMACION.FECHA_FIN)) : null;
            } catch (ParseException ex) {
                Logger.getLogger(INFORMACION.class.getName()).log(Level.SEVERE, null, ex);
            }

            String activo = request.getParameter(INFORMACION.ACTIVO) != null && !request.getParameter(INFORMACION.ACTIVO).isEmpty() ? request.getParameter(INFORMACION.ACTIVO) : null;

            String primerResultado = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? request.getParameter("start") : null;
            String cantResultados = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? request.getParameter("limit") : null;

            JSONObject jsonRespuesta = new JSONObject();

            if (opcion.equalsIgnoreCase("LISTAR")) {

                Informacion ejemplo = new Informacion();

//                if (critConsulta != null && critConsulta.matches("[0-9]+")) {
//                    ejemplo.setRucNuevo(critConsulta);
//                } else if (critConsulta != null && !critConsulta.isEmpty()) {
//                    ejemplo.setNombreContribuyente(critConsulta);
//                }
                List<Informacion> lInformaciones = new ArrayList<Informacion>();
                if (primerResultado != null && cantResultados != null) {
                    lInformaciones =
                            informacionFacade.list(ejemplo,
                            Integer.parseInt(primerResultado),
                            Integer.parseInt(cantResultados), "idInformacion", "asc", true);
                } else {
                    lInformaciones = informacionFacade.list(ejemplo, "idInformacion", "asc", true);
                }
                JSONArray jsonInformaciones = new JSONArray();
                for (Informacion it : lInformaciones) {
                    JSONObject jsonInformacion = new JSONObject();
                    jsonInformacion.put(INFORMACION.ID_INFORMACION, it.getIdInformacion());
                    jsonInformacion.put(INFORMACION.RED, it.getRed() != null ? it.getRed().getIdRed() : "");
                    jsonInformacion.put(INFORMACION.RECAUDADOR, it.getRecaudador() != null ? it.getRecaudador().getIdRecaudador() : "");
                    jsonInformacion.put(INFORMACION.SUCURSAL, it.getSucursal() != null ? it.getSucursal().getIdSucursal() : "");
                    jsonInformacion.put(INFORMACION.TERMINAL, it.getTerminal() != null ? it.getTerminal().getIdTerminal() : "");
                    jsonInformacion.put(INFORMACION.MENSAJE, it.getMensaje());
                    jsonInformacion.put(INFORMACION.FECHA_INI, sdf.format(it.getFechaIni()));
                    jsonInformacion.put(INFORMACION.FECHA_FIN, it.getFechaFin() != null ? sdf.format(it.getFechaFin()) : "");
                    jsonInformacion.put(INFORMACION.ACTIVO, it.getActivo());
                    jsonInformaciones.add(jsonInformacion);
                }
                jsonRespuesta.put("INFORMACIONES", jsonInformaciones);
                jsonRespuesta.put("TOTAL", informacionFacade.total(ejemplo, true));
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("AGREGAR")) {
                Informacion newInfo = new Informacion();
                Boolean in = false;
                if (idTerminal != null) {
                    in = true;
                    newInfo.setTerminal(new Terminal(idTerminal));
                }
                if (idSucursal != null && !in) {
                    in = true;
                    newInfo.setSucursal(new Sucursal(idSucursal));
                }
                if (idRecaudador != null && !in) {
                    in = true;
                    newInfo.setRecaudador(new Recaudador(idRecaudador));
                }
                if (idRed != null && !in) {
                    newInfo.setRed(new Red(idRed));
                }

                if (fechaFin != null) {
                    newInfo.setFechaFin(fechaFin);
                }
                newInfo.setFechaIni(fechaIni);
                newInfo.setMensaje(mensaje);
                newInfo.setActivo(activo);
                informacionFacade.merge(newInfo);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Informacion infoDetalle = informacionFacade.get(idInformacion);
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(INFORMACION.ID_INFORMACION, infoDetalle.getIdInformacion());
                jsonDetalle.put(INFORMACION.RED, infoDetalle.getRed() != null ? infoDetalle.getRed().getIdRed() : "");
                jsonDetalle.put(INFORMACION.RECAUDADOR, infoDetalle.getRecaudador() != null ? infoDetalle.getRecaudador().getIdRecaudador() : "");
                jsonDetalle.put(INFORMACION.SUCURSAL, infoDetalle.getSucursal() != null ? infoDetalle.getSucursal().getIdSucursal() : "");
                jsonDetalle.put(INFORMACION.TERMINAL, infoDetalle.getTerminal() != null ? infoDetalle.getTerminal().getIdTerminal() : "");
                jsonDetalle.put(INFORMACION.MENSAJE, infoDetalle.getMensaje());
                jsonDetalle.put(INFORMACION.FECHA_INI, sdf.format(infoDetalle.getFechaIni()));
                jsonDetalle.put(INFORMACION.FECHA_FIN, infoDetalle.getFechaFin() != null ? sdf.format(infoDetalle.getFechaFin()) : "");
                jsonDetalle.put(INFORMACION.ACTIVO, infoDetalle.getActivo());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Informacion info = this.informacionFacade.get(idInformacion);
                Boolean in = false;
                if (idTerminal != null) {
                    in = true;
                    info.setTerminal(new Terminal(idTerminal));
                }
                if (idSucursal != null && !in) {
                    in = true;
                    info.setSucursal(new Sucursal(idSucursal));
                }
                if (idRecaudador != null && !in) {
                    in = true;
                    info.setRecaudador(new Recaudador(idRecaudador));
                }
                if (idRed != null && !in) {
                    in = true;
                    info.setRed(new Red(idRed));
                }
                if (fechaFin != null) {
                    info.setFechaFin(fechaFin);
                }

                info.setFechaIni(fechaIni);
                info.setMensaje(mensaje);
                info.setActivo(activo);

                informacionFacade.update(info);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                try {
                    informacionFacade.delete(idInformacion);
                    jsonRespuesta.put("success", true);
                } catch (Exception e) {
                    Logger.getLogger(INFORMACION.class.getName()).log(Level.SEVERE, null, e);
                    jsonRespuesta.put("success", false);
                }
                out.println(jsonRespuesta.toString());
            }
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet INFORMACION</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet INFORMACION at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } catch (Exception e) {
            Logger.getLogger(INFORMACION.class.getName()).log(Level.SEVERE, null, e);
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", e.getMessage());
            out.println(jsonRespuesta.toString());
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
