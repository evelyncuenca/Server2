
package org.netbeans.xml.schema.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstadoTransaccion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EstadoTransaccion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoRetorno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idLogTransaccion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstadoTransaccion", propOrder = {
    "codigoRetorno",
    "descripcion",
    "idLogTransaccion"
})
public class EstadoTransaccion {

    protected int codigoRetorno;
    @XmlElement(required = true)
    protected String descripcion;
    protected long idLogTransaccion;

    /**
     * Gets the value of the codigoRetorno property.
     * 
     */
    public int getCodigoRetorno() {
        return codigoRetorno;
    }

    /**
     * Sets the value of the codigoRetorno property.
     * 
     */
    public void setCodigoRetorno(int value) {
        this.codigoRetorno = value;
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
     * Gets the value of the idLogTransaccion property.
     * 
     */
    public long getIdLogTransaccion() {
        return idLogTransaccion;
    }

    /**
     * Sets the value of the idLogTransaccion property.
     * 
     */
    public void setIdLogTransaccion(long value) {
        this.idLogTransaccion = value;
    }

}
