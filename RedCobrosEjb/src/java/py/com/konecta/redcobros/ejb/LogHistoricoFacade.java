/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.LogHistorico;

/**
 *
 * @author Klaus
 */
@Local
public interface LogHistoricoFacade extends GenericDao<LogHistorico, Long> {


    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public py.com.konecta.redcobros.entities.LogHistoricoTrans insertarLogTrans();

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public py.com.konecta.redcobros.ejb.core.Respuesta insertLogHistoricoResp(java.lang.Long error, java.util.Date fechaHoraCore, char sentido, java.lang.Long idTransaccionRedOrigen, java.lang.String codigoTransaccional, java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.String idTerminal, java.lang.String idUsuario, java.lang.String nombreUsuario, java.lang.String fechaHoraRed, java.lang.String fechaHoraTerminal, java.lang.Long idTipoPago, java.lang.Double tasaCambio, java.lang.Long idTipoMoneda, java.lang.Double montoTotal, py.com.konecta.redcobros.entities.LogHistoricoTrans lht);

}
