/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.farmacias.bean.Information;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * 
 * @author spramirezv
 */
public class ConsultaSistemaSiebel extends ObtenerNuevaConexion {
	private String xmlDato = "";
	private String sSQL = "";
	ResultSet rs = null;
	Information dato = null;
	String resultadoXml = "NO EXISTEN DATOS PARA ESTA CONSULTA";
	Logger log = Logger.getLogger("ConsultaAcuPmc");

	boolean validaClienteEnTabla = false;

	String tipoSistema = "", grupoCliente = "";
	
	Integer empresa =0;

	public ConsultaSistemaSiebel(String customerID, String storeID) {
		super(ConsultaSistemaSiebel.class);
		XStream xstream = new XStream(new StaxDriver());
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSidSidDataBase(1, "weblink", "weblink_2013"); // prod
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSidSidDataBase(44, "weblink", "weblink_2013"); //test
		if (conexionVarianteSid.getConn() == null) {
			resultadoXml = "FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
			return;
		}

		try {
			
			sSQL = " select empresa from ad_farmacias where codigo ="+storeID;
			rs = conexionVarianteSid.consulta(sSQL);
			while (rs.next()) {
				empresa = rs.getInt("empresa");
			}
			
			
			sSQL = " select * " + " from farmacias.FA_CLIENTES_PILOTO"
					+ " where identificacion ='" + customerID + "' "
					+ " and activo ='S'";

			rs = conexionVarianteSid.consulta(sSQL);
			while (rs.next()) {
				validaClienteEnTabla = true;
			}

			sSQL = " select valor" + " from fa_empresas_tipo_farmacias"
					+ " where parametro  = 'TIPO_SISTEMA_FDL'"
					+ " and empresa = "+ empresa;

			rs = conexionVarianteSid.consulta(sSQL);
			while (rs.next()) {
				tipoSistema = rs.getString("valor");
			}

				sSQL = " select valor " + " from fa_empresas_tipo_farmacias"
						+ " where parametro = 'GRUPO_CLIENTE'"
						+ " and empresa = " + empresa;

			rs = conexionVarianteSid.consulta(sSQL);
			while (rs.next()) {
				grupoCliente = rs.getString("valor");
			}

			conexionVarianteSid.cerrarConexion();
			rs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			log.info(ex.getMessage());
		}

		if (tipoSistema != null && "PIONUS".equals(tipoSistema.toUpperCase())
				&& grupoCliente != null
				&& "SI".equals(grupoCliente.toUpperCase())
				&& validaClienteEnTabla == true)
			xmlDato = "pionus";
		else if (tipoSistema != null
				&& "PIONUS".equals(tipoSistema.toUpperCase())
				&& grupoCliente != null
				&& "SI".equals(grupoCliente.toUpperCase())
				&& validaClienteEnTabla == false)
			xmlDato = "pionus";
		else if (tipoSistema != null
				&& "PIONUS".equals(tipoSistema.toUpperCase())
				&& grupoCliente != null
				&& "NO".equals(grupoCliente.toUpperCase())
				&& validaClienteEnTabla)
			xmlDato = "pionus";
		else if (tipoSistema != null
				&& "PIONUS".equals(tipoSistema.toUpperCase())
				&& grupoCliente != null
				&& "NO".equals(grupoCliente.toUpperCase())
				&& validaClienteEnTabla == false)
			xmlDato = "siebel";
		else if (tipoSistema != null
				&& "SIEBEL".equals(tipoSistema.toUpperCase())
				&& grupoCliente != null
				&& "SI".equals(grupoCliente.toUpperCase())
				&& validaClienteEnTabla == true)
			xmlDato = "siebel";
		else if (tipoSistema != null
				&& "SIEBEL".equals(tipoSistema.toUpperCase())
				&& grupoCliente != null
				&& "SI".equals(grupoCliente.toUpperCase())
				&& validaClienteEnTabla == false)
			xmlDato = "pionus";
		else if (tipoSistema != null
				&& "SIEBEL".equals(tipoSistema.toUpperCase())
				&& grupoCliente != null
				&& "NO".equals(grupoCliente.toUpperCase())
				&& validaClienteEnTabla == true)
			xmlDato = "siebel";
		else if (tipoSistema != null
				&& "SIEBEL".equals(tipoSistema.toUpperCase())
				&& grupoCliente != null
				&& "NO".equals(grupoCliente.toUpperCase())
				&& validaClienteEnTabla == false)
			xmlDato = "siebel";

		resultadoXml = xstream.toXML(xmlDato);
		resultadoXml = resultadoXml.replace("string", "systemType");
		resultadoXml = resultadoXml.replace(
				"com.serviciosweb.gpf.facturacion.farmacias.bean.Information",
				"Information");

	}

	public String getResultadoXml() {

		return Constantes.respuestaXmlObjeto("OK", resultadoXml);

	}
}
