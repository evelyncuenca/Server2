/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CLEARING")
@NamedQueries({@NamedQuery(name = "Clearing.findAll", query = "SELECT c FROM Clearing c")})
public class Clearing implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CLEARING")
    @GeneratedValue(generator = "clearingSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "clearingSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="clearingSeq",initialValue=1)
    private Long idClearing;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCreacion;
    @JoinColumn(name = "CONFIG_CLEARING_SERVICIO", referencedColumnName = "ID_CONFIG_CLEARING_SERVICIO")
    @ManyToOne(optional = false)
    private ConfigClearingServicio configClearingServicio;
    @OneToMany(mappedBy = "clearing",cascade=CascadeType.ALL)
    private Collection<DistribucionClearing> distribucionClearingCollection;
    @Basic(optional = false)
    @Column(name = "MONTO_DISTRIBUIDO")
    private Double montoDistribuido;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private Double cantidad;
    
    

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getMontoDistribuido() {
        return montoDistribuido;
    }

    public void setMontoDistribuido(Double montoDistribuido) {
        this.montoDistribuido = montoDistribuido;
    }



    public ConfigClearingServicio getConfigClearingServicio() {
        return configClearingServicio;
    }

    public void setConfigClearingServicio(ConfigClearingServicio configClearingServicio) {
        this.configClearingServicio = configClearingServicio;
    }




    public Clearing() {
    }

    public Clearing(Long idClearing) {
        this.idClearing = idClearing;
    }

    public Clearing(Long idClearing, Date fecha, Date fechaHoraCreacion) {
        this.idClearing = idClearing;
        this.fecha = fecha;
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Long getIdClearing() {
        return idClearing;
    }

    public void setIdClearing(Long idClearing) {
        this.idClearing = idClearing;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Collection<DistribucionClearing> getDistribucionClearingCollection() {
        return distribucionClearingCollection;
    }

    public void setDistribucionClearingCollection(Collection<DistribucionClearing> distribucionClearingCollection) {
        this.distribucionClearingCollection = distribucionClearingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClearing != null ? idClearing.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clearing)) {
            return false;
        }
        Clearing other = (Clearing) object;
        if ((this.idClearing == null && other.idClearing != null) || (this.idClearing != null && !this.idClearing.equals(other.idClearing))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Clearing[idClearing=" + idClearing + "]";
    }

}
