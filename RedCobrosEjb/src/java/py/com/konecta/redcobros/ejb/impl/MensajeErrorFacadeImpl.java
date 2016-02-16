/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;


import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import py.com.konecta.redcobros.entities.MensajeError;

/**
 *
 * @author konecta
 */
@Stateless
public class MensajeErrorFacadeImpl extends GenericDaoImpl<MensajeError,Long> implements MensajeErrorFacade {
}
