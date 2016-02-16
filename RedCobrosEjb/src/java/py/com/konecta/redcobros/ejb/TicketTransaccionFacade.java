/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.ejb.impl.GenericDaoImpl;
import py.com.konecta.redcobros.entities.TicketTransaccion;
import py.com.konecta.redcobros.entities.TicketTransaccionPK;

/**
 *
 * @author brojas
 */
@Local
public interface TicketTransaccionFacade{

    public byte[] generarTicketTransaccion(Long idTransaccion);

   // public String solicitarTransaccion(Long idTransaccion);
    
}
