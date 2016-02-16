/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import py.com.konecta.redcobros.cobrosweb.clases.CampoFallido;
import py.com.konecta.redcobros.cobrosweb.clases.EvaluadorExpresion;
import py.com.konecta.redcobros.ejb.CampoFacade;
import py.com.konecta.redcobros.entities.Campo;
import py.com.konecta.redcobros.entities.Formulario;

/**
 *
 * @author konecta
 */
public class ValidacionCampos extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private CampoFacade campoFacade;

    private String removeNonNumericChar(String barCode) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; barCode.length() > i; i++) {
            if (String.valueOf(barCode.charAt(i)).matches("[0-9]")) {
                strBuilder.append(barCode.charAt(i));
            }
        }
        return strBuilder.toString();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HashMap<String, String> mapaValores = new HashMap<String, String>();
            int minimo = new Integer(request.getParameter("minimo"));
            int maximo = new Integer(request.getParameter("maximo"));
            int numeroFormulario = new Integer(request.getParameter("formulario"));
            String numeroImpuesto = request.getParameter("impuesto");
            String ruc = request.getParameter("ruc");

            //NUMIMPUESTO
            mapaValores.put("NUMIMPUESTO", numeroImpuesto);
            //NUMFORMULARIO
            mapaValores.put("NUMFORMULARIO", "" + numeroFormulario);
            //RUC
            mapaValores.put("RUC", "" + ruc);

            if (numeroFormulario != 90) {

                String diasAtrasos = request.getParameter("diasatrasos");
                String mesesAtrasos = request.getParameter("mesesatrasos");
                String decJuradaOrig = request.getParameter("decjuradaorig");
                String decJuradaRectif = request.getParameter("decjuradarectif");
                String decJuradaClausuraCese = request.getParameter("decjuradaclausuracese");



                String periodoFiscal = request.getParameter("periodoFiscal");
                //NUMIMPUESTO

                //DIASATRASOS
                mapaValores.put("DIASATRASOS", "" + diasAtrasos);
                //MESESATRASOS
                mapaValores.put("MESESATRASOS", "" + mesesAtrasos);
                //DECJURADAORIG
                mapaValores.put("DECJURADAORIG", "" + decJuradaOrig);
                //DECJURADARECTIF
                mapaValores.put("DECJURADARECTIF", "" + decJuradaRectif);
                //DECJURADACLAUSURACESE
                mapaValores.put("DECJURADACLAUSURACESE", "" + decJuradaClausuraCese);

                String[] per = periodoFiscal.split("/");
                String cadena = null;
                if (per.length == 2) {
                    cadena = per[1] + per[0];
                } else {
                    cadena = per[0];
                }

                mapaValores.put("PERIODOFISCAL", cadena);
            }


            String validar = request.getParameter("validar");
            int version = new Integer(request.getParameter("version"));
//            String periodoFiscal=request.getParameter("periodoFiscal");

//            //DIASATRASOS
//            mapaValores.put("DIASATRASOS", ""+diasAtrasos);
//            //MESESATRASOS
//            mapaValores.put("MESESATRASOS", ""+mesesAtrasos);
//            //DECJURADAORIG
//            mapaValores.put("DECJURADAORIG", ""+decJuradaOrig);
//            //DECJURADARECTIF
//            mapaValores.put("DECJURADARECTIF", ""+decJuradaRectif);
//            //DECJURADACLAUSURACESE
//            mapaValores.put("DECJURADACLAUSURACESE", ""+decJuradaClausuraCese);
            //VERSION
            mapaValores.put("VERSION", "" + version);
            //PERIODOFISCAL
