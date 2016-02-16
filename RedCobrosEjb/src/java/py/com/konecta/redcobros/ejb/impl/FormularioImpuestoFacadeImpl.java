/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import py.com.konecta.redcobros.ejb.FormularioImpuestoFacade;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.commons.fileupload.FileItem;
import py.com.konecta.redcobros.entities.FormularioImpuesto;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FormularioImpuestoFacadeImpl extends GenericDaoImpl<FormularioImpuesto, Long> implements FormularioImpuestoFacade {

    @Resource
    private SessionContext context;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertarFormularioImpuesto(FileItem bf, String separadorCampos, Integer numeroFormulario) throws Exception {
        try {
            InputStream uploadedStream = bf.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            for (String linea : lineas) {
                String[] token = LectorConfiguracionSet.tokensDeLaLinea(linea, separadorCampos);
                if (FORM_IMP_POS_FORMULARIO < token.length) {
                    if ((numeroFormulario==null)||
                            (Integer.parseInt(token[FORM_IMP_POS_FORMULARIO]) == numeroFormulario)) {
                        Integer impuesto;
                        if (FORM_IMP_POS_IMPUESTO < token.length && token[FORM_IMP_POS_IMPUESTO].length() >= 0) {
                            impuesto = Integer.parseInt(token[FORM_IMP_POS_IMPUESTO]);
                        } else {
                            throw new Exception("No contiene numero de impuesto");
                        }
                        String obligacion = null;
                        if (FORM_IMP_POS_OBLIGACION < token.length && token[FORM_IMP_POS_OBLIGACION].length() >= 0) {
                            obligacion = token[FORM_IMP_POS_OBLIGACION];
                        }
                        FormularioImpuesto fi=new FormularioImpuesto();
                        fi.setImpuesto(impuesto);
                        fi.setNumeroFormulario(Integer.parseInt(token[FORM_IMP_POS_FORMULARIO]));
                        if (this.total(fi)==0) {
                            fi.setObligacion(obligacion);
                            this.save(fi);
                        } else {
                            fi=this.get(fi);
                            fi.setObligacion(obligacion);
                            this.update(fi);
                        }

                    }
                }
            }
        } catch (Exception e) {
            //abortar transaccion
            context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
    }
    private static int FORM_IMP_POS_FORMULARIO = 0;
    private static int FORM_IMP_POS_IMPUESTO = 3;
    private static int FORM_IMP_POS_OBLIGACION = 2;
}
