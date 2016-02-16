/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;


import py.com.konecta.redcobros.entities.ConfiguracionComisional;

/**
 *
 * @author konecta
 */
@Local
public interface ConfiguracionComisionalFacade extends GenericDao<ConfiguracionComisional, Long>{

    public java.lang.Long obtenerIdConfiguracionComisional(java.lang.Long idConfigClearingServicio, java.lang.Long idRecaudador, java.lang.Long idSucursal) throws java.lang.Exception;

    public void agregarModificarConfiguracion(java.lang.Long idConfigClearingServicio, java.lang.Long idRed, java.lang.Long idServicio, java.lang.Long idTipoClearing, java.lang.Double desde, java.lang.Double hasta, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long[] idRolComisionista, java.lang.Long[] idBeneficiario, java.lang.Double[] valor, java.lang.String descripcion) throws java.lang.Exception;

    public java.util.List<py.com.konecta.redcobros.entities.ConfigComisionalDetalle> obtenerDetalle(java.lang.Long idConfigClearingServicio, java.lang.Long idRecaudador, java.lang.Long idSucursal) throws java.lang.Exception;




}
