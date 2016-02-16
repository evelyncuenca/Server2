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
import py.com.konecta.redcobros.ejb.DepartamentoFacade;
import py.com.konecta.redcobros.entities.Departamento;
import py.com.konecta.redcobros.entities.Pais;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class DEPARTAMENTO extends HttpServlet {

    public static String ID_DEPARTAMENTO = "ID_DEPARTAMENTO";
    public static String PAIS = "PAIS";
    public static String NOMBRE = "NOMBRE";

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
            String idDepartamento = request.getParameter(DEPARTAMENTO.ID_DEPARTAMENTO);
            String idPais = request.getParameter(DEPARTAMENTO.PAIS);
            String nombre = request.getParameter(DEPARTAMENTO.NOMBRE);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Departamento departamento = new Departamento();
                Pais p = new Pais();
                p.setIdPais(new Long(idPais));
                departamento.setPais(p);
                departamento.setNombre(nombre);
                departamentoFacade.save(departamento);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                departamentoFacade.delete(new Long(idDepartamento));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Departamento departamento = new Departamento();
                departamento.setIdDepartamento(new Long(idDepartamento));
                Pais p = new Pais();
                p.setIdPais(new Long(idPais));
                departamento.setPais(p);
                departamento.setNombre(nombre);
                departamentoFacade.update(departamento);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Departamento departamento = departamentoFacade.get(new Long(idDepartamento));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(DEPARTAMENTO.ID_DEPARTAMENTO, departamento.getIdDepartamento());
                jsonDetalle.put(DEPARTAMENTO.PAIS, departamento.getPais().getIdPais());
                jsonDetalle.put(DEPARTAMENTO.NOMBRE, departamento.getNombre());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Departamento departamento = new Departamento();
                departamento.setNombre(nombre);
                List<Departamento> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = departamentoFacade.list(departamento, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC");
                } else {
                    lista = departamentoFacade.list(departamento, "nombre", "ASC");
                }
                total = departamentoFacade.total(departamento);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonDepartamentos = new JSONArray();
                for (Departamento e : lista) {
                    JSONObject jsonDepartamento = new JSONObject();
                    jsonDepartamento.put(DEPARTAMENTO.ID_DEPARTAMENTO, e.getIdDepartamento());
                    jsonDepartamento.put(DEPARTAMENTO.PAIS, e.getPais().getNombre());
                    jsonDepartamento.put(DEPARTAMENTO.NOMBRE, e.getNombre());
                    jsonDepartamentos.add(jsonDepartamento);
                }
                jsonRespuesta.put("DEPARTAMENTO", jsonDepartamentos);
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
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private DepartamentoFacade departamentoFacade;

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
