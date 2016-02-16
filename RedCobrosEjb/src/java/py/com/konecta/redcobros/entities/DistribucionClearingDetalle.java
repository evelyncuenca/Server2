/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
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

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "DISTRIBUCION_CLEARING_DETALLE")
@NamedQueries({@NamedQuery(name = "DistribucionClearingDetalle.findAll", query = "SELECT c FROM DistribucionClearingDetalle c")})
public class DistribucionClearingDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DIST_CLEARING_DET")
    @GeneratedValue(generator = "distClearingDetalleSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "distClearingDetalleSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="distClearingDetalleSeq",initialValue=1)
    private Long idDistClearingDet;
    @Basic(optional = false)
    @Column(name = "ID_BENEFICIARIO")
    private Long idBeneficiario;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_BENEFICIARIO")
    private String descripcionBeneficiario;
    @Basic(optional = false)
    @Column(name = "MONTO_COMISION")
    private Double montoComision;
    @Basic(optional = false)
    @Column(name = "IVA_COMISION")
    private Double ivaComision;
    @JoinColumn(name = "DISTRIBUCION_CLEARING", referencedColumnName = "ID_DISTRIBUCION_CLEARING")
    @ManyToOne(optional = false)
    private DistribucionClearing distribucionClearing;
    @JoinColumn(name = "ROL_COMISIONISTA", referencedColumnName = "ID_ROL_COMISIONISTA")
    @ManyToOne(optional = false)
    private RolComisionista rolComisionista;
    @JoinColumn(name = "CONFIG_COMISIONAL_DETALLE", referencedColumnName = "ID_CONFIG_COMISIONAL_DETALLE")
    @ManyToOne(optional = false)
    private ConfigComisionalDetalle configComisionalDetalle;

    public DistribucionClearing getDistribucionClearing() {
        return distribucionClearing;
    }

    public void setDistribucionClearing(DistribucionClearing distribucionClearing) {
        this.distribucionClearing = distribucionClearing;
    }

    public Long getIdDistClearingDet() {
        return idDistClearingDet;
    }

    public void setIdDistClearingDet(Long idDistClearingDet) {
        this.idDistClearingDet = idDistClearingDet;
    }





    public DistribucionClearingDetalle() {
    }


    public ConfigComisionalDetalle getConfigComisionalDetalle() {
        return configComisionalDetalle;
    }

    public void setConfigComisionalDetalle(ConfigComisionalDetalle configComisionalDetalle) {
        this.configComisionalDetalle = configComisionalDetalle;
    }

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getDescripcionBeneficiario() {
        return descripcionBeneficiario;
    }

    public void setDescripcionBeneficiario(String descripcionBeneficiario) {
        this.descripcionBeneficiario = descripcionBeneficiario;
    }

    public Double getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(Double montoComision) {
        this.montoComision = montoComision;
    }

    public RolComisionista getRolComisionista() {
        return rolComisionista;
    }

    public void setRolComisionista(RolComisionista rolComisionista) {
        this.rolComisionista = rolComisionista;
    }

    public Double getIvaComision() {
        return ivaComision;
    }

    public void setIvaComision(Double ivaComision) {
        this.ivaComision = ivaComision;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistClearingDet != null ? idDistClearingDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistribucionClearingDetalle)) {
            return false;
        }
        DistribucionClearingDetalle other = (DistribucionClearingDetalle) object;
        if ((this.idDistClearingDet == null && other.idDistClearingDet != null) || (this.idDistClearingDet != null && !this.idDistClearingDet.equals(other.idDistClearingDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.DistribucionClearingDetalle[idDistClearingDet=" + idDistClearingDet + "]";
    }

}
