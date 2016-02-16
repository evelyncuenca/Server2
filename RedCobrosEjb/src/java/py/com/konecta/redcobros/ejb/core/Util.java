/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.ejb.core;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Klaus
 */
public class Util {

    public static Date yyyyMMddHHmmssToDate (String fechaHora) {

        Date ret = null;

        try {
            fechaHora = fechaHora.replaceAll("[^0-9]","");
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMddHHmmss");
            formatoFecha.setLenient(false);
            ret = formatoFecha.parse(fechaHora);
            if (fechaHora.length()!=14) ret = null;
        } catch (Exception e) {
            e.printStackTrace();
            ret = null;
        }

        return ret;
    }
    
}
