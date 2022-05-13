package com.serviciosweb.gpf.facturacion.servicios;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import com.gpf.postg.pedidos.util.ConexionMSSQLServer;
import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.ConversionsTasks;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdFarmaciasBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdFarmaciasImagenBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdServiciosFarmaciasBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdSucursalesBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DatosFarmaciasBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.FarmaciasGeolocalizacionBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.FarmaciasHorariosBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.FarmaciasServiciosBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.FybecaAdminBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.TiposHorariosBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.WfArchivosBean;

public class ParametrizacionKioskoServicios extends ObtenerNuevaConexion{

	public ParametrizacionKioskoServicios() {
		super(ParametrizacionKioskoServicios.class);
	}
	private static String tipoConexion="1";
/********************    FARMACIAS HORARIOS     ********************/
	public String getFarmaciasHorarios(Long codigoLocal){
			String queryBusqueda=" SELECT a.codigo \"codigoFarmaciasHorario\","+
								 	" a.desde \"desde\","+
								 	" a.hasta \"hasta\","+
								 	" a.codigo_tipo_horario \"codigoTipoHorario\","+
								 	" a.codigo_farmacia \"codigoFarmacia\","+
								 	" b.nombre \"nombreTipoHorario\"  "+
								 " FROM ad_farmacias_horarios a, ad_tipos_horarios b "+ 
								 " WHERE a.codigo_tipo_horario=b.codigo "+
								 " AND a.codigo_farmacia="+codigoLocal+
								 " AND b.activo='S'";
			List<FarmaciasHorariosBean> listInfoProd = new ArrayList<FarmaciasHorariosBean>();
			try{
				//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
				ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
				ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
				FarmaciasHorariosBean InfoProd = null;
				
	
				while(conexionVarianteSid.siguiente(rs)){
					InfoProd = new FarmaciasHorariosBean();
					InfoProd.setCodigoFarmaciasHorario(conexionVarianteSid.getLong(rs, "codigoFarmaciasHorario"));
					InfoProd.setDesde(conexionVarianteSid.getString(rs, "desde"));
					InfoProd.setHasta(conexionVarianteSid.getString(rs, "hasta"));
					InfoProd.setCodigoTipoHorario(conexionVarianteSid.getLong(rs, "codigoTipoHorario"));
					InfoProd.setCodigoFarmacia(conexionVarianteSid.getLong(rs, "codigoFarmacia"));
					InfoProd.setNombreTipoHorario(conexionVarianteSid.getString(rs, "nombreTipoHorario"));
					listInfoProd.add(InfoProd);
					
				}
				rs.close();
				conexionVarianteSid.cerrarConexion();
				return ConversionsTasks.serializar(listInfoProd,"cargarFarmaciasHorarios","Carga Horarios","104");
			}catch(SQLException e){
				e.printStackTrace();
				return ConversionsTasks.serializar("ERR","cargarFarmaciasHorarios","Carga Horarios","404");
			}
			/*
			if (listInfoProd.size()>0){
				json= ConversionsTasks.serializar(listInfoProd);
				
				json=json.concat(callback+"([");
				for (InformacionProductoBean gp:listInfoProd){
					count=count+1;
					json=json.concat("{");
					json=json.concat("\"texto\":\""+gp.getTexto()+"\"");
					json=json.concat("}" ); 
					if (listInfoProd.size()!=count){
						json=json.concat(",");
					}
				}
				json=json.concat("]);");
				//json1=json.replace("\"class\":"+"\""+InformacionProductoBean.class.getName()+"\"", "");
				//json=json1.replace("\""+InformacionProductoBean.class.getName()+"\",", "");
				//return callback+"("+json.replace("\"class\":"+"\""+FarmaciasHorariosBean.class.getName()+"\",", "")+");";
				return ConversionsTasks.serializar(listInfoProd);
			}else{
				json=json.concat(callback+"([");
				json=json.concat("]);");
		        return json;
			}*/
		
	}
	
