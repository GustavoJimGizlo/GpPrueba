package com.gpf.serviciosweb.facturacion.farmacias;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdFarmaciasArticuloInterBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class ConsultaArticuloInterCompany extends ObtenerNuevaConexion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(this.getClass().getName());
	String resultadoXml = Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA ESTA CONSULTA");

	public ConsultaArticuloInterCompany(String item, String pdv, String sid, String tipo) {
		super(ConsultaLocalesCercanos.class);

		this.getConsultaArticuloInterCompany(Integer.parseInt(item), Integer.parseInt(pdv), sid, tipo);
	}

	public List<AdFarmaciasArticuloInterBean> getConsultaArticuloInterCompany(int item, int pdv, String sid,
			String tipo) {
		XStream xstream = new XStream(new StaxDriver());
		CallableStatement cs = null;

		if ("P".equals(tipo)) {

			if (!sid.trim().toUpperCase().contains(".UIO"))
				sid = sid + ".UIO";
		}

		if ("T".equals(tipo)) {
			if (!sid.trim().toUpperCase().contains(".UIO"))
				sid = sid + ".UIO";
		}

		if ("D".equals(tipo)) {
			if (!sid.trim().toUpperCase().contains(".UIO")) {
				sid = sid + ".UIO";
			}
		}

		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSidSidDataBase(sid);

		String queryBusqueda = "SELECT  pia.item CODIGO,pia.activo AS activo,round(to_number(gravamen) / 100, 2) AS gravamen,pia.pvp_sin_iva AS pvp_sin_iva, "
				+ " pia.pvp_con_iva AS pvp_con_iva,pia.comisariato_sin_iva AS com_sin_iva,pia.comisariato_con_iva AS com_con_iva,pia.costo_compra AS costo_compra, "
				+ " pia.costo AS costo,pia.unidad_venta AS unidad_venta,p.nombre || ' ' || i.presentacion AS presentacion,i.psicotropico AS psicotropico "
				+ " FROM pr_items_autorizados pia,pr_items i,pr_productos p "
				+ " WHERE i.producto = p.codigo AND pia.farmacia = " + pdv + " AND pia.item = i.codigo AND pia.item = "
				+ item + " ";

		ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);

		ResultSetMapper<AdFarmaciasArticuloInterBean> resultSetMapper = new ResultSetMapper<AdFarmaciasArticuloInterBean>();

		List<AdFarmaciasArticuloInterBean> listadoAdFarmaciasArticulo = resultSetMapper.mapRersultSetToObject(rs,
				AdFarmaciasArticuloInterBean.class);

		conexionVarianteSid.cerrarConexion(rs);

		xstream.alias("AdFarmaciasArticuloInter", AdFarmaciasArticuloInterBean.class);

		resultadoXml = xstream.toXML(listadoAdFarmaciasArticulo);

		return listadoAdFarmaciasArticulo;

	}

	public String getResultadoXml() {
		return Constantes.toUTF8(resultadoXml);
	}

}
