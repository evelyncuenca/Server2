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
@Table(name = "RED_RECAUDADOR_NUMERO_ORDEN")
@NamedQueries({@NamedQuery(name = "RedRecaudadorNumeroOrden.findAll", query = "SELECT r FROM RedRecaudadorNumeroOrden r")})
public class RedRecaudadorNumeroOrden implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RED_RECAUDADOR_NUMERO_ORDEN")
    @GeneratedValue(generator = "redRecNumeroOrdenSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "redRecNumeroOrdenSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="redRecNumeroOrdenSeq",initialValue=1)
    private Long idRedRecaudadorNumeroOrden;
    @Basic(optional = false)
    @Column(name = "NUMERO_INICIAL")
    private Long numeroInicial;
    @Basic(optional = false)
    @Column(name = "NUMERO_FINAL")
    private Long numeroFinal;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    @ManyToOne(optional = false)
    private Recaudador recaudador;
    @JoinColumn(name = "RED", referencedColumnName = "ID_RED")
    @ManyToOne(optional = false)
    private Red red;

    public RedRecaudadorNumeroOrden() {
    }

    public RedRecaudadorNumeroOrden(Long idRedRecaudadorNumeroOrden) {
        this.idRedRecaudadorNumeroOrden = idRedRecaudadorNumeroOrden;
    }

    public RedRecaudadorNumeroOrden(Long idRedRecaudadorNumeroOrden, Long numeroInicial, Long numeroFinal, Date fechaHora) {
        this.idRedRecaudadorNumeroOrden = idRedRecaudadorNumeroOrden;
        this.numeroInicial = numeroInicial;
        this.numeroFinal = numeroFinal;
        this.fechaHora = fechaHora;
    }

    public Long getIdRedRecaudadorNumeroOrden() {
        return idRedRecaudadorNumeroOrden;
    }

    public void setIdRedRecaudadorNumeroOrden(Long idRedRecaudadorNumeroOrden) {
        this.idRedRecaudadorNumeroOrden = idRedRecaudadorNumeroOrden;
    }

    public Long getNumeroInicial() {
        return numeroInicial;
    }

    public void setNumeroInicial(Long numeroInicial) {
        this.numeroInicial = numeroInicial;
    }

    public Long getNumeroFinal() {
        return numeroFinal;
    }

    public void setNumeroFinal(Long numeroFinal) {
        this.numeroFinal = numeroFinal;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Recaudador getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Recaudador recaudador) {
        this.recaudador = recaudador;
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
        hash += (idRedRecaudadorNumeroOrden != null ? idRedRecaudadorNumeroOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RedRecaudadorNumeroOrden)) {
            return false;
        }
        RedRecaudadorNumeroOrden other = (RedRecaudadorNumeroOrden) object;
        if ((this.idRedRecaudadorNumeroOrden == null && other.idRedRecaudadorNumeroOrden != null) || (this.idRedRecaudadorNumeroOrden != null && !this.idRedRecaudadorNumeroOrden.equals(other.idRedRecaudadorNumeroOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RedRecaudadorNumeroOrden[idRedRecaudadorNumeroOrden=" + idRedRecaudadorNumeroOrden + "]";
    }

}
