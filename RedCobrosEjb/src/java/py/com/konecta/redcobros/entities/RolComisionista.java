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
@Table(name = "ROL_COMISIONISTA")
@NamedQueries({@NamedQuery(name = "RolComisionista.findAll", query = "SELECT r FROM RolComisionista r")})
public class RolComisionista implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ROL_COMISIONISTA")
    private Long idRolComisionista;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    public RolComisionista() {
    }

    public RolComisionista(Long idRolComisionista) {
        this.idRolComisionista = idRolComisionista;
    }

    public RolComisionista(Long idRolComisionista, String descripcion) {
        this.idRolComisionista = idRolComisionista;
        this.descripcion = descripcion;
    }

    public Long getIdRolComisionista() {
        return idRolComisionista;
    }

    public void setIdRolComisionista(Long idRolComisionista) {
        this.idRolComisionista = idRolComisionista;
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
        hash += (idRolComisionista != null ? idRolComisionista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolComisionista)) {
            return false;
        }
        RolComisionista other = (RolComisionista) object;
        if ((this.idRolComisionista == null && other.idRolComisionista != null) || (this.idRolComisionista != null && !this.idRolComisionista.equals(other.idRolComisionista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RolComisionista[idRolComisionista=" + idRolComisionista + "]";
    }

    public final static long RECAUDADOR=1;
    public final static long SUCURSAL=2;
    public final static long BANCO_CLEARING=3;
    public final static long ENTIDAD_CABECERA_RED=4;
    public final static long PROCESADOR_DOCUMENTA=5;
    public final static long ENTIDAD_POLITICA=6;
}
