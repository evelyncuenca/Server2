/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.RuteoServicio;

/**
 *
 * @author documenta
 */
@Remote
public interface RuteoServicioFacade extends GenericDao<RuteoServicio,Long>{

}