/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;


import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import py.com.konecta.redcobros.entities.Pais;

/**
 *
 * @author konecta
 */
@Stateless
public class PaisFacadeImpl extends GenericDaoImpl<Pais, Long> implements PaisFacade {
}
