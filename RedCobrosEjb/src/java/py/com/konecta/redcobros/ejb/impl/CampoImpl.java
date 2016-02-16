/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
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
import py.com.konecta.redcobros.entities.Campo;
import py.com.konecta.redcobros.entities.Formulario;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CampoImpl extends GenericDaoImpl<Campo, Long> implements CampoFacade {

    @Resource
    private SessionContext context;

    @EJB
    private FormularioFacade formularioFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void capturarCampos(FileItem bf, String separadorCampos, Integer numeroFormulario, Integer version) throws Exception {
        try {
            InputStream uploadedStream = bf.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            for (String linea : lineas) {
                String[] token = LectorConfiguracionSet.tokensDeLaLinea(linea, separadorCampos);

                if (CAMPO_POS_FORMULARIO < token.length && CAMPO_POS_VERSION < token.length) {
                    if ((numeroFormulario == null && version == null) ||
                            (Integer.parseInt(token[CAMPO_POS_FORMULARIO]) == numeroFormulario && Integer.parseInt(token[CAMPO_POS_VERSION]) == version)) {

                        int numeroCampo;
                        if (CAMPO_POS_NUMERO < token.length && token[CAMPO_POS_NUMERO].length() > 0) {
                            numeroCampo = Integer.parseInt(token[CAMPO_POS_NUMERO]);
                        } else {
                            throw new Exception("Campo no contiene numero");
                        }
                        String descripcion = null, inciso = null;
                        Integer rubro;
                        if (CAMPO_POS_DESCRIPCION < token.length && token[CAMPO_POS_DESCRIPCION].length() >= 7) {
                            if (token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("1") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("2") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("3") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("4") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("5") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("6") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("7") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("8") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("9") || token[CAMPO_POS_DESCRIPCION].substring(0, 1).equals("0")) {
                                if (token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("1") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("2") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("3") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("4") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("5") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("6") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("7") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("8") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("9") || token[CAMPO_POS_DESCRIPCION].substring(1, 2).equals("0")) {
                                    rubro = Integer.parseInt(token[CAMPO_POS_DESCRIPCION].substring(0, 2));
                                    if (token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("1") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("2") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("3") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("4") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("5") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("6") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("7") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("8") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("9") || token[CAMPO_POS_DESCRIPCION].substring(4, 5).equals("0")) {
                                        inciso = token[CAMPO_POS_DESCRIPCION].substring(3, 6);
                                        descripcion = token[CAMPO_POS_DESCRIPCION].substring(6);
                                    } else {
                                        inciso = token[CAMPO_POS_DESCRIPCION].substring(3, 4);
                                        descripcion = token[CAMPO_POS_DESCRIPCION].substring(4);
                                    }
                                } else {
                                    rubro = Integer.parseInt(token[CAMPO_POS_DESCRIPCION].substring(0, 1));
                                    if (token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("1") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("2") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("3") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("4") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("5") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("6") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("7") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("8") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("9") || token[CAMPO_POS_DESCRIPCION].substring(3, 4).equals("0")) {
                                        inciso = token[CAMPO_POS_DESCRIPCION].substring(2, 5);
                                        descripcion = token[CAMPO_POS_DESCRIPCION].substring(5);
                                    } else {
                                        inciso = token[CAMPO_POS_DESCRIPCION].substring(2, 3);
                                        descripcion = token[CAMPO_POS_DESCRIPCION].substring(3);
                                    }
                                }
                            } else {
                                rubro = null;
                                inciso = null;
                                descripcion = token[CAMPO_POS_DESCRIPCION];
                            }
                        } else {
                            descripcion = token[CAMPO_POS_DESCRIPCION];
                            rubro = null;
                            inciso = null;
                        }

                        String formula = null;
                        if (CAMPO_POS_FORMULA < token.length && token[CAMPO_POS_FORMULA].length() >= 0) {
                            formula = ((token[CAMPO_POS_FORMULA].replaceAll(" ", "")));
                            formula=formula.replace('[', ' ');
                            formula=formula.replace(']', ' ');
                            formula=formula.replaceAll(" ", "");
                        }

                        int requerido;
                        if (CAMPO_POS_MANDATORIO < token.length && token[CAMPO_POS_MANDATORIO].length() > 0) {
                            requerido = token[CAMPO_POS_MANDATORIO].equalsIgnoreCase("N") ? 2 : 1;
                        } else {
                            requerido = 2;
                        }

                        Integer columna;
                        if (CAMPO_POS_COLUMNA < token.length && token[CAMPO_POS_COLUMNA].length() > 0) {
                            columna = Integer.parseInt(token[CAMPO_POS_COLUMNA]);
                        } else {
                            throw new Exception("Campo no contiene numero de columna: "+
                                    "formulario "+token[CAMPO_POS_FORMULARIO]+
                                    " version "+token[CAMPO_POS_VERSION]+
                                    " numero de campo "+token[CAMPO_POS_NUMERO]);
                        }
                        Formulario formulario=new Formulario();
                        formulario.setNumeroFormulario(Integer.parseInt(token[CAMPO_POS_FORMULARIO]));
                        formulario.setVersion(Integer.parseInt(token[CAMPO_POS_VERSION]));
                        Campo c=new Campo();
                        c.setFormulario(this.formularioFacade.get(formulario));
                        c.setNumero(numeroCampo);
                        int cantidad=this.total(c);
                        if (cantidad==0) {
                            c.setDescripcion(descripcion);
                            if (rubro!=null) {
                                c.setIdRubro(new BigInteger(""+rubro));
                            }
                            c.setInciso(inciso);
                            c.setFormula(formula);
                            c.setRequerido(new BigInteger(""+requerido));
                            c.setNumeroColumna(new BigInteger(""+columna));
                            c.setCadenaCampo(linea);
                            c.setMostrarSugerencia(new BigInteger("1"));
                            c.setValido(new BigInteger("1"));
                            c.setEtiqueta("C"+numeroCampo);
                            this.save(c);
                        } else {
                            c=this.get(c);
                            c.setDescripcion(descripcion);
                            if (rubro!=null) {
                                c.setIdRubro(new BigInteger(""+rubro));
                            }
                            c.setInciso(inciso);
                            c.setFormula(formula);
                            c.setRequerido(new BigInteger(""+requerido));
                            c.setNumeroColumna(new BigInteger(""+columna));
                            c.setCadenaCampo(linea);
                            this.update(c);
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
    private static int CAMPO_POS_FORMULARIO = 0;
    private static int CAMPO_POS_DESCRIPCION = 4;
    private static int CAMPO_POS_VERSION = 1;
    private static int CAMPO_POS_NUMERO = 2;
    private static int CAMPO_POS_MANDATORIO = 6;
    private static int CAMPO_POS_FORMULA = 5;
    private static int CAMPO_POS_COLUMNA = 8;
}
