/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.InformacionFacade;
import py.com.konecta.redcobros.ejb.ServicioRcFacade;
import py.com.konecta.redcobros.entities.Informacion;
import py.com.konecta.redcobros.entities.ServicioRc;

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
    public static String MENSAJE = "MENSAJE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            SimpleDateFormat spdf = new SimpleDateFormat("ddMMyyyy");
            String opcion = request.getParameter("op");
            JSONObject jsonRespuesta = new JSONObject();
            HttpSession session = request.getSession();
            Long idRed = (Long) session.getAttribute("idRed");
            Long idRecaudador = (Long) session.getAttribute("idRecaudador");
            Long idSucursal = (Long) session.getAttribute("idSucursal");
            Long idTerminal = (Long) session.getAttribute("idTerminal");
            if (opcion.equalsIgnoreCase("SYS_INF")) {
                Informacion inf = new Informacion();
                inf.setActivo("S");
                List<Informacion> lInformacion = informacionFacade.list(inf);
                Date today = new Date();
                today = spdf.parse(spdf.format(today));
                JSONArray jsonInformaciones = new JSONArray();
                int total = 0;
                boolean isPut = false;
                for (Informacion it : lInformacion) {
                    JSONObject jsonInformacion = new JSONObject();                    
                    if ((today.compareTo(it.getFechaIni()) >= 0) && (it.getFechaFin() == null ? true : today.compareTo(it.getFechaFin())<=0)) {
                        if (it.getRed() == null
                                && it.getRecaudador() == null
                                && it.getSucursal() == null
                                && it.getTerminal() == null) {
                            isPut = true;
                        } else {
                            if (it.getRed() != null) {
                                if (it.getRed().getIdRed().equals(idRed)) {
                                    isPut = true;
                                }
                            }
                            if (it.getRecaudador() != null) {
                                if (it.getRecaudador().getIdRecaudador().equals(idRecaudador)) {
                                    isPut = true;
                                }
                            }
                            if (it.getSucursal() != null) {
                                if (it.getSucursal().getIdSucursal().equals(idSucursal)) {
                                    isPut = true;
                                }
                            }
                            if (it.getTerminal() != null) {
                                if (it.getTerminal().getIdTerminal().equals(idTerminal)) {
                                    isPut = true;
                                }
                            }
                        }
                        if (isPut) {
                            jsonInformacion.put(INFORMACION.MENSAJE, it.getMensaje());
                            jsonInformaciones.add(jsonInformacion);
                            total++;
                            isPut = false;
                        }
                    }
                }

                jsonRespuesta.put("INFORMACION", jsonInformaciones);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            }
        } catch (Exception e) {
            Logger.getLogger(INFORMACION.class.getName()).log(Level.SEVERE, null, e);
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", e.getMessage());
            out.println(jsonRespuesta.toString());

        } finally {
            try {
                //out.close();
            } finally {
            }
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
