package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.serviciosweb.gpf.facturacion.servicios.Integraciones;
import com.serviciosweb.intCoperah.process.ProcessFy;
import com.serviciosweb.intCoperah.process.ProcessOki;
import com.serviciosweb.intCoperah.process.ProcessSana;

@Path("Coperah")
public class CoperahResource {

	@GET
   @Path("/ejecutaTransaccional")
   @Produces("text/json")
   public String procesaTransaccional(){

		System.out.println("Entra en WS procesa transaccional");
		String response = "";
		
		try {
			ProcessFy processFy = new ProcessFy();
			
			processFy.start();
			
			ProcessSana processSana = new ProcessSana();
			
			processSana.start();
			
			ProcessOki processOki = new ProcessOki();
			
			processOki.start();
			
			response =  "Ejecutando Transaccional";
			
		} catch (Exception e) {
			System.out.println("Error: "+ e.getMessage());
			
			response = e.getMessage();
		}
		
		return response;
   }
}
