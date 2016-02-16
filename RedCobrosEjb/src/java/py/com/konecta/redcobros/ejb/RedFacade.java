/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import py.com.konecta.redcobros.entities.Entidad;
import py.com.konecta.redcobros.entities.EntidadPolitica;
import py.com.konecta.redcobros.entities.Red;

/**
 *
 * @author konecta
 */
@Local
public interface RedFacade extends GenericDao<Red, Long> {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public java.lang.Long[] getProximoRangoOrden(java.lang.Long idRecaudador) throws java.lang.Exception;

    public java.util.List<py.com.konecta.redcobros.entities.Recaudador> obtenerRecaudadoresNoHabilitados(java.lang.Long idRed);

    public java.util.List<py.com.konecta.redcobros.entities.Recaudador> obtenerRecaudadoresHabilitados(java.lang.Long idRed);

    public boolean comprobarHabilitacionRecaudador(java.lang.Long idRed, java.lang.Long idRecaudador);

    public boolean comprobarHabilitacionFacturador(java.lang.Long idRed, java.lang.Long idFacturador);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void agregarFacturadores(java.lang.Long idRed, java.lang.String[] idFacturadores, java.lang.String[] idCuentas) throws java.lang.Exception;

    public java.util.List<py.com.konecta.redcobros.entities.Facturador> obtenerFacturadoresNoHabilitados(java.lang.Long idRed);

    public java.util.List<py.com.konecta.redcobros.entities.Facturador> obtenerFacturadoresHabilitados(java.lang.Long idRed);

    public java.util.List<py.com.konecta.redcobros.entities.HabilitacionFactRed> obtenerHabilitacionesFactRed(java.lang.Long idRed);

    public java.util.List<py.com.konecta.redcobros.entities.Servicio> obtenerServiciosPorRed(java.lang.Long idRed, java.lang.Integer start, java.lang.Integer limit);

    public java.lang.Integer cantidadServiciosPorRed(java.lang.Long idRed);

    public List<EntidadPolitica> obtenerEntidadesPoliticas(Long idRed);

    public List<Entidad> obtenerEntidadesNoPoliticas(Long idRed);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarEntidadesPoliticas(Long idRed, String[] idEntidades, String[] idCuentas) throws Exception;

}
