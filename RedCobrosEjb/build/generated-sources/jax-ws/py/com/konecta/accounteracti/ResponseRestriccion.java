
package py.com.konecta.accounteracti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responseRestriccion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="responseRestriccion">
 *   &lt;complexContent>
 *     &lt;extension base="{http://accounteracti.konecta.com.py/}response">
 *       &lt;sequence>
 *         &lt;element name="codigoRestriccion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="formulario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mensajeRestriccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseRestriccion", propOrder = {
    "codigoRestriccion",
    "formulario",
    "mensajeRestriccion"
})
public class ResponseRestriccion
    extends Response
{

    protected Integer codigoRestriccion;
    protected String formulario;
    protected String mensajeRestriccion;

    /**
     * Gets the value of the codigoRestriccion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodigoRestriccion() {
        return codigoRestriccion;
    }

    /**
     * Sets the value of the codigoRestriccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodigoRestriccion(Integer value) {
        this.codigoRestriccion = value;
    }

    /**
     * Gets the value of the formulario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormulario() {
        return formulario;
    }

    /**
     * Sets the value of the formulario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormulario(String value) {
        this.formulario = value;
    }

    /**
     * Gets the value of the mensajeRestriccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajeRestriccion() {
        return mensajeRestriccion;
    }

    /**
     * Sets the value of the mensajeRestriccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajeRestriccion(String value) {
        this.mensajeRestriccion = value;
    }

}
