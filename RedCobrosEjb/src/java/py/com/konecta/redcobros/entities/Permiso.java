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
@Table(name = "PERMISO")
@NamedQueries({@NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p")})
public class Permiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "permisoSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "permisoSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="permisoSeq",initialValue=1)
    @Column(name = "ID_PERMISO")
    private Long idPermiso;
    @Basic(optional = false)
    @Column(name = "CODIGO_PERMISO")
    private String codigoPermiso;
    @Basic(optional = false)
    @Column(name = "SECCION")
    private String seccion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permiso")
    private Collection<PermisoDeRol> permisoDeRolCollection;
    @JoinColumn(name = "APLICACION", referencedColumnName = "ID_APLICACION")
    @ManyToOne
    private Aplicacion aplicacion;

    public Permiso() {
    }

    public Permiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public Permiso(Long idPermiso, String codigoPermiso) {
        this.idPermiso = idPermiso;
        this.codigoPermiso = codigoPermiso;
    }

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getCodigoPermiso() {
        return codigoPermiso;
    }

    public void setCodigoPermiso(String codigoPermiso) {
        this.codigoPermiso = codigoPermiso;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<PermisoDeRol> getPermisoDeRolCollection() {
        return permisoDeRolCollection;
    }

    public void setPermisoDeRolCollection(Collection<PermisoDeRol> permisoDeRolCollection) {
        this.permisoDeRolCollection = permisoDeRolCollection;
    }

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPermiso != null ? idPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.idPermiso == null && other.idPermiso != null) || (this.idPermiso != null && !this.idPermiso.equals(other.idPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Permiso[idPermiso=" + idPermiso + "]";
    }

}
