/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.cobrosweb.clases;

/**
 *
 * @author gustavo
 */
public class CampoFallido {
    private String idCampo;
    private String mensajeError;
    private String posibleValor;
    private int tipoFallo;

    public static int VALOR_CORRECTO=0;
    public static int VALOR_INVALIDO=1;
    public static int VALOR_MANDATORIO=2;
    public static int VALOR_QUE_NO_SE_PUDO_CALCULAR=3;

    public int getTipoFallo() {
        return tipoFallo;
    }

    public void setTipoFallo(int tipoFallo) {
        this.tipoFallo = tipoFallo;
    }

    public String getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(String idCampo) {
        this.idCampo = idCampo;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getPosibleValor() {
        return posibleValor;
    }

    public void setPosibleValor(String posibleValor) {
        this.posibleValor = posibleValor;
    }

    public CampoFallido() {

    }
}
