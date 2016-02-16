
package py.com.documenta.onlinemanager.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for olTipoSeleccion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="olTipoSeleccion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RADIO_BUTTON"/>
 *     &lt;enumeration value="CHECK_BOX"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "olTipoSeleccion")
@XmlEnum
public enum OlTipoSeleccion {

    RADIO_BUTTON,
    CHECK_BOX;

    public String value() {
        return name();
    }

    public static OlTipoSeleccion fromValue(String v) {
        return valueOf(v);
    }

}
