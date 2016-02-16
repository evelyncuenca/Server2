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
import py.com.konecta.redcobros.ejb.FacturadorFacade;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.Persona;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class FACTURADOR extends HttpServlet {

    public static String ID_FACTURADOR = "ID_FACTURADOR";
    public static String ENTIDAD = "ENTIDAD";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String CONTACTO = "PERSONA";
    public static String CONTACTO_LISTAR = "CONTACTO";
    public static String TELEFONO = "TELEFONO";
    public static String DIRECCION = "DIRECCION";
    public static String LOCALIDAD = "LOCALIDAD";
    public static String CODIGO = "CODIGO";

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
            String idFacturador = request.getParameter(FACTURADOR.ID_FACTURADOR);
            String idEntidad = request.getParameter(FACTURADOR.ENTIDAD);
            String descripcion = request.getParameter(FACTURADOR.DESCRIPCION);
            String contacto = request.getParameter(FACTURADOR.CONTACTO);
            String codigo = request.getParameter(FACTURADOR.CODIGO);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Facturador facturador = new Facturador();
                Entidad entidad = new Entidad();
                entidad.setIdEntidad(new Long(idEntidad));
                facturador.setEntidad(entidad);
                facturador.setDescripcion(descripcion);
                facturador.setCodigo(new Integer(codigo));
                if (contacto.matches("[0-9]+")) {
                    Persona p = new Persona();
                    p.setIdPersona(new Long(contacto));
                    facturador.setContacto(p);
                }
                facturadorFacade.save(facturador);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                facturadorFacade.delete(new Long(idFacturador));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Facturador facturador = new Facturador();
                facturador.setIdFacturador(new Long(idFacturador));
                Entidad entidad = new Entidad();
                entidad.setIdEntidad(new Long(idEntidad));
                facturador.setEntidad(entidad);
                facturador.setDescripcion(descripcion);
                facturador.setCodigo(new Integer(codigo));
                if (contacto.matches("[0-9]+")) {
                    Persona p = new Persona();
                    p.setIdPersona(new Long(contacto));
                    facturador.setContacto(p);
                }
                facturadorFacade.update(facturador);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Facturador facturador = facturadorFacade.get(new Long(idFacturador));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(FACTURADOR.ID_FACTURADOR, facturador.getIdFacturador());
                jsonDetalle.put(FACTURADOR.ENTIDAD, facturador.getEntidad().getIdEntidad());
                jsonDetalle.put(FACTURADOR.DESCRIPCION, facturador.getDescripcion());
                jsonDetalle.put(FACTURADOR.CONTACTO, (facturador.getContacto() != null) ? facturador.getContacto().getApellidos() + ", " + facturador.getContacto().getNombres() : "");
                jsonDetalle.put(FACTURADOR.CODIGO, facturador.getCodigo());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Facturador facturador = new Facturador();
                facturador.setDescripcion(descripcion);
                List<Facturador> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = facturadorFacade.list(facturador, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "codigo", "ASC");
                } else {
                    lista = facturadorFacade.list(facturador, "codigo", "ASC");
                }
                total = facturadorFacade.total(facturador);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonFacturadores = new JSONArray();
                for (Facturador e : lista) {
                    JSONObject jsonFacturador = new JSONObject();
                    jsonFacturador.put(FACTURADOR.ID_FACTURADOR, e.getIdFacturador());
                    jsonFacturador.put(FACTURADOR.ENTIDAD, e.getEntidad().getNombre());
                    jsonFacturador.put(FACTURADOR.DESCRIPCION, e.getDescripcion());
                    jsonFacturador.put(FACTURADOR.CONTACTO_LISTAR, (e.getContacto() != null) ? e.getContacto().getApellidos() + ", " + e.getContacto().getNombres() : "");
                    jsonFacturador.put(FACTURADOR.DIRECCION, e.getEntidad().getDireccion());
                    jsonFacturador.put(FACTURADOR.LOCALIDAD, e.getEntidad().getLocalidad().getNombre());
                    jsonFacturador.put(FACTURADOR.TELEFONO, e.getEntidad().getTelefono());
                    jsonFacturador.put(FACTURADOR.CODIGO, e.getCodigo());
                    jsonFacturadores.add(jsonFacturador);
                }
                jsonRespuesta.put("FACTURADOR", jsonFacturadores);
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
            // jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private FacturadorFacade facturadorFacade;


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
