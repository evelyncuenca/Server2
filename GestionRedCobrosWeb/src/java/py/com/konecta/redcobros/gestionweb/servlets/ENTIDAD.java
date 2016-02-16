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
import py.com.konecta.redcobros.ejb.EntidadFacade;
import py.com.konecta.redcobros.ejb.LocalidadFacade;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.Localidad;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class ENTIDAD extends HttpServlet {

    public static String ID_ENTIDAD = "ID_ENTIDAD";
    public static String NOMBRE = "NOMBRE";
    // public static String APELLIDO="APELLIDO";
    public static String RUC_CI = "RUC_CI";
    public static String LOCALIDAD = "LOCALIDAD";
    public static String TELEFONO = "TELEFONO";
    public static String DIRECCION = "DIRECCION";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String opcion = request.getParameter("op");
            String idEntidad = request.getParameter(ENTIDAD.ID_ENTIDAD);
            String nombre = request.getParameter(ENTIDAD.NOMBRE) != null && !request.getParameter(ENTIDAD.NOMBRE).isEmpty() ? request.getParameter(ENTIDAD.NOMBRE) : null;
            String rucCi = request.getParameter(ENTIDAD.RUC_CI);
            String localidad = request.getParameter(ENTIDAD.LOCALIDAD);
            String telefono = request.getParameter(ENTIDAD.TELEFONO);
            String direccion = request.getParameter(ENTIDAD.DIRECCION);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Entidad entidad = new Entidad();
                entidad.setNombre(nombre);
                entidad.setRucCi(rucCi);
                entidad.setTelefono(telefono);
                entidad.setDireccion(direccion);
                Localidad l = new Localidad();
                l.setIdLocalidad(new Long(localidad));
                entidad.setLocalidad(l);
                entidadFacade.save(entidad);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                entidadFacade.delete(new Long(idEntidad));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Entidad entidad = new Entidad();
                entidad.setIdEntidad(new Long(idEntidad));
                entidad.setNombre(nombre);
                entidad.setRucCi(rucCi);
                entidad.setTelefono(telefono);
                entidad.setDireccion(direccion);
                Localidad l = new Localidad();
                l.setIdLocalidad(new Long(localidad));
                entidad.setLocalidad(l);
                entidadFacade.merge(entidad);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Entidad entidad = entidadFacade.get(new Long(idEntidad));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(ENTIDAD.ID_ENTIDAD, entidad.getIdEntidad());
                jsonDetalle.put(ENTIDAD.NOMBRE, entidad.getNombre());
                jsonDetalle.put(ENTIDAD.RUC_CI, entidad.getRucCi());
                jsonDetalle.put(ENTIDAD.LOCALIDAD, entidad.getLocalidad().getIdLocalidad());
                jsonDetalle.put(ENTIDAD.TELEFONO, entidad.getTelefono());
                jsonDetalle.put(ENTIDAD.DIRECCION, entidad.getDireccion());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Entidad entidad = new Entidad();
                entidad.setNombre(nombre);
                entidad.setRucCi(rucCi);
                List<Entidad> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = entidadFacade.list(entidad, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), true);
                } else {
                    lista = entidadFacade.list(entidad, true);
                }
                total = entidadFacade.total(entidad, true);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonEntidades = new JSONArray();
                for (Entidad e : lista) {
                    JSONObject jsonEntidad = new JSONObject();
                    jsonEntidad.put(ENTIDAD.ID_ENTIDAD, e.getIdEntidad());
                    jsonEntidad.put(ENTIDAD.NOMBRE, e.getNombre());
                    jsonEntidad.put(ENTIDAD.RUC_CI, e.getRucCi());
                    jsonEntidad.put(ENTIDAD.TELEFONO, e.getTelefono());
                    jsonEntidad.put(ENTIDAD.LOCALIDAD, e.getLocalidad().getNombre());
                    jsonEntidad.put(ENTIDAD.DIRECCION, e.getDireccion());
                    jsonEntidades.add(jsonEntidad);
                }
                jsonRespuesta.put("ENTIDAD", jsonEntidades);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else {
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
                out.println(jsonRespuesta.toString());
            }
        } catch (Exception exc) {
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            //jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
            exc.printStackTrace();
        } finally {
            //out.close();
        }
    }
    @EJB
    private EntidadFacade entidadFacade;

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
