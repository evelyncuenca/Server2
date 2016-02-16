/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ws.handler;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author ystmiog
 */
public class MessageHandler implements SOAPHandler<SOAPMessageContext> {

   
    public boolean handleMessage(SOAPMessageContext messageContext) {
        //SOAPMessage msg = messageContext.getMessage();
        dumpSOAPMessage(messageContext);
        return true;
    }

    public Set<QName> getHeaders() {
        java.util.Set<QName> qnames = Collections.emptySet();
        return qnames;
    }

    public boolean handleFault(SOAPMessageContext messageContext) {
        dumpSOAPMessage(messageContext);
        return false;
    }

    public void close(MessageContext context) {
    }
    private void dumpSOAPMessage(SOAPMessageContext smc) {
        SOAPMessage msg = smc.getMessage();
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        System.out.println("--------------------");
        System.out.println(outboundProperty.booleanValue() ? "Outbound message" : "Inbound message");
        System.out.println("--------------------");
        
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            msg.writeTo(baos);
            System.out.println(baos.toString(getMessageEncoding(msg)));
            //String values = msg.getSOAPBody().getTextContent();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String getMessageEncoding(SOAPMessage msg) throws SOAPException {
        String encoding = "utf-8";

        if (msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING) != null) {
            encoding = msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING).toString();
        }

        return encoding;
    }

}