	/********************    CARGA COMBO TIPOS HORARIOS  ***************************/
	public String getTiposHorarios(){
		String queryBusqueda=" SELECT a.codigo \"codigo\",nombre \"nombre\" "+ 
							 " FROM ad_tipos_horarios a "+
							 " WHERE a.activo='S'";
		List<TiposHorariosBean> listInfo = new ArrayList<TiposHorariosBean>();
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			TiposHorariosBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new TiposHorariosBean();
				infoProd.setCodigo(conexionVarianteSid.getLong(rs, "codigo"));
				infoProd.setNombre(conexionVarianteSid.getString(rs, "nombre"));
				
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"cargarTiposHorarios","Carga Tipos Horaios","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","cargarTiposHorarios","Carga Tipos Horaios","404");
		}
	}
	
	/********************    CARGA COMBO LOCALES   ***************************/
	public String getFarmacias(){
		String queryBusqueda=" SELECT a.codigo \"codigo\",nombre \"nombre\" "+ 
							 " FROM Ad_Farmacias a "+
							 " WHERE a.campo3 = 'S' "+
							 " AND a.fma_Autorizacion_Farmaceutica = 'FS' "+  
							 " ORDER BY a.nombre";
		List<AdFarmaciasBean> listInfo = new ArrayList<AdFarmaciasBean>();
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			AdFarmaciasBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new AdFarmaciasBean();
				infoProd.setCodigo(conexionVarianteSid.getLong(rs, "codigo"));
				infoProd.setNombre(conexionVarianteSid.getString(rs, "nombre"));
				
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"AdFarmacias","Carga AdFarmacias","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","AdFarmacias","Carga AdFarmacias","404");
		}
	}
	/*****************************    GEOLOCALIZACION    *****************************/
	public String getGeoLocalizacion(Long codigoLocal){
		String queryBusqueda=" SELECT a.codigo \"codigoGeo\" , "+
				                    " a.longitud \"longitud\", " +
				                    " a.latitud \"latitud\", " +
				                    " a.url1 \"url1\", "+
				                    " a.distancia_kilometros \"distanciaKilometros\", "+
				                    " a.codigo_farmacia \"codigoFarmacia\" "+ 
				             " FROM ad_farmacias_geolocalizacion a "+
				             " WHERE a.codigo_farmacia="+codigoLocal;
		List<FarmaciasGeolocalizacionBean> listInfo = new ArrayList<FarmaciasGeolocalizacionBean>();
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			FarmaciasGeolocalizacionBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new FarmaciasGeolocalizacionBean();
				infoProd.setCodigoGelocalizacion(conexionVarianteSid.getLong(rs, "codigoGeo"));
				infoProd.setLongitud(conexionVarianteSid.getDouble(rs, "longitud"));
				infoProd.setLatitud(conexionVarianteSid.getDouble(rs, "latitud"));
				infoProd.setUrl1(conexionVarianteSid.getString(rs, "url1"));
				infoProd.setCodigoFarmacia(conexionVarianteSid.getLong(rs, "codigoFarmacia"));
				infoProd.setDistanciaKilometros(conexionVarianteSid.getLong(rs, "distanciaKilometros"));
				
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"cargarGeoLocalizacion","Carga Geolocalizacion","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","cargarGeoLocalizacion","Carga Geolocalizacion","404");
		}
	}	
	/*********************    FARMACIAS SERVICIOS    ******************************/
	public String getFarmaciasServicios(Long codigoLocal){
		String queryBusqueda=" SELECT a.codigo \"codigoFarmaciasServicios\", "+
							        " a.codigo_servicio \"codigoServicios\", "+
							        " a.codigo_farmacia \"codigoFarmacia\", "+
							        " b.nombre \"nombreServicio\" "+
							 " FROM ad_farmacias_servicios a, ad_servicios_farmacias b "+
							 " WHERE a.codigo_servicio=b.codigo "+
							 " AND b.activo='S' "+
							 " AND a.CODIGO_FARMACIA="+codigoLocal;
		List<FarmaciasServiciosBean> listInfo = new ArrayList<FarmaciasServiciosBean>();
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			FarmaciasServiciosBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new FarmaciasServiciosBean();
				infoProd.setCodigoFarmaciasServicios(conexionVarianteSid.getLong(rs, "codigoFarmaciasServicios"));
				infoProd.setCodigoServicios(conexionVarianteSid.getLong(rs, "codigoServicios"));
				infoProd.setCodigoFarmacia(conexionVarianteSid.getLong(rs, "codigoFarmacia"));
				infoProd.setNombreServicio(conexionVarianteSid.getString(rs, "nombreServicio"));
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"cargarServicios","Carga Servicios","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","cargarServicios","Carga Servicios","404");
		}
	}		

	
	/*********************    FARMACIAS FOTOS CENTROS COSTO    ******************************/
	public String getFarmaciasFotosCentrosCostos(Long codigoLocal){
		String queryBusqueda ="select b.filename \"nombreImagen\"," +
				                     " A.CODIGO_ARCHIVO \"codigoArchivo\"," +
				                     " A.CODIGO_FARMACIA \"codigoFarmacia\"," +
				                     " A.MOSTRAR \"mostar\" " +
				                     " from AD_FARMACIAS_IMAGEN a , wf_archivos b" +
				                     " where A.CODIGO_ARCHIVO = B.CODIGO" +
				                     " AND A.CODIGO_FARMACIA="+codigoLocal;
		List<AdFarmaciasImagenBean> listInfo = new ArrayList<AdFarmaciasImagenBean>();
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			AdFarmaciasImagenBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new AdFarmaciasImagenBean();
				infoProd.setCodigoArchivo(conexionVarianteSid.getLong(rs, "codigoArchivo"));
				infoProd.setCodigoFarmacia(conexionVarianteSid.getLong(rs, "codigoFarmacia"));
				infoProd.setMostar(conexionVarianteSid.getString(rs, "mostar"));
				infoProd.setNombreImagen(conexionVarianteSid.getString(rs, "nombreImagen"));
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"cargarFotosCentrosCosto","Carga Fotos Centros Costo","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","cargarFotosCentrosCosto","Carga Fotos Centros Costo","404");
		}
	}		
		
	/***************************     BUSCA FARMACIAS HORARIOS   *******************/
	
	public Boolean buscartiposHorarios(String codigoLocal,String codigoTipoHorario){
		Boolean existe=true;
		String queryBusqueda=" SELECT 1 \"existe\" "+
							 " FROM ad_farmacias_horarios a "+
							 " WHERE a.codigo_farmacia="+codigoLocal+
							 " AND a.codigo_tipo_horario="+codigoTipoHorario;
		List<FarmaciasServiciosBean> listInfo = new ArrayList<FarmaciasServiciosBean>();
		
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			FarmaciasServiciosBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				//infoProd = new FarmaciasServiciosBean();
				 existe=false;
				
				//listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return existe;
		}catch(SQLException e){
			e.printStackTrace();
			return existe;
		}
	}		
	/***********************    OBTENER CODIGO MAXIMO FARMACIAS HORARIOS  ***********************/
	
	
	public Integer obtenerMaxAdFarmaciasHorarios(){
		Integer maximo=0;
		String queryBusqueda=" SELECT MAX(codigo) \"maximo\" FROM ad_farmacias_horarios ";
		
		
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			

			while(conexionVarianteSid.siguiente(rs)){
				maximo=conexionVarianteSid.getInt(rs, "maximo");
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return maximo+1;
		}catch(SQLException e){
			e.printStackTrace();
			return 1;
		}
	}		
	/**************************     INSERTAR NUEVO FARMACIAS HORARIOS    *****************************/
	public Boolean insertFarmaciasHorarios(String codigoFarmaciasHorarios,String desde,String hasta,String codigoTipoHorario,String codigoFarmacia) throws SQLException{

		String insert=" INSERT INTO ad_farmacias_horarios (codigo,desde,hasta,codigo_tipo_horario,codigo_farmacia)"+
							 " VALUES("+ codigoFarmaciasHorarios+",'"+desde+"','"+hasta+"',"+codigoTipoHorario+","+codigoFarmacia+")";
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(insert);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}		
	
	/**************************     ELIMINAR FARMACIAS HORARIOS    *****************************/
	public Boolean eliminarFarmaciasHorarios(String codigoTipoHorario,String codigoFarmacia) throws SQLException{

		String eliminar=" DELETE FROM  ad_farmacias_horarios "+
					  " WHERE CODIGO_TIPO_HORARIO="+codigoTipoHorario+
					  " AND CODIGO_FARMACIA="+codigoFarmacia;
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(eliminar);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}
	/***************************     ACTUALIZA  FARMACIAS HORARIOS   *************************************/
	public Boolean updateFarmaciasHorarios(String codigoFarmaciasHorarios,String desde,String hasta) throws SQLException{

		String update=" UPDATE ad_farmacias_horarios "+
				      " SET desde='"+desde+"',"+
				      "     hasta='"+hasta+"'"+
					  " WHERE codigo="+codigoFarmaciasHorarios;
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(update);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}		
	
