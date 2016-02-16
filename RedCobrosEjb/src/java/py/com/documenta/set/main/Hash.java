/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.main;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
public class Hash {

    static final int BUFFER = 2048;
    private MessageDigest md = null;
    static private Hash md5 = null;
    private static final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private Hash() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
    }

    public static Hash getInstance() throws NoSuchAlgorithmException {
        if (md5 == null) {
            md5 = new Hash();
        }
        return (md5);
    }

    public String getHash(byte[] dataToHash) {
        return hexStringFromBytes((calculateHash(dataToHash)));
    }

    public String getHash(InputStream iStream) throws IOException {
        return hexStringFromBytes((calculateHash(iStream)));
    }

    public byte[] calculateHash(byte[] dataToHash) {
        md.update(dataToHash, 0, dataToHash.length);
        return (md.digest());

    }

    public byte[] calculateHash(InputStream iStream) throws IOException {
        DigestInputStream dIStream = new DigestInputStream(iStream, md);
        byte[] buffer = new byte[8192];
        while (dIStream.read(buffer) != -1);
        return md.digest();
    }

    private String hexStringFromBytes(byte[] b) {
        String hex = "";
        int msb;
        int lsb = 0;
        int i;
        for (i = 0; i < b.length; i++) {
            msb = ((int) b[i] & 0x000000FF) / 16;
            lsb = ((int) b[i] & 0x000000FF) % 16;
            hex = hex + hexChars[msb] + hexChars[lsb];
        }
        return (hex);
    }

    public String getAdler(byte[] bytes) {
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

    public String getMD5(String cadena) {
        return getHash(cadena.getBytes());
    }

}
