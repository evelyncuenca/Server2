/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.BoletaPagoContrib;
import py.com.konecta.redcobros.entities.EntidadFinanciera;
import py.com.konecta.redcobros.entities.EstadoTransaccion;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;

import py.com.konecta.redcobros.entities.FormularioImpuesto;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.TipoCobro;
import py.com.konecta.redcobros.entities.TipoPago;
import py.com.konecta.redcobros.entities.Transaccion;
import py.com.konecta.redcobros.utiles.Utiles;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
@Stateless
public class BoletaPagoContribImpl extends GenericDaoImpl<BoletaPagoContrib, Long> implements BoletaPagoContribFacade {

    @EJB
    private RedFacade redFacade;
    @EJB
    private TransaccionFacade transaccionFacade;
    @EJB
    private FormularioImpuestoFacade formularioImpuestoFacade;

    public List<Map<String, Object>> listaReporteRechazadosNoRechazados(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Boolean todosFormaPago, Date fechaDesde, Date fechaHasta, Boolean filtroAceptado, Boolean filtroRechazados, Boolean todosAceptadosRechazados) throws Exception {
        List<Map<String, Object>> lista = null;
        Criteria c = this.obtenerCriteriaReporteRechazadosNoRechazados(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, fechaDesde, fechaHasta, filtroAceptado, filtroRechazados, todosAceptadosRechazados);

        // Criteria cTransaccion = c.createCriteria("transaccion", "transaccion");
       // Criteria cGrupo = c.createCriteria("grupo", "grupo");
       // Criteria cGestion = cGrupo.createCriteria("gestion", "gestion");
      //  Criteria cTerminal = cGestion.createCriteria("terminal", "terminal");
        // Criteria cTipoPago = cTransaccion.createCriteria("tipoPago", "tipoPago");
        Criteria cContribuyente = c.createCriteria("contribuyente", "contribuyente");
        ProjectionList pl = Projections.projectionList();
        //Transaccion tt = new Transaccion();
        //BoletaPagoContrib bp = new BoletaPagoContrib();
        //  Gestion gg = new Gestion();
        ///Projections
        pl.add(Projections.property("transaccion.idTransaccion"), "idTransaccion");
        pl.add(Projections.property("tipoPago.descripcion"), "tipoPagoDescripcion");
        pl.add(Projections.property("transaccion.fechaHoraSistema"), "fechaCobro");
        pl.add(Projections.property("transaccion.numeroCheque"), "numeroCheque");
        pl.add(Projections.property("terminal.codigoTerminal"), "codigoTerminal");
        pl.add(Projections.property("transaccion.idTransaccion"), "idTransaccion");
        pl.add(Projections.property("gestion.nroGestion"), "descripcionGestion");
        pl.add(Projections.property("numeroOrden"), "numeroOrden");
        pl.add(Projections.property("consecutivo"), "consecutivo");
        pl.add(Projections.property("contribuyente.rucNuevo"), "rucNuevo");
        pl.add(Projections.property("tipoPago.descripcion"), "formaPago");
        pl.add(Projections.property("transaccion.estadoTransaccion.idEstadoTransaccion"), "idEstadoTransaccion");

        pl.add(Projections.property("total"), "montoPagado");
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        lista = (List<Map<String, Object>>) c.list();

        for (Map<String, Object> mapaBoletas : lista) {

            if (mapaBoletas.get("idEstadoTransaccion") != null && EstadoTransaccion.RECHAZADO == ((Long) mapaBoletas.get("idEstadoTransaccion")).longValue()) {
                mapaBoletas.put("rechazado", "S");
            } else if (mapaBoletas.get("idEstadoTransaccion") != null && EstadoTransaccion.ACEPTADO == ((Long) mapaBoletas.get("idEstadoTransaccion")).longValue()) {
                mapaBoletas.put("rechazado", "N");
            } else {
                mapaBoletas.put("rechazado", "-");
            }
            mapaBoletas.put("montoPagado", UtilesSet.formatearNumerosSeparadorMiles(mapaBoletas.get("montoPagado"), false));

            Map<String, Object> mapaTransaccion = transaccionFacade.get(new Transaccion((Long) mapaBoletas.get("idTransaccion")),
                    new String[]{"idTransaccion", "formContrib.formulario.numeroFormulario", "formContrib.enviado", "formContrib.totalPagar", "entidadFinanciera.descripcion"});
            FormularioImpuesto ejemploFI = new FormularioImpuesto();
            if (mapaTransaccion != null && mapaTransaccion.get("formContrib.formulario.numeroFormulario") != null) {
                ejemploFI.setNumeroFormulario((Integer) mapaTransaccion.get("formContrib.formulario.numeroFormulario"));
                mapaBoletas.put("numeroImpuesto", formularioImpuestoFacade.get(ejemploFI).getImpuesto());
            } else {
                mapaBoletas.put("numeroImpuesto", "-");
            }
            if (mapaTransaccion != null && mapaTransaccion.get("formContrib.totalPagar") != null) {
                mapaBoletas.put("montoTotal", (UtilesSet.formatearNumerosSeparadorMiles(mapaTransaccion.get("formContrib.totalPagar"), false)));
            } else {
                mapaBoletas.put("montoTotal", null);
            }
            if (mapaTransaccion != null && mapaTransaccion.get("formContrib.enviado") != null) {
                mapaBoletas.put("enviado", (mapaTransaccion.get("formContrib.enviado")));
            } else {
                mapaBoletas.put("enviado", "-");
            }
            if (mapaTransaccion != null && mapaTransaccion.get("entidadFinanciera.descripcion") != null) {
                mapaBoletas.put("banco", (mapaTransaccion.get("entidadFinanciera.descripcion")));
            } else {
                mapaBoletas.put("banco", null);
            }
        }
        return lista;
    }

