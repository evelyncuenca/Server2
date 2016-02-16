/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "RESPUESTA_CARRIER")
@NamedQueries({@NamedQuery(name = "RespuestaCarrier.findAll", query = "SELECT r FROM RespuestaCarrier r"), @NamedQuery(name = "RespuestaCarrier.findByIdRespuestaCarrier", query = "SELECT r FROM RespuestaCarrier r WHERE r.respuestaCarrierPK.idRespuestaCarrier = :idRespuestaCarrier"), @NamedQuery(name = "RespuestaCarrier.findByIdFacturador", query = "SELECT r FROM RespuestaCarrier r WHERE r.respuestaCarrierPK.idFacturador = :idFacturador"), @NamedQuery(name = "RespuestaCarrier.findByAprobado", query = "SELECT r FROM RespuestaCarrier r WHERE r.aprobado = :aprobado"), @NamedQuery(name = "RespuestaCarrier.findByDescripcion", query = "SELECT r FROM RespuestaCarrier r WHERE r.descripcion = :descripcion")})
public class RespuestaCarrier implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespuestaCarrierPK respuestaCarrierPK;
    @Basic(optional = false)
    @Column(name = "APROBADO")
    private char aprobado;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_FACTURADOR", referencedColumnName = "ID_FACTURADOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Facturador facturador;

    public RespuestaCarrier() {
    }

    public RespuestaCarrier(RespuestaCarrierPK respuestaCarrierPK) {
        this.respuestaCarrierPK = respuestaCarrierPK;
    }

    public RespuestaCarrier(RespuestaCarrierPK respuestaCarrierPK, char aprobado, String descripcion) {
        this.respuestaCarrierPK = respuestaCarrierPK;
        this.aprobado = aprobado;
        this.descripcion = descripcion;
    }

    public RespuestaCarrier(String idRespuestaCarrier, BigInteger idFacturador) {
        this.respuestaCarrierPK = new RespuestaCarrierPK(idRespuestaCarrier, idFacturador);
    }

    public RespuestaCarrierPK getRespuestaCarrierPK() {
        return respuestaCarrierPK;
    }

    public void setRespuestaCarrierPK(RespuestaCarrierPK respuestaCarrierPK) {
        this.respuestaCarrierPK = respuestaCarrierPK;
    }

    public char getAprobado() {
        return aprobado;
    }

    public void setAprobado(char aprobado) {
        this.aprobado = aprobado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Facturador getFacturador() {
        return facturador;
    }

    public void setFacturador(Facturador facturador) {
        this.facturador = facturador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaCarrierPK != null ? respuestaCarrierPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaCarrier)) {
            return false;
        }
        RespuestaCarrier other = (RespuestaCarrier) object;
        if ((this.respuestaCarrierPK == null && other.respuestaCarrierPK != null) || (this.respuestaCarrierPK != null && !this.respuestaCarrierPK.equals(other.respuestaCarrierPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RespuestaCarrier[respuestaCarrierPK=" + respuestaCarrierPK + "]";
    }

}
