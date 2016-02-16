/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "GRUPO")
@NamedQueries({@NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")})
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_GRUPO")
    @GeneratedValue(generator = "grupoSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "grupoSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="grupoSeq",initialValue=1)
    private Long idGrupo;
    @Basic(optional = false)
    @Column(name = "PROXIMA_POSICION")
    private Integer proximaPosicion;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaInicio;
    @Column(name = "HORA_CIERRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaCierre;
    @JoinColumn(name = "GESTION", referencedColumnName = "ID_GESTION")
    @ManyToOne(optional = false)
    private Gestion gestion;
    @JoinColumn(name = "SERVICIO", referencedColumnName = "ID_SERVICIO")
    @ManyToOne(optional = false)
    private Servicio servicio;    
    @Basic(optional = false)
    @Column(name = "CERRADO")
    private String cerrado;   
    @OneToMany(mappedBy = "grupo")
    private Collection<FormContrib> formContribCollection;
    @OneToMany(mappedBy = "grupo")
    private Collection<BoletaPagoContrib> boletaPagoContribCollection;
    @Basic(optional = false)
    @Column(name = "PROCESADO")
    private String procesado;
    @Column(name = "NUMERO_CAJA")
    private Integer numeroCaja;
    @Column(name = "TOTAL_OPERACIONES")
    private Integer totalOperaciones;

    public Integer getTotalOperaciones() {
        return totalOperaciones;
    }

    public void setTotalOperaciones(Integer totalOperaciones) {
        this.totalOperaciones = totalOperaciones;
    }
    

    public Integer getNumeroCaja() {
        return numeroCaja;
    }

    public void setNumeroCaja(Integer numeroCaja) {
        this.numeroCaja = numeroCaja;
    }



    public String getProcesado() {
        return procesado;
    }

    public void setProcesado(String procesado) {
        this.procesado = procesado;
    }

    public Collection<BoletaPagoContrib> getBoletaPagoContribCollection() {
        return boletaPagoContribCollection;
    }

    public void setBoletaPagoContribCollection(Collection<BoletaPagoContrib> boletaPagoContribCollection) {
        this.boletaPagoContribCollection = boletaPagoContribCollection;
    }

    public Collection<FormContrib> getFormContribCollection() {
        return formContribCollection;
    }

    public void setFormContribCollection(Collection<FormContrib> formContribCollection) {
        this.formContribCollection = formContribCollection;
    }

   

    public String getCerrado() {
        return cerrado;
    }

    public void setCerrado(String cerrado) {
        this.cerrado = cerrado;
    }

    public Grupo() {
    }

    public Grupo(Long idGestion) {
        this.idGrupo = idGestion;
    }

    public Grupo(Long idGestion, Integer numeroGestion, Date fecha, Date horaInicio) {
        this.idGrupo = idGestion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGestion) {
        this.idGrupo = idGestion;
    }

    public Integer getProximaPosicion() {
        return proximaPosicion;
    }

    public void setProximaPosicion(Integer proximaPosicion) {
        this.proximaPosicion = proximaPosicion;
    }

    

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Date horaCierre) {
        this.horaCierre = horaCierre;
    }

    public Gestion getGestion() {
        return gestion;
    }

    public void setGestion(Gestion gestion) {
        this.gestion = gestion;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Gestion[idGestion=" + idGrupo + "]";
    }

}
