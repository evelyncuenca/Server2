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
import py.com.konecta.redcobros.ejb.CiudadFacade;
import py.com.konecta.redcobros.entities.Ciudad;
import py.com.konecta.redcobros.entities.Departamento;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class CIUDAD extends HttpServlet {
    public static String ID_CIUDAD="ID_CIUDAD";
    public static String DEPARTAMENTO="DEPARTAMENTO";
    public static String NOMBRE="NOMBRE";

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
            String opcion=request.getParameter("op");
            String idCiudad=request.getParameter(CIUDAD.ID_CIUDAD);
            String idDepartamento=request.getParameter(CIUDAD.DEPARTAMENTO);
            String nombre=request.getParameter(CIUDAD.NOMBRE);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Ciudad ciudad=new Ciudad();
                Departamento d=new Departamento();
                d.setIdDepartamento(new Long(idDepartamento));
                ciudad.setDepartamento(d);
                ciudad.setNombre(nombre);
                ciudadFacade.save(ciudad);
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                ciudadFacade.delete(new Long(idCiudad));
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Ciudad ciudad=new Ciudad();
                ciudad.setIdCiudad(new Long(idCiudad));
                Departamento d=new Departamento();
                d.setIdDepartamento(new Long(idDepartamento));
                ciudad.setDepartamento(d);
                ciudad.setNombre(nombre);
                ciudadFacade.update(ciudad);
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Ciudad ciudad=ciudadFacade.get(new Long(idCiudad));
                JSONObject jsonRespuesta=new JSONObject();
                JSONObject jsonDetalle=new JSONObject();
                jsonDetalle.put(CIUDAD.ID_CIUDAD, ciudad.getIdCiudad());
                jsonDetalle.put(CIUDAD.DEPARTAMENTO, ciudad.getDepartamento().getIdDepartamento());
                jsonDetalle.put(CIUDAD.NOMBRE, ciudad.getNombre());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Ciudad ciudad=new Ciudad();
                ciudad.setNombre(nombre);
                List<Ciudad> lista;
                int total;
                String primerResultado=request.getParameter("start");
                String cantResultados=request.getParameter("limit");
                if (primerResultado!=null && cantResultados!=null) {
                    lista=ciudadFacade.list(ciudad, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados),"nombre","ASC");
                } else {
                    lista=ciudadFacade.list(ciudad,"nombre","ASC");
                }
                total=ciudadFacade.total(ciudad);
                JSONObject jsonRespuesta=new JSONObject();
                JSONArray jsonCiudades=new JSONArray();
                for (Ciudad e : lista) {
                    JSONObject jsonCiudad=new JSONObject();
                    jsonCiudad.put(CIUDAD.ID_CIUDAD, e.getIdCiudad());
                    jsonCiudad.put(CIUDAD.DEPARTAMENTO, e.getDepartamento().getNombre());
                    jsonCiudad.put(CIUDAD.NOMBRE, e.getNombre());
                    jsonCiudades.add(jsonCiudad);
                }
                jsonRespuesta.put("CIUDAD", jsonCiudades);
                jsonRespuesta.put("TOTAL",total);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else {
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
                out.println(jsonRespuesta.toString());
            }
        } catch (Exception exc) {
            JSONObject jsonRespuesta=new JSONObject();
            jsonRespuesta.put("success", false);
            //jsonRespuesta.put("motivo", exc.getMessage());
             jsonRespuesta.put("motivo",Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private CiudadFacade ciudadFacade;

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
