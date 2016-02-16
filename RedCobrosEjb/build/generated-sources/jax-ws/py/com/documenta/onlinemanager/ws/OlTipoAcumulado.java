
package py.com.documenta.onlinemanager.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for olTipoAcumulado.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="olTipoAcumulado">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PAGO_ANTERIORES"/>
 *     &lt;enumeration value="PAGO_TOTAL"/>
 *     &lt;enumeration value="PAGO_INDEPENDIENTE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "olTipoAcumulado")
@XmlEnum
public enum OlTipoAcumulado {

    PAGO_ANTERIORES,
    PAGO_TOTAL,
    PAGO_INDEPENDIENTE;

    public String value() {
        return name();
    }

    public static OlTipoAcumulado fromValue(String v) {
        return valueOf(v);
    }

}
