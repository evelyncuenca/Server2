/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.utiles;

/**
 *
 * @author konecta
 */
import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

    private DateUtils() {
    }

    public static Date getDateFromJulian7(String julianDate)
            throws ParseException {
        return new SimpleDateFormat("yyyyD").parse(julianDate);
    }

    public static String getJulian7FromDate(Date date) {
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return sb.append(cal.get(Calendar.YEAR)).append(String.format("%03d", cal.get(Calendar.DAY_OF_YEAR))).toString();
    }

      
    public static Date getCompleteTime(String time){
        Date complete = null;

        String parts [] = time.split(":");
        if (parts != null && parts.length == 3){
            int hora = new Integer(parts[0]);
            int min = new Integer(parts[1]);
            int sec = new Integer(parts[2]);

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hora);
            cal.set(Calendar.MINUTE, min);
            cal.set(Calendar.SECOND, sec);
            complete = cal.getTime();
        }

        return complete;
    }

    public static String getFormattedDate(Date date, String format){
        Format formatotimeStamp;
        //Date date = new Date();
        //formatotimeStamp = new SimpleDateFormat("yyyyMMddHHmmss");
        formatotimeStamp = new SimpleDateFormat(format);
        return formatotimeStamp.format(date);
    }
    
    public static Date getDateFromString(String dateStr, String format) throws ParseException{
        Format formatotimeStamp;

        formatotimeStamp = new SimpleDateFormat(format);
        Date date = (Date)formatotimeStamp.parseObject(dateStr);

        return date;

    }

    public static Date getTruncatedDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date add(Date d, int cant, int field){
        Calendar c = Calendar.getInstance();

        c.clear();
        c.setTime(d);
        c.add(field, cant);

        return c.getTime();
    }
}
