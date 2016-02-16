
package py.com.konecta.accounteracti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for activarCliente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="activarCliente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pojo" type="{http://accounteracti.konecta.com.py/}pojo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activarCliente", propOrder = {
    "pojo"
})
public class ActivarCliente {

    protected Pojo pojo;

    /**
     * Gets the value of the pojo property.
     * 
     * @return
     *     possible object is
     *     {@link Pojo }
     *     
     */
    public Pojo getPojo() {
        return pojo;
    }

    /**
     * Sets the value of the pojo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pojo }
     *     
     */
    public void setPojo(Pojo value) {
        this.pojo = value;
    }

}
