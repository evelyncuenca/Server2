/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.StatelessSession;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.ejb.TicketTransaccionFacade;

/**
 *
 * @author brojas
 */
@Stateless
public class TicketTransaccionFacadeImpl implements TicketTransaccionFacade {

    @PersistenceContext
    private EntityManager em;
    
    private static String REPORT_DIR = "/py/com/konecta/redcobros/reportes/cobranza/";

    @Override
    public byte[] generarTicketTransaccion(Long idTransaccion) {
        byte[] reporte = null;
        HashMap parameters = new HashMap();
        StatelessSession statelessSession = ((HibernateEntityManager) em.getDelegate()).getSession().getSessionFactory().openStatelessSession();
        Connection con = statelessSession.connection();
        parameters.put("idTransaccion", idTransaccion);
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(REPORT_DIR + "ticketTransaccion.jasper");
            JasperPrint jp = JasperFillManager.fillReport(is, parameters, con);
            if (jp != null && jp.getPages().size() > 0) {
                reporte = JasperExportManager.exportReportToPdf(jp);
            }

            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(TicketTransaccionFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
             Logger.getLogger(TicketTransaccionFacadeImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                con.close();
                statelessSession.close();
            } catch (Exception e2) {
            }
        }
        return reporte;
    }
}
