/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fgonzalez
 */
@Embeddable
public class CiudadExtPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CIUDAD_INT")
    private Long idCiudadInt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CIUDAD_EXT")
    private Long idCiudadExt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENTIDAD")
    private Integer entidad;

    public CiudadExtPK() {
    }

    public CiudadExtPK(Long idCiudadInt, Long idCiudadExt, Integer entidad) {
        this.idCiudadInt = idCiudadInt;
        this.idCiudadExt = idCiudadExt;
        this.entidad = entidad;
    }

    public Long getIdCiudadInt() {
        return idCiudadInt;
    }

    public void setIdCiudadInt(Long idCiudadInt) {
        this.idCiudadInt = idCiudadInt;
    }

    public Long getIdCiudadExt() {
        return idCiudadExt;
    }

    public void setIdCiudadExt(Long idCiudadExt) {
        this.idCiudadExt = idCiudadExt;
    }

    public Integer getEntidad() {
        return entidad;
    }

    public void setEntidad(Integer entidad) {
        this.entidad = entidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCiudadInt != null ? idCiudadInt.hashCode() : 0);
        hash += (idCiudadExt != null ? idCiudadExt.hashCode() : 0);
        hash += (int) entidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CiudadExtPK)) {
            return false;
        }
        CiudadExtPK other = (CiudadExtPK) object;
        if ((this.idCiudadInt == null && other.idCiudadInt != null) || (this.idCiudadInt != null && !this.idCiudadInt.equals(other.idCiudadInt))) {
            return false;
        }
        if ((this.idCiudadExt == null && other.idCiudadExt != null) || (this.idCiudadExt != null && !this.idCiudadExt.equals(other.idCiudadExt))) {
            return false;
        }
        if (this.entidad != other.entidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities2.CiudadExtPK[ idCiudadInt=" + idCiudadInt + ", idCiudadExt=" + idCiudadExt + ", entidad=" + entidad + " ]";
    }
    
}
