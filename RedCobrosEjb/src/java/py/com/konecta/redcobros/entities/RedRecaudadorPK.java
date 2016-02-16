/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
//import javax.validation.constraints.NotNull;

/**
 *
 * @author konecta
 */
@Embeddable
public class RedRecaudadorPK implements Serializable {
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ID_RED")
    private Long idRed;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "ID_RECAUDADOR")
    private Long idRecaudador;

    public RedRecaudadorPK() {
    }

    public RedRecaudadorPK(Long idRed, Long idRecaudador) {
        this.idRed = idRed;
        this.idRecaudador = idRecaudador;
    }

    public Long getIdRed() {
        return idRed;
    }

    public void setIdRed(Long idRed) {
        this.idRed = idRed;
    }

    public Long getIdRecaudador() {
        return idRecaudador;
    }

    public void setIdRecaudador(Long idRecaudador) {
        this.idRecaudador = idRecaudador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRed != null ? idRed.hashCode() : 0);
        hash += (idRecaudador != null ? idRecaudador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RedRecaudadorPK)) {
            return false;
        }
        RedRecaudadorPK other = (RedRecaudadorPK) object;
        if ((this.idRed == null && other.idRed != null) || (this.idRed != null && !this.idRed.equals(other.idRed))) {
            return false;
        }
        if ((this.idRecaudador == null && other.idRecaudador != null) || (this.idRecaudador != null && !this.idRecaudador.equals(other.idRecaudador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RedRecaudadorPK[ idRed=" + idRed + ", idRecaudador=" + idRecaudador + " ]";
    }
    
}
