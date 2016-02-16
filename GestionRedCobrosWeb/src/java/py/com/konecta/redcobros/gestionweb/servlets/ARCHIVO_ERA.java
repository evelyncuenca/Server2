/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.GrupoFacade;
import py.com.konecta.redcobros.ejb.RedFacade;
import py.com.konecta.redcobros.ejb.UtilFacade;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.TipoPago;
import py.com.konecta.redcobros.utiles.Constantes;
import py.com.konecta.redcobros.utiles.UtilesSet;
import py.com.konecta.redcobros.gestionweb.web.Zipear;

/**
 *
 * @author konecta
 */
public class ARCHIVO_ERA extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static String PATH_ARCHIVO = "";
    @EJB
    private UtilFacade utilFacade;
    @EJB
    private RedFacade redFacade;
    @EJB
    private GrupoFacade grupoFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        Integer codEra = null;
        String op = request.getParameter("op");
        Long idRed = null;
        Date fecha = null;
        //Date fechaActual = new Date();
        String cadena = null;
        //Format formatter = new SimpleDateFormat("ddMMyyyyHHmmSS");
        SimpleDateFormat parseDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat parseDateSinBarra = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat parseDateConHoraSinBarra = new SimpleDateFormat("ddMMyyyyHHmmss");
        JSONObject jsonRespuesta = new JSONObject();
        Red red = null;
        Boolean valido = false;

        if (request.getParameter("FECHA") != null && !request.getParameter("FECHA").isEmpty() && request.getParameter("RED") != null && !request.getParameter("RED").isEmpty() && request.getParameter("RED").matches("[0-9]+")) {
            idRed = new Long(request.getParameter("RED"));
            red = redFacade.get(idRed);
            if (red.getCodEra() != null) {
                codEra = red.getCodEra();
                fecha = parseDate.parse(request.getParameter("FECHA"));
                valido = true;
            }


        }
        if (valido) {
            //String fechaFormateada = parseDateSinBarra.format(fecha)+"235959";
            Date fechaGeneracionArchivo = new Date();
            String fechaFormateada = parseDateConHoraSinBarra.format(fechaGeneracionArchivo);

            String nombreArchivo = PATH_ARCHIVO + "ERA"
                    + UtilesSet.cerosIzquierda(codEra.longValue(), Constantes.TAM_ERA)
                    + "_"
                    + fechaFormateada + ".txt";
            String nombreArchivoZipeado = PATH_ARCHIVO + "ERA"
                    + UtilesSet.cerosIzquierda(codEra.longValue(), Constantes.TAM_ERA)
                    + "_"
                    + fechaFormateada + ".zip";
            String nombreArchivoZipeadoConHash, hashZip;
            PrintWriter escribir = null;

            try {
                if (op == null) {
                    jsonRespuesta.clear();
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "Especifique la operacion: "
                            + "ERA_BOLETA_PAGO o ERA_FORMULARIO");
                    response.getWriter().println(jsonRespuesta.toString());
                } else if (op.equalsIgnoreCase("ERA_FORMULARIO")) {
//                    try {
//                        grupoFacade.cerrarGruposRed(idRed, fecha);
//                    } catch (Exception ex) {
//                        Logger.getLogger(ARCHIVO_ERA.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    cadena = this.utilFacade.generarArchivoERAFormulario(idRed, fecha);
                    escribir = new PrintWriter(
                            new BufferedWriter(new FileWriter(nombreArchivo)));
                    escribir.print(cadena);
                    escribir.close();
                    Zipear zip = new Zipear(nombreArchivo, nombreArchivoZipeado);
                    zip.comprimir();
                    //Se renombra el archivo con su hash
                    File archivo = new File(nombreArchivoZipeado);
                    hashZip = UtilesSet.getHashSet(archivo);
                    nombreArchivoZipeadoConHash = PATH_ARCHIVO + "ERA"
                            + UtilesSet.cerosIzquierda(codEra.longValue(), Constantes.TAM_ERA)
                            + "_"
                            + fechaFormateada
                            + "_"
                            + hashZip
                            + ".zip";
                    archivo.renameTo(new File(nombreArchivoZipeadoConHash));
                    archivo = new File(nombreArchivo);
                    archivo.delete();

                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("archivo_generado", nombreArchivoZipeadoConHash);
                    response.getWriter().println(jsonRespuesta.toString());
                } else if (op.equalsIgnoreCase("EXISTE_ERA_FORMULARIO")) {
                    try {
                        grupoFacade.cerrarGruposRed(idRed, fecha);
                    } catch (Exception ex) {
                        Logger.getLogger(ARCHIVO_ERA.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    boolean existe = this.utilFacade.existeRegistrosFormulariosERA(idRed, fecha);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("existe", existe ? "S" : "N");
                    jsonRespuesta.put("motivo", existe ? "" : "No existen registros para la fecha indicada");
                    response.getWriter().println(jsonRespuesta.toString());
                } else if (op.equalsIgnoreCase("EXISTE_ERA_BOLETA_PAGO_EFECTIVO")) {
                    Long tipoPago = 1L;
                    try {
                        grupoFacade.cerrarGruposRed(idRed, fecha);
                    } catch (Exception ex) {
                        Logger.getLogger(ARCHIVO_ERA.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    boolean existe = this.utilFacade.existeRegistrosBoletasPagosERA(new TipoPago(tipoPago), idRed, fecha);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("existe", existe ? "S" : "N");
                    jsonRespuesta.put("motivo", existe ? "" : "No existen registros para la fecha indicada");
                    response.getWriter().println(jsonRespuesta.toString());
                } else if (op.equalsIgnoreCase("EXISTE_ERA_BOLETA_PAGO_CHEQUE")) {
                    Long tipoPago = 2L;
                    try {
                        grupoFacade.cerrarGruposRed(idRed, fecha);
                    } catch (Exception ex) {
                        Logger.getLogger(ARCHIVO_ERA.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    boolean existe = this.utilFacade.existeRegistrosBoletasPagosERA(new TipoPago(tipoPago), idRed, fecha);
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("existe", existe ? "S" : "N");
                    jsonRespuesta.put("motivo", existe ? "" : "No existen registros para la fecha indicada");
                    response.getWriter().println(jsonRespuesta.toString());
                } else if (op.equalsIgnoreCase("ERA_BOLETA_PAGO_EFECTIVO")) {
                    Long tipoPago = 1L;
                    
                    cadena = this.utilFacade.generarArchivoERABoletaPago(new TipoPago(tipoPago), idRed, fecha);
                    escribir = new PrintWriter(
                            new BufferedWriter(new FileWriter(nombreArchivo)));
                    escribir.print(cadena);
                    escribir.close();
                    Zipear zip = new Zipear(nombreArchivo, nombreArchivoZipeado);
                    zip.comprimir();
                    //Se renombra el archivo con su hash
                    File archivo = new File(nombreArchivoZipeado);
                    hashZip = UtilesSet.getHashSet(archivo);
                    nombreArchivoZipeadoConHash = PATH_ARCHIVO + "ERA"
                            + UtilesSet.cerosIzquierda(codEra.longValue(), Constantes.TAM_ERA)
                            + "_"
                            + fechaFormateada
                            + "_"
                            + hashZip
                            + ".zip";
                    archivo.renameTo(new File(nombreArchivoZipeadoConHash));
                    archivo = new File(nombreArchivo);
                    archivo.delete();
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("archivo_generado", nombreArchivoZipeadoConHash);
                    response.getWriter().println(jsonRespuesta.toString());
                } else if (op.equalsIgnoreCase("ERA_BOLETA_PAGO_CHEQUE")) {
                    Long tipoPago = 2L;
//                    try {
//                        grupoFacade.cerrarGruposRed(idRed, fecha);
//                    } catch (Exception ex) {
//                        Logger.getLogger(ARCHIVO_ERA.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    cadena = this.utilFacade.generarArchivoERABoletaPago(new TipoPago(tipoPago), idRed, fecha);
                    escribir = new PrintWriter(
                            new BufferedWriter(new FileWriter(nombreArchivo)));
                    escribir.print(cadena);
                    escribir.close();
                    Zipear zip = new Zipear(nombreArchivo, nombreArchivoZipeado);
                    zip.comprimir();
                    //Se renombra el archivo con su hash
                    File archivo = new File(nombreArchivoZipeado);
                    hashZip = UtilesSet.getHashSet(archivo);
                    nombreArchivoZipeadoConHash = PATH_ARCHIVO + "ERA"
                            + UtilesSet.cerosIzquierda(codEra.longValue(), Constantes.TAM_ERA)
                            + "_"
                            + fechaFormateada
                            + "_"
                            + hashZip
                            + ".zip";
                    archivo.renameTo(new File(nombreArchivoZipeadoConHash));
                    archivo = new File(nombreArchivo);
                    archivo.delete();
                    jsonRespuesta = new JSONObject();
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("archivo_generado", nombreArchivoZipeadoConHash);
                    response.getWriter().println(jsonRespuesta.toString());
                } else {

                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No existe la operacion");
                    response.getWriter().println(jsonRespuesta.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (escribir != null) {
                        escribir.close();
                    }
                } catch (Exception exc) {
                }

                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", e.getMessage());
                response.getWriter().println(jsonRespuesta.toString());
            }
        } else {

            jsonRespuesta.clear();
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", "Los Parametros no son correctos");
            response.getWriter().println(jsonRespuesta.toString());
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            ex.printStackTrace();
            Logger.getLogger(ARCHIVO_ERA.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            ex.printStackTrace();
            Logger.getLogger(ARCHIVO_ERA.class.getName()).log(Level.SEVERE, null, ex);
        }
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
