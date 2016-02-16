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
@Table(name = "CLEARING_MANUAL")
public class ClearingManual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CLEARING_MANUAL")
    @GeneratedValue(generator = "clearingManualSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "clearingManualSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="clearingManualSeq",initialValue=1)
    private Long idClearingManual;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "FECHA_ACREDITACION")
    @Temporal(TemporalType.DATE)
    private Date fechaAcreditacion;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCreacion;
    @OneToMany(mappedBy = "clearingManual",cascade=CascadeType.ALL)
    private Collection<DistribucionClearingManual> distribucionClearingManualCollection;
    @Basic(optional = false)
    @Column(name = "MONTO_DISTRIBUIDO")
    private Double montoDistribuido;    
    //si es C. la cabecera es debito y los detalles son creditos, sino es al reves
    @Basic(optional = false)
    @Column(name = "CABECERA_DEBITO_CREDITO")
    private Character cabeceraDebitoCredito;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_BENEFICIARIO")
    private String descripcionBeneficiario;
    @Basic(optional = false)
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;

    public Character getCabeceraDebitoCredito() {
        return cabeceraDebitoCredito;
    }

    public void setCabeceraDebitoCredito(Character cabeceraDebitoCredito) {
        this.cabeceraDebitoCredito = cabeceraDebitoCredito;
    }

    public String getDescripcionBeneficiario() {
        return descripcionBeneficiario;
    }

    public void setDescripcionBeneficiario(String descripcionBeneficiario) {
        this.descripcionBeneficiario = descripcionBeneficiario;
    }

    public Collection<DistribucionClearingManual> getDistribucionClearingManualCollection() {
        return distribucionClearingManualCollection;
    }

    public void setDistribucionClearingManualCollection(Collection<DistribucionClearingManual> distribucionClearingManualCollection) {
        this.distribucionClearingManualCollection = distribucionClearingManualCollection;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaAcreditacion() {
        return fechaAcreditacion;
    }

    public void setFechaAcreditacion(Date fechaAcreditacion) {
        this.fechaAcreditacion = fechaAcreditacion;
    }

    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Long getIdClearingManual() {
        return idClearingManual;
    }

    public void setIdClearingManual(Long idClearingManual) {
        this.idClearingManual = idClearingManual;
    }

    public Double getMontoDistribuido() {
        return montoDistribuido;
    }

    public void setMontoDistribuido(Double montoDistribuido) {
        this.montoDistribuido = montoDistribuido;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }   
    
    public ClearingManual() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClearingManual != null ? idClearingManual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClearingManual)) {
            return false;
        }
        ClearingManual other = (ClearingManual) object;
        if ((this.idClearingManual == null && other.idClearingManual != null) || (this.idClearingManual != null && !this.idClearingManual.equals(other.idClearingManual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ClearingManual[idClearingManual=" + idClearingManual + "]";
    }
}
