
package py.com.documenta.ws;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for definicionFacturador complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="definicionFacturador">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idFacturador" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="lsDefinicionServicios" type="{http://ws.documenta.com.py/}definicionServicio" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "definicionFacturador", propOrder = {
    "descripcion",
    "idFacturador",
    "lsDefinicionServicios"
})
public class DefinicionFacturador {

    protected String descripcion;
    protected BigDecimal idFacturador;
    @XmlElement(nillable = true)
    protected List<DefinicionServicio> lsDefinicionServicios;

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
     * Gets the value of the idFacturador property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIdFacturador() {
        return idFacturador;
    }

    /**
     * Sets the value of the idFacturador property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIdFacturador(BigDecimal value) {
        this.idFacturador = value;
    }

    /**
     * Gets the value of the lsDefinicionServicios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lsDefinicionServicios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLsDefinicionServicios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DefinicionServicio }
     * 
     * 
     */
    public List<DefinicionServicio> getLsDefinicionServicios() {
        if (lsDefinicionServicios == null) {
            lsDefinicionServicios = new ArrayList<DefinicionServicio>();
        }
        return this.lsDefinicionServicios;
    }

}
