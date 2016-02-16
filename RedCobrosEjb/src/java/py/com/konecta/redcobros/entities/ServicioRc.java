/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.Size;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "SERVICIO_RC")
@NamedQueries({
    @NamedQuery(name = "ServicioRc.findAll", query = "SELECT s FROM ServicioRc s"),
    @NamedQuery(name = "ServicioRc.findByIdServicio", query = "SELECT s FROM ServicioRc s WHERE s.idServicio = :idServicio"),
    @NamedQuery(name = "ServicioRc.findByDescripcion", query = "SELECT s FROM ServicioRc s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "ServicioRc.findByAnulable", query = "SELECT s FROM ServicioRc s WHERE s.anulable = :anulable"),
    @NamedQuery(name = "ServicioRc.findByCbPresente", query = "SELECT s FROM ServicioRc s WHERE s.cbPresente = :cbPresente"),
    @NamedQuery(name = "ServicioRc.findByCbLongitud", query = "SELECT s FROM ServicioRc s WHERE s.cbLongitud = :cbLongitud"),
    @NamedQuery(name = "ServicioRc.findByHabilitado", query = "SELECT s FROM ServicioRc s WHERE s.habilitado = :habilitado")})
public class ServicioRc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO")
    private Integer idServicio;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ANULABLE")
    private String anulable;
    @Basic(optional = false)
    @Column(name = "CB_PRESENTE")
    private String cbPresente;
    @Basic(optional = false)
    @Column(name = "CB_LONGITUD")
    private Short cbLongitud;
    @Basic(optional = false)
    @Column(name = "HABILITADO")
    private String habilitado;
    @JoinColumn(name = "ID_COMPLEMENTO", referencedColumnName = "ID_COMPLEMENTO")
    @ManyToOne
    private Complemento idComplemento;
    @JoinColumn(name = "ID_FACTURADOR", referencedColumnName = "ID_FACTURADOR")
    @ManyToOne(optional = false)
    private Facturador idFacturador;
    @JoinColumn(name = "ID_MONEDA", referencedColumnName = "ID_MONEDA")
    @ManyToOne(optional = false)
    private Moneda idMoneda;
    @OneToMany(mappedBy = "idServicioPadre")
    private Collection<ServicioRc> servicioRcCollection;
    @JoinColumn(name = "ID_SERVICIO_PADRE", referencedColumnName = "ID_SERVICIO")
    @ManyToOne
    private ServicioRc idServicioPadre;
    @JoinColumn(name = "ID_TIPO_SERVICIO", referencedColumnName = "ID_TIPO_SERVICIO")
    @ManyToOne(optional = false)
    private TipoServicio idTipoServicio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicioRc")
    private Collection<ServicioCarrier> servicioCarrierCollection;
    @OneToMany(mappedBy = "idServicio")
    private Collection<LogTransaccionRc> logTransaccionRcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicio")
    private Collection<TransaccionRc> transaccionRcCollection;
    @Column(name = "IMAGEN")
    private byte[] imagen;
    @Size(max = 30)
    @Column(name = "NOMBRE_IMAGEN")
    private String nombreImagen;

    public ServicioRc() {
    }

    public ServicioRc(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public ServicioRc(Integer idServicio, String descripcion, String anulable, String cbPresente, short cbLongitud, String habilitado) {
        this.idServicio = idServicio;
        this.descripcion = descripcion;
        this.anulable = anulable;
        this.cbPresente = cbPresente;
        this.cbLongitud = cbLongitud;
        this.habilitado = habilitado;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAnulable() {
        return anulable;
    }

    public void setAnulable(String anulable) {
        this.anulable = anulable;
    }

    public String getCbPresente() {
        return cbPresente;
    }

    public void setCbPresente(String cbPresente) {
        this.cbPresente = cbPresente;
    }

    public Short getCbLongitud() {
        return cbLongitud;
    }

    public void setCbLongitud(short cbLongitud) {
        this.cbLongitud = cbLongitud;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public Complemento getIdComplemento() {
        return idComplemento;
    }

    public void setIdComplemento(Complemento idComplemento) {
        this.idComplemento = idComplemento;
    }

    public Facturador getIdFacturador() {
        return idFacturador;
    }

    public void setIdFacturador(Facturador idFacturador) {
        this.idFacturador = idFacturador;
    }

    public Moneda getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Moneda idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Collection<ServicioRc> getServicioRcCollection() {
        return servicioRcCollection;
    }

    public void setServicioRcCollection(Collection<ServicioRc> servicioRcCollection) {
        this.servicioRcCollection = servicioRcCollection;
    }

    public ServicioRc getIdServicioPadre() {
        return idServicioPadre;
    }

    public void setIdServicioPadre(ServicioRc idServicioPadre) {
        this.idServicioPadre = idServicioPadre;
    }

    public TipoServicio getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(TipoServicio idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Collection<ServicioCarrier> getServicioCarrierCollection() {
        return servicioCarrierCollection;
    }

    public void setServicioCarrierCollection(Collection<ServicioCarrier> servicioCarrierCollection) {
        this.servicioCarrierCollection = servicioCarrierCollection;
    }

    public Collection<LogTransaccionRc> getLogTransaccionRcCollection() {
        return logTransaccionRcCollection;
    }

    public void setLogTransaccionRcCollection(Collection<LogTransaccionRc> logTransaccionRcCollection) {
        this.logTransaccionRcCollection = logTransaccionRcCollection;
    }

    public Collection<TransaccionRc> getTransaccionRcCollection() {
        return transaccionRcCollection;
    }

    public void setTransaccionRcCollection(Collection<TransaccionRc> transaccionRcCollection) {
        this.transaccionRcCollection = transaccionRcCollection;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioRc)) {
            return false;
        }
        ServicioRc other = (ServicioRc) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ServicioRc[idServicio=" + idServicio + "]";
    }

}
