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
@Table(name = "ENTIDAD_DIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntidadDir.findAll", query = "SELECT e FROM EntidadDir e"),
    @NamedQuery(name = "EntidadDir.findByIdEntidadDir", query = "SELECT e FROM EntidadDir e WHERE e.idEntidadDir = :idEntidadDir"),
    @NamedQuery(name = "EntidadDir.findByIdGrupo", query = "SELECT e FROM EntidadDir e WHERE e.idGrupo = :idGrupo"),
    @NamedQuery(name = "EntidadDir.findByDireccion", query = "SELECT e FROM EntidadDir e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "EntidadDir.findByEstado", query = "SELECT e FROM EntidadDir e WHERE e.estado = :estado"),
    @NamedQuery(name = "EntidadDir.findByIdEntidad", query = "SELECT e FROM EntidadDir e WHERE e.idEntidad = :idEntidad")})
public class EntidadDir implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ENTIDAD_DIR")
    @GeneratedValue(generator = "entidadDirSeq",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name="entidadDirSeq", sequenceName="ENTIDAD_DIR_SEQ")

    private Long idEntidadDir;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_GRUPO")
    private Long idGrupo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ENTIDAD")
    private Long idEntidad;

    public EntidadDir() {
    }

    public EntidadDir(Long idEntidadDir) {
        this.idEntidadDir = idEntidadDir;
    }

    public EntidadDir(Long idEntidadDir, Long idGrupo, String direccion, String estado, Long idEntidad) {
        this.idEntidadDir = idEntidadDir;
        this.idGrupo = idGrupo;
        this.direccion = direccion;
        this.estado = estado;
        this.idEntidad = idEntidad;
    }

    public Long getIdEntidadDir() {
        return idEntidadDir;
    }

    public void setIdEntidadDir(Long idEntidadDir) {
        this.idEntidadDir = idEntidadDir;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntidadDir != null ? idEntidadDir.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadDir)) {
            return false;
        }
        EntidadDir other = (EntidadDir) object;
        if ((this.idEntidadDir == null && other.idEntidadDir != null) || (this.idEntidadDir != null && !this.idEntidadDir.equals(other.idEntidadDir))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.ejb.EntidadDir[ idEntidadDir=" + idEntidadDir + " ]";
    }
    
}
