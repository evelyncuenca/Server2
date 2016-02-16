/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.BoletaPagoContrib;
import py.com.konecta.redcobros.entities.NumeroOt;
import py.com.konecta.redcobros.entities.ParametroSistema;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.TipoPago;
import py.com.konecta.redcobros.utiles.Constantes;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NumeroOtFacadeImpl extends GenericDaoImpl<NumeroOt, Long> implements NumeroOtFacade {

    @EJB
    ParametroSistemaFacade parametroSistemaFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public synchronized NumeroOt obtenerNumeroOT(Long idRed, Integer numeroERA, Long idTipoPago, Date fecha) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        NumeroOt numeroOt = null;
        List<NumeroOt> listaNroOT = (List<NumeroOt>) hem.getSession().createCriteria(NumeroOt.class).add(Restrictions.eq("red", new Red(idRed))).add(Restrictions.eq("tipoPago", new TipoPago(idTipoPago))).add(Restrictions.eq("fecha", fecha)).list();
        if (listaNroOT.size() == 1) {
            numeroOt = listaNroOT.get(0);
        } else {
            Integer numero;
            List<Long> listaIdNumeroOt = (List<Long>) hem.getSession().createCriteria(NumeroOt.class).add(Restrictions.eq("red", new Red(idRed))).setProjection(Projections.max("idNumeroOt")).list();
            if (listaIdNumeroOt.size() == 1
                    && listaIdNumeroOt.get(0) != null) {
                numero = this.get(listaIdNumeroOt.get(0)).getNumero() + 1;
            } else {
                numero = 1;
            }
            String numeroOTSinDv;
            ParametroSistema p = new ParametroSistema();
            p.setNombreParametro("SET_ONLINE");
            ParametroSistema setOnline = parametroSistemaFacade.get(p);
            System.out.println("FLAAAAG DE SET-ONLINE " + setOnline);

            if (idTipoPago.equals(1L) && setOnline != null || true) {//cambiar aqui
                numeroOTSinDv = String.valueOf((numeroERA.longValue() * 100000000L) + numero.longValue());
            } else {
                numeroOTSinDv = UtilesSet.cerosIzquierda(numeroERA.longValue() * 10000000L + numero.longValue(), Constantes.TAM_OT - 1);
            }
            String numeroOT = numeroOTSinDv + UtilesSet.MODULO11(numeroOTSinDv);
            numeroOt = new NumeroOt();
            numeroOt.setRed(new Red(idRed));
            numeroOt.setTipoPago(new TipoPago(idTipoPago));
            numeroOt.setFecha(fecha);
            numeroOt.setNumero(numero);
            numeroOt.setEraNumeroOtDv(numeroOT);
            numeroOt = this.merge(numeroOt);
        }
        return numeroOt;
    }

    public List<Map<String, Object>> obtenerDatosReporteOT(Long idRed, String fecha, Integer tipoPago) throws Exception {

        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria critNumeroOt = hem.getSession().createCriteria(NumeroOt.class);
        Criteria critRed = critNumeroOt.createCriteria("red", "r");
        critRed.add(Restrictions.eq("idRed", idRed));
        critRed.createCriteria("bancoClearing", "bc");
        critNumeroOt.createCriteria("tipoPago", "tp").add(Restrictions.eq("idTipoPago", tipoPago.longValue()));
        critNumeroOt.add(Restrictions.eq("fecha", new SimpleDateFormat("ddMMyyyy").parse(fecha)));

        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("eraNumeroOtDv"), "eraNumeroOtDv");
        pl.add(Projections.property("r.descripcion"), "redDescripcion");
        pl.add(Projections.property("r.cuentaBcpImpuestos"), "cuentaBcpImpuestos");
        pl.add(Projections.property("r.codEra"), "codEra");
        pl.add(Projections.property("bc.descripcion"), "bcDescripcion");

        critNumeroOt.setProjection(pl);
        critNumeroOt.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> retorno = critNumeroOt.list();

        if (retorno.size() == 1) {
            Criteria critBoletaPago = hem.getSession().createCriteria(BoletaPagoContrib.class);
            critBoletaPago.createCriteria("numeroOt")
                    .add(Restrictions.eq("eraNumeroOtDv", retorno.get(0).get("eraNumeroOtDv")));
            critBoletaPago.createCriteria("transaccion").add(Restrictions.eq("flagAnulado", "N"));
            critBoletaPago.setProjection(Projections.projectionList().add(Projections.sum("total"), "total"));
            critBoletaPago.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            retorno.get(0).put("total", ((List<Map<String, Object>>) critBoletaPago.list()).get(0).get("total"));
        }
        return retorno;
    }

}
