/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
public class SHAUtil {

    private static MessageDigest mdig;

    static {
        try {
            mdig = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public static byte[] getDigest(String text) {
        byte[] digest = mdig.digest(text.getBytes());
        return digest;

    }

//    public static void main(String[] args) {
//        byte[] b = SHAUtil.getDigest("99887766");
//        int i;
//        for (i=0; i<b.length; i++) {
//            System.out.printf("%d %x\n", new Object[] {Integer.valueOf(b[i]), Integer.valueOf(b[i])});
//        }
//    }
}
