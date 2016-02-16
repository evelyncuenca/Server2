/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.pago.consultar;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Fernando Invernizzi <ferinver92@gmail.com>
 */
public class Errores {

    @XmlElement(name = "Error")
    private List<Error> errores;

    public Errores() {
    }

    public List<Error> getPagos() {
        return errores;
    }

    @Override
    public String toString() {
        return "Errores{" + "errores=" + errores + '}';
    }
}
