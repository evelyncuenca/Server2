
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getProximoNroOrden complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getProximoNroOrden">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idRecaudador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProximoNroOrden", propOrder = {
    "idRecaudador"
})
public class GetProximoNroOrden {

    protected String idRecaudador;

    /**
     * Gets the value of the idRecaudador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRecaudador() {
        return idRecaudador;
    }

    /**
     * Sets the value of the idRecaudador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRecaudador(String value) {
        this.idRecaudador = value;
    }

}
