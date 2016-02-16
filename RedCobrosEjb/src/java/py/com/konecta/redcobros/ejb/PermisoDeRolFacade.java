/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.PermisoDeRol;

/**
 *
 * @author konecta
 */
@Local
public interface PermisoDeRolFacade extends GenericDao<PermisoDeRol, Long>{

}
