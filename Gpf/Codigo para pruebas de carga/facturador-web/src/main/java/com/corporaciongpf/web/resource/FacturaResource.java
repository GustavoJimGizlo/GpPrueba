package com.corporaciongpf.web.resource;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.corporaciongpf.adm.vo.Auth;
import com.corporaciongpf.adm.vo.VOCancelarFactura;
import com.corporaciongpf.adm.vo.VOFactura;
import com.corporaciongpf.adm.vo.VORequestCancelacion;
import com.corporaciongpf.web.bean.local.FacturaBeanLocal;
import com.corporaciongpf.web.filters.GeneralFilter;
import com.corporaciongpf.adm.vo.VOResponse;



@Path("/facturador")
public class FacturaResource {

	
    @EJB
    private FacturaBeanLocal facturaBean;	
    
    
	@GET
	@Path("/testejb")
    @Produces(MediaType.TEXT_PLAIN)    
	public String testEjb() { 
    	String test;
    	test=facturaBean.testEjb();
    	
		return test;		
		
		
    }
		

	@POST
	@Path("/crearFactura")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public VOResponse crearFactura(VOFactura vo) throws  IOException {
		VOResponse respuesta= new VOResponse();
		
		if (vo.getAuthentication()== null) {
			respuesta.setCode("414");
			respuesta.setMsg("No se ha enviado la autenticacion.");
		}
		else {
			Auth auth= vo.getAuthentication();
			GeneralFilter generalFilter = new GeneralFilter();
			Boolean authentication = generalFilter.getAuth(auth);
			
			if (Boolean.TRUE.equals(authentication))
			{		
				respuesta=facturaBean.crearFactura(vo);
			}
			else {
				respuesta.setCode("400");
				respuesta.setMsg("El servicio no ha sido autenticado correctamente");
			}

		}

		return respuesta;	
	}

//	@POST
//	@Path("/crearNotaCredito")
//	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces({MediaType.APPLICATION_JSON})
//	public VOResponse crearNotaCredito(VOFactura vo) throws  IOException {
//		VOResponse respuesta= new VOResponse();
//		respuesta=facturaBean.crearNotaCredito(vo);
//
//
//
//		return respuesta;	
//	}
	
	@POST
	@Path("/registrarCancelacionFactura")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public VOResponse registrarCancelacionFactura(VOCancelarFactura vo) throws  IOException {
		VOResponse respuesta= new VOResponse();
		respuesta=facturaBean.registrarCancelarFactura(vo);



		return respuesta;	
	}	

	@GET
	@Path("/actualizarReacudo")
	@Produces({MediaType.APPLICATION_JSON})
	public VOResponse actualizar() throws  IOException {
		VOResponse respuesta= new VOResponse();
		respuesta=facturaBean.ejecutarActualizarRecaudo();

		return respuesta;	
	}	
	
	@GET
	@Path("/crearArchivoPorXML")
	@Produces({MediaType.APPLICATION_JSON})
	public VOResponse crearArchivo() throws  IOException {
		VOResponse respuesta= new VOResponse();
		String respuestaArchivo=facturaBean.crearArchivo();

		return respuesta;	
	}		
	
	
	@SuppressWarnings("null")
	@GET
	@Path("/consumoWebService/{amount}/{storeCode}/{correlationTX}")
	@Produces("text/xml")
	public String consumoWebService(@PathParam("amount") BigDecimal amount
								 , @PathParam("storeCode") String storeCode
								 , @PathParam("correlationTX") String correlationTX) {

		System.out.println(amount);
		System.out.println(storeCode);
		System.out.println(correlationTX);
		try {
			VORequestCancelacion request = new VORequestCancelacion();
			request.setAmount(amount);
			request.setStoreCode(storeCode);
			request.setCorrelationTX(correlationTX);
	
			
			String respuesta=facturaBean.ejecutarConsumoWS(request);
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<transaccion>"
			+ "<httpStatus>104</httpStatus>"
			+ "<mensaje>"
			+ respuesta
			+ "</mensaje></transaccion>";
		
			}
		 catch (Exception e) {
			e.printStackTrace();
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<transaccion><httpStatus>502</httpStatus><mensaje>"+e.getMessage()+"</mensaje></transaccion>";
		}	


	}	
	
	
	
	
	
	
	
}
