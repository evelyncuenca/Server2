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
@Table(name = "TIPO_CAPTURA")
@NamedQueries({@NamedQuery(name = "TipoCaptura.findAll", query = "SELECT t FROM TipoCaptura t"), @NamedQuery(name = "TipoCaptura.findByIdTipoCaptura", query = "SELECT t FROM TipoCaptura t WHERE t.idTipoCaptura = :idTipoCaptura"), @NamedQuery(name = "TipoCaptura.findByAlias", query = "SELECT t FROM TipoCaptura t WHERE t.alias = :alias"), @NamedQuery(name = "TipoCaptura.findByDescripcion", query = "SELECT t FROM TipoCaptura t WHERE t.descripcion = :descripcion")})
public class TipoCaptura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_CAPTURA")
    private Short idTipoCaptura;
    @Basic(optional = false)
    @Column(name = "ALIAS")
    private String alias;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idTipoCaptura")
    private Collection<LogTransaccionRc> logTransaccionRcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoCaptura")
    private Collection<TransaccionRc> transaccionRcCollection;

    public TipoCaptura() {
    }

    public TipoCaptura(Short idTipoCaptura) {
        this.idTipoCaptura = idTipoCaptura;
    }

    public TipoCaptura(Short idTipoCaptura, String alias, String descripcion) {
        this.idTipoCaptura = idTipoCaptura;
        this.alias = alias;
        this.descripcion = descripcion;
    }

    public Short getIdTipoCaptura() {
        return idTipoCaptura;
    }

    public void setIdTipoCaptura(Short idTipoCaptura) {
        this.idTipoCaptura = idTipoCaptura;
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

    public Collection<LogTransaccionRc> getLogTransaccionRcCollection() {
        return logTransaccionRcCollection;
    }

    public void setLogTransaccionRcCollection(Collection<LogTransaccionRc> logTransaccionRcCollection) {
        this.logTransaccionRcCollection = logTransaccionRcCollection;
    }

    public Collection<TransaccionRc> getTransaccionRcCollection() {
        return transaccionRcCollection;
    }

    public void setTransaccionRcCollection(Collection<TransaccionRc> transaccionRcCollection) {
        this.transaccionRcCollection = transaccionRcCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCaptura != null ? idTipoCaptura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCaptura)) {
            return false;
        }
        TipoCaptura other = (TipoCaptura) object;
        if ((this.idTipoCaptura == null && other.idTipoCaptura != null) || (this.idTipoCaptura != null && !this.idTipoCaptura.equals(other.idTipoCaptura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TipoCaptura[idTipoCaptura=" + idTipoCaptura + "]";
    }

}
