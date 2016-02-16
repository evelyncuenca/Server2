
package py.com.documenta.ws.set.contrib;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the py.com.documenta.ws.set.contrib package. 
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

    private final static QName _ConsultaResponse_QNAME = new QName("http://contrib.ws.daemon.documenta.com.py/", "consultaResponse");
    private final static QName _MergeContrib_QNAME = new QName("http://contrib.ws.daemon.documenta.com.py/", "mergeContrib");
    private final static QName _MergeContribResponse_QNAME = new QName("http://contrib.ws.daemon.documenta.com.py/", "mergeContribResponse");
    private final static QName _Consulta_QNAME = new QName("http://contrib.ws.daemon.documenta.com.py/", "consulta");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: py.com.documenta.ws.set.contrib
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MergeContrib }
     * 
     */
    public MergeContrib createMergeContrib() {
        return new MergeContrib();
    }

    /**
     * Create an instance of {@link ConsultaResponse }
     * 
     */
    public ConsultaResponse createConsultaResponse() {
        return new ConsultaResponse();
    }

    /**
     * Create an instance of {@link MergeContribResponse }
     * 
     */
    public MergeContribResponse createMergeContribResponse() {
        return new MergeContribResponse();
    }

    /**
     * Create an instance of {@link Consulta }
     * 
     */
    public Consulta createConsulta() {
        return new Consulta();
    }

    /**
     * Create an instance of {@link Contribuyente }
     * 
     */
    public Contribuyente createContribuyente() {
        return new Contribuyente();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://contrib.ws.daemon.documenta.com.py/", name = "consultaResponse")
    public JAXBElement<ConsultaResponse> createConsultaResponse(ConsultaResponse value) {
        return new JAXBElement<ConsultaResponse>(_ConsultaResponse_QNAME, ConsultaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MergeContrib }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://contrib.ws.daemon.documenta.com.py/", name = "mergeContrib")
    public JAXBElement<MergeContrib> createMergeContrib(MergeContrib value) {
        return new JAXBElement<MergeContrib>(_MergeContrib_QNAME, MergeContrib.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MergeContribResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://contrib.ws.daemon.documenta.com.py/", name = "mergeContribResponse")
    public JAXBElement<MergeContribResponse> createMergeContribResponse(MergeContribResponse value) {
        return new JAXBElement<MergeContribResponse>(_MergeContribResponse_QNAME, MergeContribResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Consulta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://contrib.ws.daemon.documenta.com.py/", name = "consulta")
    public JAXBElement<Consulta> createConsulta(Consulta value) {
        return new JAXBElement<Consulta>(_Consulta_QNAME, Consulta.class, null, value);
    }

}
