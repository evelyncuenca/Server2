/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import py.com.konecta.redcobros.entities.BoletaPagoContrib;
import py.com.konecta.redcobros.entities.CamposFormContrib;
import py.com.konecta.redcobros.entities.Contribuyentes;
import py.com.konecta.redcobros.entities.Corte;
import py.com.konecta.redcobros.entities.Dominios;
import py.com.konecta.redcobros.entities.EntidadFinanciera;
import py.com.konecta.redcobros.entities.FormContrib;
import py.com.konecta.redcobros.entities.Formulario;
import py.com.konecta.redcobros.entities.FormularioImpuesto;
import py.com.konecta.redcobros.entities.Gestion;
import py.com.konecta.redcobros.entities.Grupo;
import py.com.konecta.redcobros.entities.NumeroOt;
import py.com.konecta.redcobros.entities.ParamVencimientos;
import py.com.konecta.redcobros.entities.Recaudador;
import py.com.konecta.redcobros.entities.Red;
import py.com.konecta.redcobros.entities.Sucursal;
import py.com.konecta.redcobros.entities.Terminal;
import py.com.konecta.redcobros.entities.Transaccion;
import py.com.konecta.redcobros.entities.TipoPago;
import py.com.konecta.redcobros.utiles.Constantes;
import py.com.konecta.redcobros.utiles.GeneradorArchivoClearing;
import py.com.konecta.redcobros.utiles.GeneradorContinental;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UtilFacadeImpl implements UtilFacade {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    public static String CARACTER_SEPARADOR_LINEA = "\r\n";
    @EJB
    private FormContribFacade formContribFacade;
    @EJB
    private NumeroOtFacade numeroOtFacade;
    @EJB
    private BoletaPagoContribFacade boletaPagoContribFacade;
    @EJB
    private CamposFormContribFacade campoFormContribFacade;
    @EJB
    private DominiosFacade dominiosFacade;
    @EJB
    private ContribuyentesFacade contribuyentesFacade;
    @EJB
    private ParamVencimientosFacade paramVencimientosFacade;
    @EJB
    private FeriadosFacade feriadosFacade;
    @EJB
    private FormularioImpuestoFacade fiFacade;
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private FormularioFacade formularioFacade;
    @EJB
    private RedFacade redF;
    @Resource
    private SessionContext context;

    public boolean existeRegistrosFormulariosERA(Long idRed, Date fecha) {
        return this.formContribFacade.existeFormulariosERA(idRed, fecha);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String generarArchivoERAFormulario(Long idRed, Date fecha) throws Exception {
        try {
            StringBuffer escribir = new StringBuffer();
            Format formatter;

            Red ejemploRed = new Red(idRed);
            Integer numeroERA = (Integer) this.redF.get(ejemploRed,
                    new String[]{"codEra"}).get("codEra");

            Recaudador ejemploRecaudador = new Recaudador();
            ejemploRecaudador.setRed(ejemploRed);
            Sucursal ejemploSucursal = new Sucursal();
            ejemploSucursal.setRecaudador(ejemploRecaudador);
            Terminal ejemploTerminal = new Terminal();
            ejemploTerminal.setSucursal(ejemploSucursal);
            Gestion ejemploGestion = new Gestion();
            ejemploGestion.setTerminal(ejemploTerminal);
            Grupo ejemploGrupo = new Grupo();
            ejemploGrupo.setCerrado("S");
            ejemploGrupo.setGestion(ejemploGestion);
            FormContrib ejemploFormContrib = new FormContrib();
            ejemploFormContrib.setCamposValidos(1);
            ejemploFormContrib.setCertificado("S");
            ejemploFormContrib.setFechaCertificadoSet(fecha);
            ejemploFormContrib.setGrupo(ejemploGrupo);

            int total = formContribFacade.total(ejemploFormContrib);

            if (total > 0) {
                Integer totalLineas = 1;
                totalLineas += total;

                CamposFormContrib ejemploCFC = null;

                int contador = 0;
                int cantidad = 50;
                while (contador < total) {
                    List<Map<String, Object>> lista
                            = formContribFacade.listAtributos(ejemploFormContrib,
                                    contador, cantidad, new String[]{"idFormContrib"});

                    ejemploCFC = new CamposFormContrib();
                    for (int i = 0; i < lista.size(); i++) {
                        ejemploCFC.setFormContrib(new FormContrib(
                                (Long) lista.get(i).get("idFormContrib")));
                        totalLineas
                                += this.campoFormContribFacade.total(ejemploCFC);
                    }
                    contador += cantidad;
                }

                //primera linea
                //identificador del registro
                escribir.append("00");
                //cero
                escribir.append("0");
                //ERA
                escribir.append(UtilesSet.cerosIzquierda(numeroERA.longValue(),
                        Constantes.TAM_ERA));
                //fecha de operacion
                formatter = new SimpleDateFormat("ddMMyyyy");
                escribir.append(formatter.format(fecha));
                //cantidad de registros en el archivo
                escribir.append(UtilesSet.cerosIzquierda(
                        totalLineas.longValue(), Constantes.TAM_TOTAL_LINEAS)).append((totalLineas.intValue() > 1) ? CARACTER_SEPARADOR_LINEA : "");
                Grupo grupo;
                int contadorLineas = 1;
                contador = 0;
                while (contador < total) {
                    List<FormContrib> listaFC
                            = formContribFacade.list(ejemploFormContrib,
                                    contador, cantidad,
                                    new String[]{"grupo.gestion.terminal.sucursal.codigoSucursalSet",
                                        "grupo.gestion.terminal.codigoCajaSet",
                                        "consecutivo"},
                                    new String[]{"asc", "asc", "asc"});
                    for (FormContrib fc : listaFC) {
                        ejemploCFC.setFormContrib(new FormContrib(fc.getIdFormContrib()));
                        List<CamposFormContrib> listaCFC = this.campoFormContribFacade.list(ejemploCFC, "numeroCampo", "asc");

                        if (fc.getCodigoHash() == null) {
                            fc.setCodigoHash(fc.toStringHash(listaCFC));
                            fc = this.formContribFacade.merge(fc);
                        }
                        contadorLineas++;
                        escribir.append(fc.toStringERA(numeroERA)).append((contadorLineas < totalLineas.intValue()) ? CARACTER_SEPARADOR_LINEA : "");

                        for (CamposFormContrib cfc : listaCFC) {
                            contadorLineas++;
                            escribir.append(cfc.toStringERA(numeroERA)).append((contadorLineas < totalLineas.intValue()) ? CARACTER_SEPARADOR_LINEA : "");
                        }
                        grupo = fc.getGrupo();
                        if (grupo.getProcesado().equalsIgnoreCase("N")) {
                            grupo.setProcesado("S");
                            this.grupoFacade.merge(grupo);
                        }
                    }
                    contador += cantidad;
                }
                return escribir.toString();
            } else {
                throw new Exception("No existen formularios para la fecha solicitada o las gestiones " + "aun no fueron cerradas");
            }

        } catch (Exception e) {
            context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
    }

    public boolean existeRegistrosBoletasPagosERA(TipoPago tipoPago, Long idRed, Date fecha) {
        return this.boletaPagoContribFacade.existeBoletasPagosERA(tipoPago, idRed, fecha);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String generarArchivoERABoletaPago(TipoPago tipoPago, Long idRed, Date fecha) throws Exception {
        if (tipoPago.getIdTipoPago().equals(3L)) {//cambiar aqui para que este normal todo
            try {
                //Red red = this.redF.get(idRed);

                StringBuffer escribir = new StringBuffer();
                Long montoTotal = 0L;
                Integer totalLineas = 2;

                Red ejemploRed = new Red(idRed);

//            Integer numeroERA = ejemploRed.getCodEra();
//            String numeroCuenta = ejemploRed.getCuentaBcpImpuestos();
                Integer numeroEra = (Integer) this.redF.get(ejemploRed,
                        new String[]{"codEra"}).get("codEra");

                String numeroCuenta = (String) this.redF.get(ejemploRed,
                        new String[]{"cuentaBcpImpuestos"}).get("cuentaBcpImpuestos");

                Recaudador ejemploRecaudador = new Recaudador();
                ejemploRecaudador.setRed(ejemploRed);
                Sucursal ejemploSucursal = new Sucursal();
                ejemploSucursal.setRecaudador(ejemploRecaudador);
                Terminal ejemploTerminal = new Terminal();
                ejemploTerminal.setSucursal(ejemploSucursal);
                Gestion ejemploGestion = new Gestion();
                ejemploGestion.setTerminal(ejemploTerminal);
                Grupo ejemploGrupo = new Grupo();
                ejemploGrupo.setCerrado("S");
                ejemploGrupo.setGestion(ejemploGestion);
                Transaccion ejemploTransaccion = new Transaccion();
                ejemploTransaccion.setFlagAnulado("N");
                ejemploTransaccion.setTipoPago(tipoPago);
                BoletaPagoContrib ejemploBoleta = new BoletaPagoContrib();
                ejemploBoleta.setFechaCobro(fecha);
                ejemploBoleta.setTransaccion(ejemploTransaccion);
                ejemploBoleta.setGrupo(ejemploGrupo);

                int total = boletaPagoContribFacade.total(ejemploBoleta);

//            List<BoletaPagoContrib> listaBPC = this.boletaPagoContribFacade.boletasPagosERA(tipoPago, idRed, fecha);
                if (total > 0) {
                    totalLineas += total;
                    //primera linea
                    //identificador del registro
                    Format formatter;
                    escribir.append("00");
                    //cero
                    escribir.append("0");
                    //ERA
                    escribir.append(UtilesSet.cerosIzquierda(numeroEra.longValue(), Constantes.TAM_ERA));
                    //fecha de operacion
                    formatter = new SimpleDateFormat("ddMMyyyy");
                    escribir.append(formatter.format(fecha));
                    //cantidad de registros en el archivo
                    escribir.append(UtilesSet.cerosIzquierda(totalLineas.longValue(), Constantes.TAM_TOTAL_LINEAS) + CARACTER_SEPARADOR_LINEA);
                    NumeroOt numeroOt = this.numeroOtFacade.obtenerNumeroOT(idRed, numeroEra, tipoPago.getIdTipoPago(), fecha);
                    Grupo grupo;
                    boolean actualizado;
                    int contador = 0, cantidad = 50;

                    while (contador < total) {
                        List<BoletaPagoContrib> listaBPC = boletaPagoContribFacade.list(ejemploBoleta, contador, cantidad,
                                new String[]{"grupo.gestion.terminal.sucursal.codigoSucursalSet",
                                    "grupo.gestion.terminal.codigoCajaSet",
                                    "consecutivo"},
                                new String[]{"asc", "asc", "asc"});
                        for (BoletaPagoContrib bpc : listaBPC) {
                            actualizado = false;
                            if (bpc.getCodigoHash() == null) {
                                bpc.setCodigoHash(bpc.toStringHash());
                                actualizado = true;
                            }
                            if (bpc.getNumeroOt() == null) {
                                bpc.setNumeroOt(numeroOt);
                                actualizado = true;
                            }
                            if (actualizado) {
                                bpc = this.boletaPagoContribFacade.merge(bpc);
                            }
                            escribir.append(bpc.toStringERA(numeroEra) + CARACTER_SEPARADOR_LINEA);
                            montoTotal += bpc.getTotal().longValue();
                            grupo = bpc.getGrupo();
                            if (grupo.getProcesado().equalsIgnoreCase("N")) {
                                grupo.setProcesado("S");
                                this.grupoFacade.merge(grupo);
                            }
                        }
                        contador += cantidad;
                    }
                    String cadenaHASH = "";

                    //primera linea
                    //identificador del registro
                    escribir.append("77");
                    //nueve
                    escribir.append("9");
                    //ERA
                    escribir.append(UtilesSet.cerosIzquierda(numeroEra.longValue(), Constantes.TAM_ERA));
                    //OT
                    cadenaHASH += numeroOt.getEraNumeroOtDv();
                    escribir.append(numeroOt.getEraNumeroOtDv());
                    //tipo OT: 1 impuestos
                    cadenaHASH += "1";
                    escribir.append("1");
                    //formulario
                    if (tipoPago.getIdTipoPago() == 1) {
                        //efectivo
                        cadenaHASH += "0910";
                        escribir.append("0910");
                    } else {
                        //cheque
                        cadenaHASH += "0921";
                        escribir.append("0921");
                    }
                    //version
                    cadenaHASH += "01";
                    escribir.append("01");
                    //fecha de operacion
                    formatter = new SimpleDateFormat("ddMMyyyy");
                    cadenaHASH += formatter.format(fecha);
                    escribir.append(formatter.format(fecha));
                    //monto
                    cadenaHASH += UtilesSet.cerosIzquierda(montoTotal, Constantes.TAM_IMPORTE);
                    escribir.append(UtilesSet.cerosIzquierda(montoTotal, Constantes.TAM_IMPORTE));
                    //cuenta bcp
                    cadenaHASH += UtilesSet.espaciosDerecha(numeroCuenta, Constantes.TAM_CUENTA_BCP);
                    escribir.append(UtilesSet.espaciosDerecha(numeroCuenta, Constantes.TAM_CUENTA_BCP));
                    //codigo hash
                    escribir.append(UtilesSet.getHashSet(cadenaHASH));
                    return escribir.toString();
                } else {
                    throw new Exception("No existen boletas de pago para la fecha solicitada o las gestiones " + "aun no fueron cerradas");
                }

            } catch (Exception e) {
                context.setRollbackOnly();
                e.printStackTrace();
                throw e;
            }
        } else {
            try {
                //Red red = this.redF.get(idRed);

                StringBuffer escribir = new StringBuffer();
                Long montoTotal = 0L;
                Integer totalLineas = 2;

                Red ejemploRed = new Red(idRed);

//            Integer numeroERA = ejemploRed.getCodEra();
//            String numeroCuenta = ejemploRed.getCuentaBcpImpuestos();
                Integer numeroEra = (Integer) this.redF.get(ejemploRed,
                        new String[]{"codEra"}).get("codEra");

                String numeroCuenta = (String) this.redF.get(ejemploRed,
                        new String[]{"cuentaBcpImpuestos"}).get("cuentaBcpImpuestos");

                Recaudador ejemploRecaudador = new Recaudador();
                ejemploRecaudador.setRed(ejemploRed);
                Sucursal ejemploSucursal = new Sucursal();
                ejemploSucursal.setRecaudador(ejemploRecaudador);
                Terminal ejemploTerminal = new Terminal();
                ejemploTerminal.setSucursal(ejemploSucursal);
                Gestion ejemploGestion = new Gestion();
                ejemploGestion.setTerminal(ejemploTerminal);
                Grupo ejemploGrupo = new Grupo();
                ejemploGrupo.setCerrado("S");
                ejemploGrupo.setGestion(ejemploGestion);
                Transaccion ejemploTransaccion = new Transaccion();
                ejemploTransaccion.setFlagAnulado("N");
                ejemploTransaccion.setTipoPago(tipoPago);
                BoletaPagoContrib ejemploBoleta = new BoletaPagoContrib();
                ejemploBoleta.setFechaCobro(fecha);
                ejemploBoleta.setTransaccion(ejemploTransaccion);
                ejemploBoleta.setGrupo(ejemploGrupo);
                ejemploBoleta.setFlgPagoOnline(2);
                int total = boletaPagoContribFacade.total(ejemploBoleta);

//            List<BoletaPagoContrib> listaBPC = this.boletaPagoContribFacade.boletasPagosERA(tipoPago, idRed, fecha);
                if (total > 0) {
                    totalLineas += total;
                    //primera linea
                    //identificador del registro
                    Format formatter;
                    escribir.append("00");
                    //cero
                    escribir.append("0");
                    //ERA
                    escribir.append(UtilesSet.cerosIzquierda(numeroEra.longValue(), Constantes.TAM_ERA));
                    //fecha de operacion
                    formatter = new SimpleDateFormat("ddMMyyyy");
                    escribir.append(formatter.format(fecha));
                    //cantidad de registros en el archivo
                    escribir.append(UtilesSet.cerosIzquierda(totalLineas.longValue(), Constantes.TAM_TOTAL_LINEAS) + CARACTER_SEPARADOR_LINEA);
                    NumeroOt numeroOt = this.numeroOtFacade.obtenerNumeroOT(idRed, numeroEra, tipoPago.getIdTipoPago(), fecha);
                    Grupo grupo;
                    boolean actualizado;
                    int contador = 0, cantidad = 50;

                    while (contador < total) {
                        List<BoletaPagoContrib> listaBPC = boletaPagoContribFacade.list(ejemploBoleta, contador, cantidad,
                                new String[]{"grupo.gestion.terminal.sucursal.codigoSucursalSet",
                                    "grupo.gestion.terminal.codigoCajaSet",
                                    "consecutivo"},
                                new String[]{"asc", "asc", "asc"});
                        for (BoletaPagoContrib bpc : listaBPC) {
                            actualizado = false;
                            if (bpc.getCodigoHash() == null) {
                                bpc.setCodigoHash(bpc.toStringHash());
                                actualizado = true;
                            }
                            if (bpc.getNumeroOt() == null) {
                                bpc.setNumeroOt(numeroOt);
                                actualizado = true;
                            }
                            if (actualizado) {
                                bpc = this.boletaPagoContribFacade.merge(bpc);
                            }
                            escribir.append(bpc.toStringERA(numeroEra) + CARACTER_SEPARADOR_LINEA);
                            montoTotal += bpc.getTotal().longValue();
                            grupo = bpc.getGrupo();
                            if (grupo.getProcesado().equalsIgnoreCase("N")) {
                                grupo.setProcesado("S");
                                this.grupoFacade.merge(grupo);
                            }
                        }
                        contador += cantidad;
                    }
                    String cadenaHASH = "";

                    //primera linea
                    //identificador del registro
                    escribir.append("77");
                    //nueve
                    escribir.append("9");
                    //ERA
                    escribir.append(UtilesSet.cerosIzquierda(numeroEra.longValue(), Constantes.TAM_ERA));
                    //OT
                    cadenaHASH += numeroOt.getEraNumeroOtDv();
                    escribir.append(numeroOt.getEraNumeroOtDv());
                    //tipo OT: 1 impuestos
                    cadenaHASH += "1";
                    escribir.append("1");
                    //formulario
                    if (tipoPago.getIdTipoPago() == 1) {
                        //efectivo
                        cadenaHASH += "0910";
                        escribir.append("0910");
                    } else {
                        //cheque
                        cadenaHASH += "0921";
                        escribir.append("0921");
                    }
                    //version
                    cadenaHASH += "01";
                    escribir.append("01");
                    //fecha de operacion
                    formatter = new SimpleDateFormat("ddMMyyyy");
                    cadenaHASH += formatter.format(fecha);
                    escribir.append(formatter.format(fecha));
                    //monto
                    cadenaHASH += UtilesSet.cerosIzquierda(montoTotal, Constantes.TAM_IMPORTE);
                    escribir.append(UtilesSet.cerosIzquierda(montoTotal, Constantes.TAM_IMPORTE));
                    //cuenta bcp
                    cadenaHASH += UtilesSet.espaciosDerecha(numeroCuenta, Constantes.TAM_CUENTA_BCP);
                    escribir.append(UtilesSet.espaciosDerecha(numeroCuenta, Constantes.TAM_CUENTA_BCP));
                    //codigo hash
                    escribir.append(UtilesSet.getHashSet(cadenaHASH));
                    return escribir.toString();
                } else {
                    throw new Exception("No existen boletas de pago para la fecha solicitada o las gestiones " + "aun no fueron cerradas");
                }

            } catch (Exception e) {
                context.setRollbackOnly();
                e.printStackTrace();
                throw e;
            }
        }
    }

    public String generarArchivoClearing(Long idRed,
            boolean comision,
            boolean debito,
            List<Map<String, Object>> listaClearing, Corte corte) throws Exception {
        return this.generarArchivoClearing(idRed, comision, debito, listaClearing, false, corte);
    }

    public String generarArchivoClearing(Long idRed,
            boolean comision,
            boolean debito,
            List<Map<String, Object>> listaClearing,
            boolean esManual, Corte corte) throws Exception {
        GeneradorArchivoClearing generadorArchivoClearing = null;
        EntidadFinanciera ef = redF.get(idRed).getBancoClearing();
        if (ef.getIdEntidadFinanciera().equals(3L)) {
            //continental
            generadorArchivoClearing = new GeneradorContinental(comision, debito, listaClearing, esManual, corte);
        } else {
            throw new Exception("Generacion de archivo de clearing no soportado para "
                    + "el banco con identificador " + ef.getIdEntidadFinanciera()
                    + " - " + ef.getDescripcion());
        }
        return generadorArchivoClearing.obtenerTextoGenerado();
    }

    public String calcularVencimientoForm90(Integer numeroFormulario,
            Integer version, Integer numeroImpuesto, String ruc, String fecha, String fechaPresentacion)
            throws Exception {

        String respuesta = null;

        Integer anho, mes, dia;
        String mesCad, diaCad, mesAnho;

        Calendar calendarVencimiento = Calendar.getInstance();
        if (numeroImpuesto == 114) {
            calendarVencimiento.setTime(new SimpleDateFormat("ddMMyyyyzzz").parse(fecha + "PST"));
            calendarVencimiento = this.proximoVencimientoHabil(calendarVencimiento, 60);
        } else {
            if (numeroImpuesto == 115 || numeroImpuesto == 431 || numeroImpuesto == 461) {
                anho = Integer.parseInt(fecha.substring(4));
                mes = Integer.parseInt(fecha.substring(2, 4)) + 1;
                dia = Integer.parseInt(fecha.substring(0, 2));
                if (mes == 13) {
                    anho++;
                    mes = 1;
                }
                mesCad = ((("" + mes).length() == 1) ? "0" + mes : "" + mes);
                diaCad = ((("" + dia).length() == 1) ? "0" + dia : "" + dia);
                calendarVencimiento.setTime(new SimpleDateFormat("ddMMyyyyzzz").parse(diaCad + mesCad + anho + "PST"));
                calendarVencimiento = this.proximoDiaHabil(calendarVencimiento);
            } else {
                if (numeroImpuesto == 112) {

                    mesAnho = fecha.substring(2, 8);

                    respuesta = this.calcularVencimiento(numeroFormulario, version, numeroImpuesto, ruc, fechaPresentacion, mesAnho);
                    return respuesta;

                }
            }
        }

        Calendar calendarPresentacion = Calendar.getInstance();
        calendarPresentacion.setTime(new SimpleDateFormat("ddMMyyyyzzz").parse(fechaPresentacion.replaceAll("/", "") + "PST"));
        Long mesesAtrasos;
        Long diasAtrasos = (long) (((double) (calendarPresentacion.getTime().getTime() - calendarVencimiento.getTime().getTime() + 7200000) / (double) UN_DIA));
        if (diasAtrasos.intValue() <= 0) {
            diasAtrasos = 0L;
            mesesAtrasos = 0L;
        } else {
            if (diasAtrasos.intValue() % 30 == 0) {
                mesesAtrasos = diasAtrasos / 30;
            } else {
                mesesAtrasos = diasAtrasos / 30 + 1;
            }
        }

        respuesta = "" + new SimpleDateFormat("dd/MM/yyyy").format(calendarVencimiento.getTime()) + ":" + diasAtrasos + ":" + mesesAtrasos;
        return respuesta;
    }

    public String calcularVencimiento(Integer numeroFormulario,
            Integer version,
            Integer numeroImpuesto,
            String ruc,
            String fechaPresentacion,
            String periodoFiscal) throws Exception {

        return this.calcularVencimiento(numeroFormulario, version,
                numeroImpuesto, ruc, fechaPresentacion, null,
                periodoFiscal);
    }

    public String calcularVencimiento(Integer numeroFormulario,
            Integer version,
            Integer numeroImpuesto,
            String ruc,
            String fechaPresentacion,
            String fechaOperacion,
            String periodoFiscal) throws Exception {

        String respuesta = null;
        Formulario ejemploF = new Formulario();
        ejemploF.setNumeroFormulario(numeroFormulario);
        ejemploF.setVersion(version);
        Dominios dominio = new Dominios();
        dominio.setEtiquetaDetalle("PERIODOFISCAL");
        dominio.setFormulario(ejemploF);
        dominio = this.dominiosFacade.get(dominio);
        Contribuyentes cont = new Contribuyentes();
        cont.setRucNuevo(ruc);
        cont = this.contribuyentesFacade.get(cont);
        String periodo = periodoFiscal.replaceAll("/", "");
        ParamVencimientos pv = this.paramVencimientosFacade.obtenerParamVencimiento(numeroImpuesto, periodo, cont);
        Integer anho, mes, dia;
        if (dominio.getFormato().equals("MM/YYYY")) {
            anho = Integer.parseInt(periodo.substring(2));
            mes = Integer.parseInt(periodo.substring(0, 2)) + 1;
        } else if (dominio.getFormato().equals("YYYY")) {
            anho = Integer.parseInt(periodo) + 1;
            mes = pv.getMesesPlazo() + 1;
        } else {
            //algun dia, vencimiento diario
            throw new Exception("Vencimiento diario no soportado");
        }
        if (mes == 13) {
            anho++;
            mes = 1;
        }
        dia = pv.getPlazoDeclaracion();
        Calendar calendarVencimiento = Calendar.getInstance();
        String mesCad, diaCad;
        if (pv.getUnidadTiempo().endsWith("DIA")) {
            mesCad = ((("" + mes).length() == 1) ? "0" + mes : "" + mes);
            diaCad = ((("" + dia).length() == 1) ? "0" + dia : "" + dia);
            calendarVencimiento.setTime(new SimpleDateFormat("ddMMyyyyzzz").parse(diaCad + mesCad + anho + "PST"));
            calendarVencimiento = this.proximoDiaHabil(calendarVencimiento);
        } else {
            mesCad = ((("" + mes).length() == 1) ? "0" + mes : "" + mes);
            diaCad = "01";
            calendarVencimiento.setTime(new SimpleDateFormat("ddMMyyyyzzz").parse(diaCad + mesCad + anho + "PST"));
            calendarVencimiento = this.proximoVencimientoHabil(calendarVencimiento, pv.getPlazoDeclaracion());
        }
        Calendar calendarPresentacion = Calendar.getInstance();
        calendarPresentacion.setTime(new SimpleDateFormat("ddMMyyyyzzz").parse(fechaPresentacion.replaceAll("/", "") + "PST"));
        Long mesesAtrasos;
        Long diasAtrasos = (long) (((double) (calendarPresentacion.getTime().getTime() - calendarVencimiento.getTime().getTime() + 7200000) / (double) UN_DIA));
        if (diasAtrasos.intValue() <= 0) {
            diasAtrasos = 0L;
            mesesAtrasos = 0L;
        } else {
            if (diasAtrasos.intValue() % 30 == 0) {
                mesesAtrasos = diasAtrasos / 30;
            } else {
                mesesAtrasos = diasAtrasos / 30 + 1;
            }
        }
        respuesta = "" + new SimpleDateFormat("dd/MM/yyyy").format(calendarVencimiento.getTime()) + ":" + diasAtrasos + ":" + mesesAtrasos;
        return respuesta;
    }

    public Calendar proximoDiaHabil(Calendar calendar) throws Exception {
        Calendar retorno = Calendar.getInstance();
        retorno.setTime(calendar.getTime());
        int anho = retorno.get(Calendar.YEAR);
        int mes = retorno.get(Calendar.MONTH) + 1;
        int dia = retorno.get(Calendar.DATE);
        while (!this.feriadosFacade.esDiaHabil(anho, mes, dia)) {
            retorno.add(Calendar.DATE, 1);
            anho = retorno.get(Calendar.YEAR);
            mes = retorno.get(Calendar.MONTH) + 1;
            dia = retorno.get(Calendar.DATE);
        }
        return retorno;
    }

    public Calendar diaHabilSiguiente(Calendar calendar, int dias) throws Exception {
        Calendar retorno = Calendar.getInstance();
        retorno.setTime(calendar.getTime());
        int variacion = 1;

        if (dias < 0) {
            variacion = -1;
            dias = -dias;
        }

        do {
            retorno.add(Calendar.DATE, variacion);
            int anho = retorno.get(Calendar.YEAR);
            int mes = retorno.get(Calendar.MONTH) + 1;
            int dia = retorno.get(Calendar.DATE);
            if (this.feriadosFacade.esDiaHabil(anho, mes, dia)) {
                dias--;
            }
        } while (dias != 0);

        return retorno;
    }

    public java.util.Calendar proximoVencimientoHabil(Calendar calendar, int plazo) throws Exception {
        int anho, mes, dia;
        int contadorDias = 0;
        boolean terminarConteo = false;
        while (!terminarConteo) {
            anho = calendar.get(Calendar.YEAR);
            mes = calendar.get(Calendar.MONTH) + 1;
            dia = calendar.get(Calendar.DATE);
            if (this.feriadosFacade.esDiaHabil(anho, mes, dia)) {
                contadorDias++;
                if (contadorDias == plazo) {
                    terminarConteo = true;
                } else {
                    calendar.add(Calendar.DATE, 1);
                }
            } else {
                calendar.add(Calendar.DATE, 1);
            }
        }
        return calendar;
    }

    public String validarPeriodoFiscal(String opcion,
            Integer numeroFormulario,
            Integer version,
            String periodoFiscal,
            String fechaPresentacion,
            Integer numeroImpuesto) throws Exception {
        Formulario formulario = null;
        String periodo;
        if (periodoFiscal != null) {
            periodo = periodoFiscal.replaceAll("/", "");
        } else {
            periodo = null;
        }

        if (opcion.equals("BP")) {
            if (periodo == null) {
                return "VALIDO";
            } else {
                //veremos si tiene formulario este impuesto
                FormularioImpuesto fImpuesto = new FormularioImpuesto();
                fImpuesto.setImpuesto(numeroImpuesto);
                if (this.fiFacade.total(fImpuesto) == 0) {
                    return "VALIDO";
                }
                try {
                    formulario = this.formularioFacade.obtenerFormulario(numeroImpuesto, periodo);
                } catch (Exception ex) {
                    return "INVALIDO";
                }
                numeroFormulario = formulario.getNumeroFormulario();
                version = formulario.getVersion();
            }
        }
        String presentacion = fechaPresentacion.replaceAll("/", "");
        String respuesta = null;
        FormularioImpuesto fi = new FormularioImpuesto();
        fi.setNumeroFormulario(numeroFormulario);
        fi = this.fiFacade.get(fi);
        ParamVencimientos pv = new ParamVencimientos();
        pv.setImpuesto(fi.getImpuesto());
        pv = this.paramVencimientosFacade.list(pv).get(0);

        if (pv.getDescripcion().equalsIgnoreCase("SEMESTRAL") && !periodo.substring(0, 2).equalsIgnoreCase("06") && !periodo.substring(0, 2).equalsIgnoreCase("12")) {
            return "INVALIDO";
        } else if (pv.getDescripcion().equalsIgnoreCase("CUATRIMESTRAL") && !periodo.substring(0, 2).equalsIgnoreCase("04") && !periodo.substring(0, 2).equalsIgnoreCase("08") && !periodo.substring(0, 2).equalsIgnoreCase("12")) {
            return "INVALIDO";
        } else if (pv.getDescripcion().equalsIgnoreCase("MENSUAL") && periodo.length() != 6) {
            return "INVALIDO";
        }
        if (formulario == null) {
            formulario = new Formulario();
            formulario.setNumeroFormulario(numeroFormulario);
            formulario.setVersion(version);
            formulario = this.formularioFacade.get(formulario);
        }
        Dominios dominio = new Dominios();
        dominio.setEtiquetaDetalle("PERIODOFISCAL");
        dominio.setFormulario(formulario);
        dominio = this.dominiosFacade.get(dominio);
        String formDesde, formHasta;
        formDesde = new SimpleDateFormat("yyyyMMdd").format(formulario.getFechaDesde());
        if (formulario.getFechaHasta() != null) {
            formHasta = new SimpleDateFormat("yyyyMMdd").format(formulario.getFechaHasta());
        } else {
            formHasta = null;
        }
        String anhoPeriodo, mesPeriodo, mesPresentacion, anhoPresentacion;
        anhoPresentacion = presentacion.substring(4);
        mesPresentacion = presentacion.substring(2, 4);
        if (dominio.getFormato().equalsIgnoreCase("MM/YYYY")) {
            anhoPeriodo = periodo.substring(2);
            mesPeriodo = periodo.substring(0, 2);

            Integer mp = new Integer(mesPeriodo);
            Integer ap = new Integer(anhoPeriodo);
            Integer ar = new Integer(anhoPresentacion);
            if (mp < 1 || mp > 12) {//********************************debe validar ademas que sea valido para la epoca del año
                return "INVALIDO";
                //throw new Exception("Mes no válido");
            }
            if (ap > ar) {
                return "INVALIDO";
            }
            if (Integer.parseInt(formDesde.substring(0, 6)) <= Integer.parseInt(anhoPeriodo + mesPeriodo) && ((formHasta != null && Integer.parseInt(formHasta.substring(0, 6)) >= Integer.parseInt(anhoPeriodo + mesPeriodo)) || formHasta == null)) {
                if ((opcion.equals("DJ") && Integer.parseInt(anhoPresentacion + mesPresentacion) > Integer.parseInt(anhoPeriodo + mesPeriodo)) || (opcion.equals("DJCC") && Integer.parseInt(anhoPresentacion + mesPresentacion) >= Integer.parseInt(anhoPeriodo + mesPeriodo)) || (opcion.equals("DJRC") && Integer.parseInt(anhoPresentacion + mesPresentacion) >= Integer.parseInt(anhoPeriodo + mesPeriodo)) || (opcion.equals("BP") && Integer.parseInt(anhoPresentacion + mesPresentacion) > Integer.parseInt(anhoPeriodo + mesPeriodo))) {
                    respuesta = "VALIDO";
                } else {
                    respuesta = "INVALIDO";
                }
            } else {
                respuesta = "INVALIDO";
            }
        } else if (dominio.getFormato().equalsIgnoreCase("YYYY")) {
            anhoPeriodo = "" + periodo;
            //if ((opcion.equals("DJ") && Integer.parseInt(anhoPresentacion) > Integer.parseInt(anhoPeriodo)
            if (Integer.parseInt(formDesde.substring(0, 4)) <= Integer.parseInt(anhoPeriodo) && ((formHasta != null && Integer.parseInt(formHasta.substring(0, 4)) >= Integer.parseInt(anhoPeriodo)) || formHasta == null)) {
                if ((opcion.equals("DJ") && Integer.parseInt(anhoPresentacion) >= Integer.parseInt(anhoPeriodo)) || (opcion.equals("DJCC") && Integer.parseInt(anhoPresentacion) >= Integer.parseInt(anhoPeriodo)) || (opcion.equals("DJRC") && Integer.parseInt(anhoPresentacion) >= Integer.parseInt(anhoPeriodo)) || (opcion.equals("BP") && Integer.parseInt(anhoPresentacion) > Integer.parseInt(anhoPeriodo))) {
                    respuesta = "VALIDO";
                } else {
                    respuesta = "INVALIDO";
                }
            } else {
                respuesta = "INVALIDO";
            }
        } else {
            throw new Exception("Vencimiento diario no implementado");
        }
        return respuesta;
    }
    static final long UN_DIA = 24 * 60 * 60 * 1000L;
}
