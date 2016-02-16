/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.GestorDireccionesFacade;
import py.com.konecta.redcobros.ejb.GestorFacade;
import py.com.konecta.redcobros.ejb.LocalidadFacade;
import py.com.konecta.redcobros.entities.Gestor;
import py.com.konecta.redcobros.entities.GestorDirecciones;
import py.com.konecta.redcobros.entities.Localidad;

/**
 *
 * @author konecta
 */
public class GESTOR extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private GestorFacade gestorFacade;
    @EJB
    private GestorDireccionesFacade gestorDireccionFacade;
    @EJB
    private LocalidadFacade localidadFacade;
    public static String ID_GESTOR = "ID_GESTOR";
    public static String SEXO = "SEXO";
    public static String INDEPENDIENTE = "INDEPENDIENTE";
    public static String LOCALIDAD = "LOCALIDAD";
    public static String DESCRIPCION_LOCALIDAD = "DESCRIPCION_LOCALIDAD";
    public static String FECHA_NACIMIENTO = "FECHA_NACIMIENTO";
    public static String FECHA_DESDE = "FECHA_DESDE";
    public static String FECHA_HASTA = "FECHA_HASTA";
    public static String NOMBRE = "NOMBRE";
    public static String APELLIDO = "APELLIDO";
    public static String CI = "CI";
    public static String DIRECCION = "DIRECCION";
    public static String TEL = "TEL";
    public static String CEL = "CEL";
    public static String MAIL = "MAIL";
    public static String EMPRESA = "EMPRESA";
    public static String EMP_DIRECCION = "EMP_DIRECCION";
    public static String TEL_EMPRESA = "TEL_EMPRESA";
    public static String CLIENTE_RECOMENDO = "CLIENTE_RECOMENDO";
    public static String NOMBRE_RECOMENDO = "NOMBRE_RECOMENDO";
    public static String TEL_RECOMENDO = "TEL_RECOMENDO";
    public static String CONSULTA = "CONSULTA";
    public static String SORTEAR = "SORTEAR";
    public static String MIN = "MIN";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String opcion = request.getParameter("op");
            DateFormat df = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");

            String idGestor = request.getParameter(GESTOR.ID_GESTOR) != null && !request.getParameter(GESTOR.ID_GESTOR).isEmpty() ? request.getParameter(GESTOR.ID_GESTOR) : null;
            String sexo = request.getParameter(GESTOR.SEXO) != null && !request.getParameter(GESTOR.SEXO).isEmpty() ? request.getParameter(GESTOR.SEXO) : null;
            String independiente = request.getParameter(GESTOR.INDEPENDIENTE) != null && !request.getParameter(GESTOR.INDEPENDIENTE).isEmpty() ? "INDEPENDIENTE" : "DEPENDIENTE";
            Long idLocalidad = request.getParameter(GESTOR.LOCALIDAD) != null && !request.getParameter(GESTOR.LOCALIDAD).isEmpty() ? new Long(request.getParameter(GESTOR.LOCALIDAD)) : null;
            Date fechaNacimiento = request.getParameter(GESTOR.FECHA_NACIMIENTO) != null && !request.getParameter(GESTOR.FECHA_NACIMIENTO).isEmpty() ? df.parse(request.getParameter(GESTOR.FECHA_NACIMIENTO)) : null;
            Date fechaDesde = request.getParameter(GESTOR.FECHA_DESDE) != null && !request.getParameter(GESTOR.FECHA_DESDE).isEmpty() ? df.parse(request.getParameter(GESTOR.FECHA_DESDE)) : null;
            Date fechaHasta = request.getParameter(GESTOR.FECHA_HASTA) != null && !request.getParameter(GESTOR.FECHA_HASTA).isEmpty() ? df.parse(request.getParameter(GESTOR.FECHA_HASTA)) : null;
            String nombreGestor = request.getParameter(GESTOR.NOMBRE) != null && !request.getParameter(GESTOR.NOMBRE).isEmpty() ? request.getParameter(GESTOR.NOMBRE).toUpperCase() : null;
            String apellidoGestor = request.getParameter(GESTOR.APELLIDO) != null && !request.getParameter(GESTOR.APELLIDO).isEmpty() ? request.getParameter(GESTOR.APELLIDO).toUpperCase() : null;
            String ci = request.getParameter(GESTOR.CI) != null && !request.getParameter(GESTOR.CI).isEmpty() ? request.getParameter(GESTOR.CI) : null;
            String direccionGestor = request.getParameter(GESTOR.DIRECCION) != null && !request.getParameter(GESTOR.DIRECCION).isEmpty() ? request.getParameter(GESTOR.DIRECCION).toUpperCase() : null;
            String telGestor = request.getParameter(GESTOR.TEL) != null && !request.getParameter(GESTOR.TEL).isEmpty() ? request.getParameter(GESTOR.TEL) : null;
            String celGestor = request.getParameter(GESTOR.CEL) != null && !request.getParameter(GESTOR.CEL).isEmpty() ? request.getParameter(GESTOR.CEL) : null;
            String mailGestor = request.getParameter(GESTOR.MAIL) != null && !request.getParameter(GESTOR.MAIL).isEmpty() ? request.getParameter(GESTOR.MAIL) : null;
            String nombreEmpresa = request.getParameter(GESTOR.EMPRESA) != null && !request.getParameter(GESTOR.EMPRESA).isEmpty() ? request.getParameter(GESTOR.EMPRESA) : null;
            String direccionEmpresa = request.getParameter(GESTOR.EMP_DIRECCION) != null && !request.getParameter(GESTOR.EMP_DIRECCION).isEmpty() ? request.getParameter(GESTOR.EMP_DIRECCION).toUpperCase() : null;
            String telEmpresa = request.getParameter(GESTOR.TEL_EMPRESA) != null && !request.getParameter(GESTOR.TEL_EMPRESA).isEmpty() ? request.getParameter(GESTOR.TEL_EMPRESA) : null;
            String clienteRecomendo = request.getParameter(GESTOR.CLIENTE_RECOMENDO) != null && !request.getParameter(GESTOR.CLIENTE_RECOMENDO).isEmpty() ? request.getParameter(GESTOR.CLIENTE_RECOMENDO) : null;
            String nombreRecomendo = request.getParameter(GESTOR.NOMBRE_RECOMENDO) != null && !request.getParameter(GESTOR.NOMBRE_RECOMENDO).isEmpty() ? request.getParameter(GESTOR.NOMBRE_RECOMENDO) : null;
            String telRecomendo = request.getParameter(GESTOR.TEL_RECOMENDO) != null && !request.getParameter(GESTOR.TEL_RECOMENDO).isEmpty() ? request.getParameter(GESTOR.TEL_RECOMENDO) : null;
            String critConsulta = request.getParameter(GESTOR.CONSULTA) != null && !request.getParameter(GESTOR.CONSULTA).isEmpty() ? request.getParameter(GESTOR.CONSULTA) : null;
            Integer minForm = request.getParameter(GESTOR.MIN) != null && !request.getParameter(GESTOR.MIN).isEmpty() ? new Integer(request.getParameter(GESTOR.MIN)) : 25;

            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");

            if (opcion.equalsIgnoreCase("CONSULTA")) {
                JSONObject jsonRespuesta = new JSONObject();
                Gestor gesEjemplo = new Gestor();
                if (critConsulta != null && critConsulta.matches("[0-9]+")) {
                    gesEjemplo.setCedula(critConsulta);
                } else if (critConsulta != null && !critConsulta.isEmpty()) {
                    gesEjemplo.setNombre(critConsulta);
                }

                List<Gestor> lGestores =
                        gestorFacade.list(gesEjemplo,
                        Integer.parseInt(primerResultado),
                        Integer.parseInt(cantResultados), "nombre", "asc", true);

              /*  for (Gestor g : lGestores) {
                }*/
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("AGREGAR")) {

                JSONObject jsonRespuesta = new JSONObject();
                Gestor newGestor = new Gestor();
                if (gestorFacade.get(ci) == null) {
                    try {
                        GestorDirecciones newGesDir = new GestorDirecciones();
                        GestorDirecciones newGesEmpDir = new GestorDirecciones();

                        newGesDir.setIdGestor(ci);
                        newGesDir.setCelular(celGestor);
                        newGesDir.setDireccion(direccionGestor);
                        newGesDir.setEmail(mailGestor);
                        newGesDir.setIdLocalidad(idLocalidad);
                        newGesDir.setTelefono(telGestor);
                        newGesDir.setTipoDireccion("PARTICULAR");
                        //newGesDir.setEmpresa(nombreEmpresa);
                        gestorDireccionFacade.save(newGesDir);
                        if (direccionEmpresa != null || telEmpresa != null || nombreEmpresa != null) {
                            newGesEmpDir.setIdGestor(ci);
                            //newGesEmpDir.setCelular(celGestor);
                            newGesEmpDir.setDireccion(direccionEmpresa);
                            //newGesEmpDir.setEmail(ci);
                            newGesEmpDir.setIdLocalidad(idLocalidad);
                            newGesEmpDir.setTelefono(telEmpresa);
                            newGesEmpDir.setTipoDireccion("LABORAL");
                            newGesEmpDir.setEmpresa(nombreEmpresa);
                            gestorDireccionFacade.save(newGesEmpDir);
                        }


                        newGestor.setNombre(nombreGestor);
                        newGestor.setApellido(apellidoGestor);
                        newGestor.setCedula(ci);
                        newGestor.setFechaNacimiento(fechaNacimiento);
                        newGestor.setSexo(sexo);
                        newGestor.setSugerido(clienteRecomendo);

                        newGestor.setTipoGestor(independiente);
                        gestorFacade.save(newGestor);
                        jsonRespuesta.put("success", true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        jsonRespuesta.put("success", false);
                    }
                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "Gestor ya ingresado");
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                JSONObject jsonRespuesta = new JSONObject();
                Gestor gesUp = new Gestor();
                GestorDirecciones entity = new GestorDirecciones();
                GestorDirecciones newDirLab = new GestorDirecciones();
                Boolean isNewDirLab = false;
                entity.setIdGestor(idGestor);

                gesUp.setApellido(apellidoGestor);
                gesUp.setNombre(nombreGestor);
                gesUp.setCedula(idGestor);
                gesUp.setFechaNacimiento(fechaNacimiento);
                gesUp.setSexo(sexo);
                gesUp.setSugerido(clienteRecomendo);//null
                gesUp.setTipoGestor(independiente);

                List<GestorDirecciones> lGesDir = gestorDireccionFacade.list(entity);
                if (lGesDir.size() > 0) {
                    lGesDir.get(0).setCelular(celGestor);//null
                    lGesDir.get(0).setDireccion(direccionGestor);
                    lGesDir.get(0).setEmail(mailGestor);//null
                    // lGesDir.get(0).setEmpresa(nombreEmpresa);//null
                    lGesDir.get(0).setIdGestor(idGestor);
                    lGesDir.get(0).setIdLocalidad(idLocalidad);
                    lGesDir.get(0).setTelefono(telGestor);//null
                    lGesDir.get(0).setTipoDireccion("PARTICULAR");
                    if (lGesDir.size() > 1) {
                        lGesDir.get(1).setIdGestor(idGestor);
                        lGesDir.get(1).setDireccion(direccionEmpresa);
                        lGesDir.get(1).setIdLocalidad(idLocalidad);
                        lGesDir.get(1).setTelefono(telEmpresa);
                        lGesDir.get(1).setTipoDireccion("LABORAL");
                        lGesDir.get(1).setEmpresa(nombreEmpresa);
                    } else if (independiente.equalsIgnoreCase("DEPENDIENTE")) {
                        isNewDirLab = true;
                        newDirLab.setIdGestor(idGestor);
                        newDirLab.setDireccion(direccionEmpresa);
                        newDirLab.setIdLocalidad(idLocalidad);
                        newDirLab.setTelefono(telEmpresa);
                        newDirLab.setTipoDireccion("LABORAL");
                        newDirLab.setEmpresa(nombreEmpresa);
                    }
                }


                try {
                    gestorFacade.update(gesUp);
                    gestorDireccionFacade.update(lGesDir.get(0));

                    if (independiente.equalsIgnoreCase("DEPENDIENTE")) {
                        gestorDireccionFacade.update(isNewDirLab ? newDirLab : lGesDir.get(1));
                    }
                    jsonRespuesta.put("success", true);

                } catch (Exception ex) {
                    jsonRespuesta.put("motivo", "No se pudo actualizar los datos");
                    jsonRespuesta.put("success", false);
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {
                Gestor gestor = new Gestor();
                List<Gestor> lista;
                int total;

                if (critConsulta != null && critConsulta.matches("[0-9]+")) {
                    gestor.setCedula(critConsulta);
                } else if (critConsulta != null && !critConsulta.isEmpty()) {
                    gestor.setNombre(critConsulta);
                }

                if (primerResultado != null && cantResultados != null) {
                    if (critConsulta != null) {
                        lista = gestorFacade.list(gestor, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC", true);
                    } else {
                        lista = gestorFacade.list(gestor, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC");
                    }
                } else {
                    if (critConsulta != null) {
                        lista = gestorFacade.list(gestor, "nombre", "ASC", true);
                    } else {
                        lista = gestorFacade.list(gestor, "nombre", "ASC");
                    }
                }

                if (critConsulta != null) {
                    total = gestorFacade.total(gestor, true);
                } else {
                    total = gestorFacade.total(gestor);
                }

                JSONObject jsonRespuesta = new JSONObject();
                JSONArray jsonGestores = new JSONArray();
                for (Gestor e : lista) {
                    JSONObject jsonContribuyente = new JSONObject();
                    jsonContribuyente.put(GESTOR.ID_GESTOR, e.getCedula());
                    jsonContribuyente.put(GESTOR.NOMBRE, e.getNombre());
                    jsonContribuyente.put(GESTOR.APELLIDO, e.getApellido());
                    jsonContribuyente.put(GESTOR.CLIENTE_RECOMENDO, e.getSugerido());
                    jsonContribuyente.put(GESTOR.INDEPENDIENTE, e.getTipoGestor());
                    jsonContribuyente.put(GESTOR.FECHA_NACIMIENTO, e.getFechaNacimiento() != null ? spdf.format(e.getFechaNacimiento()) : "");
                    jsonContribuyente.put(GESTOR.SEXO, e.getSexo());
                    jsonGestores.add(jsonContribuyente);
                }
                jsonRespuesta.put("GESTOR", jsonGestores);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Gestor gestor = new Gestor();
                gestor.setCedula(idGestor);
                Gestor getGestor = gestorFacade.get(gestor);
                JSONObject jsonRespuesta = new JSONObject();

                JSONObject jsonGestor = new JSONObject();
                if (getGestor != null) {
                    GestorDirecciones gesDir = new GestorDirecciones();
                    gesDir.setIdGestor(idGestor);
                    List<GestorDirecciones> lGesDir = gestorDireccionFacade.list(gesDir);

                    //------------------------------DATOS PERSONALES------------------------------
                    jsonGestor.put(GESTOR.INDEPENDIENTE, getGestor.getTipoGestor().equalsIgnoreCase("INDEPENDIENTE") ? "true" : "false");
                    jsonGestor.put(GESTOR.NOMBRE, getGestor.getNombre());
                    jsonGestor.put(GESTOR.APELLIDO, getGestor.getApellido());
                    jsonGestor.put(GESTOR.ID_GESTOR, getGestor.getCedula());
                    if (lGesDir.size() > 0) {

                        jsonGestor.put(GESTOR.DIRECCION, lGesDir.get(0).getDireccion());
                        Localidad localidad = new Localidad(lGesDir.get(0).getIdLocalidad());
                        jsonGestor.put(GESTOR.LOCALIDAD, localidadFacade.get(localidad).getIdLocalidad());
                        jsonGestor.put(GESTOR.TEL, lGesDir.get(0).getTelefono());
                        jsonGestor.put(GESTOR.CEL, lGesDir.get(0).getCelular());
                        jsonGestor.put(GESTOR.MAIL, lGesDir.get(0).getEmail());
                        jsonGestor.put(GESTOR.FECHA_NACIMIENTO, spdf.format(getGestor.getFechaNacimiento()));
                        jsonGestor.put(GESTOR.SEXO, getGestor.getSexo());

                        //------------------------------DATOS EMPRESA------------------------------
                        if (lGesDir.size() > 1) {
                            jsonGestor.put(GESTOR.EMPRESA, lGesDir.get(1).getEmpresa());
                            jsonGestor.put(GESTOR.EMP_DIRECCION, lGesDir.get(1).getDireccion());
                            jsonGestor.put(GESTOR.TEL_EMPRESA, lGesDir.get(1).getTelefono());
                        }

                    }

                    //     ------------------------------DATOS SUGERIDO------------------------------
                    if (getGestor.getSugerido() != null) {
                        Gestor gestSug = gestorFacade.get(getGestor.getSugerido());
                        if (gestSug != null) {
                            jsonGestor.put(GESTOR.CLIENTE_RECOMENDO, gestSug.getCedula());
                            jsonGestor.put(GESTOR.NOMBRE_RECOMENDO, gestSug.getNombre() + " " + gestSug.getApellido());
                            //jsonGestor.put(GESTOR.TEL_RECOMENDO, gestSug.);
                        }
                    }

                    jsonRespuesta.put("data", jsonGestor);
                    jsonRespuesta.put("TOTAL", gestorFacade.total(gestor));
                    jsonRespuesta.put("success", true);
                } else {
                    jsonRespuesta.put("success", false);
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                JSONObject jsonRespuesta = new JSONObject();
                try {
                    GestorDirecciones ejemplo = new GestorDirecciones();
                    ejemplo.setIdGestor(idGestor);
                    List<GestorDirecciones> lGesDir = gestorDireccionFacade.list(ejemplo);
                    for (GestorDirecciones it : lGesDir) {
                        gestorDireccionFacade.delete(it.getIdDireccion());
                    }
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    jsonRespuesta.put("motivo", "No se pudo borrar direcciones Gestor.");
                    jsonRespuesta.put("success", false);
                }
                try {
                    gestorFacade.delete(idGestor);
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    jsonRespuesta.put("motivo", "No se pudo borrar Gestor.");
                    jsonRespuesta.put("success", false);
                }

                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("SORTEAR")) {
                JSONObject jsonRespuesta = new JSONObject();

                Long idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
                Long idSucursal = request.getParameter("SUCURSAL") != null && !request.getParameter("SUCURSAL").isEmpty() ? new Long(request.getParameter("SUCURSAL")) : null;
                try {
                    List<Map<String, Object>> lGes = gestorFacade.getListGestor(idRecaudador, idSucursal, fechaDesde, fechaHasta);
                    List<Map<String, Object>> preSeleccion = new ArrayList<Map<String, Object>>();
                    for (Map<String, Object> map : lGes) {
                        //     String gestor = (String) map.get("idGestor");
                        Integer total = (Integer) map.get("total");
                        if (total >= minForm) {
                            preSeleccion.add(map);
                        }
                    }
                    int index = (int) (Math.random() * 1000000) % preSeleccion.size();

                    jsonRespuesta.put("GANADOR", preSeleccion.get(index).get("idGestor"));
                    jsonRespuesta.put("TOTAL", "1");
                    jsonRespuesta.put("success", true);
                    out.println(jsonRespuesta.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    jsonRespuesta.put("motivo", "No hay Ganador en esta fecha y Sucursal.");
                    jsonRespuesta.put("success", false);
                    out.println(jsonRespuesta.toString());
                }
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
