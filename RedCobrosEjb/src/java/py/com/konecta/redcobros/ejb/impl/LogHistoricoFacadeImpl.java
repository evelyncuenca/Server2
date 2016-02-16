/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import py.com.konecta.redcobros.ejb.LogHistoricoFacade;
import py.com.konecta.redcobros.ejb.LogHistoricoTransFacade;
import py.com.konecta.redcobros.ejb.MensajeErrorFacade;
import py.com.konecta.redcobros.ejb.core.Parametros;
import py.com.konecta.redcobros.ejb.core.Respuesta;
import py.com.konecta.redcobros.entities.LogHistorico;
import py.com.konecta.redcobros.entities.LogHistoricoTrans;

/**
 *
 * @author Klaus
 */
@Stateless
public class LogHistoricoFacadeImpl extends GenericDaoImpl<LogHistorico, Long> implements LogHistoricoFacade {
    /*@Resource
    private SessionContext context;*/

    @EJB
    private MensajeErrorFacade mensErrorF;
    @EJB
    private LogHistoricoTransFacade logHistTransF;

    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private boolean insertLogHistorico(Date fechaHoraCore, char sentido, Long idTransaccionRedOrigen, String  codigoTransaccional,
            Long idRed, Long idRecaudador, Long idSucursal,  String idTerminal,
            String idUsuario, String nombreUsuario, String fechaHoraRed, String fechaHoraTerminal, Long idTipoPago,
            Double tasaCambio, Long idTipoMoneda, Double montoTotal, String mensaje, LogHistoricoTrans lht) {

        try {
            // Se crea un log historico de entrada
            LogHistorico lh = new LogHistorico();
            lh.setFechaHora(fechaHoraCore);
            lh.setSentido(sentido);
            lh.setTransaccion(idTransaccionRedOrigen);
            lh.setRed(idRed);
            lh.setRecaudador(idRecaudador);
            lh.setSucursal(idSucursal);

            lh.setTerminal(idTerminal);
            lh.setUsuario(idUsuario);
            lh.setNombreUsuario(nombreUsuario);
            lh.setFechaHoraRed(fechaHoraRed);
            lh.setFechaHoraTerminal(fechaHoraTerminal);
            lh.setIdLogHistoricoTrans(lht);
            lh.setMensaje(mensaje);
            lh.setMonto(montoTotal.toString());
            lh.setTasaCambio(tasaCambio);
            lh.setIdTipoPago(idTipoPago);
            lh.setIdTipoMoneda(idTipoMoneda);
            lh.setCodigoTransaccional(codigoTransaccional);
            this.save(lh);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Respuesta insertLogHistoricoResp(Long error, Date fechaHoraCore, char sentido, Long idTransaccionRedOrigen,
            String codigoTransaccional, Long idRed, Long idRecaudador, Long idSucursal,
             String idTerminal, String idUsuario, String nombreUsuario, String fechaHoraRed,
            String fechaHoraTerminal, Long idTipoPago, Double tasaCambio, Long idTipoMoneda, Double montoTotal,
            LogHistoricoTrans lht) {

        String mensaje;
        Respuesta resp = new Respuesta();
        resp.setFechaHoraRed(fechaHoraCore);
        resp.setIdRespuesta(error);
        try {
            mensaje = mensErrorF.get(error).getDescripcion();
        } catch (Exception e) {
            mensaje = "Mensaje de Error no seteado";
        }
        resp.setDescRespuesta(mensaje);

        if (!this.insertLogHistorico(fechaHoraCore, sentido, idTransaccionRedOrigen, codigoTransaccional, idRed, idRecaudador,
                idSucursal,  idTerminal, idUsuario, nombreUsuario, fechaHoraRed, fechaHoraTerminal, idTipoPago, tasaCambio,
                idTipoMoneda, montoTotal, mensaje, lht)) {
            error = Parametros.ERROR_LOG_HISTORIA_NO_INSERTADO.longValue();
            resp.setIdRespuesta(error);
            try {
                mensaje = mensErrorF.get(error).getDescripcion();
            } catch (Exception e) {
                mensaje = "Mensaje de Error no seteado";
            }
            resp.setDescRespuesta(mensaje);
        }
        resp.setLht(lht);
        return resp;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public LogHistoricoTrans insertarLogTrans() {
        LogHistoricoTrans lht = new LogHistoricoTrans();
        lht = logHistTransF.merge(lht);
        return lht;
    }
}
