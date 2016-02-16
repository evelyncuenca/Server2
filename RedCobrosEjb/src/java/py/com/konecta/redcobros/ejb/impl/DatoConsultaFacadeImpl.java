/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import py.com.konecta.redcobros.ejb.AliasServicioFacturadorFacade;
import py.com.konecta.redcobros.ejb.DatoConsultaFacade;
import py.com.konecta.redcobros.entities.AliasServicioFacturador;
import py.com.konecta.redcobros.entities.DatoConsulta;
import py.com.konecta.redcobros.entities.DatoConsultaPK;
import py.com.konecta.redcobros.entities.Facturador;
import py.com.konecta.redcobros.entities.ServicioRc;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DatoConsultaFacadeImpl extends GenericDaoImpl<DatoConsulta, DatoConsultaPK> implements DatoConsultaFacade {

    @EJB
    private AliasServicioFacturadorFacade aliasServicioFacturadorFacade;
    @Resource
    private SessionContext context;
    // @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

    @Override
    public Integer insertarDatoConsulta(String linea, String separadorCampos) throws Exception {

        try {
            DateFormat df = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");

            String[] token = LectorConfiguracionSet.tokensDeLaLinea(linea, separadorCampos);

            DatoConsulta control = new DatoConsulta();
            DatoConsultaPK controlPk = new DatoConsultaPK();
            controlPk.setIdFacturador(Long.valueOf(token[POS_FACTURADOR]));
            controlPk.setReferenciaPago(token[POS_REFERENCIA_PAGO]);

            AliasServicioFacturador alias = new AliasServicioFacturador();
            alias.setAliasServicio(new Integer(token[POS_SERVICIO]));
            alias.setFacturador(new Facturador(Long.valueOf(token[POS_FACTURADOR])));
            ServicioRc servicioDocu = aliasServicioFacturadorFacade.get(alias).getServicioRc();

            controlPk.setIdServicio(servicioDocu.getIdServicio());
            control.setDatoConsultaPK(controlPk);

            if (this.get(control) == null) {

                DatoConsulta fac = new DatoConsulta();
                AliasServicioFacturador aliasServFac = new AliasServicioFacturador();

                aliasServFac.setServicioRc(servicioDocu);
                aliasServFac.setFacturador(new Facturador(Long.valueOf(token[POS_FACTURADOR])));

                fac.setAliasServicioFacturador(aliasServicioFacturadorFacade.get(aliasServFac));
                fac.setAlias(new Integer(token[POS_SERVICIO]));
                fac.setDatoConsultaPK(controlPk);

                if (token[POS_INT_MORATORIO] != null && !token[POS_INT_MORATORIO].isEmpty() && !token[POS_INT_MORATORIO].equals(" ")) {
                    fac.setInteresMoratorio(new BigDecimal(token[POS_INT_MORATORIO]));//null
                }
                if (token[POS_VENCIMIENTO] != null && !token[POS_VENCIMIENTO].isEmpty() && !token[POS_VENCIMIENTO].equals(" ")) {
                    fac.setVencimiento(spdf.parse(token[POS_VENCIMIENTO]));//null
                }
                if (token[POS_PAGAR_MAS_VENCIDA] != null && !token[POS_PAGAR_MAS_VENCIDA].isEmpty() && !token[POS_PAGAR_MAS_VENCIDA].equals(" ")) {
                    fac.setPagarMasVencido(token[POS_PAGAR_MAS_VENCIDA].charAt(0)=='1' ? 'S' : 'N');//null
                }
                if (token[POS_MENSAJE] != null && !token[POS_MENSAJE].isEmpty()) {
                    fac.setMensaje(token[POS_MENSAJE]);//null
                }
                if (token[POS_MONTO_VENCIDO] != null && !token[POS_MONTO_VENCIDO].isEmpty()) {
                    fac.setMontoVencido(new BigDecimal(token[POS_MONTO_VENCIDO]));//null
                }
                if (token[POS_DESCRIPCION_REF_PAGO] != null && !token[POS_DESCRIPCION_REF_PAGO].isEmpty()) {
                    fac.setDescripcion(token[POS_DESCRIPCION_REF_PAGO]);//null
                }
                fac.setAnulable(token[POS_ANULABLE].equals("1") ? 'S' : 'N');
                fac.setCobrarVencido(token[POS_COBRAR_VENCIDO].equals("1") ? 'S' : 'N');

                fac.setDatoConsultaPK(controlPk);
                fac.setMoneda(new Short(token[POS_MONEDA]));

                fac.setMonto(new BigDecimal(token[POS_MONTO]));

                fac.setPendiente('S');
                fac.setReferenciaBuqueda(token[POS_REFERENCIA_BUSQUEDA]);

                fac.setSoloEfectivo(token[POS_SOLO_EFECTIVO].equals("1") ? 'S' : 'N');
                fac.setTipoRecargo(new Short(token[POS_TIPO_RECARGO]));
                // fac.setFechaIngreso(new Date());
                this.save(fac);
            } else {
                return 1;
            }

        } catch (Exception e) {
            Logger.getLogger(DatoConsultaFacadeImpl.class.getName()).log(Level.SEVERE, null, e);
            return 2;
        }
        return 0;
    }
    private static int POS_FACTURADOR = 0;
    private static int POS_SERVICIO = 1;
    private static int POS_REFERENCIA_BUSQUEDA = 2;
    private static int POS_REFERENCIA_PAGO = 3;
    private static int POS_DESCRIPCION_REF_PAGO = 4;
    private static int POS_MONEDA = 5;
    private static int POS_MONTO = 6;
    private static int POS_MONTO_VENCIDO = 7;
    private static int POS_INT_MORATORIO = 8;
    private static int POS_VENCIMIENTO = 9;
    private static int POS_MENSAJE = 10;
    private static int POS_COBRAR_VENCIDO = 11;
    private static int POS_TIPO_RECARGO = 12;
    private static int POS_PAGAR_MAS_VENCIDA = 13;
    private static int POS_SOLO_EFECTIVO = 14;
    private static int POS_ANULABLE = 15;
}
