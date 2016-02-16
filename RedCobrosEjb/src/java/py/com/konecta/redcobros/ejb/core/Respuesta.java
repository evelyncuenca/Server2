/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.core;

import java.util.Date;
import py.com.konecta.redcobros.entities.Grupo;
import py.com.konecta.redcobros.entities.LogHistoricoTrans;

/**
 *
 * @author Klaus
 */
public class Respuesta {

    private Long idRespuesta = 0L;
    private String descRespuesta;
    private Date fechaHoraRed;
    private Long idTransaccion;
    private String mensajePie;
    private String ticket;
    private String certificacion    ;




    private LogHistoricoTrans lht;
    private Date fechaHoraCore;
    private Grupo grupo;

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }



    public Date getFechaHoraCore() {
        return fechaHoraCore;
    }

    public void setFechaHoraCore(Date fechaHoraCore) {
        this.fechaHoraCore = fechaHoraCore;
    }

    public LogHistoricoTrans getLht() {
        return lht;
    }

    public void setLht(LogHistoricoTrans lht) {
        this.lht = lht;
    }

    @Override
    public String toString() {
        return "idRespuesta=" + idRespuesta + ";descRespuesta=" + descRespuesta + ";fechaHoraRed=" + fechaHoraRed + ";idTransaccion=" + idTransaccion +";certificacion:"+certificacion+";ticket="+ticket+ ";mensajePie=" + mensajePie;
    }

    /**
     * @return the idRespuesta
     */
    public Long getIdRespuesta() {
        return idRespuesta;
    }

    /**
     * @param idRespuesta the idRespuesta to set
     */
    public void setIdRespuesta(Long idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    /**
     * @return the descRespuesta
     */
    public String getDescRespuesta() {
        return descRespuesta;
    }

    /**
     * @param descRespuesta the descRespuesta to set
     */
    public void setDescRespuesta(String descRespuesta) {
        this.descRespuesta = descRespuesta;
    }

    /**
     * @return the fechaHoraRed
     */
    public Date getFechaHoraRed() {
        return fechaHoraRed;
    }

    /**
     * @param fechaHoraRed the fechaHoraRed to set
     */
    public void setFechaHoraRed(Date fechaHoraRed) {
        this.fechaHoraRed = fechaHoraRed;
    }

    /**
     * @return the idTransaccion
     */
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * @param idTransaccion the idTransaccion to set
     */
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    /**
     * @return the mensajePie
     */
    public String getMensajePie() {
        return mensajePie;
    }

    /**
     * @param mensajePie the mensajePie to set
     */
    public void setMensajePie(String mensajePie) {
        this.mensajePie = mensajePie;
    }

    /**
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the certificacion
     */
    public String getCertificacion() {
        return certificacion;
    }

    /**
     * @param certificacion the certificacion to set
     */
    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }
}
