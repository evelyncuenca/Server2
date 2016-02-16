/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.Evento;

/**
 *
 * @author konecta
 */
@Remote
public interface EventoFacade extends GenericDao<Evento, Long> {
}
