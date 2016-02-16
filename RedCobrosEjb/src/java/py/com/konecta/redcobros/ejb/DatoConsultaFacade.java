/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.DatoConsulta;
import py.com.konecta.redcobros.entities.DatoConsultaPK;

/**
 *
 * @author konecta
 */
@Local
public interface DatoConsultaFacade extends GenericDao<DatoConsulta, DatoConsultaPK> {

    public Integer insertarDatoConsulta(String linea, java.lang.String separadorCampos) throws java.lang.Exception;
}
