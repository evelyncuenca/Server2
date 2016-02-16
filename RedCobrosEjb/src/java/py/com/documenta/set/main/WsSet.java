/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.documenta.set.main;

import java.net.URL;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import py.com.documenta.set.pojo.ddjj.consultar.ConsultarDeclaracionJuradaResponse;
import py.com.documenta.set.pojo.ddjj.guardar.GuardarDeclaracionJuradaResponse;
import py.com.documenta.set.pojo.deuda.consultar.ConsultarDeudaResponse;
import py.com.documenta.set.pojo.pago.guardar.GuardarPagoResponse;
import static py.com.konecta.redcobros.utiles.UtilesSet.toObject;
import py.gov.set.rucas.beans.jaws.Parameter;
import services.set.gov.py.rucas.AppInfo;
import services.set.gov.py.rucas.CryptMethod;
import services.set.gov.py.rucas.LoginMethod;
import services.set.gov.py.rucas.RUCASConnectResponse;
import services.set.gov.py.rucas.RUCASLoginResponse;
import services.set.gov.py.rucas.SessionManagement;
import services.set.gov.py.rucas.SessionManagementService;
import services.set.gov.py.rucas.era.EraManagement;
import services.set.gov.py.rucas.era.EraManagementService;
import services.set.gov.py.rucas.pagos.PagoElectronicoManagement;
import services.set.gov.py.rucas.pagos.PagoElectronicoManagementService;
import sun.misc.BASE64Encoder;
import javax.xml.ws.handler.Handler;
import py.com.documenta.set.pojo.ot.guardar.GuardarOTResponse;
import py.com.documenta.set.pojo.pago.consultar.ConsultarOTResponse;
import py.com.konecta.redcobros.ws.handler.MessageHandler;

/**
 *
 * @author Fernando Invernizzi <fernando.invernizzi@konecta.com.py>
 */
public class WsSet {

    private final Logger logger = Logger.getLogger(WsSet.class.getName());
    private String sessionId;
    String urlSession;
    String urlEra;
    String urlPago;
    String user;
    String pwd;
    Integer connTO;
    Integer readTO;

    public WsSet(String urlSession, String urlEra, String urlPago, String user, String pwd, Integer connTO, Integer readTO) {
        logger.info(urlSession + ";" + urlEra + ";" + urlPago + ";" + user + ";" + pwd + ";" + connTO + ";" + readTO);
        this.urlSession = urlSession;
        this.urlEra = urlEra;
        this.urlPago = urlPago;
        this.user = user;
        this.pwd = pwd;
        this.connTO = connTO;
        this.readTO = readTO;
        this.sessionId = this.iniciarSesion();

    }

    public GuardarPagoResponse guardarPago(String xmlPago, String nroHash) {
        logger.info(xmlPago);
        GuardarPagoResponse response = null;
        try {
            PagoElectronicoManagement port = getWSPagoElectronicoManagement(this.urlPago, connTO, readTO);
            String result = port.guardarPago(sessionId, xmlPago, nroHash);
            logger.log(Level.INFO, "RESPUESTA PAGO = {0}", result);
            response = toObject(result, GuardarPagoResponse.class);
            logger.info(response.toString());
        } catch (Exception ex) {
            logger.logp(Level.SEVERE, this.getClass().getName(), "guardarPago", ex.getMessage(), ex);
        }
        return response;
    }

    public GuardarDeclaracionJuradaResponse guardarDDJJ(String xmlDDJJ, String nroHash) {
        GuardarDeclaracionJuradaResponse response = null;
        try {
            EraManagement port = getWSEraManagement(this.urlEra, connTO, readTO);
            java.lang.String result = port.guardarDDJJ(sessionId, xmlDDJJ, nroHash);
            logger.log(Level.INFO, "RESPUESTA GUARDAR DDJJ = {0}", result);
            response = toObject(result, GuardarDeclaracionJuradaResponse.class);
            logger.info(response.toString());
        } catch (Exception ex) {
            logger.logp(Level.SEVERE, this.getClass().getName(), "guardarDDJJ", ex.getMessage(), ex);
        }
        return response;
    }

    public ConsultarDeclaracionJuradaResponse consultarDDJJ(String xmlDDJJ, String nroHash) {
        ConsultarDeclaracionJuradaResponse response = null;
        try {
            EraManagement port = getWSEraManagement(this.urlEra, connTO, readTO);
            java.lang.String result = port.consultarDDJJ(sessionId, xmlDDJJ, nroHash);
            logger.log(Level.INFO, "RESPUESTA CONSULTAR DDJJ = {0}", result);
            response = toObject(result, ConsultarDeclaracionJuradaResponse.class);
            logger.info(response.toString());
        } catch (Exception ex) {
            logger.logp(Level.SEVERE, this.getClass().getName(), "consultarDDJJ", ex.getMessage(), ex);
        }
        return response;
    }

