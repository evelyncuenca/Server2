
package py.com.documenta.ws.set.contrib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mergeContrib complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mergeContrib">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contribuyente" type="{http://contrib.ws.daemon.documenta.com.py/}contribuyente" minOccurs="0"/>
 *         &lt;element name="modo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mergeContrib", propOrder = {
    "contribuyente",
    "modo"
})
public class MergeContrib {

    protected Contribuyente contribuyente;
    protected boolean modo;

    /**
     * Gets the value of the contribuyente property.
     * 
     * @return
     *     possible object is
     *     {@link Contribuyente }
     *     
     */
    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    /**
     * Sets the value of the contribuyente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contribuyente }
     *     
     */
    public void setContribuyente(Contribuyente value) {
        this.contribuyente = value;
    }

    /**
     * Gets the value of the modo property.
     * 
     */
    public boolean isModo() {
        return modo;
    }

    /**
     * Sets the value of the modo property.
     * 
     */
    public void setModo(boolean value) {
        this.modo = value;
    }

}
