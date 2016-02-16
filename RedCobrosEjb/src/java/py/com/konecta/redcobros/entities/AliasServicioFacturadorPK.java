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
public class AliasServicioFacturadorPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_FACTURADOR")
    private BigInteger idFacturador;
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO")
    private Integer idServicio;

    public AliasServicioFacturadorPK() {
    }

    public AliasServicioFacturadorPK(BigInteger idFacturador, Integer idServicio) {
        this.idFacturador = idFacturador;
        this.idServicio = idServicio;
    }

    public BigInteger getIdFacturador() {
        return idFacturador;
    }

    public void setIdFacturador(BigInteger idFacturador) {
        this.idFacturador = idFacturador;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacturador != null ? idFacturador.hashCode() : 0);
        hash += (int) idServicio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AliasServicioFacturadorPK)) {
            return false;
        }
        AliasServicioFacturadorPK other = (AliasServicioFacturadorPK) object;
        if ((this.idFacturador == null && other.idFacturador != null) || (this.idFacturador != null && !this.idFacturador.equals(other.idFacturador))) {
            return false;
        }
        if (this.idServicio != other.idServicio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.AliasServicioFacturadorPK[idFacturador=" + idFacturador + ", idServicio=" + idServicio + "]";
    }

}
