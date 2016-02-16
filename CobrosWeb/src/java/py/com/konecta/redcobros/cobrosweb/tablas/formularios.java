/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.tablas;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.CamposFormContribFacade;
import py.com.konecta.redcobros.ejb.ContribuyentesBloqueadosFacade;
import py.com.konecta.redcobros.ejb.ContribuyentesFacade;
import py.com.konecta.redcobros.ejb.FormContribFacade;
import py.com.konecta.redcobros.ejb.FormularioFacade;
import py.com.konecta.redcobros.ejb.FormularioImpuestoFacade;
import py.com.konecta.redcobros.ejb.ModContribuyentesAceptadosFacade;
import py.com.konecta.redcobros.ejb.UtilFacade;
import py.com.konecta.redcobros.entities.CamposFormContrib;
import py.com.konecta.redcobros.entities.Contribuyentes;
import py.com.konecta.redcobros.entities.ContribuyentesBloqueados;
import py.com.konecta.redcobros.entities.FormContrib;
import py.com.konecta.redcobros.entities.Formulario;
import py.com.konecta.redcobros.entities.FormularioImpuesto;
import py.com.konecta.redcobros.entities.ModContribAceptados;

/**
 *
 * @author konecta
 */
public class formularios extends HttpServlet {

    @EJB
    private UtilFacade utilFacade;
    @EJB
    private FormularioFacade formularioFacade;
    @EJB
    private ContribuyentesFacade contribuyentesFacade;
    @EJB
    private ModContribuyentesAceptadosFacade modContribuyentesAceptadosFacade;
    @EJB
    private FormularioImpuestoFacade formularioImpuestoFacade;
    @EJB
    private FormContribFacade formContribFacade;
    @EJB
    private CamposFormContribFacade camposContribFacade;
    @EJB
    private ContribuyentesBloqueadosFacade contribuyentesBloqFacade;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Integer version = null;
        try {
            JSONObject json = new JSONObject();
            JSONObject jsonFinal = new JSONObject();
            JSONObject configuracion = new JSONObject();
            JSONArray arrayJson = new JSONArray();



            String op = request.getParameter("op");
            if (op.equalsIgnoreCase("RUC-LLENAR_CAMPOS")) {
                Formulario formularioExample = new Formulario();
                formularioExample.setVersionDefault(1);
                formularioExample.setNumeroFormulario(new Integer(request.getParameter("idFormulario")));

                List<Formulario> lcabecera = formularioFacade.list(formularioExample);
//
                // List<Formulario> lcabecera = schemaQ.getLimitLogFormulario(10, 0, " where e.numeroFormulario = " + request.getParameter("idFormulario"), "e.numeroFormulario", "asc", "e");

                if (lcabecera.size() > 0) {
                    ContribuyentesBloqueados contBloqueados = contribuyentesBloqFacade.get(request.getParameter("CC4"));
                    if (contBloqueados != null) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Este Contribuyente esta afectado por la resolución general Nº 49 de la SET. Tiene la obligación de presentar su Declaración via Internet.");
                    } else {
                        /**
                         * *************DESPUES DE ELIMNAR CABECERA
                         * FORMULARIO*****************
                         */
                        Contribuyentes contribuyente = contribuyentesFacade.getContribuyentePorRuc(request.getParameter("CC4"), request.getParameter("CC4"), request.getParameter("CC6"));
                        // List<Contribuyentes> listaContribuyente = schemaQ.getLimitLogContribuyentes(10, 0, "where (e.rucNuevo = '" + request.getParameter("CC4") + "'" + " or e.rucAnterior = '" + request.getParameter("CC4") + "') AND e.digitoVerificador='" + request.getParameter("CC6") + "' ", "e.idContribuyente", "asc", "e");
                        if (contribuyente == null) {
                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", "No existe el Contribuyente");
                        } else {
                            if (contribuyente.getEstado() == null || contribuyente.getEstado().equalsIgnoreCase("ACTIVO")) {
                                if (contribuyente.getModalidadContribuyente() == 2 || contribuyente.getModalidadContribuyente() == 3) {
                                    json.put("campo", "CC8");
                                    json.put("valor", contribuyente.getNombreContribuyente());
                                    arrayJson.add(json.clone());

                                    jsonFinal.put("success", true);
                                    jsonFinal.put("total", 1);
                                    jsonFinal.put("items", arrayJson);

                                } else {
                                    jsonFinal.put("success", false);
                                    jsonFinal.put("motivo", "No se permite la modalidad Gran Contribuyente");
                                }

                                if (!contribuyente.getDigitoVerificador().equals(request.getParameter("CC6"))) {
                                    jsonFinal.put("success", false);
                                    jsonFinal.put("motivo", "Digito Verificador debe ser " + contribuyente.getDigitoVerificador());
                                }
                            } else {
                                jsonFinal.put("success", false);
                                jsonFinal.put("motivo", "Estado de Contribuyente: " + contribuyente.getEstado());
                            }
                        }
                    }
                }
                out.println(jsonFinal);

            } else if (op.equalsIgnoreCase("VALIDAR_PERIODO_FISCAL")) {
                //jsonFinal.put("success", false);//Por default no valida la cabecera
                if ((request.getParameter("CC1") != null) && (request.getParameter("CC1").matches("[0-9]+")) && (request.getParameter("CC4") != null) && (request.getParameter("CC1") != null) && (request.getParameter("CC9").matches("[0-9]{2}/[0-9]{4}"))) {
                    List<Formulario> lcabecera = formularioFacade.list(new Formulario(new Long(request.getParameter("idFormulario"))));
                    //  List<Formulario> lcabecera = schemaQ.getLimitLogFormulario(10, 0, " where e.numeroFormulario = " + request.getParameter("idFormulario"), "e.numeroFormulario", "asc", "e");
                    int contador = 0;
                    if (lcabecera.size() > 0) {
                        //Se hace asi hasta que se encuentre una manera generica de hacerlo.

                        /**
                         * **************DESPUES DE ELIMNAR CABECERA
                         * FOIRMULARIO****************************************************
                         */
                        List<ModContribAceptados> lmodAcptadas = modContribuyentesAceptadosFacade.list();
                        //List<ModContribuyentesAceptados> lmodAcptadas = schemaQ.getLimitLogModContribuyentesAceptados(100, 0, "", "e.idModContribuyenteAceptado", "asc", "e");
                        List<BigInteger> clientesAcptados = new ArrayList<BigInteger>();

                        for (ModContribAceptados oo : lmodAcptadas) {
                            clientesAcptados.add(oo.getModalidadContribuyente());
                        }
                        Contribuyentes contribuyente = contribuyentesFacade.getContribuyentePorRuc(request.getParameter("CC4"), request.getParameter("CC4"), request.getParameter("CC6"));
                        if (contribuyente != null) //for (Contribuyentes o : schemaQ.getLimitLogContribuyentes(10, 0, "where (e.rucNuevo = '" + request.getParameter("CC4") + "'" + " or e.rucAnterior = '" + request.getParameter("CC4") + "') AND e.digitoVerificador='" + request.getParameter("CC6") + "' ", "e.idContribuyente", "asc", "e")) {
                        {
                            if (!contribuyente.getDigitoVerificador().equals(request.getParameter("CC6"))) {//Cuando los digitos verificadores no son iguales
                                if ((contribuyente.getModalidadContribuyente() != null) && (clientesAcptados.contains(BigInteger.valueOf(contribuyente.getModalidadContribuyente().longValue())))) {
                                    // System.out.println(" *** "+o.getModalidadContribuyente());
                                    //Se permite este tipo de contribuyente. Se controla si el periodo es correcto
                                    /*
                                     * con schemaquery
                                     */
                                    /*
                                     * Map<String, String> resultado =
                                     * schemaQ.validarPeriodoFiscalDJ(lcabecera.get(0).getNumeroFormulario().intValue(),
                                     * Integer.parseInt(request.getParameter("CC1")),
                                     * request.getParameter("CC9"),
                                     * request.getParameter("CC7")); if
                                     * ((resultado != null) &&
                                     * (!resultado.isEmpty())) { if
                                     * (resultado.get("VALOR").equalsIgnoreCase("VALIDO"))
                                     * { jsonFinal.put("success", true); } else
                                     * { jsonFinal.put("success", false);
                                     * jsonFinal.put("motivo", "El periodo no es
                                     * valido"); } } else {
                                     * jsonFinal.put("success", false);
                                     * jsonFinal.put("motivo", "No se pudo
                                     * realizar la operacion");
                                     *
                                     * }
                                     */
                                    /*
                                     * con facade
                                     */
                                    String resultado;
                                    try {
                                        resultado = this.utilFacade.validarPeriodoFiscal("DJ", lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), request.getParameter("CC9"), request.getParameter("CC7"), null);
                                        if (resultado.equalsIgnoreCase("VALIDO")) {
                                            jsonFinal.put("success", true);
                                        } else {
                                            jsonFinal.put("success", false);
                                            jsonFinal.put("motivo", "El periodo no es valido");
                                        }
                                    } catch (Exception e) {
                                        jsonFinal.put("success", false);
                                        jsonFinal.put("motivo", e.getMessage());
                                        Logger.getLogger(formularios.class.getName()).log(Level.SEVERE, null, e);
                                    }



                                } else {
                                    jsonFinal.put("success", false);
                                    jsonFinal.put("motivo", "No se permite la modadlidad de Cliente: " + contribuyente.getModalidadContribuyente());
                                }
                            } else {
                                jsonFinal.put("success", false);
                                jsonFinal.put("motivo", "Digito verificador debe ser " + contribuyente.getDigitoVerificador());
                            }
                        }
                        //}

                        /**
                         * *************ANTES DE ELIMNAR CABECERA
                         * FORMULARIO*****************
                         */
//                        if (lcabecera.get(0).getIdCabeceraFormulario().getIdCabeceraFormulario().intValue() == 1) {
//
//                            List<ModContribuyentesAceptados> lmodAcptadas = modContribuyentesAceptadosFacade.list();
//                            //List<ModContribuyentesAceptados> lmodAcptadas = schemaQ.getLimitLogModContribuyentesAceptados(100, 0, "", "e.idModContribuyenteAceptado", "asc", "e");
//                            List<BigInteger> clientesAcptados = new ArrayList<BigInteger>();
//
//                            for (ModContribuyentesAceptados oo : lmodAcptadas) {
//                                clientesAcptados.add(oo.getModalidadContribuyente());
//                            }
//                            Contribuyentes contribuyente = contribuyentesFacade.getContribuyentePorRuc(request.getParameter("CC4"), request.getParameter("CC4"), request.getParameter("CC6"));
//                            if (contribuyente != null) //for (Contribuyentes o : schemaQ.getLimitLogContribuyentes(10, 0, "where (e.rucNuevo = '" + request.getParameter("CC4") + "'" + " or e.rucAnterior = '" + request.getParameter("CC4") + "') AND e.digitoVerificador='" + request.getParameter("CC6") + "' ", "e.idContribuyente", "asc", "e")) {
//                            {
//                                if ((contribuyente.getModalidadContribuyente() != null) && (clientesAcptados.contains(contribuyente.getModalidadContribuyente()))) {
//                                    // System.out.println(" *** "+o.getModalidadContribuyente());
//                                    //Se permite este tipo de contribuyente. Se controla si el periodo es correcto
//                                    /*con schemaquery*/
//                                    /*Map<String, String> resultado = schemaQ.validarPeriodoFiscalDJ(lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), request.getParameter("CC9"), request.getParameter("CC7"));
//                                    if ((resultado != null) && (!resultado.isEmpty())) {
//                                    if (resultado.get("VALOR").equalsIgnoreCase("VALIDO")) {
//                                    jsonFinal.put("success", true);
//                                    } else {
//                                    jsonFinal.put("success", false);
//                                    jsonFinal.put("motivo", "El periodo no es valido");
//                                    }
//                                    } else {
//                                    jsonFinal.put("success", false);
//                                    jsonFinal.put("motivo", "No se pudo realizar la operacion");
//
//                                    }*/
//                                    /*con facade*/
//                                    String resultado;
//                                    try {
//                                        resultado = this.utilFacade.validarPeriodoFiscal("DJ", lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), request.getParameter("CC9"), request.getParameter("CC7"), null);
//                                        if (resultado.equalsIgnoreCase("VALIDO")) {
//                                            jsonFinal.put("success", true);
//                                        } else {
//                                            jsonFinal.put("success", false);
//                                            jsonFinal.put("motivo", "El periodo no es valido");
//                                        }
//                                    } catch (Exception e) {
//                                        jsonFinal.put("success", false);
//                                        jsonFinal.put("motivo", e.getMessage());
//                                        e.printStackTrace();
//                                    }
//
//
//
//                                } else {
//                                    jsonFinal.put("success", false);
//                                    jsonFinal.put("motivo", "No se permite la modadlidad de Cliente: " + contribuyente.getModalidadContribuyente());
//                                }
//                            }
//                        //}
//                        } else {
//                            jsonFinal.put("success", false);
//                            jsonFinal.put("motivo", "No se pudo realizar la operación!");
//                        }
                        /**
                         * ******FIN ANTES DE ELIMINAR DEATLLE
                         * CABECERA**********
                         */
                    } else {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "No existe el formulario");
                    }
                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "Los parametros no son correctos");
                }
                out.println(jsonFinal);

            } else if (op.equalsIgnoreCase("BUSCAR_FORMULARIO")) {
                Formulario formularioExample = new Formulario();
                //formularioExample.setVersionDefault(1);
                formularioExample.setNumeroFormulario(new Integer(request.getParameter("numeroFormulario")));

                HttpSession session = request.getSession();

                boolean hasCodSucSET = (Boolean) session.getAttribute("hasCodSucSET");
                boolean hasCodCajaSET = (Boolean) session.getAttribute("hasCodCajaSET");
                if (hasCodSucSET) {
                    if (hasCodCajaSET) {
                        int cantidad = formularioFacade.total(formularioExample);
                        // List<Formulario> lcabecera = schemaQ.getLimitLogFormulario(10, 0, " where e.numeroFormulario = " + request.getParameter("numeroFormulario") + " and e.versionDefault = 1", "e.numeroFormulario", "asc", "e");
                        if (cantidad == 0) {
                            jsonFinal.put("success", false);
                            jsonFinal.put("motivo", "No existe el Formulario");

                        } else {
                            jsonFinal.put("success", true);
                        }

                    } else {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "La Terminal no tiene Codigo Caja SET para operar. Favor llamar a Documenta.");
                    }
                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "La Sucursal no tiene Codigo Sucursal SET para operar. Favor llamar a Documenta.");
                }
                out.println(jsonFinal);

            } else if (op.equalsIgnoreCase("VALIDAR_CABECERA")) {
                String perioFiscal = "";
                jsonFinal.put("success", false);

                Formulario forNum = new Formulario();


                try {
                    forNum = formularioFacade.obtenerFormularioNumeroForm(new Integer(request.getParameter("idFormulario")), request.getParameter("CC9"));
                    if (forNum == null) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Cabecera incorrecta, favor verificar periodo fiscal.");
                        out.print(jsonFinal);
                        return;
                    }
                } catch (Exception e) {
                    json.put("Error", e.getMessage());
                    arrayJson.add(json.clone());
                    Logger.getLogger(formularios.class.getName()).log(Level.SEVERE, null, e);
                }

                version = forNum.getVersion();

                ContribuyentesBloqueados contBloqueados = contribuyentesBloqFacade.get(request.getParameter("CC4"));
                if (contBloqueados != null) {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "Este Contribuyente esta afectado por la resolución general Nº 49 de la SET. Tiene la obligación de presentar su Declaración via Internet.");
                    out.print(jsonFinal);
                    return;
                }

                Contribuyentes contribuyentes = contribuyentesFacade.getContribuyentePorRuc(request.getParameter("CC4"), request.getParameter("CC4"), request.getParameter("CC6"));
                if (contribuyentes != null) {
                    if (!contribuyentes.getDigitoVerificador().equals(request.getParameter("CC6"))) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Digito verificador debe ser " + contribuyentes.getDigitoVerificador());
                        out.print(jsonFinal);
                        return;
                    }
                    if (contribuyentes.getEstado() != null && !contribuyentes.getEstado().equalsIgnoreCase("ACTIVO")) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Estado de Contribuyente: " + contribuyentes.getEstado());
                        out.print(jsonFinal);
                        return;
                    }
                }
