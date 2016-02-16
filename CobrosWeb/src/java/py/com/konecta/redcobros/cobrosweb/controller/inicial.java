/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;
import py.com.konecta.redcobros.ejb.FranjaHorariaDetFacade;
import py.com.konecta.redcobros.ejb.GestionFacade;
import py.com.konecta.redcobros.ejb.PermisoDeRolFacade;
import py.com.konecta.redcobros.ejb.PermisosFacade;
import py.com.konecta.redcobros.ejb.RolDeUsuarioFacade;
import py.com.konecta.redcobros.ejb.TerminalFacade;
import py.com.konecta.redcobros.ejb.UsuarioFacade;
import py.com.konecta.redcobros.ejb.UsuarioTerminalFacade;
import py.com.konecta.redcobros.entities.FranjaHorariaDet;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.Permiso;
import py.com.konecta.redcobros.entities.RolDeUsuario;
import py.com.konecta.redcobros.entities.Terminal;
import py.com.konecta.redcobros.entities.Usuario;
import py.com.konecta.redcobros.entities.UsuarioTerminal;
import py.com.konecta.redcobros.utiles.ResultadoAutenticarCobrosWeb;
import py.com.konecta.redcobros.utiles.ResultadoControlFranjaHoraria;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
public class inicial extends HttpServlet {

    @EJB
    private GestionFacade gestionFacade;
    @EJB
    private UsuarioTerminalFacade usuarioTerminalFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private TerminalFacade terminalFacade;
    @EJB
    private FranjaHorariaDetFacade franjaHorariaDetFacade;
    @EJB
    PermisoDeRolFacade permisoDeRolFacade;
    @EJB
    PermisosFacade permisoFacade;
    @EJB
    RolDeUsuarioFacade rolDeUsuarioFacade;
    @EJB
    PermisosFacade permisosFacade;

    private static final Logger logger = Logger.getLogger(inicial.class.getName());

    public JSONObject cerrarGestionesAnteriores(UsuarioTerminal ut) {
        JSONObject jsonFinal = new JSONObject();
        try {
            // gestionFacade.cerrarGestionesAbiertas(ut.getUsuario());
            jsonFinal.put("success", true);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            jsonFinal.put("success", false);
            jsonFinal.put("motivo", "No se pudo cerrar las gestiones anteriores.");
        }
        return jsonFinal;
    }

    public boolean controlFranjaHoraria(Long idTerminal, Long idUsuario) throws ParseException {
        Terminal t = terminalFacade.get(idTerminal);
        Usuario u = usuarioFacade.get(idUsuario);

        UsuarioTerminal utExample = new UsuarioTerminal();
        utExample.setUsuario(u);
        utExample.setTerminal(t);
        List<UsuarioTerminal> lUsuarioTerminal = usuarioTerminalFacade.list(utExample);

        //verificar si se encuentra en su franja horaria
        Date ahoraMismo = new Date();
        SimpleDateFormat fechaFormat = new SimpleDateFormat("ddMMyyyy HHmm");
        SimpleDateFormat horaMinutoFormat = new SimpleDateFormat("HHmm");

        SimpleDateFormat diaFormat = new SimpleDateFormat("dd");
        SimpleDateFormat mesFormat = new SimpleDateFormat("MM");
        SimpleDateFormat anhoFormat = new SimpleDateFormat("yyyy");

        Integer dia = Integer.parseInt(diaFormat.format(ahoraMismo));
        Integer mes = Integer.parseInt(mesFormat.format(ahoraMismo)) - 1;
        Integer anho = Integer.parseInt(anhoFormat.format(ahoraMismo));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, anho);
        calendar.set(Calendar.MONTH, mes);
        calendar.set(Calendar.DATE, dia);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        Boolean franjaHorariaPassed = false;

        Calendar calendarHoraIni = Calendar.getInstance();
        Calendar calendarHoraFin = Calendar.getInstance();
        Calendar calendarAhoraMismo = Calendar.getInstance();
        calendarAhoraMismo.setTime(ahoraMismo);
        calendarAhoraMismo.setTime(fechaFormat.parse("24062010 " + horaMinutoFormat.format(ahoraMismo)));
        FranjaHorariaDet fdExample = new FranjaHorariaDet();
        fdExample.setFranjaHorariaCab(lUsuarioTerminal.get(0).getFranjaHorariaCab());
        List<FranjaHorariaDet> lFranjaHorariaDetalle = franjaHorariaDetFacade.list(fdExample);

