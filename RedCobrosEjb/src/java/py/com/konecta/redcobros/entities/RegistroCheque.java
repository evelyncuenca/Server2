/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "REGISTRO_CHEQUE")
@NamedQueries({@NamedQuery(name = "RegistroCheque.findAll", query = "SELECT r FROM RegistroCheque r"), @NamedQuery(name = "RegistroCheque.findByIdTransaccion", query = "SELECT r FROM RegistroCheque r WHERE r.idTransaccion = :idTransaccion"), @NamedQuery(name = "RegistroCheque.findByNroCheque", query = "SELECT r FROM RegistroCheque r WHERE r.nroCheque = :nroCheque")})
public class RegistroCheque implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TRANSACCION")
    private BigDecimal idTransaccion;
    @Basic(optional = false)
    @Column(name = "NRO_CHEQUE")
    private String nroCheque;
    @JoinColumn(name = "ID_ENTIDAD_FINANCIERA", referencedColumnName = "ID_ENTIDAD_FINANCIERA")
    @ManyToOne(optional = false)
    private EntidadFinanciera idEntidadFinanciera;
    @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID_TRANSACCION", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TransaccionRc transaccionRc;

    public RegistroCheque() {
    }

    public RegistroCheque(BigDecimal idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public RegistroCheque(BigDecimal idTransaccion, String nroCheque) {
        this.idTransaccion = idTransaccion;
        this.nroCheque = nroCheque;
    }

    public BigDecimal getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(BigDecimal idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getNroCheque() {
        return nroCheque;
    }

    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
    }

    public EntidadFinanciera getIdEntidadFinanciera() {
        return idEntidadFinanciera;
    }

    public void setIdEntidadFinanciera(EntidadFinanciera idEntidadFinanciera) {
        this.idEntidadFinanciera = idEntidadFinanciera;
    }

    public TransaccionRc getTransaccionRc() {
        return transaccionRc;
    }

    public void setTransaccionRc(TransaccionRc transaccionRc) {
        this.transaccionRc = transaccionRc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaccion != null ? idTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroCheque)) {
            return false;
        }
        RegistroCheque other = (RegistroCheque) object;
        if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RegistroCheque[idTransaccion=" + idTransaccion + "]";
    }

}
