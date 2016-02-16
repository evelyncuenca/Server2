/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.FormularioImpuesto;

/**
 *
 * @author konecta
 */
@Remote
public interface FormularioImpuestoFacade  extends GenericDao <FormularioImpuesto, Long> {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertarFormularioImpuesto(org.apache.commons.fileupload.FileItem bf, java.lang.String separadorCampos, java.lang.Integer numeroFormulario) throws java.lang.Exception;

}
