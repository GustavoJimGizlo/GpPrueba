package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.EnvioParametros;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

public class CupoVitalCard extends ObtenerNuevaConexion{

	
	private String resultadoXML="OK";
	//private String respuesta="";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CupoVitalCard(String numeroTarjeta, String valorConsumo, String banderaConsumo, 
			String pn_farmacia, String pv_numero_autorizacion, String  pn_documento_venta ){
		super(CupoVitalCard.class);		
		//System.out.println("numeroTarjeta  "+numeroTarjeta+"  valorConsumo  "+valorConsumo+" banderaConsumo  "+banderaConsumo);
		if(valorConsumo.substring(0, 1).equals("."))
			valorConsumo="0"+valorConsumo;
		
		ArrayList<EnvioParametros> InLista = new ArrayList();		
		InLista.add(getParametro(1, Types.VARCHAR, numeroTarjeta));
		InLista.add(getParametro(2, Types.NUMERIC, valorConsumo));
		InLista.add(getParametro(3, Types.NUMERIC, banderaConsumo));		
		InLista.add(getParametro(4, Types.NUMERIC, pn_farmacia));
		InLista.add(getParametro(5, Types.VARCHAR, pv_numero_autorizacion));
		InLista.add(getParametro(6, Types.NUMERIC, pn_documento_venta));
		
		
		ArrayList<EnvioParametros> OutLista = new ArrayList();
		OutLista.add(getParametro(7, Types.VARCHAR, null));
		
		ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
		String query="{call  VITALCARD.vc_pkg_gen_consumo_tarjeta.act_consumo_pendiente_ws(?,?,?,?,?,?,?)}";
		try {
			CallableStatement comm=conexionVarianteSid.Extraer(query, InLista, OutLista);
			if(comm.getObject(7)!=null)				
			  resultadoXML =(String)comm.getObject(7);
			else
				resultadoXML="NULL";	
		} catch (SQLException e) {
			e.printStackTrace();
			resultadoXML=e.getMessage();
		}
		
		conexionVarianteSid.cerrarConexion();
	}
	
	public String getResultadoXML() {
		return resultadoXML;
	}

	public void setResultadoXML(String resultadoXML) {
		this.resultadoXML = resultadoXML;
	}
/*
	public static void main(String[] args) {
	   new CupoVitalCard("9858100022", "300", "1");	
	}
*/	
}
