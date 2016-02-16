/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.pago.guardar;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Fernando Invernizzi <ferinver92@gmail.com>
 */
public class Pagos {

    @XmlElement(name = "Pago")
    private List<Pago> pagos;

    public Pagos() {
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    @Override
    public String toString() {
        return "Pagos{" + "pagos=" + pagos + '}';
    }

}
