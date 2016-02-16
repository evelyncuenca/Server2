/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
@Entity
public class ValorEtiquetaSet implements Serializable {

    @Column(name = "ID")
    @Id
    private Long id;

    @Column(name = "VALOR")
    private String valor;

    @Column(name = "ETIQUETA")
    private String etiqueta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return "ValorEtiquetaSet{" + "id=" + id + ", valor=" + valor + ", etiqueta=" + etiqueta + '}';
    }

}
