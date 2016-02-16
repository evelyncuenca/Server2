/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "PARAM_TASAS")
@NamedQueries({@NamedQuery(name = "ParamTasas.findAll", query = "SELECT p FROM ParamTasas p")})
public class ParamTasas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PARAM_TASA")
    @GeneratedValue(generator = "paramTasasSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "paramTasasSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="paramTasasSeq",initialValue=1)
    private Long idParamTasa;
    @Column(name = "FORMULARIO")
    private Integer formulario;
    @Column(name = "CONCEPTO_CAMPO")
    private Integer conceptoCampo;
    @Column(name = "FECHA_DESDE")
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    @Column(name = "FECHA_HASTA")
    @Temporal(TemporalType.DATE)
    private Date fechaHasta;
    @Column(name = "TASA_EFECTIVA")
    private Double tasaEfectiva;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public ParamTasas() {
    }

    public ParamTasas(Long idParamTasa) {
        this.idParamTasa = idParamTasa;
    }

    public Long getIdParamTasa() {
        return idParamTasa;
    }

    public void setIdParamTasa(Long idParamTasa) {
        this.idParamTasa = idParamTasa;
    }

    public Integer getFormulario() {
        return formulario;
    }

    public void setFormulario(Integer formulario) {
        this.formulario = formulario;
    }

    public Integer getConceptoCampo() {
        return conceptoCampo;
    }

    public void setConceptoCampo(Integer conceptoCampo) {
        this.conceptoCampo = conceptoCampo;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Double getTasaEfectiva() {
        return tasaEfectiva;
    }

    public void setTasaEfectiva(Double tasaEfectiva) {
        this.tasaEfectiva = tasaEfectiva;
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
        hash += (idParamTasa != null ? idParamTasa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamTasas)) {
            return false;
        }
        ParamTasas other = (ParamTasas) object;
        if ((this.idParamTasa == null && other.idParamTasa != null) || (this.idParamTasa != null && !this.idParamTasa.equals(other.idParamTasa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ParamTasas[idParamTasa=" + idParamTasa + "]";
    }

}
