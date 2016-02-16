/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import py.com.konecta.redcobros.ejb.EventoFacade;
import py.com.konecta.redcobros.ejb.SectorEventoFacade;
import py.com.konecta.redcobros.ejb.SuscriptorEventoFacade;
import py.com.konecta.redcobros.ejb.SuscriptorFacade;
import py.com.konecta.redcobros.entities.Evento;
import py.com.konecta.redcobros.entities.SectorEvento;
import py.com.konecta.redcobros.entities.Suscriptor;
import py.com.konecta.redcobros.entities.SuscriptorEvento;
import py.com.konecta.redcobros.entities.SuscriptorEventoPK;

/**
 *
 * @author documenta
 */
public class SUSCRIPTORES extends HttpServlet {

    @EJB
    private SuscriptorFacade suscriptorFacade;
    @EJB
    private SectorEventoFacade sectorEventoFacade;
    @EJB
    private SuscriptorEventoFacade suscriptorEventoFacade;
    @EJB
    private EventoFacade eventoFacade;
    public static String NOMBRE = "NOMBRE";
    public static String APELLIDO = "APELLIDO";
    public static String CI = "CI";
    public static String TELEFONO = "TELEFONO";
    public static String EMAIL = "EMAIL";
    public static String CONSULTA = "CONSULTA";
    public static String A_ASIGNAR = "A_ASIGNAR";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonRespuesta = new JSONObject();
        try {
            String opcion = request.getParameter("op");
            String nombre = request.getParameter(SUSCRIPTORES.NOMBRE) != null && !request.getParameter(SUSCRIPTORES.NOMBRE).isEmpty() ? request.getParameter(SUSCRIPTORES.NOMBRE).toUpperCase() : null;
            String apellido = request.getParameter(SUSCRIPTORES.APELLIDO) != null && !request.getParameter(SUSCRIPTORES.APELLIDO).isEmpty() ? request.getParameter(SUSCRIPTORES.APELLIDO).toUpperCase() : null;
            Long ci = request.getParameter(SUSCRIPTORES.CI) != null && !request.getParameter(SUSCRIPTORES.CI).isEmpty() ? new Long(request.getParameter(SUSCRIPTORES.CI)) : null;
            String telefono = request.getParameter(SUSCRIPTORES.TELEFONO) != null && !request.getParameter(SUSCRIPTORES.TELEFONO).isEmpty() ? request.getParameter(SUSCRIPTORES.TELEFONO) : null;
            String email = request.getParameter(SUSCRIPTORES.EMAIL) != null && !request.getParameter(SUSCRIPTORES.EMAIL).isEmpty() ? request.getParameter(SUSCRIPTORES.EMAIL) : null;
            String consulta = request.getParameter(SUSCRIPTORES.CONSULTA);
            String eventos = request.getParameter(SUSCRIPTORES.A_ASIGNAR);
            String[] eventosArray;
            if (eventos != null && !eventos.isEmpty()) {
                eventosArray = eventos.replaceAll(" ", "").split(",");
            } else {
                eventosArray = new String[]{};
            }
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Suscriptor suscriptor = new Suscriptor();
                suscriptor.setNombre(nombre);
                suscriptor.setApellido(apellido);
                suscriptor.setCi(ci);
                suscriptor.setEmail(email);
                suscriptor.setTelefono(telefono);
                try {
                    suscriptorFacade.save(suscriptor);
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    Logger.getLogger(SUSCRIPTORES.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo modificar");
                }
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Suscriptor suscriptor = suscriptorFacade.get(ci);
                suscriptor.setApellido(apellido);
                suscriptor.setEmail(email);
                suscriptor.setNombre(nombre);
                suscriptor.setTelefono(telefono);
                try {
                    suscriptorFacade.update(suscriptor);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("motivo", "Operacion Exitosa");
                } catch (Exception ex) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo modificar");
                }
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                try {
                    suscriptorFacade.delete(ci);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("motivo", "Operacion Exitosa");
                } catch (Exception ex) {
                    Logger.getLogger(SUSCRIPTORES.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo borrar");
                }
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Suscriptor suscriptor = suscriptorFacade.get(ci);
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(SUSCRIPTORES.NOMBRE, suscriptor.getNombre());
                jsonDetalle.put(SUSCRIPTORES.APELLIDO, suscriptor.getApellido());
                jsonDetalle.put(SUSCRIPTORES.CI, suscriptor.getCi());
                jsonDetalle.put(SUSCRIPTORES.TELEFONO, suscriptor.getTelefono() != null ? suscriptor.getTelefono() : "");
                jsonDetalle.put(SUSCRIPTORES.EMAIL, suscriptor.getEmail() != null ? suscriptor.getEmail() : "");
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Suscriptor suscriptor = new Suscriptor();
                suscriptor.setNombre(consulta);
                List<Suscriptor> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = suscriptorFacade.list(suscriptor, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC", true);
                } else {
                    lista = suscriptorFacade.list(suscriptor, "descripcion", "ASC", true);
                }
                total = suscriptorFacade.total(suscriptor, true);
                JSONArray jsonSuscriptor = new JSONArray();
                for (Suscriptor e : lista) {
                    JSONObject jsonItem = new JSONObject();
                    jsonItem.put(SUSCRIPTORES.NOMBRE, e.getNombre());
                    jsonItem.put(SUSCRIPTORES.APELLIDO, e.getApellido());
                    jsonItem.put(SUSCRIPTORES.CI, e.getCi());
                    jsonItem.put(SUSCRIPTORES.TELEFONO, e.getTelefono() != null ? e.getTelefono() : "");
                    jsonItem.put(SUSCRIPTORES.EMAIL, e.getEmail() != null ? e.getEmail() : "");
                    jsonSuscriptor.add(jsonItem);
                }
                jsonRespuesta.put("SUSCRIPTORES", jsonSuscriptor);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
            } else if (opcion.equalsIgnoreCase("LISTAR_EVENTOS")) {
                String flagAsignacion = request.getParameter("FLAG_ASIGNACION");
                List<SuscriptorEvento> suscriptorList = suscriptorEventoFacade.list(new SuscriptorEvento(ci, null));
                List<Evento> eventosList = new ArrayList<Evento>();
                if (flagAsignacion.equalsIgnoreCase("SI")) {
                    for (SuscriptorEvento it : suscriptorList) {
                        if (!eventosList.contains(it.getSectorEvento().getEvento())) {
                            eventosList.add(it.getSectorEvento().getEvento());
                        }
                    }
                } else {
                    eventosList = eventoFacade.list();
                    for (SuscriptorEvento it : suscriptorList) {
                        eventosList.remove(it.getSectorEvento().getEvento());
                    }
                }
                JSONArray jsonEventos = new JSONArray();
                for (Evento e : eventosList) {
                    JSONObject jsonEvento = new JSONObject();
                    jsonEvento.put("ID_EVENTO", e.getIdEvento());
                    jsonEvento.put("DESCRIPCION", e.getDescripcion());
                    jsonEventos.add(jsonEvento);
                }
                jsonRespuesta.put("EVENTOS", jsonEventos);
                jsonRespuesta.put("TOTAL", eventosList.size());
                jsonRespuesta.put("success", true);
            } else if (opcion.equalsIgnoreCase("ASIGNAR_EVENTOS")) {
                Suscriptor suscriptor = suscriptorFacade.get(new Long(ci));
                List<SuscriptorEvento> suscriptorEventoList = new ArrayList<SuscriptorEvento>();
                SectorEvento sectorEvento = new SectorEvento();
                for (String idEvento : eventosArray) {
                    sectorEvento.setEvento(new Evento(new Long(idEvento)));
                    List<SectorEvento> sectorEventoList = sectorEventoFacade.list(sectorEvento);
                    for (SectorEvento it : sectorEventoList) {
                        SuscriptorEvento suscriptorEvento = new SuscriptorEvento(new SuscriptorEventoPK(ci, it.getIdSector()));
                        suscriptorEvento.setSectorEvento(new SectorEvento(it.getIdSector()));
                        suscriptorEvento.setSuscriptor(new Suscriptor(ci));
                        suscriptorEvento.setFechaSuscripcion(new Date());
                        if (!suscriptorEventoList.contains(suscriptorEvento)) {
                            suscriptorEventoList.add(suscriptorEvento);
                        }
                    }
                }
                suscriptor.setSuscriptorEventoList(suscriptorEventoList);
                suscriptorFacade.update(suscriptor);
                jsonRespuesta.put("success", true);

            } else {
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
            }

        } catch (Exception ex) {
            Logger.getLogger(SUSCRIPTORES.class.getName()).log(Level.SEVERE, null, ex);
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
