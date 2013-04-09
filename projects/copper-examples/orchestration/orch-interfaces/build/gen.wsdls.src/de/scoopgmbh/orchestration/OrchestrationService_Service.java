package de.scoopgmbh.orchestration;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2013-04-08T12:41:21.278+02:00
 * Generated source version: 2.6.0
 * 
 */
@WebServiceClient(name = "OrchestrationService", 
                  wsdlLocation = "classpath:wsdl/OrchestrationEngine.wsdl",
                  targetNamespace = "http://orchestration.scoopgmbh.de/") 
public class OrchestrationService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://orchestration.scoopgmbh.de/", "OrchestrationService");
    public final static QName OrchestrationServicePort = new QName("http://orchestration.scoopgmbh.de/", "OrchestrationServicePort");
    static {
        URL url = OrchestrationService_Service.class.getClassLoader().getResource("wsdl/OrchestrationEngine.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(OrchestrationService_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/OrchestrationEngine.wsdl");
        }       
        WSDL_LOCATION = url;   
    }

    public OrchestrationService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OrchestrationService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OrchestrationService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public OrchestrationService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public OrchestrationService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public OrchestrationService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns OrchestrationService
     */
    @WebEndpoint(name = "OrchestrationServicePort")
    public OrchestrationService getOrchestrationServicePort() {
        return super.getPort(OrchestrationServicePort, OrchestrationService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OrchestrationService
     */
    @WebEndpoint(name = "OrchestrationServicePort")
    public OrchestrationService getOrchestrationServicePort(WebServiceFeature... features) {
        return super.getPort(OrchestrationServicePort, OrchestrationService.class, features);
    }

}