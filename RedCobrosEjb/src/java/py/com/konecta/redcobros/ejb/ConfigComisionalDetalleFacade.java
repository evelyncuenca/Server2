/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;


import py.com.konecta.redcobros.entities.ConfigComisionalDetalle;

/**
 *
 * @author konecta
 */
@Local
public interface ConfigComisionalDetalleFacade extends GenericDao<ConfigComisionalDetalle, Long>{

    public java.util.List<py.com.konecta.redcobros.entities.ConfigComisionalDetalle> list(Long idConfiguracionComisional, java.lang.Double valorRepartir) throws java.lang.Exception;
}