/***********************    OBTENER CODIGO MAXIMO FARMACIAS HORARIOS  ***********************/
	
	
	public String obtenerHorarioAdFarmaciasHorarios(String codigoFarmacia){
		String horario="";
				String queryBusqueda=" select a.nombre \"nombre\"  , b.desde \"desde\" , b.hasta \"hasta\"" +
						" from AD_TIPOS_HORARIOS  a, ad_farmacias_horarios b" +
						" where A.CODIGO = B.CODIGO_TIPO_HORARIO" +
						" and B.CODIGO_FARMACIA ="+codigoFarmacia;
		try{
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				horario+=conexionVarianteSid.getString(rs, "nombre")+": "+conexionVarianteSid.getString(rs, "desde")+" a "+ conexionVarianteSid.getString(rs, "hasta")+".<br/>";
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return horario;
		}catch(SQLException e){
			e.printStackTrace();
			return "";
		}
	}		

	/***********************    OBTENER CODIGO MAXIMO GEOLOCALIZACION  ***********************/
	
	
	public Integer obtenerMaxGeolocalizacion(){
		Integer maximo=0;
		String queryBusqueda=" SELECT MAX(codigo) \"maximo\" FROM ad_farmacias_geolocalizacion ";
		
		
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			

			while(conexionVarianteSid.siguiente(rs)){
				maximo=conexionVarianteSid.getInt(rs, "maximo");
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return maximo+1;
		}catch(SQLException e){
			e.printStackTrace();
			return 1;
		}
	}	
	
	/***************************     BUSCA GEOLOCALIZACION   *******************/
	
	public Boolean buscarGeolocalizacion(String codigoLocal){
		Boolean existe=true;
		String queryBusqueda=" SELECT 1 \"existe\" "+
							 " FROM ad_farmacias_geolocalizacion a "+
							 " WHERE a.codigo_farmacia="+codigoLocal;
				
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
		
			

			while(conexionVarianteSid.siguiente(rs)){
				 existe=false;
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return existe;
		}catch(SQLException e){
			e.printStackTrace();
			return existe;
		}
	}		
	/**************************     INSERTAR NUEVO FARMACIAS HORARIOS    *****************************/
	public Boolean insertGeolocalizacion(String codigoGeolocalizacion,String longitud,String latitud,String codigoFarmacia,String url1, String distancia) throws SQLException{

		String insert=" INSERT INTO ad_farmacias_geolocalizacion(codigo,longitud,latitud,codigo_farmacia,url1,distancia_kilometros) "+
				      " VALUES ("+codigoGeolocalizacion+","+longitud+","+latitud+","+codigoFarmacia+",'"+ url1 +"',"+distancia+")";
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(insert);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}		
	
	/******************************  ACTUALIZA   GEOLOCALIZACION   ************************************************/
	public Boolean updateGeolocalizacion(String codigoGeolocalizacion,String longitud,String latitud,String url1,String distancia) throws SQLException{

		String update=" UPDATE ad_farmacias_geolocalizacion "+
					  " SET longitud="+longitud+","+
					  " latitud="+latitud+","+
					  " distancia_kilometros="+distancia+""+
					  " WHERE codigo="+codigoGeolocalizacion;
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(update);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}
	
	/***************************     BUSCA TIPO SERVICIOS   *******************/
	
	public Boolean buscarTiposServicios(String codigoLocal,String codigoServicios){
		Boolean existe=true;
		String queryBusqueda=" SELECT 1 \"existe\" "+
							 " FROM ad_farmacias_servicios a "+
							 " WHERE a.codigo_farmacia="+codigoLocal+
							 " AND a.codigo_servicio="+codigoServicios;
				
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				 existe=false;
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return existe;
		}catch(SQLException e){
			e.printStackTrace();
			return existe;
		}
	}	
	/***********************    OBTENER CODIGO MAXIMO FARMACIAS SERVICIOS  ***********************/
	
	
	public Integer obtenerMaxFarmaciasServicios(){
		Integer maximo=0;
		String queryBusqueda=" SELECT MAX(codigo) \"maximo\" FROM ad_farmacias_servicios ";
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				maximo=conexionVarianteSid.getInt(rs, "maximo");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return maximo+1;
		}catch(SQLException e){
			e.printStackTrace();
			return 1;
		}
	}
	
	/***********************    OBTENER CODIGO MAXIMO FARMACIAS SERVICIOS  ***********************/
	
	
	public Integer obtenerCodigoMapeoServicios(String codigoServicio){
		Integer maximo=0;
		String queryBusqueda=" SELECT ser_servicio FROM AD_MAPEO_SERVICIOS " +
				" where codigo_servicio =" +codigoServicio;
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				maximo=conexionVarianteSid.getInt(rs, "ser_servicio");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return maximo;
		}catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
	}
	/**************************     INSERTAR NUEVO FARMACIAS SERVICIOS    *****************************/
	public Boolean insertFarmaciasServicios(String codigoFarmaciasServicios,String codigoServicios,String codigoFarmacia) throws SQLException{

		String insert=" INSERT INTO ad_farmacias_servicios(codigo,codigo_servicio,codigo_farmacia) "+
				      " VALUES("+codigoFarmaciasServicios+","+codigoServicios+","+codigoFarmacia+")";
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(insert);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}		
	
	/**************************     ELIMINAR FARMACIAS SERVICIOS    *****************************/
	public Boolean eliminarFarmaciasServicios(String codigoServicios,String codigoFarmacia) throws SQLException{

		String eliminar=" DELETE FROM  ad_farmacias_servicios "+
					  " WHERE CODIGO_SERVICIO="+codigoServicios+
					  " AND codigo_farmacia="+codigoFarmacia;
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(eliminar);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}
		
	
/***********************    OBTENER CODIGO MAXIMO WF_ARCHIVOS  ***********************/
	
	
	public Integer obtenerMaxWfArchivos(){
		Integer maximo=0;
		String queryBusqueda="SELECT WF_ARC_SEQ.NEXTVAL \"maximo\" FROM DUAL";
		try{
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				maximo=conexionVarianteSid.getInt(rs, "maximo");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return maximo;
		}catch(SQLException e){
			e.printStackTrace();
			return 1;
		}
	}
	
	/**************************     INSERTAR NUEVO WF_ARCHIVOS   *****************************/
	public Boolean insertWfArchivos(WfArchivosBean archivo) throws SQLException{
		  PreparedStatement ps = null;
          ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
          String queryInser=" Insert into WF_ARCHIVOS" +
          		            " (CODIGO, MIME_TYPE, FILENAME, FULL_FILENAME, FECHA_CREACION,USUARIO_CREACION,CONTENIDO,TIPO_IMAGEN)" +
          		            " Values(?,?,?,?,?,?,?,?)";
          try {                              
             
              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
              ps.setLong(1, archivo.getCodigo());
              ps.setString(2,archivo.getMinyType());
              ps.setString(3, archivo.getFileName());
              ps.setString(4, archivo.getFullFileName());
              ps.setDate(5, archivo.getFechaCreacion());
              ps.setString(6, archivo.getUsuarioCreacion());
              ps.setBytes(7, archivo.getContenido());
              ps.setString(8,"WEB");
              
              
              ps.executeUpdate();    
              ps.close();
            
              conexionVarianteSid.getConn().commit();
              return true;
           
       } catch (SQLException e) {
           if(e.getErrorCode()!=2291){
               System.out.println("ErrorCode  "+e.getErrorCode());
               try {
                   ps.close();
               } catch (SQLException e1) {
            	   e1.printStackTrace();
               }
               e.printStackTrace();
               try {
            	   this.finalize();
               } catch (Throwable e1) {
                   e1.printStackTrace();
               }
           }
           return false;
       }
         
	}		
	
	
	/**************************     MODIFICAR WF_ARCHIVOS   *****************************/
	public Boolean editarWfArchivos(WfArchivosBean archivo) throws SQLException{
		  PreparedStatement ps = null;
          ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
          String queryInser=" UPDATE WF_ARCHIVOS" +
          		            " SET  MIME_TYPE =?" +
          		            " ,FILENAME = ?" +
          		            " ,FULL_FILENAME = ?" +
          		            " ,FECHA_CREACION = ?" +
          		            " ,USUARIO_CREACION = ?" ;
       
          if(archivo.getTamanio()>0)
        	  queryInser += " ,CONTENIDO = ? "; 
          queryInser+=" WHERE CODIGO = ?";
          try {                              
             
              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
            
              ps.setString(1,archivo.getMinyType());
              ps.setString(2, archivo.getFileName());
              ps.setString(3, archivo.getFullFileName());
              ps.setDate(4, archivo.getFechaCreacion());
              ps.setString(5, archivo.getUsuarioCreacion());
              
              if(archivo.getTamanio()>0){
            	  ps.setBytes(6, archivo.getContenido());
            	  ps.setLong(7, archivo.getCodigo());
              }else{
            	  ps.setLong(6, archivo.getCodigo());
              }
              
              ps.executeUpdate();    
              ps.close();
            
              conexionVarianteSid.getConn().commit();
              return true;
           
       } catch (SQLException e) {
           if(e.getErrorCode()!=2291){
               System.out.println("ErrorCode  "+e.getErrorCode());
               try {
                   ps.close();
               } catch (SQLException e1) {
            	   e1.printStackTrace();
               }
               e.printStackTrace();
               try {
            	   this.finalize();
               } catch (Throwable e1) {
                   e1.printStackTrace();
               }
           }
           return false;
       }
          
	}
         
	
	/**************************     INSERTAR NUEVO FOTOS CESTROS COSTO    *****************************/
	public Boolean insertFotosCentrosCosto(AdFarmaciasImagenBean imagen) throws SQLException{
		  PreparedStatement ps = null;
          ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
          String queryInser=" Insert into ADMINISTRACION.AD_FARMACIAS_IMAGEN" +
          		            " (CODIGO_FARMACIA, CODIGO_ARCHIVO, MOSTRAR) " +
          		            " Values(?,?,?)";
          try {                              
             
              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
              ps.setLong(1, imagen.getCodigoFarmacia());
              ps.setLong(2,imagen.getCodigoArchivo());
              ps.setString(3, imagen.getMostar());
              ps.executeUpdate();    
              ps.close();
            
              conexionVarianteSid.getConn().commit();
              return true;
           
       } catch (SQLException e) {
           if(e.getErrorCode()!=2291){
               System.out.println("ErrorCode  "+e.getErrorCode());
               try {
                   ps.close();
               } catch (SQLException e1) {
            	   e1.printStackTrace();
               }
               e.printStackTrace();
               try {
            	   this.finalize();
               } catch (Throwable e1) {
                   e1.printStackTrace();
               }
           }
           return false;
       }
         
	}		
	
	
	/**************************     INSERTAR NUEVO FOTOS CESTROS COSTO    *****************************/
	public Boolean editarFotosCentrosCosto(AdFarmaciasImagenBean imagen) throws SQLException{
		  PreparedStatement ps = null;
          ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
          String queryInser=" update ADMINISTRACION.AD_FARMACIAS_IMAGEN" +
          		            " set MOSTRAR = ? " +
          		            " where CODIGO_FARMACIA = ?" +
          		            " and CODIGO_ARCHIVO = ?";
          try {                              
             
              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
              ps.setString(1, imagen.getMostar());
              ps.setLong(2, imagen.getCodigoFarmacia());
              ps.setLong(3,imagen.getCodigoArchivo());
              
              ps.executeUpdate();    
              ps.close();
            
              conexionVarianteSid.getConn().commit();
              return true;
           
       } catch (SQLException e) {
           if(e.getErrorCode()!=2291){
               System.out.println("ErrorCode  "+e.getErrorCode());
               try {
                   ps.close();
               } catch (SQLException e1) {
            	   e1.printStackTrace();
               }
               e.printStackTrace();
               try {
            	   this.finalize();
               } catch (Throwable e1) {
                   e1.printStackTrace();
               }
           }
           return false;
       }
         
	}		
	
	
	/**************************     INSERTAR NUEVO FOTOS CESTROS COSTO    *****************************/
	public Boolean editarFotosCentrosCostoInactivas(AdFarmaciasImagenBean imagen) throws SQLException{
		  PreparedStatement ps = null;
          ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
          String queryInser=" update ADMINISTRACION.AD_FARMACIAS_IMAGEN" +
          		            " set MOSTRAR = 'N' " +
          		            " where CODIGO_FARMACIA = ?" +
          		            " and CODIGO_ARCHIVO <> ?";
          try {                              
             
              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
              ps.setLong(1, imagen.getCodigoFarmacia());
              ps.setLong(2,imagen.getCodigoArchivo());
              
              ps.executeUpdate();    
              ps.close();
            
              conexionVarianteSid.getConn().commit();
              return true;
           
       } catch (SQLException e) {
           if(e.getErrorCode()!=2291){
               System.out.println("ErrorCode  "+e.getErrorCode());
               try {
                   ps.close();
               } catch (SQLException e1) {
            	   e1.printStackTrace();
               }
               e.printStackTrace();
               try {
            	   this.finalize();
               } catch (Throwable e1) {
                   e1.printStackTrace();
               }
           }
           return false;
       }
         
	}		
	/***SERVICIOS SQLSERVER ****/
	/**************************     INSERTAR NUEVO FOTOS CESTROS COSTO   SQL SERVER ************
	 * @throws NamingException *****************************/
	
	/**/
	public int buscarFarnaciasCodigoSitio(String codigoLocal) {
		int existe = 0;
		String queryBusqueda = " SELECT a.sit_id \"idFarmacia\" "
				+ " from bbo_sitio a , bbo_comercio b "
				+ "  where a.sit_id = b.sit_id" + "  and b.com_codigo = '"
				+ codigoLocal + "'";
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			if(conexionVarianteSid.getConn()!=null){
				if(!conexionVarianteSid.getConn().isClosed())
					System.out.println("CONEXION ABIERTA");
				else
					System.out.println("CONEXION CERRADA");
			}else
				System.out.println("CONEXION CERRADA");
			int count = 0;
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {

				count = conexionVarianteSid.getInt(rs, "idFarmacia");

			}
			rs.close();
			conexionVarianteSid.cerrarConexion();

			if (count > 0)
				existe = count;

			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
			return existe;
		}
	}
	
	
	public int buscarMaxSitId(){
		int existe = 0;
		
		 
		String queryBusqueda = " SELECT MAX(sit_id) \"sit_id\" "
				+" from bbo_sitio ";
				
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			int count = 0;
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {

				count = conexionVarianteSid.getInt(rs, "sit_id");

			}
			rs.close();
			conexionVarianteSid.cerrarConexion();

			if (count > 0)
				existe = count;

			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
			return existe;
		}
	}
	
	public int buscarHorario(String codFarmacia){
		int existe = 0;
		
		 
		String queryBusqueda = " select hor_id" +
								" from dbo.sit_horario_sitio" +
								" where sit_id =( select sit_id " +
												" frOm  dbo.bbo_comercio " +
												" WHERE com_codigo = '"+codFarmacia+"') ";
				
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			int count = 0;
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {
				count = conexionVarianteSid.getInt(rs, "hor_id");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();

			if (count > 0)
				existe = count;

			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
			return existe;
		}
	}
	
	/*
	 * 
	 * */
	
	public FybecaAdminBean buscarAdFarmacias(FybecaAdminBean fybecaAdmin) {
		

		String queryBusqueda = " SELECT NOMBRE," + " TELEFONO," + " CALLE,"
				+ " (SELECT SIT_CIUDAD" + " FROM AD_MAPEO_CIUDADES"
				+ " WHERE CODIGO_CIUDAD = A.CIUDAD)" + "  \"CIUDAD\" ,"
				+ " (SELECT SIT_PROVINCIA"
				+ " FROM AD_MAPEO_PROVINCIAS WHERE CODIGO_PROVINCIA ="
				+ " (SELECT PROVINCIA   FROM AD_CIUDADES"
				+ " WHERE CODIGO = A.CIUDAD)) \"PROVINCIA\" "
				+ " FROM AD_FARMACIAS A WHERE CODIGO = " + fybecaAdmin.getCom_codigo();
		try {

			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);

			while (conexionVarianteSid.siguiente(rs)) {

				fybecaAdmin.setSit_nombre(conexionVarianteSid.getString(rs, "NOMBRE"));
				fybecaAdmin.setCom_telefono(conexionVarianteSid.getString(rs,"TELEFONO"));
				fybecaAdmin.setSit_direccion(conexionVarianteSid.getString(rs,"CALLE"));
				fybecaAdmin.setSit_ciudad(conexionVarianteSid.getInt(rs, "CIUDAD"));
				fybecaAdmin.setSit_provincia(conexionVarianteSid.getInt(rs,"PROVINCIA"));

			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			
			return fybecaAdmin;
		} catch (SQLException e) {
			e.printStackTrace();
			return fybecaAdmin;
		}
	}/*
	public FybecaAdminBean buscarAdFarmacias(String codFarmacia){
		FybecaAdminBean existe = new FybecaAdminBean();
		
		 
		String queryBusqueda = " SELECT NOMBRE,"
				+ " TELEFONO,"
				+ " CALLE,"
				+ " (SELECT SIT_CIUDAD"
				+ " FROM AD_MAPEO_CIUDADES"
				+ " WHERE CODIGO_CIUDAD = A.CIUDAD)"
				+ "  \"CIUDAD\" ," 
				+ " (SELECT SIT_PROVINCIA"
				+ " FROM AD_MAPEO_PROVINCIAS WHERE CODIGO_PROVINCIA ="
				+ " (SELECT PROVINCIA   FROM AD_CIUDADES"
				+ " WHERE CODIGO = A.CIUDAD)) \"PROVINCIA\" "
				+ " FROM AD_FARMACIAS A WHERE CODIGO = " + codFarmacia;
				
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}

			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {

				existe.setSit_nombre(conexionVarianteSid.getString(rs, "NOMBRE"));
				existe.setCom_telefono(conexionVarianteSid.getString(rs, "TELEFONO"));
				existe.setSit_direccion(conexionVarianteSid.getString(rs, "CALLE"));
				existe.setSit_ciudad(conexionVarianteSid.getInt(rs, "CIUDAD"));
				existe.setSit_provincia(conexionVarianteSid.getInt(rs, "PROVINCIA"));

			}
			rs.close();
			conexionVarianteSid.cerrarConexion();

			
			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
			return existe;
		}
	}*/
	public Boolean actulizarComHorarios(FybecaAdminBean fybecaAdmin) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		String queryUpdate = " update  bbo_comercio " 
		                   + " set com_horario= ? "
				           + " where sit_id = ? ";

		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryUpdate);
			ps.setString(1, fybecaAdmin.getCon_horario());
			ps.setInt(2, fybecaAdmin.getSit_id());
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean actulizarComImagen(FybecaAdminBean fybecaAdmin) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		String queryUpdate = " update  bbo_comercio " 
		                   + " set com_imagen= ? "
				           + " where sit_id = ? ";

		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryUpdate);
			ps.setBytes(1, fybecaAdmin.getCon_imagen());
			ps.setInt(2, fybecaAdmin.getSit_id());
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean ingresarBboSitio(FybecaAdminBean fybecaAdmin) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryInser=" INSERT INTO bbo_sitio"+
          " (sit_nombre"+
          " ,sit_direccion"+
          " ,sit_provincia"+
          " ,sit_ciudad"+
          " ,sit_latitud"+
          " ,sit_longitud"+
          " ,sit_estado)"+
          " VALUES(?,?,?,?,?,?,?)";

		try {
			ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
			ps.setString(1, fybecaAdmin.getSit_nombre());
			ps.setString(2, fybecaAdmin.getSit_direccion());
			ps.setInt(3, fybecaAdmin.getSit_provincia());
			ps.setInt(4, fybecaAdmin.getSit_ciudad());
			ps.setString(5,fybecaAdmin.getSit_latitud());
			ps.setString(6,fybecaAdmin.getSit_longitud());
			ps.setInt(7, fybecaAdmin.getSit_estado());
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean ingresarBboComercio(FybecaAdminBean fybecaAdmin) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryInser=" INSERT INTO bbo_comercio "+
           " (sit_id "+
           " ,com_telefono "+
           " ,com_tipo" +
           ",com_codigo) "+
           " VALUES(?,?,?,?)";

		try {
			ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
			
			ps.setInt(1, fybecaAdmin.getSit_id());
			ps.setString(2, fybecaAdmin.getCom_telefono());
			ps.setInt(3, fybecaAdmin.getCom_tipo());
			ps.setString(4, fybecaAdmin.getCom_codigo());
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean ingresarServicio(FybecaAdminBean fybecaAdmin) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryInser=" Insert into bbo_servicio (sit_id ,ser_servicio)" +
		           " Values(?,?)";

		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
			ps.setInt(1, fybecaAdmin.getSit_id());
			ps.setInt(2, fybecaAdmin.getSer_servicio());
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean eliminarServicio(FybecaAdminBean fybecaAdmin) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryDelete=" delete bbo_servicio " +
		 		           " where sit_id = ? " +
		 		           " and ser_servicio = ? " ;

		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryDelete);
			ps.setInt(1, fybecaAdmin.getSit_id());
			ps.setInt(2, fybecaAdmin.getSer_servicio());
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean actulizarSitLatitudSitLongitud(FybecaAdminBean fybecaAdmin) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		String queryUpdate = " update  bbo_sitio " 
		                   + " set sit_latitud = ? ," 
				           + " sit_longitud = ? "
				           + " where sit_id = ? ";

		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryUpdate);
			ps.setString(1, fybecaAdmin.getSit_latitud());
			ps.setString(2, fybecaAdmin.getSit_longitud());
			ps.setInt(3, fybecaAdmin.getSit_id());
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	
	
	
	/************* FIN DE SERVICIOS SQLSERVER***********************/
	/**************************     ELIMINAR FARMACIAS WF_ARCHIVOS    *****************************/
	public Boolean eliminarWfArchivos(String codigoArchivo) throws SQLException{

		String eliminar=" DELETE FROM  wf_archivos "+
					  " WHERE CODIGO ="+codigoArchivo;
					  
		boolean respuesta;
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(eliminar);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}
	
	/**************************     ELIMINAR FARMACIAS FOTOS CESTROS COSTO    *****************************/
	public Boolean eliminarFotoCentosCosto(String codigoArchivo,String codigoFarmacia) throws SQLException{

		String eliminar=" DELETE FROM  AD_FARMACIAS_IMAGEN "+
					  " WHERE CODIGO_ARCHIVO="+codigoArchivo+
					  " AND CODIGO_FARMACIA="+codigoFarmacia;
		boolean respuesta;
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(eliminar);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}
	
	/*****************************  VER IMAGENES     ***************************************************/
	
	
	public byte[] getImagenCodigoWfArchivos(String codigoImagen)
			throws IOException {
		String queryBusqueda = "select codigo \"codigo\", "
				+ "filename \"filename\", " 
				+ "contenido \"contenido\" "
				+ "from wf_archivos where codigo=" + codigoImagen;

		try {
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			WfArchivosBean archivo = null;
			while (conexionVarianteSid.siguiente(rs)) {
				archivo = new WfArchivosBean();
				archivo.setContenido(conexionVarianteSid.getByte(rs,
						"contenido"));
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			byte[] a = new byte[] {};
			if(archivo!= null)
			return archivo.getContenido();
			else return  a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
/*****************************  VER IMAGENES   SQL SERVER  ***************************************************/
	
	
	public byte[] getImagenCodigoSqServer(String codigoLocal)
			throws IOException {
		String queryBusqueda = " select com_imagen " +
				               " from bbo_comercio " +
				               " where sit_id = "+codigoLocal;
		byte[] contenido = null;
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {
				contenido= conexionVarianteSid.getByte(rs,"com_imagen");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return contenido;
		} catch (SQLException e) {
			e.printStackTrace();
			return contenido;
		}
		
		
	}
	 
	 
	
	/********************    CARGA COMBO SERVICIOS FARMACIAS  ***************************/
	public String getServiciosFarmaciasCombo(){
		String queryBusqueda=" SELECT a.codigo \"codigo\", a.nombre \"nombre\", a.activo \"activo\" "+ 
						     " FROM ad_servicios_farmacias a " +
						     " WHERE activo ='S'";
		List<AdServiciosFarmaciasBean> listInfo = new ArrayList<AdServiciosFarmaciasBean>();
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			AdServiciosFarmaciasBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new AdServiciosFarmaciasBean();
				infoProd.setCodigo(conexionVarianteSid.getLong(rs, "codigo"));
				infoProd.setNombre(conexionVarianteSid.getString(rs, "nombre"));
				infoProd.setActivo(conexionVarianteSid.getString(rs, "activo"));
				
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"cargarServiciosFarmacias","Carga Servicios Farmacias","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","cargarServiciosFarmacias","Carga Servicios Farmacias","404");
		}
	}

	/**************************     INSERTAR NUEVO SERVICIOS FARMACIAS   *****************************/
	public Boolean insertServiciosFarmacias(String codigo,String nombre,String activo) throws SQLException{

		String insert=" INSERT INTO ad_servicios_farmacias(codigo,nombre,activo) "+
				      " VALUES("+codigo+",'"+nombre.toUpperCase().trim()+"','"+activo.toUpperCase().trim()+"')";
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(insert);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}	
	/***********************    OBTENER CODIGO MAXIMO  SERVICIOS FARMACIAS ***********************/
	
	
	public Integer obtenerMaxServiciosFarmacias(){
		Integer maximo=0;
		String queryBusqueda=" SELECT MAX(codigo) \"maximo\" FROM ad_servicios_farmacias ";
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				maximo=conexionVarianteSid.getInt(rs, "maximo");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return maximo+1;
		}catch(SQLException e){
			e.printStackTrace();
			return 1;
		}
	}
	/***************************     BUSCA SERVICIOS FARMACIAS   *******************/
	
	public Boolean buscarServiciosFarmacias(String nombre){
		Boolean existe=true;
		String queryBusqueda=" SELECT 1 \"existe\" "+
							 " FROM ad_servicios_farmacias a "+
							 " WHERE a.nombre='"+nombre.toUpperCase().trim()+"'";
				
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				 existe=false;
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return existe;
		}catch(SQLException e){
			e.printStackTrace();
			return existe;
		}
	}	
	
	
	/***************************    VALIDAR IMAGEN FARMACIAS ACTIVAS   *******************/
	
	public int buscarImgenActiva(long codigoFarmacia){
		int existe= 0;
		String queryBusqueda=" select CODIGO_ARCHIVO" +
							 " from AD_FARMACIAS_IMAGEN" +
							 " where codigo_farmacia = " +codigoFarmacia+
							 " and mostrar ='S'" +
							 " GROUP BY CODIGO_ARCHIVO ";
				int cont =0;
		try{
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				cont =conexionVarianteSid.getInt(rs, "CODIGO_ARCHIVO");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			
			if(cont>0)
				existe = cont;
			
			return existe;
		}catch(SQLException e){
			e.printStackTrace();
			return existe;
		}
	}	
	

   /***************************     BUSCA CODIGO IMAGENES ACTIVAS FARMACIAS   *******************/
	public String buscarCodigoImgenActiva(long codigoFarmacia){
	
		String queryBusqueda=" select codigo_archivo \"existe\"" +
							 " from AD_FARMACIAS_IMAGEN" +
							 " where codigo_farmacia = " +codigoFarmacia+
							 " and mostrar ='S'" +
							 " and rownum = 1";
			long codigoArchivo =0;
		try{
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				codigoArchivo =conexionVarianteSid.getLong(rs, "existe");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			
			return String.valueOf(codigoArchivo);
		}catch(SQLException e){
			e.printStackTrace();
			return "0";
		}
	}	
	/******************************  ACTUALIZA   SERVICIOS FARMACIAS   ************************************************/
	public Boolean updateServiciosFarmacias(String codigo,String nombre,String activo) throws SQLException{

		String update=" UPDATE ad_servicios_farmacias "+
					  " SET nombre='"+nombre.toUpperCase().trim()+"',"+
					  " activo='"+activo.toUpperCase().trim()+"'"+
					  " WHERE codigo="+codigo;
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(update);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}
	
	/**************************     ELIMINAR FARMACIAS SERVICIOS    *****************************/
	public Boolean eliminarServiciosFarmacias(String codigo) throws SQLException{

		String eliminar=" DELETE FROM  ad_servicios_farmacias "+
					  	" WHERE CODIGO="+codigo;
		boolean respuesta;
		
		//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(eliminar);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}
	
	/********************    CARGA COMBO SERVICIOS FARMACIAS  ***************************/
	public String getServiciosFarmaciasGrid(){
		String queryBusqueda=" SELECT a.codigo \"codigo\", a.nombre \"nombre\", a.activo \"activo\" "+ 
						     " FROM ad_servicios_farmacias a ";
		List<AdServiciosFarmaciasBean> listInfo = new ArrayList<AdServiciosFarmaciasBean>();
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			AdServiciosFarmaciasBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new AdServiciosFarmaciasBean();
				infoProd.setCodigo(conexionVarianteSid.getLong(rs, "codigo"));
				infoProd.setNombre(conexionVarianteSid.getString(rs, "nombre"));
				infoProd.setActivo(conexionVarianteSid.getString(rs, "activo"));
				
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"cargarServiciosFarmacias","Carga Servicios Farmacias","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","cargarServiciosFarmacias","Carga Servicios Farmacias","404");
		}
	}
	
	
	
	
	public List<WfArchivosBean> buscarImgenesFybecaAdmin() {
		String queryBusqueda = " select com_codigo, com_imagen ,b.sit_nombre " +
				 "  from bbo_comercio a  , bbo_sitio b" +
				 " where a.com_imagen is not null " +
				 " and a.com_tipo = 195 " +
				 " and a.sit_id=b.sit_id ";
		List<WfArchivosBean> datosList = new ArrayList<WfArchivosBean>();
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {
				WfArchivosBean dato = new WfArchivosBean();
				
				dato.setInstanceId(conexionVarianteSid.getString(rs, "com_codigo"));
				dato.setContenido(conexionVarianteSid.getByte(rs, "com_imagen"));
				dato.setFileName(conexionVarianteSid.getString(rs, "sit_nombre"));
				
				datosList.add(dato);

			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return datosList;
		} catch ( SQLException e) {
			e.printStackTrace();
			return datosList;
		}catch (Exception e2) {
			e2.printStackTrace();
			return datosList;
		}
	}
	
	public List<HashMap<String,String>>  buscarCatalogo(String cat_id) {
		String queryBusqueda = "SELECT itc_nombre ,itc_id" +
				" FROM  dbo.adm_item_de_catalogo a" +
				" WHERE cat_id=" +cat_id+
				" ORDER BY itc_id";
		List<HashMap<String,String>>  datosList = new ArrayList<HashMap<String,String>>();
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {
				HashMap<String,String> dato = new HashMap<String, String>();
				
				dato.put("nombre",conexionVarianteSid.getString(rs, "itc_nombre"));
				dato.put("codigo",String.valueOf(conexionVarianteSid.getInt(rs,"itc_id")));
				datosList.add(dato);
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return datosList;
		} catch ( SQLException e) {
			e.printStackTrace();
			return datosList;
		}catch (Exception e2) {
			e2.printStackTrace();
			return datosList;
		}
	}
	
	
	public List<HashMap<String,String>>  buscarSitDetalleHorario(int cat_id) {
		String queryBusqueda = " SELECT b.dho_id , a.itc_nombre "+
							  "  FROM  dbo.adm_item_de_catalogo a, dbo.sit_detalle_horario   b "+ 
							  "  WHERE a.itc_id  = b.dho_dia_semana "+
							  "  and hor_id = "+cat_id;
		List<HashMap<String,String>>  datosList = new ArrayList<HashMap<String,String>>();
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {
				HashMap<String,String> dato = new HashMap<String, String>();
				
				dato.put("codigoDia",conexionVarianteSid.getString(rs, "dho_id"));
				dato.put("nombreDia",conexionVarianteSid.getString(rs,"itc_nombre"));
				datosList.add(dato);
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return datosList;
		} catch ( SQLException e) {
			e.printStackTrace();
			return datosList;
		}catch (Exception e2) {
			e2.printStackTrace();
			return datosList;
		}
	}
	/**/
	
	public Boolean ingresarSitHorarioSitio(int horId,int sitId) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryInser= " INSERT INTO sit_horario_sitio (hor_id ,sit_id)" +
		 		            " VALUES (?,?)";
		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
			ps.setInt(1, horId);
			ps.setInt(2, sitId);
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean ingresarSitHorario(String nombreHorario) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryInser= "INSERT INTO sit_horario (hor_nombre,hor_tipo,hor_estado)" +
		 		" VALUES  (?,?,?)";
		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
			ps.setString(1, nombreHorario);
			ps.setInt(2, 197);
			ps.setInt(3, 1);
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean ingresarDetalleHorario(int horId , int horDiaSemana) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryInser= "INSERT INTO sit_detalle_horario (hor_id,dho_dia_semana)"+
		                                                     "VALUES(?,?)";
		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
			ps.setInt(1, horId);
			ps.setInt(2, horDiaSemana);
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	
	public Boolean ingresarItervalo(int dhoId, int horaInicio,int horaFin,int minutoInicio, int minultoFin) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryInser="INSERT INTO sit_intervalo(dho_id,int_hora_inicio,int_hora_fin,int_minuto_inicio,int_minulto_fin)" +
		                                            " VALUES(?,?,?,?,?)";
		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
			ps.setInt(1, dhoId);
			ps.setInt(2, horaInicio);
			ps.setInt(3, horaFin);
			ps.setInt(4, minutoInicio);
			ps.setInt(5, minultoFin);
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean eliminarIntervalo(int dhoId) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryDelete=" delete dbo.sit_intervalo where  dho_id =?";

		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryDelete);
			ps.setInt(1, dhoId);
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public Boolean eliminarDetalleHorario(int dhoId) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		 String queryDelete=" delete dbo.sit_detalle_horario where dho_id =?";

		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryDelete);
			ps.setInt(1, dhoId);
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	public int buscarMaxSitHorario(){
		int existe = 0;
		
		 
		String queryBusqueda = " SELECT MAX(hor_id) hor_id " +
							   " from sit_horario ";
				
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			int count = 0;
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {

				count = conexionVarianteSid.getInt(rs, "hor_id");

			}
			rs.close();
			conexionVarianteSid.cerrarConexion();

			if (count > 0)
				existe = count;

			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
			return existe;
		}
	}
	
	public int buscarMaxDetalleHorario(){
		int existe = 0;
		
		 
		String queryBusqueda = " SELECT MAX(dho_id) dho_id" +
							   " from sit_detalle_horario";
				
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			int count = 0;
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {

				count = conexionVarianteSid.getInt(rs, "dho_id");

			}
			rs.close();
			conexionVarianteSid.cerrarConexion();

			if (count > 0)
				existe = count;

			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
			return existe;
		}
	}
	/*
	 * 
	 * */
	public Boolean actulizarIntervalos(int horaInicio, int minutoInicio, int horaFin, int minutoFin, int codigoDia) {
		PreparedStatement ps = null;
		ConexionMSSQLServer conexionVarianteSid = null;
		try {
			conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		String queryUpdate = " update dbo.sit_intervalo set " +
				             " int_hora_inicio = ?,"+
                             " int_minuto_inicio=?,"+
                             " int_hora_fin =?,"+
                             " int_minulto_fin=? "+
                             " where dho_id =?";

		try {

			ps = conexionVarianteSid.getConn().prepareStatement(queryUpdate);
			ps.setInt(1, horaInicio);
			ps.setInt(2, minutoInicio);
			ps.setInt(3, horaFin);
			ps.setInt(4, minutoFin);
			ps.setInt(5, codigoDia);
			ps.executeUpdate();
			ps.close();

			conexionVarianteSid.getConn().commit();
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != 2291) {
				System.out.println("ErrorCode  " + e.getErrorCode());
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				try {
					this.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	public Boolean insertMapeoProvincias(String sitProvincia,String codigoProvincia) throws SQLException{
		String insert=" INSERT INTO AD_MAPEO_PROVINCIAS (SIT_PROVINCIA,CODIGO_PROVINCIA) "+
				      " VALUES("+sitProvincia+",'"+codigoProvincia+"')";
		boolean respuesta;
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(insert);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}	
	//INSERT INTO AD_MAPEO_CIUDADES (SIT_CIUDAD,COD_CIUDAD)VALUES( 77    , 9)
	public Boolean insertMapeoCiudades(String sitCiudad,String codCiudad) throws SQLException{
		String insert=" INSERT INTO AD_MAPEO_CIUDADES (SIT_CIUDAD,CODIGO_CIUDAD)"+
				      " VALUES("+sitCiudad+","+codCiudad+")";
		boolean respuesta;
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
		respuesta = conexionVarianteSid.ejecutar(insert);
		conexionVarianteSid.cerrarConexion();
		return respuesta;
	}	

	public String getCodProvincia(String nombre){
		String queryBusqueda=" select codigo from AD_PROVINCIAS WHERE NOMBRE LIKE '%"+nombre.trim()+"%' ";
		String resultado = "";
		try{
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				resultado= conexionVarianteSid.getString(rs, "codigo");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return resultado;
		}catch(SQLException e){
			e.printStackTrace();
			return resultado;
		}
	}
	
	public String getCodCiudad(String nombre){
		String queryBusqueda=" select codigo from ad_ciudades WHERE NOMBRE LIKE '%"+nombre.trim()+"%' ";
		String resultado = "";
		try{
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while(conexionVarianteSid.siguiente(rs)){
				resultado= conexionVarianteSid.getString(rs, "codigo");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return resultado;
		}catch(SQLException e){
			e.printStackTrace();
			return resultado;
		}
	}
	
	
	public List<HashMap<String,String>>  obtenerListadoAdFarmaciasHorarios(String codigoFarmacia) throws NamingException {
		String queryBusqueda=" select a.nombre \"nombre\"  , b.desde \"desde\" , b.hasta \"hasta\"" +
				" from AD_TIPOS_HORARIOS  a, ad_farmacias_horarios b" +
				" where A.CODIGO = B.CODIGO_TIPO_HORARIO" +
				" and B.CODIGO_FARMACIA ="+codigoFarmacia;
		List<HashMap<String,String>>  datosList = new ArrayList<HashMap<String,String>>();
		try {
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {
				HashMap<String,String> dato = new HashMap<String, String>();
				
				dato.put("nombre",conexionVarianteSid.getString(rs, "nombre"));
				dato.put("desde",conexionVarianteSid.getString(rs,"desde"));
				dato.put("hasta",conexionVarianteSid.getString(rs,"hasta"));
				datosList.add(dato);
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return datosList;
		} catch ( SQLException e) {
			e.printStackTrace();
			return datosList;
		}catch (Exception e2) {
			e2.printStackTrace();
			return datosList;
		}
	}

	
	
	public String  buscarSitDetalleHorario(String nombreDia) {
		String queryBusqueda = " select itc_id " +
							   " from  dbo.adm_item_de_catalogo a  " +
							   " where cat_id = 47 and itc_nombre like'%"+nombreDia+"%'";
		String respuesta ="0";
		try {
			ConexionMSSQLServer conexionVarianteSid = null;
			try {
				conexionVarianteSid = obtenerNuevaConexionConexionMSSQLServerDataBase("FybecaAdmin");
			} catch (NamingException e2) {
				e2.printStackTrace();
			}
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			while (conexionVarianteSid.siguiente(rs)) {
				respuesta=conexionVarianteSid.getString(rs, "itc_id");
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return respuesta;
		} catch ( SQLException e) {
			e.printStackTrace();
			return respuesta;
		}catch (Exception e2) {
			e2.printStackTrace();
			return respuesta;
		}
	}
	
	
	
	/********************    CARGA COMBO AD SUCURSALES   ***************************/
	public String getSucursales(){
		String queryBusqueda=" SELECT a.codigo \"codigo\",nombre \"nombre\" "+ 
							 " FROM ad_sucursales a "+
							 " ORDER BY a.nombre";
		List<AdSucursalesBean> listInfo = new ArrayList<AdSucursalesBean>();
		try{
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			AdSucursalesBean infoProd = null;
			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new AdSucursalesBean();
				infoProd.setCodigo(conexionVarianteSid.getLong(rs, "codigo"));
				infoProd.setNombre(conexionVarianteSid.getString(rs, "nombre"));
				
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"AdSucursales","Carga AdSucursales","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","AdSucursales","Carga AdSucursales","404");
		}
	}
	
	
	/********************    CARGA COMBO LOCALES POR SUCURSAL  ***************************/
	public String getFarmaciasPorSucursal(String codigoSucursal){
		String queryBusqueda=" SELECT a.codigo \"codigo\",nombre \"nombre\" "+ 
							 " FROM Ad_Farmacias a "+
							 " WHERE a.campo3 = 'S' "+
							 " AND a.fma_Autorizacion_Farmaceutica = 'FS'" +
							 " and a.codigo in(select farmacia from ad_farmacias_suc where sucursal = " +codigoSucursal+
							 " ) "+  
							 " ORDER BY a.nombre";
		List<AdFarmaciasBean> listInfo = new ArrayList<AdFarmaciasBean>();
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			AdFarmaciasBean infoProd = null;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new AdFarmaciasBean();
				infoProd.setCodigo(conexionVarianteSid.getLong(rs, "codigo"));
				infoProd.setNombre(conexionVarianteSid.getString(rs, "nombre"));
				
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializar(listInfo,"AdFarmacias","Carga AdFarmacias","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERR","AdFarmacias","Carga AdFarmacias","404");
		}
	}
	
	/********************    VALIDAR SI PUEDE INSERTAR EN FYBECA ADMIN ***************************/
	public Boolean validarFarmacia(String codigoFarmacia){
		String queryBusqueda=" select COUNT(EPA_CODIGO) \"cont\" " +
				             " from AD_EMPRESAS_MAS a where " +
				             " EPA_CODIGO IN (select EMPRESA from Ad_Farmacias a WHERE CODIGO = " + codigoFarmacia+
				             " ) AND A.CP_VAR1 ='S'";
		
		try{
			//ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			int infoProd = 0;
			

			while(conexionVarianteSid.siguiente(rs)){
				infoProd = conexionVarianteSid.getInt(rs, "cont");
				
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			if(infoProd > 0) 
				return Boolean.FALSE;

			return Boolean.TRUE;
		}catch(SQLException e){
			e.printStackTrace();
			return Boolean.TRUE;
		}
	}

	
	/*****************************    GEOLOCALIZACION    *****************************/
	public String getDatosFarmacia(String usuario){
		
		String queryBusqueda=" select codigo, nombre, direccion, LONGITUD, LATITUD, CUMPLIMIENTO"
						    + "	from (SELECT b.codigo CODIGO,"
						    + " b.nombre NOMBRE,"
						    + " b.calle || '  ' || b.numero || ' Y ' || b.interseccion DIRECCION,"
						    + " a.longitud LONGITUD,"
						    + " a.latitud LATITUD,"
						    + " 1 CUMPLIMIENTO"
						    + " FROM ad_farmacias_geolocalizacion a, ad_farmacias b"
						    + " WHERE a.codigo_farmacia = b.codigo"
						    + " and b.campo3 = 'S'"
						    + " and b.fma_autorizacion_farmaceutica = 'FS'"
						    + " and b.empresa = 1"
						    + " and exists (select 1"
						    + " from fa_facturas f"
						    + " where f.farmacia = b.codigo"
						    + " and f.fecha >= sysdate - 1)"
						    + " union"
						    + " SELECT b.codigo CODIGO,"
						    + " b.nombre NOMBRE,"
						    + " b.calle || '  ' || b.numero || ' Y ' || b.interseccion DIRECCION,"
						    + " a.longitud LONGITUD,"
						    + " a.latitud LATITUD,"
						    + " 1 CUMPLIMIENTO"
						    + " FROM ad_farmacias_geolocalizacion a, ad_farmacias b"
						    + " WHERE a.codigo_farmacia = b.codigo"
						    + " and b.campo3 = 'S'"
						    + " and b.fma_autorizacion_farmaceutica = 'FS'"
						    + " and b.empresa = 8"
						    + " and exists (select 1"
						    + " from fa_facturas@sana f"
						    + " where f.farmacia = b.codigo"
						    + " and f.fecha >= sysdate - 1)"
						    + " union"
						    + " SELECT b.codigo CODIGO,"
						    + " b.nombre NOMBRE,"
						    + " b.calle || '  ' || b.numero || ' Y ' || b.interseccion DIRECCION,"
						    + " a.longitud LONGITUD,"
						    + " a.latitud LATITUD,"
						    + " 1 CUMPLIMIENTO"
						    + " FROM ad_farmacias_geolocalizacion a, ad_farmacias b"
						    + " WHERE a.codigo_farmacia = b.codigo"
						    + " and b.campo3 = 'S'"
						    + " and b.fma_autorizacion_farmaceutica = 'FS'"
						    + " and b.empresa = 16"
						    + " and exists (select 1"
						    + " from fa_facturas@franquicia f"
						    + " where f.farmacia = b.codigo"
						    + " and f.fecha >= sysdate - 1)"
						    + " union"
						    + " SELECT b.codigo CODIGO,"
						    + " b.nombre NOMBRE,"
						    + " b.calle || '  ' || b.numero || ' Y ' || b.interseccion DIRECCION,"
						    + " a.longitud LONGITUD,"
						    + " a.latitud LATITUD,"
						    + " 1 CUMPLIMIENTO"
						    + " FROM ad_farmacias_geolocalizacion a, ad_farmacias b"
						    + " WHERE a.codigo_farmacia = b.codigo"
						    + " and b.campo3 = 'S'"
						    + " and b.fma_autorizacion_farmaceutica = 'FS'"
						    + " and b.empresa = 14"
						    + " and exists (select 1"
						    + " from fa_facturas@popular f"
						    + " where f.farmacia = b.codigo"
						    + " and f.fecha >= sysdate - 1))"
						    + " order by codigo";
		List<DatosFarmaciasBean> listInfo = new ArrayList<DatosFarmaciasBean>();
		try{
			ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			DatosFarmaciasBean infoProd = null;
			while(conexionVarianteSid.siguiente(rs)){
				infoProd = new DatosFarmaciasBean();
				infoProd.setCodigo(conexionVarianteSid.getLong(rs, "CODIGO"));
				infoProd.setNombre(conexionVarianteSid.getString(rs, "NOMBRE"));
				infoProd.setDireccion(conexionVarianteSid.getString(rs, "DIRECCION"));
				infoProd.setLongitud(conexionVarianteSid.getString(rs, "LONGITUD"));
				infoProd.setLatitud(conexionVarianteSid.getString(rs, "LATITUD"));
				infoProd.setCumplimiento(conexionVarianteSid.getString(rs, "CUMPLIMIENTO"));
				
				listInfo.add(infoProd);
				
			}
			rs.close();
			conexionVarianteSid.cerrarConexion();
			return ConversionsTasks.serializarJSON(listInfo,"Farmacias","Datos Farmacias","104");
		}catch(SQLException e){
			e.printStackTrace();
			return ConversionsTasks.serializar("ERROR","Farmacias","Datos Farmacias","404");
		}
	}	
	
	
}
