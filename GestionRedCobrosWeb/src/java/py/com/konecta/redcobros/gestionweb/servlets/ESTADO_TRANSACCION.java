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
import py.com.konecta.redcobros.ejb.EstadoTransaccionFacade;
import py.com.konecta.redcobros.entities.EstadoTransaccion;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class ESTADO_TRANSACCION extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static String ID_ESTADO_TRANSACCION = "ID_ESTADO_TRANSACCION";
    public static String DESCRIPCION = "DESCRIPCION";

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
            String descripcion = request.getParameter(ESTADO_TRANSACCION.DESCRIPCION);
            String idEstadoTransaccion = request.getParameter(ESTADO_TRANSACCION.ID_ESTADO_TRANSACCION);

            if (opcion.equalsIgnoreCase("AGREGAR")) {
                EstadoTransaccion estadoTransaccion = new EstadoTransaccion();
                estadoTransaccion.setDescripcion(descripcion);
               // estadoTransaccion.setIdEstadoTransaccion(Integer.parseInt(idEstadoTransaccion));

                estadoTransaccionFacade.save(estadoTransaccion);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                estadoTransaccionFacade.delete(new Long(idEstadoTransaccion));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                EstadoTransaccion estadoTransaccion = new EstadoTransaccion();
                estadoTransaccion.setDescripcion(descripcion);
                estadoTransaccion.setIdEstadoTransaccion(new Long(idEstadoTransaccion));
                estadoTransaccionFacade.update(estadoTransaccion);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                EstadoTransaccion estadoTransaccion = estadoTransaccionFacade.get(new Long(idEstadoTransaccion));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(ESTADO_TRANSACCION.ID_ESTADO_TRANSACCION, estadoTransaccion.getIdEstadoTransaccion());
                jsonDetalle.put(ESTADO_TRANSACCION.DESCRIPCION, estadoTransaccion.getDescripcion());

                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                EstadoTransaccion estadoTransaccion = new EstadoTransaccion();
                estadoTransaccion.setDescripcion(descripcion);
               // estadoTransaccion.setIdEstadoTransaccion(new Long(idEstadoTransaccion));
                List<EstadoTransaccion> lista;

                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = estadoTransaccionFacade.list(estadoTransaccion, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados));
                } else {
                    lista = estadoTransaccionFacade.list(estadoTransaccion);
                }
              
                total = estadoTransaccionFacade.total(estadoTransaccion);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonPersonas = new JSONArray();
                for (EstadoTransaccion e : lista) {
                    JSONObject jsonPersona = new JSONObject();
                    jsonPersona.put(ESTADO_TRANSACCION.ID_ESTADO_TRANSACCION, e.getIdEstadoTransaccion());
                    jsonPersona.put(ESTADO_TRANSACCION.DESCRIPCION, e.getDescripcion());
                    jsonPersonas.add(jsonPersona);
                }
                jsonRespuesta.put("ESTADO_TRANSACCION", jsonPersonas);
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
           // jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo",Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private EstadoTransaccionFacade estadoTransaccionFacade;

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
