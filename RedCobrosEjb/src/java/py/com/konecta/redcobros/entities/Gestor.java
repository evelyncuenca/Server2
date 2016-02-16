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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "GESTOR")
@NamedQueries({
    @NamedQuery(name = "Gestor.findAll", query = "SELECT g FROM Gestor g"),
    @NamedQuery(name = "Gestor.findByNombre", query = "SELECT g FROM Gestor g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Gestor.findByApellido", query = "SELECT g FROM Gestor g WHERE g.apellido = :apellido"),
    @NamedQuery(name = "Gestor.findByTipoGestor", query = "SELECT g FROM Gestor g WHERE g.tipoGestor = :tipoGestor"),
    @NamedQuery(name = "Gestor.findByFechaNacimiento", query = "SELECT g FROM Gestor g WHERE g.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Gestor.findBySexo", query = "SELECT g FROM Gestor g WHERE g.sexo = :sexo"),
    @NamedQuery(name = "Gestor.findBySugerido", query = "SELECT g FROM Gestor g WHERE g.sugerido = :sugerido"),
    @NamedQuery(name = "Gestor.findByCedula", query = "SELECT g FROM Gestor g WHERE g.cedula = :cedula"),
    @NamedQuery(name = "Gestor.findByRemitido", query = "SELECT g FROM Gestor g WHERE g.remitido = :remitido")})
public class Gestor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "TIPO_GESTOR")
    private String tipoGestor;
    @Basic(optional = false)
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "SUGERIDO")
    private String sugerido;
    @Id
    @Basic(optional = false)
    @Column(name = "CEDULA")
    private String cedula;
    @Column(name = "REMITIDO")
    private Character remitido;

    public Gestor() {
    }

    public Gestor(String cedula) {
        this.cedula = cedula;
    }

    public Gestor(String cedula, String nombre, String apellido, String tipoGestor, String sexo, String sugerido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoGestor = tipoGestor;
        this.sexo = sexo;
        this.sugerido = sugerido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoGestor() {
        return tipoGestor;
    }

    public void setTipoGestor(String tipoGestor) {
        this.tipoGestor = tipoGestor;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSugerido() {
        return sugerido;
    }

    public void setSugerido(String sugerido) {
        this.sugerido = sugerido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Character getRemitido() {
        return remitido;
    }

    public void setRemitido(Character remitido) {
        this.remitido = remitido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gestor)) {
            return false;
        }
        Gestor other = (Gestor) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Gestor[cedula=" + cedula + "]";
    }
}
