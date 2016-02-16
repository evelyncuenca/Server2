/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.CiudadExtFacade;
import py.com.konecta.redcobros.entities.CiudadExt;
import py.com.konecta.redcobros.entities.CiudadExtPK;

/**
 *
 * @author fgonzalez
 */
@Stateless
public class CiudadExtFacadeImpl extends GenericDaoImpl<CiudadExt, CiudadExtPK> implements CiudadExtFacade {
    
}
