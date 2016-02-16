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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "DIST_CLEARING_FACT")
public class DistribucionClearingFacturador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DIST_CLEARING_FACT")
    @GeneratedValue(generator = "distribucionClearingFacturadorSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "distribucionClearingFacturadorSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="distribucionClearingFacturadorSeq",initialValue=1)
    private Long idDistribucionClearingFacturador;
    @JoinColumn(name = "CLEARING_FACTURADOR", referencedColumnName = "ID_CLEARING_FACTURADOR")
    @ManyToOne(optional = false)
    private ClearingFacturador clearingFacturador;
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    @ManyToOne(optional = false)
    private Recaudador recaudador;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private Double monto;

    public DistribucionClearingFacturador() {
    }

    public ClearingFacturador getClearingFacturador() {
        return clearingFacturador;
    }

    public void setClearingFacturador(ClearingFacturador clearingFacturador) {
        this.clearingFacturador = clearingFacturador;
    }

    public Long getIdDistribucionClearingFacturador() {
        return idDistribucionClearingFacturador;
    }

    public void setIdDistribucionClearingFacturador(Long idDistribucionClearingFacturador) {
        this.idDistribucionClearingFacturador = idDistribucionClearingFacturador;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Recaudador getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Recaudador recaudador) {
        this.recaudador = recaudador;
    }




    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistribucionClearingFacturador != null ? idDistribucionClearingFacturador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistribucionClearingFacturador)) {
            return false;
        }
        DistribucionClearingFacturador other = (DistribucionClearingFacturador) object;
        if ((this.idDistribucionClearingFacturador == null && other.idDistribucionClearingFacturador != null) || (this.idDistribucionClearingFacturador != null && !this.idDistribucionClearingFacturador.equals(other.idDistribucionClearingFacturador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ClearingFacturador[idClearingFacturador=" + idDistribucionClearingFacturador + "]";
    }

}
