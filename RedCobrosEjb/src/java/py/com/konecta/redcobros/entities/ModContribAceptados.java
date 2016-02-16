/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;

import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "MOD_CONTRIB_ACEP")
public class ModContribAceptados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MOD_CONTRIB_ACEP")
    private Long idModContribAceptado;
    @Column(name = "MOD_CONTRIB_ACEP")
    private BigInteger modalidadContribuyente;

    public ModContribAceptados() {
    }

    public ModContribAceptados(Long idModContribuyenteAceptado) {
        this.idModContribAceptado = idModContribuyenteAceptado;
    }

    public Long getIdModContribuyenteAceptado() {
        return idModContribAceptado;
    }

    public void setIdModContribuyenteAceptado(Long idModContribuyenteAceptado) {
        this.idModContribAceptado = idModContribuyenteAceptado;
    }

    public BigInteger getModalidadContribuyente() {
        return modalidadContribuyente;
    }

    public void setModalidadContribuyente(BigInteger modalidadContribuyente) {
        this.modalidadContribuyente = modalidadContribuyente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModContribAceptado != null ? idModContribAceptado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModContribAceptados)) {
            return false;
        }
        ModContribAceptados other = (ModContribAceptados) object;
        if ((this.idModContribAceptado == null && other.idModContribAceptado != null) || (this.idModContribAceptado != null && !this.idModContribAceptado.equals(other.idModContribAceptado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.ModContribuyentesAceptados[idModContribuyenteAceptado=" + idModContribAceptado + "]";
    }

}
