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
 * @author konecta
 */
@Entity
@Table(name = "CONFIGURACION_COMISIONAL")
@NamedQueries({
    @NamedQuery(name = "ConfiguracionComisional.findAll", query = "SELECT c FROM ConfiguracionComisional c")})
public class ConfiguracionComisional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONFIGURACION_COMISIONAL")
    @GeneratedValue(generator = "configComisionalSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "configComisionalSeq", table = "SECUENCIA",
    allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
    pkColumnValue = "configComisionalSeq", initialValue = 1)
    private Long idConfiguracionComisional;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    /*
    @Column(name = "MONTO_DESDE")
    private Double montoDesde;
    @Column(name = "MONTO_HASTA")
    private Double montoHasta;
     */
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    @ManyToOne
    private Recaudador recaudador;
    @JoinColumn(name = "CONFIG_CLEARING_SERVICIO", referencedColumnName = "ID_CONFIG_CLEARING_SERVICIO")
    @ManyToOne(optional = false)
    private ConfigClearingServicio configClearingServicio;
    @JoinColumn(name = "SUCURSAL", referencedColumnName = "ID_SUCURSAL")
    @ManyToOne
    private Sucursal sucursal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configuracionComisional")
    private Collection<ConfigComisionalDetalle> configComisionalDetalleCollection;
    @Basic(optional = false)
    @Column(name = "HABILITADO")
    private String habilitado;

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public ConfiguracionComisional() {
    }

    public ConfiguracionComisional(Long idConfiguracionComisional) {
        this.idConfiguracionComisional = idConfiguracionComisional;
    }

    public Long getIdConfiguracionComisional() {
        return idConfiguracionComisional;
    }

    public void setIdConfiguracionComisional(Long idConfiguracionComisional) {
        this.idConfiguracionComisional = idConfiguracionComisional;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    /*
    public Double getMontoDesde() {
    return montoDesde;
    }

    public void setMontoDesde(Double montoDesde) {
    this.montoDesde = montoDesde;
    }

    public Double getMontoHasta() {
    return montoHasta;
    }

    public void setMontoHasta(Double montoHasta) {
    this.montoHasta = montoHasta;
    }
     */
    public Recaudador getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Recaudador recaudador) {
        this.recaudador = recaudador;
    }

    public ConfigClearingServicio getConfigClearingServicio() {
        return configClearingServicio;
    }

    public void setConfigClearingServicio(ConfigClearingServicio configClearingServicio) {
        this.configClearingServicio = configClearingServicio;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Collection<ConfigComisionalDetalle> getConfigComisionalDetalleCollection() {
        return configComisionalDetalleCollection;
    }

    public void setConfigComisionalDetalleCollection(Collection<ConfigComisionalDetalle> configComisionalDetalleCollection) {
        this.configComisionalDetalleCollection = configComisionalDetalleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracionComisional != null ? idConfiguracionComisional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfiguracionComisional)) {
            return false;
        }
        ConfiguracionComisional other = (ConfiguracionComisional) object;
        if ((this.idConfiguracionComisional == null && other.idConfiguracionComisional != null) || (this.idConfiguracionComisional != null && !this.idConfiguracionComisional.equals(other.idConfiguracionComisional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ConfiguracionComisional[idConfiguracionComisional=" + idConfiguracionComisional + "]";
    }
}
