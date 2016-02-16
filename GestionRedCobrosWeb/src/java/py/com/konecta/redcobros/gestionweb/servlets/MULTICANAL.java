/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.gestionweb.utiles.Utiles;

/**
 *
 * @author konecta
 */
public class MULTICANAL extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String opcion = request.getParameter("op");

            if (opcion.equalsIgnoreCase("LISTAR")) {
                //LISTAR!
            }else if (opcion.equalsIgnoreCase("MULTICANAL_LEVANTAR_ARCHIVO_FACTURACION")) {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                if (isMultipart) {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();

                        //LLAMAR AL FACADE PARA REALIZAR LOS INSERTS...
                    }
                }
            } else if (opcion.equalsIgnoreCase("MULTICANAL_GENERAR_ARCHIVO_COBROS")) {
                    Long tipoPago = 1L;
                    StringBuffer cadena = new StringBuffer();
                    String nombreArchivo ="";

                    //Llamar al facade que hace la operacion.
                    //cadena = this.utilFacade.generarArchivoERABoletaPago(new TipoPago(tipoPago), idRed, fecha);
                   PrintWriter escribir = new PrintWriter(
                            new BufferedWriter(new FileWriter(nombreArchivo)));
                    escribir.print(cadena);
                    escribir.close();
                    //Enviar el resulatado a la interfaz
                   

                 //  procesarResultadoReportes(result, "BP-ORDEN-" + numeroOrden + ".pdf", response);
                }else {
                JSONObject jsonRespuesta = new JSONObject();
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la operacion");
                out.println(jsonRespuesta.toString());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            // jsonRespuesta.put("motivo", exc.getMessage());
            jsonRespuesta.put("motivo", Utiles.DEFAULT_ERROR_MESSAGE);
            out.println(jsonRespuesta.toString());
        } finally {
           //out.close();
        }
    }
 public static void procesarResultadoReportes(byte[] result, String fileName, HttpServletResponse response) throws IOException {

        if (result != null) {
            response.setContentType("application/pdf");
            response.addHeader(
                    "Content-Disposition",
                    "attachment; filename=" + fileName);
            response.getOutputStream().write(result);
            response.getOutputStream().close();
        } else {
            PrintWriter out = response.getWriter();
            out.print("<script> parent.alertNoExisteResuladoReporte();</script>");
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
