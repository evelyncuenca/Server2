/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;
import javax.ejb.Local;
import py.com.konecta.redcobros.entities.AliasServicioFacturador;
import py.com.konecta.redcobros.entities.AliasServicioFacturadorPK;

/**
 *
 * @author konecta
 */
@Local
public interface AliasServicioFacturadorFacade extends GenericDao<AliasServicioFacturador, AliasServicioFacturadorPK>{

}