    public GuardarPagoResponse consultarPago(String xmlPago, String nroHash) {
        GuardarPagoResponse response = null;
        try {
            PagoElectronicoManagement port = getWSPagoElectronicoManagement(this.urlPago, connTO, readTO);
            java.lang.String result = port.consultarPago(sessionId, xmlPago, nroHash);
            logger.log(Level.INFO, "RESPUESTA CONSULTAR PAGO= {0}", result);
            response = toObject(result, GuardarPagoResponse.class);
            logger.info(response.toString());
        } catch (Exception ex) {
            logger.logp(Level.SEVERE, this.getClass().getName(), "consultarPago", ex.getMessage(), ex);
        }
        return response;
    }

    public ConsultarDeudaResponse consultarDeuda(String ruc, String fechaConstitucion, String nroHash) {
        /*La verdad es que no se si necesitamos este metodo pero por si acaso */
        ConsultarDeudaResponse response = null;
        try {
            PagoElectronicoManagement port = getWSPagoElectronicoManagement(this.urlPago, connTO, readTO);
            java.lang.String result = port.consultarDeuda(sessionId, ruc, fechaConstitucion, "");//el ultimo campo es vacio para traer todas las boletas
            logger.log(Level.INFO, "RESPUESTA CONSULTAR DEUDA = {0}", result);
            response = toObject(result, ConsultarDeudaResponse.class);
            logger.info(response.toString());
        } catch (Exception ex) {
            logger.logp(Level.SEVERE, this.getClass().getName(), "consultarDDJJ", ex.getMessage(), ex);
        }
        return response;
    }

    public GuardarOTResponse guardarOT(String xmlOT, String nroHash) {
        GuardarOTResponse response = null;
        try {
            PagoElectronicoManagement port = getWSPagoElectronicoManagement(this.urlPago, connTO, readTO);
            java.lang.String result = port.guardarOT(sessionId, xmlOT, nroHash);
            logger.log(Level.INFO, "Result = {0}", result);
            response = toObject(result, GuardarOTResponse.class);
            logger.info(response.toString());
        } catch (Exception ex) {
            logger.logp(Level.SEVERE, this.getClass().getName(), "guardarOT", ex.getMessage(), ex);
        }
        return response;
    }

    public ConsultarOTResponse consultarOT(String xmlOT, String nroHash) {
        ConsultarOTResponse response = null;
        try {
            PagoElectronicoManagement port = getWSPagoElectronicoManagement(this.urlPago, connTO, readTO);
            java.lang.String result = port.consultarOT(sessionId, xmlOT, nroHash);
            logger.log(Level.INFO, "Result = {0}", result);
            response = toObject(result, ConsultarOTResponse.class);
            logger.info(response.toString());
        } catch (Exception ex) {
            logger.logp(Level.SEVERE, this.getClass().getName(), "guardarOT", ex.getMessage(), ex);
        }
        return response;
    }

