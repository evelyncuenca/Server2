/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.ClearingFacturador;
import py.com.konecta.redcobros.entities.Corte;
import py.com.konecta.redcobros.entities.DistribucionClearingFacturador;
import py.com.konecta.redcobros.entities.HabilitacionFactRedPK;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Servicio;
import py.com.konecta.redcobros.entities.TipoCobro;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClearingFacturadorFacadeImpl extends GenericDaoImpl<ClearingFacturador, Long> implements ClearingFacturadorFacade {

    @EJB
    private DistribucionClearingFacturadorFacade distribucionClearingFacturadorFacade;
    @EJB
    private BoletaPagoContribFacade boletaPagoContribFacade;
    @EJB
    private FeriadosFacade feriadosFacade;
    @EJB
    private ServicioFacade servicioFacade;
    @EJB
    private HabilitacionFactRedFacade habilitacionFactRedFacade;
    @EJB
    private TipoCobroFacade tipoCobroFacade;
    @EJB
    private UtilFacade utilFacade;
    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public synchronized void generarClearingFacturador(
            Date fechaDesde, Date fechaHasta, Long idRed, String codigoServicio, boolean recalcular) throws Exception {
        try {
            if (fechaHasta != null && fechaDesde.after(fechaHasta)) {
                throw new RuntimeException("Fecha hasta debe ser mayor o igual a fecha desde");
            }
            if (fechaHasta == null) {
                fechaHasta = fechaDesde;
            }
            List<Servicio> listaServicio;
            Servicio ejemploServicio = new Servicio();
            if (codigoServicio==null) {
                ejemploServicio.setFacturable("S");
                listaServicio=this.servicioFacade.list(ejemploServicio);
            } else {
                ejemploServicio.setCodigoTransaccional(codigoServicio);
                Map<String, Object> mapaServicio = this.servicioFacade.get(ejemploServicio,
                        new String[]{"idServicio", "facturable"});
                ejemploServicio.setIdServicio((Long) mapaServicio.get("idServicio"));
                ejemploServicio.setFacturable((String) mapaServicio.get("facturable"));
                if (!ejemploServicio.getFacturable().equalsIgnoreCase("S")) {
                    throw new RuntimeException("El servicio elegido no es facturable");
                }
                listaServicio=new ArrayList<Servicio>();
                listaServicio.add(ejemploServicio);
            }
            Calendar calendarFecha = Calendar.getInstance();
            calendarFecha.setTime(fechaDesde);
            ClearingFacturador clearing = null, ejemploClearing = new ClearingFacturador();
            ejemploClearing.setRed(new Red(idRed));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            int anho, mes, dia;
            Double total;
            List<ClearingFacturador> listaClearing = null;
            Date fechaActual;
            List<TipoCobro> listaTipoCobros;
            while (Integer.parseInt(sdf.format(calendarFecha.getTime())) <= Integer.parseInt(sdf.format(fechaHasta))) {
                anho = calendarFecha.get(Calendar.YEAR);
                mes = calendarFecha.get(Calendar.MONTH) + 1;
                dia = calendarFecha.get(Calendar.DATE);
                //solo si es habil puede hacerse clearing
                for (Servicio servicio : listaServicio) {
                    if (this.feriadosFacade.esDiaHabil(anho, mes, dia)) {
                        fechaActual = calendarFecha.getTime();
                        ejemploClearing.setFecha(fechaActual);
                        ejemploClearing.setServicio(servicio);
                        if (recalcular) {
                            listaClearing = this.list(ejemploClearing);
                            for (ClearingFacturador cl : listaClearing) {
                                this.delete(cl.getIdClearingFacturador());
                            }
                        }
                        if (recalcular || this.total(ejemploClearing) == 0) {
                            listaTipoCobros = this.tipoCobroFacade.list();
                            for (TipoCobro tc : listaTipoCobros) {
                                total = this.obtenerTotalFacturado(servicio, fechaActual, idRed, tc);
                                if (total.doubleValue() > 0.0) {
                                    clearing = new ClearingFacturador();
                                    clearing.setRed(new Red(idRed));
                                    clearing.setTipoCobro(tc);
                                    clearing.setServicio(servicio);
                                    clearing.setFecha(fechaActual);
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(fechaActual);
                                    //se le pasa +1 porque se cuenta desde el dia actual, osea el dia de la operacion
                                    //y nosotros queremos que se cuente desde el dia sgte habil
                                    calendar=this.utilFacade.proximoVencimientoHabil(calendar, tc.getDiasAcreditacion()+1);
                                    clearing.setFechaAcreditacion(calendar.getTime());

                                    clearing.setFechaHoraCreacion(new Date());
                                    clearing.setMontoTotal(new Double(Math.round(total)));
                                    clearing.setDistribucionClearingFacturadorCollection(new ArrayList<DistribucionClearingFacturador>());
                                    //primero con con recaudador y sucursal (y se obtienen las sucursales especiales)
                                    //luego solo recaudador sin las sucursales ya procesadas (y se obtienen los recaudadores especiales)
                                    //luego sin recaudadores procesados en el paso anterior
                                    this.distribucionClearingFacturadorFacade.generarClearingDistribucion(clearing,
                                            this.obtenerMapaRecaudadores(servicio, fechaActual, idRed, tc));
                                    this.save(clearing);
                                }
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

    private Double obtenerTotalFacturado(Servicio servicio,
            Date fechaActual,
            Long idRed,
            TipoCobro tc) throws Exception {
        Double total = null;
        //si el ejemploServicio incluye la comision en la factura,
        //habria que devolver el total cobrado sin las comisiones,
        //sino, se devuelve el total facturado
        if (servicio.getCodigoTransaccional().equalsIgnoreCase("PAGO-SET")) {
            total = this.boletaPagoContribFacade.boletasClearingFacturadasConTotal(fechaActual, idRed, tc.getIdTipoCobro());
        } else {
            throw new Exception("El servicio "
                    + servicio.getCodigoTransaccional()
                    + " no es facturable");
        }
        return total;
    }

    private List<Map<String, Object>> obtenerMapaRecaudadores(
            Servicio servicio,
            Date fechaActual,
            Long idRed,
            TipoCobro tc) throws Exception {
        List<Map<String, Object>> listaMapaValores = new ArrayList<Map<String, Object>>();
        if (servicio.getCodigoTransaccional().equalsIgnoreCase("PAGO-SET")) {
            listaMapaValores = this.boletaPagoContribFacade.boletasClearingFacturadasConTotalPorRecaudador(fechaActual, idRed, tc.getIdTipoCobro());
        } else {
            throw new Exception("El servicio "
                    + servicio.getCodigoTransaccional()
                    + " no es facturable");
        }
        return listaMapaValores;
    }

    public List<Map<String, Object>> listaReporte(Date fechaDesde,Date fechaHasta, Long idRed, String codigoServicio) throws Exception {
        List<Map<String, Object>> lista = null;
        Servicio servicio = null;
        Long idServicio = null;
        if (codigoServicio != null) {
            servicio = new Servicio();
            servicio.setCodigoTransaccional(codigoServicio);
            Map<String, Object> mapaServicio = this.servicioFacade.get(servicio,
                    new String[]{"idServicio", "facturable"});
            servicio.setIdServicio((Long) mapaServicio.get("idServicio"));
            servicio.setFacturable((String) mapaServicio.get("facturable"));
            if (!servicio.getFacturable().equalsIgnoreCase("S")) {
                throw new RuntimeException("El servicio elegido no es facturable");
            }
            idServicio = servicio.getIdServicio();
        }
        lista = this.obtenerCriteriaReporte(fechaDesde,fechaHasta, idRed, idServicio).list();
        Long idClearingFacturador, idFacturador, idTipoCobro;
        HabilitacionFactRedPK habilitacionFactRedPK=new HabilitacionFactRedPK();
        habilitacionFactRedPK.setRed(idRed);
        Date fecha;
        for (Map<String, Object> mapaClearing : lista) {
            idClearingFacturador = (Long) mapaClearing.get("idClearingFacturador");
            idFacturador = (Long) mapaClearing.get("idFacturador");
            idTipoCobro = (Long) mapaClearing.get("idTipoCobro");
            fecha = (Date) mapaClearing.get("fechaClearingFacturador");
            habilitacionFactRedPK.setFacturador(idFacturador);
            mapaClearing.put("cuentaFacturador",
                    this.habilitacionFactRedFacade.get(habilitacionFactRedPK).getNumeroCuenta());
            mapaClearing.put("fechaClearingFacturadorString",
                    new SimpleDateFormat("dd/MM/yyyy").format((Date) mapaClearing.get("fechaClearingFacturador")));
            mapaClearing.put("fechaAcreditacionString",
                    new SimpleDateFormat("dd/MM/yyyy").format((Date) mapaClearing.get("fechaAcreditacion")));
            mapaClearing.put("distribucionClearingFacturador",
                    new JRMapCollectionDataSource((Collection)this.distribucionClearingFacturadorFacade.listaReporte(idClearingFacturador, idFacturador, idTipoCobro, fecha, idRed)));
//            Corte cut = obtenerCorte(fechaDesde);
//            mapaClearing.put("idCorte", cut.getIdCorte().longValue());
//            mapaClearing.put("fechaCorte", cut.getFechaCorte());
        }
        
        return lista;
    }

    private Criteria obtenerCriteriaReporte(Date fechaDesde,Date fechaHasta, Long idRed, Long idServicio) throws Exception {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(ClearingFacturador.class);
        //desde<=parametro
        c.add(Restrictions.ge("fecha", fechaDesde));
        if (fechaHasta==null) {
            fechaHasta=fechaDesde;
        }
        //hasta>parametro
        c.add(Restrictions.le("fecha", fechaHasta));
        c.add(Restrictions.eq("red", new Red(idRed)));
        if (idServicio != null) {
            c.add(Restrictions.eq("servicio", new Servicio(idServicio)));
        }
        /*c.createCriteria("red", "r").
                createCriteria("habilitacionFactRedCollection", "hfr").
                createCriteria("facturador", "f");
        c.createCriteria("servicio", "s");*/
        c.createCriteria("servicio", "s").createCriteria("facturador", "f");
        c.createAlias("red", "r");
        c.createAlias("tipoCobro", "tc");
        ProjectionList pl = Projections.projectionList();
        if (idServicio != null) {
            //reporte por ejemploServicio
            pl.add(Projections.property("idClearingFacturador"), "idClearingFacturador");
            pl.add(Projections.property("fecha"), "fechaClearingFacturador");
            pl.add(Projections.property("fechaAcreditacion"), "fechaAcreditacion");
            pl.add(Projections.property("r.descripcion"), "descripcionRed");
            pl.add(Projections.property("s.descripcion"), "descripcionServicio");
            pl.add(Projections.property("r.idRed"), "idRed");
            pl.add(Projections.property("s.idServicio"), "idServicio");
            pl.add(Projections.property("montoTotal"), "montoTotal");
            pl.add(Projections.property("tc.idTipoCobro"), "idTipoCobro");
            pl.add(Projections.property("tc.descripcion"), "descripcionTipoCobro");
            pl.add(Projections.property("f.idFacturador"), "idFacturador");
            pl.add(Projections.property("f.descripcion"), "descripcionFacturador");
            //pl.add(Projections.property("hfr.numeroCuenta"), "cuentaFacturador");
        } else {
            //agrupado por facturador para generar archivo clearing
            pl.add(Projections.groupProperty("fecha"), "fechaClearingFacturador");
            pl.add(Projections.groupProperty("f.idFacturador"), "idFacturador");
            pl.add(Projections.groupProperty("f.descripcion"), "descripcionFacturador");
            //pl.add(Projections.groupProperty("hfr.numeroCuenta"), "cuentaFacturador");
            pl.add(Projections.groupProperty("tc.idTipoCobro"), "idTipoCobro");
            pl.add(Projections.groupProperty("tc.descripcion"), "descripcionTipoCobro");
            pl.add(Projections.groupProperty("fechaAcreditacion"), "fechaAcreditacion");
            pl.add(Projections.sum("montoTotal"), "montoTotal");
        }
        c.setProjection(pl);
        c.addOrder(Order.asc("fecha"));
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return c;
    }
    
     public Corte obtenerCorte() throws Exception {
         Corte corte = new Corte();
         try{
            HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
            Criteria c = hem.getSession().createCriteria(Corte.class);    
            c.add(Restrictions.sqlRestriction("id_corte = (SELECT MAX(c.id_corte) FROM Corte c)"));
                        
            corte = (Corte) c.list().get(0);
            
         }catch(Exception e){
             e.printStackTrace();
             Logger.getLogger(ClearingFacturadorFacadeImpl.class.getName()).log(Level.SEVERE, null,e);
         }  
         return corte;
     }
}
