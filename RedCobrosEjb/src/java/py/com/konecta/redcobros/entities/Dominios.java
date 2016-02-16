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
@Table(name = "DOMINIOS")
@NamedQueries({@NamedQuery(name = "Dominios.findAll", query = "SELECT d FROM Dominios d")})
public class Dominios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DOMINIO")
    @GeneratedValue(generator = "dominiosSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "dominiosSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="dominiosSeq",initialValue=1)
    private Long idDominio;
    @Column(name = "ETIQUETA_CABECERA")
    private String etiquetaCabecera;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "FORMATO")
    private String formato;
    @Column(name = "ETIQUETA_DETALLE")
    private String etiquetaDetalle;
    @JoinColumn(name = "FORMULARIO", referencedColumnName = "ID_FORMULARIO")
    @ManyToOne
    private Formulario formulario;

    public Dominios() {
    }

    public Dominios(Long idDominio) {
        this.idDominio = idDominio;
    }

    public Long getIdDominio() {
        return idDominio;
    }

    public void setIdDominio(Long idDominio) {
        this.idDominio = idDominio;
    }

    public String getEtiquetaCabecera() {
        return etiquetaCabecera;
    }

    public void setEtiquetaCabecera(String etiquetaCabecera) {
        this.etiquetaCabecera = etiquetaCabecera;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getEtiquetaDetalle() {
        return etiquetaDetalle;
    }

    public void setEtiquetaDetalle(String etiquetaDetalle) {
        this.etiquetaDetalle = etiquetaDetalle;
    }

    public Formulario getIdFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDominio != null ? idDominio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dominios)) {
            return false;
        }
        Dominios other = (Dominios) object;
        if ((this.idDominio == null && other.idDominio != null) || (this.idDominio != null && !this.idDominio.equals(other.idDominio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Dominios[idDominio=" + idDominio + "]";
    }

}
