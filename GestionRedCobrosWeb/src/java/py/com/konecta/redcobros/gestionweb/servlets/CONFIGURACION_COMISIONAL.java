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
import py.com.konecta.redcobros.ejb.ConfigClearingServicioFacade;
import py.com.konecta.redcobros.ejb.ConfigComisionalDetalleFacade;
import py.com.konecta.redcobros.ejb.ConfiguracionComisionalFacade;
import py.com.konecta.redcobros.entities.ConfigClearingServicio;
import py.com.konecta.redcobros.entities.ConfigComisionalDetalle;
import py.com.konecta.redcobros.entities.ConfiguracionComisional;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.ServicioRc;
import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.entities.TipoClearing;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
public class CONFIGURACION_COMISIONAL extends HttpServlet {

    public static String RED = "RED";
    public static String SERVICIO = "SERVICIO";
    public static String RECAUDADOR = "RECAUDADOR";
    public static String TIPO_CLEARING = "TIPO_CLEARING";
    public static String SUCURSAL = "SUCURSAL";
    public static String DESDE = "DESDE";
    public static String HASTA = "HASTA";
    public static String ROL_COMISIONISTA = "ROL_COMISIONISTA";
    public static String ROL_COMISIONISTA_DESCRIPCION = "ROL_COMISIONISTA_DESCRIPCION";
    public static String BENEFICIARIO = "BENEFICIARIO";
    public static String BENEFICIARIO_DESCRIPCION = "BENEFICIARIO_DESCRIPCION";
    public static String BENEFICIARIO_ID = "BENEFICIARIO_ID";
    public static String CONFIGURACION_COMISIONAL_ID = "CONFIGURACION_COMISIONAL_ID";
    public static String CONFIG_COMISIONAL_DET_ID = "CONFIG_COMISIONAL_DET_ID";
    public static String VALOR = "VALOR";
    public static String TOTAL_REPARTIR = "TOTAL_REPARTIR";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String ID_RANGO = "ID_RANGO";

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
            String opcion = request.getParameter("op");
            String red = request.getParameter(CONFIGURACION_COMISIONAL.RED);
            String servicio = request.getParameter(CONFIGURACION_COMISIONAL.SERVICIO);
            String recaudador = request.getParameter(CONFIGURACION_COMISIONAL.RECAUDADOR);
            String sucursal = request.getParameter(CONFIGURACION_COMISIONAL.SUCURSAL);
            String tipoClearing = request.getParameter(CONFIGURACION_COMISIONAL.TIPO_CLEARING);
            String desde = request.getParameter(CONFIGURACION_COMISIONAL.DESDE);
            String hasta = request.getParameter(CONFIGURACION_COMISIONAL.HASTA);
            String rolComisionista = request.getParameter(CONFIGURACION_COMISIONAL.ROL_COMISIONISTA);
            String configClearingServicio = request.getParameter(CONFIGURACION_COMISIONAL.ID_RANGO);
            String beneficiario = request.getParameter(CONFIGURACION_COMISIONAL.BENEFICIARIO);
            String valor = request.getParameter(CONFIGURACION_COMISIONAL.VALOR);
            String descripcion = request.getParameter(CONFIGURACION_COMISIONAL.DESCRIPCION);
            String totalRepartir = request.getParameter(CONFIGURACION_COMISIONAL.TOTAL_REPARTIR);
            Long[] idRolComisionistaArray;
            Long[] idBeneficiarioArray;
            Double[] valorArray;
            Long redInt = (red != null && !red.isEmpty()) ? new Long(red) : null;
            Long servicioInt = (servicio != null && !servicio.isEmpty()) ? new Long(servicio) : null;
            Long tipoClearingInt = (tipoClearing != null && !tipoClearing.isEmpty()) ? new Long(tipoClearing) : null;
            Long configClearingServicioInt = (configClearingServicio != null && !configClearingServicio.isEmpty()) ? new Long(configClearingServicio) : null;
            Long recaudadorInt = (recaudador != null && !recaudador.isEmpty()) ? new Long(recaudador) : null;
            Long sucursalInt = (sucursal != null && !sucursal.isEmpty()) ? new Long(sucursal) : null;
            Double desdeD = (desde != null && !desde.isEmpty()) ? Double.parseDouble(desde.replaceAll(",", "")) : null;
            Double hastaD = (hasta != null && !hasta.isEmpty()) ? Double.parseDouble(hasta.replaceAll(",", "")) : null;
            Double totalRepartirDouble = (totalRepartir != null && !totalRepartir.isEmpty()) ? Double.parseDouble(totalRepartir.replaceAll(",", "")) : null;
            if (rolComisionista != null && !rolComisionista.isEmpty()) {
                String[] array = rolComisionista.substring(0, rolComisionista.length() - 1).replaceAll(" ", "").split(";");
                idRolComisionistaArray = new Long[array.length];
                for (int i = 0; i < array.length; i++) {
                    if (!array[i].equalsIgnoreCase("null")) {
                        idRolComisionistaArray[i] = new Long(array[i]);
                    } else {
                        idRolComisionistaArray[i] = null;
                    }

                }
            } else {
                idRolComisionistaArray = new Long[]{};
            }
            if (beneficiario != null && !beneficiario.isEmpty()) {
                String[] array = beneficiario.substring(0, beneficiario.length() - 1).replaceAll(" ", "").split(";");
                idBeneficiarioArray = new Long[array.length];
                for (int i = 0; i < array.length; i++) {
                    if (!array[i].equalsIgnoreCase("null")) {
                        idBeneficiarioArray[i] = new Long(array[i]);
                    } else {
                        idBeneficiarioArray[i] = null;
                    }

                }
            } else {
                idBeneficiarioArray = new Long[]{};
            }
            if (valor != null && !valor.isEmpty()) {
                String[] array = valor.substring(0, valor.length() - 1).replaceAll(" ", "").split(";");
                valorArray = new Double[array.length];
                for (int i = 0; i < array.length; i++) {
                    if (!array[i].equalsIgnoreCase("null")) {
                        // valorArray[i] = Double.parseDouble(array[i].replaceAll(",", "."));
                        valorArray[i] = Double.parseDouble(array[i].replaceAll(",", ""));
                    } else {
                        valorArray[i] = null;
                    }

                }
            } else {
                valorArray = new Double[]{};
            }
            if (opcion.equals("GUARDAR_RANGO_ASIGNAR")) {
                this.configClearingServicioFacade.agregarModificarConfiguracion(configClearingServicioInt, redInt, servicioInt, tipoClearingInt, desdeD, hastaD, totalRepartirDouble);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equals("ELIMINAR_RANGO_ASIGNAR")) {
                this.configClearingServicioFacade.eliminarConfiguracion(configClearingServicioInt);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equals("VER_RANGOS")) {
                List<ConfigClearingServicio> lista = null;
                if (servicioInt > 2) {
                    ConfigClearingServicio ejemplo = new ConfigClearingServicio();
                    ServicioRc src = new ServicioRc();
                    src.setIdServicio(servicioInt.intValue());
                    ejemplo.setServicioRc(src);
                    Red r = new Red();
                    r.setIdRed(redInt);
                    ejemplo.setRed(r);
                    TipoClearing tc = new TipoClearing();
                    tc.setIdTipoClearing(tipoClearingInt);
                    ejemplo.setTipoClearing(tc);
                    lista = this.configClearingServicioFacade.list(ejemplo);
                } else {
                    lista = this.configClearingServicioFacade.verRangosCCS(redInt, servicioInt, tipoClearingInt);
                }
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonConfigClearingServicioArray = new JSONArray();
                for (ConfigClearingServicio e : lista) {
                    JSONObject jsonConfigClearingServicio = new JSONObject();
                    jsonConfigClearingServicio.put(CONFIGURACION_COMISIONAL.ID_RANGO, e.getIdConfigClearingServicio());
                    jsonConfigClearingServicio.put(CONFIGURACION_COMISIONAL.DESDE, UtilesSet.formatearNumerosSeparadorMiles(e.getDesde(), false));
                    jsonConfigClearingServicio.put(CONFIGURACION_COMISIONAL.HASTA, UtilesSet.formatearNumerosSeparadorMiles(e.getHasta(), false));
                    jsonConfigClearingServicio.put(CONFIGURACION_COMISIONAL.TOTAL_REPARTIR, UtilesSet.formatearNumerosSeparadorMiles(e.getValor(), true));
                    jsonConfigClearingServicioArray.add(jsonConfigClearingServicio);
                }
                jsonRespuesta.put("RANGOS", jsonConfigClearingServicioArray);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equals("VER_RANGOS_DETALLE")) {
                ConfigClearingServicio entity = this.configClearingServicioFacade.get(new Long(configClearingServicio));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(CONFIGURACION_COMISIONAL.ID_RANGO, entity.getIdConfigClearingServicio());
                jsonDetalle.put(CONFIGURACION_COMISIONAL.DESDE, UtilesSet.formatearNumerosSeparadorMiles(entity.getDesde(), false));
                jsonDetalle.put(CONFIGURACION_COMISIONAL.HASTA, UtilesSet.formatearNumerosSeparadorMiles(entity.getHasta(), false));
                jsonDetalle.put(CONFIGURACION_COMISIONAL.TOTAL_REPARTIR, UtilesSet.formatearNumerosSeparadorMiles(entity.getValor(), true));
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equals("OBTENER_PLANTILLA_CONFIGURACION")) {
                List<ConfigComisionalDetalle> lista = null;
                lista = this.configComFacade.obtenerDetalle(configClearingServicioInt, recaudadorInt, sucursalInt);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonConfigComisionalDetArray = new JSONArray();
                for (ConfigComisionalDetalle e : lista) {
                    JSONObject jsonConfigComisionalDet = new JSONObject();
                    jsonConfigComisionalDet.put(CONFIGURACION_COMISIONAL.CONFIG_COMISIONAL_DET_ID, e.getIdConfigComisionalDetalle());
                    jsonConfigComisionalDet.put(CONFIGURACION_COMISIONAL.ROL_COMISIONISTA, e.getRolComisionista().getIdRolComisionista());
                    jsonConfigComisionalDet.put(CONFIGURACION_COMISIONAL.ROL_COMISIONISTA_DESCRIPCION, e.getRolComisionista().getDescripcion());
                    jsonConfigComisionalDet.put(CONFIGURACION_COMISIONAL.BENEFICIARIO, e.getIdBeneficiario());
                    jsonConfigComisionalDet.put(CONFIGURACION_COMISIONAL.BENEFICIARIO_DESCRIPCION, e.getDescripcionBeneficiario());
                    jsonConfigComisionalDet.put(CONFIGURACION_COMISIONAL.BENEFICIARIO_ID, e.getIdBeneficiario());
                    jsonConfigComisionalDet.put(CONFIGURACION_COMISIONAL.VALOR, UtilesSet.formatearNumerosSeparadorMiles(e.getValor(), true));
                    jsonConfigComisionalDetArray.add(jsonConfigComisionalDet);
                }
                jsonRespuesta.put("DETALLE", jsonConfigComisionalDetArray);
                jsonRespuesta.put("TOTAL", lista.size());
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equals("GUARDAR_CONFIGURACION")) {
                this.configComFacade.agregarModificarConfiguracion(configClearingServicioInt, redInt, servicioInt, tipoClearingInt, desdeD, hastaD, recaudadorInt, sucursalInt, idRolComisionistaArray, idBeneficiarioArray, valorArray, descripcion);
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
    private ConfiguracionComisionalFacade configComFacade;
    @EJB
    private ConfigClearingServicioFacade configClearingServicioFacade;    

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
