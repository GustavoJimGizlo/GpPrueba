
package com.corporaciongpf.integration.soap.empleado.services.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.corporaciongpf.integration.empleado.services.v1 package. 
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

    private final static QName _EmpleadoResponse_QNAME = new QName("http://www.corporaciongpf.com/integration/empleado/services/v1", "EmpleadoResponse");
    private final static QName _EmpleadoRequest_QNAME = new QName("http://www.corporaciongpf.com/integration/empleado/services/v1", "EmpleadoRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.corporaciongpf.integration.empleado.services.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EmpleadoResponse }
     * 
     */
    public EmpleadoResponse createEmpleadoResponse() {
        return new EmpleadoResponse();
    }

    /**
     * Create an instance of {@link EmpleadoRequest }
     * 
     */
    public EmpleadoRequest createEmpleadoRequest() {
        return new EmpleadoRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmpleadoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.corporaciongpf.com/integration/empleado/services/v1", name = "EmpleadoResponse")
    public JAXBElement<EmpleadoResponse> createEmpleadoResponse(EmpleadoResponse value) {
        return new JAXBElement<EmpleadoResponse>(_EmpleadoResponse_QNAME, EmpleadoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmpleadoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.corporaciongpf.com/integration/empleado/services/v1", name = "EmpleadoRequest")
    public JAXBElement<EmpleadoRequest> createEmpleadoRequest(EmpleadoRequest value) {
        return new JAXBElement<EmpleadoRequest>(_EmpleadoRequest_QNAME, EmpleadoRequest.class, null, value);
    }

}
