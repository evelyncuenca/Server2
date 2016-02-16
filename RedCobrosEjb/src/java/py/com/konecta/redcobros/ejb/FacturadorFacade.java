/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import java.util.List;
import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.Facturador;

/**
 *
 * @author konecta
 */
@Remote
public interface FacturadorFacade extends GenericDao<Facturador, Long> {
    public List<Facturador> getFacturadoresWithServicioRcHabilitados(String servicio);
}
