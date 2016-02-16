/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "BOLETA_PAGO_CONTRIB")
@NamedQueries({
    @NamedQuery(name = "BoletaPagoContrib.findAll", query = "SELECT b FROM BoletaPagoContrib b")})
public class BoletaPagoContrib implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BOLETA_PAGO_CONTRIB")
    @GeneratedValue(generator = "boletaPagoContribSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "boletaPagoContribSeq", table = "SECUENCIA",
            allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
            pkColumnValue = "boletaPagoContribSeq", initialValue = 1)
    private Long idBoletaPagoContrib;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_RECEPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRecepcion;
    @Basic(optional = false)
    @Column(name = "FECHA_COBRO")
    @Temporal(TemporalType.DATE)
    private Date fechaCobro;
    @Basic(optional = false)
    @Column(name = "NUMERO_ORDEN")
    private Long numeroOrden;
    @Column(name = "CODIGO_HASH")
    private String codigoHash;
    @Basic(optional = false)
    @Column(name = "CONSECUTIVO")
    private Long consecutivo;
    @JoinColumn(name = "IMPUESTO", referencedColumnName = "ID_IMPUESTO")
    @ManyToOne(optional = false)
    private Impuesto impuesto;
    @Column(name = "NUMERO_RESOLUCION")
    private Long numeroResolucion;
    @Column(name = "PERIODO")
    private String periodo;
    @Basic(optional = false)
    @Column(name = "TOTAL")
    private Double total;
    @JoinColumn(name = "CONTRIBUYENTE", referencedColumnName = "ID_CONTRIBUYENTE")
    @ManyToOne(optional = false)
    private Contribuyentes contribuyente;
    @Basic(optional = false)
    @Column(name = "FORMULARIO")
    private Integer formulario;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private Integer version;
    @JoinColumn(name = "TRANSACCION", referencedColumnName = "ID_TRANSACCION")
    @OneToOne(optional = false)
    private Transaccion transaccion;
    @JoinColumn(name = "FORM_CONTRIB", referencedColumnName = "ID_FORM_CONTRIB")
    @ManyToOne
    private FormContrib formContrib;
    @Basic(optional = false)
    @Column(name = "POS_GRUPO")
    private Integer posGrupo;
    @JoinColumn(name = "GRUPO", referencedColumnName = "ID_GRUPO")
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "NUMERO_OT", referencedColumnName = "ID_NUMERO_OT")
    @ManyToOne
    private NumeroOt numeroOt;
    @Column(name = "RUC")
    private String ruc;
    @Column(name = "DV")
    private String dv;
    @Column(name = "FLG_PAGO_ONLINE")
    private Integer flgPagoOnline;

    public NumeroOt getNumeroOt() {
        return numeroOt;
    }

    public void setNumeroOt(NumeroOt numeroOt) {
        this.numeroOt = numeroOt;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
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

    public FormContrib getFormContrib() {
        return formContrib;
    }

    public void setFormContrib(FormContrib formContrib) {
        this.formContrib = formContrib;
    }

    public BoletaPagoContrib() {
    }

    public BoletaPagoContrib(Long idBoletaPagoContrib) {
        this.idBoletaPagoContrib = idBoletaPagoContrib;
    }

    public Long getIdBoletaPagoContrib() {
        return idBoletaPagoContrib;
    }

    public void setIdBoletaPagoContrib(Long idBoletaPagoContrib) {
        this.idBoletaPagoContrib = idBoletaPagoContrib;
    }

    public Date getFechaHoraRecepcion() {
        return fechaHoraRecepcion;
    }

    public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
    }

    public Long getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Long numeroOrden) {
        this.numeroOrden = numeroOrden;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Contribuyentes getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyentes contribuyente) {
        this.contribuyente = contribuyente;
    }

    public Integer getFormulario() {
        return formulario;
    }

    public void setFormulario(Integer formulario) {
        this.formulario = formulario;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(Long numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
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
        hash += (idBoletaPagoContrib != null ? idBoletaPagoContrib.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BoletaPagoContrib)) {
            return false;
        }
        BoletaPagoContrib other = (BoletaPagoContrib) object;
        if ((this.idBoletaPagoContrib == null && other.idBoletaPagoContrib != null) || (this.idBoletaPagoContrib != null && !this.idBoletaPagoContrib.equals(other.idBoletaPagoContrib))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.BoletaPagoContrib[idBoletaPagoContrib=" + idBoletaPagoContrib + "]";
    }

    public String toStringHash() {
        String cadena = "";
        //numero de orden
        cadena += UtilesSet.cerosIzquierda(numeroOrden, Constantes.TAM_BOLETA_PAGO_NUMERO_ORDEN);
        //formulario
        cadena += UtilesSet.cerosIzquierda(formulario.longValue(), Constantes.TAM_BOLETA_PAGO_FORMULARIO);
        //version
        cadena += UtilesSet.cerosIzquierda(version.longValue(), Constantes.TAM_BOLETA_PAGO_VERSION);
        //fecha
        Format formatter = new SimpleDateFormat("ddMMyyyy");
        cadena += formatter.format(fechaCobro);
//        //ruc
//        cadena+=UtilesSet.espaciosDerecha(contribuyente.getRucNuevo(),Constantes.TAM_BOLETA_PAGO_RUC);
//        //digito verificador
//        cadena+=contribuyente.getDigitoVerificador();
        //ruc
        cadena += UtilesSet.espaciosDerecha(ruc, Constantes.TAM_BOLETA_PAGO_RUC);
        //digito verificador
        cadena += dv;
        //impuesto
        cadena += UtilesSet.cerosIzquierda(this.getImpuesto().getNumero().longValue(), Constantes.TAM_BOLETA_PAGO_IMPUESTO);
        //importe
        cadena += UtilesSet.cerosIzquierda(total.longValue(), Constantes.TAM_BOLETA_PAGO_IMPORTE_PAGADO);
        //numero de resolucion
        cadena += UtilesSet.cerosIzquierda(numeroResolucion, Constantes.TAM_BOLETA_PAGO_NUMERO_RESOLUCION);
        System.out.println("cadena para hash " + cadena);
        return UtilesSet.getHashSet(cadena);
    }

    public String toStringERA(Integer numeroERA) {
        String cadena = "";
        //identificador del registro
        cadena += "30";
        //tipo de informacion, 2 boleta de pago
        cadena += "2";
        //numero de ERA
        cadena += UtilesSet.cerosIzquierda(numeroERA.longValue(), Constantes.TAM_ERA);
        //codigo de sucursal
        cadena += UtilesSet.cerosIzquierda(
                this.getTransaccion().getGestion().getTerminal().getSucursal().getCodigoSucursalSet().longValue(),
                Constantes.TAM_SUCURSAL);
        //codigo del cajero
        cadena += UtilesSet.cerosIzquierda(
                this.getTransaccion().getGestion().getTerminal().getCodigoCajaSet().longValue(),
                Constantes.TAM_CAJERO);
        //consecutivo
        cadena += UtilesSet.cerosIzquierda(
                consecutivo,
                Constantes.TAM_CONSECUTIVO);
        //numero de orden de pago
        cadena += UtilesSet.cerosIzquierda(numeroOrden, Constantes.TAM_PAGO_FORMULARIO_NUMERO_ORDEN);
        //numero de orden de transferencia
        cadena += numeroOt.getEraNumeroOtDv();
        //formulario
        cadena += UtilesSet.cerosIzquierda(formulario.longValue(), Constantes.TAM_BOLETA_PAGO_FORMULARIO);

        //version
        cadena += UtilesSet.cerosIzquierda(version.longValue(), Constantes.TAM_BOLETA_PAGO_VERSION);

        //fecha
        Format formatter = new SimpleDateFormat("ddMMyyyy");
        cadena += formatter.format(fechaCobro);
        //ruc
        cadena += UtilesSet.espaciosDerecha(contribuyente.getRucNuevo(), Constantes.TAM_BOLETA_PAGO_RUC);
        //digito verificador
        cadena += contribuyente.getDigitoVerificador();
        //impuesto
        cadena += UtilesSet.cerosIzquierda(this.getImpuesto().getNumero().longValue(), Constantes.TAM_BOLETA_PAGO_IMPUESTO);
        //periodo
        if (periodo != null) {
            periodo = periodo.replaceAll("/", "");
            if (periodo.length() == 8) {
                cadena += UtilesSet.cerosIzquierda(
                        new Long(periodo.substring(4)
                                + periodo.substring(2, 4)
                                + periodo.substring(0, 2)),
                        Constantes.TAM_PERIODO);
            } else if (periodo.length() == 6) {
                cadena += UtilesSet.cerosIzquierda(new Long(periodo.substring(2) + periodo.substring(0, 2)), Constantes.TAM_PERIODO);
            } else {
                cadena += UtilesSet.cerosIzquierda(new Long(periodo), Constantes.TAM_PERIODO);
            }
        } else {
            cadena += UtilesSet.cerosIzquierda(0L, Constantes.TAM_PERIODO);
        }
        //importe
        cadena += UtilesSet.cerosIzquierda(total.longValue(), Constantes.TAM_BOLETA_PAGO_IMPORTE_PAGADO);
        //numero de resolucion
        cadena += UtilesSet.cerosIzquierda(numeroResolucion.longValue(), Constantes.TAM_BOLETA_PAGO_NUMERO_RESOLUCION);
        //hash
        cadena += UtilesSet.espaciosDerecha(codigoHash, Constantes.TAM_HASH);
        return cadena;
    }

    /**
     * @return the impuesto
     */
    public Impuesto getImpuesto() {
        return impuesto;
    }

    /**
     * @param impuesto the impuesto to set
     */
    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

}