//                CC1	VERSION
//                CC2	DECLARACION JURADA ORIGINAL
//                CC3	DECLARACION JURADA RECTIFICADA
//                CC4	RUC
//                CC5   VALOR DE DECLARACION JURADA RECTIFICADA
//                CC6	DV
//                CC7	FECHA PRESENTACION
//                CC8	DESCRIPCION CONTRIBUYENTE
//                CC9	PERIODO FISCAL
//                CC10  CLAUSURA O CESE
/*
                 * CC1	1 CC2	DECLARACION JURADA ORIGINAL--distinto CC4	2025547
                 * CC6	0 CC7	04/08/2010 CC9
                 */

                /*
                 * CC1	1 CC3	DECLARACION JURADA RECTIFICADA--distinto CC4
                 * 3204904 CC5	131--distinto CC6	8 CC7	04/08/2010 CC9	022010
                 */
                /*
                 * cc1 cc4 cc6 cc7 cc9
                 */
                /*
                 * CC1	1 CC10	CLAUSURA O CESE--distinto CC2	CC2--distinto CC4
                 * 3204904 CC6	8 CC7	04/08/2010 CC9	022010
                 */
                // FORMULARIOS QUE NO SON 90, 130 y 860
                if ((request.getParameter("CC6") != null) && !(request.getParameter("CC6").isEmpty())
                        && (request.getParameter("CC4") != null) && !(request.getParameter("CC4").isEmpty())
                        //&& (request.getParameter("CC1") != null) && !(request.getParameter("CC1").isEmpty())
                        && (request.getParameter("CC7") != null) && !(request.getParameter("CC7").isEmpty())
                        && (request.getParameter("idFormulario") != null) && !(request.getParameter("idFormulario").isEmpty())
                        && request.getParameter("idFormulario").matches("[0-9]+")
                        && Integer.parseInt(request.getParameter("idFormulario")) != 90
                        && Integer.parseInt(request.getParameter("idFormulario")) != 130
                        && Integer.parseInt(request.getParameter("idFormulario")) != 860) {


                    /*
                     * Verificar si es formulario repetido---DDJJ
                     */
                    FormContrib formContribEjemplo = new FormContrib();
                    Formulario formularioEjemplo = new Formulario();

                    //formularioEjemplo.setIdFormulario(new Long(request.getParameter("idFormulario")));


                    //Integer periodoFiscal = new Integer (request.getParameter("CC9"));

                    formularioEjemplo.setVersion(version);


                    formularioEjemplo.setNumeroFormulario(new Integer(request.getParameter("idFormulario")));

                    formContribEjemplo.setFormulario(formularioEjemplo);
                    formContribEjemplo.setDigitoVerificador(request.getParameter("CC6"));
                    formContribEjemplo.setRuc(request.getParameter("CC4"));

                    if (request.getParameter("CC9") != null && !request.getParameter("CC9").isEmpty()) {
                        perioFiscal = request.getParameter("CC9");

                        if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
                            if (perioFiscal.length() == 6) {
                                perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
                            }
                            formContribEjemplo.setPeriodoFiscal(perioFiscal);
                        }
                    }
                    if ((request.getParameter("CC5") == null || request.getParameter("CC5").isEmpty())
                            && request.getParameter("CC3") != null && !request.getParameter("CC3").isEmpty()) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "No se indico el número de Orden que rectifica.");
                        out.println(jsonFinal);
                        return;
                    }

                    if ((request.getParameter("CC3") == null)
                            && (request.getParameter("CC2") == null)
                            && (request.getParameter("CC10") != null || request.getParameter("CC10").isEmpty())) {
                        jsonFinal.put("success", false);
                        jsonFinal.put("motivo", "Marcar DDJJ Original <br> o DDJJ Rectificativa");
                        out.println(jsonFinal);
                        return;

                    }
                    if ((request.getParameter("CC2") != null) && !(request.getParameter("CC2").isEmpty())
                            && (request.getParameter("CC10") == null || request.getParameter("CC10").isEmpty())) {
                        //List<CamposFormContrib> camposFormContribCollection = new ArrayList<CamposFormContrib>();
                        // dec. jurada original
                        CamposFormContrib campoEjemplo = new CamposFormContrib();
                        campoEjemplo.setValor("1");
                        campoEjemplo.setNumeroCampo(1);
                        campoEjemplo.setEtiqueta("1");
                        campoEjemplo.setFormContrib(formContribEjemplo);
                        //camposFormContribCollection.add(campoEjemplo);

                        //formContribEjemplo.setCamposFormContribCollection(camposFormContribCollection);
                        int totalFC = camposContribFacade.total(campoEjemplo);
                        //int totalFC = formContribFacade.total(formContribEjemplo);
                        if (totalFC > 0) {
                            List<FormContrib> formContribNroOrden = formContribFacade.list(formContribEjemplo);
                            Long nroDeOrden = null;//formContribNroOrden.get(formContribNroOrden.size() - 1).getNumeroOrden();
                            for (FormContrib fc : formContribNroOrden) {
                                if (fc.getNumeroOrden() != null && fc.getCamposValidos() == 1 && fc.getCertificado().equalsIgnoreCase("S")) {
                                    nroDeOrden = fc.getNumeroOrden();
                                }
                            }
                            if (nroDeOrden != null) {//se guardo el formulario sin nro de orden
                                jsonFinal.put("success", false);
                                jsonFinal.put("motivo", "Formulario ya ingresado.<br>Tiene que ser rectificativo del siguiente numero de orden:" + nroDeOrden);
                                out.println(jsonFinal);
                                return;
                            }

                        }
                    }
                    if ((request.getParameter("CC3") != null) && !(request.getParameter("CC3").isEmpty())
                            && (request.getParameter("CC5") != null) && !(request.getParameter("CC5").isEmpty())
                            && (request.getParameter("CC10") == null || request.getParameter("CC10").isEmpty())) {
                        // numero de orden rectificado
                        CamposFormContrib camposEjemplo = new CamposFormContrib();
                        camposEjemplo.setValor((request.getParameter("CC5")).toString());
                        camposEjemplo.setNumeroCampo((int) 3);
                        camposEjemplo.setEtiqueta("3");
                        //camposEjemplo.setFormContrib(formContribEjemplo);

                        int totalFCRect = camposContribFacade.total(camposEjemplo);

                        if (totalFCRect > 0) {
                            List<FormContrib> formContribNroOrden = formContribFacade.list(formContribEjemplo);
                            Long nroDeOrden = null;//formContribNroOrden.get(formContribNroOrden.size() - 1).getNumeroOrden();
                            for (FormContrib fc : formContribNroOrden) {
                                if (fc.getNumeroOrden() != null && fc.getCamposValidos().equals(1) && fc.getCertificado().equalsIgnoreCase("S")) {

                                    List<CamposFormContrib> lCamposFc = camposContribFacade.list(camposEjemplo);
                                    for (CamposFormContrib cFC : lCamposFc) {
                                        if (cFC.getFormContrib().getIdFormContrib().equals(fc.getIdFormContrib())) {
                                            nroDeOrden = fc.getNumeroOrden();
                                        }
                                    }
                                }
                            }
                            if (nroDeOrden != null) {//se guardo el formulario sin nro de orden
                                jsonFinal.put("success", false);
                                jsonFinal.put("motivo", "EL Nro. de orden ya fue rectificado.<br>El número de orden debe ser:" + nroDeOrden);
                                out.println(jsonFinal);
                                return;
                            }
                        }
                    }


                    if ((request.getParameter("CC2") != null) && !(request.getParameter("CC2").isEmpty())
                            && request.getParameter("CC10") != null && !request.getParameter("CC10").isEmpty()) {

                        // dec. original
                        CamposFormContrib camposEjemplo = new CamposFormContrib();
                        camposEjemplo.setValor("1");
                        camposEjemplo.setNumeroCampo(1);
                        camposEjemplo.setEtiqueta("1");
                        camposEjemplo.setFormContrib(formContribEjemplo);

                        int totalFCRect = camposContribFacade.total(camposEjemplo);

                        // clausura
                        /*
                         * camposEjemplo.setValor("5");
                         * camposEjemplo.setNumeroCampo(5);
                         * camposEjemplo.setEtiqueta("5");
                         * camposEjemplo.setFormContrib(formContribEjemplo);
                         *
                         * totalFCRect *=
                         * camposContribFacade.total(camposEjemplo);
                         */

                        if (totalFCRect > 0) {
                            List<FormContrib> formContribNroOrden = formContribFacade.list(formContribEjemplo);
                            Long nroDeOrden = null;//formContribNroOrden.get(formContribNroOrden.size() - 1).getNumeroOrden();
                            for (FormContrib fc : formContribNroOrden) {
                                if (fc.getNumeroOrden() != null && fc.getCamposValidos() == 1 && fc.getCertificado().equalsIgnoreCase("S")) {
                                    nroDeOrden = fc.getNumeroOrden();
                                }
                            }
                            if (nroDeOrden != null) {//se guardo el formulario sin nro de orden
                                jsonFinal.put("success", false);
                                jsonFinal.put("motivo", "Formulario ya ingresado.<br>Tiene que ser rectificativo del siguiente numero de orden:" + nroDeOrden);
                                out.println(jsonFinal);
                                return;
                            }

                        }

                    }
                    if ((request.getParameter("CC3") != null) && !(request.getParameter("CC3").isEmpty())
                            && (request.getParameter("CC5") != null) && !(request.getParameter("CC5").isEmpty())
                            && request.getParameter("CC10") != null && !request.getParameter("CC10").isEmpty()) {
                        // clausura
                        CamposFormContrib camposEjemplo = new CamposFormContrib();

                        camposEjemplo.setValor((request.getParameter("CC5")).toString());
                        camposEjemplo.setNumeroCampo(3);
                        camposEjemplo.setEtiqueta("3");
                        // camposEjemplo.setFormContrib(formContribEjemplo);

                        int totalFCRect = camposContribFacade.total(camposEjemplo);

                        if (totalFCRect > 0) {
                            List<FormContrib> formContribNroOrden = formContribFacade.list(formContribEjemplo);
                            Long nroDeOrden = null;
                            for (FormContrib fc : formContribNroOrden) {
                                if (fc.getNumeroOrden() != null && fc.getCamposValidos() == 1 && fc.getCertificado().equalsIgnoreCase("S")) {

                                    List<CamposFormContrib> lCamposFc = camposContribFacade.list(camposEjemplo);
                                    for (CamposFormContrib cFC : lCamposFc) {
                                        if (cFC.getFormContrib().getIdFormContrib().equals(fc.getIdFormContrib())) {
                                            nroDeOrden = fc.getNumeroOrden();
                                        }
                                    }
                                }
                            }
                            if (nroDeOrden != null) {
                                jsonFinal.put("success", false);
                                jsonFinal.put("motivo", "EL Nro. de orden ya fue rectificado.<br>Tiene que ser rectificativo del siguiente numero de orden:" + nroDeOrden);
                                out.println(jsonFinal);
                                return;
                            }
                        }

                        /*
                         * camposEjemplo.setValor("5");
                         * camposEjemplo.setNumeroCampo(5);
                         * camposEjemplo.setEtiqueta("5");
                         * camposEjemplo.setFormContrib(formContribEjemplo);
                         *
                         * totalFCRect *=
                         * camposContribFacade.total(camposEjemplo);
                         */
                        // nro de orden rectificado




                        /*
                         * if (totalFCRect > 0) { jsonFinal.put("success",
                         * false); jsonFinal.put("motivo", "Formulario ya
                         * ingresado"); out.println(jsonFinal); return;
                         *
                         * }
                         */

                    }


                    /*
                     * int totalFC =
                     * formContribFacade.total(formContribEjemplo); if (totalFC
                     * > 0) { jsonFinal.put("success", false);
                     * jsonFinal.put("motivo", "Formulario ya ingresado");
                     * out.println(jsonFinal); return;
                     *
                     * }
                     */

                    /**
                     * *********************************************************
                     */
                }
                if ((request.getParameter("CC6") != null) && !(request.getParameter("CC6").isEmpty())
                        && (request.getParameter("CC4") != null) && !(request.getParameter("CC4").isEmpty())
                        //&& (request.getParameter("CC1") != null) && !(request.getParameter("CC1").isEmpty())
                        && (request.getParameter("CC7") != null) && !(request.getParameter("CC7").isEmpty())
                        && (request.getParameter("CC9") != null) && !(request.getParameter("CC9").isEmpty())
                        && (request.getParameter("idFormulario") != null) && !(request.getParameter("idFormulario").isEmpty())
                        && request.getParameter("idFormulario").matches("[0-9]+")
                        && Integer.parseInt(request.getParameter("idFormulario")) != 90 /*
                         * &&
                         * /*Integer.parseInt(request.getParameter("idFormulario"))
                         * != 130 &&
                         * Integer.parseInt(request.getParameter("idFormulario"))
                         * != 860
                         */) {
                    Formulario formularioExample = new Formulario();
                    formularioExample.setVersion(version);
                    formularioExample.setNumeroFormulario(new Integer(request.getParameter("idFormulario")));

                    List<Formulario> lcabecera = formularioFacade.list(formularioExample);

                    // List<Formulario> lcabecera = schemaQ.getLimitLogFormulario(10, 0, " where e.numeroFormulario = " + request.getParameter("idFormulario") + " and e.version = " + request.getParameter("CC1"), "e.numeroFormulario", "asc", "e");
                    int contador = 0;
                    if (lcabecera.size() > 0) {
                        List<ModContribAceptados> lmodAcptadas = modContribuyentesAceptadosFacade.list();
                        // List<ModContribuyentesAceptados> lmodAcptadas = schemaQ.getLimitLogModContribuyentesAceptados(100, 0, "", "e.idModContribuyenteAceptado", "asc", "e");
                        List<Integer> clientesAcptados = new ArrayList<Integer>();

                        for (ModContribAceptados oo : lmodAcptadas) {

                            clientesAcptados.add(oo.getModalidadContribuyente().intValue());
                        }
                        /**
                         * ***************************DESPUES DE ELIMNAR
                         * CABECERA
                         * FORMULARIO****************************************************************************
                         */
//                        Contribuyentes contribuyentes = contribuyentesFacade.getContribuyentePorRuc(request.getParameter("CC4"), request.getParameter("CC4"), request.getParameter("CC6"));
//                            List<Contribuyentes> contribuyentes = schemaQ.getLimitLogContribuyentes(10, 0, "where (e.rucNuevo = '" + request.getParameter("CC4") + "'" + " or e.rucAnterior = '" + request.getParameter("CC4") + "') AND e.digitoVerificador='" + request.getParameter("CC6") + "' ", "e.idContribuyente", "asc", "e");
                        if (contribuyentes != null) {
                            // for (Contribuyentes o : contribuyentes) {
                            if ((contribuyentes.getModalidadContribuyente() != null) && (clientesAcptados.contains(contribuyentes.getModalidadContribuyente()))) {
                                //Siguiente Control
                                if ((request.getParameter("CC2") != null) && (request.getParameter("CC2").equalsIgnoreCase("CC2")) && (request.getParameter("CC10") == null)) {
                                    perioFiscal = request.getParameter("CC9");

                                    if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
                                        if (perioFiscal.length() == 6) {
                                            perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
                                        }
                                        /*
                                         * con schemaquery
                                         */
                                        /*
                                         * Map<String, String> resultado =
                                         * schemaQ.validarPeriodoFiscalDJ(lcabecera.get(0).getNumeroFormulario().intValue(),
                                         * Integer.parseInt(request.getParameter("CC1")),
                                         * perioFiscal,
                                         * request.getParameter("CC7")); if
                                         * ((resultado != null) &&
                                         * (!resultado.isEmpty())) { if
                                         * (!(resultado.get("VALOR").equalsIgnoreCase("VALIDO")))
                                         * { json.put("Error", "Periodo Fiscal
                                         * Invalido");
                                         * arrayJson.add(json.clone()); } }
                                         */
                                        /*
                                         * con facade
                                         */
                                        String resultado;
                                        try {
                                            resultado = this.utilFacade.validarPeriodoFiscal("DJ", lcabecera.get(0).getNumeroFormulario().intValue(), version, perioFiscal, request.getParameter("CC7"), null);
                                            if (!resultado.equalsIgnoreCase("VALIDO")) {
                                                json.put("Error", "Periodo Fiscal Invalido");
                                                arrayJson.add(json.clone());
                                            }
                                        } catch (Exception e) {
                                            json.put("Error", e.getMessage());
                                            arrayJson.add(json.clone());
                                            Logger.getLogger(formularios.class.getName()).log(Level.SEVERE, null, e);
                                        }

                                    } else {
                                        json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera mmyyyy");
                                        arrayJson.add(json.clone());
                                    }

                                } else if ((request.getParameter("CC2") != null) && (request.getParameter("CC2").equalsIgnoreCase("CC2")) && (request.getParameter("CC10") != null) && (request.getParameter("CC10").equalsIgnoreCase("CC10"))) {
                                    perioFiscal = request.getParameter("CC9");
                                    if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
                                        if (perioFiscal.length() == 6) {
                                            perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
                                        }
                                        /*
                                         * con schemaquery
                                         */
                                        /*
                                         * Map<String, String> resultado =
                                         * schemaQ.validarPeriodoFiscalDJCC(lcabecera.get(0).getNumeroFormulario().intValue(),
                                         * Integer.parseInt(request.getParameter("CC1")),
                                         * perioFiscal,
                                         * request.getParameter("CC7")); if
                                         * ((resultado != null) &&
                                         * (!resultado.isEmpty())) { if
                                         * (!resultado.get("VALOR").equalsIgnoreCase("VALIDO"))
                                         * { json.put("Error", "No coinciden los
                                         * meses de Periodo Fiscal y Fecha
                                         * Presentacion"); arrayJson.add(json);
                                         * } }
                                         */
                                        /*
                                         * con facade
                                         */
                                        String resultado;
                                        try {
                                            resultado = this.utilFacade.validarPeriodoFiscal("DJCC", lcabecera.get(0).getNumeroFormulario().intValue(), version, perioFiscal, request.getParameter("CC7"), null);
                                            if (!resultado.equalsIgnoreCase("VALIDO")) {
                                                json.put("Error", "No coinciden los meses de Periodo Fiscal y Fecha Presentacion");
                                                arrayJson.add(json);
                                            }
                                        } catch (Exception e) {
                                            json.put("Error", e.getMessage());
                                            arrayJson.add(json);
                                            Logger.getLogger(formularios.class.getName()).log(Level.SEVERE, null, e);
                                        }
                                    } else {
                                        json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera mmyyyy");
                                        arrayJson.add(json.clone());
                                    }

                                } else if ((request.getParameter("CC2") == null) && (request.getParameter("CC10") != null) && (request.getParameter("CC10").equalsIgnoreCase("CC10"))) {
                                    perioFiscal = request.getParameter("CC9");
                                    if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
                                        if (perioFiscal.length() == 6) {
                                            perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
                                        }
                                        /*
                                         * con schemaquery
                                         */
                                        /*
                                         * Map<String, String> resultado =
                                         * schemaQ.validarPeriodoFiscalDJCC(lcabecera.get(0).getNumeroFormulario().intValue(),
                                         * Integer.parseInt(request.getParameter("CC1")),
                                         * perioFiscal,
                                         * request.getParameter("CC7")); if
                                         * ((resultado != null) &&
                                         * (!resultado.isEmpty())) { if
                                         * (!resultado.get("VALOR").equalsIgnoreCase("VALIDO"))
                                         * { json.put("Error", "No coinciden los
                                         * meses de Periodo Fiscal y Fecha
                                         * Presentacion"); arrayJson.add(json);
                                         * } }
                                         */
                                        /*
                                         * con facade
                                         */
                                        String resultado;
                                        try {
                                            resultado = this.utilFacade.validarPeriodoFiscal("DJCC", lcabecera.get(0).getNumeroFormulario().intValue(), version, perioFiscal, request.getParameter("CC7"), null);
                                            if (!resultado.equalsIgnoreCase("VALIDO")) {
                                                json.put("Error", "No coinciden los meses de Periodo Fiscal y Fecha Presentacion");
                                                arrayJson.add(json);
                                            }
                                        } catch (Exception e) {
                                            json.put("Error", e.getMessage());
                                            arrayJson.add(json);
                                            Logger.getLogger(formularios.class.getName()).log(Level.SEVERE, null, e);
                                        }
                                    } else {
                                        json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera mmyyyy");
                                        arrayJson.add(json.clone());
                                    }

                                } else if (request.getParameter("CC10") != null && (request.getParameter("CC10").equalsIgnoreCase("CC10")) && (request.getParameter("CC2") != null)) {
                                    perioFiscal = request.getParameter("CC9");
                                    if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
                                        if (perioFiscal.length() == 6) {
                                            perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
                                        }
                                        /*
                                         * con schemaquery
                                         */
                                        /*
                                         * Map<String, String> resultado =
                                         * schemaQ.validarPeriodoFiscalDJCC(lcabecera.get(0).getNumeroFormulario().intValue(),
                                         * Integer.parseInt(request.getParameter("CC1")),
                                         * perioFiscal,
                                         * request.getParameter("CC7")); if
                                         * ((resultado != null) &&
                                         * (!resultado.isEmpty())) { if
                                         * (!resultado.get("VALOR").equalsIgnoreCase("VALIDO"))
                                         * { json.put("Error", "Marco Clausura o
                                         * Cese y no coinciden los meses de
                                         * Periodo Fiscal y Fecha
                                         * Presentacion"); arrayJson.add(json);
                                         * } }
                                         */
                                        /*
                                         * con facade
                                         */
                                        String resultado;
                                        try {
                                            resultado = this.utilFacade.validarPeriodoFiscal("DJCC", lcabecera.get(0).getNumeroFormulario().intValue(), version, perioFiscal, request.getParameter("CC7"), null);
                                            if (!resultado.equalsIgnoreCase("VALIDO")) {
                                                json.put("Error", "Marco Clausura o Cese y no coinciden los meses de Periodo Fiscal y Fecha Presentacion");
                                                arrayJson.add(json);
                                            }
                                        } catch (Exception e) {
                                            json.put("Error", e.getMessage());
                                            arrayJson.add(json);
                                            Logger.getLogger(formularios.class.getName()).log(Level.SEVERE, null, e);
                                        }
                                    } else {
                                        json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera mmyyyy");
                                        arrayJson.add(json.clone());
                                    }

                                } else if ((request.getParameter("CC3") != null) && (request.getParameter("CC3").equalsIgnoreCase("CC3")) && (request.getParameter("CC10") == null)) {
                                    perioFiscal = request.getParameter("CC9");
                                    if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
                                        if (perioFiscal.length() == 6) {
                                            perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
                                        }
                                        if ((request.getParameter("CC5") == null) || (request.getParameter("CC5") != null && request.getParameter("CC5").isEmpty())) {
                                            json.put("Error", "Marco Declaracion Jurada Rectificada pero no indico el numero de Orden que rectifica");
                                            arrayJson.add(json.clone());
                                        } else if ((request.getParameter("CC5") != null) && !(request.getParameter("CC5").isEmpty())) {                                                //CONTINUAR CON EL CONTROL
                                                    /*
                                             * con squemaquery
                                             */
                                            /*
                                             * Map<String, String> resultado =
                                             * schemaQ.validarPeriodoFiscalDJ(lcabecera.get(0).getNumeroFormulario().intValue(),
                                             * Integer.parseInt(request.getParameter("CC1")),
                                             * perioFiscal,
                                             * request.getParameter("CC7")); if
                                             * ((resultado != null) &&
                                             * (!resultado.isEmpty())) { if
                                             * (!resultado.get("VALOR").equalsIgnoreCase("VALIDO"))
                                             * { json.put("Error", "Periodo
                                             * Fiscal Invalido");
                                             * arrayJson.add(json); } }
                                             */
                                            /*
                                             * con facade
                                             */
                                            String resultado;
                                            try {
                                                resultado = this.utilFacade.validarPeriodoFiscal("DJ", lcabecera.get(0).getNumeroFormulario().intValue(), version, perioFiscal, request.getParameter("CC7"), null);
                                                if (!resultado.equalsIgnoreCase("VALIDO")) {
                                                    json.put("Error", "Periodo Fiscal Invalido");
                                                    arrayJson.add(json);
                                                }
                                            } catch (Exception e) {
                                                json.put("Error", e.getMessage());
                                                arrayJson.add(json);
                                                Logger.getLogger(formularios.class.getName()).log(Level.SEVERE, null, e);
                                            }

                                        }
                                    } else {
                                        json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera mmyyyy");
                                        arrayJson.add(json.clone());
                                    }

                                } else if ((request.getParameter("CC3") != null) && (request.getParameter("CC3").equalsIgnoreCase("CC3")) && (request.getParameter("CC10") != null)) {
                                    perioFiscal = request.getParameter("CC9");
                                    if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {

                                        if (perioFiscal.length() == 6) {
                                            perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
                                        }
                                        if ((request.getParameter("CC5") == null) || (request.getParameter("CC5") != null && request.getParameter("CC5").isEmpty())) {
                                            json.put("Error", "Marco Declaracion Jurada Rectificada pero no inidico el numero de Orden que rectifica");
                                            arrayJson.add(json.clone());
                                        } else if ((request.getParameter("CC5") != null) && !(request.getParameter("CC5").isEmpty())) {                                                //CONTINUAR CON EL CONTROL

                                            /*
                                             * con schemaquery
                                             */
                                            /*
                                             * Map<String, String> resultado =
                                             * schemaQ.validarPeriodoFiscalDJRC(lcabecera.get(0).getNumeroFormulario().intValue(),
                                             * Integer.parseInt(request.getParameter("CC1")),
                                             * perioFiscal,
                                             * request.getParameter("CC7")); if
                                             * ((resultado != null) &&
                                             * (!resultado.isEmpty())) { if
                                             * (!resultado.get("VALOR").equalsIgnoreCase("VALIDO"))
                                             * { json.put("Error", "Periodo
                                             * Fiscal Invalido");
                                             * arrayJson.add(json); } }
                                             */
                                            /*
                                             * con facade
                                             */
                                            String resultado;
                                            try {
                                                resultado = this.utilFacade.validarPeriodoFiscal("DJRC", lcabecera.get(0).getNumeroFormulario().intValue(), version, perioFiscal, request.getParameter("CC7"), null);
                                                if (!resultado.equalsIgnoreCase("VALIDO")) {
                                                    json.put("Error", "Periodo Fiscal Invalido");
                                                    arrayJson.add(json);
                                                }
                                            } catch (Exception e) {
                                                json.put("Error", e.getMessage());
                                                arrayJson.add(json);
                                                Logger.getLogger(formularios.class.getName()).log(Level.SEVERE, null, e);
                                            }
                                        }
                                    } else {
                                        json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera mmyyyy");
                                        arrayJson.add(json.clone());
                                    }

                                } else {
                                    json.put("Error", "Debe Marcar una opcion");
                                    arrayJson.add(json);
                                }
                            } else {
                                json.put("Error", "No se permite la modadlidad de Cliente: Gran Contribuyente");
                                arrayJson.add(json.clone());
                            }
                            //}


                            if (arrayJson.size() > 0) {
                                jsonFinal.put("success", false);
                                jsonFinal.put("total", arrayJson.size());
                                jsonFinal.put("items", arrayJson);
                                jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");

                            } else {
                                jsonFinal.put("success", true);
                                jsonFinal.put("version", version.toString());
                                jsonFinal.put("formulario", request.getParameter("idFormulario"));
                                //FormularioImpuesto formualrioImpuestoExample =formularioImpuestoFacade.get(new Integer(request.getParameter("idFormulario")));
                                FormularioImpuesto formualrioImpuestoExample = new FormularioImpuesto();
                                formualrioImpuestoExample.setNumeroFormulario(new Integer(request.getParameter("idFormulario")));
                                List<FormularioImpuesto> lresultado = formularioImpuestoFacade.list(formualrioImpuestoExample);



                                // List<FormularioImpuesto> lresultado = schemaQ.getLimitLogFormularioImpuesto(10, 0, " where e.idFormulario = " + request.getParameter("idFormulario"), "e.idFormulario", "asc", "e");

                                if (lresultado.size() == 1) {

                                    HttpSession sessionUsuario = request.getSession(false);

                                    if (sessionUsuario != null) {

                                        Map<String, String> camposDerivados = new HashMap<String, String>();

                                        camposDerivados.put("numeroImpuesto", lresultado.get(0).getImpuesto().toString());
                                        camposDerivados.put("version", version.toString());
                                        camposDerivados.put("numeroFormulario", request.getParameter("idFormulario"));
                                        if (request.getParameter("CC2") != null) {
                                            camposDerivados.put("decJuradaOrig", "1");
                                        } else {
                                            camposDerivados.put("decJuradaOrig", "0");
                                        }

                                        if (request.getParameter("CC3") != null) {
                                            camposDerivados.put("decJuradaRectif", "1");
                                            camposDerivados.put("decNumeroRectif", request.getParameter("CC5"));

                                        } else {
                                            camposDerivados.put("decJuradaRectif", "0");
                                            camposDerivados.put("decNumeroRectif", "0");
                                        }
                                        if (request.getParameter("CC10") != null) {
                                            camposDerivados.put("decJuradaClausuraCese", "1");
                                        } else {
                                            camposDerivados.put("decJuradaClausuraCese", "0");
                                        }

                                        camposDerivados.put("ruc", request.getParameter("CC4"));
                                        camposDerivados.put("dv", request.getParameter("CC6"));
                                        camposDerivados.put("fechaPresentacion", request.getParameter("CC7"));
                                        camposDerivados.put("periodoFiscal", perioFiscal);


                                        sessionUsuario.setAttribute("camposDerivados", camposDerivados);
                                        jsonFinal.put("success", true);


                                    } else {
                                        jsonFinal.put("success", false);
                                        json.put("Error", "El usuario no tiene Session");
                                        arrayJson.add(json);
                                        jsonFinal.put("items", arrayJson);
                                        jsonFinal.put("total", arrayJson.size());
                                        jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");
                                    }

                                } else {
                                    jsonFinal.put("success", false);
                                    json.put("Error", "Id del formulario no es valido");
                                    arrayJson.add(json);
                                    jsonFinal.put("total", arrayJson.size());
                                    jsonFinal.put("items", arrayJson);
                                    jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");


                                }
                            }
                        } else {
                            jsonFinal.put("success", false);
                            json.put("Error", "No existe el contribuyente");
                            arrayJson.add(json.clone());
                            jsonFinal.put("total", arrayJson.size());
                            jsonFinal.put("items", arrayJson);
                            jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");
                        }
                        /**
                         * ********************ANTES DE ELEMINAR CABECERA
                         * FORMULARIO*******************************************
                         */
                        //Se hace asi hasta que se encuentre una manera generica de hacerlo.
