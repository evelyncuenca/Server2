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
public class HabilitacionFactRedPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "RED")
    private Long red;
    @Basic(optional = false)
    @Column(name = "FACTURADOR")
    private Long facturador;

    public HabilitacionFactRedPK(Long red, Long facturador) {
        this.red = red;
        this.facturador = facturador;
    }

    public HabilitacionFactRedPK() {
    }

    public Long getRed() {
        return red;
    }

    public void setRed(Long red) {
        this.red = red;
    }

    public Long getFacturador() {
        return facturador;
    }

    public void setFacturador(Long facturador) {
        this.facturador = facturador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (red != null ? red.hashCode() : 0);
        hash += (facturador != null ? facturador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HabilitacionFactRedPK)) {
            return false;
        }
        HabilitacionFactRedPK other = (HabilitacionFactRedPK) object;
        if ((this.red == null && other.red != null) || (this.red != null && !this.red.equals(other.red))) {
            return false;
        }
        if ((this.facturador == null && other.facturador != null) || (this.facturador != null && !this.facturador.equals(other.facturador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.HabilitacionFactRedPK[red=" + red + ", facturador=" + facturador + "]";
    }

}
