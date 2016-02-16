/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.ot.consultar;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Fernando Invernizzi <ferinver92@gmail.com>
 */
public class Pago {

    @XmlElement(name = "Identificador")
    private String identificador;
    @XmlElement(name = "Impuesto")
    private String impuesto;
    @XmlElement(name = "DescripcionImpuesto")
    private String descripcionImpuesto;
    @XmlElement(name = "ValorPagado")
    private String valorPagado;
    @XmlElement(name = "Estado")
    private String estado;

    public Pago() {
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public String getDescripcionImpuesto() {
        return descripcionImpuesto;
    }

    public String getValorPagado() {
        return valorPagado;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Pago{" + "identificador=" + identificador + ", impuesto=" + impuesto + ", descripcionImpuesto=" + descripcionImpuesto + ", valorPagado=" + valorPagado + ", estado=" + estado + '}';
    }

}
