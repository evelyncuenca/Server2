/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.utiles;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author konecta
 */
public class Utiles {

    public static String DEFAULT_ERROR_MESSAGE = "No se puede realizar la operación";

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
     public static String getElementValue(String cadena, String regex, int posicion){
        String array [] = cadena.split(regex);

        return array[posicion];
    }
     
     
}
