/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brojas
 */
@Entity
@Table(name = "GRUPO_REPORTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoReporte.findAll", query = "SELECT g FROM GrupoReporte g"),
    @NamedQuery(name = "GrupoReporte.findByIdGrupo", query = "SELECT g FROM GrupoReporte g WHERE g.idGrupo = :idGrupo"),
    @NamedQuery(name = "GrupoReporte.findByDescripcion", query = "SELECT g FROM GrupoReporte g WHERE g.descripcion = :descripcion")})
public class GrupoReporte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_GRUPO")
    private Integer idGrupo;
    @Size(max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public GrupoReporte() {
    }

    public GrupoReporte(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
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
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoReporte)) {
            return false;
        }
        GrupoReporte other = (GrupoReporte) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.ejb.GrupoReporte[ idGrupo=" + idGrupo + " ]";
    }
    
}
