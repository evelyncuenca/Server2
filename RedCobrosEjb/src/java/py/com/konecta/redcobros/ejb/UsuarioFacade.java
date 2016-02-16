/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Usuario;



/**
 *
 * @author Kiki
 */
@Local
public interface UsuarioFacade extends GenericDao<Usuario, Long> {

    public java.util.List<py.com.konecta.redcobros.entities.Usuario> obtenerSupervisoresNoAsignados(java.lang.Long idUsuario);

    public java.util.Collection<py.com.konecta.redcobros.entities.Usuario> obtenerSupervisoresAsignados(java.lang.Long idUsuario);

    public boolean esSupervisor(java.lang.Long idUsuario, java.lang.String nombreUsuarioSupervisor, java.lang.String passwordSupervisor);


}
