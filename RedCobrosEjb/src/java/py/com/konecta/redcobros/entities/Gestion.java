/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "GESTION")
@NamedQueries({@NamedQuery(name = "Gestion.findAll", query = "SELECT g FROM Gestion g")})
public class Gestion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_GESTION")
    @GeneratedValue(generator = "gestionSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "gestionSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="gestionSeq",initialValue=1)
    private Long idGestion;
    @Basic(optional = false)
    @Column(name = "FECHA_APERTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaApertura;
    @Basic(optional = false)
    @Column(name = "HORA_APERTURA")
    @Temporal(TemporalType.TIME)
    private Date horaApertura;
    @Column(name = "FECHA_CIERRE")
    @Temporal(TemporalType.DATE)
    private Date fechaCierre;
    @Column(name = "HORA_CIERRE")
    @Temporal(TemporalType.TIME)
    private Date horaCierre;
    @Column(name = "FEC_HORA_ULT_UPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecHoraUltUpdate;
    @Basic(optional = false)
    @Column(name = "CERRADO")
    private String cerrado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gestion")
    private Collection<Transaccion> transaccionCollection;
    @Basic(optional = false)
    @Column(name = "PROXIMA_POSICION")
    private Integer proximaPosicion;
    @Basic(optional = false)
    @Column(name = "NRO_GESTION")
    private Integer nroGestion;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    

    public Integer getNroGestion() {
        return nroGestion;
    }

    public void setNroGestion(Integer nroGestion) {
        this.nroGestion = nroGestion;
    }

    public Date getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(Date horaApertura) {
        this.horaApertura = horaApertura;
    }

    public Date getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Date horaCierre) {
        this.horaCierre = horaCierre;
    }


    

    public Integer getProximaPosicion() {
        return proximaPosicion;
    }

    public void setProximaPosicion(Integer proximaPosicion) {
        this.proximaPosicion = proximaPosicion;
    }


    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }


    public Date getFecHoraUltUpdate() {
        return fecHoraUltUpdate;
    }

    public void setFecHoraUltUpdate(Date fecHoraUltUpdate) {
        this.fecHoraUltUpdate = fecHoraUltUpdate;
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gestion")
    private Collection<Grupo> grupoCollection;

    @JoinColumn(name = "TERMINAL", referencedColumnName = "ID_TERMINAL")
    @ManyToOne(optional = false)
    private Terminal terminal;

    public Gestion() {
    }

    public Gestion(Long idGestion) {
        this.idGestion = idGestion;
    }

    public Gestion(Long idGestion, Date fechaApertura, String cerrado) {
        this.idGestion = idGestion;
        this.fechaApertura = fechaApertura;
        this.cerrado = cerrado;
    }

    public Long getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Long idGestion) {
        this.idGestion = idGestion;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getCerrado() {
        return cerrado;
    }

    public void setCerrado(String cerrado) {
        this.cerrado = cerrado;
    }

    public Collection<Grupo> getGrupoCollection() {
        return grupoCollection;
    }

    public void setGrupoCollection(Collection<Grupo> grupoCollection) {
        this.grupoCollection = grupoCollection;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestion != null ? idGestion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gestion)) {
            return false;
        }
        Gestion other = (Gestion) object;
        if ((this.idGestion == null && other.idGestion != null) || (this.idGestion != null && !this.idGestion.equals(other.idGestion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.idGestion[idGestion=" + idGestion + "]";
    }

}
