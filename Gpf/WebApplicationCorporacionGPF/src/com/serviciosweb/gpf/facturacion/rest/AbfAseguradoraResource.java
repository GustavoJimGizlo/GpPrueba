package com.serviciosweb.gpf.facturacion.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.serviciosweb.gpf.facturacion.servicios.MapeoAseguradoraAbfServices;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.AseguradoraController;

@Path("AbfAseguradora")

public class AbfAseguradoraResource {

	final String PATH_CONSULTA_DATOS = "consultaDatos";
	final String PATH_CONFIRMACION_DATOS = "confirmacionDatos";
	final String PATH_CONSULTA_PLANES = "ConsultaPlanes";

	@GET
	// @Path("/"+ PATH_CONSULTA_DATOS +"/{identificacion}/{idAseguradora}/{tipoid}")
	@Path("/" + PATH_CONSULTA_DATOS + "/{identificacion}/{idAseguradora}")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public String consultaDatos(@PathParam("idAseguradora") Integer idAseguradora,
			@PathParam("identificacion") String identificacion

	)

	{
		String respuesta = "";
		if (idAseguradora != null) {
			// int identificacion. identificacion.indexOf("a");
			String id = identificacion;
			String tipoid = null;
			int s = id.indexOf("_");

			String identificacionnew = null;

			if (s != -1) {
				identificacionnew = id.substring(0, s);
				tipoid = id.substring(s + 1, s + 2);
			} else {
				identificacionnew = id;
				tipoid = "C";
			}

			List<Object> objetos = new ArrayList<Object>();
			objetos.add(identificacionnew);

			respuesta = new AseguradoraController(idAseguradora, PATH_CONSULTA_DATOS, objetos,
					tipoid).AseguradoraResponse;

		} else {
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			respuestaXml.append("<Transaccion><Respuesta>No existe Id de Aseguradora</Respuesta></Transaccion>");
			return respuestaXml.toString();

		}
		return respuesta;
	}

	@POST
	@Path("/" + PATH_CONFIRMACION_DATOS)
	@Consumes({ "application/xml" })
	@Produces("text/xml; charset=UTF-8")
	public String confirmacionDatos(String recetaGenBean) {
		String respuesta = "";
		Integer idAseguradora = null;

		if (recetaGenBean.contains("<aseguradora>")) {
			try {
				idAseguradora = Integer.parseInt((recetaGenBean.split("<aseguradora>")[1]).split("</aseguradora>")[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (idAseguradora != null) {
			// respuesta = new
			// AseguradoraController(idAseguradora,recetaGenBean).AseguradoraResponse;
			List<Object> objetos = new ArrayList<Object>();
			objetos.add(recetaGenBean);
			// objetos

			respuesta = new AseguradoraController(idAseguradora, PATH_CONFIRMACION_DATOS, objetos,
					"").AseguradoraResponse;
		} else {
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			respuestaXml.append("<Transaccion><Respuesta>No existe Id de Aseguradora</Respuesta></Transaccion>");
			return respuestaXml.toString();
		}

		return respuesta;
	}

	@GET
	@Path("/mapeoAseguradoraAbf/")
	@Produces(MediaType.APPLICATION_XML)
	public String mapeoAseguradoraAbf() {
		try {
			new MapeoAseguradoraAbfServices().start();
		} catch (Exception e) {
		}

		StringBuilder respuestaXml = new StringBuilder();
		respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		respuestaXml.append("<Transaccion><Respuesta>Los datos enviados son correctos</Respuesta></Transaccion>");

		return respuestaXml.toString();
	}

	@GET
	@Path("/" + PATH_CONSULTA_PLANES + "/{idAseguradora}")
	@Produces(MediaType.APPLICATION_XML)
	public String consultaPlanes(@PathParam("idAseguradora") Integer idAseguradora) {
		String respuesta = "";
		if (idAseguradora != null) {
			// respuesta = new AseguradoraController(idAseguradora,1).AseguradoraResponse;
			List<Object> objetos = new ArrayList<Object>();
			objetos.add(1);
			respuesta = new AseguradoraController(idAseguradora, PATH_CONSULTA_PLANES, objetos, "").AseguradoraResponse;
		} else {
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			respuestaXml.append("<Transaccion><Respuesta>No existe Id de Aseguradora</Respuesta></Transaccion>");
			return respuestaXml.toString();

		}
		return respuesta;
	}

	//

	@GET
	@Path("/ValidaJsonHumana/{valor}")
	@Produces("text/plain")
	public String HumanaValidacionJson(@PathParam("valor") String valor) {
		new com.serviciosweb.gpf.facturacion.servicios.aseguradora.controlador.HumanaValidacionJson();
		return "OK";
	}

}
