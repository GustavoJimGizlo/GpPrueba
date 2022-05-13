

package modelo;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class conexion {
	
	private Connection conexion;

	public Connection getConexion() {
	     return conexion;
	}

	public void setConexion(Connection conexion) {
	this.conexion = conexion;
	}
	
	public conexion conectar() {
		try {
		Class.forName("oracle.jdbc.OracleDriver");
		String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:XE";

		conexion = DriverManager.getConnection(BaseDeDatos,"SYSTEM","12345");
		
		
		if (conexion != null) {
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
