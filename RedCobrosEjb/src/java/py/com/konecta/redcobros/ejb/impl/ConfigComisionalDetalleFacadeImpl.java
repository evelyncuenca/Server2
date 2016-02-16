/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;


import java.util.List;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.ConfigComisionalDetalle;

/**
 *
 * @author konecta
 */
@Stateless
public class ConfigComisionalDetalleFacadeImpl extends GenericDaoImpl<ConfigComisionalDetalle, Long> implements ConfigComisionalDetalleFacade {
    public List<ConfigComisionalDetalle> list(Long idConfiguracionComisional, Double valorRepartir) throws Exception {
        //validar si suma el porcentaje si es por volumen y 
        //total del monto fijo si es fijo por cantidad
        Criteria c = this.obtenerCriteria(idConfiguracionComisional);
        c.setProjection(Projections.sum("valor"));
        Double sumatoria=(Double)c.list().get(0);
        if (sumatoria.doubleValue()!=valorRepartir.doubleValue()) {
            throw new Exception("Sumatoria del detalle de la " +
                    "configuracion comisional (id:"+idConfiguracionComisional+"): "+
                    sumatoria+" no coincide con la cantidad a repartir: "+
                    valorRepartir);
        }
        c = this.obtenerCriteria(idConfiguracionComisional);
        return c.list();
    }
    private Criteria obtenerCriteria(Long idConfiguracionComisional) {
        //validar si suma el porcentaje si es por volumen y
        //total del monto fijo si es fijo por cantidad
        //y sacar la validacion del == y poner que siempre el ultimo sea
        //lo restante nomas ya
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(ConfigComisionalDetalle.class);
        c.add(Restrictions.gt("valor", 0.0));
        c.createCriteria("configuracionComisional").
                add(Restrictions.eq("idConfiguracionComisional", idConfiguracionComisional));
        return c;
    }
}
