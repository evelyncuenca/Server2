/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import py.com.konecta.redcobros.entities.NumeroOt;

/**
 *
 * @author konecta
 */
@Local
public interface NumeroOtFacade extends GenericDao<NumeroOt, Long> {

    //public List<Map<String, Object>> obtenerDatosReporteOT(Long idRed, String fecha, Long nroOt, Integer tipoPago) throws Exception;
    public List<Map<String, Object>> obtenerDatosReporteOT(Long idRed, String fecha, Integer tipoPago) throws Exception;
    public py.com.konecta.redcobros.entities.NumeroOt obtenerNumeroOT(java.lang.Long idRed,java.lang.Integer numeroERA, java.lang.Long idTipoPago, java.util.Date fecha);

}