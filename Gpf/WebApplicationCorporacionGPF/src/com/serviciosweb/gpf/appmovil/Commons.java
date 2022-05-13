package com.serviciosweb.gpf.appmovil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;

public class Commons {
	
	public static String getParametroSistema(String nombreParametro){
		ConexionVarianteSid con=null;
		String retorno = null;
		try {
			con=new ConexionVarianteSid(1); //1 prod  44 pro_desa
			String query = "select VALOR from WORKFLOW.WF_PARAMETROS_SISTEMA where NOMBRE_PARAMETRO = ? ";
			
			PreparedStatement ps =con.getConn().prepareStatement(query);
			ps.setString(1, nombreParametro);  
			ResultSet rs = ps.executeQuery();
			rs.next();
			retorno = rs.getString("VALOR");
			rs.close();
			con.cerrarConexion();
			return retorno;
		} catch (Exception e) {
			String error = e.toString();
		}
		return retorno;
	}
}
