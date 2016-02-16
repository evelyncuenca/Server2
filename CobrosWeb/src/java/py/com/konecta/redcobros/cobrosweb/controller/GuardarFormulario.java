/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import py.com.konecta.redcobros.entities.Contribuyentes;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.json.simple.JSONObject;
import py.com.documenta.ws.Autenticacion;
import py.com.documenta.ws.RedCobro;
import py.com.documenta.ws.RedCobroService;
import py.com.konecta.redcobros.cobrosweb.utiles.StringUtils;
import py.com.konecta.redcobros.cobrosweb.utiles.Utiles;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.entities.*;
import py.com.konecta.redcobros.utiles.UtilesSet;

/**
 *
 * @author konecta
 */
public class GuardarFormulario extends HttpServlet {

    @EJB
    FormContribFacade formContribFacade;
    @EJB
    FormularioFacade formularioFacade;
    @EJB
    ContribuyentesFacade contribuyentesFacade;
    @EJB
    UtilFacade utilFacade;
    @EJB
    private GestionFacade gestionFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private RegistroImpWebFacade registroImpWebFacade;
    @EJB
    private RuteoServicioFacade ruteoServicioFacade;
    @EJB
    private TransaccionRcFacade transaccionRcFacade;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    private static RedCobroService service = null;
    private static ReentrantLock lock = new ReentrantLock();

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

