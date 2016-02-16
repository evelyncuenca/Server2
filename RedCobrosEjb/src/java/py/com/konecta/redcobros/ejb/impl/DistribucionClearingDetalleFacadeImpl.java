/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJB;
import java.math.BigDecimal;
import java.math.RoundingMode;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.entities.DistribucionClearingDetalle;
import py.com.konecta.redcobros.entities.ConfigComisionalDetalle;
import py.com.konecta.redcobros.entities.DistribucionClearing;
import py.com.konecta.redcobros.entities.EntidadFinanciera;
import py.com.konecta.redcobros.entities.EntidadPolitica;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.RolComisionista;
import py.com.konecta.redcobros.entities.Sucursal;

/**
 *
 * @author konecta
 */
@Stateless
public class DistribucionClearingDetalleFacadeImpl extends GenericDaoImpl<DistribucionClearingDetalle, Long> implements DistribucionClearingDetalleFacade {

    @EJB
    private RecaudadorFacade recaudadorFacade;
    @EJB
    private RedFacade redFacade;
    @EJB
    private SucursalFacade sucursalFacade;
    @EJB
    private EntidadPoliticaFacade entidadPoliticaFacade;
    @EJB
    private EntidadFinancieraFacade entidadFinancieraFacade;
    @EJB
    private ConfigComisionalDetalleFacade configComDetFacade;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    

    public void generarDistribucionClearingDetalle(DistribucionClearing distribucionClearing,
            Double cantidad,
            Long idConfiguracionComisional, Long idRecaudador, Long idSucursal, Double divisor) throws Exception {
        ConfigComisionalDetalle ccd;
        Double valorRepartir = distribucionClearing.getClearing().getConfigClearingServicio().getValor();
        DistribucionClearingDetalle dcd;

        Double montoRepartido = 0.0, monto = null, montoIva = null, iva = null,
               montoRepartidoIva = 0.0,
               montoTotalRepartir = new Double (distribucionClearing.getMontoDistribucion()),            
               montoTotalRepartirIva = new Double (distribucionClearing.getMontoDistribucion() / 1.1);               
               montoTotalRepartir = new BigDecimal (montoTotalRepartir).setScale(4,RoundingMode.HALF_UP).doubleValue();
               montoTotalRepartirIva = new BigDecimal (montoTotalRepartirIva).setScale(4,RoundingMode.HALF_UP).doubleValue();
               
        Collection<DistribucionClearingDetalle> listaDCD = new ArrayList<DistribucionClearingDetalle>();
        List<ConfigComisionalDetalle> listaConfigComDet = this.configComDetFacade.list(idConfiguracionComisional, valorRepartir);
        for (int i = 0; i < listaConfigComDet.size(); i++) {
            ccd = listaConfigComDet.get(i);
            if (i == (listaConfigComDet.size() - 1)) {
                //es el ultimo a procesar , por tanto lleva todo lo que sobra
                montoIva = montoTotalRepartirIva - montoRepartidoIva;
                montoIva = new BigDecimal (montoIva).setScale(4,RoundingMode.HALF_UP).doubleValue();
                monto = montoTotalRepartir - montoRepartido;
                monto = new BigDecimal (monto).setScale(4,RoundingMode.HALF_UP).doubleValue();                
                iva = monto / 11;
                iva = new BigDecimal (iva).setScale(4,RoundingMode.HALF_DOWN).doubleValue();
            } else {
                montoIva = new Double (((ccd.getValor() / divisor) * cantidad) / 1.1);
                montoIva = new BigDecimal (montoIva).setScale(4,RoundingMode.HALF_UP).doubleValue();
                monto = new Double (((ccd.getValor() / divisor) * cantidad));
                monto = new BigDecimal (monto).setScale(4,RoundingMode.HALF_UP).doubleValue();
                iva = ((ccd.getValor() / divisor) * cantidad) / 11;
                iva = new BigDecimal (iva).setScale(4,RoundingMode.HALF_DOWN).doubleValue();
            }
            montoRepartidoIva += montoIva;
            montoRepartido += monto;
            montoRepartido = new BigDecimal (montoRepartido).setScale(4,RoundingMode.HALF_UP).doubleValue();
            
            if (montoRepartido > montoTotalRepartir) {
                throw new Exception("Monto repartido (" + montoRepartido + ") " +
                        "excede el monto total a repartir " + montoTotalRepartir +
                        ", cantidad : " + cantidad +
                        ", comision : " + valorRepartir);
            }
            dcd = this.crearDistribucionClearingDetalle(
                    distribucionClearing.getClearing().getConfigClearingServicio().getRed(),
                    idRecaudador,
                    idSucursal,
                    ccd,
                    montoIva,
                    iva,
                    distribucionClearing);
            listaDCD.add(dcd);
        }
        distribucionClearing.setDistribucionClearingDetalleCollection(listaDCD);
    }

