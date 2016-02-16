/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.ConfigClearingServicio;
import py.com.konecta.redcobros.entities.ConfigComisionalDetalle;
import py.com.konecta.redcobros.entities.ConfiguracionComisional;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.RolComisionista;
import py.com.konecta.redcobros.entities.Sucursal;

/**
 *
 * @author konecta
 */
@Stateless
public class ConfiguracionComisionalFacadeImpl extends GenericDaoImpl<ConfiguracionComisional, Long> implements ConfiguracionComisionalFacade {

    @EJB
    private ConfigComisionalDetalleFacade ccdFacade;
    @EJB
    private RolComisionistaFacade rolComisionistaFacade;
    @EJB
    private RedFacade redFacade;
    @EJB
    private RecaudadorFacade recaudadorFacade;
    @EJB
    private SucursalFacade sucursalFacade;
    @EJB
    private EntidadPoliticaFacade entidadPoliticaFacade;
    @EJB
    private EntidadFinancieraFacade entidadFinancieraFacade;
    @EJB
    private ConfigClearingServicioFacade configClearingServicioFacade;

    public Long obtenerIdConfiguracionComisional(
            Long idConfigClearingServicio, Long idRecaudador,
            Long idSucursal) throws Exception {
        //se busca segun red-servicio-tipoClearing, servicio, recaudador, sucursal
        Long idConfiguracionComisional = this.obtenerIdConfiguracionComisionalSegunParametros(idConfigClearingServicio, idRecaudador, idSucursal);
        return idConfiguracionComisional;
    }

    private Long obtenerIdConfiguracionComisionalSegunParametros(Long idConfigClearingServicio,
            Long idRecaudador, Long idSucursal) throws Exception {
        Criteria c = this.obtenerCriteriaSegunParametros(idConfigClearingServicio, idRecaudador, idSucursal, true);
        c.setProjection(Projections.projectionList().add(Projections.property("idConfiguracionComisional")));
        List<Long> lista = (List<Long>) c.list();
        Long idConfiguracionComisional = null;
        if (lista.size() > 1) {
            throw new Exception("Existe mas de una configuracion para los parametros dados: "
                    + "idConfigClearingServicio=" + idConfigClearingServicio + ";"
                    + "idRecaudador=" + idRecaudador + ";"
                    + "idSucursal=" + idSucursal + ";");
        } else if (lista.size() == 1) {
            idConfiguracionComisional = lista.get(0);
        }
        return idConfiguracionComisional;
    }

    private Criteria obtenerCriteriaSegunParametros(Long idConfigClearingServicio,
            Long idRecaudador, Long idSucursal,
            boolean buscarActual) {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(ConfiguracionComisional.class);
        c.createCriteria("configClearingServicio").add(Restrictions.eq("idConfigClearingServicio", idConfigClearingServicio));
        if (idRecaudador != null) {
            c.add(Restrictions.eq("recaudador", new Recaudador(idRecaudador)));
        } else {
            c.add(Restrictions.isNull("recaudador"));
        }
        if (idSucursal != null) {
            c.add(Restrictions.eq("sucursal", new Sucursal(idSucursal)));
        } else {
            c.add(Restrictions.isNull("sucursal"));
        }
        if (buscarActual) {
            c.add(Restrictions.eq("habilitado", "S"));
        }
        /*if (montoTotal != null) {
        //desde<=parametro
        c.add(Restrictions.le("desde", montoTotal));
        //hasta>parametro
        c.add(Restrictions.gt("hasta", montoTotal));
        } else {
        if (desde != null && hasta != null) {
        //modo encontrar configuracion se ingreso
        //los sumatoriaValor deben ser igual a los ingresados si o si
        c.add(Restrictions.eq("desde", desde));
        c.add(Restrictions.eq("hasta", hasta));
        } else if (buscarConfiguracionComisional) {
        //modo encontrar configuracion se ingreso
        //los sumatoriaValor deben ser null si o si
        c.add(Restrictions.isNull("desde"));
        c.add(Restrictions.isNull("hasta"));
        }
        //sino no se aplica restriccion sobre rangos
        //se obtenndran todos los rangos
        }*/
        return c;
    }

