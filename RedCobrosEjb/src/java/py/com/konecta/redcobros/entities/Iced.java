/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brojas
 */
@Entity
@Table(name = "ICED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Iced.findAll", query = "SELECT i FROM Iced i"),
    @NamedQuery(name = "Iced.findByIdIced", query = "SELECT i FROM Iced i WHERE i.idIced = :idIced"),
    @NamedQuery(name = "Iced.findByDescripcion", query = "SELECT i FROM Iced i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Iced.findByFechaInicio", query = "SELECT i FROM Iced i WHERE i.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Iced.findByFechaFin", query = "SELECT i FROM Iced i WHERE i.fechaFin = :fechaFin"),
    @NamedQuery(name = "Iced.findByMonto", query = "SELECT i FROM Iced i WHERE i.monto = :monto")})
public class Iced implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ICED")
    private Long idIced;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FECHA_INICIO")
    private String fechaInicio;
    @Size(max = 20)
    @Column(name = "FECHA_FIN")
    private String fechaFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private BigInteger monto;

    public Iced() {
    }

    public Iced(Long idIced) {
        this.idIced = idIced;
    }

    public Iced(Long idIced, String descripcion, String fechaInicio, BigInteger monto) {
        this.idIced = idIced;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.monto = monto;
    }

    public Long getIdIced() {
        return idIced;
    }

    public void setIdIced(Long idIced) {
        this.idIced = idIced;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIced != null ? idIced.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iced)) {
            return false;
        }
        Iced other = (Iced) object;
        if ((this.idIced == null && other.idIced != null) || (this.idIced != null && !this.idIced.equals(other.idIced))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Iced[ idIced=" + idIced + " ]";
    }
    
}
