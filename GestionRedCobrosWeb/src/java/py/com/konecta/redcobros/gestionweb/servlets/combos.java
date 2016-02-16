/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.entities.*;

/**
 *
 * @author konecta
 */
public class combos extends HttpServlet {

    @EJB
    private FormContribFacade formContribFacade;
    @EJB
    private RedFacade redFacade;
    @EJB
    private RecaudadorFacade recaudadorFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private FacturadorFacade facturadorFacade;
    @EJB
    private FranjaHorariaCabFacade franjaHorariaCabFacade;
    @EJB
    private LocalidadFacade localidadFacade;
    @EJB
    private SucursalFacade sucursalFacade;
    @EJB
    private PaisFacade paisFacade;
    @EJB
    private DepartamentoFacade departamentoFacade;
    @EJB
    private CiudadFacade ciudadFacade;
    @EJB
    private TerminalFacade terminalFacade;
    @EJB
    private EntidadFacade entidadFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private EntidadFinancieraFacade entidadFinancieraFacade;
    @EJB
    private CuentaFacade cuentaFacade;
    @EJB
    private RolFacade rolFacade;
    @EJB
    private RolComisionistaFacade rolComisionistaFacade;
    @EJB
    private AplicacionesFacade aplicacionesFacade;
    @EJB
    private PermisosFacade permisosFacade;
    @EJB
    private ServicioFacade servicioFacade;
    @EJB
    private GestionFacade gestionFacade;
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private TipoClearingFacade tipoClearingFacade;
    @EJB
    private ServicioRcFacade servicioRcFacade;
    @EJB
    private RedRecaudadorFacade redRecaudadorFacade;
    @EJB
    private CorteFacade corteFacade;
    @EJB
    private GrupoReporteFacade grupoReporteFacade;
    @EJB
    private NumeroOtFacade numeroOtFacade;
    @EJB
    private UtilFacade util;

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
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String op = request.getParameter("op");
        String elWhere = "";
        String elWhereTotal = "";
        String order_by = "";
        String dir = "asc";

        JSONObject json = new JSONObject();

