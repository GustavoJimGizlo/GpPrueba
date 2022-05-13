package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.gpf.partnersGroupGPF.servicios.PartnersGroupGpf;


@Path("FacturacionElectronicaGPF")
public class FacturacionElectronicaResource {
	
	 @Context
	 private UriInfo context;

	 
	   public FacturacionElectronicaResource() {
	    }
	   
	   
	   @GET
	   @Path("/consultaDatosCliente/{identificacion}")
	   @Produces("application/json")
	   public String getPartnerGroupGpf(@PathParam("identificacion")  String identificacion){
		   return new PartnersGroupGpf().consultarDatosClientes(identificacion);
	   }
	   

}
