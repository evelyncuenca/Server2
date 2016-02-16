/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;

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
@Table(name = "PERMISO_DE_ROL")
@NamedQueries({@NamedQuery(name = "PermisoDeRol.findAll", query = "SELECT p FROM PermisoDeRol p")})
public class PermisoDeRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "permisoDeRolSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "permisoDeRolSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="permisoDeRolSeq",initialValue=1)
    @Column(name = "ID_PERMISO_DE_ROL")
    private Long idPermisoDeRol;
    @JoinColumn(name = "PERMISO", referencedColumnName = "ID_PERMISO")
    @ManyToOne(optional = false)
    private Permiso permiso;
    @JoinColumn(name = "ROL", referencedColumnName = "ID_ROL")
    @ManyToOne(optional = false)
    private Rol rol;

    public PermisoDeRol() {
    }

    public PermisoDeRol(Long idPermisoDeRol) {
        this.idPermisoDeRol = idPermisoDeRol;
    }

    public Long getIdPermisoDeRol() {
        return idPermisoDeRol;
    }

    public void setIdPermisoDeRol(Long idPermisoDeRol) {
        this.idPermisoDeRol = idPermisoDeRol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPermisoDeRol != null ? idPermisoDeRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PermisoDeRol)) {
            return false;
        }
        PermisoDeRol other = (PermisoDeRol) object;
        if ((this.idPermisoDeRol == null && other.idPermisoDeRol != null) || (this.idPermisoDeRol != null && !this.idPermisoDeRol.equals(other.idPermisoDeRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.PermisoDeRol[idPermisoDeRol=" + idPermisoDeRol + "]";
    }

}
