/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.RolDeUsuarioFacade;
import py.com.konecta.redcobros.ejb.RolFacade;
import py.com.konecta.redcobros.ejb.UsuarioFacade;
import py.com.konecta.redcobros.entities.Rol;
import py.com.konecta.redcobros.entities.RolDeUsuario;
import py.com.konecta.redcobros.entities.Usuario;

/**
 *
 * @author konecta
 */
public class USUARIOS_ROLES extends HttpServlet {

    @EJB
    RolFacade rolFacade;
    @EJB
    UsuarioFacade usuarioFacade;
    @EJB
    RolDeUsuarioFacade rolDeUsuarioFacade;

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
            String []orderBy =new String[2];
            String []dirBy =new String[2];

            orderBy[0] ="aplicacion.descripcion";
            orderBy[1]="descripcion";
            dirBy[0]="asc";
            dirBy[1]= "asc";


            List<Rol> lroles = rolFacade.list(null, orderBy, dirBy);
            RolDeUsuario rolDeUSuarioExample = new RolDeUsuario();
            orderBy[0] ="rol.descripcion";
            orderBy[1]="usuario.nombreUsuario";
            dirBy[0]="asc";
            dirBy[1]= "asc";

            for (Rol o : lroles) {

                rolDeUSuarioExample.setRol(new Rol(o.getIdRol()));
                for (RolDeUsuario oo : rolDeUsuarioFacade.list(rolDeUSuarioExample, orderBy, dirBy)) {

                    jsonChild.put("id", oo.getRol().getIdRol() + "." + oo.getUsuario().getIdUsuario());
                    jsonChild.put("text",oo.getUsuario().getNombreUsuario()+ " ("+ oo.getUsuario().getPersona().getApellidos() + ", " + oo.getUsuario().getPersona().getNombres()+")");
                    jsonChild.put("iconCls", "leaf");
                    jsonChild.put("leaf", true);
                    arrayChildren.add(jsonChild.clone());
                }

                jsonRoot.put("id", o.getIdRol() + ".rol");
                jsonRoot.put("text", o.getDescripcion() +" - "+o.getAplicacion().getDescripcion());
                jsonRoot.put("children", arrayChildren.clone());
                arrayChildren.clear();

                arrayJson.add(jsonRoot.clone());
            }
            out.println(arrayJson.toString());

        } else if (op.equalsIgnoreCase("AGREGAR")) {
            if ((request.getParameter("ROLES") != null) && (request.getParameter("ROLES").matches("[0-9]+")) && (request.getParameter("USUARIO") != null) && (request.getParameter("USUARIO").matches("[0-9]+"))) {
                /**********************************************************************/
                try {
                    Usuario usuario = usuarioFacade.get(new Long(request.getParameter("USUARIO")));
                    Rol rol = rolFacade.get(new Long(request.getParameter("ROLES")));
                    RolDeUsuario example = new RolDeUsuario();
                    example.setUsuario(usuario);
                    example.setRol(rol);
                    example.setFechaAsignacion(new Date());
                    example.setUsuarioAsignador(((Long) request.getSession().getAttribute("idUsuario")).intValue());
                    rolDeUsuarioFacade.save(example);
                    json.put("success", true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    json.put("success", false);
                    json.put("motivo", "No se pudo realizar la asignacion");
                }
                out.println(json);
            }

        } else if (op.equalsIgnoreCase("BORRAR")) {

            if ((request.getParameter("elementoABorrar") != null) && (request.getParameter("elementoABorrar").matches("[0-9]+.[0-9]+"))) {

                String[] componentes = request.getParameter("elementoABorrar").split("\\.");

                RolDeUsuario example = new RolDeUsuario();
                example.setRol(new Rol(new Long(componentes[0])));
                example.setUsuario(new Usuario(new Long(componentes[1])));
                RolDeUsuario rolDeUsuario = rolDeUsuarioFacade.get(example);
                if (rolDeUsuario != null) {
                  
                    try {

                        rolDeUsuarioFacade.delete(rolDeUsuario.getIdRolDeUsuario());

                        json.put("success", true);
                        json.put("motivo", "Actualizacion Exitosa");

                    } catch (Exception ex) {
                        ex.printStackTrace();
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
        //out.close();
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    }

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
