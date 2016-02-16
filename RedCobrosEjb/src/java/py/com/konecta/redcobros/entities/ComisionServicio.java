/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@Entity
@Table(name = "COMISION_SERVICIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComisionServicio.findAll", query = "SELECT c FROM ComisionServicio c"),
    @NamedQuery(name = "ComisionServicio.findByIdComisionServicio", query = "SELECT c FROM ComisionServicio c WHERE c.idComisionServicio = :idComisionServicio"),
    @NamedQuery(name = "ComisionServicio.findByPorcentaje", query = "SELECT c FROM ComisionServicio c WHERE c.porcentaje = :porcentaje"),
    @NamedQuery(name = "ComisionServicio.findByMinimo", query = "SELECT c FROM ComisionServicio c WHERE c.minimo = :minimo"),
    @NamedQuery(name = "ComisionServicio.findByMaximo", query = "SELECT c FROM ComisionServicio c WHERE c.maximo = :maximo")})
public class ComisionServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMISION_SERVICIO")
    private Integer idComisionServicio;
    @Column(name = "ID_SERVICIO")
    private Integer idServicio;
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    @Column(name = "MINIMO")
    private Integer minimo;
    @Column(name = "MAXIMO")
    private Integer maximo;

    public ComisionServicio() {
    }

    public ComisionServicio(Integer idComisionServicio) {
        this.idComisionServicio = idComisionServicio;
    }

    public Integer getIdComisionServicio() {
        return idComisionServicio;
    }

    public void setIdComisionServicio(Integer idComisionServicio) {
        this.idComisionServicio = idComisionServicio;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public String toString() {
        return "ComisionServicio{" + "idComisionServicio=" + idComisionServicio + ", idServicio=" + idServicio + ", porcentaje=" + porcentaje + ", minimo=" + minimo + ", maximo=" + maximo + '}';
    }

}
