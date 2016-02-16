/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.hibernate.StatelessSession;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Servicio;
import py.com.konecta.redcobros.entities.TransaccionRc;

/**
 *
 * @author konecta
 */
@Stateless
public class ReportesFacadeImpl implements ReportesFacade {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    @PersistenceContext
    private EntityManager em;
    private static String COBRANZAS_DIR = "/py/com/konecta/redcobros/reportes/cobranza/";
    private static String IMAGES_DIR = "/py/com/konecta/redcobros/reportes/imagenes/";
    @EJB
    RedFacade redFacade;
    @EJB
    private ServicioFacade servFacade;
    @EJB
    private ClearingFacade clearingFacade;
    @EJB
    private NumeroOtFacade numeroOtFacade;
    @EJB
    private UtilFacade utilFacade;
    @EJB
    private TransaccionRcFacade trcFacade;
    @EJB
    private GestionFacade gesFacade;

    @Override
    public byte[] declaracionesDelDia(Long idRed, String fecha, String formato) {
        HashMap parameters = new HashMap();
        parameters.put("nombreRed", redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion"));
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("fecha", fecha);
        parameters.put("SUBREPORT_DIR", "");
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte("DetalleCobroDDJJPorDia.jasper", parameters);
        } else {
            return this.generarReporteXLS("DetalleCobroDDJJPorDia.jasper", parameters);
        }
    }

