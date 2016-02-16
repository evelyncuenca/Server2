/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import py.com.konecta.redcobros.ejb.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.apache.commons.fileupload.FileItem;
import py.com.documenta.ws.set.contrib.ContribSETWS;
import py.com.documenta.ws.set.contrib.ContribSETWS_Service;
import py.com.documenta.ws.set.contrib.Contribuyente;
import py.com.konecta.redcobros.entities.Contribuyentes;
import py.com.konecta.redcobros.entities.ParametroSistema;
import py.com.konecta.redcobros.utiles.LectorConfiguracionSet;

/**
 *
 * @author konecta
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContribuyentesFacadeImpl extends GenericDaoImpl<Contribuyentes, Long> implements ContribuyentesFacade {

    @Resource
    private SessionContext context;
    @EJB
    private ParametroSistemaFacade parametroSistemaFacade;
    @EJB
    private ContribuyentesFacade contribuyentesFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void actualizarContribuyentes(FileItem bf, String separador) throws Exception {
        try {
            InputStream uploadedStream = bf.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
            String[] lineas = LectorConfiguracionSet.lineasDelArchivo(new BufferedReader(reader));
            uploadedStream.close();
            String rucNuevo, dv, rucViejo, tipoContribuyente,
                    modalidadContribuyente, mesCierre, nombreContribuyente;
            Contribuyentes c = new Contribuyentes();
            String campos[], opcion;
            int indice, contador = 0;
            for (String linea : lineas) {
                contador++;
                campos = linea.split(separador);
                if (campos.length == 7) {
                    indice = 0;
                    opcion = "NUEVO";
                } else {
                    indice = 1;
                    opcion = campos[campos.length - 1];
                }
                rucNuevo = campos[indice++];
                //rucNuevo=linea.substring(0,TAM_RUC_NUEVO);
                //linea=linea.substring(TAM_RUC_NUEVO);
                dv = campos[indice++];
                //dv=linea.substring(0,TAM_DV);
                //linea=linea.substring(TAM_DV);
                rucViejo = campos[indice++];
                //rucViejo=linea.substring(0,TAM_RUC_ANTERIOR);
                //linea=linea.substring(TAM_RUC_ANTERIOR);
                tipoContribuyente = campos[indice++];
                //tipoContribuyente=linea.substring(0,TAM_TIPO_CONTRIBUYENTE);
                //linea=linea.substring(TAM_TIPO_CONTRIBUYENTE);
                modalidadContribuyente = campos[indice++];
                //modalidadContribuyente=linea.substring(0,TAM_MODALIDAD_CONTRIBUYENTE);
                //linea=linea.substring(TAM_MODALIDAD_CONTRIBUYENTE);
                mesCierre = campos[indice++];
                //mesCierre=linea.substring(0,TAM_MES_CIERRE);
                //linea=linea.substring(TAM_MES_CIERRE);
                nombreContribuyente = campos[indice++];
                //nombreContribuyente=linea;
                c = new Contribuyentes();
                c.setRucNuevo(rucNuevo);
                if (this.total(c) == 0) {
                    //if (opcion.equalsIgnoreCase("NUEVO")) {
                    c.setDigitoVerificador(dv);
                    c.setRucAnterior(rucViejo);
                    c.setMesCierre(Integer.parseInt(mesCierre));
                    c.setModalidadContribuyente(Integer.parseInt(modalidadContribuyente));
                    c.setNombreContribuyente(nombreContribuyente);
                    c.setTipoContribuyente(Integer.parseInt(tipoContribuyente));
                    this.save(c);
                } else {
                    c = this.get(c);
                    c.setDigitoVerificador(dv);
                    c.setRucAnterior(rucViejo);
                    c.setMesCierre(Integer.parseInt(mesCierre));
                    c.setModalidadContribuyente(Integer.parseInt(modalidadContribuyente));
                    c.setNombreContribuyente(nombreContribuyente);
                    c.setTipoContribuyente(Integer.parseInt(tipoContribuyente));
                    this.update(c);
                }
            }
        } catch (IOException e) {
            //abortar transaccion
            context.setRollbackOnly();
            throw e;
        } catch (NumberFormatException e) {
            //abortar transaccion
            context.setRollbackOnly();
            throw e;
        }
    }
    private static ContribSETWS_Service service = null;
    private static final ReentrantLock lock = new ReentrantLock();

    public static ContribSETWS getWSManager(String url, String uri, String localPart,
            int connTo, int readTo) {
        ContribSETWS pexSoap;

        URL wsdlURL = ContribSETWS_Service.class.getClassLoader().getResource("schema/ContribSETWS.wsdl");
        try {
            if (service == null) {
                try {
                    lock.lock();
                    if (service == null) {
                        service = new ContribSETWS_Service(wsdlURL, new QName(uri, localPart));
                    }
                } catch (Exception ex) {
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception ex) {
        }

        pexSoap = service.getContribSETWSPort();
        BindingProvider provider = (BindingProvider) pexSoap;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        provider.getRequestContext().put("com.sun.xml.ws.connect.timeout", connTo * 1000);
        provider.getRequestContext().put("com.sun.xml.ws.request.timeout", readTo * 1000);

        return pexSoap;
    }

    @Override
    public Contribuyentes getContribuyentePorRuc(String rucNuevo, String rucAnterior, String digitoVerificador) {
//        Contribuyentes contribuyente = null;
//        HibernateEntityManager hem = (HibernateEntityManager) this.getEm().getDelegate();
//        Criteria c1 = hem.getSession().createCriteria(Contribuyentes.class);
//        Disjunction disjunction = Restrictions.disjunction();
//        Criterion critRucNuevo = Restrictions.eq("rucNuevo", rucNuevo);
//        Criterion critRucViejo = Restrictions.eq("rucAnterior", rucAnterior);
//        disjunction.add(critRucNuevo);
//        disjunction.add(critRucViejo);
//
//        c1.add(disjunction);
//
//        List<Contribuyentes> lContribuyente = (List<Contribuyentes>) c1.list();
//
//        if (lContribuyente.size() == 1) {
//            contribuyente = lContribuyente.get(0);
//        } else {

        Contribuyentes localContrib;
        try {
            localContrib = new Contribuyentes();
            localContrib.setRucNuevo(rucNuevo);
            List<Contribuyentes> lcontribuyentes = contribuyentesFacade.list(localContrib, "rucNuevo", "asc", false);
            localContrib = (lcontribuyentes != null && !lcontribuyentes.isEmpty()) ? lcontribuyentes.get(0) : null;
            
            ParametroSistema paramFile = new ParametroSistema();
            paramFile.setNombreParametro("urlContribSet");
            String url = parametroSistemaFacade.get(paramFile).getValor();
            ContribSETWS serviceWS = getWSManager(url, "http://contrib.ws.daemon.documenta.com.py/", "ContribSETWS", 10, 80);

            Contribuyente contrib = serviceWS.consulta(rucNuevo);
            if (contrib != null) {
                if (localContrib == null){
                    localContrib = new Contribuyentes();
                    localContrib.setIdContribuyente(Long.valueOf(contrib.getId()));
                }
//                contribuyente = new Contribuyentes();
                
                localContrib.setNombreContribuyente(contrib.getRazonSocial());
                localContrib.setDigitoVerificador(contrib.getDigitoVerificador());
                localContrib.setRucNuevo(contrib.getRucNuevo());
                localContrib.setRucAnterior(contrib.getRucViejo());
                localContrib.setMesCierre(Integer.valueOf(contrib.getMesCierre()));
                localContrib.setModalidadContribuyente(Integer.valueOf(contrib.getModalidad()));
                localContrib.setTipoContribuyente(Integer.valueOf(contrib.getTipo()));
                contribuyentesFacade.merge(localContrib);
            } else {
                Logger.getLogger(ContribuyentesFacadeImpl.class.getName()).log(Level.SEVERE, String.format("No existe contribuyente %s en Documenta", rucNuevo));
            }
        } catch (Exception ex) {
            Logger.getLogger(ContribuyentesFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
            localContrib = null;
        }
//        }
        return localContrib;
    }
    private static final int TAM_RUC_NUEVO = 8;
    private static final int TAM_DV = 1;
    private static final int TAM_RUC_ANTERIOR = 11;
    private static final int TAM_TIPO_CONTRIBUYENTE = 1;
    private static final int TAM_MODALIDAD_CONTRIBUYENTE = 1;
    private static final int TAM_MES_CIERRE = 2;
}
