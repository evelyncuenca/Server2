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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "USUARIO")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    @GeneratedValue(generator = "usuarioSeq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "usuarioSeq", table = "SECUENCIA",
            allocationSize = 1, pkColumnName = "NOMBRE", valueColumnName = "ACTUAL",
            pkColumnValue = "usuarioSeq", initialValue = 1)
    private Long idUsuario;
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(name = "CONTRASENHA")
    private String contrasenha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<UsuarioTerminal> usuarioTerminalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Gestion> gestionCollection;
    @JoinColumn(name = "PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne(optional = false)
    private Persona persona;
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;
    @ManyToOne(optional = true)
    @JoinColumn(name = "RECAUDADOR", referencedColumnName = "ID_RECAUDADOR")
    private Recaudador recaudador;
    @JoinTable(name = "USUARIO_SUPERVISOR", joinColumns = {
        @JoinColumn(name = "USUARIO", referencedColumnName = "ID_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "SUPERVISOR", referencedColumnName = "ID_USUARIO")})
    @ManyToMany
    private Collection<Usuario> supervisoresCollection;
    @ManyToMany(mappedBy = "supervisoresCollection")
    private Collection<Usuario> subordinadosCollection;
    @Basic(optional = false)
    @Column(name = "ES_SUPERVISOR")
    private String esSupervisor;
    @Column(name = "COD_EXTERNO")
    private Long codExterno;

    public String getEsSupervisor() {
        return esSupervisor;
    }

    public void setEsSupervisor(String esSupervisor) {
        this.esSupervisor = esSupervisor;
    }

    public Collection<Usuario> getSubordinadosCollection() {
        return subordinadosCollection;
    }

    public void setSubordinadosCollection(Collection<Usuario> subordinadosCollection) {
        this.subordinadosCollection = subordinadosCollection;
    }

    public Collection<Usuario> getSupervisoresCollection() {
        return supervisoresCollection;
    }

    public void setSupervisoresCollection(Collection<Usuario> supervisoresCollection) {
        this.supervisoresCollection = supervisoresCollection;
    }

    public Recaudador getRecaudador() {
        return recaudador;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setRecaudador(Recaudador recaudador) {
        this.recaudador = recaudador;
    }

    public Collection<Gestion> getGestionCollection() {
        return gestionCollection;
    }

    public void setGestionCollection(Collection<Gestion> gestionCollection) {
        this.gestionCollection = gestionCollection;
    }

    public Usuario() {
    }

    public Usuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Long idUsuario, String nombreUsuario, String contrasenha) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenha = contrasenha;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public Collection<UsuarioTerminal> getUsuarioTerminalCollection() {
        return usuarioTerminalCollection;
    }

    public void setUsuarioTerminalCollection(Collection<UsuarioTerminal> usuarioTerminalCollection) {
        this.usuarioTerminalCollection = usuarioTerminalCollection;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Long getCodExterno() {
        return codExterno;
    }

    public void setCodExterno(Long codExterno) {
        this.codExterno = codExterno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        return !((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario)));
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", contrasenha=" + contrasenha + ", persona=" + persona + ", codigo=" + codigo + ", recaudador=" + recaudador + ", esSupervisor=" + esSupervisor + ", codExterno=" + codExterno + '}';
    }

}
