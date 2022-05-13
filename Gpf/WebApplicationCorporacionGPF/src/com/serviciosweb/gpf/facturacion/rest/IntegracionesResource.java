package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.serviciosweb.gpf.facturacion.servicios.Integraciones;


@Path("IntegracionesGPF")
public class IntegracionesResource {
	
	 @Context
	 private UriInfo context;

	 
	   public IntegracionesResource() {
	    }
	   
	   
	   @SuppressWarnings("static-access")
	   @GET
	   @Path("/consultaNAEService/{companiaFactura}/{numeroFactura}")
	   @Produces("text/xml")
	   public String consultaNae(@PathParam("companiaFactura")  String companiaFactura , @PathParam("numeroFactura")  String numeroFactura){
		   return new Integraciones().consultaNaeSoap(companiaFactura, numeroFactura).toString();
	   }
	   
	   
	   @SuppressWarnings("static-access")
	   @GET
	   @Path("/validaStop/{item}/{location}/{locType}")
	   @Produces("text/xml")
	   public String validaStop(@PathParam("item")  String item , @PathParam("location")  String location, @PathParam("locType")  String locType){
		   return new Integraciones().consultaStockSoap(item, location,locType).toString();
	   }
	   
	   
	   
	   

}
