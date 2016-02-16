/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Embeddable
public class DetalleEnLineaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_EN_LINEA")
    private BigInteger idDetalleEnLinea;
    @Basic(optional = false)
    @Column(name = "FECHA_INGRESO")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;

    public DetalleEnLineaPK(BigInteger idDetalleEnLinea, Date fechaIngreso) {
        this.idDetalleEnLinea = idDetalleEnLinea;
        this.fechaIngreso = fechaIngreso;
    }

    public BigInteger getIdDetalleEnLinea() {
        return idDetalleEnLinea;
    }

    public void setIdDetalleEnLinea(BigInteger idDetalleEnLinea) {
        this.idDetalleEnLinea = idDetalleEnLinea;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleEnLinea != null ? idDetalleEnLinea.hashCode() : 0);
        hash += (fechaIngreso != null ? fechaIngreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleEnLineaPK)) {
            return false;
        }
        DetalleEnLineaPK other = (DetalleEnLineaPK) object;
        if ((this.idDetalleEnLinea == null && other.idDetalleEnLinea != null) || (this.idDetalleEnLinea != null && !this.idDetalleEnLinea.equals(other.idDetalleEnLinea))) {
            return false;
        }
        if ((this.fechaIngreso == null && other.fechaIngreso != null) || (this.fechaIngreso != null && !this.fechaIngreso.equals(other.fechaIngreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DetalleEnLineaPK[idDetalleEnLinea=" + idDetalleEnLinea + ", fechaIngreso=" + fechaIngreso + "]";
    }

}
