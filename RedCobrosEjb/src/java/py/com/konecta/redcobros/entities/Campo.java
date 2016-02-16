/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CAMPO")
@NamedQueries({@NamedQuery(name = "Campo.findAll", query = "SELECT c FROM Campo c")})
public class Campo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CAMPO")
    @GeneratedValue(generator = "campoSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "campoSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="campoSeq",initialValue=1)
    private Long idCampo;
    @Column(name = "NUMERO")
    private Integer numero;
    @Column(name = "ETIQUETA")
    private String etiqueta;
    @Column(name = "INCISO")
    private String inciso;
    @Column(name = "ID_RUBRO")
    private BigInteger idRubro;
    @Column(name = "FORMULA")
    private String formula;
    @Column(name = "REQUERIDO")
    private BigInteger requerido;
    @Column(name = "NUMERO_COLUMNA")
    private BigInteger numeroColumna;
    @Column(name = "CADENA_CAMPO")
    private String cadenaCampo;
    @Column(name = "VALIDO")
    private BigInteger valido;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "MOSTRAR_SUGERENCIA")
    private BigInteger mostrarSugerencia;
    @JoinColumn(name = "FORMULARIO", referencedColumnName = "ID_FORMULARIO")
    @ManyToOne(optional = false)
    private Formulario formulario;

    public Campo() {
    }

    public Campo(Long idCampo) {
        this.idCampo = idCampo;
    }

    public Long getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(Long idCampo) {
        this.idCampo = idCampo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getInciso() {
        return inciso;
    }

    public void setInciso(String inciso) {
        this.inciso = inciso;
    }

    public BigInteger getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(BigInteger idRubro) {
        this.idRubro = idRubro;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public BigInteger getRequerido() {
        return requerido;
    }

    public void setRequerido(BigInteger requerido) {
        this.requerido = requerido;
    }

    public BigInteger getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(BigInteger numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public String getCadenaCampo() {
        return cadenaCampo;
    }

    public void setCadenaCampo(String cadenaCampo) {
        this.cadenaCampo = cadenaCampo;
    }

    public BigInteger getValido() {
        return valido;
    }

    public void setValido(BigInteger valido) {
        this.valido = valido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getMostrarSugerencia() {
        return mostrarSugerencia;
    }

    public void setMostrarSugerencia(BigInteger mostrarSugerencia) {
        this.mostrarSugerencia = mostrarSugerencia;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

//    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCampo != null ? idCampo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campo)) {
            return false;
        }
        Campo other = (Campo) object;
        if ((this.idCampo == null && other.idCampo != null) || (this.idCampo != null && !this.idCampo.equals(other.idCampo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Campo[idCampo=" + idCampo + "]";
    }

}
