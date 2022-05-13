package com.serviciosweb.gpf.salesforce.querys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.serviciosweb.gpf.salesforce.util.ConexionVarianteSid;
import com.serviciosweb.gpf.salesforce.util.Utilities;
import com.serviciosweb.gpf.salesforce.xml.DireccionEnvio;
import com.serviciosweb.gpf.salesforce.xml.Domicilio;
import com.serviciosweb.gpf.salesforce.xml.FormasPago;
import com.serviciosweb.gpf.salesforce.xml.MedioDescuento;
import com.serviciosweb.gpf.salesforce.xml.PlaceToPay;
import com.serviciosweb.gpf.salesforce.xml.Respuesta;
import com.serviciosweb.gpf.salesforce.xml.Sumario;
import com.serviciosweb.gpf.salesforce.xml.Transaccion;

public class RespuestaQuery {
	
	private Transaccion resultado = new Transaccion();

	//private Respuesta resultado = new Respuesta();

	static {
		new Utilities();
	}

	protected static final Logger log = Logger.getLogger("SalesForceServices."
			+ RespuestaQuery.class.getName());
	private ConexionVarianteSid con;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public RespuestaQuery(String databaseNickName) {
		this.con = new Utilities().conexionFileExtern(databaseNickName);
	}

	public RespuestaQuery(String databaseNickName, String service) {
		this.con = new Utilities()
				.conexionFileExtern(databaseNickName, service);
	}

	public RespuestaQuery(String farmacia ,Integer ordenCompra) {
		this("web", "getOrder");
		
		this.resultado.setMensaje("Transaccion Exitosa");
		this.resultado.setHttpStatus("104");
		
		Respuesta respuesta = new Respuesta();
		
		respuesta.setSumario(getSumario(ordenCompra));
 
		if( respuesta.getSumario().getTotalVenta() == null ){
			this.resultado.setMensaje("Error: Numero de orden incorrecta");
			this.resultado.setHttpStatus("504");
		}else if(!"N".equals(respuesta.getSumario().getEstado()) && !"P".equals(respuesta.getSumario().getEstado())  ){
			this.resultado.setMensaje("Error: El numero de orden " +ordenCompra+ " ya fue gestionada" );
			this.resultado.setHttpStatus("505");
		}else {
			respuesta.setDetalleFactura(new DetalleFacturaQuery(ordenCompra).getResultado());
			respuesta.setDetalleCompra(new DetalleCompraQuery(farmacia,ordenCompra).getResultado());
			respuesta.setMedioDescuento(getMedioDescuento(ordenCompra));
			respuesta.setFormasPago(getFormasPago(ordenCompra));
			respuesta.setPlaceToPay(getPlaceToPay(ordenCompra));
			respuesta.setDomicilio(getDomicilio(ordenCompra));
			respuesta.setDireccionEnvio(getDireccionEnvio(ordenCompra));
			this.resultado.setRespuesta(respuesta);					
		}
	}

	public MedioDescuento getMedioDescuento(Integer ordenCompra) {
		MedioDescuento medioDescuento = new MedioDescuento();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryMedioDescuento.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {
				medioDescuento.setCodigo(rs.getString(1));
				medioDescuento.setTarjetaDescuento(rs.getString(2));
			}

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return medioDescuento;
	}

	public FormasPago getFormasPago(Integer ordenCompra) {
		FormasPago formasPago = new FormasPago();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryFormasPago.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {
				formasPago.setCodigo(rs.getString(1));
				formasPago.setValor(rs.getString(2));
			}

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return formasPago;
	}

	public PlaceToPay getPlaceToPay(Integer ordenCompra) {
		PlaceToPay placeToPay = null;
		

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryPlaceToPay.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {
				placeToPay= new PlaceToPay();
				placeToPay.setNumeroTarjeta(rs.getString(1));
				placeToPay.setNumeroAutorizacion(rs.getString(2));
				placeToPay.setTarjetaHabiente(rs.getString(3));
				placeToPay.setPlanCredito(rs.getString(4));
				placeToPay.setNumeroAutBoletin("N".equals(rs.getString(5))?"":rs.getString(5));
			}

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return placeToPay;
	}

	public Domicilio getDomicilio(Integer ordenCompra) {
		Domicilio domicilio = new Domicilio();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryDomicilio.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {

				domicilio.setVentaNeta(rs.getString(1));
				domicilio.setIva(rs.getString(2));
				domicilio.setTax(rs.getString(3));
				domicilio.setVenta(rs.getString(4));
			}

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return domicilio;
	}

	public Sumario getSumario(Integer ordenCompra) {
		Sumario sumario = new Sumario();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"querySumario.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {

				sumario.setBase0(rs.getString(1));
				sumario.setBase12(rs.getString(2));
				sumario.setSubTotal(rs.getString(3));
				sumario.setIva0(rs.getString(4));
				sumario.setIva12(rs.getString(5));
				sumario.setTotalVenta(rs.getString(6));
				sumario.setEstado(rs.getString(7));
			}

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return sumario;
	}
	
	
	public DireccionEnvio getDireccionEnvio(Integer ordenCompra) {
		DireccionEnvio direccionEnvio = new DireccionEnvio();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryDireccionEnvio.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {
				
				direccionEnvio.setAddress1(rs.getString(1));
				direccionEnvio.setAddress2(rs.getString(2));
				direccionEnvio.setDirCompleta(rs.getString(3));
				direccionEnvio.setLatitude(rs.getString(4));
				direccionEnvio.setLongitude(rs.getString(5));
				direccionEnvio.setPhone(rs.getString(6));

			}

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return direccionEnvio;
	}

	public Transaccion getResultado() {
		return resultado;
	}

	public void setResultado(Transaccion resultado) {
		this.resultado = resultado;
	}

	/*public Respuesta getResultado() {
		return resultado;
	}

	public void setResultado(Respuesta resultado) {
		this.resultado = resultado;
	}*/
	
	

}
