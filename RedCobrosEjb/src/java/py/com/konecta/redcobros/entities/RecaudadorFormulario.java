/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fgonzalez
 */
@Entity
@Table(name = "RECAUDADOR_FORMULARIO")
@NamedQueries({
    @NamedQuery(name = "RecaudadorFormulario.findAll", query = "SELECT r FROM RecaudadorFormulario r"),
    @NamedQuery(name = "RecaudadorFormulario.findByRecaudador", query = "SELECT r FROM RecaudadorFormulario r WHERE r.recaudador = :recaudador"),
    @NamedQuery(name = "RecaudadorFormulario.findByMonto", query = "SELECT r FROM RecaudadorFormulario r WHERE r.monto = :monto")})
public class RecaudadorFormulario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECAUDADOR")
    private Long recaudador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private Integer monto;

    public RecaudadorFormulario() {
    }

    public RecaudadorFormulario(Long recaudador) {
        this.recaudador = recaudador;
    }

    public RecaudadorFormulario(Long recaudador, Integer monto) {
        this.recaudador = recaudador;
        this.monto = monto;
    }

    public Long getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Long recaudador) {
        this.recaudador = recaudador;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
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
        if (!(object instanceof RecaudadorFormulario)) {
            return false;
        }
        RecaudadorFormulario other = (RecaudadorFormulario) object;
        if ((this.recaudador == null && other.recaudador != null) || (this.recaudador != null && !this.recaudador.equals(other.recaudador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RecaudadorFormulario[ recaudador=" + recaudador + " ]";
    }
}
