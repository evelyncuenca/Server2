/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
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
import py.com.konecta.redcobros.ejb.SectorEventoFacade;
import py.com.konecta.redcobros.entities.Evento;
import py.com.konecta.redcobros.entities.SectorEvento;

/**
 *
 * @author documenta
 */
public class SECTOR extends HttpServlet {

    @EJB
    private SectorEventoFacade sectorEventoFacade;
    public static String ID_SECTOR = "ID_SECTOR";
    public static String ID_EVENTO = "ID_EVENTO";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String MONTO = "MONTO";
    public static String EVENTO = "EVENTO";
    public static String CONSULTA = "CONSULTA";

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
            Long idEvento = request.getParameter(SECTOR.ID_EVENTO) != null && !request.getParameter(SECTOR.ID_EVENTO).isEmpty() ? Long.valueOf(request.getParameter(SECTOR.ID_EVENTO)) : null;
            Integer idSector = request.getParameter(SECTOR.ID_SECTOR) != null && !request.getParameter(SECTOR.ID_SECTOR).isEmpty() ? Integer.valueOf(request.getParameter(SECTOR.ID_SECTOR)) : null;
            String descripcion = request.getParameter(SECTOR.DESCRIPCION) != null && !request.getParameter(SECTOR.DESCRIPCION).isEmpty() ? request.getParameter(SECTOR.DESCRIPCION).toUpperCase() : null;
            BigDecimal monto = request.getParameter(SECTOR.MONTO) != null && !request.getParameter(SECTOR.MONTO).isEmpty() ? new BigDecimal(request.getParameter(SECTOR.MONTO).replace(",", "")) : null;
            String consulta = request.getParameter(SECTOR.CONSULTA) != null && !request.getParameter(SECTOR.CONSULTA).isEmpty() ? request.getParameter(SECTOR.CONSULTA) : null;
            NumberFormat nf = NumberFormat.getInstance();
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                SectorEvento sectorEvento = new SectorEvento();
                sectorEvento.setEvento(new Evento(idEvento));
                sectorEvento.setDescripcion(descripcion);
                sectorEvento.setMonto(monto);
                try {
                    sectorEventoFacade.save(sectorEvento);
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    Logger.getLogger(EVENTO.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo modificar");
                }
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                SectorEvento sectorEvento = sectorEventoFacade.get(idSector);
                sectorEvento.setMonto(monto);
                sectorEvento.setDescripcion(descripcion);
                try {
                    sectorEventoFacade.update(sectorEvento);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("motivo", "Operacion Exitosa");
                } catch (Exception ex) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo modificar");
                }
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                try {
                    sectorEventoFacade.delete(idSector);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("motivo", "Operacion Exitosa");
                } catch (Exception ex) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo borrar");
                }
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                SectorEvento sectorEvento = sectorEventoFacade.get(idSector);
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(SECTOR.EVENTO, sectorEvento.getEvento().getDescripcion());
                jsonDetalle.put(SECTOR.DESCRIPCION, sectorEvento.getDescripcion());
                jsonDetalle.put(SECTOR.MONTO, sectorEvento.getMonto());
                jsonDetalle.put(SECTOR.ID_SECTOR, sectorEvento.getIdSector());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                SectorEvento sectorEvento = new SectorEvento();
                sectorEvento.setDescripcion(consulta);
                sectorEvento.setEvento(new Evento(idEvento));
                List<SectorEvento> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = sectorEventoFacade.list(sectorEvento, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC");
                } else {
                    lista = sectorEventoFacade.list(sectorEvento, "descripcion", "ASC");
                }
                total = sectorEventoFacade.total(sectorEvento);
                JSONArray jsonSectorEvento = new JSONArray();
                for (SectorEvento e : lista) {
                    JSONObject jsonItem = new JSONObject();
                    jsonItem.put(SECTOR.ID_SECTOR, e.getIdSector());
                    jsonItem.put(SECTOR.EVENTO, e.getEvento().getDescripcion());
                    jsonItem.put(SECTOR.DESCRIPCION, e.getDescripcion());
                    jsonItem.put(SECTOR.EVENTO, e.getEvento().getDescripcion());
                    jsonItem.put(SECTOR.MONTO, nf.format(e.getMonto()));
                    jsonSectorEvento.add(jsonItem);
                }
                jsonRespuesta.put("SECTOR", jsonSectorEvento);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
            } else {
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
            }
        } catch (Exception ex) {
            Logger.getLogger(SECTOR.class.getName()).log(Level.SEVERE, null, ex);
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", ex.getMessage());

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
