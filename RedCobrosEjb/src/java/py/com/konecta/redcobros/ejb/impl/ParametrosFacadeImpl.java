/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
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
import py.com.konecta.redcobros.entities.Parametros;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ParametrosFacadeImpl extends GenericDaoImpl<Parametros, Long> implements ParametrosFacade {

    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertarTasasInteresesContravencionesMultas(FileItem bf, String separadorCampos, int formatoFecha) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            InputStream uploadedStream = bf.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            for (String linea : lineas) {
                String[] token = LectorConfiguracionSet.tokensDeLaLinea(linea, separadorCampos);
                if (PARAMETROS_POS_FUNDAMENTO_LEGAL < token.length) {
                    Date fechaDesde, fechaHasta;
                    if (token[PARAMETROS_POS_FECHA_DESDE].length() > 0) {
                        if (LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY==formatoFecha) {
                            fechaDesde = sdf.parse(LectorConfiguracionSet.DDMMYYYYtoFechaDDMMYYYY(token[PARAMETROS_POS_FECHA_DESDE]));
                        } else {
                            fechaDesde = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[PARAMETROS_POS_FECHA_DESDE]));
                        }
                        
                    } else {
                        throw new Exception("Fecha desde es mandatorio:" +
                                    "tipo " + token[PARAMETROS_POS_TIPO] +
                                    " infraccion " + Integer.parseInt(token[PARAMETROS_POS_INFRACCION]));
                    }
                    if (token[PARAMETROS_POS_FECHA_HASTA].length() > 0) {
                        if (LectorConfiguracionSet.FORMATO_FECHA_DDMMYYYY==formatoFecha) {
                            fechaHasta = sdf.parse(LectorConfiguracionSet.DDMMYYYYtoFechaDDMMYYYY(token[PARAMETROS_POS_FECHA_HASTA]));
                        } else {
                            fechaHasta = sdf.parse(LectorConfiguracionSet.MMDDYYYYtoFechaDDMMYYYY(token[PARAMETROS_POS_FECHA_HASTA]));
                        }
                    } else {
                        fechaHasta = null;
                    }                    
                    Parametros p=new Parametros();
                    p.setInfraccion(Integer.parseInt(token[PARAMETROS_POS_INFRACCION]));
                    p.setFechaDesde(fechaDesde);
                    p.setFechaHasta(fechaHasta);
                    if (this.total(p)==0) {
                        p.setTipo(token[PARAMETROS_POS_TIPO]);
                        p.setFundamentoLegal(token[PARAMETROS_POS_FUNDAMENTO_LEGAL]);
                        p.setPlazoDesde(Integer.parseInt(token[PARAMETROS_POS_PLAZO_DESDE]));
                        p.setPlazoHasta(Integer.parseInt(token[PARAMETROS_POS_PLAZO_HASTA]));
                        p.setTipoCalculo(token[PARAMETROS_POS_TIPO_CALCULO]);
                        p.setValor(Double.parseDouble(token[PARAMETROS_POS_VALOR].replaceAll(",", ".")));
                        p.setValorMinimo(new BigInteger(token[PARAMETROS_POS_VALOR_MINIMO]));
                        p.setValorMaximo(new BigInteger(token[PARAMETROS_POS_VALOR_MAXIMO]));
                        this.save(p);
                    } else {
                        p=this.get(p);
                        p.setTipo(token[PARAMETROS_POS_TIPO]);
                        String fundamentoLegal = null;
                        if (PARAMETROS_POS_FUNDAMENTO_LEGAL<token.length) {
                            fundamentoLegal=token[PARAMETROS_POS_FUNDAMENTO_LEGAL];
                        }
                        p.setFundamentoLegal(fundamentoLegal);
                        p.setPlazoDesde(Integer.parseInt(token[PARAMETROS_POS_PLAZO_DESDE]));
                        p.setPlazoHasta(Integer.parseInt(token[PARAMETROS_POS_PLAZO_HASTA]));
                        p.setTipoCalculo(token[PARAMETROS_POS_TIPO_CALCULO]);
                        p.setValor(Double.parseDouble(token[PARAMETROS_POS_VALOR].replaceAll(",", ".")));
                        p.setValorMinimo(new BigInteger(token[PARAMETROS_POS_VALOR_MINIMO]));
                        p.setValorMaximo(new BigInteger(token[PARAMETROS_POS_VALOR_MAXIMO]));
                        this.update(p);
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

    public Double obtenerMulta(Integer impuesto, Double monto) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(Parametros.class);
        c.add(Restrictions.eq("infraccion", 12));
        c.add(Restrictions.isNull("fechaHasta"));
        return ((Parametros) c.list().get(0)).getValor();
    }

    public Double obtenerMora(Double monto, Integer mesesAtraso) {
        if (mesesAtraso <= 0) {
            return 0.0;
        } else {
            HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
            Criteria c = hem.getSession().createCriteria(Parametros.class);
            c.add(Restrictions.eq("infraccion", 2));
            c.add(Restrictions.isNull("fechaHasta"));
            c.add(Restrictions.lt("plazoDesde", mesesAtraso));
            c.add(Restrictions.ge("plazoHasta", mesesAtraso));
            Double valor = (((Parametros) c.list().get(0)).getValor() * monto) / 100.0;
            return valor;
        }
    }

    public Double obtenerInteres(Double monto, Integer diasAtraso) {
        if (diasAtraso == 0) {
            return 0.0;
        } else {
            HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
            Criteria c = hem.getSession().createCriteria(Parametros.class);
            c.add(Restrictions.eq("infraccion", 1));
            c.add(Restrictions.isNull("fechaHasta"));
            Double valor = (((((Parametros) c.list().get(0)).getValor() / 30.0) * diasAtraso) * monto) / 100.0;
            return valor;
        }
    }
    private static int PARAMETROS_POS_TIPO = 0;
    private static int PARAMETROS_POS_INFRACCION = 1;
    private static int PARAMETROS_POS_FECHA_DESDE = 2;
    private static int PARAMETROS_POS_FECHA_HASTA = 3;
    private static int PARAMETROS_POS_PLAZO_DESDE = 4;
    private static int PARAMETROS_POS_PLAZO_HASTA = 5;
    private static int PARAMETROS_POS_VALOR = 6;
    private static int PARAMETROS_POS_TIPO_CALCULO = 7;
    private static int PARAMETROS_POS_VALOR_MINIMO = 8;
    private static int PARAMETROS_POS_VALOR_MAXIMO = 9;
    private static int PARAMETROS_POS_FUNDAMENTO_LEGAL = 10;
}
