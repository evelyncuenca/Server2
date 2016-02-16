
package org.netbeans.xml.schema.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RespuestaConsultaCodigoBarras complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RespuestaConsultaCodigoBarras">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="estado" type="{http://xml.netbeans.org/schema/common}EstadoTransaccion"/>
 *         &lt;element name="referenciaPago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="comision" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idServicio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Monedas" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Moneda" type="{http://xml.netbeans.org/schema/common}ElementoMoneda" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Adicionales" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Adicional" type="{http://xml.netbeans.org/schema/common}ElementoAdicional" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaConsultaCodigoBarras", propOrder = {
    "estado",
    "referenciaPago",
    "monto",
    "comision",
    "mensaje",
    "idServicio",
    "monedas",
    "adicionales"
})
public class RespuestaConsultaCodigoBarras {

    @XmlElement(required = true)
    protected EstadoTransaccion estado;
    protected String referenciaPago;
    protected BigDecimal monto;
    protected BigDecimal comision;
    protected String mensaje;
    protected Integer idServicio;
    @XmlElement(name = "Monedas")
    protected RespuestaConsultaCodigoBarras.Monedas monedas;
    @XmlElement(name = "Adicionales")
    protected RespuestaConsultaCodigoBarras.Adicionales adicionales;

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
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

    /**
     * Gets the value of the idServicio property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdServicio() {
        return idServicio;
    }

    /**
     * Sets the value of the idServicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdServicio(Integer value) {
        this.idServicio = value;
    }

    /**
     * Gets the value of the monedas property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaConsultaCodigoBarras.Monedas }
     *     
     */
    public RespuestaConsultaCodigoBarras.Monedas getMonedas() {
        return monedas;
    }

    /**
     * Sets the value of the monedas property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaConsultaCodigoBarras.Monedas }
     *     
     */
    public void setMonedas(RespuestaConsultaCodigoBarras.Monedas value) {
        this.monedas = value;
    }

    /**
     * Gets the value of the adicionales property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaConsultaCodigoBarras.Adicionales }
     *     
     */
    public RespuestaConsultaCodigoBarras.Adicionales getAdicionales() {
        return adicionales;
    }

    /**
     * Sets the value of the adicionales property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaConsultaCodigoBarras.Adicionales }
     *     
     */
    public void setAdicionales(RespuestaConsultaCodigoBarras.Adicionales value) {
        this.adicionales = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Adicional" type="{http://xml.netbeans.org/schema/common}ElementoAdicional" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "adicional"
    })
    public static class Adicionales {

        @XmlElement(name = "Adicional", required = true)
        protected List<ElementoAdicional> adicional;

        /**
         * Gets the value of the adicional property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the adicional property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdicional().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ElementoAdicional }
         * 
         * 
         */
        public List<ElementoAdicional> getAdicional() {
            if (adicional == null) {
                adicional = new ArrayList<ElementoAdicional>();
            }
            return this.adicional;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Moneda" type="{http://xml.netbeans.org/schema/common}ElementoMoneda" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "moneda"
    })
    public static class Monedas {

        @XmlElement(name = "Moneda", required = true)
        protected List<ElementoMoneda> moneda;

        /**
         * Gets the value of the moneda property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the moneda property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMoneda().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ElementoMoneda }
         * 
         * 
         */
        public List<ElementoMoneda> getMoneda() {
            if (moneda == null) {
                moneda = new ArrayList<ElementoMoneda>();
            }
            return this.moneda;
        }

    }

}
