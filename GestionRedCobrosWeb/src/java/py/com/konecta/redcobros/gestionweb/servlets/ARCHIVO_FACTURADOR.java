/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.DatoConsultaFacade;
import py.com.konecta.redcobros.ejb.ServicioRcFacade;
import py.com.konecta.redcobros.entities.ServicioRc;
import py.com.konecta.redcobros.gestionweb.interfaces.IServiceFile;
import py.com.konecta.redcobros.gestionweb.utiles.PluginUtil;

/**
 *
 * @author documenta
 */
public class ARCHIVO_FACTURADOR extends HttpServlet {

    @EJB
    private ServicioRcFacade servicioRcFacade;
    @EJB
    private DatoConsultaFacade datoConsultaFacade;

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
        PrintWriter out = response.getWriter();
        try {
            JSONObject jsonRespuesta = new JSONObject();
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            String opcion = request.getParameter("op");
            Integer idServicio = request.getParameter("ID_SERVICIO") != null && !request.getParameter("ID_SERVICIO").isEmpty() ? Integer.valueOf(request.getParameter("ID_SERVICIO")) : null;
            ServicioRc servicioRc = servicioRcFacade.get(idServicio);
            boolean ok = false;
            Integer cantRegistros = 0;
            String descripcion = "";
            if (opcion.equalsIgnoreCase("UPLOAD")) {
                if (isMultipart) {
                    IServiceFile isp = PluginUtil.getComplemento(servicioRc, servicioRcFacade.getEm());
                    if (isp != null) {
                        DiskFileItemFactory factory = new DiskFileItemFactory();
                        ServletFileUpload upload = new ServletFileUpload(factory);
                        List items = upload.parseRequest(request);
                        Iterator iter = items.iterator();
                        while (iter.hasNext()) {
                            FileItem item = (FileItem) iter.next();
                            ok = isp.parser((Object)item);
                            break;
                        }
                        descripcion = isp.getDescripcion();
                        if (ok) {
                            descripcion = String.format("Procesado correctamente\nFilas insertadas: %s", isp.getCantRegistros().toString() );
                        }
                    }else{
                        descripcion = "El servicio no posee archivo de cobranza";
                    }
                }
            }
            if (ok) {
                jsonRespuesta.put("success", true);
                jsonRespuesta.put("motivo", descripcion);
            } else {
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", descripcion);
            }
            out.println(jsonRespuesta.toString());

        } catch (Exception ex) {
            Logger.getLogger(ARCHIVO_FACTURADOR.class.getName()).log(Level.SEVERE, null, ex);
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
