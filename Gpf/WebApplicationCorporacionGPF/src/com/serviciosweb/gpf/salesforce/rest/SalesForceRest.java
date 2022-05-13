package com.serviciosweb.gpf.salesforce.rest;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.serviciosweb.gpf.salesforce.service.NotificaEstadoService;
import com.serviciosweb.gpf.salesforce.service.OrdenCompraService;
import com.serviciosweb.gpf.salesforce.xml.Transaccion;

@Path("salesForce")
public class SalesForceRest {

	@GET
	@Path("/ordenCompra/{farmacia}/{ordenCompra}")
	@Produces("application/xml; charset=UTF-8")
	public Transaccion ordenCompra(@PathParam("farmacia") String farmacia,@PathParam("ordenCompra") String ordenCompra) throws UnsupportedEncodingException {

		OrdenCompraService ordenComSer = new OrdenCompraService();
		ordenComSer.consultarOrdenCompra(farmacia,ordenCompra);
       return ordenComSer.getResultado();

	}

	@GET
	@Path("/notificaEstado/{ordenCompra}/{codPdv}/{estado}/{docVenta}")
	@Produces("text/xml")
	public Transaccion notificaEstado(
			@PathParam("ordenCompra") String ordenCompra,
			@PathParam("codPdv") String codPdv,
			@PathParam("estado") String estado,
			@PathParam("docVenta") String docVenta) {

		NotificaEstadoService notificaEstadoServ = new NotificaEstadoService(ordenCompra,codPdv,estado,docVenta);

		return notificaEstadoServ.getResultado();
	}

}
