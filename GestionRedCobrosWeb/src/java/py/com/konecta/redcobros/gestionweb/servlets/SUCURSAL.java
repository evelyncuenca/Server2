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
import py.com.konecta.redcobros.ejb.SucursalFacade;
import py.com.konecta.redcobros.entities.Localidad;
import py.com.konecta.redcobros.entities.Persona;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class SUCURSAL extends HttpServlet {

    public static String ID_SUCURSAL = "ID_SUCURSAL";
    public static String RECAUDADOR = "RECAUDADOR";
    public static String DIRECCION = "DIRECCION";
    public static String DESCRIPCION = "DESCRIPCION";
    public static String CONTACTO_LISTAR = "CONTACTO";
    public static String CONTACTO_DETALLE = "PERSONA";
    public static String TELEFONO = "TELEFONO";
    public static String LOCALIDAD = "LOCALIDAD";
    public static String CODIGO_SUCURSAL = "CODIGO_SUCURSAL";
    public static String NUMERO_CUENTA = "NUMERO_CUENTA";
    public static String CODIGO_SUCURSAL_SET = "CODIGO_SUCURSAL_SET";
    public static String ZONA = "ZONA";
    public static String ES_TIGO = "ES_TIGO";

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
        try {
            String opcion = request.getParameter("op");
            String idSucursal = request.getParameter(SUCURSAL.ID_SUCURSAL);
            String idRecaudador = request.getParameter(SUCURSAL.RECAUDADOR);
            String direccion = request.getParameter(SUCURSAL.DIRECCION);
            String descripcion = request.getParameter(SUCURSAL.DESCRIPCION) != null && !request.getParameter(SUCURSAL.DESCRIPCION).isEmpty() ? request.getParameter(SUCURSAL.DESCRIPCION) : null;
            String contacto = request.getParameter(SUCURSAL.CONTACTO_DETALLE);
            String telefono = request.getParameter(SUCURSAL.TELEFONO);
            String idLocalidad = request.getParameter(SUCURSAL.LOCALIDAD);
            String codigoSucursal = request.getParameter(SUCURSAL.CODIGO_SUCURSAL);
            String numeroCuenta = request.getParameter(SUCURSAL.NUMERO_CUENTA);
            Integer codigoSucursalSet = request.getParameter(SUCURSAL.CODIGO_SUCURSAL_SET) != null && !request.getParameter(SUCURSAL.CODIGO_SUCURSAL_SET).isEmpty() ? new Integer(request.getParameter(SUCURSAL.CODIGO_SUCURSAL_SET)) : null;
            String zona = request.getParameter(SUCURSAL.ZONA);
            String esTigo = request.getParameter(SUCURSAL.ES_TIGO);
            if (opcion.equalsIgnoreCase("AGREGAR")) {
                Sucursal sucursal = new Sucursal();
                sucursal.setRecaudador(new Recaudador(new Long(idRecaudador)));
                sucursal.setLocalidad(new Localidad(new Long(idLocalidad)));
                sucursal.setContacto(new Persona(new Long(contacto)));
                sucursal.setDescripcion(descripcion);
                sucursal.setDireccion(direccion);
                sucursal.setTelefono(telefono);
                sucursal.setCodigoSucursal(Integer.parseInt(codigoSucursal));
                sucursal.setNumeroCuenta(numeroCuenta);
                sucursal.setCodigoSucursalSet(codigoSucursalSet != null ? codigoSucursalSet : null);
                sucursal.setZona(Integer.parseInt(zona));
                sucursal.setEsTigo(esTigo);
                sucursalFacade.save(sucursal);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);

                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                sucursalFacade.delete(new Long(idSucursal));
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Sucursal sucursal = new Sucursal();
                sucursal.setIdSucursal(new Long(idSucursal));
                sucursal.setRecaudador(new Recaudador(new Long(idRecaudador)));
                sucursal.setLocalidad(new Localidad(new Long(idLocalidad)));
                sucursal.setDescripcion(descripcion);
                sucursal.setDireccion(direccion);
                sucursal.setContacto(new Persona(new Long(contacto)));
                sucursal.setTelefono(telefono);
                sucursal.setCodigoSucursal(Integer.parseInt(codigoSucursal));
                sucursal.setNumeroCuenta(numeroCuenta);
                sucursal.setCodigoSucursalSet(codigoSucursalSet != null ? codigoSucursalSet : null);
                sucursal.setZona(Integer.parseInt(zona));
                sucursal.setEsTigo(esTigo);
                sucursalFacade.update(sucursal);
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Sucursal sucursal = sucursalFacade.get(new Long(idSucursal));
                JSONObject jsonRespuesta = new JSONObject();
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(SUCURSAL.ID_SUCURSAL, sucursal.getIdSucursal());
                jsonDetalle.put(SUCURSAL.RECAUDADOR, sucursal.getRecaudador().getIdRecaudador());
                jsonDetalle.put(SUCURSAL.LOCALIDAD, sucursal.getLocalidad().getIdLocalidad());
                jsonDetalle.put(SUCURSAL.DESCRIPCION, sucursal.getDescripcion());
                jsonDetalle.put(SUCURSAL.DIRECCION, sucursal.getDireccion());
                jsonDetalle.put(SUCURSAL.TELEFONO, sucursal.getTelefono());
                jsonDetalle.put(SUCURSAL.CONTACTO_DETALLE, sucursal.getContacto().getIdPersona());
                jsonDetalle.put(SUCURSAL.CODIGO_SUCURSAL, sucursal.getCodigoSucursal());
                jsonDetalle.put(SUCURSAL.NUMERO_CUENTA, sucursal.getNumeroCuenta());
                jsonDetalle.put(SUCURSAL.CODIGO_SUCURSAL_SET, sucursal.getCodigoSucursalSet() != null ? sucursal.getCodigoSucursalSet() : "");
                jsonDetalle.put(SUCURSAL.ZONA, sucursal.getZona());
                jsonDetalle.put(SUCURSAL.ES_TIGO, sucursal.getEsTigo() != null ? sucursal.getEsTigo() : "N");
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Sucursal sucursal = new Sucursal();
                sucursal.setDescripcion(descripcion);
                sucursal.setDireccion(direccion);
                if (idRecaudador != null) {
                    Recaudador rec = new Recaudador();
                    rec.setDescripcion(idRecaudador);
                    sucursal.setRecaudador(rec);
                }
                List<Sucursal> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = sucursalFacade.list(sucursal, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "codigoSucursal", "ASC", true);
                } else {
                    lista = sucursalFacade.list(sucursal, "codigoSucursal", "ASC", true);
                }
                total = sucursalFacade.total(sucursal, true);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonSucursales = new JSONArray();
                for (Sucursal e : lista) {
                    JSONObject jsonSucursal = new JSONObject();
                    jsonSucursal.put(SUCURSAL.ID_SUCURSAL, e.getIdSucursal());
                    jsonSucursal.put(SUCURSAL.RECAUDADOR, e.getRecaudador().getDescripcion());
                    jsonSucursal.put(SUCURSAL.LOCALIDAD, e.getLocalidad().getNombre());
                    jsonSucursal.put(SUCURSAL.DESCRIPCION, e.getDescripcion());
                    jsonSucursal.put(SUCURSAL.DIRECCION, e.getDireccion());
                    jsonSucursal.put(SUCURSAL.TELEFONO, e.getTelefono());
                    jsonSucursal.put(SUCURSAL.CONTACTO_LISTAR, e.getContacto().getApellidos() + ", " + e.getContacto().getNombres());
                    jsonSucursal.put(SUCURSAL.CODIGO_SUCURSAL, e.getCodigoSucursal());
                    jsonSucursal.put(SUCURSAL.NUMERO_CUENTA, e.getNumeroCuenta() != null ? e.getNumeroCuenta() : "-");
                    jsonSucursal.put(SUCURSAL.CODIGO_SUCURSAL_SET, e.getCodigoSucursalSet() != null ? e.getCodigoSucursalSet() : "-");
                    jsonSucursal.put(SUCURSAL.ZONA, e.getZona() == 1 ? "CAPITAL" : "INTERIOR");
                    jsonSucursal.put(SUCURSAL.ES_TIGO, e.getEsTigo() != null ? e.getEsTigo() : "-");
                    jsonSucursales.add(jsonSucursal);
                }
                jsonRespuesta.put("SUCURSAL", jsonSucursales);
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
            //jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
            //out.close();
        }
    }
    @EJB
    private SucursalFacade sucursalFacade;

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
