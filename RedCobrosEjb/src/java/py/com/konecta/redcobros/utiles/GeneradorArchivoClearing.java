/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.utiles;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import py.com.konecta.redcobros.entities.Corte;

/**
 *
 * @author gustavo
 */
public abstract class GeneradorArchivoClearing {
    protected  StringBuffer cadena;
    protected  boolean comision;
    protected  boolean manual;
    protected  boolean debito;
    protected List<Map<String,Object>> lista;
    protected Corte corte;
    public GeneradorArchivoClearing(boolean comision, boolean debito,
            List<Map<String,Object>> lista,boolean manual, Corte corte) {
        this.comision=comision;
        this.debito=debito;
        this.lista=lista;
        this.manual=manual;
        this.corte=corte;
    }

    public String obtenerTextoGenerado() {
        if (this.cadena==null) {
            this.cadena=new StringBuffer();
            this.agregarCabecera();
            this.agregarCuerpo();
            this.agregarPie();
        }
        return this.cadena.toString();
    }

    protected void agregarCuerpo(){
        int posicionRegistro=0;
        String cuenta;
        double monto;
        Long idTipoCobro;
        Character debitoCredito;
        Date fecha;
        Date fechaAcreditacion;
        List<Map<String,Object>> listaDetalle;
        for (Map<String,Object> mapa:this.lista) {
            idTipoCobro=(Long)mapa.get("idTipoCobro");
            fechaAcreditacion=(Date)mapa.get("fechaAcreditacion");
            if (this.manual) {
                //manual
                fecha=(Date)mapa.get("fechaClearing");
                debitoCredito=(Character)mapa.get("cabeceraDebitoCredito");
                if (this.debito) {
                    if (debitoCredito.equals('D')) {
                        //la cabecera es debito por tanto en la cabecera ta el debito
                        posicionRegistro++;
                        cuenta=(String)mapa.get("numeroCuenta");
                        monto=(Double)mapa.get("montoTotal");
                        this.agregarCuerpoDetalle(posicionRegistro, cuenta, monto, 1L, fechaAcreditacion,fecha, null);
                    } else {
                        //la cabecera es credito por tanto en el detalle ta el debito
                        listaDetalle=(List<Map<String,Object>>)(Collection)((JRMapCollectionDataSource)mapa.get("distribucionClearingManual")).getData();
                        for (Map<String,Object> mapaDetalle :  listaDetalle) {
                            posicionRegistro++;
                            cuenta=(String)mapaDetalle.get("numeroCuenta");
                            monto=(Double)mapaDetalle.get("montoTotal");
                            this.agregarCuerpoDetalle(posicionRegistro, cuenta,
                                   monto, 1L,
                                   fechaAcreditacion,fecha, null);
                        }
                    }

                } else {
                    if (debitoCredito.equals('C')) {
                        //la cabecera es credito por tanto en la cabecera ta el credito
                        posicionRegistro++;
                        cuenta=(String)mapa.get("numeroCuenta");
                        monto=(Double)mapa.get("montoTotal");
                        this.agregarCuerpoDetalle(posicionRegistro, cuenta, monto, 1L, fechaAcreditacion,fecha, null);
                    } else {
                        //la cabecera es debito por tanto en el detalle ta el credito
                        listaDetalle=(List<Map<String,Object>>)(Collection)((JRMapCollectionDataSource)mapa.get("distribucionClearingManual")).getData();
                        for (Map<String,Object> mapaDetalle :  listaDetalle) {
                            posicionRegistro++;
                            cuenta=(String)mapaDetalle.get("numeroCuenta");
                            monto=(Double)mapaDetalle.get("montoTotal");
                            this.agregarCuerpoDetalle(posicionRegistro, cuenta,
                                   monto, 1L,
                                   fechaAcreditacion,fecha, null);
                        }
                    }
                }
            } else if (!comision) {
                //clearing facturador
                fecha=(Date)mapa.get("fechaClearingFacturador");
                if (this.debito) {
                    //hay q recorrer el detalle
                    listaDetalle=(List<Map<String,Object>>)(Collection)((JRMapCollectionDataSource)mapa.get("distribucionClearingFacturador")).getData();
                    for (Map<String,Object> mapaDetalle :  listaDetalle) {
                        posicionRegistro++;
                        cuenta=(String)mapaDetalle.get("cuentaRecaudador");
                        monto=(Double)mapaDetalle.get("monto");
                        this.agregarCuerpoDetalle(posicionRegistro, cuenta, 
                               monto, idTipoCobro,
                               fechaAcreditacion,fecha, corte);
                    }
                } else {
                    posicionRegistro++;
                    cuenta=(String)mapa.get("cuentaFacturador");
                    monto=(Double)mapa.get("montoTotal");
                    this.agregarCuerpoDetalle(posicionRegistro, cuenta, monto, idTipoCobro, fechaAcreditacion,fecha, corte);
                }
            } else {
                //clearing comisiones
                if (this.debito) {

                } else {

                }
            }
        }
    }

    protected abstract void agregarCabecera();
    protected abstract void agregarCuerpoDetalle(int posicion,String cuenta,
            double monto, Long idTipocobro, Date fechaAcreditacion,
            Date fecha, Corte corte);
    protected abstract void agregarPie();


}
