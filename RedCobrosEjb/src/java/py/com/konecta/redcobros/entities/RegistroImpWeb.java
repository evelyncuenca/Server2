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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author documenta
 */
@Entity
@Table(name = "REGISTRO_IMP_WEB")
@NamedQueries({
    @NamedQuery(name = "RegistroImpWeb.findAll", query = "SELECT r FROM RegistroImpWeb r"),
    @NamedQuery(name = "RegistroImpWeb.findByIdRegistroImpWeb", query = "SELECT r FROM RegistroImpWeb r WHERE r.idRegistroImpWeb = :idRegistroImpWeb"),
    @NamedQuery(name = "RegistroImpWeb.findByUsuario", query = "SELECT r FROM RegistroImpWeb r WHERE r.usuario = :usuario"),
    @NamedQuery(name = "RegistroImpWeb.findByEvento", query = "SELECT r FROM RegistroImpWeb r WHERE r.evento = :evento"),
    @NamedQuery(name = "RegistroImpWeb.findByDescripcion", query = "SELECT r FROM RegistroImpWeb r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "RegistroImpWeb.findByFecha", query = "SELECT r FROM RegistroImpWeb r WHERE r.fecha = :fecha")})
public class RegistroImpWeb implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_REGISTRO_IMP_WEB")
    @GeneratedValue(generator = "registroImpWeb", strategy = GenerationType.TABLE)
    @TableGenerator(name = "registroImpWeb", table = "SECUENCIA",
    allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
    pkColumnValue = "registroImpWeb", initialValue = 1)
    private Long idRegistroImpWeb;
    @Size(max = 255)
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "TERMINAL")
    private Long terminal;
    @Size(max = 20)
    @Column(name = "EVENTO")
    private String evento;
    @Size(max = 2000)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 2000)
    @Column(name = "MOTIVO")
    private String motivo;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public RegistroImpWeb() {
    }

    public RegistroImpWeb(Long idRegistroImpWeb) {
        this.idRegistroImpWeb = idRegistroImpWeb;
    }

    public Long getIdRegistroImpWeb() {
        return idRegistroImpWeb;
    }

    public void setIdRegistroImpWeb(Long idRegistroImpWeb) {
        this.idRegistroImpWeb = idRegistroImpWeb;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroImpWeb != null ? idRegistroImpWeb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroImpWeb)) {
            return false;
        }
        RegistroImpWeb other = (RegistroImpWeb) object;
        if ((this.idRegistroImpWeb == null && other.idRegistroImpWeb != null) || (this.idRegistroImpWeb != null && !this.idRegistroImpWeb.equals(other.idRegistroImpWeb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RegistroImpWeb[ idRegistroImpWeb=" + idRegistroImpWeb + " ]";
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the terminal
     */
    public Long getTerminal() {
        return terminal;
    }

    /**
     * @param terminal the terminal to set
     */
    public void setTerminal(Long terminal) {
        this.terminal = terminal;
    }
}
