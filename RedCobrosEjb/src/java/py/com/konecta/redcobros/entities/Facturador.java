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
@Table(name = "FACTURADOR")
@NamedQueries({@NamedQuery(name = "Facturador.findAll", query = "SELECT f FROM Facturador f")})
public class Facturador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FACTURADOR")
    @GeneratedValue(generator = "facturadorSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "facturadorSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="facturadorSeq",initialValue=1)
    private Long idFacturador;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "facturador")
    private Collection<HabilitacionFactRed> habilitacionFactRedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturador")
    private Collection<Servicio> servicioCollection;
    @JoinColumn(name = "ENTIDAD", referencedColumnName = "ID_ENTIDAD")
    @ManyToOne(optional = false)
    private Entidad entidad;
    @JoinColumn(name = "CONTACTO", referencedColumnName = "ID_PERSONA")
    @ManyToOne
    private Persona contacto;
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    public Facturador() {
    }

    public Facturador(Long idFacturador) {
        this.idFacturador = idFacturador;
    }

    public Facturador(Long idFacturador, String descripcion) {
        this.idFacturador = idFacturador;
        this.descripcion = descripcion;
    }

    public Long getIdFacturador() {
        return idFacturador;
    }

    public void setIdFacturador(Long idFacturador) {
        this.idFacturador = idFacturador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<HabilitacionFactRed> getHabilitacionFactRedCollection() {
        return habilitacionFactRedCollection;
    }

    public void setHabilitacionFactRedCollection(Collection<HabilitacionFactRed> habilitacionFactRedCollection) {
        this.habilitacionFactRedCollection = habilitacionFactRedCollection;
    }

    public Collection<Servicio> getServicioCollection() {
        return servicioCollection;
    }

    public void setServicioCollection(Collection<Servicio> servicioCollection) {
        this.servicioCollection = servicioCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacturador != null ? idFacturador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facturador)) {
            return false;
        }
        Facturador other = (Facturador) object;
        if ((this.idFacturador == null && other.idFacturador != null) || (this.idFacturador != null && !this.idFacturador.equals(other.idFacturador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Facturador[idFacturador=" + idFacturador + "]";
    }

}
