/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.deuda.consultar;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@XmlRootElement(name = "Deuda")
@XmlAccessorType(XmlAccessType.FIELD)
public class Deuda {

    @XmlElement(name = "ruc")
    private String ruc;

    @XmlElement(name = "dv")
    private String dv;

    @XmlElement(name = "nombreRazonSocial")
    private String nombreRazonSocial;

    @XmlElement(name = "Boleta")
    private List<Boleta> boletas;

    public Deuda() {
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

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public List<Boleta> getBoletas() {
        return boletas;
    }

    public void setBoletas(List<Boleta> boletas) {
        this.boletas = boletas;
    }

    @Override
    public String toString() {
        return "Deuda{" + "ruc=" + ruc + ", dv=" + dv + ", nombreRazonSocial=" + nombreRazonSocial + ", boletas=" + boletas + '}';
    }

}
