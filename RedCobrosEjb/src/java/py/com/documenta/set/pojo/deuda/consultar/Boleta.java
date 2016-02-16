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
@XmlRootElement(name = "Boleta")
@XmlAccessorType(XmlAccessType.FIELD)
public class Boleta {

    @XmlElement(name = "numero")
    private String numero;

    @XmlElement(name = "total_a_pagar")
    private String totalPagar;

    @XmlElement(name = "fecha")
    private String fecha;

    @XmlElement(name = "Impuesto")
    private Impuesto impuesto;

    public Boleta() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(String totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    @Override
    public String toString() {
        return "Boleta{" + "numero=" + numero + ", totalPagar=" + totalPagar + ", fecha=" + fecha + ", impuesto=" + impuesto + '}';
    }

}
