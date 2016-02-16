/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Permiso;

/**
 *
 * @author konecta
 */
@Local
public interface PermisosFacade extends GenericDao<Permiso, Long>{



    public java.util.List<py.com.konecta.redcobros.entities.Permiso> getPermisosSeccion(java.lang.Long idUsuario, java.lang.Long aplicacion, java.lang.String seccion);

}
