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
public class ServicioCarrierPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO_CARRIER")
    private short idServicioCarrier;
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO")
    private int idServicio;

    public ServicioCarrierPK(short idServicioCarrier, int idServicio) {
        this.idServicioCarrier = idServicioCarrier;
        this.idServicio = idServicio;
    }

    public short getIdServicioCarrier() {
        return idServicioCarrier;
    }

    public void setIdServicioCarrier(short idServicioCarrier) {
        this.idServicioCarrier = idServicioCarrier;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idServicioCarrier;
        hash += (int) idServicio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioCarrierPK)) {
            return false;
        }
        ServicioCarrierPK other = (ServicioCarrierPK) object;
        if (this.idServicioCarrier != other.idServicioCarrier) {
            return false;
        }
        if (this.idServicio != other.idServicio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ServicioCarrierPK[idServicioCarrier=" + idServicioCarrier + ", idServicio=" + idServicio + "]";
    }

}
