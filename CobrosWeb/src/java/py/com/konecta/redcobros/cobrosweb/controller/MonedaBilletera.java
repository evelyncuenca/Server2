/*
 * Moneda.java
 *
 * Created on 12 de abril de 2007, 11:17 AM
 *
 * Clase creada para el manejo de las monedas con puntos de miles, 
 * decimales, etc.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

/**
 *
 * @author Klaus
 */
public class MonedaBilletera {

    private long numero = 0;
    private long decimal = 0;
    private String numcpm = "";

    private String numoriginal = "";

    /**
     * Creates a new instance of Moneda
     */
    public MonedaBilletera(String moneda) {
        this.numoriginal = moneda;
        //System.out.println("moneda: "+ moneda );
        try {
            if ((moneda != null) && (!moneda.equalsIgnoreCase(""))) {
                int i, length, pos = moneda.indexOf(".");
                if (pos > 0) {
                    this.numero = (long) Long.parseLong(moneda.substring(0, pos));
                    this.decimal = (long) Long.parseLong(moneda.substring(pos + 1));
                    moneda = moneda.substring(0, pos);
                } else {
                    this.numero = (long) Long.parseLong(moneda);
                }

                length = moneda.length();

                for (i = 1; i < length; i++) {
                    this.numcpm = moneda.charAt(length - i) + this.numcpm;
                    if (i % 3 == 0) {
                        this.numcpm = "." + this.numcpm;
                    }
                }
                this.numcpm = moneda.charAt(0) + this.numcpm;
            } else {
                this.numcpm = "0";
            }
        } catch (Exception e) {
            this.decimal = 0;
            this.numcpm = "";
        }
    }

    public String toString() {
        if (this.decimal > 0) {
            return this.numcpm + "," + this.decimal;
        }
        return this.numcpm;
    }

    public String toStringOriginal() {
        return this.numoriginal;
    }

}
