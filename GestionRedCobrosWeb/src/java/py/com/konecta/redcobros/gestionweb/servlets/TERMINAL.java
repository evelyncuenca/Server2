/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.TerminalFacade;

import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.entities.Terminal;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class TERMINAL extends HttpServlet {

    public static String ID_TERMINAL = "ID_TERMINAL";
    public static String SUCURSAL = "SUCURSAL";
    // public static String TIPO_TERMINAL="TIPO_TERMINAL";
    public static String HASH = "HASH";
    public static String CAJA = "CAJA";
    public static String RECAUDADOR = "RECAUDADOR";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String URL_IMPRESORA = "URL_IMPRESORA";
    public static String PROXIMO_NRO_GESTION = "PROXIMO_NRO_GESTION";
    public static String CODIGO_TERMINAL = "CODIGO_TERMINAL";
    public static String CODIGO_CAJA_SET = "CODIGO_CAJA_SET";
    private static final Logger logger = Logger.getLogger(TERMINAL.class.getName());

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
            String idTerminal = request.getParameter(TERMINAL.ID_TERMINAL);
            String idSucursal = request.getParameter(TERMINAL.SUCURSAL);
            String descripcion = request.getParameter(TERMINAL.DESCRIPCION);
            // String idTipoTerminal=request.getParameter(TERMINAL.TIPO_TERMINAL);
            String hash = request.getParameter(TERMINAL.HASH);
            //String caja = request.getParameter(TERMINAL.CAJA);
            String urlImpresora = request.getParameter(TERMINAL.URL_IMPRESORA);
            //  String proximoNroGestion = request.getParameter(TERMINAL.PROXIMO_NRO_GESTION);
            String codigoTerminal = request.getParameter(TERMINAL.CODIGO_TERMINAL);
            Integer codigoCajaSet = request.getParameter(TERMINAL.CODIGO_CAJA_SET) != null && !request.getParameter(TERMINAL.CODIGO_CAJA_SET).isEmpty() ? new Integer(request.getParameter(TERMINAL.CODIGO_CAJA_SET)) : null;

            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Terminal terminal = new Terminal();
                terminal.setSucursal(new Sucursal(new Long(idSucursal)));

                terminal.setCodigoHash(py.com.konecta.redcobros.gestionweb.utiles.Utiles.getSha1(hash));

                terminal.setDescripcion(descripcion);
                terminal.setUrlImpresora(urlImpresora);
                //terminal.setProximoNroGestion(new Long(proximoNroGestion));
                terminal.setProximoNroGestion(1);
                terminal.setCodigoTerminal(Integer.parseInt(codigoTerminal));
                terminal.setCodigoCajaSet(codigoCajaSet != null ? codigoCajaSet : null);
                terminalFacade.save(terminal);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                terminalFacade.delete(new Long(idTerminal));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                logger.info("MODIFICAR");
                Terminal terminal = this.terminalFacade.get(new Long(idTerminal));
                terminal.setIdTerminal(new Long(idTerminal));
                terminal.setSucursal(new Sucursal(new Long(idSucursal)));
                logger.log(Level.INFO, "Terminal: {0}", terminal.toString());
                if ((hash != null && !hash.isEmpty())) {
                    terminal.setCodigoHash(Utiles.getSha1(hash));
                }

                terminal.setDescripcion(descripcion);
                terminal.setUrlImpresora(urlImpresora);
                terminal.setCodigoCajaSet(codigoCajaSet != null ? codigoCajaSet : null);

                terminal.setCodigoTerminal(Integer.parseInt(codigoTerminal));
                logger.log(Level.INFO, "Terminal Update: {0}", terminal.toString());
                terminalFacade.update(terminal);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Terminal terminal = terminalFacade.get(new Long(idTerminal));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(TERMINAL.ID_TERMINAL, terminal.getIdTerminal());
                jsonDetalle.put(TERMINAL.SUCURSAL, terminal.getSucursal().getIdSucursal());
                jsonDetalle.put(TERMINAL.DESCRIPCION, terminal.getDescripcion());
                jsonDetalle.put(TERMINAL.CODIGO_CAJA_SET, terminal.getCodigoCajaSet() != null ? terminal.getCodigoCajaSet() : "");
                jsonDetalle.put(TERMINAL.URL_IMPRESORA, terminal.getUrlImpresora());
                jsonDetalle.put(TERMINAL.PROXIMO_NRO_GESTION, terminal.getProximoNroGestion());
                jsonDetalle.put(TERMINAL.CODIGO_TERMINAL, terminal.getCodigoTerminal());
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Terminal terminal = new Terminal();
                List<Terminal> lista;
                int total;
                String primerResultado = request.getParameter("start");
                if (idTerminal != null && !idTerminal.isEmpty()) {
                    if (idTerminal.matches("[0-9]+")) {
                        terminal.setCodigoTerminal(new Integer(idTerminal));
                    } else {
                        terminal.setDescripcion(idTerminal);
                    }
                }
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = terminalFacade.list(terminal, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "codigoTerminal", "asc", true);
                } else {
                    lista = terminalFacade.list(terminal, "descripcion", "asc", true);
                }

                total = terminalFacade.total(terminal, true);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonTerminales = new JSONArray();
                for (Terminal e : lista) {
                    JSONObject jsonTerminal = new JSONObject();
                    jsonTerminal.put(TERMINAL.ID_TERMINAL, e.getIdTerminal());
                    jsonTerminal.put(TERMINAL.RECAUDADOR, e.getSucursal().getRecaudador().getDescripcion());
                    jsonTerminal.put(TERMINAL.SUCURSAL, e.getSucursal().getDescripcion());
                    jsonTerminal.put(TERMINAL.DESCRIPCION, "" + e.getDescripcion());
                    jsonTerminal.put(TERMINAL.URL_IMPRESORA, e.getUrlImpresora());
                    jsonTerminal.put(TERMINAL.PROXIMO_NRO_GESTION, e.getProximoNroGestion());
                    jsonTerminal.put(TERMINAL.CODIGO_TERMINAL, e.getCodigoTerminal());
                    jsonTerminal.put(TERMINAL.CODIGO_CAJA_SET, e.getCodigoCajaSet() != null ? e.getCodigoCajaSet() : "");
                    jsonTerminales.add(jsonTerminal);
                }
                jsonRespuesta.put("TERMINAL", jsonTerminales);
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
    private TerminalFacade terminalFacade;

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
