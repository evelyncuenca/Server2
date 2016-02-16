/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.plugins;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.fileupload.FileItem;
import py.com.konecta.redcobros.gestionweb.IServiceDataSource;
import py.com.konecta.redcobros.gestionweb.interfaces.IServiceFile;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author documenta
 */
public class CDI implements IServiceFile {

    private Integer cantidadRegistros = 0;
    private String descripcion = "";
    Integer idServicio;
    //EntityManager em;

    public CDI(Integer idServicio) {
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
            //EntityManager em = getEntityManager();


            InputStream uploadedStream = item.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            for (String linea : lineas) {
                try {
                    cantidadRegistros++;
//                    List<ServicioRc> servicios = new ArrayList<ServicioRc>();
//                    //Session session = ((HibernateEntityManager) em.getDelegate()).getSession();
//                    //Query q = session.createQuery("from ServicioRc where habilitado = ? and cbPresente = ? ");
//                    Query q = em.createQuery("from ServicioRc where habilitado = ? and cbPresente = ? ");
//                    q.setParameter(0, 'S');
//                    q.setParameter(1, 'S');
//                    servicios = (List<ServicioRc>) q.getResultList();
                } catch (Exception ex) {
                    Logger.getLogger(CDI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ok = true;
        } catch (Exception ex) {
            ok = false;
            Logger.getLogger(CDI.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if (ds != null && conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CDI.class.getName()).log(Level.SEVERE, null, ex);
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
