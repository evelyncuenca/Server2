/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.pago.guardar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <ferinver92@gmail.com>
 */
@XmlRootElement(name = "Pago")
@XmlAccessorType(XmlAccessType.FIELD)
public class GuardarPagoRequest {

    @XmlAttribute(name = "era")
    private String era;

    @XmlAttribute(name = "tipoInformacion")
    private String tipoInformacion;

    @XmlAttribute(name = "sucursalEra")
    private String sucursalEra;

    @XmlAttribute(name = "cajero")
    private String cajero;

    @XmlAttribute(name = "consecutivo")
    private String consecutivo;

    @XmlAttribute(name = "formulario")
    private String formulario;

    @XmlAttribute(name = "version")
    private String version;

    @XmlAttribute(name = "fechaDocumento")
    private String fechaDocumento;

    @XmlAttribute(name = "impuesto")
    private String impuesto;

    @XmlAttribute(name = "hash")
    private String hash;

    @XmlElement(name = "numeroOt")
    private String numeroOt;

    @XmlElement(name = "numeroDocumento")
    private String numeroDocumento;

    @XmlElement(name = "numeroDocumentoPagar")
    private String numeroDocumentoPagar;

    @XmlElement(name = "ruc")
    private String ruc;

    @XmlElement(name = "dv")
    private String dv;

    @XmlElement(name = "valorPagado")
    private String valorPagado;

    @XmlElement(name = "periodo")
    private String periodo;

    public GuardarPagoRequest() {
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getTipoInformacion() {
        return tipoInformacion;
    }

    public void setTipoInformacion(String tipoInformacion) {
        this.tipoInformacion = tipoInformacion;
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

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNumeroOt() {
        return numeroOt;
    }

    public void setNumeroOt(String numeroOt) {
        this.numeroOt = numeroOt;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(String valorPagado) {
        this.valorPagado = valorPagado;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNumeroDocumentoPagar() {
        return numeroDocumentoPagar;
    }

    public void setNumeroDocumentoPagar(String numeroDocumentoPagar) {
        this.numeroDocumentoPagar = numeroDocumentoPagar;
    }

    @Override
    public String toString() {
        return "GuardarPagoRequest{" + "era=" + era + ", tipoInformacion=" + tipoInformacion + ", sucursalEra=" + sucursalEra + ", cajero=" + cajero + ", consecutivo=" + consecutivo + ", formulario=" + formulario + ", version=" + version + ", fechaDocumento=" + fechaDocumento + ", impuesto=" + impuesto + ", hash=" + hash + ", numeroOt=" + numeroOt + ", numeroDocumento=" + numeroDocumento + ", numeroDocumentoPagar=" + numeroDocumentoPagar + ", ruc=" + ruc + ", dv=" + dv + ", valorPagado=" + valorPagado + ", periodo=" + periodo + '}';
    }

}
