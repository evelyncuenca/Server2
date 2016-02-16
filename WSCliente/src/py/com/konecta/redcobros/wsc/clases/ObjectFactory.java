
package py.com.konecta.redcobros.wsc.clases;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the py.com.konecta.redcobros.ejb.impl package. 
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

    private final static QName _Exception_QNAME = new QName("http://impl.ejb.redcobros.konecta.com.py/", "Exception");
    private final static QName _GetProximoRangoOrden_QNAME = new QName("http://impl.ejb.redcobros.konecta.com.py/", "getProximoRangoOrden");
    private final static QName _GetProximoRangoOrdenResponse_QNAME = new QName("http://impl.ejb.redcobros.konecta.com.py/", "getProximoRangoOrdenResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: py.com.konecta.redcobros.ejb.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetProximoRangoOrdenResponse }
     * 
     */
    public GetProximoRangoOrdenResponse createGetProximoRangoOrdenResponse() {
        return new GetProximoRangoOrdenResponse();
    }

    /**
     * Create an instance of {@link GetProximoRangoOrden }
     * 
     */
    public GetProximoRangoOrden createGetProximoRangoOrden() {
        return new GetProximoRangoOrden();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ejb.redcobros.konecta.com.py/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProximoRangoOrden }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ejb.redcobros.konecta.com.py/", name = "getProximoRangoOrden")
    public JAXBElement<GetProximoRangoOrden> createGetProximoRangoOrden(GetProximoRangoOrden value) {
        return new JAXBElement<GetProximoRangoOrden>(_GetProximoRangoOrden_QNAME, GetProximoRangoOrden.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProximoRangoOrdenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ejb.redcobros.konecta.com.py/", name = "getProximoRangoOrdenResponse")
    public JAXBElement<GetProximoRangoOrdenResponse> createGetProximoRangoOrdenResponse(GetProximoRangoOrdenResponse value) {
        return new JAXBElement<GetProximoRangoOrdenResponse>(_GetProximoRangoOrdenResponse_QNAME, GetProximoRangoOrdenResponse.class, null, value);
    }

}
