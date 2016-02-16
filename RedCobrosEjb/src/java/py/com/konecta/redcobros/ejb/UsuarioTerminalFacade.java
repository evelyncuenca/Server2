/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import java.util.List;
import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.Terminal;
import py.com.konecta.redcobros.entities.Usuario;
import py.com.konecta.redcobros.entities.UsuarioTerminal;
import py.com.konecta.redcobros.utiles.ResultadoControlFranjaHoraria;




/**
 *
 * @author Kiki
 */
@Remote
public interface UsuarioTerminalFacade extends GenericDao<UsuarioTerminal, Long> {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public py.com.konecta.redcobros.entities.UsuarioTerminal auntenticarGestion(java.lang.String user, java.lang.String passwd, java.lang.String mac);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public py.com.konecta.redcobros.utiles.ResultadoAutenticarCobrosWeb auntenticar(java.lang.String user, java.lang.String passwd, java.lang.String hash, java.lang.String appAutenticar);

    public List<Terminal> getUsuarioLogeadosTerminal(Usuario usuario);

    public boolean controlFranjaHoraria(java.util.List<py.com.konecta.redcobros.entities.FranjaHorariaDet> lFranjaHorariaDetalle) throws java.text.ParseException;

    public ResultadoControlFranjaHoraria controlFranjaHorariaTimeToEnd(java.util.List<py.com.konecta.redcobros.entities.FranjaHorariaDet> lFranjaHorariaDetalle) throws java.text.ParseException;

}
