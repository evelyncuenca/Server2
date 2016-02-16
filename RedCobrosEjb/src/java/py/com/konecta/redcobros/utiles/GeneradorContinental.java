/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.utiles;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import py.com.konecta.redcobros.ejb.impl.UtilFacadeImpl;
import py.com.konecta.redcobros.entities.Corte;
import py.com.konecta.redcobros.entities.TipoCobro;

/**
 *
 * @author gustavo
 */
public class GeneradorContinental extends GeneradorArchivoClearing {
    public GeneradorContinental(boolean comision, boolean debito,
            List<Map<String, Object>> lista,boolean esManual, Corte corte) {
        super(comision, debito, lista,esManual, corte);
    }
    @Override
    protected void agregarCabecera() {        
    }
    @Override
    protected void agregarPie() {        
    }
    @Override
    protected void agregarCuerpoDetalle(int posicion, String cuenta, double monto, Long idTipocobro, Date fechaAcreditacion,Date fecha, Corte corte) {
        //numero de orden: 4 lugares
//        String numeroOrden=UtilesSet.cerosIzquierda((long)posicion,4);
        String numeroOrden=UtilesSet.cerosIzquierda((long)posicion,6);
        this.cadena.append(numeroOrden);
        //numero de cuenta de la entidad: 6 lugares
        String numeroCuenta=UtilesSet.cerosIzquierda(Long.parseLong(cuenta),6);
        this.cadena.append(numeroCuenta);
        //fecha de acreditacion: 8 lugares
        this.cadena.append(new SimpleDateFormat("yyyyMMdd").format(fechaAcreditacion));
        //importe del movimiento: 12 enteros, 2 decimales
        NumberFormat formatter = new DecimalFormat("############.##");
        formatter.setMaximumIntegerDigits(12);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        String importe=formatter.format(monto).replaceAll(",", "").replace(".", "");
        this.cadena.append(UtilesSet.cerosIzquierda(Long.parseLong(importe), 14));
        //codigo leyenda: 2
        //ANTES
        //this.cadena.append(UtilesSet.cerosIzquierda(idTipocobro, 2));
        String codigoLeyenda=null;
        if (idTipocobro.longValue()==TipoCobro.EFECTIVO) {
            if (this.comision) {
                if (this.debito) {
                    codigoLeyenda="05";
                } else {
                    codigoLeyenda="07";
                }
            } else {
                if (this.debito) {
                    codigoLeyenda="03";
                } else {
                    codigoLeyenda="01";
                }
            }
        } if (idTipocobro.longValue()==TipoCobro.CHEQUES_BANCO_LOCAL
                || idTipocobro.longValue()==TipoCobro.CHEQUES_OTRO_BANCO) {
            if (this.comision) {
                if (this.debito) {
                    codigoLeyenda="06";
                } else {
                    codigoLeyenda="08";
                }
            } else {
                if (this.debito) {
                    codigoLeyenda="04";
                } else {
                    codigoLeyenda="02";
                }
            }
        }
        this.cadena.append(codigoLeyenda);
        //leyenda de extracto: 6 fecha de la transaccion formato yymmdd seguido de
        // 4 digitos de numero de orden seguido dos digitos segun criterio
        String leyendaExtracto=new SimpleDateFormat("yyMMdd").format(fecha);
        leyendaExtracto+=numeroOrden;
        leyendaExtracto+=codigoLeyenda;
        this.cadena.append(leyendaExtracto);
        //codigo de comercio q recibe la comision: 6 lugares
        //se asume q es el mismo que el numero de cuenta
//        this.cadena.append(numeroCuenta);
        this.cadena.append("000000");
        
        //se agrega la fecha del ultimo Corte y el idCorte
        String fechaCorte = new SimpleDateFormat("yyyyMMdd").format(corte.getFechaCorte());
        this.cadena.append(fechaCorte);
        String idCorte=UtilesSet.cerosIzquierda(corte.getIdCorte().longValue(),6);
        this.cadena.append(idCorte);
        

        this.cadena.append(UtilFacadeImpl.CARACTER_SEPARADOR_LINEA);
    }
//
//    public static void main(String [] args) {
//        NumberFormat formatter = new DecimalFormat("############.##");
//        formatter.setMaximumIntegerDigits(12);
//        formatter.setMinimumFractionDigits(2);
//        formatter.setMaximumFractionDigits(2);
//        System.out.println(formatter.format(452528));
//    }
}
