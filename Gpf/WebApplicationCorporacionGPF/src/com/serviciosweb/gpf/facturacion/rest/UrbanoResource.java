package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.gpf.partnersGroupGPF.bean.GuiaElectronicaEspsRequest;
import com.gpf.partnersGroupGPF.bean.RpGuia;
import com.gpf.partnersGroupGPF.bean.TrackingRequest;
import com.serviciosweb.gpf.facturacion.servicios.UrbanoServices;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;


@Path("urbano")
public class UrbanoResource {

	   @GET
	   @Path("/guiaElectronicaEsp/{linea}/{codRastreo}/{codCliente}/{codBarra}/{nomCliente}/{dirEntrega}/{refDirec}/{fechaSs}/{fechaDe}/{fechaAd}/{fechaSr}/{unidadPlaca}/{cedulaCou}/{agencia}")
	   @Produces("text/xml")
	   public String getRecetaAbf(@PathParam("linea")  String linea,
			   @PathParam("codRastreo")  String codRastreo,
			   @PathParam("codCliente")  String codCliente,
			   @PathParam("codBarra")  String codBarra,
			   @PathParam("nomCliente")  String nomCliente,
			   @PathParam("dirEntrega")  String dirEntrega,
			   @PathParam("refDirec")  String refDirec,
			   @PathParam("fechaSs")  String fechaSs,
			   @PathParam("fechaDe")  String fechaDe,
			   @PathParam("fechaAd")  String fechaAd,
			   @PathParam("fechaSr")  String fechaSr,
			   @PathParam("unidadPlaca")  String unidadPlaca,
			   @PathParam("cedulaCou")  String cedulaCou,
			   @PathParam("agencia")  String agencia){
		   
		   XStream xstream = new XStream(new StaxDriver());
		   RpGuia guia= new RpGuia(); 
     	   guia.setCodigoGuia("WYB16171360");
		   xstream.alias("Guia", RpGuia.class);
		   GuiaElectronicaEspsRequest  rqGuia= new GuiaElectronicaEspsRequest();
			rqGuia.setVp_agencia(agencia);
			rqGuia.setVp_cedula_cou(cedulaCou);
			rqGuia.setVp_cod_barra(codBarra);
			rqGuia.setVp_cod_cliente(codCliente.replace("&", " "));
			rqGuia.setVp_cod_rastreo(codRastreo);
			//rqGuia.setVp_dir_entrega(dirEntrega.replace("&", " "));
			rqGuia.setVp_dir_entrega(new UrbanoServices().getDirEcommers(codRastreo, agencia, dirEntrega.replace("&", " ")));
			rqGuia.setVp_ref_direc(refDirec.replace("&", " "));
			rqGuia.setVp_fecha_ad(fechaAd.replace("&", " "));
			rqGuia.setVp_fecha_de(fechaDe.replace("&", " "));
			rqGuia.setVp_fecha_sr(fechaSr.replace("&", " "));
			rqGuia.setVp_fecha_ss(fechaSs.replace("&", " "));
			rqGuia.setVp_linea(linea);
			rqGuia.setVp_nom_cliente(nomCliente.replace("&", " "));
			
			
			rqGuia.setVp_unidad_placa(unidadPlaca);
			
		    UrbanoServices dato = new UrbanoServices(rqGuia);

			return dato.urbanoServicesResponse;
		
		   
	   }
	   
	   @GET
	   @Path("/tracking/{guia}/{docref}/{linea}")
	   @Produces("text/xml")
	   public String setEstadoRecetaAbf(@PathParam("guia")  String guia,
			   @PathParam("docref")  String docref,
			   @PathParam("linea")  String linea){
		   
		   
		    TrackingRequest  trackingRequest = new TrackingRequest();
		    trackingRequest.setDocref(docref);
		    trackingRequest.setGuia(guia);
		    trackingRequest.setVp_linea(linea);		    
		    UrbanoServices dato = new UrbanoServices(trackingRequest);
		    
		    
		    return dato.urbanoServicesResponse;
	   }

}
