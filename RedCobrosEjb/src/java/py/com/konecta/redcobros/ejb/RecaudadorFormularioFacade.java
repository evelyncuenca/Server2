/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.RecaudadorFormulario;

/**
 *
 * @author fgonzalez
 */
@Remote
public interface RecaudadorFormularioFacade extends GenericDao<RecaudadorFormulario, Long> {
}
