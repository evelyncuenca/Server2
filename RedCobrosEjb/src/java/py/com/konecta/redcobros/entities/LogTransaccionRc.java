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
import javax.persistence.JoinColumn;
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
@Table(name = "LOG_TRANSACCION_RC")
@NamedQueries({@NamedQuery(name = "LogTransaccionRc.findAll", query = "SELECT l FROM LogTransaccionRc l"), @NamedQuery(name = "LogTransaccionRc.findByIdLogTransaccionRc", query = "SELECT l FROM LogTransaccionRc l WHERE l.idLogTransaccionRc = :idLogTransaccionRc"), @NamedQuery(name = "LogTransaccionRc.findByReferenciaPago", query = "SELECT l FROM LogTransaccionRc l WHERE l.referenciaPago = :referenciaPago"), @NamedQuery(name = "LogTransaccionRc.findByMonto", query = "SELECT l FROM LogTransaccionRc l WHERE l.monto = :monto"), @NamedQuery(name = "LogTransaccionRc.findByMensaje", query = "SELECT l FROM LogTransaccionRc l WHERE l.mensaje = :mensaje"), @NamedQuery(name = "LogTransaccionRc.findByError", query = "SELECT l FROM LogTransaccionRc l WHERE l.error = :error"), @NamedQuery(name = "LogTransaccionRc.findByFechaIngreso", query = "SELECT l FROM LogTransaccionRc l WHERE l.fechaIngreso = :fechaIngreso"), @NamedQuery(name = "LogTransaccionRc.findByFechaTermino", query = "SELECT l FROM LogTransaccionRc l WHERE l.fechaTermino = :fechaTermino"), @NamedQuery(name = "LogTransaccionRc.findByComision", query = "SELECT l FROM LogTransaccionRc l WHERE l.comision = :comision"), @NamedQuery(name = "LogTransaccionRc.findByAdicional", query = "SELECT l FROM LogTransaccionRc l WHERE l.adicional = :adicional")})
public class LogTransaccionRc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LOG_TRANSACCION_RC")
    private Long idLogTransaccionRc;
    @Column(name = "REFERENCIA_PAGO")
    private String referenciaPago;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "MENSAJE")
    private String mensaje;
    @Basic(optional = false)
    @Column(name = "ERROR")
    private short error;
    @Basic(optional = false)
    @Column(name = "FECHA_INGRESO")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "FECHA_TERMINO")
    @Temporal(TemporalType.DATE)
    private Date fechaTermino;
    @Column(name = "COMISION")
    private BigDecimal comision;
    @Column(name = "ADICIONAL")
    private String adicional;
    @JoinColumn(name = "ID_GESTION", referencedColumnName = "ID_GESTION")
    @ManyToOne
    private Gestion idGestion;
    @JoinColumn(name = "ID_PATRON", referencedColumnName = "ID_PATRON")
    @ManyToOne
    private PatronPagina idPatron;
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO")
    @ManyToOne
    private ServicioRc idServicio;
    @JoinColumn(name = "ID_TIPO_CAPTURA", referencedColumnName = "ID_TIPO_CAPTURA")
    @ManyToOne
    private TipoCaptura idTipoCaptura;
    @JoinColumn(name = "ID_TIPO_OPERACION", referencedColumnName = "ID_TIPO_OPERACION")
    @ManyToOne
    private TipoOperacion idTipoOperacion;
    @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID_TRANSACCION")
    @ManyToOne
    private TransaccionRc idTransaccion;

    public LogTransaccionRc() {
    }

    public LogTransaccionRc(Long idLogTransaccionRc) {
        this.idLogTransaccionRc = idLogTransaccionRc;
    }

    public LogTransaccionRc(Long idLogTransaccionRc, short error, Date fechaIngreso) {
        this.idLogTransaccionRc = idLogTransaccionRc;
        this.error = error;
        this.fechaIngreso = fechaIngreso;
    }

    public Long getIdLogTransaccionRc() {
        return idLogTransaccionRc;
    }

    public void setIdLogTransaccionRc(Long idLogTransaccionRc) {
        this.idLogTransaccionRc = idLogTransaccionRc;
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public short getError() {
        return error;
    }

    public void setError(short error) {
        this.error = error;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public String getAdicional() {
        return adicional;
    }

    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }

    public Gestion getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Gestion idGestion) {
        this.idGestion = idGestion;
    }

    public PatronPagina getIdPatron() {
        return idPatron;
    }

    public void setIdPatron(PatronPagina idPatron) {
        this.idPatron = idPatron;
    }

    public ServicioRc getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(ServicioRc idServicio) {
        this.idServicio = idServicio;
    }

    public TipoCaptura getIdTipoCaptura() {
        return idTipoCaptura;
    }

    public void setIdTipoCaptura(TipoCaptura idTipoCaptura) {
        this.idTipoCaptura = idTipoCaptura;
    }

    public TipoOperacion getIdTipoOperacion() {
        return idTipoOperacion;
    }

    public void setIdTipoOperacion(TipoOperacion idTipoOperacion) {
        this.idTipoOperacion = idTipoOperacion;
    }

    public TransaccionRc getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(TransaccionRc idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogTransaccionRc != null ? idLogTransaccionRc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogTransaccionRc)) {
            return false;
        }
        LogTransaccionRc other = (LogTransaccionRc) object;
        if ((this.idLogTransaccionRc == null && other.idLogTransaccionRc != null) || (this.idLogTransaccionRc != null && !this.idLogTransaccionRc.equals(other.idLogTransaccionRc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.LogTransaccionRc[idLogTransaccionRc=" + idLogTransaccionRc + "]";
    }

}
