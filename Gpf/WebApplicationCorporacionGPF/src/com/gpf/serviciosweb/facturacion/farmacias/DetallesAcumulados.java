
package com.gpf.serviciosweb.facturacion.farmacias;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DetallesAcumuladosBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @date 27/06/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */

public class DetallesAcumulados extends ObtenerNuevaConexion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS";

	public DetallesAcumulados(String item, String persona, String promocion,String empresa) {
		super(DetallesAcumulados.class);
		XStream xstream = new XStream(new StaxDriver());
		ResultSetMapper<DetallesAcumuladosBean> resultSetMapper=new ResultSetMapper<DetallesAcumuladosBean>();
		ConexionVarianteSid con=obtenerNuevaConexionVarianteSid(empresa);
		if(!con.isValid())
			resultadoXml="FALLA LA CONEXION A LA BDD";
		String querySql="select ubicacion,cajas_residuo, cajas_acumuladas, numero_veces_bonifica, to_char(to_date(fecha_acumula_mensual,'DD-MM-YYYY')) as  fecha_acumula_mensual "+ 
                " from fa_detalles_acumulados where persona="+persona+" and item="+item+" and promocion="+promocion;
		ResultSet rs = con.consulta(querySql);
		
		List<DetallesAcumuladosBean> listDetallesAcumuladosBean=resultSetMapper.mapRersultSetToObject(rs, DetallesAcumuladosBean.class);
		xstream.alias("DetallesAcumuladosBean", DetallesAcumuladosBean.class);
		if(listDetallesAcumuladosBean!=null)
		  resultadoXml=xstream.toXML(listDetallesAcumuladosBean);

		con.cerrarConexion(rs);		
	}

	public String getResultadoXml() {
		return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
	}

	
}
