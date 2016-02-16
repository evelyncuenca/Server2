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
public class DepartamentoExtPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DEPARTAMENTO_INT")
    private Long idDepartamentoInt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DEPARTAMENTO_EXT")
    private Long idDepartamentoExt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENTIDAD")
    private Integer entidad;

    public DepartamentoExtPK() {
    }

    public DepartamentoExtPK(Long idDepartamentoInt, Long idDepartamentoExt, Integer entidad) {
        this.idDepartamentoInt = idDepartamentoInt;
        this.idDepartamentoExt = idDepartamentoExt;
        this.entidad = entidad;
    }

    public Long getIdDepartamentoInt() {
        return idDepartamentoInt;
    }

    public void setIdDepartamentoInt(Long idDepartamentoInt) {
        this.idDepartamentoInt = idDepartamentoInt;
    }

    public Long getIdDepartamentoExt() {
        return idDepartamentoExt;
    }

    public void setIdDepartamentoExt(Long idDepartamentoExt) {
        this.idDepartamentoExt = idDepartamentoExt;
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
        hash += (idDepartamentoInt != null ? idDepartamentoInt.hashCode() : 0);
        hash += (idDepartamentoExt != null ? idDepartamentoExt.hashCode() : 0);
        hash += (int) entidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepartamentoExtPK)) {
            return false;
        }
        DepartamentoExtPK other = (DepartamentoExtPK) object;
        if ((this.idDepartamentoInt == null && other.idDepartamentoInt != null) || (this.idDepartamentoInt != null && !this.idDepartamentoInt.equals(other.idDepartamentoInt))) {
            return false;
        }
        if ((this.idDepartamentoExt == null && other.idDepartamentoExt != null) || (this.idDepartamentoExt != null && !this.idDepartamentoExt.equals(other.idDepartamentoExt))) {
            return false;
        }
        if (this.entidad != other.entidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities2.DepartamentoExtPK[ idDepartamentoInt=" + idDepartamentoInt + ", idDepartamentoExt=" + idDepartamentoExt + ", entidad=" + entidad + " ]";
    }
    
}