//                        if (lcabecera.get(0).getIdCabeceraFormulario().getIdCabeceraFormulario().intValue() == 1 || lcabecera.get(0).getIdCabeceraFormulario().getIdCabeceraFormulario().intValue() == 2) {
//                            Contribuyentes contribuyentes = contribuyentesFacade.getContribuyentePorRuc(request.getParameter("CC4"), request.getParameter("CC4"), request.getParameter("CC6"));
////                            List<Contribuyentes> contribuyentes = schemaQ.getLimitLogContribuyentes(10, 0, "where (e.rucNuevo = '" + request.getParameter("CC4") + "'" + " or e.rucAnterior = '" + request.getParameter("CC4") + "') AND e.digitoVerificador='" + request.getParameter("CC6") + "' ", "e.idContribuyente", "asc", "e");
//                            if (contribuyentes != null) {
//                                // for (Contribuyentes o : contribuyentes) {
//                                if ((contribuyentes.getModalidadContribuyente() != null) && (clientesAcptados.contains(contribuyentes.getModalidadContribuyente()))) {
//                                    //Siguiente Control
//                                    if ((request.getParameter("CC2") != null) && (request.getParameter("CC2").equalsIgnoreCase("CC2")) && (request.getParameter("CC10") == null)) {
//                                        perioFiscal = request.getParameter("CC9");
//                                        System.out.println("PERIODO FISCAL: " + perioFiscal);
//                                        if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
//                                            if (perioFiscal.length() == 6) {
//                                                perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
//                                            }
//                                            /*con schemaquery*/
//                                            /*Map<String, String> resultado = schemaQ.validarPeriodoFiscalDJ(lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"));
//                                            if ((resultado != null) && (!resultado.isEmpty())) {
//                                            if (!(resultado.get("VALOR").equalsIgnoreCase("VALIDO"))) {
//                                            json.put("Error", "Periodo Fiscal Invalido");
//                                            arrayJson.add(json.clone());
//                                            }
//                                            }*/
//                                            /*con facade*/
//                                            String resultado;
//                                            try {
//                                                resultado = this.utilFacade.validarPeriodoFiscal("DJ", lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"), null);
//                                                if (!resultado.equalsIgnoreCase("VALIDO")) {
//                                                    json.put("Error", "Periodo Fiscal Invalido");
//                                                    arrayJson.add(json.clone());
//                                                }
//                                            } catch (Exception e) {
//                                                json.put("Error", e.getMessage());
//                                                arrayJson.add(json.clone());
//                                                e.printStackTrace();
//                                            }
//
//                                        } else {
//                                            json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera ddyyyy");
//                                            arrayJson.add(json.clone());
//                                        }
//
//                                    } else if ((request.getParameter("CC2") != null) && (request.getParameter("CC2").equalsIgnoreCase("CC2")) && (request.getParameter("CC10") != null) && (request.getParameter("CC10").equalsIgnoreCase("CC10"))) {
//                                        perioFiscal = request.getParameter("CC9");
//                                        if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
//                                            if (perioFiscal.length() == 6) {
//                                                perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
//                                            }
//                                            /*con schemaquery*/
//                                            /*
//                                            Map<String, String> resultado = schemaQ.validarPeriodoFiscalDJCC(lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"));
//                                            if ((resultado != null) && (!resultado.isEmpty())) {
//                                            if (!resultado.get("VALOR").equalsIgnoreCase("VALIDO")) {
//                                            json.put("Error", "No coinciden los meses de Periodo Fiscal y Fecha Presentacion");
//                                            arrayJson.add(json);
//                                            }
//                                            }*/
//                                            /*con facade*/
//                                            String resultado;
//                                            try {
//                                                resultado = this.utilFacade.validarPeriodoFiscal("DJCC", lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"), null);
//                                                if (!resultado.equalsIgnoreCase("VALIDO")) {
//                                                    json.put("Error", "No coinciden los meses de Periodo Fiscal y Fecha Presentacion");
//                                                    arrayJson.add(json);
//                                                }
//                                            } catch (Exception e) {
//                                                json.put("Error", e.getMessage());
//                                                arrayJson.add(json);
//                                                e.printStackTrace();
//                                            }
//                                        } else {
//                                            json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera mmyyyy");
//                                            arrayJson.add(json.clone());
//                                        }
//
//                                    } else if ((request.getParameter("CC2") == null) && (request.getParameter("CC10") != null) && (request.getParameter("CC10").equalsIgnoreCase("CC10"))) {
//                                        perioFiscal = request.getParameter("CC9");
//                                        if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
//                                            if (perioFiscal.length() == 6) {
//                                                perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
//                                            }
//                                            /*con schemaquery*/
//                                            /*Map<String, String> resultado = schemaQ.validarPeriodoFiscalDJCC(lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"));
//                                            if ((resultado != null) && (!resultado.isEmpty())) {
//                                            if (!resultado.get("VALOR").equalsIgnoreCase("VALIDO")) {
//                                            json.put("Error", "No coinciden los meses de Periodo Fiscal y Fecha Presentacion");
//                                            arrayJson.add(json);
//                                            }
//                                            }*/
//                                            /*con facade*/
//                                            String resultado;
//                                            try {
//                                                resultado = this.utilFacade.validarPeriodoFiscal("DJCC", lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"), null);
//                                                if (!resultado.equalsIgnoreCase("VALIDO")) {
//                                                    json.put("Error", "No coinciden los meses de Periodo Fiscal y Fecha Presentacion");
//                                                    arrayJson.add(json);
//                                                }
//                                            } catch (Exception e) {
//                                                json.put("Error", e.getMessage());
//                                                arrayJson.add(json);
//                                                e.printStackTrace();
//                                            }
//                                        } else {
//                                            json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera ddyyyy");
//                                            arrayJson.add(json.clone());
//                                        }
//
//                                    } else if (request.getParameter("CC10") != null && (request.getParameter("CC10").equalsIgnoreCase("CC10")) && (request.getParameter("CC2") != null)) {
//                                        perioFiscal = request.getParameter("CC9");
//                                        if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
//                                            if (perioFiscal.length() == 6) {
//                                                perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
//                                            }
//                                            /*con schemaquery*/
//                                            /*Map<String, String> resultado = schemaQ.validarPeriodoFiscalDJCC(lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"));
//                                            if ((resultado != null) && (!resultado.isEmpty())) {
//                                            if (!resultado.get("VALOR").equalsIgnoreCase("VALIDO")) {
//                                            json.put("Error", "Marco Clausura o Cese y no coinciden los meses de Periodo Fiscal y Fecha Presentacion");
//                                            arrayJson.add(json);
//                                            }
//                                            }*/
//                                            /*con facade*/
//                                            String resultado;
//                                            try {
//                                                resultado = this.utilFacade.validarPeriodoFiscal("DJCC", lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"), null);
//                                                if (!resultado.equalsIgnoreCase("VALIDO")) {
//                                                    json.put("Error", "Marco Clausura o Cese y no coinciden los meses de Periodo Fiscal y Fecha Presentacion");
//                                                    arrayJson.add(json);
//                                                }
//                                            } catch (Exception e) {
//                                                json.put("Error", e.getMessage());
//                                                arrayJson.add(json);
//                                                e.printStackTrace();
//                                            }
//                                        } else {
//                                            json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera ddyyyy");
//                                            arrayJson.add(json.clone());
//                                        }
//
//                                    } else if ((request.getParameter("CC3") != null) && (request.getParameter("CC3").equalsIgnoreCase("CC3")) && (request.getParameter("CC10") == null)) {
//                                        perioFiscal = request.getParameter("CC9");
//                                        if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
//                                            if (perioFiscal.length() == 6) {
//                                                perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
//                                            }
//                                            if ((request.getParameter("CC5") == null) || (request.getParameter("CC5") != null && request.getParameter("CC5").isEmpty())) {
//                                                json.put("Error", "Marco Declaracion Jurada Rectificada pero no inidico el numero de Orden que rectifica");
//                                                arrayJson.add(json.clone());
//                                            } else if ((request.getParameter("CC5") != null) && !(request.getParameter("CC5").isEmpty())) {                                                //CONTINUAR CON EL CONTROL
//                                                    /*con squemaquery*/
//                                                /*
//                                                Map<String, String> resultado = schemaQ.validarPeriodoFiscalDJ(lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"));
//                                                if ((resultado != null) && (!resultado.isEmpty())) {
//                                                if (!resultado.get("VALOR").equalsIgnoreCase("VALIDO")) {
//                                                json.put("Error", "Periodo Fiscal Invalido");
//                                                arrayJson.add(json);
//                                                }
//                                                }
//                                                 */
//                                                /*con facade*/
//                                                String resultado;
//                                                try {
//                                                    resultado = this.utilFacade.validarPeriodoFiscal("DJ", lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"), null);
//                                                    if (!resultado.equalsIgnoreCase("VALIDO")) {
//                                                        json.put("Error", "Periodo Fiscal Invalido");
//                                                        arrayJson.add(json);
//                                                    }
//                                                } catch (Exception e) {
//                                                    json.put("Error", e.getMessage());
//                                                    arrayJson.add(json);
//                                                    e.printStackTrace();
//                                                }
//
//                                            }
//                                        } else {
//                                            json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera ddyyyy");
//                                            arrayJson.add(json.clone());
//                                        }
//
//                                    } else if ((request.getParameter("CC3") != null) && (request.getParameter("CC3").equalsIgnoreCase("CC3")) && (request.getParameter("CC10") != null)) {
//                                        perioFiscal = request.getParameter("CC9");
//                                        if (perioFiscal != null && (perioFiscal.length() == 6 || perioFiscal.length() == 4)) {
//
//                                            if (perioFiscal.length() == 6) {
//                                                perioFiscal = (String) perioFiscal.subSequence(0, 2) + "/" + perioFiscal.subSequence(2, perioFiscal.length());
//                                            }
//                                            if ((request.getParameter("CC5") == null) || (request.getParameter("CC5") != null && request.getParameter("CC5").isEmpty())) {
//                                                json.put("Error", "Marco Declaracion Jurada Rectificada pero no inidico el numero de Orden que rectifica");
//                                                arrayJson.add(json.clone());
//                                            } else if ((request.getParameter("CC5") != null) && !(request.getParameter("CC5").isEmpty())) {                                                //CONTINUAR CON EL CONTROL
//
//                                                /*con schemaquery*/
//                                                /*Map<String, String> resultado = schemaQ.validarPeriodoFiscalDJRC(lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"));
//                                                if ((resultado != null) && (!resultado.isEmpty())) {
//                                                if (!resultado.get("VALOR").equalsIgnoreCase("VALIDO")) {
//                                                json.put("Error", "Periodo Fiscal Invalido");
//                                                arrayJson.add(json);
//                                                }
//                                                }*/
//                                                /*con facade*/
//                                                String resultado;
//                                                try {
//                                                    resultado = this.utilFacade.validarPeriodoFiscal("DJRC", lcabecera.get(0).getNumeroFormulario().intValue(), Integer.parseInt(request.getParameter("CC1")), perioFiscal, request.getParameter("CC7"), null);
//                                                    if (!resultado.equalsIgnoreCase("VALIDO")) {
//                                                        json.put("Error", "Periodo Fiscal Invalido");
//                                                        arrayJson.add(json);
//                                                    }
//                                                } catch (Exception e) {
//                                                    json.put("Error", e.getMessage());
//                                                    arrayJson.add(json);
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        } else {
//                                            json.put("Error", "Periodo Fiscal Invalido. El formato no es valido se espera ddyyyy");
//                                            arrayJson.add(json.clone());
//                                        }
//
//                                    } else {
//                                        json.put("Error", "Debe Marcar una opcion");
//                                        arrayJson.add(json);
//                                    }
//                                } else {
//                                    json.put("Error", "No se permite la modadlidad de Cliente: Gran Contribuyente");
//                                    arrayJson.add(json.clone());
//                                }
//                                //}
//                                System.out.println("LISTA DE ERRORES " + arrayJson.toString());
//
//                                if (arrayJson.size() > 0) {
//                                    jsonFinal.put("success", false);
//                                    jsonFinal.put("total", arrayJson.size());
//                                    jsonFinal.put("items", arrayJson);
//                                    jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");
//
//                                } else {
//                                    jsonFinal.put("success", true);
//                                    //FormularioImpuesto formualrioImpuestoExample =formularioImpuestoFacade.get(new Integer(request.getParameter("idFormulario")));
//                                    FormularioImpuesto formualrioImpuestoExample = new FormularioImpuesto();
//                                    formualrioImpuestoExample.setIdFormulario(new Integer(request.getParameter("idFormulario")));
//                                    List<FormularioImpuesto> lresultado = formularioImpuestoFacade.list(formualrioImpuestoExample);
//
//
//
//                                    // List<FormularioImpuesto> lresultado = schemaQ.getLimitLogFormularioImpuesto(10, 0, " where e.idFormulario = " + request.getParameter("idFormulario"), "e.idFormulario", "asc", "e");
//
//                                    if (lresultado.size() == 1) {
//
//                                        HttpSession sessionUsuario = request.getSession(false);
//
//                                        if (sessionUsuario != null) {
//
//                                            Map<String, String> camposDerivados = new HashMap<String, String>();
//
//                                            camposDerivados.put("numeroImpuesto", lresultado.get(0).getImpuesto().toString());
//                                            camposDerivados.put("numeroFormulario", request.getParameter("idFormulario"));
//                                            if (request.getParameter("CC2") != null) {
//                                                camposDerivados.put("decJuradaOrig", "1");
//                                            } else {
//                                                camposDerivados.put("decJuradaOrig", "0");
//                                            }
//
//                                            if (request.getParameter("CC3") != null) {
//                                                camposDerivados.put("decJuradaRectif", "1");
//                                                camposDerivados.put("decNumeroRectif", request.getParameter("CC5"));
//
//                                            } else {
//                                                camposDerivados.put("decJuradaRectif", "0");
//                                                camposDerivados.put("decNumeroRectif", "0");
//                                            }
//                                            if (request.getParameter("CC10") != null) {
//                                                camposDerivados.put("decJuradaClausuraCese", "1");
//                                            } else {
//                                                camposDerivados.put("decJuradaClausuraCese", "0");
//                                            }
//
//                                            camposDerivados.put("ruc", request.getParameter("CC4"));
//                                            camposDerivados.put("dv", request.getParameter("CC6"));
//                                            camposDerivados.put("fechaPresentacion", request.getParameter("CC7"));
//                                            camposDerivados.put("periodoFiscal", perioFiscal);
//
//
//                                            sessionUsuario.setAttribute("camposDerivados", camposDerivados);
//                                            jsonFinal.put("success", true);
//
//
//                                        } else {
//                                            jsonFinal.put("success", false);
//                                            json.put("Error", "El usuario no tiene Session");
//                                            arrayJson.add(json);
//                                            jsonFinal.put("items", arrayJson);
//                                            jsonFinal.put("total", arrayJson.size());
//                                            jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");
//                                        }
//
//                                    } else {
//                                        jsonFinal.put("success", false);
//                                        json.put("Error", "Id del formulario no es valido");
//                                        arrayJson.add(json);
//                                        jsonFinal.put("total", arrayJson.size());
//                                        jsonFinal.put("items", arrayJson);
//                                        jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");
//
//
//                                    }
//                                }
//                            } else {
//                                jsonFinal.put("success", false);
//                                json.put("Error", "No existe el contribuyente");
//                                arrayJson.add(json.clone());
//                                jsonFinal.put("total", arrayJson.size());
//                                jsonFinal.put("items", arrayJson);
//                                jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");
//                            }
//                        }
                    } else {
                        jsonFinal.put("success", false);
                        json.put("Error", "No se ha encontrado el fomulario que coincida con los parametros: Tipo de formualrio y Version ");
                        arrayJson.add(json);
                        jsonFinal.put("total", arrayJson.size());
                        jsonFinal.put("items", arrayJson);
                        jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");

                    }

                } //CC1	VERSION
                //CC4	RUC
                //CC6	DV
                //CC7	FECHA PRESENTACION
                else if ((request.getParameter("CC6") != null) && !(request.getParameter("CC6").isEmpty())
                        && (request.getParameter("CC4") != null) && !(request.getParameter("CC4").isEmpty())
                        //&& (request.getParameter("CC1") != null) && !(request.getParameter("CC1").isEmpty())
                        && (request.getParameter("CC7") != null) && !(request.getParameter("CC7").isEmpty())
                        && // (request.getParameter("CC9") != null) && !(request.getParameter("CC9").isEmpty()) &&
                        (request.getParameter("idFormulario") != null) && !(request.getParameter("idFormulario").isEmpty())
                        && request.getParameter("idFormulario").matches("[0-9]+")
                        && Integer.parseInt(request.getParameter("idFormulario")) == 90) {
                    jsonFinal.put("success", true);
                    json.put("Valido", "Los Parametros son validos");
                    arrayJson.add(json);
                    jsonFinal.put("total", arrayJson.size());
                    jsonFinal.put("items", arrayJson);
                    jsonFinal.put("motivo", "LA CABECERA ES VALIDA");


                    //FormularioImpuesto formualrioImpuestoExample =formularioImpuestoFacade.get(new Integer(request.getParameter("idFormulario")));
                    FormularioImpuesto formualrioImpuestoExample = new FormularioImpuesto();
                    formualrioImpuestoExample.setNumeroFormulario(new Integer(request.getParameter("idFormulario")));
                    List<FormularioImpuesto> lresultado = formularioImpuestoFacade.list(formualrioImpuestoExample);



                    // List<FormularioImpuesto> lresultado = schemaQ.getLimitLogFormularioImpuesto(10, 0, " where e.idFormulario = " + request.getParameter("idFormulario"), "e.idFormulario", "asc", "e");

                    if (lresultado.size() == 1) {

                        HttpSession sessionUsuario = request.getSession(false);

                        if (sessionUsuario != null) {

                            Map<String, String> camposDerivados = new HashMap<String, String>();

                            camposDerivados.put("numeroImpuesto", lresultado.get(0).getImpuesto().toString());
                            camposDerivados.put("version", version.toString());
                            camposDerivados.put("numeroFormulario", request.getParameter("idFormulario"));
                            camposDerivados.put("ruc", request.getParameter("CC4"));
                            camposDerivados.put("dv", request.getParameter("CC6"));
                            camposDerivados.put("fechaPresentacion", request.getParameter("CC7"));
                            camposDerivados.put("radioFormulario90", request.getParameter("radioFormulario90"));


                            sessionUsuario.setAttribute("camposDerivados", camposDerivados);
                            jsonFinal.put("success", true);
                        }
                    }
                } else {
                    jsonFinal.put("success", false);
                    json.put("Error", "Los Parametros no son validos");
                    arrayJson.add(json);
                    jsonFinal.put("total", arrayJson.size());
                    jsonFinal.put("items", arrayJson);
                    jsonFinal.put("motivo", "LA CABECERA NO ES VALIDA");
                }

                out.println(jsonFinal);

            }
        } finally {
            //out.close();
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
