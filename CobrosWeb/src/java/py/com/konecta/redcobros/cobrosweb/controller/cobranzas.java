/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import py.com.konecta.redcobros.ejb.FormContribFacade;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.ejb.core.Respuesta;
import py.com.konecta.redcobros.entities.*;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
public class cobranzas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade; //Agregado por Ystmio Gaona 12/09/2014
    @EJB
    private GestionFacade gestionFacade;
    @EJB
    FormContribFacade formContribFacade;
    @EJB
    TransaccionFacade transaccionFacade;
    @EJB
    ContribuyentesFacade contribuyentesFacade;
    @EJB
    ImpuestoFacade impuestoFacade;
    @EJB
    FormularioImpuestoFacade formularioImpuestoFacade;
    @EJB
    UsuarioFacade usuarioFacade;
    @EJB
    RecaudadorFormularioFacade recaudadorFormularioFacade;
    @EJB
    private UtilFacade utilFacade;
    @EJB
    private RegistroImpWebFacade registroImpWebFacade;
    private static final Logger logger = Logger.getLogger(cobranzas.class.getName());

    private void saveRegister(RegistroImpWeb registro) {
        try {
            registroImpWebFacade.save(registro);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
//    @EJB
//    private ParametroSistemaFacade parametroSistemaFacade;

//    private void writeOnUserFile(String pathFile, Long codExtUsuario, Long idTrx, Boolean indicador) {
//        Transaccion transaccion = transaccionFacade.get(idTrx);
//        try {
//            Utiles.writeToFile(pathFile, codExtUsuario, transaccion, indicador);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IllegalArgumentException, JAXBException, DatatypeConfigurationException, Exception {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Long idRecaudador = (Long) session.getAttribute("idRecaudador");

        String op = request.getParameter("op");
        JSONObject propiedad = new JSONObject();

        JSONObject json = new JSONObject();
        JSONArray arrayJson = new JSONArray();
        JSONObject jsonFinal = new JSONObject();

        if (op.equalsIgnoreCase("DETALLE_COBRO_CON_FORMULARIO")) {
            if ((request.getParameter("idFormContrib") != null) && (request.getParameter("idFormContrib").matches("[0-9]+"))) {

                //FormContrib formContrib = formContribFacade.get(Integer.parseInt(request.getParameter("idFormContrib")));
                FormContrib formContrib = new FormContrib();
                formContrib.setIdFormContrib(new Long(request.getParameter("idFormContrib")));
                Map<String, Object> mapaFC = this.formContribFacade.get(formContrib,
                        new String[]{"periodoFiscal", "ruc",
                            "digitoVerificador", "totalPagar",
                            "formulario.idFormulario",
                            "formulario.numeroFormulario"});
                formContrib.setPeriodoFiscal((String) mapaFC.get("periodoFiscal"));
                formContrib.setRuc((String) mapaFC.get("ruc"));
                formContrib.setDigitoVerificador((String) mapaFC.get("digitoVerificador"));
                formContrib.setTotalPagar((Integer) mapaFC.get("totalPagar"));
                formContrib.setFormulario(new Formulario());
                formContrib.getFormulario().setIdFormulario((Long) mapaFC.get("formulario.idFormulario"));
                formContrib.getFormulario().setNumeroFormulario((Integer) mapaFC.get("formulario.numeroFormulario"));

                RecaudadorFormulario recForm = recaudadorFormularioFacade.get(idRecaudador);

                if (recForm != null) {
                    if (formContrib != null) {
                        propiedad.put("periodoFiscal", formContrib.getPeriodoFiscal());
                        propiedad.put("ruc", formContrib.getRuc());
                        propiedad.put("dv", formContrib.getDigitoVerificador());
                        propiedad.put("totalPagar", UtilesSet.formatearNumerosSeparadorMiles(formContrib.getTotalPagar(), false));
                        if (formContrib.getFormulario() != null) {
                            propiedad.put("numero_formulario", formContrib.getFormulario().getNumeroFormulario());
                        }
                        propiedad.put("montoDeclarado", UtilesSet.formatearNumerosSeparadorMiles(formContrib.getTotalPagar(), false));
                        propiedad.put("montoFormulario", NumberFormat.getInstance().format(recForm.getMonto()));
                        jsonFinal.put("success", true);
                        jsonFinal.put("data", propiedad);

                    } else {
                        jsonFinal.put("motivo", "No se ha encontrado la referencia seleccionada");
                        jsonFinal.put("success", false);
                    }
                } else {
                    jsonFinal.put("motivo", "Monto Recaudador Formulario Faltante");
                    jsonFinal.put("success", false);
                }
            } else {
                jsonFinal.put("motivo", "Los Parametros no son correctos");
                jsonFinal.put("success", false);
            }
            out.println(jsonFinal.toString());

        } else if (op.equalsIgnoreCase("COBRO_CON_BOLETA_DE_PAGO")) {

            //HttpSession session = request.getSession();
            boolean hasCodSucSET = (Boolean) session.getAttribute("hasCodSucSET");
            boolean hasCodCajaSET = (Boolean) session.getAttribute("hasCodCajaSET");

            if (hasCodSucSET) {
                if (hasCodCajaSET) {
                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "La Terminal no tiene Codigo Caja SET para operar. Favor llamar a Documenta.");
                    out.println(jsonFinal);
                    return;
                }
            } else {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", "La Sucursal no tiene Codigo Sucursal SET para operar. Favor llamar a Documenta.");
                out.println(jsonFinal);
                return;
            }

            Integer numeroCheque = 0;
            Long idEntidadFinanciera = -1L;
            Boolean validado = false;
            String fechaCheque = request.getParameter("FECHA_CHEQUE") != null && !request.getParameter("FECHA_CHEQUE").isEmpty() ? request.getParameter("FECHA_CHEQUE") : null;
            if (request.getParameter("ENTIDAD_FINANCIERA") != null && !request.getParameter("ENTIDAD_FINANCIERA").isEmpty() && request.getParameter("nro_cheque") != null && !request.getParameter("nro_cheque").isEmpty()) {
                idEntidadFinanciera = new Long(request.getParameter("ENTIDAD_FINANCIERA"));
                numeroCheque = Integer.parseInt(request.getParameter("nro_cheque"));
                validado = true;

                if (fechaCheque != null) {
                    Calendar cal = Utiles.getFechaVencimiento(fechaCheque);
                    Long diasCheque = cal.getTimeInMillis();
                    Long diasReales = new Date().getTime();
                    if (diasReales < diasCheque) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Fecha de cheque mayor a fecha de presentación.");
                        out.println(jsonFinal.toString());
                        return;
                    } else {
                        diasCheque = cal.getTimeInMillis() + Utiles.toMillisecond("25");
                        if (diasReales > diasCheque) {
                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", "Fecha De Cheque Vencido.");
                            out.println(jsonFinal.toString());
                            return;
                        }
                    }
                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "No se ingreso fecha de cheque.");
                    out.println(jsonFinal.toString());
                    return;
                }
            } else if ((request.getParameter("ENTIDAD_FINANCIERA") == null || (request.getParameter("ENTIDAD_FINANCIERA") != null && request.getParameter("ENTIDAD_FINANCIERA").isEmpty())) && (request.getParameter("nro_cheque") == null || (request.getParameter("nro_cheque") != null && request.getParameter("nro_cheque").isEmpty()))) {
                validado = true;
            }

            String monto = request.getParameter("monto");
            if (monto != null) {
                monto = monto.replaceAll(",", "");
            }
            if (validado && (monto != null) && (monto.matches("[0-9]+")) && Long.parseLong(monto) > 0 && (request.getParameter("IMPUESTO") != null) && (request.getParameter("dv") != null) && (request.getParameter("ruc") != null)) {

                Contribuyentes contribuyente = contribuyentesFacade.getContribuyentePorRuc(request.getParameter("ruc"), request.getParameter("ruc"), request.getParameter("dv"));

                if (contribuyente == null) {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "No existe el Contribuyente");
                    validado = false;
                } else {
                    if (contribuyente.getModalidadContribuyente() == 2 || contribuyente.getModalidadContribuyente() == 3) {
                        validado = true;
                    } else {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "No se permite la modalidad Gran Contribuyente");
                        validado = false;
                    }

                    if (!contribuyente.getDigitoVerificador().equals(request.getParameter("dv"))) {
                        validado = false;
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Digito verificador debe ser " + contribuyente.getDigitoVerificador());
                    }
                }

                if (validado) {
                    String periodoFiscal = request.getParameter("periodo_fiscal");
                    if (!periodoFiscal.matches("[0-9]+")) {
                        periodoFiscal = null;
                    }

                    String identificadorImpuesto = request.getParameter("IMPUESTO");
                    Impuesto impuesto = null;
                    if (identificadorImpuesto == null) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Se requiere el numero de impuesto");
                        validado = false;
                    } else {
                        impuesto = impuestoFacade.get(new Long(identificadorImpuesto));
                        String res = this.utilFacade.validarPeriodoFiscal("BP", null, null, periodoFiscal, new SimpleDateFormat("ddMMyyyy").format(new Date()), impuesto.getNumero());
                        if (res.equalsIgnoreCase("VALIDO")) {
                            validado = true;
                        } else {
                            validado = false;
                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", "El periodo Fiscal no es valido.");
                        }
//                        if (periodoFiscal != null && !periodoFiscal.isEmpty()) {
//
//                            if (impuesto != null && impuesto.getNumero().intValue() == 211 && periodoFiscal.length() == 6 && periodoFiscal.subSequence(0, 2).toString().matches("[0-9]+") && periodoFiscal.subSequence(2, periodoFiscal.length()).toString().matches("[0-9]+")) {
//                                validado = true;
//                                /***** Regla segun articulo   System.out.println("VALIDDADO: " + validado); 162 de la ley 125/91 ******/
//                                if ((Integer.parseInt(periodoFiscal.subSequence(2, periodoFiscal.length()).toString()) < 2007) || ((Integer.parseInt(periodoFiscal.subSequence(2, periodoFiscal.length()).toString()) == 2007) && (Integer.parseInt(periodoFiscal.subSequence(0, 2).toString()) == 1))) {
//                                    registrarPeriodoFiscal = true;
//                                } else {
//                                    registrarPeriodoFiscal = false;
//                                }
//                                periodoFiscal = (String) periodoFiscal.subSequence(0, 2) + "/" + periodoFiscal.subSequence(2, periodoFiscal.length());
//                            } else if (impuesto != null && impuesto.getNumero().intValue() == 111 && periodoFiscal.length() != 4) {
//
//                                jsonFinal.put("success", false);
//                                jsonFinal.put("motivo", "El periodo Fiscal no es valido. Para el impuesto 111 debe llenar en formato yyyy");
//                                validado = false;
//
//                            } else if (impuesto != null && impuesto.getNumero().intValue() == 111 && periodoFiscal.length() == 4 && periodoFiscal.matches("[0-9]+")) {
//                                validado = true;
//                                /***** Regla segun articulo 162 de la ley 125/91 ******/
//                                if ((Integer.parseInt(periodoFiscal) <= 2007)) {
//                                    registrarPeriodoFiscal = true;
//                                } else {
//                                    registrarPeriodoFiscal = false;
//                                }
//                                periodoFiscal = (String) periodoFiscal.subSequence(0, 2) + "/" + periodoFiscal.subSequence(2, periodoFiscal.length());
//                            } else {
//                                jsonFinal.put("success", false);
//                                jsonFinal.put("motivo", "El periodo Fiscal no es valido. No existe formato definido para el codigo de Impuesto seleccionado");
//                                validado = false;
//                            }
//                        }
                    }

                    if (validado) {
                        if (impuesto != null) {
                            if (impuesto.getTipoAtributo().equalsIgnoreCase("RESOLUCION")) {
                                if ((request.getParameter("nro_resolucion") != null) && !(request.getParameter("nro_resolucion").isEmpty())) {
                                    if (!(request.getParameter("nro_resolucion").matches("[1-9][0-9]+"))) {
                                        jsonFinal.put("motivo", "El Número de Resolución no es válido");
                                        jsonFinal.put("success", false);
                                        validado = false;
                                    } else {
                                        validado = true;
                                    }
                                } else {
                                    jsonFinal.put("motivo", "Debe introducir el Numero de Resolucion");
                                    jsonFinal.put("success", false);
                                    validado = false;
                                }

                            } else if (impuesto.getTipoAtributo().equalsIgnoreCase("DETERMINATIVO")) {
                                validado = true;

                            } else {
                                jsonFinal.put("motivo", "Solo Resolucion y Determinativo");
                                jsonFinal.put("success", false);
                                validado = false;
                            }

                        } else {

                            jsonFinal.put("motivo", "No se existe el codigo de impuesto seleccionado");
                            jsonFinal.put("success", false);
                            validado = false;
                        }
                    }

                    if (validado) {

                        Long idRed = (Long) request.getSession().getAttribute("idRed");
                        //Long idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
                        Long idSucursal = (Long) request.getSession().getAttribute("idSucursal");
                        //  Integer idTipoTerminal = (Integer) request.getSession().getAttribute("idTipoTerminal");
                        String idTerminal = (Long) request.getSession().getAttribute("idTerminal") + "";
                        String idUsuario = (Long) request.getSession().getAttribute("idUsuario") + "";
                        String user = (String) request.getSession().getAttribute("user");
                        String contrasenha = (String) request.getSession().getAttribute("contrasenha");
                        Date fechaHoraRed = new Date();
                        Date fechaHoraTerminal = new Date();
                        Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                        Long idImpuesto = new Long(request.getParameter("IMPUESTO"));
                        Long nro_resolucion = 0L;
                        Long era = ((Integer) (request.getSession().getAttribute("cod_era"))).longValue();
                        Long idFormulario = 0L;
                        Long versionFormulario = 0L;

                        if (request.getParameter("nro_resolucion") != null) {
                            nro_resolucion = Long.parseLong(request.getParameter("nro_resolucion"));
                        }

                        Respuesta respuesta = transaccionFacade.doTransacctionSetBoletaPago(0L, idRed, idRecaudador, idSucursal, idTerminal, idUsuario, user, contrasenha, formatter.format(fechaHoraRed), formatter.format(fechaHoraTerminal), new Long(request.getParameter("idTipoPago")), 1.0, 600L, new Double(monto), request.getParameter("ruc"), request.getParameter("dv"), periodoFiscal, idImpuesto, idFormulario, versionFormulario, era, nro_resolucion, numeroCheque, idEntidadFinanciera, fechaCheque, request.getSession());

                        if (respuesta.getIdRespuesta() == 0) {
                            Transaccion transa = transaccionFacade.get(respuesta.getIdTransaccion());
                            //writeOnUserFile(pathFile, codExtUsuario, respuesta.getIdTransaccion(), false);
                            String respuesta2 = UtilesSet.generarTicketTransa(transa).replaceAll("\n\n", ";;;");
                            String[] respuestaComponentes = respuesta2.split(";;;");

                            String cadenaImpresionTicket = "";
                            String cadenaImpresionCertificacion = "";
                            Integer modo = 1;//modo Rollo
                            String ticket_pantalla = "";

                            //Ticket
                            for (String ooo : respuestaComponentes) {

                                cadenaImpresionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
                                ticket_pantalla += ooo + "<br/>";
                            }
                            //Certificacion
                            respuesta2 = UtilesSet.generarCertificacionBoletaPagoTransa(transa, era.intValue()).replaceAll("\n\n", ";;;");
                            respuestaComponentes = respuesta2.split(";;;");
                            modo = 2;
                            for (String ooo : respuestaComponentes) {

                                cadenaImpresionCertificacion += modo + ";;" + "N" + ";;" + ooo + ";;;";
                                ticket_pantalla += ooo + "<br/>";
                            }
                            jsonFinal.put("ticket_pantalla", ticket_pantalla);
                            jsonFinal.put("ticket", cadenaImpresionTicket);
                            jsonFinal.put("certificacion", cadenaImpresionCertificacion);
                            jsonFinal.put("success", true);

                        } else {
                            jsonFinal.put("ticket", "");
                            jsonFinal.put("motivo", respuesta.getDescRespuesta());
                            jsonFinal.put("success", false);

                        }

                    }
                }

            } else {
                if (!(request.getParameter("monto").matches("[1-9][0-9]+"))) {
                    jsonFinal.put("motivo", "El monto no es valido");
                    jsonFinal.put("success", false);
                } else {
                    jsonFinal.put("motivo", "Los parametros no son correctos");
                    jsonFinal.put("success", false);
                }

            }

            out.println(jsonFinal.toString());

        } else if (op.equalsIgnoreCase("ANULAR_TRANSACCION")) {
            // if (request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (request.getSession().getAttribute("contrasenha").equals(inicial.getSha1(request.getParameter("PASSWORD"))))) {
            if (request.getParameter("TRANSACCION") != null && !request.getParameter("TRANSACCION").isEmpty() && usuarioFacade.esSupervisor((Long) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                try {

                    if (gestionFacade.tieneGestionesAbiertas(((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")), new Date())) {
                        transaccionFacade.anularTransaccion(new Long(request.getParameter("TRANSACCION")), ((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")).getIdUsuarioTerminal());
                        Transaccion transa = new Transaccion();
                        transa.setIdTransaccion(new Long(request.getParameter("TRANSACCION")));
                        List<Transaccion> lTransaccion = transaccionFacade.list(transa);

                        if (lTransaccion.size() == 1) {
                            //componentesImpresion =  " A N U L A C I O N \n\n";
                            //writeOnUserFile(pathFile, codExtUsuario, lTransaccion.get(0).getIdTransaccion(), true);
                            String objetoImpresion = UtilesSet.generarTicketTransa(lTransaccion.get(0));
                            objetoImpresion = "              A N U L A C I O N         \n\n" + objetoImpresion;
                            Map<String, String> componentesImpresion = inicial.getComponentesImpresion(objetoImpresion);

                            jsonFinal.put("ticket_pantalla", componentesImpresion.get("ticket_pantalla"));
                            jsonFinal.put("ticket", componentesImpresion.get("cadena_impresion"));
                            jsonFinal.put("transaccionAnulada", request.getParameter("TRANSACCION"));
                            jsonFinal.put("success", true);

                            String motivoAnulacion = request.getParameter("motivoAnulacion") != null && !request.getParameter("motivoAnulacion").isEmpty() ? request.getParameter("motivoAnulacion") : "Sin motivo";
                            try {
                                //HttpSession session = request.getSession();
                                Long usuario = (Long) session.getAttribute("idUsuario");
                                Long idTerminal = (Long) session.getAttribute("idTerminal");
                                RegistroImpWeb registro = new RegistroImpWeb();
                                registro.setDescripcion("Se anulo la transaccion " + lTransaccion.get(0).getIdTransaccion() + " Nro. Orden[" + lTransaccion.get(0).getBoletaPagoContrib().getNumeroOrden() + "]");
                                registro.setMotivo(motivoAnulacion);
                                registro.setEvento("ANULACION");
                                registro.setFecha(new Date());
                                registro.setUsuario(usuario.toString());
                                registro.setTerminal(idTerminal);
                                this.saveRegister(registro);
                            } catch (Exception ex) {
                                Logger.getLogger(GuardarFormulario.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {

                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", "Existe más de una coincidencia");
                        }
                    } else {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Debe Abrir la Caja para realizar la operación");
                    }

                } catch (Exception e) {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", e.getMessage());
                }

            } else {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", Utiles.CLAVE_INCORRECTA);
            }

            out.println(jsonFinal);

        } else if (op.equalsIgnoreCase("DETALLE_TRANSA_A_ANULAR")) {
            //if (request.getParameter("TRANSACCION") != null && !request.getParameter("TRANSACCION").isEmpty() && request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (request.getSession().getAttribute("contrasenha").equals(inicial.getSha1(request.getParameter("PASSWORD"))))) {
            if (request.getParameter("TRANSACCION") != null && !request.getParameter("TRANSACCION").isEmpty() && usuarioFacade.esSupervisor((Long) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                try {

                    Transaccion transa = new Transaccion();
                    transa.setIdTransaccion(new Long(request.getParameter("TRANSACCION")));
                    List<Transaccion> lTransaccion = transaccionFacade.list(transa);
                    if (gestionFacade.tieneGestionesAbiertas(((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")), new Date())) {
                        if ((lTransaccion.size() == 1)) {
                            if (lTransaccion.get(0).getMigrado() == null || lTransaccion.get(0).getMigrado() != 'S') {
                                if (!(lTransaccion.get(0).getFlagAnulado().equalsIgnoreCase("S"))) {
                                    Map<String, String> componentesDeImpresion = UtilesSet.getDetalleTransaccionAnular(lTransaccion.get(0));

                                    jsonFinal.put("ticket_pantalla", componentesDeImpresion.get("ticket_pantalla"));
                                    jsonFinal.put("ticket", componentesDeImpresion.get("ticket"));
                                    jsonFinal.put("certificacion", componentesDeImpresion.get("certificacion"));
                                    jsonFinal.put("success", true);

                                } else {
                                    jsonFinal.put("success", false);
                                    jsonFinal.put("motivo", "La Transaccion ya esta Anulada.");
                                }
                            } else {
                                jsonFinal.put("success", false);
                                jsonFinal.put("motivo", "No se puede anular, operacion ya migrada ");
                            }

                        } else if (lTransaccion.isEmpty()) {
                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", "No existe la Transaccion: " + request.getParameter("TRANSACCION"));
                        }
                    } else {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Debe Abrir la Caja para realizar la operacion");
                    }

                } catch (Exception e) {
                    logger.log(Level.SEVERE, null, e);
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", e.getMessage());
                }

            } else {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", Utiles.CLAVE_INCORRECTA);
            }

            out.println(jsonFinal);

        } else if (op.equalsIgnoreCase("REIMPRIMIR")) {
            // if (request.getParameter("TRANSACCION") != null && !request.getParameter("TRANSACCION").isEmpty() && request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (request.getSession().getAttribute("contrasenha").equals(inicial.getSha1(request.getParameter("PASSWORD"))))) {
            if (request.getParameter("TRANSACCION") != null && !request.getParameter("TRANSACCION").isEmpty() && usuarioFacade.esSupervisor((Long) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                try {
                    Transaccion transa = new Transaccion();
                    transa.setIdTransaccion(new Long(request.getParameter("TRANSACCION")));
                    List<Transaccion> lTransaccion = transaccionFacade.list(transa);

                    if (lTransaccion.size() == 1) {
                        Map<String, String> componentesImpresion = UtilesSet.getReimpresionTicketCertificacion(lTransaccion.get(0),
                                (Integer) (request.getSession().getAttribute("cod_era")));

                        jsonFinal.put("ticket_pantalla", componentesImpresion.get("ticket_pantalla"));
                        jsonFinal.put("ticket", componentesImpresion.get("ticket"));
                        jsonFinal.put("certificacion_boleta", componentesImpresion.get("certificacion_boleta"));
                        jsonFinal.put("certificacion_ticket", componentesImpresion.get("certificacion_ticket"));
                        jsonFinal.put("success", true);

                    } else {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "No se pudo realizar la operacion.");
                    }

                } catch (Exception e) {
                    logger.log(Level.SEVERE, null, e);
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", e.getMessage());
                }

            } else {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", Utiles.CLAVE_INCORRECTA);
            }

            out.println(jsonFinal);

        } else if (op.equalsIgnoreCase("REIMPRIMIR_FORMULARIO")) {
            // if (request.getParameter("TRANSACCION") != null && !request.getParameter("TRANSACCION").isEmpty() && request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (request.getSession().getAttribute("contrasenha").equals(inicial.getSha1(request.getParameter("PASSWORD"))))) {
            if (request.getParameter("FORMULARIO") != null && !request.getParameter("FORMULARIO").isEmpty()
                    && usuarioFacade.esSupervisor((Long) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                try {

                    Map<String, String> resultado = UtilesSet.getCertificacionFormulario(Long.parseLong(request.getParameter("FORMULARIO")));

                    if (resultado != null) {
                        jsonFinal.put("pantalla", resultado.get("pantalla"));
                        jsonFinal.put("certificacion", resultado.get("certificacion"));
                        jsonFinal.put("success", true);

                    } else {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "No se pudo realizar la operacion.");
                    }

                } catch (Exception e) {
                    logger.log(Level.SEVERE, null, e);
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", e.getMessage());
                }

            } else {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", Utiles.CLAVE_INCORRECTA);
            }

            out.println(jsonFinal);

        } else if (op.equalsIgnoreCase("COBRO_CON_FORMULARIO")) {
            logger.info("Iniciando Cobro Con Formulario");
            out.println(CertificarFormulario.certificarFormulario(request));

            /* Integer numeroCheque = 0;
             Long idEntidadFinanciera = -1L;
             Boolean validado = false;
             String fechaCheque = request.getParameter("FECHA_CHEQUE") != null && !request.getParameter("FECHA_CHEQUE").isEmpty() ? request.getParameter("FECHA_CHEQUE") : null;

             if (request.getParameter("ENTIDAD_FINANCIERA") != null
             && !request.getParameter("ENTIDAD_FINANCIERA").isEmpty()
             && request.getParameter("nro_cheque") != null
             && !request.getParameter("nro_cheque").isEmpty()) {
             idEntidadFinanciera = new Long(request.getParameter("ENTIDAD_FINANCIERA"));
             numeroCheque = Integer.parseInt(request.getParameter("nro_cheque"));
             validado = true;
             Calendar cal = Utiles.getFechaVencimiento(fechaCheque);
             Long diasCheque = cal.getTimeInMillis();
             Long diasReales = new Date().getTime();
             if (diasReales < diasCheque) {
             logger.info("Fecha de cheque mayor a fecha de presentación.");
             jsonFinal.put("success", false);
             jsonFinal.put("motivo", "Fecha de cheque mayor a fecha de presentación.");
             out.println(jsonFinal.toString());
             return;
             } else {
             diasCheque = cal.getTimeInMillis() + Utiles.toMillisecond("25");
             if (diasReales > diasCheque) {
             logger.info("Fecha de Cheque Vencido");
             jsonFinal.put("success", false);
             jsonFinal.put("motivo", "Fecha De Cheque Vencido.");
             out.println(jsonFinal.toString());
             return;
             }
             }
             } else if ((request.getParameter("ENTIDAD_FINANCIERA") == null
             || (request.getParameter("ENTIDAD_FINANCIERA") != null
             && request.getParameter("ENTIDAD_FINANCIERA").isEmpty()))
             && (request.getParameter("nro_cheque") == null
             || (request.getParameter("nro_cheque") != null
             && request.getParameter("nro_cheque").isEmpty()))) {
             validado = true;
             }

             if (validado && (request.getParameter("idTipoPago") != null) && (request.getParameter("idTipoPago").matches("[0-9]+")) && (request.getParameter("idFormContrib") != null) && (request.getParameter("idFormContrib").matches("[0-9]+"))) {
             logger.info("Iniciando proceso de pago");
             Double montoPagar = 0.0;
             if (request.getParameter("monto") != null && !request.getParameter("monto").isEmpty()) {
             montoPagar = Double.parseDouble(request.getParameter("monto").replaceAll(",", ""));
             }
             logger.info("monto a pagar");
             Long idRed = (Long) request.getSession().getAttribute("idRed");
             //Long idRecaudador = (Long)
             request.getSession().getAttribute("idRecaudador");
              Long era = ((Integer) (request.getSession().getAttribute("cod_era"))).longValue();
             Long idSucursal = (Long) request.getSession().getAttribute("idSucursal");
             String idTerminal = (Long) request.getSession().getAttribute("idTerminal") + "";
             String idUsuario = (Long) request.getSession().getAttribute("idUsuario") + "";
             String user = (String) request.getSession().getAttribute("user");
             String contrasenha = (String) request.getSession().getAttribute("contrasenha");
             Date fechaHoraRed = new Date();
             Date fechaHoraTerminal = new Date();//traer por javascript 
             Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
             Long era = ((Integer) (request.getSession().getAttribute("cod_era"))).longValue();
             Long idFormContrib = new Long(request.getParameter("idFormContrib"));

             //FormContrib formContrib = formContribFacade.get(idFormContrib);
             FormContrib formContrib = new FormContrib();
             formContrib.setIdFormContrib(idFormContrib);
             Map<String, Object> mapaFC = this.formContribFacade.get(formContrib, new String[]{"totalPagar", "certificado"});
             formContrib.setCertificado((String) mapaFC.get("certificado"));
             formContrib.setTotalPagar((Integer) mapaFC.get("totalPagar"));
             logger.info("certificado");
             if ((montoPagar == 0 && formContrib.getTotalPagar() == 0)
             || (montoPagar == 0 && formContrib.getTotalPagar() > 0
             && formContrib.getCertificado().equalsIgnoreCase("N"))) {
             logger.info("monto a pagar -1");
             out.println(CertificarFormulario.certificarFormulario(request));
             } else if (montoPagar > 0) {
             logger.info("monto a pagar -2");
             Respuesta respuesta = transaccionFacade.doTransacctionSetPagoFormulario(0L, idRed,
             idRecaudador, idSucursal, idTerminal, idUsuario, user, contrasenha, formatter.format(fechaHoraRed),
             formatter.format(fechaHoraTerminal), new Long(request.getParameter("idTipoPago")), 1.0, 600L, montoPagar,
             new Long(request.getParameter("idFormContrib")), era,
             numeroCheque, idEntidadFinanciera, fechaCheque, request.getSession());

             if (respuesta.getIdRespuesta() == 0) {
             Transaccion transa
             = transaccionFacade.get(respuesta.getIdTransaccion());

             String respuesta2 = UtilesSet.generarTicketTransa(transa).replaceAll("\n\n", ";;;");
             String[] respuestaComponentes = respuesta2.split(";;;");

             String cadenaImpresionTicket = "";
             String cadenaImpresionCertificacionFormulario = "";
             String cadenaImpresionCertificacionBoleta = "";
             Integer modo = 1;//modo Rollo
             String ticket_pantalla = ""; //Ticket 
             for (String ooo : respuestaComponentes) {

             cadenaImpresionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
             ticket_pantalla += ooo + "<br/>";
             }

             //                        Certificacion FORMULARIO 
             respuesta2 = UtilesSet.generarCertificacionFormularioTransa(transa, (Integer) (request.getSession().getAttribute("cod_era"))).replaceAll("\n\n",
             ";;;");
             respuestaComponentes = respuesta2.split(";;;");
             modo = 2;
             for (String ooo : respuestaComponentes) {

             cadenaImpresionCertificacionFormulario += modo + ";;" + "N"
             + ";;" + ooo + ";;;";
             ticket_pantalla += ooo + "<br/>";
             }
             //Certificacion BOLETA 
             respuesta2 = UtilesSet.generarCertificacionBoletaPagoTransa(transa, (Integer) (request.getSession().getAttribute("cod_era"))).replaceAll("\n\n",
             ";;;");
             respuestaComponentes = respuesta2.split(";;;");
             modo = 2;
             for (String ooo : respuestaComponentes) {

             cadenaImpresionCertificacionBoleta += modo + ";;" + "N" + ";;"
             + ooo + ";;;";
             ticket_pantalla += ooo + "<br/>";
             }

             jsonFinal.put("ticket_pantalla", ticket_pantalla);
             jsonFinal.put("ticket", cadenaImpresionTicket);
             jsonFinal.put("certificacion_formulario", cadenaImpresionCertificacionFormulario);
             jsonFinal.put("certificacion_boleta", cadenaImpresionCertificacionBoleta);
             jsonFinal.put("success", true);
             out.println(jsonFinal.toString());
             } else {
             //formContrib.setPagado(1); jsonFinal.put("ticket", "");
             jsonFinal.put("motivo", respuesta.getDescRespuesta());
             jsonFinal.put("success", false);
             out.println(jsonFinal.toString());
             }

             } else if (formContrib.getTotalPagar() > 0 && montoPagar == 0
             && formContrib.getCertificado().equalsIgnoreCase("S")) {
             logger.info("Formulario certificado, el 'Total a  Pagar ' debe ser mayor a 0");
             jsonFinal.put("motivo", "Formulario certificado, el 'Total a  Pagar ' debe ser mayor a 0");
             jsonFinal.put("success", false);

             out.println(jsonFinal.toString());
             }

             } else {

             jsonFinal.put("motivo", "Los Parametros no son correctos");
             jsonFinal.put("success", false);
             out.println(jsonFinal.toString());

             }*/
        } else if (op.equalsIgnoreCase("CODIGO_REFERNCIA-LLENAR_CAMPOS")) {
            if (request.getParameter("numeroImpuesto") != null && request.getParameter("numeroImpuesto").matches("[0-9]+")) {
                Impuesto impuestoExample = new Impuesto();
                impuestoExample.setNumero(new Integer(request.getParameter("numeroImpuesto")));
                List<Impuesto> limpuesto = impuestoFacade.list(impuestoExample);

                if (limpuesto.size() == 1) {
                    for (Impuesto o : limpuesto) {
                        if (o.getSigla() != null) {
                            json.put("campo", "idDescripcionObligacion");
                            json.put("valor", o.getSigla());
                            arrayJson.add(json.clone());

                            jsonFinal.put("success", true);
                            jsonFinal.put("total", 1);
                            jsonFinal.put("items", arrayJson);

                            if (o.getTipoAtributo().equalsIgnoreCase("RESOLUCION")) {
                                jsonFinal.put("resolutivo", true);
                            } else {
                                jsonFinal.put("resolutivo", false);
                            }

                            break;

                        } else {
                            jsonFinal.put("success", false);

                            jsonFinal.put("motivo", "No existe descripcion para el codigo seleccionado");
                        }
                    }
                } else {
                    jsonFinal.put("success", false);
                }
            } else {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", "Los parametros no son validos");

            }

            out.println(jsonFinal);

        } else if (op.equalsIgnoreCase("RUC-LLENAR_CAMPOS")) {
            //Se hace asi hasta que se encuentre una manera generica de hacerlo.
            //HttpSession session = request.getSession();

            boolean hasCodSucSET = (Boolean) session.getAttribute("hasCodSucSET");
            boolean hasCodCajaSET = (Boolean) session.getAttribute("hasCodCajaSET");
            if (hasCodSucSET) {
                if (hasCodCajaSET) {
//                    Agregado por Ystmio Gaona
//                    ParametroSistema paramFile = new ParametroSistema();
//                    paramFile.setNombreParametro("urlContribSet");
//
//                    String url = parametroSistemaFacade.get(paramFile).getValor();
//                    ContribSETWS serviceSet = getWSManager(url, "http://contrib.ws.daemon.documenta.com.py/", "ContribSETWS", 10, 80);
//
//                    Contribuyente contribWS = null;
//
//                    try {
//                        contribWS = serviceSet.consulta(critConsulta);
//
//                        if (contribWS == null) {
//                            Logger.getLogger(CONTRIBUYENTE.class.getName()).log(Level.SEVERE, String.format("Contribuyente [%s] no encontrado", critConsulta));
//                            jsonRespuesta.put("success", false);
//                            jsonRespuesta.put("motivo", String.format("Contribuyente con Ruc %s no encontrado", critConsulta));
//                            out.println(jsonRespuesta.toString());
//                            return;
//                        }
//                        Contribuyentes ejemplo = new Contribuyentes();
//                        ejemplo.setRucNuevo(critConsulta);
//
//                        List<Contribuyentes> lcontribuyentes = contribuyentesFacade.list(ejemplo, "rucNuevo", "asc", true);
//                        Contribuyentes e = (lcontribuyentes != null && !lcontribuyentes.isEmpty()) ? lcontribuyentes.get(0) : null;
//
//                        if (e == null) {
//                            e = new Contribuyentes(new Long(contribWS.getId()));
//                        }
//                        e.setDigitoVerificador(contribWS.getDigitoVerificador());
////                        e.setEstado(contribWS.get);
//                        contribuyentesFacade.merge(e);
//
//                        JSONArray jsonContribuyentes = new JSONArray();
//
////                    for (Contribuyentes e : lcontribuyentes) {
//                        JSONObject jsonContribuyente = new JSONObject();
//                        jsonContribuyente.put(CONTRIBUYENTE.ID_CONTRIBUYENTE, e.getIdContribuyente());
//                        jsonContribuyente.put(CONTRIBUYENTE.RUC_NUEVO, e.getRucNuevo());
//                        jsonContribuyente.put(CONTRIBUYENTE.DIGITO_VERIFICADOR, e.getDigitoVerificador());
//                        jsonContribuyente.put(CONTRIBUYENTE.RUC_ANTERIOR, e.getRucAnterior() != null ? e.getRucAnterior() : "");
//                        jsonContribuyente.put(CONTRIBUYENTE.MODALIDAD_CONTRIBUYENTE, (e.getModalidadContribuyente()) == 1 ? "Grande" : (e.getModalidadContribuyente()) == 2 ? "Pequeño" : "Mediano");
//                        jsonContribuyente.put(CONTRIBUYENTE.NOMBRE_CONTRIBUYENTE, e.getNombreContribuyente());
//                        jsonContribuyente.put(CONTRIBUYENTE.TIPO_CONTRIBUYENTE, (e.getTipoContribuyente()) == 1 ? "Fisico" : "Juridico");
//                        jsonContribuyente.put(CONTRIBUYENTE.MES_CIERRE, e.getMesCierre());
//                        jsonContribuyente.put(CONTRIBUYENTE.ESTADO, e.getEstado() != null ? e.getEstado() : "");
//                        jsonContribuyentes.add(jsonContribuyente);
////                    }
//
//                        jsonRespuesta.put("CONTRIBUYENTE", jsonContribuyentes);
//                        jsonRespuesta.put("TOTAL", contribuyentesFacade.total(ejemplo, true));
//                        jsonRespuesta.put("success", true);
//                        out.println(jsonRespuesta.toString());
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(CONTRIBUYENTE.class.getName()).log(Level.SEVERE, String.format("Error al consultar por existencia del contribuyente [%s]", critConsulta), ex);
//                        jsonRespuesta.put("success", false);
//                        jsonRespuesta.put("motivo", String.format("Contribuyente con Ruc %s no encontrado", critConsulta));
//                        out.println(jsonRespuesta.toString());
////                    return;
//                    }

                    Contribuyentes contribuyente = contribuyentesFacade.getContribuyentePorRuc(request.getParameter("CC4"), request.getParameter("CC4"), request.getParameter("CC6"));
                    if (contribuyente == null) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "No existe el Contribuyente en base de datos SET");
                    } else {

                        if (contribuyente.getModalidadContribuyente().intValue() == 2 || contribuyente.getModalidadContribuyente().intValue() == 3) {
                            json.put("campo", "idNombreRazonSocialBoletaPago");
                            json.put("valor", contribuyente.getNombreContribuyente());
                            arrayJson.add(json.clone());

                            jsonFinal.put("success", true);
                            jsonFinal.put("total", 1);
                            jsonFinal.put("items", arrayJson);

                        } else {
                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", "No se permite la modalidad Gran Contribuyente");
                        }
                    }
                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "La Terminal no tiene Codigo Caja SET para operar");
                }
            } else {
                jsonFinal.put("success", false);
                jsonFinal.put("motivo", "La Sucursal no tiene Codigo Sucursal SET para operar");
            }
            out.println(jsonFinal);

        } else if (op.equalsIgnoreCase("CODIGO_OBLIGACION-LLENAR_CAMPOS")) {

            int contador = 0;
            FormularioImpuesto formularioImpuesto = new FormularioImpuesto();
            formularioImpuesto.setImpuesto(new Integer(request.getParameter("impuesto")));
            List<FormularioImpuesto> lresultado = formularioImpuestoFacade.list(formularioImpuesto);
            //Se hace asi hasta que se encuentre una manera generica de hacerlo.
            for (FormularioImpuesto o : lresultado) {
                json.put("campo", "idObligacionSocialBoletaPago");
                if (o.getObligacion() != null) {
                    json.put("valor", o.getObligacion());
                } else {
                    json.put("valor", "");
                }
                arrayJson.add(json.clone());
                contador++;
            }

            jsonFinal.put("success", true);
            jsonFinal.put("total", contador);
            jsonFinal.put("items", arrayJson);
            out.println(jsonFinal);

        } else {
            json.put("success", false);
            json.put("motivo", "No existe la opcion pedida");
            out.println(json);

        }

        //out.close();
    }

    public static int Receive(byte[] cbuf, int off, int len, int timeout, Socket sock) throws SocketException, IOException {
        sock.setSoTimeout(timeout);

        InputStream input = sock.getInputStream();
        int res = input.read(cbuf, off, len);

        return res;

    }

    public static void Send(byte[] buffer, Socket sock) throws IOException {

        OutputStream output = sock.getOutputStream();
        output.write(buffer, 0, buffer.length);
        output.flush();
        //System.out.println("TcpClient.Send OK");
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        } catch (IllegalArgumentException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        } catch (IllegalArgumentException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
