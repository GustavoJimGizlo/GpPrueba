package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.gpf.partnersGroupGPF.servicios.PartnersGroupGpf;
import com.gpf.postg.pedidos.util.FacadeException;
import com.serviciosweb.gpf.facturacion.servicios.CopiaFotosNomina;
import com.serviciosweb.gpf.facturacion.servicios.ParametrizacionKioskoServicios;
import com.serviciosweb.gpf.facturacion.servicios.Requerimientos;

@Path("Public")
public class PublicoResource {
	   @GET
	   @Path("/RequerimientoEmail/{codigoRequerimiento}")
	   @Produces("text/html")
	   public String RequerimientoEmail (@PathParam("codigoRequerimiento")String codigoRequerimiento){
		   System.out.println("codigoRequerimiento  "+codigoRequerimiento);		   
		   new Thread(new Requerimientos(codigoRequerimiento)).start();
		   return "OK";
	   }
	   
	   @GET
	   @Path("/AprobarRechazarRequerimiento/{codigoRequerimiento}/{valor}")
	   @Produces("text/html")
	   public String AprobarRechazarRequerimiento (@PathParam("codigoRequerimiento")String codigoRequerimiento,@PathParam("valor")String valor){		   
		   return "<html><h2>"+new Requerimientos(codigoRequerimiento).autorizarRequerimiento(codigoRequerimiento,null,valor)+"</h2></html>";
	   }
	   
	   @GET
	   @Path("/AprobarRechazarRequerimientoV1/{codigoRequerimiento}/{userAprobador}/{valor}")
	   @Produces("text/html")
	   public String AprobarRechazarRequerimiento (@PathParam("codigoRequerimiento")String codigoRequerimiento,@PathParam("userAprobador")String userAprobador,@PathParam("valor")String valor){		   
		   return "<html><h2>"+new Requerimientos(codigoRequerimiento).autorizarRequerimiento(codigoRequerimiento,userAprobador,valor)+"</h2></html>";
	   }
	   @GET
	   @Path("/CopiaImagenesProd/{codigoItem}")
	   @Produces("text/xml")
	   public String CopiaImagenesProd (@PathParam("codigoItem")String codigoItem){		   
		   return new CopiaFotosNomina().copiaImagenesProd(codigoItem).toString();
	   }
	   
	   
	   @GET
	   @Path("/CupoClienteVc/{codigoTarjeta}")
	   @Produces("text/xml")
	   public String CupoClienteVc (@PathParam("codigoTarjeta")String codigoTarjeta){		   
		   return new PartnersGroupGpf().cupoClienteVc(codigoTarjeta).toString();
	   }
	   
	   @GET
	   @Path("/datosFarmacias/{usuario}")
	   @Produces("application/json")
	   public String getDatosFarmacias (@PathParam("usuario")String usuario)throws FacadeException{		   
		   return new ParametrizacionKioskoServicios().getDatosFarmacia(usuario);
	   }
	   
	   
}
