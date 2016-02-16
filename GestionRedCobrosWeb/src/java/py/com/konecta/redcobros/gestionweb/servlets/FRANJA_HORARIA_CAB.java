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
import py.com.konecta.redcobros.ejb.FranjaHorariaCabFacade;
import py.com.konecta.redcobros.entities.FranjaHorariaCab;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class FRANJA_HORARIA_CAB extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static String ID_FRANJA_HORARIA_CAB = "ID_FRANJA_HORARIA_CAB";
    public static String COMENTARIO = "COMENTARIO";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String RECAUDADOR = "RECAUDADOR";

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
            String idFranjaHorariaCab = request.getParameter(FRANJA_HORARIA_CAB.ID_FRANJA_HORARIA_CAB);
            String comentario = request.getParameter(FRANJA_HORARIA_CAB.COMENTARIO);
            String descripcion = request.getParameter(FRANJA_HORARIA_CAB.DESCRIPCION);
            String recaudador = request.getParameter(FRANJA_HORARIA_CAB.RECAUDADOR);

            if (opcion.equalsIgnoreCase("AGREGAR")) {
                FranjaHorariaCab franjaHorariaCab = new FranjaHorariaCab();
                franjaHorariaCab.setComentario(comentario);
                franjaHorariaCab.setDescripcion(descripcion);
                if (recaudador.matches("[0-9]+")) {
                    franjaHorariaCab.setRecaudador(new Recaudador(new Long(recaudador)));
                }
                franjaHorariaCabFacade.save(franjaHorariaCab);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                franjaHorariaCabFacade.delete(new Long(idFranjaHorariaCab));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                FranjaHorariaCab franjaHorariaCab = new FranjaHorariaCab();
                franjaHorariaCab.setComentario(comentario);
                franjaHorariaCab.setDescripcion(descripcion);
                franjaHorariaCab.setIdFranjaHorariaCab(new Long(idFranjaHorariaCab));
                if (recaudador.matches("[0-9]+")) {
                    franjaHorariaCab.setRecaudador(new Recaudador(new Long(recaudador)));
                }

                franjaHorariaCabFacade.update(franjaHorariaCab);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                FranjaHorariaCab franjaHorariaCab = franjaHorariaCabFacade.get(new Long(idFranjaHorariaCab));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(FRANJA_HORARIA_CAB.ID_FRANJA_HORARIA_CAB, franjaHorariaCab.getIdFranjaHorariaCab());
                jsonDetalle.put(FRANJA_HORARIA_CAB.COMENTARIO, (franjaHorariaCab.getComentario() != null) ? franjaHorariaCab.getComentario() : "");
                jsonDetalle.put(FRANJA_HORARIA_CAB.DESCRIPCION, franjaHorariaCab.getDescripcion());
                jsonDetalle.put(FRANJA_HORARIA_CAB.RECAUDADOR, (franjaHorariaCab.getRecaudador() != null) ? franjaHorariaCab.getRecaudador().getDescripcion() : "");

                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {

                FranjaHorariaCab franjaHorariaCab = new FranjaHorariaCab();
                franjaHorariaCab.setComentario(comentario);
                franjaHorariaCab.setDescripcion(descripcion);
                if (recaudador != null && !recaudador.isEmpty()) {
                    Recaudador rec = new Recaudador();
                    rec.setDescripcion(recaudador);
                    franjaHorariaCab.setRecaudador(rec);
                }

                //  franjaHorariaCab.setIdFranjaHorariaCab(new Long(idFranjaHorariaCab));
                List<FranjaHorariaCab> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = franjaHorariaCabFacade.list(franjaHorariaCab, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), true);
                } else {
                    lista = franjaHorariaCabFacade.list(franjaHorariaCab, true);
                }
                total = franjaHorariaCabFacade.total(franjaHorariaCab, true);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonPersonas = new JSONArray();
                for (FranjaHorariaCab e : lista) {

                    JSONObject jsonPersona = new JSONObject();

                    jsonPersona.put(FRANJA_HORARIA_CAB.ID_FRANJA_HORARIA_CAB, e.getIdFranjaHorariaCab());
                    jsonPersona.put(FRANJA_HORARIA_CAB.COMENTARIO, (e.getComentario() != null) ? e.getComentario() : "");
                    jsonPersona.put(FRANJA_HORARIA_CAB.DESCRIPCION, e.getDescripcion());
                    jsonPersona.put(FRANJA_HORARIA_CAB.RECAUDADOR, (e.getRecaudador() != null) ? e.getRecaudador().getDescripcion() : "");
                    jsonPersonas.add(jsonPersona);
                }
                jsonRespuesta.put("FRANJA_HORARIA_CAB", jsonPersonas);
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
            //  jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private FranjaHorariaCabFacade franjaHorariaCabFacade;

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
