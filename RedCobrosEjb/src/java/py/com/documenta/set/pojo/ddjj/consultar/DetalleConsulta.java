/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.ddjj.consultar;

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
public class DetalleConsulta {

    /**
     * Corresponde al número del documento asignado a la declaración jurada que
     * quiere consultarse.
     */
    @XmlElement(name = "numeroDocumento")
    private String numeroDocumento;

    public DetalleConsulta() {
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @Override
    public String toString() {
        return "DetalleConsulta{" + "numeroDocumento=" + numeroDocumento + '}';
    }

}
