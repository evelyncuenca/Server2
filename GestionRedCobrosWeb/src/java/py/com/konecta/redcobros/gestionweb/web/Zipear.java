/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.web;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author konecta
 */
public class Zipear {

    private int BUFFER_SIZE = 2048;

    private String filename_txt = "";

    private String filename_zip = "";

    public Zipear(String filename_txt, String filename_zip) {
        this.filename_txt = filename_txt;
        this.filename_zip = filename_zip;
//        this.configurarLog();

    }

    public void comprimir() {

        String[] files = new String[1];

        files[0] = new String(this.filename_txt);

        try {
            // Reference to the file we will be adding to the zipfile
            BufferedInputStream origin = null;
            // Reference to our zip file
            FileOutputStream dest = new FileOutputStream(this.filename_zip);
            // Wrap our destination zipfile with a ZipOutputStream
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            // Create a byte[] buffer that we will read data

            // from the source
            // files into and then transfer it to the zip file
            byte[] data = new byte[this.BUFFER_SIZE];

            // Iterate over all of the files in our list
            for (String f : files) {
                // Get a BufferedInputStream that we can use to read the

                // source file
                filename_zip = f;
                FileInputStream fi = new FileInputStream(filename_zip);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);

                // Setup the entry in the zip file
                String[] partes = filename_zip.split("/");
                filename_zip = partes[partes.length - 1];
                ZipEntry entry = new ZipEntry(filename_zip);
                out.putNextEntry(entry);

                // Read data from the source file and write it out to the zip file
                int count;
                while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                    out.write(data, 0, count);
                }
                // Close the source file
                origin.close();
                fi.close();
            }

            // Close the zip file
            out.close();
            dest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void descomprimir() {
        String destination = "/";

        try {

            // Create a ZipInputStream to read the zip file
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(filename_zip);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));

            // Loop over all of the entries in the zip file
            int count;
            byte data[] = new byte[BUFFER_SIZE];
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {

                if (!entry.isDirectory()) {

                    String entryName = entry.getName();
                    String destFN = destination + entry.getName();

                    // Write the file to the file system
                    FileOutputStream fos = new FileOutputStream(destFN);
                    dest = new BufferedOutputStream(fos, BUFFER_SIZE);

                    while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
                        dest.write(data, 0, count);
                    }

                    dest.flush();
                    dest.close();

                }
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviar() {
        String destination = "/";

        StringBuffer sb = new StringBuffer();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filename_zip));
            String linea = reader.readLine();
            while (linea != null) {
                sb.append(linea);
                linea = reader.readLine();
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void configurarLog() {
//        Properties properties = new Properties();
//
//        properties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
//        properties.setProperty("log4j.appender.stdout.Threshold", "DEBUG");
//        properties.setProperty("log4j.appender.stdout.ImmediateFlush", "true");
//        properties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
//        properties.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%-5p [%d{ISO8601}] [%t] (%M [%C:%L]) - %m%n");
//        properties.setProperty("log4j.rootCategory","DEBUG, stdout");
//
//        org.apache.log4j.PropertyConfigurator.configure(properties);
//    }
//    public static void main(String[] args) {
//
//        Properties properties = new Properties();
//
//        properties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
//        properties.setProperty("log4j.appender.stdout.Threshold", "DEBUG");
//        properties.setProperty("log4j.appender.stdout.ImmediateFlush", "true");
//        properties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
//        properties.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%-5p [%d{ISO8601}] [%t] (%M [%C:%L]) - %m%n");
//        properties.setProperty("log4j.rootCategory","DEBUG, stdout");
//
//        org.apache.log4j.PropertyConfigurator.configure(properties);
//
//        Zipear z = new Zipear( "/var/opt/numi/TCEMAIL/mail/1_20090714.txt", "/var/opt/numi/TCEMAIL/mail/1_20090714.zip");
//        z.comprimir();
//
//    }
}
