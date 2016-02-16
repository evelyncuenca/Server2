/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.documenta.ws.set.contrib.ContribSETWS;
import py.com.documenta.ws.set.contrib.ContribSETWS_Service;
import py.com.documenta.ws.set.contrib.Contribuyente;
import py.com.konecta.redcobros.ejb.ContribuyentesFacade;
import py.com.konecta.redcobros.ejb.ParametroSistemaFacade;
import py.com.konecta.redcobros.entities.Contribuyentes;
import py.com.konecta.redcobros.entities.ParametroSistema;

/**
 *
 * @author konecta
 */
public class CONTRIBUYENTE extends HttpServlet {

    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    public static String ID_CONTRIBUYENTE = "ID_CONTRIBUYENTE";
    public static String RUC_NUEVO = "RUC_NUEVO";
    public static String MES_CIERRE = "MES_CIERRE";
    public static String RUC_ANTERIOR = "RUC_ANTERIOR";
    public static String DIGITO_VERIFICADOR = "DIGITO_VERIFICADOR";
    public static String TIPO_CONTRIBUYENTE = "TIPO_CONTRIBUYENTE";
    public static String NOMBRE_CONTRIBUYENTE = "NOMBRE_CONTRIBUYENTE";
    public static String MODALIDAD_CONTRIBUYENTE = "MODALIDAD_CONTRIBUYENTE";
    public static String CONSULTA = "CONSULTA";
    public static String ESTADO = "ESTADO";
    private static ContribSETWS_Service service = null;
    private static final ReentrantLock lock = new ReentrantLock();

