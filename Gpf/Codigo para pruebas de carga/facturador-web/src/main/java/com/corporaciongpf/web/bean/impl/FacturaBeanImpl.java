package com.corporaciongpf.web.bean.impl;


import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.corporaciongpf.ejb.bean.local.CancelarFacturaLocal;
import com.corporaciongpf.ejb.bean.local.FacturaLocal;
import com.corporaciongpf.ejb.jobs.ActualizarRecaudo;
import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.excepcion.GizloPersistException;
import com.corporaciongpf.adm.excepcion.GizloUpdateException;
import com.corporaciongpf.adm.vo.VOCancelarFactura;
import com.corporaciongpf.adm.vo.VOFactura;
import com.corporaciongpf.adm.vo.VORequestCancelacion;
import com.corporaciongpf.adm.vo.VOResponse;
import com.corporaciongpf.web.bean.local.FacturaBeanLocal;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.sql.SQLException;



@Stateless
public class FacturaBeanImpl implements FacturaBeanLocal{


	@EJB(lookup = "java:global/facturador-ejb/FacturaImpl!com.corporaciongpf.ejb.bean.local.FacturaLocal")
	FacturaLocal facturaNueva;	

	
	@EJB(lookup = "java:global/facturador-ejb/CancelarFacturaImpl!com.corporaciongpf.ejb.bean.local.CancelarFacturaLocal")
	CancelarFacturaLocal cancelarFactura;	
	
	@EJB(lookup = "java:global/facturador-ejb/ActualizarRecaudo!com.corporaciongpf.ejb.jobs.ActualizarRecaudo")
	ActualizarRecaudo actualizarRecaudo;		
	
    public VOResponse crearFactura(VOFactura factura) {
		VOResponse respuesta= new VOResponse();
			
		
		try {
			respuesta=facturaNueva.crearFactura(factura);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (GizloPersistException e) {
			e.printStackTrace();
		} catch (GizloException e) {
			e.printStackTrace();
		} catch (GizloUpdateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return respuesta;
    	}
    		

    
    public VOResponse registrarCancelarFactura(VOCancelarFactura factura) {
		VOResponse respuesta= new VOResponse();
		try {
			respuesta=cancelarFactura.registrarCancelarFactura(factura);
			} 
		catch (IOException e) {
			e.printStackTrace();
			e.getMessage();
			respuesta.setCode("500");
			respuesta.setMsg(e.getMessage());
			return respuesta;			
			
			}

		return respuesta;
    	}    
        
    
    
    
    
    public VOResponse ejecutarActualizarRecaudo() {
		VOResponse respuesta= new VOResponse();
		
		
		//actualizarRecaudo.init();

		return respuesta.setResponse(respuesta, "200", "consumo exitoso");
    	}      
    
    
    public String ejecutarConsumoWS(VORequestCancelacion request) {
		
		
    	String respuesta= actualizarRecaudo.ConsumoWSCancelacion(request);


		return respuesta;
    	}      
        
    
    public String crearArchivo() {
		
		
    	//	String respuesta= actualizarRecaudo.ConsumoWSCancelacion(request);
    		String respuesta = null;
			try {
				respuesta = actualizarRecaudo.obtenerArchivo();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return respuesta;
    	}     
	
	
    public String testEjb() { 
    	String test= "";
    	test=facturaNueva.test("Test Exitoso");

    	return test;
    	
    	
    	
    	
    }
	
	

	
}
