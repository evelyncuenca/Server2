/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "TIPO_COBRO")
public class TipoCobro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_COBRO")
    private Long idTipoCobro;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "DIAS_ACREDITACION")
    private Integer diasAcreditacion;

    public Integer getDiasAcreditacion() {
        return diasAcreditacion;
    }

    public void setDiasAcreditacion(Integer diasAcreditacion) {
        this.diasAcreditacion = diasAcreditacion;
    }
    

    public TipoCobro() {
    }

    public TipoCobro(Long idTipoCobro) {
        this.idTipoCobro = idTipoCobro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdTipoCobro() {
        return idTipoCobro;
    }

    public void setIdTipoCobro(Long idTipocobro) {
        this.idTipoCobro = idTipocobro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCobro != null ? idTipoCobro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCobro)) {
            return false;
        }
        TipoCobro other = (TipoCobro) object;
        if ((this.idTipoCobro == null && other.idTipoCobro != null) || (this.idTipoCobro != null && !this.idTipoCobro.equals(other.idTipoCobro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TipoCobro[idTipoCobro=" + idTipoCobro + "]";
    }
    public final static long EFECTIVO = 1L;
    public final static long CHEQUES_BANCO_LOCAL = 2L;
    public final static long CHEQUES_OTRO_BANCO = 3L;
}
