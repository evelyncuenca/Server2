/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.RedRecaudadorFacade;
import py.com.konecta.redcobros.entities.RedRecaudador;
import py.com.konecta.redcobros.entities.RedRecaudadorPK;

/**
 *
 * @author konecta
 */

@Stateless
public class RedRecaudadorFacadeImpl extends GenericDaoImpl<RedRecaudador, RedRecaudadorPK> implements RedRecaudadorFacade {
}