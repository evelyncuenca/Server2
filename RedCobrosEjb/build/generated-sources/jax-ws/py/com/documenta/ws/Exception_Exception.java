
package py.com.documenta.ws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.5-b04 
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "Exception", targetNamespace = "http://ws.documenta.com.py/")
public class Exception_Exception
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private py.com.documenta.ws.Exception faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public Exception_Exception(String message, py.com.documenta.ws.Exception faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public Exception_Exception(String message, py.com.documenta.ws.Exception faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: py.com.documenta.ws.Exception
     */
    public py.com.documenta.ws.Exception getFaultInfo() {
        return faultInfo;
    }

}
