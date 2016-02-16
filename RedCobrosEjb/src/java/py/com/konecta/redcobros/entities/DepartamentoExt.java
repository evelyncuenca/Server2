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
@Table(name = "DEPARTAMENTO_EXT")
@NamedQueries({
    @NamedQuery(name = "DepartamentoExt.findAll", query = "SELECT d FROM DepartamentoExt d"),
    @NamedQuery(name = "DepartamentoExt.findByIdDepartamentoInt", query = "SELECT d FROM DepartamentoExt d WHERE d.departamentoExtPK.idDepartamentoInt = :idDepartamentoInt"),
    @NamedQuery(name = "DepartamentoExt.findByIdDepartamentoExt", query = "SELECT d FROM DepartamentoExt d WHERE d.departamentoExtPK.idDepartamentoExt = :idDepartamentoExt"),
    @NamedQuery(name = "DepartamentoExt.findByDescripcion", query = "SELECT d FROM DepartamentoExt d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "DepartamentoExt.findByEntidad", query = "SELECT d FROM DepartamentoExt d WHERE d.departamentoExtPK.entidad = :entidad")})
public class DepartamentoExt implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DepartamentoExtPK departamentoExtPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_DEPARTAMENTO_INT", referencedColumnName = "ID_DEPARTAMENTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Departamento departamento;

    public DepartamentoExt() {
    }

    public DepartamentoExt(DepartamentoExtPK departamentoExtPK) {
        this.departamentoExtPK = departamentoExtPK;
    }

    public DepartamentoExt(DepartamentoExtPK departamentoExtPK, String descripcion) {
        this.departamentoExtPK = departamentoExtPK;
        this.descripcion = descripcion;
    }

    public DepartamentoExt(Long idDepartamentoInt, Long idDepartamentoExt, Integer entidad) {
        this.departamentoExtPK = new DepartamentoExtPK(idDepartamentoInt, idDepartamentoExt, entidad);
    }

    public DepartamentoExtPK getDepartamentoExtPK() {
        return departamentoExtPK;
    }

    public void setDepartamentoExtPK(DepartamentoExtPK departamentoExtPK) {
        this.departamentoExtPK = departamentoExtPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departamentoExtPK != null ? departamentoExtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepartamentoExt)) {
            return false;
        }
        DepartamentoExt other = (DepartamentoExt) object;
        if ((this.departamentoExtPK == null && other.departamentoExtPK != null) || (this.departamentoExtPK != null && !this.departamentoExtPK.equals(other.departamentoExtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities2.DepartamentoExt[ departamentoExtPK=" + departamentoExtPK + " ]";
    }
    
}
