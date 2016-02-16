
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import py.com.documenta.onlinemanager.ws.OlDetalleReferencia;


/**
 * <p>Java class for realizarCobranzaManual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="realizarCobranzaManual">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idServicio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="detalleReferencia" type="{http://ws.onlineManager.documenta.com.py/}olDetalleReferencia" minOccurs="0"/>
 *         &lt;element name="auth" type="{http://ws.documenta.com.py/}autenticacion" minOccurs="0"/>
 *         &lt;element name="formaPago" type="{http://ws.documenta.com.py/}formaPago" minOccurs="0"/>
 *         &lt;element name="referenciaConsulta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valoresIngresados" type="{http://ws.documenta.com.py/}hashMapContainer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "realizarCobranzaManual", propOrder = {
    "idServicio",
    "detalleReferencia",
    "auth",
    "formaPago",
    "referenciaConsulta",
    "valoresIngresados"
})
public class RealizarCobranzaManual {

    protected Integer idServicio;
    protected OlDetalleReferencia detalleReferencia;
    protected Autenticacion auth;
    protected FormaPago formaPago;
    protected String referenciaConsulta;
    protected HashMapContainer valoresIngresados;

    /**
     * Gets the value of the idServicio property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdServicio() {
        return idServicio;
    }

    /**
     * Sets the value of the idServicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdServicio(Integer value) {
        this.idServicio = value;
    }

    /**
     * Gets the value of the detalleReferencia property.
     * 
     * @return
     *     possible object is
     *     {@link OlDetalleReferencia }
     *     
     */
    public OlDetalleReferencia getDetalleReferencia() {
        return detalleReferencia;
    }

    /**
     * Sets the value of the detalleReferencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link OlDetalleReferencia }
     *     
     */
    public void setDetalleReferencia(OlDetalleReferencia value) {
        this.detalleReferencia = value;
    }

    /**
     * Gets the value of the auth property.
     * 
     * @return
     *     possible object is
     *     {@link Autenticacion }
     *     
     */
    public Autenticacion getAuth() {
        return auth;
    }

    /**
     * Sets the value of the auth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Autenticacion }
     *     
     */
    public void setAuth(Autenticacion value) {
        this.auth = value;
    }

    /**
     * Gets the value of the formaPago property.
     * 
     * @return
     *     possible object is
     *     {@link FormaPago }
     *     
     */
    public FormaPago getFormaPago() {
        return formaPago;
    }

    /**
     * Sets the value of the formaPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormaPago }
     *     
     */
    public void setFormaPago(FormaPago value) {
        this.formaPago = value;
    }

    /**
     * Gets the value of the referenciaConsulta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaConsulta() {
        return referenciaConsulta;
    }

    /**
     * Sets the value of the referenciaConsulta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaConsulta(String value) {
        this.referenciaConsulta = value;
    }

    /**
     * Gets the value of the valoresIngresados property.
     * 
     * @return
     *     possible object is
     *     {@link HashMapContainer }
     *     
     */
    public HashMapContainer getValoresIngresados() {
        return valoresIngresados;
    }

    /**
     * Sets the value of the valoresIngresados property.
     * 
     * @param value
     *     allowed object is
     *     {@link HashMapContainer }
     *     
     */
    public void setValoresIngresados(HashMapContainer value) {
        this.valoresIngresados = value;
    }

}
