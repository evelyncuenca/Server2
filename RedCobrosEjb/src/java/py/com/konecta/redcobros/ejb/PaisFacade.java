/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Pais;

/**
 *
 * @author konecta
 */
@Local
public interface PaisFacade extends GenericDao<Pais, Long> {
}