    public void agregarModificarConfiguracion(
            Long idConfigClearingServicio,
            Long idRed,
            Long idServicio,
            Long idTipoClearing,
            Double desde,
            Double hasta,
            Long idRecaudador,
            Long idSucursal,
            Long[] idRolComisionista,
            Long[] idBeneficiario,
            Double[] valor,
            String descripcion) throws Exception {
        ConfigClearingServicio ccs = this.configClearingServicioFacade.obtenerConfigClearingServicio(idRed, idServicio, idTipoClearing, desde, hasta);
        if (!ccs.getIdConfigClearingServicio().equals(idConfigClearingServicio)) {
            throw new Exception("Los datos del rango no coincide con el identificador enviado");
        }
        String datosValidos = this.validarDatosConfiguracion(ccs, idRecaudador, idSucursal, idRolComisionista, idBeneficiario, valor);
        if (datosValidos != null) {
            throw new Exception(datosValidos);
        }
        Long idConfiguracionComisional = this.obtenerIdConfiguracionComisional(ccs.getIdConfigClearingServicio(), idRecaudador, idSucursal);
        ConfiguracionComisional cc = new ConfiguracionComisional();
        cc.setConfigComisionalDetalleCollection(new ArrayList<ConfigComisionalDetalle>());
        cc.setConfigClearingServicio(ccs);
        cc.setDescripcion(descripcion);
        cc.setHabilitado("S");
        ConfigComisionalDetalle ccd;
        if (idConfiguracionComisional == null) {
            //es agregar uno nuevo
            if (idRecaudador != null) {
                cc.setRecaudador(new Recaudador(idRecaudador));
            }
            if (idSucursal != null) {
                cc.setSucursal(new Sucursal(idSucursal));
            }
        } else {
            //es desabilitar el existente y crear nuevo
            ConfiguracionComisional ccAntiguo = this.get(idConfiguracionComisional);
            ccAntiguo.setHabilitado("N");
            this.update(ccAntiguo);
            cc.setRecaudador(ccAntiguo.getRecaudador());
            cc.setSucursal(ccAntiguo.getSucursal());
        }
        for (int i = 0; i < idRolComisionista.length; i++) {
            ccd = new ConfigComisionalDetalle();
            ccd.setConfiguracionComisional(cc);
            ccd.setRolComisionista(new RolComisionista(idRolComisionista[i]));
            ccd.setIdBeneficiario(idBeneficiario[i]);
            ccd.setValor(valor[i]);
            if (idRolComisionista[i].longValue() == RolComisionista.RECAUDADOR) {
                //recaudador
                if (idBeneficiario[i] != null) {
                    ccd.setDescripcionBeneficiario(this.recaudadorFacade.get(idBeneficiario[i]).getDescripcion());
                }
            } else if (idRolComisionista[i].longValue() == RolComisionista.SUCURSAL) {
                //sucursal
                if (idBeneficiario[i] != null) {
                    ccd.setDescripcionBeneficiario(this.sucursalFacade.get(idBeneficiario[i]).getDescripcion());
                }
            } else if (idRolComisionista[i].longValue() == RolComisionista.BANCO_CLEARING) {
                //banco clearing
                ccd.setDescripcionBeneficiario(this.entidadFinancieraFacade.get(idBeneficiario[i]).getDescripcion());
            } else if (idRolComisionista[i].longValue() == RolComisionista.ENTIDAD_CABECERA_RED) {
                //entidad cabecera - red
                ccd.setDescripcionBeneficiario(this.redFacade.get(idBeneficiario[i]).getDescripcion());
            } else if (idRolComisionista[i].longValue() == RolComisionista.PROCESADOR_DOCUMENTA) {
                //procesador documenta
            } else if (idRolComisionista[i].longValue() == RolComisionista.ENTIDAD_POLITICA) {
                //entidad politica
                if (idBeneficiario[i] != null) {
                    ccd.setDescripcionBeneficiario(this.entidadPoliticaFacade.get(idBeneficiario[i]).getEntidad().getNombre());
                }
            }
            cc.getConfigComisionalDetalleCollection().add(ccd);
        }
        this.save(cc);
    }

