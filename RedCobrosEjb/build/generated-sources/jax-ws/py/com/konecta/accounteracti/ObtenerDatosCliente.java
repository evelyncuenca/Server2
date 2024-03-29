
package py.com.konecta.accounteracti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtenerDatosCliente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtenerDatosCliente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idCuentaPractigiros" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerDatosCliente", propOrder = {
    "idCuentaPractigiros"
})
public class ObtenerDatosCliente {

    protected Long idCuentaPractigiros;

    /**
     * Gets the value of the idCuentaPractigiros property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdCuentaPractigiros() {
        return idCuentaPractigiros;
    }

    /**
     * Sets the value of the idCuentaPractigiros property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdCuentaPractigiros(Long value) {
        this.idCuentaPractigiros = value;
    }

}
