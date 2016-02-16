/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.utiles;

/**
 *
 * @author documenta
 */
public class ExceptionExpiredSession extends Exception{
    public ExceptionExpiredSession(String msg) {
        super(msg);
    }
}
