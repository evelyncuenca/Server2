/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.entities;

import java.io.Serializable;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "SERVICIO")
@NamedQueries({@NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s")})
public class Servicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO")
    @GeneratedValue(generator = "servicioSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "servicioSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="servicioSeq",initialValue=1)
    private Long idServicio;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "COMENTARIO")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "TAMANHO_GRUPO")
    private Integer tamanhoGrupo;
    @Column(name = "CODIGO_TRANSACCIONAL")
    private String codigoTransaccional;
    @OneToMany(mappedBy = "servicio")
    private Collection<Grupo> gestionCollection;
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "HORA_CIERRE")
    @Temporal(TemporalType.TIME)
    private Date horaCierre;
    @Basic(optional = false)
    @Column(name = "FACTURABLE")
    private String facturable;
    @Column(name = "FACTURA_INCLUYE_COMISION")
    private String facturaIncluyeComision;

    public String getFacturaIncluyeComision() {
        return facturaIncluyeComision;
    }

    public void setFacturaIncluyeComision(String facturaIncluyeComision) {
        this.facturaIncluyeComision = facturaIncluyeComision;
    }


    public String getFacturable() {
        return facturable;
    }

    public void setFacturable(String facturable) {
        this.facturable = facturable;
    }


    public Date getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Date horaCierre) {
        this.horaCierre = horaCierre;
    }



    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    @JoinColumn(name = "FACTURADOR", referencedColumnName = "ID_FACTURADOR")
    @ManyToOne(optional = false)
    private Facturador facturador;
    @ManyToMany(mappedBy = "servicioCollection")
    private Collection<Recaudador> recaudadorCollection;
  

    public Servicio() {
    }

    public Servicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Collection<Recaudador> getRecaudadorCollection() {
        return recaudadorCollection;
    }

    public void setRecaudadorCollection(Collection<Recaudador> recaudadorCollection) {
        this.recaudadorCollection = recaudadorCollection;
    }



    public Servicio(Long idServicio, String descripcion, Integer tamanhoGrupo) {
        this.idServicio = idServicio;
        this.descripcion = descripcion;
        this.tamanhoGrupo = tamanhoGrupo;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getTamanhoGrupo() {
        return tamanhoGrupo;
    }

    public void setTamanhoGrupo(Integer tamanhoGrupo) {
        this.tamanhoGrupo = tamanhoGrupo;
    }

   

    public String getCodigoTransaccional() {
        return codigoTransaccional;
    }

    public void setCodigoTransaccional(String codigoTransaccional) {
        this.codigoTransaccional = codigoTransaccional;
    }

    public Collection<Grupo> getGestionCollection() {
        return gestionCollection;
    }

    public void setGestionCollection(Collection<Grupo> gestionCollection) {
        this.gestionCollection = gestionCollection;
    }

    public Facturador getFacturador() {
        return facturador;
    }

    public void setFacturador(Facturador facturador) {
        this.facturador = facturador;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.Servicio[idServicio=" + idServicio + "]";
    }

}
