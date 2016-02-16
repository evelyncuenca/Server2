/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.RecRetenidoFacade;
import py.com.konecta.redcobros.ejb.RecaudadorFacade;
import py.com.konecta.redcobros.ejb.RedRecaudadorFacade;
import py.com.konecta.redcobros.entities.*;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class RECAUDADOR extends HttpServlet {

    @EJB
    private RedRecaudadorFacade redRecaudadorFacade;
    public static String ID_RECAUDADOR = "ID_RECAUDADOR";
    public static String RED = "RED";
    public static String ENTIDAD = "ENTIDAD";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String CONTACTO = "CONTACTO";
    public static String CODIGO_RECAUDADOR = "CODIGO_RECAUDADOR";
    public static String CUENTA = "CUENTA";
    public static String FACTURADOR = "FACTURADOR";
    public static String SERVICIO = "SERVICIO";
    public static String A_ASIGNAR = "A_ASIGNAR";
    public static String COMENTARIO = "COMENTARIO";
    public static String TAMANHO_GRUPO = "TAMANHO_GRUPO";
    public static String RUC = "RUC";
    public static String NUMERO_ORDEN_TAM_RANGO = "NUMERO_ORDEN_TAM_RANGO";
    public static String HABILITADO = "HABILITADO";
    public static String CONCILIAR_CAJA = "CONCILIAR_CAJA";
    public static String RETENIDO = "RETENIDO";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            String idRecaudador = request.getParameter(RECAUDADOR.ID_RECAUDADOR);
            String idRed = request.getParameter(RECAUDADOR.RED);
            String idCuenta = request.getParameter(RECAUDADOR.CUENTA);
            String idEntidad = request.getParameter(RECAUDADOR.ENTIDAD);
            String descripcion = request.getParameter(RECAUDADOR.DESCRIPCION) != null && !request.getParameter(RECAUDADOR.DESCRIPCION).isEmpty() ? request.getParameter(RECAUDADOR.DESCRIPCION) : null;
            String contacto = request.getParameter(RECAUDADOR.CONTACTO);
            String servicios = request.getParameter(RECAUDADOR.A_ASIGNAR);
            String codigoRecaudador = request.getParameter(RECAUDADOR.CODIGO_RECAUDADOR);
            String numeroOrdenTamRango = request.getParameter(RECAUDADOR.NUMERO_ORDEN_TAM_RANGO);
            String habilitado = request.getParameter(RECAUDADOR.HABILITADO);
            String conciliarCaja = request.getParameter(RECAUDADOR.CONCILIAR_CAJA);
            String retenido = request.getParameter(RECAUDADOR.RETENIDO);
            String[] serviciosArray;
            if (servicios != null && !servicios.isEmpty()) {
                serviciosArray = servicios.substring(0, servicios.length() - 1).replaceAll(" ", "").split(",");
            } else {
                serviciosArray = new String[]{};
            }
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Recaudador recaudador = new Recaudador();
                recaudador.setRed(new Red(new Long(idRed)));
                recaudador.setEntidad(new Entidad(new Long(idEntidad)));
                recaudador.setDescripcion(descripcion);
                recaudador.setContacto(new Persona(new Long(contacto)));
                recaudador.setCodigoRecaudador(Integer.parseInt(codigoRecaudador));
                //recaudador.setCuenta(new Cuenta(new Long(idCuenta)));
                recaudador.setNumeroCuenta(idCuenta);
                recaudador.setNumeroOrdenTamRango(Integer.parseInt(numeroOrdenTamRango));
                recaudador.setHabilitado(habilitado);
                recaudador.setConciliarCaja(conciliarCaja);
                recaudador.setRetenido("N");
                if (habilitado.equalsIgnoreCase("S")) {
                    recaudador.setFechaAlta(new Date());
                }
                recaudadorFacade.save(recaudador);
                try {
                    Recaudador ejemplo = new Recaudador();
                    ejemplo.setEntidad(new Entidad(new Long(idEntidad)));
                    RedRecaudador entity = new RedRecaudador(new Long(idRed), recaudadorFacade.get(ejemplo).getIdRecaudador());
                    entity.setDescripcion(descripcion);
                    String redTicket = "RED DE PAGOS";

                    switch (Integer.parseInt(idRed)) {
                        case 1:
                        case 6:
                            redTicket = "PRACTIPAGO";
                            break;
                        case 2:
                        case 4:
                        case 5:
                            redTicket = "RED COOP";
                            break;
                        case 3:
                            redTicket = "MULTICOOP";
                            break;
                    }
                    entity.setRedTicket(redTicket);
                    redRecaudadorFacade.save(entity);
                } catch (Exception ex) {
                    Logger.getLogger(RECAUDADOR.class.getName()).log(Level.SEVERE, "No se puedo ingresar Red[{0}] Recaudador[{1}]", new Object[]{idRed, idRecaudador});
                }
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                recaudadorFacade.delete(new Long(idRecaudador));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                //Recaudador rec_ant = recaudadorFacade.get(new Long(idRecaudador));
                Recaudador recaudador = recaudadorFacade.get(new Long(idRecaudador));
                recaudador.setRed(new Red(new Long(idRed)));
                recaudador.setEntidad(new Entidad(new Long(idEntidad)));
                recaudador.setDescripcion(descripcion);
                recaudador.setContacto(new Persona(new Long(contacto)));
                recaudador.setCodigoRecaudador(Integer.parseInt(codigoRecaudador));
                //recaudador.setCuenta(new Cuenta(new Long(idCuenta)));
                recaudador.setNumeroCuenta(idCuenta);
                recaudador.setNumeroOrdenTamRango(Integer.parseInt(numeroOrdenTamRango));
                recaudador.setConciliarCaja(conciliarCaja);
                boolean insertRetenido = false;
                if (retenido.equals("S") && (recaudador.getRetenido() == null
                        || recaudador.getRetenido().isEmpty()
                        || recaudador.getRetenido().equals("N"))) {
                    recaudador.setRetenido(retenido);
                    insertRetenido = true;

                }
                if (!retenido.equalsIgnoreCase("S")) {
                    if (!recaudador.getHabilitado().equals("B")
                            && habilitado.equalsIgnoreCase("B")) {
                        recaudador.setFechaBaja(new Date());
                    } else if (recaudador.getFechaAlta() == null
                            && habilitado.equalsIgnoreCase("S")) {
                        recaudador.setFechaAlta(new Date());
                    }
                }
                JSONObject jsonRespuesta = new JSONObject();
                try {
                    if (retenido.equalsIgnoreCase("S") && insertRetenido) {
                        RecRetenido rr = new RecRetenido();
                        rr.setRecaudador(recaudador);
                        rr.setFechaInicio(new Date());
                        rr.setImporte(BigInteger.ZERO);
                        rr.setRetenido('S');
                        try {
                            rrf.save(rr);
                        } catch (Exception ex) {
                            Logger.getLogger(RECAUDADOR.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        jsonRespuesta.put("success", true);
                    } else if (retenido.equalsIgnoreCase("N")) {
                        request.getSession(false).setAttribute("NO_LISTAR_REC", "1");
//                        RecRetenido aux = new RecRetenido();
//                        aux.setRecaudador(recaudador);
//                        recaudador.setRetenido(retenido);
//                        List<RecRetenido> rr = rrf.list(aux, true);
//                        for (RecRetenido r : rr) {
//                            if (r.getRetenido().compareTo('S') == 0 && r.getFechaFinal() == null) {
//                                r.setFechaFinal(new Date());
//                                rrf.update(r);
//                            }
//
//                        }
                    }
                    recaudador.setHabilitado(habilitado);
                    recaudadorFacade.update(recaudador);
                } catch (Exception e) {
                    Logger.getLogger(RECAUDADOR.class.getName()).log(Level.SEVERE, null, e);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo actualizar");
                }

            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Recaudador recaudador = recaudadorFacade.get(new Long(idRecaudador));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(RECAUDADOR.ID_RECAUDADOR, recaudador.getIdRecaudador());
                jsonDetalle.put(RECAUDADOR.RED, recaudador.getRed().getIdRed());
                jsonDetalle.put(RECAUDADOR.ENTIDAD, recaudador.getEntidad().getIdEntidad());
                jsonDetalle.put(RECAUDADOR.DESCRIPCION, recaudador.getDescripcion());
                jsonDetalle.put(RECAUDADOR.CONTACTO, recaudador.getContacto().getIdPersona());
                jsonDetalle.put(RECAUDADOR.CODIGO_RECAUDADOR, recaudador.getCodigoRecaudador());
                jsonDetalle.put(RECAUDADOR.HABILITADO, recaudador.getHabilitado());
                jsonDetalle.put(RECAUDADOR.CONCILIAR_CAJA, recaudador.getConciliarCaja() != null && !recaudador.getConciliarCaja().isEmpty() ? recaudador.getConciliarCaja() : "-");
                //jsonDetalle.put(RECAUDADOR.CUENTA, recaudador.getCuenta().getIdCuenta());
                jsonDetalle.put(RECAUDADOR.CUENTA, recaudador.getNumeroCuenta());
                jsonDetalle.put(RECAUDADOR.NUMERO_ORDEN_TAM_RANGO, recaudador.getNumeroOrdenTamRango());
                jsonDetalle.put(RECAUDADOR.RETENIDO, recaudador.getRetenido() != null ? recaudador.getRetenido().toString() : "-");
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                String listarRecs = (String) request.getSession(false).getAttribute("NO_LISTAR_REC");

                JSONObject jsonRespuesta = new JSONObject();
                if (listarRecs == null || !"1".equals(listarRecs)) {
                    Recaudador recaudador = new Recaudador();
                    //recaudador.("S");
                    boolean like = false;
                    if (descripcion != null) {
                        like = true;
                        recaudador.setDescripcion(descripcion);
                    }
                    List<Recaudador> lista;
                    int total = 0;
                    String primerResultado = request.getParameter("start");
                    String cantResultados = request.getParameter("limit");
                    if (primerResultado != null && cantResultados != null) {
                        if (like) {
                            lista = recaudadorFacade.list(recaudador, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "codigoRecaudador", "ASC", true);
                        } else {
                            lista = recaudadorFacade.list(Integer.parseInt(primerResultado), Integer.parseInt(cantResultados));
                        }
                    } else {
                        lista = recaudadorFacade.list(recaudador, "codigoRecaudador", "ASC", false);
                    }

                    if (like) {
                        total = recaudadorFacade.total(recaudador, true);
                    } else {
                        total = recaudadorFacade.total();
                    }

                    JSONArray jsonRecaudadores = new JSONArray();

                    for (Recaudador e : lista) {

                        JSONObject jsonRecaudador = new JSONObject();
                        jsonRecaudador.put(RECAUDADOR.ID_RECAUDADOR, e.getIdRecaudador());
                        jsonRecaudador.put(RECAUDADOR.CONTACTO, e.getContacto().getApellidos() + ", " + e.getContacto().getNombres());
                        jsonRecaudador.put(RECAUDADOR.ENTIDAD, e.getEntidad().getNombre());
                        jsonRecaudador.put(RECAUDADOR.DESCRIPCION, e.getDescripcion());
                        jsonRecaudador.put(RECAUDADOR.CODIGO_RECAUDADOR, e.getCodigoRecaudador());
                        jsonRecaudador.put(RECAUDADOR.CUENTA, e.getNumeroCuenta());
                        jsonRecaudador.put(RECAUDADOR.RED, e.getRed().getDescripcion());
                        jsonRecaudador.put(RECAUDADOR.RUC, e.getEntidad().getRucCi());
                        jsonRecaudador.put(RECAUDADOR.NUMERO_ORDEN_TAM_RANGO, e.getNumeroOrdenTamRango());
                        jsonRecaudador.put(RECAUDADOR.CONCILIAR_CAJA, e.getConciliarCaja() != null && !e.getConciliarCaja().isEmpty() ? e.getConciliarCaja().compareTo("S") == 0 ? "SI" : "NO" : "-");
                        jsonRecaudador.put(RECAUDADOR.HABILITADO, e.getHabilitado().compareTo("S") == 0 ? "SI" : "NO");
                        jsonRecaudador.put(RECAUDADOR.RETENIDO, e.getRetenido() != null ? e.getRetenido().equalsIgnoreCase("S") ? "SI" : "NO" : "-");

                        jsonRecaudadores.add(jsonRecaudador);
                    }
                    jsonRespuesta.put("RECAUDADOR", jsonRecaudadores);
                    jsonRespuesta.put("TOTAL", total);
                    jsonRespuesta.put("success", true);

                } else {
                    request.getSession(false).setAttribute("NO_LISTAR_REC", "0");
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se permite el desbloqueo de comisiones desde esta interfaz");
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("AGREGAR_SERVICIOS_RECAUDADOR")) {
                Collection<Servicio> serviciosCollection = new ArrayList<Servicio>();
                for (String idServicio : serviciosArray) {
                    serviciosCollection.add(new Servicio(new Long(idServicio)));
                }
                Recaudador recaudador = recaudadorFacade.get(new Long(idRecaudador));
                recaudador.setServicioCollection(serviciosCollection);
                this.recaudadorFacade.update(recaudador);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR_SERVICIOS_RECAUDADOR")) {
                String flagAsignacion = request.getParameter("FLAG_ASIGNACION");
                JSONObject jsonRespuesta = new JSONObject();
                Collection<Servicio> serviciosCollection;
                if (flagAsignacion.equalsIgnoreCase("SI")) {
                    serviciosCollection = this.recaudadorFacade.obtenerServiciosHabilitados(new Long(idRecaudador));
                } else {
                    serviciosCollection = this.recaudadorFacade.obtenerServiciosNoHabilitados(new Long(idRecaudador));
                }
                JSONArray jsonServicios = new JSONArray();
                for (Servicio e : serviciosCollection) {
                    JSONObject jsonServicio = new JSONObject();
                    jsonServicio.put(RECAUDADOR.SERVICIO, e.getIdServicio());
                    jsonServicio.put(RECAUDADOR.FACTURADOR, e.getFacturador().getDescripcion());
                    jsonServicio.put(RECAUDADOR.COMENTARIO, e.getComentario());
                    jsonServicio.put(RECAUDADOR.DESCRIPCION, e.getDescripcion());
                    jsonServicio.put(RECAUDADOR.TAMANHO_GRUPO, e.getTamanhoGrupo());
                    jsonServicios.add(jsonServicio);
                }
                
                jsonRespuesta.put("SERVICIO", jsonServicios);
                jsonRespuesta.put("TOTAL", serviciosCollection.size());
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
                
            } else {
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
                out.println(jsonRespuesta.toString());
            }
        } catch (Exception exc) {
            Logger.getLogger(RECAUDADOR.class.getName()).log(Level.SEVERE, "Recaudador", exc);
            
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            //jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }

//        System.out.print("");
    }
    @EJB
    private RecaudadorFacade recaudadorFacade;
    @EJB
    private RecRetenidoFacade rrf;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
