/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;


import javax.ejb.Local;
import py.com.konecta.redcobros.entities.ClearingFacturador;
import py.com.konecta.redcobros.entities.Corte;

/**
 *
 * @author konecta
 */
@Local
public interface ClearingFacturadorFacade extends GenericDao<ClearingFacturador, Long> {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void generarClearingFacturador(java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Long idRed, java.lang.String codigoServicio, boolean recalcular) throws java.lang.Exception;

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporte(java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Long idRed, java.lang.String codigoServicio) throws java.lang.Exception;

    public Corte obtenerCorte() throws Exception;
}
