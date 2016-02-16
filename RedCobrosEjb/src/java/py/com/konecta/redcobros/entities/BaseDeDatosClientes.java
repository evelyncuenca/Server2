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
import javax.persistence.Table;

/**
 *
 * @author gustavo
 */
@Entity
@Table(name = "BASE_DE_DATOS_CLIENTES")
public class BaseDeDatosClientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_BD_CLIENTE")
    private Long idBDCliente;
    @Basic(optional = false)
    @Column(name = "URL_CLIENTE")
    private String urlCliente;
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(name = "PASSWORD_USUARIO")
    private String passwordUsuario;
    @Basic(optional = false)
    @Column(name = "DRIVER_CLASS")
    private String diverClass;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ID_RED")
    private Long idRed;
    @Column(name = "ID_RECAUDADOR")
    private Long idRecaudador;
    @Column(name = "ID_SUCURSAL")
    private Long idSucursal;
    @Basic(optional = false)
    @Column(name = "HABILITADO")
    private String habilitado;
    @Basic(optional = false)
    @Column(name = "PREFIJO")
    private Long prefijo;

    public Long getIdBDCliente() {
        return idBDCliente;
    }

    public void setIdBDCliente(Long idBDCliente) {
        this.idBDCliente = idBDCliente;
    }

    

    public String getDiverClass() {
        return diverClass;
    }

    public void setDiverClass(String diverClass) {
        this.diverClass = diverClass;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public String getUrlCliente() {
        return urlCliente;
    }

    public void setUrlCliente(String urlCliente) {
        this.urlCliente = urlCliente;
    }



    public Long getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(Long prefijo) {
        this.prefijo = prefijo;
    }



    public BaseDeDatosClientes() {
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public Long getIdRecaudador() {
        return idRecaudador;
    }

    public void setIdRecaudador(Long idRecaudador) {
        this.idRecaudador = idRecaudador;
    }

    public Long getIdRed() {
        return idRed;
    }

    public void setIdRed(Long idRed) {
        this.idRed = idRed;
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }



    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BaseDeDatosClientes)) {
            return false;
        }
        BaseDeDatosClientes other = (BaseDeDatosClientes) object;
        if ((this.idBDCliente == null && other.idBDCliente != null) || (this.idBDCliente != null && !this.idBDCliente.equals(other.idBDCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.BaseDeDatosClientes[idBDCliente=" + idBDCliente + "]";
    }
}