    public static ContribSETWS getWSManager(String url, String uri, String localPart,
            int connTo, int readTo) {
        ContribSETWS pexSoap;

        URL wsdlURL = ContribSETWS_Service.class.getClassLoader().getResource("schema/ContribSETWS.wsdl");
        try {
            if (service == null) {
                try {
                    lock.lock();
                    if (service == null) {
                        service = new ContribSETWS_Service(wsdlURL, new QName(uri, localPart));
                    }
                } catch (Exception ex) {
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception ex) {
        }

        pexSoap = service.getContribSETWSPort();
        BindingProvider provider = (BindingProvider) pexSoap;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        provider.getRequestContext().put("com.sun.xml.ws.connect.timeout", connTo * 1000);
        provider.getRequestContext().put("com.sun.xml.ws.request.timeout", readTo * 1000);

        return pexSoap;
    }

    private Contribuyente mergeContrib(ContribSETWS service, Contribuyente contribuyente, boolean modo) {
        Contribuyente respuesta = null;
        try {
            respuesta = service.mergeContrib(contribuyente, modo);
        } catch (Exception ex) {
            Logger.getLogger(CONTRIBUYENTE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String opcion = request.getParameter("op");
            String idContribuyente = request.getParameter(CONTRIBUYENTE.ID_CONTRIBUYENTE);
            String rucNuevo = request.getParameter(CONTRIBUYENTE.RUC_NUEVO);
            String digitoVerificador = request.getParameter(CONTRIBUYENTE.DIGITO_VERIFICADOR);
            String rucAnterior = request.getParameter(CONTRIBUYENTE.RUC_ANTERIOR);
            String modalidadContribuyente = request.getParameter(CONTRIBUYENTE.MODALIDAD_CONTRIBUYENTE);
            String nombreContribuyente = request.getParameter(CONTRIBUYENTE.NOMBRE_CONTRIBUYENTE) != null && !request.getParameter(CONTRIBUYENTE.NOMBRE_CONTRIBUYENTE).isEmpty() ? request.getParameter(CONTRIBUYENTE.NOMBRE_CONTRIBUYENTE).toUpperCase() : null;
            String tipoContribuyente = request.getParameter(CONTRIBUYENTE.TIPO_CONTRIBUYENTE);
            String mesCierre = request.getParameter(CONTRIBUYENTE.MES_CIERRE);
            String estado = request.getParameter(CONTRIBUYENTE.ESTADO) != null && !request.getParameter(CONTRIBUYENTE.ESTADO).isEmpty() ? request.getParameter(CONTRIBUYENTE.ESTADO).toUpperCase() : null;
            String critConsulta = request.getParameter(CONTRIBUYENTE.CONSULTA);
            String primerResultado = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? request.getParameter("start") : null;
            String cantResultados = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? request.getParameter("limit") : null;

            ParametroSistema paramFile = new ParametroSistema();
            paramFile.setNombreParametro("urlContribSet");

            String url = parametroSistemaFacade.get(paramFile).getValor();
            ContribSETWS serviceSet = getWSManager(url, "http://contrib.ws.daemon.documenta.com.py/", "ContribSETWS", 10, 80);

            if (opcion.equalsIgnoreCase("CONSULTAR_CONTRIBUYENTE")) {
                JSONObject jsonRespuesta = new JSONObject();

                Contribuyente contribWS = null;

                try {
                    contribWS = serviceSet.consulta(critConsulta);

                    if (contribWS == null) {
                        Logger.getLogger(CONTRIBUYENTE.class.getName()).log(Level.SEVERE, String.format("Contribuyente [%s] no encontrado", critConsulta));
                        jsonRespuesta.put("success", false);
                        jsonRespuesta.put("motivo", String.format("Contribuyente con Ruc %s no encontrado", critConsulta));
                        out.println(jsonRespuesta.toString());
                        return;
                    }
                    Contribuyentes ejemplo = new Contribuyentes();
                    ejemplo.setRucNuevo(critConsulta);

                    List<Contribuyentes> lcontribuyentes = contribuyentesFacade.list(ejemplo, "rucNuevo", "asc", true);
                    Contribuyentes e = (lcontribuyentes != null && !lcontribuyentes.isEmpty()) ? lcontribuyentes.get(0) : null;

                    if (e == null) {
                        e = new Contribuyentes(new Long(contribWS.getId()));
                    }
                    e.setDigitoVerificador(contribWS.getDigitoVerificador());
//                        e.setEstado(contribWS.get);
                    contribuyentesFacade.merge(e);
                    
                    JSONArray jsonContribuyentes = new JSONArray();

//                    for (Contribuyentes e : lcontribuyentes) {
                    JSONObject jsonContribuyente = new JSONObject();
                    jsonContribuyente.put(CONTRIBUYENTE.ID_CONTRIBUYENTE, e.getIdContribuyente());
                    jsonContribuyente.put(CONTRIBUYENTE.RUC_NUEVO, e.getRucNuevo());
                    jsonContribuyente.put(CONTRIBUYENTE.DIGITO_VERIFICADOR, e.getDigitoVerificador());
                    jsonContribuyente.put(CONTRIBUYENTE.RUC_ANTERIOR, e.getRucAnterior() != null ? e.getRucAnterior() : "");
                    jsonContribuyente.put(CONTRIBUYENTE.MODALIDAD_CONTRIBUYENTE, (e.getModalidadContribuyente()) == 1 ? "Grande" : (e.getModalidadContribuyente()) == 2 ? "Pequeño" : "Mediano");
                    jsonContribuyente.put(CONTRIBUYENTE.NOMBRE_CONTRIBUYENTE, e.getNombreContribuyente());
                    jsonContribuyente.put(CONTRIBUYENTE.TIPO_CONTRIBUYENTE, (e.getTipoContribuyente()) == 1 ? "Fisico" : "Juridico");
                    jsonContribuyente.put(CONTRIBUYENTE.MES_CIERRE, e.getMesCierre());
                    jsonContribuyente.put(CONTRIBUYENTE.ESTADO, e.getEstado() != null ? e.getEstado() : "");
                    jsonContribuyentes.add(jsonContribuyente);
//                    }

                    jsonRespuesta.put("CONTRIBUYENTE", jsonContribuyentes);
                    jsonRespuesta.put("TOTAL", contribuyentesFacade.total(ejemplo, true));
                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());

                } catch (Exception ex) {
                    Logger.getLogger(CONTRIBUYENTE.class.getName()).log(Level.SEVERE, String.format("Error al consultar por existencia del contribuyente [%s]", critConsulta), ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", String.format("Contribuyente con Ruc %s no encontrado", critConsulta));
                    out.println(jsonRespuesta.toString());
//                    return;
                }

//                Contribuyentes ejemplo = new Contribuyentes();
//
//                if (critConsulta != null && critConsulta.matches("[0-9]+")) {
//                    ejemplo.setRucNuevo(critConsulta);
//                } else if (critConsulta != null && !critConsulta.isEmpty()) {
//                    ejemplo.setNombreContribuyente(critConsulta);
//                }
//                List<Contribuyentes> lcontribuyentes;
//                if (primerResultado != null && cantResultados != null) {
//                    lcontribuyentes
//                            = contribuyentesFacade.list(ejemplo,
//                                    Integer.parseInt(primerResultado),
//                                    Integer.parseInt(cantResultados), "rucNuevo", "asc", true);
//                } else {
//                    lcontribuyentes
//                            = contribuyentesFacade.list(ejemplo, "rucNuevo", "asc", true);
//                }
            } else if (opcion.equalsIgnoreCase("AGREGAR")) {

                JSONObject jsonRespuesta = new JSONObject();
//                Contribuyentes newContrib = new Contribuyentes();
//                Contribuyente contrib = serviceSet.consulta(rucNuevo);
//
//                String fechaHoraAlta = Long.toString(new Date().getTime());
//                String usuarioTerminal = ((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")).getIdUsuarioTerminal().toString();
//
//                if (contrib != null) {//SI EXISTE EN PRODUCCION
//                    newContrib.setIdContribuyente(Long.valueOf(contrib.getId()));
//                    newContrib.setNombreContribuyente(contrib.getRazonSocial());
//                    newContrib.setDigitoVerificador(contrib.getDigitoVerificador());
//                    newContrib.setRucNuevo(contrib.getRucNuevo());
//                    newContrib.setRucAnterior(contrib.getRucViejo());
//                    newContrib.setMesCierre(Integer.valueOf(contrib.getMesCierre()));
//                    newContrib.setModalidadContribuyente(Integer.valueOf(contrib.getModalidad()));
//                    newContrib.setTipoContribuyente(Integer.valueOf(contrib.getTipo()));
//                    //newContrib.setEstado(contrib.getEstado());
//                    if (contrib.getUsuarioTerminal() != null) {
//                        newContrib.setUsuarioTerminal(Long.valueOf(contrib.getUsuarioTerminal()));
//                    }
//                    if (contrib.getFechaAlta() != null) {
//                        newContrib.setFechaAlta(new Date(new Long(contrib.getFechaAlta())));
//                    }
//                } else {
//                    newContrib.setNombreContribuyente(nombreContribuyente);
//                    newContrib.setDigitoVerificador(digitoVerificador);
//                    newContrib.setRucNuevo(rucNuevo);
//                    newContrib.setRucAnterior(rucAnterior);
//                    newContrib.setMesCierre(Integer.valueOf(mesCierre));
//                    newContrib.setModalidadContribuyente(Integer.valueOf(modalidadContribuyente));
//                    newContrib.setTipoContribuyente(Integer.valueOf(tipoContribuyente));
//                    newContrib.setUsuarioTerminal(Long.parseLong(usuarioTerminal));
//                    newContrib.setFechaAlta(new Date(Long.valueOf(fechaHoraAlta)));
//                    newContrib.setEstado(estado);
//                }
//
//                if (contrib == null) {
//                    contrib = new Contribuyente();
//                    contrib.setDigitoVerificador(digitoVerificador);
//                    contrib.setMesCierre(mesCierre);
//                    contrib.setModalidad(modalidadContribuyente);
//                    contrib.setRazonSocial(nombreContribuyente);
//                    contrib.setRucNuevo(rucNuevo);
//                    contrib.setRucViejo(rucAnterior);
//                    contrib.setTipo(tipoContribuyente);
//                    contrib.setFechaAlta(fechaHoraAlta);
//                    contrib.setUsuarioTerminal(usuarioTerminal);
//                    //contrib.setEstado(estado);
//                    contrib = mergeContrib(serviceSet, contrib, true);
//                    newContrib.setIdContribuyente(Long.valueOf(contrib.getId()));
//                }
//                contribuyentesFacade.merge(newContrib);
//                jsonRespuesta.put("success", true);
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "El alta de nuevos contribuyentes depende de la SET");
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {//FALTA AGREGAR PERMISO A LA BBDD
                JSONObject jsonRespuesta = new JSONObject();
//                Contribuyentes contrib = this.contribuyentesFacade.get(new Long(idContribuyente));
//
//                contrib.setIdContribuyente(new Long(idContribuyente));
//                contrib.setNombreContribuyente(nombreContribuyente);
//                contrib.setDigitoVerificador(digitoVerificador);
//                contrib.setRucNuevo(rucNuevo);
//                contrib.setRucAnterior(rucAnterior);
//                contrib.setMesCierre(Integer.valueOf(mesCierre));
//                contrib.setModalidadContribuyente(Integer.valueOf(modalidadContribuyente));
//                contrib.setTipoContribuyente(Integer.valueOf(tipoContribuyente));
//                contrib.setEstado(estado);
//
//                Contribuyente con = new Contribuyente();
//                con.setDigitoVerificador(digitoVerificador);
//                con.setId(idContribuyente);
//                con.setMesCierre(mesCierre);
//                con.setModalidad(modalidadContribuyente);
//                con.setRazonSocial(nombreContribuyente);
//                con.setRucNuevo(rucNuevo);
//                con.setRucViejo(rucAnterior);
//                con.setTipo(tipoContribuyente);
//                //con.setEstado(estado);                
//
//                contribuyentesFacade.update(contrib);
//                mergeContrib(serviceSet, con, false);

//                jsonRespuesta.put("success", true);
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "La actualizacion de contribuyentes depende de la SET");
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {//cambiar el LISTAR por DETALLE
                Contribuyentes contrib = this.contribuyentesFacade.get(new Long(idContribuyente));

                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(CONTRIBUYENTE.ID_CONTRIBUYENTE, contrib.getIdContribuyente());
                jsonDetalle.put(CONTRIBUYENTE.RUC_NUEVO, contrib.getRucNuevo());
                jsonDetalle.put(CONTRIBUYENTE.RUC_ANTERIOR, contrib.getRucAnterior());
                jsonDetalle.put(CONTRIBUYENTE.DIGITO_VERIFICADOR, contrib.getDigitoVerificador());
                jsonDetalle.put(CONTRIBUYENTE.MES_CIERRE, contrib.getMesCierre());
                jsonDetalle.put(CONTRIBUYENTE.MODALIDAD_CONTRIBUYENTE, contrib.getModalidadContribuyente());
                jsonDetalle.put(CONTRIBUYENTE.NOMBRE_CONTRIBUYENTE, contrib.getNombreContribuyente());
                jsonDetalle.put(CONTRIBUYENTE.TIPO_CONTRIBUYENTE, contrib.getTipoContribuyente());
                jsonDetalle.put(CONTRIBUYENTE.ESTADO, contrib.getEstado() != null ? contrib.getEstado() : "");
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());

            } else {
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
                out.println(jsonRespuesta.toString());
            }
        } catch (Exception exc) {
            Logger.getLogger(CONTRIBUYENTE.class.getName()).log(Level.SEVERE, null, exc);
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", exc.getMessage());
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private ContribuyentesFacade contribuyentesFacade;

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
