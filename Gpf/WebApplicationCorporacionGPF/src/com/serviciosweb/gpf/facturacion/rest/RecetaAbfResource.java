package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import com.serviciosweb.gpf.facturacion.farmacias.recetaAbf.RecetaAbf;


@Path("RecetaAbf")
public class RecetaAbfResource {

	   @GET
	   @Path("/getRecetaAbf/{codigoReceta}")
	   @Produces("text/xml")
	   public String getRecetaAbf(@PathParam("codigoReceta")  String codigoReceta){
		   return new RecetaAbf(codigoReceta).getResultadoXml();
		   
	   }
	   
	   @GET
	   @Path("/setEstadoRecetaAbf/{recetaId}")
	   @Produces("text/xml")
	   public String setEstadoRecetaAbf(@PathParam("recetaId")  String recetaId){
		   return new RecetaAbf(Integer.decode(recetaId)).getResultadoXml();
		   
	   }
	   @GET
	   @Path("/setEstadoRecetaAbf/{recetaId}/{documentoVenta}/{codigoLocal}")
	   @Produces("text/xml")
	   public String setEstadoRecetaAbfDocumentoFarmacia(@PathParam("recetaId")  String recetaId,@PathParam("documentoVenta")  String documentoVenta,
			   @PathParam("codigoLocal")  String codigoLocal){
		   return new RecetaAbf(Integer.decode(recetaId),documentoVenta,codigoLocal).getResultadoXml();
		   
	   }

}
