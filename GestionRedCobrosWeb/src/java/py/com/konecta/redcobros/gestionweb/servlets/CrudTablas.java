/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;




/**
 *
 * @author konecta
 */
public class CrudTablas extends HttpServlet {

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
        JSONObject json = new JSONObject();

        if (op.equalsIgnoreCase("LISTAR")) {

            JSONArray arrayChildren = new JSONArray();
            JSONArray arrayJson = new JSONArray();

            JSONObject jsonChild = new JSONObject();
            JSONObject jsonRoot = new JSONObject();
            List<String> tablas = new ArrayList();
            Map<String, List<String>> roots = new HashMap();

            Collections.sort(tablas);

            roots.put("Conf. Regionales", new ArrayList<String>());
            roots.put("Conf. Entidades", new ArrayList<String>());
            roots.put("Conf. Usuarios", new ArrayList<String>());
            roots.put("Conf. Facturadores", new ArrayList<String>());
            roots.put("Conf. Recaudadores", new ArrayList<String>());
            roots.put("Conf. Redes", new ArrayList<String>());
            roots.put("Conf. Esq. Comis.", new ArrayList<String>());


            roots.get("Conf. Regionales").add("Pais");
            roots.get("Conf. Regionales").add("Departamento");
            roots.get("Conf. Regionales").add("Ciudad");
            roots.get("Conf. Regionales").add("Localidad");

            roots.get("Conf. Entidades").add("Persona");
            roots.get("Conf. Entidades").add("Entidad");
            roots.get("Conf. Entidades").add("Entidad Financiera");
            roots.get("Conf. Entidades").add("Cuenta");
            roots.get("Conf. Entidades").add("Entidades Politicas");


            roots.get("Conf. Usuarios").add("Usuario");
            roots.get("Conf. Usuarios").add("Terminal");
            roots.get("Conf. Usuarios").add("Usuario Terminal");
            roots.get("Conf. Usuarios").add("Franja Horaria");
            roots.get("Conf. Usuarios").add("Admin. Permisos");

            roots.get("Conf. Redes").add("Red");
            roots.get("Conf. Facturadores").add("Facturador");
            roots.get("Conf. Facturadores").add("Servicio");
           

            roots.get("Conf. Recaudadores").add("Recaudador");
            roots.get("Conf. Recaudadores").add("Sucursal");

            roots.get("Conf. Esq. Comis.").add("Esq. Comis.");
            roots.get("Conf. Esq. Comis.").add("Config. Clearing Servicios");

             Date fecha = new Date();
             SimpleDateFormat spdf = new SimpleDateFormat("yyyy");
             spdf.format(fecha);

            int contador = 0;
            for (String o : roots.keySet()) {

                for (String oo : roots.get(o)) {
                    jsonChild.put("id", contador++);
                    jsonChild.put("text", oo);
                    jsonChild.put("iconCls", "leaf");
                    jsonChild.put("leaf", true);
                    arrayChildren.add(jsonChild.clone());
                }
                jsonRoot.put("id", o);
                jsonRoot.put("text", o);
                jsonRoot.put("children", arrayChildren.clone());
                arrayChildren.clear();
                arrayJson.add(jsonRoot.clone());

            }
            out.println(arrayJson.toString());
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
