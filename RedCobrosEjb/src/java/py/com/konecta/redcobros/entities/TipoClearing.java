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
@Table(name = "TIPO_CLEARING")
@NamedQueries({@NamedQuery(name = "TipoClearing.findAll", query = "SELECT c FROM TipoClearing c")})
public class TipoClearing implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_CLEARING")
    private Long idTipoClearing;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    public TipoClearing() {
    }

    public TipoClearing(Long idTipoClearing) {
        this.idTipoClearing=idTipoClearing;
    }

    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdTipoClearing() {
        return idTipoClearing;
    }

    public void setIdTipoClearing(Long idTipoClearing) {
        this.idTipoClearing = idTipoClearing;
    }



    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoClearing != null ? idTipoClearing.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoClearing)) {
            return false;
        }
        TipoClearing other = (TipoClearing) object;
        if ((this.idTipoClearing == null && other.idTipoClearing != null) || (this.idTipoClearing != null && !this.idTipoClearing.equals(other.idTipoClearing))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TipoClearing[idTipoClearing=" + idTipoClearing + "]";
    }
    public final static long PORCENTAJE_VOLUMEN=1L;
    public final static long FIJO_CANTIDAD=2L;
}
