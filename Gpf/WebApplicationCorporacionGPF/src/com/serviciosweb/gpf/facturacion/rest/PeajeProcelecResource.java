package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.serviciosweb.gpf.facturacion.servicios.PeajeProcelecIntegracion;


@Path("peajeProcelect")
public class PeajeProcelecResource {

	   @GET
	   @Path("/busquedaCliente/{cedula}")
	   @Produces("text/xml")
	   public String busquedaCliente(@PathParam("cedula")  String cedula){
		   PeajeProcelecIntegracion servicio = new PeajeProcelecIntegracion(cedula);
			return servicio.peajeProcelecResponse;
	   }
	   
	   @GET
	   @Path("/confirmacionRecarga/{identity}/{factura}")
	   @Produces("text/xml")
	   public String confirmacionRecarga(@PathParam("identity")  String identity,
			   @PathParam("factura")  String factura){
	   
		   PeajeProcelecIntegracion servicio = new PeajeProcelecIntegracion(identity,factura,null,"confirmacionRecarga");
    		return servicio.peajeProcelecResponse;
	   }
	   
	   @GET
	   @Path("/eliminacionRecarga/{identity}/{cedula}/{valor}")
	   @Produces("text/xml")
	   public String eliminacionRecarga(@PathParam("identity")  String identity,@PathParam("cedula")  String cedula,
			   @PathParam("valor")  String valor){
	   
		    PeajeProcelecIntegracion servicio = new PeajeProcelecIntegracion(identity,cedula,valor,"EliminarRecarga");
    		return servicio.peajeProcelecResponse;
	   }
	   
	   
	   @GET
	   @Path("/grabaRecarga/{cedula}/{valor}")
	   @Produces("text/xml")
	   public String grabaRecarga(@PathParam("cedula")  String cedula,
			   @PathParam("valor")  String valor){
	   
		    PeajeProcelecIntegracion servicio = new PeajeProcelecIntegracion(cedula,valor,null,"grabaRecarga");
    		return servicio.peajeProcelecResponse;
	   }
	   
	   

}
