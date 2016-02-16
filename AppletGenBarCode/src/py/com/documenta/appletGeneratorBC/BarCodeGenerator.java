/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.appletGeneratorBC;

import java.applet.Applet;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author documenta
 */
public class BarCodeGenerator extends Applet {

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        // TODO start asynchronous download of heavy resources
    }

    public String[] genBarCodeFromFile(String path) {
        String datos[]= new String[2];
        
        String codigoBarraTigo = "";
        File file = null;
        FileReader fr = null;
        try {
            file = new File(path);
            fr = new FileReader(file);
            BufferedReader entrada = new BufferedReader(fr);
            //FileInputStream in = new FileInputStream(file);
            String registo = entrada.readLine();
            codigoBarraTigo = "01474"
                    + registo.substring(6, 8)//tipoComprobante
                    + registo.substring(8, 15)//Numero Comprobante o Contrato
                    // + linea.substring(69, 79)//nroCuenta
                    + "0000"//fill
                    + registo.substring(79, 87)//fecha
                    + registo.substring(15, 25) //monto
                    + "00"; //fill
            datos[0] = codigoBarraTigo;
            datos[1] = registo.substring(27, 57);
            Logger.getLogger(BarCodeGenerator.class.getName()).log(Level.INFO, "Codigo de barra generado [{0}]", codigoBarraTigo);
        } catch (Exception e) {
            datos = null;
            Logger.getLogger(BarCodeGenerator.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (file != null && file.exists()) {
                    if (!file.delete()) {
                        Logger.getLogger(BarCodeGenerator.class.getName()).log(Level.SEVERE, "No se pudo borrar el Archivo de Cobranza");
                    }
                }                
            } catch (Exception e2) {
                Logger.getLogger(BarCodeGenerator.class.getName()).log(Level.SEVERE, null, e2);
            }
        }
        return datos;
    }

    @Override
    public void paint(Graphics g) {
    }
}
