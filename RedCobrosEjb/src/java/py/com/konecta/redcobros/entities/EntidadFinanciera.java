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
@Table(name = "ENTIDAD_FINANCIERA")
@NamedQueries({
    @NamedQuery(name = "EntidadFinanciera.findAll", query = "SELECT e FROM EntidadFinanciera e"),
    @NamedQuery(name = "EntidadFinanciera.findByIdEntidadFinanciera", query = "SELECT e FROM EntidadFinanciera e WHERE e.idEntidadFinanciera = :idEntidadFinanciera"),
    @NamedQuery(name = "EntidadFinanciera.findByDescripcion", query = "SELECT e FROM EntidadFinanciera e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "EntidadFinanciera.findByNumeroCuenta", query = "SELECT e FROM EntidadFinanciera e WHERE e.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "EntidadFinanciera.findByCodRef", query = "SELECT e FROM EntidadFinanciera e WHERE e.codRef = :codRef")})
public class EntidadFinanciera implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ENTIDAD_FINANCIERA")
    @GeneratedValue(generator = "entidadFinancieraSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "entidadFinancieraSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="entidadFinancieraSeq",initialValue=1)
    private Long idEntidadFinanciera;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadFinanciera")
    private Collection<Cuenta> cuentaCollection;
    @JoinColumn(name = "ENTIDAD", referencedColumnName = "ID_ENTIDAD")
    @ManyToOne(optional = false)
    private Entidad entidad;
    @JoinColumn(name = "CONTACTO", referencedColumnName = "ID_PERSONA")
    @ManyToOne(optional = false)
    private Persona contacto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadFinanciera")
    private Collection<Transaccion> transaccionCollection;
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    @Column(name = "COD_REF")
    private Short codRef;

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }


    public EntidadFinanciera() {
    }

    public EntidadFinanciera(Long idEntidadFinanciera) {
        this.idEntidadFinanciera = idEntidadFinanciera;
    }

    public EntidadFinanciera(Long idEntidadFinanciera, String descripcion) {
        this.idEntidadFinanciera = idEntidadFinanciera;
        this.descripcion = descripcion;
    }

    public Long getIdEntidadFinanciera() {
        return idEntidadFinanciera;
    }

    public void setIdEntidadFinanciera(Long idEntidadFinanciera) {
        this.idEntidadFinanciera = idEntidadFinanciera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   public Collection<Cuenta> getCuentaCollection() {
        return cuentaCollection;
    }

    public void setCuentaCollection(Collection<Cuenta> cuentaCollection) {
        this.cuentaCollection = cuentaCollection;
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

    public Short getCodRef() {
            return codRef;
        }

    public void setCodRef(Short codRef) {
        this.codRef = codRef;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntidadFinanciera != null ? idEntidadFinanciera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadFinanciera)) {
            return false;
        }
        EntidadFinanciera other = (EntidadFinanciera) object;
        if ((this.idEntidadFinanciera == null && other.idEntidadFinanciera != null) || (this.idEntidadFinanciera != null && !this.idEntidadFinanciera.equals(other.idEntidadFinanciera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.EntidadFinanciera[idEntidadFinanciera=" + idEntidadFinanciera + "]";
    }

}
