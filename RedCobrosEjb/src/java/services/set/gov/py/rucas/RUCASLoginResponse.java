
package services.set.gov.py.rucas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import py.gov.set.rucas.beans.components.jaws.SessionInfo;
import py.gov.set.rucas.beans.error.jaws.RucasError;


/**
 * <p>Java class for RUCASLoginResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RUCASLoginResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errors" type="{http://error.beans.rucas.set.gov.py/jaws}RucasError" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="permanentSessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sessionInfo" type="{http://components.beans.rucas.set.gov.py/jaws}SessionInfo"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RUCASLoginResponse", propOrder = {
    "errors",
    "permanentSessionId",
    "sessionInfo",
    "success"
})
public class RUCASLoginResponse {

    @XmlElement(nillable = true)
    protected List<RucasError> errors;
    @XmlElement(required = true, nillable = true)
    protected String permanentSessionId;
    @XmlElement(required = true, nillable = true)
    protected SessionInfo sessionInfo;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean success;

    /**
     * Gets the value of the errors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RucasError }
     * 
     * 
     */
    public List<RucasError> getErrors() {
        if (errors == null) {
            errors = new ArrayList<RucasError>();
        }
        return this.errors;
    }

    /**
     * Gets the value of the permanentSessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermanentSessionId() {
        return permanentSessionId;
    }

    /**
     * Sets the value of the permanentSessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermanentSessionId(String value) {
        this.permanentSessionId = value;
    }

    /**
     * Gets the value of the sessionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SessionInfo }
     *     
     */
    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    /**
     * Sets the value of the sessionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionInfo }
     *     
     */
    public void setSessionInfo(SessionInfo value) {
        this.sessionInfo = value;
    }

    /**
     * Gets the value of the success property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuccess(Boolean value) {
        this.success = value;
    }

}
