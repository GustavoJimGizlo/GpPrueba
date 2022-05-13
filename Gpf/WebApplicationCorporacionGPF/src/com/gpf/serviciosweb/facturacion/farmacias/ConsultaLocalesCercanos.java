
package com.gpf.serviciosweb.facturacion.farmacias;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Column;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdFarmaciasCercanas;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @date 20/06/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
public class ConsultaLocalesCercanos extends ObtenerNuevaConexion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(this.getClass().getName());
	String resultadoXml=Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA ESTA CONSULTA");
	private String idBdd="1";
	private Boolean desarrollo=false;
	private ConexionVarianteSid conexionVarianteSidMaster=null;
	private String incluirOtrasCadenas;
	private Integer empresaAnterior=null;
	{
		if(desarrollo)
			idBdd="6";
	}
	public ConsultaLocalesCercanos(String idLocal,String item,String empresa,String incluirOtrasCadenas) {
		super(ConsultaLocalesCercanos.class);
	    this.incluirOtrasCadenas=incluirOtrasCadenas;
		this.getCodigosFarmaciasCercanas(Integer.parseInt(idLocal),item,Integer.parseInt(empresa), incluirOtrasCadenas );
	}
	
	
	
	private Double getDistanciaKilometros(Double longitudOrigen, Double latitudOrigen, Double longitudDestino, Double latitudDestino){
		Double piRadianes=(Math.PI/180);
		Double radioKm=new Double(6317);
		Double cosenos;
		Double distanciaKm;
		longitudOrigen=longitudOrigen*piRadianes;
		latitudOrigen=latitudOrigen*piRadianes;
		longitudDestino=longitudDestino*piRadianes;
		latitudDestino=latitudDestino*piRadianes;
						
		cosenos=(Math.cos(latitudOrigen)*Math.cos(longitudOrigen)*Math.cos(latitudDestino)*Math.cos(longitudDestino))+
		(Math.cos(latitudOrigen)*Math.sin(longitudOrigen)*Math.cos(latitudDestino)*Math.sin(longitudDestino))+
        (Math.sin(latitudOrigen)*Math.sin(latitudDestino));
		
		distanciaKm=Math.acos(cosenos)*radioKm;
		/*
		if(cosenos>1)
			System.out.println(">1");
			*/
	    if(distanciaKm.isNaN())
	    	System.out.println("isNaN");
	    
		return distanciaKm;
	}
	
	
	
	 public List<AdFarmaciasCercanas> getCodigosFarmaciasCercanas(int codigoFarmacia,String item,Integer empresa,String incluirOtrasCadenas) 
     {   		 
		   
		     XStream xstream = new XStream(new StaxDriver());
             String farmaciasCercanas = "";
             CallableStatement cs = null;
             //ConexionDirecta  conexion = new ConexionDirecta();
             ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid(idBdd);
         try {
        	 if(incluirOtrasCadenas.equals("S")){
	        	 cs = conexionVarianteSid.getConn().prepareCall("{?=call Administracion.ad_obt_far_cercanas_otras_cad (?, ?)}");
	             //cs = conexionVarianteSid.getConn().prepareCall("{?=call Administracion.ad_obtiene_farmacias_cercanas(?)}");
	             // Registra el tipo del valor a retornar
	             cs.registerOutParameter(1, Types.VARCHAR);
	             // Setea el valor para el parametro de entrada
	             cs.setInt(2, codigoFarmacia);
	             cs.setInt(3, empresa);
	             // Ejecuta y retorna el valor
	             cs.execute();
	             farmaciasCercanas = cs.getString(1);    
        	 }else{
        		 
        	 }
         } catch (SQLException ex) {
        	 Logger.getLogger(ConsultaLocalesCercanos.class.getName()).log(Level.SEVERE, null, ex);
         }finally{
             try {
                 if (cs != null) {
                     cs.close();                     
                 }
	         } catch (Exception e1) {
	            cs = null;                         
	            Logger.getLogger(ConsultaLocalesCercanos.class.getName()).log(Level.SEVERE, null, e1);
	         }
         }         
         
         //String filtro=empresa!=null?empresa>0?" AND (A.EMPRESA="+empresa+" OR A.CODIGO="+codigoFarmacia+")":"":"";
         String filtro=" AND (A.EMPRESA="+empresa;
         if(farmaciasCercanas!=null)
            filtro+=incluirOtrasCadenas.equals("S") && farmaciasCercanas.trim().length()>0 ?" OR A.CODIGO IN ("+farmaciasCercanas+")) ":")";
         else
        	filtro+=")";
/*
         String queryBusqueda="SELECT A.CODIGO,"+getQueryEspecial("A.NOMBRE")+" AS NOMBRE, A.EMPRESA, " +
        		 getQueryEspecial("A.CALLE || ' ' ||  A.NUMERO || ' ' || A.INTERSECCION")+" AS DIRECCION," +
          		" B.LONGITUD, B.LATITUD FROM AD_FARMACIAS A, ad_farmacias_geolocalizacion B WHERE A.CODIGO=B.codigo_farmacia " +
          		 filtro+" AND B.LONGITUD IS NOT NULL AND B.LATITUD IS NOT NULL AND B.DISTANCIA_KILOMETROS IS NOT NULL  ORDER BY A.EMPRESA";
         
         */
         
         String queryBusqueda="SELECT A.CODIGO,A.NOMBRE AS NOMBRE, A.EMPRESA, " +
        		 "A.CALLE || ' ' ||  A.NUMERO || ' ' || A.INTERSECCION AS DIRECCION," +
          		" B.LONGITUD, B.LATITUD FROM AD_FARMACIAS A, ad_farmacias_geolocalizacion B WHERE A.CODIGO=B.codigo_farmacia " +
          		 filtro+" AND B.LONGITUD IS NOT NULL AND B.LATITUD IS NOT NULL AND B.DISTANCIA_KILOMETROS IS NOT NULL " +
          		 " ORDER BY A.CODIGO,A.EMPRESA";
         ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
         ResultSetMapper<AdFarmaciasCercanas> resultSetMapper = new ResultSetMapper<AdFarmaciasCercanas>();
         List<AdFarmaciasCercanas> listadoAdFarmaciasCercanas=resultSetMapper.mapRersultSetToObject(rs, AdFarmaciasCercanas.class);
         conexionVarianteSid.cerrarConexion(rs);
         AdFarmaciasCercanas localOrigen=getLocal(listadoAdFarmaciasCercanas,codigoFarmacia);
         if(localOrigen==null)
        	 return null;
         for(AdFarmaciasCercanas adFarmaciasCercanasIterador:listadoAdFarmaciasCercanas){
        	 if(localOrigen.getCodigoLocal().compareTo(adFarmaciasCercanasIterador.getCodigoLocal())!=0)
        		 adFarmaciasCercanasIterador.setDistanciaKilometros(getDistanciaKilometros(localOrigen.getLongitudOrigen(),localOrigen.getLatitudOrigen(),adFarmaciasCercanasIterador.getLongitudOrigen(),adFarmaciasCercanasIterador.getLatitudOrigen()));
        	 else
        		 adFarmaciasCercanasIterador.setDistanciaKilometros(0D);
        	 getStockLocal(adFarmaciasCercanasIterador,item);
         }
         Collections.sort(listadoAdFarmaciasCercanas, new Comparator<AdFarmaciasCercanas>() {
      
       public int compare(AdFarmaciasCercanas o1, AdFarmaciasCercanas o2) {
            	 try{
            	   if(o2.getDistanciaKilometros().isNaN() || o1.getDistanciaKilometros().isNaN())
            		   return -1;
            	   return o1.getDistanciaKilometros()<o2.getDistanciaKilometros()?-1:o1.getDistanciaKilometros()>o2.getDistanciaKilometros()?1:doSecodaryOrderSort(o1,o2);
            	 }catch(Exception e){
            		 //e.printStackTrace();
            		 return 0;
            	 }
 
       
             }
       public int doSecodaryOrderSort(AdFarmaciasCercanas o1,AdFarmaciasCercanas o2) {
            	 try{
            	 return o1.getDistanciaKilometros()<o2.getDistanciaKilometros()?-1:o1.getDistanciaKilometros()>o2.getDistanciaKilometros()?1:0;
            	 }catch(Exception e){
            		 e.printStackTrace();
            		 return 0;
            	 }
             }
         });
         this.conexionVarianteSidMaster.cerrarConexion();         
         xstream.alias("AdFarmaciasCercanas", AdFarmaciasCercanas.class);
         resultadoXml=xstream.toXML(listadoAdFarmaciasCercanas);
         return listadoAdFarmaciasCercanas;         
      }
