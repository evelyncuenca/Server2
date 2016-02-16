/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.utiles;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import py.com.konecta.redcobros.entities.ServicioRc;
import py.com.konecta.redcobros.gestionweb.interfaces.IServiceFile;


/**
 *
 * @author ystmiog
 */
public class PluginUtil {
    public static IServiceFile getComplemento(ServicioRc servicio, EntityManager em){
        IServiceFile isp = null;
        try {
            isp = (IServiceFile)Class.forName(servicio.getIdComplemento().getComplemento()).getConstructor(Integer.class).
                    newInstance(servicio.getIdServicio());

        } catch (Exception ex) {
            Logger.getLogger(PluginUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isp;
    }

}
