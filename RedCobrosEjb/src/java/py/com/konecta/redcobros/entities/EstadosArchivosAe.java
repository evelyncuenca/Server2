/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ESTADOS_ARCHIVOS_AE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosArchivosAe.findAll", query = "SELECT e FROM EstadosArchivosAe e"),
    @NamedQuery(name = "EstadosArchivosAe.findByIdEstadoArchivoAe", query = "SELECT e FROM EstadosArchivosAe e WHERE e.idEstadoArchivoAe = :idEstadoArchivoAe"),
    @NamedQuery(name = "EstadosArchivosAe.findByDescripcion", query = "SELECT e FROM EstadosArchivosAe e WHERE e.descripcion = :descripcion")})
public class EstadosArchivosAe implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO_ARCHIVO_AE")
    private BigDecimal idEstadoArchivoAe;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public EstadosArchivosAe() {
    }

    public EstadosArchivosAe(BigDecimal idEstadoArchivoAe) {
        this.idEstadoArchivoAe = idEstadoArchivoAe;
    }

    public BigDecimal getIdEstadoArchivoAe() {
        return idEstadoArchivoAe;
    }

    public void setIdEstadoArchivoAe(BigDecimal idEstadoArchivoAe) {
        this.idEstadoArchivoAe = idEstadoArchivoAe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoArchivoAe != null ? idEstadoArchivoAe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadosArchivosAe)) {
            return false;
        }
        EstadosArchivosAe other = (EstadosArchivosAe) object;
        if ((this.idEstadoArchivoAe == null && other.idEstadoArchivoAe != null) || (this.idEstadoArchivoAe != null && !this.idEstadoArchivoAe.equals(other.idEstadoArchivoAe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.EstadosArchivosAe[ idEstadoArchivoAe=" + idEstadoArchivoAe + " ]";
    }

}
