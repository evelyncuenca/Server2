/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.EntidadDirFacade;
import py.com.konecta.redcobros.ejb.EntidadFacade;
import py.com.konecta.redcobros.ejb.GrupoReporteFacade;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.GrupoReporte;
import py.com.konecta.redcobros.entities.EntidadDir;


/**
 *
 * @author documenta
 */
public class Entidad_Dir extends HttpServlet {

    public static String ID_ENTIDAD = "ID_ENTIDAD";
    public static String ID_GRUPO = "ID_GRUPO";
    public static String ENTIDAD = "ENTIDAD";
    public static String GRUPO = "GRUPO";
    public static String EMAIL = "EMAIL";
    public static String CONSULTA_ENTIDAD = "CONSULTA_ENTIDAD";
    public static String CONSULTA_GRUPO = "CONSULTA_GRUPO";
    public static String CONSULTA_EMAIL = "CONSULTA_EMAIL";
    public static String ID_ENTIDAD_DIR = "ID_ENTIDAD_DIR";
    @EJB
    private EntidadDirFacade entidadDirFacade;
    @EJB
    private GrupoReporteFacade grupoReporteFacade;
    @EJB
    private EntidadFacade entidadFacade;

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
        JSONObject jsonRespuesta = new JSONObject();
        try {
            String opcion = request.getParameter("op");
            Long idEntidad = request.getParameter(Entidad_Dir.ID_ENTIDAD) != null && !request.getParameter(Entidad_Dir.ID_ENTIDAD).isEmpty() ? Long.valueOf(request.getParameter(Entidad_Dir.ID_ENTIDAD)) : null;
            Long idEntidad_Dir=request.getParameter(Entidad_Dir.ID_ENTIDAD_DIR) != null && !request.getParameter(Entidad_Dir.ID_ENTIDAD_DIR).isEmpty() ? Long.valueOf(request.getParameter(Entidad_Dir.ID_ENTIDAD_DIR)) : null;
            Long idGrupo = request.getParameter(Entidad_Dir.ID_GRUPO) != null && !request.getParameter(Entidad_Dir.ID_GRUPO).isEmpty() ? Long.valueOf(request.getParameter(Entidad_Dir.ID_GRUPO)) : null;
            String email = request.getParameter(Entidad_Dir.EMAIL) != null && !request.getParameter(Entidad_Dir.EMAIL).isEmpty() ? request.getParameter(Entidad_Dir.EMAIL) : null;

            if (opcion.equalsIgnoreCase("AGREGAR")) {

                EntidadDir entidadDir=new EntidadDir();
                entidadDir.setIdGrupo(idGrupo);
                entidadDir.setIdEntidad(idEntidad);
                //GrupoReporte  grupoReporte= new GrupoReporte(Integer.valueOf(idGrupo.toString()));
                entidadDir.setDireccion(email);
                entidadDir.setEstado("A");

                try {
                    entidadDirFacade.save(entidadDir);
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    Logger.getLogger(Entidad_Dir.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo guardar");
                }

            }else if (opcion.equalsIgnoreCase("MODIFICAR")) { 
                EntidadDir ejemplo = entidadDirFacade.get(new EntidadDir(idEntidad_Dir));
                
                if(email!=null){
                    ejemplo.setDireccion(email);
                }else if (idEntidad!=null){
                    ejemplo.setIdEntidad(idEntidad);
                }else if (idGrupo!=null){
                    ejemplo.setIdGrupo(idGrupo);
                }
                
               try {
                    entidadDirFacade.update(ejemplo);
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    Logger.getLogger(Entidad_Dir.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo actualizar");
                }
                
            }else if (opcion.equalsIgnoreCase("BORRAR")) {                
                try {
                    //entidadDirFacade.delete();
                    EntidadDir entDir= entidadDirFacade.get(new EntidadDir(idEntidad_Dir));
                    entDir.setEstado("I");
                    entidadDirFacade.update(entDir);
                    
                    jsonRespuesta.put("success", true);
                } catch (Exception ex) {
                    Logger.getLogger(Entidad_Dir.class.getName()).log(Level.SEVERE, null, ex);
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo borrar");
                }
            } else if (opcion.equalsIgnoreCase("DETALLE")) {

                EntidadDir EntidadRec = entidadDirFacade.get(new EntidadDir(idEntidad_Dir));
                Entidad entidad= entidadFacade.get(new Entidad (idEntidad));
                GrupoReporte grupoReporte= grupoReporteFacade.get(new GrupoReporte(Integer.valueOf(idGrupo.toString())));
                JSONObject jsonDetalle = new JSONObject();
                jsonDetalle.put(Entidad_Dir.ENTIDAD, entidad.getNombre());
                jsonDetalle.put(Entidad_Dir.GRUPO, grupoReporte.getDescripcion());
                jsonDetalle.put(Entidad_Dir.EMAIL, EntidadRec.getDireccion());
                jsonDetalle.put(Entidad_Dir.ID_ENTIDAD, entidad.getIdEntidad());
                jsonDetalle.put(Entidad_Dir.ID_GRUPO, grupoReporte.getIdGrupo());
                jsonDetalle.put(Entidad_Dir.ID_ENTIDAD_DIR, EntidadRec.getIdEntidadDir());

                jsonRespuesta.put("success", true);
                jsonRespuesta.put("data", jsonDetalle);

            } else if (opcion.equalsIgnoreCase("LISTAR")) {

                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                String critConsultaENTIDAD = request.getParameter(Entidad_Dir.CONSULTA_ENTIDAD) != null && !request.getParameter(Entidad_Dir.CONSULTA_ENTIDAD).isEmpty() ? request.getParameter(Entidad_Dir.CONSULTA_ENTIDAD) : null;
             
                boolean like = false;
                int total = 0;
                List<EntidadDir> lista=new ArrayList <EntidadDir>();
                EntidadDir ejemplo = new EntidadDir();
                Entidad entidad = new Entidad();
                GrupoReporte rec = new GrupoReporte();
                ejemplo.setEstado("A");
                
                if (critConsultaENTIDAD != null) {
                    like = true;
                    entidad.setNombre(critConsultaENTIDAD);
                    
                    //ejemplo.setIdEntidad(entidadFacade.get(entidad).getIdEntidad());
                }


                int cont=0;
                if (primerResultado != null && cantResultados != null) {
                    if (like) {
                         List<Entidad> listaEnt=entidadFacade.list(entidad, "nombre","ASC",true);
                        for (Entidad ent : listaEnt){
                            Logger.getLogger(Entidad_Dir.class.getName()).log(Level.INFO, "Entidad: {0} " ,ent.getIdEntidad());
                            ejemplo.setIdEntidad(ent.getIdEntidad());
                            List<EntidadDir> aux=entidadDirFacade.list(ejemplo, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "idEntidadDir", "ASC", true);
                            if (!aux.isEmpty()){
                                for (EntidadDir e : aux){
                                    Logger.getLogger(Entidad_Dir.class.getName()).log(Level.INFO, "EntidadDir: {0} " ,e.getIdEntidadDir());
                                    lista.add(e);
                                }
                                cont+=lista.size();
                            }
                        }

                        //lista = entidadDirFacade.list(ejemplo, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "idEntidadDir", "ASC", true);
                    } else {
                        lista = entidadDirFacade.list(ejemplo, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "idEntidadDir", "ASC");
                    }
                } else {
                    if (like) {
                        lista = entidadDirFacade.list(ejemplo, "idEntidadDir", "ASC", true);
                    } else {
                        lista = entidadDirFacade.list(ejemplo, "idEntidadDir", "ASC");
                    }
                }

                JSONArray jsonENTIDADGRUPOes = new JSONArray();
                for (EntidadDir e : lista) {                    
                    if (e.getEstado().equalsIgnoreCase("A")){
                        JSONObject jsonENTIDADRec = new JSONObject();
                        entidad=entidadFacade.get(new Entidad(e.getIdEntidad()));
                        Integer i=Integer.valueOf(e.getIdGrupo().toString());
                        rec=grupoReporteFacade.get(new GrupoReporte(i));
                        jsonENTIDADRec.put(Entidad_Dir.ENTIDAD, entidad.getNombre());
                        jsonENTIDADRec.put(Entidad_Dir.GRUPO, rec.getDescripcion());
                        jsonENTIDADRec.put(Entidad_Dir.ID_ENTIDAD, e.getIdEntidad());
                        jsonENTIDADRec.put(Entidad_Dir.ID_GRUPO, e.getIdGrupo());
                        jsonENTIDADRec.put(Entidad_Dir.EMAIL, e.getDireccion());
                        jsonENTIDADRec.put(Entidad_Dir.ID_ENTIDAD_DIR, e.getIdEntidadDir());
                        jsonENTIDADGRUPOes.add(jsonENTIDADRec);
                    }   
                }
                if (like){
                    total=jsonENTIDADGRUPOes.size();
                }else{
                    total = entidadDirFacade.total(ejemplo, true);
                }
                jsonRespuesta.put("Entidad_Dir", jsonENTIDADGRUPOes);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);} 
            else {
                jsonRespuesta.put("success", false);
                jsonRespuesta.put("motivo", "No existe la opcion pedida");
            }
        } catch (Exception ex) {
            Logger.getLogger(Entidad_Dir.class.getName()).log(Level.SEVERE, null, ex);
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", "Error en la operacion");
        } finally {
            out.println(jsonRespuesta.toString());
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
