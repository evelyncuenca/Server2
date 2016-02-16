/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.ddjj.consultar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@XmlRootElement(name = "declaracion")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarDeclaracionJuradaRequest {

    /**
     * Número de formulario enviado.
     */
    @XmlAttribute(name = "formulario")
    private String formulario;
    /**
     * Versión del formulario enviado.
     */
    @XmlAttribute(name = "version")
    private String version;
    /**
     * Número de ERA.
     */
    @XmlAttribute(name = "era")
    private String era;

    @XmlElement(name = "detalle")
    private DetalleConsulta detalle;

    public ConsultarDeclaracionJuradaRequest() {
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public DetalleConsulta getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleConsulta detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "ConsultarDeclaracionJuradaRequest{" + "formulario=" + formulario + ", version=" + version + ", era=" + era + ", detalle=" + detalle + '}';
    }

}
