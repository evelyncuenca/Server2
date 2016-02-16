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
@Table(name = "FRANJA_HORARIA_CAB")
@NamedQueries({@NamedQuery(name = "FranjaHorariaCab.findAll", query = "SELECT f FROM FranjaHorariaCab f")})
public class FranjaHorariaCab implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FRANJA_HORARIA_CAB")
    @GeneratedValue(generator = "franjaHorariaCabSeq",strategy=GenerationType.TABLE)
    @TableGenerator(name = "franjaHorariaCabSeq", table = "SECUENCIA",
        allocationSize = 1,pkColumnName="NOMBRE",valueColumnName="ACTUAL",
        pkColumnValue="franjaHorariaCabSeq",initialValue=1)
    private Long idFranjaHorariaCab;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "COMENTARIO")
    private String comentario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "franjaHorariaCab")
    private Collection<FranjaHorariaDet> franjaHorariaDetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "franjaHorariaCab")
    private Collection<UsuarioTerminal> usuarioTerminalCollection;
    @ManyToOne(optional=true)//(optional=false)
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    private Recaudador recaudador;

    public Recaudador getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(Recaudador recaudador) {
        this.recaudador = recaudador;
    }

    public FranjaHorariaCab() {
    }

    public FranjaHorariaCab(Long idFranjaHorariaCab) {
        this.idFranjaHorariaCab = idFranjaHorariaCab;
    }

    public FranjaHorariaCab(Long idFranjaHorariaCab, String descripcion) {
        this.idFranjaHorariaCab = idFranjaHorariaCab;
        this.descripcion = descripcion;
    }

    public Long getIdFranjaHorariaCab() {
        return idFranjaHorariaCab;
    }

    public void setIdFranjaHorariaCab(Long idFranjaHorariaCab) {
        this.idFranjaHorariaCab = idFranjaHorariaCab;
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

    public Collection<FranjaHorariaDet> getFranjaHorariaDetCollection() {
        return franjaHorariaDetCollection;
    }

    public void setFranjaHorariaDetCollection(Collection<FranjaHorariaDet> franjaHorariaDetCollection) {
        this.franjaHorariaDetCollection = franjaHorariaDetCollection;
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
        hash += (idFranjaHorariaCab != null ? idFranjaHorariaCab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FranjaHorariaCab)) {
            return false;
        }
        FranjaHorariaCab other = (FranjaHorariaCab) object;
        if ((this.idFranjaHorariaCab == null && other.idFranjaHorariaCab != null) || (this.idFranjaHorariaCab != null && !this.idFranjaHorariaCab.equals(other.idFranjaHorariaCab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.konecta.redcobros.entities.FranjaHorariaCab[idFranjaHorariaCab=" + idFranjaHorariaCab + "]";
    }

}
