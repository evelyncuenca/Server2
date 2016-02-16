/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.types;

/**
 *
 * @author igaona
 */
public class AliasServicioFacturador {
    private Long idFacturador;
    private Integer idServicio;
    private Integer idAliasServicio;

    public AliasServicioFacturador(Long idFacturador, Integer idServicio, Integer idAliasServicio) {
        this.idFacturador = idFacturador;
        this.idServicio = idServicio;
        this.idAliasServicio = idAliasServicio;
    }

    public AliasServicioFacturador() {
    }

    public Integer getIdAliasServicio() {
        return idAliasServicio;
    }

    public void setIdAliasServicio(Integer idAliasServicio) {
        this.idAliasServicio = idAliasServicio;
    }

    public Long getIdFacturador() {
        return idFacturador;
    }

    public void setIdFacturador(Long idFacturador) {
        this.idFacturador = idFacturador;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }
    
    
    
}
