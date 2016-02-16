
package py.com.documenta.ws.practigiro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parametroComision complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="parametroComision">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idTable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="porcentaje" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="respuesta" type="{http://practigiro.ws.documenta.com.py/}respuesta" minOccurs="0"/>
 *         &lt;element name="tablaComisional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parametroComision", propOrder = {
    "comision",
    "idTable",
    "importe",
    "porcentaje",
    "respuesta",
    "tablaComisional"
})
public class ParametroComision {

    protected String comision;
    protected String idTable;
    protected String importe;
    protected Double porcentaje;
    protected Respuesta respuesta;
    protected String tablaComisional;

    /**
     * Gets the value of the comision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComision() {
        return comision;
    }

    /**
     * Sets the value of the comision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComision(String value) {
        this.comision = value;
    }

    /**
     * Gets the value of the idTable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTable() {
        return idTable;
    }

    /**
     * Sets the value of the idTable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTable(String value) {
        this.idTable = value;
    }

    /**
     * Gets the value of the importe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporte() {
        return importe;
    }

    /**
     * Sets the value of the importe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporte(String value) {
        this.importe = value;
    }

    /**
     * Gets the value of the porcentaje property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPorcentaje() {
        return porcentaje;
    }

    /**
     * Sets the value of the porcentaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPorcentaje(Double value) {
        this.porcentaje = value;
    }

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link Respuesta }
     *     
     */
    public Respuesta getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Respuesta }
     *     
     */
    public void setRespuesta(Respuesta value) {
        this.respuesta = value;
    }

    /**
     * Gets the value of the tablaComisional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTablaComisional() {
        return tablaComisional;
    }

    /**
     * Sets the value of the tablaComisional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTablaComisional(String value) {
        this.tablaComisional = value;
    }

}
