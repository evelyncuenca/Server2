/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.ClearingManual;
import py.com.konecta.redcobros.entities.DistribucionClearingManual;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClearingManualFacadeImpl extends GenericDaoImpl<ClearingManual, Long> implements ClearingManualFacade {

    @Resource
    private SessionContext context;
    @EJB
    private DistribucionClearingManualFacade dcmf;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public synchronized void realizarClearing(Date fecha,
            Date fechaAcreditacion,
            Character debitoCreditoCabecera,
            String descripcionCabecera,
            String numeroCuentaCabecera,
            Double montoCabecera,
            String []descripcionDetalle,
            String []numeroCuentaDetalle,
            Double []montoDetalle) throws Exception {
        try {
            ClearingManual clearingManual = null, ejemploClearingManual = new ClearingManual();
            DistribucionClearingManual dist=null;
            ejemploClearingManual.setFecha(fecha);
            if (this.total(ejemploClearingManual)>0) {
                clearingManual = this.get(ejemploClearingManual);
                this.delete(clearingManual.getIdClearingManual());
            }
            clearingManual=new ClearingManual();
            clearingManual.setCabeceraDebitoCredito(debitoCreditoCabecera);
            clearingManual.setFecha(fecha);
            clearingManual.setFechaAcreditacion(fechaAcreditacion);
            clearingManual.setFechaHoraCreacion(new Date());
            clearingManual.setMontoDistribuido(montoCabecera);
            clearingManual.setDescripcionBeneficiario(descripcionCabecera);
            clearingManual.setNumeroCuenta(numeroCuentaCabecera);
            clearingManual.setDistribucionClearingManualCollection(new ArrayList<DistribucionClearingManual>());
            for (int i=0;i<descripcionDetalle.length;i++) {
                dist=new DistribucionClearingManual();
                dist.setClearingManual(clearingManual);
                dist.setDescripcionBeneficiario(descripcionDetalle[i]);
                dist.setNumeroCuenta(numeroCuentaDetalle[i]);
                dist.setMonto(montoDetalle[i]);
                clearingManual.getDistribucionClearingManualCollection().add(dist);
            }
            this.save(clearingManual);
        } catch (Exception e) {
            //abortar transaccion
            context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
    }
    public List<Map<String, Object>> listaReporte(Date fecha) throws Exception {
        List<Map<String, Object>> lista = this.obtenerCriteriaReporte(fecha).list();
        Long idClearingManual;
        for (Map<String, Object> mapaClearing : lista) {
            idClearingManual=(Long)mapaClearing.get("idClearingManual");
            mapaClearing.put("distribucionClearingManual",
                    new JRMapCollectionDataSource((Collection)this.dcmf.listaReporte(idClearingManual)));
        }
        return lista;
    }

    private Criteria obtenerCriteriaReporte(Date fecha) throws Exception {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(ClearingManual.class);
        //desde<=parametro
        c.add(Restrictions.eq("fecha", fecha));
        ProjectionList pl = Projections.projectionList();
        //agrupado por facturador para generar archivo clearing
        pl.add(Projections.property("idClearingManual"), "idClearingManual");
        pl.add(Projections.property("fecha"), "fechaClearing");
        pl.add(Projections.property("fechaAcreditacion"), "fechaAcreditacion");
        pl.add(Projections.property("cabeceraDebitoCredito"), "cabeceraDebitoCredito");
        pl.add(Projections.property("numeroCuenta"), "numeroCuenta");
        pl.add(Projections.property("montoDistribuido"), "montoTotal");
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return c;
    }
}