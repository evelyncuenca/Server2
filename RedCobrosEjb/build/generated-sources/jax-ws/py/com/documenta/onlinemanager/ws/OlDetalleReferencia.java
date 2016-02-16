
package py.com.documenta.onlinemanager.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for olDetalleReferencia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="olDetalleReferencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="acumulado" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="comision" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="descipcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hashMapContainer" type="{http://ws.onlineManager.documenta.com.py/}hashMapContainer" minOccurs="0"/>
 *         &lt;element name="idMoneda" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="referenciaPago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tasa" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "olDetalleReferencia", propOrder = {
    "acumulado",
    "comision",
    "descipcion",
    "hashMapContainer",
    "idMoneda",
    "monto",
    "referenciaPago",
    "tasa"
})
public class OlDetalleReferencia {

    protected BigDecimal acumulado;
    protected BigDecimal comision;
    protected String descipcion;
    protected HashMapContainer hashMapContainer;
    protected Integer idMoneda;
    protected BigDecimal monto;
    protected String referenciaPago;
    protected Integer tasa;

    /**
     * Gets the value of the acumulado property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAcumulado() {
        return acumulado;
    }

    /**
     * Sets the value of the acumulado property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAcumulado(BigDecimal value) {
        this.acumulado = value;
    }

    /**
     * Gets the value of the comision property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getComision() {
        return comision;
    }

    /**
     * Sets the value of the comision property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setComision(BigDecimal value) {
        this.comision = value;
    }

    /**
     * Gets the value of the descipcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescipcion() {
        return descipcion;
    }

    /**
     * Sets the value of the descipcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescipcion(String value) {
        this.descipcion = value;
    }

    /**
     * Gets the value of the hashMapContainer property.
     * 
     * @return
     *     possible object is
     *     {@link HashMapContainer }
     *     
     */
    public HashMapContainer getHashMapContainer() {
        return hashMapContainer;
    }

    /**
     * Sets the value of the hashMapContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link HashMapContainer }
     *     
     */
    public void setHashMapContainer(HashMapContainer value) {
        this.hashMapContainer = value;
    }

    /**
     * Gets the value of the idMoneda property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdMoneda() {
        return idMoneda;
    }

    /**
     * Sets the value of the idMoneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdMoneda(Integer value) {
        this.idMoneda = value;
    }

    /**
     * Gets the value of the monto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Sets the value of the monto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonto(BigDecimal value) {
        this.monto = value;
    }

    /**
     * Gets the value of the referenciaPago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaPago() {
        return referenciaPago;
    }

    /**
     * Sets the value of the referenciaPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaPago(String value) {
        this.referenciaPago = value;
    }

    /**
     * Gets the value of the tasa property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTasa() {
        return tasa;
    }

    /**
     * Sets the value of the tasa property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTasa(Integer value) {
        this.tasa = value;
    }

}
