
package services.set.gov.py.rucas.pagos;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the services.set.gov.py.rucas.pagos package. 
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

    private final static QName _RucasWsException_QNAME = new QName("http://py.gov.set.services/rucas/pagos", "RucasWsException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: services.set.gov.py.rucas.pagos
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RucasWsException }
     * 
     */
    public RucasWsException createRucasWsException() {
        return new RucasWsException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RucasWsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://py.gov.set.services/rucas/pagos", name = "RucasWsException")
    public JAXBElement<RucasWsException> createRucasWsException(RucasWsException value) {
        return new JAXBElement<RucasWsException>(_RucasWsException_QNAME, RucasWsException.class, null, value);
    }

}
