/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.EntidadPoliticaFacade;
import py.com.konecta.redcobros.entities.EntidadPolitica;

/**
 *
 * @author Kiki
 */
@Stateless
public class EntidadPoliticaFacadeImpl extends GenericDaoImpl<EntidadPolitica, Long> implements EntidadPoliticaFacade {
}
