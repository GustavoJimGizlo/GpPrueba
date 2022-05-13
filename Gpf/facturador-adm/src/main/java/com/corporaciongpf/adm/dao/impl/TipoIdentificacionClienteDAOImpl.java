package com.corporaciongpf.adm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;


import com.corporaciongpf.adm.dao.TipoIdentificacionClienteDAO;


@Stateless
public class TipoIdentificacionClienteDAOImpl  implements TipoIdentificacionClienteDAO {

	
	public Boolean obtenerIdentificacion(Connection conn, String codigo) {
		
		List<Long> respuesta = new ArrayList<Long>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select c.id from FARMACIAS.FA_IDENTIFICACION_CLIENTE C  where c.codigo=? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, codigo);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getLong("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (respuesta.size()>0) {
			return true;
		}
		else {
			return false;
		}

	
	}	
	
	
	



	
}
