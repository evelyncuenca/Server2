/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
@Table(name = "NUMERO_OT")
@NamedQueries({@NamedQuery(name = "NumeroOt.findAll", query = "SELECT n FROM NumeroOt n")})
public class NumeroOt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_NUMERO_OT")
    @GeneratedValue(generator = "numeroOtSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "numeroOtSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="numeroOtSeq",initialValue=1)
    private Long idNumeroOt;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private Integer numero;
    @JoinColumn(name = "RED", referencedColumnName = "ID_RED")
    @ManyToOne(optional = false)
    private Red red;
    @JoinColumn(name = "TIPO_PAGO", referencedColumnName = "ID_TIPO_PAGO")
    @ManyToOne(optional = false)
    private TipoPago tipoPago;
    @Basic(optional = false)
    @Column(name = "ERA_NUMERO_OT_DV")
    private String eraNumeroOtDv;
    @OneToMany(mappedBy = "numeroOt")
    private Collection<BoletaPagoContrib> boletaPagoContribCollection;
    @Column(name = "LOTE")
    private Integer lote;

    public Integer getLote() {
        return lote;
    }

    public void setLote(Integer lote) {
        this.lote = lote;
    }

    public Collection<BoletaPagoContrib> getBoletaPagoContribCollection() {
        return boletaPagoContribCollection;
    }

    public void setBoletaPagoContribCollection(Collection<BoletaPagoContrib> boletaPagoContribCollection) {
        this.boletaPagoContribCollection = boletaPagoContribCollection;
    }



    public String getEraNumeroOtDv() {
        return eraNumeroOtDv;
    }

    public void setEraNumeroOtDv(String eraNumeroOtDv) {
        this.eraNumeroOtDv = eraNumeroOtDv;
    }

     

    public NumeroOt() {
    }

    public NumeroOt(Long idNumeroOt) {
        this.idNumeroOt = idNumeroOt;
    }

    public NumeroOt(Long idNumeroOt, Date fecha, Integer numero) {
        this.idNumeroOt = idNumeroOt;
        this.fecha = fecha;
        this.numero = numero;
    }

    public Long getIdNumeroOt() {
        return idNumeroOt;
    }

    public void setIdNumeroOt(Long idNumeroOt) {
        this.idNumeroOt = idNumeroOt;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNumeroOt != null ? idNumeroOt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NumeroOt)) {
            return false;
        }
        NumeroOt other = (NumeroOt) object;
        if ((this.idNumeroOt == null && other.idNumeroOt != null) || (this.idNumeroOt != null && !this.idNumeroOt.equals(other.idNumeroOt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.NumeroOt[idNumeroOt=" + idNumeroOt + "]";
    }

}
