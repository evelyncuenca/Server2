/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.cobrosweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import py.com.documenta.ws.RedCobro;
import py.com.documenta.ws.RedCobroService;
import py.com.documenta.ws.practigiro.*;
import py.com.konecta.redcobros.ejb.*;
import py.com.konecta.redcobros.entities.Ciudad;
import py.com.konecta.redcobros.entities.*;

/**
 *
 * @author fgonzalez
 */
public class PRACTIGIRO extends HttpServlet {

    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    @EJB
    private RuteoServicioFacade ruteoServicioFacade;
    @EJB
    private CiudadExtFacade ciudadExtFacade;
    @EJB
    private DepartamentoExtFacade departamentoExtFacade;
    @EJB
    private SucursalFacade sucursalFacade;
    private static String TIPO_DOC = "TIPO_DOC";
    private static String ID_DOCUMENTO = "ID_DOCUMENTO";
    private static String NOMBRES = "NOMBRES";
    private static String APELLIDOS = "APELLIDOS";
    private static String FECHA_NAC = "FECHA_NAC";
    private static String PAIS = "PAIS";
    private static String DEPARTAMENTO = "DEPARTAMENTO";
    private static String CIUDAD = "CIUDAD";
    private static String DIRECCION = "DIRECCION";
    private static String TELEFONO = "TELEFONO";
    private static String BARRIO = "BARRIO";
    private static String FECHA_DOCUMENTO = "FECHA_DOCUMENTO";
    private static String PROFESION = "PROFESION";
    private static String COD_CLIENTE = "COD_CLIENTE";
    private static String COD_REMESA = "COD_REMESA";
    private static String MOVIL = "MOVIL";
    private static String MONTO = "MONTO";
    private static String TIPO_OP = "TIPO_OP";
    private static String PRESENTO = "PRESENTO";
    private static String ID_TRX = "ID_TRX";
    private static String IS_COM_INCL = "IS_COM_INCL";
    private static String MONEDA = "MONEDA";
    
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            JSONObject jsonRespuesta = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            String opcion = request.getParameter("op");

