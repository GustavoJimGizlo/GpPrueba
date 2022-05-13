package com.serviciosweb.gizlo.utilitarios;



import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.serviciosweb.gpf.facturacion.farmacias.bean.CredencialDS;


public class Conexion {
	private static Logger log = Logger.getLogger(Conexion.class.getName());

	public static Connection obtenerConexionFybeca(CredencialDS credencialDS) {
		Conexion conexion = new Conexion();
		Connection con = null;
		try {

			con = conexion.obtenerConexion(credencialDS.getDatabaseId(),
					credencialDS.getUsuario(), credencialDS.getClave());

		} catch (Exception e) {
			log.error("Ha ocurrido un error en Conexion: ", e);
		}
		return con;
	}
	
	public static Connection obtenerConexionCRPDTA(CredencialDS credencialDS) {
		Conexion conexion = new Conexion();
		Connection con = null;
		try {
			con = conexion.obtenerConexion(credencialDS.getDatabaseId(),
					credencialDS.getUsuario(), credencialDS.getClave());
		} catch (Exception e) {
			log.error("Ha ocurrido un error en Conexion: ", e);
		}

		return con;
	}

	public Connection obtenerConexion(String sidDataBase, String usuario,
			String clave) throws NamingException, Exception {
		String url = null;
		Connection conn = null;
		try {
			System.setProperty("oracle.net.tns_admin", "/data/oracle");
			Class.forName("oracle.jdbc.OracleDriver").newInstance();

			url = "jdbc:oracle:thin:@"
					+ (sidDataBase != null ? sidDataBase.toUpperCase() : "")
					+ ".UIO";
			// log.debug("CONECTANDO A " + url);
			DriverManager.setLoginTimeout(100);
			conn = DriverManager.getConnection(url, usuario, clave);
		} catch (Exception ex) {
			log.error(ex.toString() + " FALLA COMEXION A SID:  " + sidDataBase
					+ " URL: " + url, ex);
		}
		return conn;
	}
	

}
