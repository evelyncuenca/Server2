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
import py.com.documenta.set.pojo.pago.guardar.Errores;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@XmlRootElement(name = "Respuesta")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarDeclaracionJuradaResponse {

    @XmlElement(name = "Era")
    private String era;

    @XmlElement(name = "NumeroDocumento")
    private String numeroDocumento;

    @XmlElement(name = "FechaRecepcion")
    private String fechaRecepcion;

    @XmlElement(name = "IdentificadorDocumento")
    private String identificadorDocumento;

    @XmlElement(name = "Estado")
    private String estado;

    @XmlElement(name = "Errores")
    private Errores errores;

    public ConsultarDeclaracionJuradaResponse() {
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getIdentificadorDocumento() {
        return identificadorDocumento;
    }

    public void setIdentificadorDocumento(String identificadorDocumento) {
        this.identificadorDocumento = identificadorDocumento;
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
        return "DeclaracionJuradaResponse{" + "era=" + era + ", numeroDocumento=" + numeroDocumento + ", fechaRecepcion=" + fechaRecepcion + ", identificadorDocumento=" + identificadorDocumento + ", estado=" + estado + ", errores=" + errores + '}';
    }

}
