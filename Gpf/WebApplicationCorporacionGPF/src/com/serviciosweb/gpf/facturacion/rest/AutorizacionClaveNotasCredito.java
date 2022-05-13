package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import com.serviciosweb.gpf.facturacion.servicios.AutorizacionClaveNotasCreditoControlador;

/**
 * @author Antonio Encarnacion R
 * 
 *         Fecha: 01/10/2020
 * 
 *         AER_REQ4913 Generacion de claves Notas de Credito
 * 
 */

@Path("NotasCredito")
public class AutorizacionClaveNotasCredito {

	@GET
	@Path("/verificarClaveAutorizacion/{codigoPdv}/{numDocumento}/{tipoTrxNC}/{tipoAutorizadorTrx}/{clave}")
	@Produces("text/xml")
	public String bmverific(@PathParam("codigoPdv") String codigoPdv, @PathParam("numDocumento") String numDocumento,
			@PathParam("tipoTrxNC") String tipoTrxNC, @PathParam("tipoAutorizadorTrx") String tipoAutorizadorTrx,
			@PathParam("clave") String clave) {

		AutorizacionClaveNotasCreditoControlador aut = new AutorizacionClaveNotasCreditoControlador();
		return aut.claveAutorizada(codigoPdv, numDocumento, tipoTrxNC, tipoAutorizadorTrx, clave);
	}
}
