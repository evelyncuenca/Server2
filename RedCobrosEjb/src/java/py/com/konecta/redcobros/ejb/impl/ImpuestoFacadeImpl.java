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
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.commons.fileupload.FileItem;
import py.com.konecta.redcobros.ejb.ImpuestoFacade;
import py.com.konecta.redcobros.entities.Impuesto;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author Kiki
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImpuestoFacadeImpl extends GenericDaoImpl<Impuesto, Long> implements ImpuestoFacade {

    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertarImpueso(FileItem bf, String separadorCampos, Integer numeroImpuesto) throws Exception {
        try {

            InputStream uploadedStream = bf.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            for (String linea : lineas) {
                String[] token = LectorConfiguracionSet.tokensDeLaLinea(linea, separadorCampos);
                if (IMPUESTO_POS_IMPUESTO < token.length) {
                    if (numeroImpuesto == null ||
                            Integer.parseInt(token[IMPUESTO_POS_IMPUESTO]) == numeroImpuesto) {
                        Impuesto i = new Impuesto();
                        i.setNumero(Integer.parseInt(token[IMPUESTO_POS_IMPUESTO]));
                        if (this.total(i) == 0) {
                            i.setNombre(token[IMPUESTO_POS_NOMBRE]);
                            i.setSigla(token[IMPUESTO_POS_SIGLA]);
                            i.setTipoAtributo(token[IMPUESTO_POS_TIPO_ATRIBUTO]);
                            this.save(i);
                        } else {
                            i = this.get(i);
                            i.setNombre(token[IMPUESTO_POS_NOMBRE]);
                            i.setSigla(token[IMPUESTO_POS_SIGLA]);
                            i.setTipoAtributo(token[IMPUESTO_POS_TIPO_ATRIBUTO]);
                            this.update(i);
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
    private static int IMPUESTO_POS_IMPUESTO = 0;
    private static int IMPUESTO_POS_NOMBRE = 1;
    private static int IMPUESTO_POS_SIGLA = 2;
    private static int IMPUESTO_POS_TIPO_ATRIBUTO = 3;
}
