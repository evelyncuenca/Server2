/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import py.com.konecta.redcobros.entities.Evento;

/**
 *
 * @author konecta
 */
@Stateless
public class EventoFacadeImpl extends GenericDaoImpl<Evento, Long> implements EventoFacade {
}
