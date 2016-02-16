/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import py.com.konecta.redcobros.ejb.ClearingFacade;
import py.com.konecta.redcobros.ejb.ClearingFacturadorFacade;
import py.com.konecta.redcobros.ejb.ClearingManualFacade;
import py.com.konecta.redcobros.ejb.ReportesFacade;
import py.com.konecta.redcobros.ejb.UtilFacade;
import py.com.konecta.redcobros.ejb.impl.UtilFacadeImpl;
import py.com.konecta.redcobros.entities.Corte;

/**
 *
 * @author konecta
 */
public class ARCHIVO_CLEARING extends HttpServlet {
    public static String RED="RED";
    public static String SERVICIO="SERVICIO";
    public static String DESDE="DESDE";
    public static String HASTA="HASTA";
    //COMISION,FACTURACION
    public static String OPCION="OP";
    //ARCHIVO,REPORTE
    public static String ARCHIVO="ARCHIVO";
    //DEBITO,CREDITO
    public static String DEBITO_CREDITO="DEBITO_CREDITO";
    //GENERAR,VER
    public static String GENERAR_CLEARING="GENERAR_CLEARING";
    //true,false
    public static String RECALCULAR="RECALCULAR";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String red=request.getParameter(ARCHIVO_CLEARING.RED);
            String servicio=request.getParameter(ARCHIVO_CLEARING.SERVICIO);
            String desde=request.getParameter(ARCHIVO_CLEARING.DESDE);
            String hasta=request.getParameter(ARCHIVO_CLEARING.HASTA);
            String opcion=request.getParameter(ARCHIVO_CLEARING.OPCION);
            String archivo=request.getParameter(ARCHIVO_CLEARING.ARCHIVO);
            String debitoCredito=request.getParameter(ARCHIVO_CLEARING.DEBITO_CREDITO);
            SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy");
            String formatoDescarga = request.getParameter("formatoDescarga") != null && !request.getParameter("formatoDescarga").isEmpty() ? request.getParameter("formatoDescarga") : "pdf";
            Date fechaHasta=null,fechaDesde=sdf.parse(desde);
            if (hasta!=null && !hasta.isEmpty()) {
                fechaHasta=sdf.parse(hasta);
            }
            String cadena,nombreArchivo=null;
            byte[] result=null;
            String rangoFecha=desde+((fechaHasta!=null)?"-"+hasta:"");
            boolean comision=opcion.equalsIgnoreCase("COMISION");
            boolean manual=opcion.equalsIgnoreCase("MANUAL");
            boolean generarClearing=request.getParameter(ARCHIVO_CLEARING.GENERAR_CLEARING).equalsIgnoreCase("on");
            boolean reporte=archivo.equalsIgnoreCase("REPORTE");
            boolean debito;
            String rec=request.getParameter(ARCHIVO_CLEARING.RECALCULAR);
            boolean recalcular=(rec!=null&&rec.equalsIgnoreCase("true"))?true:false;
            String tipoArchivo=null;
            if (comision) {
                //clearing comision
                if (generarClearing) {
                    this.clearingFacade.realizarClearing(fechaDesde, fechaHasta, new Long(red), servicio, recalcular);
                    if (reporte) {
                        //reporte
                        tipoArchivo=formatoDescarga;
                        result=this.reportesF.reporteClearing(new Long(red), servicio, fechaDesde, fechaHasta, formatoDescarga);
                        nombreArchivo ="COMISION_"+servicio+"_"+rangoFecha+"."+formatoDescarga;
                    } else {
                        //archivo de texto de clearing
                        tipoArchivo="text";
                        debito=debitoCredito.equalsIgnoreCase("DEBITO");
                    }
                    reportes.procesarResultadoReportes(result,
                        nombreArchivo,
                        tipoArchivo,
                        response);
                }
            } else if (manual) {
                //generacion manual
                debito=debitoCredito.equalsIgnoreCase("DEBITO");
                nombreArchivo = (debito?"DB":"CR")+"_"+desde+".txt";
                List<Map<String,Object>> lista;
                lista=this.clearingManualFacade.listaReporte(fechaDesde);
                cadena = this.utilFacade.generarArchivoClearing(new Long(red), true, debito, lista,manual, null);
                result=cadena.getBytes();
                tipoArchivo="text";
                reportes.procesarResultadoReportes(result,
                    nombreArchivo,
                    tipoArchivo,
                    response);
            } else {
                //clearing facturador
                if (generarClearing) {
                    this.clearingFacturadorFacade.generarClearingFacturador(fechaDesde, reporte?fechaHasta:null, new Long(red), null, recalcular);
                    List<Map<String,Object>> lista;
                    if (reporte) {
                        //reporte
                        nombreArchivo = "CLEARING_FACTURADOR"+"_"+rangoFecha+"."+formatoDescarga;
                        lista=this.clearingFacturadorFacade.listaReporte(fechaDesde,fechaHasta, new Long(red), null);
                        result=this.reportesF.reporteClearingFacturador(lista,new Long(red),
                                rangoFecha, formatoDescarga);
                        tipoArchivo=formatoDescarga;
                        //LLAMAR AL REPORTE
                    } else {
                        //archivo de texto de clearing
                        debito=debitoCredito.equalsIgnoreCase("DEBITO");
                        nombreArchivo = desde+"_IMP_"+(debito?"DB":"CR")+".txt";
                        //obtener corte
                        Corte cut = this.clearingFacturadorFacade.obtenerCorte();
                        lista=this.clearingFacturadorFacade.listaReporte(fechaDesde,null, new Long(red), null);
                        cadena = this.utilFacade.generarArchivoClearing(new Long(red), comision, debito, lista, cut);
                        //vamos a concatenarle lo del clearing manual para esta fecha, si es q hay
                        lista=this.clearingManualFacade.listaReporte(fechaDesde);
                        if (lista.size()>0) {
                            cadena += this.utilFacade.generarArchivoClearing(new Long(red), true, debito, lista,true, cut);
                        }
                        result=cadena.getBytes();
                        tipoArchivo="text";
                    }
                    reportes.procesarResultadoReportes(result,
                        nombreArchivo,
                        tipoArchivo,
                        response);
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            Logger.getLogger(ARCHIVO_CLEARING.class.getName()).log(Level.SEVERE, null,exc);
        }
    }
    @EJB
    private ClearingFacade clearingFacade;
    @EJB
    private ReportesFacade reportesF;
    @EJB
    private UtilFacade utilFacade;
    @EJB
    private ClearingFacturadorFacade clearingFacturadorFacade;
    @EJB
    private ClearingManualFacade clearingManualFacade;

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
