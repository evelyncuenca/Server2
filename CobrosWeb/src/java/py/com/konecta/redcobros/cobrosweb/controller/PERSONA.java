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
import py.com.konecta.redcobros.ejb.PersonaFacade;
import py.com.konecta.redcobros.entities.Localidad;
import py.com.konecta.redcobros.entities.Persona;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class PERSONA extends HttpServlet {
    public static String ID_PERSONA="ID_PERSONA";
    public static String NOMBRES="NOMBRES";
    public static String APELLIDOS="APELLIDOS";
    public static String TELEFONO="TELEFONO";
    public static String DIRECCION="DIRECCION";
    public static String RUC_CI="RUC_CI";
    public static String LOCALIDAD="LOCALIDAD";

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
            String idPersona=request.getParameter(PERSONA.ID_PERSONA);
            String nombres=request.getParameter(PERSONA.NOMBRES);
            String apellidos=request.getParameter(PERSONA.APELLIDOS);
            String direccion=request.getParameter(PERSONA.DIRECCION);
            String telefono=request.getParameter(PERSONA.TELEFONO);
            String rucCi=request.getParameter(PERSONA.RUC_CI);
            String localidad=request.getParameter(PERSONA.LOCALIDAD);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Persona persona=new Persona();
                persona.setNombres(nombres);
                persona.setApellidos(apellidos);
                persona.setRucCi(rucCi);
                persona.setTelefono(telefono);
                persona.setDireccion(direccion);
                persona.setLocalidad(new Localidad(new Long(localidad)));
                personaFacade.save(persona);
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                personaFacade.delete(new Long(idPersona));
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Persona persona=new Persona();
                persona.setIdPersona(new Long(idPersona));
                persona.setNombres(nombres);
                persona.setApellidos(apellidos);
                persona.setRucCi(rucCi);
                persona.setTelefono(telefono);
                persona.setDireccion(direccion);
                persona.setLocalidad(new Localidad(new Long(localidad)));
                personaFacade.update(persona);
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Persona persona=personaFacade.get(new Long(idPersona));
                JSONObject jsonRespuesta=new JSONObject();
                JSONObject jsonDetalle=new JSONObject();
                jsonDetalle.put(PERSONA.ID_PERSONA, persona.getIdPersona());
                jsonDetalle.put(PERSONA.NOMBRES, persona.getNombres());
                jsonDetalle.put(PERSONA.APELLIDOS, persona.getApellidos());
                jsonDetalle.put(PERSONA.RUC_CI, persona.getRucCi());
                jsonDetalle.put(PERSONA.TELEFONO, persona.getTelefono());
                jsonDetalle.put(PERSONA.DIRECCION, persona.getDireccion());
                jsonDetalle.put(PERSONA.LOCALIDAD, persona.getLocalidad().getIdLocalidad());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Persona persona=new Persona();
                persona.setNombres(nombres);
                persona.setApellidos(apellidos);
                persona.setRucCi(rucCi);
                persona.setTelefono(telefono);
                persona.setDireccion(direccion);
                List<Persona> lista;
                int total;
                String primerResultado=request.getParameter("start");
                String cantResultados=request.getParameter("limit");
                if (primerResultado!=null && cantResultados!=null) {
                    lista=personaFacade.list(persona, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados));
                } else {
                    lista=personaFacade.list(persona);
                }
                total=personaFacade.total(persona);
                JSONObject jsonRespuesta=new JSONObject();
                JSONArray jsonPersonas=new JSONArray();
                for (Persona e : lista) {
                    JSONObject jsonPersona=new JSONObject();
                    jsonPersona.put(PERSONA.ID_PERSONA, e.getIdPersona());
                    jsonPersona.put(PERSONA.NOMBRES, e.getNombres());
                    jsonPersona.put(PERSONA.APELLIDOS, e.getApellidos());
                    jsonPersona.put(PERSONA.TELEFONO, e.getTelefono());
                    jsonPersona.put(PERSONA.DIRECCION, e.getDireccion());
                    jsonPersona.put(PERSONA.RUC_CI, e.getRucCi());
                    jsonPersona.put(PERSONA.LOCALIDAD, e.getLocalidad().getNombre());
                    jsonPersonas.add(jsonPersona);
                }
                jsonRespuesta.put("PERSONA", jsonPersonas);
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
    private PersonaFacade personaFacade;

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
