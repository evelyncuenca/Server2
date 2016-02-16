/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.*;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.entities.*;
import py.com.konecta.redcobros.utiles.ResultadoAutenticarCobrosWeb;
import py.com.konecta.redcobros.utiles.ResultadoControlFranjaHoraria;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioTerminalFacadeImpl extends GenericDaoImpl<UsuarioTerminal, Long> implements UsuarioTerminalFacade {

    @Resource
    private SessionContext context;
    @EJB
    private TerminalFacade termFacade;
    @EJB
    private UtilFacade utilFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private PermisosFacade permisosFacade;
    @EJB
    private FranjaHorariaDetFacade franjaHorariaDetFacade;
    @EJB
    private LoginExcepcionFacade loginExcepcionFacade;
    private static final Logger logger = Logger.getLogger(UsuarioTerminalFacadeImpl.class.getName());

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public UsuarioTerminal auntenticarGestion(String user, String passwd, String mac) {
        try {
            UsuarioTerminal ut = new UsuarioTerminal();
            //Terminal t = new Terminal();
            Usuario u = new Usuario(null, user, UtilesSet.getSha1(passwd));
            List<Usuario> lUsuario = usuarioFacade.list(u, false, true);
            if (lUsuario.size() == 1 && lUsuario.get(0).getEsSupervisor().equals("R")) {
                //Si estamos aquí es porque existe solamente un usuario con nombre root y password dado. Entra de sin mas preambulos al sistema.
                //u.getNombreUsuario().equalsIgnoreCase("root");
                ut.setUsuario(lUsuario.get(0));
                return ut;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            context.setRollbackOnly();
        }
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public ResultadoAutenticarCobrosWeb auntenticar(String user, String passwd, String hash, String appAutenticar) {
        logger.log(Level.INFO, "IN: [{0};{1};{2};{3}]", new Object[]{user, passwd, hash, appAutenticar});
        ResultadoAutenticarCobrosWeb resultado = null;
        try {
            Usuario u = new Usuario(null, user, passwd);
            Terminal t = new Terminal();
            t.setCodigoHash(hash);
            UsuarioTerminal ut = new UsuarioTerminal();
            ut.setUsuario(u);
            ut.setTerminal(t);
            logger.log(Level.INFO, "UsuarioTerminal: {0}", ut);
            ut = this.get(ut);
            logger.log(Level.INFO, "UsuarioTerminal: {0}", ut);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date fechaActualD = new Date();
            Integer fechaActual = Integer.parseInt(sdf.format(fechaActualD));

            if (ut == null && (u = usuarioFacade.get(u)) != null) {

                LoginExcepcion ejemplo = new LoginExcepcion(u.getRecaudador().getIdRecaudador());
                ejemplo = loginExcepcionFacade.get(ejemplo);
                if (ejemplo != null && ejemplo.getHabilitado().equalsIgnoreCase("S")) {
                    ut = new UsuarioTerminal();
                    ut.setUsuario(u);
                    List<UsuarioTerminal> listUt = this.list(ut);
                    if (listUt.size() == 1) {
                        ut = listUt.get(0);
                    } else {
                        ut = null;
                    }
                }
            }

            if (ut != null) {
                if (ut.getTerminal().getFechaConsecutivoSet() == null
                        || Integer.parseInt(sdf.format(ut.getTerminal().getFechaConsecutivoSet()))
                        < fechaActual) {
                    ut.getTerminal().setConsecutivoActualForm(1L);
                    ut.getTerminal().setConsecutivoActualBP(1L);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaActualD);
                    calendar = this.utilFacade.proximoDiaHabil(calendar);
                    ut.getTerminal().setFechaConsecutivoSet(calendar.getTime());
                    this.termFacade.merge(ut.getTerminal());
                }
            }

            if (ut != null) {
                List<Permiso> lPermisos = permisosFacade.getPermisosSeccion(ut.getUsuario().getIdUsuario(), 1L, "OPERACIONES");
                List<Terminal> lTerminal = getUsuarioLogeadosTerminal(usuarioFacade.get(u));
                List<Long> lLongPermisos = new ArrayList<Long>();
                for (Permiso o : lPermisos) {
                    lLongPermisos.add(o.getIdPermiso());
                }
                List<FranjaHorariaDet> lFranjaHorariaDet = franjaHorariaDetFacade.getDetalleFranjaHoraria(ut);
                Boolean entraEnFranjaHoraria = controlFranjaHoraria(lFranjaHorariaDet);
                if (lLongPermisos.size() > 0 && lLongPermisos.contains(23L) && entraEnFranjaHoraria) {
                    //Se permite que entre, sin más preambulos, porque tiene permiso de múltiples logueos.
                    if (ut.getLogueado().equalsIgnoreCase("N")) {
                        //Control para no sobreescribir el estado con el mismo valor mas de una vez. En este caso, si ya está marcado como logueado, no volver a marcar con el mismo estado
                        ut.setLogueado("S");
                        this.merge(ut);
                    }

                    resultado = new ResultadoAutenticarCobrosWeb("OK", ut, null);

                } else {

                    if (lTerminal == null && entraEnFranjaHoraria) {
                        //Sólo tiene permiso para un logueo simultaneo. Es el primer logueo de la persona
                        ut.setLogueado("S");
                        this.merge(ut);
                        resultado = new ResultadoAutenticarCobrosWeb("OK", ut, null);
                    } else if (lTerminal != null && lTerminal.size() == 1 && ut.getTerminal().getIdTerminal().intValue() == lTerminal.get(0).getIdTerminal().intValue() && entraEnFranjaHoraria) {
                        //Estar aquí significa que el usuarario ya está logueado, en esta terminal y tiene permiso para un solo loguedo.
                        resultado = new ResultadoAutenticarCobrosWeb("OK", ut, null);
                    } else {
                        //El usuario trata de loguearse en mas de una terminal al mismo tiempo.
                        logger.info("El usuario trata de loguearse en mas de una terminal al mismo tiempo.");
                        resultado = new ResultadoAutenticarCobrosWeb("FALSE", ut, lTerminal);

                    }
                }
                if (!entraEnFranjaHoraria) {
                    logger.info("Usted no puede loguearse en este horario.");
                    resultado.setMotivo("Usted no puede loguearse en este horario. Consulte con el administrador");
                } else {
                    resultado.setMotivo(null);
                }

            } else {
                logger.info("No existe usuario terminal");
                resultado = new ResultadoAutenticarCobrosWeb("FALSE", null, null);

            }
            if (resultado.getResultado().equals("OK")) {
                logger.info("OUT: OK");
            }
            return resultado;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            context.setRollbackOnly();
            resultado = new ResultadoAutenticarCobrosWeb("FALSE", null, null);
            return resultado;
        }
    }

    //Trae todas las terminales donde esta logeado un usuario.
    @Override
    public List<Terminal> getUsuarioLogeadosTerminal(Usuario usuario) {
        List<Terminal> lTerminales = new ArrayList<Terminal>();
        UsuarioTerminal ut = new UsuarioTerminal();
        ut.setLogueado("S");
        ut.setUsuario(usuario);
        List<UsuarioTerminal> lUsuarioTerminal = this.list(ut);
        for (UsuarioTerminal o : lUsuarioTerminal) {
            lTerminales.add(o.getTerminal());
        }
        return (lTerminales.isEmpty()) ? null : lTerminales;
    }

    private ResultadoControlFranjaHoraria controlFranjaHorariaCore(List<FranjaHorariaDet> lFranjaHorariaDetalle) throws ParseException {
        ResultadoControlFranjaHoraria resultado = new ResultadoControlFranjaHoraria();
        TimeZone timeZone = TimeZone.getTimeZone("America/Asuncion");
        Boolean franjaHorariaPassed = false;
        Long timeToEnd = 0L;
        //verificar si se encuentra en su franja horaria
        try {
            Date ahoraMismo = new Date();
            SimpleDateFormat fechaFormat = new SimpleDateFormat("ddMMyyyy HHmm");
            SimpleDateFormat horaMinutoFormat = new SimpleDateFormat("HHmm");
            SimpleDateFormat diaFormat = new SimpleDateFormat("dd");
            SimpleDateFormat mesFormat = new SimpleDateFormat("MM");
            SimpleDateFormat anhoFormat = new SimpleDateFormat("yyyy");
            Integer dia = Integer.parseInt(diaFormat.format(ahoraMismo));
            Integer mes = Integer.parseInt(mesFormat.format(ahoraMismo)) - 1;
            Integer anho = Integer.parseInt(anhoFormat.format(ahoraMismo));

            //Calendar calendar = Calendar.getInstance();
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeZone(timeZone);
            calendar.set(Calendar.YEAR, anho);
            calendar.set(Calendar.MONTH, mes);
            calendar.set(Calendar.DATE, dia);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            Calendar calendarHoraIni = Calendar.getInstance();
            Calendar calendarHoraFin = Calendar.getInstance();
            Calendar calendarAhoraMismo = Calendar.getInstance();
            calendarAhoraMismo.setTime(ahoraMismo);
            calendarAhoraMismo.setTime(fechaFormat.parse("24062010 " + horaMinutoFormat.format(ahoraMismo)));

            for (FranjaHorariaDet o : lFranjaHorariaDetalle) {

                calendarHoraIni.setTime(fechaFormat.parse("24062010 " + horaMinutoFormat.format(o.getHoraIni())));
                calendarHoraFin.setTime(fechaFormat.parse("24062010 " + horaMinutoFormat.format(o.getHoraFin())));
                if ((o.getDia().intValue() == dayOfWeek) && ((calendarHoraIni.getTime().getTime() <= calendarAhoraMismo.getTime().getTime()) && (calendarHoraFin.getTime().getTime() >= calendarAhoraMismo.getTime().getTime()))) {

                    franjaHorariaPassed = true;
                    timeToEnd = (calendarHoraFin.getTime().getTime() - calendarAhoraMismo.getTime().getTime()) / 1000;
                    resultado.setTimeStarted(ahoraMismo.getTime() / 1000);
                    break;
                }

            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            franjaHorariaPassed = false;
        }
        resultado.setEntra(franjaHorariaPassed);
        resultado.setTimeToEnd(timeToEnd);

        return resultado;
    }

    @Override
    public boolean controlFranjaHoraria(List<FranjaHorariaDet> lFranjaHorariaDetalle) throws ParseException {
        ResultadoControlFranjaHoraria resultado = controlFranjaHorariaCore(lFranjaHorariaDetalle);
        return resultado.getEntra();
    }

    @Override
    public ResultadoControlFranjaHoraria controlFranjaHorariaTimeToEnd(List<FranjaHorariaDet> lFranjaHorariaDetalle) throws ParseException {
        ResultadoControlFranjaHoraria resultado = controlFranjaHorariaCore(lFranjaHorariaDetalle);
        return resultado;
    }
}
