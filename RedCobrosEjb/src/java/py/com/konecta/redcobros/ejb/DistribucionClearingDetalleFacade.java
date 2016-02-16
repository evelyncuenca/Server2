/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;



import javax.ejb.Local;
import py.com.konecta.redcobros.entities.DistribucionClearingDetalle;

/**
 *
 * @author konecta
 */
@Local
public interface DistribucionClearingDetalleFacade extends GenericDao<DistribucionClearingDetalle, Long>{

    public void generarDistribucionClearingDetalle(py.com.konecta.redcobros.entities.DistribucionClearing distribucionClearing, java.lang.Double cantidad, java.lang.Long idConfiguracionComisional, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Double divisor) throws java.lang.Exception;

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporte(java.lang.Long idDistribucionClearing) throws java.lang.Exception;


}
