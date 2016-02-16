
package py.com.documenta.ws.set.contrib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for contribuyente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="contribuyente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="digitoVerificador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mesCierre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="razonSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rucNuevo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rucViejo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuarioTerminal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contribuyente", propOrder = {
    "digitoVerificador",
    "fechaAlta",
    "id",
    "mesCierre",
    "modalidad",
    "razonSocial",
    "rucNuevo",
    "rucViejo",
    "tipo",
    "usuarioTerminal"
})
public class Contribuyente {

    protected String digitoVerificador;
    protected String fechaAlta;
    protected String id;
    protected String mesCierre;
    protected String modalidad;
    protected String razonSocial;
    protected String rucNuevo;
    protected String rucViejo;
    protected String tipo;
    protected String usuarioTerminal;

    /**
     * Gets the value of the digitoVerificador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    /**
     * Sets the value of the digitoVerificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigitoVerificador(String value) {
        this.digitoVerificador = value;
    }

    /**
     * Gets the value of the fechaAlta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Sets the value of the fechaAlta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAlta(String value) {
        this.fechaAlta = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the mesCierre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMesCierre() {
        return mesCierre;
    }

    /**
     * Sets the value of the mesCierre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMesCierre(String value) {
        this.mesCierre = value;
    }

    /**
     * Gets the value of the modalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * Sets the value of the modalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalidad(String value) {
        this.modalidad = value;
    }

    /**
     * Gets the value of the razonSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Sets the value of the razonSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonSocial(String value) {
        this.razonSocial = value;
    }

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

    /**
     * Gets the value of the rucViejo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRucViejo() {
        return rucViejo;
    }

    /**
     * Sets the value of the rucViejo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRucViejo(String value) {
        this.rucViejo = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the usuarioTerminal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuarioTerminal() {
        return usuarioTerminal;
    }

    /**
     * Sets the value of the usuarioTerminal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioTerminal(String value) {
        this.usuarioTerminal = value;
    }

}