//            String []per=periodoFiscal.split("/");
//            String cadena=null;
//            if (per.length==2) {
//                cadena=per[1]+per[0];
//            } else {
//                cadena=per[0];
//            }
//
//            mapaValores.put("PERIODOFISCAL", cadena);

            String idCampo = null;
            Campo campo;
            List<CampoFallido> mandatorios = new ArrayList<CampoFallido>();
            List<CampoFallido> fallidos = new ArrayList<CampoFallido>();

            EvaluadorExpresion eval = null;
            String respuesta;
            CampoFallido cf = null;
            for (int i = minimo; i <= maximo; i++) {
                idCampo = "C" + i;
                String valor = (request.getParameter(idCampo) != null
                        && !request.getParameter(idCampo).equals(""))
                        ? request.getParameter(idCampo)
                        : null;
                if (valor == null) {
                    continue;
                } else {
                    mapaValores.put(idCampo, valor);
                }
            }

            if (numeroFormulario == 90) {

                idCampo = "C4";
                String valor = (request.getParameter(idCampo) != null
                        && !request.getParameter(idCampo).equals(""))
                        ? request.getParameter(idCampo)
                        : null;

                mapaValores.put(idCampo, valor);

                for (int i = 10; i < 15; i++) {
                    idCampo = "C" + i;
                    valor = (request.getParameter(idCampo) != null
                            && !request.getParameter(idCampo).equals(""))
                            ? request.getParameter(idCampo)
                            : null;
                    if (valor == null) {
                        continue;
                    } else {

                        if (valor.equalsIgnoreCase("10")) {
                            mapaValores.put("NUMIMPUESTO", "114");
                        } else {
                            if (valor.equalsIgnoreCase("11")) {
                                mapaValores.put("NUMIMPUESTO", "115");
                            } else {
                                if (valor.equalsIgnoreCase("12")) {
                                    mapaValores.put("NUMIMPUESTO", "112");
                                } else {
                                    if (valor.equalsIgnoreCase("13")) {
                                        mapaValores.put("NUMIMPUESTO", "431");
                                    } else {
                                        if (valor.equalsIgnoreCase("14")) {
                                            mapaValores.put("NUMIMPUESTO", "461");
                                        }
                                    }
                                }
                            }
                        }
                        mapaValores.put(idCampo, valor);
                    }
                }
            }
            Formulario formulario = new Formulario();
            formulario.setNumeroFormulario(numeroFormulario);
            formulario.setVersion(version);

            campo = new Campo();
            campo.setFormulario(formulario);

            List<Campo> camposList = this.campoFacade.list(campo);

            HashMap<Integer, Campo> camposMap = new HashMap<Integer, Campo>();
            for (Campo c : camposList) {
                camposMap.put(c.getNumero(), c);
            }

            for (int i = minimo; i <= maximo; i++) {
                idCampo = "C" + i;
                String valor = (request.getParameter(idCampo) != null
                        && !request.getParameter(idCampo).equals(""))
                        ? request.getParameter(idCampo)
                        : null;
                if (numeroFormulario == 90) {
                    if (mapaValores.get("C10").equalsIgnoreCase("10") && idCampo.equalsIgnoreCase("C19") && !valor.equalsIgnoreCase("0")) {
                        mapaValores.put("PERIODOFISCAL", valor.substring(4) + valor.substring(2, 4));
                    } else {
                        if (mapaValores.get("C11").equalsIgnoreCase("11") && idCampo.equalsIgnoreCase("C21") && !valor.equalsIgnoreCase("0")) {
                            mapaValores.put("PERIODOFISCAL", valor.substring(4) + valor.substring(2, 4));
                        } else {
                            if (mapaValores.get("C12").equalsIgnoreCase("12") && idCampo.equalsIgnoreCase("C29") && !valor.equalsIgnoreCase("0")) {
                                mapaValores.put("PERIODOFISCAL", valor.substring(4) + valor.substring(2, 4));
                            } else {
                                if (mapaValores.get("C14").equalsIgnoreCase("14") && idCampo.equalsIgnoreCase("C33") && !valor.equalsIgnoreCase("0")) {
                                    mapaValores.put("PERIODOFISCAL", valor.substring(4) + valor.substring(2, 4));
                                }
                            }
                        }
                    }
                }

                //campo=sq.getCampo(numeroFormulario, version ,i);
                //campo.setNumero(i);
                campo = camposMap.get(i);

                //if (this.campoFacade.total(campo)>0) {
                //    campo=this.campoFacade.get(campo);
                //} else {
                if (campo == null) {

                    continue;
                }
                //System.out.println("no es null");
                //habria q ver la manera de verificar un campo mandatorio
                //ya que siempre vendra 0 por lo menos
                if (valor == null) {

                    //no vino el campo porque no se cargo su valor
                    //verificamos si es mandatorio
                    if ((validar.equals("todos") || campo.getEtiqueta().equals(validar))
                            && campo.getRequerido().equals(new BigInteger("1"))) {
                        cf = new CampoFallido();
                        cf.setIdCampo(idCampo);
                        cf.setTipoFallo(CampoFallido.VALOR_MANDATORIO);
                        cf.setMensajeError("debe ser introducido");
                        cf.setPosibleValor("--");
                        mandatorios.add(cf);
                    }
                } else {
                    /*
                     * System.out.println("validar"); System.out.println("valor
                     * de validar "+validar); System.out.println("valor de
                     * etiqueta "+campo.getEtiqueta());
                     * System.out.println("valor de formula "+campo.getFormula());
                     */
                    //mapaValores.put(idCampo, valor);
                    if ((validar.equals("todos") || campo.getEtiqueta().equals(validar))
                            && campo.getFormula() != null
                            && !campo.getFormula().equals("")) {

                        eval = new EvaluadorExpresion(campo.getFormula(), mapaValores);

                        try {
                            respuesta = eval.evaluarExpresion();

                            if (campo.getTipo().equalsIgnoreCase("N") && Long.parseLong(removeNonNumericChar(valor)) != Math.round(Double.parseDouble(respuesta))) {
                                cf = new CampoFallido();
                                cf.setIdCampo(idCampo);
                                cf.setTipoFallo(CampoFallido.VALOR_INVALIDO);
                                cf.setMensajeError("no valido");
                                //ver si se debe mostrar el posible valor
                                if (campo.getMostrarSugerencia().intValue() == 2) {
                                    cf.setPosibleValor("--");
                                } else {
                                    cf.setPosibleValor("" + Math.round(Double.parseDouble(respuesta)));
                                }

                                fallidos.add(cf);
                                //
                                mapaValores.remove(idCampo);
                                mapaValores.put(idCampo, "" + Math.round(Double.parseDouble(respuesta)));
                            } else if (campo.getTipo().equalsIgnoreCase("T") && !valor.equalsIgnoreCase(respuesta)) {
                                cf = new CampoFallido();
                                cf.setIdCampo(idCampo);
                                cf.setTipoFallo(CampoFallido.VALOR_INVALIDO);
                                cf.setMensajeError("no valido");
                                //ver si se debe mostrar el posible valor
                                if (campo.getMostrarSugerencia().intValue() == 2) {
                                    cf.setPosibleValor("--");
                                } else {
                                    cf.setPosibleValor(respuesta);
                                }

                                fallidos.add(cf);
                                //
                                mapaValores.remove(idCampo);
                                mapaValores.put(idCampo, respuesta);
                            } else {
                                cf = new CampoFallido();
                                cf.setIdCampo(idCampo);
                                cf.setTipoFallo(CampoFallido.VALOR_CORRECTO);
                                cf.setMensajeError("valido");
                                cf.setPosibleValor("--");
                                fallidos.add(cf);
                            }
                        } catch (Exception ex) {
                            cf = new CampoFallido();
                            cf.setIdCampo(idCampo);
                            cf.setMensajeError("error al validar el campo");
                            cf.setPosibleValor("--");
                            cf.setTipoFallo(CampoFallido.VALOR_QUE_NO_SE_PUDO_CALCULAR);
                            fallidos.add(cf);

                            ex.printStackTrace();

                        }
                    } else if (validar.equals("todos") || campo.getEtiqueta().equals(validar)) {

                        cf = new CampoFallido();
                        cf.setIdCampo(idCampo);
                        cf.setTipoFallo(CampoFallido.VALOR_CORRECTO);
                        cf.setMensajeError("valido");
                        cf.setPosibleValor("--");
                        fallidos.add(cf);
                    }
                }

            }

            out.print("{campos:[");
            Iterator<CampoFallido> itFallidos = fallidos.iterator();
            Iterator<CampoFallido> itMand = mandatorios.iterator();
            while (itFallidos.hasNext()) {
                cf = itFallidos.next();
                out.print("{identificador:'" + cf.getIdCampo() + "',"
                        + "mensaje:'" + cf.getMensajeError() + "',"
                        + "tipofallo:'" + cf.getTipoFallo() + "',"
                        + "posiblevalor:'" + cf.getPosibleValor() + "'}");
                if (itFallidos.hasNext()) {
                    out.print(",");
                } else {
                    if (itMand.hasNext()) {
                        out.print(",");
                    }
                }
            }

            while (itMand.hasNext()) {
                cf = itMand.next();
                out.print("{identificador:'" + cf.getIdCampo() + "',"
                        + "mensaje:'" + cf.getMensajeError() + "',"
                        + "tipofallo:'" + cf.getTipoFallo() + "',"
                        + "posiblevalor:'" + cf.getPosibleValor() + "'}");
                if (itMand.hasNext()) {
                    out.print(",");
                }
            }
            out.print("]}");



        } finally {
            out.close();
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
