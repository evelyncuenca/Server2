/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CONFIG_CLEARING_SERVICIO")
@NamedQueries({
    @NamedQuery(name = "ConfigClearingServicio.findAll", query = "SELECT c FROM ConfigClearingServicio c")})
public class ConfigClearingServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONFIG_CLEARING_SERVICIO")
    @GeneratedValue(generator = "configClearingServicioSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "configClearingServicioSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="configClearingServicioSeq",initialValue=1)
    private Long idConfigClearingServicio;

    @Column(name = "DESDE")
    private Double desde;
 
    @Column(name = "HASTA")
    private Double hasta;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private Double valor;
    @JoinColumn(name = "RED", referencedColumnName = "ID_RED")
    @ManyToOne(optional = false)
    private Red red;
    @JoinColumn(name = "SERVICIO", referencedColumnName = "ID_SERVICIO")
    @ManyToOne
    private Servicio servicio;
    @JoinColumn(name = "SERVICIO_RC", referencedColumnName = "ID_SERVICIO")
    @ManyToOne
    private ServicioRc servicioRc;
    @JoinColumn(name = "TIPO_CLEARING", referencedColumnName = "ID_TIPO_CLEARING")
    @ManyToOne(optional = false)
    private TipoClearing tipoClearing;
    @OneToMany(mappedBy = "configClearingServicio")
    private Collection<ConfiguracionComisional> configuracionComisionalCollection;
    @Basic(optional = false)
    @Column(name = "HABILITADO")
    private String habilitado;
    @Column(name = "MONTO", precision = 19, scale = 2)
    private BigDecimal monto;

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }



    public ConfigClearingServicio() {
    }

   public ConfigClearingServicio(Long idConfigClearingServicio) {
       this.idConfigClearingServicio=idConfigClearingServicio;
    }

    public Long getIdConfigClearingServicio() {
        return idConfigClearingServicio;
    }

    public void setIdConfigClearingServicio(Long idConfigClearingServicio) {
        this.idConfigClearingServicio = idConfigClearingServicio;
    }

    public Double getDesde() {
        return desde;
    }

    public void setDesde(Double desde) {
        this.desde = desde;
    }

    public Double getHasta() {
        return hasta;
    }

    public void setHasta(Double hasta) {
        this.hasta = hasta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public TipoClearing getTipoClearing() {
        return tipoClearing;
    }

    public void setTipoClearing(TipoClearing tipoClearing) {
        this.tipoClearing = tipoClearing;
    }

    public Collection<ConfiguracionComisional> getConfiguracionComisionalCollection() {
        return configuracionComisionalCollection;
    }

    public void setConfiguracionComisionalCollection(Collection<ConfiguracionComisional> configuracionComisionalCollection) {
        this.configuracionComisionalCollection = configuracionComisionalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfigClearingServicio != null ? idConfigClearingServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigClearingServicio)) {
            return false;
        }
        ConfigClearingServicio other = (ConfigClearingServicio) object;
        if ((this.idConfigClearingServicio == null && other.idConfigClearingServicio != null) || (this.idConfigClearingServicio != null && !this.idConfigClearingServicio.equals(other.idConfigClearingServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "red="+this.red.getDescripcion()+";;"+
               "servicio="+this.servicio.getDescripcion()+";;"+
               "tipoClearing="+this.tipoClearing.getDescripcion()+";;"+
               "rango="+this.desde+"-"+this.hasta+";;";
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public ServicioRc getServicioRc() {
        return servicioRc;
    }

    public void setServicioRc(ServicioRc servicioRc) {
        this.servicioRc = servicioRc;
    }

}
