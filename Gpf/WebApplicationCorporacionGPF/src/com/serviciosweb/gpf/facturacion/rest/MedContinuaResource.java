/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.rest;


import com.serviciosweb.gpf.facturacion.servicios.MedContinua;
import com.serviciosweb.gpf.facturacion.servicios.bean.ResultadoTransaccionesBean;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * REST Web Service
 *
 * @author mftellor
 */

@Path("MedContinua")
public class MedContinuaResource {
    @Context
    private UriInfo context;

    /** Creates a new instance of MedContinuaResource */
    public MedContinuaResource() {
    }

    /**
     * Retrieves representation of an instance of com.serviciosweb.gpf.facturacion.rest.MedContinuaResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of MedContinuaResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

   @GET
   @Path("/transaccionVC/{fechaInicial}/{empresa}/{usuario}/{clave}")
   @Produces("text/xml")
   public String transaccionVC(@PathParam("fechaInicial") String fechaInicial,
           @PathParam("empresa") String empresa,@PathParam("usuario") String usuario,
           @PathParam("clave") String clave){
       fechaInicial=fechaInicial.replace("-", "/");
       XStream xstream = new XStream(new StaxDriver());
       xstream.alias("ResultadoTransaccionesBean", ResultadoTransaccionesBean.class);
       MedContinua medContinua=new MedContinua();
       ArrayList<ResultadoTransaccionesBean> resultado=medContinua.TransaccionesVC(fechaInicial, empresa, usuario, clave);
       String resultadoXml=xstream.toXML(resultado);
       //resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" ?>","");
       //resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
       //resultadoXml=resultadoXml.replace("<?xml version='1.0' encoding='UTF-8'?>","");
       resultadoXml=resultadoXml.replace("<list>","");
       resultadoXml=resultadoXml.replace("</list>","");
       resultadoXml=resultadoXml.replace("com.​serviciosweb.​gpf.​facturacion.​servicios.​bean.ResultadoTransaccionesBean","FacturasVentasProd");
       return resultadoXml;//xstream.toXML(listadoResultado);
   }
}