    public final String iniciarSesion() {
        AppInfo appInfo = new AppInfo();
        appInfo.setLanguage("ES");
        appInfo.setName("appName");
        appInfo.setUuid("uuid");
        appInfo.setVersion("app version");
        LoginMethod lm = new LoginMethod();
        lm.setCode("RSLM001");
        lm.setName("RUCAS_STANDARD_LOGIN_METHOD");
        lm.setVersion("001");
        lm.setTemporalSessionId("");
        CryptMethod cm = new CryptMethod();
        cm.setCode("RSCM001");
        cm.setName("RUCAS_STANDARD_CRYPT_METHOD");
        cm.setVersion("001");
        try {
            SessionManagement port = getWSSessionManagement(this.urlSession, connTO, readTO);
            RUCASConnectResponse resp = port.rucasConnect(appInfo, lm, cm);
            String temporalSessionId = resp.getTemporarySessionId();

            RUCASLoginResponse rl;
            Parameter[] params = new Parameter[3];

            params[0] = new Parameter();
            params[0].setName("username");
            params[0].setValue(this.user);

            params[1] = new Parameter();
            params[1].setName("token");
            BASE64Encoder encoder = new BASE64Encoder();
            String token = temporalSessionId + encoder.encode(SHAUtil.getDigest(this.pwd));
            params[1].setValue(encoder.encode(SHAUtil.getDigest(token)));
//        Base64 encoder = new Base64();
//        String token = temporalSessionId + encoder.encode(SHAUtil.getDigest("123456"));
//        params[1].setValue(new String(encoder.encode(SHAUtil.getDigest(token))));

            params[2] = new Parameter();
            params[2].setName("temporalSessionId");
            params[2].setValue(temporalSessionId);

            lm.setParams(params);
            rl = port.rucasLogin(lm, cm);
            String permanentSession = rl.getPermanentSessionId();
            logger.log(Level.INFO, "Sesion Permanente: {0}", rl);
            logger.log(Level.INFO, "Sesion Permanente: {0}", permanentSession);

            return permanentSession;
        } catch (Exception ex) {
            Logger.getLogger(WsSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    static final ReentrantLock lockSession = new ReentrantLock();
    static SessionManagementService sms = null;

    static final ReentrantLock lockPagoElectronico = new ReentrantLock();
    static PagoElectronicoManagementService pems = null;

    static final ReentrantLock lockEra = new ReentrantLock();
    static EraManagementService ems = null;

    private static SessionManagement getWSSessionManagement(String url, int connectTO, int readTO) {
        SessionManagement sm = null;
        try {
            if (sms == null) {
                try {
                    lockSession.lock();
                    if (sms == null) {
                        URL wsdlURL = WsSet.class.getClassLoader().getResource("schema/SessionManagementEJB.wsdl");
                        sms = new SessionManagementService(wsdlURL, new QName("http://py.gov.set.services/rucas/", "SessionManagementService"));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(WsSet.class.getName()).log(Level.SEVERE, "", ex);
                    return null;
                } finally {
                    lockSession.unlock();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(WsSet.class.getName()).log(Level.SEVERE, "", ex);
            return sm;
        }
        sm = sms.getSessionManagementPort();
        BindingProvider provider = (BindingProvider) sm;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        provider.getRequestContext().put("com.sun.xml.ws.connect.timeout", connectTO * 1000);
        provider.getRequestContext().put("com.sun.xml.ws.request.timeout", readTO * 1000);
        Binding binding = provider.getBinding();
        List<Handler> handlerList = binding.getHandlerChain();
        handlerList.add(new MessageHandler());
        binding.setHandlerChain(handlerList);
        return sm;
    }

    private static PagoElectronicoManagement getWSPagoElectronicoManagement(String url, int connectTO, int readTO) {
        PagoElectronicoManagement pem = null;
        try {
            if (pems == null) {
                try {
                    lockPagoElectronico.lock();
                    if (pems == null) {
                        URL wsdlURL = WsSet.class.getClassLoader().getResource("schema/PagoElectronicoManagementEJB.wsdl");
                        pems = new PagoElectronicoManagementService(wsdlURL, new QName("http://py.gov.set.services/rucas/pagos", "PagoElectronicoManagementService"));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(WsSet.class.getName()).log(Level.SEVERE, "", ex);
                    return null;
                } finally {
                    lockPagoElectronico.unlock();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(WsSet.class.getName()).log(Level.SEVERE, "", ex);
            return pem;
        }
        pem = pems.getPagoElectronicoManagementPort();
        BindingProvider provider = (BindingProvider) pem;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        provider.getRequestContext().put("com.sun.xml.ws.connect.timeout", connectTO * 1000);
        provider.getRequestContext().put("com.sun.xml.ws.request.timeout", readTO * 1000);
        Binding binding = provider.getBinding();
        List<Handler> handlerList = binding.getHandlerChain();
        handlerList.add(new MessageHandler());
        binding.setHandlerChain(handlerList);
        return pem;
    }

    private static EraManagement getWSEraManagement(String url, int connectTO, int readTO) {
        EraManagement em = null;
        try {
            if (ems == null) {
                try {
                    lockEra.lock();
                    if (ems == null) {
                        URL wsdlURL = WsSet.class.getClassLoader().getResource("schema/EraManagementEJB.wsdl");
                        ems = new EraManagementService(wsdlURL, new QName("http://py.gov.set.services/rucas/era", "EraManagementService"));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(WsSet.class.getName()).log(Level.SEVERE, "", ex);
                    return null;
                } finally {
                    lockEra.unlock();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(WsSet.class.getName()).log(Level.SEVERE, "", ex);
            return em;
        }
        em = ems.getEraManagementPort();
        BindingProvider provider = (BindingProvider) em;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        provider.getRequestContext().put("com.sun.xml.ws.connect.timeout", connectTO * 1000);
        provider.getRequestContext().put("com.sun.xml.ws.request.timeout", readTO * 1000);
        Binding binding = provider.getBinding();
        List<Handler> handlerList = binding.getHandlerChain();
        handlerList.add(new MessageHandler());
        binding.setHandlerChain(handlerList);
        return em;
    }

}
