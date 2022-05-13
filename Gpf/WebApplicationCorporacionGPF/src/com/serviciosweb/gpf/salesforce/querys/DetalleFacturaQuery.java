package com.serviciosweb.gpf.salesforce.querys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.serviciosweb.gpf.salesforce.util.ConexionVarianteSid;
import com.serviciosweb.gpf.salesforce.util.Utilities;
import com.serviciosweb.gpf.salesforce.xml.Contactos;
import com.serviciosweb.gpf.salesforce.xml.DetalleFactura;
import com.serviciosweb.gpf.salesforce.xml.Direccion;
import com.serviciosweb.gpf.salesforce.xml.Persona;

public class DetalleFacturaQuery {

	private DetalleFactura resultado = new DetalleFactura();

	static {
		new Utilities();
	}

	protected static final Logger log = Logger.getLogger("SalesForceServices."
			+ DetalleFacturaQuery.class.getName());
	private ConexionVarianteSid con;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public DetalleFacturaQuery(String databaseNickName) {
		this.con = new Utilities().conexionFileExtern(databaseNickName);
	}

	public DetalleFacturaQuery(String databaseNickName, String service) {
		this.con = new Utilities()
				.conexionFileExtern(databaseNickName, service);
	}

	public DetalleFacturaQuery(Integer ordenCompra) {
		this("web", "getOrder");

		resultado.setPersona(getPersona(ordenCompra));
		resultado.setDireccion(getDireccion(ordenCompra));
		
		resultado.setContactos(getContacto(ordenCompra));
	}

	public Persona getPersona(Integer ordenCompra) {
		Persona persona = new Persona();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryPersona.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {
				persona = setPersona(this.rs);
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
		return persona;
	}

	public Direccion getDireccion(Integer ordenCompra) {
		Direccion direccion = new Direccion();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryDireccion.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {
				direccion = setDireccion(this.rs);
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
		return direccion;
	}

	public Contactos getContacto(Integer ordenCompra) {
		Contactos contacto = new Contactos();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryContactos.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, ordenCompra);
			this.rs = this.ps.executeQuery();

			while (rs.next()) {
				contacto = setContacto(this.rs);
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
		return contacto;
	}

	public Direccion setDireccion(ResultSet set) {
		Direccion direccion = new Direccion();

		try {

			direccion.setCodigo(set.getString(1));
			direccion.setCallePrincipal(set.getString(2));
			direccion.setNumero(set.getString(3));
			direccion.setInterseccion(set.getString(4));
			direccion.setReferencia(set.getString(5));
			direccion.setDirCompleta(set.getString(6));

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		}

		return direccion;

	}

	public Contactos setContacto(ResultSet set) {
		Contactos contacto = new Contactos();

		try {
			contacto.setEmail(set.getString(1));
			contacto.setTelefono(set.getString(2));

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		}

		return contacto;

	}

	public Persona setPersona(ResultSet set) {
		Persona persona = new Persona();

		try {
			persona.setCodigo(set.getString(1));
			persona.setTipoIdentificacion(set.getString(2));
			persona.setIdentificacion(set.getString(3));
			persona.setNombres(set.getString(4));
			persona.setPrimerApellido(set.getString(6));
			persona.setSegundoApellido(set.getString(7));
			resultado.setPdv(set.getString(8));
		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		}

		return persona;

	}

	public DetalleFactura getResultado() {
		return resultado;
	}

	public void setResultado(DetalleFactura resultado) {
		this.resultado = resultado;
	}

}