        for (FranjaHorariaDet o : lFranjaHorariaDetalle) {
            calendarHoraIni.setTime(fechaFormat.parse("24062010 " + horaMinutoFormat.format(o.getHoraIni())));
            calendarHoraFin.setTime(fechaFormat.parse("24062010 " + horaMinutoFormat.format(o.getHoraFin())));
            if ((o.getDia().intValue() == dayOfWeek) && ((calendarHoraIni.getTime().getTime() <= calendarAhoraMismo.getTime().getTime()) && (calendarHoraFin.getTime().getTime() >= calendarAhoraMismo.getTime().getTime()))) {
                franjaHorariaPassed = true;
                break;
            }
        }

        return franjaHorariaPassed;

    }

    public boolean verificarGestionesAnterioresAbiertas() {
        Boolean gAbiertas = false;
        return gAbiertas;
    }

    public static Map<String, String> getComponentesImpresion(String objetoDeImpresion) {

        Map<String, String> mapComponentesImpresion = new HashMap<String, String>();

        String respuesta2 = objetoDeImpresion.replaceAll("\n\n", ";;;");
        String[] respuestaComponentes = respuesta2.split(";;;");
        String cadenaImpresion = "";
        String ticket_pantalla = "";

        Integer modo = 1;
        for (String ooo : respuestaComponentes) {
            cadenaImpresion += modo + ";;" + "N" + ";;" + ooo + ";;;";
            ticket_pantalla += ooo + "<br/>";
        }
        mapComponentesImpresion.put("ticket_pantalla", ticket_pantalla);
        mapComponentesImpresion.put("cadena_impresion", cadenaImpresion);
        return mapComponentesImpresion;

    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

    }

    @Override
    public void destroy() {
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        JSONObject jsonFinal = new JSONObject();
        response.setContentType("text/html;charset=UTF-8");
        /*
         * Variables e inicializaciones
         */
        String viewJsp = null;
        JSONObject json = new JSONObject();

        String op = (request.getParameter("op") != null) ? request.getParameter("op") : "";
        HttpSession session = request.getSession(false);

        if ((op.equalsIgnoreCase("logeado")) && session != null) {
            //if (session.getAttributeNames().hasMoreElements()) {
            viewJsp = "/areaTrabajo.jsp";
//            } else {
//                viewJsp = "/";
//            }

            this.llamarPagina(request, response, viewJsp);
        } else if (op.equalsIgnoreCase("LOGOUT")) {
            PrintWriter out = response.getWriter();

            if (session != null) {
                UsuarioTerminal ut = (UsuarioTerminal) (session.getAttribute("objUsuarioTerminal"));
                ut.setLogueado("N");
                usuarioTerminalFacade.merge(ut);
                session.invalidate();
                session = null;
            }

            json.put("success", true);
            out.println(json.toString());
            //out.close();
        } else if (session != null && op.equalsIgnoreCase("GET_URI_IMPRESORA")) {
            PrintWriter out = response.getWriter();
            try {
                Terminal terminal = terminalFacade.get((Long) session.getAttribute("idTerminal"));
                if (terminal.getUrlImpresora() != null && !(terminal.getUrlImpresora().isEmpty())) {
                    json.put("success", true);
                    json.put("uriImpresora", terminal.getUrlImpresora());

                } else {
                    json.put("success", false);
                    json.put("motivo", "No se encontró una impresora para realizar la operación ");
                }

            } catch (Exception ex) {
                json.put("motivo", "No se pudo encontrar la terminal del usuario");
                json.put("success", false);
            }

            out.print(json.toString());
            //out.close();
        } else if (session != null && op.equalsIgnoreCase("CERRAR_GESTION")) {
            logger.info("Cerrando Gestion");
            PrintWriter out = response.getWriter();
            if (request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (session.getAttribute("contrasenha").equals(UtilesSet.getSha1(request.getParameter("PASSWORD"))))) {
                //if (usuarioFacade.esSupervisor((Integer) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                try {
                    if (request.getSession().getAttribute("objUsuarioTerminal") != null) {
                        Gestion gestion = gestionFacade.cerrarGestiones((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal"));

                        if (gestion != null) {
                            Map<String, String> mapComponentesImpresion = getComponentesImpresion(UtilesSet.getCertificacionCierre(gestion));

                            json.put("ticket_pantalla", mapComponentesImpresion.get("ticket_pantalla"));
                            json.put("ticket", mapComponentesImpresion.get("cadena_impresion"));

                            json.put("success", true);
                        } else {
                            json.put("success", false);
                            json.put("motivo", "La gestión está cerrada, debe realizar una Apertura");
                        }

                    } else {
                        json.put("success", false);
                        json.put("motivo", "No se encontró al usuario en la terminal");
                    }

                } catch (Exception ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                    json.put("motivo", "No se pudo realizar el cierre de las gestiones");
                    json.put("success", false);
                }
            } else {
                json.put("success", false);
                json.put("motivo", Utiles.CLAVE_INCORRECTA);
            }
            out.print(json.toString());
            //out.close();
        } else if (session != null && op.equalsIgnoreCase("VERIFICAR_GESTION_ABIERTA")) {
            logger.info("Verificar Gestion Abierta");
            PrintWriter out = response.getWriter();
            jsonFinal.clear();
            try {

                if (gestionFacade.tieneGestionesAbiertas(((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")), new Date())) {
                    jsonFinal.put("success", true);
                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "No tiene gestiones abiertas");
                }
            } catch (Exception e) {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", "No tiene gestiones abiertas");
            }
            out.print(jsonFinal.toString());
            //out.close();

        } else if (session != null && op.equalsIgnoreCase("ABRIR_GESTION")) {
            logger.info("Abriendo Gestion");
            Map<String, HashMap<String, String>> tasks = (Map<String, HashMap<String, String>>) session.getAttribute("TASKS");
            PrintWriter out = response.getWriter();
            jsonFinal.clear();

            if (request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (session.getAttribute("contrasenha").equals(UtilesSet.getSha1(request.getParameter("PASSWORD"))))) {
                //if (usuarioFacade.esSupervisor((Integer) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                try {
                    UsuarioTerminal ut = (UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal");

                    //Control de gestion abierta anterior, para esta terminal.
                    if (gestionFacade.tieneGestionesAbiertasDeDiasAnteriores(ut)) {
                        jsonFinal.put("tieneRegistrosAbiertosAnteriores", true);
                        jsonFinal.put("success", true);
                    } //Control de gestion abierta actual, para esta terminal.
                    else if (gestionFacade.tieneGestionesAbiertas(ut, new Date())) {
                        jsonFinal.put("tieneRegistrosAbiertosHoy", true);
                        jsonFinal.put("success", true);

                    } //Control de gestiones multiples. Estando primero significa que es excluyente, no importa si el administrador setea como permiso la apertura simple
                    else if (tasks != null && tasks.get("OPERACIONES") != null && tasks.get("OPERACIONES").get("APERTURA_MULTIPLE") != null) {
                        Gestion gestion = gestionFacade.obtenerGestionDelDia(ut.getIdUsuarioTerminal(), false);
                        request.getSession(true).setAttribute("gestion", gestion);
                        Map<String, String> mapComponentesImpresion = getComponentesImpresion(UtilesSet.getCertificacionAperturaCaja(gestion));

                        jsonFinal.put("nuevaGestionAbierta", true);
                        jsonFinal.put("ticket_pantalla", mapComponentesImpresion.get("ticket_pantalla"));
                        jsonFinal.put("ticket", mapComponentesImpresion.get("cadena_impresion"));
                        jsonFinal.put("success", true);
                    } else if (tasks != null && tasks.get("OPERACIONES") != null && tasks.get("OPERACIONES").get("APERTURA_SIMPLE") != null) {
                        try {
                            Gestion gestion = new Gestion();
                            gestion = gestionFacade.obtenerGestionDelDia(ut.getIdUsuarioTerminal(), true);
                            request.getSession(true).setAttribute("gestion", gestion);
                            Map<String, String> mapComponentesImpresion = getComponentesImpresion(UtilesSet.getCertificacionAperturaCaja(gestion));

                            jsonFinal.put("nuevaGestionAbierta", true);
                            jsonFinal.put("ticket_pantalla", mapComponentesImpresion.get("ticket_pantalla"));
                            jsonFinal.put("ticket", mapComponentesImpresion.get("cadena_impresion"));
                            jsonFinal.put("success", true);

                        } catch (Exception e) {
                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", e.getMessage());
                        }

                    } else {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "No se puede realizar la apertura");
                    }

                } catch (Exception ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                    jsonFinal.put("motivo", "No se puede realizar la apertura");
                    jsonFinal.put("success", false);
                }

            } else {

                jsonFinal.put("success", false);
                jsonFinal.put("motivo", Utiles.CLAVE_INCORRECTA);
            }

            out.print(jsonFinal.toString());
            //out.close();
        } else if (op.equalsIgnoreCase("CAMBIAR_PASSWORD")) {

            PrintWriter out = response.getWriter();
            if (request.getParameter("confirmar_new_pass") != null && (request.getParameter("old_pass") != null) && (request.getParameter("old_pass").matches("[\\w]+")) && ((request.getParameter("new_pass") != null)) && ((request.getParameter("new_pass").matches("[\\w]+")))) {

                Usuario registro = usuarioFacade.get((Long) request.getSession().getAttribute("idUsuario"));
                if (registro.getContrasenha().equals(UtilesSet.getSha1(request.getParameter("old_pass")))) {
                    if (request.getParameter("new_pass").equals(request.getParameter("confirmar_new_pass"))) {
                        registro.setContrasenha(UtilesSet.getSha1(request.getParameter("new_pass")));
                        usuarioFacade.merge(registro);
                        json.put("success", true);
                        json.put("motivo", "Actualización Exitosa");

                    } else {
                        json.put("success", false);
                        json.put("motivo", "No coinciden el campo 'Password Nuevo' y el campo 'Confirmar Password Nuevo'");
                    }
                } else {
                    json.put("success", false);
                    json.put("motivo", "No se puede realizar la operación. El password actual no coincide");
                }
            } else {

                json.put("success", false);
                json.put("motivo", "Los parametros no son validos");

            }

            out.println(json);
            //out.close();

        } else if (op.equalsIgnoreCase("login")) {
            logger.info("Login");
            try {
                String user = (request.getParameter("loginUsername").isEmpty()) ? "" : request.getParameter("loginUsername").trim();
                String password = (request.getParameter("loginPassword").isEmpty()) ? "" : request.getParameter("loginPassword").trim();

                String mac1 = (request.getParameter("mac1").isEmpty()) ? "" : request.getParameter("mac1");
                String mac2 = (request.getParameter("mac2").isEmpty()) ? "" : request.getParameter("mac2");

                String macSHA1 = UtilesSet.getSha1(mac1);
                String macSHA2 = UtilesSet.getSha1(mac2);

                if (user.matches("[\\w]+")) {
                    ResultadoAutenticarCobrosWeb login;
                    ResultadoAutenticarCobrosWeb login2;

                    login2 = usuarioTerminalFacade.auntenticar(user, UtilesSet.getSha1(password), macSHA2, "COBROS_WEB");
                    login = usuarioTerminalFacade.auntenticar(user, UtilesSet.getSha1(password), macSHA1, "COBROS_WEB");

                    if (login.getResultado().equalsIgnoreCase("OK")) {
                        Terminal t = terminalFacade.get(login.getUt().getTerminal().getIdTerminal());
                        t.setCodigoHash(macSHA2);
                        terminalFacade.merge(t);
                    }

                    if (login2.getResultado().equalsIgnoreCase("OK")) {
                        login = login2;
                    }

                    if (login.getResultado().equalsIgnoreCase("FALSE")) {
                        String motivo;
                        String terminales = "";
                        if (login.getMotivo() != null) {
                            motivo = login.getMotivo();
                        } else if (login.getLTerminalesUsuarioIn() != null) {
                            for (Terminal o : login.getLTerminalesUsuarioIn()) {
                                terminales += o.getDescripcion() + ",";
                            }
                            if (!terminales.isEmpty()) {
                                terminales = terminales.substring(0, terminales.length() - 1);
                            }
                            logger.info("Fallo Login. No tiene permisos Multiples");
                            motivo = "No tiene permiso de Logueos Múltiples. Consulte a su administrador. Se encuentra logueado en las siguientes terminales: " + terminales;
                        } else {
                            logger.info("Fallo Login. Motivo Desconocido");
                            motivo = "Fallo el login. Inténtelo de nuevo, por favor.";
                        }
                        json.put("motivo", motivo);
                        PrintWriter out = response.getWriter();
                        json.put("success", false);
                        out.print(json);
                        //out.close();

                    } else if (login.getResultado().equalsIgnoreCase("OK")) {
                        logger.info("Login Correcto");
                        PrintWriter out = response.getWriter();
                        if (login.getUt().getTerminal().getSucursal().getRecaudador().getHabilitado().equalsIgnoreCase("S")) {
                            json.put("success", true);

                            /*
                             * Se crea una variable de sesión para el usuario y
                             * los atributos de sesión.
                             */
                            Integer codigoCajaSet = login.getUt().getTerminal().getCodigoCajaSet();
                            Integer codigoSucSet = login.getUt().getTerminal().getSucursal().getCodigoSucursalSet();

                            HttpSession sessionUsuario = request.getSession(true);
                            sessionUsuario.setAttribute("nombresUsuario", login.getUt().getUsuario().getPersona().getNombres());
                            sessionUsuario.setAttribute("apellidosUsuario", login.getUt().getUsuario().getPersona().getApellidos());
                            sessionUsuario.setAttribute("user", login.getUt().getUsuario().getNombreUsuario());
                            sessionUsuario.setAttribute("idRed", login.getUt().getTerminal().getSucursal().getRecaudador().getRed().getIdRed());
                            sessionUsuario.setAttribute("idRecaudador", login.getUt().getTerminal().getSucursal().getRecaudador().getIdRecaudador());
                            sessionUsuario.setAttribute("idSucursal", login.getUt().getTerminal().getSucursal().getIdSucursal());
                            sessionUsuario.setAttribute("idTerminal", login.getUt().getTerminal().getIdTerminal());
                            sessionUsuario.setAttribute("idUsuario", login.getUt().getUsuario().getIdUsuario());

                            sessionUsuario.setAttribute("hasCodSucSET", codigoSucSet != null && codigoSucSet != 0 ? true : false);
                            sessionUsuario.setAttribute("hasCodCajaSET", codigoCajaSet != null && codigoCajaSet != 0 ? true : false);

                            sessionUsuario.setAttribute("cod_era", login.getUt().getTerminal().getSucursal().getRecaudador().getRed().getCodEra());
                            sessionUsuario.setAttribute("objUsuarioTerminal", login.getUt());
                            sessionUsuario.setAttribute("codTerminal", login.getUt().getTerminal().getCodigoTerminal().toString());
                            sessionUsuario.setAttribute("codExtUsuario", login.getUt().getUsuario().getIdUsuario());
                            sessionUsuario.setAttribute("conciliarCaja", login.getUt().getTerminal().getSucursal().getRecaudador().getConciliarCaja());

                            sessionUsuario.setAttribute("contrasenha", login.getUt().getUsuario().getContrasenha());

                            List<FranjaHorariaDet> lFranjaHorariaDet = franjaHorariaDetFacade.getDetalleFranjaHoraria(login.getUt());
                            sessionUsuario.setAttribute("listaFranjaHoraria", lFranjaHorariaDet);
                            ResultadoControlFranjaHoraria resultado = usuarioTerminalFacade.controlFranjaHorariaTimeToEnd(lFranjaHorariaDet);
                            sessionUsuario.setAttribute("timeToEndSesion", resultado.getTimeToEnd());
                            sessionUsuario.setAttribute("timeStarted", resultado.getTimeStarted());
                            Map<String, HashMap<String, String>> tasks = new HashMap<String, HashMap<String, String>>();
                            sessionUsuario.setAttribute("TASKS", tasks);
                            RolDeUsuario rolDeUSuarioExample = new RolDeUsuario();
                            rolDeUSuarioExample.setUsuario(login.getUt().getUsuario());
                            List<Permiso> permisos = permisoFacade.getPermisosSeccion(login.getUt().getUsuario().getIdUsuario(), 1L, "REPORTES");
                            if (permisos.size() > 0) {
                                HashMap<String, String> mapDecisor = new HashMap<String, String>();
                                tasks.put("REPORTES", mapDecisor);//REPORTES
                                for (Permiso p : permisos) {
                                    mapDecisor.put(p.getCodigoPermiso(), p.getCodigoPermiso());
                                }
                            }

                            permisos = permisoFacade.getPermisosSeccion(login.getUt().getUsuario().getIdUsuario(), 1L, "CONFIGURACIONES");
                            if (permisos.size() > 0) {
                                HashMap<String, String> mapDecisor = new HashMap<String, String>();
                                tasks.put("CONFIGURACIONES", mapDecisor);//CONFIGURACIONES
                                for (Permiso p : permisos) {
                                    mapDecisor.put(p.getCodigoPermiso(), p.getCodigoPermiso());
                                }
                            }

                            permisos = permisoFacade.getPermisosSeccion(login.getUt().getUsuario().getIdUsuario(), 1L, "OPERACIONES");
                            //Caso especial, porque en operaciones se encuentra Logout, que debe estar en cualquier app del sistema.
                            HashMap<String, String> mapDecisor2 = new HashMap<String, String>();
                            tasks.put("OPERACIONES", mapDecisor2);
                            for (Permiso p : permisos) {
                                mapDecisor2.put(p.getCodigoPermiso(), p.getCodigoPermiso());
                            }
                            //Seteo para permitir las llamadas a la opcion ABRIR_GESTION, cuando el usuario tiene permisos de APERTURA_SIMPLE o APERTURA_MULTIPLE
                            if ((tasks.get("OPERACIONES") != null) && ((tasks.get("OPERACIONES").get("APERTURA_SIMPLES") != null) || (tasks.get("OPERACIONES").get("APERTURA_MULTIPLE") != null))) {
                                mapDecisor2.put("ABRIR_GESTION", "ABRIR_GESTION");
                            }

                            mapDecisor2.put("LOGOUT", "LOGOUT");
                            //Permisos para Digitacion
                            permisos = permisoFacade.getPermisosSeccion(login.getUt().getUsuario().getIdUsuario(), 1L, "DIGITACION");
                            if (permisos.size() > 0) {
                                HashMap<String, String> mapDecisor = new HashMap<String, String>();
                                tasks.put("DIGITACION", mapDecisor);
                                for (Permiso p : permisos) {
                                    mapDecisor.put(p.getCodigoPermiso(), p.getCodigoPermiso());
                                }

                            }
                            //Permisos para Multicanal
                            permisos = permisoFacade.getPermisosSeccion(login.getUt().getUsuario().getIdUsuario(), 1L, "MULTICANAL");
                            if (permisos.size() > 0) {
                                HashMap<String, String> mapDecisor = new HashMap<String, String>();
                                tasks.put("MULTICANAL", mapDecisor);
                                for (Permiso p : permisos) {
                                    mapDecisor.put(p.getCodigoPermiso(), p.getCodigoPermiso());
                                }

                            }
                            //Se usa para las operaciones rutinarias.
                            //Estas son consultas se permiten siempre.
                            //Esto permite que, setear los permisos a la aplicacion no sean tan complicados para el usuario configurador
                            HashMap<String, String> mapDecisorComodines = new HashMap<String, String>();
                            mapDecisorComodines.put("logeado", "logeado");
                            mapDecisorComodines.put("GET_URI_IMPRESORA", "GET_URI_IMPRESORA");
                            mapDecisorComodines.put("GET_NEW_SERVICES", "GET_NEW_SERVICES");
                            mapDecisorComodines.put("LOGOUT", "LOGOUT");
                            mapDecisorComodines.put("VERIFICAR_GESTION_ABIERTA", "VERIFICAR_GESTION_ABIERTA");
                            mapDecisorComodines.put("BUSCAR_FORMULARIO", "BUSCAR_FORMULARIO");
                            mapDecisorComodines.put("CABECERA", "CABECERA");
                            mapDecisorComodines.put("RUC-LLENAR_CAMPOS", "RUC-LLENAR_CAMPOS");
                            mapDecisorComodines.put("VALIDAR_CABECERA", "VALIDAR_CABECERA");
                            mapDecisorComodines.put("REPORTE_ERROR_DIGITACION", "REPORTE_ERROR_DIGITACION");
                            mapDecisorComodines.put("CAMBIAR_PASSWORD", "CAMBIAR_PASSWORD");
                            mapDecisorComodines.put("DETALLADO", "DETALLADO");
                            mapDecisorComodines.put("RESUMIDO", "RESUMIDO");
                            mapDecisorComodines.put("TRANSACCION_CS", "TRANSACCION_CS");
                            mapDecisorComodines.put("CIERRE-CS", "CIERRE-CS");
                            mapDecisorComodines.put("TICKET", "TICKET");
                            mapDecisorComodines.put("SERVICIO_CS", "SERVICIO_CS");
                            mapDecisorComodines.put("REPORTES_CIERRE_USUARIOS", "REPORTES_CIERRE_USUARIOS");
                            mapDecisorComodines.put("RESUMEN-CLEARING-COMISION", "RESUMEN-CLEARING-COMISION");
                            mapDecisorComodines.put("REPORTE-GESTOR", "REPORTE-GESTOR");
                            mapDecisorComodines.put("AYUDA", "AYUDA");
                            mapDecisorComodines.put("ENTIDAD", "ENTIDAD");

                            mapDecisorComodines.put("GUARDAR", "GUARDAR");
                            mapDecisorComodines.put("DOWNLOAD_USER_FILE", "DOWNLOAD_USER_FILE");
                            mapDecisorComodines.put("SAVE_FILE", "SAVE_FILE");
                            mapDecisorComodines.put("GET_SERVICIOS", "GET_SERVICIOS");
                            mapDecisorComodines.put("RESOLVER_SERVICIO_CONSULTA", "RESOLVER_SERVICIO_CONSULTA");
                            mapDecisorComodines.put("PROCESAR_CONSULTA", "PROCESAR_CONSULTA");
                            mapDecisorComodines.put("REPORTE-CHEQUES-COBRADOS-DDJJ", "REPORTE-CHEQUES-COBRADOS-DDJJ");
                            mapDecisorComodines.put("REALIZAR_COBRANZA", "REALIZAR_COBRANZA");
                            mapDecisorComodines.put("SORTEAR", "SORTEAR");

                            mapDecisorComodines.put("RESOLVER_SERVICIO", "RESOLVER_SERVICIO");
                            mapDecisorComodines.put("PROCESAR_SERVICIO", "PROCESAR_SERVICIO");
                            mapDecisorComodines.put("SYS_INF", "SYS_INF");
                            mapDecisorComodines.put("SERVICIO_TELEFONIA", "SERVICIO_TELEFONIA");
                            mapDecisorComodines.put("TIPO_DOC", "TIPO_DOC");
                            mapDecisorComodines.put("GET_CLIENTE", "GET_CLIENTE");
                            mapDecisorComodines.put("GET_DATOS_ENVIO", "GET_DATOS_ENVIO");
                            mapDecisorComodines.put("ALTA_CLIENTE", "ALTA_CLIENTE");
                            mapDecisorComodines.put("PARAM_COMISION", "PARAM_COMISION");
                            mapDecisorComodines.put("ENVIO_REMESA", "ENVIO_REMESA");
                            mapDecisorComodines.put("RETIRO_REMESA", "RETIRO_REMESA");
                            mapDecisorComodines.put("SET_BLOQUEO_DDJJ", "SET_BLOQUEO_DDJJ");
                            mapDecisorComodines.put("GENERAR_PASE", "GENERAR_PASE");

                            /////////////////////////opciones en el servlet combos
                            mapDecisorComodines.put("TIPO_IDENTIFICADOR", "TIPO_IDENTIFICADOR");
                            mapDecisorComodines.put("GESTOR", "GESTOR");
                            mapDecisorComodines.put("DETALLE", "DETALLE");
                            mapDecisorComodines.put("LISTAR", "LISTAR");
                            mapDecisorComodines.put("LISTAR_BOLETAS", "LISTAR_BOLETAS");
                            mapDecisorComodines.put("AGREGAR", "AGREGAR");
                            mapDecisorComodines.put("BORRAR", "BORRAR");
                            mapDecisorComodines.put("APLICACIONES", "APLICACIONES");
                            mapDecisorComodines.put("ROLES", "ROLES");
                            mapDecisorComodines.put("USUARIO_PERSONA", "USUARIO_PERSONA");
                            mapDecisorComodines.put("PERMISOS", "PERMISOS");
                            mapDecisorComodines.put("CONF_USUARIO", "CONF_USUARIO");
                            mapDecisorComodines.put("RECAUDADOR", "RECAUDADOR");
                            mapDecisorComodines.put("PERSONA", "PERSONA");
                            mapDecisorComodines.put("SUCURSAL", "SUCURSAL");
                            mapDecisorComodines.put("ASIGNAR_EVENTOS", "ASIGNAR_EVENTOS");
                            mapDecisorComodines.put("GUARDAR_SUPERVISORES", "GUARDAR_SUPERVISORES");
                            mapDecisorComodines.put("LISTAR_SUPERVISORES", "LISTAR_SUPERVISORES");
                            mapDecisorComodines.put("LISTAR_EVENTOS", "LISTAR_EVENTOS");
                            mapDecisorComodines.put("TERMINAL", "TERMINAL");
                            mapDecisorComodines.put("FRANJA_HORARIA_CAB", "FRANJA_HORARIA_CAB");
                            mapDecisorComodines.put("LOCALIDAD", "LOCALIDAD");
                            mapDecisorComodines.put("CIUDAD", "CIUDAD");
                            mapDecisorComodines.put("DEPARTAMENTO", "DEPARTAMENTO");
                            mapDecisorComodines.put("PAIS", "PAIS");
                            mapDecisorComodines.put("CONSULTA_MORE_MT", "CONSULTA_MORE_MT");

                            mapDecisorComodines.put("RED", "RED");
                            mapDecisorComodines.put("FILTRAR", "FILTRAR");
                            mapDecisorComodines.put("REGISTRO_GESTION", "REGISTRO_GESTION");
                            mapDecisorComodines.put("MODIFICAR", "MODIFICAR");
                            mapDecisorComodines.put("MODIFICAR_BOLETAS", "MODIFICAR_BOLETAS");
                            mapDecisorComodines.put("DETALLE", "DETALLE");
                            mapDecisorComodines.put("DETALLE_BOLETAS", "DETALLE_BOLETAS");
                            mapDecisorComodines.put("DETALLE", "DETALLE");
                            mapDecisorComodines.put("MODIFICAR", "MODIFICAR");
                            mapDecisorComodines.put("HAS_SESSION", "HAS_SESSION");

                            mapDecisorComodines.put("REFERENCIAS", "REFERENCIAS");
                            mapDecisorComodines.put("CERRAR_GESTIONES_ANTERIORES", "CERRAR_GESTIONES_ANTERIORES");

                            mapDecisorComodines.put("ENTIDAD_FINANCIERA", "ENTIDAD_FINANCIERA");
                            mapDecisorComodines.put("CODIGOS_IMPUESTOS", "CODIGOS_IMPUESTOS");
                            mapDecisorComodines.put("USUARIO", "USUARIO");
                            mapDecisorComodines.put("GRUPOS_USUARIO_FECHA", "GRUPOS_USUARIO_FECHA");
                            mapDecisorComodines.put("REGISTRO_GESTION", "REGISTRO_GESTION");
                            mapDecisorComodines.put("GESTION", "GESTION");
                            mapDecisorComodines.put("FACTURADOR", "FACTURADOR");
                            mapDecisorComodines.put("SERVICIO", "SERVICIO");
                            mapDecisorComodines.put("TRANSACCION", "TRANSACCION");
                            mapDecisorComodines.put("FORMULARIO", "FORMULARIO");

                            ////////////////////////////////////////////////////////
                            mapDecisorComodines.put("CODIGO_REFERNCIA-LLENAR_CAMPOS", "CODIGO_REFERNCIA-LLENAR_CAMPOS");
                            // mapDecisorComodines.put("AUTORIZAR_REPORTE", "AUTORIZAR_REPORTE");
                            mapDecisorComodines.put("login", "login");
                            mapDecisorComodines.put("DETALLE_COBRO_CON_FORMULARIO", "DETALLE_COBRO_CON_FORMULARIO");
                            // mapDecisorComodines.put("DETALLE_TRANSA_A_ANULAR", "DETALLE_TRANSA_A_ANULAR");
                            mapDecisorComodines.put("VALIDAR_PERIODO_FISCAL", "VALIDAR_PERIODO_FISCAL");
                            //mapDecisorComodines.put("DETALLE_DIGITACION_A_ANULAR", "DETALLE_DIGITACION_A_ANULAR");

                            tasks.put("COMODINES", mapDecisorComodines);

                            out.print(json.toString());
                            //out.close();
                        } else {
                            logger.info("Usuario no esta habilitado");
                            json.put("success", false);
                            json.put("motivo", "Usted se encuentra inhabilitado para operar.");
                            out.print(json.toString());
                            //out.close();
                        }
                    }
                } else {
                    logger.info("Nombre de usuario no tiene la expresion regular");
                    PrintWriter out = response.getWriter();
                    json.put("success", false);
                    json.put("motivo", "Fallo el login. Inténtelo de nuevo, por favor.");
                    out.print(json);
                    //out.close();
                }

            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                PrintWriter out = response.getWriter();
                json.put("success", false);
                json.put("motivo", "Fallo el login. Problemas con la aplicación, consulte con su administrador.");
                out.print(json);
                //out.close();

            }

        } else if (op.equalsIgnoreCase("HAS_SESSION")) {
            PrintWriter out = response.getWriter();
            if (session == null) {
                json.put("success", false);
            } else {
                json.put("success", true);
            }
            out.print(json);
            //out.close();
        }
    }

    protected void llamarPagina(HttpServletRequest request, HttpServletResponse response, String viewJsp)
            throws ServletException, IOException {
        try {
            getServletConfig().getServletContext().getRequestDispatcher(viewJsp).forward(request, response);
        } catch (Exception ex) {
            getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            //Logger.getLogger(inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
