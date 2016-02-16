/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.EntidadFacade;
import py.com.konecta.redcobros.entities.Entidad;

/**
 *
 * @author Kiki
 */
@Stateless
public class EntidadFacadeImpl extends GenericDaoImpl<Entidad, Long> implements EntidadFacade {
}
