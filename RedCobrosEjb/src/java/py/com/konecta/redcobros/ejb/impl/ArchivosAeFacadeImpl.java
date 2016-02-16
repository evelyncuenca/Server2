/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.ArchivosAeFacade;
import py.com.konecta.redcobros.entities.ArchivosAe;

/**
 *
 * @author konecta
 */
@Stateless
public class ArchivosAeFacadeImpl extends GenericDaoImpl<ArchivosAe, Long> implements ArchivosAeFacade {
}
