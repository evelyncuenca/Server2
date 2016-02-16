/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 *
 * @author konecta
 */
@Entity
@Table(name = "LOG_REPLICADOR")
public class LogReplicador implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LogReplicadorPK logReplicadorPK;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_ERROR")
    private String descripcionError;

    public LogReplicador() {
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public LogReplicadorPK getLogReplicadorPK() {
        return logReplicadorPK;
    }

    public void setLogReplicadorPK(LogReplicadorPK logReplicadorPK) {
        this.logReplicadorPK = logReplicadorPK;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logReplicadorPK != null ? logReplicadorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogReplicador)) {
            return false;
        }
        LogReplicador other = (LogReplicador) object;
        if ((this.logReplicadorPK == null && other.logReplicadorPK != null) ||
                (this.logReplicadorPK != null && !this.logReplicadorPK.equals(other.logReplicadorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + logReplicadorPK;
    }
 
}
