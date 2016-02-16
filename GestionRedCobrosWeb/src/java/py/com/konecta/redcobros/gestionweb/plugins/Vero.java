/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.plugins;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.fileupload.FileItem;
import py.com.konecta.redcobros.gestionweb.types.AliasServicioFacturador;
import py.com.konecta.redcobros.gestionweb.util.ServiceFileUtil;
import py.com.konecta.redcobros.gestionweb.IServiceDataSource;
import py.com.konecta.redcobros.gestionweb.interfaces.IServiceFile;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author documenta
 */
public class Vero implements IServiceFile {
    
    private Integer cantidadRegistros = 0;
    private String descripcion = "";
    Integer idServicio;
    //EntityManager em;

    public Vero(Integer idServicio) {
        this.idServicio = idServicio;
        //this.em = em;
    }
    
    @Override
    public boolean parser(Object _item) {
        boolean ok = false;
        FileItem item = (FileItem) _item;
        
        DataSource ds = null;
        Connection conn = null;
        
        try {
            ds = IServiceDataSource.getServiceDataSource();
            conn = ds.getConnection();
            
            List<AliasServicioFacturador> lsASF = ServiceFileUtil.getAliasServicio(conn, idServicio);
            
            if (lsASF == null || lsASF.isEmpty()) {
                descripcion = "No se encuentran alias para el servicio";
                return false;
            }
            
            ServiceFileUtil.deleteDatoConsulta(conn, idServicio);
            
            InputStream uploadedStream = item.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            
            for (String linea : lineas) {
                try {
                    linea = linea.trim();
                    //if (linea.length() == 71) {
                    //String datos[] = linea.split("\\s+");
                    String datos[] = linea.split(";");
                    if (datos.length == 6) {
                        String nroContrato = datos[0];
                        String plan = datos[1].replaceAll("\"", "");
                        String nombre = datos[2].replaceAll("\"", "").replaceAll("'", "");                        
                        String referenciaBusqueda = datos[3].replaceAll("\"", "").replaceAll("[.]", "").replaceAll(",", "");
                        String dependencia = datos[4].replaceAll("\"", "");
                        String monto = datos[5];
                        
                        
//                        for (int i = 1; i < datos.length - 1; i++) {
//                            nombre += (datos[i] + " ");
//                        }
                        
//                        String monto = datos[datos.length - 1].substring(0, 9);
//                        String vencimiento = datos[datos.length - 1].substring(12, 20);
                        String mensaje = String.format("Cto: %s - Plan: %s - Nom: %s", nroContrato, plan, nombre ) ;
                        ServiceFileUtil.insertDatoConsulta(conn, lsASF.get(0),
                                referenciaBusqueda, nroContrato+"-"+referenciaBusqueda, mensaje,
                                1, new BigDecimal(monto), null, null,
                                null, 'S', 0, 'N',
                                'N', 'S', null);
                        
                        cantidadRegistros++;
                        //}                        
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Vero.class.getName()).log(Level.SEVERE, null, ex);
                    conn.rollback();
                    return ok;
                }
            }
            conn.commit();
            
            ok = true;
        } catch (Exception ex) {
            ok = false;
            Logger.getLogger(Vero.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ds != null && conn != null) {
                try {
                    conn.close();
                    conn = null;
                    ds = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Vero.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ok;
    }
    
    @Override
    public String getDescripcion() {
        return descripcion;
    }
    
    @Override
    public Integer getCantRegistros() {
        return cantidadRegistros;
    }
//    private DataSource getDbArchivoFacturador() throws NamingException {
//        Context c = new InitialContext();
//        return (DataSource) c.lookup("java:comp/env/dbArchivoFacturador");
//    }
}
