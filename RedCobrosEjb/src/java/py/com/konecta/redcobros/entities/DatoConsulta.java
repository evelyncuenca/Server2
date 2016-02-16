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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "DATO_CONSULTA")
@NamedQueries({
    @NamedQuery(name = "DatoConsulta.findAll", query = "SELECT d FROM DatoConsulta d"),
    @NamedQuery(name = "DatoConsulta.findByIdFacturador", query = "SELECT d FROM DatoConsulta d WHERE d.datoConsultaPK.idFacturador = :idFacturador"),
    @NamedQuery(name = "DatoConsulta.findByIdServicio", query = "SELECT d FROM DatoConsulta d WHERE d.datoConsultaPK.idServicio = :idServicio"),
    @NamedQuery(name = "DatoConsulta.findByAlias", query = "SELECT d FROM DatoConsulta d WHERE d.alias = :alias"),
    @NamedQuery(name = "DatoConsulta.findByReferenciaBuqueda", query = "SELECT d FROM DatoConsulta d WHERE d.referenciaBuqueda = :referenciaBuqueda"),
    @NamedQuery(name = "DatoConsulta.findByReferenciaPago", query = "SELECT d FROM DatoConsulta d WHERE d.datoConsultaPK.referenciaPago = :referenciaPago"),
    @NamedQuery(name = "DatoConsulta.findByDescripcion", query = "SELECT d FROM DatoConsulta d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "DatoConsulta.findByMoneda", query = "SELECT d FROM DatoConsulta d WHERE d.moneda = :moneda"),
    @NamedQuery(name = "DatoConsulta.findByMonto", query = "SELECT d FROM DatoConsulta d WHERE d.monto = :monto"),
    @NamedQuery(name = "DatoConsulta.findByMontoVencido", query = "SELECT d FROM DatoConsulta d WHERE d.montoVencido = :montoVencido"),
    @NamedQuery(name = "DatoConsulta.findByInteresMoratorio", query = "SELECT d FROM DatoConsulta d WHERE d.interesMoratorio = :interesMoratorio"),
    @NamedQuery(name = "DatoConsulta.findByVencimiento", query = "SELECT d FROM DatoConsulta d WHERE d.vencimiento = :vencimiento"),
    @NamedQuery(name = "DatoConsulta.findByCobrarVencido", query = "SELECT d FROM DatoConsulta d WHERE d.cobrarVencido = :cobrarVencido"),
    @NamedQuery(name = "DatoConsulta.findByTipoRecargo", query = "SELECT d FROM DatoConsulta d WHERE d.tipoRecargo = :tipoRecargo"),
    @NamedQuery(name = "DatoConsulta.findByPagarMasVencido", query = "SELECT d FROM DatoConsulta d WHERE d.pagarMasVencido = :pagarMasVencido"),
    @NamedQuery(name = "DatoConsulta.findBySoloEfectivo", query = "SELECT d FROM DatoConsulta d WHERE d.soloEfectivo = :soloEfectivo"),
    @NamedQuery(name = "DatoConsulta.findByAnulable", query = "SELECT d FROM DatoConsulta d WHERE d.anulable = :anulable"),
    @NamedQuery(name = "DatoConsulta.findByPendiente", query = "SELECT d FROM DatoConsulta d WHERE d.pendiente = :pendiente")})
public class DatoConsulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DatoConsultaPK datoConsultaPK;
    @Basic(optional = false)
    @Column(name = "ALIAS")
    private Integer alias;
    @Basic(optional = false)
    @Column(name = "REFERENCIA_BUQUEDA")
    private String referenciaBuqueda;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "MONEDA")
    private Short moneda;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "MONTO_VENCIDO")
    private BigDecimal montoVencido;
    @Column(name = "INTERES_MORATORIO")
    private BigDecimal interesMoratorio;
    @Column(name = "VENCIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @Basic(optional = false)
    @Column(name = "COBRAR_VENCIDO")
    private Character cobrarVencido;
    @Basic(optional = false)
    @Column(name = "TIPO_RECARGO")
    private Short tipoRecargo;
    @Column(name = "PAGAR_MAS_VENCIDO")
    private Character pagarMasVencido;
    @Basic(optional = false)
    @Column(name = "SOLO_EFECTIVO")
    private Character soloEfectivo;
    @Basic(optional = false)
    @Column(name = "ANULABLE")
    private Character anulable;
    @Basic(optional = false)
    @Column(name = "PENDIENTE")
    private Character pendiente;
    @Basic(optional = true)
    @Column(name = "MENSAJE")
    private String mensaje;
    @JoinColumns({
        @JoinColumn(name = "ID_FACTURADOR", referencedColumnName = "ID_FACTURADOR", insertable = false, updatable = false),
        @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private AliasServicioFacturador aliasServicioFacturador;

    public DatoConsulta() {
    }

    public DatoConsulta(DatoConsultaPK datoConsultaPK) {
        this.datoConsultaPK = datoConsultaPK;
    }

    public DatoConsulta(DatoConsultaPK datoConsultaPK, Integer alias, String referenciaBuqueda, String descripcion, Short moneda, BigDecimal monto, Character cobrarVencido, Short tipoRecargo, Character soloEfectivo, Character anulable, Character pendiente) {
        this.datoConsultaPK = datoConsultaPK;
        this.alias = alias;
        this.referenciaBuqueda = referenciaBuqueda;
        this.descripcion = descripcion;
        this.moneda = moneda;
        this.monto = monto;
        this.cobrarVencido = cobrarVencido;
        this.tipoRecargo = tipoRecargo;
        this.soloEfectivo = soloEfectivo;
        this.anulable = anulable;
        this.pendiente = pendiente;
    }

    public DatoConsulta(Long idFacturador, Integer idServicio, String referenciaPago) {
        this.datoConsultaPK = new DatoConsultaPK(idFacturador, idServicio, referenciaPago);
    }

    public DatoConsultaPK getDatoConsultaPK() {
        return datoConsultaPK;
    }

    public void setDatoConsultaPK(DatoConsultaPK datoConsultaPK) {
        this.datoConsultaPK = datoConsultaPK;
    }

    public Integer getAlias() {
        return alias;
    }

    public void setAlias(Integer alias) {
        this.alias = alias;
    }

    public String getReferenciaBuqueda() {
        return referenciaBuqueda;
    }

    public void setReferenciaBuqueda(String referenciaBuqueda) {
        this.referenciaBuqueda = referenciaBuqueda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getMoneda() {
        return moneda;
    }

    public void setMoneda(Short moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMontoVencido() {
        return montoVencido;
    }

    public void setMontoVencido(BigDecimal montoVencido) {
        this.montoVencido = montoVencido;
    }

    public BigDecimal getInteresMoratorio() {
        return interesMoratorio;
    }

    public void setInteresMoratorio(BigDecimal interesMoratorio) {
        this.interesMoratorio = interesMoratorio;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Character getCobrarVencido() {
        return cobrarVencido;
    }

    public void setCobrarVencido(Character cobrarVencido) {
        this.cobrarVencido = cobrarVencido;
    }

    public Short getTipoRecargo() {
        return tipoRecargo;
    }

    public void setTipoRecargo(Short tipoRecargo) {
        this.tipoRecargo = tipoRecargo;
    }

    public Character getPagarMasVencido() {
        return pagarMasVencido;
    }

    public void setPagarMasVencido(Character pagarMasVencido) {
        this.pagarMasVencido = pagarMasVencido;
    }

    public Character getSoloEfectivo() {
        return soloEfectivo;
    }

    public void setSoloEfectivo(Character soloEfectivo) {
        this.soloEfectivo = soloEfectivo;
    }

    public Character getAnulable() {
        return anulable;
    }

    public void setAnulable(Character anulable) {
        this.anulable = anulable;
    }

    public Character getPendiente() {
        return pendiente;
    }

    public void setPendiente(Character pendiente) {
        this.pendiente = pendiente;
    }

    public AliasServicioFacturador getAliasServicioFacturador() {
        return aliasServicioFacturador;
    }

    public void setAliasServicioFacturador(AliasServicioFacturador aliasServicioFacturador) {
        this.aliasServicioFacturador = aliasServicioFacturador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datoConsultaPK != null ? datoConsultaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatoConsulta)) {
            return false;
        }
        DatoConsulta other = (DatoConsulta) object;
        if ((this.datoConsultaPK == null && other.datoConsultaPK != null) || (this.datoConsultaPK != null && !this.datoConsultaPK.equals(other.datoConsultaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DatoConsulta[datoConsultaPK=" + datoConsultaPK + "]";
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
