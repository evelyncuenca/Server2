/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "PARAM_VENCIMIENTOS")
@NamedQueries({@NamedQuery(name = "ParamVencimientos.findAll", query = "SELECT p FROM ParamVencimientos p")})
public class ParamVencimientos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PARAM_VENCIMIENTO")
    @GeneratedValue(generator = "paramVencimientosSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "paramVencimientosSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="paramVencimientosSeq",initialValue=1)
    private Long idParamVencimiento;
    @Column(name = "IMPUESTO")
    private Integer impuesto;
    @Column(name = "ABREVIATURA")
    private String abreviatura;
    @Column(name = "PERIODICIDAD")
    private Integer periodicidad;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "NUMERO_CUOTA")
    private Integer numeroCuota;
    @Column(name = "MESES_PLAZO")
    private Integer mesesPlazo;
    @Column(name = "PLAZO_DECLARACION")
    private Integer plazoDeclaracion;
    @Column(name = "UNIDAD_TIEMPO")
    private String unidadTiempo;
    @Column(name = "FECHA_DESDE")
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    @Column(name = "FECHA_HASTA")
    @Temporal(TemporalType.DATE)
    private Date fechaHasta;
    @Column(name = "DIGITO_DESDE_CAD")
    private String digitoDesdeCad;
    @Column(name = "DIGITO_HASTA_CAD")
    private String digitoHastaCad;

    public ParamVencimientos() {
    }

    public ParamVencimientos(Long idParamVencimiento) {
        this.idParamVencimiento = idParamVencimiento;
    }

    public Long getIdParamVencimiento() {
        return idParamVencimiento;
    }

    public void setIdParamVencimiento(Long idParamVencimiento) {
        this.idParamVencimiento = idParamVencimiento;
    }

    public Integer getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Integer getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Integer periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public Integer getMesesPlazo() {
        return mesesPlazo;
    }

    public void setMesesPlazo(Integer mesesPlazo) {
        this.mesesPlazo = mesesPlazo;
    }

    public Integer getPlazoDeclaracion() {
        return plazoDeclaracion;
    }

    public void setPlazoDeclaracion(Integer plazoDeclaracion) {
        this.plazoDeclaracion = plazoDeclaracion;
    }

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getDigitoDesdeCad() {
        return digitoDesdeCad;
    }

    public void setDigitoDesdeCad(String digitoDesdeCad) {
        this.digitoDesdeCad = digitoDesdeCad;
    }

    public String getDigitoHastaCad() {
        return digitoHastaCad;
    }

    public void setDigitoHastaCad(String digitoHastaCad) {
        this.digitoHastaCad = digitoHastaCad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParamVencimiento != null ? idParamVencimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamVencimientos)) {
            return false;
        }
        ParamVencimientos other = (ParamVencimientos) object;
        if ((this.idParamVencimiento == null && other.idParamVencimiento != null) || (this.idParamVencimiento != null && !this.idParamVencimiento.equals(other.idParamVencimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ParamVencimientos[idParamVencimiento=" + idParamVencimiento + "]";
    }

}
