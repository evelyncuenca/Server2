/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.ddjj.guardar;

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
public class GuardarDeclaracionJuradaRequest {

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
     * Fecha de presentación de la DDJJ. Formato: dd/mm/aaaa
     */
    @XmlAttribute(name = "fechaDocumento")
    private String fechaDocumento;
    /**
     * Número de ERA.
     */
    @XmlAttribute(name = "era")
    private String era;
    /**
     * Número de sucursal de la ERA.
     */
    @XmlAttribute(name = "sucursalEra")
    private String sucursalEra;
    /**
     * Número de cajero de la ERA.
     */
    @XmlAttribute(name = "cajero")
    private String cajero;
    /**
     * Consecutivo de Cajero asignado a la Transacción, comienza en uno por día.
     */
    @XmlAttribute(name = "consecutivo")
    private String consecutivo;
    /**
     * Campo obligatorio por compatibilidad con versiones anteriores. Ingresar
     * valor cero (hash="0")
     */
    @XmlAttribute(name = "hash")
    private String hash;

    @XmlElement(name = "detalle")
    private DetalleDDJJ detalle;

    public GuardarDeclaracionJuradaRequest() {
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

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getSucursalEra() {
        return sucursalEra;
    }

    public void setSucursalEra(String sucursalEra) {
        this.sucursalEra = sucursalEra;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public DetalleDDJJ getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleDDJJ detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "GuardarDeclaracionJuradaRequest{" + "formulario=" + formulario + ", version=" + version + ", fechaDocumento=" + fechaDocumento + ", era=" + era + ", sucursalEra=" + sucursalEra + ", cajero=" + cajero + ", consecutivo=" + consecutivo + ", hash=" + hash + ", detalle=" + detalle + '}';
    }

}
