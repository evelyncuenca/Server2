/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.xml.schema.common.EstadoTransaccion;
import org.netbeans.xml.schema.common.RespuestaCobranza;
import org.netbeans.xml.schema.common.RespuestaConsultaCodigoBarras;
import py.com.documenta.onlinemanager.ws.OlDetalleReferencia;
import py.com.documenta.onlinemanager.ws.OlResponseConsulta;
import py.com.documenta.ws.Autenticacion;
import py.com.documenta.ws.FormaPago;
import py.com.documenta.ws.HashMapContainer;
import py.com.documenta.ws.RedCobro;

/**
 *
 * @author fgonzalez
 */
public class PaymentMethods {

    public static final ReentrantLock lock = new ReentrantLock();

    public static String procesarServicio(RedCobro rcService, Long idTransaccional, Autenticacion auth, FormaPago formaDePago, Boolean adicional, HashMapContainer container) {
        String retorno = null;
        try {
            lock.lock();
            retorno = rcService.procesarServicio(idTransaccional, auth, formaDePago, adicional ? container : null);
        } catch (Exception ex) {
            Logger.getLogger(PaymentMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                lock.unlock();
            } catch (Exception ex) {
            }
        }
        return retorno;
    }

    public static RespuestaCobranza procesarConsulta(RedCobro rcService, Autenticacion auth, OlResponseConsulta respProcesar, FormaPago formaDePago) {
        RespuestaCobranza retorno = null;

        try {
            lock.lock();
            retorno = rcService.procesarConsulta(auth, respProcesar, formaDePago);
        } catch (Exception ex) {
            EstadoTransaccion estado = new EstadoTransaccion();
            estado.setDescripcion("Error en el pago..Favor intentelo de vuelta en unos minutos..");
            retorno = new RespuestaCobranza();
            retorno.setEstado(estado);
            Logger.getLogger(PaymentMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                lock.unlock();
            } catch (Exception ex) {
            }
        }
        return retorno;
    }

    public static RespuestaCobranza realizarCobranzaManual(RedCobro rcService, Integer idServicio, OlDetalleReferencia detalleReferencia, Autenticacion auth, FormaPago formaPago, String referenciaConsulta, HashMapContainer valoresIngresados) {
        RespuestaCobranza retorno = null;
        try {
            lock.lock();
            retorno = rcService.realizarCobranzaManual(idServicio, detalleReferencia, auth, formaPago, referenciaConsulta, valoresIngresados);
        } catch (Exception ex) {
            EstadoTransaccion estado = new EstadoTransaccion();
            estado.setDescripcion("Error en el pago..Favor intentelo de vuelta en unos minutos..");
            retorno = new RespuestaCobranza();
            retorno.setEstado(estado);
            Logger.getLogger(PaymentMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                lock.unlock();
            } catch (Exception ex) {
            }
        }
        return retorno;
    }

    public static RespuestaCobranza pagoCodigoDeBarras(RedCobro rcService, RespuestaConsultaCodigoBarras respuestaConsulta, Autenticacion auth, FormaPago formaPago, HashMapContainer valoresIngresados) {
        RespuestaCobranza retorno = null;
        try {
            lock.lock();
            retorno = rcService.pagoCodigoBarras(respuestaConsulta, auth, formaPago, valoresIngresados);
        } catch (Exception ex) {
            EstadoTransaccion estado = new EstadoTransaccion();
            estado.setDescripcion("Error en el pago..Favor intentelo de vuelta en unos minutos..");
            retorno = new RespuestaCobranza();
            retorno.setEstado(estado);
            Logger.getLogger(PaymentMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                lock.unlock();
            } catch (Exception ex) {
            }
        }
        return retorno;
    }
}
