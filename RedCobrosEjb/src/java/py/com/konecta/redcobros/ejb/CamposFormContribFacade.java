/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;


import py.com.konecta.redcobros.entities.CamposFormContrib;
import py.com.konecta.redcobros.entities.CamposFormContribPK;

/**
 *
 * @author konecta
 */
@Local
public interface CamposFormContribFacade extends GenericDao<CamposFormContrib, CamposFormContribPK>{

}