            Short tipoDocumento = request.getParameter(PRACTIGIRO.TIPO_DOC) != null && !request.getParameter(PRACTIGIRO.TIPO_DOC).isEmpty() ? new Short(request.getParameter(PRACTIGIRO.TIPO_DOC)) : null;
            String idDocumento = request.getParameter(PRACTIGIRO.ID_DOCUMENTO) != null && !request.getParameter(PRACTIGIRO.ID_DOCUMENTO).isEmpty() ? request.getParameter(PRACTIGIRO.ID_DOCUMENTO) : null;
            String nombres = request.getParameter(PRACTIGIRO.NOMBRES) != null && !request.getParameter(PRACTIGIRO.NOMBRES).isEmpty() ? request.getParameter(PRACTIGIRO.NOMBRES) : null;
            String apellidos = request.getParameter(PRACTIGIRO.APELLIDOS) != null && !request.getParameter(PRACTIGIRO.APELLIDOS).isEmpty() ? request.getParameter(PRACTIGIRO.APELLIDOS) : null;
            String fechaNacimiento = request.getParameter(PRACTIGIRO.FECHA_NAC) != null && !request.getParameter(PRACTIGIRO.FECHA_NAC).isEmpty() ? request.getParameter(PRACTIGIRO.FECHA_NAC) : null;
            String fechaDocumento = request.getParameter(PRACTIGIRO.FECHA_DOCUMENTO) != null && !request.getParameter(PRACTIGIRO.FECHA_DOCUMENTO).isEmpty() ? request.getParameter(PRACTIGIRO.FECHA_DOCUMENTO) : null;
            Short pais = request.getParameter(PRACTIGIRO.PAIS) != null && !request.getParameter(PRACTIGIRO.PAIS).isEmpty() ? new Short(request.getParameter(PRACTIGIRO.PAIS)) : null;
            Short departamento = request.getParameter(PRACTIGIRO.DEPARTAMENTO) != null && !request.getParameter(PRACTIGIRO.DEPARTAMENTO).isEmpty() ? new Short(request.getParameter(PRACTIGIRO.DEPARTAMENTO)) : null;
            Integer ciudad = request.getParameter(PRACTIGIRO.CIUDAD) != null && !request.getParameter(PRACTIGIRO.CIUDAD).isEmpty() ? new Integer(request.getParameter(PRACTIGIRO.CIUDAD)) : null;
            Short barrio = request.getParameter(PRACTIGIRO.BARRIO) != null && !request.getParameter(PRACTIGIRO.BARRIO).isEmpty() ? new Short(request.getParameter(PRACTIGIRO.BARRIO)) : null;
            String direccion = request.getParameter(PRACTIGIRO.DIRECCION) != null && !request.getParameter(PRACTIGIRO.DIRECCION).isEmpty() ? request.getParameter(PRACTIGIRO.DIRECCION) : null;
            String telefono = request.getParameter(PRACTIGIRO.TELEFONO) != null && !request.getParameter(PRACTIGIRO.TELEFONO).isEmpty() ? request.getParameter(PRACTIGIRO.TELEFONO) : null;
            String movil = request.getParameter(PRACTIGIRO.MOVIL) != null && !request.getParameter(PRACTIGIRO.MOVIL).isEmpty() ? request.getParameter(PRACTIGIRO.MOVIL) : null;
            String profesion = request.getParameter(PRACTIGIRO.PROFESION) != null && !request.getParameter(PRACTIGIRO.PROFESION).isEmpty() ? request.getParameter(PRACTIGIRO.PROFESION) : null;
            String codCliente = request.getParameter(PRACTIGIRO.COD_CLIENTE) != null && !request.getParameter(PRACTIGIRO.COD_CLIENTE).isEmpty() ? request.getParameter(PRACTIGIRO.COD_CLIENTE) : "";
            String codigoRemesa = request.getParameter(PRACTIGIRO.COD_REMESA) != null && !request.getParameter(PRACTIGIRO.COD_REMESA).isEmpty() ? request.getParameter(PRACTIGIRO.COD_REMESA) : null;
            String monto = request.getParameter(PRACTIGIRO.MONTO) != null && !request.getParameter(PRACTIGIRO.MONTO).isEmpty() ? request.getParameter(PRACTIGIRO.MONTO).replaceAll(",", "") : null;
            String moneda = request.getParameter(PRACTIGIRO.MONEDA) != null && !request.getParameter(PRACTIGIRO.MONEDA).isEmpty() ? request.getParameter(PRACTIGIRO.MONEDA) : "600";
            String isComisionInclud = request.getParameter(PRACTIGIRO.IS_COM_INCL) != null && !request.getParameter(PRACTIGIRO.IS_COM_INCL).isEmpty() ? request.getParameter(PRACTIGIRO.IS_COM_INCL) : null;


            ParametroSistema param = new ParametroSistema();
            param.setNombreParametro("userPG");
            String user = parametroSistemaFacade.get(param).getValor();
            param.setNombreParametro("passPG");
            String password = parametroSistemaFacade.get(param).getValor();

            RuteoServicio ruteoRC = (RuteoServicio) session.getAttribute("ruteoServicio");
            try {
                if (ruteoRC == null) {
                    ruteoRC = ruteoServicioFacade.get(2L);
                    session.setAttribute("ruteoServicio", ruteoRC);
                }
                Logger.getLogger(PRACTIGIRO.class.getName()).log(Level.INFO, "URL:{0}", ruteoRC.getUrlHost());
            } catch (Exception e) {
                Logger.getLogger(PRACTIGIRO.class.getName()).log(Level.SEVERE, null, e);
            }

            RedCobro rcService = getWSManager(ruteoRC.getUrlHost(), "http://ws.documenta.com.py/", "RedCobroService", ruteoRC.getConexionTo(), ruteoRC.getReadTo());

