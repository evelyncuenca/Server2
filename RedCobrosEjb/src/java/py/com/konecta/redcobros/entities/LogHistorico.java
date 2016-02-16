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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "LOG_HISTORICO")
@NamedQueries({@NamedQuery(name = "LogHistorico.findAll", query = "SELECT l FROM LogHistorico l")})
public class LogHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LOG_HISTORICO")
    @GeneratedValue(generator = "logHistoricoSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "logHistoricoSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="logHistoricoSeq",initialValue=1)
    private Long idLogHistorico;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Basic(optional = false)
    @Column(name = "SENTIDO")
    private char sentido;
    @Column(name = "FECHA_HORA_RED")
    private String fechaHoraRed;
    @Column(name = "FECHA_HORA_TERMINAL")
    private String fechaHoraTerminal;
    @Column(name = "RED")
    private Long red;
    @Column(name = "RECAUDADOR")
    private Long recaudador;
    @Column(name = "SUCURSAL")
    private Long sucursal;
    @Column(name = "TIPO_TERMINAL")
    private Integer tipoTerminal;
    @Column(name = "TERMINAL")
    private String terminal;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Column(name = "TRANSACCION")
    private Long transaccion;
    @Column(name = "MONTO")
    private String monto;
    @Column(name = "MENSAJE")
    private String mensaje;
    @Column(name = "ID_TIPO_PAGO")
    private Long idTipoPago;
    @Column(name = "ID_TIPO_MONEDA")
    private Long idTipoMoneda;
    @Column(name = "TASA_CAMBIO")
    private Double tasaCambio;
    @Column(name = "CODIGO_TRANSACCIONAL")
    private String codigoTransaccional;
    @JoinColumn(name = "ID_LOG_HISTORICO_TRANS", referencedColumnName = "ID_LOG_HISTORICO_TRANS")
    @ManyToOne(optional = false)
    private LogHistoricoTrans idLogHistoricoTrans;

    public LogHistorico() {
    }

    public LogHistorico(Long idLogHistorico) {
        this.idLogHistorico = idLogHistorico;
    }

    public LogHistorico(Long idLogHistorico, Date fechaHora, char sentido) {
        this.idLogHistorico = idLogHistorico;
        this.fechaHora = fechaHora;
        this.sentido = sentido;
    }

    public Long getIdLogHistorico() {
        return idLogHistorico;
    }

    public void setIdLogHistorico(Long idLogHistorico) {
        this.idLogHistorico = idLogHistorico;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public char getSentido() {
        return sentido;
    }

    public void setSentido(char sentido) {
        this.sentido = sentido;
    }

    public String getFechaHoraRed() {
        return fechaHoraRed;
    }

    public void setFechaHoraRed(String fechaHoraRed) {
        this.fechaHoraRed = fechaHoraRed;
    }

    public String getFechaHoraTerminal() {
        return fechaHoraTerminal;
    }

    public void setFechaHoraTerminal(String fechaHoraTerminal) {
        this.fechaHoraTerminal = fechaHoraTerminal;
    }

    public Long getRed() {
        return red;
    }

    public void setRed(Long red) {
        this.red = red;
    }

    public Long getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Long recaudador) {
        this.recaudador = recaudador;
    }

    public Long getSucursal() {
        return sucursal;
    }

    public void setSucursal(Long sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getTipoTerminal() {
        return tipoTerminal;
    }

    public void setTipoTerminal(Integer tipoTerminal) {
        this.tipoTerminal = tipoTerminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Long getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Long transaccion) {
        this.transaccion = transaccion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(Long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public Long getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Long idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public Double getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(Double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public String getCodigoTransaccional() {
        return codigoTransaccional;
    }

    public void setCodigoTransaccional(String codigoTransaccional) {
        this.codigoTransaccional = codigoTransaccional;
    }

    public LogHistoricoTrans getIdLogHistoricoTrans() {
        return idLogHistoricoTrans;
    }

    public void setIdLogHistoricoTrans(LogHistoricoTrans idLogHistoricoTrans) {
        this.idLogHistoricoTrans = idLogHistoricoTrans;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogHistorico != null ? idLogHistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogHistorico)) {
            return false;
        }
        LogHistorico other = (LogHistorico) object;
        if ((this.idLogHistorico == null && other.idLogHistorico != null) || (this.idLogHistorico != null && !this.idLogHistorico.equals(other.idLogHistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.LogHistorico[idLogHistorico=" + idLogHistorico + "]";
    }

}
