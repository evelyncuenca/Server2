
package py.com.documenta.ws.practigiro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for datosTransaccion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datosTransaccion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datosTrx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mensajePromocional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="montoBase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respuesta" type="{http://practigiro.ws.documenta.com.py/}respuesta" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosTransaccion", propOrder = {
    "comision",
    "datosTrx",
    "mensajePromocional",
    "montoBase",
    "respuesta"
})
public class DatosTransaccion {

    protected String comision;
    protected String datosTrx;
    protected String mensajePromocional;
    protected String montoBase;
    protected Respuesta respuesta;

    /**
     * Gets the value of the comision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComision() {
        return comision;
    }

    /**
     * Sets the value of the comision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComision(String value) {
        this.comision = value;
    }

    /**
     * Gets the value of the datosTrx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatosTrx() {
        return datosTrx;
    }

    /**
     * Sets the value of the datosTrx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatosTrx(String value) {
        this.datosTrx = value;
    }

    /**
     * Gets the value of the mensajePromocional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajePromocional() {
        return mensajePromocional;
    }

    /**
     * Sets the value of the mensajePromocional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajePromocional(String value) {
        this.mensajePromocional = value;
    }

    /**
     * Gets the value of the montoBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMontoBase() {
        return montoBase;
    }

    /**
     * Sets the value of the montoBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMontoBase(String value) {
        this.montoBase = value;
    }

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link Respuesta }
     *     
     */
    public Respuesta getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Respuesta }
     *     
     */
    public void setRespuesta(Respuesta value) {
        this.respuesta = value;
    }

}
