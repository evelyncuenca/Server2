/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;


import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.TipoClearingFacade;
import py.com.konecta.redcobros.entities.TipoClearing;

/**
 *
 * @author konecta
 */
@Stateless
public class TipoClearingFacadeImpl extends GenericDaoImpl<TipoClearing, Long> implements TipoClearingFacade {
}
