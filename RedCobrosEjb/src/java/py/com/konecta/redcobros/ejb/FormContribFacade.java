/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.FormContrib;

/**
 *
 * @author konecta
 */
@Remote
public interface FormContribFacade extends GenericDao<FormContrib, Long> {

    public java.util.List<py.com.konecta.redcobros.entities.FormContrib> formulariosERA(java.lang.Long idRed, java.util.Date fecha);

    public boolean existeFormulariosERA(java.lang.Long idRed, java.util.Date fecha);

    public java.lang.Integer cantidadFormulariosClearing(java.util.Date fecha, java.lang.Long idRed);

    public List<Map<String, Object>> formulariosClearing(Date fecha, Long idRed,
            List<Long> listaRecaudadores,
            Map<Long, List<Long>> mapaRecaudadorSucursales);

    public boolean anularDigitacion(py.com.konecta.redcobros.entities.FormContrib formulario, py.com.konecta.redcobros.entities.Terminal t);

    //  public java.util.List<java.lang.Long> getReferencias(py.com.konecta.redcobros.entities.Sucursal sucursal);
    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public py.com.konecta.redcobros.entities.FormContrib certificarFormulario(java.lang.Long idFormContrib, java.lang.Integer codERA, py.com.konecta.redcobros.entities.UsuarioTerminal ut, java.lang.Long idRed, Long idSucursal) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void save(py.com.konecta.redcobros.entities.FormContrib fc, py.com.konecta.redcobros.entities.Terminal t) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public py.com.konecta.redcobros.entities.FormContrib merge(py.com.konecta.redcobros.entities.FormContrib fc, py.com.konecta.redcobros.entities.Terminal t) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public py.com.konecta.redcobros.entities.FormContrib merge(py.com.konecta.redcobros.entities.FormContrib fc, java.util.List<py.com.konecta.redcobros.entities.CamposFormContrib> campos, py.com.konecta.redcobros.entities.Terminal t) throws java.lang.Exception;

    public java.util.List<java.lang.Long> getReferencias(py.com.konecta.redcobros.entities.Sucursal sucursal, java.lang.String query);

    public java.util.List<py.com.konecta.redcobros.entities.FormContrib> getControlFormulario(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean recepcionados, java.lang.Boolean todosRecepcionados, java.lang.Boolean controlados, java.lang.Boolean todosControlados, java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Integer start, java.lang.Integer limit);

    public java.lang.Integer getTotalControlFormulario(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean recepcionados, java.lang.Boolean todosRecepcionados, java.lang.Boolean controlados, java.lang.Boolean todosControlados, java.util.Date fechaDesde, java.util.Date fechaHasta);

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> listaReporteRecContEnv(java.lang.Long idRed, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.Long idTerminal, java.lang.Long idGestion, java.lang.Boolean recepcionados, java.lang.Boolean todosRecepcionados, java.lang.Boolean controlados, java.lang.Boolean todosControlados, java.util.Date fechaDesde, java.util.Date fechaHasta) throws java.lang.Exception;

    public Integer totalFormulariosERA(Long idRed, Date fecha);

}