    private String validarDatosConfiguracion(
            ConfigClearingServicio ccs,
            Long idRecaudador,
            Long idSucursal,
            Long[] idRolComisionista,
            Long[] idBeneficiario,
            Double[] valor) {
        Double sumatoriaValor = 0.0;
        String mensajeError = "";
        if (idRecaudador == null && idSucursal != null) {
            mensajeError += "Si recaudador es nulo, sucursal tambien debe serlo; ";
        }
        if (idRolComisionista.length != idBeneficiario.length
                || idRolComisionista.length != valor.length) {
            mensajeError += "Se debe recibir la misma cantidad de roles comisionistas,"
                    + " conceptos de comision y beneficiarios; ";
        } else {
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
            formatter.setMaximumFractionDigits(4);
            List<Long> listaIdBeneficiario = new ArrayList<Long>();
            for (int i = 0; i < idRolComisionista.length; i++) {
                if (idRolComisionista[i] == null) {
                    mensajeError += "El rol del comisionista no puede ser nulo; ";
                }
                sumatoriaValor += valor[i];
                if (idRolComisionista[i].longValue() == RolComisionista.RECAUDADOR) {
                    //recaudador
                } else if (idRolComisionista[i].longValue() == RolComisionista.SUCURSAL) {
                    //sucursal
                } else if (idRolComisionista[i].longValue() == RolComisionista.BANCO_CLEARING) {
                    //banco clearing
                    if (idBeneficiario[i] == null) {
                        mensajeError += "Para el rol Banco clearing se requiere el idEntidadFinanciera "
                                + "de la entidad financiera beneficiaria; ";
                    }
                } else if (idRolComisionista[i].longValue() == RolComisionista.ENTIDAD_CABECERA_RED) {
                    //entidad cabecera - red
                    if (idBeneficiario[i] == null) {
                        mensajeError += "Para el rol Entidad cabecera se requiere el idRed "
                                + "de la red; ";
                    }
                } else if (idRolComisionista[i].longValue() == RolComisionista.PROCESADOR_DOCUMENTA) {
                    //procesador documenta
                } else if (idRolComisionista[i].longValue() == RolComisionista.ENTIDAD_POLITICA) {
                    //entidad politica
                    if (idBeneficiario[i] == null) {
                        if (valor[i].doubleValue() > 0.0) {
                            mensajeError += "Para el rol Entidad politica se requiere el idEntidadPolitica "
                                    + "de la entidad beneficiaria ya que el monto/porcentaje asignado es mayor a cero; ";
                        }
                    } else {
                        if (listaIdBeneficiario.contains(idBeneficiario[i])) {
                            mensajeError += "Para el rol Entidad politica existe "
                                    + "beneficiarios similares; ";
                        } else {
                            listaIdBeneficiario.add(idBeneficiario[i]);
                        }
                    }
                } else {
                    //no deberia pasar pero si alguien hace insert por debajo
                    mensajeError += "Rol comisionista no soportado: "
                            + idRolComisionista[i] + "; ";
                }
            }
            sumatoriaValor = Double.valueOf(formatter.format(sumatoriaValor).replaceAll(",", ""));
            if (!sumatoriaValor.equals(ccs.getValor())) {
                mensajeError += "La sumatoria de valores a repartir " + sumatoriaValor + " debe ser igual "
                        + "al total repartir " + ccs.getValor()
                        + " para la configuracion de clearing: " + ccs;

            }
            /*if (montosFijos > 0.0) {
            if (servicio.getValor() < montosFijos) {
            mensajeError += "El monto a repartir " + montosFijos + " supera " +
            "al monto comision del servicio " + servicio.getValor() + "; ";
            }
            }
            if (porcentajes == 0.0 && servicio.getValor() != montosFijos) {
            mensajeError += "No hay configuraciones de tipo de pago de comision de porcentajes, " +
            "por tanto la sumatoria de sumatoriaValor a repartir " + montosFijos +
            " deberia ser igual al monto comision del servicio " + servicio.getValor() + "; ";
            }
            if (porcentajes != 0.0) {
            if (porcentajes != 100.0) {
            mensajeError += "La sumatoria de porcentajes debe ser 100, actualmente es " + porcentajes + "; ";
            }
            if (servicio.getValor() <= montosFijos) {
            mensajeError += "El monto a repartir " + montosFijos + " debe ser menor " +
            "al monto comision del servicio " + servicio.getValor() + "; ";
            }
            }
            if (porcentajes == 0.0 && montosFijos == 0.0) {
            mensajeError += "Todos los valores asignados a los detalles son cero; ";
            }*/
        }
        return (mensajeError.trim().equals("")) ? null : mensajeError;
    }

