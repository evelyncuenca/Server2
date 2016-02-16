/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.utiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.com.konecta.redcobros.entities.TransaccionRc;

/**
 *
 * @author Kiki
 */
public class Utiles {


    private static SimpleDateFormat anhoMesDia = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat anhoMesDiaHora = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static Date primerMomentoFecha(Date fecha) {
        try {
            return anhoMesDiaHora.parse(anhoMesDia.format(fecha) + "000000000");
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
    }

    public static Date ultimoMomentoFecha(Date fecha) {
        try {
            return anhoMesDiaHora.parse(anhoMesDia.format(fecha) + "235959999");
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
    }
    public static String getElementValue(String cadena, String regex, int posicion) {
        String array[] = cadena.split(regex);

        return array[posicion];
    }
    public static void writeToFile(String pathFile, Long codExtUsuario, TransaccionRc transaccion, Boolean anulado) {
        try {
            if (codExtUsuario != null) {
                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                int beginIndex = transaccion.getIdTransaccion().toString().length() > 6 ? transaccion.getIdTransaccion().toString().length() - 6 : 0;
                String nombreArchivoUsuario = codExtUsuario.toString() + sdf.format(today) + ".txt";
                String indicadorAnulacion = anulado ? "*" : " ";
                String registro = StringUtils.lpad("9994", "0", 8)
                        + StringUtils.lpad(transaccion.getIdTransaccion().toString().substring(beginIndex), "0", 6)//6
                        + StringUtils.lpad(transaccion.getIdServicio().getIdServicio().toString(), "0", 4) //4
                        + StringUtils.lpad(transaccion.getMonto().toString(), "0", 9) //9
                        + transaccion.getIdTipoPago().getIdTipoPago().toString()
                        + StringUtils.lpad("0", "0", 12)//
                        + indicadorAnulacion //1
                        + StringUtils.lpad("0", "0", 7)//
                        + StringUtils.lpad(transaccion.getReferenciaPago(), " ", 20) //20
                        + StringUtils.lpad(transaccion.getIdTransaccion().toString(), "0", 15)
                        + StringUtils.lpad(transaccion.getIdServicio().getDescripcion(), " ", 50)//30
                        + "\n";

                String URI_USER_FILE = pathFile + nombreArchivoUsuario;
                //File archivoUsuario = new File(URI_USER_FILE);
                BufferedWriter bw = new BufferedWriter(new FileWriter(URI_USER_FILE, true));
                bw.write(registro);
                bw.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Utiles.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
