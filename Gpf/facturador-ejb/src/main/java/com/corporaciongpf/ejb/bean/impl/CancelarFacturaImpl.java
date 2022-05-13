package com.corporaciongpf.ejb.bean.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.corporaciongpf.adm.dao.CancelarFacturaDAO;
import com.corporaciongpf.adm.dao.ParametroDAO;
import com.corporaciongpf.adm.modelo.CancelarFactura;
import com.corporaciongpf.adm.modelo.CredencialDS;
import com.corporaciongpf.adm.vo.VOCancelarFactura;
import com.corporaciongpf.adm.vo.VOResponse;
import com.corporaciongpf.ejb.bean.local.CancelarFacturaLocal;
import com.corporaciongpf.ejb.utilitario.Conexion;
import com.corporaciongpf.ejb.utilitario.Constantes;



@Stateless
public class CancelarFacturaImpl implements CancelarFacturaLocal {

	@EJB(lookup = "java:global/facturador-adm/CancelarFacturaDAOImpl!com.corporaciongpf.adm.dao.CancelarFacturaDAO")
	CancelarFacturaDAO cancelarFacturaDAO;	
	
	
	@EJB(lookup = "java:global/facturador-adm/ParametroDAOImpl!com.corporaciongpf.adm.dao.ParametroDAO")
	ParametroDAO parametroDAO;

	

	
	
	public VOResponse registrarCancelarFactura( VOCancelarFactura facturaRequest  ) throws IOException {

		VOResponse respuesta= new VOResponse();
		CredencialDS credencialDSOfi = new CredencialDS();
		credencialDSOfi.setDatabaseId(Constantes.BASE_PRS6);
		credencialDSOfi.setUsuario(Constantes.USUARIO_PRS6);
		credencialDSOfi.setClave(Constantes.CLAVE_PRS6);
		credencialDSOfi.setRegion(Constantes.REGION_PRS6);
		credencialDSOfi.setDirectorio(Constantes.DIRECTORIO_TS_NAME_PRS6);			
		Connection connPrs6 = null;						
		connPrs6 = Conexion.obtenerConexionFybeca(credencialDSOfi);		
		
		BigDecimal numeroEnvios= cancelarFacturaDAO.obtenerNumeroEnvio(connPrs6,facturaRequest.getDocumentoVenta()) ;
		CancelarFactura cancelarFactura = new CancelarFactura();
		cancelarFactura.setDocumentoVenta(facturaRequest.getDocumentoVenta());
		cancelarFactura.setFarmacia(facturaRequest.getFarmacia());
		cancelarFactura.setOrdenSfcc(facturaRequest.getOrdenSfcc());
        Date fechaSistema= new Date();
		long tiempofechaSistema = fechaSistema.getTime();
        java.sql.Date date = new java.sql.Date(tiempofechaSistema);				
		
		
		cancelarFactura.setFechaRequest(date);
		cancelarFactura.setJsonRequest(facturaRequest.getJsonRequest());
		cancelarFactura.setMensajeRequest(facturaRequest.getMensajeRequest());
		cancelarFactura.setTrazaId(facturaRequest.getTrazaID());
		cancelarFactura.setFechaInserta(facturaRequest.getFechaInserta());
		cancelarFactura.setUsuarioInserta(facturaRequest.getUsuarioInserta());
		cancelarFactura.setJsonResponse(facturaRequest.getJsonResponse());
		cancelarFactura.setFechaResponse(facturaRequest.getFechaResponse());
		cancelarFactura.setFechaActualiza(facturaRequest.getFechaResponse());
		cancelarFactura.setUsuarioActualiza("-");
		
		
		BigDecimal nuevo;
		if (numeroEnvios == null){
			 nuevo = new BigDecimal("1") ;
		}
		else {
			nuevo= numeroEnvios.add(new BigDecimal("1"));
		}
		
		cancelarFactura.setNumeroEnvios(nuevo);
		cancelarFactura.setId(cancelarFacturaDAO.obtenerSecuencia(connPrs6));
		
		try {
			cancelarFacturaDAO.insertRegistroCancelarFactura(connPrs6,cancelarFactura);
			respuesta.setCode("200");
			respuesta.setMsg("Se realizó el registro  correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setCode("502");
			respuesta.setMsg("Error Insperado en la insercion: "+ e.getMessage());
			
		}
	
		try {
			connPrs6.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return respuesta;	
		}
}