    private void saveRegister(RegistroImpWeb registro) {
        try {
            registroImpWebFacade.save(registro);
        } catch (Exception ex) {
            Logger.getLogger(GESTOR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeOnUserFile(HttpSession session, String idTrx) {
        try {
            
            String conciliar = (String) session.getAttribute("conciliarCaja");
            
            if (conciliar != null && conciliar.equalsIgnoreCase("S")) {
                Long codExtUsuario = (Long) session.getAttribute("codExtUsuario");
                TransaccionRc transRc = transaccionRcFacade.get(new BigDecimal(idTrx));
                ParametroSistema paramFile = new ParametroSistema();
                paramFile.setNombreParametro("pathFileUser");
                String pathFile = parametroSistemaFacade.get(paramFile).getValor();
                Utiles.writeToFile(pathFile, codExtUsuario, transRc, true);
            } 
            
        } catch (Exception ex) {
            Logger.getLogger(COBRO_SERVICIOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");

        String op = request.getParameter("op");
        JSONObject json = new JSONObject();
        PrintWriter out = response.getWriter();
        if (op.equalsIgnoreCase("GUARDAR_FORMULARIO")) {

            try {
                int minimo = new Integer(request.getParameter("minimoCampo"));
                int maximo = new Integer(request.getParameter("maximoCampo"));
                int numeroFormulario = new Integer(request.getParameter("formulario"));
                String gestorCI = request.getParameter("gestorCI") != null && request.getParameter("gestorCI").matches("[0-9]+") ? request.getParameter("gestorCI") : null;
                String decJuradaOrig = null;
                String decJuradaRectif = null;
                String decNumeroRectif = null;
                String decJuradaClausuraCese = null;
                String periodoFiscal = request.getParameter("periodoFiscal");
                String ruc = request.getParameter("ruc");
                String digitoVerificador = request.getParameter("dv");
                String camposValidos = request.getParameter("validado");
                String fechaPresentacion = request.getParameter("fechaPresentacion");

                if (numeroFormulario != 90) {
                    decJuradaOrig = request.getParameter("decJuradaOrig");
                    decJuradaRectif = request.getParameter("decJuradaRectif");
                    decNumeroRectif = request.getParameter("decNumeroRectif");
                    decJuradaClausuraCese = request.getParameter("decJuradaClausuraCese");

                }
                int version = new Integer(request.getParameter("version"));
                Formulario formulario = new Formulario();
                formulario.setNumeroFormulario(numeroFormulario);
                formulario.setVersion(version);
                List<Formulario> lFormulario = formularioFacade.list(formulario);

                if (lFormulario.size() == 1) {
                    formulario = lFormulario.get(0);
                } else {
                    throw new Exception("No existe el formulario para " + numeroFormulario + " y " + version);
                }
                Contribuyentes ejemplo = new Contribuyentes();
                ejemplo.setRucNuevo(ruc);
                ejemplo.setDigitoVerificador(digitoVerificador);

                String idCampo;
                FormContrib fc = new FormContrib();
                if (gestorCI != null) {
                    fc.setIdGestor(gestorCI);
                }
                fc.setRuc(ruc);
                fc.setDigitoVerificador(digitoVerificador);
                fc.setCamposValidos(Integer.parseInt(camposValidos));
                fc.setTotalPagar(new Integer(request.getParameter("C" + formulario.getCampoPagar())));
                fc.setCertificado("N");
                fc.setFormulario(formulario);
                fc.setPagado(2);
                if (periodoFiscal != null) {

                    periodoFiscal = periodoFiscal.replace(",", "");

                    if (periodoFiscal.length() == 8) {
                        periodoFiscal = periodoFiscal.substring(2, 4) + "/" + periodoFiscal.substring(4);
                    }

                    fc.setPeriodoFiscal(periodoFiscal);
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date d = sdf.parse(fechaPresentacion);
                fc.setFechaPresentacion(d);
                fc.setFechaHoraRecepcion(new Date());
                //CANDIDATO A SALIR - COSA RARA.
                //UsuarioTerminal ut = new UsuarioTerminal();
                //Terminal ter = termF.get(idTerminal);
                //ut.setTerminal(ter);
                //Usuario usu = usuaF.get(idUsuario);
                //ut.setUsuario(usu);
                //ut = usuaTermF.get(ut);

                fc.setUsuarioTerminalCarga((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal"));

                /*
                 * Long numeroOrdenConERA =
                 * UtilesSet.ERANumeroOrden(codERA.longValue(),
                 * utilFacade.getProximoValorOrden());
                 * fc.setNumeroOrden(numeroOrdenConERA);
                 *
                 * //consecutivo // usuario en terminal
                 *
                 * Long consecutivoActual = ut.getConsecutivoActualSet();
                 * fc.setConsecutivo(consecutivoActual);
                 * ut.setConsecutivoActualSet(consecutivoActual + 1);
                 * usuaTermF.merge(ut);
                 */
                List<Contribuyentes> lc = contribuyentesFacade.list(ejemplo);
                fc.setContribuyente(lc.get(0));
                List<CamposFormContrib> lista = new ArrayList<CamposFormContrib>();
                CamposFormContrib cfc;
                if (decJuradaOrig != null) {
                    if (formulario.getNumDecOrig() != null && !decJuradaOrig.equalsIgnoreCase("0")) {
                        cfc = new CamposFormContrib();
                        cfc.setEtiqueta("" + formulario.getNumDecOrig());
                        cfc.setNumeroCampo(formulario.getNumDecOrig());
                        cfc.setValor("" + formulario.getValorDecOrig());
                        cfc.setFormContrib(fc);
                        lista.add(cfc);
                    }
                }

                if (decJuradaRectif != null) {
                    if (formulario.getNumDecRectif() != null && !decJuradaRectif.equalsIgnoreCase("0")) {
                        cfc = new CamposFormContrib();
                        cfc.setEtiqueta("" + formulario.getNumDecRectif());
                        cfc.setNumeroCampo(+formulario.getNumDecRectif());
                        cfc.setValor("" + formulario.getValorDecRectif());
                        cfc.setFormContrib(fc);
                        lista.add(cfc);
                    }
                }

                if (decNumeroRectif != null) {
                    if (formulario.getNumNumDecRectif() != null && !decNumeroRectif.equalsIgnoreCase("0")) {
                        cfc = new CamposFormContrib();
                        cfc.setEtiqueta("" + formulario.getNumNumDecRectif());
                        cfc.setNumeroCampo(formulario.getNumNumDecRectif());
                        cfc.setValor(decNumeroRectif);
                        cfc.setFormContrib(fc);
                        lista.add(cfc);
                    }
                }

                if (decJuradaClausuraCese != null) {
                    if (formulario.getNumDecClausura() != null && !decJuradaClausuraCese.equalsIgnoreCase("0")) {
                        cfc = new CamposFormContrib();
                        cfc.setEtiqueta("" + formulario.getNumDecClausura());
                        cfc.setNumeroCampo(formulario.getNumDecClausura());
                        cfc.setValor("" + formulario.getValorDecClausura());
                        cfc.setFormContrib(fc);
                        lista.add(cfc);
                    }
                }

                if (periodoFiscal != null) {
                    cfc = new CamposFormContrib();
                    cfc.setEtiqueta("" + formulario.getNumPeriodoFiscal());
                    cfc.setNumeroCampo(formulario.getNumPeriodoFiscal());
                    String[] per = periodoFiscal.split("/");
                    String cadena = null;
                    if (per.length == 2) {
                        cadena = per[1] + per[0];
                    } else {
                        cadena = per[0];
                    }
                    cfc.setValor(cadena);
                    cfc.setFormContrib(fc);
                    lista.add(cfc);
                }
                boolean hasDetail = false;
                if (numeroFormulario == 90) {
                    for (int i = 10; i <= 14; i++) {
                        idCampo = "C" + i;
                        String valor = (request.getParameter(idCampo) != null && !request.getParameter(idCampo).equals("")) ? request.getParameter(idCampo) : null;

                        if (valor != null && !StringUtils.ltrim(valor, '0').equals("")) {
                            hasDetail = true;
                            cfc = new CamposFormContrib();
                            cfc.setEtiqueta("" + i);
                            cfc.setNumeroCampo(i);
                            cfc.setValor(valor);
                            //cfc.setFormContrib(fc);
                            lista.add(cfc);
                        }

                    }
                }

                if (numeroFormulario != 90) {
                    for (int i = minimo; i <= maximo; i++) {
                        idCampo = "C" + i;
                        String valor = (request.getParameter(idCampo) != null && !request.getParameter(idCampo).equals("")) ? request.getParameter(idCampo) : null;
                        if (valor != null && !StringUtils.ltrim(valor, '0').equals("")) {
                            hasDetail = true;
                            cfc = new CamposFormContrib();
                            cfc.setEtiqueta("" + i);
                            cfc.setNumeroCampo(i);
                            cfc.setValor(valor);
                            //cfc.setFormContrib(fc);
                            lista.add(cfc);
                        }

                    }
                } else {
                    for (int i = minimo; i <= maximo; i++) {
                        idCampo = "C" + i;

                        String valor = (request.getParameter(idCampo) != null && !request.getParameter(idCampo).equals("")) ? request.getParameter(idCampo) : null;
                        if (idCampo.equals("C19") || idCampo.equals("C29") || idCampo.equals("C33") || idCampo.equals("C21")) {
                            valor = valor.replace(",", "");
                            if (valor.length() == 8) {
                                valor = valor.substring(4) + valor.substring(2, 4) + valor.substring(0, 2);
                            }
                        }
                        if (valor != null && !StringUtils.ltrim(valor, '0').equals("")) {
                            hasDetail = true;
                            cfc = new CamposFormContrib();
                            cfc.setEtiqueta("" + i);
                            cfc.setNumeroCampo(i);
                            cfc.setValor(valor);
                            //cfc.setFormContrib(fc);
                            lista.add(cfc);
                        }

                    }
                }
                // fc.setCamposFormContribCollection(lista);

                try {
                    if (hasDetail) {
                        fc = formContribFacade.merge(fc, lista, ((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")).getTerminal());
                        json.put("codigo", fc.getIdFormContrib());
                        json.put("success", true);
                    } else {
                        json.put("motivo", "No se puede presentar formularios sin movimiento");
                        json.put("success", false);
                    }

                } catch (Exception e) {
                    json.put("success", false);
                    json.put("motivo", e.getMessage());
                }
                out.println(json);


            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                //out.close();
            }

        } else if (op.equalsIgnoreCase("ANULAR_DIGITACION")) {
            // if (request.getParameter("REFERENCIA") != null && request.getParameter("REFERENCIA").matches("[0-9]+") && request.getParameter("PASSWORD") != null && !request.getParameter("PASSWORD").isEmpty() && !request.getParameter("PASSWORD").equalsIgnoreCase("null") && (request.getSession().getAttribute("contrasenha").equals(inicial.getSha1(request.getParameter("PASSWORD"))))) {
            if (request.getParameter("REFERENCIA") != null && request.getParameter("REFERENCIA").matches("[0-9]+") && usuarioFacade.esSupervisor((Long) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                FormContrib formContribExample = new FormContrib();
                formContribExample.setNumeroOrden(new Long(request.getParameter("REFERENCIA")));
                // FormContrib formulario = formContribFacade.get(Integer.parseInt(request.getParameter("REFERENCIA")));
                FormContrib formulario = formContribFacade.get(formContribExample);
                if (formulario != null) {
                    if (gestionFacade.tieneGestionesAbiertas(((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")), new Date())) {

                        if (((formulario.getGrupo() == null) || (formulario.getGrupo() != null && formulario.getGrupo().getProcesado().equalsIgnoreCase("N"))) && formulario.getCamposValidos() == 1 && (formulario.getCertificado().equalsIgnoreCase("S"))) {
                            HttpSession session = request.getSession();
                            String motivoAnulacion = request.getParameter("motivoAnulacion") != null && !request.getParameter("motivoAnulacion").isEmpty() ? request.getParameter("motivoAnulacion") : "Sin motivo";

                            RuteoServicio ruteoRC = (RuteoServicio) session.getAttribute("ruteoServicio");

                            try {
                                if (ruteoRC == null) {
                                    ruteoRC = ruteoServicioFacade.get(2L);
                                    session.setAttribute("ruteoServicio", ruteoRC);
                                }
                                Logger.getLogger(GuardarFormulario.class.getName()).log(Level.INFO, "URL:{0}", ruteoRC.getUrlHost());
                            } catch (Exception e) {
                                Logger.getLogger(GuardarFormulario.class.getName()).log(Level.SEVERE, null, e);
                            }

                            RedCobro rcService = getWSManager(ruteoRC.getUrlHost(), "http://ws.documenta.com.py/", "RedCobroService", ruteoRC.getConexionTo(), ruteoRC.getReadTo());

                            Autenticacion auth = new Autenticacion();
                            auth.setClave(session.getAttribute("contrasenha").toString());
                            auth.setHash(UtilesSet.getSha1(session.getAttribute("contrasenha").toString()));
                            auth.setIdGestion(new BigDecimal(formulario.getGrupo().getGestion().getIdGestion()));
                            auth.setIdUsuario(session.getAttribute("idUsuario").toString());

                            String respuestaAnulacion = rcService.anulacion(formulario.getTransaccionRc(), motivoAnulacion, auth);
                            String[] retorno = respuestaAnulacion.split(".jsp");

                            if (!retorno[0].equalsIgnoreCase("error")) {
                                if (formContribFacade.anularDigitacion(formulario, ((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")).getTerminal())) {

                                    Map<String, String> componentesDeImpresion = UtilesSet.getDetalleDigitacionAnular(formulario, (UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal"));
                                    json.put("ticket_pantalla", componentesDeImpresion.get("ticket_pantalla"));
                                    json.put("ticket", componentesDeImpresion.get("ticket"));
                                    json.put("success", true);
                                    
                                    String idTrx  = Utiles.getElementValue(Utiles.getElementValue(respuestaAnulacion, "[?]", 1), "=", 1);
                                    writeOnUserFile(session, idTrx);

                                    try {

                                        Long usuario = (Long) session.getAttribute("idUsuario");
                                        Long idTerminal = (Long) session.getAttribute("idTerminal");
                                        RegistroImpWeb registro = new RegistroImpWeb();
                                        registro.setDescripcion("Se anulo el formulario id " + formulario.getIdFormContrib() + " Nro. Orden[" + formulario.getNumeroOrden() + "]");
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
                                    json.put("success", false);
                                    json.put("motivo", "No se puede Anular esta Digitacion");
                                }
                            } else {
                                json.put("success", false);
                                json.put("motivo", "No se puede Anular esta Digitacion");
                            }
                        } else {
                            json.put("success", false);
                            json.put("motivo", "No se puede Anular esta Digitacion");
                        }
                    } else {
                        json.put("success", false);
                        json.put("motivo", "Debe Abrir la Caja para realizar la operacion");
                    }
                } else {
                    json.put("success", false);
                    json.put("motivo", "No  existe la referencia");
                }
            } else {
                json.put("success", false);
                json.put("motivo", "Los Parametros no son Correctos");
            }
            out.println(json);

        } else if (op.equalsIgnoreCase("DETALLE_DIGITACION_A_ANULAR")) {
            if (request.getParameter("REFERENCIA") != null && request.getParameter("REFERENCIA").matches("[0-9]+") && usuarioFacade.esSupervisor((Long) request.getSession().getAttribute("idUsuario"), request.getParameter("userSupervisor"), request.getParameter("passwordSupervisor"))) {
                FormContrib formContribExample = new FormContrib();
                formContribExample.setNumeroOrden(new Long(request.getParameter("REFERENCIA")));
                FormContrib formulario = formContribFacade.get(formContribExample);
                if (formulario != null) {
                    if (formulario.getMigrado() == null || formulario.getMigrado() != 'S') {
                        if (gestionFacade.tieneGestionesAbiertas(((UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal")), new Date())) {

                            if (((formulario.getGrupo() == null) || (formulario.getGrupo() != null && formulario.getGrupo().getProcesado().equalsIgnoreCase("N"))) && formulario.getCamposValidos() == 1 && (formulario.getCertificado().equalsIgnoreCase("S"))) {
                                Map<String, String> componentesDeImpresion = UtilesSet.getDetalleDigitacionAnular(formulario, (UsuarioTerminal) request.getSession().getAttribute("objUsuarioTerminal"));
                                json.put("ticket_pantalla", componentesDeImpresion.get("ticket_pantalla"));
                                json.put("ticket", componentesDeImpresion.get("ticket"));
                                json.put("success", true);
                            } else {
                                json.put("success", false);
                                json.put("motivo", "No se puede Anular esta Digitacion");
                            }
                        } else {
                            json.put("success", false);
                            json.put("motivo", "Debe Abrir la Caja para realizar la operacion");
                        }
                    } else {
                        json.put("success", false);
                        json.put("motivo", "No se puede anular, operacion ya migrada ");
                    }
                } else {
                    json.put("success", false);
                    json.put("motivo", "No  existe la referencia");
                }

            } else {
                json.put("success", false);
                json.put("motivo", "Los parametros no son correctos");
            }
            out.print(json);
        } else {
            json.put("success", false);
            json.put("motivo", "No  existe la opcion pedida");
            out.print(json);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(GuardarFormulario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(GuardarFormulario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