    public List<ConfigComisionalDetalle> obtenerDetalle(Long idConfigClearingServicio,
            Long idRecaudador, Long idSucursal) throws Exception {
        ConfigClearingServicio ccs = new ConfigClearingServicio();
        ccs.setIdConfigClearingServicio(idConfigClearingServicio);
        ConfigClearingServicio configClearingServicio = this.configClearingServicioFacade.get(ccs);
        Long idConfiguracionComisional = null;
        idConfiguracionComisional = this.obtenerIdConfiguracionComisionalSegunParametros(configClearingServicio.getIdConfigClearingServicio(), idRecaudador, idSucursal);
        List<ConfigComisionalDetalle> lista;
        ConfigComisionalDetalle ccd = new ConfigComisionalDetalle();
        if (idConfiguracionComisional != null) {
            ccd = new ConfigComisionalDetalle();
            ccd.setConfiguracionComisional(new ConfiguracionComisional());
            ccd.getConfiguracionComisional().setIdConfiguracionComisional(idConfiguracionComisional);
            lista = this.ccdFacade.list(ccd);
        } else {
            lista = new ArrayList<ConfigComisionalDetalle>();
            int indice = 0;
            ccd = new ConfigComisionalDetalle();
            ccd.setValor(0.0);
            ccd.setRolComisionista(this.rolComisionistaFacade.get(RolComisionista.RECAUDADOR));
            ccd.setIdBeneficiario(idRecaudador);
            ccd.setDescripcionBeneficiario((idRecaudador == null) ? null : this.recaudadorFacade.get(idRecaudador).getDescripcion());
            lista.add(indice++, ccd);
            if (idSucursal != null) {
                ccd = new ConfigComisionalDetalle();
                ccd.setValor(0.0);
                ccd.setRolComisionista(this.rolComisionistaFacade.get(RolComisionista.SUCURSAL));
                ccd.setIdBeneficiario(idSucursal);
                ccd.setDescripcionBeneficiario((idSucursal == null) ? null : this.sucursalFacade.get(idSucursal).getDescripcion());
                lista.add(indice++, ccd);
            }
            ccd = new ConfigComisionalDetalle();
            ccd.setValor(0.0);
            ccd.setRolComisionista(this.rolComisionistaFacade.get(RolComisionista.BANCO_CLEARING));
            ccd.setIdBeneficiario(configClearingServicio.getRed().getBancoClearing().getIdEntidadFinanciera());
            ccd.setDescripcionBeneficiario(configClearingServicio.getRed().getBancoClearing().getDescripcion());
            lista.add(indice++, ccd);
            ccd = new ConfigComisionalDetalle();
            ccd.setValor(0.0);
            ccd.setRolComisionista(this.rolComisionistaFacade.get(RolComisionista.ENTIDAD_CABECERA_RED));
            ccd.setIdBeneficiario(configClearingServicio.getRed().getIdRed());
            ccd.setDescripcionBeneficiario(configClearingServicio.getRed().getDescripcion());
            lista.add(indice++, ccd);
            ccd = new ConfigComisionalDetalle();
            ccd.setValor(0.0);
            ccd.setRolComisionista(this.rolComisionistaFacade.get(RolComisionista.PROCESADOR_DOCUMENTA));
            lista.add(indice++, ccd);
            ccd = new ConfigComisionalDetalle();
            ccd.setValor(0.0);
            ccd.setRolComisionista(this.rolComisionistaFacade.get(RolComisionista.ENTIDAD_POLITICA));
            lista.add(indice++, ccd);
        }
        return lista;
    }
}
