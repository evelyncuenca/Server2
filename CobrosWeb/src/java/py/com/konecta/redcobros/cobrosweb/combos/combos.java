/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.combos;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.AplicacionesFacade;
import py.com.konecta.redcobros.ejb.EntidadFinancieraFacade;
import py.com.konecta.redcobros.ejb.FacturadorFacade;
import py.com.konecta.redcobros.ejb.FormContribFacade;
import py.com.konecta.redcobros.ejb.FranjaHorariaCabFacade;
import py.com.konecta.redcobros.ejb.GestionFacade;
import py.com.konecta.redcobros.ejb.GrupoFacade;
import py.com.konecta.redcobros.ejb.ImpuestoFacade;
import py.com.konecta.redcobros.ejb.LocalidadFacade;
import py.com.konecta.redcobros.ejb.CiudadFacade;
import py.com.konecta.redcobros.ejb.DepartamentoFacade;
import py.com.konecta.redcobros.ejb.EntidadFacade;
import py.com.konecta.redcobros.ejb.EventoFacade;
import py.com.konecta.redcobros.ejb.GestorFacade;
import py.com.konecta.redcobros.ejb.IcedFacade;
import py.com.konecta.redcobros.ejb.MonedaFacade;
import py.com.konecta.redcobros.ejb.PaisFacade;
import py.com.konecta.redcobros.ejb.PermisosFacade;
import py.com.konecta.redcobros.ejb.PersonaFacade;
import py.com.konecta.redcobros.ejb.RecaudadorFacade;
import py.com.konecta.redcobros.ejb.RedFacade;
import py.com.konecta.redcobros.ejb.RolFacade;
import py.com.konecta.redcobros.ejb.ServicioFacade;
import py.com.konecta.redcobros.ejb.ServicioRcFacade;
import py.com.konecta.redcobros.ejb.SucursalFacade;
import py.com.konecta.redcobros.ejb.TerminalFacade;

import py.com.konecta.redcobros.ejb.TipoClearingFacade;
import py.com.konecta.redcobros.ejb.TransaccionFacade;
import py.com.konecta.redcobros.ejb.TransaccionRcFacade;
import py.com.konecta.redcobros.ejb.UsuarioFacade;

import py.com.konecta.redcobros.entities.Aplicacion;
import py.com.konecta.redcobros.entities.Ciudad;
import py.com.konecta.redcobros.entities.EntidadFinanciera;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.FormContrib;
import py.com.konecta.redcobros.entities.FranjaHorariaCab;

import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.Grupo;
import py.com.konecta.redcobros.entities.Impuesto;
import py.com.konecta.redcobros.entities.Localidad;
import py.com.konecta.redcobros.entities.Departamento;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.EntidadPolitica;
import py.com.konecta.redcobros.entities.EstadoTransaccion;
import py.com.konecta.redcobros.entities.Evento;
import py.com.konecta.redcobros.entities.Gestor;
import py.com.konecta.redcobros.entities.Iced;
import py.com.konecta.redcobros.entities.Moneda;
import py.com.konecta.redcobros.entities.Pais;
import py.com.konecta.redcobros.entities.Permiso;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Rol;
import py.com.konecta.redcobros.entities.Persona;
import py.com.konecta.redcobros.entities.Servicio;
import py.com.konecta.redcobros.entities.ServicioRc;
import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.entities.Terminal;
import py.com.konecta.redcobros.entities.Transaccion;
import py.com.konecta.redcobros.entities.TransaccionRc;
import py.com.konecta.redcobros.entities.TipoOperacion;
import py.com.konecta.redcobros.entities.Usuario;
import py.com.konecta.redcobros.entities.UsuarioTerminal;

/**
 *
 * @author konecta
 */
public class combos extends HttpServlet {

