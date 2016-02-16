/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "INFORMACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Informacion.findAll", query = "SELECT i FROM Informacion i"),
    @NamedQuery(name = "Informacion.findByMensaje", query = "SELECT i FROM Informacion i WHERE i.mensaje = :mensaje"),
    @NamedQuery(name = "Informacion.findByFechaIni", query = "SELECT i FROM Informacion i WHERE i.fechaIni = :fechaIni"),
    @NamedQuery(name = "Informacion.findByFechaFin", query = "SELECT i FROM Informacion i WHERE i.fechaFin = :fechaFin"),
    @NamedQuery(name = "Informacion.findByActivo", query = "SELECT i FROM Informacion i WHERE i.activo = :activo"),
    @NamedQuery(name = "Informacion.findByIdInformacion", query = "SELECT i FROM Informacion i WHERE i.idInformacion = :idInformacion")})
public class Informacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_INFORMACION")
    @GeneratedValue(generator = "informacionSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "informacionSeq", table = "SECUENCIA",
    allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
    pkColumnValue = "informacionSeq", initialValue = 1)
    private Integer idInformacion;
    @Basic(optional = false)
    @Column(name = "MENSAJE")
    private String mensaje;
    @Basic(optional = false)
    @Column(name = "FECHA_INI")
    @Temporal(TemporalType.DATE)
    private Date fechaIni;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private String activo;
    @JoinColumn(name = "TERMINAL", referencedColumnName = "ID_TERMINAL")
    @ManyToOne
    private Terminal terminal;
    @JoinColumn(name = "SUCURSAL", referencedColumnName = "ID_SUCURSAL")
    @ManyToOne
    private Sucursal sucursal;
    @JoinColumn(name = "RED", referencedColumnName = "ID_RED")
    @ManyToOne
    private Red red;
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    @ManyToOne
    private Recaudador recaudador;

    public Informacion() {
    }

    public Informacion(Integer idInformacion) {
        this.idInformacion = idInformacion;
    }

    public Informacion(Integer idInformacion, String mensaje, Date fechaIni, String activo) {
        this.idInformacion = idInformacion;
        this.mensaje = mensaje;
        this.fechaIni = fechaIni;
        this.activo = activo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Integer getIdInformacion() {
        return idInformacion;
    }

    public void setIdInformacion(Integer idInformacion) {
        this.idInformacion = idInformacion;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public Recaudador getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Recaudador recaudador) {
        this.recaudador = recaudador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInformacion != null ? idInformacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Informacion)) {
            return false;
        }
        Informacion other = (Informacion) object;
        if ((this.idInformacion == null && other.idInformacion != null) || (this.idInformacion != null && !this.idInformacion.equals(other.idInformacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Informacion[ idInformacion=" + idInformacion + " ]";
    }
}
