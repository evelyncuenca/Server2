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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CUENTA")
@NamedQueries({@NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"), @NamedQuery(name = "Cuenta.findByIdCuenta", query = "SELECT c FROM Cuenta c WHERE c.idCuenta = :idCuenta"), @NamedQuery(name = "Cuenta.findByNroCuenta", query = "SELECT c FROM Cuenta c WHERE c.nroCuenta = :nroCuenta"), @NamedQuery(name = "Cuenta.findByAliasCuenta", query = "SELECT c FROM Cuenta c WHERE c.aliasCuenta = :aliasCuenta")})
public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "cuentaSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "cuentaSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="cuentaSeq",initialValue=1)
    @Column(name = "ID_CUENTA")
    private Long idCuenta;
    @Column(name = "NRO_CUENTA")
    private String nroCuenta;
    @Basic(optional = false)
    @Column(name = "ALIAS_CUENTA")
    private String aliasCuenta;
    @JoinColumn(name = "ENTIDAD_FINANCIERA", referencedColumnName = "ID_ENTIDAD_FINANCIERA")
    @ManyToOne(optional = false)
    private EntidadFinanciera entidadFinanciera;
    
    public Cuenta() {
    }

    public Cuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Cuenta(Long idCuenta, String aliasCuenta) {
        this.idCuenta = idCuenta;
        this.aliasCuenta = aliasCuenta;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getAliasCuenta() {
        return aliasCuenta;
    }

    public void setAliasCuenta(String aliasCuenta) {
        this.aliasCuenta = aliasCuenta;
    }


    public EntidadFinanciera getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public void setEntidadFinanciera(EntidadFinanciera entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Cuenta[idCuenta=" + idCuenta + "]";
    }

}
