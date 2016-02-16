/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.konecta.redcobros.ejb.DatoConsultaFacade;
import py.com.konecta.redcobros.entities.DatoConsulta;
import py.com.konecta.redcobros.entities.DatoConsultaPK;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
public class CUENTAS_A_COBRAR extends HttpServlet {

    @EJB
    private DatoConsultaFacade DatoConsultaFacade;
    public static String REFERENCIA_PAGO = "REFERENCIA_PAGO";
    public static String REFERENCIA_BUSQUEDA = "REFERENCIA_BUSQUEDA";
    public static String DESCRIPCION_REF_PAGO = "DESCRIPCION_REF_PAGO";
    public static String MONEDA = "MONEDA";
    public static String MONTO = "MONTO";
    public static String MONTO_VENCIDO = "MONTO_VENCIDO";
    public static String INT_MORATORIO = "INT_MORATORIO";
    public static String VENCIMIENTO = "VENCIMIENTO";
    public static String COBRAR_VENCIDO = "COBRAR_VENCIDO";
    public static String TIPO_RECARGO = "TIPO_RECARGO";
    public static String SOLO_EFECTIVO = "SOLO_EFECTIVO";
    public static String PAGAR_MAS_VENCIDA = "PAGAR_MAS_VENCIDA";
    public static String ANULABLE = "ANULABLE";
    public static String SERVICIO = "SERVICIO";
    public static String FACTURADOR = "FACTURADOR";
    public static String PAGADO = "PAGADO";
    public static String SEPARADOR = "SEPARADOR";
    public static String SEPARADOR_TOKENS_PIPE = "|";
    public static String SEPARADOR_TOKENS_PUNTO_COMA = ";";
    public static String CONSULTA = "CONSULTA";
    public static String MENSAJE = "MENSAJE";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            JSONObject jsonRespuesta = new JSONObject();
            String opcion = request.getParameter("op");
            DateFormat df = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");
            String[] refPago = request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) != null && !request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO).substring(1).replace("undefined", " ").split(";") : null;
            String[] refBusqueda = request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA) != null && !request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA).substring(1).replace("undefined", " ").split(";") : null;
            String[] descRefPago = request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO) != null && !request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO).substring(1).replace("undefined", " ").split(";") : null;
            String[] moneda = request.getParameter(CUENTAS_A_COBRAR.MONEDA) != null && !request.getParameter(CUENTAS_A_COBRAR.MONEDA).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.MONEDA).substring(1).replace("undefined", " ").split(";") : null;
            String[] monto = request.getParameter(CUENTAS_A_COBRAR.MONTO) != null && !request.getParameter(CUENTAS_A_COBRAR.MONTO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.MONTO).substring(1).replace("undefined", " ").split(";") : null;
            String[] montoVencido = request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO) != null && !request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO).substring(1).replace("undefined", "").split(";") : null;
            String[] intMoratorio = request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO) != null && !request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO).substring(1).replace("undefined", " ").split(";") : null;
            String[] vencimiento = request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO) != null && !request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO).substring(1).replace("NaNNaNNaN", " ").split(";") : null;
            String[] cobrarVencido = request.getParameter(CUENTAS_A_COBRAR.COBRAR_VENCIDO) != null && !request.getParameter(CUENTAS_A_COBRAR.COBRAR_VENCIDO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.COBRAR_VENCIDO).substring(1).replace("undefined", " ").split(";") : null;
            String[] tipoRecargo = request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO) != null && !request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO).substring(1).replace("undefined", " ").split(";") : null;
            String[] soloEfectivo = request.getParameter(CUENTAS_A_COBRAR.SOLO_EFECTIVO) != null && !request.getParameter(CUENTAS_A_COBRAR.SOLO_EFECTIVO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.SOLO_EFECTIVO).substring(1).replace("undefined", " ").split(";") : null;
            String[] pagarMasVencido = request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA) != null && !request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA).substring(1).replace("undefined", " ").split(";") : null;
            String[] anulable = request.getParameter(CUENTAS_A_COBRAR.ANULABLE) != null && !request.getParameter(CUENTAS_A_COBRAR.ANULABLE).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.ANULABLE).substring(1).replace("undefined", " ").split(";") : null;
            String[] servicio = request.getParameter(CUENTAS_A_COBRAR.SERVICIO) != null && !request.getParameter(CUENTAS_A_COBRAR.SERVICIO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.SERVICIO).substring(1).replace("undefined", " ").split(";") : null;
            String[] mensaje = request.getParameter(CUENTAS_A_COBRAR.MENSAJE) != null && !request.getParameter(CUENTAS_A_COBRAR.MENSAJE).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.MENSAJE).substring(1).replace("undefined", " ").split(";") : null;

            String consulta = request.getParameter(CUENTAS_A_COBRAR.CONSULTA) != null && !request.getParameter(CUENTAS_A_COBRAR.CONSULTA).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.CONSULTA) : null;
            String primerResultado = request.getParameter("start");
            String cantResultados = request.getParameter("limit");

            HttpSession session = request.getSession();
            Long idFacturador = session.getAttribute("idFacturador") != null ? (Long) session.getAttribute("idFacturador") : 3L;
            if (opcion.equals("GUARDAR")) {
                for (int i = 0; refPago.length > i; i++) {
                    try {

                        DatoConsulta duplicado = new DatoConsulta();
                        DatoConsultaPK duplicadoPk = new DatoConsultaPK();
                        duplicadoPk.setIdFacturador(idFacturador);
                        duplicadoPk.setReferenciaPago(refPago[i]);
                        duplicadoPk.setIdServicio(new Integer(servicio[i]));
                        duplicado.setDatoConsultaPK(duplicadoPk);

                        if (DatoConsultaFacade.get(duplicado) == null) {

                            DatoConsulta fac = new DatoConsulta();
                            if (intMoratorio[i] != null && !intMoratorio[i].isEmpty() && !intMoratorio[i].equalsIgnoreCase(" ")) {
                                fac.setInteresMoratorio(new BigDecimal(intMoratorio[i]));//null
                            }
                            if (vencimiento[i] != null && !vencimiento[i].isEmpty() && !vencimiento[i].equalsIgnoreCase(" ")) {
                                fac.setVencimiento(spdf.parse(vencimiento[i]));//null
                            }
                            if (pagarMasVencido[i] != null && !pagarMasVencido[i].isEmpty() && !pagarMasVencido[i].equalsIgnoreCase(" ")) {
                                fac.setPagarMasVencido(pagarMasVencido[i].equalsIgnoreCase("S") ? 'S' : 'N');//null
                            }
                            if (mensaje[i] != null && !mensaje[i].isEmpty() && !mensaje[i].equalsIgnoreCase(" ")) {
                                fac.setMensaje(mensaje[i]);//null
                            }
                            if (descRefPago[i] != null && !descRefPago[i].isEmpty() && !descRefPago[i].equalsIgnoreCase(" ")) {
                                fac.setDescripcion(descRefPago[i]);
                            }
                            if (montoVencido[i] != null && !montoVencido[i].isEmpty() && !montoVencido[i].equalsIgnoreCase(" ")) {
                                fac.setMontoVencido(new BigDecimal(montoVencido[i]));
                            }
//                            DatoConsultaPK facPk = new DatoConsultaPK();
//                            facPk.setFacturador(idFacturador);
//                            facPk.setReferenciaPago(new Long(refPago[i]));
//                            facPk.setServicio(new Integer(servicio[i]));

                            fac.setAnulable(anulable[i].equalsIgnoreCase("S") ? 'S' : 'N');
                            fac.setCobrarVencido(cobrarVencido[i].equalsIgnoreCase("S") ? 'S' : 'N');

                            fac.setDatoConsultaPK(duplicadoPk);

                            fac.setMoneda(new Short(moneda[i]));

                            fac.setMonto(new BigDecimal(monto[i]));

                            fac.setPendiente('S');
                            fac.setReferenciaBuqueda(refBusqueda[i]);
                            //fac.setFechaIngreso(new Date());

                            fac.setSoloEfectivo(soloEfectivo[i].equalsIgnoreCase("S") ? 'N' : 'N');
                            fac.setTipoRecargo(tipoRecargo[i].equalsIgnoreCase("NINGUNO") ? new Short("0") : tipoRecargo[i].equalsIgnoreCase("MONTO") ? new Short("1") : new Short("2"));
                            DatoConsultaFacade.save(fac);
                        }

                    } catch (Exception e) {
                        jsonRespuesta.put("success", false);
                        out.println(jsonRespuesta.toString());
                        Logger.getLogger(CUENTAS_A_COBRAR.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("SAVE_FILE")) {
                String mensajeError = "";
                try {
                    String separador = request.getParameter(CUENTAS_A_COBRAR.SEPARADOR) != null && !request.getParameter(CUENTAS_A_COBRAR.SEPARADOR).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.SEPARADOR) : ";";
                    if (separador.equals("PIPE")) {
                        separador = CUENTAS_A_COBRAR.SEPARADOR_TOKENS_PIPE;
                    } else {
                        separador = CUENTAS_A_COBRAR.SEPARADOR_TOKENS_PUNTO_COMA;
                    }

                    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                    int codRetorno;
                    int contFilas = 0;
                    if (isMultipart) {
                        DiskFileItemFactory factory = new DiskFileItemFactory();
                        ServletFileUpload upload = new ServletFileUpload(factory);
                        List items = upload.parseRequest(request);
                        Iterator iter = items.iterator();
                        while (iter.hasNext()) {
                            FileItem item = (FileItem) iter.next();
                            InputStream uploadedStream = item.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
                            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
                            uploadedStream.close();
                            for (String linea : lineas) {
                                contFilas++;
                                try {
                                    codRetorno = DatoConsultaFacade.insertarDatoConsulta(linea, separador);
                                    switch (codRetorno) {
                                        case 1:
                                            mensajeError += "<br>" + "Registro Duplicado: " + contFilas;
                                            break;
                                        case 2:
                                            mensajeError += "<br>" + "Error en Registro: " + contFilas;
                                            break;
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(CUENTAS_A_COBRAR.class.getName()).log(Level.SEVERE, null, ex);
                                    mensajeError += "<br>" + "Error en Registro: " + contFilas;
                                }
                            }
                            break;
                        }
                    }

                    if (mensajeError.isEmpty()) {
                        jsonRespuesta.put("success", true);
                    } else {
                        jsonRespuesta.put("success", false);
                        jsonRespuesta.put("motivo", "Los demás registros fueron ingresados con éxito." + mensajeError);
                    }

                } catch (Exception ex) {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "Error al procesar el archivo");
                }
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("LISTAR")) {

                DatoConsulta ejemplo = new DatoConsulta();
                if (consulta != null) {
                    DatoConsultaPK fPK = new DatoConsultaPK();
                    fPK.setReferenciaPago(consulta);
                    ejemplo.setDatoConsultaPK(fPK);
                }

//                    Facturador facturador = new Facturador();
//                    facturador.setIdFacturador(idFacturador);
//                    ejemplo.setFacturador(facturador);

                List<DatoConsulta> lDatoConsulta = DatoConsultaFacade.list(ejemplo, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), true);

                JSONArray jsonDatoConsulta = new JSONArray();
                for (DatoConsulta e : lDatoConsulta) {
                    JSONObject jsonFactura = new JSONObject();
                    jsonFactura.put(CUENTAS_A_COBRAR.ANULABLE, e.getAnulable() == 'S' ? "SI" : "NO");
                    jsonFactura.put(CUENTAS_A_COBRAR.COBRAR_VENCIDO, e.getCobrarVencido() == 'S' ? "SI" : "NO");
                    jsonFactura.put(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO, e.getDescripcion() != null ? e.getDescripcion() : "");
                    jsonFactura.put(CUENTAS_A_COBRAR.INT_MORATORIO, e.getInteresMoratorio() != null ? e.getInteresMoratorio() : "");
                    jsonFactura.put(CUENTAS_A_COBRAR.MONEDA, e.getMoneda().toString().equals("1") ? "GS" : "USD");
                    jsonFactura.put(CUENTAS_A_COBRAR.MONTO, e.getMonto());
                    jsonFactura.put(CUENTAS_A_COBRAR.FACTURADOR, e.getDatoConsultaPK().getIdFacturador());
                    jsonFactura.put(CUENTAS_A_COBRAR.MONTO_VENCIDO, e.getMontoVencido() != null ? e.getMontoVencido() : null);
                    jsonFactura.put(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA, e.getPagarMasVencido() != null ? e.getPagarMasVencido() == 'S' ? "SI" : "NO" : "");
                    jsonFactura.put(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA, e.getReferenciaBuqueda());
                    jsonFactura.put(CUENTAS_A_COBRAR.REFERENCIA_PAGO, e.getDatoConsultaPK().getReferenciaPago().toString());
                    jsonFactura.put(CUENTAS_A_COBRAR.SERVICIO, e.getDatoConsultaPK().getIdServicio());
                    jsonFactura.put(CUENTAS_A_COBRAR.PAGADO, e.getPendiente() == 'N' ? "SI" : "NO");
                    jsonFactura.put(CUENTAS_A_COBRAR.SOLO_EFECTIVO, e.getSoloEfectivo() == 'S' ? "SI" : "NO");
                    jsonFactura.put(CUENTAS_A_COBRAR.TIPO_RECARGO, e.getTipoRecargo() == 0 ? "NINGUNO" : e.getTipoRecargo() == 1 ? "MONTO" : "PORCENTAJE");
                    jsonFactura.put(CUENTAS_A_COBRAR.VENCIMIENTO, e.getVencimiento() != null ? spdf.format(e.getVencimiento()) : "");
                    jsonFactura.put(CUENTAS_A_COBRAR.MENSAJE, e.getMensaje() != null ? e.getMensaje() : "");
                    jsonDatoConsulta.add(jsonFactura);
                }

                jsonRespuesta.put("FACTURAS", jsonDatoConsulta);
                jsonRespuesta.put("TOTAL", DatoConsultaFacade.total(ejemplo, true));

                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());
            } else if (opcion.equalsIgnoreCase("DETALLE")) {
                Integer idServicio = request.getParameter(CUENTAS_A_COBRAR.SERVICIO) != null && !request.getParameter(CUENTAS_A_COBRAR.SERVICIO).isEmpty() ? new Integer(request.getParameter(CUENTAS_A_COBRAR.SERVICIO)) : null;
                Long idFac = request.getParameter(CUENTAS_A_COBRAR.FACTURADOR) != null && !request.getParameter(CUENTAS_A_COBRAR.FACTURADOR).isEmpty() ? new Long(request.getParameter(CUENTAS_A_COBRAR.FACTURADOR)) : null;
                String idRefPago = request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) != null && !request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) : null;
                DatoConsulta factura = new DatoConsulta();
                DatoConsultaPK ejemploPk = new DatoConsultaPK();
                ejemploPk.setIdServicio(idServicio);
                ejemploPk.setIdFacturador(idFac);
                ejemploPk.setReferenciaPago(idRefPago);
                factura = DatoConsultaFacade.get(ejemploPk);
                JSONObject jsonFactura = new JSONObject();
                jsonFactura.put(CUENTAS_A_COBRAR.ANULABLE, factura.getAnulable() == 'S' ? "SI" : "NO");
                jsonFactura.put(CUENTAS_A_COBRAR.COBRAR_VENCIDO, factura.getCobrarVencido() == 'S' ? "SI" : "NO");
                jsonFactura.put(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO, factura.getDescripcion() != null ? factura.getDescripcion() : "");
                jsonFactura.put(CUENTAS_A_COBRAR.INT_MORATORIO, factura.getInteresMoratorio() != null ? factura.getInteresMoratorio() : "");
                jsonFactura.put(CUENTAS_A_COBRAR.MONEDA, factura.getMoneda().toString().equals("1") ? "GS" : "USD");
                jsonFactura.put(CUENTAS_A_COBRAR.MONTO, factura.getMonto());
                jsonFactura.put(CUENTAS_A_COBRAR.FACTURADOR, factura.getDatoConsultaPK().getIdFacturador());
                jsonFactura.put(CUENTAS_A_COBRAR.MONTO_VENCIDO, factura.getMontoVencido() != null ? factura.getMontoVencido() : "");
                jsonFactura.put(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA, factura.getPagarMasVencido() != null ? factura.getPagarMasVencido().equals('S') ? "SI" : "NO" : "");
                jsonFactura.put(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA, factura.getReferenciaBuqueda());
                jsonFactura.put(CUENTAS_A_COBRAR.REFERENCIA_PAGO, factura.getDatoConsultaPK().getReferenciaPago().toString());
                jsonFactura.put(CUENTAS_A_COBRAR.SERVICIO, factura.getDatoConsultaPK().getIdServicio());
                jsonFactura.put(CUENTAS_A_COBRAR.PAGADO, factura.getPendiente() == 'S' ? "SI" : "NO");
                jsonFactura.put(CUENTAS_A_COBRAR.SOLO_EFECTIVO, factura.getSoloEfectivo() == 'S' ? "SI" : "NO");
                jsonFactura.put(CUENTAS_A_COBRAR.TIPO_RECARGO, factura.getTipoRecargo() == '0' ? "NINGUNO" : factura.getTipoRecargo() == '1' ? "MONTO" : "PORCENTAJE");
                jsonFactura.put(CUENTAS_A_COBRAR.VENCIMIENTO, factura.getVencimiento() != null ? spdf.format(factura.getVencimiento()) : "");
                jsonFactura.put(CUENTAS_A_COBRAR.MENSAJE, factura.getMensaje() != null ? factura.getMensaje() : "");
                jsonRespuesta.put("data", jsonFactura);
                jsonRespuesta.put("TOTAL", 1);
                jsonRespuesta.put("success", true);
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("AGREGAR")) {
                String refPagoAdd = request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) != null && !request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) : null;
                String refBusquedaAdd = request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA) != null && !request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA) : null;
                String descRefPagoAdd = request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO) != null && !request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO) : null;
                Short monedaAdd = request.getParameter(CUENTAS_A_COBRAR.MONEDA) != null && !request.getParameter(CUENTAS_A_COBRAR.MONEDA).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.MONEDA).equalsIgnoreCase("GS") ? new Short("1") : new Short("2") : null;
                BigDecimal montoAdd = request.getParameter(CUENTAS_A_COBRAR.MONTO) != null && !request.getParameter(CUENTAS_A_COBRAR.MONTO).isEmpty() ? new BigDecimal(request.getParameter(CUENTAS_A_COBRAR.MONTO)) : null;
                BigDecimal montoVencidoAdd = request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO) != null && !request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO).isEmpty() ? new BigDecimal(request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO)) : null;
                BigDecimal intMoratorioAdd = request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO) != null && !request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO).isEmpty() ? new BigDecimal(request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO)) : null;
                Date vencimientoAdd = request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO) != null && !request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO).isEmpty() ? spdf.parse(request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO)) : null;
                Character cobrarVencidoAdd = request.getParameter(CUENTAS_A_COBRAR.COBRAR_VENCIDO) != null && !request.getParameter(CUENTAS_A_COBRAR.COBRAR_VENCIDO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.COBRAR_VENCIDO).equalsIgnoreCase("SI") ? 'S' : 'N' : null;
                Short tipoRecargoAdd = request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO) != null && !request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO).equalsIgnoreCase("NINGUNO") ? new Short("0") : request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO).equalsIgnoreCase("MONTO") ? new Short("1") : new Short("2") : null;
                Character soloEfectivoAdd = request.getParameter(CUENTAS_A_COBRAR.SOLO_EFECTIVO) != null && !request.getParameter(CUENTAS_A_COBRAR.SOLO_EFECTIVO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.SOLO_EFECTIVO).equalsIgnoreCase("SI") ? 'S' : 'N' : null;
                Character pagarMasVencidoAdd = request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA) != null && !request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA).equalsIgnoreCase("SI") ? 'S' : 'N' : null;
                Character anulableAdd = request.getParameter(CUENTAS_A_COBRAR.ANULABLE) != null && !request.getParameter(CUENTAS_A_COBRAR.ANULABLE).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.ANULABLE).equalsIgnoreCase("SI") ? 'S' : 'N' : null;
                Integer servicioAdd = request.getParameter(CUENTAS_A_COBRAR.SERVICIO) != null && !request.getParameter(CUENTAS_A_COBRAR.SERVICIO).isEmpty() ? new Integer(request.getParameter(CUENTAS_A_COBRAR.SERVICIO)) : null;
                String mensajeAdd = request.getParameter(CUENTAS_A_COBRAR.MENSAJE) != null && !request.getParameter(CUENTAS_A_COBRAR.MENSAJE).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.MENSAJE) : null;
                DatoConsulta duplicado = new DatoConsulta();
                DatoConsultaPK controlPk = new DatoConsultaPK();
                controlPk.setIdFacturador(idFacturador);
                controlPk.setReferenciaPago(refPagoAdd);
                controlPk.setIdServicio(servicioAdd);
                duplicado.setDatoConsultaPK(controlPk);

                if (DatoConsultaFacade.get(duplicado) == null) {

                    DatoConsulta factura = new DatoConsulta();
                    try {
                        if (vencimientoAdd != null) {
                            factura.setVencimiento(vencimientoAdd);
                        }
                        if (intMoratorioAdd != null) {
                            factura.setInteresMoratorio(intMoratorioAdd);
                        }
                        if (mensajeAdd != null) {
                            factura.setMensaje(mensajeAdd);
                        }
                        if (montoVencidoAdd != null) {
                            factura.setMontoVencido(montoVencidoAdd);
                        }
                        if (descRefPagoAdd != null) {
                            factura.setDescripcion(descRefPagoAdd);
                        }
                        factura.setAnulable(anulableAdd);
                        factura.setCobrarVencido(cobrarVencidoAdd);
                        DatoConsultaPK facPk = new DatoConsultaPK();
                        facPk.setIdFacturador(idFacturador);
                        facPk.setReferenciaPago(refPagoAdd);
                        facPk.setIdServicio(servicioAdd);
                        factura.setDatoConsultaPK(facPk);

                        factura.setMoneda(monedaAdd);
                        factura.setMonto(montoAdd);

                        factura.setPendiente('S');
                        factura.setPagarMasVencido(pagarMasVencidoAdd);
                        factura.setReferenciaBuqueda(refBusquedaAdd);
                        factura.setSoloEfectivo(soloEfectivoAdd);
                        factura.setTipoRecargo(tipoRecargoAdd);
                        //factura.setFechaIngreso(new Date());
                        DatoConsultaFacade.save(factura);
                        jsonRespuesta.put("success", true);
                    } catch (Exception e) {
                        jsonRespuesta.put("success", true);
                        jsonRespuesta.put("motivo", "No se pudo agregar el registro.");
                        Logger.getLogger(CUENTAS_A_COBRAR.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se pudo agregar el registro.");
                }
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("MODIFICAR")) {
                Integer idServicio = request.getParameter(CUENTAS_A_COBRAR.SERVICIO) != null && !request.getParameter(CUENTAS_A_COBRAR.SERVICIO).isEmpty() ? new Integer(request.getParameter(CUENTAS_A_COBRAR.SERVICIO)) : null;
                Long idFac = request.getParameter(CUENTAS_A_COBRAR.FACTURADOR) != null && !request.getParameter(CUENTAS_A_COBRAR.FACTURADOR).isEmpty() ? new Long(request.getParameter(CUENTAS_A_COBRAR.FACTURADOR)) : null;
                String idRefPago = request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) != null && !request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) : null;
                DatoConsulta factura = new DatoConsulta();
                DatoConsultaPK ejemploPk = new DatoConsultaPK();
                ejemploPk.setIdServicio(idServicio);
                ejemploPk.setIdFacturador(idFac);
                ejemploPk.setReferenciaPago(idRefPago);
                factura = DatoConsultaFacade.get(ejemploPk);

                if (factura.getPendiente() == 'S') {
                    try {
                        factura.setAnulable(request.getParameter(CUENTAS_A_COBRAR.ANULABLE).equalsIgnoreCase("SI") ? 'S' : 'N');
                        factura.setCobrarVencido(request.getParameter(CUENTAS_A_COBRAR.COBRAR_VENCIDO).equalsIgnoreCase("SI") ? 'S' : 'N');

                        if (request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO) != null && !request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO).isEmpty()) {
                            factura.setVencimiento(spdf.parse(request.getParameter(CUENTAS_A_COBRAR.VENCIMIENTO)));
                        }
                        if (request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO) != null && !request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO).isEmpty()) {
                            factura.setInteresMoratorio(new BigDecimal(request.getParameter(CUENTAS_A_COBRAR.INT_MORATORIO).replace(",", ".")));
                        }
                        if (request.getParameter(CUENTAS_A_COBRAR.MENSAJE) != null && !request.getParameter(CUENTAS_A_COBRAR.MENSAJE).isEmpty()) {
                            factura.setMensaje(request.getParameter(CUENTAS_A_COBRAR.MENSAJE));
                        }
                        if (request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO) != null && !request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO).isEmpty()) {
                            factura.setDescripcion(request.getParameter(CUENTAS_A_COBRAR.DESCRIPCION_REF_PAGO));
                        }
                        if (request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO) != null && !request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO).isEmpty()) {
                            factura.setMontoVencido(new BigDecimal(request.getParameter(CUENTAS_A_COBRAR.MONTO_VENCIDO)));
                        }
                        if (request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA) != null && !request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA).isEmpty()) {
                            factura.setPagarMasVencido(request.getParameter(CUENTAS_A_COBRAR.PAGAR_MAS_VENCIDA).equalsIgnoreCase("SI") ? 'S' : 'N');
                        }

                        factura.setMoneda(new Short(request.getParameter(CUENTAS_A_COBRAR.MONEDA).equalsIgnoreCase("GS") ? "1" : "2"));
                        factura.setMonto(new BigDecimal(request.getParameter(CUENTAS_A_COBRAR.MONTO)));
                        factura.setReferenciaBuqueda(request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_BUSQUEDA));
                        factura.setSoloEfectivo(request.getParameter(CUENTAS_A_COBRAR.SOLO_EFECTIVO).equalsIgnoreCase("SI") ? 'S' : 'N');
                        factura.setTipoRecargo(request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO).equalsIgnoreCase("NINGUNO") ? new Short("0") : request.getParameter(CUENTAS_A_COBRAR.TIPO_RECARGO).equalsIgnoreCase("MONTO") ? new Short("1") : new Short("2"));
                        //factura.setFechaIngreso(new Date());
                        DatoConsultaFacade.update(factura);
                        jsonRespuesta.put("success", true);
                    } catch (Exception e) {
                        Logger.getLogger(CUENTAS_A_COBRAR.class.getName()).log(Level.SEVERE, null, e);
                        jsonRespuesta.put("success", false);
                        jsonRespuesta.put("motivo", "No se pudo actualizar el registro.");
                    }

                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("motivo", "No se puede modificar una factura cobrada.");
                }
                out.println(jsonRespuesta.toString());

            } else if (opcion.equalsIgnoreCase("BORRAR")) {
                try {
                    Integer idServicio = request.getParameter(CUENTAS_A_COBRAR.SERVICIO) != null && !request.getParameter(CUENTAS_A_COBRAR.SERVICIO).isEmpty() ? new Integer(request.getParameter(CUENTAS_A_COBRAR.SERVICIO)) : null;
                    String idRefPago = request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) != null && !request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO).isEmpty() ? request.getParameter(CUENTAS_A_COBRAR.REFERENCIA_PAGO) : null;
                    Long idFac = request.getParameter(CUENTAS_A_COBRAR.FACTURADOR) != null && !request.getParameter(CUENTAS_A_COBRAR.FACTURADOR).isEmpty() ? new Long(request.getParameter(CUENTAS_A_COBRAR.FACTURADOR)) : null;
                    DatoConsultaPK fpk = new DatoConsultaPK(idFac, idServicio, idRefPago);
                    DatoConsultaFacade.delete(fpk);
                    jsonRespuesta.put("success", true);
                } catch (Exception e) {
                    Logger.getLogger(CUENTAS_A_COBRAR.class.getName()).log(Level.SEVERE, null, e);
                    jsonRespuesta.put("success", false);
                }
                out.println(jsonRespuesta.toString());
            }
        } catch (Exception e) {
            Logger.getLogger(CUENTAS_A_COBRAR.class.getName()).log(Level.SEVERE, null, e);
            JSONObject jsonRespuesta = new JSONObject();
            jsonRespuesta.put("success", false);
            jsonRespuesta.put("motivo", e.getMessage());
            out.println(jsonRespuesta.toString());

        } finally {
            try {
                //out.close();
            } finally {
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
