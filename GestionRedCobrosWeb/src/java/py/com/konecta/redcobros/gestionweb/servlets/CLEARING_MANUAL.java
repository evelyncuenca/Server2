/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.ClearingManualFacade;
import py.com.konecta.redcobros.ejb.DistribucionClearingManualFacade;
import py.com.konecta.redcobros.entities.ClearingManual;
import py.com.konecta.redcobros.entities.DistribucionClearingManual;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author gustavo
 */
public class CLEARING_MANUAL extends HttpServlet {

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
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            String opcion = request.getParameter("op");
            Date fecha = (request.getParameter("FECHA") != null && !request.getParameter("FECHA").isEmpty()) ? sdf.parse(request.getParameter("FECHA")) : null;
            Date fechaAcreditacion = (request.getParameter("FECHA_ACREDITACION") != null && !request.getParameter("FECHA_ACREDITACION").isEmpty()) ? sdf.parse(request.getParameter("FECHA_ACREDITACION")) : null;
            Character debitoCreditoCabecera = (request.getParameter("DEBITO_CREDITO") != null && !request.getParameter("DEBITO_CREDITO").isEmpty()) ? request.getParameter("DEBITO_CREDITO").equalsIgnoreCase("DEBITO") ? 'D' : 'C' : null;
            String descripcionCabecera = request.getParameter("DESCRIPCION_CABECERA");
            String numeroCuentaCabecera = request.getParameter("CUENTA_CABECERA");
            Double montoCabecera = (request.getParameter("MONTO_CABECERA") != null && !request.getParameter("MONTO_CABECERA").isEmpty()) ? Double.parseDouble(request.getParameter("MONTO_CABECERA").replaceAll(",", "")) : null;
            String[] descripcionDetalle;
            String[] numeroCuentaDetalle;
            Double[] montoDetalle;
            String descripcionesDetalle = request.getParameter("DESCRIPCION_DETALLE");
            String cuentasDetalle = request.getParameter("NUMERO_CUENTA_DETALLE");
            String montosDetalle = request.getParameter("MONTO_DETALLE");
            if (descripcionesDetalle != null && !descripcionesDetalle.isEmpty()) {
                descripcionDetalle = descripcionesDetalle.trim().substring(1).split(";");
            } else {
                descripcionDetalle = new String[]{};
            }
            if (cuentasDetalle != null && !cuentasDetalle.isEmpty()) {
                numeroCuentaDetalle = cuentasDetalle.replaceAll(" ", "").substring(1).split(";");
            } else {
                numeroCuentaDetalle = new String[]{};
            }
            if (montosDetalle != null && !montosDetalle.isEmpty()) {
                String[] array = montosDetalle.replaceAll(" ", "").substring(1).split(";");
                montoDetalle = new Double[array.length];
                for (int i = 0; i < array.length; i++) {
                    montoDetalle[i] = Double.parseDouble(array[i].replaceAll(",", ""));

                }
            } else {
                montoDetalle = new Double[]{};
            }
            if (opcion.equals("OBTENER_CABECERA")) {
                ClearingManual clearing = new ClearingManual();
                clearing.setFecha(fecha);
                clearing = this.clearingManualFacade.get(clearing);
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                if (clearing != null) {
                    jsonDetalle.put("FECHA_ACREDITACION", (clearing.getFechaAcreditacion() != null) ? sdf.format(clearing.getFechaAcreditacion()) : "");
                    jsonDetalle.put("TIPO", clearing.getCabeceraDebitoCredito().equals('D') ? "DEBITO" : "CREDITO");
                    jsonDetalle.put("DESCRIPCION_CABECERA", clearing.getDescripcionBeneficiario());
                    jsonDetalle.put("CUENTA_CABECERA", clearing.getNumeroCuenta());
                    jsonDetalle.put("MONTO_CABECERA", UtilesSet.formatearNumerosSeparadorMiles(clearing.getMontoDistribuido(), false));
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("data", jsonDetalle);
                } else {
                    jsonDetalle.put("FECHA_ACREDITACION", "");
                    jsonDetalle.put("TIPO", "");
                    jsonDetalle.put("DESCRIPCION_CABECERA", "");
                    jsonDetalle.put("CUENTA_CABECERA", "");
                    jsonDetalle.put("MONTO_CABECERA", "");
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("data", jsonDetalle);

                }
                out.println(jsonRespuesta.toString());

            } else if (opcion.equals("OBTENER_DETALLE")) {
                ClearingManual ejemplo = new ClearingManual();
                ejemplo.setFecha(fecha);
                DistribucionClearingManual ejemplo2 = new DistribucionClearingManual();
                ejemplo2.setClearingManual(ejemplo);
                List<DistribucionClearingManual> lista = this.distribucionClearingManualFacade.list(ejemplo2);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonClearingDetArray = new JSONArray();
                for (DistribucionClearingManual e : lista) {
                    JSONObject jsonClearingDet = new JSONObject();
                    jsonClearingDet.put("DESCRIPCION_DETALLE", e.getDescripcionBeneficiario());
                    jsonClearingDet.put("NUMERO_CUENTA_DETALLE", e.getNumeroCuenta());
                    jsonClearingDet.put("MONTO_DETALLE", UtilesSet.formatearNumerosSeparadorMiles(e.getMonto(), false));
                    jsonClearingDetArray.add(jsonClearingDet);
                }
                jsonRespuesta.put("DETALLE", jsonClearingDetArray);
                jsonRespuesta.put("TOTAL", lista.size());
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equals("GUARDAR_CLEARING_MANUAL")) {
                this.clearingManualFacade.realizarClearing(fecha,
                        fechaAcreditacion,
                        debitoCreditoCabecera,
                        descripcionCabecera,
                        numeroCuentaCabecera,
                        montoCabecera,
                        descripcionDetalle,
                        numeroCuentaDetalle,
                        montoDetalle);
                JSONObject jsonRespuesta = new JSONObject();
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
            jsonRespuesta.put("motivo", exc.getMessage());
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private ClearingManualFacade clearingManualFacade;
    @EJB
    private DistribucionClearingManualFacade distribucionClearingManualFacade;

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
