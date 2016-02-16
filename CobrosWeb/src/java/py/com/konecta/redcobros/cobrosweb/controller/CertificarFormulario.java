/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;
import py.com.konecta.redcobros.ejb.FormContribFacade;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
public class CertificarFormulario extends HttpServlet {

    @EJB
    FormContribFacade formContribFacade;

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public static String certificarFormulario(HttpServletRequest request) throws Exception {

        JSONObject jsonFinal = new JSONObject();
        long idFormContrib = new Long(request.getParameter("idFormContrib"));

        Long idRed = (Long) request.getSession().getAttribute("idRed");
        Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
        Long idTerminal = (Long) request.getSession().getAttribute("idTerminal");
        Integer codEra = (Integer) request.getSession().getAttribute("cod_era");
        Long idSucursal = (Long) request.getSession().getAttribute("idSucursal");
        //Armar la certificacion
        try {
            UtilesSet utilesSet = new UtilesSet();
            String cadenaCertificacion = utilesSet.generarCertificacionFormulario(idFormContrib, idUsuario, idTerminal, idRed, codEra, idSucursal);
            //Certificacion
            if (!"ERROR".equalsIgnoreCase(cadenaCertificacion)) {
                String respuesta2 = cadenaCertificacion.replaceAll("\n\n", ";;;");
                String[] respuestaComponentes = respuesta2.split(";;;");
                String impresionCertificacion = "";
                String impresionTicket = "";
                String ticket_pantalla = "";
                Integer modo = 2;
                for (String ooo : respuestaComponentes) {
                    impresionCertificacion += modo + ";;" + "N" + ";;" + ooo + ";;;";
                    impresionTicket += "1" + ";;" + "N" + ";;" + ooo + ";;;";
                    ticket_pantalla += ooo + "<br/>";
                }
                jsonFinal.put("ticket_pantalla", ticket_pantalla);
                jsonFinal.put("ticket", impresionCertificacion);
                jsonFinal.put("ticketTMU", impresionTicket);
                jsonFinal.put("success", true);
            } else {
                jsonFinal.put("success", true);
                jsonFinal.put("motivo", "Error en el proceso");
            }
        } catch (Exception ex) {
            Logger.getLogger(UtilesSet.class.getName()).log(Level.SEVERE, null, ex);
            jsonFinal.put("success", false);
            jsonFinal.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
        }
        return jsonFinal.toString();
        /**
         * ****************Antes de pasar a UtilesSet********************
         */
//        JSONObject jsonFinal = new JSONObject();
//        long idFormContrib = new Long(request.getParameter("idFormContrib"));
//        Context context = new InitialContext();
//        FormContribFacade formContribFacade = (FormContribFacade) context.lookup(FormContribFacade.class.getName());
//        TerminalFacade terminalFacade = (TerminalFacade) context.lookup(TerminalFacade.class.getName());
//        UsuarioTerminalFacade usuarioTerminalFacade = (UsuarioTerminalFacade) context.lookup(UsuarioTerminalFacade.class.getName());
//        ImpuestoFacade impuestoFacade = (ImpuestoFacade) context.lookup(ImpuestoFacade.class.getName());
//        FormularioImpuestoFacade fiFacade = (FormularioImpuestoFacade) context.lookup(FormularioImpuestoFacade.class.getName());
//        Long idRed = (Long) request.getSession().getAttribute("idRed");
//        //Armar la certificacion
//        try {
//            Terminal terminal = terminalFacade.get((Long) request.getSession().getAttribute("idTerminal"));
//            UsuarioTerminal us = new UsuarioTerminal();
//            us.setUsuario(new Usuario((Long) request.getSession().getAttribute("idUsuario")));
//            us.setTerminal(terminal);
//            us = usuarioTerminalFacade.list(us).get(0);
//            FormContrib formContrib = formContribFacade.certificarFormulario(idFormContrib,
//                    (Integer) request.getSession().getAttribute("cod_era"),
//                    us,
//                    idRed);
//            FormularioImpuesto fi = new FormularioImpuesto();
//            fi.setNumeroFormulario(formContrib.getFormulario().getNumeroFormulario());
//            fi = fiFacade.get(fi);
//            Impuesto impuesto = new Impuesto();
//            impuesto.setNumero(fi.getImpuesto());
//            impuesto = impuestoFacade.get(impuesto);
//
//
//            String cabeceraCertificacion = new String();
//            String bodyCertificacion = new String();
//            String pieCertificacion = new String();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
//
//            bodyCertificacion+=formContrib.getUsuarioTerminal().getTerminal().getSucursal().getRecaudador().getDescripcion() +" RUC:"+formContrib.getUsuarioTerminal().getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi()+"\n\n";
//            bodyCertificacion += "Nro. Orden:" + formContrib.getNumeroOrden() + " RUC:" + formContrib.getRuc() + "-" + formContrib.getDigitoVerificador() + "\n\n";
//            bodyCertificacion += "Fecha y Hora " + formatter.format(new Date()) + "\n\n";
//           // System.out.println("*** EL GRUPO "+formContrib.getGrupo());
//            // System.out.println("*** LA GESTION "+formContrib.getGrupo().getGestion());
//            bodyCertificacion += "ERA: " + request.getSession().getAttribute("cod_era");
//            bodyCertificacion += " Suc. SET:" + formContrib.getGrupo().getGestion().getTerminal().getSucursal().getCodigoSucursalSet();
//            //bodyCertificacion += " Caja SET:" + us.getTerminal().getCodigoCajaSet();
////            String recaudadorDescripcion = new String();
////            if (terminal.getSucursal().getRecaudador().getDescripcion().length() > 4) {
////                recaudadorDescripcion = terminal.getSucursal().getRecaudador().getDescripcion().substring(0, 4) + ".";
////            } else {
////                recaudadorDescripcion = terminal.getSucursal().getRecaudador().getDescripcion() + ".";
////            }
//           // bodyCertificacion += " Term.:" + terminal.getCodigoTerminal() +" Caja SET: "+terminal.getCodigoCajaSet();
//            bodyCertificacion += " Val.:" + us.getUsuario().getCodigo() + "\n\n";
//            bodyCertificacion += "Hash: " + formContrib.getCodigoHash();
//            bodyCertificacion += " Consecutivo:" + formContrib.getConsecutivo() + "\n\n";
//            bodyCertificacion += "Periodo:" + formContrib.getPeriodoFiscal();
//            bodyCertificacion += " Formul:" + formContrib.getFormulario().getNumeroFormulario() + "\n\n";
//            pieCertificacion = "\"No es Valido como Comprobante de Pago\"" + "\n\n";
//
//
//            String certificacion = cabeceraCertificacion + bodyCertificacion + pieCertificacion;
//            //Certificacion
//            String respuesta2 = certificacion.replaceAll("\n\n", ";;;");
//            String[] respuestaComponentes = respuesta2.split(";;;");
//            String cadenaImpresion = "";
//            String ticket_pantalla = "";
//
//            Integer modo = 2;
//            for (String ooo : respuestaComponentes) {
//                System.out.println(ooo);
//                cadenaImpresion += modo + ";;" + "N" + ";;" + ooo + ";;;";
//                ticket_pantalla += ooo + "<br/>";
//            }
//            jsonFinal.put("ticket_pantalla", ticket_pantalla);
//            jsonFinal.put("ticket", cadenaImpresion);
//            jsonFinal.put("success", true);
//
//
//        } catch (Exception ex) {
//            jsonFinal.put("success", false);
//            jsonFinal.put("motivo", ex.getMessage());
//
//        }
//
//        return jsonFinal.toString();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(certificarFormulario(request));
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        } finally {
            //out.close();
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
        } catch (NamingException ex) {
            Logger.getLogger(CertificarFormulario.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NamingException ex) {
            Logger.getLogger(CertificarFormulario.class.getName()).log(Level.SEVERE, null, ex);
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
