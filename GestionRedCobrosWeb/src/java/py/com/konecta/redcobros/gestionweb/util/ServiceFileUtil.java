/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.gestionweb.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.com.konecta.redcobros.gestionweb.types.AliasServicioFacturador;

/**
 *
 * @author igaona
 */
public class ServiceFileUtil {

    public static List<AliasServicioFacturador> getAliasServicio(Connection conn, Integer idServicio) {
        Statement stm = null;
        ResultSet rs = null;

        List<AliasServicioFacturador> lsAliasServicioFacturadors = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT id_facturador, id_servicio, alias_servicio FROM alias_servicio_facturador WHERE id_servicio = " + idServicio.toString());
            lsAliasServicioFacturadors = new ArrayList<AliasServicioFacturador>();
            while (rs.next()) {
                AliasServicioFacturador asf = new AliasServicioFacturador(rs.getLong(1), rs.getInt(2), rs.getInt(3));
                lsAliasServicioFacturadors.add(asf);
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceFileUtil.class.getName()).log(Level.SEVERE, "getAliasServicio", ex);
        } finally {
            if (stm != null) {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ServiceFileUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return lsAliasServicioFacturadors;
    }

    public static boolean insertDatoConsulta(Connection conn, AliasServicioFacturador asf,
            String referenciaBusqueda, String referenciaPago, String descripcion,
            Integer moneda, BigDecimal monto, BigDecimal montoVencido,
            Double interesMoratorio, String vencimiento, Character cobrarVencido,
            Integer tipoRecargo, Character pagarMasVencido, Character soloEfectivo,
            Character anulable, String mensaje) {
        boolean inserted = false;

        Statement stm = null;
        try {
            
            //String fechaVenc = null;
            if (vencimiento != null){
                vencimiento = String.format("to_date('%s', 'yyyyMMdd')", vencimiento);
            }
            String sql = "INSERT INTO dato_consulta ( "
                    + "id_facturador, "
                    + "id_servicio, "
                    + "alias, "
                    + "referencia_buqueda, "
                    + "referencia_pago, "
                    + "descripcion, "
                    + "moneda, "
                    + "monto,     "
                    + "monto_vencido, "
                    + "interes_moratorio, "
                    + "vencimiento, "
                    + "cobrar_vencido, "
                    + "tipo_recargo, "
                    + "pagar_mas_vencido, "
                    + "solo_efectivo, "
                    + "anulable,      "
                    + "pendiente, "
                    + "mensaje) "
                    + "VALUES ( "
                    + asf.getIdFacturador().toString() + ","
                    + asf.getIdServicio().toString() + ","
                    + asf.getIdAliasServicio().toString() + ","
                    + nvlNull(quote(referenciaBusqueda)) + ","
                    + nvlNull(quote(referenciaPago)) + ","
                    + nvlNull(quote(descripcion)) + ","
                    + nvlNull(moneda) + ","
                    + nvlNull(monto) + ","
                    + nvlNull(montoVencido) + ","
                    + nvlNull(interesMoratorio) + ","
                    + nvlNull(vencimiento) + ","
                    + nvlNull(quote(cobrarVencido.toString())) + ","
                    + nvlNull(tipoRecargo) + ","
                    + nvlNull(quote(pagarMasVencido.toString())) + ","
                    + nvlNull(quote(soloEfectivo.toString())) + ","
                    + nvlNull(quote(anulable.toString())) + ","
                    + "'S'" + ","
                    + nvlNull(quote(mensaje)) + ")";
                       
            Logger.getLogger(ServiceFileUtil.class.getName()).log(Level.SEVERE, "inserting: {0}", sql);
            stm = conn.createStatement();
            stm.executeUpdate(sql);
            
            inserted = true;
        } catch (Exception ex) {
            Logger.getLogger(ServiceFileUtil.class.getName()).log(Level.SEVERE, "insertDatoConsulta", ex);
        } finally {
            if (stm != null) try {
                stm.close();
                stm = null;
            } catch (SQLException ex) {
                Logger.getLogger(ServiceFileUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return inserted;
    }

    public static boolean deleteDatoConsulta(Connection conn, Integer idServicio){
     boolean deleted = false;

        Statement stm = null;
        try {

            String sql = "DELETE FROM dato_consulta WHERE id_servicio = " + idServicio.toString();

            stm = conn.createStatement();
            stm.executeUpdate(sql);
            deleted = true;                  

        } catch (Exception ex) {
            Logger.getLogger(ServiceFileUtil.class.getName()).log(Level.SEVERE, "deleteDatoConsulta", ex);
        }
        return deleted;        
    }
    
    public static String quote(String text) {
        return text == null ? null : "'" + text + "'";
    }

    public static <T> String nvlNull(T text) {
        return text == null ? "null" : (text instanceof String ? (String) text : text.toString());
    }
}
