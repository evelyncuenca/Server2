/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import py.com.konecta.redcobros.utiles.Constantes;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "FORM_CONTRIB")
@NamedQueries({
    @NamedQuery(name = "FormContrib.findAll", query = "SELECT f FROM FormContrib f")})
public class FormContrib implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "formContribSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "formContribSeq", table = "SECUENCIA",
            allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
            pkColumnValue = "formContribSeq", initialValue = 1)
    @Column(name = "ID_FORM_CONTRIB")
    private Long idFormContrib;
    @Basic(optional = false)
    @Column(name = "RUC")
    private String ruc;
    @Basic(optional = false)
    @Column(name = "DIGITO_VERIFICADOR")
    private String digitoVerificador;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_RECEPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRecepcion;
    @Basic(optional = false)
    @Column(name = "TOTAL_PAGAR")
    private Integer totalPagar;
    @Basic(optional = false)
    @Column(name = "CAMPOS_VALIDOS")
    private Integer camposValidos;
    @Basic(optional = false)
    @Column(name = "PAGADO")
    private Integer pagado;
    @Basic(optional = false)
    @Column(name = "FECHA_PRESENTACION")
    @Temporal(TemporalType.DATE)
    private Date fechaPresentacion;
    @Column(name = "PERIODO_FISCAL")
    private String periodoFiscal;
    @Column(name = "NUMERO_ORDEN")
    private Long numeroOrden;
    @Column(name = "CODIGO_HASH")
    private String codigoHash;
    @Column(name = "CONSECUTIVO")
    private Long consecutivo;
    @JoinColumn(name = "CONTRIBUYENTE", referencedColumnName = "ID_CONTRIBUYENTE")
    @ManyToOne(optional = false)
    private Contribuyentes contribuyente;
    @JoinColumn(name = "FORMULARIO", referencedColumnName = "ID_FORMULARIO")
    @ManyToOne(optional = false)
    private Formulario formulario;
    @JoinColumn(name = "TRANSACCION", referencedColumnName = "ID_TRANSACCION")
    @OneToOne
    private Transaccion transaccion;
    @OneToMany(mappedBy = "camposFormContribPK.formContrib", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})//, fetch=FetchType.EAGER)
    @OrderBy(value = "numeroCampo")
    private Collection<CamposFormContrib> camposFormContribCollection;
    @OneToMany(mappedBy = "formContrib")
    private Collection<BoletaPagoContrib> boletaPagoContribCollection;
    @Column(name = "POS_GRUPO")
    private Integer posGrupo;
    @JoinColumn(name = "GRUPO", referencedColumnName = "ID_GRUPO")
    @ManyToOne
    private Grupo grupo;
    @ManyToOne
    @JoinColumn(name = "USUARIO_TERMINAL_CARGA", referencedColumnName = "ID_USUARIO_TERMINAL")
    private UsuarioTerminal usuarioTerminalCarga;
    @Basic(optional = false)
    @Column(name = "CERTIFICADO", length = 1)
    private String certificado;
    @Column(name = "FECHA_HORA_REAL_CERTIFICADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRealCertificado;
    @Column(name = "FECHA_CERTIFICADO_SET")
    @Temporal(TemporalType.DATE)
    private Date fechaCertificadoSet;
    @Column(name = "FECHA_RECIBIDO")
    @Temporal(TemporalType.DATE)
    private Date fechaRecibido;
    @Column(name = "FECHA_CONTROLADO")
    @Temporal(TemporalType.DATE)
    private Date fechaControlado;
    @Column(name = "ENVIADO", length = 1)
    private String enviado;
    @Column(name = "GESTOR")
    private String gestor;
    @Column(name = "MIGRADO", length = 1)
    private Character migrado;
    @Column(name = "TRANSACCION_RC")
    private Long transaccionRc;
    @Column(name = "FLG_PAGO_ONLINE")
    private Integer flgPagoOnline;

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }

    public Date getFechaControlado() {
        return fechaControlado;
    }

    public void setFechaControlado(Date fechaControlado) {
        this.fechaControlado = fechaControlado;
    }

    public Date getFechaRecibido() {
        return fechaRecibido;
    }

    public void setFechaRecibido(Date fechaRecibido) {
        this.fechaRecibido = fechaRecibido;
    }

    public Date getFechaCertificadoSet() {
        return fechaCertificadoSet;
    }

    public void setFechaCertificadoSet(Date fechaCertificadoSet) {
        this.fechaCertificadoSet = fechaCertificadoSet;
    }

    public Date getFechaHoraRealCertificado() {
        return fechaHoraRealCertificado;
    }

    public void setFechaHoraRealCertificado(Date fechaHoraRealCertificado) {
        this.fechaHoraRealCertificado = fechaHoraRealCertificado;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public Collection<BoletaPagoContrib> getBoletaPagoContribCollection() {
        return boletaPagoContribCollection;
    }

    public void setBoletaPagoContribCollection(Collection<BoletaPagoContrib> boletaPagoContribCollection) {
        this.boletaPagoContribCollection = boletaPagoContribCollection;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Integer getPosGrupo() {
        return posGrupo;
    }

    public void setPosGrupo(Integer posGrupo) {
        this.posGrupo = posGrupo;
    }

    public FormContrib() {
    }

    public FormContrib(Long idFormContrib) {
        this.idFormContrib = idFormContrib;
    }

    public Long getIdFormContrib() {
        return idFormContrib;
    }

    public void setIdFormContrib(Long idFormContrib) {
        this.idFormContrib = idFormContrib;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    public Date getFechaHoraRecepcion() {
        return fechaHoraRecepcion;
    }

    public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
    }

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }

    public Integer getCamposValidos() {
        return camposValidos;
    }

    public void setCamposValidos(Integer camposValidos) {
        this.camposValidos = camposValidos;
    }

    public Integer getPagado() {
        return pagado;
    }

    public void setPagado(Integer pagado) {
        this.pagado = pagado;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getPeriodoFiscal() {
        return periodoFiscal;
    }

    public void setPeriodoFiscal(String periodoFiscal) {
        this.periodoFiscal = periodoFiscal;
    }

    public String getCodigoHash() {
        return codigoHash;
    }

    public void setCodigoHash(String codigoHash) {
        this.codigoHash = codigoHash;
    }

    public Long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Long getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Long numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Contribuyentes getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyentes contribuyente) {
        this.contribuyente = contribuyente;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public Collection<CamposFormContrib> getCamposFormContribCollection() {
        return camposFormContribCollection;
    }

    public void setCamposFormContribCollection(Collection<CamposFormContrib> camposFormContribCollection) {
        this.camposFormContribCollection = camposFormContribCollection;
    }

    public UsuarioTerminal getUsuarioTerminalCarga() {
        return usuarioTerminalCarga;
    }

    public void setUsuarioTerminalCarga(UsuarioTerminal usuarioTerminal) {
        this.usuarioTerminalCarga = usuarioTerminal;
    }

    public String getIdGestor() {
        return gestor;
    }

    public void setIdGestor(String gestor) {
        this.gestor = gestor;
    }

    public Long getTransaccionRc() {
        return transaccionRc;
    }

    public void setTransaccionRc(Long transaccionRc) {
        this.transaccionRc = transaccionRc;
    }

    public Integer getFlgPagoOnline() {
        return flgPagoOnline;
    }

    public void setFlgPagoOnline(Integer flgPagoOnline) {
        this.flgPagoOnline = flgPagoOnline;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormContrib != null ? idFormContrib.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (object == null || !(object instanceof FormContrib)) {
            return false;
        }
        FormContrib other = (FormContrib) object;
        if ((this.idFormContrib == null && other.idFormContrib != null) || (this.idFormContrib != null && !this.idFormContrib.equals(other.idFormContrib))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.FormContrib[idFormContrib=" + idFormContrib + "]";
    }

    public String toStringHash() {
        String cadena = "";
        //numero de orden
        cadena += UtilesSet.cerosIzquierda(numeroOrden, Constantes.TAM_PAGO_FORMULARIO_NUMERO_ORDEN);
        //formulario
        cadena += UtilesSet.cerosIzquierda(formulario.getNumeroFormulario().longValue(), Constantes.TAM_PAGO_FORMULARIO_FORMULARIO);
        //version
        cadena += UtilesSet.cerosIzquierda(formulario.getVersion().longValue(), Constantes.TAM_PAGO_FORMULARIO_VERSION);
        //fecha
        Format formatter = new SimpleDateFormat("ddMMyyyy");
        cadena += formatter.format(fechaCertificadoSet);
        //ruc
        cadena += UtilesSet.espaciosDerecha(contribuyente.getRucNuevo(), Constantes.TAM_PAGO_FORMULARIO_RUC);
        //digito verificador
        cadena += contribuyente.getDigitoVerificador();
        for (CamposFormContrib cfc : camposFormContribCollection) {
            if (!cfc.getValor().equalsIgnoreCase("0")) {
                cadena += cfc.toStringHash();
            }
        }
        return UtilesSet.getHashSet(cadena);
    }

    public String toStringHash(List<CamposFormContrib> campos) {
        String cadena = "";
        //numero de orden
        cadena += UtilesSet.cerosIzquierda(numeroOrden, Constantes.TAM_PAGO_FORMULARIO_NUMERO_ORDEN);
        //formulario
        cadena += UtilesSet.cerosIzquierda(formulario.getNumeroFormulario().longValue(), Constantes.TAM_PAGO_FORMULARIO_FORMULARIO);
        //version
        cadena += UtilesSet.cerosIzquierda(formulario.getVersion().longValue(), Constantes.TAM_PAGO_FORMULARIO_VERSION);
        //fecha
        Format formatter = new SimpleDateFormat("ddMMyyyy");
        cadena += formatter.format(fechaCertificadoSet);
        //ruc
        cadena += UtilesSet.espaciosDerecha(contribuyente.getRucNuevo(), Constantes.TAM_PAGO_FORMULARIO_RUC);
        //digito verificador
        cadena += contribuyente.getDigitoVerificador();
        for (CamposFormContrib cfc : campos) {
            if (!cfc.getValor().equalsIgnoreCase("0")) {
                cadena += cfc.toStringHash();
            }
        }
        return UtilesSet.getHashSet(cadena);
    }

    public String toStringERA(Integer numeroERA) {
        String cadena = "";
        //identificador del registro
        cadena += "20";
        //tipo de informacion, 1 declaracion jurada
        cadena += "1";
        //numero de ERA
        cadena += UtilesSet.cerosIzquierda(numeroERA.longValue(), Constantes.TAM_ERA);
        //codigo de sucursal
        cadena += UtilesSet.cerosIzquierda(
                this.getGrupo().getGestion().getTerminal().getSucursal().getCodigoSucursalSet().longValue(),
                Constantes.TAM_SUCURSAL);
        //codigo del cajero
        cadena += UtilesSet.cerosIzquierda(
                this.getGrupo().getGestion().getTerminal().getCodigoCajaSet().longValue(),
                Constantes.TAM_CAJERO);
        //consecutivo
        cadena += UtilesSet.cerosIzquierda(
                consecutivo,
                Constantes.TAM_CONSECUTIVO);
        //numero de orden
        cadena += UtilesSet.cerosIzquierda(numeroOrden, Constantes.TAM_PAGO_FORMULARIO_NUMERO_ORDEN);
        //formulario
        cadena += UtilesSet.cerosIzquierda(formulario.getNumeroFormulario().longValue(), Constantes.TAM_PAGO_FORMULARIO_FORMULARIO);
        //version
        cadena += UtilesSet.cerosIzquierda(formulario.getVersion().longValue(), Constantes.TAM_PAGO_FORMULARIO_VERSION);
        //fecha
        Format formatter = new SimpleDateFormat("ddMMyyyy");
        cadena += formatter.format(fechaCertificadoSet);
        //ruc
        cadena += UtilesSet.espaciosDerecha(contribuyente.getRucNuevo(), Constantes.TAM_PAGO_FORMULARIO_RUC);
        //digito verificador
        cadena += contribuyente.getDigitoVerificador();
        //hash
        cadena += UtilesSet.espaciosDerecha(codigoHash, Constantes.TAM_HASH);
        return cadena;
    }

    /**
     * @return the migrado
     */
    public Character getMigrado() {
        return migrado;
    }

    /**
     * @param migrado the migrado to set
     */
    public void setMigrado(Character migrado) {
        this.migrado = migrado;
    }
}
