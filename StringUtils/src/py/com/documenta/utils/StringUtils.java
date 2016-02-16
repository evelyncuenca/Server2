/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Ystmio Gaona
 */
public class StringUtils {

    public static String lpad(String src, String ch, int len) {
        StringBuilder l = new StringBuilder();

        //System.out.println("lpad src:" + src);
        //System.out.println("lpad ch:" + ch);
        //System.out.println("lpad len:" + String.valueOf(len));

        if (len > src.length()) {
            for (int i = 0; i < len - src.length(); i++) {
                l.append(ch);
            }
        }
        l.append(src);
        //System.out.println("StringUtils.lpad: " + l.toString());

        return l.toString();
    }

    public static String rpad(String src, String ch, int len) {
        String l = src;

        for (int i = 0; i < len - src.length(); i++) {
            l = l + ch;
        }
        return l;
    }

    public static String ltrim(String src, char chr) {
        int i = 0;
        for (; i < src.length(); i++) {
            if (src.charAt(i) != chr) {
                break;
            }
        }
        return src.substring(i);
    }

    //12500
    public static String rtrim(String src, char chr) {
        int i = src.length() - 1;
        for (; i >= 0; i--) {
            if (src.charAt(i) != chr) {
                break;
            }
        }
        return src.substring(0, i + 1);
    }
    
    public static String[] splitBySize(String src, int size) {
        int portions = src.length() / size;

        String[] stringSplited = new String[portions == 0 ? 1 : portions];
        int j = 0;

        for (int i = 0; i < src.length(); i += size) {
            stringSplited[j++] = src.substring(i, i + size);
        }

        return stringSplited;
    }
    
    public static String getMd5File(File file) {
        try {
            InputStream fin = new FileInputStream(file);
            java.security.MessageDigest md5er = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int read;

            do {
                read = fin.read(buffer);
                if (read > 0) {
                    md5er.update(buffer, 0, read);
                }
            } while (read != -1);
            fin.close();

            byte[] digest = md5er.digest();
            if (digest == null) {
                return null;
            }

            String strDigest = "";
            for (int i = 0; i < digest.length; i++) {
                strDigest += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1).toLowerCase();
            }
            return strDigest;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMd5String(String input) {
        String res = "";

        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");

            algorithm.reset();
            algorithm.update(input.getBytes());

            byte[] md5 = algorithm.digest();

            String tmp = "";
            for (int i = 0; i < md5.length; i++) {
                tmp = (Integer.toHexString(0xFF & md5[i]));
                if (tmp.length() == 1) {
                    res += "0" + tmp;
                } else {
                    res += tmp;
                }
            }
        } catch (NoSuchAlgorithmException ex) {
        }
        return res;
    }

    public static String getAdler(byte[] bytes) {
        Integer a = 1;
        Integer b = 0;
        String hex1 = "";
        String hex2 = "";

        for (int i = 0; i < bytes.length; i++) {
            a = a + bytes[i];
            b = a + b;
        }
        hex1 = Integer.toString(b, 16);
        hex2 = Integer.toString(a, 16);

        for (int i = 3; i > 0; i--) {
            if ((hex2.length() - 1) < i) {
                hex2 = "0" + hex2;
            }
        }
        return hex1 + hex2;
    }

    public static String getHashSet(String input) {
        return getAdler(getMd5String(input).getBytes());
    }

    public static String getHashSet(File input) {
        return getAdler(getMd5File(input).getBytes());
    }    
}
