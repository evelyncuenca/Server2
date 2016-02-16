/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author konecta
 */
@Local
public interface ReportesFacade {

    public byte[] cobranzaDetalladoCS(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal, Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni, String fechaFin, Long idGestion, String formatoDescarga, String tipoPago, boolean tmu, Integer moneda);

    public byte[] cobranzaDetalladoPorServicioCS(Long idRed, Long idRecaudador, Long idSucursal, Long idFacturador, String codigoServicio, String fechaIni, String fechaFin, String zona, String formatoDescarga);

    public byte[] cobranzaResumidoCS(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal, Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni, String fechaFin, Long idGestion, String formatoDescarga, Integer idMoneda);

    public byte[] declaracionesDelDia(java.lang.Long idRed, java.lang.String fecha, java.lang.String formato);

    public byte[] boletasPagoPorOrden(java.lang.Long numeroOrden, java.lang.Long idUsuario, java.lang.Long idRed, java.lang.String formato);

    public byte[] DDJJPorOrden(java.lang.Long numeroOrden, java.lang.Long idUsuario, java.lang.Long idRed, String formato);

    public byte[] cobranzaDetallado(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formato);

    public byte[] cobranzaDetalladoCheque(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formato);

    public byte[] cobranzaDetalladoEfectivo(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formato);

    public byte[] reporteClearing(Long aLong, String servicio, Date fechaDesde, Date fechaHasta, java.lang.String formato) throws Exception;

    public byte[] resumenClearingComision(Long idRed, Long idRecaudador, Long idSucursal, String fechaDesde, String fechaHasta, java.lang.String formato) throws Exception;

    // public byte[] cobranzaResumido(java.lang.Long idRed, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion);
    public byte[] tapaLoteGrupo(java.lang.Long idRed, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idSucursal, java.lang.Long idRecaudador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGrupo, String formato);

    public byte[] tapaLoteGrupoResumido(java.lang.Long idRed, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idSucursal, java.lang.Long idRecaudador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGrupo, java.lang.String formato);

    public byte[] reporteCierreDetallado(java.lang.Long idRed, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idSucursal, java.lang.Long idRecaudador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formato);

    public byte[] reporteCierreDetalladoCS(java.lang.Long idRed, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idSucursal, java.lang.Long idRecaudador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, Long idFacturador, java.lang.String formato);

    public byte[] reporteCierreResumido(java.lang.Long idRed, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idSucursal, java.lang.Long idRecaudador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formato);

    public byte[] reporteCierreResumidoCS(java.lang.Long idRed, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idSucursal, java.lang.Long idRecaudador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, Long idFacturador, java.lang.String formato, Boolean tmu);

    public byte[] reporteClearingFacturador(java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaDS, java.lang.Long idRed, java.lang.String fechaParametro, java.lang.String formato);

    public byte[] reporteTapaCaja(java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaDS, java.lang.Long idRed, java.lang.String fechaParametro, boolean rotulo, boolean detallado, String formato);

    public byte[] reporteControlFormulariosRecepControlEnv(java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaDS, java.lang.Long idRed, java.lang.String formato);

    public byte[] reporteRechazadosNoRechazados(java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaDS, java.lang.Long idRed, java.lang.String formato);

    public byte[] cobranzaResumido(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formato);

    public byte[] reporteFormularioOt(java.lang.Long idRed, java.lang.String fecha,/*java.lang.Long nroOt,*/ java.lang.Integer tipoPago) throws Exception;

    public byte[] reporteTransferenciaDGR(java.lang.String fecha) throws Exception;

    public byte[] reporteRecaudacion(Long idRed, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.String formato, java.lang.String tipoReporte) throws Exception;

    public byte[] reporteResumenFacturador(Long idRed, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idRecaudador, java.lang.Long idFacturador, java.lang.Long idSucursal, String isCorte, java.lang.String formato, String tipoReporte);

    public byte[] reporteTerminalesAbiertas(java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.String formato) throws Exception;

    public byte[] cobranzaDetalladoEfectivoCS(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formato, Integer idMoneda);

    public byte[] cobranzaDetalladoChequeCS(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formato, Integer idMoneda);

    public byte[] reporteDetalladoTransferenciaDGR(java.lang.Long idRed, java.lang.String fecha, java.lang.String formato) throws java.lang.Exception;

    public byte[] reporteChequesCobradosDDJJ(java.lang.Long idRed, java.lang.Long idUsuario, Long idGestion, java.lang.Long idTerminal, java.lang.Long idSucursal, java.lang.Long idRecaudador, java.lang.String fechaIni, java.lang.String formato);

    public byte[] reporteCierreUsuariosSet(java.lang.Long idRed, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idSucursal, java.lang.Long idRecaudador, java.lang.String formato, java.lang.String tipoReporte) throws java.lang.Exception;

    public byte[] cobranzaDetalladoParaFacturador(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idUsuario, java.lang.Long idTerminal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String estadoTransaccion, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idGestion, java.lang.String formatoDescarga);

    public byte[] reporteGestor(java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idSucursal, java.lang.Long ci, java.lang.String formato);

    public byte[] reporteMovimientoFacturadores(java.lang.String fechaIni, java.lang.String fechaFin, java.lang.String formatoDescarga);

    public byte[] reporteComisionServicios(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idFacturador, java.lang.Long idSucursal, Long idServicio, Integer tipoPago, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.String tipoReporte, java.lang.String formatoDescarga);

    public byte[] reporteClearingCS(java.lang.Long idEntidad, String fechaIni, String fechaFin, String tipoReporte, java.lang.String formatoDescarga);

    public byte[] reporteFacturacionCS(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Integer idTipoPago, java.lang.String fechaIni, String tipoReporte, java.lang.String fechaFin, Integer diasAnteriores, java.lang.String formatoDescarga);

    public byte[] reporteSanCristobal(Long idRed, String fechaIni, String fechaFin, Long idSucursal, Long idRecaudador, Long idServicio, String formatoDescarga);

    public byte[] reporteFacturacionAdministracion(String fechaIni, String fechaFin, Long idRecaudador, Integer idTipoPago, Long idRed, Integer diasAnteriores, Long idServicio, Long idFacturador, String tipoReporte, String formatoDescarga);

    public byte[] reporteComisionParaFacRec(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idFacturador, java.lang.String codigoServicio, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.String zona, String entidad, String tipoReporte, java.lang.String formatoDescarga);

    public byte[] reporteDigitacion(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idUsuario, java.lang.String certificado, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.String formatoDescarga);

    public byte[] reportePractiRetiro(java.lang.String fechaIni, java.lang.String fechaFin, java.lang.String formatoDescarga);
}
