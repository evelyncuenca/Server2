/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.cobrosweb.utiles;

/**
 *
 * @author konecta
 */
public class RespuestaFiltro {
    Boolean success ;
    String motivo ;

    public RespuestaFiltro(){
        success = false;
        motivo = null;

    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


}
