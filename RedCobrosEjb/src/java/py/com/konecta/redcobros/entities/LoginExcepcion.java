/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fgonzalez
 */
@Entity
@Table(name = "LOGIN_EXCEPCION")
@NamedQueries({
    @NamedQuery(name = "LoginExcepcion.findAll", query = "SELECT l FROM LoginExcepcion l"),
    @NamedQuery(name = "LoginExcepcion.findByRecaudador", query = "SELECT l FROM LoginExcepcion l WHERE l.recaudador = :recaudador"),
    @NamedQuery(name = "LoginExcepcion.findByHabilitado", query = "SELECT l FROM LoginExcepcion l WHERE l.habilitado = :habilitado")})
public class LoginExcepcion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECAUDADOR")
    private Long recaudador;
    @Size(max = 1)
    @Column(name = "HABILITADO")
    private String habilitado;

    public LoginExcepcion() {
    }

    public LoginExcepcion(Long recaudador) {
        this.recaudador = recaudador;
    }

    public Long getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Long recaudador) {
        this.recaudador = recaudador;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recaudador != null ? recaudador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginExcepcion)) {
            return false;
        }
        LoginExcepcion other = (LoginExcepcion) object;
        if ((this.recaudador == null && other.recaudador != null) || (this.recaudador != null && !this.recaudador.equals(other.recaudador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.LoginExcepcion[ recaudador=" + recaudador + " ]";
    }
    
}
