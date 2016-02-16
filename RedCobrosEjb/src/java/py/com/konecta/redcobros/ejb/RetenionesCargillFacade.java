/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import java.math.BigDecimal;
import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.RetenionesCargill;

/**
 *
 * @author brojas
 */
@Remote
public interface RetenionesCargillFacade extends GenericDao<RetenionesCargill, BigDecimal>{

    public Long getNextId();
    
   // public Long saveRC(RetenionesCargill rc);
    
}
