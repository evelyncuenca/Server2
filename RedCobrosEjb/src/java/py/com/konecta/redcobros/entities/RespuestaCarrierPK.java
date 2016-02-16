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
public class RespuestaCarrierPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_RESPUESTA_CARRIER")
    private String idRespuestaCarrier;
    @Basic(optional = false)
    @Column(name = "ID_FACTURADOR")
    private BigInteger idFacturador;

    public RespuestaCarrierPK() {
    }

    public RespuestaCarrierPK(String idRespuestaCarrier, BigInteger idFacturador) {
        this.idRespuestaCarrier = idRespuestaCarrier;
        this.idFacturador = idFacturador;
    }

    public String getIdRespuestaCarrier() {
        return idRespuestaCarrier;
    }

    public void setIdRespuestaCarrier(String idRespuestaCarrier) {
        this.idRespuestaCarrier = idRespuestaCarrier;
    }

    public BigInteger getIdFacturador() {
        return idFacturador;
    }

    public void setIdFacturador(BigInteger idFacturador) {
        this.idFacturador = idFacturador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRespuestaCarrier != null ? idRespuestaCarrier.hashCode() : 0);
        hash += (idFacturador != null ? idFacturador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaCarrierPK)) {
            return false;
        }
        RespuestaCarrierPK other = (RespuestaCarrierPK) object;
        if ((this.idRespuestaCarrier == null && other.idRespuestaCarrier != null) || (this.idRespuestaCarrier != null && !this.idRespuestaCarrier.equals(other.idRespuestaCarrier))) {
            return false;
        }
        if ((this.idFacturador == null && other.idFacturador != null) || (this.idFacturador != null && !this.idFacturador.equals(other.idFacturador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RespuestaCarrierPK[idRespuestaCarrier=" + idRespuestaCarrier + ", idFacturador=" + idFacturador + "]";
    }

}
