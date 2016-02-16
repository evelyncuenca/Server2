/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author konecta
 */
@Embeddable
public class CamposFormContribPK implements Serializable {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FORM_CONTRIB", referencedColumnName = "ID_FORM_CONTRIB")
    private FormContrib formContrib;

    @Basic(optional = false)
    @Column(name = "ETIQUETA")
    private String etiqueta;

    public CamposFormContribPK() {
    }

    public FormContrib getFormContrib() {
        return formContrib;
    }

    public void setFormContrib(FormContrib formContrib) {
        this.formContrib = formContrib;
    }


    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }


    @Override
    public int hashCode() {
        if (this.formContrib == null || this.formContrib.getIdFormContrib() == null || this.etiqueta == null)  {
            return 0;
        }

        return this.formContrib.getIdFormContrib().hashCode() + etiqueta.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CamposFormContribPK)) {
            return false;
        }
        CamposFormContribPK other = (CamposFormContribPK) object;
        if ((this.formContrib == null && other.formContrib != null) || (this.formContrib != null && !this.formContrib.equals(other.formContrib))) {
            return false;
        }
        if ((this.etiqueta == null && other.etiqueta != null) || (this.etiqueta != null && !this.etiqueta.equals(other.etiqueta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CamposFormContribPK[formContrib=" + formContrib  + ", etiqueta=" + etiqueta + "]";
    }

}
