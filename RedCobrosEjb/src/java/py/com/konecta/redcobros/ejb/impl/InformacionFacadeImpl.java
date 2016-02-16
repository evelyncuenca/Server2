/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.InformacionFacade;
import py.com.konecta.redcobros.entities.Informacion;

/**
 *
 * @author konecta
 */
@Stateless
public class InformacionFacadeImpl extends GenericDaoImpl<Informacion, Integer> implements InformacionFacade   {
    
}
