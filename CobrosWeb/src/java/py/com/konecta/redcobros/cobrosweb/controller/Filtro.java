/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.cobrosweb.utiles.RespuestaFiltro;

/**
 *
 * @author konecta
 */
public class Filtro implements Filter {

    private static final boolean debug = true;
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public Filtro() {
    }

    private boolean entraFranjaHoraria(Long timeFin) {
        return timeFin > (new Date().getTime() / 1000);
    }

    public RespuestaFiltro verficarPermiso(HttpSession session, String URI, String op) throws ParseException {

        Map<String, HashMap<String, String>> tasks = (Map<String, HashMap<String, String>>) session.getAttribute("TASKS");

        RespuestaFiltro respuesta = new RespuestaFiltro();

        if (op != null && tasks != null) {
            //List<FranjaHorariaDet> lFranjaHorariaDet = (List<FranjaHorariaDet>) session.getAttribute("listaFranjaHoraria");
            Long timeStarted = (Long) session.getAttribute("timeStarted");
            Long timeToEnd = (Long) session.getAttribute("timeToEndSesion");

            Boolean entraEnFranjaHoraria = entraFranjaHoraria(timeStarted + timeToEnd);//usuarioTerminalFacade.controlFranjaHoraria(lFranjaHorariaDet);

            //Primero se controla si el pedido esta en los comodines. De ser asi son operaciones permitidas siempre. Caso contrario se debe realizar control de franja horaria.
            if (tasks.get("COMODINES") != null) {
                if (tasks.get("COMODINES").get(op) != null && !tasks.get("COMODINES").get(op).equalsIgnoreCase("HAS_SESSION")) {
                    respuesta.setSuccess(true);
                    return respuesta;
                }
            }
            if (!entraEnFranjaHoraria) {

                respuesta.setSuccess(false);
                respuesta.setMotivo("Usted no puede realizar la operación en este horario. La sesión se cerrará en 5 segs...");
                return respuesta;
            }
            if (tasks.get("COMODINES") != null && tasks.get("COMODINES").get(op) != null) {
                if (tasks.get("COMODINES").get(op).equalsIgnoreCase("HAS_SESSION")) {
                    respuesta.setSuccess(true);
                    return respuesta;
                }
            }
            if (tasks.get("OPERACIONES") != null) {
                if (tasks.get("OPERACIONES").get(op) != null) {
                    respuesta.setSuccess(true);
                    return respuesta;

                }
            }
            if (tasks.get("REPORTES") != null) {
                if (tasks.get("REPORTES").get(op) != null) {
                    respuesta.setSuccess(true);
                    return respuesta;

                }
            }
            if (tasks.get("CONFIGURACIONES") != null) {
                if (tasks.get("CONFIGURACIONES").get(op) != null) {
                    respuesta.setSuccess(true);
                    return respuesta;

                }
            }
            if (tasks.get("DIGITACION") != null) {
                if (tasks.get("DIGITACION").get(op) != null) {
                    respuesta.setSuccess(true);
                    return respuesta;

                }
            }
            respuesta.setSuccess(false);
            return respuesta;

        } else {
            respuesta.setSuccess(true);
            return respuesta;

        }
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            // log("Filtro:DoBeforeProcessing");
        }


    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            //log("Filtro:DoAfterProcessing");
        }


    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

