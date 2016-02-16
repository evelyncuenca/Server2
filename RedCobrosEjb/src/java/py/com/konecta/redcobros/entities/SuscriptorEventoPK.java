/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author documenta
 */
@Embeddable
public class SuscriptorEventoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUSCRIPTOR")
    private Long suscriptor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECTOR")
    private Integer sector;

    public SuscriptorEventoPK() {
    }

    public SuscriptorEventoPK(Long ci, Integer sector) {
        this.suscriptor = ci;
        this.sector = sector;
    }

    public Long getCi() {
        return suscriptor;
    }

    public void setCi(Long ci) {
        this.suscriptor = ci;
    }

    public Integer getSector() {
        return sector;
    }

    public void setSector(Integer sector) {
        this.sector = sector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (long) suscriptor;
        hash += (int) sector;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuscriptorEventoPK)) {
            return false;
        }
        SuscriptorEventoPK other = (SuscriptorEventoPK) object;
        if (this.suscriptor != other.suscriptor) {
            return false;
        }
        if (this.sector != other.sector) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.SuscriptorEventoPK[ ci=" + suscriptor + ", sector=" + sector + " ]";
    }
    
}
