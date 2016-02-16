/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author konecta
 */
public class BAJAR_ARCHIVO extends HttpServlet {
   
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

        JSONObject json = new JSONObject();

        //get the filename from the "file" parameter
        //System.out.println("\t\t************* DOWNLOAD: " + request.getParameter("archivo_generado"));
        String fileName = (String) request.getParameter("archivo_generado");
        if (fileName == null || fileName.equals("")) {
            throw new ServletException("Invalido o no existen todos los parametros en la llamada al servlet.");
        }
        ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        try {

            stream = response.getOutputStream();

            File pdf = new File(request.getParameter("archivo_generado"));

            //set response headers

            response.setContentType("application/octet-stream");
            response.addHeader("Pragma", " public");
            response.addHeader("Content-type", "application/force-download");
            response.addHeader("Content-Transfer-Encoding", "Binary");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

            response.setContentLength((int) pdf.length());

            FileInputStream input = new FileInputStream(pdf);
            buf = new BufferedInputStream(input);
            int readBytes = 0;

            //read from the file; write to the ServletOutputStream
            while ((readBytes = buf.read()) != -1) {
                stream.write(readBytes);
            }
        } catch (IOException ioe) {
            //throw new ServletException(ioe.getMessage( ));
        } finally {

            //close the input/output streams
            if (stream != null) {
                stream.close();
            }
            if (buf != null) {
                buf.close();
            }
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
