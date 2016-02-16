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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "RUTEO_SERVICIO")
@NamedQueries({@NamedQuery(name = "RuteoServicio.findAll", query = "SELECT r FROM RuteoServicio r"), @NamedQuery(name = "RuteoServicio.findByIdFacturador", query = "SELECT r FROM RuteoServicio r WHERE r.idFacturador = :idFacturador"), @NamedQuery(name = "RuteoServicio.findByUrlHost", query = "SELECT r FROM RuteoServicio r WHERE r.urlHost = :urlHost"), @NamedQuery(name = "RuteoServicio.findByPuerto", query = "SELECT r FROM RuteoServicio r WHERE r.puerto = :puerto"), @NamedQuery(name = "RuteoServicio.findByConexionTo", query = "SELECT r FROM RuteoServicio r WHERE r.conexionTo = :conexionTo"), @NamedQuery(name = "RuteoServicio.findByReadTo", query = "SELECT r FROM RuteoServicio r WHERE r.readTo = :readTo"), @NamedQuery(name = "RuteoServicio.findByGuardarMensajes", query = "SELECT r FROM RuteoServicio r WHERE r.guardarMensajes = :guardarMensajes")})
public class RuteoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FACTURADOR")
    private Long idFacturador;
    @Basic(optional = false)
    @Column(name = "URL_HOST")
    private String urlHost;
    @Column(name = "PUERTO")
    private Integer puerto;
    @Basic(optional = false)
    @Column(name = "CONEXION_TO")
    private short conexionTo;
    @Basic(optional = false)
    @Column(name = "READ_TO")
    private int readTo;
    @Basic(optional = false)
    @Column(name = "GUARDAR_MENSAJES")
    private char guardarMensajes;
    @JoinColumn(name = "ID_FACTURADOR", referencedColumnName = "ID_FACTURADOR", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Facturador facturador;

    public RuteoServicio() {
    }

    public RuteoServicio(Long idFacturador) {
        this.idFacturador = idFacturador;
    }

    public RuteoServicio(Long idFacturador, String urlHost, short conexionTo, int readTo, char guardarMensajes) {
        this.idFacturador = idFacturador;
        this.urlHost = urlHost;
        this.conexionTo = conexionTo;
        this.readTo = readTo;
        this.guardarMensajes = guardarMensajes;
    }

    public Long getIdFacturador() {
        return idFacturador;
    }

    public void setIdFacturador(Long idFacturador) {
        this.idFacturador = idFacturador;
    }

    public String getUrlHost() {
        return urlHost;
    }

    public void setUrlHost(String urlHost) {
        this.urlHost = urlHost;
    }

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

    public short getConexionTo() {
        return conexionTo;
    }

    public void setConexionTo(short conexionTo) {
        this.conexionTo = conexionTo;
    }

    public int getReadTo() {
        return readTo;
    }

    public void setReadTo(int readTo) {
        this.readTo = readTo;
    }

    public char getGuardarMensajes() {
        return guardarMensajes;
    }

    public void setGuardarMensajes(char guardarMensajes) {
        this.guardarMensajes = guardarMensajes;
    }

    public Facturador getFacturador() {
        return facturador;
    }

    public void setFacturador(Facturador facturador) {
        this.facturador = facturador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacturador != null ? idFacturador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuteoServicio)) {
            return false;
        }
        RuteoServicio other = (RuteoServicio) object;
        if ((this.idFacturador == null && other.idFacturador != null) || (this.idFacturador != null && !this.idFacturador.equals(other.idFacturador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RuteoServicio[idFacturador=" + idFacturador + "]";
    }

}
