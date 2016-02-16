/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.FranjaHorariaDetFacade;
import py.com.konecta.redcobros.entities.FranjaHorariaCab;
import py.com.konecta.redcobros.entities.FranjaHorariaDet;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class FRANJA_HORARIA_DET extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static String ID_FRANJA_HORARIA_DET = "ID_FRANJA_HORARIA_DET";
    public static String DIA = "DIA";
    public static String HORA_FIN = "HORA_FIN";
    public static String HORA_INI = "HORA_INI";
    public static String FRANJA_HORARIA_CAB = "FRANJA_HORARIA_CAB";

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
            String dia = request.getParameter(FRANJA_HORARIA_DET.DIA);
            String horaIni = request.getParameter(FRANJA_HORARIA_DET.HORA_INI);
            String horaFin = request.getParameter(FRANJA_HORARIA_DET.HORA_FIN);
            String idFranjaHorariaCab = request.getParameter(FRANJA_HORARIA_DET.FRANJA_HORARIA_CAB);
            String idFranjaHorariaDet = request.getParameter(FRANJA_HORARIA_DET.ID_FRANJA_HORARIA_DET);
            String[] dias = {"-", "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");



            if (opcion.equalsIgnoreCase("AGREGAR")) {
                JSONObject jsonRespuesta = new JSONObject();
                if (format.parse(horaFin).getTime() >= format.parse(horaIni).getTime()) {

                    FranjaHorariaDet franjaHorariaDet = new FranjaHorariaDet();
                    franjaHorariaDet.setDia(new BigInteger(dia));
                    franjaHorariaDet.setHoraFin(format.parse(horaFin));
                    franjaHorariaDet.setHoraIni(format.parse(horaIni));
                    franjaHorariaDet.setFranjaHorariaCab(new FranjaHorariaCab(new Long(idFranjaHorariaCab)));

                    franjaHorariaDetFacade.save(franjaHorariaDet);

                    jsonRespuesta.put("success", true);
                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "Hora de Inicio mayor que Hora Fin");
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                franjaHorariaDetFacade.delete(new Long(idFranjaHorariaDet));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                FranjaHorariaDet franjaHorariaDet = new FranjaHorariaDet();
                franjaHorariaDet.setDia(new BigInteger(dia));
                franjaHorariaDet.setHoraFin(format.parse(horaFin));
                franjaHorariaDet.setHoraIni(format.parse(horaIni));
                franjaHorariaDet.setFranjaHorariaCab(new FranjaHorariaCab(new Long(idFranjaHorariaCab)));
                franjaHorariaDet.setIdFranjaHorariaDet(new Long(idFranjaHorariaDet));
                franjaHorariaDetFacade.update(franjaHorariaDet);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                FranjaHorariaDet franjaHorariaDet = franjaHorariaDetFacade.get(new Long(idFranjaHorariaDet));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(FRANJA_HORARIA_DET.DIA, franjaHorariaDet.getDia());
                jsonDetalle.put(FRANJA_HORARIA_DET.HORA_FIN, format.format(franjaHorariaDet.getHoraFin()));
                jsonDetalle.put(FRANJA_HORARIA_DET.HORA_INI, format.format(franjaHorariaDet.getHoraIni()));
                jsonDetalle.put(FRANJA_HORARIA_DET.FRANJA_HORARIA_CAB, franjaHorariaDet.getFranjaHorariaCab().getIdFranjaHorariaCab());
                jsonDetalle.put(FRANJA_HORARIA_DET.ID_FRANJA_HORARIA_DET, franjaHorariaDet.getIdFranjaHorariaDet());

                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                FranjaHorariaDet franjaHorariaDet = new FranjaHorariaDet();

                if (idFranjaHorariaCab != null && idFranjaHorariaCab.matches("[0-9]+")) {
                    franjaHorariaDet.setFranjaHorariaCab(new FranjaHorariaCab(new Long(idFranjaHorariaCab)));
                }

                List<FranjaHorariaDet> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = franjaHorariaDetFacade.list(franjaHorariaDet, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados),"dia","ASC");
                } else {
                    lista = franjaHorariaDetFacade.list(franjaHorariaDet,"dia","ASC");
                }
                total = franjaHorariaDetFacade.total(franjaHorariaDet);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonPersonas = new JSONArray();
                for (FranjaHorariaDet e : lista) {
                    JSONObject jsonPersona = new JSONObject();

                    jsonPersona.put(FRANJA_HORARIA_DET.DIA, dias[e.getDia().intValue()] + "");
                    jsonPersona.put(FRANJA_HORARIA_DET.HORA_FIN, format.format(e.getHoraFin()));
                    jsonPersona.put(FRANJA_HORARIA_DET.HORA_INI, format.format(e.getHoraIni()));
                    jsonPersona.put(FRANJA_HORARIA_DET.FRANJA_HORARIA_CAB, e.getFranjaHorariaCab().getDescripcion());
                    jsonPersona.put(FRANJA_HORARIA_DET.ID_FRANJA_HORARIA_DET, e.getIdFranjaHorariaDet());
                    jsonPersonas.add(jsonPersona);
                }
                jsonRespuesta.put("FRANJA_HORARIA_DET", jsonPersonas);
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
             jsonRespuesta.put("motivo",Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private FranjaHorariaDetFacade franjaHorariaDetFacade;

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
