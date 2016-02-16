
package py.com.konecta.accounteracti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the py.com.konecta.accounteracti package. 
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

    private final static QName _CrearCuenta_QNAME = new QName("http://accounteracti.konecta.com.py/", "crearCuenta");
    private final static QName _ObtenerDatosClienteResponse_QNAME = new QName("http://accounteracti.konecta.com.py/", "obtenerDatosClienteResponse");
    private final static QName _ActivarCliente_QNAME = new QName("http://accounteracti.konecta.com.py/", "activarCliente");
    private final static QName _CrearCuentaResponse_QNAME = new QName("http://accounteracti.konecta.com.py/", "crearCuentaResponse");
    private final static QName _ValidarExisteCuenta_QNAME = new QName("http://accounteracti.konecta.com.py/", "validarExisteCuenta");
    private final static QName _ValidarExisteCuentaResponse_QNAME = new QName("http://accounteracti.konecta.com.py/", "validarExisteCuentaResponse");
    private final static QName _ObtenerCodTipoRestriccion_QNAME = new QName("http://accounteracti.konecta.com.py/", "obtenerCodTipoRestriccion");
    private final static QName _ObtenerCodTipoRestriccionResponse_QNAME = new QName("http://accounteracti.konecta.com.py/", "obtenerCodTipoRestriccionResponse");
    private final static QName _ObtenerDatosCliente_QNAME = new QName("http://accounteracti.konecta.com.py/", "obtenerDatosCliente");
    private final static QName _ActivarClienteResponse_QNAME = new QName("http://accounteracti.konecta.com.py/", "activarClienteResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: py.com.konecta.accounteracti
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ActivarClienteResponse }
     * 
     */
    public ActivarClienteResponse createActivarClienteResponse() {
        return new ActivarClienteResponse();
    }

    /**
     * Create an instance of {@link ObtenerCodTipoRestriccionResponse }
     * 
     */
    public ObtenerCodTipoRestriccionResponse createObtenerCodTipoRestriccionResponse() {
        return new ObtenerCodTipoRestriccionResponse();
    }

    /**
     * Create an instance of {@link ObtenerDatosCliente }
     * 
     */
    public ObtenerDatosCliente createObtenerDatosCliente() {
        return new ObtenerDatosCliente();
    }

    /**
     * Create an instance of {@link ObtenerCodTipoRestriccion }
     * 
     */
    public ObtenerCodTipoRestriccion createObtenerCodTipoRestriccion() {
        return new ObtenerCodTipoRestriccion();
    }

    /**
     * Create an instance of {@link ActivarCliente }
     * 
     */
    public ActivarCliente createActivarCliente() {
        return new ActivarCliente();
    }

    /**
     * Create an instance of {@link CrearCuentaResponse }
     * 
     */
    public CrearCuentaResponse createCrearCuentaResponse() {
        return new CrearCuentaResponse();
    }

    /**
     * Create an instance of {@link ValidarExisteCuenta }
     * 
     */
    public ValidarExisteCuenta createValidarExisteCuenta() {
        return new ValidarExisteCuenta();
    }

    /**
     * Create an instance of {@link ValidarExisteCuentaResponse }
     * 
     */
    public ValidarExisteCuentaResponse createValidarExisteCuentaResponse() {
        return new ValidarExisteCuentaResponse();
    }

    /**
     * Create an instance of {@link ObtenerDatosClienteResponse }
     * 
     */
    public ObtenerDatosClienteResponse createObtenerDatosClienteResponse() {
        return new ObtenerDatosClienteResponse();
    }

    /**
     * Create an instance of {@link CrearCuenta }
     * 
     */
    public CrearCuenta createCrearCuenta() {
        return new CrearCuenta();
    }

    /**
     * Create an instance of {@link ResponseCliente }
     * 
     */
    public ResponseCliente createResponseCliente() {
        return new ResponseCliente();
    }

    /**
     * Create an instance of {@link Pojo }
     * 
     */
    public Pojo createPojo() {
        return new Pojo();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link ResponseRestriccion }
     * 
     */
    public ResponseRestriccion createResponseRestriccion() {
        return new ResponseRestriccion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrearCuenta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "crearCuenta")
    public JAXBElement<CrearCuenta> createCrearCuenta(CrearCuenta value) {
        return new JAXBElement<CrearCuenta>(_CrearCuenta_QNAME, CrearCuenta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDatosClienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "obtenerDatosClienteResponse")
    public JAXBElement<ObtenerDatosClienteResponse> createObtenerDatosClienteResponse(ObtenerDatosClienteResponse value) {
        return new JAXBElement<ObtenerDatosClienteResponse>(_ObtenerDatosClienteResponse_QNAME, ObtenerDatosClienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActivarCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "activarCliente")
    public JAXBElement<ActivarCliente> createActivarCliente(ActivarCliente value) {
        return new JAXBElement<ActivarCliente>(_ActivarCliente_QNAME, ActivarCliente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrearCuentaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "crearCuentaResponse")
    public JAXBElement<CrearCuentaResponse> createCrearCuentaResponse(CrearCuentaResponse value) {
        return new JAXBElement<CrearCuentaResponse>(_CrearCuentaResponse_QNAME, CrearCuentaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarExisteCuenta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "validarExisteCuenta")
    public JAXBElement<ValidarExisteCuenta> createValidarExisteCuenta(ValidarExisteCuenta value) {
        return new JAXBElement<ValidarExisteCuenta>(_ValidarExisteCuenta_QNAME, ValidarExisteCuenta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarExisteCuentaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "validarExisteCuentaResponse")
    public JAXBElement<ValidarExisteCuentaResponse> createValidarExisteCuentaResponse(ValidarExisteCuentaResponse value) {
        return new JAXBElement<ValidarExisteCuentaResponse>(_ValidarExisteCuentaResponse_QNAME, ValidarExisteCuentaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCodTipoRestriccion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "obtenerCodTipoRestriccion")
    public JAXBElement<ObtenerCodTipoRestriccion> createObtenerCodTipoRestriccion(ObtenerCodTipoRestriccion value) {
        return new JAXBElement<ObtenerCodTipoRestriccion>(_ObtenerCodTipoRestriccion_QNAME, ObtenerCodTipoRestriccion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCodTipoRestriccionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "obtenerCodTipoRestriccionResponse")
    public JAXBElement<ObtenerCodTipoRestriccionResponse> createObtenerCodTipoRestriccionResponse(ObtenerCodTipoRestriccionResponse value) {
        return new JAXBElement<ObtenerCodTipoRestriccionResponse>(_ObtenerCodTipoRestriccionResponse_QNAME, ObtenerCodTipoRestriccionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDatosCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "obtenerDatosCliente")
    public JAXBElement<ObtenerDatosCliente> createObtenerDatosCliente(ObtenerDatosCliente value) {
        return new JAXBElement<ObtenerDatosCliente>(_ObtenerDatosCliente_QNAME, ObtenerDatosCliente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActivarClienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accounteracti.konecta.com.py/", name = "activarClienteResponse")
    public JAXBElement<ActivarClienteResponse> createActivarClienteResponse(ActivarClienteResponse value) {
        return new JAXBElement<ActivarClienteResponse>(_ActivarClienteResponse_QNAME, ActivarClienteResponse.class, null, value);
    }

}
