/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.List;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.konecta.redcobros.entities.ServicioRc;

/**
 *
 * @author konecta
 */
@Stateless
public class ServicioRcFacadeImpl extends GenericDaoImpl<ServicioRc, Integer> implements ServicioRcFacade {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ServicioRc> getAllConsultable() {
        String sql = "select s.* "
                + "  from servicio_rc s, parametro_servicio ps "
                + " where s.id_servicio = ps.id_servicio "
                + "   and ps.clave = 'CONSULTABLE' "
                + "   and ps.valor = 'true'";
        Query query = em.createNativeQuery(sql, ServicioRc.class);
        List<ServicioRc> retorno = query.getResultList();
        return retorno;
    }

}
