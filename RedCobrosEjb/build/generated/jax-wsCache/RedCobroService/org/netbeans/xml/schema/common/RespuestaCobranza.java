
package org.netbeans.xml.schema.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RespuestaCobranza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RespuestaCobranza">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="estado" type="{http://xml.netbeans.org/schema/common}EstadoTransaccion"/>
 *         &lt;element name="idTransaccion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nroBoleta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adicional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaCobranza", propOrder = {
    "estado",
    "idTransaccion",
    "nroBoleta",
    "ticket",
    "adicional"
})
public class RespuestaCobranza {

    @XmlElement(required = true)
    protected EstadoTransaccion estado;
    protected Long idTransaccion;
    protected String nroBoleta;
    protected String ticket;
    protected String adicional;

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link EstadoTransaccion }
     *     
     */
    public EstadoTransaccion getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoTransaccion }
     *     
     */
    public void setEstado(EstadoTransaccion value) {
        this.estado = value;
    }

    /**
     * Gets the value of the idTransaccion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * Sets the value of the idTransaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdTransaccion(Long value) {
        this.idTransaccion = value;
    }

    /**
     * Gets the value of the nroBoleta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroBoleta() {
        return nroBoleta;
    }

    /**
     * Sets the value of the nroBoleta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroBoleta(String value) {
        this.nroBoleta = value;
    }

    /**
     * Gets the value of the ticket property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Sets the value of the ticket property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicket(String value) {
        this.ticket = value;
    }

    /**
     * Gets the value of the adicional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdicional() {
        return adicional;
    }

    /**
     * Sets the value of the adicional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdicional(String value) {
        this.adicional = value;
    }

}
