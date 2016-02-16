/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.Grupo;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.Terminal;
import py.com.konecta.redcobros.entities.Usuario;
import py.com.konecta.redcobros.entities.UsuarioTerminal;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GestionFacadeImpl extends GenericDaoImpl<Gestion, Long> implements GestionFacade {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private TerminalFacade terminalFacade;
    @EJB
    private UsuarioTerminalFacade utFacade;
    @Resource
    private SessionContext context;

    private Criteria getCriteriaComboFechaIniFechaFin(Long idTerminal, Long idUsuario, Date fechaIni, Date fechaFin, Integer primerResultado, Integer cantResultados) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(Gestion.class);
        c.add(Restrictions.ge("fechaApertura", fechaIni));
        c.add(Restrictions.eq("usuario", new Usuario(idUsuario)));
        if (fechaFin != null) {
            c.add(Restrictions.le("fechaApertura", fechaFin));
        }
        if (idTerminal != null) {
            c.add(Restrictions.eq("terminal", new Terminal(idTerminal)));
        }
        if (primerResultado != null && cantResultados != null) {
            c.setFirstResult(primerResultado);
            c.setMaxResults(cantResultados);
        }

        c.addOrder(Order.asc("nroGestion"));
        return c;
    }

    public Integer countCriteriaComboFechaIniFechaFin(Long idTerminal, Long idUsuario, Date fechaIni, Date fechaFin) {
        Criteria c = this.getCriteriaComboFechaIniFechaFin(idTerminal, idUsuario, fechaIni, fechaFin, null, null);
        c.setProjection(Projections.rowCount());
        Integer cantidad = (Integer) c.list().get(0);
        return cantidad;
    }

    public List<Gestion> comboCriteriaComboFechaIniFechaFin(Long idTerminal, Long idUsuario, Date fechaIni, Date fechaFin, Integer primerResultado, Integer cantResultados) {
        Criteria c = this.getCriteriaComboFechaIniFechaFin(idTerminal, idUsuario, fechaIni, fechaFin, primerResultado, cantResultados);
        return c.list();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Gestion obtenerGestionDelDia(Long idUsuarioTerminal, Boolean aperturaSimple) throws Exception {

        String exceptionMessage = null;
        try {
            UsuarioTerminal ut = this.utFacade.get(idUsuarioTerminal);
            Gestion g = new Gestion();
            g.setUsuario(ut.getUsuario());
            g.setCerrado("N");

            if (this.total(g) > 0 && aperturaSimple) {
                exceptionMessage = "El usuario tiene más de una gestion abierta. Contacte con el administrador";
                throw new Exception(exceptionMessage);
            } else {
                Date fechaHora = new Date();
                g.setFechaApertura(fechaHora);
                g.setTerminal(ut.getTerminal());
                // se busca la única gestion que debe existir POR TERMINAL, si hay más de uno get lanza una excepcion
                Gestion g2 = this.get(g);
                if (g2 != null) {
                    return g2;
                }
                // si g2 es igual a null se crea uno nuevo
                g.setFecHoraUltUpdate(fechaHora);
                g.setHoraApertura(fechaHora);
                g.setProximaPosicion(1);
                g.setNroGestion(ut.getTerminal().getProximoNroGestion());
                ut.getTerminal().setProximoNroGestion(ut.getTerminal().getProximoNroGestion() + 1);
                terminalFacade.merge(ut.getTerminal());
                g = this.merge(g);
                return g;
            }

        } catch (Exception e) {
            context.setRollbackOnly();
            if (exceptionMessage == null) {
                exceptionMessage = "Error al obtener una gestion";
            }
            throw new Exception(exceptionMessage, e);

        }

    }


   
    //Control por Usuario Terminal
    public boolean tieneGestionesAbiertasDeDiasAnteriores(UsuarioTerminal ut) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Integer cantidad = (Integer) hem.getSession().createCriteria(Gestion.class).
                add(Restrictions.eq("usuario", ut.getUsuario())).
                add(Restrictions.eq("terminal", ut.getTerminal())).
                add(Restrictions.eq("cerrado", "N")).
                add(Restrictions.lt("fechaApertura", new Date())).
                setProjection(Projections.rowCount()).list().get(0);
        return cantidad > 0;
    }

//    public boolean tieneGestionesAbiertasHoy(Usuario u) {
//        Date fechaHora = new Date();
//        Gestion rc = new Gestion();
//        rc.setUsuario(u);
//        rc.setFechaApertura(fechaHora);
//        rc.setCerrado("N");
//        return this.total(rc) > 0;
//    }
    public boolean tieneGestionesAbiertas(UsuarioTerminal ut, Date fecha) {
        Date fechaHora = new Date();
        Gestion rc = new Gestion();
        rc.setUsuario(ut.getUsuario());
        rc.setTerminal(ut.getTerminal());
        if (fecha != null) {
            rc.setFechaApertura(fechaHora);
        }

        rc.setCerrado("N");
    return this.total(rc) > 0;
    }

