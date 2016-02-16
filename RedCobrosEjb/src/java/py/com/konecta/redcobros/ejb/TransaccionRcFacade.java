/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.TransaccionRc;

/**
 *
 * @author konecta
 */
@Remote
public interface TransaccionRcFacade extends GenericDao<TransaccionRc, BigDecimal> {

    public List<TransaccionRc> getControlServicios(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Date fechaDesde, Date fechaHasta, Integer start, Integer limit);

    public Integer getTotalControlServicio(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Date fechaDesde, Date fechaHasta);

    public List<Map<String, Object>> getResumenServiciosRc(java.lang.Long idGestion, java.lang.Long idServicio, java.lang.Long idFacturador);

    public java.util.List<TransaccionRc> getDataCobranzaDetallada(java.lang.Long idUsuario,java.lang.Long idTerminal, Integer idServicio, java.lang.Long idFacturador, String fechaIni, String fechaFin, java.lang.String tipoPago);
}
