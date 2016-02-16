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

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "ENTIDAD")
@NamedQueries({@NamedQuery(name = "Entidad.findAll", query = "SELECT e FROM Entidad e")})
public class Entidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ENTIDAD")
    @GeneratedValue(generator = "entidadSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "entidadSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="entidadSeq",initialValue=1)
    private Long idEntidad;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "RUC_CI")
    private String rucCi;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidad")
    private Collection<Recaudador> recaudadorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidad")
    private Collection<Red> redCollection;
    @JoinColumn(name = "LOCALIDAD", referencedColumnName = "ID_LOCALIDAD")
    @ManyToOne(optional = false)
    private Localidad localidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidad")
    private Collection<EntidadFinanciera> entidadFinancieraCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidad")
    private Collection<Facturador> facturadorCollection;

    public Entidad() {
    }

    public Entidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public Entidad(Long idEntidad, String nombre, String direccion) {
        this.idEntidad = idEntidad;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRucCi() {
        return rucCi;
    }

    public void setRucCi(String rucCi) {
        this.rucCi = rucCi;
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

    public Collection<Recaudador> getRecaudadorCollection() {
        return recaudadorCollection;
    }

    public void setRecaudadorCollection(Collection<Recaudador> recaudadorCollection) {
        this.recaudadorCollection = recaudadorCollection;
    }

    public Collection<Red> getRedCollection() {
        return redCollection;
    }

    public void setRedCollection(Collection<Red> redCollection) {
        this.redCollection = redCollection;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Collection<EntidadFinanciera> getEntidadFinancieraCollection() {
        return entidadFinancieraCollection;
    }

    public void setEntidadFinancieraCollection(Collection<EntidadFinanciera> entidadFinancieraCollection) {
        this.entidadFinancieraCollection = entidadFinancieraCollection;
    }

    public Collection<Facturador> getFacturadorCollection() {
        return facturadorCollection;
    }

    public void setFacturadorCollection(Collection<Facturador> facturadorCollection) {
        this.facturadorCollection = facturadorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntidad != null ? idEntidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidad)) {
            return false;
        }
        Entidad other = (Entidad) object;
        if ((this.idEntidad == null && other.idEntidad != null) || (this.idEntidad != null && !this.idEntidad.equals(other.idEntidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Entidad[idEntidad=" + idEntidad + "]";
    }

}
