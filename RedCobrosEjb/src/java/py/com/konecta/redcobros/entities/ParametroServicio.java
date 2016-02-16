/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "PARAMETRO_SERVICIO")
@NamedQueries({
    @NamedQuery(name = "ParametroServicio.findAll", query = "SELECT p FROM ParametroServicio p"),
    @NamedQuery(name = "ParametroServicio.findByIdServicio", query = "SELECT p FROM ParametroServicio p WHERE p.parametroServicioPK.idServicio = :idServicio"),
    @NamedQuery(name = "ParametroServicio.findByClave", query = "SELECT p FROM ParametroServicio p WHERE p.parametroServicioPK.clave = :clave"),
    @NamedQuery(name = "ParametroServicio.findByValor", query = "SELECT p FROM ParametroServicio p WHERE p.valor = :valor")})
public class ParametroServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParametroServicioPK parametroServicioPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ServicioRc servicioRc;

    public ParametroServicio() {
    }

    public ParametroServicio(ParametroServicioPK parametroServicioPK) {
        this.parametroServicioPK = parametroServicioPK;
    }

    public ParametroServicio(ParametroServicioPK parametroServicioPK, String valor) {
        this.parametroServicioPK = parametroServicioPK;
        this.valor = valor;
    }

    public ParametroServicio(int idServicio, String clave) {
        this.parametroServicioPK = new ParametroServicioPK(idServicio, clave);
    }

    public ParametroServicioPK getParametroServicioPK() {
        return parametroServicioPK;
    }

    public void setParametroServicioPK(ParametroServicioPK parametroServicioPK) {
        this.parametroServicioPK = parametroServicioPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public ServicioRc getServicioRc() {
        return servicioRc;
    }

    public void setServicioRc(ServicioRc servicioRc) {
        this.servicioRc = servicioRc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parametroServicioPK != null ? parametroServicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroServicio)) {
            return false;
        }
        ParametroServicio other = (ParametroServicio) object;
        if ((this.parametroServicioPK == null && other.parametroServicioPK != null) || (this.parametroServicioPK != null && !this.parametroServicioPK.equals(other.parametroServicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ParametroServicio[parametroServicioPK=" + parametroServicioPK + "]";
    }

}
