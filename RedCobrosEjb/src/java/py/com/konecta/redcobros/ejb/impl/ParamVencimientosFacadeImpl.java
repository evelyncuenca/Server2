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
import py.com.konecta.redcobros.entities.Contribuyentes;
import py.com.konecta.redcobros.entities.ParamVencimientos;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ParamVencimientosFacadeImpl extends GenericDaoImpl<ParamVencimientos, Long> implements ParamVencimientosFacade {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    /*
     * public ParamVencimientos obtenerParamVencimiento( Integer
     * numeroImpuesto,String periodo, Contribuyentes cont) throws Exception {
     * String periodoD,periodoH; if (periodo.length()==4) {
     * periodoD="3112"+periodo; periodoH="0101"+periodo; } else if
     * (periodo.length()==6) { periodoD="31"+periodo; periodoH="01"+periodo; }
     * else { periodoD=""+periodo; periodoH=""+periodo; } Date periodoDesde=new
     * SimpleDateFormat("ddMMyyyy").parse(periodoD); Date periodoHasta=new
     * SimpleDateFormat("ddMMyyyy").parse(periodoH); HibernateEntityManager hem
     * = (HibernateEntityManager) this.getEm().getDelegate(); Criteria
     * c=hem.getSession().createCriteria(ParamVencimientos.class);
     * c.add(Restrictions.eq("impuesto", numeroImpuesto)); //periodo debe caer
     * dentro de fechaDesde y fechaHasta //fechaDesde menor o igual a periodo
     * c.add(Restrictions.le("fechaDesde", periodoDesde)); //fechaHasta mayor o
     * igual a periodo o fechaHasta es null c.add(Restrictions.or(
     * Restrictions.and( Restrictions.isNotNull("fechaHasta"),
     * Restrictions.ge("fechaHasta", periodoHasta) ),
     * Restrictions.isNull("fechaHasta")));
     *
     * //ultimo digito del ruc viejo si es dia //o ruc viejo de lo contrario
     * c.add(Restrictions.or( Restrictions.and(
     * Restrictions.ne("unidadTiempo","DIA"), Restrictions.le("digitoDesdeCad",
     * cont.getRucAnterior().substring(cont.getRucAnterior().length()-1)) ),
     * Restrictions.and( Restrictions.eq("unidadTiempo","DIA"),
     * Restrictions.le("digitoDesdeCad",
     * cont.getRucNuevo().substring(cont.getRucNuevo().length()-1)) ) ));
     * c.add(Restrictions.or( Restrictions.and(
     * Restrictions.ne("unidadTiempo","DIA"), Restrictions.ge("digitoHastaCad",
     * cont.getRucAnterior().substring(cont.getRucAnterior().length()-1)) ),
     * Restrictions.and( Restrictions.eq("unidadTiempo","DIA"),
     * Restrictions.ge("digitoHastaCad",
     * cont.getRucNuevo().substring(cont.getRucNuevo().length()-1)) ) )); for
     * (Object obj : c.list()) { ParamVencimientos pv=(ParamVencimientos)obj;
     * System.out.println(""+pv.getIdParamVencimiento()+";;"+
     * pv.getFechaDesde()+";;"+ pv.getFechaHasta()+";;"+
     * pv.getDescripcion()+";;"+ pv.getUnidadTiempo()+";;"+
     * pv.getMesesPlazo()+";;"+ pv.getPeriodicidad()+";;"+
     * pv.getPlazoDeclaracion()+";;" ); }
     *
     * return (ParamVencimientos) c.list().get(0);
     *
     * }
     */
    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertarVencimientos(FileItem bf, String separadorCampos, int formatoFecha, Integer numeroImpuesto) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            InputStream uploadedStream = bf.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            for (String linea : lineas) {
                String[] token = LectorConfiguracionSet.tokensDeLaLinea(linea, separadorCampos);
                if (VENCIMIENTOS_POS_IMPUESTO < token.length) {
                    if (numeroImpuesto == null
                            || Integer.parseInt(token[VENCIMIENTOS_POS_IMPUESTO]) == numeroImpuesto) {
                        Date fechaDesde, fechaHasta;
                        if (VENCIMIENTOS_POS_FECHA_DESDE < token.length && token[VENCIMIENTOS_POS_FECHA_DESDE].length() > 0) {
                            if (LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY == formatoFecha) {
                                fechaDesde = sdf.parse(LectorConfiguracionSet.DDMMYYYYtoFechaDDMMYYYY(token[VENCIMIENTOS_POS_FECHA_DESDE]));
                            } else {
                                fechaDesde = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[VENCIMIENTOS_POS_FECHA_DESDE]));
                            }
                        } else {
                            throw new Exception("Fecha desde es mandatorio:"
                                    + " impuesto " + token[VENCIMIENTOS_POS_IMPUESTO]
                                    + " digito desde " + token[VENCIMIENTOS_POS_DIGITO_DESDE]
                                    + " digito hasta " + token[VENCIMIENTOS_POS_DIGITO_HASTA]);
                        }
                        if (VENCIMIENTOS_POS_FECHA_HASTA < token.length && token[VENCIMIENTOS_POS_FECHA_HASTA].length() > 0) {
                            if (LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY == formatoFecha) {
                                fechaHasta = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[VENCIMIENTOS_POS_FECHA_HASTA]));
                            } else {
                                fechaHasta = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[VENCIMIENTOS_POS_FECHA_HASTA]));
                            }
                        } else {
                            fechaHasta = null;
                        }
                        ParamVencimientos pv = new ParamVencimientos();
                        pv.setImpuesto(Integer.parseInt(token[VENCIMIENTOS_POS_IMPUESTO]));
                        pv.setDigitoDesdeCad(token[VENCIMIENTOS_POS_DIGITO_DESDE]);
                        pv.setDigitoHastaCad(token[VENCIMIENTOS_POS_DIGITO_HASTA]);
                        pv.setFechaDesde(fechaDesde);
                        if (this.total(pv) == 0) {
                            pv.setAbreviatura(token[VENCIMIENTOS_POS_ABREVIATURA]);
                            pv.setDescripcion(token[VENCIMIENTOS_POS_DESCRIPCION]);
                            pv.setMesesPlazo(Integer.parseInt(token[VENCIMIENTOS_POS_MESES_PLAZO]));
                            pv.setPeriodicidad(Integer.parseInt(token[VENCIMIENTOS_POS_PERIODICIDAD]));
                            pv.setFechaHasta(fechaHasta);
                            pv.setNumeroCuota(Integer.parseInt(token[VENCIMIENTOS_POS_NUMERO_CUOTA]));
                            pv.setUnidadTiempo(token[VENCIMIENTOS_POS_UNIDAD_TIEMPO]);
                            pv.setPlazoDeclaracion(Integer.parseInt(token[VENCIMIENTOS_POS_PLAZO_DECLARACION]));
                            this.save(pv);
                        } else {
                            pv = this.get(pv);
                            pv.setAbreviatura(token[VENCIMIENTOS_POS_ABREVIATURA]);
                            pv.setDescripcion(token[VENCIMIENTOS_POS_DESCRIPCION]);
                            pv.setMesesPlazo(Integer.parseInt(token[VENCIMIENTOS_POS_MESES_PLAZO]));
                            pv.setPeriodicidad(Integer.parseInt(token[VENCIMIENTOS_POS_PERIODICIDAD]));
                            pv.setFechaHasta(fechaHasta);
                            pv.setNumeroCuota(Integer.parseInt(token[VENCIMIENTOS_POS_NUMERO_CUOTA]));
                            pv.setUnidadTiempo(token[VENCIMIENTOS_POS_UNIDAD_TIEMPO]);
                            pv.setPlazoDeclaracion(Integer.parseInt(token[VENCIMIENTOS_POS_PLAZO_DECLARACION]));
                            this.update(pv);
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

    public static boolean bisiesto(int anno) {
        if ((anno % 4 == 0 && anno % 100 != 0) || anno % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public ParamVencimientos obtenerParamVencimiento(
            Integer numeroImpuesto, String periodo, Contribuyentes cont) throws Exception {
        if (periodo.length() == 4) {
            periodo = "3112" + periodo;
        } else if (periodo.length() == 6) {
            int año = Integer.parseInt(periodo.substring(2));
            int mes = Integer.parseInt(periodo.substring(0, 2));
            if (mes == 2 && bisiesto(año)) {
                periodo = "29" + periodo;
            } else if (mes == 2) {
                periodo = "28" + periodo;
            } else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                periodo = "31" + periodo;
            } else {
                periodo = "30" + periodo;
            }

        }

        Date periodoDate = new SimpleDateFormat("ddMMyyyy").parse(periodo);
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(ParamVencimientos.class);
        c.add(Restrictions.eq("impuesto", numeroImpuesto));
        //periodo debe caer dentro de fechaDesde y fechaHasta
        //fechaDesde menor o igual a periodo
        c.add(Restrictions.le("fechaDesde", periodoDate));
        //fechaHasta mayor o igual a periodo o fechaHasta es null
        c.add(Restrictions.or(Restrictions.isNull("fechaHasta"), Restrictions.ge("fechaHasta", periodoDate)));

        //Por si el ultimo caracter es distinto de un numero, solo para el RucNuevo
        String digitoRucNuevo = "";
        for (int i = cont.getRucNuevo().length() - 1; i>=0 ; i--) {
            if(cont.getRucNuevo().codePointAt(i) >= 48 && cont.getRucNuevo().codePointAt(i) <= 57){
               digitoRucNuevo = String.valueOf(cont.getRucNuevo().charAt(i));
               break;
            }
        }
        //if (cont.getRucNuevo().substring(cont.getRucNuevo().length() - 1))
        //ultimo digito del ruc nuevo si es dia
        //o ultimo caracter del ruc viejo si no es dia
        c.add(Restrictions.or(
                Restrictions.and(
                Restrictions.ne("unidadTiempo", "DIA"),
                Restrictions.and(
                Restrictions.le("digitoDesdeCad", cont.getRucAnterior().substring(cont.getRucAnterior().length() - 1)),
                Restrictions.ge("digitoHastaCad", cont.getRucAnterior().substring(cont.getRucAnterior().length() - 1)))),
                Restrictions.and(
                Restrictions.eq("unidadTiempo", "DIA"),
                Restrictions.and(
                Restrictions.le("digitoDesdeCad", digitoRucNuevo),
                Restrictions.ge("digitoHastaCad", digitoRucNuevo)))));

        return (ParamVencimientos) c.list().get(0);

    }
    private static int VENCIMIENTOS_POS_IMPUESTO = 0;
    private static int VENCIMIENTOS_POS_ABREVIATURA = 1;
    private static int VENCIMIENTOS_POS_PERIODICIDAD = 2;
    private static int VENCIMIENTOS_POS_DESCRIPCION = 3;
    private static int VENCIMIENTOS_POS_NUMERO_CUOTA = 4;
    private static int VENCIMIENTOS_POS_DIGITO_DESDE = 5;
    private static int VENCIMIENTOS_POS_DIGITO_HASTA = 6;
    private static int VENCIMIENTOS_POS_MESES_PLAZO = 7;
    private static int VENCIMIENTOS_POS_PLAZO_DECLARACION = 8;
    private static int VENCIMIENTOS_POS_UNIDAD_TIEMPO = 9;
    private static int VENCIMIENTOS_POS_FECHA_DESDE = 10;
    private static int VENCIMIENTOS_POS_FECHA_HASTA = 11;
}
