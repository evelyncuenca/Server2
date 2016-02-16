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
@Table(name = "ESTADO_TRANSACCION")
@NamedQueries({@NamedQuery(name = "EstadoTransaccion.findAll", query = "SELECT e FROM EstadoTransaccion e")})
public class EstadoTransaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTADO_TRANSACCION")
    private Long idEstadoTransaccion;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoTransaccion")
    private Collection<Transaccion> transaccionCollection;

    public EstadoTransaccion() {
    }

    public EstadoTransaccion(Long idEstadoTransaccion) {
        this.idEstadoTransaccion = idEstadoTransaccion;
    }

    public EstadoTransaccion(Long idEstadoTransaccion, String descripcion) {
        this.idEstadoTransaccion = idEstadoTransaccion;
        this.descripcion = descripcion;
    }

    public Long getIdEstadoTransaccion() {
        return idEstadoTransaccion;
    }

    public void setIdEstadoTransaccion(Long idEstadoTransaccion) {
        this.idEstadoTransaccion = idEstadoTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoTransaccion != null ? idEstadoTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoTransaccion)) {
            return false;
        }
        EstadoTransaccion other = (EstadoTransaccion) object;
        if ((this.idEstadoTransaccion == null && other.idEstadoTransaccion != null) || (this.idEstadoTransaccion != null && !this.idEstadoTransaccion.equals(other.idEstadoTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.EstadoTransaccion[idEstadoTransaccion=" + idEstadoTransaccion + "]";
    }
    public final static long RECHAZADO = 50;
    public final static long ACEPTADO = 51;
    public final static long PENDIENTE_DE_RESPUESTA_OL = 2;
    public final static long TERMINADA_POR_TIMEOUT_EN_CARRIER = 3;
    public final static long TERMINADA_POR_TIMEOUT_CON_ONLINEMANAGER = 4;
    public final static long INICIAL = 1;
    public final static long RECHAZADA_POR_AUTORIZADOR = 21;
    public final static long FINALIZADA_Y_COBRADA = 22;
    public final static long RECHAZADA_P0R_EL_FACTURADOR = 23;
    public final static long FINALIZADA_Y_PENDIENTE_DE_COBRO = 41;
}
