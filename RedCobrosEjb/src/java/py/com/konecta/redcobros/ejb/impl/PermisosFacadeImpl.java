/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;

import java.util.List;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import py.com.konecta.redcobros.entities.Aplicacion;

import py.com.konecta.redcobros.entities.Permiso;

/**
 *
 * @author konecta
 */
@Stateless
public class PermisosFacadeImpl extends GenericDaoImpl<Permiso, Long> implements PermisosFacade {

     public List<Permiso> getPermisosSeccion(Long idUsuario, Long aplicacion, String seccion){

        Session  session = this.getSession();
        Criteria c = session.createCriteria(Permiso.class);
        c.add(Restrictions.eq("seccion", seccion));
        c.add(Restrictions.eq("aplicacion",  new Aplicacion(aplicacion) ));
        c.createCriteria("permisoDeRolCollection")
                .createCriteria("rol")
                .createCriteria("rolDeUsuarioCollection")
                .createCriteria("usuario")
                .add(Restrictions.eq("idUsuario",idUsuario));
        
        return c.list();
    }

}
