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
import py.com.konecta.redcobros.ejb.CuentaFacade;
import py.com.konecta.redcobros.entities.Cuenta;
import py.com.konecta.redcobros.entities.EntidadFinanciera;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class CUENTA extends HttpServlet {


    public static String ID_CUENTA = "ID_CUENTA";
    public static String NRO_CUENTA = "NRO_CUENTA";
    public static String ALIAS_CUENTA = "ALIAS_CUENTA";
    public static String ENTIDAD_FINANCIERA = "ENTIDAD_FINANCIERA";
    @EJB
    private CuentaFacade cuentaFacade;
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
            String idCuenta = request.getParameter(CUENTA.ID_CUENTA);
            String nroCuenta = request.getParameter(CUENTA.NRO_CUENTA);
            String aliasCuenta = request.getParameter(CUENTA.ALIAS_CUENTA);
            String idEntidadFinanciera = request.getParameter(CUENTA.ENTIDAD_FINANCIERA);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Cuenta cuenta = new Cuenta();
                cuenta.setAliasCuenta(aliasCuenta);
                cuenta.setNroCuenta(nroCuenta);
                cuenta.setEntidadFinanciera(new EntidadFinanciera(new Long(idEntidadFinanciera)));
                cuentaFacade.save(cuenta);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                cuentaFacade.delete(new Long(idCuenta));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Cuenta cuenta = new Cuenta();
                cuenta.setIdCuenta(new Long(idCuenta));
                cuenta.setAliasCuenta(aliasCuenta);
                cuenta.setNroCuenta(nroCuenta);
                cuenta.setEntidadFinanciera(new EntidadFinanciera(new Long(idEntidadFinanciera)));
                cuentaFacade.update(cuenta);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Cuenta cuenta = cuentaFacade.get(new Long(idCuenta));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(CUENTA.ID_CUENTA, cuenta.getIdCuenta());
                jsonDetalle.put(CUENTA.NRO_CUENTA, cuenta.getNroCuenta());
                jsonDetalle.put(CUENTA.ALIAS_CUENTA, cuenta.getAliasCuenta());
                jsonDetalle.put(CUENTA.ENTIDAD_FINANCIERA, cuenta.getEntidadFinanciera().getIdEntidadFinanciera());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Cuenta cuenta = new Cuenta();
                cuenta.setAliasCuenta(aliasCuenta);
                List<Cuenta> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = cuentaFacade.list(cuenta, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados));
                } else {
                    lista = cuentaFacade.list(cuenta);
                }
                total = cuentaFacade.total(cuenta);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonCuentas = new JSONArray();
                for (Cuenta e : lista) {
                    JSONObject jsonCuenta = new JSONObject();
                    jsonCuenta.put(CUENTA.ID_CUENTA, e.getIdCuenta());
                    jsonCuenta.put(CUENTA.ALIAS_CUENTA, e.getAliasCuenta());
                    jsonCuenta.put(CUENTA.NRO_CUENTA, e.getNroCuenta());
                    jsonCuenta.put(CUENTA.ENTIDAD_FINANCIERA, e.getEntidadFinanciera().getDescripcion());
                    jsonCuentas.add(jsonCuenta);
                }
                jsonRespuesta.put("CUENTA", jsonCuentas);
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
