/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.DepartamentoExtFacade;
import py.com.konecta.redcobros.entities.DepartamentoExt;
import py.com.konecta.redcobros.entities.DepartamentoExtPK;

/**
 *
 * @author fgonzalez
 */
@Stateless
public class DepartamentoExtFacadeImpl extends GenericDaoImpl<DepartamentoExt, DepartamentoExtPK> implements DepartamentoExtFacade {
    
}
