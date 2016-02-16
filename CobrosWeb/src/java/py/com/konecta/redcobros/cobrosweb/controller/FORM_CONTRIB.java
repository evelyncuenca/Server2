/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author Kiki
 */
public class FORM_CONTRIB extends HttpServlet {

    /**
     *
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
        JSONObject jsonFinal = new JSONObject();


        try {
            String opcion = request.getParameter("op");

            if (opcion.equalsIgnoreCase("REIMPRIMIR_FORMULARIO")) {
                if (request.getParameter("FORMULARIO") != null && !request.getParameter("FORMULARIO").isEmpty()) {
                    try {

                        Map<String, String> resultado = UtilesSet.getCertificacionFormulario(Long.parseLong(request.getParameter("FORMULARIO")));

                        if (resultado != null) {
                            jsonFinal.put("pantalla", resultado.get("pantalla"));
                            jsonFinal.put("certificacion", resultado.get("certificacion"));
                            jsonFinal.put("success", true);

                        } else {
                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", "No se pudo realizar la operacion.");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", e.getMessage());
                    }

                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "No se pudo realizar la operacion.");
                }

                out.println(jsonFinal);


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
