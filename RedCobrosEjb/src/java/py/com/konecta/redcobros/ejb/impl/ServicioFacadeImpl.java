/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.ServicioFacade;
import py.com.konecta.redcobros.entities.Servicio;

/**
 *
 * @author konecta
 */
@Stateless
public class ServicioFacadeImpl extends GenericDaoImpl<Servicio, Long> implements ServicioFacade {

   

//    public boolean comprobarHabilitacionRecaudadorServicio(Integer idServicio, Integer idRecaudador) {
//        Recaudador rec = recaudadorFacade.get(idRecaudador);
//
//        return rec.getServicioCollection().contains(new Servicio(idServicio));
//    }
}
