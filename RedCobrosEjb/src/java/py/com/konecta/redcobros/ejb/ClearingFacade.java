/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;



import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Clearing;

/**
 *
 * @author konecta
 */
@Local
public interface ClearingFacade extends GenericDao<Clearing, Long>{

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void realizarClearing(java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Long idRed, java.lang.String codigoServicio, boolean recalcular) throws java.lang.Exception;

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporte(java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Long idRed, java.lang.String codigoServicio) throws java.lang.Exception;

}
