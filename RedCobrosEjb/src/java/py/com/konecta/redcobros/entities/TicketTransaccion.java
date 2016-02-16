/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brojas
 */
@Entity
@Table(name = "TICKET_TRANSACCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicketTransaccion.findAll", query = "SELECT t FROM TicketTransaccion t"),
    @NamedQuery(name = "TicketTransaccion.findByIdTransaccion", query = "SELECT t FROM TicketTransaccion t WHERE t.ticketTransaccionPK.idTransaccion = :idTransaccion"),
    @NamedQuery(name = "TicketTransaccion.findByIdTipoOperacion", query = "SELECT t FROM TicketTransaccion t WHERE t.ticketTransaccionPK.idTipoOperacion = :idTipoOperacion"),
    @NamedQuery(name = "TicketTransaccion.findByTicket", query = "SELECT t FROM TicketTransaccion t WHERE t.ticket = :ticket")})
public class TicketTransaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TicketTransaccionPK ticketTransaccionPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "TICKET")
    private String ticket;

    public TicketTransaccion() {
    }

    public TicketTransaccion(TicketTransaccionPK ticketTransaccionPK) {
        this.ticketTransaccionPK = ticketTransaccionPK;
    }

    public TicketTransaccion(TicketTransaccionPK ticketTransaccionPK, String ticket) {
        this.ticketTransaccionPK = ticketTransaccionPK;
        this.ticket = ticket;
    }

    public TicketTransaccion(Long idTransaccion, Short idTipoOperacion) {
        this.ticketTransaccionPK = new TicketTransaccionPK(idTransaccion, idTipoOperacion);
    }

    public TicketTransaccionPK getTicketTransaccionPK() {
        return ticketTransaccionPK;
    }

    public void setTicketTransaccionPK(TicketTransaccionPK ticketTransaccionPK) {
        this.ticketTransaccionPK = ticketTransaccionPK;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketTransaccionPK != null ? ticketTransaccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketTransaccion)) {
            return false;
        }
        TicketTransaccion other = (TicketTransaccion) object;
        if ((this.ticketTransaccionPK == null && other.ticketTransaccionPK != null) || (this.ticketTransaccionPK != null && !this.ticketTransaccionPK.equals(other.ticketTransaccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.TicketTransaccion[ ticketTransaccionPK=" + ticketTransaccionPK + " ]";
    }
    
}
