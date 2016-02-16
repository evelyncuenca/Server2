/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *
 * @author konecta
 */
@Entity
@Table(name = "TIPO_PAGO")
public class TipoPago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_PAGO")
    private Long idTipoPago;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "SOPORTADO")
    private Character soportado;
    @OneToMany(mappedBy = "tipoPago")
    private Collection<NumeroOt> numeroOtCollection;

    public Collection<NumeroOt> getNumeroOtCollection() {
        return numeroOtCollection;
    }

    public void setNumeroOtCollection(Collection<NumeroOt> numeroOtCollection) {
        this.numeroOtCollection = numeroOtCollection;
    }

    



    public TipoPago() {
    }

    public TipoPago(Long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public TipoPago(Long idTipoPago, String descripcion) {
        this.idTipoPago = idTipoPago;
        this.descripcion = descripcion;
    }

    public Long getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(Long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getSoportado() {
        return soportado;
    }

    public void setSoportado(Character soportado) {
        this.soportado = soportado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPago != null ? idTipoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPago)) {
            return false;
        }
        TipoPago other = (TipoPago) object;
        if ((this.idTipoPago == null && other.idTipoPago != null) || (this.idTipoPago != null && !this.idTipoPago.equals(other.idTipoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.ejb.TipoPago[idTipoPago=" + idTipoPago + "]";
    }

    public final static long EFECTIVO = 1L;
    public final static long CHEQUE = 2L;

}