    private Criteria obtenerCriteriaReporteRechazadosNoRechazados(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Boolean todosFormaPago, Date fechaDesde, Date fechaHasta, Boolean filtroAceptado, Boolean filtroRechazados, Boolean todosAceptadosRechazados) throws Exception {
        Criteria c = this.getCriteriaControlFormularioBoletas(idRed, idRecaudador, idSucursal, idTerminal, idGestion, filtroCheque, filtroEfectivo, todosFormaPago, fechaDesde, fechaHasta, filtroAceptado, filtroRechazados, todosAceptadosRechazados);
        return c;
    }

    /*****************************/
    private Criteria getCriteriaControlFormularioBoletas(Long idRed, Long idRecaudador, Long idSucursal, Long idTerminal, Long idGestion, Boolean filtroCheque, Boolean filtroEfectivo, Boolean todosFormaPago, Date fechaDesde, Date fechaHasta, Boolean filtroAceptado, Boolean filtroRechazados, Boolean todosAceptadosRechazados) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c1 = hem.getSession().createCriteria(BoletaPagoContrib.class);
        Criteria cTransaccion = c1.createCriteria("transaccion", "transaccion");
        // c1.add(Restrictions.isNotNull("boletaPagoContrib"));//Solo las que tienen boleta
        if (fechaDesde != null && fechaHasta != null) {
            cTransaccion.add(Restrictions.between("fechaHoraSistema", Utiles.primerMomentoFecha(fechaDesde), Utiles.ultimoMomentoFecha(fechaHasta)));

        } else if (fechaDesde != null) {
            cTransaccion.add(Restrictions.between("fechaHoraSistema", Utiles.primerMomentoFecha(fechaDesde), Utiles.ultimoMomentoFecha(fechaDesde)));
        }
        Criteria cTipoPago = cTransaccion.createCriteria("tipoPago", "tipoPago");
        //Criteria cEstadoTransaccion = cTransaccion.createCriteria("estadoTransaccion", "estadoTransaccion");
        //Transaccion tra = new Transaccion();
        //tra.getGestion().getTerminal().getSucursal().getRecaudador().getR

       

            Criteria cBoleta = cTransaccion.createCriteria("boletaPagoContrib", "boletaPagoContrib");
            Criteria cGestion = cTransaccion.createCriteria("gestion", "gestion");
            Criteria cTerminal = cGestion.createCriteria("terminal", "terminal");
            Criteria cSucursal = cTerminal.createCriteria("sucursal", "sucursal");
            Criteria cRecaudador = cSucursal.createCriteria("recaudador", "recaudador");
            Criteria cRed = cRecaudador.createCriteria("red", "red");

            // cRed = cRecaudador.createCriteria("red");
            cBoleta.addOrder(Order.asc("consecutivo"));
            if (idRed != null) {
                 cRed.add(Restrictions.eq("idRed", idRed));
            }


            //  BoletaPagoContrib bp = new BoletaPagoContrib();

            if (idRecaudador != null) {
                cRecaudador.add(Restrictions.eq("idRecaudador", idRecaudador));

            }
            if (idSucursal != null) {
                cSucursal.add(Restrictions.eq("idSucursal", idSucursal));
            }

            if (idTerminal != null) {
                cTerminal.add(Restrictions.eq("idTerminal", idTerminal));
            }

            if (idGestion != null) {
                cGestion.add(Restrictions.eq("idGestion", idGestion));
            }

        


