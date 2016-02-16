/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@Entity
@Table(name = "ARCHIVOS_AE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchivosAe.findAll", query = "SELECT a FROM ArchivosAe a"),
    @NamedQuery(name = "ArchivosAe.findByIdArchivoAe", query = "SELECT a FROM ArchivosAe a WHERE a.idArchivoAe = :idArchivoAe"),
    @NamedQuery(name = "ArchivosAe.findByFecha", query = "SELECT a FROM ArchivosAe a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "ArchivosAe.findByIdGestion", query = "SELECT a FROM ArchivosAe a WHERE a.idGestion = :idGestion")})
public class ArchivosAe implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ARCHIVO_AE")
    private Long idArchivoAe;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Column(name = "ARCHIVO")
    private Serializable archivo;
    @Column(name = "ID_GESTION")
    private Long idGestion;
    @OneToMany(mappedBy = "idArchivoAe")
    private List<AdelantosEfectivo> adelantosEfectivoList;
    @JoinColumn(name = "ID_ESTADO_ACHIVO_AE", referencedColumnName = "ID_ESTADO_ARCHIVO_AE")
    @ManyToOne
    private EstadosArchivosAe idEstadoAchivoAe;

    public ArchivosAe() {
    }

    public ArchivosAe(Long idArchivoAe) {
        this.idArchivoAe = idArchivoAe;
    }

    public Long getIdArchivoAe() {
        return idArchivoAe;
    }

    public void setIdArchivoAe(Long idArchivoAe) {
        this.idArchivoAe = idArchivoAe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Serializable getArchivo() {
        return archivo;
    }

    public void setArchivo(Serializable archivo) {
        this.archivo = archivo;
    }

    public Long getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Long idGestion) {
        this.idGestion = idGestion;
    }

    @XmlTransient
    public List<AdelantosEfectivo> getAdelantosEfectivoList() {
        return adelantosEfectivoList;
    }

    public void setAdelantosEfectivoList(List<AdelantosEfectivo> adelantosEfectivoList) {
        this.adelantosEfectivoList = adelantosEfectivoList;
    }

    public EstadosArchivosAe getIdEstadoAchivoAe() {
        return idEstadoAchivoAe;
    }

    public void setIdEstadoAchivoAe(EstadosArchivosAe idEstadoAchivoAe) {
        this.idEstadoAchivoAe = idEstadoAchivoAe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivoAe != null ? idArchivoAe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivosAe)) {
            return false;
        }
        ArchivosAe other = (ArchivosAe) object;
        if ((this.idArchivoAe == null && other.idArchivoAe != null) || (this.idArchivoAe != null && !this.idArchivoAe.equals(other.idArchivoAe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ArchivosAe[ idArchivoAe=" + idArchivoAe + " ]";
    }

}
