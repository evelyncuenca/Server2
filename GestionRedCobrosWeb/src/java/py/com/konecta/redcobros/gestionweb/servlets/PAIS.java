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
import py.com.konecta.redcobros.ejb.PaisFacade;
import py.com.konecta.redcobros.entities.Pais;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class PAIS extends HttpServlet {
    public static String ID_PAIS="ID_PAIS";
    public static String NOMBRE="NOMBRE";
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
            String idPais=request.getParameter(PAIS.ID_PAIS);
            String nombre=request.getParameter(PAIS.NOMBRE);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Pais pais=new Pais();
                pais.setNombre(nombre);
                paisFacade.save(pais);
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                paisFacade.delete(new Long(idPais));
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Pais pais=new Pais();
                pais.setIdPais(new Long(idPais));
                pais.setNombre(nombre);
                paisFacade.update(pais);
                JSONObject jsonRespuesta=new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Pais pais=paisFacade.get(new Long(idPais));
                JSONObject jsonRespuesta=new JSONObject();
                JSONObject jsonDetalle=new JSONObject();
                jsonDetalle.put(PAIS.ID_PAIS, pais.getIdPais());
                jsonDetalle.put(PAIS.NOMBRE, pais.getNombre());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Pais pais=new Pais();
              //  pais.setNombre(nombre);
                List<Pais> lista;
                int total;
                String primerResultado=request.getParameter("start");
                String cantResultados=request.getParameter("limit");
                if (primerResultado!=null && cantResultados!=null) {
                 
                    lista=paisFacade.list(pais, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados),"nombre", "asc");
                } else {
                    lista=paisFacade.list(pais,"nombre", "asc");
                }
                total=paisFacade.total(pais);
                JSONObject jsonRespuesta=new JSONObject();
                JSONArray jsonPaises=new JSONArray();
                for (Pais e : lista) {
                    JSONObject jsonPais=new JSONObject();
                    jsonPais.put(PAIS.ID_PAIS, e.getIdPais());
                    jsonPais.put(PAIS.NOMBRE, e.getNombre());
                    jsonPaises.add(jsonPais);
                }
                jsonRespuesta.put("PAIS", jsonPaises);
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
            exc.printStackTrace();
            JSONObject jsonRespuesta=new JSONObject();
            jsonRespuesta.put("success", false);
           // jsonRespuesta.put("motivo", exc.getMessage());
             jsonRespuesta.put("motivo",Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private PaisFacade paisFacade;

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
