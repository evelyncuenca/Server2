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

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "FERIADOS")
@NamedQueries({@NamedQuery(name = "Feriados.findAll", query = "SELECT f FROM Feriados f")})
public class Feriados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FERIADO")
    @GeneratedValue(generator = "feriadoSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "feriadoSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="feriadoSeq",initialValue=1)
    private Long idFeriado;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "FLAG_OBVIAR_ANHO")
    private Integer flagObviarAnho;
    @Basic(optional = false)
    @Column(name = "CADENA_FECHA")
    private String cadenaFecha;

    public String getCadenaFecha() {
        return cadenaFecha;
    }

    public void setCadenaFecha(String cadenaFecha) {
        this.cadenaFecha = cadenaFecha;
    }



    public Feriados() {
    }

    public Feriados(Long idFeriado) {
        this.idFeriado = idFeriado;
    }

    public Long getIdFeriado() {
        return idFeriado;
    }

    public void setIdFeriado(Long idFeriado) {
        this.idFeriado = idFeriado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getFlagObviarAnho() {
        return flagObviarAnho;
    }

    public void setFlagObviarAnho(Integer flagObviarAnho) {
        this.flagObviarAnho = flagObviarAnho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFeriado != null ? idFeriado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feriados)) {
            return false;
        }
        Feriados other = (Feriados) object;
        if ((this.idFeriado == null && other.idFeriado != null) || (this.idFeriado != null && !this.idFeriado.equals(other.idFeriado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Feriados[idFeriado=" + idFeriado + "]";
    }

}
