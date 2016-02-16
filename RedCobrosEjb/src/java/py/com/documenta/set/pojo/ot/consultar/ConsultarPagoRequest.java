/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.pojo.ot.consultar;

import py.com.documenta.set.pojo.pago.consultar.*;
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
public class ConsultarPagoRequest {

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

    @XmlElement(name = "ruc")
    private String ruc;

    @XmlElement(name = "dv")
    private String dv;

    @XmlElement(name = "valorPagado")
    private String valorPagado;

    @XmlElement(name = "periodo")
    private String periodo;

    public ConsultarPagoRequest() {
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

    @Override
    public String toString() {
        return "Pago{" + "era=" + era + ", tipoInformacion=" + tipoInformacion + ", sucursalEra=" + sucursalEra + ", cajero=" + cajero + ", consecutivo=" + consecutivo + ", formulario=" + formulario + ", version=" + version + ", fechaDocumento=" + fechaDocumento + ", impuesto=" + impuesto + ", hash=" + hash + ", numeroOt=" + numeroOt + ", numeroDocumento=" + numeroDocumento + ", ruc=" + ruc + ", dv=" + dv + ", valorPagado=" + valorPagado + ", periodo=" + periodo + '}';
    }

}
