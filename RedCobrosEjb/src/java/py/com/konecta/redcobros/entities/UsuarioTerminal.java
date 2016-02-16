/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "USUARIO_TERMINAL")
//@NamedQueries({@NamedQuery(name = "UsuarioTerminal.findAll", query = "SELECT u FROM UsuarioTerminal u")})
public class UsuarioTerminal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "usuarioTerminalSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "usuarioTerminalSeq", table = "SECUENCIA",
    allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
    pkColumnValue = "usuarioTerminalSeq", initialValue = 1)
    @Basic(optional = false)
    @Column(name = "ID_USUARIO_TERMINAL")
    private Long idUsuarioTerminal;
    @JoinColumn(name = "FRANJA_HORARIA_CAB", referencedColumnName = "ID_FRANJA_HORARIA_CAB")
    @ManyToOne(optional = false)
    private FranjaHorariaCab franjaHorariaCab;
    @JoinColumn(name = "TERMINAL", referencedColumnName = "ID_TERMINAL")
    @ManyToOne(optional = false)
    private Terminal terminal;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @Column(name = "LOGUEADO")
    private String logueado;
    @Column(name = "PASE")
    private Integer pase;
    @Column(name = "FECHA_PASE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPase;

    public Date getFechaPase() {
        return fechaPase;
    }

    public void setFechaPase(Date fechaPase) {
        this.fechaPase = fechaPase;
    }    
    
    public Integer getPase() {
        return pase;
    }

    public void setPase(Integer pase) {
        this.pase = pase;
    }

    public String getLogueado() {
        return logueado;
    }

    public void setLogueado(String logueado) {
        this.logueado = logueado;
    }

    public UsuarioTerminal() {
    }

    public UsuarioTerminal(Long idUsuarioTerminal) {
        this.idUsuarioTerminal = idUsuarioTerminal;
    }

    public Long getIdUsuarioTerminal() {
        return idUsuarioTerminal;
    }

    public void setIdUsuarioTerminal(Long idUsuarioTerminal) {
        this.idUsuarioTerminal = idUsuarioTerminal;
    }

    public FranjaHorariaCab getFranjaHorariaCab() {
        return franjaHorariaCab;
    }

    public void setFranjaHorariaCab(FranjaHorariaCab franjaHorariaCab) {
        this.franjaHorariaCab = franjaHorariaCab;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioTerminal != null ? idUsuarioTerminal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioTerminal)) {
            return false;
        }
        UsuarioTerminal other = (UsuarioTerminal) object;
        if ((this.idUsuarioTerminal == null && other.idUsuarioTerminal != null) || (this.idUsuarioTerminal != null && !this.idUsuarioTerminal.equals(other.idUsuarioTerminal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UsuarioTerminal{" + "idUsuarioTerminal=" + idUsuarioTerminal + ", franjaHorariaCab=" + franjaHorariaCab + ", terminal=" + terminal + ", usuario=" + usuario + ", logueado=" + logueado + ", pase=" + pase + ", fechaPase=" + fechaPase + '}';
    }

}
