/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.pago.guardar;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Fernando Invernizzi <ferinver92@gmail.com>
 */
public class Error {

    @XmlElement(name = "TIPO")
    private String tipo;

    @XmlElement(name = "RECHAZAR")
    private String rechazar;

    @XmlElement(name = "DESCRIPCION")
    private String descripcion;

    public Error() {
    }

    public String getTipo() {
        return tipo;
    }

    public String getRechazar() {
        return rechazar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Error{" + "tipo=" + tipo + ", rechazar=" + rechazar + ", descripcion=" + descripcion + '}';
    }

}
