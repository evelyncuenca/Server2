
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for procesarServicio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procesarServicio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="auth" type="{http://ws.documenta.com.py/}autenticacion" minOccurs="0"/>
 *         &lt;element name="formaPago" type="{http://ws.documenta.com.py/}formaPago" minOccurs="0"/>
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
@XmlType(name = "procesarServicio", propOrder = {
    "identificador",
    "auth",
    "formaPago",
    "valoresIngresados"
})
public class ProcesarServicio {

    protected Long identificador;
    protected Autenticacion auth;
    protected FormaPago formaPago;
    protected HashMapContainer valoresIngresados;

    /**
     * Gets the value of the identificador property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdentificador() {
        return identificador;
    }

    /**
     * Sets the value of the identificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdentificador(Long value) {
        this.identificador = value;
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