//        if (debug) {
//            //log("Filtro:doFilter()");
//        }
//
//        doBeforeProcessing(request, response);

        /**
         * ***********
         */
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        String URI = (((HttpServletRequest) request).getRequestURI()).trim();
        //Logger.getLogger(Filtro.class.getName()).log(Level.INFO, "SESSION[{0}]---URI[{1}]",new Object[]{session,URI});

        //if (URI.matches("/CobrosWeb/login.jsp") || URI.matches("/CobrosWeb/")) {
        if (URI.matches("/CobrosWeb/login.jsp")
                || URI.matches("/CobrosWeb/js/ext/adapter/ext/ext-base.js")
                || URI.matches("/CobrosWeb/")
                || URI.matches("/CobrosWeb/js/validadorDetalleFormularios.js")
                || URI.matches("/CobrosWeb/js/areaTrabajo.js")
                || URI.matches("/CobrosWeb/js/login.js")
                || URI.matches("/CobrosWeb/js/ext/ext-all.js")
                || URI.matches("/CobrosWeb/js/ext/resources/css/ext-all.css")
                || URI.matches("/CobrosWeb/AppletInfo.jar")
                || URI.matches("/CobrosWeb/AppletGenBarCode.jar")
                || URI.matches("/CobrosWeb/launch.jnlp")) {

            if (request instanceof HttpServletRequest) {
                HttpServletRequest hrequest = (HttpServletRequest) request;
                HttpServletResponse hresponse = (HttpServletResponse) response;
                String ae = hrequest.getHeader("accept-encoding");
                if (ae != null && ae.indexOf("gzip") != -1) {

                    GZIPResponseWrapper wrappedResponse =
                            new GZIPResponseWrapper(hresponse);
                    //System.out.pritln(" *** "+response);
                    chain.doFilter(request, wrappedResponse);
                    wrappedResponse.finishResponse();
                    return;
                }
                chain.doFilter(request, response);
            }

        /*} else if (session != null && !session.getAttributeNames().hasMoreElements()
                && URI.matches("/CobrosWeb/inicial") && ((request.getParameter("op") != null) && request.getParameter("op").equalsIgnoreCase("logeado"))
                || (request.getParameter("op") != null) && request.getParameter("op").equalsIgnoreCase("VERIFICAR_GESTION_ABIERTA")
                || (request.getParameter("op") != null) && request.getParameter("op").equalsIgnoreCase("GET_URI_IMPRESORA")) {
            
            HttpServletResponse hres = (HttpServletResponse) response;            
            String urlWithSessionID = hres.encodeRedirectURL("/CobrosWeb/login.jsp");
            hres.sendRedirect(urlWithSessionID);
         */   
        } else if (session == null
                && (!(URI.matches("/inicial")) && !((request.getParameter("op") != null) && request.getParameter("op").equalsIgnoreCase("login")))
                && (!(URI.trim().matches("/inicial")) && !((request.getParameter("op") != null) && request.getParameter("op").equalsIgnoreCase("LOGOUT")))) {
            JSONObject json = new JSONObject();
            if (request.getParameter("op") != null && request.getParameter("op").equalsIgnoreCase("HAS_SESSION")) {
                PrintWriter out = response.getWriter();
                Logger.getLogger(Filtro.class.getName()).log(Level.INFO, "LA SESION HA CADUCADO");
                json.put("success", false);
                out.print(json);
            } else {
                HttpServletResponse hres = (HttpServletResponse) response;
                String urlWithSessionID = hres.encodeRedirectURL("/CobrosWeb/login.jsp");
                hres.sendRedirect(urlWithSessionID);
                return;
            }
        } else if (session != null) {
            /**
             * ************
             */
            Throwable problem = null;
            try {
                /**
                 * *********************************************************************************************
                 */
                RespuestaFiltro respuestaFiltro = new RespuestaFiltro();

                respuestaFiltro = verficarPermiso(session, URI, (request.getParameter("op") != null && !request.getParameter("op").isEmpty()) ? request.getParameter("op") : null);
                // System.out.println("LA RESPUESTA DESDE EL FILTRO " + respuestaFiltro.getSuccess() + " motivo " + respuestaFiltro.getMotivo());
                if (respuestaFiltro.getSuccess()) {

                    chain.doFilter(request, response);
                } else {
                    JSONObject json = new JSONObject();
                    String motivo = "";
                    String tipo = "0";//Tipo de mensaje sin timer
                    if (respuestaFiltro.getMotivo() != null) {
                        motivo = respuestaFiltro.getMotivo();
                        tipo = "1"; //Tipo de mensaje con timer
                    } else {
                        motivo = "No tiene permiso para realizar la operación";
                    }

                    json.put("success", false);
                    json.put("motivo", motivo);
                    json.put("tipo", tipo);

                    PrintWriter out = response.getWriter();
                    out.print(json);
                    System.out.println("NO ENTRA AL SISTEMA");
                }


                /**
                 * ********************************************************************************************
                 */
            } catch (Throwable t) {
                // If an exception is thrown somewhere down the filter chain,
                // we still want to execute our after processing, and then
                // rethrow the problem after that.
                problem = t;
                t.printStackTrace();
            }

            doAfterProcessing(request, response);

            // If there was a problem, we want to rethrow it if it is
            // a known type, otherwise log it.
            if (problem != null) {
                if (problem instanceof ServletException) {
                    throw (ServletException) problem;
                }
                if (problem instanceof IOException) {
                    throw (IOException) problem;
                }
                sendProcessingError(problem, response);
            }
        } else {
            chain.doFilter(request, response);
        }


    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                // log("Filtro:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("Filtro()");
        }
        StringBuffer sb = new StringBuffer("Filtro(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
