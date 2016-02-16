/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
@Table(name = "DETALLE_EN_LINEA")
@NamedQueries({@NamedQuery(name = "DetalleEnLinea.findAll", query = "SELECT d FROM DetalleEnLinea d"), @NamedQuery(name = "DetalleEnLinea.findByIdDetalleEnLinea", query = "SELECT d FROM DetalleEnLinea d WHERE d.detalleEnLineaPK.idDetalleEnLinea = :idDetalleEnLinea"), @NamedQuery(name = "DetalleEnLinea.findByFechaIngreso", query = "SELECT d FROM DetalleEnLinea d WHERE d.detalleEnLineaPK.fechaIngreso = :fechaIngreso"), @NamedQuery(name = "DetalleEnLinea.findByFechaRespuesta", query = "SELECT d FROM DetalleEnLinea d WHERE d.fechaRespuesta = :fechaRespuesta"), @NamedQuery(name = "DetalleEnLinea.findByCodAutorizacion", query = "SELECT d FROM DetalleEnLinea d WHERE d.codAutorizacion = :codAutorizacion"), @NamedQuery(name = "DetalleEnLinea.findByMensajeEnviado", query = "SELECT d FROM DetalleEnLinea d WHERE d.mensajeEnviado = :mensajeEnviado"), @NamedQuery(name = "DetalleEnLinea.findByMensajeRecibido", query = "SELECT d FROM DetalleEnLinea d WHERE d.mensajeRecibido = :mensajeRecibido"), @NamedQuery(name = "DetalleEnLinea.findByIdRespuestaCarrier", query = "SELECT d FROM DetalleEnLinea d WHERE d.idRespuestaCarrier = :idRespuestaCarrier")})
public class DetalleEnLinea implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetalleEnLineaPK detalleEnLineaPK;
    @Column(name = "FECHA_RESPUESTA")
    @Temporal(TemporalType.DATE)
    private Date fechaRespuesta;
    @Column(name = "COD_AUTORIZACION")
    private String codAutorizacion;
    @Column(name = "MENSAJE_ENVIADO")
    private String mensajeEnviado;
    @Column(name = "MENSAJE_RECIBIDO")
    private String mensajeRecibido;
    @Column(name = "ID_RESPUESTA_CARRIER")
    private String idRespuestaCarrier;
    @JoinColumns({@JoinColumn(name = "ID_SERVICIO_CARRIER", referencedColumnName = "ID_SERVICIO_CARRIER"), @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO")})
    @ManyToOne(optional = false)
    private ServicioCarrier servicioCarrier;
    @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID_TRANSACCION")
    @ManyToOne(optional = false)
    private TransaccionRc idTransaccion;

    public DetalleEnLinea() {
    }

    public DetalleEnLinea(DetalleEnLineaPK detalleEnLineaPK) {
        this.detalleEnLineaPK = detalleEnLineaPK;
    }

    public DetalleEnLinea(BigInteger idDetalleEnLinea, Date fechaIngreso) {
        this.detalleEnLineaPK = new DetalleEnLineaPK(idDetalleEnLinea, fechaIngreso);
    }

    public DetalleEnLineaPK getDetalleEnLineaPK() {
        return detalleEnLineaPK;
    }

    public void setDetalleEnLineaPK(DetalleEnLineaPK detalleEnLineaPK) {
        this.detalleEnLineaPK = detalleEnLineaPK;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodAutorizacion(String codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public String getMensajeEnviado() {
        return mensajeEnviado;
    }

    public void setMensajeEnviado(String mensajeEnviado) {
        this.mensajeEnviado = mensajeEnviado;
    }

    public String getMensajeRecibido() {
        return mensajeRecibido;
    }

    public void setMensajeRecibido(String mensajeRecibido) {
        this.mensajeRecibido = mensajeRecibido;
    }

    public String getIdRespuestaCarrier() {
        return idRespuestaCarrier;
    }

    public void setIdRespuestaCarrier(String idRespuestaCarrier) {
        this.idRespuestaCarrier = idRespuestaCarrier;
    }

    public ServicioCarrier getServicioCarrier() {
        return servicioCarrier;
    }

    public void setServicioCarrier(ServicioCarrier servicioCarrier) {
        this.servicioCarrier = servicioCarrier;
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
        hash += (detalleEnLineaPK != null ? detalleEnLineaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleEnLinea)) {
            return false;
        }
        DetalleEnLinea other = (DetalleEnLinea) object;
        if ((this.detalleEnLineaPK == null && other.detalleEnLineaPK != null) || (this.detalleEnLineaPK != null && !this.detalleEnLineaPK.equals(other.detalleEnLineaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DetalleEnLinea[detalleEnLineaPK=" + detalleEnLineaPK + "]";
    }

}
