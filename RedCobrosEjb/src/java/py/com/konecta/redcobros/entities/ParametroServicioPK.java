/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author konecta
 */
@Embeddable
public class ParametroServicioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO")
    private int idServicio;
    @Basic(optional = false)
    @Column(name = "CLAVE")
    private String clave;

    public ParametroServicioPK() {
    }

    public ParametroServicioPK(int idServicio, String clave) {
        this.idServicio = idServicio;
        this.clave = clave;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idServicio;
        hash += (clave != null ? clave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroServicioPK)) {
            return false;
        }
        ParametroServicioPK other = (ParametroServicioPK) object;
        if (this.idServicio != other.idServicio) {
            return false;
        }
        if ((this.clave == null && other.clave != null) || (this.clave != null && !this.clave.equals(other.clave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ParametroServicioPK[idServicio=" + idServicio + ", clave=" + clave + "]";
    }

}
