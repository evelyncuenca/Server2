/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CONFIG_COMISIONAL_DETALLE")
@NamedQueries({@NamedQuery(name = "ConfigComisionalDetalle.findAll", query = "SELECT c FROM ConfigComisionalDetalle c")})
public class ConfigComisionalDetalle implements Serializable {
    @Column(name = "MONTO", precision = 19, scale = 2)
    private BigDecimal monto;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONFIG_COMISIONAL_DETALLE")
    @GeneratedValue(generator = "configComisionalDetSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "configComisionalDetSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="configComisionalDetSeq",initialValue=1)
    private Long idConfigComisionalDetalle;
    @Column(name = "ID_BENEFICIARIO")
    private Long idBeneficiario;
    @Column(name = "DESCRIPCION_BENEFICIARIO")
    private String descripcionBeneficiario;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private Double valor;
    @JoinColumn(name = "CONFIGURACION_COMISIONAL", referencedColumnName = "ID_CONFIGURACION_COMISIONAL")
    @ManyToOne(optional = false)
    private ConfiguracionComisional configuracionComisional;
    @JoinColumn(name = "ROL_COMISIONISTA", referencedColumnName = "ID_ROL_COMISIONISTA")
    @ManyToOne(optional = false)
    private RolComisionista rolComisionista;

    public String getDescripcionBeneficiario() {
        return descripcionBeneficiario;
    }

    public void setDescripcionBeneficiario(String descripcionBeneficiario) {
        this.descripcionBeneficiario = descripcionBeneficiario;
    }

    public ConfigComisionalDetalle() {
    }

    public ConfigComisionalDetalle(Long idConfigComisionalDetalle) {
        this.idConfigComisionalDetalle = idConfigComisionalDetalle;
    }

    public ConfigComisionalDetalle(Long idConfigComisionalDetalle, Double valor) {
        this.idConfigComisionalDetalle = idConfigComisionalDetalle;
        this.valor = valor;
    }

    public Long getIdConfigComisionalDetalle() {
        return idConfigComisionalDetalle;
    }

    public void setIdConfigComisionalDetalle(Long idConfigComisionalDetalle) {
        this.idConfigComisionalDetalle = idConfigComisionalDetalle;
    }

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public ConfiguracionComisional getConfiguracionComisional() {
        return configuracionComisional;
    }

    public void setConfiguracionComisional(ConfiguracionComisional configuracionComisional) {
        this.configuracionComisional = configuracionComisional;
    }


    public RolComisionista getRolComisionista() {
        return rolComisionista;
    }

    public void setRolComisionista(RolComisionista rolComisionista) {
        this.rolComisionista = rolComisionista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfigComisionalDetalle != null ? idConfigComisionalDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigComisionalDetalle)) {
            return false;
        }
        ConfigComisionalDetalle other = (ConfigComisionalDetalle) object;
        if ((this.idConfigComisionalDetalle == null && other.idConfigComisionalDetalle != null) || (this.idConfigComisionalDetalle != null && !this.idConfigComisionalDetalle.equals(other.idConfigComisionalDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ConfigComisionalDetalle[idConfigComisionalDetalle=" + idConfigComisionalDetalle + "]";
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
   
}
