/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.RedFacade;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.EntidadFinanciera;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.HabilitacionFactRed;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class RED extends HttpServlet {

    public static String ID_RED = "ID_RED";
    public static String ALIAS_CUENTA = "ALIAS";
    public static String ID_CUENTA = "ID_CUENTA";
     public static String CUENTA = "CUENTA";
    public static String CUENTA_PROCESADOR = "CUENTA_PROCESADOR";
    public static String CUENTA_RED = "CUENTA_RED";
    public static String ENTIDAD = "ENTIDAD";
     public static String ENTIDAD_FINANCIERA = "ENTIDAD_FINANCIERA";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String FACTURADOR = "FACTURADOR";
    public static String A_ASIGNAR = "A_ASIGNAR";
    public static String CONTACTO = "CONTACTO";
    public static String COMENTARIO = "COMENTARIO";
    public static String TAMANHO_GESTION = "TAMANHO_GESTION";
    public static String COD_ERA = "COD_ERA";
    public static String CUENTA_BCP_IMPUESTOS = "CUENTA_BCP_IMPUESTOS";
    public static String CUENTA_BCP_OTROS_CONCEPTOS = "CUENTA_BCP_OTROS_CONCEPTOS";
    public static String CUENTA_BCP_PENALIDADES = "CUENTA_BCP_PENALIDADES";
    public static String BANCO_CLEARING = "BANCO_CLEARING";
   
    public static String NUMERO_CUENTA = "NUMERO_CUENTA";
    public static String NUMERO_CUENTA_PROCESADOR = "NUMERO_CUENTA_PROCESADOR";

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
            String idRed = request.getParameter(RED.ID_RED);
            String idEntidad = request.getParameter(RED.ENTIDAD);
            String descripcion = request.getParameter(RED.DESCRIPCION);
            String facturadores = request.getParameter(RED.A_ASIGNAR);
            String entidades = request.getParameter(RED.A_ASIGNAR);
            String cuentas = request.getParameter(RED.ID_CUENTA);
            String codEra = request.getParameter(RED.COD_ERA);
            String cuentaBcpImpuestos = request.getParameter(RED.CUENTA_BCP_IMPUESTOS);
            String cuentaBcpOtrosConceptos = request.getParameter(RED.CUENTA_BCP_OTROS_CONCEPTOS);
            String cuentaBcpPenalidades = request.getParameter(RED.CUENTA_BCP_PENALIDADES);

            String numeroCuenta = request.getParameter(RED.NUMERO_CUENTA);
            String numeroCuentaProcesador = request.getParameter(RED.NUMERO_CUENTA_PROCESADOR);
            String idEntidadFinanciera = request.getParameter(RED.ENTIDAD_FINANCIERA);

           

            String[] facturadoresArray;
            String[] cuentasArray;



            String[] entidadesArray;
            if (entidades != null && !entidades.isEmpty()) {
                entidadesArray = entidades.substring(0, entidades.length() - 1).replaceAll(" ", "").split(",");
            } else {
                entidadesArray = new String[]{};
            }

            if (facturadores != null && !facturadores.isEmpty()) {
                facturadoresArray = facturadores.substring(0, facturadores.length() - 1).replaceAll(" ", "").split(",");
            } else {
                facturadoresArray = new String[]{};
            }
            if (cuentas != null && !cuentas.isEmpty()) {
                cuentasArray = cuentas.replaceAll(" ", "").split(",");
            } else {
                cuentasArray = new String[]{};
            }
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Red red = new Red();
                red.setEntidad(new Entidad(new Long(idEntidad)));
                red.setDescripcion(descripcion);
                red.setCodEra(new Integer(codEra));
                red.setCuentaBcpImpuestos(cuentaBcpImpuestos);
                red.setCuentaBcpOtrosConceptos(cuentaBcpOtrosConceptos);
                red.setCuentaBcpPenalidades(cuentaBcpPenalidades);

                red.setBancoClearing(new EntidadFinanciera(new Long(idEntidadFinanciera)));
                red.setNumeroCuenta(numeroCuenta);
                red.setNumeroCuentaProcesador(numeroCuentaProcesador);
                red.setNumeroOrdenProximo(1L);
                redFacade.save(red);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                redFacade.delete(new Long(idRed));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Red red = this.redFacade.get(new Long(idRed));
                red.setEntidad(new Entidad(new Long(idEntidad)));
                red.setCodEra(Integer.parseInt(codEra));
                red.setCuentaBcpImpuestos(cuentaBcpImpuestos);
                red.setCuentaBcpOtrosConceptos(cuentaBcpOtrosConceptos);
                red.setCuentaBcpPenalidades(cuentaBcpPenalidades);
                red.setDescripcion(descripcion);

                red.setBancoClearing(new EntidadFinanciera(new Long(idEntidadFinanciera)));
                red.setNumeroCuenta(numeroCuenta);
                red.setNumeroCuentaProcesador(numeroCuentaProcesador);
                redFacade.update(red);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Red red = this.redFacade.get(new Long(idRed));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(RED.ID_RED, red.getIdRed());
                jsonDetalle.put(RED.DESCRIPCION, red.getDescripcion());
                jsonDetalle.put(RED.CUENTA_BCP_IMPUESTOS, red.getCuentaBcpImpuestos());
                jsonDetalle.put(RED.CUENTA_BCP_OTROS_CONCEPTOS, red.getCuentaBcpOtrosConceptos());
                jsonDetalle.put(RED.CUENTA_BCP_PENALIDADES, red.getCuentaBcpPenalidades());
                jsonDetalle.put(RED.COD_ERA, red.getCodEra());
                jsonDetalle.put(RED.CUENTA_RED, red.getNumeroCuenta());
                jsonDetalle.put(RED.CUENTA_PROCESADOR, red.getNumeroCuentaProcesador());
                jsonDetalle.put(RED.ENTIDAD, red.getEntidad().getIdEntidad());
                jsonDetalle.put(RED.NUMERO_CUENTA, red.getNumeroCuenta());
                jsonDetalle.put(RED.NUMERO_CUENTA_PROCESADOR, red.getNumeroCuentaProcesador());
                jsonDetalle.put(RED.ENTIDAD_FINANCIERA, red.getBancoClearing().getIdEntidadFinanciera());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            /*} else if (opcion.equalsIgnoreCase("AGREGAR_RECAUDADORES")) {
            redFacade.agregarRecaudadores(Integer.parseInt(idRed), recaudadoresArray,cuentasArray);
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR_RECAUDADORES")) {
            String flagAsignacion = request.getParameter("FLAG_ASIGNACION");
            JSONObject jsonRespuesta = new JSONObject();
            Collection<Recaudador> recaudadoresCollection;
            Collection<HabilitacionRecaudadorRed> habilitacionesCollection;
            if (flagAsignacion.equalsIgnoreCase("SI")) {
            habilitacionesCollection = this.redFacade.obtenerHabilitacionesRecaudadorRed(Integer.parseInt(idRed));
            JSONArray jsonRecaudadores = new JSONArray();
            for (HabilitacionRecaudadorRed e : habilitacionesCollection) {
            JSONObject jsonRecaudador = new JSONObject();
            jsonRecaudador.put(RED.RECAUDADOR, e.getRecaudador().getIdRecaudador());
            jsonRecaudador.put(RED.ENTIDAD, e.getRecaudador().getEntidad().getNombre());
            jsonRecaudador.put(RED.DESCRIPCION, e.getRecaudador().getDescripcion());
            jsonRecaudador.put(RED.CONTACTO, e.getRecaudador().getContacto().getApellidos() + ", " + e.getRecaudador().getContacto().getNombres());
            jsonRecaudador.put(RED.ALIAS_CUENTA, e.getCuenta().getAliasCuenta());
            jsonRecaudador.put(RED.ID_CUENTA, e.getCuenta().getIdCuenta());
            jsonRecaudadores.add(jsonRecaudador);
            }
            jsonRespuesta.put("RECAUDADOR", jsonRecaudadores);
            jsonRespuesta.put("TOTAL", habilitacionesCollection.size());
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
            } else {
            recaudadoresCollection = this.redFacade.obtenerRecaudadoresNoHabilitados(Integer.parseInt(idRed));
            JSONArray jsonRecaudadores = new JSONArray();
            for (Recaudador e : recaudadoresCollection) {
            JSONObject jsonRecaudador = new JSONObject();
            jsonRecaudador.put(RED.RECAUDADOR, e.getIdRecaudador());
            jsonRecaudador.put(RED.ENTIDAD, e.getEntidad().getNombre());
            jsonRecaudador.put(RED.DESCRIPCION, e.getDescripcion());
            jsonRecaudador.put(RED.CONTACTO, e.getContacto().getApellidos() + ", " + e.getContacto().getNombres());
            jsonRecaudador.put(RED.ALIAS_CUENTA, "");

            jsonRecaudadores.add(jsonRecaudador);
            }
            jsonRespuesta.put("RECAUDADOR", jsonRecaudadores);
            jsonRespuesta.put("TOTAL", recaudadoresCollection.size());
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
            }*/
            } else if (opcion.equalsIgnoreCase("AGREGAR_FACTURADORES")) {
                redFacade.agregarFacturadores(new Long(idRed), facturadoresArray, cuentasArray);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("AGREGAR_ENTIDAD_POLITICA")) {
                redFacade.agregarEntidadesPoliticas(new Long(idRed), entidadesArray,cuentasArray);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR_FACTURADORES")) {
                String flagAsignacion = request.getParameter("FLAG_ASIGNACION");
                JSONObject jsonRespuesta = new JSONObject();
                Collection<Facturador> facturadoresCollection;
                Collection<HabilitacionFactRed> habilitacionesCollection;
                if (flagAsignacion.equalsIgnoreCase("SI")) {
                    habilitacionesCollection = this.redFacade.obtenerHabilitacionesFactRed(new Long(idRed));
                    JSONArray jsonFacturadores = new JSONArray();
                    for (HabilitacionFactRed e : habilitacionesCollection) {
                        JSONObject jsonFacturador = new JSONObject();
                        jsonFacturador.put(RED.FACTURADOR, e.getFacturador().getIdFacturador());
                        jsonFacturador.put(RED.ENTIDAD, e.getFacturador().getEntidad().getNombre());
                        jsonFacturador.put(RED.DESCRIPCION, e.getFacturador().getDescripcion());
                        jsonFacturador.put(RED.CONTACTO, ((e.getFacturador().getContacto() != null) ? e.getFacturador().getContacto().getApellidos() + ", " + e.getFacturador().getContacto().getNombres() : ""));
                        //jsonFacturador.put(RED.ALIAS_CUENTA, e.getCuenta().getAliasCuenta());
                        //jsonFacturador.put(RED.ID_CUENTA, e.getCuenta().getIdCuenta());
                        jsonFacturador.put(RED.CUENTA, e.getNumeroCuenta());
                        jsonFacturadores.add(jsonFacturador);
                    }
                    jsonRespuesta.put("FACTURADOR", jsonFacturadores);
                    jsonRespuesta.put("TOTAL", habilitacionesCollection.size());
                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());
                } else {
                    facturadoresCollection = this.redFacade.obtenerFacturadoresNoHabilitados(new Long(idRed));
                    JSONArray jsonFacturadores = new JSONArray();
                    for (Facturador e : facturadoresCollection) {
                        JSONObject jsonFacturador = new JSONObject();
                        jsonFacturador.put(RED.FACTURADOR, e.getIdFacturador());
                        jsonFacturador.put(RED.ENTIDAD, e.getEntidad().getNombre());
                        jsonFacturador.put(RED.DESCRIPCION, e.getDescripcion());
                        jsonFacturador.put(RED.CONTACTO, ((e.getContacto() != null) ? e.getContacto().getApellidos() + ", " + e.getContacto().getNombres() : ""));
                        jsonFacturador.put(RED.ALIAS_CUENTA, "");
                        jsonFacturadores.add(jsonFacturador);
                    }
                    jsonRespuesta.put("FACTURADOR", jsonFacturadores);
                    jsonRespuesta.put("TOTAL", facturadoresCollection.size());
                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());
                }
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Red red = new Red();
                red.setDescripcion(descripcion);
                List<Red> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = redFacade.list(red, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "codEra", "Asc");
                } else {
                    lista = redFacade.list(red, "codEra", "Asc");
                }
                total = redFacade.total(red);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonRedes = new JSONArray();
                for (Red e : lista) {
                    JSONObject jsonRed = new JSONObject();
                    jsonRed.put(RED.ID_RED, e.getIdRed());
                    jsonRed.put(RED.ENTIDAD, e.getEntidad().getNombre());
                    jsonRed.put(RED.COD_ERA, e.getCodEra());
                    jsonRed.put(RED.DESCRIPCION, e.getDescripcion());
                    jsonRed.put(RED.CUENTA_BCP_IMPUESTOS, e.getCuentaBcpImpuestos());
                    jsonRed.put(RED.CUENTA_BCP_OTROS_CONCEPTOS, e.getCuentaBcpOtrosConceptos());
                    jsonRed.put(RED.CUENTA_BCP_PENALIDADES, e.getCuentaBcpPenalidades());
                    jsonRed.put(RED.BANCO_CLEARING, e.getBancoClearing()!=null?e.getBancoClearing().getDescripcion():"-");
                    jsonRed.put(RED.NUMERO_CUENTA, e.getNumeroCuenta());
                    jsonRed.put(RED.NUMERO_CUENTA_PROCESADOR, e.getNumeroCuentaProcesador());
                    jsonRedes.add(jsonRed);
                }
                jsonRespuesta.put("RED", jsonRedes);
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
            exc.printStackTrace();
        } finally {
            //out.close();
        }
    }
    @EJB
    private RedFacade redFacade;
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
