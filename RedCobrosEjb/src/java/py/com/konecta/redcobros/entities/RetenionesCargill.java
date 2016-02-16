/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brojas
 */
@Entity
@Table(name = "RETENIONES_CARGILL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RetenionesCargill.findAll", query = "SELECT r FROM RetenionesCargill r"),
    @NamedQuery(name = "RetenionesCargill.findByIdRetencion", query = "SELECT r FROM RetenionesCargill r WHERE r.idRetencion = :idRetencion"),
    @NamedQuery(name = "RetenionesCargill.findByIdCliente", query = "SELECT r FROM RetenionesCargill r WHERE r.idCliente = :idCliente"),
    @NamedQuery(name = "RetenionesCargill.findByRuc", query = "SELECT r FROM RetenionesCargill r WHERE r.ruc = :ruc"),
    @NamedQuery(name = "RetenionesCargill.findByMonto", query = "SELECT r FROM RetenionesCargill r WHERE r.monto = :monto"),
    @NamedQuery(name = "RetenionesCargill.findByMoneda", query = "SELECT r FROM RetenionesCargill r WHERE r.moneda = :moneda"),
    @NamedQuery(name = "RetenionesCargill.findByFechaIngreso", query = "SELECT r FROM RetenionesCargill r WHERE r.fechaIngreso = :fechaIngreso")})
public class RetenionesCargill implements Serializable {
    @Size(max = 20)
    @Column(name = "NRO_RETENCION")
    private String nroRetencion;
    @Size(max = 100)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_RETENCION")
    private Long idRetencion;
    @Column(name = "ID_CLIENTE")
    private BigInteger idCliente;
    @Size(max = 20)
    @Column(name = "RUC")
    private String ruc;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "MONEDA")
    private Short moneda;
    @Column(name = "FECHA_INGRESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;

    public RetenionesCargill() {
    }

    public RetenionesCargill(Long idRetencion) {
        this.idRetencion = idRetencion;
    }

    public Long getIdRetencion() {
        return idRetencion;
    }

    public void setIdRetencion(Long idRetencion) {
        this.idRetencion = idRetencion;
    }

    public BigInteger getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigInteger idCliente) {
        this.idCliente = idCliente;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Short getMoneda() {
        return moneda;
    }

    public void setMoneda(Short moneda) {
        this.moneda = moneda;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRetencion != null ? idRetencion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RetenionesCargill)) {
            return false;
        }
        RetenionesCargill other = (RetenionesCargill) object;
        if ((this.idRetencion == null && other.idRetencion != null) || (this.idRetencion != null && !this.idRetencion.equals(other.idRetencion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RetenionesCargill[ idRetencion=" + idRetencion + " ]";
    }

    public String getNroRetencion() {
        return nroRetencion;
    }

    public void setNroRetencion(String nroRetencion) {
        this.nroRetencion = nroRetencion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