    private DistribucionClearingDetalle crearDistribucionClearingDetalle(Red red, Long idRecaudador,
            Long idSucursal, ConfigComisionalDetalle ccd, double monto, double iva, DistribucionClearing dc) throws Exception {
        Long idBeneficiario = null;
        String descripcionBeneficiario = null;
        if (ccd.getRolComisionista().getIdRolComisionista().longValue() ==
                RolComisionista.RECAUDADOR) {

            //recaudador
            //podia venir o no en la config
            //porque pudo ser especifico o no
            //pero si no vino, tenemos luego
            if (ccd.getIdBeneficiario() != null) {
                if (ccd.getIdBeneficiario().equals(idRecaudador)) {
                    idBeneficiario = idRecaudador;
                } else {
                    //algo muy malo, el det trajo un idBeneficiario diferente al
                    //recaudador que se encuentra en la cabecera dela config
                    throw new Exception("El/La " + ccd.getRolComisionista() + " recibido como parametro " +
                            idRecaudador + " no coincide con el idBeneficiario del detalle " +
                            ccd.getIdBeneficiario() + ". idConfiguracionComisional: " +
                            ccd.getConfiguracionComisional().getIdConfiguracionComisional() +
                            ", idConfigComisionalDetalle: " + ccd.getIdConfigComisionalDetalle());
                }
            } else {
                idBeneficiario = idRecaudador;
            }
            descripcionBeneficiario = (String) this.recaudadorFacade.listAtributos(
                    new Recaudador(idBeneficiario),
                    new String[]{"descripcion"}).get(0).get("descripcion");
        } else if (ccd.getRolComisionista().getIdRolComisionista().longValue() == RolComisionista.SUCURSAL) {
            //sucursal
            //podia venir o no en la config
            //porque pudo ser especifico o no
            //pero si no vino, tenemos luego
            if (ccd.getIdBeneficiario() != null) {
                if (ccd.getIdBeneficiario().equals(idSucursal)) {
                    idBeneficiario = idSucursal;
                } else {
                    //algo muy malo, el det trajo un idBeneficiario diferente al
                    //recaudador que se encuentra en la cabecera dela config
                    throw new Exception("El/La " + ccd.getRolComisionista() + " recibido como parametro " +
                            idRecaudador + " no coincide con el idBeneficiario del detalle " +
                            ccd.getIdBeneficiario() + ". idConfiguracionComisional: " +
                            ccd.getConfiguracionComisional().getIdConfiguracionComisional() +
                            ", idConfigComisionalDetalle: " + ccd.getIdConfigComisionalDetalle());
                }
            } else {
                idBeneficiario = idSucursal;
            }
            descripcionBeneficiario = (String) this.sucursalFacade.listAtributos(
                    new Sucursal(idBeneficiario),
                    new String[]{"descripcion"}).get(0).get("descripcion");
        } else if (ccd.getRolComisionista().getIdRolComisionista().longValue() == RolComisionista.BANCO_CLEARING) {
            //banco clearing
            //ya tenia que haber venido en la config
            //porque al momento de configurar ya hay que ponerse
            //pero si no vino, hay que recuperar de red
            if (ccd.getIdBeneficiario() != null) {
                idBeneficiario = ccd.getIdBeneficiario();
            } else {
                throw new Exception("El/La " + ccd.getRolComisionista() + " recibido como parametro " +
                        "no tiene seteado su idBeneficiario; idConfiguracionComisional: " +
                        ccd.getConfiguracionComisional().getIdConfiguracionComisional() +
                        ", idConfigComisionalDetalle: " + ccd.getIdConfigComisionalDetalle());
            }
            descripcionBeneficiario = (String) this.entidadFinancieraFacade.listAtributos(
                    new EntidadFinanciera(idBeneficiario),
                    new String[]{"descripcion"}).get(0).get("descripcion");
        } else if (ccd.getRolComisionista().getIdRolComisionista().longValue() == RolComisionista.ENTIDAD_CABECERA_RED) {
            //entidad cabecera - red
            //ya tenia que haber venido en la config
            //porque al momento de configurar ya hay que ponerse
            //pero si no vino, tenemos luego
            if (ccd.getIdBeneficiario() != null) {
                if (ccd.getIdBeneficiario().equals(red.getIdRed())) {
                    idBeneficiario = red.getIdRed();
                } else {
                    //algo muy malo, el det trajo un idBeneficiario diferente al
                    //recaudador que se encuentra en la cabecera dela config
                    throw new Exception("El/La " + ccd.getRolComisionista() + " recibido como parametro " +
                            idRecaudador + " no coincide con el idBeneficiario del detalle " +
                            ccd.getIdBeneficiario() + ". idConfiguracionComisional: " +
                            ccd.getConfiguracionComisional().getIdConfiguracionComisional() +
                            ", idConfigComisionalDetalle: " + ccd.getIdConfigComisionalDetalle());
                }
            } else {
                idBeneficiario = red.getIdRed();
            }
            descripcionBeneficiario = (String) this.redFacade.listAtributos(
                    new Red(idBeneficiario),
                    new String[]{"descripcion"}).get(0).get("descripcion");

        } else if (ccd.getRolComisionista().getIdRolComisionista().longValue() == RolComisionista.PROCESADOR_DOCUMENTA) {
            //procesador documenta
            //ya tenia que haber venido en la config
            //porque al momento de configurar ya hay que ponerse
            //pero si no vino, hay que poner
            //en duro, porq no existe tabla de procesador ya que
            //hay un solo procesador: documenta
            //idBeneficiario = ID_ENTIDAD_DOCUMENTA;
            idBeneficiario=Long.parseLong(this.parametroSistemaFacade.get("idEntidadProcesadorDocumenta").getValor());
            descripcionBeneficiario = "Documenta S.A.";
        } else if (ccd.getRolComisionista().getIdRolComisionista().longValue() == RolComisionista.ENTIDAD_POLITICA) {
            //entidad politica
            //ya tenia que haber venido en la config
            //porque al momento de configurar ya hay que ponerse
            //pero si no vino, hay que recuperar de red
            if (ccd.getIdBeneficiario() != null) {
                idBeneficiario = ccd.getIdBeneficiario();
            } else {
                throw new Exception("El/La " + ccd.getRolComisionista() + " recibido como parametro " +
                        "no tiene seteado su idBeneficiario; idConfiguracionComisional: " +
                        ccd.getConfiguracionComisional().getIdConfiguracionComisional() +
                        ", idConfigComisionalDetalle: " + ccd.getIdConfigComisionalDetalle());
            }
            descripcionBeneficiario = (String) this.entidadPoliticaFacade.listAtributos(
                    new EntidadPolitica(idBeneficiario),
                    new String[]{"entidad.nombre"}).get(0).get("entidad.nombre");

        } else {
            //no deberia pasar pero si alguien hace insert por debajo
            throw new Exception("Rol comisionista no soportado: " +
                    ccd.getRolComisionista().getIdRolComisionista());
        }
        DistribucionClearingDetalle distribucionClearingDetalle = new DistribucionClearingDetalle();
        distribucionClearingDetalle.setConfigComisionalDetalle(ccd);
        distribucionClearingDetalle.setDistribucionClearing(dc);
        distribucionClearingDetalle.setRolComisionista(ccd.getRolComisionista());
        distribucionClearingDetalle.setIdBeneficiario(idBeneficiario);
        distribucionClearingDetalle.setDescripcionBeneficiario(descripcionBeneficiario);
        distribucionClearingDetalle.setMontoComision(monto);
        distribucionClearingDetalle.setIvaComision(iva);
        return distribucionClearingDetalle;
    }

