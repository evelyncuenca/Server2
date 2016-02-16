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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "GRUPO_PAGINA")
@NamedQueries({@NamedQuery(name = "GrupoPagina.findAll", query = "SELECT g FROM GrupoPagina g"), @NamedQuery(name = "GrupoPagina.findByIdGrupoPagina", query = "SELECT g FROM GrupoPagina g WHERE g.idGrupoPagina = :idGrupoPagina"), @NamedQuery(name = "GrupoPagina.findByDescripcion", query = "SELECT g FROM GrupoPagina g WHERE g.descripcion = :descripcion")})
public class GrupoPagina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_GRUPO_PAGINA")
    private Integer idGrupoPagina;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrupoPagina")
    private Collection<PatronPagina> patronPaginaCollection;

    public GrupoPagina() {
    }

    public GrupoPagina(Integer idGrupoPagina) {
        this.idGrupoPagina = idGrupoPagina;
    }

    public GrupoPagina(Integer idGrupoPagina, String descripcion) {
        this.idGrupoPagina = idGrupoPagina;
        this.descripcion = descripcion;
    }

    public Integer getIdGrupoPagina() {
        return idGrupoPagina;
    }

    public void setIdGrupoPagina(Integer idGrupoPagina) {
        this.idGrupoPagina = idGrupoPagina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<PatronPagina> getPatronPaginaCollection() {
        return patronPaginaCollection;
    }

    public void setPatronPaginaCollection(Collection<PatronPagina> patronPaginaCollection) {
        this.patronPaginaCollection = patronPaginaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupoPagina != null ? idGrupoPagina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoPagina)) {
            return false;
        }
        GrupoPagina other = (GrupoPagina) object;
        if ((this.idGrupoPagina == null && other.idGrupoPagina != null) || (this.idGrupoPagina != null && !this.idGrupoPagina.equals(other.idGrupoPagina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.GrupoPagina[idGrupoPagina=" + idGrupoPagina + "]";
    }

}
