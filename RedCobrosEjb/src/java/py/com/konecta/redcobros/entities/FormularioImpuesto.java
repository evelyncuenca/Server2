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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "FORMULARIO_IMPUESTO")
@NamedQueries({@NamedQuery(name = "FormularioImpuesto.findAll", query = "SELECT f FROM FormularioImpuesto f")})
public class FormularioImpuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FORMULARIO_IMPUESTO")
    @GeneratedValue(generator = "formularioImpuestoSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "formularioImpuestoSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="formularioImpuestoSeq",initialValue=1)
    private Long idFormularioImpuesto;
    @Column(name = "NUMERO_FORMULARIO")
    private Integer numeroFormulario;
    @Column(name = "IMPUESTO")
    private Integer impuesto;
    @Column(name = "OBLIGACION")
    private String obligacion;

    public FormularioImpuesto() {
    }

    public FormularioImpuesto(Long idFormularioImpuesto) {
        this.idFormularioImpuesto = idFormularioImpuesto;
    }

    public Long getIdFormularioImpuesto() {
        return idFormularioImpuesto;
    }

    public void setIdFormularioImpuesto(Long idFormularioImpuesto) {
        this.idFormularioImpuesto = idFormularioImpuesto;
    }

    public Integer getNumeroFormulario() {
        return numeroFormulario;
    }

    public void setNumeroFormulario(Integer numeroFormulario) {
        this.numeroFormulario = numeroFormulario;
    }

    public Integer getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    public String getObligacion() {
        return obligacion;
    }

    public void setObligacion(String obligacion) {
        this.obligacion = obligacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormularioImpuesto != null ? idFormularioImpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioImpuesto)) {
            return false;
        }
        FormularioImpuesto other = (FormularioImpuesto) object;
        if ((this.idFormularioImpuesto == null && other.idFormularioImpuesto != null) || (this.idFormularioImpuesto != null && !this.idFormularioImpuesto.equals(other.idFormularioImpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.FormularioImpuesto[idFormularioImpuesto=" + idFormularioImpuesto + "]";
    }

}
