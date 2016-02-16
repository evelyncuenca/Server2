/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import java.util.Date;
import java.util.List;
import java.util.Map;
import py.com.konecta.redcobros.entities.Gestor;

/**
 *
 * @author konecta
 */
public interface GestorFacade extends GenericDao<Gestor, String> {
        public List<Map<String, Object>> getListGestor(Long idRecaudador, Long idSucursal,Date fechaDesde, Date fechaHasta);
}
