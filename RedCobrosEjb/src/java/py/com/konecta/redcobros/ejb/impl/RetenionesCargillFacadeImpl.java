/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.math.BigDecimal;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import org.hibernate.ejb.QueryImpl;
import py.com.konecta.redcobros.ejb.RetenionesCargillFacade;
import py.com.konecta.redcobros.entities.RetenionesCargill;

/**
 *
 * @author brojas
 */
@Stateless
public class RetenionesCargillFacadeImpl extends GenericDaoImpl<RetenionesCargill, BigDecimal> implements RetenionesCargillFacade {

    @Override
    @PermitAll
    public Long getNextId() {
        EntityManager e= super.getEm();
        QueryImpl q = (QueryImpl) e.createNativeQuery("select RET_CARGILL_SEQ.nextval from dual");
        return ((BigDecimal) q.getSingleResult()).longValue();
    }
}
