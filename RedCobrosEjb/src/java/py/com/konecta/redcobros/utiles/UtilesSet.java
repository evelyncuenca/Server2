/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.utiles;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.netbeans.xml.schema.common.RespuestaCobranza;
import py.com.documenta.onlinemanager.ws.OlDetalleReferencia;
import py.com.documenta.set.main.Hash;
import py.com.documenta.ws.Autenticacion;
import py.com.documenta.ws.FormaPago;
import py.com.documenta.ws.RedCobro;
import py.com.documenta.ws.RedCobroService;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.entities.*;

/**
 *
 * @author konecta
 */
public class UtilesSet {

    private static RedCobroService service = null;
    private static ReentrantLock lock = new ReentrantLock();
    private static Logger log = Logger.getLogger(UtilesSet.class.getName());

    public static RedCobro getWSManager(String url, String uri, String localPart,
            int connTo, int readTo) {
        RedCobro pexSoap;

        URL wsdlURL = RedCobroService.class.getClassLoader().getResource("schema/RedCobroService.wsdl");
        try {
            if (service == null) {
                try {
                    lock.lock();
                    if (service == null) {
                        service = new RedCobroService(wsdlURL, new QName(uri, localPart));
                    }
                } catch (Exception ex) {
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception ex) {
        }

        pexSoap = service.getRedCobroPort();
        BindingProvider provider = (BindingProvider) pexSoap;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        provider.getRequestContext().put("com.sun.xml.ws.connect.timeout", connTo * 1000);
        provider.getRequestContext().put("com.sun.xml.ws.request.timeout", readTo * 1000);

        return pexSoap;
    }

    public static String getMd5File(File file) {
        try {
            InputStream fin = new FileInputStream(file);
            java.security.MessageDigest md5er = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int read;

            do {
                read = fin.read(buffer);
                if (read > 0) {
                    md5er.update(buffer, 0, read);
                }
            } while (read != -1);
            fin.close();

            byte[] digest = md5er.digest();
            if (digest == null) {
                return null;
            }

            String strDigest = "";
            for (int i = 0; i < digest.length; i++) {
                strDigest += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1).toLowerCase();
            }
            return strDigest;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMd5String(String input) {
        String res = "";

        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");

            algorithm.reset();
            algorithm.update(input.getBytes());

            byte[] md5 = algorithm.digest();

            String tmp = "";
            for (int i = 0; i < md5.length; i++) {
                tmp = (Integer.toHexString(0xFF & md5[i]));
                if (tmp.length() == 1) {
                    res += "0" + tmp;
                } else {
                    res += tmp;
                }
            }
        } catch (NoSuchAlgorithmException ex) {
        }
        return res;
    }

    public static String getAdler(byte[] bytes) {
        Integer a = 1;
        Integer b = 0;
        String hex1 = "";
        String hex2 = "";

        for (int i = 0; i < bytes.length; i++) {
            a = a + bytes[i];
            b = a + b;
        }
        hex1 = Integer.toString(b, 16);
        hex2 = Integer.toString(a, 16);

        for (int i = 3; i > 0; i--) {
            if ((hex2.length() - 1) < i) {
                hex2 = "0" + hex2;
            }
        }
        return hex1 + hex2;
    }

    public static String getHashSet(String input) {
        return getAdler(getMd5String(input).getBytes());
    }

    public static String getHashSet(File input) {
        return getAdler(getMd5File(input).getBytes());
    }

    private static String obtenerPatron(String caracter, Integer cantidadTotal) {
        String patron = "";
        for (int i = 0; i < cantidadTotal; i++) {
            patron += caracter;
        }
        return patron;
    }

    public static String cerosIzquierda(Long numero, Integer cantidadTotal) {
        DecimalFormat df = new DecimalFormat(UtilesSet.obtenerPatron("0", cantidadTotal));
        return df.format(numero);
    }

    public static String espaciosDerecha(String cadena, Integer cantidadTotal) {
        return cadena + UtilesSet.obtenerPatron(" ", cantidadTotal - cadena.length());
    }

    public static String espaciosIzquierda(String cadena, Integer cantidadTotal) {
        return UtilesSet.obtenerPatron(" ", cantidadTotal - cadena.length()) + cadena;
    }

    public static String getCertificacionAperturaCaja(Gestion gestion) throws Exception {

        //Armar la certificacion
        String cabeceraCertificacion = new String();
        String bodyCertificacion = new String();
        String pieCertificacion = new String();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        cabeceraCertificacion = "            A P E R T U R A            \n\n";
        cabeceraCertificacion += gestion.getTerminal().getSucursal().getRecaudador().getDescripcion() + "\n\n";
        cabeceraCertificacion += "RUC: " + gestion.getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi() + "    -    TEL.:" + gestion.getTerminal().getSucursal().getTelefono() + "\n\n";
        cabeceraCertificacion += "DIRECCION: " + gestion.getTerminal().getSucursal().getDireccion() + "\n\n";
        cabeceraCertificacion += " \n\n";
        bodyCertificacion += "TERMINAL: " + gestion.getTerminal().getCodigoTerminal() + "     SUC: " + gestion.getTerminal().getSucursal().getDescripcion() + "\n\n";
        bodyCertificacion += "USUARIO: " + gestion.getUsuario().getPersona().getApellidos() + ", " + gestion.getUsuario().getPersona().getNombres() + " (" + gestion.getUsuario().getCodigo() + ")" + "\n\n";
        bodyCertificacion += "GESTION: " + gestion.getNroGestion() + "\n\n";
        bodyCertificacion += " \n\n";
        bodyCertificacion += "Fecha y Hora: " + formatter.format(gestion.getFecHoraUltUpdate()) + "\n\n";
        pieCertificacion += "   *** R E D  D E  P A G O S ***       " + "\n\n";
        return cabeceraCertificacion + bodyCertificacion + pieCertificacion;

    }

    public static String getCertificacionCierre(Gestion gestion) throws Exception {

        //Armar la certificacion
        String cabeceraCertificacion = new String();
        String bodyCertificacion = new String();
        String pieCertificacion = new String();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        cabeceraCertificacion = "            C I E R R E           \n\n";
        cabeceraCertificacion += gestion.getTerminal().getSucursal().getRecaudador().getDescripcion() + "\n\n";
        cabeceraCertificacion += "RUC: " + gestion.getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi() + "    -    TEL.:" + gestion.getTerminal().getSucursal().getTelefono() + "\n\n";
        cabeceraCertificacion += "DIRECCION: " + gestion.getTerminal().getSucursal().getDireccion() + "\n\n";
        cabeceraCertificacion += " \n\n";
        bodyCertificacion += "TERMINAL: " + gestion.getTerminal().getCodigoTerminal() + "     SUC: " + gestion.getTerminal().getSucursal().getDescripcion() + "\n\n";
        bodyCertificacion += "USUARIO: " + gestion.getUsuario().getPersona().getApellidos() + ", " + gestion.getUsuario().getPersona().getNombres() + " (" + gestion.getUsuario().getCodigo() + ")" + "\n\n";
        bodyCertificacion += "GESTION: " + gestion.getNroGestion() + "\n\n";
        bodyCertificacion += " \n\n";
        bodyCertificacion += "Fecha y Hora: " + formatter.format(gestion.getFecHoraUltUpdate()) + "\n\n";
        pieCertificacion += "   *** R E D  D E  P A G O S ***       " + "\n\n";
        return cabeceraCertificacion + bodyCertificacion + pieCertificacion;

    }

    public static Map<String, String> getReimpresionTicketCertificacion(Transaccion transa, Integer era) throws Exception {

        String ticketTransa = UtilesSet.generarTicketTransa(transa);
        String certificacionTransaBoleta = (transa.getBoletaPagoContrib() != null) ? UtilesSet.generarCertificacionBoletaPagoTransa(transa, era) : null;
        String certificacionTransFormulario = (transa.getFormContrib() != null) ? UtilesSet.generarCertificacionFormularioTransa(transa, era) : null;
        String respuesta2 = ticketTransa.replaceAll("\n\n", ";;;");
        String[] respuestaComponentes = respuesta2.split(";;;");

        String ticket_pantalla = "";
        String cadenaImpresionTicket = "";
        String cadenaImpresionCertificacionBoleta = "";
        String cadenaImpresionCertificacionTicket = "";
        Map<String, String> componentesImpresion = new HashMap<String, String>();

        Integer modo = 1;

        if (ticketTransa != null) {
            respuesta2 = ticketTransa.replaceAll("\n\n", ";;;");
            respuestaComponentes = respuesta2.split(";;;");
            //Ticket
            if (respuestaComponentes.length > 0) {
                if (transa.getFlagAnulado().equalsIgnoreCase("S")) {
                    cadenaImpresionTicket += modo + ";;" + "N" + ";;" + "TRANSACCION ANULADA" + ";;;";
                    ticket_pantalla += "TRANSACCION ANULADA" + "<br/>";

                } else {
                    cadenaImpresionTicket += modo + ";;" + "N" + ";;" + "        R E I M P R E S I O N          " + ";;;";

                    ticket_pantalla += "         R E I M P R E S I O N         " + "<br/>";

                }

            }
            for (String ooo : respuestaComponentes) {
                cadenaImpresionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
                ticket_pantalla += ooo + "<br/>";
            }
        } else {
            cadenaImpresionTicket += modo + ";;" + "N" + ";;" + "NO existe Ticket para esta Transaccion." + ";;;";
            ticket_pantalla += "No existe Ticket para esta Transaccion." + "<br/>";
        }

        //Certificacion
        if (transa.getFlagAnulado().equalsIgnoreCase("N")) {
            if (certificacionTransFormulario != null && transa.getFormContrib().getCertificado().equals("S")) {
                respuesta2 = certificacionTransFormulario.replaceAll("\n\n", ";;;");
                respuestaComponentes = respuesta2.split(";;;");
                modo = 2;

                for (String ooo : respuestaComponentes) {

                    cadenaImpresionCertificacionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
                    ticket_pantalla += ooo + "<br/>";
                }
            } else {
                cadenaImpresionCertificacionTicket += modo + ";;" + "N" + ";;" + "NO existe Certificacion para esta Transaccion." + ";;;";
                ticket_pantalla += "NO existe Certificacion para esta Transaccion." + "<br/>";
            }
            if (certificacionTransaBoleta != null) {
                respuesta2 = certificacionTransaBoleta.replaceAll("\n\n", ";;;");
                respuestaComponentes = respuesta2.split(";;;");
                modo = 2;

                for (String ooo : respuestaComponentes) {

                    cadenaImpresionCertificacionBoleta += modo + ";;" + "N" + ";;" + ooo + ";;;";
                    ticket_pantalla += ooo + "<br/>";
                }
            } else {
                cadenaImpresionCertificacionBoleta += modo + ";;" + "N" + ";;" + "NO existe Certificacion para esta Transaccion." + ";;;";
                ticket_pantalla += "NO existe Certificacion para esta Transaccion." + "<br/>";
            }

        }

        componentesImpresion.put("ticket_pantalla", ticket_pantalla);
        componentesImpresion.put("ticket", cadenaImpresionTicket);
        componentesImpresion.put("certificacion_boleta", cadenaImpresionCertificacionBoleta);
        componentesImpresion.put("certificacion_ticket", cadenaImpresionCertificacionTicket);

        return componentesImpresion;

    }

    public static Map<String, String> getDetalleTransaccionAnular(Transaccion transa) throws Exception {
        String ticketTransa = UtilesSet.generarTicketTransa(transa);

        String respuesta2 = ticketTransa.replaceAll("\n\n", ";;;");
        String[] respuestaComponentes = respuesta2.split(";;;");
        String ticket_pantalla = "";
        String cadenaImpresionTicket = "";

        Integer modo = 1;

        Map<String, String> componentesImpresion = new HashMap<String, String>();

        //ticketTransa += UtilesSet.generarTicketTransa(transa);
        if (ticketTransa != null) {
            respuesta2 = ticketTransa.replaceAll("\n\n", ";;;");
            respuestaComponentes = respuesta2.split(";;;");
            //Ticket
            if (respuestaComponentes.length > 0) {
            }
            for (String ooo : respuestaComponentes) {
                cadenaImpresionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
                ticket_pantalla += ooo + "<br/>";
            }
        } else {
            cadenaImpresionTicket += modo + ";;" + "N" + ";;" + "NO existe Ticket para esta Transaccion." + ";;;";
            ticket_pantalla += "NO existe Ticket para esta Transaccion." + "<br/>";
        }

        componentesImpresion.put("ticket_pantalla", ticket_pantalla);
        componentesImpresion.put("ticket", cadenaImpresionTicket);
        componentesImpresion.put("certificacion", "");
        return componentesImpresion;

    }

    public static Map<String, String> getDetalleDigitacionAnular(FormContrib formContrib, UsuarioTerminal us) throws Exception {

        String cabeceraTicket = new String();
        String bodyTicket = new String();
        String pieTicket = new String();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        //Armar el Ticket
        cabeceraTicket = " A N U L A C I O N    D E    D I G I T A C I O N\n\n";
        cabeceraTicket += "Nro. Orden:" + formContrib.getNumeroOrden() + "\n\n";
        // cabeceraTicket += "ERA: " + us.getTerminal().getSucursal().getRecaudador().getRed().getCodEra();
        /*
         * String recaudadorDescripcion; if
         * (us.getTerminal().getSucursal().getRecaudador().getDescripcion().length()
         * > 4) { recaudadorDescripcion =
         * us.getTerminal().getSucursal().getRecaudador().getDescripcion().substring(0,
         * 4) + "."; } else { recaudadorDescripcion =
         * us.getTerminal().getSucursal().getRecaudador().getDescripcion() +
         * "."; }
         */
        cabeceraTicket += " Term.:" + us.getTerminal().getCodigoTerminal();
        cabeceraTicket += " Val.:" + formContrib.getGrupo().getGestion().getTerminal().getCodigoCajaSet() + "\n\n";

        bodyTicket = "Referencia: " + formContrib.getIdFormContrib() + "\n\n";
        bodyTicket += "RUC: " + formContrib.getRuc() + " - " + formContrib.getDigitoVerificador() + "\n\n";
        bodyTicket += "Formulario: " + formContrib.getFormulario().getNumeroFormulario() + "\n\n";
        bodyTicket += "Periodo Fiscal: " + formContrib.getPeriodoFiscal() + "\n\n";
        bodyTicket += "Monto Declarado: " + formContrib.getTotalPagar() + "\n\n";

        bodyTicket += "Fecha y Hora: " + formatter.format(new Date()) + "\n\n";
        pieTicket = "**ESTE ES SU COMPROBANTE, CONSERVELO**" + "\n\n";
        pieTicket += "    *** R E D  D E  P A G O S ***" + "\n\n";

        String ticket = cabeceraTicket + bodyTicket + pieTicket;
        String respuesta2 = ticket.replaceAll("\n\n", ";;;");
        String[] respuestaComponentes = respuesta2.split(";;;");
        String ticket_pantalla = "";
        String cadenaImpresionTicket = "";

        Integer modo = 1;

        Map<String, String> componentesImpresion = new HashMap<String, String>();

        // respuesta2 = cabeceraTicket + bodyTicket + pieTicket.replaceAll("\n\n", ";;;");
        // respuestaComponentes = respuesta2.split(";;;");
        if (respuestaComponentes.length > 0) {
        }
        for (String ooo : respuestaComponentes) {
            cadenaImpresionTicket += modo + ";;" + "N" + ";;" + ooo + ";;;";
            ticket_pantalla += ooo + "<br/>";
        }
        componentesImpresion.put("ticket_pantalla", ticket_pantalla);
        componentesImpresion.put("ticket", cadenaImpresionTicket);

        return componentesImpresion;

    }

    public static String formatearNumerosSeparadorMiles(Object valor, Boolean decimales) {
        // NumberFormat formatter =  NumberFormat.getNumberInstance(Locale.US);
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        String s = "";
        if (valor != null && !valor.toString().isEmpty()) {

            // formatter = new DecimalFormat("#,###,###");
            if (decimales) {
                formatter.setMaximumFractionDigits(4);
            }
            s = formatter.format(valor);
        }
        return s;
    }

    public static String generarTicketTransa(Transaccion transa) {
        String cabeceraTicket = new String();
        String bodyTicket = new String();
        String pieTicket = new String();

        String nvu = "";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        //Armar el Ticket
        cabeceraTicket = " R E C I B O  D E  D I N E R O\n\n";
        cabeceraTicket += transa.getGestion().getTerminal().getSucursal().getRecaudador().getDescripcion() + "\n\n";
        cabeceraTicket += "RUC: " + transa.getGestion().getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi() + "    -    TEL.:" + transa.getGestion().getTerminal().getSucursal().getTelefono() + "\n\n";
        cabeceraTicket += "DIRECCION: " + transa.getGestion().getTerminal().getSucursal().getDireccion() + "\n\n";
        cabeceraTicket += "Terminal: " + transa.getGestion().getTerminal().getCodigoTerminal() + "     SUC: " + transa.getGestion().getTerminal().getSucursal().getDescripcion() + "\n\n";
        cabeceraTicket += "TRANS.: " + transa.getIdTransaccion() + " \n\n";
        cabeceraTicket += " \n\n";

        cabeceraTicket += "Pago por Cuenta y Orden de Impuestos\n\n";
        //  bodyTicket = "Impuesto: " + transa.getBoletaPagoContrib().getImpuesto().getNumero() + "-" + transa.getBoletaPagoContrib().getImpuesto().getSigla() + "\n\n";
        bodyTicket = "Impuesto: " + transa.getBoletaPagoContrib().getImpuesto().getNumero() + "\n\n";
        bodyTicket += "Nro. Boleta Pago: " + transa.getBoletaPagoContrib().getNumeroOrden() + "\n\n";
        bodyTicket += "RUC Contrib.: " + transa.getBoletaPagoContrib().getContribuyente().getRucNuevo() + "-" + transa.getBoletaPagoContrib().getContribuyente().getDigitoVerificador() + "\n\n";
        bodyTicket += transa.getBoletaPagoContrib().getContribuyente().getNombreContribuyente() + "\n\n";
        //  bodyTicket += "Importe: " + transa.getMontoTotal().intValue();
        bodyTicket += "Importe: " + formatearNumerosSeparadorMiles(transa.getMontoTotal(), false);
        bodyTicket += "   F. Pago: " + transa.getTipoPago().getDescripcion() + "\n\n";
        nvu = transa.getBoletaPagoContrib().getImpuesto().getNumero() + transa.getBoletaPagoContrib().getNumeroOrden() + transa.getBoletaPagoContrib().getContribuyente().getRucNuevo() + transa.getBoletaPagoContrib().getContribuyente().getDigitoVerificador() + transa.getMontoTotal().intValue();
        if (transa.getEntidadFinanciera() != null) {
            bodyTicket += "Banco: " + ((transa.getEntidadFinanciera().getDescripcion().length() > 70) ? transa.getEntidadFinanciera().getDescripcion().substring(0, 70) + "." : transa.getEntidadFinanciera().getDescripcion()) + "\n\n";
            bodyTicket += "Cheque: " + (transa.getNumeroCheque()) + "\n\n";
            nvu += transa.getNumeroCheque();
        }
        nvu += transa.getBoletaPagoContrib().getCodigoHash() + transa.getBoletaPagoContrib().getConsecutivo() + formatter.format(transa.getFechaHoraRed());

        bodyTicket += "Hash: " + transa.getBoletaPagoContrib().getCodigoHash() + "\n\n";
        bodyTicket += "Consecutivo: " + transa.getBoletaPagoContrib().getConsecutivo() + "\n\n";
        bodyTicket += "NVU: " + getNVU(nvu) + "\n\n";
        bodyTicket += "\n\n";
        bodyTicket += "Fecha y Hora: " + formatter.format(transa.getFechaHoraRed()) + "\n\n";
        pieTicket = "**ESTE ES SU COMPROBANTE, CONSERVELO**" + "\n\n";
        pieTicket += "    *** R E D  D E  P A G O S ***" + "\n\n";

        return cabeceraTicket + bodyTicket + pieTicket;

    }

    private void writeOnUserFile(String pathFile, Long codExtUsuario, String idTrx, Boolean indicador) {
        try {
            Context context = new InitialContext();
            TransaccionRcFacade transaccionRcFacade = (TransaccionRcFacade) context.lookup(TransaccionRcFacade.class.getName());

            TransaccionRc transRc = transaccionRcFacade.get(new BigDecimal(idTrx));
            Utiles.writeToFile(pathFile, codExtUsuario, transRc, indicador);
        } catch (Exception ex) {
            Logger.getLogger(UtilesSet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Long pagarFormulario(Long idUsuario, UsuarioTerminal us, FormContrib formContrib, RecaudadorFormulario recForm, boolean conciliarCaja) {
        Long idTransaccion = null;
        try {
            Context context = new InitialContext();

            RuteoServicioFacade ruteoServicioFacade = (RuteoServicioFacade) context.lookup(RuteoServicioFacade.class.getName());

            RuteoServicio ruteoRC = ruteoServicioFacade.get(2L);

            Autenticacion auth = new Autenticacion();
            auth.setClave(us.getUsuario().getContrasenha());
            auth.setHash(UtilesSet.getSha1(us.getUsuario().getContrasenha()));
            auth.setIdGestion(new BigDecimal(formContrib.getGrupo().getGestion().getIdGestion()));
            auth.setIdUsuario(idUsuario.toString());

            RedCobro rcService = getWSManager(ruteoRC.getUrlHost(), "http://ws.documenta.com.py/", "RedCobroService", ruteoRC.getConexionTo(), ruteoRC.getReadTo());

            if (recForm != null) {
                FormaPago formaDePago = new FormaPago();
                formaDePago.setTipoPago(1);

                OlDetalleReferencia detalle = new OlDetalleReferencia();
                detalle.setIdMoneda(600);
                detalle.setReferenciaPago(formContrib.getNumeroOrden().toString());
                detalle.setMonto(new BigDecimal(recForm.getMonto()));
                detalle.setTasa(1);

                RespuestaCobranza respuesta = rcService.realizarCobranzaManual(142, detalle, auth, formaDePago, null, null);
                if (respuesta.getEstado().getCodigoRetorno() == 0) {
                    idTransaccion = respuesta.getIdTransaccion();
                    ParametroSistemaFacade parametroSistemaFacade = (ParametroSistemaFacade) context.lookup(ParametroSistemaFacade.class.getName());

                    ParametroSistema paramFile = new ParametroSistema();
                    paramFile.setNombreParametro("pathFileUser");
                    String pathFile = parametroSistemaFacade.get(paramFile).getValor();
                    if (conciliarCaja) {
                        writeOnUserFile(pathFile, idUsuario, idTransaccion.toString(), false);
                    }
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(UtilesSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idTransaccion;
    }

    public String generarCertificacionFormulario(Long idFormContrib, Long idUsuario, Long idTerminal, Long idRed, Integer codEra, Long idSucursal) throws Exception {
        String retorno = "ERROR";

        Context context = new InitialContext();
        FormContribFacade formContribFacade = (FormContribFacade) context.lookup(FormContribFacade.class.getName());
        TerminalFacade terminalFacade = (TerminalFacade) context.lookup(TerminalFacade.class.getName());
        UsuarioTerminalFacade usuarioTerminalFacade = (UsuarioTerminalFacade) context.lookup(UsuarioTerminalFacade.class.getName());
        FormularioImpuestoFacade formularioImpuestoFacade = (FormularioImpuestoFacade) context.lookup(FormularioImpuestoFacade.class.getName());
        RecaudadorFormularioFacade recFormFacade = (RecaudadorFormularioFacade) context.lookup(RecaudadorFormularioFacade.class.getName());

        //ImpuestoFacade impuestoFacade = (ImpuestoFacade) context.lookup(ImpuestoFacade.class.getName());
        Terminal terminal = terminalFacade.get(idTerminal);
        UsuarioTerminal us = new UsuarioTerminal();
        us.setUsuario(new Usuario(idUsuario));
        us.setTerminal(terminal);
        us = usuarioTerminalFacade.get(us);

        RecaudadorFormulario recForm = recFormFacade.get(terminal.getSucursal().getRecaudador().getIdRecaudador());

        FormContrib formContrib = formContribFacade.certificarFormulario(idFormContrib, codEra, us, idRed, idSucursal);
        Recaudador recaudador = formContrib.getUsuarioTerminalCarga().getTerminal().getSucursal().getRecaudador();
        boolean conciliar = recaudador.getConciliarCaja() != null && recaudador.getConciliarCaja().equalsIgnoreCase("S") ? true : false;
        Long idTransaccion = pagarFormulario(idUsuario, us, formContrib, recForm, conciliar);
        if (idTransaccion != null) {

            formContrib.setTransaccionRc(idTransaccion);
            formContrib.setUsuarioTerminalCarga(us);
            formContribFacade.merge(formContrib);

            FormularioImpuesto fi = new FormularioImpuesto();
            fi.setNumeroFormulario(formContrib.getFormulario().getNumeroFormulario());
            fi = formularioImpuestoFacade.get(fi);
            Impuesto impuesto = new Impuesto();
            impuesto.setNumero(fi.getImpuesto());
            //impuesto = impuestoFacade.get(impuesto);
            String cabeceraCertificacion = new String();
            String bodyCertificacion = new String();
            String pieCertificacion;
            SimpleDateFormat formatterFecha = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatterHora = new SimpleDateFormat("HH:mm:ss");

            terminal = formContrib.getGrupo().getGestion().getTerminal();

            bodyCertificacion += recaudador.getDescripcion() + " RUC:" + recaudador.getEntidad().getRucCi() + "\n\n";
            bodyCertificacion += "Nro. Orden:" + formContrib.getNumeroOrden() + " RUC:" + formContrib.getRuc() + "-" + formContrib.getDigitoVerificador() + "\n\n";
            bodyCertificacion += "Fecha y Hora: " + formatterFecha.format(formContrib.getFechaCertificadoSet()) + " - " + formatterHora.format(formContrib.getFechaHoraRealCertificado()) + "\n\n";

            bodyCertificacion += "ERA: " + codEra;
            bodyCertificacion += " Suc. SET:" + terminal.getSucursal().getCodigoSucursalSet();

            bodyCertificacion += " Val.:" + terminal.getCodigoCajaSet() + "," + us.getUsuario().getNombreUsuario() + "\n\n";
            bodyCertificacion += "Hash: " + formContrib.getCodigoHash();
            bodyCertificacion += " Consecutivo:" + formContrib.getConsecutivo() + "\n\n";
            bodyCertificacion += "Periodo:" + formContrib.getPeriodoFiscal();
            bodyCertificacion += " Formul:" + formContrib.getFormulario().getNumeroFormulario() + "\n\n";
            pieCertificacion = "\"VALIDO COMO COMPROBANTE DE PAGO,\n\nGs. " + NumberFormat.getInstance().format(recForm.getMonto()) + " IVA INCLUIDO\"" + "\n\n";

            retorno = cabeceraCertificacion + bodyCertificacion + pieCertificacion;
        }
        return retorno;
    }

    public static Map<String, String> getCertificacionFormulario(Long idFormContrib) throws Exception {

        Context context = new InitialContext();
        FormContribFacade formContribFacade = (FormContribFacade) context.lookup(FormContribFacade.class.getName());

        RecaudadorFormularioFacade recFormFacade = (RecaudadorFormularioFacade) context.lookup(RecaudadorFormularioFacade.class.getName());

        if (formContribFacade.total(new FormContrib(idFormContrib)) == 0) {
            return null;
        }

        Map<String, Object> atributos = formContribFacade.get(new FormContrib(idFormContrib), new String[]{
            "usuarioTerminalCarga.terminal.sucursal.recaudador.descripcion",
            "usuarioTerminalCarga.terminal.sucursal.recaudador.idRecaudador",
            "usuarioTerminalCarga.terminal.sucursal.recaudador.entidad.rucCi",
            "numeroOrden", "ruc", "digitoVerificador", "fechaHoraRealCertificado", "fechaCertificadoSet",
            "usuarioTerminalCarga.terminal.sucursal.recaudador.red.codEra",
            "usuarioTerminalCarga.terminal.sucursal.codigoSucursalSet",
            "grupo.gestion.terminal.codigoCajaSet",
            "usuarioTerminalCarga.usuario.nombreUsuario",
            "codigoHash",
            "consecutivo",
            "periodoFiscal",
            "formulario.numeroFormulario",
            "camposValidos"
        });

        String cabeceraCertificacion = new String();
        String bodyCertificacion = new String();
        String pieCertificacion = new String();
        SimpleDateFormat formatterFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterHora = new SimpleDateFormat("HH:mm:ss");

        RecaudadorFormulario recForm = recFormFacade.get((Long) atributos.get("usuarioTerminalCarga.terminal.sucursal.recaudador.idRecaudador"));

        bodyCertificacion += "R-" + atributos.get("usuarioTerminalCarga.terminal.sucursal.recaudador.descripcion")
                + " RUC:" + atributos.get("usuarioTerminalCarga.terminal.sucursal.recaudador.entidad.rucCi") + "\n\n";
        bodyCertificacion += "Nro. Orden:" + atributos.get("numeroOrden") + " RUC:" + atributos.get("ruc")
                + "-" + atributos.get("digitoVerificador") + "\n\n";
        bodyCertificacion += "Fecha y Hora: " + formatterFecha.format(atributos.get("fechaCertificadoSet")) + " - " + formatterHora.format(atributos.get("fechaHoraRealCertificado")) + "\n\n";
        bodyCertificacion += "ERA: " + atributos.get("usuarioTerminalCarga.terminal.sucursal.recaudador.red.codEra");
        bodyCertificacion += " Suc. SET:" + atributos.get("usuarioTerminalCarga.terminal.sucursal.codigoSucursalSet");
        bodyCertificacion += " Val.:" + atributos.get("grupo.gestion.terminal.codigoCajaSet") + "," + atributos.get("usuarioTerminalCarga.usuario.nombreUsuario") + "\n\n";
        bodyCertificacion += "Hash: " + atributos.get("codigoHash");
        bodyCertificacion += " Consecutivo:" + atributos.get("consecutivo") + "\n\n";
        bodyCertificacion += "Periodo:" + atributos.get("periodoFiscal");
        bodyCertificacion += " Formul:" + atributos.get("formulario.numeroFormulario") + "\n\n";
        pieCertificacion = "\"VALIDO COMO COMPROBANTE DE PAGO,\n\nGs. " + NumberFormat.getInstance().format(recForm.getMonto()) + " IVA INCLUIDO\"" + "\n\n";

        String retorno = cabeceraCertificacion + bodyCertificacion + pieCertificacion;
        String pantalla = "";
        String certificacion = "";

        if (atributos.get("camposValidos").equals(1)) {
            for (String s : retorno.split("\n\n")) {
                certificacion += 2 + ";;" + "N" + ";;" + s + ";;;";
                pantalla += s + "<br/>";
            }
        }

        Map<String, String> resultado = new HashMap<String, String>();
        resultado.put("pantalla", pantalla);
        resultado.put("certificacion", certificacion);

        return resultado;
    }

    public static String generarCertificacionFormularioTransa(Transaccion transa, Integer era) {

        String cabeceraCertificacion = new String();
        String bodyCertificacion = new String();
        String pieCertificacion = new String();

        SimpleDateFormat formatterFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterHora = new SimpleDateFormat("HH:mm:ss");

        bodyCertificacion += transa.getGestion().getTerminal().getSucursal().getRecaudador().getDescripcion() + " RUC:" + transa.getGestion().getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi() + "\n\n";
        bodyCertificacion += "Nro. Orden:" + transa.getFormContrib().getNumeroOrden() + "  RUC.: " + transa.getFormContrib().getRuc() + "-" + transa.getFormContrib().getDigitoVerificador() + "\n\n";
        bodyCertificacion += "Fecha y Hora:" + formatterFecha.format(transa.getFormContrib().getFechaCertificadoSet()) + " - " + formatterHora.format(transa.getFormContrib().getFechaHoraRealCertificado()) + "\n\n";
        bodyCertificacion += "ERA:" + era;
        bodyCertificacion += " Suc. SET:" + transa.getFormContrib().getGrupo().getGestion().getTerminal().getSucursal().getCodigoSucursalSet();
        bodyCertificacion += " Val.:" + transa.getFormContrib().getGrupo().getGestion().getTerminal().getCodigoCajaSet() + "," + transa.getGestion().getUsuario().getNombreUsuario() + "\n\n";
        bodyCertificacion += "Hash: " + transa.getFormContrib().getCodigoHash();
        bodyCertificacion += " Consecutivo:" + transa.getFormContrib().getConsecutivo() + "\n\n";
        bodyCertificacion += "Periodo:" + transa.getFormContrib().getPeriodoFiscal();
        //bodyCertificacion += " Formul.:" + transa.getFormContrib().getFormulario().getNumeroFormulario() + " - " + transa.getFormContrib().getFormulario().getDescripcion() + "\n\n";
        bodyCertificacion += " Formul.:" + transa.getFormContrib().getFormulario().getNumeroFormulario() + "\n\n";
        pieCertificacion = "\"No es Valido como Comprobante de Pago\"" + "\n\n";

        return cabeceraCertificacion + bodyCertificacion + pieCertificacion;

    }

    public static String generarCertificacionBoletaPagoTransa(Transaccion transa, Integer era) {

        String cabeceraCertificacion = new String();
        String bodyCertificacion = new String();
        String pieCertificacion = new String();
        SimpleDateFormat formatterFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterHora = new SimpleDateFormat("HH:mm:ss");

        //Armar la certificacion
        bodyCertificacion += transa.getGestion().getTerminal().getSucursal().getRecaudador().getDescripcion() + " RUC:" + transa.getGestion().getTerminal().getSucursal().getRecaudador().getEntidad().getRucCi() + "\n\n";
        bodyCertificacion += "Nro. Boleta:" + transa.getBoletaPagoContrib().getNumeroOrden() + " RUC:" + transa.getBoletaPagoContrib().getContribuyente().getRucNuevo() + "-" + transa.getBoletaPagoContrib().getContribuyente().getDigitoVerificador() + "\n\n";
        bodyCertificacion += "Fecha y Hora:" + formatterFecha.format(transa.getBoletaPagoContrib().getFechaCobro()) + " - " + formatterHora.format(transa.getBoletaPagoContrib().getFechaHoraRecepcion()) + "\n\n";
        bodyCertificacion += "ERA:" + era;
        bodyCertificacion += " Suc. SET:" + transa.getGestion().getTerminal().getSucursal().getCodigoSucursalSet();
        //bodyCertificacion += " Caja SET:" + transa.getGestion().getTerminal().getCodigoCajaSet() + "\n\n";
        // bodyCertificacion += " Terminal:" + transa.getGestion().getTerminal().getCodigoTerminal() + "\n\n";
        bodyCertificacion += " Caj.:" + transa.getGestion().getTerminal().getCodigoCajaSet() + "," + transa.getGestion().getUsuario().getNombreUsuario() + "\n\n";
        bodyCertificacion += "Importe:" + formatearNumerosSeparadorMiles(transa.getBoletaPagoContrib().getTotal(), false);
        bodyCertificacion += " Cons.:" + transa.getBoletaPagoContrib().getConsecutivo() + "\n\n";
        bodyCertificacion += "Hash:" + transa.getBoletaPagoContrib().getCodigoHash();

//        if (transa.getFormContrib() != null) {
//            bodyCertificacion += "Periodo:" + transa.getFormContrib().getPeriodoFiscal();
//        }
        //bodyCertificacion += "IMP:" + transa.getBoletaPagoContrib().getImpuesto().getNumero() + "  " + transa.getBoletaPagoContrib().getImpuesto().getSigla();
        bodyCertificacion += " IMP:" + transa.getBoletaPagoContrib().getImpuesto().getNumero();
        bodyCertificacion += " Res:" + transa.getBoletaPagoContrib().getNumeroResolucion() + "\n\n";

        pieCertificacion = "\"No es Valido como Comprobante de Pago\"" + "\n\n";

        return cabeceraCertificacion + bodyCertificacion + pieCertificacion;

    }

    public static Long ERANumeroOrden(Long codigoERA, Long secuenciaNumeroOrden) {
        /*
         * long resultado=codigoERA; for(int i=0; i<8; i++){ resultado*=10; }
         */
        return codigoERA * 100000000 + secuenciaNumeroOrden;

    }

    public static String getSha1(String pass) {

        byte[] defaultBytes = pass.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA1");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            String foo = messageDigest.toString();
            return hexString.toString();

        } catch (NoSuchAlgorithmException nsae) {

            return "";
        }

    }

    public static String getNVU(String cadenaTicket) {
        String nvu = cadenaTicket;

        nvu = getSha1(nvu);
        nvu = getAdler(nvu.getBytes());

        return nvu;

    }

//    public static void main(String args[]) throws ParseException {
//        //System.out.println(getHashSet("47000000062012001211020092354902    5000100000000000000000100040000000000002009090113000000000000050000011400000000000005000001150000000000000500000118000000000000050000"));
//        //TimeZone.setDefault();
//        Calendar calendarVenc = Calendar.getInstance();
//        calendarVenc.setTimeZone(TimeZone.getTimeZone("America/Quito"));
//        calendarVenc.setTime(new SimpleDateFormat("ddMMyyyy").parse("15042005"));
//        //Date d=new SimpleDateFormat("ddMMyyyy").parse("15042005");
//        //System.out.println(d);
//        Calendar calendarPresentacion = Calendar.getInstance();
//        calendarPresentacion.setTimeZone(TimeZone.getTimeZone("America/Quito"));
//        calendarPresentacion.setTime(new SimpleDateFormat("ddMMyyyy").parse("28012010"));
//
//        System.out.println(calendarPresentacion.getTime());
//
//
//        System.out.println(calendarVenc.getTime());
//        Long milisPorDia = 24L * 60 * 60 * 1000;
//        Double res = (double) (calendarPresentacion.getTime().getTime() - calendarVenc.getTime().getTime()) / (double) milisPorDia;
//        System.out.println("res " + res);
//        Long diasAtrasos = (calendarPresentacion.getTime().getTime() - calendarVenc.getTime().getTime()) / milisPorDia;
//        System.out.println("noanda " + diasAtrasos);
//
//        res = Math.ceil(res);
//        System.out.println("res " + res);
//
//        //calendarVenc=Calendar.getInstance();
//        calendarVenc.setTime(new SimpleDateFormat("ddMMyyyy").parse("25012010"));
//        System.out.println(calendarVenc.getTime());
//        diasAtrasos = (calendarPresentacion.getTime().getTime() - calendarVenc.getTime().getTime()) / milisPorDia;
//        System.out.println("anda " + diasAtrasos);
//        res = (double) (calendarPresentacion.getTime().getTime() - calendarVenc.getTime().getTime()) / (double) milisPorDia;
//        System.out.println("res " + res);
//        res = Math.ceil(res);
//        System.out.println("res " + res);
//
//
//
//
//        /*
//        FormContrib fc = new FormContrib();
//        Formulario f = new Formulario();
//        f.setNumeroFormulario(120);
//        f.setVersion(1);
//        fc.setNumeroOrden(47000000062L);
//        fc.setIdFormulario(f);
//        Format formatter = new SimpleDateFormat("ddMMyyyy");
//        fc.setFechaPresentacion((Date) formatter.parseObject("21102009"));
//        fc.setRuc("2354902");
//        fc.setDigitoVerificador("5");
//
//
//
//        List<CamposFormContrib> lista = new ArrayList<CamposFormContrib>();
//
//        CamposFormContrib cfc = new CamposFormContrib();
//        cfc.setEtiqueta("1");
//        cfc.setValor("1");
//        lista.add(cfc);
//        cfc = new CamposFormContrib();
//        cfc.setEtiqueta("4");
//        cfc.setValor("200909");
//        lista.add(cfc);
//        cfc = new CamposFormContrib();
//        cfc.setEtiqueta("113");
//        cfc.setValor("50000");
//        lista.add(cfc);
//        cfc = new CamposFormContrib();
//        cfc.setEtiqueta("114");
//        cfc.setValor("50000");
//        lista.add(cfc);
//        cfc = new CamposFormContrib();
//        cfc.setEtiqueta("115");
//        cfc.setValor("50000");
//        lista.add(cfc);
//        cfc = new CamposFormContrib();
//        cfc.setEtiqueta("118");
//        cfc.setValor("50000");
//        lista.add(cfc);
//        fc.setCamposFormContribCollection(lista);
//        String cadenaHash = fc.toStringHash();
//        System.out.println(cadenaHash);
//        fc.setCodigoHash(UtilesSet.getHashSet(cadenaHash));
//        System.out.println(fc.getCodigoHash());
//        System.out.println(UtilesSet.ERANumeroOrden(22L, 65L));*/
//    }
    public static Integer MODULO11(String p_numero) {
        int p_basemax = 11;
        int v_total;
        int v_resto;
        int k;
        int v_numero_aux;
        String v_numero_al = "";
        char v_caracter;
        int v_digit;

        for (int i = 0; i < p_numero.length(); i++) {
            v_caracter = p_numero.charAt(i);
            if (!(48 <= v_caracter && v_caracter <= 57)) {
                v_numero_al += ((int) v_caracter);
            } else {
                v_numero_al += Integer.parseInt(v_caracter + "");
            }
        }

        k = 2;
        v_total = 0;

        for (int i = v_numero_al.length() - 1; i >= 0; i--) {
            if (k > p_basemax) {
                k = 2;
            }
            v_numero_aux = Integer.parseInt(v_numero_al.substring(i, i + 1));
            v_total = v_total + (v_numero_aux * k);
            k = k + 1;
        }

        v_resto = v_total % 11;
        if (v_resto > 1) {
            v_digit = 11 - v_resto;
        } else {
            v_digit = 0;
        }
        return v_digit;
    }

    public static String calcularCodigoHash(String pContenido) throws NoSuchAlgorithmException {
        Hash md = Hash.getInstance();
        String valorHash = md.getAdler(md.getHash(pContenido.getBytes()).getBytes());
        return valorHash;
    }

    public static String toXml(Object element) {
        try {
            JAXBContext jc = JAXBContext.newInstance(element.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(element, baos);
            return baos.toString().replaceAll("\\{", "<").replaceAll("\\}", ">").replaceAll("</c>", "").replaceAll("<c>", "");
        } catch (Exception ex) {
            log.logp(Level.SEVERE, "UtilesSet", "toXml", ex.getMessage(), ex);
        }
        return "";
    }

    public static <T extends Object> T toObject(String xml, Class<T> clase) {
        try {
            StringReader inputXML = new StringReader(xml);
            Object object = JAXBContext.newInstance(clase).createUnmarshaller().unmarshal(inputXML);
            return clase.cast(object);
        } catch (Exception ex) {
            log.logp(Level.SEVERE, "UtilesSet", "toXml", ex.getMessage(), ex);
            return null;
        }
    }

    public static String calcularDVOT(String nroOt) {
        Integer vTotal;
        Integer vResto;
        Integer k;
        Integer vNumeroAux;
        String vNumeroAl = "";
        char vCaracter;
        Integer vDigit;
        int i;
        for (i = 0; i < nroOt.length(); i++) {
            vCaracter = nroOt.charAt(i);
            int ascii = (int) vCaracter;
            if (ascii < 48 && ascii > 57) {
                vNumeroAl = vNumeroAl + String.valueOf(ascii);
            } else {
                vNumeroAl = vNumeroAl + vCaracter;
            }
        }
        k = 2;
        vTotal = 0;
        for (; i > 0; i--) {
            if (k > 11) {
                k = 2;
            }
            vNumeroAux = Integer.parseInt(String.valueOf(vNumeroAl.charAt(i - 1)));
            vTotal = vTotal + (vNumeroAux * k);
            k++;
        }
        vResto = vTotal % 11;
        if (vResto > 1) {
            vDigit = 11 - vResto;
        } else {
            vDigit = 0;
        }

        return String.valueOf(vDigit);
    }
}
