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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Null;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "SUCURSAL")
@NamedQueries({@NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s")})
public class Sucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SUCURSAL")
    @GeneratedValue(generator = "sucursalSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "sucursalSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="sucursalSeq",initialValue=1)
    private Long idSucursal;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "CODIGO_SUCURSAL")
    private Integer codigoSucursal;
    @Basic(optional = true)
    @Column(name = "CODIGO_SUCURSAL_SET")
    private Integer codigoSucursalSet;
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private String telefono;
    @JoinColumn(name = "LOCALIDAD", referencedColumnName = "ID_LOCALIDAD")
    @ManyToOne(optional = false)
    private Localidad localidad;
    @JoinColumn(name = "CONTACTO", referencedColumnName = "ID_PERSONA")
    @ManyToOne
    private Persona contacto;
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    @ManyToOne(optional = false)
    private Recaudador recaudador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursal")
    private Collection<Terminal> terminalCollection;
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    @Column(name = "ES_TIGO")
    private String esTigo;
    @Column(name = "ZONA")
    private Integer zona;

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public Integer getCodigoSucursalSet() {
        return codigoSucursalSet;
    }

    public void setCodigoSucursalSet(Integer codigoSucursalSet) {
        this.codigoSucursalSet = codigoSucursalSet;
    }
    

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Sucursal() {
    }

    public Sucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Sucursal(Long idSucursal, String descripcion) {
        this.idSucursal = idSucursal;
        this.descripcion = descripcion;
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

   

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Persona getContacto() {
        return contacto;
    }

    public void setContacto(Persona contacto) {
        this.contacto = contacto;
    }

    public Recaudador getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Recaudador recaudador) {
        this.recaudador = recaudador;
    }

    public Collection<Terminal> getTerminalCollection() {
        return terminalCollection;
    }

    public void setTerminalCollection(Collection<Terminal> terminalCollection) {
        this.terminalCollection = terminalCollection;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSucursal != null ? idSucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.idSucursal == null && other.idSucursal != null) || (this.idSucursal != null && !this.idSucursal.equals(other.idSucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Sucursal[idSucursal=" + idSucursal + "]";
    }

    /**
     * @return the codigoSucursal
     */
    public Integer getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * @param codigoSucursal the codigoSucursal to set
     */
    public void setCodigoSucursal(Integer codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    /**
     * @return the esTigo
     */
    public String getEsTigo() {
        return esTigo;
    }

    /**
     * @param esTigo the esTigo to set
     */
    public void setEsTigo(String esTigo) {
        this.esTigo = esTigo;
    }

}
