
package services.set.gov.py.rucas.pagos;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.5-b04 
 * Generated source version: 2.1
 * 
 */
@WebService(name = "PagoElectronicoManagement", targetNamespace = "http://py.gov.set.services/rucas/pagos")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    py.gov.set.rucas.exception.jaws.ObjectFactory.class,
    services.set.gov.py.rucas.pagos.ObjectFactory.class
})
public interface PagoElectronicoManagement {


    /**
     * 
     * @param nroBoleta
     * @param sessionId
     * @param ruc
     * @param fechaConstitucion
     * @return
     *     returns java.lang.String
     * @throws RucasWsException_Exception
     */
    @WebMethod
    @WebResult(name = "result", partName = "result")
    public String consultarDeuda(
        @WebParam(name = "sessionId", partName = "sessionId")
        String sessionId,
        @WebParam(name = "ruc", partName = "ruc")
        String ruc,
        @WebParam(name = "fechaConstitucion", partName = "fechaConstitucion")
        String fechaConstitucion,
        @WebParam(name = "nroBoleta", partName = "nroBoleta")
        String nroBoleta)
        throws RucasWsException_Exception
    ;

    /**
     * 
     * @param xmlPago
     * @param sessionId
     * @param nroHash
     * @return
     *     returns java.lang.String
     * @throws RucasWsException_Exception
     */
    @WebMethod
    @WebResult(name = "result", partName = "result")
    public String consultarOT(
        @WebParam(name = "sessionId", partName = "sessionId")
        String sessionId,
        @WebParam(name = "xmlPago", partName = "xmlPago")
        String xmlPago,
        @WebParam(name = "nroHash", partName = "nroHash")
        String nroHash)
        throws RucasWsException_Exception
    ;

    /**
     * 
     * @param xmlPago
     * @param sessionId
     * @param nroHash
     * @return
     *     returns java.lang.String
     * @throws RucasWsException_Exception
     */
    @WebMethod
    @WebResult(name = "result", partName = "result")
    public String consultarPago(
        @WebParam(name = "sessionId", partName = "sessionId")
        String sessionId,
        @WebParam(name = "xmlPago", partName = "xmlPago")
        String xmlPago,
        @WebParam(name = "nroHash", partName = "nroHash")
        String nroHash)
        throws RucasWsException_Exception
    ;

    /**
     * 
     * @param xmlPago
     * @param sessionId
     * @param nroHash
     * @return
     *     returns java.lang.String
     * @throws RucasWsException_Exception
     */
    @WebMethod
    @WebResult(name = "result", partName = "result")
    public String guardarOT(
        @WebParam(name = "sessionId", partName = "sessionId")
        String sessionId,
        @WebParam(name = "xmlPago", partName = "xmlPago")
        String xmlPago,
        @WebParam(name = "nroHash", partName = "nroHash")
        String nroHash)
        throws RucasWsException_Exception
    ;

    /**
     * 
     * @param sessionId
     * @param xml
     * @param nroHash
     * @return
     *     returns java.lang.String
     * @throws RucasWsException_Exception
     */
    @WebMethod
    @WebResult(name = "result", partName = "result")
    public String guardarPago(
        @WebParam(name = "sessionId", partName = "sessionId")
        String sessionId,
        @WebParam(name = "xml", partName = "xml")
        String xml,
        @WebParam(name = "nroHash", partName = "nroHash")
        String nroHash)
        throws RucasWsException_Exception
    ;

}