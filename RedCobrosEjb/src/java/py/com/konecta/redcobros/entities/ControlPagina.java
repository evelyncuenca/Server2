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
@Table(name = "CONTROL_PAGINA")
@NamedQueries({
    @NamedQuery(name = "ControlPagina.findAll", query = "SELECT c FROM ControlPagina c"),
    @NamedQuery(name = "ControlPagina.findByIdControl", query = "SELECT c FROM ControlPagina c WHERE c.controlPaginaPK.idControl = :idControl"),
    @NamedQuery(name = "ControlPagina.findByIdPatron", query = "SELECT c FROM ControlPagina c WHERE c.controlPaginaPK.idPatron = :idPatron"),
    @NamedQuery(name = "ControlPagina.findByValor", query = "SELECT c FROM ControlPagina c WHERE c.valor = :valor")})
public class ControlPagina implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ControlPaginaPK controlPaginaPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "ID_PATRON", referencedColumnName = "ID_PATRON", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PatronPagina patronPagina;

    public ControlPagina() {
    }

    public ControlPagina(ControlPaginaPK controlPaginaPK) {
        this.controlPaginaPK = controlPaginaPK;
    }

    public ControlPagina(ControlPaginaPK controlPaginaPK, String valor) {
        this.controlPaginaPK = controlPaginaPK;
        this.valor = valor;
    }

    public ControlPagina(String idControl, int idPatron) {
        this.controlPaginaPK = new ControlPaginaPK(idControl, idPatron);
    }

    public ControlPaginaPK getControlPaginaPK() {
        return controlPaginaPK;
    }

    public void setControlPaginaPK(ControlPaginaPK controlPaginaPK) {
        this.controlPaginaPK = controlPaginaPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public PatronPagina getPatronPagina() {
        return patronPagina;
    }

    public void setPatronPagina(PatronPagina patronPagina) {
        this.patronPagina = patronPagina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (controlPaginaPK != null ? controlPaginaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlPagina)) {
            return false;
        }
        ControlPagina other = (ControlPagina) object;
        if ((this.controlPaginaPK == null && other.controlPaginaPK != null) || (this.controlPaginaPK != null && !this.controlPaginaPK.equals(other.controlPaginaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ControlPagina[controlPaginaPK=" + controlPaginaPK + "]";
    }
}
