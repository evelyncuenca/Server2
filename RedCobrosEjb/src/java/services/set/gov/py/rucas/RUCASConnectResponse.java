
package services.set.gov.py.rucas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import py.gov.set.rucas.beans.connect.jaws.RUCAS;
import py.gov.set.rucas.beans.error.jaws.RucasError;


/**
 * <p>Java class for RUCASConnectResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RUCASConnectResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cryptMethod" type="{http://py.gov.set.services/rucas/}CryptMethod"/>
 *         &lt;element name="errors" type="{http://error.beans.rucas.set.gov.py/jaws}RucasError" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="loginMethod" type="{http://py.gov.set.services/rucas/}LoginMethod"/>
 *         &lt;element name="rucasList" type="{http://connect.beans.rucas.set.gov.py/jaws}RUCAS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="temporarySessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RUCASConnectResponse", propOrder = {
    "cryptMethod",
    "errors",
    "loginMethod",
    "rucasList",
    "temporarySessionId"
})
public class RUCASConnectResponse {

    @XmlElement(required = true, nillable = true)
    protected CryptMethod cryptMethod;
    @XmlElement(nillable = true)
    protected List<RucasError> errors;
    @XmlElement(required = true, nillable = true)
    protected LoginMethod loginMethod;
    @XmlElement(nillable = true)
    protected List<RUCAS> rucasList;
    @XmlElement(required = true, nillable = true)
    protected String temporarySessionId;

    /**
     * Gets the value of the cryptMethod property.
     * 
     * @return
     *     possible object is
     *     {@link CryptMethod }
     *     
     */
    public CryptMethod getCryptMethod() {
        return cryptMethod;
    }

    /**
     * Sets the value of the cryptMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CryptMethod }
     *     
     */
    public void setCryptMethod(CryptMethod value) {
        this.cryptMethod = value;
    }

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
     * Gets the value of the loginMethod property.
     * 
     * @return
     *     possible object is
     *     {@link LoginMethod }
     *     
     */
    public LoginMethod getLoginMethod() {
        return loginMethod;
    }

    /**
     * Sets the value of the loginMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginMethod }
     *     
     */
    public void setLoginMethod(LoginMethod value) {
        this.loginMethod = value;
    }

    /**
     * Gets the value of the rucasList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rucasList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRucasList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RUCAS }
     * 
     * 
     */
    public List<RUCAS> getRucasList() {
        if (rucasList == null) {
            rucasList = new ArrayList<RUCAS>();
        }
        return this.rucasList;
    }

    /**
     * Gets the value of the temporarySessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemporarySessionId() {
        return temporarySessionId;
    }

    /**
     * Sets the value of the temporarySessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemporarySessionId(String value) {
        this.temporarySessionId = value;
    }

}
