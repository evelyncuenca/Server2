/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import py.com.konecta.redcobros.ejb.UsuarioFacade;
import py.com.konecta.redcobros.entities.Usuario;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author Kiki
 */
@Stateless
public class UsuarioFacadeImpl extends GenericDaoImpl<Usuario, Long> implements UsuarioFacade {

    public List<Usuario> obtenerSupervisoresNoAsignados(Long idUsuario) {
        Usuario usuario = this.get(idUsuario);
        Usuario usuarioEjemplo = new Usuario();
        usuarioEjemplo.setRecaudador(usuario.getRecaudador());
        usuarioEjemplo.setEsSupervisor("S");
        List<Usuario> lista = this.list(usuarioEjemplo);
        lista.removeAll(usuario.getSupervisoresCollection());
        //elimino al usuario mismo de la lista
        lista.remove(usuario);
        return lista;
    }

    public Collection<Usuario> obtenerSupervisoresAsignados(Long idUsuario) {
        Collection<Usuario> lista = new ArrayList<Usuario>();
        Usuario u = this.get(idUsuario);
        lista.addAll(u.getSupervisoresCollection());
        return lista;
    }

    public boolean esSupervisor(Long idUsuario,
            String nombreUsuarioSupervisor,
            String passwordSupervisor) {
        boolean esSupervisor;
        try {
            Usuario u = this.get(idUsuario);
            if (u.getEsSupervisor().equalsIgnoreCase("S")) {
                if (nombreUsuarioSupervisor.equals(u.getNombreUsuario())
                        && UtilesSet.getSha1(passwordSupervisor).equals(u.getContrasenha())) {
                    return true;
                } else {
                    return false;
                }
            }

            Usuario usuarioSupervisor = new Usuario();
            usuarioSupervisor.setNombreUsuario(nombreUsuarioSupervisor);
            usuarioSupervisor.setContrasenha(UtilesSet.getSha1(passwordSupervisor));

            usuarioSupervisor = this.get(usuarioSupervisor);

            if (usuarioSupervisor != null && usuarioSupervisor.getEsSupervisor().equals("S") && u.getSupervisoresCollection().contains(usuarioSupervisor)) {
                esSupervisor = true;
            } else {
                esSupervisor = false;
            }
        } catch (Exception exc) {
            esSupervisor = false;
            exc.printStackTrace();
        }
        return esSupervisor;
    }
}
