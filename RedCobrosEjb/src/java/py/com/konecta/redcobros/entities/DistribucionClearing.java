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

/**
 *
 * @author gustavo
 */
@Entity
@Table(name = "DISTRIBUCION_CLEARING")
@NamedQueries({
    @NamedQuery(name = "DistribucionClearing.findAll", query = "SELECT d FROM DistribucionClearing d")})
public class DistribucionClearing implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DISTRIBUCION_CLEARING")
    @GeneratedValue(generator = "distribucionClearingSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "distribucionClearingSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="distribucionClearingSeq",initialValue=1)
    private Long idDistribucionClearing;
    @JoinColumn(name = "CLEARING", referencedColumnName = "ID_CLEARING")
    @ManyToOne(optional = false)
    private Clearing clearing;
    @OneToMany(mappedBy = "distribucionClearing",cascade=CascadeType.ALL)
    private Collection<DistribucionClearingDetalle> distribucionClearingDetalleCollection;
    @JoinColumn(name = "CONFIGURACION_COMISIONAL", referencedColumnName = "ID_CONFIGURACION_COMISIONAL")
    @ManyToOne(optional = false)
    private ConfiguracionComisional configuracionComisional;
    @Basic(optional = false)
    @Column(name = "MONTO_DISTRIBUCION")
    private Double montoDistribucion;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private Double cantidad;
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    @ManyToOne(optional = false)
    private Recaudador recaudador;
    @JoinColumn(name = "SUCURSAL", referencedColumnName = "ID_SUCURSAL")
    @ManyToOne
    private Sucursal sucursal;

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }


    public Recaudador getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Recaudador recaudador) {
        this.recaudador = recaudador;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }



    public Double getMontoDistribucion() {
        return montoDistribucion;
    }

    public void setMontoDistribucion(Double montoDistribucion) {
        this.montoDistribucion = montoDistribucion;
    }



    public Collection<DistribucionClearingDetalle> getDistribucionClearingDetalleCollection() {
        return distribucionClearingDetalleCollection;
    }

    public void setDistribucionClearingDetalleCollection(Collection<DistribucionClearingDetalle> distribucionClearingDetalleCollection) {
        this.distribucionClearingDetalleCollection = distribucionClearingDetalleCollection;
    }

    public DistribucionClearing() {
    }

    public DistribucionClearing(Long idDistribucionClearing) {
        this.idDistribucionClearing = idDistribucionClearing;
    }

    public Long getIdDistribucionClearing() {
        return idDistribucionClearing;
    }

    public void setIdDistribucionClearing(Long idDistribucionClearing) {
        this.idDistribucionClearing = idDistribucionClearing;
    }

    public Clearing getClearing() {
        return clearing;
    }

    public void setClearing(Clearing clearing) {
        this.clearing = clearing;
    }

    public ConfiguracionComisional getConfiguracionComisional() {
        return configuracionComisional;
    }

    public void setConfiguracionComisional(ConfiguracionComisional configuracionComisional) {
        this.configuracionComisional = configuracionComisional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistribucionClearing != null ? idDistribucionClearing.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistribucionClearing)) {
            return false;
        }
        DistribucionClearing other = (DistribucionClearing) object;
        if ((this.idDistribucionClearing == null && other.idDistribucionClearing != null) || (this.idDistribucionClearing != null && !this.idDistribucionClearing.equals(other.idDistribucionClearing))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DistribucionClearing[idDistribucionClearing=" + idDistribucionClearing + "]";
    }

}
