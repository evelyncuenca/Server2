/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.List;
import javax.ejb.EJB;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.ConfigClearingServicio;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Servicio;
import py.com.konecta.redcobros.entities.ServicioRc;
import py.com.konecta.redcobros.entities.TipoClearing;

/**
 *
 * @author konecta
 */
@Stateless
public class ConfigClearingServicioFacadeImpl extends GenericDaoImpl<ConfigClearingServicio, Long> implements ConfigClearingServicioFacade {

    public void agregarModificarConfiguracion(
            Long idConfigClearingServicio,
            Long idRed,
            Long idServicio,
            Long idTipoClearing,
            Double desde,
            Double hasta,
            Double valor) throws Exception {
        if (!((desde != null && hasta != null) || (desde == null && hasta == null))) {
            throw new Exception("El rango debe estar definido en ambos limites, o debe ser por defecto");
        }
        if ((desde != null && hasta != null) && (desde >= hasta)) {
            throw new Exception("El rango desde debe ser menor que el rango hasta");
        }
        List<ConfigClearingServicio> rangos = this.verRangosCCS(idRed, idServicio, idTipoClearing);
        if (rangos.size() == 1 && rangos.get(0).getDesde() == null && rangos.get(0).getHasta() == null
                && idConfigClearingServicio == null) {
            if (desde != null && hasta != null) {
                throw new Exception("Existe un rango por defecto, por tanto en vez de agregar "
                        + "el nuevo rango deberia editar el rango existente");
            } else {
                throw new Exception("Existe un rango por defecto, por tanto no puede "
                        + "agregar otro rango por defecto");
            }

        }
        if (rangos.size() > 1 && desde == null && hasta == null) {
            throw new Exception("Ya existen rangos predefinidos por tanto no puede agregar rango por "
                    + "defecto");
        }
        if (valor.doubleValue() <= 0.0) {
            throw new Exception("El valor a repartir debe ser mayor a cero");
        }
        if (idTipoClearing.equals(1) && valor.doubleValue() > 100.0) {
            throw new Exception("El tipo de clearing requiere un valor porcentual, es decir menor o igual a 100");
        }
        double diferencia = valor.doubleValue() - (Math.round(valor));
        if (idTipoClearing.equals(2) && diferencia != 0.0) {
            throw new Exception("El tipo de clearing requiere un valor porcentual, es decir menor o igual a 100");
        }
        ConfigClearingServicio ccs;
        if (idConfigClearingServicio != null) {
            ccs = this.get(idConfigClearingServicio);
            ccs.setDesde(desde);
            ccs.setHasta(hasta);
            ccs.setValor(valor);
            this.update(ccs);
        } else {
            ccs = new ConfigClearingServicio();
            ccs.setRed(new Red(idRed));
            ccs.setServicio(new Servicio(idServicio));
            ccs.setTipoClearing(new TipoClearing(idTipoClearing));
            ccs.setValor(valor);
            ccs.setDesde(desde);
            ccs.setHasta(hasta);
            ccs.setHabilitado("S");
            this.save(ccs);
        }
    }

    public void eliminarConfiguracion(
            Long idConfigClearingServicio) throws Exception {
        ConfigClearingServicio ccs = this.get(idConfigClearingServicio);
        ccs.setHabilitado("N");
        this.update(ccs);

    }

    public List<ConfigClearingServicio> verRangosCCS(Long idRed, Long idServicio, Long idTipoClearing) {
        Criteria c = this.buscar(idRed, idServicio, idTipoClearing, null, null, null, true, true);
        return c.list();
    }

