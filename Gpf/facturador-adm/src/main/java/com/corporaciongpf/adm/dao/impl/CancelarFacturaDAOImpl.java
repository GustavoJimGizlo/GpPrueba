package com.corporaciongpf.adm.dao.impl;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import com.corporaciongpf.adm.dao.CancelarFacturaDAO;
import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.modelo.CancelarFactura;
import com.corporaciongpf.adm.vo.VOCreditoDevolucion;



@Stateless
public class CancelarFacturaDAOImpl extends GenericJpaDAO<CancelarFactura, Long>
		implements CancelarFacturaDAO {

	
	
	
	public BigDecimal obtenerNumeroEnvio(Connection conn,Long secuencia ) {
		
		List<BigDecimal> respuesta = new ArrayList<BigDecimal>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select numero_envios from FARMACIAS.FA_INT_CANCEL_FD where documento_venta=?  order by numero_envios desc");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setLong(1, secuencia);
			
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getBigDecimal("numero_envios"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		if (respuesta.size()>0) {
			return respuesta.get(0);				
			
		}
		else {
			return null;
		}
		
	}	
	
		
		
	
	public Long obtenerSecuencia(Connection conn) {
		
		List<Long> respuesta = new ArrayList<Long>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select FARMACIAS.SEQ_FA_INT_CANCEL_FD.nextval as secuencia from dual ");
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
	
	
	
	
	public void insertRegistroCancelarFactura(Connection conn, CancelarFactura cancelarFactura)	throws GizloException {


		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;


		sqlString
				.append("Insert into FARMACIAS.FA_INT_CANCEL_FD("
						+ "DOCUMENTO_VENTA, "
						+ "FARMACIA, "
						+ "FECHA_ACTUALIZA, "
						+ "FECHA_INSERTA, "
						+ "FECHA_REQUEST, "
						+ "FECHA_RESPONSE, "
						+ "MENSAJE, "
						+ "MENSAJE_REQUEST, "
						+ "ORDEN_SFCC, "
						+ "TRAZA_ID, "
						+ "TRAZA_REQUEST, "
						+ "USUARIO_ACTUALIZA, "
						+ "USUARIO_INSERTA,"
						+ "JSON_REQUEST,"
						+ "JSON_RESPONSE,"
						+ "NUMERO_ENVIOS,"
						+ "ID"
						+ ")");

		sqlString.append(" Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
		try {
	
					    
			ps = conn.prepareStatement(sqlString.toString());

		    Clob clob1 = ps.getConnection().createClob();
		    clob1.setString(1, cancelarFactura.getJsonRequest());			
			
		    Clob clob2 = ps.getConnection().createClob();
		    clob2.setString(1, cancelarFactura.getJsonResponse());					
			
			ps.setLong(1, cancelarFactura.getDocumentoVenta());
			ps.setLong(2, cancelarFactura.getFarmacia());
			ps.setDate(3, cancelarFactura.getFechaActualiza());
			ps.setDate(4, cancelarFactura.getFechaInserta());
			ps.setDate(5, cancelarFactura.getFechaRequest());
			ps.setDate(6, cancelarFactura.getFechaResponse());
			ps.setString(7, cancelarFactura.getMensaje());
			ps.setString(8, cancelarFactura.getMensajeRequest());
			ps.setString(9, cancelarFactura.getOrdenSfcc());
			ps.setBigDecimal(10, cancelarFactura.getTrazaId());
			ps.setString(11, cancelarFactura.getTrazaRequest());
			ps.setString(12, cancelarFactura.getUsuarioActualiza());
			ps.setString(13, cancelarFactura.getUsuarioInserta());
			ps.setClob(14, clob1);
			ps.setClob(15, clob2);
			ps.setBigDecimal(16, cancelarFactura.getNumeroEnvios());
			ps.setLong(17, cancelarFactura.getId());

		
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

    
    
}