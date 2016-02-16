/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author documenta
 */
@Entity
@Table(name = "HELP_SERVICIO")
@NamedQueries({
    @NamedQuery(name = "HelpServicio.findAll", query = "SELECT h FROM HelpServicio h"),
    @NamedQuery(name = "HelpServicio.findByIdHelpServicio", query = "SELECT h FROM HelpServicio h WHERE h.idHelpServicio = :idHelpServicio"),
    @NamedQuery(name = "HelpServicio.findByComoPagar", query = "SELECT h FROM HelpServicio h WHERE h.comoPagar = :comoPagar"),
    @NamedQuery(name = "HelpServicio.findByModulo", query = "SELECT h FROM HelpServicio h WHERE h.modulo = :modulo"),
    @NamedQuery(name = "HelpServicio.findByVencimiento", query = "SELECT h FROM HelpServicio h WHERE h.vencimiento = :vencimiento"),
    @NamedQuery(name = "HelpServicio.findByObservacion", query = "SELECT h FROM HelpServicio h WHERE h.observacion = :observacion")})
public class HelpServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_HELP_SERVICIO")
    private Long idHelpServicio;
    @Column(name = "COMO_PAGAR")
    private String comoPagar;
    @Column(name = "MODULO")
    private String modulo;
    @Column(name = "VENCIMIENTO")
    private String vencimiento;
    @Column(name = "OBSERVACION")
    private String observacion;
    @JoinColumn(name = "SERVICIO", referencedColumnName = "ID_SERVICIO")
    @ManyToOne
    private ServicioRc servicio;

    public HelpServicio() {
    }

    public HelpServicio(Long idHelpServicio) {
        this.idHelpServicio = idHelpServicio;
    }

    public Long getIdHelpServicio() {
        return idHelpServicio;
    }

    public void setIdHelpServicio(Long idHelpServicio) {
        this.idHelpServicio = idHelpServicio;
    }

    public String getComoPagar() {
        return comoPagar;
    }

    public void setComoPagar(String comoPagar) {
        this.comoPagar = comoPagar;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public ServicioRc getServicio() {
        return servicio;
    }

    public void setServicio(ServicioRc servicio) {
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHelpServicio != null ? idHelpServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HelpServicio)) {
            return false;
        }
        HelpServicio other = (HelpServicio) object;
        if ((this.idHelpServicio == null && other.idHelpServicio != null) || (this.idHelpServicio != null && !this.idHelpServicio.equals(other.idHelpServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.HelpServicio[ idHelpServicio=" + idHelpServicio + " ]";
    }
    
}