    public List<Long> obtenerTiposDeClearing(Long idRed, Long idServicio) {
        /*HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(ConfigClearingServicio.class);
        c.add(Restrictions.eq("red", new Red(idRed)));
        c.add(Restrictions.eq("servicio", new Servicio(idServicio)));
        c.createCriteria("tipoClearing", "tc");*/
        Criteria c = this.buscar(idRed, idServicio, null, null, null, null, true, true);
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("tc.idTipoClearing"));
        c.setProjection(pl);
        return c.list();
    }

    public ConfigClearingServicio obtenerConfigClearingServicio(Long idRed, Long idServicio, Long idTipoClearing,
            Double total) throws Exception {
        ConfigClearingServicio valor;
        //se busca segun red, servicio, tipo clearing
        //total
        valor = this.obtenerCCS(idRed, idServicio, idTipoClearing, total, null, null);
        if (valor != null) {
            return valor;
        }
        //si habia total, buscamos sin total
        if (total != null) {
            valor = this.obtenerCCS(idRed, idServicio, idTipoClearing, null, null, null);
            if (valor != null) {
                return valor;
            }
        }
        throw new Exception("No se encontro configuracion clearing servicio para los parametros dados: "
                + "idRed=" + idRed + ";"
                + "idServicio=" + idServicio + ";"
                + "idTipoClearing=" + idTipoClearing + ";"
                + "total=" + total + ";");
    }

    public ConfigClearingServicio obtenerConfigClearingServicio(Long idRed, Long idServicio, Long idTipoClearing,
            Double desde, Double hasta) throws Exception {
        ConfigClearingServicio valor = null;
        //se busca segun red, servicio, tipo clearing
        //desde, hasta
        if (idServicio < 3) {
            valor = this.obtenerCCS(idRed, idServicio, idTipoClearing, null, desde, hasta);
        } else {
            ConfigClearingServicio ejemplo = new ConfigClearingServicio();
            ejemplo.setHabilitado("S");
            Red red = new Red();
            red.setIdRed(idRed);
            ejemplo.setRed(red);
            ServicioRc src = new ServicioRc();
            src.setIdServicio(idServicio.intValue());
            ejemplo.setServicioRc(src);
            if (idTipoClearing != null) {
                TipoClearing tc = new TipoClearing();
                tc.setIdTipoClearing(idTipoClearing);
                ejemplo.setTipoClearing(tc);
            }
            valor = configClearingServicioFacade.get(ejemplo);
        }
        return valor;
    }

    private ConfigClearingServicio obtenerCCS(Long idRed, Long idServicio, Long idTipoClearing,
            Double total, Double desde, Double hasta) throws Exception {
        Criteria c = this.buscar(idRed, idServicio, idTipoClearing, total, desde, hasta, false, true);
        List<ConfigClearingServicio> lista = c.list();
        ConfigClearingServicio res = null;
        if (lista.size() > 1) {
            throw new Exception("Existe mas de una configuracion para los parametros dados: "
                    + "idRed=" + idRed + ";"
                    + "idServicio=" + idServicio + ";"
                    + "idTipoClearing=" + idTipoClearing + ";"
                    + "total=" + total + ";");
        } else if (lista.size() == 1) {
            res = lista.get(0);
        }
        return res;
    }

    private Criteria buscar(Long idRed, Long idServicio, Long idTipoClearing,
            Double total, Double desde, Double hasta, Boolean verRangos, Boolean verActual) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(ConfigClearingServicio.class);
        c.createCriteria("red", "r").add(Restrictions.eq("idRed", idRed));
        if (idServicio < 3) {
            c.createCriteria("servicio", "s").add(Restrictions.eq("idServicio", idServicio));
        } else {
            c.createCriteria("servicioRc", "s").add(Restrictions.eq("idServicio", idServicio.intValue()));
        }
        Criteria cTC = c.createCriteria("tipoClearing", "tc");
        if (idTipoClearing != null) {
            cTC.add(Restrictions.eq("idTipoClearing", idTipoClearing));
        }
        if (total != null) {
            //desde<=parametro
            c.add(Restrictions.le("desde", total));
            //hasta>=parametro
            c.add(Restrictions.ge("hasta", total));
        } else {
            if (desde != null && hasta != null) {
                //modo encontrar configuracion se ingreso
                //los montos deben ser igual a los ingresados si o si
                c.add(Restrictions.eq("desde", desde));
                c.add(Restrictions.eq("hasta", hasta));
            } else if (!verRangos) {
                //modo encontrar configuracion se ingreso
                //los montos deben ser null si o si
                c.add(Restrictions.isNull("desde"));
                c.add(Restrictions.isNull("hasta"));
            }
            //sino no se aplica restriccion sobre rangos
            //se obtenndran todos los rangos
        }
        if (verActual) {
            c.add(Restrictions.eq("habilitado", "S"));
        }
        return c;
    }
    @EJB
    private ConfigClearingServicioFacade configClearingServicioFacade;
}
