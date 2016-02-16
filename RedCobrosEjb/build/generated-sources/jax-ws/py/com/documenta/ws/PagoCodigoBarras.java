
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.xml.schema.common.RespuestaConsultaCodigoBarras;


/**
 * <p>Java class for pagoCodigoBarras complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pagoCodigoBarras">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="respuestaConsulta" type="{http://xml.netbeans.org/schema/common}RespuestaConsultaCodigoBarras" minOccurs="0"/>
 *         &lt;element name="auth" type="{http://ws.documenta.com.py/}autenticacion" minOccurs="0"/>
 *         &lt;element name="formaPago" type="{http://ws.documenta.com.py/}formaPago" minOccurs="0"/>
 *         &lt;element name="valoresIngresados" type="{http://ws.documenta.com.py/}hashMapContainer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagoCodigoBarras", propOrder = {
    "respuestaConsulta",
    "auth",
    "formaPago",
    "valoresIngresados"
})
public class PagoCodigoBarras {

    protected RespuestaConsultaCodigoBarras respuestaConsulta;
    protected Autenticacion auth;
    protected FormaPago formaPago;
    protected HashMapContainer valoresIngresados;

    /**
     * Gets the value of the respuestaConsulta property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaConsultaCodigoBarras }
     *     
     */
    public RespuestaConsultaCodigoBarras getRespuestaConsulta() {
        return respuestaConsulta;
    }

    /**
     * Sets the value of the respuestaConsulta property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaConsultaCodigoBarras }
     *     
     */
    public void setRespuestaConsulta(RespuestaConsultaCodigoBarras value) {
        this.respuestaConsulta = value;
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

}
