/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.BoletaPagoContrib;



/**
 *
 * @author konecta
 */
@Local
public interface BoletaPagoContribFacade extends GenericDao<BoletaPagoContrib, Long>{

    public java.util.List<py.com.konecta.redcobros.entities.BoletaPagoContrib> boletasPagosERA(py.com.konecta.redcobros.entities.TipoPago tipoPago, java.lang.Long idRed, java.util.Date fecha);

    public boolean existeBoletasPagosERA(py.com.konecta.redcobros.entities.TipoPago tipoPago, java.lang.Long idRed, java.util.Date fecha);

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> boletasClearingConCantidad(java.util.Date fecha, java.lang.Long idRed, java.util.List<java.lang.Long> listaRecaudadores, java.util.Map<java.lang.Long, java.util.List<java.lang.Long>> mapaRecaudadorSucursales);

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> boletasClearingConTotal(java.util.Date fecha, java.lang.Long idRed, java.util.List<java.lang.Long> listaRecaudadores, java.util.Map<java.lang.Long, java.util.List<java.lang.Long>> mapaRecaudadorSucursales);

    public java.lang.Double totalBoletasClearing(java.util.Date fecha, java.lang.Long idRed);

    public java.lang.Integer cantidadBoletasClearing(java.util.Date fecha, java.lang.Long idRed);

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> boletasClearingFacturadasConTotalPorRecaudador(java.util.Date fecha, java.lang.Long idRed, java.lang.Long idTipoCobro);

    public java.lang.Double boletasClearingFacturadasConTotal(java.util.Date fecha, java.lang.Long idRed, java.lang.Long idTipoCobro);

   // public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporteRechazadosNoRechazados(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean filtroCheque, java.lang.Boolean filtroEfectivo, java.lang.Boolean todosFormaPago, java.util.Date fechaDesde, java.util.Date fechaHasta) throws java.lang.Exception;

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporteRechazadosNoRechazados(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean filtroCheque, java.lang.Boolean filtroEfectivo, java.lang.Boolean todosFormaPago, java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Boolean filtroAceptado, java.lang.Boolean filtroRechazados, java.lang.Boolean todosAceptadosRechazados) throws java.lang.Exception;


}
