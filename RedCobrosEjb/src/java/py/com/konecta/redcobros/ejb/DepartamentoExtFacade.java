/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.DepartamentoExt;
import py.com.konecta.redcobros.entities.DepartamentoExtPK;

/**
 *
 * @author fgonzalez
 */
@Local
public interface DepartamentoExtFacade extends GenericDao<DepartamentoExt, DepartamentoExtPK> {
}
