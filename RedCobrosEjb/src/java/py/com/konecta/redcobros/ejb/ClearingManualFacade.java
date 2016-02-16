/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;



import javax.ejb.Local;
import py.com.konecta.redcobros.entities.ClearingManual;

/**
 *
 * @author konecta
 */
@Local
public interface ClearingManualFacade extends GenericDao<ClearingManual, Long>{
    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporte(java.util.Date fecha) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void realizarClearing(java.util.Date fecha, java.util.Date fechaAcreditacion, java.lang.Character debitoCreditoCabecera, java.lang.String descripcionCabecera, java.lang.String numeroCuentaCabecera, java.lang.Double montoCabecera, java.lang.String[] descripcionDetalle, java.lang.String[] numeroCuentaDetalle, java.lang.Double[] montoDetalle) throws java.lang.Exception;
}
