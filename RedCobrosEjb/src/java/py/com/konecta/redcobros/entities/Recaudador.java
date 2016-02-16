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
@Table(name = "RECAUDADOR")
@NamedQueries({
    @NamedQuery(name = "Recaudador.findAll", query = "SELECT r FROM Recaudador r")})
public class Recaudador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RECAUDADOR")
    @GeneratedValue(generator = "recaudadorSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "recaudadorSeq", table = "SECUENCIA",
    allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
    pkColumnValue = "recaudadorSeq", initialValue = 1)
    private Long idRecaudador;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "CODIGO_RECAUDADOR")
    private Integer codigoRecaudador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recaudador")
    private Collection<Sucursal> sucursalCollection;
    @JoinColumn(name = "ENTIDAD", referencedColumnName = "ID_ENTIDAD")
    @ManyToOne(optional = false)
    private Entidad entidad;
    @JoinColumn(name = "CONTACTO", referencedColumnName = "ID_PERSONA")
    @ManyToOne(optional = false)
    private Persona contacto;
    @JoinTable(name = "HABILITACION_SERV_REC", joinColumns = {
        @JoinColumn(name = "ID_RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO")})
    @ManyToMany
    private Collection<Servicio> servicioCollection;
    @JoinColumn(name = "RED", referencedColumnName = "ID_RED")
    @ManyToOne(optional = false)
    private Red red;
    @Column(name = "NUMERO_ORDEN_PROXIMO")
    private Long numeroOrdenProximo;
    @Column(name = "NUMERO_ORDEN_MAXIMO")
    private Long numeroOrdenMaximo;
    @Basic(optional = false)
    @Column(name = "NUMERO_ORDEN_TAM_RANGO")
    private Integer numeroOrdenTamRango;
    //cuenta para recaudacion
    @Basic(optional = false)
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    //cuenta para cobrar comisiones
    @Column(name = "NUMERO_CUENTA_COMISION")
    private String numeroCuentaComision;
    @Column(name = "HABILITADO")
    private String habilitado;
    @Column(name = "CONCILIAR_CAJA")
    private String conciliarCaja;
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Column(name = "RETENIDO")
    private String retenido;

    public String getRetenido() {
        return retenido;
    }

    public void setRetenido(String retenido) {
        this.retenido = retenido;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getNumeroCuentaComision() {
        return numeroCuentaComision;
    }

    public void setNumeroCuentaComision(String numeroCuentaComision) {
        this.numeroCuentaComision = numeroCuentaComision;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Long getNumeroOrdenMaximo() {
        return numeroOrdenMaximo;
    }

    public void setNumeroOrdenMaximo(Long numeroOrdenMaximo) {
        this.numeroOrdenMaximo = numeroOrdenMaximo;
    }

    public Long getNumeroOrdenProximo() {
        return numeroOrdenProximo;
    }

    public void setNumeroOrdenProximo(Long numeroOrdenProximo) {
        this.numeroOrdenProximo = numeroOrdenProximo;
    }

    public Integer getNumeroOrdenTamRango() {
        return numeroOrdenTamRango;
    }

    public void setNumeroOrdenTamRango(Integer numeroOrdenTamRango) {
        this.numeroOrdenTamRango = numeroOrdenTamRango;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public Recaudador() {
    }

    public Recaudador(Long idRecaudador) {
        this.idRecaudador = idRecaudador;
    }

    public Recaudador(Long idRecaudador, String descripcion) {
        this.idRecaudador = idRecaudador;
        this.descripcion = descripcion;
    }

    public Long getIdRecaudador() {
        return idRecaudador;
    }

    public void setIdRecaudador(Long idRecaudador) {
        this.idRecaudador = idRecaudador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodigoRecaudador() {
        return codigoRecaudador;
    }

    public void setCodigoRecaudador(Integer codigoRecaudador) {
        this.codigoRecaudador = codigoRecaudador;
    }

    public Collection<Sucursal> getSucursalCollection() {
        return sucursalCollection;
    }

    public void setSucursalCollection(Collection<Sucursal> sucursalCollection) {
        this.sucursalCollection = sucursalCollection;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Persona getContacto() {
        return contacto;
    }

    public void setContacto(Persona contacto) {
        this.contacto = contacto;
    }

    public Collection<Servicio> getServicioCollection() {
        return servicioCollection;
    }

    public void setServicioCollection(Collection<Servicio> servicioCollection) {
        this.servicioCollection = servicioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecaudador != null ? idRecaudador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recaudador)) {
            return false;
        }
        Recaudador other = (Recaudador) object;
        if ((this.idRecaudador == null && other.idRecaudador != null) || (this.idRecaudador != null && !this.idRecaudador.equals(other.idRecaudador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Recaudador[idRecaudador=" + idRecaudador + "]";
    }

    /**
     * @return the habilitado
     */
    public String getHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * @return the conciliarCaja
     */
    public String getConciliarCaja() {
        return conciliarCaja;
    }

    /**
     * @param conciliarCaja the conciliarCaja to set
     */
    public void setConciliarCaja(String conciliarCaja) {
        this.conciliarCaja = conciliarCaja;
    }
}
