package com.serviciosweb.gpf.facturacion.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import com.gpf.postg.pedidos.util.Constantes;

/**
 * @author Antonio Encarnacion R
 * 
 *         Fecha: 01/10/2020
 * 
 *         AER_REQ4913 Generacion de claves Notas de Credito
 * 
 */
public class AutorizacionClaveNotasCreditoControlador {

	//private String dbConexion = "TEST";// ambiente de PRUEBAS
    private String dbConexion = "PROD";//ambiente de PRODUCCION
	private Integer idEmpresa = 1;
	private Connection conn;

	public String claveAutorizada(String codigoPdv, String numDocumento, String tipoTrxNC, String tipoAutorizadorTrx,
			String clave) {

		String existeClave;
		try {
			conexionSid(idEmpresa, dbConexion);// obtener la conexion a la empresa 1 ambiente PROD o PRUEBAS
			// Query para consultar la clave de autorizacion para procesar la nota de
			// credito
			String query = "select count(1) totClave " + "from farmacias.fa_claves_notas_credito x "
					+ "where x.codigo_pdv = ? " + "and x.tipo_transaccion_nc = ? " + "and x.num_documento = ? "
					+ "and x.tipo_autorizador = ? " + "and x.clave = ? "
					+ "and sysdate between x.fecha_ini and x.fecha_fin";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(codigoPdv));
			ps.setString(2, tipoTrxNC);
			ps.setLong(3, Long.parseLong(numDocumento));
			ps.setString(4, tipoAutorizadorTrx);
			ps.setInt(5, Integer.parseInt(clave));
			ResultSet rs = ps.executeQuery();
			rs.next();

			int contClave = rs.getInt("totClave");
			cerrarConexion();
			rs.close();

			if (contClave == 0) {
				existeClave = "N";
			} else if (contClave == 1) {
				existeClave = "S";
			} else {
				existeClave = "N";
				return Constantes.respuestaXmlObjeto("Error",
						"Existe mas de una clave de autorizacion para la transaccion");
			}

			return Constantes.respuestaXmlObjeto("OK", existeClave);

		} catch (SQLException e) {
			e.getMessage();
			// e.printStackTrace();
			// System.out.println("aaaa " + e.getMessage());
		} catch (Exception e) {
			e.getMessage();
			// System.out.println("bbbb " + e.getMessage());
		}
		cerrarConexion();
		return Constantes.respuestaXmlObjeto("Error", "No existen datos para los parametros enviados");
	}

	public void conexionSid(Integer idEmpresa, String ambiente) throws NamingException, Exception {
		String url = null;
		String usuario = "WEBLINK";
		String pass = "weblink_2013";
		try {
			System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.net.tns_admin", "/data/oracle");
			if (idEmpresa == 1) {
				if (ambiente == "PROD")
					url = "jdbc:oracle:thin:" + usuario + "/" + pass + "@PROD.UIO";
				else
					url = "jdbc:oracle:thin:" + usuario + "/" + pass + "@PRODUAT_CLOUD_M2C.UIO";
			}
			DriverManager.setLoginTimeout(30);
			conn = DriverManager.getConnection(url);
		} catch (SQLException ex) {
			ex.printStackTrace();
			conn = null;
		}
	}

	public void cerrarConexion() {
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		conn = null;
	}

}
