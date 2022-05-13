package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.ConexionesBDD;



@Path("MemberQueryOutboundService")
public class MemberQueryOutboundServiceResource {
	final String PATH_MEMBER_QUERY_OUTBOUND ="MemberQueryOutbound";
	@GET
	@Path("/"+ PATH_MEMBER_QUERY_OUTBOUND)
	@Produces(MediaType.APPLICATION_XML)
	
	
	
	public String consultaDatos(@QueryParam("CustomerID") String customerID,@QueryParam("StoreID") String storeID,
			@QueryParam("SaleChannel") String saleChannel,@QueryParam("WorkstationID") String workstationID) {
		//String nombreSegmento = "<TIER_NAME>"+ConexionesBDD.consultarNombreSegmento(customerID)+"</TIER_NAME>";
		//String n1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TRANSACCION><TIER_NAME>"+ConexionesBDD.consultarNombreSegmento(customerID)+"</TIER_NAME></TRANSACCION>";
		//String n2 = "\n<TIER_CODIGO>"+ConexionesBDD.consultarCodigoSegmento(customerID)+"</TIER_CODIGO>";
		//String nombreSegmento = n1;
		String nombreSegmento = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<TRANSACCION>\n<TIER_NAME>"+ConexionesBDD.consultarNombreSegmento(customerID)+"</TIER_NAME>\n<point_type_a_val>0</point_type_a_val>\n</TRANSACCION>";
		
		
		return nombreSegmento;
	}

}