            if (opcion.equalsIgnoreCase("CIUDAD")) {
                CiudadExt entity = new CiudadExt();
                CiudadExtPK pk = new CiudadExtPK();
                pk.setEntidad(1);
                if ((request.getParameter("DEPARTAMENTO") != null) && (request.getParameter("DEPARTAMENTO").matches("[0-9]+"))) {
                    Ciudad c = new Ciudad();
                    c.setDepartamento(new Departamento(Long.valueOf(request.getParameter("DEPARTAMENTO"))));
                    entity.setCiudad(c);
                }

                entity.setCiudadExtPK(pk);
                if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                    entity.setDescripcion(request.getParameter("query"));
                }

                List<CiudadExt> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = ciudadExtFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
                } else {
                    lista = ciudadExtFacade.list(entity, true);
                }
                total = ciudadExtFacade.total(entity, true);

                for (CiudadExt e : lista) {
                    JSONObject propiedad2 = new JSONObject();
                    propiedad2.put("CIUDAD", e.getCiudadExtPK().getIdCiudadExt());
                    propiedad2.put("DESCRIPCION_CIUDAD", e.getDescripcion());

                    jsonArray.add(propiedad2);
                }
                jsonRespuesta.put("CIUDAD", jsonArray);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
            } else if (opcion.equalsIgnoreCase("DEPARTAMENTO")) {

                DepartamentoExt entity = new DepartamentoExt();
                DepartamentoExtPK pk = new DepartamentoExtPK();
                pk.setEntidad(1);
                entity.setDepartamentoExtPK(pk);
                if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                    entity.setDescripcion(request.getParameter("query"));
                }

                List<DepartamentoExt> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = departamentoExtFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
                } else {
                    lista = departamentoExtFacade.list(entity, true);
                }
                total = departamentoExtFacade.total(entity, true);

                for (DepartamentoExt e : lista) {
                    JSONObject propiedad2 = new JSONObject();
                    propiedad2.put("DEPARTAMENTO", e.getDepartamentoExtPK().getIdDepartamentoExt());
                    propiedad2.put("DESCRIPCION_DEPARTAMENTO", e.getDescripcion());

                    jsonArray.add(propiedad2);
                }
                jsonRespuesta.put("DEPARTAMENTO", jsonArray);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
            } else if (opcion.equalsIgnoreCase("SUCURSAL")) {
                Sucursal entity = new Sucursal();

                if ((request.getParameter("CIUDAD") != null) && (request.getParameter("CIUDAD").matches("[0-9]+"))) {
                    Ciudad c = new Ciudad();
                    c.setIdCiudad(Long.valueOf(request.getParameter("CIUDAD")));
                    Localidad localidad = new Localidad();
                    localidad.setCiudad(c);
                    entity.setLocalidad(localidad);
                }

                if ((request.getParameter("query") != null) && (!request.getParameter("query").isEmpty())) {
                    entity.setDescripcion(request.getParameter("query"));
                }

                List<Sucursal> lista;
                int total;
                String primerResultado = request.getParameter("start");
                String cantResultados = request.getParameter("limit");
                if (primerResultado != null && cantResultados != null) {
                    lista = sucursalFacade.list(entity, Integer.parseInt(primerResultado), Integer.parseInt(cantResultados), "descripcion", "ASC", true);
                } else {
                    lista = sucursalFacade.list(entity, true);
                }
                total = sucursalFacade.total(entity, true);

                for (Sucursal e : lista) {
                    if (e.getRecaudador().getHabilitado().equalsIgnoreCase("S")) {
                        JSONObject propiedad2 = new JSONObject();
                        propiedad2.put("SUCURSAL", e.getIdSucursal());
                        propiedad2.put("DESCRIPCION_SUCURSAL", e.getRecaudador().getDescripcion() + "(" + e.getDescripcion() + ")");
                        jsonArray.add(propiedad2);
                    } else {
                        --total;
                    }
                }
                jsonRespuesta.put("SUCURSAL", jsonArray);
                jsonRespuesta.put("TOTAL", total);
                jsonRespuesta.put("success", true);
            } else if (opcion.equalsIgnoreCase("TIPO_DOC")) {
                List<TipoDocumento> listTipoDoc = rcService.listTipoDocumento(user, password);
                if (listTipoDoc != null && !listTipoDoc.isEmpty()) {
                    for (TipoDocumento it : listTipoDoc) {
                        JSONObject jsonTipoDoc = new JSONObject();
                        jsonTipoDoc.put("DESCRIPCION_TIPO_DOC", it.getDescripcion());
                        jsonTipoDoc.put("TIPO_DOC", it.getIdTipoDocumento());
                        jsonArray.add(jsonTipoDoc);
                    }
                    jsonRespuesta.put("TIPO_DOC", jsonArray);
                    jsonRespuesta.put("TOTAL", listTipoDoc.size());
                    jsonRespuesta.put("success", true);
                } else {
                    jsonRespuesta.put("success", false);
                }
            } else if (opcion.equalsIgnoreCase("PARAM_COMISION")) {               
                ParametroComision paramComision = rcService.getParametroComision(user, password, monto, moneda, isComisionInclud);
                if (paramComision.getRespuesta().getCodigoRetorno() == 0) {
                    jsonRespuesta.put("PORCENTAJE", paramComision.getPorcentaje());
                    jsonRespuesta.put("IMPORTE", paramComision.getImporte());
                    jsonRespuesta.put("COMISION", paramComision.getComision());
                    jsonRespuesta.put("ID_TABLA", paramComision.getIdTable());
                    jsonRespuesta.put("TABLA", paramComision.getTablaComisional());
                    
                    jsonRespuesta.put("success", true);
                } else {
                    jsonRespuesta.put("MENSAJE", paramComision.getRespuesta().getDescripcion());
                    jsonRespuesta.put("success", false);
                }

            } else if (opcion.equalsIgnoreCase("GET_CLIENTE")) {
                if (tipoDocumento != null && idDocumento != null) {
                    InfoCliente infoCliente = rcService.getCliente(user, password, tipoDocumento, idDocumento);
                    if (infoCliente.getRespuesta().getCodigoRetorno() == 0) {
                        jsonRespuesta.put("NOMBRES", infoCliente.getNombres());
                        jsonRespuesta.put("APELLIDOS", infoCliente.getApellidos());
                        jsonRespuesta.put("DIRECCION", infoCliente.getDireccion());
                        jsonRespuesta.put("TELEFONO", infoCliente.getTelefono());
                        jsonRespuesta.put("PROFESION", infoCliente.getProfesion());
                        jsonRespuesta.put("FECHA_NAC", sdf.format(infoCliente.getFechaNacimiento().toGregorianCalendar().getTime()));
                        jsonRespuesta.put("FECHA_DOC", sdf.format(infoCliente.getFechaEmisionDocumento().toGregorianCalendar().getTime()));
                        jsonRespuesta.put("success", true);
                    } else {
                        jsonRespuesta.put("MENSAJE", infoCliente.getRespuesta().getDescripcion());
                        jsonRespuesta.put("success", false);
                    }
                } else {
                    jsonRespuesta.put("MENSAJE", "Favor completar todos los campos solicitados");
                    jsonRespuesta.put("success", false);
                }
            } else if (opcion.equalsIgnoreCase("ALTA_CLIENTE")) {
                if (tipoDocumento != null && idDocumento != null
                        && fechaDocumento != null && nombres != null
                        && apellidos != null && fechaNacimiento != null
                        && direccion != null && telefono != null) {
                    GregorianCalendar fechaNacGreg = new GregorianCalendar();
                    fechaNacGreg.setTime(sdf.parse(fechaNacimiento));
                    GregorianCalendar fechaEmiGreg = new GregorianCalendar();
                    fechaEmiGreg.setTime(sdf.parse(fechaDocumento));

                    XMLGregorianCalendar fechaNacXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaNacGreg);
                    XMLGregorianCalendar fechaEmisionXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaEmiGreg);

                    InfoCliente cliente = new InfoCliente();
                    InfoClienteId id = new InfoClienteId();

                    id.setIdDocumento(idDocumento);
                    id.setIdTipoDocumento(tipoDocumento);

                    cliente.setId(id);
                    cliente.setNombres(nombres);
                    cliente.setApellidos(apellidos);
                    cliente.setDireccion(direccion);
                    cliente.setTelefono(telefono);
                    cliente.setFechaNacimiento(fechaNacXML);
                    cliente.setFechaEmisionDocumento(fechaEmisionXML);
                    cliente.setProfesion(profesion);

                    Respuesta respuestaAlta = rcService.altaCliente(user, password, cliente);
                    jsonRespuesta.put("success", respuestaAlta.getCodigoRetorno() == 0);
                    jsonRespuesta.put("MENSAJE", respuestaAlta.getDescripcion());
                } else {
                    jsonRespuesta.put("MENSAJE", "Favor completar todos los campos solicitados");
                    jsonRespuesta.put("success", false);
                }
            } else if (opcion.equalsIgnoreCase("GET_DATOS_ENVIO")) {
                Long idRed = (Long) request.getSession().getAttribute("idRed");
                Long idRecaudador = (Long) request.getSession().getAttribute("idRecaudador");
                Long idSucursal = (Long) request.getSession().getAttribute("idSucursal");
                DatosTransaccion datosEnvio = rcService.getDatosEnvio(user, password, codigoRemesa, tipoDocumento, idDocumento, idRed.toString(), idRecaudador.toString(), idSucursal.toString());
                Respuesta respuesta = datosEnvio.getRespuesta();
                if (respuesta.getCodigoRetorno() == 0) {
                    String mensajePromocional = "";
                    if (datosEnvio.getMensajePromocional() != null
                            && !datosEnvio.getMensajePromocional().isEmpty()) {
                        mensajePromocional = "\nMensaje Promocional: " + datosEnvio.getMensajePromocional();
                    }
                    jsonRespuesta.put("MENSAJE", datosEnvio.getDatosTrx().replaceAll("#", "\n") + mensajePromocional);
                    jsonRespuesta.put("MONTO_BASE", datosEnvio.getMontoBase());
                    jsonRespuesta.put("COMISION", datosEnvio.getComision());
                    jsonRespuesta.put("success", true);
                } else {
                    jsonRespuesta.put("MENSAJE", datosEnvio.getRespuesta().getDescripcion());
                    jsonRespuesta.put("success", false);
                }

            } else if (opcion.equalsIgnoreCase("SET_BLOQUEO_DDJJ")) {
                String idTipoOperacion = request.getParameter(PRACTIGIRO.TIPO_OP) != null && !request.getParameter(PRACTIGIRO.TIPO_OP).isEmpty() ? request.getParameter(PRACTIGIRO.TIPO_OP) : null;
                String presento = request.getParameter(PRACTIGIRO.PRESENTO) != null && !request.getParameter(PRACTIGIRO.PRESENTO).isEmpty() ? request.getParameter(PRACTIGIRO.PRESENTO) : null;
                String idTrx = request.getParameter(PRACTIGIRO.ID_TRX) != null && !request.getParameter(PRACTIGIRO.ID_TRX).isEmpty() ? request.getParameter(PRACTIGIRO.ID_TRX) : null;

                Respuesta respuesta = rcService.setBloqueoClienteDDJJ(user, password, tipoDocumento.toString(), idDocumento, idTrx, idTipoOperacion, presento);
                if (respuesta.getCodigoRetorno() == 0) {
                    jsonRespuesta.put("success", true);
                    jsonRespuesta.put("MENSAJE", "La operacion fue exitosa");
                } else {
                    jsonRespuesta.put("success", false);
                    jsonRespuesta.put("MENSAJE", "No se realizo la operacion");
                }
            }
            out.println(jsonRespuesta.toString());
        } catch (Exception ex) {
            Logger.getLogger(PRACTIGIRO.class.getName()).log(Level.SEVERE, null, ex);
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
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
