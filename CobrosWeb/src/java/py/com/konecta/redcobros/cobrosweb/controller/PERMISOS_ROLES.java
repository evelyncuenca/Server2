/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.PermisoDeRolFacade;
import py.com.konecta.redcobros.ejb.RolFacade;
import py.com.konecta.redcobros.entities.Permiso;
import py.com.konecta.redcobros.entities.PermisoDeRol;
import py.com.konecta.redcobros.entities.Rol;

/**
 *
 * @author konecta
 */
public class PERMISOS_ROLES extends HttpServlet {

    @EJB
    RolFacade rolFacade;
    @EJB
    PermisoDeRolFacade permisoDeRolFacade;

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

            String[] orderBy = new String[2];
            String[] dirBy = new String[2];
            orderBy[0] = "aplicacion.descripcion";
            orderBy[1] = "descripcion";
            dirBy[0] = "asc";
            dirBy[1] = "asc";


            List<Rol> lroles = rolFacade.list(null, orderBy, dirBy);
            PermisoDeRol example = new PermisoDeRol();

            orderBy[0] = "rol.descripcion";
            orderBy[1] = "permiso.descripcion";
            dirBy[0] = "asc";
            dirBy[1] = "asc";
            for (Rol o : lroles) {

                example.setRol(new Rol(o.getIdRol()));

                for (PermisoDeRol oo : permisoDeRolFacade.list(example, orderBy, dirBy)) {

                    jsonChild.put("id", oo.getPermiso().getIdPermiso() + "." + oo.getRol().getIdRol());
                    jsonChild.put("text", oo.getPermiso().getDescripcion());
                    jsonChild.put("iconCls", "leaf");
                    jsonChild.put("leaf", true);
                    arrayChildren.add(jsonChild.clone());
                }

                jsonRoot.put("id", o.getIdRol() + ".rol");
                jsonRoot.put("text", o.getDescripcion() + " - " + o.getAplicacion().getDescripcion());
                jsonRoot.put("children", arrayChildren.clone());
                arrayChildren.clear();

                arrayJson.add(jsonRoot.clone());

            }
            out.println(arrayJson.toString());
        /*******************************************/
        } else if (op.equalsIgnoreCase("AGREGAR")) {
            if ((request.getParameter("PERMISOS") != null) && (request.getParameter("PERMISOS").matches("[0-9]+")) && (request.getParameter("ROLES") != null) && (request.getParameter("ROLES").matches("[0-9]+"))) {
               
                PermisoDeRol registro = new PermisoDeRol();
                registro.setPermiso(new Permiso(new Long(request.getParameter("PERMISOS").trim())));
                registro.setRol(new Rol(new Long(request.getParameter("ROLES").trim())));
                if (permisoDeRolFacade.list(registro).size() == 0) {
                    try {
                        permisoDeRolFacade.save(registro);

                        json.put("success", true);
                        json.put("motivo", "Actualizacion Exitosa");

                    } catch (Exception ex) {
                        json.put("success", false);
                        json.put("motivo", "No se pudo realizar la operacion");
                    }

                } else {
                    json.put("success", false);
                    json.put("motivo", "Ya se ha realizado esta asignación");
                }

            } else {

                json.put("success", false);
                json.put("motivo", "Los parametros no son validos");

            }
            out.println(json);
        } else if (op.equalsIgnoreCase("BORRAR")) {
            if ((request.getParameter("elementoABorrar") != null) && (request.getParameter("elementoABorrar").matches("[0-9]+.[0-9]+"))) {

                String[] componentes = request.getParameter("elementoABorrar").split("\\.");

                PermisoDeRol example = new PermisoDeRol();
                example.setPermiso(new Permiso(new Long(componentes[0])));
                example.setRol(new Rol(new Long(componentes[1])));
                PermisoDeRol permisoRol = permisoDeRolFacade.get(example);
                if (permisoRol != null) {
                    try {

                        permisoDeRolFacade.delete(permisoRol.getIdPermisoDeRol());

                        json.put("success", true);
                        json.put("motivo", "Actualizacion Exitosa");

                    } catch (Exception ex) {
                        json.put("success", false);
                        json.put("motivo", "No se pudo realizar la operacion");
                    }

                } else {
                    json.put("success", false);
                    json.put("motivo", "No se pudo realizar la operacion");
                }


            } else {
                json.put("success", false);
                json.put("motivo", "Los parametros no son validos");
            }
            out.println(json);

        } else {
            json.put("success", false);
            json.put("motivo", "No existe la opcion pedida");
            out.println(json);

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
