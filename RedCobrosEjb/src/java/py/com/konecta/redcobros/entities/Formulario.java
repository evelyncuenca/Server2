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
@Table(name = "FORMULARIO")
@NamedQueries({@NamedQuery(name = "Formulario.findAll", query = "SELECT f FROM Formulario f")})
public class Formulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FORMULARIO")
    @GeneratedValue(generator = "formularioSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "formularioSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="formularioSeq",initialValue=1)
    private Long idFormulario;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "VERSION")
    private Integer version;
    @Column(name = "FECHA_DESDE")
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    @Column(name = "FECHA_HASTA")
    @Temporal(TemporalType.DATE)
    private Date fechaHasta;
    @Column(name = "NUMERO_FORMULARIO")
    private Integer numeroFormulario;
    @Column(name = "CAMPO_PAGAR")
    private Integer campoPagar;
    @Column(name = "VERSION_DEFAULT")
    private Integer versionDefault;
    @Column(name = "NUM_DEC_ORIG")
    private Integer numDecOrig;
    @Column(name = "NUM_DEC_RECTIF")
    private Integer numDecRectif;
    @Column(name = "NUM_DEC_CLAUSURA")
    private Integer numDecClausura;
    @Column(name = "NUM_NUM_DEC_RECTIF")
    private Integer numNumDecRectif;
    @Column(name = "NUM_PERIODO_FISCAL")
    private Integer numPeriodoFiscal;
    @Column(name = "VALOR_DEC_ORIG")
    private Integer valorDecOrig;
    @Column(name = "VALOR_DEC_RECTIF")
    private Integer valorDecRectif;
    @Column(name = "VALOR_DEC_CLAUSURA") 
    private Integer valorDecClausura;
    @OneToMany(mappedBy = "formulario")
    private Collection<FormContrib> formContribCollection;
    @OneToMany(mappedBy = "formulario")
    private Collection<Dominios> dominiosCollection;
    @OneToMany(mappedBy = "formulario",cascade=CascadeType.ALL)
    private Collection<Campo> campoCollection;

    public Collection<Campo> getCampoCollection() {
        return campoCollection; 
    }

    public void setCampoCollection(Collection<Campo> campoCollection) {
        this.campoCollection = campoCollection;
    }

    

    public Collection<Dominios> getDominiosCollection() {
        return dominiosCollection;
    }

    public void setDominiosCollection(Collection<Dominios> dominiosCollection) {
        this.dominiosCollection = dominiosCollection;
    }

    
    

    public Formulario() {
    }

    public Formulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    public Formulario(Long idFormulario, String descripcion) {
        this.idFormulario = idFormulario;
        this.descripcion = descripcion;
    }

    public Long getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getNumeroFormulario() {
        return numeroFormulario;
    }

    public void setNumeroFormulario(Integer numeroFormulario) {
        this.numeroFormulario = numeroFormulario;
    }

    public Integer getCampoPagar() {
        return campoPagar;
    }

    public void setCampoPagar(Integer campoPagar) {
        this.campoPagar = campoPagar;
    }

    public Integer getVersionDefault() {
        return versionDefault;
    }

    public void setVersionDefault(Integer versionDefault) {
        this.versionDefault = versionDefault;
    }

    public Collection<FormContrib> getFormContribCollection() {
        return formContribCollection;
    }

    public void setFormContribCollection(Collection<FormContrib> formContribCollection) {
        this.formContribCollection = formContribCollection;
    }

    public Integer getNumDecClausura() {
        return numDecClausura;
    }

    public void setNumDecClausura(Integer numDecClausura) {
        this.numDecClausura = numDecClausura;
    }

    public Integer getNumDecOrig() {
        return numDecOrig;
    }

    public void setNumDecOrig(Integer numDecOrig) {
        this.numDecOrig = numDecOrig;
    }

    public Integer getNumDecRectif() {
        return numDecRectif;
    }

    public void setNumDecRectif(Integer numDecRectif) {
        this.numDecRectif = numDecRectif;
    }

    public Integer getNumNumDecRectif() {
        return numNumDecRectif;
    }

    public void setNumNumDecRectif(Integer numNumDecRectif) {
        this.numNumDecRectif = numNumDecRectif;
    }

    public Integer getNumPeriodoFiscal() {
        return numPeriodoFiscal;
    }

    public void setNumPeriodoFiscal(Integer numPeriodoFiscal) {
        this.numPeriodoFiscal = numPeriodoFiscal;
    }

    public Integer getValorDecClausura() {
        return valorDecClausura;
    }

    public void setValorDecClausura(Integer valorDecClausura) {
        this.valorDecClausura = valorDecClausura;
    }

    public Integer getValorDecOrig() {
        return valorDecOrig;
    }

    public void setValorDecOrig(Integer valorDecOrig) {
        this.valorDecOrig = valorDecOrig;
    }

    public Integer getValorDecRectif() {
        return valorDecRectif;
    }

    public void setValorDecRectif(Integer valorDecRectif) {
        this.valorDecRectif = valorDecRectif;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormulario != null ? idFormulario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formulario)) {
            return false;
        }
        Formulario other = (Formulario) object;
        if ((this.idFormulario == null && other.idFormulario != null) || (this.idFormulario != null && !this.idFormulario.equals(other.idFormulario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Formulario[idFormulario=" + idFormulario + "]";
    }

}
