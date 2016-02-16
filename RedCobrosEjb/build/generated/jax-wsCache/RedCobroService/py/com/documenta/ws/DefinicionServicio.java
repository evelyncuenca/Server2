
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for definicionServicio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="definicionServicio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="anulable" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="cbLongitud" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="cbPresente" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="habilitado" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="idServicio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tagReferencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "definicionServicio", propOrder = {
    "anulable",
    "cbLongitud",
    "cbPresente",
    "descripcion",
    "habilitado",
    "idServicio",
    "tagReferencia"
})
public class DefinicionServicio {

    @XmlSchemaType(name = "unsignedShort")
    protected int anulable;
    protected short cbLongitud;
    @XmlSchemaType(name = "unsignedShort")
    protected int cbPresente;
    protected String descripcion;
    @XmlSchemaType(name = "unsignedShort")
    protected int habilitado;
    protected int idServicio;
    protected String tagReferencia;

    /**
     * Gets the value of the anulable property.
     * 
     */
    public int getAnulable() {
        return anulable;
    }

    /**
     * Sets the value of the anulable property.
     * 
     */
    public void setAnulable(int value) {
        this.anulable = value;
    }

    /**
     * Gets the value of the cbLongitud property.
     * 
     */
    public short getCbLongitud() {
        return cbLongitud;
    }

    /**
     * Sets the value of the cbLongitud property.
     * 
     */
    public void setCbLongitud(short value) {
        this.cbLongitud = value;
    }

    /**
     * Gets the value of the cbPresente property.
     * 
     */
    public int getCbPresente() {
        return cbPresente;
    }

    /**
     * Sets the value of the cbPresente property.
     * 
     */
    public void setCbPresente(int value) {
        this.cbPresente = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the habilitado property.
     * 
     */
    public int getHabilitado() {
        return habilitado;
    }

    /**
     * Sets the value of the habilitado property.
     * 
     */
    public void setHabilitado(int value) {
        this.habilitado = value;
    }

    /**
     * Gets the value of the idServicio property.
     * 
     */
    public int getIdServicio() {
        return idServicio;
    }

    /**
     * Sets the value of the idServicio property.
     * 
     */
    public void setIdServicio(int value) {
        this.idServicio = value;
    }

    /**
     * Gets the value of the tagReferencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagReferencia() {
        return tagReferencia;
    }

    /**
     * Sets the value of the tagReferencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagReferencia(String value) {
        this.tagReferencia = value;
    }

}
