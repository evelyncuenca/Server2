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
@Table(name = "TIPO_DATO_ADICIONAL")
@NamedQueries({@NamedQuery(name = "TipoDatoAdicional.findAll", query = "SELECT t FROM TipoDatoAdicional t"), @NamedQuery(name = "TipoDatoAdicional.findByIdTipoAdicional", query = "SELECT t FROM TipoDatoAdicional t WHERE t.idTipoAdicional = :idTipoAdicional"), @NamedQuery(name = "TipoDatoAdicional.findByDescripcion", query = "SELECT t FROM TipoDatoAdicional t WHERE t.descripcion = :descripcion")})
public class TipoDatoAdicional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_ADICIONAL")
    private String idTipoAdicional;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDatoAdicional")
    private Collection<DatoAdicionalTrx> datoAdicionalTrxCollection;

    public TipoDatoAdicional() {
    }

    public TipoDatoAdicional(String idTipoAdicional) {
        this.idTipoAdicional = idTipoAdicional;
    }

    public TipoDatoAdicional(String idTipoAdicional, String descripcion) {
        this.idTipoAdicional = idTipoAdicional;
        this.descripcion = descripcion;
    }

    public String getIdTipoAdicional() {
        return idTipoAdicional;
    }

    public void setIdTipoAdicional(String idTipoAdicional) {
        this.idTipoAdicional = idTipoAdicional;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<DatoAdicionalTrx> getDatoAdicionalTrxCollection() {
        return datoAdicionalTrxCollection;
    }

    public void setDatoAdicionalTrxCollection(Collection<DatoAdicionalTrx> datoAdicionalTrxCollection) {
        this.datoAdicionalTrxCollection = datoAdicionalTrxCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoAdicional != null ? idTipoAdicional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDatoAdicional)) {
            return false;
        }
        TipoDatoAdicional other = (TipoDatoAdicional) object;
        if ((this.idTipoAdicional == null && other.idTipoAdicional != null) || (this.idTipoAdicional != null && !this.idTipoAdicional.equals(other.idTipoAdicional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TipoDatoAdicional[idTipoAdicional=" + idTipoAdicional + "]";
    }

}