    private byte[] generarReporte(String nombreReporte, HashMap parametros) {
        byte[] reporte = null;
        StatelessSession statelessSession = ((HibernateEntityManager) em.getDelegate()).getSession().getSessionFactory().openStatelessSession();
        Connection con = statelessSession.connection();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(nombreReporte);
            JasperPrint jp = JasperFillManager.fillReport(is, parametros, con);
            if (jp != null && jp.getPages().size() > 0) {
                reporte = JasperExportManager.exportReportToPdf(jp);
            }
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(ReportesFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                statelessSession.close();
            } catch (Exception e2) {
            }
        }
        return reporte;
    }

    private byte[] generarReporte(String nombreReporte, List<Map<String, Object>> listaDS, HashMap parametros) {
        byte[] reporte = null;
        try {
            Locale locale = new Locale("en", "US");
            parametros.put(JRParameter.REPORT_LOCALE, locale);
            InputStream is = getClass().getClassLoader().getResourceAsStream(nombreReporte);
            JasperPrint jp = JasperFillManager.fillReport(is, parametros, new JRMapCollectionDataSource((Collection) listaDS));
            if (jp != null && jp.getPages().size() > 0) {
                reporte = JasperExportManager.exportReportToPdf(jp);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reporte;
    }

    private byte[] generarReporteXLS(String nombreReporte, HashMap parametros) {
        ByteArrayOutputStream reporte = new ByteArrayOutputStream();
        StatelessSession statelessSession = ((HibernateEntityManager) em.getDelegate()).getSession().getSessionFactory().openStatelessSession();
        Connection con = statelessSession.connection();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(nombreReporte);
            JasperPrint jp = JasperFillManager.fillReport(is, parametros, con);
            if (jp != null && jp.getPages().size() > 0) {
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, reporte);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
                exporterXLS.exportReport();
            }
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                statelessSession.close();
            } catch (Exception e2) {
            }
        }
        if (reporte.size() > 0) {
            return reporte.toByteArray();
        } else {
            return null;
        }
    }

    private byte[] generarReporteXLS(String nombreReporte, List<Map<String, Object>> listaDS, HashMap parametros) {
        ByteArrayOutputStream reporte = new ByteArrayOutputStream();
        try {
            Locale locale = new Locale("en", "US");
            parametros.put(JRParameter.REPORT_LOCALE, locale);
            InputStream is = getClass().getClassLoader().getResourceAsStream(nombreReporte);
            JasperPrint jp = JasperFillManager.fillReport(is, parametros, new JRMapCollectionDataSource((Collection) listaDS));
            if (jp != null && jp.getPages().size() > 0) {
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, reporte);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.exportReport();
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (reporte.size() > 0) {
            return reporte.toByteArray();
        } else {
            return null;
        }
    }

    @Override
    public byte[] reporteClearing(Long idRed, String codigoServicio, Date fechaDesde, Date fechaHasta, String formato) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<Map<String, Object>> listaDS = this.clearingFacade.listaReporte(fechaDesde, fechaHasta, idRed, codigoServicio);

        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        Servicio servicio = new Servicio();
        servicio.setCodigoTransaccional(codigoServicio);
        Map<String, Object> mapaServicio = this.servFacade.get(servicio,
                new String[]{"descripcion"});
        parameters.put("nombreServicio", mapaServicio.get("descripcion"));
        parameters.put("fecha", sdf.format(new Date()));
        parameters.put("rangoFecha", sdf.format(fechaDesde) + " - " + sdf.format(fechaHasta));
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteClearing.jasper", listaDS, parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteClearing.jasper", listaDS, parameters);
        }
    }

    @Override
    public byte[] resumenClearingComision(Long idRed, Long idRecaudador, Long idSucursal, String fechaDesde, String fechaHasta, String formato) throws Exception {

//        List<Map<String,Object>> listaDS = this.clearingFacade.listaReporte(fechaDesde, fechaHasta, idRed, codigoServicio);
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
//        Servicio servicio = new Servicio();
//        servicio.setCodigoTransaccional(codigoServicio);
//        Map<String, Object> mapaServicio = this.servFacade.get(servicio,
//                new String[]{"descripcion"});
//        parameters.put("nombreServicio", mapaServicio.get("descripcion"));
        parameters.put("fechaInicial", fechaDesde);
        parameters.put("fechaFinal", fechaHasta);
        parameters.put("idRecaudador", idRecaudador);
        parameters.put("idSucursal", idSucursal);
        parameters.put("idRed", idRed);
//        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "reporteComisionResumen.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "reporteComisionResumen.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteControlFormulariosRecepControlEnv(List<Map<String, Object>> listaDS, Long idRed, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);

        parameters.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parameters.put("cantidadRegistros", listaDS.size() + "");

        //parameters.put("rangoFecha", fechaParametro);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteControlFormularioRcpCntEnv.jasper", listaDS, parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteControlFormularioRcpCntEnv.jasper", listaDS, parameters);
        }
    }

    @Override
    public byte[] reporteRechazadosNoRechazados(List<Map<String, Object>> listaDS, Long idRed, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);

        parameters.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parameters.put("cantidadRegistros", listaDS.size() + "");

        //parameters.put("rangoFecha", fechaParametro);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteControlBolRechazadasNoRechazadas.jasper", listaDS, parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteControlBolRechazadasNoRechazadas.jasper", listaDS, parameters);
        }
    }

    @Override
    public byte[] reporteClearingFacturador(List<Map<String, Object>> listaDS, Long idRed, String fechaParametro, String formato) {
        HashMap parameters = new HashMap();
        Map<String, Object> mapaRed = redFacade.get(new Red(idRed), new String[]{"descripcion", "bancoClearing.descripcion"});
        parameters.put("nombreRed", mapaRed.get("descripcion"));
        parameters.put("nombreBancoClearing", mapaRed.get("bancoClearing.descripcion"));
        parameters.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parameters.put("rangoFecha", fechaParametro);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteClearingFacturacion.jasper", listaDS, parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteClearingFacturacion.jasper", listaDS, parameters);
        }
    }

    @Override
    public byte[] reporteTapaCaja(List<Map<String, Object>> listaDS, Long idRed, String fechaParametro, boolean rotulo, boolean detallado, String formato) {
        HashMap parameters = new HashMap();
        Map<String, Object> mapaRed = redFacade.get(new Red(idRed), new String[]{"descripcion", "codEra"});
        parameters.put("nombreRed", mapaRed.get("descripcion"));
        parameters.put("eraRed", mapaRed.get("codEra"));
        parameters.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parameters.put("rangoFecha", fechaParametro);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        String reporte = null;
        if (rotulo) {
            if (detallado) {
                reporte = "tapaCajaDetallado";
            } else {
                reporte = "tapaCajaRotulo";
            }
            parameters.put("totalCajas", listaDS.size());
        } else {
            reporte = "tapaCaja";
        }
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + reporte + ".jasper", listaDS, parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + reporte + ".jasper", listaDS, parameters);
        }
    }

    @Override
    public byte[] boletasPagoPorOrden(Long numeroOrden, Long idUsuario, Long idRed, String formato) {
        if (numeroOrden == null) {
            throw new RuntimeException("El parámetro Nro de Orden no puede ser nulo");
        }
//        if ( idUsuario == null) {
//            throw new RuntimeException("El parámetro Id de Usuario no puede ser nulo");
//        }

        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("nroOrden", new BigDecimal(numeroOrden));
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "BoletaPagoPorNroOrden.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "BoletaPagoPorNroOrden.jasper", parameters);
        }
    }

    @Override
    public byte[] DDJJPorOrden(Long numeroOrden, Long idUsuario, Long idRed, String formato) {
        if (numeroOrden == null) {
            throw new RuntimeException("El parámetro Nro de Orden no puede ser nulo");
        }
//        if ( idUsuario == null) {
//            throw new RuntimeException("El parámetro Id de Usuario no puede ser nulo");
//        }

        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("nroOrden", new BigDecimal(numeroOrden));
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "DDjjPorNroOrden.jasper", parameters);
        } else {//xls
            return this.generarReporteXLS(COBRANZAS_DIR + "DDjjPorNroOrden.jasper", parameters);
        }

    }

    @Override
    public byte[] cobranzaDetallado(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal,
            Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("estadoTransaccion", estadoTransaccion);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaDetallado.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaDetallado.jasper", parameters);
        }
    }

    @Override
    public byte[] cobranzaDetalladoCS(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal,
            Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato, String tipoPago, boolean tmu, Integer idMoneda) {
        try {
            if (tmu) {
                Integer idServicio = codigoServicio != null ? new Integer(codigoServicio) : null;
                List<TransaccionRc> lmap = trcFacade.getDataCobranzaDetallada(idUsuario, idTerminal, idServicio, idFacturador, fechaIni, fechaFin, tipoPago);

                if (lmap != null && !lmap.isEmpty()) {
                    NumberFormat nf = NumberFormat.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                    String reporteCobranza = null;
                    Integer cantE = 0;
                    Integer cantC = 0;
                    BigDecimal montoE = BigDecimal.ZERO;
                    BigDecimal montoC = BigDecimal.ZERO;
                    try {
                        reporteCobranza = this.getCabeceraCobranza(gesFacade.get(idGestion));
                        reporteCobranza += "Nro.Trx  Ref.  Monto  Forma Pago  Fecha\n";
                    } catch (Exception ex) {
                        Logger.getLogger(ReportesFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for (TransaccionRc it : lmap) {
                        if (it.getIdServicio().getIdServicio() != 84) {
                            reporteCobranza += it.getIdTransaccion().toString() + "  " + it.getReferenciaPago() + "  " + nf.format(it.getMonto()) + "  " + (it.getIdTipoPago().getIdTipoPago() == 1L ? "E" : "C") + "  " + formatter.format(it.getFechaIngreso()) + "\n";
                            if (it.getIdTipoPago().getIdTipoPago() == 1L) {
                                cantE++;
                                montoE = montoE.add(it.getMonto());
                            } else {
                                cantC++;
                                montoC = montoC.add(it.getMonto());
                            }
                        }
                    }
                    reporteCobranza += this.getFooterCobranza(nf.format(cantE), nf.format(cantC), nf.format(cantE + cantC), nf.format(montoE), nf.format(montoC), nf.format(montoE.add(montoC)), false);

                    reporteCobranza += "\n" + "      **** Retiros ****" + "\n\n";
                    reporteCobranza += "Nro.Trx  Ref.  Monto  Forma Pago  Fecha\n";
                    montoE = BigDecimal.ZERO;
                    cantE = 0;
                    for (TransaccionRc it : lmap) {
                        if (it.getIdServicio().getIdServicio() == 84) {
                            reporteCobranza += it.getIdTransaccion().toString() + "  " + it.getReferenciaPago() + "  " + nf.format(it.getMonto()) + "  " + (it.getIdTipoPago().getIdTipoPago() == 1L ? "E" : "C") + "  " + formatter.format(it.getFechaIngreso()) + "\n";
                            if (it.getIdTipoPago().getIdTipoPago() == 1L) {
                                cantE++;
                                montoE = montoE.add(it.getMonto());
                            } else {
                                cantC++;
                                montoC = montoC.add(it.getMonto());
                            }
                        }
                    }

                    reporteCobranza += this.getFooterCobranza(nf.format(cantE), nf.format(cantC), nf.format(cantE + cantC), nf.format(montoE), nf.format(montoC), nf.format(montoE.add(montoC)), true);
                    return reporteCobranza.getBytes();
                } else {
                    return null;
                }
            } else {
                HashMap parameters = new HashMap();
                String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
                parameters.put("nombreRed", nombreRed);
                parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
                parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
                parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
                parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
                parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
                parameters.put("idServicio", codigoServicio != null ? new Integer(codigoServicio) : null);
                parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
                parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
                parameters.put("estadoTransaccion", estadoTransaccion);
                parameters.put("idMoneda", idMoneda);
                parameters.put("fechaIni", fechaIni);
                parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
                parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
                parameters.put("IMAGES_DIR", IMAGES_DIR);
                if (formato.equalsIgnoreCase("pdf")) {//pdf
                    return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaDetalladoCS.jasper", parameters);
                } else {
                    return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaDetalladoCS.jasper", parameters);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RecaudadorFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public byte[] cobranzaDetalladoPorServicioCS(Long idRed, Long idRecaudador, Long idSucursal, Long idFacturador, String codigoServicio, String fechaIni, String fechaFin, String zona, String formatoDescarga) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idServicio", codigoServicio != null ? new Integer(codigoServicio) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("zonaEssap", zona);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formatoDescarga.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaDetalladoPorServicioCS.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaDetalladoPorServicioCS.jasper", parameters);
        }
    }

    @Override
    public byte[] cobranzaDetalladoCheque(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal,
            Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio);
        parameters.put("estadoTransaccion", estadoTransaccion);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaDetalladoCheque.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaDetalladoCheque.jasper", parameters);
        }
    }

    @Override
    public byte[] cobranzaDetalladoChequeCS(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal,
            Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato, Integer idMoneda) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio != null ? new Integer(codigoServicio) : null);
        parameters.put("estadoTransaccion", estadoTransaccion);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("idMoneda", idMoneda);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaDetalladoChequeCS.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaDetalladoChequeCS.jasper", parameters);
        }
    }

    @Override
    public byte[] cobranzaDetalladoEfectivo(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal,
            Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio);
        parameters.put("estadoTransaccion", estadoTransaccion);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaDetalladoEfectivo.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaDetalladoEfectivo.jasper", parameters);
        }
    }

    @Override
    public byte[] cobranzaDetalladoEfectivoCS(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal,
            Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato, Integer idMoneda) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio != null ? new Integer(codigoServicio) : null);
        parameters.put("estadoTransaccion", estadoTransaccion);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : fechaIni);
        parameters.put("idMoneda", idMoneda);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaDetalladoEfectivoCS.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaDetalladoEfectivoCS.jasper", parameters);
        }
    }

    @Override
    public byte[] cobranzaResumido(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal,
            Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato) {
        HashMap parameters = new HashMap();
        parameters.put("nombreRed", redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion"));
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("estadoTransaccion", estadoTransaccion);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaResumido.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaResumido.jasper", parameters);
        }
    }

    @Override
    public byte[] cobranzaResumidoCS(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal,
            Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato, Integer idMoneda) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio != null ? new Integer(codigoServicio) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("estadoTransaccion", estadoTransaccion);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaResumidoCS.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaResumidoCS.jasper", parameters);
        }
    }

    @Override
    public byte[] tapaLoteGrupo(Long idRed, Long idUsuario, Long idTerminal, Long idSucursal,
            Long idRecaudador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGrupo, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idGrupo", idGrupo != null ? new BigDecimal(idGrupo) : null);
        parameters.put("idServicio", codigoServicio != null ? codigoServicio : null);
        parameters.put("estadoTransaccion", estadoTransaccion != null ? estadoTransaccion : null);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "TapaLote.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "TapaLote.jasper", parameters);
        }

    }

    @Override
    public byte[] tapaLoteGrupoResumido(Long idRed, Long idUsuario, Long idTerminal, Long idSucursal,
            Long idRecaudador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGrupo, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idGrupo", idGrupo != null ? new BigDecimal(idGrupo) : null);
        parameters.put("idServicio", codigoServicio != null ? codigoServicio : null);
        parameters.put("estadoTransaccion", estadoTransaccion != null ? estadoTransaccion : null);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "TapaLoteResumido.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "TapaLoteResumido.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteCierreDetallado(Long idRed, Long idUsuario, Long idTerminal, Long idSucursal,
            Long idRecaudador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio != null ? codigoServicio : null);
        parameters.put("estadoTransaccion", estadoTransaccion != null ? estadoTransaccion : null);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteCierreDetallado.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCierreDetallado.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteCierreDetalladoCS(Long idRed, Long idUsuario, Long idTerminal, Long idSucursal,
            Long idRecaudador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, Long idFacturador, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio != null ? Integer.valueOf(codigoServicio) : null);
        parameters.put("estadoTransaccion", estadoTransaccion != null ? estadoTransaccion : null);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteCierreDetalladoCS.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCierreDetalladoCS.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteChequesCobradosDDJJ(Long idRed, Long idUsuario, Long idGestion, Long idTerminal, Long idSucursal,
            Long idRecaudador, String fechaIni, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed);
        parameters.put("idUsuario", idUsuario != null ? idUsuario : null);
        parameters.put("idTerminal", idTerminal != null ? idTerminal : null);
        parameters.put("idSucursal", idSucursal != null ? idSucursal : null);
        parameters.put("idRecaudador", idRecaudador != null ? idRecaudador : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("fecha", fechaIni);
        //parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        //parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteChequesCobradosDDJJ.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteChequesCobradosDDJJ.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteCierreResumido(Long idRed, Long idUsuario, Long idTerminal, Long idSucursal,
            Long idRecaudador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, String formato) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
        parameters.put("idServicio", codigoServicio != null ? codigoServicio : null);
        parameters.put("estadoTransaccion", estadoTransaccion != null ? estadoTransaccion : null);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteCierreResumido.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCierreResumido.jasper", parameters);
        }
    }

    private String getCabeceraCierre(Gestion gestion) throws Exception {

        //Armar la certificacion
        String cabeceraCertificacion = new String();
        String bodyCertificacion = new String();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        cabeceraCertificacion = "     R E P O R T E  D E  C I E R R E    \n\n";
        cabeceraCertificacion += gestion.getTerminal().getSucursal().getRecaudador().getDescripcion() + "\n";
        cabeceraCertificacion += "RUC: " + gestion.getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi() + "    -    TEL.:" + gestion.getTerminal().getSucursal().getTelefono() + "\n";
        cabeceraCertificacion += "DIRECCION: " + gestion.getTerminal().getSucursal().getDireccion() + "\n";
        bodyCertificacion += "TERMINAL: " + gestion.getTerminal().getCodigoTerminal() + "     SUC: " + gestion.getTerminal().getSucursal().getDescripcion() + "\n";
        bodyCertificacion += "USUARIO: " + gestion.getUsuario().getPersona().getApellidos() + ", " + gestion.getUsuario().getPersona().getNombres() + " (" + gestion.getUsuario().getCodigo() + ")" + "\n";
        bodyCertificacion += "GESTION: " + gestion.getNroGestion() + "\n";
        bodyCertificacion += "Fecha y Hora: " + formatter.format(gestion.getFecHoraUltUpdate()) + "\n\n";
        return cabeceraCertificacion + bodyCertificacion;

    }

    private String getCabeceraCobranza(Gestion gestion) throws Exception {

        //Armar la certificacion
        String cabeceraCertificacion = new String();
        String bodyCertificacion = new String();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        cabeceraCertificacion = "   R E P O R T E  D E  C O B R A N Z A  \n\n";
        cabeceraCertificacion += gestion.getTerminal().getSucursal().getRecaudador().getDescripcion() + "\n";
        cabeceraCertificacion += "RUC: " + gestion.getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi() + "    -    TEL.:" + gestion.getTerminal().getSucursal().getTelefono() + "\n";
        cabeceraCertificacion += "DIRECCION: " + gestion.getTerminal().getSucursal().getDireccion() + "\n";
        bodyCertificacion += "TERMINAL: " + gestion.getTerminal().getCodigoTerminal() + "     SUC: " + gestion.getTerminal().getSucursal().getDescripcion() + "\n";
        bodyCertificacion += "USUARIO: " + gestion.getUsuario().getPersona().getApellidos() + ", " + gestion.getUsuario().getPersona().getNombres() + " (" + gestion.getUsuario().getCodigo() + ")" + "\n";
        bodyCertificacion += "Fecha y Hora: " + formatter.format(gestion.getFecHoraUltUpdate()) + "\n\n";
        return cabeceraCertificacion + bodyCertificacion;

    }

    private String getFooterCobranza(String cantE, String cantC, String total, String montoE, String montoC, String montoTotal, boolean retiro) {
        if (!retiro) {
            String footer = "\n\nTotal Cobrados:" + "\n"
                    + "Cant. Efec.:  " + cantE + " Total: " + montoE + "\n"
                    + "Cant. Cheque:  " + cantC + " Total: " + montoC + "\n"
                    + "Cant. Total:  " + total + " Total Cobrados: " + montoTotal + "\n";
            return footer;
        } else {
            String footer = "\n\nTotal Retiros:" + "\n"
                    + "Cant. Efec.:  " + cantE + " Total: " + montoE + "\n"
                    + "Cant. Total:  " + total + " Total Cobrados: " + montoTotal + "\n";
            return footer;
        }
    }

    @Override
    public byte[] reporteCierreResumidoCS(Long idRed, Long idUsuario, Long idTerminal, Long idSucursal,
            Long idRecaudador, String codigoServicio, String estadoTransaccion, String fechaIni,
            String fechaFin, Long idGestion, Long idFacturador, String formato, Boolean tmu) {
        if (tmu) {
            List<Map<String, Object>> lmap = trcFacade.getResumenServiciosRc(idGestion, idTerminal, idFacturador);
            String reporteCierre = null;
            try {
                reporteCierre = this.getCabeceraCierre(gesFacade.get(idGestion));
            } catch (Exception ex) {
                Logger.getLogger(ReportesFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            BigDecimal subCobrado = BigDecimal.ZERO, subAnulado = BigDecimal.ZERO;
            BigDecimal totalEfectivo = BigDecimal.ZERO, totalEfectivoAnulados = BigDecimal.ZERO;
            BigDecimal totalCheque = BigDecimal.ZERO, totalChequeAnulados = BigDecimal.ZERO;
            BigDecimal totalRetiro = BigDecimal.ZERO, totalRetiroAnulados = BigDecimal.ZERO;
            Integer parcialAnulado = 0, parcialCobrado = 0;
            Integer cantidadEfectivo = 0, cantidadEfectivoAnulados = 0;
            Integer cantidadCheque = 0, cantidadChequeAnulados = 0;
            Integer cantidadRetiro = 0, cantidadRetiroAnulados = 0;
            NumberFormat nf = NumberFormat.getInstance();
            String servDesc = null;

            for (Map<String, Object> map : lmap) {

                Integer idServicio = (Integer) map.get("idServicio");
                if (idServicio != 84) {
                    if (servDesc == null) {
                        servDesc = (String) map.get("servicioDescripcion");
                        reporteCierre += "Facturador: " + map.get("facturadorDescripcion") + "  Servicio: " + map.get("servicioDescripcion") + "\n";
                    }

                    if (!map.get("servicioDescripcion").toString().equalsIgnoreCase(servDesc)) {

                        servDesc = (String) map.get("servicioDescripcion");
                        reporteCierre += "Sub Cobrado: " + nf.format(subCobrado) + "  Sub Anulado: " + nf.format(subAnulado) + "\n";
                        reporteCierre += "Cant Cobrados: " + nf.format(parcialCobrado) + " Cant Anulados: " + nf.format(parcialAnulado) + "\n\n";

                        subCobrado = BigDecimal.ZERO;
                        subAnulado = BigDecimal.ZERO;
                        parcialAnulado = 0;
                        parcialCobrado = 0;

                        reporteCierre += "Facturador: " + map.get("facturadorDescripcion") + "  Servicio: " + map.get("servicioDescripcion") + "\n";
                    }

                    if (map.get("anulado").toString().equalsIgnoreCase("N")
                            && map.get("idTipoPago").toString().equalsIgnoreCase("1")) {
                        reporteCierre += "Efectivo: " + nf.format(map.get("monto")) + "  Cant: " + nf.format(map.get("cantidad")) + "\n";
                        subCobrado = subCobrado.add((BigDecimal) map.get("monto"));
                        parcialCobrado += (Integer) map.get("cantidad");
                        totalEfectivo = totalEfectivo.add((BigDecimal) map.get("monto"));
                        cantidadEfectivo += (Integer) map.get("cantidad");
                    }

                    if (map.get("anulado").toString().equalsIgnoreCase("N")
                            && map.get("idTipoPago").toString().equalsIgnoreCase("2")) {
                        reporteCierre += "Cheque: " + nf.format(map.get("monto")) + "  Cant: " + nf.format(map.get("cantidad")) + "\n";
                        subCobrado = subCobrado.add((BigDecimal) map.get("monto"));
                        parcialCobrado += (Integer) map.get("cantidad");
                        totalCheque = totalCheque.add((BigDecimal) map.get("monto"));
                        cantidadCheque += (Integer) map.get("cantidad");
                    }

                    if (map.get("anulado").toString().equalsIgnoreCase("S")
                            && map.get("idTipoPago").toString().equalsIgnoreCase("1")) {
                        reporteCierre += "AnuladoE: " + nf.format(map.get("monto")) + "  Cant: " + nf.format(map.get("cantidad")) + "\n";
                        subAnulado = subAnulado.add((BigDecimal) map.get("monto"));
                        parcialAnulado += (Integer) map.get("cantidad");
                        totalEfectivoAnulados = totalEfectivoAnulados.add((BigDecimal) map.get("monto"));
                        cantidadEfectivoAnulados += (Integer) map.get("cantidad");
                    }

                    if (map.get("anulado").toString().equalsIgnoreCase("S")
                            && map.get("idTipoPago").toString().equalsIgnoreCase("2")) {
                        reporteCierre += "AnuladoC: " + nf.format(map.get("monto")) + "  Cant: " + nf.format(map.get("cantidad")) + "\n";
                        subAnulado = subAnulado.add((BigDecimal) map.get("monto"));
                        parcialAnulado += (Integer) map.get("cantidad");
                        totalChequeAnulados = totalChequeAnulados.add((BigDecimal) map.get("monto"));
                        cantidadChequeAnulados += (Integer) map.get("cantidad");
                    }
                }
            }
            reporteCierre += "\n\nTotal Cobranzas:" + "\n";
            reporteCierre += "Efectivo: " + nf.format(totalEfectivo) + "  Cant: " + nf.format(cantidadEfectivo) + "\n";
            reporteCierre += "Anulados: " + nf.format(totalEfectivoAnulados) + "  Cant: " + nf.format(cantidadEfectivoAnulados) + "\n";
            reporteCierre += "Cheque: " + nf.format(totalCheque) + "  Cant: " + nf.format(cantidadCheque) + "\n";
            reporteCierre += "Anulados: " + nf.format(totalChequeAnulados) + "  Cant: " + nf.format(cantidadChequeAnulados) + "\n";
            reporteCierre += "Monto Cobrado: " + nf.format(totalEfectivo.add(totalCheque)) + " Cant: " + nf.format(new Integer(cantidadEfectivo + cantidadCheque)) + "\n";
            reporteCierre += "Anulados: " + nf.format(totalEfectivoAnulados.add(totalChequeAnulados)) + " Cant:" + nf.format(new Integer(cantidadEfectivoAnulados + cantidadChequeAnulados)) + "\n";
            reporteCierre += "\n" + "      **** Retiros ****" + "\n\n";

            servDesc = null;

            for (Map<String, Object> map : lmap) {
                Integer idServicio = (Integer) map.get("idServicio");
                if (idServicio == 84) {

                    if (servDesc == null) {
                        servDesc = (String) map.get("servicioDescripcion");
                        reporteCierre += "Facturador: " + map.get("facturadorDescripcion") + "  Servicio: " + map.get("servicioDescripcion") + "\n";
                    }
                    if (!map.get("servicioDescripcion").toString().equalsIgnoreCase(servDesc)) {

                        servDesc = (String) map.get("servicioDescripcion");
                        reporteCierre += "Sub Cobrado: " + nf.format(subCobrado) + "  Sub Anulado: " + nf.format(subAnulado) + "\n";
                        reporteCierre += "Cant Cobrados: " + nf.format(parcialCobrado) + " Cant Anulados: " + nf.format(parcialAnulado) + "\n\n";

                        subCobrado = BigDecimal.ZERO;
                        subAnulado = BigDecimal.ZERO;
                        parcialAnulado = 0;
                        parcialCobrado = 0;

                        reporteCierre += "Facturador: " + map.get("facturadorDescripcion") + "  Servicio: " + map.get("servicioDescripcion") + "\n";
                    }

                    if (map.get("anulado").toString().equalsIgnoreCase("N")
                            && map.get("idTipoPago").toString().equalsIgnoreCase("1")) {
                        reporteCierre += "Monto: " + nf.format(map.get("monto")) + "  Cant: " + nf.format(map.get("cantidad")) + "\n";
                        subCobrado = subCobrado.add((BigDecimal) map.get("monto"));
                        parcialCobrado += (Integer) map.get("cantidad");
                        totalRetiro = totalRetiro.add((BigDecimal) map.get("monto"));
                        cantidadRetiro += (Integer) map.get("cantidad");
                    }
                    if (map.get("anulado").toString().equalsIgnoreCase("S")
                            && map.get("idTipoPago").toString().equalsIgnoreCase("1")) {
                        reporteCierre += "AnuladoE: " + nf.format(map.get("monto")) + "  Cant: " + nf.format(map.get("cantidad")) + "\n";
                        subAnulado = subAnulado.add((BigDecimal) map.get("monto"));
                        parcialAnulado += (Integer) map.get("cantidad");
                        totalRetiroAnulados = totalRetiroAnulados.add((BigDecimal) map.get("monto"));
                        cantidadRetiroAnulados += (Integer) map.get("cantidad");
                    }

                }
            }
            reporteCierre += "\n\nTotal Retiros:" + "\n";
            reporteCierre += "Monto: " + nf.format(totalRetiro) + "  Cant: " + nf.format(cantidadRetiro) + "\n";
            reporteCierre += "Anulados: " + nf.format(totalRetiroAnulados) + "  Cant: " + nf.format(cantidadRetiroAnulados) + "\n";

            return reporteCierre.getBytes();
        } else {
            HashMap parameters = new HashMap();
            String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
            parameters.put("nombreRed", nombreRed);
            parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
            parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
            parameters.put("idTerminal", idTerminal != null ? new BigDecimal(idTerminal) : null);
            parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
            parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
            parameters.put("idGestion", idGestion != null ? new BigDecimal(idGestion) : null);
            parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
            parameters.put("idServicio", codigoServicio != null ? Integer.valueOf(codigoServicio) : null);
            parameters.put("estadoTransaccion", estadoTransaccion != null ? estadoTransaccion : null);
            parameters.put("fechaIni", fechaIni);
            parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
            parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
            parameters.put("IMAGES_DIR", IMAGES_DIR);
            if (formato.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteCierreResumidoCS.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCierreResumidoCS.jasper", parameters);
            }
        }
    }

    @Override
    public byte[] reporteFormularioOt(Long idRed, String fecha, /*
             * Long nroOt,
             */ Integer tipoPago) throws Exception {

        int nroDias = tipoPago == 1 ? 2 : tipoPago == 2 ? 4 : 0;

        Calendar fechaDoc = Calendar.getInstance();
        Calendar fechaParam = Calendar.getInstance();
        fechaDoc.setTime(new SimpleDateFormat("ddMMyyyy").parse(fecha));

        fechaParam.setTime(new SimpleDateFormat("ddMMyyyy").parse(fecha));

        fechaDoc = this.utilFacade.diaHabilSiguiente(fechaDoc, -nroDias);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

        List<Map<String, Object>> listaDS
                = numeroOtFacade.obtenerDatosReporteOT(idRed, sdf.format(fechaDoc.getTime()), /*
                         * nroOt,
                         */ tipoPago);

        HashMap parameters = new HashMap();
//        parameters.put("idRed", idRed);
        parameters.put("tipoPago", tipoPago);
        parameters.put("fecha", fechaParam.getTime());

        /*
         * for(int i = 0; i < nroDias; i ++) {
         * fechaAcreditacion.add(Calendar.DATE,); fechaAcreditacion =
         * this.utilFacade.proximoDiaHabil(fechaAcreditacion); }
         */
        parameters.put("fechaAcreditacion", fechaDoc.getTime());
        parameters.put("IMAGES_DIR", IMAGES_DIR);

        return this.generarReporte(COBRANZAS_DIR + "REPORTE910-921.jasper", listaDS, parameters);
    }

    @Override
    public byte[] reporteTransferenciaDGR(java.lang.String fecha) throws Exception {

        short flagE = 0, flagC = 0;
        Calendar fechaParam = Calendar.getInstance();
        fechaParam.setTime(new SimpleDateFormat("ddMMyyyy").parse(fecha));

        Calendar fechaDocE = Calendar.getInstance();
        fechaDocE.setTime(new SimpleDateFormat("ddMMyyyy").parse(fecha));

        fechaDocE = this.utilFacade.diaHabilSiguiente(fechaDocE, -2);

        Calendar fechaDocC = Calendar.getInstance();
        fechaDocC.setTime(new SimpleDateFormat("ddMMyyyy").parse(fecha));

        fechaDocC = this.utilFacade.diaHabilSiguiente(fechaDocC, -4);

        SimpleDateFormat sdfE = new SimpleDateFormat("ddMMyyyy");

        SimpleDateFormat sdfC = new SimpleDateFormat("ddMMyyyy");

        List<Map<String, Object>> listaDS
                = numeroOtFacade.obtenerDatosReporteOT(1L, sdfE.format(fechaDocE.getTime()), 1);

        if (!listaDS.isEmpty()) {
            //flagE = 1;
            for (Map<String, Object> mapa : listaDS) {
                mapa.put("fechaDoc", fechaDocE.getTime());
                mapa.put("concepto", "Efectivo");
            }
        }
        List<Map<String, Object>> aux = numeroOtFacade.obtenerDatosReporteOT(1L, sdfC.format(fechaDocC.getTime()), 2);
        if (!aux.isEmpty()) {
            //flagE = 1;
            for (Map<String, Object> mapa : aux) {
                mapa.put("fechaDoc", fechaDocC.getTime());
                mapa.put("concepto", "Cheque");
            }
        }

        listaDS.addAll(aux);
        /*
         * if (listaDS.size() == 1 && flagE == 0) { flagC = 1; } else if
         * (listaDS.size() == 2) { flagC = 1; }
         *
         * for (Map<String, Object> mapa : listaDS) { if (flagE == 1) { flagE =
         * 0; mapa.put("fechaDoc", fechaDocE.getTime()); mapa.put("concepto",
         * "Efectivo"); } else { if (flagC == 1) { flagC = 0;
         * mapa.put("fechaDoc", fechaDocC.getTime()); mapa.put("concepto",
         * "Cheque"); } } }
         */

        HashMap parameters = new HashMap();
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        parameters.put("fecha", fechaParam.getTime());

        return this.generarReporte(COBRANZAS_DIR + "TransferenciaDGR.jasper", listaDS, parameters);
    }

    @Override
    public byte[] reporteDetalladoTransferenciaDGR(Long idRed, java.lang.String fecha, String formato) throws Exception {
        Calendar fechaDocC = Calendar.getInstance();
        fechaDocC.setTime(new SimpleDateFormat("ddMMyyyy").parse(fecha));

        fechaDocC = this.utilFacade.diaHabilSiguiente(fechaDocC, -4);
        SimpleDateFormat sdfC = new SimpleDateFormat("ddMMyyyy");

        Red r = redFacade.get(idRed);

        HashMap parameters = new HashMap();
        parameters.put("fechaDoc", sdfC.format(fechaDocC.getTime()));
        parameters.put("cuentaBcpImpuestos", r.getCuentaBcpImpuestos());
        parameters.put("fecha", fecha);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "TransferenciaDetalladoDGR.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "TransferenciaDetalladoDGR.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteCierreUsuariosSet(Long idRed, java.lang.String fechaIni, java.lang.String fechaFin, Long idSucursal, Long idRecaudador, String formato, String tipoReporte) throws Exception {
        HashMap parameters = new HashMap();
        //parameters.put("nombreRed", redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion"));
        parameters.put("idRed", idRed);
        parameters.put("idSucursal", idSucursal != null ? idSucursal : null);
        parameters.put("idRecaudador", idRecaudador != null ? idRecaudador : null);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : fechaIni);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (tipoReporte.equalsIgnoreCase("BBPP")) {
            if (formato.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteUsuarioBBPP.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteUsuarioBBPP.jasper", parameters);
            }
        } else {
            if (formato.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteUsuarioDDJJ.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteUsuarioDDJJ.jasper", parameters);
            }
        }
    }

    @Override
    public byte[] reporteRecaudacion(Long idRed, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.String formato, String tipoReporte) {
        HashMap parameters = new HashMap();
        String reporte = "Recaudacion";
        parameters.put("idRed", idRed);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        parameters.put("idRecaudador", idRecaudador != null ? idRecaudador : null);
        parameters.put("idSucursal", idSucursal != null ? idSucursal : null);
        if (tipoReporte != null && tipoReporte.equalsIgnoreCase("detallado")) {
            reporte = "RecaudacionDetallada";
        }
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + reporte + ".jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + reporte + ".jasper", parameters);
        }
    }

    @Override
    public byte[] reporteResumenFacturador(Long idRed, java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idRecaudador, java.lang.Long idFacturador, java.lang.Long idSucursal, String isCorte, java.lang.String formato, String tipoReporte) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("redDescripcion", nombreRed);
        parameters.put("idRed", idRed);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        parameters.put("idFacturador", idFacturador != null ? idFacturador : null);
        parameters.put("idRecaudador", idRecaudador != null ? idRecaudador : null);
        parameters.put("idSucursal", idSucursal != null ? idSucursal : null);
        if (isCorte.equalsIgnoreCase("SIN_CORTE")) {
            if (formato.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteResumenFacturador.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteResumenFacturador.jasper", parameters);
            }
        } else {
            if (formato.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteResumenFacturadorCorte.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteResumenFacturadorCorte.jasper", parameters);
            }
        }
    }

    @Override
    public byte[] reporteTerminalesAbiertas(java.lang.String fechaIni, java.lang.String fechaFin, java.lang.Long idRecaudador, java.lang.Long idSucursal, java.lang.String formato) {
        HashMap parameters = new HashMap();
        parameters.put("fechaDesde", fechaIni);
        parameters.put("fechaHasta", fechaFin);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        parameters.put("idRecaudador", idRecaudador != null ? idRecaudador : null);
        parameters.put("idSucursal", idSucursal != null ? idSucursal : null);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteTerminalesAbiertas.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteTerminalesAbiertas.jasper", parameters);
        }
    }

    @Override
    public byte[] cobranzaDetalladoParaFacturador(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, Long idTerminal, Long idFacturador, String codigoServicio, String estadoTransaccion, String fechaIni, String fechaFin, Long idGestion, String formatoDescarga) {
        HashMap parameters = new HashMap();
        parameters.put("idRed", idRed);
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        parameters.put("idFacturador", idFacturador != null ? idFacturador : null);
        parameters.put("idRecaudador", idRecaudador != null ? idRecaudador : null);
        parameters.put("idSucursal", idSucursal != null ? idSucursal : null);
        parameters.put("idServicio", codigoServicio != null ? new Long(codigoServicio) : null);
        parameters.put("idUsuario", idUsuario);
        if (formatoDescarga.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteCobranzaParaFacturador.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCobranzaParaFacturador.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteGestor(String fechaIni, String fechaFin, Long idSucursal, Long ci, String formato) {
        HashMap parameters = new HashMap();
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        parameters.put("ci", ci);
        parameters.put("idSucursal", idSucursal != null ? idSucursal : null);
        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteGestor.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteGestor.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteMovimientoFacturadores(String fechaIni, String fechaFin, String formatoDescarga) {
        HashMap parameters = new HashMap();
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        if (formatoDescarga.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteMovimientoFacturadores.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteMovimientoFacturadores.jasper", parameters);
        }
    }

    @Override
    public byte[] reporteClearingCS(Long idEntidad, String fechaIni, String fechaFin, String tipoReporte, String formatoDescarga) {
        HashMap parameters = new HashMap();
        parameters.put("idEntidad", idEntidad);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formatoDescarga.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteClearingCSResumido.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteClearingCSResumido.jasper", parameters);
        }

    }

    @Override
    public byte[] reporteComisionServicios(Long idRed, Long idRecaudador, Long idFacturador, Long idSucursal, Long idServicio, Integer idTipoPago, String fechaIni, String fechaFin, String tipoReporte, String formatoDescarga) {
        HashMap parameters = new HashMap();
        parameters.put("idRed", idRed);
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        parameters.put("idSucursal", idSucursal);
        parameters.put("idRecaudador", idRecaudador);
        parameters.put("idTipoPago", idTipoPago);
        parameters.put("idServicio", idServicio);
        parameters.put("idFacturador", idFacturador);
        if (tipoReporte.equalsIgnoreCase("DETALLADO")) {
            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteCreditoComisionDetallado.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCreditoComisionDetallado.jasper", parameters);
            }
        } else if (tipoReporte.equalsIgnoreCase("RESUMIDO")) {
            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteCreditoComisionResumido.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCreditoComisionResumido.jasper", parameters);
            }
        } else if (tipoReporte.equalsIgnoreCase("RESUMIDO-REC")) {
            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteCreditoComisionRecaudadorResumido.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCreditoComisionRecaudadorResumido.jasper", parameters);
            }
        } else { //(tipoReporte.equalsIgnoreCase("DETALLADO-REC")){

            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteCreditoComisionRecaudadorDetallado.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteCreditoComisionRecaudadorDetallado.jasper", parameters);
            }
        }
    }

    @Override
    public byte[] reporteFacturacionCS(Long idRed, Long idRecaudador, Integer idTipoPago, String tipoReporte, String fechaIni, String fechaFin, Integer diasAnteriores, String formatoDescarga) {
        HashMap parameters = new HashMap();
        String fechaAnterior = String.valueOf(Integer.parseInt(fechaFin.substring(0, 2)) - diasAnteriores) + fechaFin.substring(2);
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        parameters.put("idRed", idRed);
        parameters.put("idRecaudador", idRecaudador);
        parameters.put("idTipoPago", idTipoPago);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        parameters.put("fechaAnteriorFin", fechaAnterior);
        if (tipoReporte.equalsIgnoreCase("RESUMIDO")) {
            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteFacturacion.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteFacturacion.jasper", parameters);
            }
        } else { //if (tipoReporte.equalsIgnoreCase("GENERAL")){
            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteRecaudadoresGeneral.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteRecaudadoresGeneral.jasper", parameters);
            }
        }
    }

    @Override
    public byte[] reporteSanCristobal(Long idRed, String fechaIni, String fechaFin, Long idSucursal, Long idRecaudador, Long idServicio, String formato) {
        HashMap parameters = new HashMap();
        parameters.put("idRed", idRed);
        parameters.put("idSucursal", idSucursal);
        parameters.put("idRecaudador", idRecaudador);
        parameters.put("idServicio", idServicio);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : fechaIni);
        parameters.put("IMAGES_DIR", IMAGES_DIR);

        if (formato.equalsIgnoreCase("pdf")) {
            return this.generarReporte(COBRANZAS_DIR + "ReporteSanCristobal.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteSanCristobal.jasper", parameters);
        }

    }

    @Override
    public byte[] reporteFacturacionAdministracion(String fechaIni, String fechaFin, Long idRecaudador, Integer idTipoPago, Long idRed, Integer diasAnteriores, Long idServicio, Long idFacturador, String tipoReporte, String formatoDescarga) {
        HashMap parameters = new HashMap();
        parameters.put("IMAGES_DIR", IMAGES_DIR);
        parameters.put("idRed", idRed);
        parameters.put("idRecaudador", idRecaudador);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin);
        if (tipoReporte.equalsIgnoreCase("RESUMIDO")) {
            parameters.put("idTipoPago", idTipoPago);
            parameters.put("idFacturador", idFacturador);
            parameters.put("idServicio", idServicio);

            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteAdministracionFacturadoresResumido.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteAdministracionFacturadoresResumido.jasper", parameters);
            }
        } else if (tipoReporte.equalsIgnoreCase("DETALLADO")) {
            String fechaAnterior = String.valueOf(Integer.parseInt(fechaFin.substring(0, 2)) - diasAnteriores) + fechaFin.substring(2);
            parameters.put("fechaAnteriorFin", fechaAnterior);
            parameters.put("idTipoPago", idTipoPago);
            parameters.put("idFacturador", idFacturador);
            parameters.put("idServicio", idServicio);
            parameters.put("idTipoPago", idTipoPago);
            parameters.put("idFacturador", null);
            parameters.put("idServicio", null);

            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "ReporteAdministracionFacturadoresDetallado.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteAdministracionFacturadoresDetallado.jasper", parameters);
            }
        } else {
            if (formatoDescarga.equalsIgnoreCase("pdf")) {
                return this.generarReporte(COBRANZAS_DIR + "reporteComisionResumenSET.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "reporteComisionResumenSET.jasper", parameters);
            }
        }
    }

    @Override
    public byte[] reporteComisionParaFacRec(Long idRed, Long idRecaudador, Long idSucursal, Long idFacturador, String codigoServicio, String fechaIni, String fechaFin, String zona, String entidad, String tipoReporte, String formatoDescarga) {
        HashMap parameters = new HashMap();
        String nombreRed = idRed != null ? (String) redFacade.get(new Red(idRed), new String[]{"descripcion"}).get("descripcion") : "RED DE PAGO";
        parameters.put("nombreRed", nombreRed);
        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idFacturador", idFacturador != null ? new BigDecimal(idFacturador) : null);
        parameters.put("idServicio", codigoServicio != null ? new Integer(codigoServicio) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("zonaEssap", zona);
        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("SUBREPORT_DIR", COBRANZAS_DIR);
        parameters.put("IMAGES_DIR", IMAGES_DIR);

        if (entidad.equalsIgnoreCase("FAC")) {
            if (formatoDescarga.equalsIgnoreCase("pdf")) {//pdf
                return this.generarReporte(COBRANZAS_DIR + "ReporteFacturadoresDetallado.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteFacturadoresDetallado.jasper", parameters);
            }
        } else {
            if (formatoDescarga.equalsIgnoreCase("pdf")) {//pdf
                return this.generarReporte(COBRANZAS_DIR + "ReporteRecaudadorDetallado.jasper", parameters);
            } else {
                return this.generarReporteXLS(COBRANZAS_DIR + "ReporteRecaudadorDetallado.jasper", parameters);
            }
        }

    }

    @Override
    public byte[] reporteDigitacion(Long idRed, Long idRecaudador, Long idSucursal, Long idUsuario, String certificado, String fechaIni, String fechaFin, String formatoDescarga) {
        HashMap parameters = new HashMap();

        parameters.put("idRed", idRed != null ? new BigDecimal(idRed) : null);
        parameters.put("idRecaudador", idRecaudador != null ? new BigDecimal(idRecaudador) : null);
        parameters.put("idSucursal", idSucursal != null ? new BigDecimal(idSucursal) : null);
        parameters.put("idUsuario", idUsuario != null ? new BigDecimal(idUsuario) : null);
        parameters.put("certificado", certificado);

        parameters.put("fechaIni", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);

        ///parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formatoDescarga.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReporteDigitaciones.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReporteDigitaciones.jasper", parameters);
        }
    }

    @Override
    public byte[] reportePractiRetiro(String fechaIni, String fechaFin, String formatoDescarga) {
        HashMap parameters = new HashMap();

        parameters.put("fechaInicio", fechaIni);
        parameters.put("fechaFin", fechaFin != null ? fechaFin : null);
        parameters.put("IMAGES_DIR", IMAGES_DIR);

        ///parameters.put("IMAGES_DIR", IMAGES_DIR);
        if (formatoDescarga.equalsIgnoreCase("pdf")) {//pdf
            return this.generarReporte(COBRANZAS_DIR + "ReportePractiRetiros.jasper", parameters);
        } else {
            return this.generarReporteXLS(COBRANZAS_DIR + "ReportePractiRetiros.jasper", parameters);
        }
    }

}
