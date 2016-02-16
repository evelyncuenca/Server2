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
import py.com.documenta.set.pojo.pago.guardar.Errores;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@XmlRootElement(name = "Respuesta")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarDeudaResponseError {

    @XmlElement(name = "Estado")
    private String estado;

    @XmlElement(name = "Errores")
    private Errores errores;

    public ConsultarDeudaResponseError() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Errores getErrores() {
        return errores;
    }

    public void setErrores(Errores errores) {
        this.errores = errores;
    }

    @Override
    public String toString() {
        return "ConsultarDeudaResponse{" + "estado=" + estado + ", errores=" + errores + '}';
    }

}
