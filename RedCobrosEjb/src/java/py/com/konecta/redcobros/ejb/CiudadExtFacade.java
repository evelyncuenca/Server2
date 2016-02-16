/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.CiudadExt;
import py.com.konecta.redcobros.entities.CiudadExtPK;

/**
 *
 * @author fgonzalez
 */
@Local
public interface CiudadExtFacade extends GenericDao<CiudadExt, CiudadExtPK> {
}
