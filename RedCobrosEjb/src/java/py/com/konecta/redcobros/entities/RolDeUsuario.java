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

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "ROL_DE_USUARIO")
@NamedQueries({@NamedQuery(name = "RolDeUsuario.findAll", query = "SELECT r FROM RolDeUsuario r")})
public class RolDeUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "USUARIO_ASIGNADOR")
    private Integer usuarioAsignador;
    @Basic(optional = false)
    @Column(name = "FECHA_ASIGNACION")
    @Temporal(TemporalType.DATE)
    private Date fechaAsignacion;
    @Id
    @GeneratedValue(generator = "rolDeUsuarioSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "rolDeUsuarioSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="rolDeUsuarioSeq",initialValue=1)
    @Basic(optional = false)
    @Column(name = "ID_ROL_DE_USUARIO")
    private Long idRolDeUsuario;
    @JoinColumn(name = "ROL", referencedColumnName = "ID_ROL")
    @ManyToOne(optional = false)
    private Rol rol;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public RolDeUsuario() {
    }

    public RolDeUsuario(Long idRolDeUsuario) {
        this.idRolDeUsuario = idRolDeUsuario;
    }

    public RolDeUsuario(Long idRolDeUsuario, Integer usuarioAsignador, Date fechaAsignacion) {
        this.idRolDeUsuario = idRolDeUsuario;
        this.usuarioAsignador = usuarioAsignador;
        this.fechaAsignacion = fechaAsignacion;
    }

    public Integer getUsuarioAsignador() {
        return usuarioAsignador;
    }

    public void setUsuarioAsignador(Integer usuarioAsignador) {
        this.usuarioAsignador = usuarioAsignador;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Long getIdRolDeUsuario() {
        return idRolDeUsuario;
    }

    public void setIdRolDeUsuario(Long idRolDeUsuario) {
        this.idRolDeUsuario = idRolDeUsuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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
        hash += (idRolDeUsuario != null ? idRolDeUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolDeUsuario)) {
            return false;
        }
        RolDeUsuario other = (RolDeUsuario) object;
        if ((this.idRolDeUsuario == null && other.idRolDeUsuario != null) || (this.idRolDeUsuario != null && !this.idRolDeUsuario.equals(other.idRolDeUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RolDeUsuario[idRolDeUsuario=" + idRolDeUsuario + "]";
    }
}
