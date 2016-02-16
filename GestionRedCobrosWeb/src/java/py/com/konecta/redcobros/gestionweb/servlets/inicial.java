/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import py.com.konecta.redcobros.gestionweb.utiles.Utiles;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.GrupoFacade;
import py.com.konecta.redcobros.ejb.UsuarioFacade;
import py.com.konecta.redcobros.ejb.UsuarioTerminalFacade;
import py.com.konecta.redcobros.entities.Usuario;
import py.com.konecta.redcobros.entities.UsuarioTerminal;

/**
 *
 * @author konecta
 */
public class inicial extends HttpServlet {

    @EJB
    private UsuarioTerminalFacade usuarioTerminalFacade;
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private UsuarioFacade usuarioFacade;

    private static final Logger logger = Logger.getLogger(inicial.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=UTF-8");

        String viewJsp;
        JSONObject json = new JSONObject();

        String op = (request.getParameter("op") != null) ? request.getParameter("op") : "";
        HttpSession session = request.getSession(false);

        if ((op.equalsIgnoreCase("logeado")) && session != null) {
            viewJsp = "/areaTrabajo.jsp";
            this.llamarPagina(request, response, viewJsp);

        } else if (op.equalsIgnoreCase("LOGOUT")) {
            logger.info("Logout");
            PrintWriter out = response.getWriter();
            if (session != null) {
                session.invalidate();
            }
            json.put("success", true);
            out.println(json.toString());

        } else if (op.equalsIgnoreCase("CAMBIAR_PASSWORD")) {
            PrintWriter out = response.getWriter();
            if (request.getParameter("confirmar_new_pass") != null && (request.getParameter("old_pass") != null) && (request.getParameter("old_pass").matches("[\\w]+")) && ((request.getParameter("new_pass") != null)) && ((request.getParameter("new_pass").matches("[\\w]+")))) {
                logger.info("Cambio Pass");
                Usuario registro = usuarioFacade.get((Long) request.getSession().getAttribute("idUsuario"));
                if (registro.getContrasenha().equals(Utiles.getSha1(request.getParameter("old_pass")))) {
                    if (request.getParameter("new_pass").equals(request.getParameter("confirmar_new_pass"))) {
                        registro.setContrasenha(Utiles.getSha1(request.getParameter("new_pass")));
                        usuarioFacade.merge(registro);
                        json.put("success", true);
                        json.put("motivo", "Actualizacion Exitosa");
                        logger.info("OK");
                    } else {
                        logger.info("No coinciden el campo 'Password Nuevo' y el campo 'Confirmar Password Nuevo'");
                        json.put("success", false);
                        json.put("motivo", "No coinciden el campo 'Password Nuevo' y el campo 'Confirmar Password Nuevo'");
                    }
                } else {
                    logger.info("No se puede realizar la operacion. El password actual no coincide");
                    json.put("success", false);
                    json.put("motivo", "No se puede realizar la operacion. El password actual no coincide");
                }
            } else {
                logger.info("Los parametros no son validos");
                json.put("success", false);
                json.put("motivo", "Los parametros no son validos");

            }
            out.println(json);

        } else if (session != null && op.equalsIgnoreCase("GET_HASH")) {
            PrintWriter out = response.getWriter();
            try {
                json.put("hash", Utiles.getSha1(request.getParameter("CADENA")));
                json.put("success", true);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "No se pudo generar el hash", ex);
                json.put("motivo", "No se pudo generar el hash.");
                json.put("success", false);
            }
            out.print(json.toString());

        } else if (session != null && op.equalsIgnoreCase("CERRAR_GESTIONES_RED")) {
            PrintWriter out = response.getWriter();
            try {
                SimpleDateFormat spdf = new SimpleDateFormat("ddMMyyyy");
                grupoFacade.cerrarGruposRed(new Long(request.getParameter("RED")), spdf.parse(request.getParameter("FECHA")));
                json.put("success", true);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "No se pudo realizar el cierre de las cajas", ex);
                json.put("motivo", "No se pudo realizar el cierre de las cajas");
                json.put("success", false);
            }

            out.print(json.toString());

        } else if (op.equalsIgnoreCase("login")) {
            String user = (request.getParameter("loginUsername").equalsIgnoreCase("")) ? "" : request.getParameter("loginUsername");
            String password = (request.getParameter("loginPassword").equalsIgnoreCase("")) ? "" : request.getParameter("loginPassword");
            String mac = (request.getParameter("mac1").equalsIgnoreCase("")) ? "" : request.getParameter("mac1");
            logger.log(Level.INFO, "Login : [{0};{1};{2}]", new Object[]{user, password, mac});
            if (user.matches("[\\w]+")) {
                UsuarioTerminal login = usuarioTerminalFacade.auntenticarGestion(user, password, mac);
                if (login == null) {
                    logger.info("No se encontro el usuario correspondiente");
                    PrintWriter out = response.getWriter();
                    String jsonMensaje = "{ success: false, errors: { reason: 'Fallo el login. Inténtelo de nuevo.' }}";
                    out.print(jsonMensaje);
                } else {
                    logger.info("Login correcto");
                    HttpSession sessionUsuario = request.getSession(true);
                    sessionUsuario.setAttribute("nombresUsuario", login.getUsuario().getPersona().getNombres());
                    sessionUsuario.setAttribute("apellidosUsuario", login.getUsuario().getPersona().getApellidos());
                    sessionUsuario.setAttribute("idUsuario", login.getUsuario().getIdUsuario());
                    sessionUsuario.setAttribute("contrasenha", login.getUsuario().getContrasenha());

                    List<String> listaPermisos = new ArrayList();
                    sessionUsuario.setAttribute("LISTA_PERMISOS", listaPermisos);
                    json.put("success", true);
                    PrintWriter out = response.getWriter();
                    out.print(json.toString());

                }
            } else {
                logger.info("No cumple la Exp. Regular");
                PrintWriter out = response.getWriter();
                String jsonMensaje = "{ success: false, errors: { reason: 'Fallo el login. Inténtelo de nuevo.' }}";
                out.print(jsonMensaje);
            }

        } else {
            logger.info("No existe la opcion pedida");
            json.put("success", false);
            json.put("motivo", "No existe la opcion Pedida");
            PrintWriter out = response.getWriter();
            out.print(json);
        }
    }

    protected void llamarPagina(HttpServletRequest request, HttpServletResponse response, String viewJsp) {
        try {

            getServletConfig().getServletContext().getRequestDispatcher(viewJsp).forward(request, response);
        } catch (ServletException ex) {
            logger.log(Level.SEVERE, "Error indeterminado", ex);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error indeterminado", ex);
        }
    }

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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, "NoSuchAlgorithmException", ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, "NoSuchAlgorithmException", ex);
        }
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
