/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Recaudador;

/**
 *
 * @author konecta
 */
@Local
public interface RecaudadorFacade extends GenericDao<Recaudador, Long> {

    public boolean comprobarHabilitacionRecaudadorServicio(java.lang.Long idServicio, java.lang.Long idRecaudador);

    public java.util.List<py.com.konecta.redcobros.entities.Recaudador> obtenerRecaudadoresPorServicio(java.lang.Long idServicio, java.lang.Integer start, java.lang.Integer limit);

    public java.lang.Integer cantidadRecaudadoresPorServicio(java.lang.Long idServicio);

    public java.util.List<py.com.konecta.redcobros.entities.Servicio> obtenerServiciosNoHabilitados(java.lang.Long idRecaudador);

    public java.util.List<py.com.konecta.redcobros.entities.Servicio> obtenerServiciosHabilitados(java.lang.Long idRecaudador);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public java.lang.Long obtenerProximoValorOrden(java.lang.Long idRecaudador) throws java.lang.Exception;

    public java.util.List<py.com.konecta.redcobros.entities.Recaudador> obtenerRecaudadoresPorServicioRc(java.lang.Long idServicio, java.lang.Integer start, java.lang.Integer limit);
}
