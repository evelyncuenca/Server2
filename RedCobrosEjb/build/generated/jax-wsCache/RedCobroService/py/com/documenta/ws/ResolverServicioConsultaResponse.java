
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import py.com.documenta.onlinemanager.ws.OlResponseConsulta;


/**
 * <p>Java class for resolverServicioConsultaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resolverServicioConsultaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ws.onlineManager.documenta.com.py/}olResponseConsulta" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resolverServicioConsultaResponse", propOrder = {
    "_return"
})
public class ResolverServicioConsultaResponse {

    @XmlElement(name = "return")
    protected OlResponseConsulta _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link OlResponseConsulta }
     *     
     */
    public OlResponseConsulta getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link OlResponseConsulta }
     *     
     */
    public void setReturn(OlResponseConsulta value) {
        this._return = value;
    }

}
