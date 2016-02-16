/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import java.util.List;
import javax.ejb.Remote;

import py.com.konecta.redcobros.entities.ServicioRc;

/**
 *
 * @author konecta
 */
@Remote
public interface ServicioRcFacade extends GenericDao<ServicioRc, Integer>{

    public List<ServicioRc> getAllConsultable();

}
