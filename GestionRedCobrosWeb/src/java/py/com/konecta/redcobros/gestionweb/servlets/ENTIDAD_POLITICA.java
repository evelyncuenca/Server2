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
import py.com.konecta.redcobros.ejb.EntidadPoliticaFacade;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.EntidadPolitica;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class ENTIDAD_POLITICA extends HttpServlet {

    public static String ENTIDAD = "ENTIDAD";
    public static String ENTIDADES_NO_POLITICAS = "ENTIDADES_NO_POLITICAS";
    public static String ID_ENTIDAD_POLITICA = "ID_ENTIDAD_POLITICA";
    public static String ID_ENTIDAD_POLITICA2 = "ID_ENTIDAD_POLITICA2";
    public static String RED = "RED";
    public static String NUMERO_CUENTA = "NUMERO_CUENTA";
    public static String DESCRIPCION_ENTIDAD = "DESCRIPCION_ENTIDAD";
    public static String DESCRIPCION_RED = "DESCRIPCION_RED";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String opcion = request.getParameter("op");
            String idEntidadPolitica = request.getParameter(ENTIDAD_POLITICA.ID_ENTIDAD_POLITICA);
            String idEntidadPolitica2 = request.getParameter(ENTIDAD_POLITICA.ID_ENTIDAD_POLITICA2);
            String idEntidad = request.getParameter(ENTIDAD_POLITICA.ENTIDAD);
            String idEntidadNoPolitica = request.getParameter(ENTIDAD_POLITICA.ENTIDADES_NO_POLITICAS);
            String idRed = request.getParameter(ENTIDAD_POLITICA.RED);
            String numeroCuenta = request.getParameter(ENTIDAD_POLITICA.NUMERO_CUENTA);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                EntidadPolitica entidad = new EntidadPolitica();
                entidad.setRed(new Red(new Long(idRed)));
                entidad.setEntidad(new Entidad(new Long(idEntidadNoPolitica)));
                entidad.setNumeroCuenta(numeroCuenta);
                entidadPoliticaFacade.save(entidad);

                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                EntidadPolitica entidad = entidadPoliticaFacade.get(new Long(idEntidadPolitica2));
                entidad.setRed(new Red(new Long(idRed)));
                entidad.setEntidad(new Entidad(new Long(idEntidadNoPolitica)));
                entidad.setNumeroCuenta(numeroCuenta);
                entidadPoliticaFacade.merge(entidad);

                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                EntidadPolitica entidad = entidadPoliticaFacade.get(new Long(idEntidadPolitica));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(ENTIDAD_POLITICA.ENTIDADES_NO_POLITICAS, entidad.getEntidad().getIdEntidad());
                jsonDetalle.put(ENTIDAD_POLITICA.RED, entidad.getRed().getIdRed());
                jsonDetalle.put(ENTIDAD_POLITICA.NUMERO_CUENTA, entidad.getNumeroCuenta());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                EntidadPolitica entidad = new EntidadPolitica();

                List<EntidadPolitica> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = entidadPoliticaFacade.list(entidad, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "entidad.nombre", "ASC");
                } else {
                    lista = entidadPoliticaFacade.list(entidad, "entidad.nombre", "ASC");
                }
                total = entidadPoliticaFacade.total(entidad);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonCiudades = new JSONArray();
                for (EntidadPolitica e : lista) {
                    JSONObject jsonCiudad = new JSONObject();
                    jsonCiudad.put(ENTIDAD_POLITICA.ID_ENTIDAD_POLITICA, e.getIdEntidadPolitica());
                    jsonCiudad.put(ENTIDAD_POLITICA.ENTIDAD, e.getEntidad().getIdEntidad());
                    jsonCiudad.put(ENTIDAD_POLITICA.DESCRIPCION_ENTIDAD, e.getEntidad().getNombre());
                    jsonCiudad.put(ENTIDAD_POLITICA.NUMERO_CUENTA, e.getNumeroCuenta());
                    jsonCiudad.put(ENTIDAD_POLITICA.DESCRIPCION_RED, e.getRed().getDescripcion());
                    jsonCiudades.add(jsonCiudad);
                }
                jsonRespuesta.put("ENTIDAD_POLITICA", jsonCiudades);
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
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private EntidadPoliticaFacade entidadPoliticaFacade;
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
