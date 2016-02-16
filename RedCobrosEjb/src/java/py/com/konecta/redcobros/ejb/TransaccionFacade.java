/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.http.HttpSession;
import py.com.konecta.redcobros.ejb.core.Respuesta;
import py.com.konecta.redcobros.entities.Transaccion;

/**
 *
 * @author Klaus
 */
@Local
public interface TransaccionFacade extends GenericDao<Transaccion, Long> {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public py.com.konecta.redcobros.ejb.core.Respuesta doTransacctionSetPagoFormulario(java.lang.Long idTransaccionRedOrigen, java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.String idTerminal, java.lang.String idUsuario, java.lang.String nombreUsuario, java.lang.String contrasenha, java.lang.String fechaHoraRed, java.lang.String fechaHoraTerminal, java.lang.Long idTipoPago, java.lang.Double tasaCambio, java.lang.Long idTipoMoneda, java.lang.Double montoTotal, java.lang.Long idFormContrib, java.lang.Long era, java.lang.Integer numeroCheque, java.lang.Long idEntidadFinanciera, String fechaCheque, HttpSession session);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Respuesta doTransacctionSetBoletaPago(Long idTransaccionRedOrigen, Long idRed, Long idRecaudador,
            Long idSucursal, String idTerminal, String idUsuario, String nombreUsuario,
            String contrasenha, String fechaHoraRed, String fechaHoraTerminal, Long idTipoPago, Double tasaCambio,
            Long idTipoMoneda, Double montoTotal, String ruc, String dv, String periodo, Long idImpuesto, Long idFormulario, Long versionFormulario, Long era, Long nroResolucion, Integer numeroCheque, Long idEntidadFinanciera, String fechaCheque, HttpSession session);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void anularTransaccion(Long idTransaccion, Long idUsuarioTerminal) throws Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public Transaccion anularTransaccionGestion(java.lang.Long idTransaccion) throws java.lang.Exception;

    //public java.lang.Integer getTotalControlFormularioBoletas(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean filtroCheque, java.lang.Boolean filtroEfectivo, java.lang.Boolean todosFormaPago, java.util.Date fechaDesde, java.util.Date fechaHasta);
    // public java.util.List<py.com.konecta.redcobros.entities.Transaccion> getControlFormularioBoletas(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean filtroCheque, java.lang.Boolean filtroEfectivo, java.lang.Boolean todosFormaPago, java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Integer start, java.lang.Integer limit);
    public java.util.List<py.com.konecta.redcobros.entities.Transaccion> getControlFormularioBoletas(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean filtroCheque, java.lang.Boolean filtroEfectivo, java.lang.Boolean todosFormaPago, java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Boolean filtroAceptado, java.lang.Boolean filtroRechazados, java.lang.Boolean todosAceptadosRechazados, java.lang.Integer start, java.lang.Integer limit);

    public java.lang.Integer getTotalControlFormularioBoletas(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean filtroCheque, java.lang.Boolean filtroEfectivo, java.lang.Boolean todosFormaPago, java.lang.Boolean filtroAceptado, java.lang.Boolean filtroRechazados, java.lang.Boolean todosAceptadosRechazados, java.util.Date fechaDesde, java.util.Date fechaHasta);

    public void test();
   // public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporteRecContEnv(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean filtroCheque, java.lang.Boolean filtroEfectivo, java.lang.Boolean todosFormaPago, java.util.Date fechaDesde, java.util.Date fechaHasta) throws java.lang.Exception;
}
