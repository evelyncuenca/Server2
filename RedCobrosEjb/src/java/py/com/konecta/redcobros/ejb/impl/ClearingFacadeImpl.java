/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.Clearing;
import py.com.konecta.redcobros.entities.ConfigClearingServicio;
import py.com.konecta.redcobros.entities.DistribucionClearing;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Servicio;
import py.com.konecta.redcobros.entities.TipoClearing;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClearingFacadeImpl extends GenericDaoImpl<Clearing, Long> implements ClearingFacade {

    @Resource
    private SessionContext context;
    @EJB
    private FeriadosFacade feriadosFacade;
    @EJB
    private ServicioFacade servicioFacade;
    @EJB
    private DistribucionClearingFacade distribucionClearingFacade;
    @EJB
    private FormContribFacade formContribFacade;
    @EJB
    private BoletaPagoContribFacade boletaPagoContribFacade;
    @EJB
    private ConfigClearingServicioFacade ccsFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public synchronized void realizarClearing(Date fechaDesde, Date fechaHasta, Long idRed, String codigoServicio, boolean recalcular) throws Exception {
        try {
            if (fechaHasta != null && fechaDesde.after(fechaHasta)) {
                throw new RuntimeException("Fecha hasta debe ser mayor o igual a fecha desde");
            }
            if (fechaHasta == null) {
                fechaHasta = fechaDesde;
            }
            Servicio servicio = new Servicio();
            servicio.setCodigoTransaccional(codigoServicio);
            Map<String, Object> mapaServicio = this.servicioFacade.get(servicio,
                    new String[]{"idServicio"});
            servicio.setIdServicio((Long) mapaServicio.get("idServicio"));
            Calendar calendarFecha = Calendar.getInstance();
            calendarFecha.setTime(fechaDesde);
            Clearing clearing = null, ejemploClearing = new Clearing();
            ejemploClearing.setConfigClearingServicio(new ConfigClearingServicio());
            ejemploClearing.getConfigClearingServicio().setRed(new Red(idRed));
            ejemploClearing.getConfigClearingServicio().setServicio(servicio);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            int anho, mes, dia;
            ConfigClearingServicio configClearingServicio;
            Double total;
            List<Map<String, Object>> listaMapaValores = null;
            List<Clearing> listaClearing = null;
            List<Long> listaIdTipoClearing = null;
            //se obtiene los tipos de clearing de este servicio para esta
            //y de esta red
            listaIdTipoClearing = this.ccsFacade.obtenerTiposDeClearing(idRed, servicio.getIdServicio());
            if (listaIdTipoClearing.size() == 0) {
                throw new Exception("No existe tipo de clearing asociado al servicio " + codigoServicio);
            }
            Date fechaActual;
            int contador;
            Double divisor;
            List<Long> listaRecaudador = null;
            Map<Long, List<Long>> mapaRecaudadorSucursales = null;
            while (Integer.parseInt(sdf.format(calendarFecha.getTime())) <= Integer.parseInt(sdf.format(fechaHasta))) {
                anho = calendarFecha.get(Calendar.YEAR);
                mes = calendarFecha.get(Calendar.MONTH) + 1;
                dia = calendarFecha.get(Calendar.DATE);
                //solo si es habil puede hacerse clearing
                if (this.feriadosFacade.esDiaHabil(anho, mes, dia)) {
                    fechaActual = calendarFecha.getTime();
                    ejemploClearing.setFecha(fechaActual);
                    if (recalcular) {
                        listaClearing = this.list(ejemploClearing);
                        for (Clearing cl : listaClearing) {
                            this.delete(cl.getIdClearing());
                        }
                    }
                    for (Long idTipoClearing : listaIdTipoClearing) {
                        ejemploClearing.getConfigClearingServicio().setTipoClearing(new TipoClearing(idTipoClearing));
                        if (recalcular || this.total(ejemploClearing) == 0) {
                            total = this.obtenerTotal(idTipoClearing, servicio, fechaActual, idRed);
                            if (total.doubleValue() > 0.0) {
                                configClearingServicio = this.ccsFacade.obtenerConfigClearingServicio(idRed, servicio.getIdServicio(), idTipoClearing, total);
                                divisor = (idTipoClearing == TipoClearing.PORCENTAJE_VOLUMEN) ? 100.0 : 1;
                                clearing = new Clearing();
                                clearing.setFecha(fechaActual);
                                clearing.setConfigClearingServicio(configClearingServicio);
                                clearing.setCantidad(total);
                                clearing.setFechaHoraCreacion(new Date());
                                clearing.setMontoDistribuido(new Double(Math.floor((configClearingServicio.getValor() / divisor) * total)));
                                clearing.setDistribucionClearingCollection(new ArrayList<DistribucionClearing>());
                                //primero con con recaudador y sucursal (y se obtienen las sucursales especiales)
                                //luego solo recaudador sin las sucursales ya procesadas (y se obtienen los recaudadores especiales)
                                //luego sin recaudadores procesados en el paso anterior
                                contador = 0;
                                do {
                                    contador++;
                                    if (listaMapaValores == null) {
                                        listaMapaValores = this.obtenerMapa(idTipoClearing, servicio, fechaActual, idRed, listaRecaudador, mapaRecaudadorSucursales, contador);
                                    }
                                    listaRecaudador = new ArrayList<Long>();
                                    mapaRecaudadorSucursales = new HashMap<Long, List<Long>>();
                                    listaMapaValores = this.distribucionClearingFacade.generarClearingDistribucion(listaMapaValores, clearing, divisor, contador, listaRecaudador, mapaRecaudadorSucursales);
                                } while (contador < 3);
                                this.save(clearing);
                            }
                        }
                    }
                }
                calendarFecha.add(Calendar.DATE, 1);
            }
        } catch (Exception e) {
            //abortar transaccion
            context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
    }

    private Double obtenerTotal(Long idTipoClearing,
            Servicio servicio,
            Date fechaActual,
            Long idRed) throws Exception {
        Double total = null;
        if (idTipoClearing.longValue() == TipoClearing.PORCENTAJE_VOLUMEN) {
            //porcentaje por volumen
            //para cada servicio
            //si el servicio incluye la comision en la factura,
            //habria que devolver el total de comisiones cobradas,
            //sino, se devuelve el total facturado porque sobre eso
            //se distribuira las comisiones
            if (servicio.getCodigoTransaccional().equalsIgnoreCase("PAGO-SET")) {
                total = this.boletaPagoContribFacade.totalBoletasClearing(fechaActual, idRed);
            } else {
                throw new Exception("Generacion de tipo de clearing de servicio no soportado: "
                        + servicio.getCodigoTransaccional()
                        + ", tipo de clearing: " + idTipoClearing);
            }
        } else if (idTipoClearing.longValue() == TipoClearing.FIJO_CANTIDAD) {
            //fijo por cantidad
            if (servicio.getCodigoTransaccional().equalsIgnoreCase("DDJJ-SET")) {
                total = this.formContribFacade.cantidadFormulariosClearing(fechaActual, idRed).doubleValue();
            } else if (servicio.getCodigoTransaccional().equalsIgnoreCase("PAGO-SET")) {
                total = this.boletaPagoContribFacade.cantidadBoletasClearing(fechaActual, idRed).doubleValue();
            } else {
                throw new Exception("Generacion de tipo de clearing de servicio no soportado: "
                        + servicio.getCodigoTransaccional()
                        + ", tipo de clearing: " + idTipoClearing);
            }
        } else {
            throw new Exception("Tipo de clearing no soportado: "
                    + idTipoClearing + ", servicio: " + servicio.getCodigoTransaccional());
        }
        return total;
    }

    private List<Map<String, Object>> obtenerMapa(Long idTipoClearing,
            Servicio servicio,
            Date fechaActual,
            Long idRed,
            List<Long> listaRecaudador,
            Map<Long, List<Long>> mapaRecaudadorSucursales,
            int contador) throws Exception {
        List<Map<String, Object>> listaMapaValores = new ArrayList<Map<String, Object>>();
        if (idTipoClearing.longValue() == TipoClearing.PORCENTAJE_VOLUMEN) {

            //porcentaje por volumen
            //para cada servicio
            if (servicio.getCodigoTransaccional().equalsIgnoreCase("PAGO-SET")) {
                if (contador == 1) {
                    //no se excluye nada
                    listaMapaValores = this.boletaPagoContribFacade.boletasClearingConTotal(fechaActual, idRed, null, null);
                } else if (contador == 2) {
                    //se incluye solo los parametros
                    listaMapaValores = this.boletaPagoContribFacade.boletasClearingConTotal(fechaActual, idRed, listaRecaudador, mapaRecaudadorSucursales);
                }
            } else {
                throw new Exception("Generacion de tipo de clearing de servicio no soportado: "
                        + servicio.getCodigoTransaccional()
                        + ", tipo de clearing: " + idTipoClearing);
            }
        } else if (idTipoClearing.longValue() == TipoClearing.FIJO_CANTIDAD) {

            //fijo por cantidad
            if (servicio.getCodigoTransaccional().equalsIgnoreCase("DDJJ-SET")) {
                if (contador == 1) {
                    listaMapaValores = this.formContribFacade.formulariosClearing(fechaActual, idRed, null, null);
                } else if (contador == 2) {
                    listaMapaValores = this.formContribFacade.formulariosClearing(fechaActual, idRed, listaRecaudador, mapaRecaudadorSucursales);
                }
            } else if (servicio.getCodigoTransaccional().equalsIgnoreCase("PAGO-SET")) {
                if (contador == 1) {
                    listaMapaValores = this.boletaPagoContribFacade.boletasClearingConCantidad(fechaActual, idRed, null, null);
                } else if (contador == 2) {
                    listaMapaValores = this.boletaPagoContribFacade.boletasClearingConCantidad(fechaActual, idRed, listaRecaudador, mapaRecaudadorSucursales);
                }
            } else {
                throw new Exception("Generacion de tipo de clearing de servicio no soportado: "
                        + servicio.getCodigoTransaccional()
                        + ", tipo de clearing: " + idTipoClearing);
            }

        } else {
            throw new Exception("Tipo de clearing no soportado: "
                    + idTipoClearing + ", servicio: " + servicio.getCodigoTransaccional());
        }
        return listaMapaValores;
    }

    public List<Map<String, Object>> listaReporte(Date fechaDesde, Date fechaHasta, Long idRed, String codigoServicio) throws Exception {
        if (fechaHasta == null) {
            fechaHasta = fechaDesde;
        }
        List<Map<String, Object>> lista = null;
        Servicio servicio = new Servicio();
        servicio.setCodigoTransaccional(codigoServicio);
        Map<String, Object> mapaServicio = this.servicioFacade.get(servicio,
                new String[]{"idServicio"});
        servicio.setIdServicio((Long) mapaServicio.get("idServicio"));
        lista = this.obtenerCriteriaReporte(fechaDesde, fechaHasta, idRed, servicio.getIdServicio()).list();
        Long idClearing;
        Double desde,hasta;
        NumberFormat formatter =  NumberFormat.getNumberInstance(Locale.US);
        formatter.setMaximumFractionDigits(0);
        for (Map<String, Object> mapaClearing : lista) {
            idClearing = (Long) mapaClearing.get("idClearing");
            desde = (Double) mapaClearing.get("desde");
            hasta = (Double) mapaClearing.get("hasta");
            if (desde!=null && hasta!=null) {
                mapaClearing.put("desdeHasta",
                        formatter.format(desde)+" - "+
                        formatter.format(hasta));
            } else {
                mapaClearing.put("desdeHasta",
                        "-----");
            }
            if (((Long)mapaClearing.get("idTipoClearing")).longValue()==TipoClearing.PORCENTAJE_VOLUMEN) {
                formatter.setMaximumFractionDigits(4);
            }
            mapaClearing.put("valorFormateado",
                formatter.format(mapaClearing.get("valor")));
            mapaClearing.put("fechaClearingString",
                    new SimpleDateFormat("dd/MM/yyyy").format((Date) mapaClearing.get("fechaClearing")));
            mapaClearing.put("distribucionClearing",
                    new JRMapCollectionDataSource((Collection)this.distribucionClearingFacade.listaReporte(idClearing)));
        }
        return lista;
    }

    private Criteria obtenerCriteriaReporte(Date fechaDesde, Date fechaHasta, Long idRed, Long idServicio) throws Exception {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(Clearing.class);
        Criteria cCCS = c.createCriteria("configClearingServicio", "ccs");
        cCCS.createCriteria("red", "r").add(Restrictions.eq("idRed", idRed));
        cCCS.createCriteria("servicio", "s").add(Restrictions.eq("idServicio", idServicio));
        cCCS.createCriteria("tipoClearing", "tc");
        //desde<=parametro
        c.add(Restrictions.ge("fecha", fechaDesde));
        if (fechaHasta==null) {
            fechaHasta=fechaDesde;
        }
        //hasta>parametro
        c.add(Restrictions.le("fecha", fechaHasta));
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("idClearing"), "idClearing");
        pl.add(Projections.property("fecha"), "fechaClearing");
        pl.add(Projections.property("r.descripcion"), "red");
        pl.add(Projections.property("s.descripcion"), "servicio");
        pl.add(Projections.property("tc.descripcion"), "tipoClearing");
        pl.add(Projections.property("tc.idTipoClearing"), "idTipoClearing");
        pl.add(Projections.property("ccs.valor"), "valor");
        pl.add(Projections.property("ccs.desde"), "desde");
        pl.add(Projections.property("ccs.hasta"), "hasta");
        pl.add(Projections.property("montoDistribuido"), "montoDistribuido");
        pl.add(Projections.property("cantidad"), "cantidad");
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        c.addOrder(Order.asc("fecha"));
        return c;
    }
}
