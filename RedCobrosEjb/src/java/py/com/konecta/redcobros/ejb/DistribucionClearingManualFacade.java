/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;



import javax.ejb.Local;
import py.com.konecta.redcobros.entities.DistribucionClearingManual;

/**
 *
 * @author konecta
 */
@Local
public interface DistribucionClearingManualFacade extends GenericDao<DistribucionClearingManual, Long>{

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporte(java.lang.Long idClearingManual) throws java.lang.Exception;
}
