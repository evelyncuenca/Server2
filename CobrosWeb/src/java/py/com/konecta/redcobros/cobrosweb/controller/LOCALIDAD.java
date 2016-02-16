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

import py.com.konecta.redcobros.ejb.LocalidadFacade;
import py.com.konecta.redcobros.entities.Ciudad;
import py.com.konecta.redcobros.entities.Localidad;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class LOCALIDAD extends HttpServlet {

    public static String ID_LOCALIDAD = "ID_LOCALIDAD";
    public static String CIUDAD = "CIUDAD";
    public static String NOMBRE = "NOMBRE";

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
            String idLocalidad = request.getParameter(LOCALIDAD.ID_LOCALIDAD);
            String idCiudad = request.getParameter(LOCALIDAD.CIUDAD);
            String nombre = request.getParameter(LOCALIDAD.NOMBRE);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Localidad localidad = new Localidad();
                localidad.setCiudad(new Ciudad(new Long(idCiudad)));
                localidad.setNombre(nombre);
                localidadFacade.save(localidad);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                localidadFacade.delete(new Long(idLocalidad));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Localidad localidad = new Localidad();
                localidad.setIdLocalidad(new Long(idLocalidad));
                localidad.setCiudad(new Ciudad(new Long(idCiudad)));
                localidad.setNombre(nombre);
                localidadFacade.update(localidad);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Localidad localidad = localidadFacade.get(new Long(idLocalidad));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(LOCALIDAD.ID_LOCALIDAD, localidad.getIdLocalidad());
                jsonDetalle.put(LOCALIDAD.CIUDAD, localidad.getCiudad().getIdCiudad());
                jsonDetalle.put(LOCALIDAD.NOMBRE, localidad.getNombre());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Localidad localidad = new Localidad();
                localidad.setNombre(nombre);
                List<Localidad> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = localidadFacade.list(localidad, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC");
                } else {
                    lista = localidadFacade.list(localidad, "nombre", "ASC");
                }
                total = localidadFacade.total(localidad);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonLocalidades = new JSONArray();
                for (Localidad e : lista) {
                    JSONObject jsonLocalidad = new JSONObject();
                    jsonLocalidad.put(LOCALIDAD.ID_LOCALIDAD, e.getIdLocalidad());
                    jsonLocalidad.put(LOCALIDAD.CIUDAD, e.getCiudad().getNombre());
                    jsonLocalidad.put(LOCALIDAD.NOMBRE, e.getNombre());
                    jsonLocalidades.add(jsonLocalidad);
                }
                jsonRespuesta.put("LOCALIDAD", jsonLocalidades);
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
            exc.printStackTrace();
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            //jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private LocalidadFacade localidadFacade;

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
