/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "SERVICIO_CARRIER")
@NamedQueries({@NamedQuery(name = "ServicioCarrier.findAll", query = "SELECT s FROM ServicioCarrier s"), @NamedQuery(name = "ServicioCarrier.findByIdServicioCarrier", query = "SELECT s FROM ServicioCarrier s WHERE s.servicioCarrierPK.idServicioCarrier = :idServicioCarrier"), @NamedQuery(name = "ServicioCarrier.findByIdServicio", query = "SELECT s FROM ServicioCarrier s WHERE s.servicioCarrierPK.idServicio = :idServicio"), @NamedQuery(name = "ServicioCarrier.findByDescripcion", query = "SELECT s FROM ServicioCarrier s WHERE s.descripcion = :descripcion")})
public class ServicioCarrier implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ServicioCarrierPK servicioCarrierPK;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ServicioRc servicioRc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicioCarrier")
    private Collection<DetalleEnLinea> detalleEnLineaCollection;

    public ServicioCarrier() {
    }

    public ServicioCarrier(ServicioCarrierPK servicioCarrierPK) {
        this.servicioCarrierPK = servicioCarrierPK;
    }

    public ServicioCarrier(ServicioCarrierPK servicioCarrierPK, String descripcion) {
        this.servicioCarrierPK = servicioCarrierPK;
        this.descripcion = descripcion;
    }

    public ServicioCarrier(short idServicioCarrier, int idServicio) {
        this.servicioCarrierPK = new ServicioCarrierPK(idServicioCarrier, idServicio);
    }

    public ServicioCarrierPK getServicioCarrierPK() {
        return servicioCarrierPK;
    }

    public void setServicioCarrierPK(ServicioCarrierPK servicioCarrierPK) {
        this.servicioCarrierPK = servicioCarrierPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ServicioRc getServicioRc() {
        return servicioRc;
    }

    public void setServicioRc(ServicioRc servicioRc) {
        this.servicioRc = servicioRc;
    }

    public Collection<DetalleEnLinea> getDetalleEnLineaCollection() {
        return detalleEnLineaCollection;
    }

    public void setDetalleEnLineaCollection(Collection<DetalleEnLinea> detalleEnLineaCollection) {
        this.detalleEnLineaCollection = detalleEnLineaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servicioCarrierPK != null ? servicioCarrierPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioCarrier)) {
            return false;
        }
        ServicioCarrier other = (ServicioCarrier) object;
        if ((this.servicioCarrierPK == null && other.servicioCarrierPK != null) || (this.servicioCarrierPK != null && !this.servicioCarrierPK.equals(other.servicioCarrierPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ServicioCarrier[servicioCarrierPK=" + servicioCarrierPK + "]";
    }

}
