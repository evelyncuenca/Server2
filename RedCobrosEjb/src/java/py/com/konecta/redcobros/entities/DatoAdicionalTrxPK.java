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

/**
 *
 * @author konecta
 */
@Embeddable
public class DatoAdicionalTrxPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_TRANSACCION")
    private BigInteger idTransaccion;
    @Basic(optional = false)
    @Column(name = "ID_TIPO_ADICIONAL")
    private String idTipoAdicional;

    public DatoAdicionalTrxPK(BigInteger idTransaccion, String idTipoAdicional) {
        this.idTransaccion = idTransaccion;
        this.idTipoAdicional = idTipoAdicional;
    }

    public BigInteger getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(BigInteger idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getIdTipoAdicional() {
        return idTipoAdicional;
    }

    public void setIdTipoAdicional(String idTipoAdicional) {
        this.idTipoAdicional = idTipoAdicional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaccion != null ? idTransaccion.hashCode() : 0);
        hash += (idTipoAdicional != null ? idTipoAdicional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatoAdicionalTrxPK)) {
            return false;
        }
        DatoAdicionalTrxPK other = (DatoAdicionalTrxPK) object;
        if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
            return false;
        }
        if ((this.idTipoAdicional == null && other.idTipoAdicional != null) || (this.idTipoAdicional != null && !this.idTipoAdicional.equals(other.idTipoAdicional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DatoAdicionalTrxPK[idTransaccion=" + idTransaccion + ", idTipoAdicional=" + idTipoAdicional + "]";
    }

}
