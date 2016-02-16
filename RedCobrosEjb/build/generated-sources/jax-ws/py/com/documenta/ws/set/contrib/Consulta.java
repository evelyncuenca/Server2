
package py.com.documenta.ws.set.contrib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consulta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consulta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rucNuevo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consulta", propOrder = {
    "rucNuevo"
})
public class Consulta {

    protected String rucNuevo;

    /**
     * Gets the value of the rucNuevo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRucNuevo() {
        return rucNuevo;
    }

    /**
     * Sets the value of the rucNuevo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRucNuevo(String value) {
        this.rucNuevo = value;
    }

}
