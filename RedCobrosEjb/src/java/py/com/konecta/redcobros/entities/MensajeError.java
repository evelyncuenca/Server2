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
@Table(name = "MENSAJE_ERROR")
@NamedQueries({@NamedQuery(name = "MensajeError.findAll", query = "SELECT m FROM MensajeError m")})
public class MensajeError implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MENSAJE_ERROR")
    private Long idMensajeError;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public MensajeError() {
    }

    public MensajeError(Long idMensajeError) {
        this.idMensajeError = idMensajeError;
    }

    public Long getIdMensajeError() {
        return idMensajeError;
    }

    public void setIdMensajeError(Long idMensajeError) {
        this.idMensajeError = idMensajeError;
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
        hash += (idMensajeError != null ? idMensajeError.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MensajeError)) {
            return false;
        }
        MensajeError other = (MensajeError) object;
        if ((this.idMensajeError == null && other.idMensajeError != null) || (this.idMensajeError != null && !this.idMensajeError.equals(other.idMensajeError))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.MensajeError[idMensajeError=" + idMensajeError + "]";
    }

}
