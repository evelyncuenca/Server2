/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author documenta
 */
@Entity
@Table(name = "SECTOR_EVENTO")
@NamedQueries({
    @NamedQuery(name = "SectorEvento.findAll", query = "SELECT s FROM SectorEvento s"),
    @NamedQuery(name = "SectorEvento.findByIdSector", query = "SELECT s FROM SectorEvento s WHERE s.idSector = :idSector"),
    @NamedQuery(name = "SectorEvento.findByDescripcion", query = "SELECT s FROM SectorEvento s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SectorEvento.findByMonto", query = "SELECT s FROM SectorEvento s WHERE s.monto = :monto")})
public class SectorEvento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SECTOR")
    private Integer idSector;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private BigDecimal monto;
    @JoinColumn(name = "EVENTO", referencedColumnName = "ID_EVENTO")
    @ManyToOne(optional = false)
    private Evento evento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sectorEvento")
    private List<SuscriptorEvento> suscriptorEventoList;

    public SectorEvento() {
    }

    public SectorEvento(Integer idSector) {
        this.idSector = idSector;
    }

    public SectorEvento(Integer idSector, String descripcion, BigDecimal monto) {
        this.idSector = idSector;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public Integer getIdSector() {
        return idSector;
    }

    public void setIdSector(Integer idSector) {
        this.idSector = idSector;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<SuscriptorEvento> getSuscriptorEventoList() {
        return suscriptorEventoList;
    }

    public void setSuscriptorEventoList(List<SuscriptorEvento> suscriptorEventoList) {
        this.suscriptorEventoList = suscriptorEventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSector != null ? idSector.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SectorEvento)) {
            return false;
        }
        SectorEvento other = (SectorEvento) object;
        if ((this.idSector == null && other.idSector != null) || (this.idSector != null && !this.idSector.equals(other.idSector))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.SectorEvento[ idSector=" + idSector + " ]";
    }
    
}
