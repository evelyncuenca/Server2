/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.ParamVencimientos;

/**
 *
 * @author konecta
 */
@Remote
public interface ParamVencimientosFacade extends GenericDao<ParamVencimientos, Long> {

    public py.com.konecta.redcobros.entities.ParamVencimientos obtenerParamVencimiento(java.lang.Integer numeroImpuesto, java.lang.String periodo, py.com.konecta.redcobros.entities.Contribuyentes cont) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertarVencimientos(org.apache.commons.fileupload.FileItem bf, java.lang.String separadorCampos, int formatoFecha, java.lang.Integer numeroImpuesto) throws java.lang.Exception;

}
