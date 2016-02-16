/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.LoginExcepcion;

/**
 *
 * @author fgonzalez
 */
@Local
public interface LoginExcepcionFacade extends GenericDao<LoginExcepcion, Long> {
    
}
