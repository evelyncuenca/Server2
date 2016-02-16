/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;

import java.math.BigInteger;
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
@Table(name = "ROL")
@NamedQueries({@NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r")})
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ROL")
    @GeneratedValue(generator = "rolSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "rolSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="rolSeq",initialValue=1)
    private Long idRol;
    //@Basic(optional = false)
    @Column(name = "CODIGO_ROL")
    private BigInteger codigoRol;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "APLICACION", referencedColumnName = "ID_APLICACION")
    @ManyToOne
    private Aplicacion aplicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
    private Collection<PermisoDeRol> permisoDeRolCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
    private Collection<RolDeUsuario> rolDeUsuarioCollection;

    public Rol() {
    }

    public Rol(Long idRol) {
        this.idRol = idRol;
    }

    public Rol(Long idRol, BigInteger codigoRol) {
        this.idRol = idRol;
        this.codigoRol = codigoRol;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public BigInteger getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(BigInteger codigoRol) {
        this.codigoRol = codigoRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public Collection<PermisoDeRol> getPermisoDeRolCollection() {
        return permisoDeRolCollection;
    }

    public void setPermisoDeRolCollection(Collection<PermisoDeRol> permisoDeRolCollection) {
        this.permisoDeRolCollection = permisoDeRolCollection;
    }

    public Collection<RolDeUsuario> getRolDeUsuarioCollection() {
        return rolDeUsuarioCollection;
    }

    public void setRolDeUsuarioCollection(Collection<RolDeUsuario> rolDeUsuarioCollection) {
        this.rolDeUsuarioCollection = rolDeUsuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Rol[idRol=" + idRol + "]";
    }

}
