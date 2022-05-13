
package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;
import java.util.List;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.aseguradoraAbf.AseguradoraAbf;
import com.serviciosweb.gpf.facturacion.farmacias.bean.CrClubPersona;
import com.serviciosweb.gpf.facturacion.farmacias.bean.RespuestaBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @date 03/09/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
public class ClubPersona extends ObtenerNuevaConexion {
	private String resultadoXml = "NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS";
	private String query = "";
	private final static String PARAMETRO_BDD = "1";

	private XStream xstream = new XStream(new StaxDriver());

	public ClubPersona() {
		super(ClubPersona.class);
	}

	public String getClubPersona(String persona, String club) {
		// XStream xstream = new XStream(new StaxDriver());
		ConexionVarianteSid con = obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
		ResultSetMapper<CrClubPersona> resultSetMapper = new ResultSetMapper<CrClubPersona>();
		this.query = "select * from cr_club_personas where persona_afiliada=" + persona
				+ " and  NVL(cp_num3,0)=0  AND CLUB=" + club;
		ResultSet rs = con.consulta(this.query);
		List<CrClubPersona> listDatosCentroCostos = resultSetMapper.mapRersultSetToObject(rs, CrClubPersona.class);

		con.cerrarConexion(rs);

		if (listDatosCentroCostos != null) {
			// this.resultadoXml=xstream.toXML(listDatosCentroCostos);
			// this.resultadoXml=this.resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.CrClubPersona",
			// "CrClubPersona");
			return Constantes.respuestaXmlObjeto("OK", "S");
		} else
			return Constantes.respuestaXmlObjeto("ERROR", "N");
	}

	public String setClubPersona(String persona, String club) {
		ConexionVarianteSid con = obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
		this.query = "UPDATE cr_club_personas SET CP_NUM3 = 1  where persona_afiliada=" + persona + " AND CLUB=" + club;
		Integer registrosActualizados = con.updateQuery(this.query);
		con.cerrarConexion();
		if (registrosActualizados > 0) {
			this.resultadoXml = xstream.toXML("REGISTROS ACTUALIZADOS " + registrosActualizados);
			return Constantes.respuestaXmlObjeto("OK", this.resultadoXml);
		} else
			return Constantes.respuestaXmlObjeto("ERROR", "NO SE ACTUALIZO NINGU REGISTRO");

	}

	public ClubPersona(String codigoCliente, String codigoBono, String cliente) {
		super(ClubPersona.class);
		// ConexionVarianteSid con=obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
		// ConexionVarianteSid
		// con=obtenerNuevaConexionVarianteSidSidDataBase(2,"farmacias","farmacias");

		ConexionVarianteSid con = obtenerNuevaConexionVarianteSidMaster("1");
		RespuestaBean repuestaEnvio = new RespuestaBean();

		this.query = " select count(*) num  " + " from fa_cupones_bc bc" + " where bc.id_cliente = " + codigoCliente
				+ " and bc.codigo_bono= " + codigoBono + " and bc.activo = 'S'"
				+ " and trunc(bc.fecha_vigencia) >= trunc(sysdate)";
		ResultSet rs = con.consulta(this.query);
		int num = 0;
		while (con.siguiente(rs)) {
			num = con.getInt(rs, "num");
		}
		con.cerrarConexion(rs);
		if (num > 0) {
			repuestaEnvio.setActivo("S");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		} else {
			repuestaEnvio.setActivo("N");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		}

		xstream.alias("Aseguradora", RespuestaBean.class);
		this.resultadoXml = xstream.toXML(repuestaEnvio);
		this.resultadoXml = Constantes.respuestaXmlObjeto("OK", this.resultadoXml);
		resultadoXml = xstream.toXML(repuestaEnvio);

	}

	public ClubPersona(String codigoBono, String identificacion) {
		super(ClubPersona.class);
		// ConexionVarianteSid con=obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
		ConexionVarianteSid con = obtenerNuevaConexionVarianteSidMaster("1");
		RespuestaBean repuestaEnvio = new RespuestaBean();
		this.query = " select count(*) num" + " from fa_clientes_bc cl" + " where cl.codigo_bono = " + codigoBono
				+ " and cl.identificacion = '" + identificacion + "'" + " and cl.activo = 'S'";
		ResultSet rs = con.consulta(this.query);
		int num = 0;
		while (con.siguiente(rs)) {
			num = con.getInt(rs, "num");
		}
		con.cerrarConexion(rs);
		if (num > 0) {
			repuestaEnvio.setActivo("S");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		} else {
			repuestaEnvio.setActivo("N");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		}

		xstream.alias("Aseguradora", RespuestaBean.class);
		this.resultadoXml = xstream.toXML(repuestaEnvio);
		this.resultadoXml = Constantes.respuestaXmlObjeto("OK", this.resultadoXml);
		resultadoXml = xstream.toXML(repuestaEnvio);

	}

	public ClubPersona(String codigoCliente, String codigoBono, String codigoBarra, String actualizar) {
		super(ClubPersona.class);
		ConexionVarianteSid con = obtenerNuevaConexionVarianteSidMaster("1");
		RespuestaBean repuestaEnvio = new RespuestaBean();
		this.query = " update" + " fa_cupones_bc bc" + " set bc.activo = 'N'" + " where bc.id_cliente =" + codigoCliente
				+ " and bc.codigo_bono=" + codigoBono + " and bc.codigo_barra =" + codigoBarra + " and bc.activo = 'S'";
		Integer registrosActualizados = con.updateQuery(this.query);
		con.cerrarConexion();

		if (registrosActualizados > 0) {
			repuestaEnvio.setActivo("S");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		} else {
			repuestaEnvio.setActivo("N");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		}

		xstream.alias("Aseguradora", RespuestaBean.class);
		this.resultadoXml = xstream.toXML(repuestaEnvio);
		this.resultadoXml = Constantes.respuestaXmlObjeto("OK", this.resultadoXml);
		resultadoXml = xstream.toXML(repuestaEnvio);

	}

	public String getResultadoXml() {
		if (resultadoXml != null)
			return Constantes.respuestaXmlObjeto("OK", resultadoXml);
		else
			return Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA ESTA CONSULTA");
	}

}