/*
	 private String getQueryEspecial(String campo){
		 return " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR ("+campo+"))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') ";
	 }
	 */
	 private void getStockLocal(AdFarmaciasCercanas adFarmaciasCercanas,String item){
		 
		
		 ResultSet rs=null;
		 try{
			if(conexionVarianteSidMaster==null)// || this.empresaAnterior.compareTo(adFarmaciasCercanas.getEmpresa())!=0)		 
			    conexionVarianteSidMaster = obtenerNuevaConexionVarianteSid(this.idBdd);//adFarmaciasCercanas.getEmpresa().toString());
	/*		
			 String query="select cantidad_stock, unidad_venta,c.nombre as nombre , pvp_sin_iva , pvp_con_iva  from pr_items_autorizados a, ad_farmacias b,ad_ciudades c " +
				 		" where a.farmacia=b.codigo and b.ciudad=c.codigo and  a.FARMACIA="+adFarmaciasCercanas.getCodigoLocal()+" AND ITEM="+item;
*/
			 String query="select cantidad_stock, unidad_venta,c.nombre as nombre , pvp_sin_iva , pvp_con_iva  from pr_items_autorizados a, ad_farmacias b,ad_ciudades c " +
				 		" where a.farmacia=b.codigo and b.ciudad=c.codigo and  a.FARMACIA=? AND ITEM=?";

			 
			 PreparedStatement prepares = this.conexionVarianteSidMaster.getConn().prepareStatement(query);
			 prepares.setString(1, adFarmaciasCercanas.getCodigoLocal().toString());
			 prepares.setString(2,item);
			 rs=prepares.executeQuery();
			 
			 //rs = conexionVarianteSidMaster.consulta(query);
			 boolean validarValor = true;
			 while(conexionVarianteSidMaster.siguiente(rs)){
				 adFarmaciasCercanas.setCantidadStock(conexionVarianteSidMaster.getInt(rs, "cantidad_stock"));
				 adFarmaciasCercanas.setUnidadVenta(conexionVarianteSidMaster.getInt(rs, "unidad_venta"));
				 adFarmaciasCercanas.setDireccion(conexionVarianteSidMaster.getString(rs, "nombre")+" - "+adFarmaciasCercanas.getDireccion());
				 adFarmaciasCercanas.setPvpConIva(conexionVarianteSidMaster.getDouble(rs, "pvp_con_iva"));
				 adFarmaciasCercanas.setPvpSinIva(conexionVarianteSidMaster.getDouble(rs, "pvp_sin_iva"));
				 validarValor = false;
			 }
			 if(validarValor){
				 if(adFarmaciasCercanas.getCantidadStock() == null)
					 adFarmaciasCercanas.setCantidadStock(0);
				 if(adFarmaciasCercanas.getUnidadVenta() == null)
				     adFarmaciasCercanas.setUnidadVenta(0);
				 if(adFarmaciasCercanas.getPvpConIva() == null)
				     adFarmaciasCercanas.setPvpConIva(0.0);
				 if(adFarmaciasCercanas.getPvpSinIva() == null)
				     adFarmaciasCercanas.setPvpSinIva(0.0);
			 }
			 prepares.close();
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			// if(this.empresaAnterior!=null)
			 //  if(this.empresaAnterior.compareTo(adFarmaciasCercanas.getEmpresa())!=0)
			//      conexionVarianteSidMaster.cerrarConexion(rs);	 
		 }
		 
		 this.empresaAnterior=adFarmaciasCercanas.getEmpresa();
	 }
	 private AdFarmaciasCercanas getLocal(List<AdFarmaciasCercanas> listadoAdFarmaciasCercanas, int idLocal){
		    for(AdFarmaciasCercanas adFarmaciasCercanasIterador:listadoAdFarmaciasCercanas)
	        	 if(adFarmaciasCercanasIterador.getCodigoLocal().compareTo(idLocal)==0)
	        		 return adFarmaciasCercanasIterador;
	         
		 return null;
	 }



	public String getResultadoXml() {
		return Constantes.toUTF8(resultadoXml);
	}
	 
}
