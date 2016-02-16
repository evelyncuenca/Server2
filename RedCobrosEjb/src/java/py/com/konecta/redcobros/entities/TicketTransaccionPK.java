/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author brojas
 */
@Embeddable
public class TicketTransaccionPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TRANSACCION")
    private Long idTransaccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO_OPERACION")
    private Short idTipoOperacion;

    public TicketTransaccionPK() {
    }

    public TicketTransaccionPK(Long idTransaccion, Short idTipoOperacion) {
        this.idTransaccion = idTransaccion;
        this.idTipoOperacion = idTipoOperacion;
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public short getIdTipoOperacion() {
        return idTipoOperacion;
    }

    public void setIdTipoOperacion(Short idTipoOperacion) {
        this.idTipoOperacion = idTipoOperacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaccion != null ? idTransaccion.hashCode() : 0);
        hash += (int) idTipoOperacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketTransaccionPK)) {
            return false;
        }
        TicketTransaccionPK other = (TicketTransaccionPK) object;
        if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
            return false;
        }
        if (this.idTipoOperacion != other.idTipoOperacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TicketTransaccionPK[ idTransaccion=" + idTransaccion + ", idTipoOperacion=" + idTipoOperacion + " ]";
    }
    
}
