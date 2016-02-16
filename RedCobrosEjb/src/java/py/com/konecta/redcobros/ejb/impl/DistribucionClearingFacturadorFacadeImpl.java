/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.ClearingFacturador;
import py.com.konecta.redcobros.entities.DistribucionClearingFacturador;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.TipoCobro;

/**
 *
 * @author konecta
 */
@Stateless
public class DistribucionClearingFacturadorFacadeImpl extends GenericDaoImpl<DistribucionClearingFacturador, Long> implements DistribucionClearingFacturadorFacade {

    public void generarClearingDistribucion(ClearingFacturador clearing,
            List<Map<String, Object>> listaMapa) throws Exception {
        Map<String, Object> mapa;
        Long idRecaudador;
        Double monto = null;
        DistribucionClearingFacturador distribucionClearingFacturador;
        for (int i = 0; i < listaMapa.size(); i++) {
            mapa = (Map<String, Object>) listaMapa.get(i);
            idRecaudador = (Long) mapa.get("idRecaudador");
            monto = (Double) mapa.get("montoTotal");
            if (monto.doubleValue() == 0.0) {
                continue;
            }
            distribucionClearingFacturador = new DistribucionClearingFacturador();
            distribucionClearingFacturador.setClearingFacturador(clearing);
            distribucionClearingFacturador.setRecaudador(new Recaudador(idRecaudador));
            distribucionClearingFacturador.setMonto(monto);
            clearing.getDistribucionClearingFacturadorCollection().add(distribucionClearingFacturador);
        }
    }

    public List<Map<String, Object>> listaReporte(Long idClearingFacturador,Long idFacturador,Long idTipoCobro,Date fecha, Long idRed) throws Exception {
        List<Map<String, Object>> lista = this.obtenerCriteriaReporte(idClearingFacturador,idFacturador,idTipoCobro,fecha,idRed).list();
        if (idClearingFacturador==null) {
            //clearing
            for (Map<String, Object> mapaClearing : lista) {
                mapaClearing.put("fechaClearingFacturadorString",
                        new SimpleDateFormat("dd/MM/yyyy").format((Date) mapaClearing.get("fechaClearingFacturador")));
                mapaClearing.put("fechaAcreditacionString",
                        new SimpleDateFormat("dd/MM/yyyy").format((Date) mapaClearing.get("fechaAcreditacion")));                
            }
        }
        return lista;
    }

    private Criteria obtenerCriteriaReporte(Long idClearingFacturador,Long idFacturador,Long idTipoCobro,Date fecha, Long idRed) throws Exception {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(DistribucionClearingFacturador.class);
        Criteria cCF=c.createCriteria("clearingFacturador","cf");
        c.createCriteria("recaudador", "r");
        ProjectionList pl = Projections.projectionList();
        if (idClearingFacturador!=null) {
            //reporte por servicio
            cCF.add(Restrictions.eq("idClearingFacturador", idClearingFacturador));
            pl.add(Projections.property("idDistribucionClearingFacturador"), "idDistribucionClearingFacturador");
            pl.add(Projections.property("r.idRecaudador"), "idRecaudador");
            pl.add(Projections.property("r.descripcion"), "descripcionRecaudador");
            pl.add(Projections.property("r.numeroCuenta"), "cuentaRecaudador");
            pl.add(Projections.property("monto"), "monto");
        } else {
            //agrupado por facturador para generar archivo clearing
            cCF.add(Restrictions.eq("fecha", fecha));
            cCF.add(Restrictions.eq("red", new Red(idRed)));
            cCF.add(Restrictions.eq("tipoCobro", new TipoCobro(idTipoCobro)));
            cCF.createCriteria("tipoCobro", "tc");
            cCF.createCriteria("servicio").add(Restrictions.eq("facturador", new Facturador(idFacturador)))
                    .createCriteria("facturador","f");
            pl.add(Projections.groupProperty("cf.fecha"), "fechaClearingFacturador");
            pl.add(Projections.groupProperty("tc.idTipoCobro"), "idTipoCobro");
            pl.add(Projections.groupProperty("tc.descripcion"), "descripcionTipoCobro");
            pl.add(Projections.groupProperty("cf.fechaAcreditacion"), "fechaAcreditacion");
            pl.add(Projections.groupProperty("r.idRecaudador"), "idRecaudador");
            pl.add(Projections.groupProperty("r.descripcion"), "descripcionRecaudador");
            pl.add(Projections.groupProperty("r.numeroCuenta"), "cuentaRecaudador");
            pl.add(Projections.sum("monto"), "monto");
        }
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return c;
    }
}
