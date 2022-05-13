package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;

public class PeEstadisticasVentasThread extends Thread{

	private ConexionVarianteSid conexionVarianteSid;
	private String query;
	public PeEstadisticasVentasThread(ConexionVarianteSid conexionVarianteSid, String query){
		this.conexionVarianteSid=conexionVarianteSid;
		this.query=query;
	}
	
	@Override
	public void run(){		
		PreparedStatement ps = null;		
		try {
			ps = conexionVarianteSid.getConn().prepareStatement(query);
	        ps.executeUpdate();	
	        ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(query);
		}	
		ps=null;
		this.interrupt();
	}
	
}
