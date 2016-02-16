/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "TRANSACCION_RC")
@NamedQueries({@NamedQuery(name = "TransaccionRc.findAll", query = "SELECT t FROM TransaccionRc t"), @NamedQuery(name = "TransaccionRc.findByIdTransaccion", query = "SELECT t FROM TransaccionRc t WHERE t.idTransaccion = :idTransaccion"), @NamedQuery(name = "TransaccionRc.findByNroBoleta", query = "SELECT t FROM TransaccionRc t WHERE t.nroBoleta = :nroBoleta"), @NamedQuery(name = "TransaccionRc.findByAnulado", query = "SELECT t FROM TransaccionRc t WHERE t.anulado = :anulado"), @NamedQuery(name = "TransaccionRc.findByReferenciaPago", query = "SELECT t FROM TransaccionRc t WHERE t.referenciaPago = :referenciaPago"), @NamedQuery(name = "TransaccionRc.findByMonto", query = "SELECT t FROM TransaccionRc t WHERE t.monto = :monto"), @NamedQuery(name = "TransaccionRc.findByFechaIngreso", query = "SELECT t FROM TransaccionRc t WHERE t.fechaIngreso = :fechaIngreso"), @NamedQuery(name = "TransaccionRc.findByComision", query = "SELECT t FROM TransaccionRc t WHERE t.comision = :comision"), @NamedQuery(name = "TransaccionRc.findByIdCorte", query = "SELECT t FROM TransaccionRc t WHERE t.idCorte = :idCorte")})
public class TransaccionRc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TRANSACCION")
    private BigDecimal idTransaccion;
    @Basic(optional = false)
    @Column(name = "NRO_BOLETA")
    private Integer nroBoleta;
    @Basic(optional = false)
    @Column(name = "ANULADO")
    private String anulado;
    @Column(name = "REFERENCIA_PAGO")
    private String referenciaPago;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Basic(optional = false)
    @Column(name = "FECHA_INGRESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Column(name = "COMISION")
    private BigDecimal comision;
    @Column(name = "ID_CORTE")
    private Long idCorte;
    @OneToMany(mappedBy = "idTransaccion")
    private Collection<LogTransaccionRc> logTransaccionRcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTransaccion")
    private Collection<DetalleEnLinea> detalleEnLineaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaccionRc")
    private Collection<DatoAdicionalTrx> datoAdicionalTrxCollection;
    @JoinColumn(name = "ID_ESTADO_TRANSACCION", referencedColumnName = "ID_ESTADO_TRANSACCION")
    @ManyToOne(optional = false)
    private EstadoTransaccion idEstadoTransaccion;
    @JoinColumn(name = "ID_GESTION", referencedColumnName = "ID_GESTION")
    @ManyToOne(optional = false)
    private Gestion idGestion;
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO")
    @ManyToOne(optional = false)
    private ServicioRc idServicio;
    @JoinColumn(name = "ID_TIPO_CAPTURA", referencedColumnName = "ID_TIPO_CAPTURA")
    @ManyToOne(optional = false)
    private TipoCaptura idTipoCaptura;
    @JoinColumn(name = "ID_TIPO_OPERACION", referencedColumnName = "ID_TIPO_OPERACION")
    @ManyToOne(optional = false)
    private TipoOperacion idTipoOperacion;
    @JoinColumn(name = "ID_TIPO_PAGO", referencedColumnName = "ID_TIPO_PAGO")
    @ManyToOne(optional = false)
    private TipoPago idTipoPago;

    public TransaccionRc() {
    }

    public TransaccionRc(BigDecimal idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public TransaccionRc(BigDecimal idTransaccion, int nroBoleta, String anulado, Date fechaIngreso) {
        this.idTransaccion = idTransaccion;
        this.nroBoleta = nroBoleta;
        this.anulado = anulado;
        this.fechaIngreso = fechaIngreso;
    }

    public BigDecimal getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(BigDecimal idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Integer getNroBoleta() {
        return nroBoleta;
    }

    public void setNroBoleta(Integer nroBoleta) {
        this.nroBoleta = nroBoleta;
    }

    public String getAnulado() {
        return anulado;
    }

    public void setAnulado(String anulado) {
        this.anulado = anulado;
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public Long getIdCorte() {
        return idCorte;
    }

    public void setIdCorte(Long idCorte) {
        this.idCorte = idCorte;
    }

    public Collection<LogTransaccionRc> getLogTransaccionRcCollection() {
        return logTransaccionRcCollection;
    }

    public void setLogTransaccionRcCollection(Collection<LogTransaccionRc> logTransaccionRcCollection) {
        this.logTransaccionRcCollection = logTransaccionRcCollection;
    }

    public Collection<DetalleEnLinea> getDetalleEnLineaCollection() {
        return detalleEnLineaCollection;
    }

    public void setDetalleEnLineaCollection(Collection<DetalleEnLinea> detalleEnLineaCollection) {
        this.detalleEnLineaCollection = detalleEnLineaCollection;
    }

    public Collection<DatoAdicionalTrx> getDatoAdicionalTrxCollection() {
        return datoAdicionalTrxCollection;
    }

    public void setDatoAdicionalTrxCollection(Collection<DatoAdicionalTrx> datoAdicionalTrxCollection) {
        this.datoAdicionalTrxCollection = datoAdicionalTrxCollection;
    }

    public EstadoTransaccion getIdEstadoTransaccion() {
        return idEstadoTransaccion;
    }

    public void setIdEstadoTransaccion(EstadoTransaccion idEstadoTransaccion) {
        this.idEstadoTransaccion = idEstadoTransaccion;
    }

    public Gestion getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Gestion idGestion) {
        this.idGestion = idGestion;
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

    public TipoPago getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(TipoPago idTipoPago) {
        this.idTipoPago = idTipoPago;
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
        if (!(object instanceof TransaccionRc)) {
            return false;
        }
        TransaccionRc other = (TransaccionRc) object;
        if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TransaccionRc[idTransaccion=" + idTransaccion + "]";
    }

    public String toStringDetalleAnde(Integer numeroGestion){

        String cadena="";

        //identificador del registro
        cadena+="D";
        //agencia
        cadena+=UtilesSet.cerosIzquierda(
                this.getIdGestion().getTerminal().getSucursal().getLocalidad().getCiudad().getDepartamento().getIdDepartamento().longValue(),
                4);
        //sucursal
        cadena+=UtilesSet.cerosIzquierda(
                this.getIdGestion().getTerminal().getSucursal().getCodigoSucursal().longValue(),
                4);
        //fecha
        cadena+=this.getFechaIngreso();
        //numero gestion
        cadena+=numeroGestion;



        return "";
    }

    

}
