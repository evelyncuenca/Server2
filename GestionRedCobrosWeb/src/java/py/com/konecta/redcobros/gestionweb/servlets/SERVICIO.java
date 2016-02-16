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
import py.com.konecta.redcobros.ejb.ServicioFacade;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.Servicio;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class SERVICIO extends HttpServlet {

    public static String ID_SERVICIO = "ID_SERVICIO";
    public static String FACTURADOR = "FACTURADOR";
    public static String COMENTARIO = "COMENTARIO";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String TAMANHO_GRUPO = "TAMANHO_GRUPO";
    public static String CODIGO = "CODIGO";
    public static String FACTURABLE = "FACTURABLE";
    public static String FACTURA_INCLUYE_COMISION = "FACTURA_INCLUYE_COMISION";

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
            String idServicio = request.getParameter(SERVICIO.ID_SERVICIO);
            String idFacturador = request.getParameter(SERVICIO.FACTURADOR);
            String comentario = request.getParameter(SERVICIO.COMENTARIO);
            String descripcion = request.getParameter(SERVICIO.DESCRIPCION);
            String tamanhoGestion = request.getParameter(SERVICIO.TAMANHO_GRUPO);
            String codigo = request.getParameter(SERVICIO.CODIGO);
            String facturable = (request.getParameter(SERVICIO.FACTURABLE)!= null && request.getParameter(SERVICIO.FACTURABLE).equalsIgnoreCase("on"))?"S":"N";
            String facturaIncluyeComision = (request.getParameter(SERVICIO.FACTURA_INCLUYE_COMISION)!= null && request.getParameter(SERVICIO.FACTURA_INCLUYE_COMISION).equalsIgnoreCase("on"))?"S":"N";
           
            String factIncluyeCom=request.getParameter(SERVICIO.FACTURA_INCLUYE_COMISION);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Servicio servicio = new Servicio();
                servicio.setFacturador(new Facturador(new Long(idFacturador)));
                servicio.setDescripcion(descripcion);
                servicio.setComentario(comentario);
                servicio.setTamanhoGrupo(Integer.parseInt(tamanhoGestion));
                servicio.setCodigo(Integer.parseInt(codigo));
                servicio.setFacturable(facturable);
                servicio.setFacturaIncluyeComision(facturaIncluyeComision);
                servicioFacade.save(servicio);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                servicioFacade.delete(new Long(idServicio));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Servicio servicio = servicioFacade.get(new Long(idServicio));
                servicio.setFacturador(new Facturador(new Long(idFacturador)));
                servicio.setDescripcion(descripcion);
                servicio.setComentario(comentario);
                servicio.setFacturable(facturable);
                servicio.setFacturaIncluyeComision(facturaIncluyeComision);
                servicio.setTamanhoGrupo(Integer.parseInt(tamanhoGestion));
                servicio.setCodigo(Integer.parseInt(codigo));
                servicio.setFacturable(facturable);
                servicio.setFacturaIncluyeComision(factIncluyeCom);
                servicioFacade.merge(servicio);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Servicio servicio = servicioFacade.get(new Long(idServicio));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(SERVICIO.ID_SERVICIO, servicio.getIdServicio());
                jsonDetalle.put(SERVICIO.FACTURADOR, servicio.getFacturador().getIdFacturador());
                jsonDetalle.put(SERVICIO.COMENTARIO, servicio.getComentario());
                jsonDetalle.put(SERVICIO.DESCRIPCION, servicio.getDescripcion());
                jsonDetalle.put(SERVICIO.TAMANHO_GRUPO, servicio.getTamanhoGrupo());
                jsonDetalle.put(SERVICIO.CODIGO, servicio.getCodigo());
                jsonDetalle.put(SERVICIO.FACTURABLE, (servicio.getFacturable()!= null && servicio.getFacturable().equalsIgnoreCase("S"))? "on":"");
                jsonDetalle.put(SERVICIO.FACTURA_INCLUYE_COMISION, (servicio.getFacturaIncluyeComision()!= null && servicio.getFacturaIncluyeComision().equalsIgnoreCase("S"))? "on":"");
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Servicio servicio = new Servicio();
                servicio.setDescripcion(descripcion);
                servicio.setComentario(comentario);
                List<Servicio> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = servicioFacade.list(servicio, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "codigo", "ASC");
                } else {
                    lista = servicioFacade.list(servicio, "codigo", "ASC");
                }

                total = servicioFacade.total(servicio);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonServicios = new JSONArray();
                for (Servicio e : lista) {
                    JSONObject jsonServicio = new JSONObject();
                    jsonServicio.put(SERVICIO.ID_SERVICIO, e.getIdServicio());
                    jsonServicio.put(SERVICIO.FACTURADOR, e.getFacturador().getDescripcion());
                    jsonServicio.put(SERVICIO.COMENTARIO, e.getComentario());
                    jsonServicio.put(SERVICIO.DESCRIPCION, e.getDescripcion());
                    jsonServicio.put(SERVICIO.TAMANHO_GRUPO, e.getTamanhoGrupo());
                    jsonServicio.put(SERVICIO.CODIGO, e.getCodigo());
                    jsonServicio.put(SERVICIO.FACTURA_INCLUYE_COMISION,(e.getFacturaIncluyeComision()!= null && !e.getFacturaIncluyeComision().isEmpty())?e.getFacturaIncluyeComision():"-");
                    jsonServicio.put(SERVICIO.FACTURABLE, (e.getFacturable()!= null && !e.getFacturable().isEmpty())?e.getFacturable():"-");
                    jsonServicios.add(jsonServicio);
                }
                jsonRespuesta.put("SERVICIO", jsonServicios);
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
    private ServicioFacade servicioFacade;

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
