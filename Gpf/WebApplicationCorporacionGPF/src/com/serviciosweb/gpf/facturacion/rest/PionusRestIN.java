package com.serviciosweb.gpf.facturacion.rest;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.serviciosweb.gpf.facturacion.servicios.NotificacionCambiosConveniosIN;
import com.serviciosweb.gpf.facturacion.servicios.AprobacionCambiosConveniosIN;


@Path("PionusRestIngresos")
public class PionusRestIN {
	
	final String PATH_RECEPCION_CAMBIO_CUPO ="SolicitudCambioCupoIn";
	final String PATH_ACEPTAR_CAMBIO_CUPO ="SolicitudAceptarCupoIn";
	final String PATH_RECHAZAR_CAMBIO_CUPO ="SolicitudRechazarCupoIn";

	@GET
	@Path("/"+ PATH_RECEPCION_CAMBIO_CUPO +"/{convenio}/{cupo}/{empresa}/{usuario}")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")

	public String SolicitudCambioCupoIn(@PathParam("convenio") String convenio, @PathParam("cupo") String cupo,
			@PathParam("empresa") String empresa, @PathParam("usuario") String usuario){		
		String respuesta = null;
		if (convenio != null) {
			respuesta = new NotificacionCambiosConveniosIN(convenio, PATH_RECEPCION_CAMBIO_CUPO, cupo, empresa, usuario).SolicitudResponse;
		} else {
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			respuestaXml.append("<Transaccion><Respuesta>No existe convenio</Respuesta></Transaccion>");
			return respuestaXml.toString();
		}
		return respuesta;		
	}
	
	
	@GET
	@Path("/"+ PATH_ACEPTAR_CAMBIO_CUPO +"/{convenio}/{usuario}/{empresa}/{cupo}/{nombre_empresa}/{estatus}")
	@Produces(MediaType.TEXT_HTML + ";charset=utf-8")
	//@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")

	public String SolicitudAceptarCupoIn(@PathParam("convenio") String convenio, @PathParam("usuario") String usuario, @PathParam("empresa") String empresa, @PathParam("cupo") String cupo, @PathParam("nombre_empresa") String nombre_empresa, @PathParam("estatus") String estatus){		
		String respuesta = null;
		if (convenio != null) {
			respuesta = new AprobacionCambiosConveniosIN(convenio, PATH_ACEPTAR_CAMBIO_CUPO, usuario, empresa, cupo, nombre_empresa, estatus).SolicitudResponse;
		} else {
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			respuestaXml.append("<Transaccion><Respuesta>No existe convenio</Respuesta></Transaccion>");
			return respuestaXml.toString();
		}
		return respuesta;
				
	}
}

