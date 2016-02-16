/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package org.appletinfo;

import java.applet.Applet;
import java.awt.Graphics;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author konecta
*/
public class AppletInfo extends Applet {

   /**
    * Initialization method that will be called after the applet is loaded
    * into the browser.
    */
   public void init() {
       // TODO start asynchronous download of heavy resources
   }


   public String getMac(Integer accion) {
       String respuesta = "";

       String mac = "";
       String mac2 = "";
       ArrayList<String> macList = new ArrayList<String> ();
       try {
           Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
           InetAddress ip;

           while (netInterfaces.hasMoreElements()) {
               NetworkInterface nii = (NetworkInterface) netInterfaces.nextElement();
               if (nii.getHardwareAddress() != null) {
                   byte[] adr = nii.getHardwareAddress();
                   Formatter formatter = new Formatter();

                   for (int i = 0; i < adr.length; i++) {
                       mac = formatter.format("%02X%s", adr[i], (i < adr.length - 1) ? "-" : "").toString();
                   }
                   mac2 += mac + "#";
                   macList.add(mac);

               }
               mac = "";
           }
       } catch (Exception e) {
           System.out.print("Exception: No se puede obtener la direccion MAC");
           Logger.getLogger(AppletInfo.class.getCanonicalName()).log(Level.SEVERE, null, e);
       }
       if (!mac2.isEmpty()) {

           System.out.print("Si se puede obtener la direccion MAC");
           if (accion == 1){
               Object [] macs = macList.toArray();
               Arrays.sort(macs);
               mac2 = "";
               for (Object o : macs) {
                   mac2 += o.toString() + "#";
               }
           }
           
            mac2 = mac2.substring(0, mac2.lastIndexOf("#"));
            respuesta = mac2;
            
        } else {
            respuesta = "No se puede obtener la direccion MAC";
            System.out.print("No se puede obtener la direccion MAC");
        }

        return respuesta;
    }

    public void paint(Graphics g) {}
}
