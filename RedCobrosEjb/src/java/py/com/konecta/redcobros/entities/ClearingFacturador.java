/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CLEARING_FACTURADOR")
public class ClearingFacturador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CLEARING_FACTURADOR")
    @GeneratedValue(generator = "clearingFacturadorSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "clearingFacturadorSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="clearingFacturadorSeq",initialValue=1)
    private Long idClearingFacturador;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCreacion;
    @JoinColumn(name = "SERVICIO", referencedColumnName = "ID_SERVICIO")
    @ManyToOne(optional = false)
    private Servicio servicio;
    @JoinColumn(name = "RED", referencedColumnName = "ID_RED")
    @ManyToOne(optional = false)
    private Red red;
    @OneToMany(mappedBy = "clearingFacturador",cascade=CascadeType.ALL)
    private Collection<DistribucionClearingFacturador> distribucionClearingFacturadorCollection;
    @Basic(optional = false)
    @Column(name = "MONTO_TOTAL")
    private Double montoTotal;
    @JoinColumn(name = "TIPO_COBRO", referencedColumnName = "ID_TIPO_COBRO")
    @ManyToOne(optional = false)
    private TipoCobro tipoCobro;
    @Basic(optional = false)
    @Column(name = "FECHA_ACREDITACION")
    @Temporal(TemporalType.DATE)
    private Date fechaAcreditacion;

    public Date getFechaAcreditacion() {
        return fechaAcreditacion;
    }

    public void setFechaAcreditacion(Date fechaAcreditacion) {
        this.fechaAcreditacion = fechaAcreditacion;
    }
    

    public TipoCobro getTipoCobro() {
        return tipoCobro;
    }

    public void setTipoCobro(TipoCobro tipoCobro) {
        this.tipoCobro = tipoCobro;
    }


    public Collection<DistribucionClearingFacturador> getDistribucionClearingFacturadorCollection() {
        return distribucionClearingFacturadorCollection;
    }

    public void setDistribucionClearingFacturadorCollection(Collection<DistribucionClearingFacturador> distribucionClearingFacturadorCollection) {
        this.distribucionClearingFacturadorCollection = distribucionClearingFacturadorCollection;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Long getIdClearingFacturador() {
        return idClearingFacturador;
    }

    public void setIdClearingFacturador(Long idClearingFacturador) {
        this.idClearingFacturador = idClearingFacturador;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public ClearingFacturador() {
    }



    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClearingFacturador != null ? idClearingFacturador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClearingFacturador)) {
            return false;
        }
        ClearingFacturador other = (ClearingFacturador) object;
        if ((this.idClearingFacturador == null && other.idClearingFacturador != null) || (this.idClearingFacturador != null && !this.idClearingFacturador.equals(other.idClearingFacturador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ClearingFacturador[idClearingFacturador=" + idClearingFacturador + "]";
    }

}
