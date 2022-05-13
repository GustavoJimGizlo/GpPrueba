package com.serviciosweb.gpf.facturacion.servicios.aseguradora.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.plan.PlanesBean;

import oracle.jdbc.driver.OracleDriver;

public class ConexionesBDD {

	// PROD
	
	 static String quienIngresa="WEBLINK"; 
	 static String comoIngresa="weblink_2013"; 
	 static String url="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@PRS6.UIO"; 
	 static String urlSana="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@SANA.UIO";
	

	// TEST
	//final static String quienIngresa = "Farmacias";
	//final static String comoIngresa = "farmacias";
	//final static String url = "jdbc:oracle:thin:" + quienIngresa + "/"+ comoIngresa + "@PRODUAT_CLOUD_M2C.UIO";
	//final static String urlSana = "jdbc:oracle:thin:" + quienIngresa + "/"+ comoIngresa + "@SANAUAT_CLOUD_M2C.UIO";

	  
	public static int guardarTablaMapeoAseguradora(List<PlanesBean> planes,String codigoCarga, Integer idAseguradora) {

		Connection conn = null;
		Connection connSana = null;

		int result = 0;

		try {
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");

			DriverManager.registerDriver(new OracleDriver());
			DriverManager.setLoginTimeout(100);
			conn = DriverManager.getConnection(url);
			connSana = DriverManager.getConnection(urlSana);

			for (PlanesBean plan : planes) {
				int cont = insertMapeoAseguradora(conn, plan, codigoCarga,idAseguradora);
				int contSana = insertMapeoAseguradora(connSana, plan,codigoCarga, idAseguradora);
				if (cont == 1 && contSana == 1) {
					result++;
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();

				if (connSana != null)
					connSana.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	public static int validarRegistrosMapeoAseguradora(String codigoCarga,
			Integer idAseguradora) {

		Connection conn = null;
		conn = null;
		int result = 0;
		try {
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");

			DriverManager.registerDriver(new OracleDriver());
			DriverManager.setLoginTimeout(100);

			conn = DriverManager.getConnection(url);

			result = validarIngresoMapeoAseg(conn, codigoCarga, idAseguradora);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	public static int eliminarTablaMapeoAseguradora(String codigoCarga,
			Integer idAseguradora) {

		Connection conn = null;
		conn = null;
		int result = 0;
		try {

			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");

			DriverManager.registerDriver(new OracleDriver());
			DriverManager.setLoginTimeout(100);

			conn = DriverManager.getConnection(url);

			result = deleteMapeoAseguradora(conn, codigoCarga, idAseguradora);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int insertMapeoAseguradora(Connection connection,
			PlanesBean planesBean, String codigoCarga, Integer idAseguradora)
			throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			StringBuffer sqlDelete = new StringBuffer();
			sqlDelete
					.append("insert into FA_MAPEO_ASEGURADORA_ABF (CODIGO,CONTRATO_ASEG, PLAN_ASEG,CAMPO1, COBERTURA_A, COBERTURA_B,CAMPO2,CAMPO3)");
			sqlDelete.append("values (?,?,?,?,?,?,?,?)");

			pstmt = connection.prepareStatement(sqlDelete.toString());

			pstmt.setInt(1, getCodigoMapeoAseguradora(connection));
			pstmt.setString(2,
					planesBean.getContrato() + planesBean.getCodplan());
			pstmt.setString(3, planesBean.getCodplan());
			pstmt.setString(4, "INT");
			pstmt.setString(5,(planesBean.getVad_A() != null) ? planesBean.getVad_A(): "");
			pstmt.setString(6,(planesBean.getVad_B() != null) ? planesBean.getVad_B(): "");
			pstmt.setString(7, codigoCarga);
			pstmt.setString(8, idAseguradora + "");
			result = pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}

		return result;
	}

	public static int deleteMapeoAseguradora(Connection connection,
			String codigoCarga, Integer idAseguradora) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			StringBuffer sqlDelete = new StringBuffer();
			sqlDelete.append("delete from FA_MAPEO_ASEGURADORA_ABF where  CAMPO2 = ? and CAMPO3=?");
			pstmt = connection.prepareStatement(sqlDelete.toString());
			pstmt.setString(1, codigoCarga);
			pstmt.setString(2, idAseguradora + "");
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}

		return result;
	}

	public static int getCodigoMapeoAseguradora(Connection conn) {
		int respuesta = 0;
		PreparedStatement ps = null;

		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select max(codigo) + 1 as codigo from FA_MAPEO_ASEGURADORA_ABF ");

		ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			set = ps.executeQuery();
			while (set.next()) {
				respuesta = set.getInt("codigo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (set != null)
					set.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}

	public static int validarIngresoMapeoAseg(Connection conn,String codigoConfrimacion, Integer idAseguradora) {
		int respuesta = 0;
		PreparedStatement ps = null;

		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  select count(*) codigo ");
		sqlString.append(" from FA_MAPEO_ASEGURADORA_ABF ");
		sqlString.append("	where CAMPO2 = ? ");
		sqlString.append("  and CAMPO3 = ?  ");

		ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, codigoConfrimacion);
			ps.setString(2, idAseguradora.toString());
			set = ps.executeQuery();
			while (set.next()) {
				respuesta = set.getInt("codigo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (set != null)
					set.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}

	public static String consultarNombreSegmento(String customerID) {
		Connection conn = null;
		conn = null;
		String result = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");

			DriverManager.registerDriver(new OracleDriver());
			DriverManager.setLoginTimeout(100);
			System.out.println("Conexion " + url);
			conn = DriverManager.getConnection(url);

			result = getNombreSegmento(conn, customerID);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * Metodo para consultar el nombre del segmento
	 * 
	 * @param conn
	 * @return
	 */
	public static String getNombreSegmento(Connection conn, String customerID) {
		String respuesta = null;
		PreparedStatement ps = null;

		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select (select campo2 from CR_SEGMENTOS_CLIENTE where codigo = cliente.SEGMENTO and rownum=1) as nombre "
						+ "from cr_clasificacion_cliente cliente where codigo_persona=? ");

		ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, customerID);
			set = ps.executeQuery();
			while (set.next()) {
				respuesta = set.getString("nombre");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (set != null)
					set.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}
}
