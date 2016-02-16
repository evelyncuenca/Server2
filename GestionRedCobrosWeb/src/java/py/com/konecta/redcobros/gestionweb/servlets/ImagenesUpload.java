/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import py.com.konecta.redcobros.ejb.ServicioRcFacade;
import py.com.konecta.redcobros.entities.ServicioRc;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
public class ImagenesUpload extends HttpServlet {

    private static final long FILE_SIZE_LIMIT = 20 * 1024 * 1024; // 20 MiB

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    ServicioRcFacade servicioRcFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        try {
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
            fileUpload.setSizeMax(FILE_SIZE_LIMIT);
            List<FileItem> items = fileUpload.parseRequest(request);
            Integer id = null;
            byte[] archivo = null;
            String nombreArchivo = null;
            for (FileItem item : items) {

                if (item.isFormField()) {
                    System.out.println("Recibido del Formulario:");
                    System.out.println("Nombre: " + item.getFieldName());
                    System.out.println("Valor: " + item.getString());
                    if (item.getFieldName().equals("idComboServicio")) {
                        String idComboServicio = item.getString();
                        List<ServicioRc> list = (List<ServicioRc>) request.getSession().getAttribute("servicios");
                        for (ServicioRc servicio : list) {
                            if (idComboServicio.equals(servicio.getDescripcion())) {
                                id = servicio.getIdServicio();
                            }
                        }
                    }
                } else {
                    System.out.println("Archivo Recibido:");
                    System.out.println("Nombre: " + item.getName());
                    System.out.println("Tamaño: " + item.getSize());
                    if (item.getSize() > FILE_SIZE_LIMIT) {
                        response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "El archivo excede el limite");
                        return;
                    }
                    if (item.getFieldName().equals("archivoIMG")) {
                        archivo = item.get();
                        nombreArchivo = item.getName();
                        try {
                        } catch (Exception e) {
                            Logger.getLogger(ImagenesUpload.class.getName()).log(Level.SEVERE, null, e);
                            json.put("success", false);
                        }
                    }
                    if (!item.isInMemory()) {
                        item.delete();
                    }
                }
            }

            if (id == null || archivo == null || nombreArchivo == null) {
                json.put("success", false);
            } else {
                ServicioRc servicioRC = servicioRcFacade.get(id);
                servicioRC.setNombreImagen(nombreArchivo);
                servicioRC.setImagen(archivo);
                servicioRcFacade.update(servicioRC);
                json.put("success", true);
            }
            response.setContentType("text/html;charset=UTF-8");
            out.println(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
