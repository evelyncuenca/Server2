/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import java.math.BigDecimal;
import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Corte;

/**
 *
 * @author konecta
 */
@Local
public interface CorteFacade extends GenericDao<Corte, BigDecimal> {
}