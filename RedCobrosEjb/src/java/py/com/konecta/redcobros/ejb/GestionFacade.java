/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Gestion;

/**
 *
 * @author konecta
 */
@Local
public interface GestionFacade extends GenericDao<Gestion, Long> {

    public java.lang.Integer countCriteriaComboFechaIniFechaFin(java.lang.Long idTerminal, java.lang.Long idUsuario, java.util.Date fechaIni, java.util.Date fechaFin);

    public java.util.List<py.com.konecta.redcobros.entities.Gestion> comboCriteriaComboFechaIniFechaFin(java.lang.Long idTerminal, java.lang.Long idUsuario, java.util.Date fechaIni, java.util.Date fechaFin, java.lang.Integer primerResultado, java.lang.Integer cantResultados);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public py.com.konecta.redcobros.entities.Gestion obtenerGestionDelDia(java.lang.Long idUsuarioTerminal, java.lang.Boolean aperturaSimple) throws java.lang.Exception;

    public boolean tieneGestionesAbiertasDeDiasAnteriores(py.com.konecta.redcobros.entities.UsuarioTerminal ut);

    public boolean tieneGestionesAbiertas(py.com.konecta.redcobros.entities.UsuarioTerminal ut, java.util.Date fecha);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void cerrarGestion(java.lang.Long idGestion) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public py.com.konecta.redcobros.entities.Gestion cerrarGestiones(py.com.konecta.redcobros.entities.UsuarioTerminal ut) throws java.lang.Exception;



}
