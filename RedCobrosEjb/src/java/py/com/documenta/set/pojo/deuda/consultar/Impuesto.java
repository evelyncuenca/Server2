/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.deuda.consultar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@XmlRootElement(name = "Impuesto")
@XmlAccessorType(XmlAccessType.FIELD)
public class Impuesto {

    @XmlElement(name = "codigo")
    private String codigo;

    @XmlElement(name = "descripcion")
    private String descripcion;

    @XmlElement(name = "numero_resolucion")
    private String numeroResolucion;

    @XmlElement(name = "monto")
    private String monto;

    @XmlElement(name = "periodo")
    private String periodo;

    public Impuesto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "Impuesto{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", numeroResolucion=" + numeroResolucion + ", monto=" + monto + ", periodo=" + periodo + '}';
    }

}
