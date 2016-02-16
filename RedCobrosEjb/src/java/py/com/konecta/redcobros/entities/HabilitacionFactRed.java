/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "HABILITACION_FACT_RED")
@NamedQueries({@NamedQuery(name = "HabilitacionFactRed.findAll", query = "SELECT h FROM HabilitacionFactRed h")})
public class HabilitacionFactRed implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HabilitacionFactRedPK habilitacionFactRedPK;
    @JoinColumn(name = "FACTURADOR", referencedColumnName = "ID_FACTURADOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Facturador facturador;
    @JoinColumn(name = "RED", referencedColumnName = "ID_RED", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Red red;
    @Basic(optional = false)
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public HabilitacionFactRed() {
    }

    public HabilitacionFactRed(HabilitacionFactRedPK habilitacionFactRedPK) {
        this.habilitacionFactRedPK = habilitacionFactRedPK;
    }

    public HabilitacionFactRed(Long red, Long facturador) {
        this.habilitacionFactRedPK = new HabilitacionFactRedPK(red, facturador);
    }

    public HabilitacionFactRedPK getHabilitacionFactRedPK() {
        return habilitacionFactRedPK;
    }

    public void setHabilitacionFactRedPK(HabilitacionFactRedPK habilitacionFactRedPK) {
        this.habilitacionFactRedPK = habilitacionFactRedPK;
    }

    public Facturador getFacturador() {
        return facturador;
    }

    public void setFacturador(Facturador facturador) {
        this.facturador = facturador;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red1) {
        this.red = red1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (habilitacionFactRedPK != null ? habilitacionFactRedPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HabilitacionFactRed)) {
            return false;
        }
        HabilitacionFactRed other = (HabilitacionFactRed) object;
        if ((this.habilitacionFactRedPK == null && other.habilitacionFactRedPK != null) || (this.habilitacionFactRedPK != null && !this.habilitacionFactRedPK.equals(other.habilitacionFactRedPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.HabilitacionFactRed[habilitacionFactRedPK=" + habilitacionFactRedPK + "]";
    }

}
