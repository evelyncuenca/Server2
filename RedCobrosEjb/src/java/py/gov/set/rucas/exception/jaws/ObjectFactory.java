
package py.gov.set.rucas.exception.jaws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the py.gov.set.rucas.exception.jaws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RucasException_QNAME = new QName("http://exception.rucas.set.gov.py/jaws", "RucasException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: py.gov.set.rucas.exception.jaws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RucasException }
     * 
     */
    public RucasException createRucasException() {
        return new RucasException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RucasException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.rucas.set.gov.py/jaws", name = "RucasException")
    public JAXBElement<RucasException> createRucasException(RucasException value) {
        return new JAXBElement<RucasException>(_RucasException_QNAME, RucasException.class, null, value);
    }

}
