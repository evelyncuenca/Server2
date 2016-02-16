/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "ENTIDAD_POLITICA")
public class EntidadPolitica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "entidadPoliticaSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "entidadPoliticaSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="entidadPoliticaSeq",initialValue=1)
    @Column(name = "ID_ENTIDAD_POLITICA")
    private Long idEntidadPolitica;
    @JoinColumn(name = "ENTIDAD", referencedColumnName = "ID_ENTIDAD")
    @ManyToOne(optional = false)
    private Entidad entidad;

    public Long getIdEntidadPolitica() {
        return idEntidadPolitica;
    }

    public void setIdEntidadPolitica(Long idEntidadPolitica) {
        this.idEntidadPolitica = idEntidadPolitica;
    }
    @JoinColumn(name = "RED", referencedColumnName = "ID_RED")
    @ManyToOne(optional = false)
    private Red red;
    @Basic(optional = false)
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public EntidadPolitica() {
    }

    public EntidadPolitica(Long idEntidadPolitica) {
        this.idEntidadPolitica = idEntidadPolitica;
    }

    

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }



    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntidadPolitica != null ? idEntidadPolitica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadPolitica)) {
            return false;
        }
        EntidadPolitica other = (EntidadPolitica) object;
        if ((this.idEntidadPolitica == null && other.idEntidadPolitica != null) || (this.idEntidadPolitica != null && !this.idEntidadPolitica.equals(other.idEntidadPolitica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.EntidadPolitica[idEntidadPolitica=" + idEntidadPolitica + "]";
    }

}
