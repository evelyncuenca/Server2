/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import py.com.konecta.redcobros.entities.Feriados;

/**
 *
 * @author konecta
 */
@Stateless
public class FeriadosFacadeImpl extends GenericDaoImpl<Feriados, Long> implements FeriadosFacade {

    public boolean esDiaHabil(int anho, int mes, int dia) {
        boolean respuesta = true;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Asuncion"));
        calendar.set(Calendar.YEAR, anho);
        calendar.set(Calendar.MONTH, mes-1);
        calendar.set(Calendar.DATE, dia);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY
                || dayOfWeek == Calendar.SUNDAY) {
            respuesta = false;
        } else {
            Date fechaActual = calendar.getTime();
            Feriados ejemploF = new Feriados();
            ejemploF.setFlagObviarAnho(2);
            ejemploF.setFecha(fechaActual);
            if (this.total(ejemploF) > 0) {
                respuesta = false;
            } else {
                ejemploF.setFlagObviarAnho(1);
                ejemploF.setFecha(null);
                ejemploF.setCadenaFecha(new SimpleDateFormat("ddMM").format(fechaActual));
                if (this.total(ejemploF) > 0) {
                    respuesta = false;
                }
            }
        }
        return respuesta;
    }
}
