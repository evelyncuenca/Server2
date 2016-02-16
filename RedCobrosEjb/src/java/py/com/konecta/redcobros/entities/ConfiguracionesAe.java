/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@Entity
@Table(name = "CONFIGURACIONES_AE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfiguracionesAe.findAll", query = "SELECT c FROM ConfiguracionesAe c"),
    @NamedQuery(name = "ConfiguracionesAe.findByIdConfiguracion", query = "SELECT c FROM ConfiguracionesAe c WHERE c.idConfiguracion = :idConfiguracion"),
    @NamedQuery(name = "ConfiguracionesAe.findByCodigo", query = "SELECT c FROM ConfiguracionesAe c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "ConfiguracionesAe.findByValor", query = "SELECT c FROM ConfiguracionesAe c WHERE c.valor = :valor")})
public class ConfiguracionesAe implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CONFIGURACION")
    private Long idConfiguracion;
    @Size(max = 255)
    @Column(name = "CODIGO")
    private String codigo;
    @Size(max = 255)
    @Column(name = "VALOR")
    private String valor;

    public ConfiguracionesAe() {
    }

    public ConfiguracionesAe(Long idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Long getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Long idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracion != null ? idConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfiguracionesAe)) {
            return false;
        }
        ConfiguracionesAe other = (ConfiguracionesAe) object;
        if ((this.idConfiguracion == null && other.idConfiguracion != null) || (this.idConfiguracion != null && !this.idConfiguracion.equals(other.idConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ConfiguracionesAe[ idConfiguracion=" + idConfiguracion + " ]";
    }

}
