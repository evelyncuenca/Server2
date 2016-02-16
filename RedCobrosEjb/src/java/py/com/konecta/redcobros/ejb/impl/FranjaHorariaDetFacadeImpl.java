/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.List;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import py.com.konecta.redcobros.entities.FranjaHorariaDet;
import py.com.konecta.redcobros.entities.UsuarioTerminal;

/**
 *
 * @author konecta
 */
@Stateless
public class FranjaHorariaDetFacadeImpl extends GenericDaoImpl<FranjaHorariaDet, Long> implements FranjaHorariaDetFacade {
        public List<FranjaHorariaDet> getDetalleFranjaHoraria(UsuarioTerminal usuarioTerminal) {

        FranjaHorariaDet fdExample = new FranjaHorariaDet();
        fdExample.setFranjaHorariaCab(usuarioTerminal.getFranjaHorariaCab());
        List<FranjaHorariaDet> lFranjaHorariaDetalle = this.list(fdExample);
        return lFranjaHorariaDetalle;
    }
}