    @EJB
    FormContribFacade formContribFacade;
    @EJB
    EntidadFinancieraFacade entidadFinancieraFacade;
    @EJB
    TransaccionFacade transaccionFacade;
    @EJB
    TransaccionRcFacade transaccionRcFacade;
    @EJB
    FacturadorFacade facturadorFacade;
    @EJB
    ServicioFacade servicioFacade;
    @EJB
    ServicioRcFacade servicioRcFacade;
    @EJB
    private GestionFacade gestionFacade;
    @EJB
    RedFacade redFacade;
    @EJB
    private FranjaHorariaCabFacade franjaHorariaCabFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private RecaudadorFacade recaudadorFacade;
    @EJB
    UsuarioFacade usuarioFacade;
    @EJB
    private TerminalFacade terminalFacade;
    @EJB
    private LocalidadFacade localidadFacade;
    @EJB
    private PaisFacade paisFacade;
    @EJB
    private DepartamentoFacade departamentoFacade;
    @EJB
    private CiudadFacade ciudadFacade;
    @EJB
    GrupoFacade grupoFacade;
    @EJB
    RolFacade rolFacade;
    @EJB
    PermisosFacade permisosFacade;
    @EJB
    AplicacionesFacade aplicacionesFacade;
    @EJB
    ImpuestoFacade impuestoFacade;
    @EJB
    TipoClearingFacade tipoClearingFacade;
    @EJB
    private SucursalFacade sucursalFacade;
    @EJB
    private GestorFacade gestorFacade;
    @EJB
    private EntidadFacade entidadFacade;
    @EJB
    private EventoFacade eventoFacade;
    @EJB
    private IcedFacade icedFacade;
    @EJB
    private MonedaFacade monedaFacade;

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
        JSONObject propiedad = new JSONObject();
        JSONArray arrayFilas = new JSONArray();

        JSONObject json = new JSONObject();



