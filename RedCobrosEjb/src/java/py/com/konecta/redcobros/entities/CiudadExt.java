/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fgonzalez
 */
@Entity
@Table(name = "CIUDAD_EXT")
@NamedQueries({
    @NamedQuery(name = "CiudadExt.findAll", query = "SELECT c FROM CiudadExt c"),
    @NamedQuery(name = "CiudadExt.findByIdCiudadInt", query = "SELECT c FROM CiudadExt c WHERE c.ciudadExtPK.idCiudadInt = :idCiudadInt"),
    @NamedQuery(name = "CiudadExt.findByIdCiudadExt", query = "SELECT c FROM CiudadExt c WHERE c.ciudadExtPK.idCiudadExt = :idCiudadExt"),
    @NamedQuery(name = "CiudadExt.findByDescripcion", query = "SELECT c FROM CiudadExt c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CiudadExt.findByEntidad", query = "SELECT c FROM CiudadExt c WHERE c.ciudadExtPK.entidad = :entidad")})
public class CiudadExt implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CiudadExtPK ciudadExtPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_CIUDAD_INT", referencedColumnName = "ID_CIUDAD", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ciudad ciudad;

    public CiudadExt() {
    }

    public CiudadExt(CiudadExtPK ciudadExtPK) {
        this.ciudadExtPK = ciudadExtPK;
    }

    public CiudadExt(CiudadExtPK ciudadExtPK, String descripcion) {
        this.ciudadExtPK = ciudadExtPK;
        this.descripcion = descripcion;
    }

    public CiudadExt(Long idCiudadInt, Long idCiudadExt, Integer entidad) {
        this.ciudadExtPK = new CiudadExtPK(idCiudadInt, idCiudadExt, entidad);
    }

    public CiudadExtPK getCiudadExtPK() {
        return ciudadExtPK;
    }

    public void setCiudadExtPK(CiudadExtPK ciudadExtPK) {
        this.ciudadExtPK = ciudadExtPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciudadExtPK != null ? ciudadExtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CiudadExt)) {
            return false;
        }
        CiudadExt other = (CiudadExt) object;
        if ((this.ciudadExtPK == null && other.ciudadExtPK != null) || (this.ciudadExtPK != null && !this.ciudadExtPK.equals(other.ciudadExtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities2.CiudadExt[ ciudadExtPK=" + ciudadExtPK + " ]";
    }
    
}
