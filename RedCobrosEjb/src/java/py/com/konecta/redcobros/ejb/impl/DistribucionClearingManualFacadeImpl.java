/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.List;
import java.util.Map;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.DistribucionClearingManual;

/**
 *
 * @author konecta
 */
@Stateless
public class DistribucionClearingManualFacadeImpl extends GenericDaoImpl<DistribucionClearingManual, Long> implements DistribucionClearingManualFacade {

    public List<Map<String, Object>> listaReporte(Long idClearingManual) throws Exception {
        List<Map<String, Object>> lista = this.obtenerCriteriaReporte(idClearingManual).list();
        return lista;
    }

    private Criteria obtenerCriteriaReporte(Long idClearingManual) throws Exception {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(DistribucionClearingManual.class);
        c.createCriteria("clearingManual").
                add(Restrictions.eq("idClearingManual", idClearingManual));
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("numeroCuenta"), "numeroCuenta");
        pl.add(Projections.property("monto"), "montoTotal");
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return c;
    }
}
