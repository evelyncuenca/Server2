/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.ParamTasas;

/**
 *
 * @author konecta
 */
@Remote
public interface ParamTasasFacade extends GenericDao<ParamTasas, Long> {

    public java.lang.Double obtenerValor(java.lang.Integer codigo);

    public java.lang.Double obtenerTasa(java.lang.Integer formulario, java.lang.Integer campo, java.lang.String periodo, java.lang.Integer version) throws java.lang.Exception;


    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertarTasasFormulario(org.apache.commons.fileupload.FileItem bf, java.lang.String separadorCampos, int formatoFecha, java.lang.Integer numeroFormulario) throws java.lang.Exception;


}
