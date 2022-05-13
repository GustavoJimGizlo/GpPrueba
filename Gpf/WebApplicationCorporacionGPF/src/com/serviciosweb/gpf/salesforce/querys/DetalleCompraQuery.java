package com.serviciosweb.gpf.salesforce.querys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.serviciosweb.gpf.salesforce.util.ConexionVarianteSid;
import com.serviciosweb.gpf.salesforce.util.Utilities;
import com.serviciosweb.gpf.salesforce.xml.Articulo;
import com.serviciosweb.gpf.salesforce.xml.DetalleCompra;

public class DetalleCompraQuery {

	private DetalleCompra resultado = new DetalleCompra();

	static {
		new Utilities();
	}

	protected static final Logger log = Logger.getLogger("SalesForceServices."
			+ DetalleCompraQuery.class.getName());
	private ConexionVarianteSid con;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public DetalleCompraQuery(String databaseNickName) {
		this.con = new Utilities().conexionFileExtern(databaseNickName);
	}

	public DetalleCompraQuery(String databaseNickName, String service) {
		this.con = new Utilities()
				.conexionFileExtern(databaseNickName, service);
	}

	public DetalleCompraQuery(String farmacia ,Integer ordenCompra) {
		this("web", "getOrder");

		resultado.setArticulo(getArticulo(farmacia,ordenCompra));

	}

	public List<Articulo> getArticulo(String farmacia ,Integer ordenCompra) {
		List<Articulo> articulo = new ArrayList<Articulo>();

		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryArticulo.sql", "salesforce/sql");
			this.ps = this.con.getConn().prepareStatement(queryBusqueda);
			this.ps.setInt(1, Integer.parseInt(farmacia));
			this.ps.setInt(2, ordenCompra);
			
			this.rs = this.ps.executeQuery();

			while (rs.next()) {
				articulo.add(setArticulo(this.rs));
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
		return articulo;
	}

	public Articulo setArticulo(ResultSet set) {
		Articulo articulo = new Articulo();

		try {

			articulo.setItem(set.getString(1));
			articulo.setNombre(set.getString(2));
			articulo.setCantidad(set.getString(3));
			articulo.setPvp(set.getString(4));
			articulo.setPvpComisariato(set.getString(5));
			articulo.setPorcentajeImpuesto(set.getString(6));
			articulo.setValorImpuesto(set.getString(7));
			articulo.setCosto(set.getString(8));
			articulo.setVentaSinIva(set.getString(9));
			articulo.setIva(set.getString(10));
			articulo.setVenta(set.getString(11));
			articulo.setUnidadVenta(set.getString(12));
			articulo.setSecccionContable(set.getString(13));

		} catch (SQLException e) {
			log.warning("Error, accion: leer \n"
					+ new Utilities().formatErrorSQL(e));
		}

		return articulo;

	}

	public DetalleCompra getResultado() {
		return resultado;
	}

	public void setResultado(DetalleCompra resultado) {
		this.resultado = resultado;
	}

}
