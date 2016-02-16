/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.EntidadFinancieraFacade;
import py.com.konecta.redcobros.entities.EntidadFinanciera;


/**
 *
 * @author konecta
 */
@Stateless
public class EntidadFinancieraFacadeImpl extends GenericDaoImpl<EntidadFinanciera, Long> implements EntidadFinancieraFacade {
}
