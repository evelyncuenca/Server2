/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;





import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.Formulario;

/**
 *
 * @author konecta
 */
@Remote
public interface FormularioFacade extends GenericDao<Formulario, Long>{

    public py.com.konecta.redcobros.entities.Formulario obtenerFormulario(java.lang.Integer numeroImpuesto, java.lang.String periodo) throws java.lang.Exception;
    public py.com.konecta.redcobros.entities.Formulario obtenerFormularioNumeroForm(java.lang.Integer numeroFormulario, java.lang.String periodo) throws java.lang.Exception;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertarFormularios(org.apache.commons.fileupload.FileItem bf, java.lang.String separadorCampos, int formatoFecha, java.lang.Integer numeroFormulario, java.lang.Integer version) throws java.lang.Exception;

}
