
package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

/**
 * @date 15/10/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
public class AutenticacionWbUsuarios extends ObtenerNuevaConexion{
	String resultadoXml=Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS");
	public AutenticacionWbUsuarios(String usuario,String clave){
	  super(AutenticacionWbUsuarios.class);
	  String query="SELECT FK_CODIGO  FROM WB_USUARIOS WHERE USUARIO=? AND CLAVE=Pckwb_Crypto.decrypt (clave, '§°¾Ì¯Ô´±')=?";
	  ConexionVarianteSid con=obtenerNuevaConexionVarianteSid("30");
	  try {
			PreparedStatement ps =con.getConn().prepareStatement(query);
			ps.setString(1,usuario);
			ps.setString(2,clave);
			ResultSet rs = ps.executeQuery();
			while(con.siguiente(rs))
				resultadoXml=Constantes.respuestaXmlObjeto("OK",con.getString(rs, "FK_CODIGO"));
			con.cerrarConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}	  
    }
	public String getResultadoXml() {
		return resultadoXml;
	}
}
