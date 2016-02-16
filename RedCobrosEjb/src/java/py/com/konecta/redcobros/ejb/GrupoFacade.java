/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Grupo;

/**
 *
 * @author konecta
 */
@Local
public interface GrupoFacade extends GenericDao<Grupo, Long> {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public py.com.konecta.redcobros.entities.Grupo obtenerGrupo(py.com.konecta.redcobros.entities.UsuarioTerminal ut, py.com.konecta.redcobros.entities.Servicio servicio) throws java.lang.Exception;

//    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
//    public void cerrarGruposRed(java.lang.Long idRed) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void cerrarGruposRedFacturador(java.lang.Long idRed, java.lang.Long idFacturador) throws java.lang.Exception;

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> reporteNumeroCajaDeGruposDDJJ(java.lang.Long idRed, java.util.Date fechaIni, java.util.Date fechaFin) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void cerrarGruposRed(java.lang.Long idRed, java.util.Date fecha) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void calcularNumeroCajaDeGruposDDJJ(java.lang.Long idRed, java.util.Date fechaIni, java.util.Date fechaFin) throws java.lang.Exception;

    public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> reporteNumeroCajaDeGruposDDJJDetallado(java.lang.Long idRed, java.util.Date fechaIni, java.util.Date fechaFin) throws java.lang.Exception;

    public java.util.List<py.com.konecta.redcobros.entities.Grupo> getGruposUsuarioFecha(java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Long idUsuario, java.lang.Integer start, java.lang.Integer limit);

    public java.lang.Integer getTotalGruposUsuarioFecha(java.util.Date fechaDesde, java.util.Date fechaHasta, java.lang.Long idUsuario);

}
