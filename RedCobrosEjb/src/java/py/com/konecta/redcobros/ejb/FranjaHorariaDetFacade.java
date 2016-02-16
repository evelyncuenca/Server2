/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.FranjaHorariaDet;

/**
 *
 * @author konecta
 */
@Local
public interface FranjaHorariaDetFacade extends GenericDao<FranjaHorariaDet, Long> {

    public java.util.List<py.com.konecta.redcobros.entities.FranjaHorariaDet> getDetalleFranjaHoraria(py.com.konecta.redcobros.entities.UsuarioTerminal usuarioTerminal);


}
