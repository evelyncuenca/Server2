/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.util;

import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.xml.datatype.XMLGregorianCalendar;


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


    public static Date getCompleteTime(String time) {
        Date complete = null;

        String parts[] = time.split(":");
        if (parts != null && parts.length == 3) {
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

    public static String getFormattedDate(Date date, String format) {
        Format formatotimeStamp;
        //Date date = new Date();
        //formatotimeStamp = new SimpleDateFormat("yyyyMMddHHmmss");
        formatotimeStamp = new SimpleDateFormat(format);
        return formatotimeStamp.format(date);
    }

    public static Date getDateFromString(String dateStr, String format) throws ParseException {
        Format formatotimeStamp;

        formatotimeStamp = new SimpleDateFormat(format);
        Date date = (Date) formatotimeStamp.parseObject(dateStr);

        return date;

    }

    public static Date getTruncatedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date add(Date d, int cant, int field) {
        Calendar c = Calendar.getInstance();

        c.clear();
        c.setTime(d);
        c.add(field, cant);

        return c.getTime();
    }

    public static int getDateHabiles(String FechaIni, int Dias) {
        //Esta es la Fecha que se recibe
        int countd = 0;
        int contapasadas = 0;
        int iYear = Integer.parseInt(FechaIni.substring(4));
        int iMonth = Integer.parseInt(FechaIni.substring(2, 4));
        int iDay = Integer.parseInt(FechaIni.substring(0, 2));
        Calendar cldInicio = Calendar.getInstance();
        cldInicio.setFirstDayOfWeek(cldInicio.MONDAY);
        //declaro variables para la obtencion del dia de la semana
        int iDiaSemana = 0;
        //hacemos el recorrido de todos los dias quitando los sabados y domingos
        while (Dias >= countd) {
            contapasadas++;
            System.out.println("Dia" + Dias + "!=Countd" + countd);
            cldInicio.set(iYear, iMonth - 1, iDay);
            iDiaSemana = cldInicio.get(cldInicio.DAY_OF_WEEK);
            System.out.println("Dia de la semana impresa=" + iDiaSemana);
            if (iDiaSemana == 1 || iDiaSemana == 7) {
                System.out.println("Entro en el if");
            } else {

                countd++; // se le suma uno al contador del while
                //System.out.println("Entro en el else");

            }
            iDay++; //se le suma uno al dia para recorrerlo no importa el numero quesea en la semana 

            //System.out.println("Es numero de conteos del while="+countd);  
            //System.out.println("Numero de Dias iDays="+iDay);                    
        }

//        System.out.println("Final Es numero de conteos del while=" + countd);
//        System.out.println("Final Numero de Dias iDays=" + iDay);
//        System.out.println("Conteo de pasadas=" + contapasadas);

        return contapasadas - 1;
    }

    public static int getDateHabiles(Calendar fechaIni, int dias) {
        int c = dias;
        int diaDeLaSemana = 0;
        for (int i = 0; i < dias; i++) {
            fechaIni.add(Calendar.DAY_OF_WEEK, 1);
            diaDeLaSemana = fechaIni.get(Calendar.DAY_OF_WEEK);
            if (diaDeLaSemana == Calendar.SATURDAY || diaDeLaSemana == Calendar.SUNDAY) {
                i--;
                c++;
            }
        }
        return c;
    }

    public static Calendar getCalendar(XMLGregorianCalendar xmlCalendar) {        
        TimeZone timeZone = xmlCalendar.getTimeZone(xmlCalendar.getTimezone());
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.YEAR, xmlCalendar.getYear());
        calendar.set(Calendar.MONTH, xmlCalendar.getMonth() - 1);
        calendar.set(Calendar.DATE, xmlCalendar.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, xmlCalendar.getHour());
        calendar.set(Calendar.MINUTE, xmlCalendar.getMinute());
        calendar.set(Calendar.SECOND, xmlCalendar.getSecond());
        return calendar;
    }
}