        if (op.equalsIgnoreCase("FORMULARIO")) {

            FormContrib formularioEjemplo = new FormContrib();
            formularioEjemplo.setCertificado("S");
            formularioEjemplo.setCamposValidos(1);

//            Grupo grupoEjemplo = new Grupo();
//            formularioEjemplo.setGrupo(grupoEjemplo);
//
//            Gestion ejemploGestion = new Gestion();
//            grupoEjemplo.setGestion(ejemploGestion);
//
//            ejemploGestion.setUsuario(new Usuario((Long) request.getSession().getAttribute("idUsuario")));
//            ejemploGestion.setTerminal(new Terminal((Long) request.getSession().getAttribute("idTerminal")));

            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty()) && request.getParameter("query").matches("[0-9]+")) {
                formularioEjemplo.setNumeroOrden(new Long(request.getParameter("query")));
            }

            List<Map<String, Object>> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");

            if (primerResultado != null && cantResultados != null) {
                lista = formContribFacade.listAtributos(formularioEjemplo, false,
                        Integer.parseInt(primerResultado), Integer.parseInt(cantResultados),
                        new String[]{"fechaHoraRecepcion"}, new String[]{"DESC"},
                        false, new String[]{"numeroOrden", "idFormContrib"});
            } else {
                lista = formContribFacade.listAtributos(formularioEjemplo, true, null, null,
                        new String[]{"fechaHoraRecepcion"}, new String[]{"DESC"},
                        false, new String[]{"numeroOrden", "idFormContrib"});
            }

            total = formContribFacade.total(formularioEjemplo);

            JSONObject jsonRespuesta = new JSONObject();
            JSONObject propiedad = new JSONObject();
            JSONArray arrayFilas = new JSONArray();

            for (Map<String, Object> e : lista) {

                propiedad.put("FORMULARIO", e.get("idFormContrib"));
                propiedad.put("DESCRIPCION_FORMULARIO", e.get("numeroOrden"));

                arrayFilas.add(propiedad.clone());
            }
            jsonRespuesta.put("FORMULARIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("ROLES")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;
            JSONObject propiedad = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            Rol example = new Rol();
            if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
                example.setDescripcion(request.getParameter("query"));
            }
            if (request.getParameter("APLICACION") != null && !request.getParameter("APLICACION").isEmpty()) {
                example.setAplicacion(new Aplicacion(new Long(request.getParameter("APLICACION"))));
                List<Rol> listaLog = rolFacade.list(example, start, limit, "descripcion", "ASC", true);
                total = rolFacade.total(example, true);

                for (Rol o : listaLog) {
                    propiedad.put("ROLES", o.getIdRol());
                    propiedad.put("DESCRIPCION_ROLES", o.getDescripcion());
                    arrayFilas.add(propiedad.clone());
                }

                propiedad.clear();
                propiedad.put("ROLES", arrayFilas);
                propiedad.put("success", true);

                propiedad.put("TOTAL", total);

            } else {
                propiedad.put("success", false);
            }
            out.println(propiedad.toString());

        } else if (op.equalsIgnoreCase("ROL_COMISIONISTA")) {
            RolComisionista rolComisionista = new RolComisionista();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                rolComisionista.setDescripcion(request.getParameter("query"));
            }
            List<RolComisionista> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = rolComisionistaFacade.list(rolComisionista, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = rolComisionistaFacade.list(rolComisionista, true);
            }
            total = rolComisionistaFacade.total(rolComisionista, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (RolComisionista e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("ROL", e.getIdRolComisionista());
                propiedad.put("DESCRIPCION_ROL", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("ROL", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("ENTIDADES_NO_POLITICAS")) {

            Integer total = 0;
            JSONObject propiedad = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            EntidadFinanciera example = new EntidadFinanciera();
//            if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
//                example.setDescripcion(request.getParameter("query"));
//            }


            List<Entidad> listaLog = redFacade.obtenerEntidadesNoPoliticas(new Long(request.getParameter("ID_RED")));
            total = listaLog.size();

            for (Entidad o : listaLog) {
                propiedad.put("ENTIDADES_NO_POLITICAS", o.getIdEntidad());
                propiedad.put("DESCRIPCION_ENTIDADES_NO_POLITICAS", o.getNombre());
                arrayFilas.add(propiedad.clone());
            }

            propiedad.clear();
            propiedad.put("ENTIDADES_NO_POLITICAS", arrayFilas);
            propiedad.put("success", true);

            propiedad.put("TOTAL", total);


            out.println(propiedad.toString());

        } else if (op.equalsIgnoreCase("TIPO_CLEARING")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 0;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 10;
            Integer total = 0;
            TipoClearing entity = new TipoClearing();
            if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
                entity.setDescripcion(request.getParameter("query"));
            }
            List<TipoClearing> listaLog = tipoClearingFacade.list(entity, start, limit, "descripcion", "ASC", true);
            total = tipoClearingFacade.total(entity);
            JSONObject propiedad = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (TipoClearing o : listaLog) {
                propiedad.put("TIPO_CLEARING", o.getIdTipoClearing());
                propiedad.put("DESCRIPCION_TIPO_CLEARING", o.getDescripcion());
                arrayFilas.add(propiedad.clone());
            }
            propiedad.clear();
            propiedad.put("TIPO_CLEARING", arrayFilas);
            propiedad.put("success", true);
            propiedad.put("TOTAL", total);
            out.println("(" + propiedad.toString() + ")");

        } else if (op.equalsIgnoreCase("PERMISOS")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;
            JSONObject propiedad = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            Permiso example = new Permiso();

            if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
                example.setDescripcion(request.getParameter("query"));
            }
            if (request.getParameter("APLICACION") != null && !request.getParameter("APLICACION").isEmpty()) {
                example.setAplicacion(new Aplicacion(new Long(request.getParameter("APLICACION"))));
                List<Permiso> listaLog = permisosFacade.list(example, start, limit, "descripcion", "ASC", true);
                total = permisosFacade.total(example, true);

                for (Permiso o : listaLog) {
                    propiedad.put("PERMISOS", o.getIdPermiso());
                    propiedad.put("DESCRIPCION_PERMISOS", o.getDescripcion());
                    arrayFilas.add(propiedad.clone());
                }

                propiedad.clear();
                propiedad.put("PERMISOS", arrayFilas);
                propiedad.put("success", true);

                propiedad.put("TOTAL", total);

            } else {
                propiedad.put("success", false);
            }
            out.println(propiedad.toString());

        } else if (op.equalsIgnoreCase("APLICACIONES")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;
            JSONObject propiedad = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            Aplicacion example = new Aplicacion();
            if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
                example.setDescripcion(request.getParameter("query"));
            }
            List<Aplicacion> listaLog = aplicacionesFacade.list(example, start, limit, "descripcion", "ASC", true);
            total = aplicacionesFacade.total(example, true);

            for (Aplicacion o : listaLog) {
                propiedad.put("APLICACIONES", o.getIdAplicacion());
                propiedad.put("DESCRIPCION_APLICACIONES", o.getDescripcion());
                arrayFilas.add(propiedad.clone());
            }

            propiedad.clear();
            propiedad.put("APLICACIONES", arrayFilas);
            propiedad.put("success", true);

            propiedad.put("TOTAL", total);

            out.println(propiedad.toString());

        } else if (op.equalsIgnoreCase("GESTION")) {

            if (request.getParameter("limit") != null || request.getParameter("start") != null) {
                Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
                Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
                Integer total = 0;
                SimpleDateFormat splDateFormat = new SimpleDateFormat("ddMMyyyy");

                Gestion entity = new Gestion();
                entity.setUsuario(new Usuario(new Long(request.getParameter("ID_USUARIO"))));

                JSONObject propiedad = new JSONObject();
                if (request.getParameter("fecha") != null && !request.getParameter("fecha").isEmpty()) {
                    try {
                        entity.setFechaApertura(splDateFormat.parse(request.getParameter("fecha")));
                    } catch (ParseException ex) {
                        propiedad.put("success", false);
                        out.println("(" + propiedad.toString() + ")");
                        return;
                    }

                } else {
                    entity.setFechaApertura(new Date());
                }

                entity.setCerrado("S");
                if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty()) && (request.getParameter("query").matches("[0-9]+"))) {
                    entity.setIdGestion(new Long(request.getParameter("query")));
                }

                List<Gestion> listaLog = gestionFacade.list(entity, start, limit, "nroGestion", "ASC");
                total = gestionFacade.total(entity);
                JSONArray arrayFilas = new JSONArray();
                for (Gestion o : listaLog) {
                    propiedad.put("GESTION", o.getIdGestion());
                    propiedad.put("DESCRIPCION_GESTION", o.getNroGestion());
                    arrayFilas.add(propiedad.clone());
                }

                propiedad.clear();
                propiedad.put("GESTION", arrayFilas);
                propiedad.put("success", true);

                propiedad.put("TOTAL", total);
                out.println("(" + propiedad.toString() + ")");
            } else {
                JSONObject propiedad = new JSONObject();
                propiedad.put("success", false);
                propiedad.put("motivo", "Faltan los parametros, start y limit");
                out.println("(" + propiedad.toString() + ")");

            }



        } else if (op.equalsIgnoreCase("RED")) {

            /**
             * **********************************
             */
            Red red = new Red();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                red.setDescripcion(request.getParameter("query"));
            }
            List<Red> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = redFacade.list(red, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = redFacade.list(red, true);
            }
            total = redFacade.total(red, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Red e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("RED", e.getIdRed());
                propiedad.put("DESCRIPCION_RED", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("RED", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
            /**
             * ********************************
             */
        } else if (op.equalsIgnoreCase("CUENTA")) {


            Cuenta entity = new Cuenta();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setAliasCuenta(request.getParameter("query"));
            }
            List<Cuenta> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = cuentaFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "aliasCuenta", "ASC", true);
            } else {
                lista = cuentaFacade.list(entity, true);
            }
            total = cuentaFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Cuenta e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("CUENTA", e.getIdCuenta());
                propiedad.put("DESCRIPCION_CUENTA", e.getAliasCuenta());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("CUENTA", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
            /**
             * ********************************
             */
        } else if (op.equalsIgnoreCase("ENTIDAD_FINANCIERA")) {


            EntidadFinanciera entity = new EntidadFinanciera();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }
            List<EntidadFinanciera> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = entidadFinancieraFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = entidadFinancieraFacade.list(entity, true);
            }
            total = entidadFinancieraFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (EntidadFinanciera e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("ENTIDAD_FINANCIERA", e.getIdEntidadFinanciera());
                propiedad.put("DESCRIPCION_ENTIDAD_FINANCIERA", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("ENTIDAD_FINANCIERA", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
            /**
             * ********************************
             */
        } else if (op.equalsIgnoreCase("SUCURSAL")) {
            Sucursal entity = new Sucursal();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            if ((request.getParameter("ID_RECAUDADOR") != null) && (request.getParameter("ID_RECAUDADOR").matches("[0-9]+"))) {
                entity.setRecaudador(new Recaudador(new Long(request.getParameter("ID_RECAUDADOR"))));
            }

            List<Sucursal> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = sucursalFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = sucursalFacade.list(entity, true);
            }
            total = sucursalFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Sucursal e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("SUCURSAL", e.getIdSucursal());
                propiedad.put("DESCRIPCION_SUCURSAL", e.getDescripcion() + " (" + e.getRecaudador().getDescripcion() + ")");

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("SUCURSAL", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("PAIS")) {
            Pais entity = new Pais();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombre(request.getParameter("query"));
            }

            List<Pais> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = paisFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC", true);
            } else {
                lista = paisFacade.list(entity, true);
            }
            total = paisFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Pais e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("PAIS", e.getIdPais());
                propiedad.put("DESCRIPCION_PAIS", e.getNombre());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("PAIS", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("TERMINAL")) {
            Terminal entity = new Terminal();

            if ((request.getParameter("ID_RECAUDADOR") != null) && (request.getParameter("ID_RECAUDADOR").matches("[0-9]+"))) {
                entity.setSucursal(new Sucursal());
                entity.getSucursal().setRecaudador(new Recaudador(new Long(request.getParameter("ID_RECAUDADOR"))));
            }

            // entity.setDescripcion(request.getParameter("query"));
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }
            Usuario usuario = null;
            if ((request.getParameter("USUARIO") != null) && (!request.getParameter("USUARIO").isEmpty())) {

                usuario = usuarioFacade.get(new Long(request.getParameter("USUARIO")));
                entity.setSucursal(new Sucursal());
                entity.getSucursal().setRecaudador(usuario.getRecaudador());

            }


            List<Terminal> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");


            if (primerResultado != null && cantResultados != null) {
                lista = terminalFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = terminalFacade.list(entity, true);
            }
            total = terminalFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            if (usuario != null && usuario.getRecaudador() != null) {
                for (Terminal e : lista) {
                    if ((e.getSucursal().getRecaudador().getIdRecaudador().intValue() == usuario.getRecaudador().getIdRecaudador().intValue())) {
                        JSONObject propiedad = new JSONObject();
                        propiedad.put("TERMINAL", e.getIdTerminal());
                        propiedad.put("DESCRIPCION_TERMINAL", e.getDescripcion() + " (" + e.getSucursal().getRecaudador().getDescripcion() + "-" + e.getSucursal().getDescripcion() + ")");
                        arrayFilas.add(propiedad);
                    }
                }
            } else {
                if (usuario != null && usuario.getRecaudador() == null) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No existe recaudador para este usuario");
                } else {
                    for (Terminal e : lista) {
                        JSONObject propiedad = new JSONObject();
                        propiedad.put("TERMINAL", e.getIdTerminal());
                        propiedad.put("DESCRIPCION_TERMINAL", e.getDescripcion() + " (" + e.getCodigoTerminal() + ")");
                        arrayFilas.add(propiedad);
                    }

                }

            }

            jsonRespuesta.put("TERMINAL", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("USUARIO_PERSONA")) {
            Usuario entity = new Usuario();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombreUsuario(request.getParameter("query"));
            }

            List<Usuario> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = usuarioFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombreUsuario", "ASC", true);
            } else {
                lista = usuarioFacade.list(entity, true);
            }
            total = usuarioFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Usuario e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("USUARIO", e.getIdUsuario());
                propiedad.put("DESCRIPCION_USUARIO", e.getNombreUsuario() + " (" + e.getPersona().getApellidos() + ", " + e.getPersona().getNombres() + ")");

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("USUARIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("USUARIO")) {
            Usuario entity = new Usuario();

            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombreUsuario(request.getParameter("query"));
            }
            if ((request.getParameter("ID_RECAUDADOR") != null) && (request.getParameter("ID_RECAUDADOR").matches("[0-9]+"))) {
                entity.setRecaudador(new Recaudador(new Long(request.getParameter("ID_RECAUDADOR"))));
            }
            List<Usuario> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = usuarioFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombreUsuario", "ASC", true);
            } else {
                lista = usuarioFacade.list(entity, true);
            }
            total = usuarioFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Usuario e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("USUARIO", e.getIdUsuario());
                propiedad.put("DESCRIPCION_USUARIO", e.getNombreUsuario() + " (" + e.getPersona().getApellidos() + ", " + e.getPersona().getNombres() + ")");

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("USUARIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("SERVICIO")) {

            Servicio entity = new Servicio();

            if ((request.getParameter("ID_FACTURADOR") != null) && (!request.getParameter("ID_FACTURADOR").isEmpty())) {
                entity.setFacturador(new Facturador(new Long(request.getParameter("ID_FACTURADOR"))));
            }

            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            List<Servicio> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = servicioFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC");
            } else {
                lista = servicioFacade.list(entity);
            }
            total = servicioFacade.total(entity);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Servicio e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("SERVICIO", e.getCodigoTransaccional());
                propiedad.put("DESCRIPCION_SERVICIO", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("SERVICIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("SERVICIO_CS")) {

            ServicioRc entity = new ServicioRc();

            if ((request.getParameter("ID_FACTURADOR") != null) && (!request.getParameter("ID_FACTURADOR").isEmpty())) {
                entity.setIdFacturador(new Facturador(new Long(request.getParameter("ID_FACTURADOR"))));
            }

            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            List<ServicioRc> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = servicioRcFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC");
            } else {
                lista = servicioRcFacade.list(entity);
            }
            total = servicioRcFacade.total(entity);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilasSer = new JSONArray();
            for (ServicioRc e : lista) {
                JSONObject propiedades = new JSONObject();
                propiedades.put("SERVICIO", e.getIdServicio());
                propiedades.put("DESCRIPCION_SERVICIO", e.getDescripcion());

                arrayFilasSer.add(propiedades);
            }
            jsonRespuesta.put("SERVICIO", arrayFilasSer);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("SERVICIO_CLEARING")) {
            Servicio entity = new Servicio();
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }
            List<Servicio> lista;
            int total;
            lista = redFacade.obtenerServiciosPorRed(new Long(request.getParameter("RED")), new Integer(primerResultado), new Integer(cantResultados));
            total = redFacade.cantidadServiciosPorRed(new Long(request.getParameter("RED")));

            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Servicio e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("SERVICIO", e.getCodigoTransaccional());
                propiedad.put("DESCRIPCION_SERVICIO", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("SERVICIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("SERVICIO_COMISIONAL")) {
            Servicio entity = new Servicio();
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }
            List<Servicio> lista;
            int total;
            lista = redFacade.obtenerServiciosPorRed(new Long(request.getParameter("RED")), new Integer(primerResultado), new Integer(cantResultados));
            total = redFacade.cantidadServiciosPorRed(new Long(request.getParameter("RED")));

            List<ServicioRc> listaSrc = servicioRcFacade.list(Integer.parseInt(primerResultado), Integer.parseInt(cantResultados) - total);

            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Servicio e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("SERVICIO", e.getIdServicio());
                propiedad.put("DESCRIPCION_SERVICIO", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            for (ServicioRc s : listaSrc) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("SERVICIO", s.getIdServicio());
                propiedad.put("DESCRIPCION_SERVICIO", s.getDescripcion());
                arrayFilas.add(propiedad);
            }
            total += servicioRcFacade.total();
            jsonRespuesta.put("SERVICIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } //        else if (op.equalsIgnoreCase("CONCEPTO_COMISION")) {
        //            ConceptoComision entity = new ConceptoComision();
        //
        //            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
        //                entity.setDescripcion(request.getParameter("query"));
        //            }
        //
        //            List<ConceptoComision> lista;
        //            int total;
        //            String primerResultado = request.getParameter("start");
        //            String cantResultados = request.getParameter("limit");
        //            if (primerResultado != null && cantResultados != null) {
        //                lista = conceptoComisionFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC");
        //            } else {
        //                lista = conceptoComisionFacade.list(entity);
        //            }
        //            total = conceptoComisionFacade.total(entity);
        //            JSONObject jsonRespuesta = new JSONObject();
        //            JSONArray arrayFilas = new JSONArray();
        //            for (ConceptoComision e : lista) {
        //                JSONObject propiedad = new JSONObject();
        //                propiedad.put("CONCEPTO_COMISION", e.getIdConceptoComision());
        //                propiedad.put("DESCRIPCION_CONCEPTO_COMISION", e.getDescripcion());
        //                arrayFilas.add(propiedad);
        //            }
        //            jsonRespuesta.put("CONCEPTO_COMISION", arrayFilas);
        //            jsonRespuesta.put("TOTAL", total);
        //            jsonRespuesta.put("success", true);
        //            out.println(jsonRespuesta.toString());
        //        }
        else if (op.equalsIgnoreCase("FRANJA_HORARIA_CAB")) {
            FranjaHorariaCab entity = new FranjaHorariaCab();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            //Solo se listan las franjas horarias del recaudador propietario de la teminal
            if ((request.getParameter("TERMINAL") != null) && (!request.getParameter("TERMINAL").isEmpty())) {
                Terminal terminal = terminalFacade.get(new Long(request.getParameter("TERMINAL")));
                entity.setRecaudador(terminal.getSucursal().getRecaudador());

            }

            List<FranjaHorariaCab> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = franjaHorariaCabFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = franjaHorariaCabFacade.list(entity, true);
            }
            total = franjaHorariaCabFacade.total(entity, true);

            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (FranjaHorariaCab e : lista) {

                JSONObject propiedad = new JSONObject();
                propiedad.put("FRANJA_HORARIA_CAB", e.getIdFranjaHorariaCab());
                propiedad.put("DESCRIPCION_FRANJA_HORARIA_CAB", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("FRANJA_HORARIA_CAB", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("REGISTRO_GESTION")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;

            Gestion entity = new Gestion();
            if ((request.getParameter("TERMINAL") != null && request.getParameter("TERMINAL").matches("[0-9]+"))) {
                entity.setTerminal(new Terminal(new Long(request.getParameter("TERMINAL"))));

            }
            if ((request.getParameter("ID_USUARIO") != null && request.getParameter("ID_USUARIO").matches("[0-9]+"))) {
                entity.setUsuario(new Usuario(new Long(request.getParameter("ID_USUARIO"))));

            }
            // entity.setTerminal(new Terminal((Integer) request.getSession().getAttribute("idTerminal")));
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty()) && (request.getParameter("query").matches("[0-9]+"))) {
                entity.setIdGestion(new Long(request.getParameter("query")));
            }

            List<Gestion> listaLog = gestionFacade.list(entity, start, limit, "nroGestion", "ASC");

            total = gestionFacade.total(entity);
            JSONObject propiedad = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Gestion o : listaLog) {

                propiedad.put("REGISTRO_GESTION", o.getIdGestion());
                propiedad.put("DESCRIPCION_REGISTRO_GESTION", o.getNroGestion());
                arrayFilas.add(propiedad.clone());

            }

            propiedad.clear();
            propiedad.put("REGISTRO_GESTION", arrayFilas);
            propiedad.put("success", true);

            propiedad.put("total", total);
            out.println("(" + propiedad.toString() + ")");

        } else if (op.equalsIgnoreCase("DEPARTAMENTO")) {
            Departamento entity = new Departamento();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombre(request.getParameter("query"));
            }

            List<Departamento> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = departamentoFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC", true);
            } else {
                lista = departamentoFacade.list(entity, true);
            }
            total = departamentoFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Departamento e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("DEPARTAMENTO", e.getIdDepartamento());
                propiedad.put("DESCRIPCION_DEPARTAMENTO", e.getNombre());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("DEPARTAMENTO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("ENTIDAD")) {

            Entidad entity = new Entidad();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombre(request.getParameter("query"));
            }
            Long idRed = (request.getParameter("ID_RED") != null && !request.getParameter("ID_RED").isEmpty()) ? new Long(request.getParameter("ID_RED")) : null;
            if (idRed != null) {

                List<EntidadPolitica> listaLog = redFacade.obtenerEntidadesPoliticas(new Long(request.getParameter("ID_RED")));
                int total = listaLog.size();
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray arrayFilas = new JSONArray();
                for (EntidadPolitica o : listaLog) {
                    JSONObject propiedad = new JSONObject();
                    propiedad.put("ENTIDAD", o.getIdEntidadPolitica());
                    propiedad.put("DESCRIPCION_ENTIDAD", o.getEntidad().getNombre());
                    arrayFilas.add(propiedad.clone());
                }
                jsonRespuesta.put("ENTIDAD", arrayFilas);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else {
                List<Entidad> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = entidadFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC", true);
                } else {
                    lista = entidadFacade.list(entity, true);
                }
                total = entidadFacade.total(entity, true);
                JSONObject jsonRespuesta = new JSONObject();
                JSONArray arrayFilas = new JSONArray();
                for (Entidad e : lista) {
                    JSONObject propiedad = new JSONObject();
                    propiedad.put("ENTIDAD", e.getIdEntidad());
                    propiedad.put("DESCRIPCION_ENTIDAD", e.getNombre());

                    arrayFilas.add(propiedad);
                }
                jsonRespuesta.put("ENTIDAD", arrayFilas);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());

            }


        } else if (op.equalsIgnoreCase("CORTE")) {
            Corte cut = new Corte();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            if (request.getParameter("FECHA_CORTE") != null && !request.getParameter("FECHA_CORTE").isEmpty()) {
                cut.setFechaCorte(sdf.parse(request.getParameter("FECHA_CORTE")));
            }
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            List<Corte> lista = corteFacade.list(cut);
            for (Corte e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("CORTE", e.getIdCorte());
                propiedad.put("DESCRIPCION_CORTE", e.getIdCorte());
                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("CORTE", arrayFilas);
            jsonRespuesta.put("TOTAL", corteFacade.total(cut));
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("CIUDAD")) {
            Ciudad entity = new Ciudad();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombre(request.getParameter("query"));
            }

            List<Ciudad> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = ciudadFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC", true);
            } else {
                lista = ciudadFacade.list(entity, true);
            }
            total = ciudadFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Ciudad e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("CIUDAD", e.getIdCiudad());
                propiedad.put("DESCRIPCION_CIUDAD", e.getNombre());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("CIUDAD", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("PERSONA")) {
            Persona entity = new Persona();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombres(request.getParameter("query"));
            }

            List<Persona> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = personaFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombres", "ASC", true);
            } else {
                lista = personaFacade.list(entity, true);
            }
            total = personaFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Persona e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("PERSONA", e.getIdPersona());
                propiedad.put("DESCRIPCION_PERSONA", e.getApellidos() + ", " + e.getNombres());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("PERSONA", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("FACTURADORES_HABILITADOS")) {
            Collection<HabilitacionFactRed> habilitacionesCollection;
            habilitacionesCollection = this.redFacade.obtenerHabilitacionesFactRed(new Long(request.getParameter("ID_RED")));
            int total = habilitacionesCollection.size();
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (HabilitacionFactRed e : habilitacionesCollection) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("FACTURADOR", e.getFacturador().getIdFacturador());
                propiedad.put("DESCRIPCION_FACTURADOR", e.getFacturador().getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("FACTURADOR", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("FACTURADOR")) {

            Facturador entity = new Facturador();


            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            List<Facturador> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = facturadorFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = facturadorFacade.list(entity, true);
            }
            total = facturadorFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Facturador e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("FACTURADOR", e.getIdFacturador());
                propiedad.put("DESCRIPCION_FACTURADOR", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("FACTURADOR", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("GRUPOS_USUARIO_FECHA")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;
            Long idUsuario = (request.getParameter("ID_USUARIO") != null && !(request.getParameter("ID_USUARIO").isEmpty())) ? new Long(request.getParameter("ID_USUARIO")) : null;
            SimpleDateFormat splDateFormat = new SimpleDateFormat("ddMMyyyy");
            Grupo entity = new Grupo();
            JSONObject propiedad = new JSONObject();

            Date fechaDesde = (request.getParameter("fechaIni") != null && !(request.getParameter("fechaIni").isEmpty())) ? splDateFormat.parse(request.getParameter("fechaIni")) : null;
            Date fechaHasta = (request.getParameter("fechaFin") != null && !(request.getParameter("fechaFin").isEmpty())) ? splDateFormat.parse(request.getParameter("fechaFin")) : null;

            List<Grupo> listaLog = grupoFacade.getGruposUsuarioFecha(fechaDesde, fechaHasta, idUsuario, start, limit);

            total = grupoFacade.getTotalGruposUsuarioFecha(fechaDesde, fechaHasta, idUsuario);
            JSONArray arrayFilas = new JSONArray();

            for (Grupo o : listaLog) {

                propiedad.put("GRUPOS_USUARIO_FECHA", o.getIdGrupo());
                propiedad.put("DESCRIPCION_GRUPOS_USUARIO_FECHA", o.getIdGrupo());
                arrayFilas.add(propiedad.clone());

            }

            propiedad.clear();
            propiedad.put("GRUPOS_USUARIO_FECHA", arrayFilas);
            propiedad.put("success", true);

            propiedad.put("total", total);
            out.println("(" + propiedad.toString() + ")");

        } else if (op.equalsIgnoreCase("LOCALIDAD")) {
            Localidad entity = new Localidad();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombre(request.getParameter("query"));
            }
            List<Localidad> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");

            if (primerResultado != null && cantResultados != null) {
                lista = localidadFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC", true);
            } else {
                lista = localidadFacade.list(entity, true);
            }
            total = localidadFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();

            for (Localidad e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("LOCALIDAD", e.getIdLocalidad());
                propiedad.put("DESCRIPCION_LOCALIDAD", e.getNombre());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("LOCALIDAD", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("RECAUDADOR")) {
            Recaudador entity = new Recaudador();
            List<Recaudador> lista = new ArrayList<Recaudador>();
            int total = 0;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");

            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }
            if ((request.getParameter("ID_RED") != null) && (request.getParameter("ID_RED").matches("[0-9]+"))) {
                RedRecaudadorPK rrPk = new RedRecaudadorPK();
                if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
                    List<Recaudador> lRec = recaudadorFacade.list(entity, true);
                    for (Recaudador rec : lRec) {
                        rrPk.setIdRecaudador(rec.getIdRecaudador());
                        rrPk.setIdRed(Long.parseLong(request.getParameter("ID_RED")));
                        if (redRecaudadorFacade.get(rrPk) != null) {
                            lista.add(rec);
                            total++;
                        }
                    }
                } else {

                    rrPk.setIdRed(new Long(request.getParameter("ID_RED")));
                    RedRecaudador redRec = new RedRecaudador(rrPk);

                    List<RedRecaudador> listRedRec;
                    if (primerResultado != null && cantResultados != null) {
                        listRedRec = redRecaudadorFacade.list(redRec, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
                    } else {
                        listRedRec = redRecaudadorFacade.list(redRec, "descripcion", "ASC", true);
                    }
                    total = redRecaudadorFacade.total(redRec, true);
                    for (RedRecaudador it : listRedRec) {
                        lista.add(recaudadorFacade.get(it.getRecaudador().getIdRecaudador()));
                    }
                }
            } else {

                if (primerResultado != null && cantResultados != null) {
                    lista = recaudadorFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
                } else {
                    lista = recaudadorFacade.list(entity, "descripcion", "ASC", true);
                }
                total = recaudadorFacade.total(entity, true);
            }
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Recaudador e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("RECAUDADOR", e.getIdRecaudador());
                propiedad.put("DESCRIPCION_RECAUDADOR", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("RECAUDADOR", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("RECAUDADOR_COMISIONAL")) {
            //Recaudador para el esquema comisional
            Recaudador entity = new Recaudador();
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            List<Recaudador> lista;
            int total;
            Integer idServicio = new Integer(request.getParameter("ID_SERVICIO"));
            if (idServicio < 3) {
                lista = recaudadorFacade.obtenerRecaudadoresPorServicio(new Long(request.getParameter("ID_SERVICIO")), start, limit);
                total = recaudadorFacade.cantidadRecaudadoresPorServicio(new Long(request.getParameter("ID_SERVICIO")));
            } else {
                Recaudador rec = new Recaudador();
                lista = recaudadorFacade.list(rec);
                total = recaudadorFacade.total(rec);
            }
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Recaudador e : lista) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("RECAUDADOR", e.getIdRecaudador());
                propiedad.put("DESCRIPCION_RECAUDADOR", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("RECAUDADOR", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("RED_RECAUDADORES")) {
            Recaudador entity = new Recaudador();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }
            Collection<Recaudador> recaudadoresCollection = null;
            int total = 0;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");

            if (primerResultado != null && cantResultados != null && request.getParameter("ID_RED") != null && !request.getParameter("ID_RED").isEmpty()) {
                recaudadoresCollection = this.redFacade.obtenerRecaudadoresHabilitados(
                        new Long(request.getParameter("ID_RED")));
                total = recaudadoresCollection.size();
                //} else {
                //    recaudadoresCollection = this.redFacade.obtenerRecaudadoresHabilitados(
                //            Integer.parseInt(request.getParameter("ID_RED")));
            }

            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (Recaudador e : recaudadoresCollection) {
                JSONObject propiedad = new JSONObject();
                propiedad.put("RECAUDADOR", e.getIdRecaudador());
                propiedad.put("DESCRIPCION_RECAUDADOR", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("RECAUDADOR", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("GRUPO_REPORTE")) {

            GrupoReporte entity = new GrupoReporte();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            //Long idGrupo = (request.getParameter("ID_RED") != null && !request.getParameter("ID_RED").isEmpty()) ? new Long(request.getParameter("ID_RED")) : null;
            List<GrupoReporte> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = grupoReporteFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = grupoReporteFacade.list(entity, true);
            }
            total = grupoReporteFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (GrupoReporte e : lista) {
                if (e.getIdGrupo() != 6 && e.getIdGrupo() != 7) {
                    JSONObject propiedad = new JSONObject();
                    propiedad.put("GRUPO_REPORTE", e.getIdGrupo());
                    propiedad.put("DESCRIPCION_GRUPO_REPORTE", e.getDescripcion());

                    arrayFilas.add(propiedad);
                }
            }
            jsonRespuesta.put("GRUPO_REPORTE", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("NRO_OT")) {

            NumeroOt entity = new NumeroOt();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setEraNumeroOtDv(request.getParameter("query"));
            }
            
            if (( request.getParameter("FECHA")!= null)&&( request.getParameter("TIPO")!= null)) {
                Integer tipo= new Integer(request.getParameter("TIPO"));
                int nroDias = tipo == 1 ? 2 : tipo == 2 ? 4 : 0;
                if (tipo<3){
                    String fecha=request.getParameter("FECHA");
                    Calendar fechaDoc=Calendar.getInstance();
                    fechaDoc.setTime(new SimpleDateFormat("ddMMyyyy").parse(fecha));
                    try {
                        fechaDoc = util.diaHabilSiguiente(fechaDoc, -nroDias);
                    } catch (Exception ex) {
                        Logger.getLogger(combos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Date d1=fechaDoc.getTime();
                    entity.setFecha(d1);
                }
            }

            //Long idGrupo = (request.getParameter("ID_RED") != null && !request.getParameter("ID_RED").isEmpty()) ? new Long(request.getParameter("ID_RED")) : null;
            List<NumeroOt> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = numeroOtFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "eraNumeroOtDv", "ASC", true);
            } else {
                lista = numeroOtFacade.list(entity, true);
            }
            total = numeroOtFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray arrayFilas = new JSONArray();
            for (NumeroOt e : lista) {
                //if (e.getIdGrupo() != 6 && e.getIdGrupo() != 7) {
                    JSONObject propiedad = new JSONObject();
                    propiedad.put("NRO_OT", e.getIdNumeroOt());
                    propiedad.put("DESCRIPCION_NRO_OT", e.getEraNumeroOtDv());

                    arrayFilas.add(propiedad);
                //}
            }
            jsonRespuesta.put("NRO_OT", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        }else {
            json.put("success", false);
            json.put("motivo", "No existe la opcion pedida");
            out.println(json);

        }


        //out.close();
    }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(combos.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(combos.class.getName()).log(Level.SEVERE, null, ex);
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
