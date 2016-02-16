/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;


import py.com.konecta.redcobros.entities.ConfigClearingServicio;

/**
 *
 * @author konecta
 */
@Local
public interface ConfigClearingServicioFacade extends GenericDao<ConfigClearingServicio, Long>{

    public void agregarModificarConfiguracion(java.lang.Long idConfigClearingServicio, java.lang.Long idRed, java.lang.Long idServicio, java.lang.Long idTipoClearing, java.lang.Double desde, java.lang.Double hasta, java.lang.Double valor) throws java.lang.Exception;

    public void eliminarConfiguracion(java.lang.Long idConfigClearingServicio) throws java.lang.Exception;

    public java.util.List<py.com.konecta.redcobros.entities.ConfigClearingServicio> verRangosCCS(java.lang.Long idRed, java.lang.Long idServicio, java.lang.Long idTipoClearing);

    public java.util.List<java.lang.Long> obtenerTiposDeClearing(java.lang.Long idRed, java.lang.Long idServicio);

    public py.com.konecta.redcobros.entities.ConfigClearingServicio obtenerConfigClearingServicio(java.lang.Long idRed, java.lang.Long idServicio, java.lang.Long idTipoClearing, java.lang.Double total) throws java.lang.Exception;

    public py.com.konecta.redcobros.entities.ConfigClearingServicio obtenerConfigClearingServicio(java.lang.Long idRed, java.lang.Long idServicio, java.lang.Long idTipoClearing, java.lang.Double desde, java.lang.Double hasta) throws java.lang.Exception;


}
