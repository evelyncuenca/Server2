/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@Entity
@Table(name = "ADELANTOS_EFECTIVO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdelantosEfectivo.findAll", query = "SELECT a FROM AdelantosEfectivo a"),
    @NamedQuery(name = "AdelantosEfectivo.findByIdAdelantoEfectivo", query = "SELECT a FROM AdelantosEfectivo a WHERE a.idAdelantoEfectivo = :idAdelantoEfectivo"),
    @NamedQuery(name = "AdelantosEfectivo.findByLine", query = "SELECT a FROM AdelantosEfectivo a WHERE a.line = :line"),
    @NamedQuery(name = "AdelantosEfectivo.findByFechaInicio", query = "SELECT a FROM AdelantosEfectivo a WHERE a.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "AdelantosEfectivo.findByFechaFin", query = "SELECT a FROM AdelantosEfectivo a WHERE a.fechaFin = :fechaFin"),
    @NamedQuery(name = "AdelantosEfectivo.findByIdTrxDocumenta", query = "SELECT a FROM AdelantosEfectivo a WHERE a.idTrxDocumenta = :idTrxDocumenta"),
    @NamedQuery(name = "AdelantosEfectivo.findByEstadoTransaccion", query = "SELECT a FROM AdelantosEfectivo a WHERE a.estadoTransaccion = :estadoTransaccion")})
public class AdelantosEfectivo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ADELANTO_EFECTIVO")
    private Long idAdelantoEfectivo;
    @Size(max = 1000)
    @Column(name = "LINE")
    private String line;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "ID_TRX_DOCUMENTA")
    private Long idTrxDocumenta;
    @Column(name = "ESTADO_TRANSACCION")
    private Long estadoTransaccion;
    @JoinColumn(name = "ID_ARCHIVO_AE", referencedColumnName = "ID_ARCHIVO_AE")
    @ManyToOne
    private ArchivosAe idArchivoAe;

    public AdelantosEfectivo() {
    }

    public AdelantosEfectivo(Long idAdelantoEfectivo) {
        this.idAdelantoEfectivo = idAdelantoEfectivo;
    }

    public Long getIdAdelantoEfectivo() {
        return idAdelantoEfectivo;
    }

    public void setIdAdelantoEfectivo(Long idAdelantoEfectivo) {
        this.idAdelantoEfectivo = idAdelantoEfectivo;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getIdTrxDocumenta() {
        return idTrxDocumenta;
    }

    public void setIdTrxDocumenta(Long idTrxDocumenta) {
        this.idTrxDocumenta = idTrxDocumenta;
    }

    public Long getEstadoTransaccion() {
        return estadoTransaccion;
    }

    public void setEstadoTransaccion(Long estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    public ArchivosAe getIdArchivoAe() {
        return idArchivoAe;
    }

    public void setIdArchivoAe(ArchivosAe idArchivoAe) {
        this.idArchivoAe = idArchivoAe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdelantoEfectivo != null ? idAdelantoEfectivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdelantosEfectivo)) {
            return false;
        }
        AdelantosEfectivo other = (AdelantosEfectivo) object;
        if ((this.idAdelantoEfectivo == null && other.idAdelantoEfectivo != null) || (this.idAdelantoEfectivo != null && !this.idAdelantoEfectivo.equals(other.idAdelantoEfectivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.AdelantosEfectivo[ idAdelantoEfectivo=" + idAdelantoEfectivo + " ]";
    }

}
