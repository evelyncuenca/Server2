/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "TRANSACCION")
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t")})
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TRANSACCION")
    @GeneratedValue(generator = "transaccionSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "transaccionSeq", table = "SECUENCIA",
    allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
    pkColumnValue = "transaccionSeq", initialValue = 1)
    private Long idTransaccion;
    @Basic(optional = false)
    @Column(name = "MONTO_TOTAL")
    private Double montoTotal;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_SISTEMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraSistema;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_RED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRed;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_TERMINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraTerminal;
    @Column(name = "COMENTARIO")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "FLAG_ANULADO")
    private String flagAnulado;
    @Column(name = "TICKET")
    private String ticket;
    @Column(name = "CERTIFICACION")
    private String certificacion;
    @Basic(optional = false)
    @Column(name = "POSICION_GESTION")
    private Integer posicionGestion;
    @Column(name = "MIGRADO", length = 1)
    private Character migrado;

    public Character getMigrado() {
        return migrado;
    }

    public void setMigrado(Character migrado) {
        this.migrado = migrado;
    }

    public Integer getPosicionGestion() {
        return posicionGestion;
    }

    public void setPosicionGestion(Integer posicionGestion) {
        this.posicionGestion = posicionGestion;
    }

    public String getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getFlagAnulado() {
        return flagAnulado;
    }

    public void setFlagAnulado(String flagAnulado) {
        this.flagAnulado = flagAnulado;
    }

    public EntidadFinanciera getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public void setEntidadFinanciera(EntidadFinanciera entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

    public Date getFechaCheque() {
        return fechaCheque;
    }

    public void setFechaCheque(Date fechaCheque) {
        this.fechaCheque = fechaCheque;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }
    @Column(name = "NUMERO_CHEQUE")
    private String numeroCheque;
    @Column(name = "FECHA_CHEQUE")
    @Temporal(TemporalType.DATE)
    private Date fechaCheque;
    @JoinColumn(name = "ENTIDAD_FINANCIERA", referencedColumnName = "ID_ENTIDAD_FINANCIERA")
    @ManyToOne
    private EntidadFinanciera entidadFinanciera;
    @Basic(optional = false)
    @Column(name = "CODIGO_TRANSACCIONAL")
    private String codigoTransaccional;
    @OneToMany(mappedBy = "transaccion")
    private Collection<LogHistoricoTrans> logHistoricoTransCollection;
    @OneToOne(mappedBy = "transaccion")
    private FormContrib formContrib;
    @OneToOne(mappedBy = "transaccion")
    private BoletaPagoContrib boletaPagoContrib;
    @JoinColumn(name = "ESTADO_TRANSACCION", referencedColumnName = "ID_ESTADO_TRANSACCION")
    @ManyToOne(optional = false)
    private EstadoTransaccion estadoTransaccion;
    @JoinColumn(name = "MONEDA", referencedColumnName = "ID_MONEDA")
    @ManyToOne(optional = false)
    private Moneda moneda;
    @JoinColumn(name = "GESTION", referencedColumnName = "ID_GESTION")
    @ManyToOne(optional = false)
    private Gestion gestion;
    @JoinColumn(name = "TIPO_PAGO", referencedColumnName = "ID_TIPO_PAGO")
    @ManyToOne
    private TipoPago tipoPago;

    public Transaccion() {
    }

    public Transaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Transaccion(Long idTransaccion, Double montoTotal, Date fechaHoraSistema, Date fechaHoraRed, Date fechaHoraTerminal) {
        this.idTransaccion = idTransaccion;
        this.montoTotal = montoTotal;
        this.fechaHoraSistema = fechaHoraSistema;
        this.fechaHoraRed = fechaHoraRed;
        this.fechaHoraTerminal = fechaHoraTerminal;


    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Date getFechaHoraSistema() {
        return fechaHoraSistema;
    }

    public void setFechaHoraSistema(Date fechaHoraSistema) {
        this.fechaHoraSistema = fechaHoraSistema;
    }

    public Date getFechaHoraRed() {
        return fechaHoraRed;
    }

    public void setFechaHoraRed(Date fechaHoraRed) {
        this.fechaHoraRed = fechaHoraRed;
    }

    public Date getFechaHoraTerminal() {
        return fechaHoraTerminal;
    }

    public void setFechaHoraTerminal(Date fechaHoraTerminal) {
        this.fechaHoraTerminal = fechaHoraTerminal;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCodigoTransaccional() {
        return codigoTransaccional;
    }

    public void setCodigoTransaccional(String codigoTransaccional) {
        this.codigoTransaccional = codigoTransaccional;
    }

    public Collection<LogHistoricoTrans> getLogHistoricoTransCollection() {
        return logHistoricoTransCollection;
    }

    public void setLogHistoricoTransCollection(Collection<LogHistoricoTrans> logHistoricoTransCollection) {
        this.logHistoricoTransCollection = logHistoricoTransCollection;
    }

    public FormContrib getFormContrib() {
        return formContrib;
    }

    public void setFormContrib(FormContrib formContrib) {
        this.formContrib = formContrib;
    }

    public BoletaPagoContrib getBoletaPagoContrib() {
        return boletaPagoContrib;
    }

    public void setBoletaPagoContrib(BoletaPagoContrib boletaPagoContrib) {
        this.boletaPagoContrib = boletaPagoContrib;
    }

    public EstadoTransaccion getEstadoTransaccion() {
        return estadoTransaccion;
    }

    public void setEstadoTransaccion(EstadoTransaccion estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Gestion getGestion() {
        return gestion;
    }

    public void setGestion(Gestion gestion) {
        this.gestion = gestion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaccion != null ? idTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Transaccion[idTransaccion=" + idTransaccion + "]";
    }

    /**
     * @return the tipoPago
     */
    public TipoPago getTipoPago() {
        return tipoPago;
    }

    /**
     * @param tipoPago the tipoPago to set
     */
    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }
}
