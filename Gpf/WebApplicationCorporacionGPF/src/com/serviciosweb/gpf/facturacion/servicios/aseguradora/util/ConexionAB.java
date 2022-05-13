package com.serviciosweb.gpf.facturacion.servicios.aseguradora.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.plan.PlanesBean;
import oracle.jdbc.driver.OracleDriver;

public class ConexionAB {
	final static String SIDBASE="jdbc:oracle:thin:@PRODUAT_CLOUD_M2C.UIO:1521:prs6";
	final static String USUARIOBASE="Farmacias";
	final static String CLAVEBASE="farmacias";
    
	private Connection ConexionAB;
	
	public Connection getConexion() {
	     return ConexionAB;
	}
	public void setConexion(Connection conexion) {
	this.ConexionAB = ConexionAB;
	}
	
// conectar for abterminos 
	public  ConexionAB obtConexion() {
		//Connection conn = null;
		//credencialDS.setDatabaseId("PRODUAT_CLOUD_M2C");
		//credencialDS.setUsuario("WEBLINK");
		//credencialDS.setClave("weblink_2013");
		// opcional comentar lo de arriba
		//conn = null;
		// String result =null;
		try {
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
            System.setProperty("oracle.jdbc.thinLogonCapability","o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");
			DriverManager.registerDriver(new OracleDriver());
			DriverManager.setLoginTimeout(100);
			
			//String url=    "jdbc:oracle:thin:@PRODUAT_CLOUD_M2C.UIO:1521:prs6";
			
			//comenta para web service de aseguradoras
		    String url = "jdbc:oracle:thin:@10.100.2.5:1521:PRS6"; // para el addresbook PRODDEV_CLOUD_M2C.UIO
	       //  String url = "jdbc:oracle:thin:@10.100.3.20:1521:prs6"; // para produat con este funciona servicio web
			
			//String url= "jdbc:oracle:thin:@PRODDEV_CLOUD_M2C.UIO:1521:prs6";
			// conn = DriverManager.getConnection(SIDBASE,USUARIOBASE,CLAVEBASE);
			
			//comentar para web service de aseguradoras
		ConexionAB =DriverManager.getConnection(url,"farmacias","farmacias");
		// ConexionAB =DriverManager.getConnection(url,"WEBLINK","weblink_2013");
			
			
			
			if (ConexionAB != null) {
				System.out.println("Conexion exitosa");
				} else {
				System.out.println("Conexion fallida");
				}
				} catch (Exception e) {
				     e.printStackTrace();
				}
				return this;
				}
	
}
