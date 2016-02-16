/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.ConfiguracionesAeFacade;
import py.com.konecta.redcobros.entities.ConfiguracionesAe;

/**
 *
 * @author konecta
 */
@Stateless
public class ConfiguracionesAeFacadeImpl extends GenericDaoImpl<ConfiguracionesAe, Long> implements ConfiguracionesAeFacade {
}
