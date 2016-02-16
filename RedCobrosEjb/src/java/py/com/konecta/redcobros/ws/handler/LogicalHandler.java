/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ws.handler;


import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerFactory;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author ystmiog
 */
public class LogicalHandler implements javax.xml.ws.handler.LogicalHandler<LogicalMessageContext>{

    public boolean handleMessage(LogicalMessageContext messageContext) {
        //LogicalMessage msg = messageContext.getMessage();
        dumpSOAPMessage(messageContext);

        return true;
    }

    public boolean handleFault(LogicalMessageContext messageContext) {
        return true;
    }

    public void close(MessageContext context) {
    }

    private void dumpSOAPMessage(LogicalMessageContext smc) {
        LogicalMessage msg = smc.getMessage();

        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        Logger.getLogger(LogicalHandler.class.getName()).log(Level.INFO, "--------------------");
        Logger.getLogger(LogicalHandler.class.getName()).log(Level.INFO, outboundProperty.booleanValue() ? "Outbound message" : "Inbound message");
        Logger.getLogger(LogicalHandler.class.getName()).log(Level.INFO, "--------------------");
        try {

            TransformerFactory tfactory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer xform = tfactory.newTransformer();

            StringWriter writer = new StringWriter();
            javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(writer);

            xform.transform(msg.getPayload(), result);
            
            Logger.getLogger(LogicalHandler.class.getName()).log(Level.INFO, writer.toString());

        } catch (Exception e) {
            Logger.getLogger(LogicalHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

   
}
