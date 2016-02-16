/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.SuscriptorEvento;
import py.com.konecta.redcobros.entities.SuscriptorEventoPK;

/**
 *
 * @author konecta
 */
@Remote
public interface SuscriptorEventoFacade extends GenericDao<SuscriptorEvento, SuscriptorEventoPK> {
}
