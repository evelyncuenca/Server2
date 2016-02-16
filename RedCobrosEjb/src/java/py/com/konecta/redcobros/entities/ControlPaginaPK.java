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
public class ControlPaginaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_CONTROL")
    private String idControl;
    @Basic(optional = false)
    @Column(name = "ID_PATRON")
    private int idPatron;

    public ControlPaginaPK(String idControl, int idPatron) {
        this.idControl = idControl;
        this.idPatron = idPatron;
    }

    public String getIdControl() {
        return idControl;
    }

    public void setIdControl(String idControl) {
        this.idControl = idControl;
    }

    public int getIdPatron() {
        return idPatron;
    }

    public void setIdPatron(int idPatron) {
        this.idPatron = idPatron;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idControl != null ? idControl.hashCode() : 0);
        hash += (int) idPatron;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlPaginaPK)) {
            return false;
        }
        ControlPaginaPK other = (ControlPaginaPK) object;
        if ((this.idControl == null && other.idControl != null) || (this.idControl != null && !this.idControl.equals(other.idControl))) {
            return false;
        }
        if (this.idPatron != other.idPatron) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ControlPaginaPK[idControl=" + idControl + ", idPatron=" + idPatron + "]";
    }

}
