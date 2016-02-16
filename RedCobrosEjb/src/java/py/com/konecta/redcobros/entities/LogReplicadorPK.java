/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author konecta
 */
@Embeddable
public class LogReplicadorPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "NOMBRE_PERSISTENCE_UNIT")
    private String nombrePersistenceUnit;
    @Basic(optional = false)
    @Column(name = "MOMENTO_REPLICACION")
    private String momentoReplicacion;
    @Basic(optional = false)
    @Column(name = "OPERACION")
    private String operacion;

    public LogReplicadorPK() {
    }

    public String getMomentoReplicacion() {
        return momentoReplicacion;
    }

    public void setMomentoReplicacion(String momentoReplicacion) {
        this.momentoReplicacion = momentoReplicacion;
    }

    public String getNombrePersistenceUnit() {
        return nombrePersistenceUnit;
    }

    public void setNombrePersistenceUnit(String nombrePersistenceUnit) {
        this.nombrePersistenceUnit = nombrePersistenceUnit;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }



    @Override
    public int hashCode() {
        if (this.nombrePersistenceUnit == null ||
                this.momentoReplicacion == null ||
                this.momentoReplicacion == null)  {
            return 0;
        }

        return this.nombrePersistenceUnit.hashCode() +
                this.momentoReplicacion.hashCode() +
                this.operacion.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogReplicadorPK)) {
            return false;
        }
        LogReplicadorPK other = (LogReplicadorPK) object;
        if ((this.nombrePersistenceUnit == null && other.nombrePersistenceUnit != null) ||
                (this.nombrePersistenceUnit != null && !this.nombrePersistenceUnit.equals(other.nombrePersistenceUnit))) {
            return false;
        }
        if ((this.momentoReplicacion == null && other.momentoReplicacion != null) ||
                (this.momentoReplicacion != null && !this.momentoReplicacion.equals(other.momentoReplicacion))) {
            return false;
        }
        if ((this.operacion == null && other.operacion != null) ||
                (this.operacion != null && !this.operacion.equals(other.operacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+nombrePersistenceUnit+";;"+
                momentoReplicacion+";;"+
                operacion;
    }

}
