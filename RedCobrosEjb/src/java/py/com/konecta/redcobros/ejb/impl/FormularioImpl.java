/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
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
import py.com.konecta.redcobros.entities.Dominios;
import py.com.konecta.redcobros.entities.Formulario;
import py.com.konecta.redcobros.entities.FormularioImpuesto;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FormularioImpl extends GenericDaoImpl<Formulario, Long> implements FormularioFacade {

    @EJB
    private FormularioImpuestoFacade fiFacade;
    @EJB
    private FormularioFacade fFacade;
    @EJB
    private DominiosFacade dominiosFacade;
    @Resource
    private SessionContext context;

    @Override
    public Formulario obtenerFormulario(
            Integer numeroImpuesto, String periodo) throws Exception {
        FormularioImpuesto ejemploFI = new FormularioImpuesto();
        ejemploFI.setImpuesto(numeroImpuesto);
        List<FormularioImpuesto> listaFI = this.fiFacade.list(ejemploFI);
        List<Integer> listaNumeroFormulario = new ArrayList();
        for (FormularioImpuesto fi : listaFI) {
            listaNumeroFormulario.add(fi.getNumeroFormulario());
        }

        /*
         * String periodoD,periodoH; if (periodo.length()==4) {
         * periodoD="3112"+periodo; periodoH="0101"+periodo; } else if
         * (periodo.length()==6) { periodoD="31"+periodo; periodoH="01"+periodo;
         * } else { periodoD=""+periodo; periodoH=""+periodo;
        }
         */
        if (periodo.length() == 4) {
            periodo = "3112" + periodo;
        } else if (periodo.length() == 6) {
            int año = Integer.parseInt(periodo.substring(2));
            int mes = Integer.parseInt(periodo.substring(0, 2));
            if (mes == 2 && ParamVencimientosFacadeImpl.bisiesto(año)) {
                periodo = "29" + periodo;
            } else if (mes == 2) {
                periodo = "28" + periodo;
            } else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                periodo = "31" + periodo;
            } else {
                periodo = "30" + periodo;
            }

        }
        Date periodoDesde = new SimpleDateFormat("ddMMyyyy").parse(periodo);
        Date periodoHasta = new SimpleDateFormat("ddMMyyyy").parse(periodo);
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(Formulario.class);
        //c.add(Restrictions.eq("impuesto", numeroImpuesto));
        //periodo debe caer dentro de fechaDesde y fechaHasta
        //fechaDesde menor o igual a periodo
        c.add(Restrictions.le("fechaDesde", periodoDesde));
        //fechaHasta mayor o igual a periodo o fechaHasta es null
        c.add(Restrictions.or(
                Restrictions.and(
                Restrictions.isNotNull("fechaHasta"),
                Restrictions.ge("fechaHasta", periodoHasta)),
                Restrictions.isNull("fechaHasta")));
        c.add(Restrictions.in("numeroFormulario", listaNumeroFormulario));
        return (Formulario) c.list().get(0);

    }

    @Override
    public Formulario obtenerFormularioNumeroForm(
            Integer numeroForm, String periodo) throws Exception {
        Formulario ejemploF = new Formulario();
        ejemploF.setNumeroFormulario(numeroForm);
        List<Formulario> listaF = this.fFacade.list(ejemploF);
        List<Long> listaIdFormulario = new ArrayList();
        for (Formulario f : listaF) {
            listaIdFormulario.add(f.getIdFormulario());
        }

        /*
         * String periodoD,periodoH; if (periodo.length()==4) {
         * periodoD="3112"+periodo; periodoH="0101"+periodo; } else if
         * (periodo.length()==6) { periodoD="31"+periodo; periodoH="01"+periodo;
         * } else { periodoD=""+periodo; periodoH=""+periodo;
        }
         */
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = null;
        if (numeroForm != 90) {
            if (periodo.length() == 4) {
                periodo = "3112" + periodo;
            } else if (periodo.length() == 6) {
                int año = Integer.parseInt(periodo.substring(2));
                int mes = Integer.parseInt(periodo.substring(0, 2));
                if (mes == 2 && ParamVencimientosFacadeImpl.bisiesto(año)) {
                    periodo = "29" + periodo;
                } else if (mes == 2) {
                    periodo = "28" + periodo;
                } else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                    periodo = "31" + periodo;
                } else {
                    periodo = "30" + periodo;
                }

            }
            Date periodoDesde = new SimpleDateFormat("ddMMyyyy").parse(periodo);
            Date periodoHasta = new SimpleDateFormat("ddMMyyyy").parse(periodo);

            c = hem.getSession().createCriteria(Formulario.class);
            //c.add(Restrictions.eq("impuesto", numeroImpuesto));
            //periodo debe caer dentro de fechaDesde y fechaHasta
            //fechaDesde menor o igual a periodo
            c.add(Restrictions.le("fechaDesde", periodoDesde));
            //fechaHasta mayor o igual a periodo o fechaHasta es null
            c.add(Restrictions.or(
                    Restrictions.and(
                    Restrictions.isNotNull("fechaHasta"),
                    Restrictions.ge("fechaHasta", periodoHasta)),
                    Restrictions.isNull("fechaHasta")));
            c.add(Restrictions.in("idFormulario", listaIdFormulario));
        } else {
            c = hem.getSession().createCriteria(Formulario.class);
            c.add(Restrictions.in("idFormulario", listaIdFormulario));
            c.add(Restrictions.eq("version", 2));
        }
        List<Formulario> listForm = c.list();
        Formulario f = listForm != null && !listForm.isEmpty() ? (Formulario) listForm.get(0) : null;
        return f;

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertarFormularios(FileItem bf, String separadorCampos, int formatoFecha, Integer numeroFormulario, Integer version) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            InputStream uploadedStream = bf.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            for (String linea : lineas) {
                String[] token = LectorConfiguracionSet.tokensDeLaLinea(linea, separadorCampos);
                if (FORMULARIO_POS_FORMULARIO < token.length && FORMULARIO_POS_VERSION < token.length) {
                    if ((numeroFormulario == null && version == null)
                            || (Integer.parseInt(token[FORMULARIO_POS_FORMULARIO]) == numeroFormulario && Integer.parseInt(token[FORMULARIO_POS_VERSION]) == version)) {
                        Date fechaDesde, fechaHasta;
                        if (FORMULARIO_POS_FECHA_DESDE < token.length && token[FORMULARIO_POS_FECHA_DESDE].length() > 0) {
                            if (LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY == formatoFecha) {
                                fechaDesde = sdf.parse(LectorConfiguracionSet.DDMMYYYYtoFechaDDMMYYYY(token[FORMULARIO_POS_FECHA_DESDE]));
                            } else {
                                fechaDesde = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[FORMULARIO_POS_FECHA_DESDE]));
                            }

                        } else {
                            throw new Exception("Fecha desde es mandatorio:"
                                    + "formulario " + token[FORMULARIO_POS_FORMULARIO]
                                    + " version " + token[FORMULARIO_POS_VERSION]);
                        }
                        if (FORMULARIO_POS_FECHA_HASTA < token.length && token[FORMULARIO_POS_FECHA_HASTA].length() > 0) {
                            if (LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY == formatoFecha) {
                                fechaHasta = sdf.parse(LectorConfiguracionSet.DDMMYYYYtoFechaDDMMYYYY(token[FORMULARIO_POS_FECHA_HASTA]));
                            } else {
                                fechaHasta = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[FORMULARIO_POS_FECHA_HASTA]));
                            }
                        } else {
                            fechaHasta = null;
                        }
                        Integer versionDefault;
                        Dominios dominios;
                        Formulario formulario = new Formulario();
                        formulario.setNumeroFormulario(Integer.parseInt(token[FORMULARIO_POS_FORMULARIO]));
                        if (this.total(formulario) >= 1) {
                            versionDefault = 2;
                        } else {
                            versionDefault = 1;
                        }
                        formulario.setVersion(Integer.parseInt(token[FORMULARIO_POS_VERSION]));
                        if (this.total(formulario) == 0) {
                            formulario.setDescripcion(token[FORMULARIO_POS_DESCRIPCION]);
                            formulario.setFechaDesde(fechaDesde);
                            formulario.setFechaHasta(fechaHasta);
                            formulario.setCampoPagar(Integer.parseInt(token[FORMULARIO_POS_CAMPO_PAGAR]));
                            formulario.setVersionDefault(versionDefault);
                            formulario = this.merge(formulario);
                        } else {
                            formulario = this.get(formulario);
                            formulario.setFechaDesde(fechaDesde);
                            formulario.setFechaHasta(fechaHasta);
                            formulario.setCampoPagar(Integer.parseInt(token[FORMULARIO_POS_CAMPO_PAGAR]));
                            this.update(formulario);
                            dominios = new Dominios();
                            dominios.setTipo("FECHA");
                            dominios.setFormulario(formulario);
                            this.dominiosFacade.delete(this.dominiosFacade.get(dominios));
                        }
                        dominios = new Dominios();
                        dominios.setEtiquetaCabecera("C4");
                        dominios.setEtiquetaDetalle("PERIODOFISCAL");
                        if (FORMULARIO_POS_FORMATO_PERIODO_FISCAL >= token.length) {
                            throw new Exception("Formato de periodo fiscal es mandatorio:"
                                    + " formulario " + token[FORMULARIO_POS_FORMULARIO]
                                    + " version " + token[FORMULARIO_POS_VERSION]);
                        }
                        dominios.setFormato(token[FORMULARIO_POS_FORMATO_PERIODO_FISCAL]);
                        dominios.setFormulario(formulario);
                        dominios.setTipo("FECHA");
                        this.dominiosFacade.save(dominios);
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
    private static int FORMULARIO_POS_FORMULARIO = 0;
    private static int FORMULARIO_POS_DESCRIPCION = 1;
    private static int FORMULARIO_POS_VERSION = 3;
    private static int FORMULARIO_POS_FECHA_DESDE = 4;
    private static int FORMULARIO_POS_FECHA_HASTA = 5;
    private static int FORMULARIO_POS_CAMPO_PAGAR = 6;
    private static int FORMULARIO_POS_FORMATO_PERIODO_FISCAL = 7;
}
