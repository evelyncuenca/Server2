/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.Clearing;
import py.com.konecta.redcobros.entities.ConfiguracionComisional;
import py.com.konecta.redcobros.entities.DistribucionClearing;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.entities.TipoClearing;

/**
 *
 * @author konecta
 */
@Stateless
public class DistribucionClearingFacadeImpl extends GenericDaoImpl<DistribucionClearing, Long> implements DistribucionClearingFacade {

    @EJB
    private ConfiguracionComisionalFacade configComFacade;
    @EJB
    private DistribucionClearingDetalleFacade dcdFacade;

    public List<Map<String, Object>> generarClearingDistribucion(List<Map<String, Object>> listaMapa,
            Clearing clearing,
            Double divisor,
            Integer iteracion,
            List<Long> listaRecaudador,
            Map<Long, List<Long>> mapaRecaudadorSucursales) throws Exception {
        Map<String, Object> mapa;
        Long idConfigClearingServicio, idRecaudador, idSucursal;
        idConfigClearingServicio = clearing.getConfigClearingServicio().getIdConfigClearingServicio();
        Double cantidad = null, monto = null;
        Long idConfiguracionComisional;
        DistribucionClearing distribucionClearing;
        List<Long> listaSucursales;
        List<Map<String, Object>> listaMapaRespuesta=null;
        long idTipoClearing = clearing.getConfigClearingServicio().getTipoClearing().getIdTipoClearing();
        for (int i = 0; i < listaMapa.size(); i++) {
            mapa = (Map<String, Object>) listaMapa.get(i);
            idRecaudador = (Long) mapa.get("idRecaudador");
            idSucursal = (Long) mapa.get("idSucursal");
            cantidad = this.obtenerCantidad(idTipoClearing, mapa);
            if (cantidad.doubleValue() == 0.0) {
                continue;
            }
            idConfiguracionComisional = this.configComFacade.obtenerIdConfiguracionComisional(
                    idConfigClearingServicio,
                    iteracion==3?null:idRecaudador,//en la tercera, por defecto ya
                    idSucursal);
            if (idConfiguracionComisional != null) {
                distribucionClearing = new DistribucionClearing();
                distribucionClearing.setClearing(clearing);
                distribucionClearing.setRecaudador(new Recaudador(idRecaudador));
                if (idSucursal!=null) {
                    distribucionClearing.setSucursal(new Sucursal(idSucursal));
                }
                distribucionClearing.setCantidad(cantidad);
                monto = new Double((clearing.getConfigClearingServicio().getValor() / divisor) * cantidad);
                monto = new BigDecimal (monto).setScale(4,RoundingMode.HALF_UP).doubleValue();
                distribucionClearing.setMontoDistribucion(monto);
                distribucionClearing.setConfiguracionComisional(new ConfiguracionComisional(idConfiguracionComisional));
                this.dcdFacade.generarDistribucionClearingDetalle(distribucionClearing, cantidad, idConfiguracionComisional, idRecaudador, idSucursal, divisor);
                clearing.getDistribucionClearingCollection().add(distribucionClearing);
            } else {
                if (iteracion.intValue()==1) {
                    //guardamos los recaudadores con sus sucursales para en la proxima traer
                    //solo datos de esos agrupado solo por sucursal nada mas
                    if (!listaRecaudador.contains(idRecaudador)) {
                        listaRecaudador.add(idRecaudador);
                    }
                    if (mapaRecaudadorSucursales.containsKey(idRecaudador)) {
                        listaSucursales = mapaRecaudadorSucursales.get(idRecaudador);
                    } else {
                        listaSucursales = new ArrayList<Long>();
                        mapaRecaudadorSucursales.put(idRecaudador, listaSucursales);
                    }
                    listaSucursales.add(idSucursal);
                } else if (iteracion.intValue() == 2) {
                    //guardamos el mapa actual para procesar en la ultima iteracion
                    if (listaMapaRespuesta==null) {
                        listaMapaRespuesta=new ArrayList<Map<String, Object>>();
                    }
                    listaMapaRespuesta.add(mapa);
                } else if (iteracion.intValue() == 3) {
                    Logger.getLogger(DistribucionClearingFacadeImpl.class.getName()).log(Level.SEVERE, "idConfigClearingServicio:{0}",idConfigClearingServicio);
                    Logger.getLogger(DistribucionClearingFacadeImpl.class.getName()).log(Level.SEVERE, "idRecaudador:{0}",idRecaudador);
                    Logger.getLogger(DistribucionClearingFacadeImpl.class.getName()).log(Level.SEVERE, "idSucursal:{0}",idSucursal);
                    Logger.getLogger(DistribucionClearingFacadeImpl.class.getName()).log(Level.SEVERE, "iteracion:{0}",iteracion);
                    throw new Exception("No se encontro configuracion alguna por defecto para "
                            + "la red " + clearing.getConfigClearingServicio().getRed().getDescripcion()
                            + ", servicio " + clearing.getConfigClearingServicio().getServicio().getDescripcion()
                            + " y tipo de clearing " + clearing.getConfigClearingServicio().getTipoClearing().getDescripcion());
                }
            }
        }
        return listaMapaRespuesta;
    }

