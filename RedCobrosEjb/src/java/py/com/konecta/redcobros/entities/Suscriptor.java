/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author documenta
 */
@Entity
@Table(name = "SUSCRIPTOR")
@NamedQueries({
    @NamedQuery(name = "Suscriptor.findAll", query = "SELECT s FROM Suscriptor s")})
public class Suscriptor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SUSCRIPTOR")
    private Long idSuscriptor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "APELLIDO")
    private String apellido;
    @Size(min = 1, max = 30)
    @Column(name = "EMAIL")
    private String email;
    @Size(min = 1, max = 20)
    @Column(name = "TELEFONO")
    private String telefono;
    @JoinTable(name = "SUSCRIPTOR_EVENTO", joinColumns = {
        @JoinColumn(name = "SUSCRIPTOR", referencedColumnName = "ID_SUSCRIPTOR")},inverseJoinColumns = {
        //@JoinColumn(name = "CI", referencedColumnName = "CI"),
        @JoinColumn(name = "SECTOR", referencedColumnName = "SECTOR")})
    @OneToMany//(mappedBy = "suscriptor")
    private List<SuscriptorEvento> suscriptorEventoList;

    public Suscriptor() {
    }

    public Suscriptor(Long ci) {
        this.idSuscriptor = ci;
    }

    public Suscriptor(Long ci, String nombre, String apellido) {
        this.idSuscriptor = ci;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getCi() {
        return idSuscriptor;
    }

    public void setCi(Long ci) {
        this.idSuscriptor = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<SuscriptorEvento> getSuscriptorEventoList() {
        return suscriptorEventoList;
    }

    public void setSuscriptorEventoList(List<SuscriptorEvento> suscriptorEventoList) {
        this.suscriptorEventoList = suscriptorEventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSuscriptor != null ? idSuscriptor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suscriptor)) {
            return false;
        }
        Suscriptor other = (Suscriptor) object;
        if ((this.idSuscriptor == null && other.idSuscriptor != null) || (this.idSuscriptor != null && !this.idSuscriptor.equals(other.idSuscriptor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Suscriptor[ ci=" + idSuscriptor + " ]";
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
