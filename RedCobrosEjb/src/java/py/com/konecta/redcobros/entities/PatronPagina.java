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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "PATRON_PAGINA")
@NamedQueries({@NamedQuery(name = "PatronPagina.findAll", query = "SELECT p FROM PatronPagina p"), @NamedQuery(name = "PatronPagina.findByIdPatron", query = "SELECT p FROM PatronPagina p WHERE p.idPatron = :idPatron"), @NamedQuery(name = "PatronPagina.findByUrl", query = "SELECT p FROM PatronPagina p WHERE p.url = :url"), @NamedQuery(name = "PatronPagina.findByError", query = "SELECT p FROM PatronPagina p WHERE p.error = :error"), @NamedQuery(name = "PatronPagina.findByInicial", query = "SELECT p FROM PatronPagina p WHERE p.inicial = :inicial")})
public class PatronPagina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PATRON")
    private Integer idPatron;
    @Basic(optional = false)
    @Column(name = "URL")
    private String url;
    @Basic(optional = false)
    @Column(name = "ERROR")
    private short error;
    @Basic(optional = false)
    @Column(name = "INICIAL")
    private char inicial;
    @JoinColumn(name = "ID_GRUPO_PAGINA", referencedColumnName = "ID_GRUPO_PAGINA")
    @ManyToOne(optional = false)
    private GrupoPagina idGrupoPagina;
    @OneToMany(mappedBy = "idPatronSiguiente")
    private Collection<PatronPagina> patronPaginaCollection;
    @JoinColumn(name = "ID_PATRON_SIGUIENTE", referencedColumnName = "ID_PATRON")
    @ManyToOne
    private PatronPagina idPatronSiguiente;
    @OneToMany(mappedBy = "idPatron")
    private Collection<LogTransaccionRc> logTransaccionRcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patronPagina")
    private Collection<ControlPagina> controlPaginaCollection;

    public PatronPagina() {
    }

    public PatronPagina(Integer idPatron) {
        this.idPatron = idPatron;
    }

    public PatronPagina(Integer idPatron, String url, short error, char inicial) {
        this.idPatron = idPatron;
        this.url = url;
        this.error = error;
        this.inicial = inicial;
    }

    public Integer getIdPatron() {
        return idPatron;
    }

    public void setIdPatron(Integer idPatron) {
        this.idPatron = idPatron;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public short getError() {
        return error;
    }

    public void setError(short error) {
        this.error = error;
    }

    public char getInicial() {
        return inicial;
    }

    public void setInicial(char inicial) {
        this.inicial = inicial;
    }

    public GrupoPagina getIdGrupoPagina() {
        return idGrupoPagina;
    }

    public void setIdGrupoPagina(GrupoPagina idGrupoPagina) {
        this.idGrupoPagina = idGrupoPagina;
    }

    public Collection<PatronPagina> getPatronPaginaCollection() {
        return patronPaginaCollection;
    }

    public void setPatronPaginaCollection(Collection<PatronPagina> patronPaginaCollection) {
        this.patronPaginaCollection = patronPaginaCollection;
    }

    public PatronPagina getIdPatronSiguiente() {
        return idPatronSiguiente;
    }

    public void setIdPatronSiguiente(PatronPagina idPatronSiguiente) {
        this.idPatronSiguiente = idPatronSiguiente;
    }

    public Collection<LogTransaccionRc> getLogTransaccionRcCollection() {
        return logTransaccionRcCollection;
    }

    public void setLogTransaccionRcCollection(Collection<LogTransaccionRc> logTransaccionRcCollection) {
        this.logTransaccionRcCollection = logTransaccionRcCollection;
    }

    public Collection<ControlPagina> getControlPaginaCollection() {
        return controlPaginaCollection;
    }

    public void setControlPaginaCollection(Collection<ControlPagina> controlPaginaCollection) {
        this.controlPaginaCollection = controlPaginaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPatron != null ? idPatron.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatronPagina)) {
            return false;
        }
        PatronPagina other = (PatronPagina) object;
        if ((this.idPatron == null && other.idPatron != null) || (this.idPatron != null && !this.idPatron.equals(other.idPatron))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.PatronPagina[idPatron=" + idPatron + "]";
    }

}
