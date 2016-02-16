/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.utiles;

/**
 *
 * @author konecta
 */
public class ResultadoControlFranjaHoraria {

    Boolean entra;
    Long timeToEnd;
    Long timeStarted;

    public Long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Long timeStarted) {
        this.timeStarted = timeStarted;
    }

    public ResultadoControlFranjaHoraria() {
        entra = false;
        timeToEnd = 0L;
    }

    public Boolean getEntra() {
        return entra;
    }

    public void setEntra(Boolean entra) {
        this.entra = entra;
    }

    public Long getTimeToEnd() {
        return timeToEnd;
    }

    public void setTimeToEnd(Long timeToEnd) {
        this.timeToEnd = timeToEnd;
    }
}
