package com.serviciosweb.gpf.facturacion.servicios;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.serviciosweb.gpf.facturacion.farmacias.bean.CredencialDS;

public class MapeoAseguradoraAbfServices extends Thread implements Serializable {
	private static final long serialVersionUID = 3897483315608937898L;
	private static Logger log = Logger.getLogger(MapeoAseguradoraAbfServices.class.getName());
	
	public void run() {
		copiaPdv();
	}

	public String copiaPdv() {

		StringBuilder respuestaXml = new StringBuilder();
		respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		respuestaXml
				.append("<Transaccion><Respuesta>Los datos enviados son correctos</Respuesta></Transaccion>");

		CredencialDS credencialDS = new CredencialDS();
		credencialDS.setDatabaseId("oficina");
		credencialDS.setUsuario("WEBLINK");
		credencialDS.setClave("weblink_2013");

		List<String> respuestaAll = new ArrayList<String>();

		Connection conn = null;
		List<String> respuesta = null;
		try {
			conn = ConexionServices.obtenerConexionFybeca(credencialDS);
			respuesta = listaSidOficina(conn);
			respuestaAll.addAll(respuesta);
		} catch (Exception e) {
			log.error(e.getMessage() + " " + e.getLocalizedMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				log.error(e.getMessage() + " " + e.getLocalizedMessage());
			}
		}

		List<String> listaFarmaciasAll = new ArrayList<String>();
		if (respuestaAll != null && !respuestaAll.isEmpty()) {
			for (String itera : respuestaAll) {
				if (!(listaFarmaciasAll.contains(itera))) {
					listaFarmaciasAll.add(itera);
				}
			}
		}

		if (listaFarmaciasAll != null && !listaFarmaciasAll.isEmpty()) {
			for (String itera : listaFarmaciasAll) {
				log.info(itera.toString());
				String[] parametros = itera.split("&");
				if (parametros.length != 2) {
					respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					respuestaXml.append("<Transaccion><Respuesta>Los datos enviados son correctos</Respuesta></Transaccion>");
				
				}
				String sid = parametros[1];
				credencialDS.setDatabaseId(sid);
				credencialDS.setUsuario("WEBLINK");
				credencialDS.setClave("weblink_2013");

				conn = null;
				try {
					conn = ConexionServices.obtenerConexionFybeca(credencialDS);
					//refresh(conn);
					
					MapeoAseguradoraAbfThread refresh = new MapeoAseguradoraAbfThread();
					refresh.run(conn);

					log.error("OK:" + credencialDS.getDatabaseId());
				} catch (Exception e) {
					log.error(e.getMessage());
				} finally {
					try {
						if (conn != null)
							conn.close();
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}

		}

		return respuestaXml.toString();

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

	public List<String> listaSidOficina(Connection conn) {
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;

		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select codigo,database_sid,nombre from ad_farmacias   where fma_autorizacion_farmaceutica = 'FS' and campo3 = 'S' ");
		sqlString.append("AND EMPRESA in (1,8,11,16) ORDER BY EMPRESA,CODIGO asc");
		ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getInt("codigo") + "&"+ set.getString("database_sid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (set != null)
					set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}

}
