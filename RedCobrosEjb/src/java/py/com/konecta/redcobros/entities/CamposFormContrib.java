/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import py.com.konecta.redcobros.utiles.Constantes;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "CAMPOS_FORM_CONTRIB")
public class CamposFormContrib implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CamposFormContribPK camposFormContribPK;
    @Basic(optional = false)
    @Column(name = "NUMERO_CAMPO")
    private Integer numeroCampo;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;

    public CamposFormContrib() {
    }

    public void setEtiqueta (String etiqueta) {
        if (this.camposFormContribPK==null) {
            this.camposFormContribPK=new CamposFormContribPK();
        }
        this.camposFormContribPK.setEtiqueta(etiqueta);
    }

    public String getEtiqueta () {
        if (this.camposFormContribPK!=null) {
            return this.getEtiqueta();
        }
        return null;
    }

    public Integer getNumeroCampo() {
        return numeroCampo;
    }

    public void setNumeroCampo(Integer numeroCampo) {
        this.numeroCampo = numeroCampo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public FormContrib getFormContrib() {
        return camposFormContribPK.getFormContrib();
    }

    public void setFormContrib(FormContrib formContrib) {
        //this.formContrib = formContrib;
        if (this.camposFormContribPK==null) {
            this.camposFormContribPK=new CamposFormContribPK();
        }
        this.camposFormContribPK.setFormContrib(formContrib);

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (camposFormContribPK != null ? camposFormContribPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CamposFormContrib)) {
            return false;
        }
        CamposFormContrib other = (CamposFormContrib) object;
        if ((this.camposFormContribPK == null && other.camposFormContribPK != null) || (this.camposFormContribPK != null && !this.camposFormContribPK.equals(other.camposFormContribPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CamposFormContrib[camposFormContribPK=" + camposFormContribPK + "]";
    }
    public String toStringHash() {
        String cadena="";
        //campo
        cadena+=UtilesSet.cerosIzquierda(numeroCampo.longValue(),Constantes.TAM_PAGO_FORMULARIO_CAMPO);
        //valor
        cadena+=UtilesSet.cerosIzquierda(Long.parseLong(valor),Constantes.TAM_PAGO_FORMULARIO_VALOR);

        return cadena;
    }

    public String toStringERA(Integer numeroERA) {
        String cadena="";
        //identificador del registro
        cadena+="21";
        //tipo de informacion, 1 declaracion jurada
        cadena+="1";
        //numero de ERA
        cadena+=UtilesSet.cerosIzquierda(numeroERA.longValue(),Constantes.TAM_ERA);
        //codigo de sucursal
        cadena+=UtilesSet.cerosIzquierda(
                this.getFormContrib().getGrupo().getGestion().getTerminal().getSucursal().getCodigoSucursalSet().longValue(),
                Constantes.TAM_SUCURSAL);
        //codigo del cajero
        cadena+=UtilesSet.cerosIzquierda(
                this.getFormContrib().getGrupo().getGestion().getTerminal().getCodigoCajaSet().longValue(),
                Constantes.TAM_CAJERO);
        //consecutivo
        cadena+=UtilesSet.cerosIzquierda(
                this.getFormContrib().getConsecutivo(),
                Constantes.TAM_CONSECUTIVO);
        //numero de orden
        cadena+=UtilesSet.cerosIzquierda(this.getFormContrib().getNumeroOrden(),Constantes.TAM_PAGO_FORMULARIO_NUMERO_ORDEN);
         //campo
        cadena+=UtilesSet.cerosIzquierda(numeroCampo.longValue(),Constantes.TAM_PAGO_FORMULARIO_CAMPO);
        //valor
        cadena+=UtilesSet.cerosIzquierda(Long.parseLong(valor),Constantes.TAM_PAGO_FORMULARIO_VALOR);
        return cadena;
    }
}
