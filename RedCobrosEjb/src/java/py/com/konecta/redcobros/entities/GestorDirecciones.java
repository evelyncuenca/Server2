/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "GESTOR_DIRECCIONES")
@NamedQueries({
    @NamedQuery(name = "GestorDirecciones.findAll", query = "SELECT g FROM GestorDirecciones g"),
    @NamedQuery(name = "GestorDirecciones.findByIdGestor", query = "SELECT g FROM GestorDirecciones g WHERE g.idGestor = :idGestor"),
    @NamedQuery(name = "GestorDirecciones.findByIdDireccion", query = "SELECT g FROM GestorDirecciones g WHERE g.idDireccion = :idDireccion"),
    @NamedQuery(name = "GestorDirecciones.findByDireccion", query = "SELECT g FROM GestorDirecciones g WHERE g.direccion = :direccion"),
    @NamedQuery(name = "GestorDirecciones.findByIdLocalidad", query = "SELECT g FROM GestorDirecciones g WHERE g.idLocalidad = :idLocalidad"),
    @NamedQuery(name = "GestorDirecciones.findByTipoDireccion", query = "SELECT g FROM GestorDirecciones g WHERE g.tipoDireccion = :tipoDireccion"),
    @NamedQuery(name = "GestorDirecciones.findByTelefono", query = "SELECT g FROM GestorDirecciones g WHERE g.telefono = :telefono"),
    @NamedQuery(name = "GestorDirecciones.findByCelular", query = "SELECT g FROM GestorDirecciones g WHERE g.celular = :celular"),
    @NamedQuery(name = "GestorDirecciones.findByEmail", query = "SELECT g FROM GestorDirecciones g WHERE g.email = :email"),
    @NamedQuery(name = "GestorDirecciones.findByEmpresa", query = "SELECT g FROM GestorDirecciones g WHERE g.empresa = :empresa")})
public class GestorDirecciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID_GESTOR")
    private String idGestor;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DIRECCION")
    @GeneratedValue(generator = "gestorDireccionesSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "gestorDireccionesSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="gestorDireccionesSeq",initialValue=1)
    private Long idDireccion;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "LOCALIDAD")
    private Long idLocalidad;
    @Basic(optional = false)
    @Column(name = "TIPO_DIRECCION")
    private String tipoDireccion;
    @Basic(optional = true)
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "CELULAR")
    private String celular;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "EMPRESA")
    private String empresa;

    public GestorDirecciones() {
    }

    public GestorDirecciones(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public GestorDirecciones(Long idDireccion, String idGestor, String direccion, Long idLocalidad, String tipoDireccion, String telefono) {
        this.idDireccion = idDireccion;
        this.idGestor = idGestor;
        this.direccion = direccion;
        this.idLocalidad = idLocalidad;
        this.tipoDireccion = tipoDireccion;
        this.telefono = telefono;
    }

    public String getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(String idGestor) {
        this.idGestor = idGestor;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Long idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDireccion != null ? idDireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestorDirecciones)) {
            return false;
        }
        GestorDirecciones other = (GestorDirecciones) object;
        if ((this.idDireccion == null && other.idDireccion != null) || (this.idDireccion != null && !this.idDireccion.equals(other.idDireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.GestorDirecciones[idDireccion=" + idDireccion + "]";
    }

}
