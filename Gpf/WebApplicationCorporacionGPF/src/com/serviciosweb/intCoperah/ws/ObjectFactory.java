
package com.serviciosweb.intCoperah.ws;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws_transaccional package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws_transaccional
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Procesar }
     * 
     */
    public Procesar createProcesar() {
        return new Procesar();
    }

    /**
     * Create an instance of {@link InfoCarga }
     * 
     */
    public InfoCarga createInfoCarga() {
        return new InfoCarga();
    }

    /**
     * Create an instance of {@link ProcesarResponse }
     * 
     */
    public ProcesarResponse createProcesarResponse() {
        return new ProcesarResponse();
    }

    /**
     * Create an instance of {@link InfoRespuesta }
     * 
     */
    public InfoRespuesta createInfoRespuesta() {
        return new InfoRespuesta();
    }

    /**
     * Create an instance of {@link InfoTransaccional }
     * 
     */
    public InfoTransaccional createInfoTransaccional() {
        return new InfoTransaccional();
    }

    /**
     * Create an instance of {@link ArrayOfInfoTransaccional }
     * 
     */
    public ArrayOfInfoTransaccional createArrayOfInfoTransaccional() {
        return new ArrayOfInfoTransaccional();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link ArrayOfDouble }
     * 
     */
    public ArrayOfDouble createArrayOfDouble() {
        return new ArrayOfDouble();
    }

}
