/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.pago.consultar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <ferinver92@gmail.com>
 */
@XmlRootElement(name = "documento")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarOTRequest {

    @XmlAttribute(name = "era")
    private String era;

    @XmlAttribute(name = "formulario")
    private String formulario;

    @XmlAttribute(name = "version")
    private String version;

    @XmlAttribute(name = "fechaDocumento")
    private String fechaDocumento;

    @XmlAttribute(name = "hash")
    private String hash;

    @XmlElement(name = "numeroDocumento")
    private String numeroDocumento;

    @XmlElement(name = "tipoOt")
    private String tipoOt;

    @XmlElement(name = "importeTransferir")
    private String importeTransferir;

    @XmlElement(name = "cuentaBCP")
    private String cuentaBCP;

    public ConsultarOTRequest() {
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
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

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoOt() {
        return tipoOt;
    }

    public void setTipoOt(String tipoOt) {
        this.tipoOt = tipoOt;
    }

    public String getImporteTransferir() {
        return importeTransferir;
    }

    public void setImporteTransferir(String importeTransferir) {
        this.importeTransferir = importeTransferir;
    }

    public String getCuentaBCP() {
        return cuentaBCP;
    }

    public void setCuentaBCP(String cuentaBCP) {
        this.cuentaBCP = cuentaBCP;
    }

    @Override
    public String toString() {
        return "GuardarOTRequest{" + "era=" + era + ", formulario=" + formulario + ", version=" + version + ", fechaDocumento=" + fechaDocumento + ", hash=" + hash + ", numeroDocumento=" + numeroDocumento + ", tipoOt=" + tipoOt + ", importeTransferir=" + importeTransferir + ", cuentaBCP=" + cuentaBCP + '}';
    }

}
