/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brojas
 */
@Entity
@Table(name = "REC_RETENIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecRetenido.findAll", query = "SELECT r FROM RecRetenido r"),
    @NamedQuery(name = "RecRetenido.findByIdRecRetenido", query = "SELECT r FROM RecRetenido r WHERE r.idRecRetenido = :idRecRetenido"),
    @NamedQuery(name = "RecRetenido.findByFechaInicio", query = "SELECT r FROM RecRetenido r WHERE r.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "RecRetenido.findByFechaFinal", query = "SELECT r FROM RecRetenido r WHERE r.fechaFinal = :fechaFinal"),
    @NamedQuery(name = "RecRetenido.findByRetenido", query = "SELECT r FROM RecRetenido r WHERE r.retenido = :retenido"),
    @NamedQuery(name = "RecRetenido.findByImporte", query = "SELECT r FROM RecRetenido r WHERE r.importe = :importe")})
public class RecRetenido implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REC_RETENIDO")
    @GeneratedValue(generator = "RecRetenidoSeq",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name="RecRetenidoSeq", sequenceName="REC_RETENIDO_SEQ")
    private Long idRecRetenido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RETENIDO")
    private Character retenido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPORTE")
    private BigInteger importe;
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    @ManyToOne(optional = false)
    private Recaudador recaudador;

    public RecRetenido() {
    }

    public RecRetenido(Long idRecRetenido) {
        this.idRecRetenido = idRecRetenido;
    }

    public RecRetenido(Long idRecRetenido, Date fechaInicio, Character retenido, BigInteger importe) {
        this.idRecRetenido = idRecRetenido;
        this.fechaInicio = fechaInicio;
        this.retenido = retenido;
        this.importe = importe;
    }

    public Long getIdRecRetenido() {
        return idRecRetenido;
    }

    public void setIdRecRetenido(Long idRecRetenido) {
        this.idRecRetenido = idRecRetenido;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Character getRetenido() {
        return retenido;
    }

    public void setRetenido(Character retenido) {
        this.retenido = retenido;
    }

    public BigInteger getImporte() {
        return importe;
    }

    public void setImporte(BigInteger importe) {
        this.importe = importe;
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
        hash += (idRecRetenido != null ? idRecRetenido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecRetenido)) {
            return false;
        }
        RecRetenido other = (RecRetenido) object;
        if ((this.idRecRetenido == null && other.idRecRetenido != null) || (this.idRecRetenido != null && !this.idRecRetenido.equals(other.idRecRetenido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.RecRetenido[ idRecRetenido=" + idRecRetenido + " ]";
    }
    
}
