/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.PermisosFacade;
import py.com.konecta.redcobros.entities.Permiso;

/**
 *
 * @author konecta
 */
public class PERMISOS extends HttpServlet {

    @EJB
    PermisosFacade permisosFacade;

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

        String op = request.getParameter("op");

        JSONArray arrayFilas = new JSONArray();

        JSONObject json = new JSONObject();

        if (op.equalsIgnoreCase("LISTAR")) {
            String []orderBy =new String[2];
            String []dirBy =new String[2];
            orderBy[0] ="aplicacion.descripcion";
            orderBy[1]="descripcion";
            dirBy[0]="asc";
            dirBy[1]= "asc";
            List<Permiso> lista = permisosFacade.list(null, orderBy,dirBy);
          
            for (Permiso o : lista) {
                json.put("id", o.getIdPermiso());
                json.put("text", o.getDescripcion() + " - " + o.getAplicacion().getDescripcion());
                json.put("leaf", true);

                arrayFilas.add(json.clone());

            }

            out.println(arrayFilas.toString());

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
