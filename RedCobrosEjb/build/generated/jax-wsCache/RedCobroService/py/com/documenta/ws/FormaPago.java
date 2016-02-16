
package py.com.documenta.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for formaPago complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="formaPago">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaCheque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idBanco" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="nroCheque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoPago" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "formaPago", propOrder = {
    "fechaCheque",
    "idBanco",
    "nroCheque",
    "tipoPago"
})
public class FormaPago {

    protected String fechaCheque;
    protected BigDecimal idBanco;
    protected String nroCheque;
    protected int tipoPago;

    /**
     * Gets the value of the fechaCheque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCheque() {
        return fechaCheque;
    }

    /**
     * Sets the value of the fechaCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCheque(String value) {
        this.fechaCheque = value;
    }

    /**
     * Gets the value of the idBanco property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIdBanco() {
        return idBanco;
    }

    /**
     * Sets the value of the idBanco property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIdBanco(BigDecimal value) {
        this.idBanco = value;
    }

    /**
     * Gets the value of the nroCheque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroCheque() {
        return nroCheque;
    }

    /**
     * Sets the value of the nroCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroCheque(String value) {
        this.nroCheque = value;
    }

    /**
     * Gets the value of the tipoPago property.
     * 
     */
    public int getTipoPago() {
        return tipoPago;
    }

    /**
     * Sets the value of the tipoPago property.
     * 
     */
    public void setTipoPago(int value) {
        this.tipoPago = value;
    }

}