        if (op.equalsIgnoreCase("REFERENCIAS")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;
            String query = null;
            if (request.getParameter("query") != null && request.getParameter("query").matches("[0-9]+")) {
                query = request.getParameter("query");
            }
            List<Long> listaLog = formContribFacade.getReferencias(((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")).getTerminal().getSucursal(), query);


            for (Long o : listaLog) {
                propiedad.put("idFormContrib", o);
                arrayFilas.add(propiedad.clone());
            }

            propiedad.clear();
            propiedad.put("referencias", arrayFilas);
            propiedad.put("success", true);

            //propiedad.put("total", total);
            out.println("(" + propiedad.toString() + ")");

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
            //JSONArray arrayFilas = new JSONArray();
            for (Usuario e : lista) {
                JSONObject propiedades = new JSONObject();
                propiedades.put("USUARIO", e.getIdUsuario());
                propiedades.put("DESCRIPCION_USUARIO", e.getNombreUsuario() + " (" + e.getPersona().getApellidos() + ", " + e.getPersona().getNombres() + ")");

                arrayFilas.add(propiedades);
            }
            jsonRespuesta.put("USUARIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("GRUPOS_USUARIO_FECHA")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;
            SimpleDateFormat splDateFormat = new SimpleDateFormat("ddMMyyyy");
            Grupo entity = new Grupo();

            entity.setGestion(new Gestion());
            entity.getGestion().setUsuario(((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")).getUsuario());
            if (request.getParameter("fecha") != null && !request.getParameter("fecha").isEmpty()) {
                try {
                    entity.setFecha(splDateFormat.parse(request.getParameter("fecha")));
                } catch (ParseException ex) {
                    propiedad.put("success", false);
                    out.println("(" + propiedad.toString() + ")");
                    return;
                }

            } else {
                entity.setFecha(new Date());
            }
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty()) && (request.getParameter("query").matches("[0-9]+"))) {
                entity.setIdGrupo(new Long(request.getParameter("query")));
            }

            List<Grupo> listaLog = grupoFacade.list(entity, start, limit, "idGrupo", "asc");

            total = grupoFacade.total(entity);

            for (Grupo o : listaLog) {

                propiedad.put("GRUPOS_USUARIO_FECHA", o.getIdGrupo());
                propiedad.put("DESCRIPCION_GRUPOS_USUARIO_FECHA", o.getIdGrupo());
                arrayFilas.add(propiedad.clone());

            }

            propiedad.clear();
            propiedad.put("GRUPOS_USUARIO_FECHA", arrayFilas);
            propiedad.put("success", true);

            propiedad.put("TOTAL", total);
            out.println("(" + propiedad.toString() + ")");

        } else if (op.equalsIgnoreCase("REGISTRO_GESTION")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;
            SimpleDateFormat spdf = new SimpleDateFormat("ddMMyyyy");
            Date fechaIni = null;
            Date fechaFin = null;
            Long idTerminal = null;
            Gestion entity = new Gestion();

            //TIPO_CONSULTA = 1 , todas las terminales. TIPO_CONSULTA = 2 , sólo esta terminales.
            if (request.getParameter("fechaIni") != null) {
                fechaIni = spdf.parse(request.getParameter("fechaIni"));
            }
            if (request.getParameter("fechaFin") != null) {
                fechaFin = spdf.parse(request.getParameter("fechaFin"));
            } else {
                fechaFin = spdf.parse(request.getParameter("fechaIni"));
            }
            if (request.getParameter("tipo_consulta") != null && request.getParameter("tipo_consulta").equalsIgnoreCase("2")) {
                idTerminal = (Long) request.getSession().getAttribute("idTerminal");
            }
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty()) && (request.getParameter("query").matches("[0-9]+"))) {
                entity.setIdGestion(new Long(request.getParameter("query")));
            }
            List<Gestion> listaLog = gestionFacade.comboCriteriaComboFechaIniFechaFin(idTerminal, (Long) request.getSession().getAttribute("idUsuario"), fechaIni, fechaFin, start, limit);
            total = gestionFacade.countCriteriaComboFechaIniFechaFin(idTerminal, (Long) request.getSession().getAttribute("idUsuario"), fechaIni, fechaFin);
            for (Gestion o : listaLog) {
                propiedad.put("REGISTRO_GESTION", o.getIdGestion());
                propiedad.put("DESCRIPCION_REGISTRO_GESTION", o.getNroGestion());
                arrayFilas.add(propiedad.clone());
            }
            propiedad.clear();
            propiedad.put("REGISTRO_GESTION", arrayFilas);
            propiedad.put("success", true);
            propiedad.put("TOTAL", total);
            out.println("(" + propiedad.toString() + ")");

        } else if (op.equalsIgnoreCase("GESTION")) {

            String limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? request.getParameter("limit") : null;
            String start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? request.getParameter("start") : null;
            Integer total = 0;
            SimpleDateFormat splDateFormat = new SimpleDateFormat("ddMMyyyy");

            Gestion entity = new Gestion();
            entity.setUsuario(((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")).getUsuario());
            //  entity.setTerminal(((UsuarioTerminal)request.getSession().getAttribute("objUsuarioTerminal")).getTerminal());

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
            List<Gestion> listaLog = new ArrayList<Gestion>();
            if (start != null && limit != null) {
                listaLog = gestionFacade.list(entity, Integer.parseInt(start), Integer.parseInt(limit));
            } else {
                listaLog = gestionFacade.list(entity);
            }

            total = gestionFacade.total(entity);
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
            total = facturadorFacade.total(entity);
            JSONObject jsonRespuesta = new JSONObject();
            arrayFilas = new JSONArray();
            for (Facturador e : lista) {
                propiedad = new JSONObject();
                propiedad.put("FACTURADOR", e.getIdFacturador());
                propiedad.put("DESCRIPCION_FACTURADOR", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("FACTURADOR", arrayFilas);
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
            arrayFilas = new JSONArray();
            for (Servicio e : lista) {
                propiedad = new JSONObject();
                propiedad.put("SERVICIO", e.getCodigoTransaccional());
                propiedad.put("DESCRIPCION_SERVICIO", e.getDescripcion());

                arrayFilas.add(propiedad);
            }
            jsonRespuesta.put("SERVICIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("TRANSACCION")) {

            Transaccion entity = new Transaccion();
            Gestion ejemploGestion = new Gestion();
            ejemploGestion.setUsuario(new Usuario((Long) request.getSession().getAttribute("idUsuario")));
            ejemploGestion.setTerminal(new Terminal((Long) request.getSession().getAttribute("idTerminal")));
            entity.setGestion(ejemploGestion);
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty()) && request.getParameter("query").matches("[0-9]+")) {
                entity.setIdTransaccion(new Long(request.getParameter("query")));
            }
            List<Transaccion> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = transaccionFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "fechaHoraSistema", "DESC");
            } else {
                lista = transaccionFacade.list(entity);
            }
            total = transaccionFacade.total(entity);
            JSONObject jsonRespuesta = new JSONObject();

            for (Transaccion e : lista) {

                propiedad.put("TRANSACCION", e.getIdTransaccion());
                propiedad.put("DESCRIPCION_TRANSACCION", e.getIdTransaccion());

                arrayFilas.add(propiedad.clone());
            }
            jsonRespuesta.put("TRANSACCION", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("TRANSACCION_CS")) {
            HttpSession session = request.getSession();
            String isAnulacion = request.getParameter("combo") != null && !request.getParameter("combo").isEmpty() ? request.getParameter("combo") : "empty";

            Map<String, HashMap<String, String>> tasks = new HashMap<String, HashMap<String, String>>();
            tasks = (Map<String, HashMap<String, String>>) session.getAttribute("TASKS");
            UsuarioTerminal ut = (UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal");
            Gestion gestion = null;
            try {
                gestion = gestionFacade.obtenerGestionDelDia(ut.getIdUsuarioTerminal(), tasks.get("OPERACIONES").get("APERTURA_SIMPLE") != null);
            } catch (Exception e) {
            }

            TransaccionRc transRc = new TransaccionRc();

            //if (isAnulacion.equalsIgnoreCase("ANULACION")) {
            transRc.setIdGestion(gestion);
            //}
            transRc.setAnulado("N");
            transRc.setIdEstadoTransaccion(new EstadoTransaccion(22L));
            TipoOperacion ejemploTO = new TipoOperacion();
            ejemploTO.setIdTipoOperacion(Short.valueOf("1"));
            transRc.setIdTipoOperacion(ejemploTO);

            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty()) && request.getParameter("query").matches("[0-9]+")) {
                transRc.setIdTransaccion(new BigDecimal(request.getParameter("query")));
            }
            List<TransaccionRc> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = transaccionRcFacade.list(transRc, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "idTransaccion", "ASC");
            } else {
                lista = transaccionRcFacade.list(transRc, "idTransaccion", "ASC");
            }
            total = transaccionRcFacade.total(transRc);
            JSONObject jsonRespuesta = new JSONObject();

            for (TransaccionRc e : lista) {

                propiedad.put("ID_TRANSACCION", e.getIdTransaccion());
                propiedad.put("DESCRIPCION_TRANSACCION", e.getIdTransaccion());

                arrayFilas.add(propiedad.clone());
            }
            jsonRespuesta.put("ID_TRANSACCION", arrayFilas);
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
                lista = servicioRcFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = servicioRcFacade.list(entity, true);
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
        } else if (op.equalsIgnoreCase("FORMULARIO")) {

            boolean isAnulacion = request.getParameter("combo") != null && !request.getParameter("combo").isEmpty() ? request.getParameter("combo").equalsIgnoreCase("ANULAR") ? true : false : false;
            FormContrib formularioEjemplo = new FormContrib();
            formularioEjemplo.setCertificado("S");
            formularioEjemplo.setCamposValidos(1);
            //formularioEjemplo.setUsuarioTerminalCarga((UsuarioTerminal)request.getSession().getAttribute("objUsuarioTerminal"));
            if (isAnulacion) {
                formularioEjemplo.setFechaPresentacion(new Date());
            }

//            Grupo grupoEjemplo = new Grupo();
//            formularioEjemplo.setGrupo(grupoEjemplo);

//            Gestion ejemploGestion = new Gestion();
//            grupoEjemplo.setGestion(ejemploGestion);

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

            for (Map<String, Object> e : lista) {

                propiedad.put("FORMULARIO", e.get("idFormContrib"));
                propiedad.put("DESCRIPCION_FORMULARIO", e.get("numeroOrden"));

                arrayFilas.add(propiedad.clone());
            }
            jsonRespuesta.put("FORMULARIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("APLICACIONES")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;

            Aplicacion example = new Aplicacion();
            if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
                example.setDescripcion(request.getParameter("query"));
            }
            List<Aplicacion> listaLog = aplicacionesFacade.list(example, start, limit, "descripcion", "ASC", true);
            total = aplicacionesFacade.total(example, true);

            for (Aplicacion o : listaLog) {

                if (o.getDescripcion().equalsIgnoreCase("Cobros Web")) {
                    propiedad.put("APLICACIONES", o.getIdAplicacion());
                    propiedad.put("DESCRIPCION_APLICACIONES", o.getDescripcion());
                }

                arrayFilas.add(propiedad.clone());
            }

            propiedad.clear();
            propiedad.put("APLICACIONES", arrayFilas);
            propiedad.put("success", true);

            propiedad.put("TOTAL", total);

            out.println(propiedad.toString());

        } else if (op.equalsIgnoreCase("ROLES")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;

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

            for (Usuario e : lista) {

                JSONObject propiedad2 = new JSONObject();

                propiedad2.put("CONF_USUARIO", e.getIdUsuario());
                propiedad2.put("DESCRIPCION_USUARIO", e.getNombreUsuario() + " (" + e.getPersona().getApellidos() + ", " + e.getPersona().getNombres() + ")");

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("CONF_USUARIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("CONF_USUARIO")) {
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

            for (Usuario e : lista) {

                JSONObject propiedad2 = new JSONObject();

                propiedad2.put("CONF_USUARIO", e.getIdUsuario());
                propiedad2.put("DESCRIPCION_USUARIO", e.getNombreUsuario() + " (" + e.getPersona().getApellidos() + ", " + e.getPersona().getNombres() + ")");

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("CONF_USUARIO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("PERMISOS")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;
            Integer total = 0;

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

        } else if (op.equalsIgnoreCase("RECAUDADOR")) {
            Recaudador entity = new Recaudador();

            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }
            if ((request.getParameter("ID_RED") != null) && (request.getParameter("ID_RED").matches("[0-9]+"))) {
                entity.setRed(new Red(new Long(request.getParameter("ID_RED"))));
            }


            List<Recaudador> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");

            if (primerResultado != null && cantResultados != null) {
                lista = recaudadorFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = recaudadorFacade.list(entity, "descripcion", "ASC", true);
            }
            total = recaudadorFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();

            for (Recaudador e : lista) {

                JSONObject propiedad2 = new JSONObject();

                propiedad2.put("RECAUDADOR", e.getIdRecaudador());
                propiedad2.put("DESCRIPCION_RECAUDADOR", e.getDescripcion());

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("RECAUDADOR", arrayFilas);
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

            for (Persona e : lista) {

                JSONObject propiedad2 = new JSONObject();

                propiedad2.put("PERSONA", e.getIdPersona());
                propiedad2.put("DESCRIPCION_PERSONA", e.getApellidos() + ", " + e.getNombres());

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("PERSONA", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("SUCURSAL")) {
            Sucursal entity = new Sucursal();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            if ((request.getParameter("ID_RECAUDADOR") != null) && (request.getParameter("ID_RECAUDADOR").matches("[0-9]+"))) {
                entity.setRecaudador(new Recaudador(new Long(request.getParameter("ID_RECAUDADOR"))));
            } else {
                entity.setRecaudador(new Recaudador((Long) (request.getSession().getAttribute("idRecaudador"))));
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

            for (Sucursal e : lista) {
                JSONObject propiedad2 = new JSONObject();
                propiedad2.put("SUCURSAL", e.getIdSucursal());
                propiedad2.put("DESCRIPCION_SUCURSAL", e.getDescripcion() + " (" + e.getRecaudador().getDescripcion() + ")");

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("SUCURSAL", arrayFilas);
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

            if (usuario != null && usuario.getRecaudador() != null) {
                for (Terminal e : lista) {
                    if ((e.getSucursal().getRecaudador().getIdRecaudador().intValue() == usuario.getRecaudador().getIdRecaudador().intValue())) {
                        JSONObject propiedad2 = new JSONObject();
                        propiedad2.put("TERMINAL", e.getIdTerminal());
                        propiedad2.put("DESCRIPCION_TERMINAL", e.getDescripcion() + " (" + e.getSucursal().getRecaudador().getDescripcion() + "-" + e.getSucursal().getDescripcion() + ")");
                        arrayFilas.add(propiedad2);
                    }
                }
            } else {
                if (usuario != null && usuario.getRecaudador() == null) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No existe recaudador para este usuario");
                } else {
                    for (Terminal e : lista) {
                        JSONObject propiedad2 = new JSONObject();
                        propiedad2.put("TERMINAL", e.getIdTerminal());
                        propiedad2.put("DESCRIPCION_TERMINAL", e.getDescripcion() + " (" + e.getCodigoTerminal() + ")");
                        arrayFilas.add(propiedad2);
                    }

                }

            }

            jsonRespuesta.put("TERMINAL", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("FRANJA_HORARIA_CAB")) {
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

            for (FranjaHorariaCab e : lista) {

                JSONObject propiedad2 = new JSONObject();
                propiedad2.put("FRANJA_HORARIA_CAB", e.getIdFranjaHorariaCab());
                propiedad2.put("DESCRIPCION_FRANJA_HORARIA_CAB", e.getDescripcion());

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("FRANJA_HORARIA_CAB", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

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

            for (Localidad e : lista) {
                JSONObject propiedad2 = new JSONObject();
                propiedad2.put("LOCALIDAD", e.getIdLocalidad());
                propiedad2.put("DESCRIPCION_LOCALIDAD", e.getNombre());

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("LOCALIDAD", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
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

            for (Ciudad e : lista) {
                JSONObject propiedad2 = new JSONObject();
                propiedad2.put("CIUDAD", e.getIdCiudad());
                propiedad2.put("DESCRIPCION_CIUDAD", e.getNombre());

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("CIUDAD", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

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

            for (Departamento e : lista) {
                JSONObject propiedad2 = new JSONObject();
                propiedad2.put("DEPARTAMENTO", e.getIdDepartamento());
                propiedad2.put("DESCRIPCION_DEPARTAMENTO", e.getNombre());

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("DEPARTAMENTO", arrayFilas);
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

            for (Pais e : lista) {
                JSONObject propiedad2 = new JSONObject();
                propiedad2.put("PAIS", e.getIdPais());
                propiedad2.put("DESCRIPCION_PAIS", e.getNombre());

                arrayFilas.add(propiedad2);
            }
            jsonRespuesta.put("PAIS", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

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
            for (Red e : lista) {
                JSONObject propiedad1 = new JSONObject();
                propiedad1.put("RED", e.getIdRed());
                propiedad1.put("DESCRIPCION_RED", e.getDescripcion());

                arrayFilas.add(propiedad1);
            }
            jsonRespuesta.put("RED", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
            /**
             * ********************************
             */
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

        } else if (op.equalsIgnoreCase("ENTIDAD_FINANCIERA")) {
            EntidadFinanciera entity = new EntidadFinanciera();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                //entity.setDescripcion(request.getParameter("query"));
                if ((request.getParameter("query").matches("[0-9]+"))) {
                    entity.setCodRef(new Short(request.getParameter("query")));
                } else {
                    entity.setDescripcion(request.getParameter("query"));
                }
            }
            //List<EntidadFinanciera> lista;
            List<Map<String, Object>> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = entidadFinancieraFacade.listAtributos(entity, false, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), new String[]{"descripcion"}, new String[]{"ASC"}, false, new String[]{"idEntidadFinanciera", "descripcion"});
            } else {
                lista = entidadFinancieraFacade.listAtributos(entity, new String[]{"idEntidadFinanciera", "descripcion"});
            }
            total = entidadFinancieraFacade.total(entity);
            JSONObject jsonRespuesta = new JSONObject();

            //for (EntidadFinanciera e : lista) {
            for (Map<String, Object> e : lista) {
                propiedad.put("ENTIDAD_FINANCIERA", e.get("idEntidadFinanciera"));
                propiedad.put("DESCRIPCION_ENTIDAD_FINANCIERA", e.get("descripcion"));

                arrayFilas.add(propiedad.clone());
            }
            jsonRespuesta.put("ENTIDAD_FINANCIERA", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
            /**
             * ********************************
             */
        } else if (op.equalsIgnoreCase("CODIGOS_IMPUESTOS")) {
            Integer limit = request.getParameter("limit") != null && !request.getParameter("limit").isEmpty() ? Integer.parseInt(request.getParameter("limit")) : 10;
            Integer start = request.getParameter("start") != null && !request.getParameter("start").isEmpty() ? Integer.parseInt(request.getParameter("start")) : 0;

            Impuesto entity = null;
            entity = new Impuesto();
            if ((request.getParameter("query") != null) && !(request.getParameter("query").isEmpty()) && (request.getParameter("query").matches("[0-9]+"))) {

                entity.setNumero(new Integer(request.getParameter("query")));

            }

            List<Impuesto> listaLog = impuestoFacade.list(entity, start, limit, "numero", "ASC");
            Integer total = impuestoFacade.total(entity);

            for (Impuesto o : listaLog) {
                propiedad.put("DESCRIPCION_IMPUESTO", o.getNumero());
                propiedad.put("IMPUESTO", o.getIdImpuesto());

                arrayFilas.add(propiedad.clone());

            }

            propiedad.clear();
            propiedad.put("IMPUESTO", arrayFilas);
            propiedad.put("success", true);
            /*
             * if (total == 1) { propiedad.put("total", 1); } else {
             * propiedad.put("total", impuestoFacade.total(entity)); }
             */
            propiedad.put("TOTAL", total);
            out.println(propiedad.toString());

        } else if (op.equalsIgnoreCase("GESTOR")) {
            Gestor entity = new Gestor();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setCedula(request.getParameter("query"));
            }
            List<Gestor> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");

            if (primerResultado != null && cantResultados != null) {
                lista = gestorFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "nombre", "ASC", true);
            } else {
                lista = gestorFacade.list(entity, true);
            }
            total = gestorFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();

            for (Gestor e : lista) {
                JSONObject objeto = new JSONObject();
                objeto.put("GESTOR", e.getCedula());
                arrayFilas.add(objeto);
            }
            jsonRespuesta.put("GESTOR", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("ENTIDAD")) {

            Entidad entity = new Entidad();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setNombre(request.getParameter("query"));
            }
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
            // JSONArray arrayFilas = new JSONArray();
            for (Entidad e : lista) {
                JSONObject objeto = new JSONObject();
                objeto.put("ENTIDAD", e.getIdEntidad());
                objeto.put("DESCRIPCION_ENTIDAD", e.getNombre());
                arrayFilas.add(objeto);
            }
            jsonRespuesta.put("ENTIDAD", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());
        } else if (op.equalsIgnoreCase("EVENTO")) {
            Evento entity = new Evento();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }
            List<Evento> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = eventoFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = eventoFacade.list(entity, true);
            }
            total = eventoFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            for (Evento e : lista) {
                JSONObject objeto = new JSONObject();
                objeto.put("EVENTO", e.getIdEvento());
                objeto.put("DESCRIPCION_EVENTO", e.getDescripcion());
                arrayFilas.add(objeto);
            }
            jsonRespuesta.put("EVENTO", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } /**
         * *******************************************************************
         */
        else if (op.equalsIgnoreCase("ICED")) {
            Iced entity = new Iced();
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            List<Iced> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = icedFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
        } else {
                lista = icedFacade.list(entity, true);
            }
            total = icedFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            for (Iced e : lista) {
                JSONObject objeto = new JSONObject();
                objeto.put("ICED", e.getIdIced());
                objeto.put("IMPORTE", e.getMonto().toString());
                objeto.put("DESCRIPCION", e.getDescripcion());
                arrayFilas.add(objeto);
            }
            jsonRespuesta.put("ICED", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        } else if (op.equalsIgnoreCase("MONEDA")) {
            Moneda entity = new Moneda();
            entity.setSoportado("S");
            if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                entity.setDescripcion(request.getParameter("query"));
            }

            List<Moneda> lista;
            int total;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");
            if (primerResultado != null && cantResultados != null) {
                lista = monedaFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
            } else {
                lista = monedaFacade.list(entity, true);
            }
            total = monedaFacade.total(entity, true);
            JSONObject jsonRespuesta = new JSONObject();
            for (Moneda e : lista) {
                JSONObject objeto = new JSONObject();
                objeto.put("MONEDA", e.getIdMoneda());                
                objeto.put("DESCRIPCION", e.getDescripcion());
                arrayFilas.add(objeto);
            }
            jsonRespuesta.put("MONEDA", arrayFilas);
            jsonRespuesta.put("TOTAL", total);
            jsonRespuesta.put("success", true);
            out.println(jsonRespuesta.toString());

        }/**
         * *******************************************************************
         */
        else {
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
