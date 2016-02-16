/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.LoginExcepcionFacade;
import py.com.konecta.redcobros.entities.LoginExcepcion;

/**
 *
 * @author fgonzalez
 */
@Stateless
public class LoginExcepcionFacadeImpl extends GenericDaoImpl<LoginExcepcion, Long> implements LoginExcepcionFacade {
}

