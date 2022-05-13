
package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.EnvioParametros;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.servicios.Farmacia;

/**
 * @date 24/09/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
public class Puntomatico extends ObtenerNuevaConexion{
	private String resultadoXml=Constantes.respuestaXmlUnica("ERROR");
	private Logger log=Logger.getLogger(this.getClass().getName());
	public Puntomatico(String fechaTransaccion,String codigoLocal,String codigoUsuario,String valor,String tipo,String idTransaccion){
		super(Puntomatico.class);		
		log.info("Puntomatico fechaTransaccion:"+fechaTransaccion+" codigoLocal:"+codigoLocal+" codigoUsuario :"+codigoUsuario
				+" valor :"+valor+" tipo:" +tipo+" idTransaccion:"+idTransaccion);
		
		ArrayList<EnvioParametros> InLista = new ArrayList<EnvioParametros>();
		ArrayList<EnvioParametros> OutLista = new ArrayList<EnvioParametros>();
		ConexionVarianteSid conexionVarianteSid=null;
		try{
			 Farmacia farmacia=new Farmacia();
			 farmacia=new Farmacia(codigoLocal);
			 /*
			 if(!codigoLocal.equals("17"))
			     farmacia=new Farmacia(codigoLocal);
			 else				 
				 farmacia.setDatabaseSid("LEGO_F2.UIO");
			 */
			 conexionVarianteSid=obtenerNuevaConexionVarianteSidSidDataBase(farmacia.getDatabaseSid(),"admrep","arefo07");
		   	 String query="{call FARMACIAS.FA_PR_TRNS_EASYSOFT(?,?,?,?,?,?,?,?)}";
		     // Setea el valor para el parametro de entrada
		   	 InLista.add(getParametro(1, Types.VARCHAR, fechaTransaccion));
		   	 InLista.add(getParametro(2, Types.VARCHAR, codigoLocal));
		   	 InLista.add(getParametro(3, Types.VARCHAR, codigoUsuario));
		   	 InLista.add(getParametro(4, Types.VARCHAR, valor));
		   	 InLista.add(getParametro(5, Types.VARCHAR, tipo));
		   	 InLista.add(getParametro(6, Types.VARCHAR, idTransaccion));
		     // Registra el tipo del valor a retornar
		   	 OutLista.add(getParametro(7, Types.NUMERIC, null));
		   	 OutLista.add(getParametro(8, Types.NUMERIC, null));
		     // Ejecuta y retorna el valor
		   	 CallableStatement comm=conexionVarianteSid.Extraer(query, InLista, OutLista);
		   	if(comm.getObject(7)!=null){
			   	if(comm.getObject(7).toString().equals("1"))
			   	   resultadoXml=Constantes.respuestaXmlUnica("OK");
			   	else
			   		resultadoXml=Constantes.respuestaXmlObjeto("ERROR",comm.getObject(8).toString());
		   	}else
		   		resultadoXml=Constantes.respuestaXmlObjeto("ERROR",conexionVarianteSid.getError());
		} catch (SQLException e) {	
			e.printStackTrace();
			log.info(e.getMessage());
		}finally{
			conexionVarianteSid.cerrarConexion();
		}
		
	}
	public String getResultadoXml() {
		return resultadoXml;
	}
	
}