        if (filtroCheque && !todosFormaPago) {
            cTipoPago.add(Restrictions.eq("idTipoPago", TipoPago.CHEQUE));
        } else if (filtroEfectivo && !todosFormaPago) {
            cTipoPago.add(Restrictions.eq("idTipoPago", TipoPago.EFECTIVO));
        }
        if (filtroAceptado && !todosAceptadosRechazados) {
            //cEstadoTransaccion.add(Restrictions.eq("idEstadoTransaccion", EstadoTransaccion.ACEPTADO));No anda asi
            cTransaccion.add(Restrictions.eq("estadoTransaccion", new EstadoTransaccion(new Long(EstadoTransaccion.ACEPTADO))));
        } else if (filtroRechazados && !todosAceptadosRechazados) {
            cTransaccion.add(Restrictions.eq("estadoTransaccion", new EstadoTransaccion(new Long(EstadoTransaccion.RECHAZADO))));
        }

        //cG.addOrder(Order.asc("consecutivo"));
        return c1;
    }

    /*****************************/
    private Criteria getCriteriaERA(TipoPago tipoPago, Long idRed, Date fecha) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c1 = hem.getSession().createCriteria(BoletaPagoContrib.class);
        c1.add(Restrictions.eq("fechaCobro", fecha));
        c1.createCriteria("transaccion") // .add(Restrictions.eq("fecha", fecha))
                .add(Restrictions.eq("tipoPago", tipoPago)).add(Restrictions.eq("flagAnulado", "N"));
        Criteria c2 = c1.createCriteria("grupo").add(Restrictions.eq("cerrado", "S"));
        Criteria c21 = c2.createCriteria("gestion");
        Criteria c211 = c21.createCriteria("terminal");
        c211.createCriteria("sucursal").
                createCriteria("recaudador").
                createCriteria("red").
                add(Restrictions.eq("idRed", idRed));
        c211.addOrder(Order.asc("codigoCajaSet"));
        //c21.createCriteria("usuario").addOrder(Order.asc("codigo"));
        c1.addOrder(Order.asc("consecutivo"));
        return c1;
    }

    public List<BoletaPagoContrib> boletasPagosERA(TipoPago tipoPago, Long idRed, Date fecha) {
        return (List<BoletaPagoContrib>) this.getCriteriaERA(tipoPago, idRed, fecha).list();
    }

    public boolean existeBoletasPagosERA(TipoPago tipoPago, Long idRed, Date fecha) {
        Criteria c = this.getCriteriaERA(tipoPago, idRed, fecha);
        c.setProjection(Projections.rowCount());
        Integer cantidad = (Integer) c.list().get(0);
        return cantidad > 0;

    }

    public List<Map<String, Object>> boletasClearingConCantidad(Date fecha,
            Long idRed,
            List<Long> listaRecaudadores,
            Map<Long, List<Long>> mapaRecaudadorSucursales) {
        return this.obtenerCriteriaClearing(fecha, idRed, false, true, true, false, null, null,
                listaRecaudadores, mapaRecaudadorSucursales).list();
    }

    public List<Map<String, Object>> boletasClearingConTotal(Date fecha,
            Long idRed,
            List<Long> listaRecaudadores,
            Map<Long, List<Long>> mapaRecaudadorSucursales) {
        return this.obtenerCriteriaClearing(fecha, idRed, true, false, true, false, null, null,
                listaRecaudadores, mapaRecaudadorSucursales).list();
    }

    public List<Map<String, Object>> boletasClearingFacturadasConTotalPorRecaudador(Date fecha,
            Long idRed, Long idTipoCobro) {
        Long idEntidadFinanciera = null;
        if (idTipoCobro.longValue() == TipoCobro.CHEQUES_BANCO_LOCAL || idTipoCobro.longValue() == TipoCobro.CHEQUES_OTRO_BANCO) {
            Red red = new Red(idRed);
            Map<String, Object> mapaRed = this.redFacade.get(red,
                    new String[]{"bancoClearing.idEntidadFinanciera"});
            idEntidadFinanciera = (Long) mapaRed.get("bancoClearing.idEntidadFinanciera");
        }
        Criteria c = this.obtenerCriteriaClearing(fecha, idRed, true, false, true, true, idTipoCobro, idEntidadFinanciera,
                null, null);
        return c.list();
    }

    public Double boletasClearingFacturadasConTotal(Date fecha,
            Long idRed, Long idTipoCobro) {
        Long idEntidadFinanciera = null;
        if (idTipoCobro.longValue() == TipoCobro.CHEQUES_BANCO_LOCAL || idTipoCobro.longValue() == TipoCobro.CHEQUES_OTRO_BANCO) {
            Red red = new Red(idRed);
            Map<String, Object> mapaRed = this.redFacade.get(red,
                    new String[]{"bancoClearing.idEntidadFinanciera"});
            idEntidadFinanciera = (Long) mapaRed.get("bancoClearing.idEntidadFinanciera");
        }
        List<Map<String, Object>> lista = this.obtenerCriteriaClearing(fecha, idRed, true, false, false, true, idTipoCobro, idEntidadFinanciera,
                null, null).list();
        if (lista.get(0).get("montoTotal") == null) {
            return 0.0;
        }
        return (Double) lista.get(0).get("montoTotal");
    }

    public Double totalBoletasClearing(Date fecha, Long idRed) {
        List<Map<String, Object>> lista = (List<Map<String, Object>>) this.obtenerCriteriaClearing(fecha, idRed, true, false, false, false, null, null, null, null).list();
        if (lista.get(0).get("montoTotal") == null) {
            return 0.0;
        }
        return (Double) lista.get(0).get("montoTotal");
    }

    public Integer cantidadBoletasClearing(Date fecha, Long idRed) {
        List<Map<String, Object>> lista = (List<Map<String, Object>>) this.obtenerCriteriaClearing(fecha, idRed, false, true, false, false, null, null, null, null).list();
        return (Integer) lista.get(0).get("cantidad");
    }

    private Criteria obtenerCriteriaClearing(Date fecha, Long idRed,
            boolean total, boolean cantidad, boolean agrupado, boolean procesoPagoFacturadores,
            Long idTipoCobro, Long idEntidadFinanciera,
            List<Long> listaRecaudadores,
            Map<Long, List<Long>> mapaRecaudadorSucursales) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c1 = hem.getSession().createCriteria(BoletaPagoContrib.class);
        c1.add(Restrictions.eq("fechaCobro", fecha));
        Criteria cTransaccion = c1.createCriteria("transaccion", "t");
        cTransaccion.add(Restrictions.eq("flagAnulado", "N"));
        if (procesoPagoFacturadores) {
            if (idTipoCobro == TipoCobro.EFECTIVO) {
                cTransaccion.add(Restrictions.eq("tipoPago", new TipoPago(TipoPago.EFECTIVO)));
            } else {
                cTransaccion.add(Restrictions.eq("tipoPago", new TipoPago(TipoPago.CHEQUE)));
                if (idTipoCobro == TipoCobro.CHEQUES_BANCO_LOCAL) {
                    cTransaccion.add(Restrictions.eq("entidadFinanciera", new EntidadFinanciera(idEntidadFinanciera)));
                } else {
                    cTransaccion.add(Restrictions.ne("entidadFinanciera", new EntidadFinanciera(idEntidadFinanciera)));
                }
            }
        }
        Criteria cGrupo = c1.createCriteria("grupo");
        Criteria cSucursal = cGrupo.add(Restrictions.eq("cerrado", "S")).createCriteria("gestion").createCriteria("terminal").createCriteria("sucursal", "suc");
        Criteria cRecaudador = cSucursal.createCriteria("recaudador", "rec");
        if (listaRecaudadores != null) {
            Disjunction disjunction = Restrictions.disjunction();
            List<Long> listaSucursales;
            for (Long idRec : listaRecaudadores) {
                listaSucursales = mapaRecaudadorSucursales.get(idRec);
                disjunction.add(Restrictions.and(
                        Restrictions.eq("recaudador", new Recaudador(idRec)),
                        Restrictions.in("idSucursal", listaSucursales)));
            }
            cSucursal.add(disjunction);
        }
        cRecaudador.add(Restrictions.eq("red", new Red(new Long(idRed)))).createCriteria("red", "r");
        ProjectionList pl = Projections.projectionList();
        if (total) {
            pl.add(Projections.sum("total"), "montoTotal");
        } else if (cantidad) {
            pl.add(Projections.rowCount(), "cantidad");
        }
        if (agrupado) {
            //traemos todo y como hashmap
            if (listaRecaudadores == null) {
                pl.add(Projections.groupProperty("rec.idRecaudador"), "idRecaudador");
                //para proceso de pago a facturadores, solo por recaudador es el tema
                if (!procesoPagoFacturadores) {
                    pl.add(Projections.groupProperty("suc.idSucursal"), "idSucursal");
                }
            } else {
                //agrupamos solo por rec ya que excluimos sucursales
                pl.add(Projections.groupProperty("rec.idRecaudador"), "idRecaudador");
            }//no agrupamos nada, ultimo nivel
        }
        c1.setProjection(pl);
        c1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return c1;
    }
}
