/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.documenta.ws.RedCobro;
import py.com.documenta.ws.RedCobroService;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.RuteoServicio;
import py.com.konecta.redcobros.entities.Servicio;
import py.com.konecta.redcobros.wsc.clases.RedFacadeImplService;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RecaudadorFacadeImpl extends GenericDaoImpl<Recaudador, Long> implements RecaudadorFacade {

    private static Object bloqueo = new Object();
    @EJB
    private ServicioFacade servicioFacade;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    @Resource
    private SessionContext context;
    @EJB
    private RuteoServicioFacade ruteoServicioFacade;

    /*@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarServiciosEnRecaudador(Integer idRecaudador, String[] idServicios) throws Exception {
    try {
    Recaudador recaudador = this.recaudadorFacade.get(idRecaudador);
    if (this.obtenerRecaudadoresHabilitados(idRed).contains(recaudador)) {
    Collection<HabilitacionServRecaudador> col = this.obtenerHabilitaciones(idRed, idRecaudador);
    //se elimina las asociaciones para esa red con recaudador
    for (HabilitacionServRecaudador h : col) {
    this.habilitacionServRecaudadorFacade.delete(h.getHabilitacionServRecaudadorPK());
    }
    //this.eliminarHabilitaciones(idRed, idRecaudador);
    //se agrega los servicios actuales
    for (String idServicio : idServicios) {
    HabilitacionServRecaudador hab = new HabilitacionServRecaudador();
    HabilitacionServRecaudadorPK habPK = new HabilitacionServRecaudadorPK();
    habPK.setIdRed(idRed);
    habPK.setIdRecaudador(idRecaudador);
    habPK.setIdServicio(Integer.parseInt(idServicio));
    hab.setHabilitacionServRecaudadorPK(habPK);
    this.habilitacionServRecaudadorFacade.save(hab);
    }
    } else {
    throw new Exception("El recaudador aun no esta asociado a la red");
    }
    } catch (Exception e) {
    context.setRollbackOnly();
    e.printStackTrace();
    throw e;
    }
    }*/
    public boolean comprobarHabilitacionRecaudadorServicio(Long idServicio, Long idRecaudador) {
        //Recaudador rec = this.get(idRecaudador);
        //return rec.getServicioCollection().contains(new Servicio(idServicio));
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Integer cantidad = (Integer) hem.getSession().createCriteria(Servicio.class).add(Restrictions.eq("idServicio", idServicio)).createCriteria("recaudadorCollection").add(Restrictions.eq("idRecaudador", idRecaudador)).setProjection(Projections.rowCount()).list().get(0);
        return cantidad > 0;
    }

    public List<Recaudador> obtenerRecaudadoresPorServicio(Long idServicio, Integer start, Integer limit) {
        Criteria c = this.obtenerCriteriaRecaudadorPorServicio(idServicio);
        if (start != null && limit != null) {
            c.setFirstResult(start);
            c.setMaxResults(limit);
        }
        return c.list();
    }
    public List<Recaudador> obtenerRecaudadoresPorServicioRc(Long idServicio, Integer start, Integer limit) {
        Criteria c = this.obtenerCriteriaRecaudadorPorServicioRc(idServicio);
        if (start != null && limit != null) {
            c.setFirstResult(start);
            c.setMaxResults(limit);
        }
        return c.list();
    }

    public Integer cantidadRecaudadoresPorServicio(Long idServicio) {
        Criteria c = this.obtenerCriteriaRecaudadorPorServicio(idServicio);
        c.setProjection(Projections.rowCount());
        return (Integer) c.list().get(0);
    }

    private Criteria obtenerCriteriaRecaudadorPorServicio(Long idServicio) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(Recaudador.class).createCriteria("servicioCollection").add(Restrictions.eq("idServicio", idServicio));
        return c;
    }
    private Criteria obtenerCriteriaRecaudadorPorServicioRc(Long idServicio) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(Recaudador.class).createCriteria("servicioRcCollection").add(Restrictions.eq("idServicio", idServicio));
        return c;
    }

    public List<Servicio> obtenerServiciosNoHabilitados(Long idRecaudador) {
        List<Servicio> servicios = this.servicioFacade.list();
        Collection<Servicio> serviciosHabilitados = this.get(idRecaudador).getServicioCollection();
        servicios.removeAll(serviciosHabilitados);
        return servicios;
    }

    public List<Servicio> obtenerServiciosHabilitados(Long idRecaudador) {
        List<Servicio> serviciosHabilitados = new ArrayList<Servicio>();
        serviciosHabilitados.addAll(this.get(idRecaudador).getServicioCollection());
        return serviciosHabilitados;
    }

    /*public List<Servicio> obtenerServiciosHabilitados(Integer idRed, Integer idRecaudador) {
    HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
    List<Servicio> servicios =
    (List<Servicio>) hem.getSession().createCriteria(HabilitacionServRecaudador.class).add(Restrictions.eq("red", new Red(idRed))).add(Restrictions.eq("recaudador", new Recaudador(idRecaudador))).setProjection(Projections.projectionList().add(Projections.property("servicio"))).list();
    return servicios;
    }*/

    /*public List<HabilitacionServRecaudador> obtenerHabilitaciones(Integer idRed, Integer idRecaudador) {
    HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
    List<HabilitacionServRecaudador> habilitaciones =
    (List<HabilitacionServRecaudador>) hem.getSession().createCriteria(HabilitacionServRecaudador.class).add(Restrictions.eq("red", new Red(idRed))).add(Restrictions.eq("recaudador", new Recaudador(idRecaudador))).list();
    return habilitaciones;
    }*/
    private static RedCobroService service = null;
    private static ReentrantLock lock = new ReentrantLock();
    
    public static RedCobro getWSManager(String url, String uri, String localPart,
            int connTo, int readTo) {
        RedCobro pexSoap;

        URL wsdlURL = RedCobroService.class.getClassLoader().getResource("schema/RedCobroService.wsdl");
        try {
            if (service == null) {
                try {
                    lock.lock();
                    if (service == null) {
                        service = new RedCobroService(wsdlURL, new QName(uri, localPart));
                    }
                } catch (Exception ex) {
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception ex) {
        }

        pexSoap = service.getRedCobroPort();
        BindingProvider provider = (BindingProvider) pexSoap;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        provider.getRequestContext().put("com.sun.xml.ws.connect.timeout", connTo * 1000);
        provider.getRequestContext().put("com.sun.xml.ws.request.timeout", readTo * 1000);

        return pexSoap;
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Long obtenerProximoValorOrden(Long idRecaudador) throws Exception {
        try {
            synchronized (RecaudadorFacadeImpl.bloqueo) {
                String urlServidor = this.parametroSistemaFacade.get("urlWSNumeroOrden").getValor();
                RuteoServicio ruteoRC = ruteoServicioFacade.get(2L);
                RedFacadeImplService wsRed;
                Recaudador rec = this.get(idRecaudador);
                Long valor = null;
                if (rec.getNumeroOrdenProximo() == null
                        || rec.getNumeroOrdenMaximo() == null) {
                    //Long valores[]=this.redFacade.getProximoRangoOrden(idRecaudador);
                    wsRed = RedFacadeImplService.getInstance(urlServidor);
                    Long valores[] = wsRed.getRedFacadeImplPort().getProximoRangoOrden(idRecaudador).toArray(new Long[0]);
                    if (valores.length == 0) {
                        throw new Exception("Error en el servidor central de documenta al obtener rangos de valores "
                                + "para numero de ordenes");
                    }
                    valor = valores[0];
                    rec.setNumeroOrdenProximo(valor + 1);
                    rec.setNumeroOrdenMaximo(valores[1]);
                } else {                    
                    RedCobro rcService = getWSManager(ruteoRC.getUrlHost(), "http://ws.documenta.com.py/", "RedCobroService", ruteoRC.getConexionTo(), ruteoRC.getReadTo());
                    valor = Long.valueOf(rcService.getProximoNroOrden(idRecaudador.toString()));
                    rec.setNumeroOrdenProximo(valor + 1);
                    if (rec.getNumeroOrdenProximo() > rec.getNumeroOrdenMaximo()) {
                        //Long valores[]=this.redFacade.getProximoRangoOrden(idRecaudador);
                        wsRed = RedFacadeImplService.getInstance(urlServidor);
                        Long valores[] = wsRed.getRedFacadeImplPort().getProximoRangoOrden(idRecaudador).toArray(new Long[0]);
                        if (valores.length == 0) {
                            throw new Exception("Error en el servidor central de documenta al obtener rangos de valores "
                                    + "para numero de ordenes");
                        }
                        rec.setNumeroOrdenProximo(valores[0]);
                        rec.setNumeroOrdenMaximo(valores[1]);
                    }
                }
                this.merge(rec);
                return valor;
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
    }
}
