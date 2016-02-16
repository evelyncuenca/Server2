/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.Impuesto;

/**
 *
 * @author konecta
 */
@Remote
public interface ImpuestoFacade extends GenericDao<Impuesto, Long> {


    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertarImpueso(org.apache.commons.fileupload.FileItem bf, java.lang.String separadorCampos, java.lang.Integer numeroImpuesto) throws java.lang.Exception;
}
