package com.serviciosweb.gpf.facturacion.rest;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.serviciosweb.gpf.facturacion.servicios.NotificacionCambiosConveniosVC;
import com.serviciosweb.gpf.facturacion.servicios.AprobacionCambiosConveniosVC;


@Path("PionusRestVC")
public class PionusRest {
	
	final String PATH_RECEPCION_CAMBIO_CUPO ="SolicitudCambioCupoVC";
	final String PATH_ACEPTAR_CAMBIO_CUPO ="SolicitudAceptarCupoVC";
	final String PATH_RECHAZAR_CAMBIO_CUPO ="SolicitudRechazarCupoVC";

	@GET
	@Path("/"+ PATH_RECEPCION_CAMBIO_CUPO +"/{convenio}/{cupo}/{empresa}/{usuario}")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")

	public String SolicitudCambioCupoVC(@PathParam("convenio") String convenio, @PathParam("cupo") String cupo,
			@PathParam("empresa") String empresa, @PathParam("usuario") String usuario){		
		String respuesta = null;
		if (convenio != null) {
			respuesta = new NotificacionCambiosConveniosVC(convenio, PATH_RECEPCION_CAMBIO_CUPO, cupo, empresa, usuario).SolicitudResponse;			
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

	public String SolicitudAceptarCupoVC(@PathParam("convenio") String convenio, @PathParam("usuario") String usuario, @PathParam("empresa") String empresa, @PathParam("cupo") String cupo, @PathParam("nombre_empresa") String nombre_empresa, @PathParam("estatus") String estatus){		
		String respuesta = null;
		if (convenio != null) {
			respuesta = new AprobacionCambiosConveniosVC(convenio, PATH_ACEPTAR_CAMBIO_CUPO, usuario, empresa, cupo, nombre_empresa, estatus).SolicitudResponse;
		} else {
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			respuestaXml.append("<Transaccion><Respuesta>No existe convenio</Respuesta></Transaccion>");
			return respuestaXml.toString();
		}
		return respuesta;
				
	}
	
	
}

