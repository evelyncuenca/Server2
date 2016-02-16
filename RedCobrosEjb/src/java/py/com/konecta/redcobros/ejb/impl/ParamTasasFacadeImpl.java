/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.commons.fileupload.FileItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.ParamTasas;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ParamTasasFacadeImpl extends GenericDaoImpl<ParamTasas, Long> implements ParamTasasFacade {
    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertarTasasFormulario(FileItem bf, String separadorCampos, int formatoFecha, Integer numeroFormulario) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            InputStream uploadedStream = bf.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            for (String linea : lineas) {
                String[] token = LectorConfiguracionSet.tokensDeLaLinea(linea, separadorCampos);
                if (TASAS_FORM_POS_FORMULARIO < token.length) {
                    if (numeroFormulario==null
                            ||Integer.parseInt(token[TASAS_FORM_POS_FORMULARIO]) == numeroFormulario) {
                        Date fechaDesde, fechaHasta;
                        if (TASAS_FORM_POS_FECHA_DESDE < token.length && token[TASAS_FORM_POS_FECHA_DESDE].length() > 0) {
                            if (LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY==formatoFecha) {
                                fechaDesde = sdf.parse(LectorConfiguracionSet.DDMMYYYYtoFechaDDMMYYYY(token[TASAS_FORM_POS_FECHA_DESDE]));
                            } else {
                                fechaDesde = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[TASAS_FORM_POS_FECHA_DESDE]));
                            }
                            
                        } else {
                            throw new Exception("Fecha desde es mandatorio:" +
                                    "formulario " + token[TASAS_FORM_POS_FORMULARIO] +
                                    " concepto " + token[TASAS_FORM_POS_CONCEPTO]);
                        }
                        if (TASAS_FORM_POS_FECHA_HASTA < token.length && token[TASAS_FORM_POS_FECHA_HASTA].length() > 0) {
                            if (LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY==formatoFecha) {
                                fechaHasta = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[TASAS_FORM_POS_FECHA_HASTA]));
                            } else {
                                fechaHasta = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[TASAS_FORM_POS_FECHA_HASTA]));
                            }                            
                        } else {
                            fechaHasta = null;
                        }

                        ParamTasas pt=new ParamTasas();
                        pt.setFormulario(Integer.parseInt(token[TASAS_FORM_POS_FORMULARIO]));
                        pt.setConceptoCampo(Integer.parseInt(token[TASAS_FORM_POS_CONCEPTO]));
                        pt.setFechaDesde(fechaDesde);
                        String descripcion=TASAS_FORM_POS_DESCRIPCION<token.length?
                            token[TASAS_FORM_POS_DESCRIPCION]:"";
                        if (this.total(pt)==0) {
                            pt.setDescripcion(descripcion);
                            pt.setTasaEfectiva(Double.parseDouble(token[TASAS_FORM_POS_TASA_EFECTIVA].replaceAll(",", ".")));
                            pt.setFechaHasta(fechaHasta);
                            this.save(pt);
                        } else {
                            pt=this.get(pt);
                            pt.setDescripcion(descripcion);
                            pt.setTasaEfectiva(Double.parseDouble(token[TASAS_FORM_POS_TASA_EFECTIVA].replaceAll(",", ".")));
                            pt.setFechaHasta(fechaHasta);
                            this.update(pt);
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

    public Double obtenerValor (Integer codigo) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c=hem.getSession().createCriteria(ParamTasas.class);
        c.add(Restrictions.eq("conceptoCampo", codigo));
        c.add(Restrictions.or(
                Restrictions.isNull("formulario"),
                Restrictions.eq("formulario",0)));
        return ((ParamTasas)c.list().get(0)).getTasaEfectiva();
    }

    public Double obtenerTasa (Integer formulario,
            Integer campo,
            String periodo,
            Integer version) throws Exception {
        String periodoD,periodoH;
        Date periodoDesde, periodoHasta;

       if (periodo != null && !periodo.isEmpty()){
           if (periodo.length()==4) {
            periodoD=periodo+"1231";
            periodoH=periodo+"0101";
        } else if (periodo.length()==6) {
            periodoD=periodo+"31";
            periodoH=periodo+"01";
        } else {
            periodoD=""+periodo;
            periodoH=""+periodo;
        }

        periodoDesde=new SimpleDateFormat("yyyyMMdd").parse(periodoD);
        periodoHasta=new SimpleDateFormat("yyyyMMdd").parse(periodoH);
       }
       else {
             periodoDesde = periodoHasta = new Date();
       }
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c=hem.getSession().createCriteria(ParamTasas.class);
        c.add(Restrictions.eq("formulario", formulario));
        c.add(Restrictions.eq("conceptoCampo", campo));
        //periodo debe caer dentro de fechaDesde y fechaHasta
        //fechaDesde menor o igual a periodo
        c.add(Restrictions.le("fechaDesde", periodoDesde));
        //fechaHasta mayor o igual a periodo o fechaHasta es null
        c.add(Restrictions.or(
                Restrictions.and(
                    Restrictions.isNotNull("fechaHasta"),
                    Restrictions.ge("fechaHasta", periodoHasta)
                ),
                Restrictions.isNull("fechaHasta")));

        try {
            return ((ParamTasas)c.uniqueResult()).getTasaEfectiva();
        } catch (Exception ex) {
            return 0.0;
        }
        
    }
    private static int TASAS_FORM_POS_FORMULARIO = 0;
    private static int TASAS_FORM_POS_CONCEPTO = 1;
    private static int TASAS_FORM_POS_DESCRIPCION = 5;
    private static int TASAS_FORM_POS_FECHA_DESDE = 2;
    private static int TASAS_FORM_POS_FECHA_HASTA = 3;
    private static int TASAS_FORM_POS_TASA_EFECTIVA = 4;
}
