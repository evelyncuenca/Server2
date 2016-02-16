/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;


import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.TipoCobroFacade;
import py.com.konecta.redcobros.entities.TipoCobro;

/**
 *
 * @author konecta
 */
@Stateless
public class TipoCobroFacadeImpl extends GenericDaoImpl<TipoCobro, Long> implements TipoCobroFacade {
}
