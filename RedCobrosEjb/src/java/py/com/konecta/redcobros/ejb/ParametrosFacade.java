/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb;

import javax.ejb.Remote;
import py.com.konecta.redcobros.entities.Parametros;

/**
 *
 * @author konecta
 */
@Remote
public interface ParametrosFacade extends GenericDao<Parametros, Long> {

    public java.lang.Double obtenerMulta(java.lang.Integer impuesto, java.lang.Double monto);

    public java.lang.Double obtenerMora(java.lang.Double monto, java.lang.Integer mesesAtraso);

    public java.lang.Double obtenerInteres(java.lang.Double monto, java.lang.Integer diasAtraso);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertarTasasInteresesContravencionesMultas(org.apache.commons.fileupload.FileItem bf, java.lang.String separadorCampos, int formatoFecha) throws java.lang.Exception;

}
