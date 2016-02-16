/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.ejb.TransaccionRcFacade;
import py.com.konecta.redcobros.entities.EstadoTransaccion;
import py.com.konecta.redcobros.entities.TipoOperacion;
import py.com.konecta.redcobros.entities.TipoPago;
import py.com.konecta.redcobros.entities.TransaccionRc;

/**
 *
 * @author konecta
 */
@Stateless
public class TransaccionRcFacadeImpl extends GenericDaoImpl<TransaccionRc, BigDecimal> implements TransaccionRcFacade {

    private Criteria getCriteriaControlServicios(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Date fechaDesde, Date fechaHasta) {

        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c1 = hem.getSession().createCriteria(TransaccionRc.class);

        c1.createCriteria("idEstadoTransaccion").add(Restrictions.eq("idEstadoTransaccion", EstadoTransaccion.FINALIZADA_Y_COBRADA));
        c1.createCriteria("idTipoOperacion").add(Restrictions.eq("idTipoOperacion", TipoOperacion.COBRANZA));
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

        if (fechaDesde != null && fechaHasta != null) {
            c1.add(Restrictions.sqlRestriction("trunc(fecha_ingreso) between to_date('" + sdf.format(fechaDesde) + "','ddmmyyyy') and to_date('" + sdf.format(fechaHasta) + "','ddmmyyyy')"));
        } else if (fechaDesde != null) {
            c1.add(Restrictions.sqlRestriction("trunc(fecha_ingreso) = to_date('" + sdf.format(fechaDesde) + "','ddmmyyyy')"));
        }

        // Criteria cGrupo = c1.createCriteria("gestion", "ges");
        Criteria cGestion = c1.createCriteria("idGestion", "ges");
        if (idGestion != null) {
            cGestion.add(Restrictions.eq("idGestion", idGestion));
        }

        Criteria cTerminal = cGestion.createCriteria("terminal");
        Criteria cSucursal = cTerminal.createCriteria("sucursal");
        Criteria cRecaudador = cSucursal.createCriteria("recaudador");
        Criteria cRed = cRecaudador.createCriteria("red");
        if (idRed != null) {
            cRed.add(Restrictions.eq("idRed", idRed));
        }

        if (idRecaudador != null) {
            cRecaudador.add(Restrictions.eq("idRecaudador", idRecaudador));

        }
        if (idSucursal != null) {
            cSucursal.add(Restrictions.eq("idSucursal", idSucursal));
        }

        if (idTerminal != null) {
            cTerminal.add(Restrictions.eq("idTerminal", idTerminal));
        }

        if (filtroCheque) {
            c1.createCriteria("idTipoPago").add(Restrictions.eq("idTipoPago", TipoPago.CHEQUE));
        } else if (filtroEfectivo) {
            c1.createCriteria("idTipoPago").add(Restrictions.eq("idTipoPago", TipoPago.EFECTIVO));
        }
        c1.addOrder(Order.desc("fechaIngreso"));
        return c1;
    }

    public List<TransaccionRc> getControlServicios(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Date fechaDesde, Date fechaHasta, Integer start, Integer limit) {
        Criteria c = this.getCriteriaControlServicios(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, fechaDesde, fechaHasta);
        if (start != null && limit != null) {
            c.setFirstResult(start);
            c.setMaxResults(limit);
        }
        return c.list();
    }

    public Integer getTotalControlServicio(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Date fechaDesde, Date fechaHasta) {
        Criteria c = this.getCriteriaControlServicios(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, fechaDesde, fechaHasta);
        c.setProjection(Projections.rowCount());
        return (Integer) c.list().get(0);
    }

    public List<Map<String, Object>> getResumenServiciosRc(Long idGestion, Long idServicio, Long idFacturador) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria critTrx = hem.getSession().createCriteria(TransaccionRc.class);

