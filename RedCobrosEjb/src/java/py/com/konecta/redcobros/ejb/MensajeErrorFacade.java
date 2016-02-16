/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.MensajeError;

/**
 *
 * @author konecta
 */
@Local
public interface MensajeErrorFacade extends GenericDao<MensajeError, Long> {

}