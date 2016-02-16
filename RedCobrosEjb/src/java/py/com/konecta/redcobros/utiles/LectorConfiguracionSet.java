/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.utiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author konecta
 */
public class LectorConfiguracionSet {
    public static String SEPARADOR_TOKENS_TAB = "\t";
    public static String SEPARADOR_TOKENS_PUNTO_COMA = ";";
    public static String SEPARADOR_TOKENS_COMA = ",";
    public static int FORMATO_FECHA_DDMMYYYY = 1;
    public static int FORMATO_FECHA_MMDDYYYY = 2;

    public static String[] lineasDelArchivo(BufferedReader bf) throws IOException {
        List<String> lista = new ArrayList<String>();
        String linea;
        lista = new ArrayList<String>();
        while ((linea = bf.readLine()) != null) {
            lista.add(linea.trim().replaceAll("\"", ""));
        }
        bf.close();
        return lista.toArray(new String[0]);
    }

    public static String[] lineasDelArchivo(String pathArchivo) throws Exception {
        BufferedReader bf = new BufferedReader(new FileReader(pathArchivo));
        return lineasDelArchivo(bf);
    }

    public static String[] tokensDeLaLinea(String linea,String separador) {
        String[] tokens = null;
        tokens = linea.split(separador);
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }
        return tokens;
    }
    public static String MMDDYYYYtoFechaDDMMYYYY(String fecha) {
        String[] partes = fecha.split("/");
        String salida = "";
        //MM/DD/YYYY
        if (partes[1].length() == 1) {
            salida += "0" + partes[1];
        } else {
            salida += partes[1];
        }
        if (partes[0].length() == 1) {
            salida += "0" + partes[0];
        } else {
            salida += partes[0];
        }

        salida += partes[2];
        return salida;
    }

    public static String DDMMYYYYtoFechaDDMMYYYY(String fecha) {
        String[] partes = fecha.split("/");
        String salida = "";
        //DD/MM/YYYY
        if (partes[0].length() == 1) {
            salida += "0" + partes[0];
        } else {
            salida += partes[0];
        }
        if (partes[1].length() == 1) {
            salida += "0" + partes[1];
        } else {
            salida += partes[1];
        }

        salida += partes[2];
        return salida;
    }
}
