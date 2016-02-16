/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import py.com.konecta.redcobros.ejb.EntidadFacade;
import py.com.konecta.redcobros.ejb.EventoFacade;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.Evento;

/**
 *
 * @author documenta
 */
public class EVENTO extends HttpServlet {

    @EJB
    private EventoFacade eventoFacade;
    @EJB
    private EntidadFacade entidadFacade;
    public static String DESCRIPCION = "DESCRIPCION";
    public static String ENTIDAD = "ENTIDAD";
    public static String FECHA_INI = "FECHA_INI";
    public static String FECHA_FIN = "FECHA_FIN";
    public static String ID_EVENTO = "ID_EVENTO";
    public static String CONSULTA = "CONSULTA";
    public static String HABILITADO = "HABILITADO";

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            String opcion = request.getParameter("op");
            Long idEvento = request.getParameter(EVENTO.ID_EVENTO) != null && !request.getParameter(EVENTO.ID_EVENTO).isEmpty() ? Long.valueOf(request.getParameter(EVENTO.ID_EVENTO)) : null;
            String descripcion = request.getParameter(EVENTO.DESCRIPCION) != null && !request.getParameter(EVENTO.DESCRIPCION).isEmpty() ? request.getParameter(EVENTO.DESCRIPCION).toUpperCase() : null;
            Long entidad = request.getParameter(EVENTO.ENTIDAD) != null && !request.getParameter(EVENTO.ENTIDAD).isEmpty() ? Long.valueOf(request.getParameter(EVENTO.ENTIDAD)) : null;
            String consulta = request.getParameter(EVENTO.CONSULTA) != null && !request.getParameter(EVENTO.CONSULTA).isEmpty() ? request.getParameter(EVENTO.CONSULTA) : null;
            String habilitado = request.getParameter(EVENTO.HABILITADO) != null && !request.getParameter(EVENTO.HABILITADO).isEmpty() ? request.getParameter(EVENTO.HABILITADO) : null;

            Date fechaIni = null;
            Date fechaFin = null;
            try {
                fechaIni = request.getParameter(EVENTO.FECHA_INI) != null && !request.getParameter(EVENTO.FECHA_INI).isEmpty() ? sdf.parse(request.getParameter(EVENTO.FECHA_INI)) : null;
                fechaFin = request.getParameter(EVENTO.FECHA_FIN) != null && !request.getParameter(EVENTO.FECHA_FIN).isEmpty() ? sdf.parse(request.getParameter(EVENTO.FECHA_FIN)) : null;
            } catch (Exception ex) {
                Logger.getLogger(EVENTO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Evento evento = new Evento();
                evento.setDescripcion(descripcion);
                evento.setEntidad(entidad);
                evento.setFechaInicio(fechaIni);
                evento.setFechaFin(fechaFin);
                evento.setHabilitado("N");
                try {
                    eventoFacade.save(evento);
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    Logger.getLogger(EVENTO.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo modificar");
                }
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Evento evento = eventoFacade.get(idEvento);
                evento.setEntidad(entidad);
                evento.setDescripcion(descripcion);
                evento.setFechaInicio(fechaIni);
                evento.setFechaFin(fechaFin);
                try {
                    eventoFacade.update(evento);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("motivo", "Operacion Exitosa");
                } catch (Exception ex) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo modificar");
                }
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                try {
                    eventoFacade.delete(idEvento);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("motivo", "Operacion Exitosa");
                } catch (Exception ex) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo borrar");
                }
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Evento evento = eventoFacade.get(idEvento);
                Entidad ejEntidad  = entidadFacade.get(evento.getEntidad());
                JSONObject jsonDetalle = new JSONObject();
                JSONObject jsonEntidad = new JSONObject();
                
                jsonEntidad.put(EVENTO.ENTIDAD, evento.getEntidad());
                jsonEntidad.put("DESCRIPCION_ENTIDAD", ejEntidad.getNombre());
                jsonDetalle.put(EVENTO.ID_EVENTO, evento.getIdEvento());
                jsonDetalle.put(EVENTO.DESCRIPCION, evento.getDescripcion());
                jsonDetalle.put(EVENTO.FECHA_INI, sdf.format(evento.getFechaInicio()));
                jsonDetalle.put(EVENTO.FECHA_FIN, sdf.format(evento.getFechaFin()));
                jsonDetalle.put(EVENTO.ENTIDAD, jsonEntidad);
                jsonDetalle.put(EVENTO.HABILITADO, evento.getHabilitado());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                Evento evento = new Evento();
                evento.setDescripcion(consulta);
                List<Evento> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = eventoFacade.list(evento, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
                } else {
                    lista = eventoFacade.list(evento, "descripcion", "ASC", true);
                }
                total = eventoFacade.total(evento);
                JSONArray jsonEventos = new JSONArray();
                for (Evento e : lista) {
                    JSONObject jsonEvento = new JSONObject();
                    jsonEvento.put(EVENTO.ID_EVENTO, e.getIdEvento());
                    jsonEvento.put(EVENTO.DESCRIPCION, e.getDescripcion());
                    try {
                        jsonEvento.put(EVENTO.FECHA_INI, sdf.format(e.getFechaInicio()));
                        jsonEvento.put(EVENTO.FECHA_FIN, sdf.format(e.getFechaFin()));
                    } catch (Exception ex) {
                        Logger.getLogger(EVENTO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jsonEvento.put(EVENTO.ENTIDAD, e.getEntidad());
                    jsonEvento.put(EVENTO.HABILITADO, e.getHabilitado());
                    jsonEventos.add(jsonEvento);
                }
                jsonRespuesta.put("EVENTO", jsonEventos);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
            } else {
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
            }
        } catch (Exception ex) {
            Logger.getLogger(EVENTO.class.getName()).log(Level.SEVERE, null, ex);
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", ex.getMessage());

        } finally {
            out.println(jsonRespuesta.toString());
            //out.close();            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
