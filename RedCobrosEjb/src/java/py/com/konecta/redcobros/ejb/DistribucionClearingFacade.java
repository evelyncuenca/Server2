/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;



import javax.ejb.Local;
import py.com.konecta.redcobros.entities.DistribucionClearing;

/**
 *
 * @author konecta
 */
@Local
public interface DistribucionClearingFacade extends GenericDao<DistribucionClearing, Long>{

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> generarClearingDistribucion(java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaMapa, py.com.konecta.redcobros.entities.Clearing clearing, java.lang.Double divisor, java.lang.Integer iteracion, java.util.List<java.lang.Long> listaRecaudador, java.util.Map<java.lang.Long, java.util.List<java.lang.Long>> mapaRecaudadorSucursales) throws java.lang.Exception;

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporte(java.lang.Long idClearing) throws java.lang.Exception;

}
