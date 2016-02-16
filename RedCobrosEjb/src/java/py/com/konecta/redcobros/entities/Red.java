/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "RED")
@NamedQueries({@NamedQuery(name = "Red.findAll", query = "SELECT r FROM Red r")})
public class Red implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RED")
    @GeneratedValue(generator = "redSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "redSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="redSeq",initialValue=1)
    private Long idRed;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "COD_ERA")
    private Integer codEra;
    @Basic(optional = false)
    @Column(name = "CUENTA_BCP_IMPUESTOS")
    private String cuentaBcpImpuestos;
    @Basic(optional = false)
    @Column(name = "CUENTA_BCP_OTROS_CONCEPTOS")
    private String cuentaBcpOtrosConceptos;
    @Basic(optional = false)
    @Column(name = "CUENTA_BCP_PENALIDADES")
    private String cuentaBcpPenalidades;
    @JoinColumn(name = "ENTIDAD", referencedColumnName = "ID_ENTIDAD")
    @ManyToOne(optional = false)
    private Entidad entidad;
    @OneToMany(mappedBy = "red")
    private Collection<HabilitacionFactRed> habilitacionFactRedCollection;
    @OneToMany(mappedBy = "red")
    private Collection<Recaudador> recaudadorCollection;
    @Basic(optional = false)
    @Column(name = "NUMERO_ORDEN_PROXIMO")
    private Long numeroOrdenProximo;
    @OneToMany(mappedBy = "red")
    private Collection<NumeroOt> numeroOtCollection;
    @JoinColumn(name = "BANCO_CLEARING", referencedColumnName = "ID_ENTIDAD_FINANCIERA")
    @ManyToOne(optional = false)
    private EntidadFinanciera bancoClearing;
    @Basic(optional = false)
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    //cuenta para iva de la red
    @Column(name = "NUMERO_CUENTA_IVA")
    private String numeroCuentaIva;
    @Basic(optional = false)
    @Column(name = "NUMERO_CUENTA_PROCESADOR")
    private String numeroCuentaProcesador;

    public String getNumeroCuentaIva() {
        return numeroCuentaIva;
    }

    public void setNumeroCuentaIva(String numeroCuentaIva) {
        this.numeroCuentaIva = numeroCuentaIva;
    }


    public String getNumeroCuentaProcesador() {
        return numeroCuentaProcesador;
    }

    public void setNumeroCuentaProcesador(String numeroCuentaProcesador) {
        this.numeroCuentaProcesador = numeroCuentaProcesador;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    public EntidadFinanciera getBancoClearing() {
        return bancoClearing;
    }

    public void setBancoClearing(EntidadFinanciera bancoClearing) {
        this.bancoClearing = bancoClearing;
    }


    public Collection<NumeroOt> getNumeroOtCollection() {
        return numeroOtCollection;
    }

    public void setNumeroOtCollection(Collection<NumeroOt> numeroOtCollection) {
        this.numeroOtCollection = numeroOtCollection;
    }

    public String getCuentaBcpOtrosConceptos() {
        return cuentaBcpOtrosConceptos;
    }

    public void setCuentaBcpOtrosConceptos(String cuentaBcpOtrosConceptos) {
        this.cuentaBcpOtrosConceptos = cuentaBcpOtrosConceptos;
    }

    public String getCuentaBcpPenalidades() {
        return cuentaBcpPenalidades;
    }

    public void setCuentaBcpPenalidades(String cuentaBcpPenalidades) {
        this.cuentaBcpPenalidades = cuentaBcpPenalidades;
    }

       

    public Collection<Recaudador> getRecaudadorCollection() {
        return recaudadorCollection;
    }

    public void setRecaudadorCollection(Collection<Recaudador> recaudadorCollection) {
        this.recaudadorCollection = recaudadorCollection;
    }

    public Long getNumeroOrdenProximo() {
        return numeroOrdenProximo;
    }

    public void setNumeroOrdenProximo(Long numeroOrdenProximo) {
        this.numeroOrdenProximo = numeroOrdenProximo;
    }

    public Red() {
    }

    public Red(Long idRed) {
        this.idRed = idRed;
    }

    public Red(Long idRed, String descripcion) {
        this.idRed = idRed;
        this.descripcion = descripcion;
    }

    public Long getIdRed() {
        return idRed;
    }

    public void setIdRed(Long idRed) {
        this.idRed = idRed;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodEra() {
        return codEra;
    }

    public void setCodEra(Integer codEra) {
        this.codEra = codEra;
    }

    public String getCuentaBcpImpuestos() {
        return cuentaBcpImpuestos;
    }

    public void setCuentaBcpImpuestos(String cuentaBcpImpuestos) {
        this.cuentaBcpImpuestos = cuentaBcpImpuestos;
    }

    

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    
    public Collection<HabilitacionFactRed> getHabilitacionFactRedCollection() {
        return habilitacionFactRedCollection;
    }

    public void setHabilitacionFactRedCollection(Collection<HabilitacionFactRed> habilitacionFactRedCollection) {
        this.habilitacionFactRedCollection = habilitacionFactRedCollection;
    }

    

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRed != null ? idRed.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Red)) {
            return false;
        }
        Red other = (Red) object;
        if ((this.idRed == null && other.idRed != null) || (this.idRed != null && !this.idRed.equals(other.idRed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Red[idRed=" + idRed + "]";
    }

}
