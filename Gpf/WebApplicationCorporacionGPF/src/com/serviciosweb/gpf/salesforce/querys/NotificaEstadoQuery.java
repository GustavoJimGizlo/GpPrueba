package com.serviciosweb.gpf.salesforce.querys;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.serviciosweb.gpf.salesforce.util.ConexionVarianteSid;
import com.serviciosweb.gpf.salesforce.util.Utilities;
import com.serviciosweb.gpf.salesforce.xml.Respuesta;
import com.serviciosweb.gpf.salesforce.xml.Transaccion;

public class NotificaEstadoQuery {

	private Transaccion resultado = new Transaccion();

	static {
		new Utilities();
	}

	protected static final Logger log = Logger.getLogger("SalesForceServices."
			+ NotificaEstadoQuery.class.getName());
	private ConexionVarianteSid con;
	private PreparedStatement ps = null;


	public NotificaEstadoQuery(String databaseNickName) {
		this.con = new Utilities().conexionFileExtern(databaseNickName);
	}

	public NotificaEstadoQuery(String databaseNickName, String service) {
		this.con = new Utilities()
				.conexionFileExtern(databaseNickName, service);
	}

	public NotificaEstadoQuery(String ordenCompra,
			String codPdv,
			String estado,
			 String docVenta) {
		this("web", "getOrder");

		Respuesta respuesta = new Respuesta();

		
		 Integer count = updateNotificacionEstado( ordenCompra, codPdv, estado,docVenta);
		 if(count>0){
			 resultado.setHttpStatus("104");
				resultado.setMensaje("La transaccion se ejecuto exitosamente");
				 respuesta = new Respuesta();
				respuesta.setEstado("OK");
				resultado.setRespuesta(respuesta);
		 }else{
			 resultado.setHttpStatus("505");
				resultado.setMensaje("Eror al actulizar los datos exitosamente");
				 respuesta = new Respuesta();
				respuesta.setEstado("ERROR");
				resultado.setRespuesta(respuesta);
		 }

	}

	public Integer updateNotificacionEstado(String ordenCompra,
			String codPdv,
			String estado,
			 String docVenta) {
		Integer respuesta = 0;

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"updateNotificacionEstado.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			
			
			this.ps.setString(1, codPdv);
			this.ps.setString(2, estado);
			this.ps.setString(3, docVenta);
			this.ps.setString(4, ordenCompra);
			
			respuesta = this.ps.executeUpdate();
			

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		}
		return respuesta;
	}


	public Transaccion getResultado() {
		return resultado;
	}

	public void setResultado(Transaccion resultado) {
		this.resultado = resultado;
	}


	 
}
