/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "TERMINAL")
@NamedQueries({
    @NamedQuery(name = "Terminal.findAll", query = "SELECT t FROM Terminal t")})
public class Terminal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TERMINAL")
    @GeneratedValue(generator = "terminalSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "terminalSeq", table = "SECUENCIA",
            allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
            pkColumnValue = "terminalSeq", initialValue = 1)
    private Long idTerminal;
    @Column(name = "CODIGO_HASH")
    private String codigoHash;
    @Column(name = "URL_IMPRESORA")
    private String urlImpresora;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "SUCURSAL", referencedColumnName = "ID_SUCURSAL")
    @ManyToOne(optional = false)
    private Sucursal sucursal;
    @Basic(optional = false)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "terminal")
    private Collection<UsuarioTerminal> usuarioTerminalCollection;
    @Basic(optional = false)
    @Column(name = "PROXIMO_NRO_GESTION")
    private Integer proximoNroGestion;
    @Basic(optional = false)
    @Column(name = "CODIGO_TERMINAL")
    private Integer codigoTerminal;
    @Basic(optional = false)
    @Column(name = "CODIGO_CAJA_SET")
    private Integer codigoCajaSet;
    @Column(name = "CONSECUTIVO_ACTUAL_FORM")
    private Long consecutivoActualForm;
    @Column(name = "CONSECUTIVO_ACTUAL_BP")
    private Long consecutivoActualBP;
    @Column(name = "FECHA_CONSECUTIVO_SET")
    @Temporal(TemporalType.DATE)
    private Date fechaConsecutivoSet;

    public Long getConsecutivoActualBP() {
        return consecutivoActualBP;
    }

    public void setConsecutivoActualBP(Long consecutivoActualBP) {
        this.consecutivoActualBP = consecutivoActualBP;
    }

    public Long getConsecutivoActualForm() {
        return consecutivoActualForm;
    }

    public void setConsecutivoActualForm(Long consecutivoActualForm) {
        this.consecutivoActualForm = consecutivoActualForm;
    }

    public Date getFechaConsecutivoSet() {
        return fechaConsecutivoSet;
    }

    public void setFechaConsecutivoSet(Date fechaConsecutivoSet) {
        this.fechaConsecutivoSet = fechaConsecutivoSet;
    }

    public Integer getCodigoCajaSet() {
        return codigoCajaSet;
    }

    public void setCodigoCajaSet(Integer codigoCajaSet) {
        this.codigoCajaSet = codigoCajaSet;
    }

    public Integer getCodigoTerminal() {
        return codigoTerminal;
    }

    public void setCodigoTerminal(Integer codigoTerminal) {
        this.codigoTerminal = codigoTerminal;
    }

    public Integer getProximoNroGestion() {
        return proximoNroGestion;
    }

    public void setProximoNroGestion(Integer proximoNroGestion) {
        this.proximoNroGestion = proximoNroGestion;
    }

    public Terminal() {
    }

    public Terminal(Long idTerminal) {
        this.idTerminal = idTerminal;
    }

    public Terminal(Long idTerminal, String descripcion) {
        this.idTerminal = idTerminal;
        this.descripcion = descripcion;
    }

    public Long getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(Long idTerminal) {
        this.idTerminal = idTerminal;
    }

    public String getCodigoHash() {
        return codigoHash;
    }

    public void setCodigoHash(String hash) {
        this.codigoHash = hash;
    }

    public String getUrlImpresora() {
        return urlImpresora;
    }

    public void setUrlImpresora(String urlImpresora) {
        this.urlImpresora = urlImpresora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Collection<UsuarioTerminal> getUsuarioTerminalCollection() {
        return usuarioTerminalCollection;
    }

    public void setUsuarioTerminalCollection(Collection<UsuarioTerminal> usuarioTerminalCollection) {
        this.usuarioTerminalCollection = usuarioTerminalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTerminal != null ? idTerminal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terminal)) {
            return false;
        }
        Terminal other = (Terminal) object;
        if ((this.idTerminal == null && other.idTerminal != null) || (this.idTerminal != null && !this.idTerminal.equals(other.idTerminal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Terminal{" + "idTerminal=" + idTerminal + ", codigoHash=" + codigoHash + ", urlImpresora=" + urlImpresora + ", descripcion=" + descripcion + ", sucursal=" + sucursal + ", proximoNroGestion=" + proximoNroGestion + ", codigoTerminal=" + codigoTerminal + ", codigoCajaSet=" + codigoCajaSet + ", consecutivoActualForm=" + consecutivoActualForm + ", consecutivoActualBP=" + consecutivoActualBP + ", fechaConsecutivoSet=" + fechaConsecutivoSet + '}';
    }

}
