/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;


import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.LocalidadFacade;
import py.com.konecta.redcobros.entities.Localidad;

/**
 *
 * @author konecta
 */
@Stateless
public class LocalidadFacadeImpl extends GenericDaoImpl<Localidad, Long> implements LocalidadFacade {
}
