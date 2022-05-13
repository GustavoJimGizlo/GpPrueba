package com.serviciosweb.gpf.appmovil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("Empleado")
public class EmpleadosTransacciones {
	@GET
	@Path("/bmverific/{randomCode}")
	@Produces("text/xml")
	public String bmverific(@PathParam("randomCode") String randomCode) {
		System.out.println("/bmverific/" + randomCode);
		AppController c = new AppController();
		return c.returnVerificarCompra(randomCode);
	}

	@GET
	@Path("/bmcompra/{codusuario}/{randomCode}/{valor}/{documentoventa}/{farmacia}/{formapago}")
	@Produces("text/xml")
	public String bmcompra(@PathParam("codusuario") String codusuario, @PathParam("randomCode") String randomCode, @PathParam("valor") String valor, @PathParam("documentoventa") String documentoventa, @PathParam("farmacia") String farmacia, @PathParam("formapago") String formapago) {
		System.out.println("/bmcompra/" + codusuario + "/" + randomCode + "/" + valor + "/" + documentoventa + "/" + farmacia + "/" + formapago);
		AppController c = new AppController();
		return c.returnEstadoCompra(codusuario, randomCode, valor, documentoventa, farmacia, formapago);
	}
}