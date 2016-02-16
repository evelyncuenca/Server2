/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.pago.guardar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <ferinver92@gmail.com>
 */
@XmlRootElement(name = "Respuesta")
@XmlAccessorType(XmlAccessType.FIELD)
public class GuardarPagoResponse {

    @XmlElement(name = "Era")
    private String era;

    @XmlElement(name = "NumeroDocumento")
    private String numeroDocumento;

    @XmlElement(name = "FechaRecepcion")
    private String fechaRecepcion;

    @XmlElement(name = "Estado")
    private String estado;

    @XmlElement(name = "Pagos")
    private Pagos pagos;

    @XmlElement(name = "Errores")
    private Errores errores;

    public GuardarPagoResponse() {
    }

    public String getEra() {
        return era;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public String getEstado() {
        return estado;
    }

    public Pagos getPagos() {
        return pagos;
    }

    public Errores getErrores() {
        return errores;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPagos(Pagos pagos) {
        this.pagos = pagos;
    }

    public void setErrores(Errores errores) {
        this.errores = errores;
    }

    @Override
    public String toString() {
        return "PagoResponse{" + "era=" + era + ", numeroDocumento=" + numeroDocumento + ", fechaRecepcion=" + fechaRecepcion + ", estado=" + estado + ", pagos=" + pagos + ", errores=" + errores + '}';
    }

}
