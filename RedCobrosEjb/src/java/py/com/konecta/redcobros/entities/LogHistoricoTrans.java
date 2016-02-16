/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "LOG_HISTORICO_TRANS")
@NamedQueries({@NamedQuery(name = "LogHistoricoTrans.findAll", query = "SELECT l FROM LogHistoricoTrans l")})
public class LogHistoricoTrans implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LOG_HISTORICO_TRANS")
    @GeneratedValue(generator = "logHistoricoTransSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "logHistoricoTransSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="logHistoricoTransSeq",initialValue=1)
    private Long idLogHistoricoTrans;
    @JoinColumn(name = "TRANSACCION", referencedColumnName = "ID_TRANSACCION")
    @ManyToOne
    private Transaccion transaccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLogHistoricoTrans")
    private Collection<LogHistorico> logHistoricoCollection;

    public LogHistoricoTrans() {
    }

    public LogHistoricoTrans(Long idLogHistoricoTrans) {
        this.idLogHistoricoTrans = idLogHistoricoTrans;
    }

    public Long getIdLogHistoricoTrans() {
        return idLogHistoricoTrans;
    }

    public void setIdLogHistoricoTrans(Long idLogHistoricoTrans) {
        this.idLogHistoricoTrans = idLogHistoricoTrans;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public Collection<LogHistorico> getLogHistoricoCollection() {
        return logHistoricoCollection;
    }

    public void setLogHistoricoCollection(Collection<LogHistorico> logHistoricoCollection) {
        this.logHistoricoCollection = logHistoricoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogHistoricoTrans != null ? idLogHistoricoTrans.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogHistoricoTrans)) {
            return false;
        }
        LogHistoricoTrans other = (LogHistoricoTrans) object;
        if ((this.idLogHistoricoTrans == null && other.idLogHistoricoTrans != null) || (this.idLogHistoricoTrans != null && !this.idLogHistoricoTrans.equals(other.idLogHistoricoTrans))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.LogHistoricoTrans[idLogHistoricoTrans=" + idLogHistoricoTrans + "]";
    }

}
