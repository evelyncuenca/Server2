/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.ServicioRcFacade;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.ServicioRc;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
public class Servicios extends HttpServlet {

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
    ServicioRcFacade servicioRcFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            List<ServicioRc> list = servicioRcFacade.getAllConsultable();
            request.getSession().setAttribute("servicios", list);
            List<Facturador> listF = new ArrayList<Facturador>();
            Map l = new HashMap();
            JSONArray jArrayF = new JSONArray();
            for (ServicioRc servicio : list) {
                listF.add(servicio.getIdFacturador());
            }
            for (Facturador f : listF) {
                if (l.get(f.getIdFacturador()) == null) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("facturador", f.getDescripcion());
                    jArrayF.add(jsonObj);
                }
                l.put(f.getIdFacturador(), "1");
            }
            for (Object obj : jArrayF) {
                JSONObject jObj = (JSONObject) obj;
                String facturador = (String) jObj.get("facturador");
                JSONArray servicios = new JSONArray();
                for (ServicioRc servicio : list) {
                    if (servicio.getIdFacturador().getDescripcion().equals(facturador)) {
                        JSONObject servJson = new JSONObject();
                        servJson.put("id", servicio.getIdServicio());
                        servJson.put("nombre", servicio.getDescripcion());
                        servicios.add(servJson);
                    }
                }
                jObj.put("servicios", servicios);
            }
            out.println(jArrayF);
        } finally {
            out.close();
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
