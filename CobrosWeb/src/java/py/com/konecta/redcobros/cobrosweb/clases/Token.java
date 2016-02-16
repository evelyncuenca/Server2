/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.cobrosweb.clases;

/**
 *
 * @author gustavo
 */
public class Token {
    private String token;
    private int tipoToken;
    public static int TOKEN_CAMPO=1;
    public static int TOKEN_OPERACION_UNARIA=2;
    public static int TOKEN_OPERACION_BINARIA=3;
    public static int TOKEN_PARENTESIS=4;



    public int getTipoToken() {
        return tipoToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTipoToken(int tipoToken) {
        this.tipoToken = tipoToken;
    }

    @Override
    public String toString () {
        return token+"-"+tipoToken;
    }
}
