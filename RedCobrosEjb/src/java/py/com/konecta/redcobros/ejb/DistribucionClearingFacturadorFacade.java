/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;



import javax.ejb.Local;
import py.com.konecta.redcobros.entities.DistribucionClearingFacturador;

/**
 *
 * @author konecta
 */
@Local
public interface DistribucionClearingFacturadorFacade extends GenericDao<DistribucionClearingFacturador, Long>{

    public void generarClearingDistribucion(py.com.konecta.redcobros.entities.ClearingFacturador clearing, java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaMapa) throws java.lang.Exception;

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporte(java.lang.Long idClearingFacturador, java.lang.Long idFacturador, java.lang.Long idTipoCobro, java.util.Date fecha, java.lang.Long idRed) throws java.lang.Exception;
}
