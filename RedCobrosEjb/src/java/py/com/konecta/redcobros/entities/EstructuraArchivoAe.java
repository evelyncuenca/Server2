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
@Table(name = "ESTRUCTURA_ARCHIVO_AE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstructuraArchivoAe.findAll", query = "SELECT e FROM EstructuraArchivoAe e"),
    @NamedQuery(name = "EstructuraArchivoAe.findByIdEstructuraArchivo", query = "SELECT e FROM EstructuraArchivoAe e WHERE e.idEstructuraArchivo = :idEstructuraArchivo"),
    @NamedQuery(name = "EstructuraArchivoAe.findByOrden", query = "SELECT e FROM EstructuraArchivoAe e WHERE e.orden = :orden"),
    @NamedQuery(name = "EstructuraArchivoAe.findByDescripcionPresentador", query = "SELECT e FROM EstructuraArchivoAe e WHERE e.descripcionPresentador = :descripcionPresentador"),
    @NamedQuery(name = "EstructuraArchivoAe.findByDescripcion", query = "SELECT e FROM EstructuraArchivoAe e WHERE e.descripcion = :descripcion")})
public class EstructuraArchivoAe implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTRUCTURA_ARCHIVO")
    private Long idEstructuraArchivo;
    @Column(name = "ORDEN")
    private Long orden;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_PRESENTADOR")
    private String descripcionPresentador;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public EstructuraArchivoAe() {
    }

    public EstructuraArchivoAe(Long idEstructuraArchivo) {
        this.idEstructuraArchivo = idEstructuraArchivo;
    }

    public Long getIdEstructuraArchivo() {
        return idEstructuraArchivo;
    }

    public void setIdEstructuraArchivo(Long idEstructuraArchivo) {
        this.idEstructuraArchivo = idEstructuraArchivo;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public String getDescripcionPresentador() {
        return descripcionPresentador;
    }

    public void setDescripcionPresentador(String descripcionPresentador) {
        this.descripcionPresentador = descripcionPresentador;
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
        hash += (idEstructuraArchivo != null ? idEstructuraArchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstructuraArchivoAe)) {
            return false;
        }
        EstructuraArchivoAe other = (EstructuraArchivoAe) object;
        if ((this.idEstructuraArchivo == null && other.idEstructuraArchivo != null) || (this.idEstructuraArchivo != null && !this.idEstructuraArchivo.equals(other.idEstructuraArchivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.EstructuraArchivoAe[ idEstructuraArchivo=" + idEstructuraArchivo + " ]";
    }

}
