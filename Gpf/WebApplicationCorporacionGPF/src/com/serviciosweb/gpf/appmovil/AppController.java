package com.serviciosweb.gpf.appmovil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;

public class AppController {
	//private Integer dbConexionDesa = 44;//<-Solo para prueba
     private Integer dbConexionDesa = 1;//<-Para Producción
	private String[] error={"ERROR",null};
	
	public AppController() {

	}

	public String returnVerificarCompra(String randomCode){
		   ConexionVarianteSid con=null;
		   try {
			   con=new ConexionVarianteSid(dbConexionDesa);
			   //Se consultarán las compras del día y que la fecha que se caduca el token sea menor a la fecha y hora actual
			   String query ="select * from WEBLINK.WB_BILLETERA_MOVIL where codigo_generado = ? and TO_CHAR(FECHA_GENERACION, 'yyyy-mm-dd') = TO_CHAR(sysdate, 'yyyy-mm-dd') and FECHA_VENCIMIENTO >= sysdate";
			   PreparedStatement ps =con.getConn().prepareStatement(query);
			   ps.setString(1, randomCode);
			   ResultSet rs = ps.executeQuery();
			   rs.next();
			   String cod_empleado = rs.getString("cod_empleado").toString();
			   String confirmo = rs.getString("confirmo").toString();
			   con.cerrarConexion();
			   rs.close();
			   //return cod_empleado;
			   if("S".equals(confirmo)){
				   System.out.println("Verific Random Code using method: /bmverific/" + randomCode + " ; return: confirmo=" + confirmo);
				   return Constantes.respuestaXmlObjeto("ERROR","El código ya fue usado con anterioridad y Confirmó la solicitud de pago, favor solicitar que genere un nuevo codigo.");
				   
			   }else if("R".equals(confirmo)){
				   System.out.println("Verific Random Code using method: /bmverific/" + randomCode + " ; return: confirmo=" + confirmo);
				   return Constantes.respuestaXmlObjeto("ERROR","El código ya fue usado con anterioridad y Rechazo la solicitud de pago, favor solicitar que genere un nuevo codigo.");
				   
			   }else{
				   System.out.println("Verific Random Code using method: /bmverific/" + randomCode + " ; return: cod_empleado=" + cod_empleado);
				   return Constantes.respuestaXmlObjeto("OK",cod_empleado);
			   }
			   
			} catch (SQLException e) {
				error[1] = e.getMessage();
				System.out.println(e.getMessage());
			} catch (Exception e) {
				error[1] = e.getMessage();
				System.out.println(e.getMessage());
			}
		   con.cerrarConexion();
		   //return Constantes.serializarJSON(error);
		   return Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS");
	}
	
	public String returnEstadoCompra(String codusuario, String randomCode, String valor, String documentoventa, String farmacia, String formapago){
		   ConexionVarianteSid con=null;
		   try {
			   con=new ConexionVarianteSid(dbConexionDesa);
			   String query= null;
			   ResultSet rs = null;
			   if(valor.length()>1){ //Para Agregar el Valor
				   query = "update WEBLINK.WB_BILLETERA_MOVIL set VALOR = ? , DOCUMENTO_VENTA = ?, FARMACIA = ?, FORMA_PAGO = ? where COD_EMPLEADO = ? and CODIGO_GENERADO = ?";
				   PreparedStatement ps =con.getConn().prepareStatement(query);
				   ps.setString(1, valor);
				   ps.setInt(2, Integer.parseInt(documentoventa));
				   ps.setInt(3, Integer.parseInt(farmacia));
				   ps.setString(4, formapago);
				   ps.setString(5, codusuario);
				   ps.setString(6, randomCode);
				   if(ps.executeUpdate() > 0){
					   
					   //En caso que sea pago en efectivo le enviará un mensaje push
					   if("E".equals(formapago.toUpperCase())){
						   query = "update WEBLINK.WB_BILLETERA_MOVIL set CONFIRMO = 'S' where COD_EMPLEADO = ? and CODIGO_GENERADO = ?";
						   ps =con.getConn().prepareStatement(query);
						   ps.setString(1, codusuario);
						   ps.setString(2, randomCode);
						   int r = ps.executeUpdate();
						   System.out.println("Compra realizada en efectivo, estado: " + r);
					   }
					   
					   query = "select CONFIRMO from WEBLINK.WB_BILLETERA_MOVIL where COD_EMPLEADO = ? and CODIGO_GENERADO = ?";
					   ps =con.getConn().prepareStatement(query);
					   ps.setString(1, codusuario);
					   ps.setString(2, randomCode);
					   rs = ps.executeQuery();
				   }
				   
			   }else{//Para conocer si se confirmo la compra, en el Form de facturación Javi envía 1 para comprobar.
				   query = "select CASE WHEN SYSDATE >= FECHA_VENCIMIENTO THEN 'R' ELSE CONFIRMO END CONFIRMO from WEBLINK.WB_BILLETERA_MOVIL where COD_EMPLEADO = ? and CODIGO_GENERADO = ?";
				   PreparedStatement ps =con.getConn().prepareStatement(query);
				   ps.setString(1, codusuario);
				   ps.setString(2, randomCode);
				   rs = ps.executeQuery();
			   }
			   rs.next();
			   String confirmo =rs.getString(1).toString();
			   con.cerrarConexion();
			   rs.close();
			   //return confirmo;
			   System.out.println("Set or Verific purchase using method: /bmcompra/" + codusuario + "/"+randomCode+"/"+valor+" ; return: " + confirmo);
			   return Constantes.respuestaXmlObjeto("OK",confirmo);
			} catch (SQLException e) {
				error[1] = e.getMessage();
				System.out.println(e.getMessage());
			} catch (Exception e) {
				error[1] = e.getMessage();
				System.out.println(e.getMessage());
			}
		   con.cerrarConexion();
		   //return Constantes.serializarJSON(error);
		   return Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS");
	}


}
