/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CONTRIBUYENTES_BLOQUEADOS")
@NamedQueries({
    @NamedQuery(name = "ContribuyentesBloqueados.findAll", query = "SELECT c FROM ContribuyentesBloqueados c"),
    @NamedQuery(name = "ContribuyentesBloqueados.findByRucNuevo", query = "SELECT c FROM ContribuyentesBloqueados c WHERE c.rucNuevo = :rucNuevo")})
public class ContribuyentesBloqueados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RUC_NUEVO")
    private String rucNuevo;

    public ContribuyentesBloqueados() {
    }

    public ContribuyentesBloqueados(String rucNuevo) {
        this.rucNuevo = rucNuevo;
    }

    public String getRucNuevo() {
        return rucNuevo;
    }

    public void setRucNuevo(String rucNuevo) {
        this.rucNuevo = rucNuevo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rucNuevo != null ? rucNuevo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContribuyentesBloqueados)) {
            return false;
        }
        ContribuyentesBloqueados other = (ContribuyentesBloqueados) object;
        if ((this.rucNuevo == null && other.rucNuevo != null) || (this.rucNuevo != null && !this.rucNuevo.equals(other.rucNuevo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ContribuyentesBloqueados[rucNuevo=" + rucNuevo + "]";
    }

}
