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
import javax.persistence.Table;


/**
 *
 * @author konecta
 */
@Entity
@Table(name = "PARAMETRO_SISTEMA")
public class ParametroSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE_PARAMETRO")
    private String nombreParametro;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;

    public ParametroSistema(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public ParametroSistema() {
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreParametro != null ? nombreParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroSistema)) {
            return false;
        }
        ParametroSistema other = (ParametroSistema) object;
        if ((this.nombreParametro == null && other.nombreParametro != null) || (this.nombreParametro != null && !this.nombreParametro.equals(other.nombreParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.ejb.ParametroSistema[nombreParametro=" + nombreParametro + "]";
    }

}
