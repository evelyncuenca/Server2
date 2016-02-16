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
import py.com.konecta.redcobros.ejb.EntidadFinancieraFacade;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.EntidadFinanciera;
import py.com.konecta.redcobros.entities.Persona;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class ENTIDAD_FINANCIERA extends HttpServlet {

    public static String ID_ENTIDAD_FINANCIERA = "ID_ENTIDAD_FINANCIERA";
    public static String ENTIDAD = "ENTIDAD";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String CONTACTO = "CONTACTO";
    public static String NUMERO_CUENTA = "NUMERO_CUENTA";

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
            String idEntidadFinanciera = request.getParameter(ENTIDAD_FINANCIERA.ID_ENTIDAD_FINANCIERA);
            String idEntidad = request.getParameter(ENTIDAD_FINANCIERA.ENTIDAD);
            String descripcion = request.getParameter(ENTIDAD_FINANCIERA.DESCRIPCION) != null && !request.getParameter(ENTIDAD_FINANCIERA.DESCRIPCION).isEmpty() ? request.getParameter(ENTIDAD_FINANCIERA.DESCRIPCION) : null;
            String contacto = request.getParameter(ENTIDAD_FINANCIERA.CONTACTO);
            String numeroCuenta = request.getParameter(ENTIDAD_FINANCIERA.NUMERO_CUENTA);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
                entidadFinanciera.setDescripcion(descripcion);
                Entidad entidad = new Entidad();
                entidad.setIdEntidad(new Long(idEntidad));
                entidadFinanciera.setEntidad(entidad);
                entidadFinanciera.setNumeroCuenta(numeroCuenta);
                Persona p = new Persona();
                p.setIdPersona(new Long(contacto));
                entidadFinanciera.setContacto(p);
                entidadFinancieraFacade.save(entidadFinanciera);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                entidadFinancieraFacade.delete(new Long(idEntidadFinanciera));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
                entidadFinanciera.setIdEntidadFinanciera(new Long(idEntidadFinanciera));
                entidadFinanciera.setDescripcion(descripcion);
                Persona p = new Persona();
                p.setIdPersona(new Long(contacto));
                entidadFinanciera.setContacto(p);
                Entidad entidad = new Entidad();
                entidad.setIdEntidad(new Long(idEntidad));
                entidadFinanciera.setEntidad(entidad);
                entidadFinanciera.setNumeroCuenta(numeroCuenta);
                entidadFinancieraFacade.update(entidadFinanciera);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                EntidadFinanciera entidadFinanciera = entidadFinancieraFacade.get(new Long(idEntidadFinanciera));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(ENTIDAD_FINANCIERA.ID_ENTIDAD_FINANCIERA, entidadFinanciera.getIdEntidadFinanciera());
                jsonDetalle.put(ENTIDAD_FINANCIERA.DESCRIPCION, entidadFinanciera.getDescripcion());
                jsonDetalle.put(ENTIDAD_FINANCIERA.ENTIDAD, entidadFinanciera.getEntidad().getIdEntidad());
                jsonDetalle.put(ENTIDAD_FINANCIERA.CONTACTO, entidadFinanciera.getContacto().getIdPersona());
                jsonDetalle.put(ENTIDAD_FINANCIERA.NUMERO_CUENTA, entidadFinanciera.getNumeroCuenta());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
                entidadFinanciera.setDescripcion(descripcion);
                List<EntidadFinanciera> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = entidadFinancieraFacade.list(entidadFinanciera, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados),true);
                } else {
                    lista = entidadFinancieraFacade.list(entidadFinanciera, true);
                }
                total = entidadFinancieraFacade.total(entidadFinanciera, true);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonEntidadesFinancieras = new JSONArray();
                for (EntidadFinanciera e : lista) {
                    JSONObject jsonEntidadFinanciera = new JSONObject();
                    jsonEntidadFinanciera.put(ENTIDAD_FINANCIERA.ID_ENTIDAD_FINANCIERA, e.getIdEntidadFinanciera());
                    jsonEntidadFinanciera.put(ENTIDAD_FINANCIERA.DESCRIPCION, e.getDescripcion());
                    jsonEntidadFinanciera.put(ENTIDAD_FINANCIERA.ENTIDAD, e.getEntidad().getNombre());
                    jsonEntidadFinanciera.put(ENTIDAD_FINANCIERA.CONTACTO, e.getContacto().getApellidos() + ", " + e.getContacto().getNombres());
                    jsonEntidadFinanciera.put(ENTIDAD_FINANCIERA.NUMERO_CUENTA, e.getNumeroCuenta() != null ? e.getNumeroCuenta() : "-");
                    jsonEntidadesFinancieras.add(jsonEntidadFinanciera);
                }
                jsonRespuesta.put("ENTIDAD_FINANCIERA", jsonEntidadesFinancieras);
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
        } finally {
            //out.close();
        }
    }
    @EJB
    private EntidadFinancieraFacade entidadFinancieraFacade;

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