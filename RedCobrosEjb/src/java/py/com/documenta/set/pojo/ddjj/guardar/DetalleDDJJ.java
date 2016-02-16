/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.ddjj.guardar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@XmlRootElement(name = "detalle")
@XmlAccessorType(XmlAccessType.FIELD)
public class DetalleDDJJ {

    private String c;
    /**
     * RUC del contribuyente
     */
    @XmlElement(name = "ruc")
    private String ruc;
    /**
     * Dígito verificador del RUC.
     */
    @XmlElement(name = "dv")
    private String dv;
    /**
     * Corresponde al número del documento que se asigna a la declaración en el
     * momento de la recepción. Este número es asignado por la ERA y debe ser
     * único pero no consecutivo. Debe comenzar con dos dígitos del código de la
     * ERA. Máximo 11 dígitos
     */
    @XmlElement(name = "numeroDocumento")
    private String numeroDocumento;
    /**
     * Número de identificación de la persona que presenta la declaración jurada
     */
    @XmlElement(name = "numeroCedula")
    private String numeroCedula;

    public DetalleDDJJ() {
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(String numeroCedula) {
        this.numeroCedula = numeroCedula;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "DetalleDDJJ{" + "c=" + c + ", ruc=" + ruc + ", dv=" + dv + ", numeroDocumento=" + numeroDocumento + ", numeroCedula=" + numeroCedula + '}';
    }

}