//    public boolean tieneGestionesAbiertasHoyOtrasTerminales(UsuarioTerminal ut) {
//        Date fechaHora = new Date();
//        Gestion rc = new Gestion();
//        rc.setUsuario(ut.getUsuario());
//        rc.setFechaApertura(fechaHora);
//        rc.setCerrado("N");
//        List<Gestion> lista=this.list(rc);
//        boolean otraTerminal=false;
//        for (Gestion g : lista) {
//            if (g.getTerminal().getIdTerminal().intValue()!=
//                    ut.getTerminal().getIdTerminal().intValue()) {
//                otraTerminal=true;
//            }
//        }
//        return otraTerminal;
//    }
//    public List<Terminal> tieneGestionesAbiertasHoyOtrasTerminales(UsuarioTerminal ut) {
//        Date fechaHora = new Date();
//        Gestion rc = new Gestion();
//        rc.setUsuario(ut.getUsuario());
//        rc.setFechaApertura(fechaHora);
//        rc.setCerrado("N");
//        List<Gestion> lista = this.list(rc);
//
//        List<Terminal> lTerminal = new ArrayList<Terminal>();
//
//        for (Gestion g : lista) {
//            if (g.getTerminal().getIdTerminal().intValue() !=
//                    ut.getTerminal().getIdTerminal().intValue()) {
//                lTerminal.add(g.getTerminal());
//
//            }
//        }
//        System.err.println("DESDE GESTION IMPL " + lTerminal.size());
//        return (lTerminal.size() == 0) ? null : lTerminal;
//    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cerrarGestion(Long idGestion) throws Exception {
        try {
            Date fechaHora = new Date();
            Gestion g = this.get(idGestion);
            g.setCerrado("S");
            g.setFechaCierre(fechaHora);
            g.setHoraCierre(fechaHora);
            g.setFecHoraUltUpdate(fechaHora);
            this.merge(g);
            for (Grupo grupo : g.getGrupoCollection()) {
                if (grupo.getCerrado().equalsIgnoreCase("N")) {
                    grupo.setHoraCierre(fechaHora);
                    grupo.setCerrado("S");
                    grupo.setTotalOperaciones(grupo.getProximaPosicion());
                    this.grupoFacade.merge(grupo);
                }
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            throw e;
        }

    }

    /*@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cerrarGestionesAbiertas(Usuario u) throws Exception {
    try {
    this.cerrarGestiones(u, null);
    } catch (Exception e) {
    context.setRollbackOnly();
    throw e;
    }
    }*/

    /*@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cerrarGestionesPorFecha(Usuario u, Date fecha) throws Exception {
    try {
    this.cerrarGestiones(u, fecha);
    } catch (Exception e) {
    context.setRollbackOnly();
    throw e;
    }
    }*/
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Gestion cerrarGestiones(UsuarioTerminal ut) throws Exception {
        try {
            if (this.tieneGestionesAbiertas(ut, null)) {
                Collection<Gestion> listaG = this.cerrarGestiones(ut, null);
                return listaG.iterator().next();
            } else {
                return null;
            }
        } catch (Exception e) {
            context.setRollbackOnly();
            throw e;
        }
    }

    private Collection<Gestion> cerrarGestiones(UsuarioTerminal ut, Date fecha) {

        Date fechaHora = new Date();
        Gestion ejemploG = new Gestion();
        ejemploG.setUsuario(ut.getUsuario());
        ejemploG.setTerminal(ut.getTerminal());
        ejemploG.setCerrado("N");
        if (fecha != null) {
            ejemploG.setFechaApertura(fecha);
        }
        Collection<Gestion> listaGestion = this.list(ejemploG);
        if (listaGestion.size() > 0) {
            for (Gestion gestion : listaGestion) {
                gestion.setCerrado("S");
                gestion.setFechaCierre(fechaHora);
                gestion.setHoraCierre(fechaHora);
                gestion.setFecHoraUltUpdate(fechaHora);
                this.merge(gestion);
                for (Grupo grupo : gestion.getGrupoCollection()) {
                    if (grupo.getCerrado().equalsIgnoreCase("N")) {
                        grupo.setHoraCierre(fechaHora);
                        grupo.setCerrado("S");
                        grupo.setTotalOperaciones(grupo.getProximaPosicion());
                        this.grupoFacade.merge(grupo);
                    }
                }
            }
        }
        return listaGestion;


    }
}
