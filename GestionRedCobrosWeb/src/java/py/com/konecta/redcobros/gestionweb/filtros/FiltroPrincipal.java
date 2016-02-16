/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.filtros;

import py.com.konecta.redcobros.gestionweb.utiles.GZIPResponseWrapper;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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

/**
 *
 * @author konecta
 */
public class FiltroPrincipal implements Filter {

    private static final boolean debug = true;
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
//    private static final List<String> lsURI = new ArrayList<String>();
//
//    static {
//        lsURI.add("/GestionRedCobrosWeb/launch.jnlp");
//        lsURI.add("/GestionRedCobrosWeb/AppletInfo.jar");
//        lsURI.add("/GestionRedCobrosWeb/login.jsp");
//        lsURI.add("/GestionRedCobrosWeb/js/login.js");
////        lsURI.add("/GestionRedCobrosWeb/inicial");
//        lsURI.add("/GestionRedCobrosWeb/areaTrabajo.jsp");
//        lsURI.add("/GestionRedCobrosWeb/combos");
//        lsURI.add("/GestionRedCobrosWeb/CONTROL_FORMULARIOS");
//        lsURI.add("/GestionRedCobrosWeb/CrudTablas");
//        lsURI.add("/GestionRedCobrosWeb/CONTROL_SERVICIOS");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/window/shape_square_delete.png");
//        lsURI.add("/GestionRedCobrosWeb/");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/css/ext-all.css");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/adapter/ext/ext-base.js");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/ext-all.js");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/qtip/tip-sprite.gif");
//        lsURI.add("/GestionRedCobrosWeb/org/appletinfo/AppletInfo.class");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/qtip/tip-anchor-sprite.gif");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/window/left-corners.png");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/window/right-corners.png");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/window/top-bottom.png");
//        lsURI.add("/GestionRedCobrosWeb/images/documenta.jpg");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/window/left-right.png");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/panel/corners-sprite.gif");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/panel/white-top-bottom.gif");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/panel/top-bottom.gif");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/panel/left-right.gif");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/shadow.png");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/shadow-lr.png");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/shadow-c.png");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/form/text-bg.gif");
//        lsURI.add("/GestionRedCobrosWeb/js/ext/resources/images/default/button/btn.gif");
//    }
    private FilterConfig filterConfig = null;

    public FiltroPrincipal() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            // log("Filtro:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.

        // For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
        for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
        String name = (String)en.nextElement();
        String values[] = request.getParameterValues(name);
        int n = values.length;
        StringBuffer buf = new StringBuffer();
        buf.append(name);
        buf.append("=");
        for(int i=0; i < n; i++) {
        buf.append(values[i]);
        if (i < n-1)
        buf.append(",");
        }
        log(buf.toString());
        }
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            //log("Filtro:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.

        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed.
	/*
        for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
        String name = (String)en.nextElement();
        Object value = request.getAttribute(name);
        log("attribute: " + name + "=" + value.toString());
        
        }
         */

        // For example, a filter might append something to the response.
	/*
        PrintWriter respOut = new PrintWriter(response.getWriter());
        respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
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

        if (debug) {
            // log("Filtro:doFilter()");
        }

        doBeforeProcessing(request, response);

        /**************/
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        //System.out.println("LA VARIBLAE DE SESSION DESDE EL FILTRO " + session);
        String URI = (((HttpServletRequest) request).getRequestURI()).trim();
//        System.out.println("LA DIRECCION PRETENDIDA " + URI);
        //Logger.getLogger(FiltroPrincipal.class.getName()).log(Level.SEVERE, "URI[{0}]",URI );

//        if (URI.matches("/GestionRedCobrosWeb/login.jsp") || URI.matches("/GestionRedCobrosWeb/")) {
//        if (lsURI.contains(URI)){
        if (URI.matches("/GestionRedCobrosWeb/login.jsp")
                || URI.matches("/GestionRedCobrosWeb/js/ext/adapter/ext/ext-base.js")
                || URI.matches("/GestionRedCobrosWeb/")
                || URI.matches("/GestionRedCobrosWeb/js/validadorDetalleFormularios.js")
                || URI.matches("/GestionRedCobrosWeb/js/areaTrabajo.js")
                || URI.matches("/GestionRedCobrosWeb/js/login.js")
                || URI.matches("/GestionRedCobrosWeb/js/ext/ext-all.js")
                || URI.matches("/GestionRedCobrosWeb/js/ext/resources/css/ext-all.css")
                || URI.matches("/GestionRedCobrosWeb/AppletInfo.jar")
                || URI.matches("/GestionRedCobrosWeb/org/appletinfo/AppletInfo.class")
                || URI.matches("/GestionRedCobrosWeb/launch.jnlp")) {

            if (request instanceof HttpServletRequest) {
                HttpServletRequest hrequest = (HttpServletRequest) request;
                HttpServletResponse hresponse = (HttpServletResponse) response;
                String ae = hrequest.getHeader("accept-encoding");
                if (ae != null && ae.indexOf("gzip") != -1) {
                    //     System.out.println("GZIP SOPORTADO, COMPRIMIENDO...");
                    GZIPResponseWrapper wrappedResponse =
                            new GZIPResponseWrapper(hresponse);

                    chain.doFilter(request, wrappedResponse);
                    wrappedResponse.finishResponse();
                    return;
                }
                chain.doFilter(request, response);
            }


        } else if (session == null && (!(URI.matches("/inicial")) && !((request.getParameter("op") != null) && request.getParameter("op").equalsIgnoreCase("login"))) && (!(URI.trim().matches("/inicial")) && !((request.getParameter("op") != null) && request.getParameter("op").equalsIgnoreCase("LOGOUT")))) {

            HttpServletResponse hres = (HttpServletResponse) response;
            PrintWriter out = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("motivo", "La sesión ha caducado. Reinicie sesión");
            out.print(json);
            //out.close();
            hres.encodeRedirectURL("login.jsp");
            return;

        } else {


            Throwable problem = null;
            try {
                /************************************************************************************************/
                //   System.out.println("*****************VERIFICAANDO PERMISOS******************");
                if (verficarPermiso(session, URI, (request.getParameter("op") != null && request.getParameter("op").isEmpty()) ? request.getParameter("op") : null)) {
                    chain.doFilter(request, response);
                }

                /***********************************************************************************************/
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
        }


    }

    public boolean verficarPermiso(HttpSession session, String URI, String op) {
        // System.out.println("***************** " + session.getAttribute("LISTA_PERMISOS"));
//        Map<String, HashMap<String, String>> tasks = new HashMap<String, HashMap<String, String>>();
//        if (op != null) {
//            if (session != null) {
//                tasks = (Map<String, HashMap<String, String>>) session.getAttribute("TASKS");
//                if (tasks.get("OPERACIONES") != null) {
//                    if (tasks.get(op) != null) {
//                        return true;
//                    }
//
//                }
//                if (tasks.get("REPORTES") != null) {
//                    if (tasks.get(op) != null) {
//                        return true;
//                    }
//                }
//                if (tasks.get("DIGITACION") != null) {
//                    if (tasks.get(op) != null) {
//                        return true;
//                    }
//                }
//                return false;
//            }
//        } else {
//            return true;
//        }

        return true;
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
                //log("Filtro:Initializing filter");
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
