/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.UsuarioFacade;
import py.com.konecta.redcobros.entities.Persona;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Usuario;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class USUARIO extends HttpServlet {

    public static String ID_USUARIO = "ID_USUARIO";
    public static String PERSONA = "PERSONA";
    public static String NOMBRE_USUARIO = "NOMBRE_USUARIO";
    public static String CONTRASENHA = "CONTRASENHA";
    public static String CODIGO = "CODIGO";
    public static String ES_SUPERVISOR = "ES_SUPERVISOR";
    public static String RECAUDADOR = "RECAUDADOR";
    public static String A_ASIGNAR = "A_ASIGNAR";
    public static String TELEFONO_PERSONA = "TELEFONO_PERSONA";
    public static String CODIGO_EXTERNO = "CODIGO_EXTERNO";

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
            String idUsuario = request.getParameter(USUARIO.ID_USUARIO);
            String idPersona = request.getParameter(USUARIO.PERSONA);
            String nombreUsuario = request.getParameter(USUARIO.NOMBRE_USUARIO);
            String contrasenha = request.getParameter(USUARIO.CONTRASENHA);
            String codigo = request.getParameter(USUARIO.CODIGO);
            String recaudador = request.getParameter(USUARIO.RECAUDADOR);
            String supervisores = request.getParameter(USUARIO.A_ASIGNAR);
            String esSupervisor = request.getParameter(USUARIO.ES_SUPERVISOR);
            String codigoExterno = request.getParameter(USUARIO.CODIGO_EXTERNO) != null && !request.getParameter(USUARIO.CODIGO_EXTERNO).isEmpty() ? request.getParameter(USUARIO.CODIGO_EXTERNO) : null;

            if (esSupervisor != null && esSupervisor.equals("on")) {
                esSupervisor = "S";
            } else {
                esSupervisor = "N";
            }
            String[] supervisoresArray;
            if (supervisores != null && !supervisores.isEmpty()) {
                supervisoresArray = supervisores.replaceAll(" ", "").split(",");
            } else {
                supervisoresArray = new String[]{};
            }
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                JSONObject jsonRespuesta = new JSONObject();
                if (nombreUsuario.equals("root")) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se puede crear un usuario con ese nombre");
                } else {
                    Usuario usuario = new Usuario();
                    usuario.setPersona(new Persona(new Long(idPersona)));
                    usuario.setNombreUsuario(nombreUsuario);
                    usuario.setContrasenha(py.com.konecta.redcobros.gestionweb.utiles.Utiles.getSha1(contrasenha));
                    usuario.setCodigo(Integer.parseInt(codigo));
                    usuario.setRecaudador(new Recaudador(new Long(recaudador)));
                    usuario.setEsSupervisor(esSupervisor);
                    if (codigoExterno != null) {
                        usuario.setCodExterno(new Long(codigoExterno));
                    }
                    usuarioFacade.save(usuario);
                    jsonRespuesta.put("success", true);
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                usuarioFacade.delete(new Long(idUsuario));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                JSONObject jsonRespuesta = new JSONObject();
                Usuario usuario = this.usuarioFacade.get(new Long(idUsuario));
                if (!usuario.getNombreUsuario().equals("root") && nombreUsuario.equals("root")) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se puede cambiar a ese nombre de usuario");
                } else {
                    usuario.setIdUsuario(new Long(idUsuario));
                    usuario.setPersona(new Persona(new Long(idPersona)));
                    usuario.setNombreUsuario(nombreUsuario);
                    if (codigoExterno != null) {
                        usuario.setCodExterno(new Long(codigoExterno));
                    }
                    if ((contrasenha != null && !contrasenha.isEmpty())) {
                        usuario.setContrasenha(py.com.konecta.redcobros.gestionweb.utiles.Utiles.getSha1(contrasenha));
                    }
                    usuario.setCodigo(Integer.parseInt(codigo));
                    if (!usuario.getNombreUsuario().equals("root") && !recaudador.matches("[0-9]+")) {
                        jsonRespuesta.put("success", false);
                        jsonRespuesta.put("motivo", "Debe elegir un recaudador");
                    } else if (recaudador.matches("[0-9]+")) {
                        usuario.setRecaudador(new Recaudador(new Long(recaudador)));
                    }

                    usuario.setEsSupervisor(esSupervisor);
                    usuarioFacade.update(usuario);
                    jsonRespuesta.put("success", true);

                }

                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("GUARDAR_SUPERVISORES")) {
                Usuario usuario = this.usuarioFacade.get(new Long(idUsuario));
                Collection<Usuario> supervisoresCollection = new ArrayList<Usuario>();
                for (String idSupervisor : supervisoresArray) {
                    supervisoresCollection.add(new Usuario(new Long(idSupervisor)));
                }
                usuario.setSupervisoresCollection(supervisoresCollection);
                JSONObject jsonRespuesta = new JSONObject();
                usuarioFacade.update(usuario);
                jsonRespuesta.put("success", true);

                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {

                Usuario usuario = usuarioFacade.get(new Long(idUsuario));

                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(USUARIO.ID_USUARIO, usuario.getIdUsuario());
                jsonDetalle.put(USUARIO.PERSONA, usuario.getPersona().getIdPersona());
                jsonDetalle.put(USUARIO.NOMBRE_USUARIO, usuario.getNombreUsuario());
                jsonDetalle.put(USUARIO.CODIGO, usuario.getCodigo());
                jsonDetalle.put(USUARIO.ES_SUPERVISOR, (usuario.getEsSupervisor().equals("S") ? "on" : ""));
                jsonDetalle.put(USUARIO.RECAUDADOR, (usuario.getRecaudador() != null) ? usuario.getRecaudador().getIdRecaudador() : "");
                jsonDetalle.put(USUARIO.CODIGO_EXTERNO, (usuario.getCodExterno() != null) ? usuario.getCodExterno() : "");
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR_SUPERVISORES")) {
                String flagAsignacion = request.getParameter("FLAG_ASIGNACION");
                JSONObject jsonRespuesta = new JSONObject();
                Collection<Usuario> supervisoresCollection;
                if (flagAsignacion.equalsIgnoreCase("SI")) {
                    supervisoresCollection = this.usuarioFacade.obtenerSupervisoresAsignados(new Long(idUsuario));
                } else {
                    supervisoresCollection = this.usuarioFacade.obtenerSupervisoresNoAsignados(new Long(idUsuario));
                }
                JSONArray jsonUsuarios = new JSONArray();
                String jsonParticular = "";
                for (Usuario e : supervisoresCollection) {

                    JSONObject jsonUsuario = new JSONObject();
                    jsonUsuario.put(USUARIO.ID_USUARIO, e.getIdUsuario());
                    jsonUsuario.put(USUARIO.PERSONA, e.getPersona().getApellidos() + " " + e.getPersona().getNombres() + " (" + e.getNombreUsuario() + ")");

                    jsonUsuarios.add(jsonUsuario);
                }


                jsonRespuesta.put("SUPERVISORES", jsonUsuarios);
                jsonRespuesta.put("TOTAL", supervisoresCollection.size());
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Usuario usuario = new Usuario();
                List<Usuario> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");

                if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                    usuario.setNombreUsuario(nombreUsuario);
                }
                if (idPersona != null && !idPersona.isEmpty()) {
                    Persona ejemPersona = new Persona();
                    ejemPersona.setNombres(idPersona);
                    usuario.setPersona(ejemPersona);
                }

                if (primerResultado != null && cantResultados != null) {
                    lista = usuarioFacade.list(usuario, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "codigo", "ASC", true);
                } else {
                    lista = usuarioFacade.list(usuario, "codigo", "ASC");
                }
                total = usuarioFacade.total(usuario, true);

                if (total == 0) {
                    if (idPersona != null && !idPersona.isEmpty()) {
                        Persona ejemPersona = new Persona();
                        ejemPersona.setApellidos(idPersona);
                        usuario.setPersona(ejemPersona);
                    }
                    if (primerResultado != null && cantResultados != null) {
                        lista = usuarioFacade.list(usuario, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "codigo", "ASC", true);
                    } else {
                        lista = usuarioFacade.list(usuario, "codigo", "ASC");
                    }
                    total = usuarioFacade.total(usuario, true);
                }
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonUsuarios = new JSONArray();
                for (Usuario e : lista) {
                    JSONObject jsonUsuario = new JSONObject();
                    jsonUsuario.put(USUARIO.ID_USUARIO, e.getIdUsuario());
                    jsonUsuario.put(USUARIO.PERSONA, e.getPersona().getApellidos() + ", " + e.getPersona().getNombres());
                    jsonUsuario.put(USUARIO.NOMBRE_USUARIO, e.getNombreUsuario());
                    jsonUsuario.put(USUARIO.ES_SUPERVISOR, e.getEsSupervisor());
                    jsonUsuario.put(USUARIO.CODIGO, e.getCodigo());
                    jsonUsuario.put(USUARIO.CODIGO_EXTERNO, e.getCodExterno());
                    jsonUsuario.put(USUARIO.RECAUDADOR, e.getRecaudador() != null ? e.getRecaudador().getDescripcion() : "");
                    jsonUsuario.put(USUARIO.TELEFONO_PERSONA, e.getPersona().getTelefono());
                    jsonUsuarios.add(jsonUsuario);
                }
                jsonRespuesta.put("USUARIO", jsonUsuarios);
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
            exc.printStackTrace();
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            // jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private UsuarioFacade usuarioFacade;

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
