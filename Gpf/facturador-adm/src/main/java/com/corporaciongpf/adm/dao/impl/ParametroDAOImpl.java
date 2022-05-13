package com.corporaciongpf.adm.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.corporaciongpf.adm.dao.ParametroDAO;
import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.modelo.Parametro;




@Stateless
public class ParametroDAOImpl implements ParametroDAO {


	public Long obtenerSecuenciaNotaCredito(Connection conn) {
		
		List<Long> respuesta = new ArrayList<Long>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select FARMACIAS.FA_CLAVES_NOTAS_CREDITO_SEQ.nextval as secuencia from dual ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getLong("secuencia"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return respuesta.get(0);			
		
				
		
		
		
		
	}

	
	
	
	
	public String obtenerParametro(Connection conn, String clave) {
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" SELECT VALOR FROM fa_parametros_facturador WHERE CLAVE=? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, clave);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("valor"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return respuesta.get(0);			
		
		
		
	}

	
		

	
	public String obtenerDatoSecciones(Connection conn, Long codigoItem, String campo)throws GizloException{
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
	
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" SELECT "+campo + " from pr_secciones "
				+ "where codigo=(SELECT seccion FROM PR_CASAS "
				+ "where codigo=(SELECT casa FROM PR_PRODUCTOS "
				+ "where codigo=(SELECT producto FROM PR_ITEMS where codigo =?)))");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setLong(1, codigoItem);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString(campo));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (respuesta.size()>0)
		{
			return respuesta.get(0).toString();			
		}
		else {
			if (campo.equalsIgnoreCase("codigo")){
				return "0";			
			}
			else {
				return "-";			
			}
		}
		
	}	
	
	public String obtenerFarmacia(Connection conn,Long codigo) {
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" SELECT DATABASE_SID FROM ADMINISTRACION.AD_FARMACIAS WHERE codigo=? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setLong(1, codigo);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("DATABASE_SID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return respuesta.get(0);			
		
	}		

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public String obtenerCodigoMetodoPagoFarmacia(Connection conn, String codigoPago, String codigoMetodoPago)throws GizloException{
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  SELECT  "
				+ "CODIGO_METODO_PAGO_FYBECA"
				+ " FROM FARMACIAS.FA_RELACION_FORMA_PAGO WHERE CODIGO_PAGO = ? AND CODIGO_METODO_PAGO  = ? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, codigoPago);
			ps.setString(2, codigoMetodoPago);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("CODIGO_METODO_PAGO_FYBECA"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (respuesta.size()>0) {
			return respuesta.get(0);			
		}
		else {
			return "-";			
		}
	
	}
	
	
	
	
	
	
	
	
	
	
	
}


	

