
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import py.com.documenta.onlinemanager.ws.OlResponseConsulta;


/**
 * <p>Java class for procesarConsulta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procesarConsulta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auth" type="{http://ws.documenta.com.py/}autenticacion" minOccurs="0"/>
 *         &lt;element name="respuestaConsulta" type="{http://ws.onlineManager.documenta.com.py/}olResponseConsulta" minOccurs="0"/>
 *         &lt;element name="formaPago" type="{http://ws.documenta.com.py/}formaPago" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procesarConsulta", propOrder = {
    "auth",
    "respuestaConsulta",
    "formaPago"
})
public class ProcesarConsulta {

    protected Autenticacion auth;
    protected OlResponseConsulta respuestaConsulta;
    protected FormaPago formaPago;

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
     * Gets the value of the respuestaConsulta property.
     * 
     * @return
     *     possible object is
     *     {@link OlResponseConsulta }
     *     
     */
    public OlResponseConsulta getRespuestaConsulta() {
        return respuestaConsulta;
    }

    /**
     * Sets the value of the respuestaConsulta property.
     * 
     * @param value
     *     allowed object is
     *     {@link OlResponseConsulta }
     *     
     */
    public void setRespuestaConsulta(OlResponseConsulta value) {
        this.respuestaConsulta = value;
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

}