    private Double obtenerCantidad(long idTipoClearing, Map<String, Object> mapa) throws Exception {
        Double cantidad = null;
        if (idTipoClearing == TipoClearing.PORCENTAJE_VOLUMEN) {
            //por volumen
            cantidad = (Double) mapa.get("montoTotal");
        } else if (idTipoClearing == TipoClearing.FIJO_CANTIDAD) {
            //fijo, recibimos cantidad de elementos
            cantidad = ((Integer) mapa.get("cantidad")).doubleValue();
        } else {
            throw new Exception("Generacion de distribucion de clearing: No se puede resolver si obtener cantidad o" + "montoTotal" + ", tipo de clearing: " + idTipoClearing);
        }
        return cantidad;
    }

    public List<Map<String, Object>> listaReporte(Long idClearing) throws Exception {
        List<Map<String, Object>> listaDC, lista = this.obtenerCriteriaReporte(idClearing).list();
        String descripcionCC;
        Long idDistribucionClearing, idConfiguracionComisional;
        ConfiguracionComisional cc;
        for (Map<String, Object> mapaClearing : lista) {
            idConfiguracionComisional = (Long) mapaClearing.get("idConfiguracionComisional");
            cc = this.configComFacade.get(idConfiguracionComisional);
            if (cc.getRecaudador() != null
                    && cc.getSucursal() != null){
                descripcionCC = cc.getRecaudador().getDescripcion() + " - " + cc.getSucursal().getDescripcion();
            } else if (cc.getRecaudador() != null) {
                descripcionCC = cc.getRecaudador().getDescripcion();
            } else {
                descripcionCC = "--";
            }
            mapaClearing.put("configuracionComisional", descripcionCC);
            idDistribucionClearing = (Long) mapaClearing.get("idDistribucionClearing");
            listaDC = (List<Map<String, Object>>) this.dcdFacade.listaReporte(idDistribucionClearing);
            mapaClearing.put("distribucionClearingDetalle",
                    new JRMapCollectionDataSource((Collection)listaDC));
        }
        return lista;
    }

    private Criteria obtenerCriteriaReporte(Long idClearing) throws Exception {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(DistribucionClearing.class);
        c.createCriteria("clearing").
                add(Restrictions.eq("idClearing", idClearing));
        c.createCriteria("configuracionComisional", "cc");
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("idDistribucionClearing"), "idDistribucionClearing");
        pl.add(Projections.property("cc.idConfiguracionComisional"), "idConfiguracionComisional");
        pl.add(Projections.property("montoDistribucion"), "montoDistribucion");
        pl.add(Projections.property("cantidad"), "cantidad");
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        c.addOrder(Order.asc("cc.descripcion"));
        return c;
    }
}
