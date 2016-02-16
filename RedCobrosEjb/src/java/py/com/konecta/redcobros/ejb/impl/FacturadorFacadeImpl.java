/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.ArrayList;
import java.util.List;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.Facturador;

/**
 *
 * @author konecta
 */
@Stateless
public class FacturadorFacadeImpl extends GenericDaoImpl<Facturador, Long> implements FacturadorFacade {

    @Override
    public List<Facturador> getFacturadoresWithServicioRcHabilitados(String servicio) {
        List<Facturador> lFact = new ArrayList<Facturador>();
        if (servicio == null) {
            HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
            Session session = hem.getSession();
            String sql = "Select Distinct f From Facturador f, ServicioRc s "
                    + "Where s.idFacturador = f.idFacturador "
                    + "and s.habilitado = 'S' "
                    + "Order by f.idFacturador";
            Query q = session.createQuery(sql);
            lFact = (List<Facturador>) q.list();
        } else {
            Facturador ejemplo = new Facturador();
            ejemplo.setDescripcion(servicio);
            lFact = this.list(ejemplo, true);
        }
        return lFact;
    }
}
