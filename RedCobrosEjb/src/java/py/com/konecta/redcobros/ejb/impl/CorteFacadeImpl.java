/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.CorteFacade;
import py.com.konecta.redcobros.entities.Corte;

/**
 *
 * @author konecta
 */
@Stateless
public class CorteFacadeImpl extends GenericDaoImpl<Corte, BigDecimal> implements CorteFacade {
}
