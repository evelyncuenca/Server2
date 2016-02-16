
package py.com.documenta.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for propiedadServicio.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="propiedadServicio">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CONSULTABLE"/>
 *     &lt;enumeration value="RECARGABLE"/>
 *     &lt;enumeration value="MANUAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "propiedadServicio")
@XmlEnum
public enum PropiedadServicio {

    CONSULTABLE,
    RECARGABLE,
    MANUAL;

    public String value() {
        return name();
    }

    public static PropiedadServicio fromValue(String v) {
        return valueOf(v);
    }

}
