/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "FRANJA_HORARIA_DET")
@NamedQueries({@NamedQuery(name = "FranjaHorariaDet.findAll", query = "SELECT f FROM FranjaHorariaDet f")})
public class FranjaHorariaDet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FRANJA_HORARIA_DET")
    @GeneratedValue(generator = "franjaHorariaDetSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "franjaHorariaDetSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="franjaHorariaDetSeq",initialValue=1)
    private Long idFranjaHorariaDet;
    @Column(name = "DIA")
    private BigInteger dia;
    @Basic(optional = false)
    @Column(name = "HORA_INI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaIni;
    @Basic(optional = false)
    @Column(name = "HORA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaFin;
    @JoinColumn(name = "FRANJA_HORARIA_CAB", referencedColumnName = "ID_FRANJA_HORARIA_CAB")
    @ManyToOne(optional = false)
    private FranjaHorariaCab franjaHorariaCab;

    public FranjaHorariaDet() {
    }

    public FranjaHorariaDet(Long idFranjaHorariaDet) {
        this.idFranjaHorariaDet = idFranjaHorariaDet;
    }

    public FranjaHorariaDet(Long idFranjaHorariaDet, BigInteger dia, Date horaIni, Date horaFin) {
        this.idFranjaHorariaDet = idFranjaHorariaDet;
        this.dia = dia;
        this.horaIni = horaIni;
        this.horaFin = horaFin;
    }

    public Long getIdFranjaHorariaDet() {
        return idFranjaHorariaDet;
    }

    public void setIdFranjaHorariaDet(Long idFranjaHorariaDet) {
        this.idFranjaHorariaDet = idFranjaHorariaDet;
    }

    public BigInteger getDia() {
        return dia;
    }

    public void setDia(BigInteger dia) {
        this.dia = dia;
    }

    public Date getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(Date horaIni) {
        this.horaIni = horaIni;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public FranjaHorariaCab getFranjaHorariaCab() {
        return franjaHorariaCab;
    }

    public void setFranjaHorariaCab(FranjaHorariaCab franjaHorariaCab) {
        this.franjaHorariaCab = franjaHorariaCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFranjaHorariaDet != null ? idFranjaHorariaDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FranjaHorariaDet)) {
            return false;
        }
        FranjaHorariaDet other = (FranjaHorariaDet) object;
        if ((this.idFranjaHorariaDet == null && other.idFranjaHorariaDet != null) || (this.idFranjaHorariaDet != null && !this.idFranjaHorariaDet.equals(other.idFranjaHorariaDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.FranjaHorariaDet[idFranjaHorariaDet=" + idFranjaHorariaDet + "]";
    }

}
