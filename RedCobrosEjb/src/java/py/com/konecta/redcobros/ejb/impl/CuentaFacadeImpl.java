/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;

import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import py.com.konecta.redcobros.entities.Cuenta;

/**
 *
 * @author konecta
 */
@Stateless
public class CuentaFacadeImpl  extends GenericDaoImpl <Cuenta, Long>  implements CuentaFacade {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
 
}
