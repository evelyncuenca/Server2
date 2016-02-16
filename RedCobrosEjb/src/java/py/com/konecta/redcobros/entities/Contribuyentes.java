/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CONTRIBUYENTES",
uniqueConstraints = {
    @UniqueConstraint(columnNames = {"RUC_NUEVO"}),
    @UniqueConstraint(columnNames = {"RUC_NUEVO", "DIGITO_VERIFICADOR"})
})
@NamedQueries({
    @NamedQuery(name = "Contribuyentes.findAll", query = "SELECT c FROM Contribuyentes c")})
public class Contribuyentes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONTRIBUYENTE")
//    @GeneratedValue(generator = "contribuyentesSeq",strategy=GenerationType.TABLE)
//    @TableGenerator(name = "contribuyentesSeq", table = "SECUENCIA",
//        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
//        pkColumnValue="contribuyentesSeq",initialValue=1)
    private Long idContribuyente;
    @Column(name = "RUC_NUEVO")
    private String rucNuevo;
    @Column(name = "DIGITO_VERIFICADOR")
    private String digitoVerificador;
    @Column(name = "RUC_ANTERIOR")
    private String rucAnterior;
    @Column(name = "TIPO_CONTRIBUYENTE")
    private Integer tipoContribuyente;
    @Column(name = "MODALIDAD_CONTRIBUYENTE")
    private Integer modalidadContribuyente;
    @Column(name = "MES_CIERRE")
    private Integer mesCierre;
    @Column(name = "NOMBRE_CONTRIBUYENTE")
    private String nombreContribuyente;
    @OneToMany(mappedBy = "contribuyente")
    private Collection<FormContrib> formContribCollection;
    @OneToMany(mappedBy = "contribuyente")
    private Collection<BoletaPagoContrib> boletaPagoContribCollection;
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "USUARIO_TERMINAL")
    private Long usuarioTerminal;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "MENSAJE")
    private String mensaje;

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getUsuarioTerminal() {
        return usuarioTerminal;
    }

    public void setUsuarioTerminal(Long usuarioTerminal) {
        this.usuarioTerminal = usuarioTerminal;
    }

    public Contribuyentes() {
    }

    public Contribuyentes(Long idContribuyente) {
        this.idContribuyente = idContribuyente;
    }

    public Long getIdContribuyente() {
        return idContribuyente;
    }

    public void setIdContribuyente(Long idContribuyente) {
        this.idContribuyente = idContribuyente;
    }

    public String getRucNuevo() {
        return rucNuevo;
    }

    public void setRucNuevo(String rucNuevo) {
        this.rucNuevo = rucNuevo;
    }

    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    public String getRucAnterior() {
        return rucAnterior;
    }

    public void setRucAnterior(String rucAnterior) {
        this.rucAnterior = rucAnterior;
    }

    public Integer getMesCierre() {
        return mesCierre;
    }

    public void setMesCierre(Integer mesCierre) {
        this.mesCierre = mesCierre;
    }

    public Integer getModalidadContribuyente() {
        return modalidadContribuyente;
    }

    public void setModalidadContribuyente(Integer modalidadContribuyente) {
        this.modalidadContribuyente = modalidadContribuyente;
    }

    public Integer getTipoContribuyente() {
        return tipoContribuyente;
    }

    public void setTipoContribuyente(Integer tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public String getNombreContribuyente() {
        return nombreContribuyente;
    }

    public void setNombreContribuyente(String nombreContribuyente) {
        this.nombreContribuyente = nombreContribuyente;
    }

    public Collection<FormContrib> getFormContribCollection() {
        return formContribCollection;
    }

    public void setFormContribCollection(Collection<FormContrib> formContribCollection) {
        this.formContribCollection = formContribCollection;
    }

    public Collection<BoletaPagoContrib> getBoletaPagoContribCollection() {
        return boletaPagoContribCollection;
    }

    public void setBoletaPagoContribCollection(Collection<BoletaPagoContrib> boletaPagoContribCollection) {
        this.boletaPagoContribCollection = boletaPagoContribCollection;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContribuyente != null ? idContribuyente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contribuyentes)) {
            return false;
        }
        Contribuyentes other = (Contribuyentes) object;
        if ((this.idContribuyente == null && other.idContribuyente != null) || (this.idContribuyente != null && !this.idContribuyente.equals(other.idContribuyente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Contribuyentes[idContribuyente=" + idContribuyente + "]";
    }
}
