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
@XmlRootElement(name = "Consulta")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarDeudaResponse {

    @XmlElement(name = "Deuda")
    private Deuda deuda;

    public ConsultarDeudaResponse() {
    }

    public Deuda getDeuda() {
        return deuda;
    }

    public void setDeuda(Deuda deuda) {
        this.deuda = deuda;
    }

    @Override
    public String toString() {
        return "ConsultarDeudaResponse{" + "deuda=" + deuda + '}';
    }

}
