/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

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
import py.com.konecta.redcobros.ejb.RolFacade;
import py.com.konecta.redcobros.entities.Aplicacion;
import py.com.konecta.redcobros.entities.Rol;

/**
 *
 * @author konecta
 */
public class ROLES extends HttpServlet {

    @EJB
    RolFacade rolFacade;

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
        //    SchemaQuery schemaQ = (SchemaQuery) request.getSession().getAttribute("schemaQ");
        JSONObject json = new JSONObject();

        if (op.equalsIgnoreCase("LISTAR")) {
            String []orderBy =new String[2];
            String []dirBy =new String[2];
            orderBy[0] ="aplicacion.descripcion";
            orderBy[1]="descripcion";
            dirBy[0]="asc";
            dirBy[1]= "asc";

            List<Rol> lista = rolFacade.list(null, orderBy,dirBy);
            for (Rol o : lista) {
                json.put("id", o.getIdRol());
                json.put("text", o.getDescripcion() + " - " + o.getAplicacion().getDescripcion());
                json.put("leaf", true);
                arrayFilas.add(json.clone());
            }
            out.println(arrayFilas.toString());

        } else if (op.equalsIgnoreCase("AGREGAR")) {

            if ((request.getParameter("DESCRIPCION") != null) && !(request.getParameter("DESCRIPCION").isEmpty()) && (request.getParameter("APLICACIONES") != null) && !(request.getParameter("APLICACIONES").isEmpty()) && request.getParameter("APLICACIONES").matches("[0-9]+")) {
                Rol registro = new Rol();

                registro.setDescripcion((request.getParameter("DESCRIPCION")));
                registro.setAplicacion(new Aplicacion(new Long(request.getParameter("APLICACIONES"))));

                try {
                    rolFacade.merge(registro);
                    json.put("success", true);
                    json.put("motivo", "Actualizacion Exitosa");
                } catch (Exception ex) {
                    json.put("success", false);
                    json.put("motivo", "No se pudo realizar la operacion");
                }

            } else {

                json.put("success", false);
                json.put("motivo", "Los parametros no son validos");

            }
            out.println(json);

        } else if (op.equalsIgnoreCase("MODIFICAR")) {
            JSONObject jsonRespuesta = new JSONObject();
            if ((request.getParameter("DESCRIPCION") != null) && !(request.getParameter("DESCRIPCION").isEmpty()) && (request.getParameter("ID_ROL") != null) && !(request.getParameter("ID_ROL").isEmpty())) {
                Rol rol = rolFacade.get(new Long((request.getParameter("ID_ROL"))));

                if (request.getParameter("APLICACIONES") != null && !(request.getParameter("APLICACIONES").isEmpty())) {
                    rol.setAplicacion(new Aplicacion(new Long(request.getParameter("APLICACIONES"))));
                }

                rol.setDescripcion(request.getParameter("DESCRIPCION"));
                rolFacade.merge(rol);


                jsonRespuesta.put("success", true);
            } else {

                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "Los parametros no son validos");
            }

            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("DETALLE")) {
            JSONObject jsonRespuesta = new JSONObject();
            JSONObject jsonDetalle = new JSONObject();
            if ((request.getParameter("ID_ROL") != null) && !(request.getParameter("ID_ROL").isEmpty())) {

                Rol rol = rolFacade.get(new Long((request.getParameter("ID_ROL"))));

                jsonDetalle.put("ID_ROL", rol.getIdRol());
                jsonDetalle.put("APLICACIONES", rol.getAplicacion().getIdAplicacion());
                jsonDetalle.put("DESCRIPCION", rol.getDescripcion());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);


            } else {

                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "Los parametros no son validos");

            }
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("BORRAR")) {
            if ((request.getParameter("elementoABorrar") != null) && (request.getParameter("elementoABorrar").matches("[0-9]+"))) {

                Rol registro = new Rol();
                registro.setIdRol(new Long(request.getParameter("elementoABorrar").trim()));


                try {
                    rolFacade.delete(registro.getIdRol());
                    json.put("success", true);
                    json.put("motivo", "Actualizacion Exitosa");

                } catch (Exception ex) {
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

        //out.close();
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
