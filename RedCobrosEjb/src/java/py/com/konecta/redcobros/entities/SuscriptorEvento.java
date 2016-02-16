/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author documenta
 */
@Entity
@Table(name = "SUSCRIPTOR_EVENTO")
@NamedQueries({
    @NamedQuery(name = "SuscriptorEvento.findAll", query = "SELECT s FROM SuscriptorEvento s")})
public class SuscriptorEvento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SuscriptorEventoPK suscriptorEventoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_SUSCRIPCION")
    @Temporal(TemporalType.DATE)
    private Date fechaSuscripcion;
    @JoinColumn(name = "SUSCRIPTOR", referencedColumnName = "ID_SUSCRIPTOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Suscriptor suscriptor;
    @JoinColumn(name = "SECTOR", referencedColumnName = "ID_SECTOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SectorEvento sectorEvento;

    public SuscriptorEvento() {
    }

    public SuscriptorEvento(SuscriptorEventoPK suscriptorEventoPK) {
        this.suscriptorEventoPK = suscriptorEventoPK;
    }

    public SuscriptorEvento(SuscriptorEventoPK suscriptorEventoPK, Date fechaSuscripcion) {
        this.suscriptorEventoPK = suscriptorEventoPK;
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public SuscriptorEvento(Long ci, Integer sector) {
        this.suscriptorEventoPK = new SuscriptorEventoPK(ci, sector);
    }

    public SuscriptorEventoPK getSuscriptorEventoPK() {
        return suscriptorEventoPK;
    }

    public void setSuscriptorEventoPK(SuscriptorEventoPK suscriptorEventoPK) {
        this.suscriptorEventoPK = suscriptorEventoPK;
    }

    public Date getFechaSuscripcion() {
        return fechaSuscripcion;
    }

    public void setFechaSuscripcion(Date fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public Suscriptor getSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(Suscriptor suscriptor) {
        this.suscriptor = suscriptor;
    }

    public SectorEvento getSectorEvento() {
        return sectorEvento;
    }

    public void setSectorEvento(SectorEvento sectorEvento) {
        this.sectorEvento = sectorEvento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suscriptorEventoPK != null ? suscriptorEventoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuscriptorEvento)) {
            return false;
        }
        SuscriptorEvento other = (SuscriptorEvento) object;
        if ((this.suscriptorEventoPK == null && other.suscriptorEventoPK != null) || (this.suscriptorEventoPK != null && !this.suscriptorEventoPK.equals(other.suscriptorEventoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.SuscriptorEvento[ suscriptorEventoPK=" + suscriptorEventoPK + " ]";
    }
    
}