    public List<Map<String, Object>> listaReporte(Long idDistribucionClearing) throws Exception {
        List<Map<String, Object>> lista = this.obtenerCriteriaReporte(idDistribucionClearing).list();
        NumberFormat formatter =  NumberFormat.getNumberInstance(Locale.US);
        for (Map<String, Object> mapa : lista) {
            mapa.put("valorComisionFormateado",
            formatter.format(mapa.get("valorComision")));
        }
        return lista;
    }

    private Criteria obtenerCriteriaReporte(Long idDistribucionClearing) throws Exception {
        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
        Criteria c = hem.getSession().createCriteria(DistribucionClearingDetalle.class);
        c.add(Restrictions.eq("distribucionClearing", new DistribucionClearing(idDistribucionClearing)));
        c.createCriteria("configComisionalDetalle", "ccd");
        c.createCriteria("rolComisionista", "rc");
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("rc.descripcion"), "rolComisionista");
        pl.add(Projections.property("idBeneficiario"), "idBeneficiario");
        pl.add(Projections.property("descripcionBeneficiario"), "descripcionBeneficiario");
        pl.add(Projections.property("montoComision"), "montoComision");
        pl.add(Projections.property("ivaComision"), "ivaComision");
        pl.add(Projections.property("ccd.valor"), "valorComision");
        c.setProjection(pl);
        c.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        c.addOrder(Order.asc("rc.descripcion"));
        return c;
    }
}
