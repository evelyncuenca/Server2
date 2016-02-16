/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "RED_RECAUDADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RedRecaudador.findAll", query = "SELECT r FROM RedRecaudador r"),
    @NamedQuery(name = "RedRecaudador.findByIdRed", query = "SELECT r FROM RedRecaudador r WHERE r.redRecaudadorPK.idRed = :idRed"),
    @NamedQuery(name = "RedRecaudador.findByIdRecaudador", query = "SELECT r FROM RedRecaudador r WHERE r.redRecaudadorPK.idRecaudador = :idRecaudador"),
    @NamedQuery(name = "RedRecaudador.findByDescripcion", query = "SELECT r FROM RedRecaudador r WHERE r.descripcion = :descripcion")})
public class RedRecaudador implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RedRecaudadorPK redRecaudadorPK;
 //   @Size(max = 20)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_RED", referencedColumnName = "ID_RED", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Red red;
    @JoinColumn(name = "ID_RECAUDADOR", referencedColumnName = "ID_RECAUDADOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Recaudador recaudador;
    @Column(name = "RED_TICKET")
    private String redTicket;

    public String getRedTicket() {
        return redTicket;
    }

    public void setRedTicket(String redTicket) {
        this.redTicket = redTicket;
    }

    public RedRecaudador() {
    }

    public RedRecaudador(RedRecaudadorPK redRecaudadorPK) {
        this.redRecaudadorPK = redRecaudadorPK;
    }

    public RedRecaudador(Long idRed, Long idRecaudador) {
        this.redRecaudadorPK = new RedRecaudadorPK(idRed, idRecaudador);
    }

    public RedRecaudadorPK getRedRecaudadorPK() {
        return redRecaudadorPK;
    }

    public void setRedRecaudadorPK(RedRecaudadorPK redRecaudadorPK) {
        this.redRecaudadorPK = redRecaudadorPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (redRecaudadorPK != null ? redRecaudadorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RedRecaudador)) {
            return false;
        }
        RedRecaudador other = (RedRecaudador) object;
        if ((this.redRecaudadorPK == null && other.redRecaudadorPK != null) || (this.redRecaudadorPK != null && !this.redRecaudadorPK.equals(other.redRecaudadorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RedRecaudador[ redRecaudadorPK=" + redRecaudadorPK + " ]";
    }
    
}
