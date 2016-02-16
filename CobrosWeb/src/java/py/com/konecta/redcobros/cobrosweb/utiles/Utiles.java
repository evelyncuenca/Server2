/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.utiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.com.konecta.redcobros.entities.Transaccion;
import py.com.konecta.redcobros.entities.TransaccionRc;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
public class Utiles {

    public static String CLAVE_INCORRECTA = "Password incorrecto";
    public static String DEFAULT_ERROR_MESSAGE = "No se puede realizar la operacion";

    public static String SHA1(String text) {
        String shaValue = null;
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash = new byte[40];
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
            shaValue = convertToHex(sha1hash);
        } catch (Exception ex) {
            Logger.getLogger(Utiles.class.getName()).log(Level.SEVERE, "sha", ex);
        }
        return shaValue;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String getSha1(String pass) throws NoSuchAlgorithmException {

        byte[] defaultBytes = pass.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA1");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            String foo = messageDigest.toString();
            return hexString.toString();

        } catch (NoSuchAlgorithmException nsae) {

            return "";
        }

    }

    public static String ripLastComa(String cadena) {

        return cadena.substring(0, cadena.lastIndexOf(","));


    }

    public static String getElementValue(String cadena, String regex, int posicion) {
        String array[] = cadena.split(regex);

        return array[posicion];
    }

    public static Calendar getFechaVencimiento(String vencimiento) {
        Calendar calendar = null;
        try {
            int dia = Integer.parseInt(vencimiento.substring(0, 2));
            int mes = Integer.parseInt(vencimiento.substring(2, 4));
            int agno = Integer.parseInt(vencimiento.substring(4));
            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, agno);
            calendar.set(Calendar.DAY_OF_MONTH, dia);
            calendar.set(Calendar.MONTH, mes - 1);

        } catch (Exception ex) {
        }

        return calendar;
    }

    public static Long toMillisecond(String cantDias) {
        return new Long(cantDias) * 86400000;
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

    public static void writeToFile(String pathFile, Long codExtUsuario, Transaccion transaccion, Boolean anulado) {
        try {
            if (codExtUsuario != null) {
                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                int beginIndex = transaccion.getIdTransaccion().toString().length() > 6 ? transaccion.getIdTransaccion().toString().length() - 6 : 0;
                String nombreArchivoUsuario = codExtUsuario.toString() + sdf.format(today) + ".txt";
                String indicadorAnulacion = anulado ? "*" : " ";
                String registro = StringUtils.lpad("9994", "0", 8)
                        + StringUtils.lpad((transaccion.getIdTransaccion().toString().substring(beginIndex)), "0", 6)//6
                        + "000" + (transaccion.getCodigoTransaccional().equalsIgnoreCase("PAGO-SET") ? "1" : "2")
                        + StringUtils.lpad(String.valueOf(transaccion.getMontoTotal().intValue()), "0", 9) //9
                        + transaccion.getTipoPago().getIdTipoPago()
                        + StringUtils.lpad("0", "0", 12)
                        + indicadorAnulacion //1
                        + StringUtils.lpad("0", "0", 7)
                        + StringUtils.lpad(transaccion.getBoletaPagoContrib().getContribuyente().getRucNuevo(), " ", 20)//30
                        + StringUtils.lpad(transaccion.getBoletaPagoContrib().getNumeroOrden().toString(), " ", 15) //20
                        + StringUtils.lpad(transaccion.getCodigoTransaccional(), " ", 50)
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
