/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.RedRecaudadorFacade;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.RedRecaudador;
import py.com.konecta.redcobros.entities.RedRecaudadorPK;

/**
 *
 * @author documenta
 */
public class RED_RECAUDADOR extends HttpServlet {

    public static String ID_RED = "ID_RED";
    public static String ID_RECAUDADOR = "ID_RECAUDADOR";
    public static String RED = "RED";
    public static String RECAUDADOR = "RECAUDADOR";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String CONSULTA_RED = "CONSULTA_RED";
    public static String CONSULTA_RECAUDADOR = "CONSULTA_RECAUDADOR";
    public static String RED_TICKET = "RED_TICKET";
    @EJB
    private RedRecaudadorFacade redRecaudadorFacade;

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
        JSONObject jsonRespuesta = new JSONObject();
        try {
            String opcion = request.getParameter("op");
            Long idRed = request.getParameter(RED_RECAUDADOR.ID_RED) != null && !request.getParameter(RED_RECAUDADOR.ID_RED).isEmpty() ? Long.valueOf(request.getParameter(RED_RECAUDADOR.ID_RED)) : null;
            Long idRecaudador = request.getParameter(RED_RECAUDADOR.ID_RECAUDADOR) != null && !request.getParameter(RED_RECAUDADOR.ID_RECAUDADOR).isEmpty() ? Long.valueOf(request.getParameter(RED_RECAUDADOR.ID_RECAUDADOR)) : null;
            String descripcion = request.getParameter(RED_RECAUDADOR.DESCRIPCION) != null && !request.getParameter(RED_RECAUDADOR.DESCRIPCION).isEmpty() ? request.getParameter(RED_RECAUDADOR.DESCRIPCION) : null;
            String redTicket = request.getParameter(RED_RECAUDADOR.RED_TICKET) != null && !request.getParameter(RED_RECAUDADOR.RED_TICKET).isEmpty() ? request.getParameter(RED_RECAUDADOR.RED_TICKET) : null;
            if (opcion.equalsIgnoreCase("AGREGAR")) {

                RedRecaudador newRedRec = new RedRecaudador(idRed, idRecaudador);
                newRedRec.setRedTicket(redTicket);
                if (descripcion != null) {
                    newRedRec.setDescripcion(descripcion);
                }
                try {
                    redRecaudadorFacade.save(newRedRec);
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    Logger.getLogger(RED_RECAUDADOR.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo guardar");
                }

            }else if (opcion.equalsIgnoreCase("MODIFICAR")) { 
                if(descripcion!=null){
                    RedRecaudador ejemplo = new RedRecaudador(idRed, idRecaudador);
                    ejemplo.setDescripcion(descripcion);
                    ejemplo.setRedTicket(redTicket);
                    redRecaudadorFacade.update(ejemplo);
                    jsonRespuesta.put("success", true);
                }else{
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se actualizo");
                }
            }else if (opcion.equalsIgnoreCase("BORRAR")) {                
                try {
                    redRecaudadorFacade.delete(new RedRecaudadorPK(idRed, idRecaudador));
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    Logger.getLogger(RED_RECAUDADOR.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo borrar");
                }
            } else if (opcion.equalsIgnoreCase("DETALLE")) {

                RedRecaudador redRec = redRecaudadorFacade.get(new RedRecaudador(idRed, idRecaudador));
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(RED_RECAUDADOR.RED, redRec.getRed().getDescripcion());
                jsonDetalle.put(RED_RECAUDADOR.RECAUDADOR, redRec.getRecaudador().getDescripcion());
                jsonDetalle.put(RED_RECAUDADOR.DESCRIPCION, redRec.getDescripcion());
                jsonDetalle.put(RED_RECAUDADOR.ID_RED, redRec.getRed().getIdRed());
                jsonDetalle.put(RED_RECAUDADOR.RED_TICKET, redRec.getRedTicket());
                jsonDetalle.put(RED_RECAUDADOR.ID_RECAUDADOR, redRec.getRecaudador().getIdRecaudador());
                

                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);

            } else if (opcion.equalsIgnoreCase("LISTAR")) {

                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                String critConsultaRed = request.getParameter(RED_RECAUDADOR.CONSULTA_RED) != null && !request.getParameter(RED_RECAUDADOR.CONSULTA_RED).isEmpty() ? request.getParameter(RED_RECAUDADOR.CONSULTA_RED) : null;
                String critConsultaRec = request.getParameter(RED_RECAUDADOR.CONSULTA_RECAUDADOR) != null && !request.getParameter(RED_RECAUDADOR.CONSULTA_RECAUDADOR).isEmpty() ? request.getParameter(RED_RECAUDADOR.CONSULTA_RECAUDADOR) : null;
                boolean like = false;
                int total = 0;
                List<RedRecaudador> lista;
                RedRecaudador ejemplo = new RedRecaudador();
                if (critConsultaRed != null) {
                    like = true;
                    Red red = new Red();
                    red.setDescripcion(critConsultaRed);
                    ejemplo.setRed(red);
                }
                if (critConsultaRec != null) {
                    like = true;
                    Recaudador rec = new Recaudador();
                    rec.setDescripcion(critConsultaRec);
                    ejemplo.setRecaudador(rec);
                }


                if (primerResultado != null && cantResultados != null) {
                    if (like) {
                        lista = redRecaudadorFacade.list(ejemplo, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "red", "ASC", true);
                    } else {
                        lista = redRecaudadorFacade.list(ejemplo, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "red", "ASC");
                    }
                } else {
                    if (like) {
                        lista = redRecaudadorFacade.list(ejemplo, "red", "ASC", true);
                    } else {
                        lista = redRecaudadorFacade.list(ejemplo, "red", "ASC");
                    }
                }

                JSONArray jsonRedRecaudadores = new JSONArray();
                for (RedRecaudador e : lista) {                    
                    JSONObject jsonRedRec = new JSONObject();
                    jsonRedRec.put(RED_RECAUDADOR.RED, e.getRed().getDescripcion());
                    jsonRedRec.put(RED_RECAUDADOR.RECAUDADOR, e.getRecaudador().getDescripcion());
                    jsonRedRec.put(RED_RECAUDADOR.ID_RED, e.getRed().getIdRed());
                    jsonRedRec.put(RED_RECAUDADOR.ID_RECAUDADOR, e.getRecaudador().getIdRecaudador());
                    jsonRedRec.put(RED_RECAUDADOR.DESCRIPCION, e.getDescripcion());
                    jsonRedRec.put(RED_RECAUDADOR.RED_TICKET, e.getRedTicket());
                    jsonRedRecaudadores.add(jsonRedRec);
                }
                total = redRecaudadorFacade.total(ejemplo, true);
                jsonRespuesta.put("RED_RECAUDADOR", jsonRedRecaudadores);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
            } else {
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la opcion pedida");
            }
        } catch (Exception ex) {
            Logger.getLogger(RED_RECAUDADOR.class.getName()).log(Level.SEVERE, null, ex);
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", "Error en la operacion");
        } finally {
            out.println(jsonRespuesta.toString());
            //out.close();
        }
        
    }

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
