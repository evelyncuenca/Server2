/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CORTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Corte.findAll", query = "SELECT c FROM Corte c"),
    @NamedQuery(name = "Corte.findByIdCorte", query = "SELECT c FROM Corte c WHERE c.idCorte = :idCorte"),
    @NamedQuery(name = "Corte.findByFechaCorte", query = "SELECT c FROM Corte c WHERE c.fechaCorte = :fechaCorte"),
    @NamedQuery(name = "Corte.findByFechaHoraCorte", query = "SELECT c FROM Corte c WHERE c.fechaHoraCorte = :fechaHoraCorte"),
    @NamedQuery(name = "Corte.findByDescripcion", query = "SELECT c FROM Corte c WHERE c.descripcion = :descripcion")})
public class Corte implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
  //  @NotNull
    @Column(name = "ID_CORTE")
    private BigDecimal idCorte;
    @Basic(optional = false)
 //   @NotNull
    @Column(name = "FECHA_CORTE")
    @Temporal(TemporalType.DATE)
    private Date fechaCorte;
    @Column(name = "FECHA_HORA_CORTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCorte;
    //@Size(max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public Corte() {
    }

    public Corte(BigDecimal idCorte) {
        this.idCorte = idCorte;
    }

    public Corte(BigDecimal idCorte, Date fechaCorte) {
        this.idCorte = idCorte;
        this.fechaCorte = fechaCorte;
    }

    public BigDecimal getIdCorte() {
        return idCorte;
    }

    public void setIdCorte(BigDecimal idCorte) {
        this.idCorte = idCorte;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Date getFechaHoraCorte() {
        return fechaHoraCorte;
    }

    public void setFechaHoraCorte(Date fechaHoraCorte) {
        this.fechaHoraCorte = fechaHoraCorte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorte != null ? idCorte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Corte)) {
            return false;
        }
        Corte other = (Corte) object;
        if ((this.idCorte == null && other.idCorte != null) || (this.idCorte != null && !this.idCorte.equals(other.idCorte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Corte[ idCorte=" + idCorte + " ]";
    }
    
}
