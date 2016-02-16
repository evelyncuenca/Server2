/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "PARAMETROS")
@NamedQueries({@NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p")})
public class Parametros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PARAMETRO")
    @GeneratedValue(generator = "parametrosSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "parametrosSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="parametrosSeq",initialValue=1)
    private Long idParametro;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "INFRACCION")
    private Integer infraccion;
    @Column(name = "FECHA_DESDE")
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    @Column(name = "FECHA_HASTA")
    @Temporal(TemporalType.DATE)
    private Date fechaHasta;
    @Column(name = "PLAZO_DESDE")
    private Integer plazoDesde;
    @Column(name = "PLAZO_HASTA")
    private Integer plazoHasta;
    @Column(name = "VALOR")
    private Double valor;
    @Column(name = "TIPO_CALCULO")
    private String tipoCalculo;
    @Column(name = "VALOR_MINIMO")
    private BigInteger valorMinimo;
    @Column(name = "VALOR_MAXIMO")
    private BigInteger valorMaximo;
    @Column(name = "FUNDAMENTO_LEGAL")
    private String fundamentoLegal;

    public Parametros() {
    }

    public Parametros(Long idParametro) {
        this.idParametro = idParametro;
    }

    public Long getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro = idParametro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getInfraccion() {
        return infraccion;
    }

    public void setInfraccion(Integer infraccion) {
        this.infraccion = infraccion;
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

    public Integer getPlazoDesde() {
        return plazoDesde;
    }

    public void setPlazoDesde(Integer plazoDesde) {
        this.plazoDesde = plazoDesde;
    }

    public Integer getPlazoHasta() {
        return plazoHasta;
    }

    public void setPlazoHasta(Integer plazoHasta) {
        this.plazoHasta = plazoHasta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(String tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public BigInteger getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(BigInteger valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public BigInteger getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(BigInteger valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getFundamentoLegal() {
        return fundamentoLegal;
    }

    public void setFundamentoLegal(String fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Parametros[idParametro=" + idParametro + "]";
    }

}
