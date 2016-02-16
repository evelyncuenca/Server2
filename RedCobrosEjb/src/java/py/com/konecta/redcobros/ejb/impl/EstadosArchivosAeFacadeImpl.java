/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.EstadosArchivosAeFacade;
import py.com.konecta.redcobros.entities.EstadosArchivosAe;

/**
 *
 * @author konecta
 */
@Stateless
public class EstadosArchivosAeFacadeImpl extends GenericDaoImpl<EstadosArchivosAe, Long> implements EstadosArchivosAeFacade {
}
