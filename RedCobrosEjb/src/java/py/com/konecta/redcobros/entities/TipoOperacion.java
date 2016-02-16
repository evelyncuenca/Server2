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
@Table(name = "TIPO_OPERACION")
@NamedQueries({
    @NamedQuery(name = "TipoOperacion.findAll", query = "SELECT t FROM TipoOperacion t"),
    @NamedQuery(name = "TipoOperacion.findByIdTipoOperacion", query = "SELECT t FROM TipoOperacion t WHERE t.idTipoOperacion = :idTipoOperacion"),
    @NamedQuery(name = "TipoOperacion.findByDescripcion", query = "SELECT t FROM TipoOperacion t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoOperacion.findByClase", query = "SELECT t FROM TipoOperacion t WHERE t.clase = :clase"),
    @NamedQuery(name = "TipoOperacion.findByAlias", query = "SELECT t FROM TipoOperacion t WHERE t.alias = :alias")})
public class TipoOperacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_OPERACION")
    private Short idTipoOperacion;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "CLASE")
    private String clase;
    @Column(name = "ALIAS")
    private String alias;
    @OneToMany(mappedBy = "idTipoOperacion")
    private Collection<LogTransaccionRc> logTransaccionRcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoOperacion")
    private Collection<TransaccionRc> transaccionRcCollection;

    public TipoOperacion() {
    }

    public TipoOperacion(Short idTipoOperacion) {
        this.idTipoOperacion = idTipoOperacion;
    }

    public TipoOperacion(Short idTipoOperacion, String descripcion, String clase) {
        this.idTipoOperacion = idTipoOperacion;
        this.descripcion = descripcion;
        this.clase = clase;
    }

    public Short getIdTipoOperacion() {
        return idTipoOperacion;
    }

    public void setIdTipoOperacion(Short idTipoOperacion) {
        this.idTipoOperacion = idTipoOperacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
        hash += (idTipoOperacion != null ? idTipoOperacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoOperacion)) {
            return false;
        }
        TipoOperacion other = (TipoOperacion) object;
        if ((this.idTipoOperacion == null && other.idTipoOperacion != null) || (this.idTipoOperacion != null && !this.idTipoOperacion.equals(other.idTipoOperacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TipoOperacion[idTipoOperacion=" + idTipoOperacion + "]";
    }
    public final static Short RECHAZADO = 50;
    public final static Short COBRANZA = 1;
    public final static Short ANULACION = 2;
    public final static Short CONSULTA_CODIGO_DE_BARRAS = 3;
    public final static Short CONSULTA_MANUAL = 4;
    public final static Short REVERSION = 11;
}
