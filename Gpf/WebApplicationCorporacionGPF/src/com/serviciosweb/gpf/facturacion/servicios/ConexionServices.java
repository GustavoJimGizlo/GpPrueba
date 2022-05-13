package com.serviciosweb.gpf.facturacion.servicios;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.NamingException;

import oracle.jdbc.driver.OracleDriver;

import org.apache.log4j.Logger;

import com.serviciosweb.gpf.facturacion.farmacias.bean.CredencialDS;


public class ConexionServices {
	private static Logger log = Logger.getLogger(ConexionServices.class.getName());
	
	public static void main(String[] args) {
		
		ConexionServices Conexion = new ConexionServices();
		
		try {
			Connection conecion = Conexion.obtenerConexion("oficina", "weblink", "weblink_2013");
			conecion.getMetaData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static Connection obtenerConexionFybeca(CredencialDS credencialDS) {
		ConexionServices conexion = new ConexionServices();
		Connection con = null;
		try {
			con = conexion.obtenerConexion( credencialDS.getDatabaseId()
										   ,credencialDS.getUsuario()
										   ,credencialDS.getClave());

		} catch (Exception e) {
			log.error("Ha ocurrido un error en Conexion: ", e);
		}
		return con;
	}
	
	/*public static Connection obtenerConexionFarmacia(CredencialDS credencialDS) {
		ConexionServices conexion = new ConexionServices();
		Connection con = null;
		try {
			con = conexion.obtenerConexion2( credencialDS.getDatabaseId()
										   ,credencialDS.getUsuario()
										   ,credencialDS.getClave());

		} catch (Exception e) {
			log.error("Ha ocurrido un error en Conexion: ", e);
		}
		return con;
	}*/
	
	public static Connection obtenerConexionCRPDTA(CredencialDS credencialDS) {
		ConexionServices conexion = new ConexionServices();
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
		StringBuilder  url = new StringBuilder();
		Connection conn = null;
		try {
			
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability","o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");

			DriverManager.registerDriver(new OracleDriver());
			url.append("jdbc:oracle:thin:@");
			url.append(sidDataBase != null ? sidDataBase.toUpperCase() : "");
			url.append(".UIO");
			DriverManager.setLoginTimeout(100);
			conn = DriverManager.getConnection(url.toString(),usuario,clave);
			
		} catch (Exception ex) {
			log.error(ex.toString() + " FALLA COMEXION A SID:  " + sidDataBase+ " URL: " + url, ex);
		}
		return conn;
	}
	
	/*public Connection obtenerConexion2(String sidDataBase, String usuario,
			String clave) throws NamingException, Exception {
		StringBuilder  url = new StringBuilder();
		Connection conn = null;
		try {
			
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability","o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");

			DriverManager.registerDriver(new OracleDriver());
			url.append("jdbc:oracle:thin:@");
			url.append(sidDataBase != null ? sidDataBase.toUpperCase() : "");
			url.append(".UIO:1521:prs6");
			DriverManager.setLoginTimeout(100);
			conn = DriverManager.getConnection(url.toString(),usuario,clave);
			
		} catch (Exception ex) {
			log.error(ex.toString() + " FALLA COMEXION A SID:  " + sidDataBase+ " URL: " + url, ex);
		}
		return conn;
	}*/
	
	
	
}