/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Local;
import py.com.konecta.redcobros.entities.Contribuyentes;

/**
 *
 * @author konecta
 */
@Local
public interface ContribuyentesFacade extends GenericDao<Contribuyentes, Long>{

    public py.com.konecta.redcobros.entities.Contribuyentes getContribuyentePorRuc(java.lang.String rucNuevo, java.lang.String rucAnterior, java.lang.String digitoVerificador);


    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void actualizarContribuyentes(org.apache.commons.fileupload.FileItem bf, java.lang.String separador) throws java.lang.Exception;

}