        Criteria critServicio = critTrx.createCriteria("idServicio", "s");
        Criteria critFact = critServicio.createCriteria("idFacturador", "f");
        critTrx.createCriteria("idGestion", "ges").add(Restrictions.eq("idGestion", idGestion));
        critTrx.createCriteria("idTipoOperacion", "t").add(Restrictions.eq("idTipoOperacion", new Short("1")));
        critTrx.createCriteria("idEstadoTransaccion", "e").add(Restrictions.eq("idEstadoTransaccion", 22L));

        if (idServicio != null) {
            critServicio.add(Restrictions.eq("idServicio", idServicio));
        }
        if (idFacturador != null) {
            critFact.add(Restrictions.eq("idFacturador", idFacturador));
        }
        critTrx.createAlias("idTipoPago", "tp");
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.groupProperty("s.idServicio"), "idServicio");
        pl.add(Projections.groupProperty("s.descripcion"), "servicioDescripcion");
        pl.add(Projections.groupProperty("f.descripcion"), "facturadorDescripcion");
        pl.add(Projections.groupProperty("anulado"), "anulado");
        pl.add(Projections.groupProperty("tp.idTipoPago"), "idTipoPago");
        pl.add(Projections.sum("monto"), "monto");
        pl.add(Projections.rowCount(), "cantidad");


        critTrx.setProjection(pl);

        critServicio.addOrder(Order.asc("s.descripcion"));
        critServicio.addOrder(Order.asc("tp.idTipoPago"));

        critTrx.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return critTrx.list();
    }

    @Override
    public List<TransaccionRc> getDataCobranzaDetallada(Long idUsuario, Long idTerminal, Integer idServicio, Long idFacturador, String fechaIni, String fechaFin, String tipoPago) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria critTrx = hem.getSession().createCriteria(TransaccionRc.class, "trx");

        Criteria critServicio = critTrx.createCriteria("idServicio");
        Criteria critFact = critServicio.createCriteria("idFacturador");
        Criteria critGes = critTrx.createCriteria("idGestion");
        critGes.createCriteria("usuario").add(Restrictions.eq("idUsuario", idUsuario));
        if (idTerminal != null) {
            critGes.createCriteria("terminal").add(Restrictions.eq("idTerminal", idTerminal));
        }
        critTrx.add(Restrictions.eq("anulado", "N"));
        critTrx.createCriteria("idTipoOperacion").add(Restrictions.eq("idTipoOperacion", new Short("1")));
        critTrx.createCriteria("idEstadoTransaccion").add(Restrictions.eq("idEstadoTransaccion", 22L));

        if (fechaIni != null && fechaFin != null) {
            critTrx.add(Restrictions.sqlRestriction("trunc(fecha_ingreso) between to_date('" + fechaIni + "','ddmmyyyy') and to_date('" + fechaFin + "','ddmmyyyy')"));
        } else if (fechaIni != null) {
            critTrx.add(Restrictions.sqlRestriction("trunc(fecha_ingreso) = to_date('" + fechaIni + "','ddmmyyyy')"));
        }
        if (tipoPago != null) {
            critTrx.createCriteria("idTipoPago").add(Restrictions.eq("idTipoPago", tipoPago.equalsIgnoreCase("E") ? 1l : 2l));
        }

        if (idServicio != null) {
            critServicio.add(Restrictions.eq("idServicio", idServicio));
        }
        if (idFacturador != null) {
            critFact.add(Restrictions.eq("idFacturador", idFacturador));
        }
        //  critTrx.createAlias("idTipoPago", "tp");
//        ProjectionList pl = Projections.projectionList();
//        pl.add(Projections.groupProperty("s.descripcion"), "servicioDescripcion");
//        pl.add(Projections.groupProperty("f.descripcion"), "facturadorDescripcion");
//        pl.add(Projections.groupProperty("tp.idTipoPago"), "idTipoPago");



        // critTrx.setProjection(pl);

        critServicio.addOrder(Order.asc("descripcion"));
        critTrx.addOrder(Order.asc("idTransaccion"));

        return critTrx.list();
    }
}
