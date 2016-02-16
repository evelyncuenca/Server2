/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "ALIAS_SERVICIO_FACTURADOR")
@NamedQueries({
    @NamedQuery(name = "AliasServicioFacturador.findAll", query = "SELECT a FROM AliasServicioFacturador a"),
    @NamedQuery(name = "AliasServicioFacturador.findByIdFacturador", query = "SELECT a FROM AliasServicioFacturador a WHERE a.aliasServicioFacturadorPK.idFacturador = :idFacturador"),
    @NamedQuery(name = "AliasServicioFacturador.findByIdServicio", query = "SELECT a FROM AliasServicioFacturador a WHERE a.aliasServicioFacturadorPK.idServicio = :idServicio"),
    @NamedQuery(name = "AliasServicioFacturador.findByAliasServicio", query = "SELECT a FROM AliasServicioFacturador a WHERE a.aliasServicio = :aliasServicio")})
public class AliasServicioFacturador implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AliasServicioFacturadorPK aliasServicioFacturadorPK;
    @Basic(optional = false)
    @Column(name = "ALIAS_SERVICIO")
    private Integer aliasServicio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aliasServicioFacturador")
    private Collection<DatoConsulta> datoConsultaCollection;
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ServicioRc servicioRc;
    @JoinColumn(name = "ID_FACTURADOR", referencedColumnName = "ID_FACTURADOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Facturador facturador;

    public AliasServicioFacturador() {
    }

    public AliasServicioFacturador(AliasServicioFacturadorPK aliasServicioFacturadorPK) {
        this.aliasServicioFacturadorPK = aliasServicioFacturadorPK;
    }

    public AliasServicioFacturador(AliasServicioFacturadorPK aliasServicioFacturadorPK, Integer aliasServicio) {
        this.aliasServicioFacturadorPK = aliasServicioFacturadorPK;
        this.aliasServicio = aliasServicio;
    }

    public AliasServicioFacturador(BigInteger idFacturador, Integer idServicio) {
        this.aliasServicioFacturadorPK = new AliasServicioFacturadorPK(idFacturador, idServicio);
    }

    public AliasServicioFacturadorPK getAliasServicioFacturadorPK() {
        return aliasServicioFacturadorPK;
    }

    public void setAliasServicioFacturadorPK(AliasServicioFacturadorPK aliasServicioFacturadorPK) {
        this.aliasServicioFacturadorPK = aliasServicioFacturadorPK;
    }

    public Integer getAliasServicio() {
        return aliasServicio;
    }

    public void setAliasServicio(Integer aliasServicio) {
        this.aliasServicio = aliasServicio;
    }

    public Collection<DatoConsulta> getDatoConsultaCollection() {
        return datoConsultaCollection;
    }

    public void setDatoConsultaCollection(Collection<DatoConsulta> datoConsultaCollection) {
        this.datoConsultaCollection = datoConsultaCollection;
    }

    public ServicioRc getServicioRc() {
        return servicioRc;
    }

    public void setServicioRc(ServicioRc servicioRc) {
        this.servicioRc = servicioRc;
    }

    public Facturador getFacturador() {
        return facturador;
    }

    public void setFacturador(Facturador facturador) {
        this.facturador = facturador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aliasServicioFacturadorPK != null ? aliasServicioFacturadorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AliasServicioFacturador)) {
            return false;
        }
        AliasServicioFacturador other = (AliasServicioFacturador) object;
        if ((this.aliasServicioFacturadorPK == null && other.aliasServicioFacturadorPK != null) || (this.aliasServicioFacturadorPK != null && !this.aliasServicioFacturadorPK.equals(other.aliasServicioFacturadorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.AliasServicioFacturador[aliasServicioFacturadorPK=" + aliasServicioFacturadorPK + "]";
    }

}
