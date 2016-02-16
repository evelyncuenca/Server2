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
 * @author gustavo
 */
@Entity
@Table(name = "DISTRIBUCION_CLEARING_MANUAL")
public class DistribucionClearingManual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DIST_CLEARING_MANUAL")
    @GeneratedValue(generator = "distribucionClearingManualSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "distribucionClearingManualSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="distribucionClearingManualSeq",initialValue=1)
    private Long idDistClearingManual;
    @JoinColumn(name = "CLEARING_MANUAL", referencedColumnName = "ID_CLEARING_MANUAL")
    @ManyToOne(optional = false)
    private ClearingManual clearingManual;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private Double monto;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_BENEFICIARIO")
    private String descripcionBeneficiario;
    @Basic(optional = false)
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;

    public DistribucionClearingManual() {
    }

    public ClearingManual getClearingManual() {
        return clearingManual;
    }

    public void setClearingManual(ClearingManual clearingManual) {
        this.clearingManual = clearingManual;
    }

    public String getDescripcionBeneficiario() {
        return descripcionBeneficiario;
    }

    public void setDescripcionBeneficiario(String descripcionBeneficiario) {
        this.descripcionBeneficiario = descripcionBeneficiario;
    }

    public Long getIdDistClearingManual() {
        return idDistClearingManual;
    }

    public void setIdDistClearingManual(Long idDistClearingManual) {
        this.idDistClearingManual = idDistClearingManual;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistClearingManual != null ? idDistClearingManual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistribucionClearingManual)) {
            return false;
        }
        DistribucionClearingManual other = (DistribucionClearingManual) object;
        if ((this.idDistClearingManual == null && other.idDistClearingManual != null) || (this.idDistClearingManual != null && !this.idDistClearingManual.equals(other.idDistClearingManual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DistribucionClearing[idDistribucionClearing=" + idDistClearingManual + "]";
    }

}
