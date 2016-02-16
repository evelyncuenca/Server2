
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.xml.schema.common.RespuestaConsultaCodigoBarras;


/**
 * <p>Java class for validarCodigoBarrasResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validarCodigoBarrasResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://xml.netbeans.org/schema/common}RespuestaConsultaCodigoBarras" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validarCodigoBarrasResponse", propOrder = {
    "_return"
})
public class ValidarCodigoBarrasResponse {

    @XmlElement(name = "return")
    protected RespuestaConsultaCodigoBarras _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaConsultaCodigoBarras }
     *     
     */
    public RespuestaConsultaCodigoBarras getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaConsultaCodigoBarras }
     *     
     */
    public void setReturn(RespuestaConsultaCodigoBarras value) {
        this._return = value;
    }

}
