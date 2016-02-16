/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;

import java.util.Collection;
import javax.persistence.Basic;
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

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "IMPUESTO")
@NamedQueries({@NamedQuery(name = "Impuesto.findAll", query = "SELECT i FROM Impuesto i")})
public class Impuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_IMPUESTO")
    @GeneratedValue(generator = "impuestoSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "impuestoSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="impuestoSeq",initialValue=1)
    private Long idImpuesto;
    @Column(name = "NUMERO")
    private Integer numero;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "SIGLA")
    private String sigla;
    @Column(name = "TIPO_ATRIBUTO")
    private String tipoAtributo;
    @OneToMany(mappedBy = "impuesto")
    private Collection<BoletaPagoContrib> boletaPagoContribCollection;

    public Impuesto() {
    }

    public Impuesto(Long idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public Long getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(Long idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipoAtributo() {
        return tipoAtributo;
    }

    public void setTipoAtributo(String tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public Collection<BoletaPagoContrib> getBoletaPagoContribCollection() {
        return boletaPagoContribCollection;
    }

    public void setBoletaPagoContribCollection(Collection<BoletaPagoContrib> boletaPagoContribCollection) {
        this.boletaPagoContribCollection = boletaPagoContribCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImpuesto != null ? idImpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Impuesto)) {
            return false;
        }
        Impuesto other = (Impuesto) object;
        if ((this.idImpuesto == null && other.idImpuesto != null) || (this.idImpuesto != null && !this.idImpuesto.equals(other.idImpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Impuesto[idImpuesto=" + idImpuesto + "]";
    }

}
