/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.utiles;

/**
 *
 * @author konecta
 */

public class StringUtils {
    public static String lpad(String src, String ch, int len)
    {
        StringBuilder l = new StringBuilder();

        //System.out.println("lpad src:" + src);
        //System.out.println("lpad ch:" + ch);
        //System.out.println("lpad len:" + String.valueOf(len));

        if (len > src.length()){
            for (int i = 0; i<len - src.length() ;i++)
            {
                l.append(ch);
            }
        }
        l.append(src);
        //System.out.println("StringUtils.lpad: " + l.toString());

        return l.toString();
    }
    public static String rpad(String src, String ch, int len)
    {
        String l = src;

        for (int i = 0; i<len-src.length();i++)
        {
            l = l + ch;
        }
        return l;
    }

    public static String ltrim(String src, char chr){
        int i = 0;
        for (; i<src.length(); i++){
            if (src.charAt(i) != chr){
                break;
            }
        }
        return src.substring(i);
    }

    //12500
    public static String rtrim(String src, char chr){
        int i = src.length() - 1;
        for (; i>=0; i--){
            if (src.charAt(i) != chr){
                break;
            }
        }
        return src.substring(0, i+1);
    }
    
    public static String getVal(String cadena, String key) {
        String val = null;
        try {
            String[] datos = cadena.split("#");
            for (String it : datos) {
                if (it.contains(key)) {
                    String[] item = it.split("=");
                    val = item[1].trim();
                    break;
                }
            }
        } catch (Exception ex) {
            val = null;
        }
        return val;
    }

}

