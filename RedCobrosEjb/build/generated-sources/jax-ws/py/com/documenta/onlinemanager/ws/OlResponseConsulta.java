
package py.com.documenta.onlinemanager.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for olResponseConsulta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="olResponseConsulta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cambioMontoPermitido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="codRetorno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="detalleServicios" type="{http://ws.onlineManager.documenta.com.py/}olDetalleServicio" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mensajeOperacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenciaConsulta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "olResponseConsulta", propOrder = {
    "cambioMontoPermitido",
    "codRetorno",
    "detalleServicios",
    "mensajeOperacion",
    "referenciaConsulta"
})
public class OlResponseConsulta {

    protected boolean cambioMontoPermitido;
    protected Integer codRetorno;
    @XmlElement(nillable = true)
    protected List<OlDetalleServicio> detalleServicios;
    protected String mensajeOperacion;
    protected String referenciaConsulta;

    /**
     * Gets the value of the cambioMontoPermitido property.
     * 
     */
    public boolean isCambioMontoPermitido() {
        return cambioMontoPermitido;
    }

    /**
     * Sets the value of the cambioMontoPermitido property.
     * 
     */
    public void setCambioMontoPermitido(boolean value) {
        this.cambioMontoPermitido = value;
    }

    /**
     * Gets the value of the codRetorno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodRetorno() {
        return codRetorno;
    }

    /**
     * Sets the value of the codRetorno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodRetorno(Integer value) {
        this.codRetorno = value;
    }

    /**
     * Gets the value of the detalleServicios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detalleServicios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetalleServicios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OlDetalleServicio }
     * 
     * 
     */
    public List<OlDetalleServicio> getDetalleServicios() {
        if (detalleServicios == null) {
            detalleServicios = new ArrayList<OlDetalleServicio>();
        }
        return this.detalleServicios;
    }

    /**
     * Gets the value of the mensajeOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajeOperacion() {
        return mensajeOperacion;
    }

    /**
     * Sets the value of the mensajeOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajeOperacion(String value) {
        this.mensajeOperacion = value;
    }

    /**
     * Gets the value of the referenciaConsulta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaConsulta() {
        return referenciaConsulta;
    }

    /**
     * Sets the value of the referenciaConsulta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaConsulta(String value) {
        this.referenciaConsulta = value;
    }

}
