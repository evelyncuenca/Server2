/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.utiles;

import java.util.List;
import py.com.konecta.redcobros.entities.Terminal;
import py.com.konecta.redcobros.entities.UsuarioTerminal;

/**
 *
 * @author konecta
 */
public class ResultadoAutenticarCobrosWeb {

    String resultado;
    String motivo ;
    UsuarioTerminal ut;
    List<Terminal> lTerminalesUsuarioIn;

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public ResultadoAutenticarCobrosWeb(String resultado, UsuarioTerminal ut, List<Terminal> lTerminalesUsuarioIn) {
        this.resultado = resultado;
        this.ut = ut;
        this.lTerminalesUsuarioIn = lTerminalesUsuarioIn;
    }

    public List<Terminal> getLTerminalesUsuarioIn() {
        return lTerminalesUsuarioIn;
    }

    public void setLTerminalesUsuarioIn(List<Terminal> lTerminalesUsuarioIn) {
        this.lTerminalesUsuarioIn = lTerminalesUsuarioIn;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public UsuarioTerminal getUt() {
        return ut;
    }

    public void setUt(UsuarioTerminal ut) {
        this.ut = ut;
    }
}