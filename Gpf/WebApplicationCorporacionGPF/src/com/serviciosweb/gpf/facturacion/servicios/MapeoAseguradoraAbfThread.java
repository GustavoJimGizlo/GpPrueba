package com.serviciosweb.gpf.facturacion.servicios;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MapeoAseguradoraAbfThread extends Thread implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -549953758293276900L;
	private static Logger log = Logger.getLogger(MapeoAseguradoraAbfThread.class.getName());
	
	
	public void run(Connection connection) {
		try {
			refresh(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void refresh(Connection connection) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			StringBuffer sqlDelete = new StringBuffer();
			sqlDelete.append("call DBMS_MVIEW.REFRESH('farmacias.FA_MAPEO_ASEGURADORA_ABF','c')");
			pstmt = connection.prepareStatement(sqlDelete.toString());
			pstmt.executeQuery();
			log.error("OK FA_MAPEO_ASEGURADORA_ABF: Items refresh");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

}
