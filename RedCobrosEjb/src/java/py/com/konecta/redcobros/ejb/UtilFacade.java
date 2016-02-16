/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import java.util.Calendar;
import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.Corte;


/**
 *
 * @author konecta
 */
@Remote
public interface UtilFacade {

    public java.lang.String calcularVencimiento(java.lang.Integer numeroFormulario, java.lang.Integer version, java.lang.Integer numeroImpuesto, java.lang.String ruc, java.lang.String fechaPresentacion, java.lang.String periodoFiscal) throws java.lang.Exception;

    public String calcularVencimientoForm90(java.lang.Integer numeroFormulario, java.lang.Integer version, java.lang.Integer numeroImpuesto, java.lang.String ruc, java.lang.String fecha, java.lang.String fechaPresentacion) throws java.lang.Exception;

    public java.lang.String validarPeriodoFiscal(java.lang.String opcion, java.lang.Integer numeroFormulario, java.lang.Integer version, java.lang.String periodoFiscal, java.lang.String fechaPresentacion, java.lang.Integer numeroImpuesto) throws java.lang.Exception;

    public java.lang.String generarArchivoERABoletaPago(py.com.konecta.redcobros.entities.TipoPago tipoPago, java.lang.Long idRed, java.util.Date fecha) throws java.lang.Exception;

    public java.lang.String generarArchivoERAFormulario(java.lang.Long idRed, java.util.Date fecha) throws java.lang.Exception;

    public java.util.Calendar proximoDiaHabil(java.util.Calendar calendar) throws java.lang.Exception;

    public boolean existeRegistrosFormulariosERA(java.lang.Long idRed, java.util.Date fecha);

    public boolean existeRegistrosBoletasPagosERA(py.com.konecta.redcobros.entities.TipoPago tipoPago, java.lang.Long idRed, java.util.Date fecha);

    public java.lang.String generarArchivoClearing(java.lang.Long idRed, boolean comision, boolean debito, java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaClearing, Corte corte) throws java.lang.Exception;

    public java.util.Calendar proximoVencimientoHabil(java.util.Calendar calendar, int plazo) throws java.lang.Exception;

    public java.lang.String generarArchivoClearing(java.lang.Long idRed, boolean comision, boolean debito, java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaClearing, boolean esManual, Corte corte) throws java.lang.Exception;

    public Calendar diaHabilSiguiente(Calendar calendar, int dias) throws Exception;

}
