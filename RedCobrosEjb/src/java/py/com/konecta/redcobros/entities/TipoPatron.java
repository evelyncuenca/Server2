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
@Table(name = "TIPO_PATRON")
@NamedQueries({@NamedQuery(name = "TipoPatron.findAll", query = "SELECT t FROM TipoPatron t"), @NamedQuery(name = "TipoPatron.findByIdTipoPatron", query = "SELECT t FROM TipoPatron t WHERE t.idTipoPatron = :idTipoPatron"), @NamedQuery(name = "TipoPatron.findByAlias", query = "SELECT t FROM TipoPatron t WHERE t.alias = :alias"), @NamedQuery(name = "TipoPatron.findByDescripcion", query = "SELECT t FROM TipoPatron t WHERE t.descripcion = :descripcion")})
public class TipoPatron implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_PATRON")
    private Short idTipoPatron;
    @Basic(optional = false)
    @Column(name = "ALIAS")
    private String alias;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public TipoPatron() {
    }

    public TipoPatron(Short idTipoPatron) {
        this.idTipoPatron = idTipoPatron;
    }

    public TipoPatron(Short idTipoPatron, String alias, String descripcion) {
        this.idTipoPatron = idTipoPatron;
        this.alias = alias;
        this.descripcion = descripcion;
    }

    public Short getIdTipoPatron() {
        return idTipoPatron;
    }

    public void setIdTipoPatron(Short idTipoPatron) {
        this.idTipoPatron = idTipoPatron;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPatron != null ? idTipoPatron.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPatron)) {
            return false;
        }
        TipoPatron other = (TipoPatron) object;
        if ((this.idTipoPatron == null && other.idTipoPatron != null) || (this.idTipoPatron != null && !this.idTipoPatron.equals(other.idTipoPatron))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TipoPatron[idTipoPatron=" + idTipoPatron + "]";
    }

}
