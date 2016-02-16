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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "TIPO_SERVICIO")
@NamedQueries({@NamedQuery(name = "TipoServicio.findAll", query = "SELECT t FROM TipoServicio t"), @NamedQuery(name = "TipoServicio.findByIdTipoServicio", query = "SELECT t FROM TipoServicio t WHERE t.idTipoServicio = :idTipoServicio"), @NamedQuery(name = "TipoServicio.findByAlias", query = "SELECT t FROM TipoServicio t WHERE t.alias = :alias"), @NamedQuery(name = "TipoServicio.findByDescripcion", query = "SELECT t FROM TipoServicio t WHERE t.descripcion = :descripcion")})
public class TipoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_SERVICIO")
    private Short idTipoServicio;
    @Basic(optional = false)
    @Column(name = "ALIAS")
    private String alias;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoServicio")
    private Collection<ServicioRc> servicioRcCollection;

    public TipoServicio() {
    }

    public TipoServicio(Short idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public TipoServicio(Short idTipoServicio, String alias, String descripcion) {
        this.idTipoServicio = idTipoServicio;
        this.alias = alias;
        this.descripcion = descripcion;
    }

    public Short getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(Short idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<ServicioRc> getServicioRcCollection() {
        return servicioRcCollection;
    }

    public void setServicioRcCollection(Collection<ServicioRc> servicioRcCollection) {
        this.servicioRcCollection = servicioRcCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoServicio != null ? idTipoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoServicio)) {
            return false;
        }
        TipoServicio other = (TipoServicio) object;
        if ((this.idTipoServicio == null && other.idTipoServicio != null) || (this.idTipoServicio != null && !this.idTipoServicio.equals(other.idTipoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TipoServicio[idTipoServicio=" + idTipoServicio + "]";
    }

}
