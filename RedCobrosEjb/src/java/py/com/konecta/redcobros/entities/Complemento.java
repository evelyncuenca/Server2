/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "COMPLEMENTO")
@NamedQueries({@NamedQuery(name = "Complemento.findAll", query = "SELECT c FROM Complemento c"), @NamedQuery(name = "Complemento.findByIdComplemento", query = "SELECT c FROM Complemento c WHERE c.idComplemento = :idComplemento"), @NamedQuery(name = "Complemento.findByComplemento", query = "SELECT c FROM Complemento c WHERE c.complemento = :complemento")})
public class Complemento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_COMPLEMENTO")
    private Short idComplemento;
    @Basic(optional = false)
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @OneToMany(mappedBy = "idComplemento")
    private Collection<ServicioRc> servicioRcCollection;

    public Complemento() {
    }

    public Complemento(Short idComplemento) {
        this.idComplemento = idComplemento;
    }

    public Complemento(Short idComplemento, String complemento) {
        this.idComplemento = idComplemento;
        this.complemento = complemento;
    }

    public Short getIdComplemento() {
        return idComplemento;
    }

    public void setIdComplemento(Short idComplemento) {
        this.idComplemento = idComplemento;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Collection<ServicioRc> getServicioRcCollection() {
        return servicioRcCollection;
    }

    public void setServicioRcCollection(Collection<ServicioRc> servicioRcCollection) {
        this.servicioRcCollection = servicioRcCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComplemento != null ? idComplemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complemento)) {
            return false;
        }
        Complemento other = (Complemento) object;
        if ((this.idComplemento == null && other.idComplemento != null) || (this.idComplemento != null && !this.idComplemento.equals(other.idComplemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Complemento[idComplemento=" + idComplemento + "]";
    }

}
