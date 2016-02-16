/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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

import py.com.konecta.redcobros.ejb.UsuarioTerminalFacade;
import py.com.konecta.redcobros.entities.*;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class USUARIO_TERMINAL extends HttpServlet {

    public static String ID_USUARIO_TERMINAL = "ID_USUARIO_TERMINAL";
    public static String USUARIO = "USUARIO";
    public static String TERMINAL = "TERMINAL";
    public static String FRANJA_HORARIA_CAB = "FRANJA_HORARIA_CAB";
    public static String LOGUEADO = "LOGUEADO";
    public static String PERSONA = "PERSONA";
    public static String RECAUDADOR = "RECAUDADOR";
    public static String SUCURSAL = "SUCURSAL";
    public static String PASE = "PASE";

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JSONObject jsonRespuesta = new JSONObject();
        try {
            String opcion = request.getParameter("op");
            String idUsuarioTerminal = request.getParameter(USUARIO_TERMINAL.ID_USUARIO_TERMINAL);
            String idUsuario = request.getParameter(USUARIO_TERMINAL.USUARIO);
            String idTerminal = request.getParameter(USUARIO_TERMINAL.TERMINAL);
            String idFranjaHorariaCab = request.getParameter(USUARIO_TERMINAL.FRANJA_HORARIA_CAB);
            String logueado = request.getParameter(USUARIO_TERMINAL.LOGUEADO);
            String pase = request.getParameter(USUARIO_TERMINAL.PASE);

            if (opcion.equalsIgnoreCase("AGREGAR")) {
                UsuarioTerminal usuarioTerminal = new UsuarioTerminal();
                usuarioTerminal.setUsuario(new Usuario(new Long(idUsuario)));
                usuarioTerminal.setTerminal(new Terminal(new Long(idTerminal)));
                usuarioTerminal.setFranjaHorariaCab(new FranjaHorariaCab(new Long(idFranjaHorariaCab)));
                usuarioTerminal.setLogueado("N");
                usuarioTerminalFacade.save(usuarioTerminal);

                jsonRespuesta.put("success", true);

            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                usuarioTerminalFacade.delete(new Long(idUsuarioTerminal));

                jsonRespuesta.put("success", true);

            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {

                UsuarioTerminal usuarioTerminal = new UsuarioTerminal();
                usuarioTerminal.setIdUsuarioTerminal(new Long(idUsuarioTerminal));
                usuarioTerminal.setUsuario(new Usuario(new Long(idUsuario)));
                usuarioTerminal.setTerminal(new Terminal(new Long(idTerminal)));
                usuarioTerminal.setFranjaHorariaCab(new FranjaHorariaCab(new Long(idFranjaHorariaCab)));
                usuarioTerminal.setLogueado((logueado != null && logueado.equalsIgnoreCase("on")) ? "S" : "N");
                usuarioTerminalFacade.update(usuarioTerminal);

                jsonRespuesta.put("success", true);

            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                UsuarioTerminal usuarioTerminal = usuarioTerminalFacade.get(new Long(idUsuarioTerminal));

                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(USUARIO_TERMINAL.ID_USUARIO_TERMINAL, usuarioTerminal.getIdUsuarioTerminal());
                jsonDetalle.put(USUARIO_TERMINAL.USUARIO, usuarioTerminal.getUsuario().getIdUsuario());
                jsonDetalle.put(USUARIO_TERMINAL.TERMINAL, usuarioTerminal.getTerminal().getIdTerminal());
                jsonDetalle.put(USUARIO_TERMINAL.FRANJA_HORARIA_CAB, usuarioTerminal.getFranjaHorariaCab().getIdFranjaHorariaCab());
                jsonDetalle.put(USUARIO_TERMINAL.LOGUEADO, (usuarioTerminal.getLogueado() != null && usuarioTerminal.getLogueado().equals("S") ? "on" : ""));
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);

            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                UsuarioTerminal usuarioTerminal = new UsuarioTerminal();
                List<UsuarioTerminal> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                String[] orderBy = new String[2];
                String[] dirBy = new String[2];
                orderBy[0] = "usuario.nombreUsuario";
                orderBy[1] = "terminal.descripcion";
                dirBy[0] = "asc";
                dirBy[1] = "asc";

                if (idUsuario != null && !idUsuario.isEmpty()) {
                    Usuario usuario = new Usuario();
                    usuario.setNombreUsuario(idUsuario);
                    usuarioTerminal.setUsuario(usuario);
                }
                if (idTerminal != null && !idTerminal.isEmpty()) {
                    Terminal terminal = new Terminal();
                    terminal.setDescripcion(idTerminal);
                    usuarioTerminal.setTerminal(terminal);
                }

                if (primerResultado != null && cantResultados != null) {
                    lista = usuarioTerminalFacade.list(usuarioTerminal, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), orderBy, dirBy, true);
                } else {
                    lista = usuarioTerminalFacade.list(usuarioTerminal, "usuario", "asc");
                }
                total = usuarioTerminalFacade.total(usuarioTerminal, true);

                JSONArray jsonUsuariosTerminales = new JSONArray();
                for (UsuarioTerminal e : lista) {
                    JSONObject jsonUsuarioTerminal = new JSONObject();
                    jsonUsuarioTerminal.put(USUARIO_TERMINAL.ID_USUARIO_TERMINAL, e.getIdUsuarioTerminal());
                    jsonUsuarioTerminal.put(USUARIO_TERMINAL.USUARIO, e.getUsuario().getNombreUsuario() + " (" + e.getUsuario().getPersona().getApellidos() + ", " + e.getUsuario().getPersona().getNombres() + ")");
                    jsonUsuarioTerminal.put(USUARIO_TERMINAL.TERMINAL, e.getTerminal().getDescripcion());
                    jsonUsuarioTerminal.put(USUARIO_TERMINAL.FRANJA_HORARIA_CAB, e.getFranjaHorariaCab().getDescripcion());
                    jsonUsuarioTerminal.put(USUARIO_TERMINAL.RECAUDADOR, e.getUsuario().getRecaudador().getDescripcion());
                    jsonUsuarioTerminal.put(USUARIO_TERMINAL.LOGUEADO, e.getLogueado());
                    jsonUsuarioTerminal.put(USUARIO_TERMINAL.SUCURSAL, e.getTerminal().getSucursal().getDescripcion());


                    jsonUsuariosTerminales.add(jsonUsuarioTerminal);
                }
                jsonRespuesta.put("USUARIO_TERMINAL", jsonUsuariosTerminales);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);

            } else if (opcion.equalsIgnoreCase("OBTENER_PASE")) {
                UsuarioTerminal ut = new UsuarioTerminal();
                ut.setFechaPase(new Date());
                ut.setPase(new Integer(pase));

                try {
                    ut = usuarioTerminalFacade.get(ut);
                    if (ut != null) {
                        Terminal ter = ut.getTerminal();
                        Usuario us = ut.getUsuario();
                        Sucursal suc = ter.getSucursal();
                        String datos = "Usuario: " + us.getNombreUsuario() + "</br>"
                                + "Persona: " + us.getPersona().getApellidos() + ", " + ut.getUsuario().getPersona().getNombres() + "</br>"
                                + "Nro. terminal: " + ut.getTerminal().getCodigoTerminal() + "</br>"
                                + "Sucursal: " + suc.getDescripcion() + "</br>"
                                + "Recaudador: " + suc.getRecaudador().getDescripcion();
                        jsonRespuesta.put("msg", datos);
                        jsonRespuesta.put("success", true);
                    } else {
                        jsonRespuesta.put("msg", "No existe Nro. de pase");
                        jsonRespuesta.put("success", false);
                    }

                    /*
                     * jsonRespuesta.put("usuario",
                     * ut.getUsuario().getNombreUsuario());
                     * jsonRespuesta.put("sucursal",ter.getSucursal().getDescripcion());
                     * jsonRespuesta.put("persona",
                     * ut.getUsuario().getPersona().getApellidos()+",
                     * "+ut.getUsuario().getPersona().getNombres());
                     * jsonRespuesta.put("recaudador",
                     * ter.getSucursal().getRecaudador().getDescripcion());
                     */

                } catch (Exception ex) {
                    Logger.getLogger(USUARIO_TERMINAL.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("msg", "Solicitar generacion de un nuevo pase");
                }
            } else {
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
            }
        } catch (Exception exc) {

            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);

        } finally {
            out.println(jsonRespuesta.toString());
        }
    }
    @EJB
    private UsuarioTerminalFacade usuarioTerminalFacade;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
