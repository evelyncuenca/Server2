/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.ejb.GestorFacade;
import py.com.konecta.redcobros.entities.FormContrib;
import py.com.konecta.redcobros.entities.Gestor;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GestorFacadeImpl extends GenericDaoImpl<Gestor, String> implements GestorFacade {

    private Criteria getCriteriaGestores(Long idRecaudador, Long idSucursal, Date fechaDesde, Date fechaHasta) {

        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        
        Criteria critFc = hem.getSession().createCriteria(FormContrib.class,"fc");
        Criteria critGestor = critFc.createCriteria("gestor", "ges");
        Criteria critUT = critFc.createCriteria("usuarioTerminalCarga", "ut");
     //   Criteria critUs = critUT.createCriteria("usuario", "u");
        Criteria critTe = critUT.createCriteria("terminal", "t");
        Criteria critSuc = critTe.createCriteria("sucursal", "suc");
        Criteria critRec = critSuc.createCriteria("recaudador", "rec");

        if (idRecaudador != null) {
            critRec.add(Restrictions.eq("idRecaudador", idRecaudador));
        }
        if (idSucursal != null) {
            critSuc.add(Restrictions.eq("idSucursal", idSucursal));
        }

        if (fechaDesde != null && fechaHasta != null) {
            critFc.add(Restrictions.between("fechaPresentacion", fechaDesde, fechaHasta));
        } else if (fechaDesde != null) {
            critFc.add(Restrictions.like("fechaPresentacion", fechaDesde));
        }
        ProjectionList pl = Projections.projectionList();
        //pl.add(Projections.property("ges.cedula"), "idGestor");
        pl.add(Projections.groupProperty("ges.cedula"),"idGestor");
        pl.add(Projections.count("fc.idFormContrib"), "total");
        critFc.setProjection(pl);

       // critFc.addOrder(Order.desc("fechaPresentacion"));
        return critFc;
    }

    public List<Map<String, Object>> getListGestor(Long idRecaudador, Long idSucursal, Date fechaDesde, Date fechaHasta) {
        Criteria c = this.getCriteriaGestores(idRecaudador, idSucursal, fechaDesde, fechaHasta);
//        if (start != null && limit != null) {
//            c.setFirstResult(start);
//            c.setMaxResults(limit);
//        }critTrx.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return c.list();
    }
}
