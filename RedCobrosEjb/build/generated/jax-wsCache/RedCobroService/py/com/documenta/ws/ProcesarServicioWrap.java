
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for procesarServicioWrap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procesarServicioWrap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="auth" type="{http://ws.documenta.com.py/}autenticacion" minOccurs="0"/>
 *         &lt;element name="formaPago" type="{http://ws.documenta.com.py/}formaPago" minOccurs="0"/>
 *         &lt;element name="valoresIngresados" type="{http://ws.documenta.com.py/}hashMapContainer" minOccurs="0"/>
 *         &lt;element name="secuencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entidad" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procesarServicioWrap", propOrder = {
    "identificador",
    "auth",
    "formaPago",
    "valoresIngresados",
    "secuencia",
    "entidad"
})
public class ProcesarServicioWrap {

    protected Long identificador;
    protected Autenticacion auth;
    protected FormaPago formaPago;
    protected HashMapContainer valoresIngresados;
    protected String secuencia;
    protected Long entidad;

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

    /**
     * Gets the value of the secuencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecuencia() {
        return secuencia;
    }

    /**
     * Sets the value of the secuencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecuencia(String value) {
        this.secuencia = value;
    }

    /**
     * Gets the value of the entidad property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEntidad() {
        return entidad;
    }

    /**
     * Sets the value of the entidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEntidad(Long value) {
        this.entidad = value;
    }

}
