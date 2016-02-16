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
public class DatoConsultaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_FACTURADOR")
    private Long idFacturador;
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO")
    private Integer idServicio;
    @Basic(optional = false)
    @Column(name = "REFERENCIA_PAGO")
    private String referenciaPago;

    public DatoConsultaPK() {
    }

    public DatoConsultaPK(Long idFacturador, Integer idServicio, String referenciaPago) {
        this.idFacturador = idFacturador;
        this.idServicio = idServicio;
        this.referenciaPago = referenciaPago;
    }

    public Long getIdFacturador() {
        return idFacturador;
    }

    public void setIdFacturador(Long idFacturador) {
        this.idFacturador = idFacturador;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacturador != null ? idFacturador.hashCode() : 0);
        hash += (int) idServicio;
        hash += (referenciaPago != null ? referenciaPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatoConsultaPK)) {
            return false;
        }
        DatoConsultaPK other = (DatoConsultaPK) object;
        if ((this.idFacturador == null && other.idFacturador != null) || (this.idFacturador != null && !this.idFacturador.equals(other.idFacturador))) {
            return false;
        }
        if (this.idServicio != other.idServicio) {
            return false;
        }
        if ((this.referenciaPago == null && other.referenciaPago != null) || (this.referenciaPago != null && !this.referenciaPago.equals(other.referenciaPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DatoConsultaPK[idFacturador=" + idFacturador + ", idServicio=" + idServicio + ", referenciaPago=" + referenciaPago + "]";
    }

}
