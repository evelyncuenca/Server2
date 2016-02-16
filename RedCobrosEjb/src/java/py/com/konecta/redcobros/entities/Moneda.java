/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "MONEDA") 
@NamedQueries({@NamedQuery(name = "Moneda.findAll", query = "SELECT m FROM Moneda m")})
public class Moneda implements Serializable {
    @Size(max = 8)
    @Column(name = "ABREVIATURA")
    private String abreviatura;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MONEDA")
    private Long idMoneda;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "SOPORTADO")
    private String soportado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moneda")
    private Collection<Transaccion> transaccionCollection;

    public Moneda() {
    }

    public Moneda(Long idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Moneda(Long idMoneda, String descripcion, String soportado) {
        this.idMoneda = idMoneda;
        this.descripcion = descripcion;
        this.soportado = soportado;
    }

    public Long getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Long idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSoportado() {
        return soportado;
    }

    public void setSoportado(String soportado) {
        this.soportado = soportado;
    }

    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMoneda != null ? idMoneda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Moneda)) {
            return false;
        }
        Moneda other = (Moneda) object;
        if ((this.idMoneda == null && other.idMoneda != null) || (this.idMoneda != null && !this.idMoneda.equals(other.idMoneda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Moneda[idMoneda=" + idMoneda + "]";
    }

    public String getAbreviatura() {
        return abreviatura;
}

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

}
