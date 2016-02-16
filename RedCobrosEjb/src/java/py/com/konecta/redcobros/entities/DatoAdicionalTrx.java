/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "DATO_ADICIONAL_TRX")
@NamedQueries({@NamedQuery(name = "DatoAdicionalTrx.findAll", query = "SELECT d FROM DatoAdicionalTrx d"), @NamedQuery(name = "DatoAdicionalTrx.findByIdTransaccion", query = "SELECT d FROM DatoAdicionalTrx d WHERE d.datoAdicionalTrxPK.idTransaccion = :idTransaccion"), @NamedQuery(name = "DatoAdicionalTrx.findByIdTipoAdicional", query = "SELECT d FROM DatoAdicionalTrx d WHERE d.datoAdicionalTrxPK.idTipoAdicional = :idTipoAdicional"), @NamedQuery(name = "DatoAdicionalTrx.findByValor", query = "SELECT d FROM DatoAdicionalTrx d WHERE d.valor = :valor")})
public class DatoAdicionalTrx implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DatoAdicionalTrxPK datoAdicionalTrxPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "ID_TIPO_ADICIONAL", referencedColumnName = "ID_TIPO_ADICIONAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoDatoAdicional tipoDatoAdicional;
    @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID_TRANSACCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TransaccionRc transaccionRc;

    public DatoAdicionalTrx() {
    }

    public DatoAdicionalTrx(DatoAdicionalTrxPK datoAdicionalTrxPK) {
        this.datoAdicionalTrxPK = datoAdicionalTrxPK;
    }

    public DatoAdicionalTrx(DatoAdicionalTrxPK datoAdicionalTrxPK, String valor) {
        this.datoAdicionalTrxPK = datoAdicionalTrxPK;
        this.valor = valor;
    }

    public DatoAdicionalTrx(BigInteger idTransaccion, String idTipoAdicional) {
        this.datoAdicionalTrxPK = new DatoAdicionalTrxPK(idTransaccion, idTipoAdicional);
    }

    public DatoAdicionalTrxPK getDatoAdicionalTrxPK() {
        return datoAdicionalTrxPK;
    }

    public void setDatoAdicionalTrxPK(DatoAdicionalTrxPK datoAdicionalTrxPK) {
        this.datoAdicionalTrxPK = datoAdicionalTrxPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public TipoDatoAdicional getTipoDatoAdicional() {
        return tipoDatoAdicional;
    }

    public void setTipoDatoAdicional(TipoDatoAdicional tipoDatoAdicional) {
        this.tipoDatoAdicional = tipoDatoAdicional;
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
        hash += (datoAdicionalTrxPK != null ? datoAdicionalTrxPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatoAdicionalTrx)) {
            return false;
        }
        DatoAdicionalTrx other = (DatoAdicionalTrx) object;
        if ((this.datoAdicionalTrxPK == null && other.datoAdicionalTrxPK != null) || (this.datoAdicionalTrxPK != null && !this.datoAdicionalTrxPK.equals(other.datoAdicionalTrxPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DatoAdicionalTrx[datoAdicionalTrxPK=" + datoAdicionalTrxPK + "]";
    }

}
