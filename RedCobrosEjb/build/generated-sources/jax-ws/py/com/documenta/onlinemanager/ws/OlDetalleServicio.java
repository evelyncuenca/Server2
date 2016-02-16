
package py.com.documenta.onlinemanager.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for olDetalleServicio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="olDetalleServicio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="detalleReferencias" type="{http://ws.onlineManager.documenta.com.py/}olDetalleReferencia" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="idServicio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="monedas" type="{http://ws.onlineManager.documenta.com.py/}moneda" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tipoAcumulado" type="{http://ws.onlineManager.documenta.com.py/}olTipoAcumulado" minOccurs="0"/>
 *         &lt;element name="tipoSeleccion" type="{http://ws.onlineManager.documenta.com.py/}olTipoSeleccion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "olDetalleServicio", propOrder = {
    "detalleReferencias",
    "idServicio",
    "monedas",
    "tipoAcumulado",
    "tipoSeleccion"
})
public class OlDetalleServicio {

    @XmlElement(nillable = true)
    protected List<OlDetalleReferencia> detalleReferencias;
    protected Integer idServicio;
    @XmlElement(nillable = true)
    protected List<Moneda> monedas;
    protected OlTipoAcumulado tipoAcumulado;
    protected OlTipoSeleccion tipoSeleccion;

    /**
     * Gets the value of the detalleReferencias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detalleReferencias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetalleReferencias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OlDetalleReferencia }
     * 
     * 
     */
    public List<OlDetalleReferencia> getDetalleReferencias() {
        if (detalleReferencias == null) {
            detalleReferencias = new ArrayList<OlDetalleReferencia>();
        }
        return this.detalleReferencias;
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monedas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonedas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Moneda }
     * 
     * 
     */
    public List<Moneda> getMonedas() {
        if (monedas == null) {
            monedas = new ArrayList<Moneda>();
        }
        return this.monedas;
    }

    /**
     * Gets the value of the tipoAcumulado property.
     * 
     * @return
     *     possible object is
     *     {@link OlTipoAcumulado }
     *     
     */
    public OlTipoAcumulado getTipoAcumulado() {
        return tipoAcumulado;
    }

    /**
     * Sets the value of the tipoAcumulado property.
     * 
     * @param value
     *     allowed object is
     *     {@link OlTipoAcumulado }
     *     
     */
    public void setTipoAcumulado(OlTipoAcumulado value) {
        this.tipoAcumulado = value;
    }

    /**
     * Gets the value of the tipoSeleccion property.
     * 
     * @return
     *     possible object is
     *     {@link OlTipoSeleccion }
     *     
     */
    public OlTipoSeleccion getTipoSeleccion() {
        return tipoSeleccion;
    }

    /**
     * Sets the value of the tipoSeleccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link OlTipoSeleccion }
     *     
     */
    public void setTipoSeleccion(OlTipoSeleccion value) {
        this.tipoSeleccion = value;
    }

}
