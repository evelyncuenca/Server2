/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;



import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.Campo;

/**
 *
 * @author konecta
 */
@Remote
public interface CampoFacade extends GenericDao<Campo, Long>{

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void capturarCampos(org.apache.commons.fileupload.FileItem bf, java.lang.String separadorCampos, java.lang.Integer numeroFormulario, java.lang.Integer version) throws java.lang.Exception;

}
