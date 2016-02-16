/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import py.com.konecta.redcobros.ejb.GestorDireccionesFacade;
import py.com.konecta.redcobros.entities.GestorDirecciones;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GestorDireccionesFacadeImpl extends GenericDaoImpl<GestorDirecciones, Long> implements GestorDireccionesFacade {

   
}
