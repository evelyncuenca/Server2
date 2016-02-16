/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import py.com.konecta.redcobros.ejb.CampoFacade;
import py.com.konecta.redcobros.ejb.ContribuyentesFacade;
import py.com.konecta.redcobros.ejb.FormularioFacade;
import py.com.konecta.redcobros.ejb.FormularioImpuestoFacade;
import py.com.konecta.redcobros.ejb.ImpuestoFacade;
import py.com.konecta.redcobros.ejb.ParamTasasFacade;
import py.com.konecta.redcobros.ejb.ParamVencimientosFacade;
import py.com.konecta.redcobros.ejb.ParametrosFacade;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author gustavo
 */
public class LECTOR_ARCHIVOS extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private FormularioFacade formularioFacade;
    @EJB
    private CampoFacade campoFacade;
    @EJB
    private ParametrosFacade parametrosFacade;
    @EJB
    private ParamVencimientosFacade paramVencimientosFacade;
    @EJB
    private ParamTasasFacade paramTasasFacade;
    @EJB
    private ImpuestoFacade impuestoFacade;
    @EJB
    private FormularioImpuestoFacade formularioImpuestoFacade;
    @EJB
    private ContribuyentesFacade contribuyentesFacade;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                
                //los parametros 
                String archivo=request.getParameter("combo1");
                String formatoFecha=request.getParameter("combo2");
                int formatoFechaInt;
                if (formatoFecha.equals("DD/MM/YYYY")) {
                    formatoFechaInt=LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY;
                } else {
                    formatoFechaInt=LectorConfiguracionSet.FORMATO_FECHA_MMDDYYYY;
                }
                String separador=request.getParameter("combo3");
                if (separador.equals("TAB")) {
                    separador=LectorConfiguracionSet.SEPARADOR_TOKENS_TAB;
                } else if (separador.equals("COMA")) {
                    separador=LectorConfiguracionSet.SEPARADOR_TOKENS_COMA;
                } else {
                    separador=LectorConfiguracionSet.SEPARADOR_TOKENS_PUNTO_COMA;
                }
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (archivo.equals("FORMULARIO")) {
                        this.formularioFacade.insertarFormularios(item, separador, formatoFechaInt, null, null);
                    } else if (archivo.equals("CAMPO")) {
                        this.campoFacade.capturarCampos(item, separador, null, null);
                    } else if (archivo.equals("IMPUESTO")) {
                        this.impuestoFacade.insertarImpueso(item, separador, null);
                    } else if (archivo.equals("FORM_IMP")) {
                        this.formularioImpuestoFacade.insertarFormularioImpuesto(item, separador, null);
                    } else if (archivo.equals("PARAMETROS")) {
                        this.parametrosFacade.insertarTasasInteresesContravencionesMultas(item, separador, formatoFechaInt);
                    } else if (archivo.equals("TASAS_FORM")) {
                        this.paramTasasFacade.insertarTasasFormulario(item, separador, formatoFechaInt, null);
                    } else if (archivo.equals("VENCIMIENTOS")) {
                        this.paramVencimientosFacade.insertarVencimientos(item, separador, formatoFechaInt, null);
                    } else if (archivo.equals("CONTRIBUYENTE")) {
                        this.contribuyentesFacade.actualizarContribuyentes(item,separador);
                    } else {
                        out.write("{success:false}");
                    }
                    
                    break;//Porque los demas ya no son archivos!
                }
                out.write("{success:true}");
            } else {
                out.write("{success:false}");
            }
        } catch (Exception e) {
            out.write("{success:false}");
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
