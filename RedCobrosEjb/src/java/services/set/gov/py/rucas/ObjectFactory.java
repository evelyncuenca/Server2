
package services.set.gov.py.rucas;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the services.set.gov.py.rucas package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: services.set.gov.py.rucas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LoginMethod }
     * 
     */
    public LoginMethod createLoginMethod() {
        return new LoginMethod();
    }

    /**
     * Create an instance of {@link ClientSystem }
     * 
     */
    public ClientSystem createClientSystem() {
        return new ClientSystem();
    }

    /**
     * Create an instance of {@link RUCASConnectResponse }
     * 
     */
    public RUCASConnectResponse createRUCASConnectResponse() {
        return new RUCASConnectResponse();
    }

    /**
     * Create an instance of {@link AppInfo }
     * 
     */
    public AppInfo createAppInfo() {
        return new AppInfo();
    }

    /**
     * Create an instance of {@link RUCASLoginResponse }
     * 
     */
    public RUCASLoginResponse createRUCASLoginResponse() {
        return new RUCASLoginResponse();
    }

    /**
     * Create an instance of {@link CryptMethod }
     * 
     */
    public CryptMethod createCryptMethod() {
        return new CryptMethod();
    }

}
