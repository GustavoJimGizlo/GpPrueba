package com.serviciosweb.gpf.facturacion.rest;

import java.util.Iterator;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.codehaus.jackson.map.ObjectMapper;

import com.serviciosweb.gpf.facturacion.farmacias.bean.CredencialDS;
import com.serviciosweb.gpf.facturacion.servicios.ConexionServices;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.AseguradoraControl;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaGenBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.DatosGeneralesBean;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaPerBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RespuestaPerBean;



import oracle.jdbc.driver.OracleDriver;

public class PertinenciaController {
	//public String PerResponse = "";
	final String SIDBASE="jdbc:oracle:thin:@PRODUAT_CLOUD_M2C.UIO:1521:prs6";
	//final String SIDBASE="jdbc:oracle:thin:@10.100.3.20:1521:prs6";
	final String USUARIOBASE="Farmacias";
	final String CLAVEBASE="farmacias";

	
	
	
	public Boolean Estadoper(String  cpro, String cdia, String caseg) {
		
		Boolean res = new Boolean(false);
				
		Connection conn = null;
		Statement comando;
		ResultSet resultado=null;
		String sqlf="";
		String sql1="";
		String sql2="";
		
		RespuestaPerBean respuesta = new RespuestaPerBean();
		
		try {
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability","o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");
			DriverManager.registerDriver(new OracleDriver());
			DriverManager.setLoginTimeout(100);

			// conexion ab comentar
			//ConexionAB con = new ConexionAB();
			//con.obtConexion();
			// conn = DriverManager.getConnection(SIDBASE,USUARIOBASE,CLAVEBASE);
			//ConexionAB =DriverManager.getConnection(url,"farmacias","farmacias");
			//conn = ConexionServices.obtenerConexionFarmacia(credencialDS);
		    //comando = con.getConexion().createStatement();
			
		//  conn = DriverManager.getConnection(SIDBASE,USUARIOBASE,CLAVEBASE);
		//	conn = DriverManager.getConnection("jdbc:oracle:thin:@10.100.3.20:1521:prs6",USUARIOBASE,CLAVEBASE);
		
			  //test
			 //String quienIngresa="Farmacias";
			 //String comoIngresa="farmacias";
   		     //String url="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@PRODUAT_CLOUD_M2C.UIO";  
	    	 
	    	 //prd
	    	 String quienIngresa="WEBLINK";
			 String comoIngresa="weblink_2013";
   		     String url="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@PRS6.UIO"; 
   	         
	    	 
				
			
   		    conn = DriverManager.getConnection(url);
   		   System.out.println("Conexion Pertinencia" + url);
  	      
			
		 	comando = conn.createStatement();
		//sql= "select id_pertinencia , id_medicamento , id_diagnostico from fa_det_pertinencia where id_medicamento = '" + cpro + " ' and id_diagnostico ='" + cdia +"'" ;
				
				//sql1= "select id_pertinencia , id_medicamento , id_diagnostico from fa_det_pertinencia where id_medicamento = '" + cpro + " ' and id_diagnostico ='" + cdia +"'" ;
		sql1= "select a.id_pertinencia , b.id_medicamento, b.id_diagnostico, a.id_aseguradora from farmacias.FA_CAB_PERTINENCIA a, farmacias.FA_DET_PERTINENCIA b";
		sql2 =" where b.estado = 'A' and a.id_pertinencia = b.id_pertinencia and b.id_medicamento = '" + cpro + " ' and b.id_diagnostico ='" + cdia + "'  and a.id_aseguradora ='" + caseg + "'" ;
		sqlf = sql1 + sql2 ;
				
				
		resultado=comando.executeQuery(sqlf);
		
			    // ponenmos en nullo estado y pertinencia para devolver que no encontro pertinencia medica
				 //respuesta.setEstado(false);
				 //respuesta.setId_pertinencia("");
				 
				 // recorremos resultado de query para devolver q si encontro pertinencia media
				 while(resultado.next())
				 {
					//respuesta.setEstado(true);
				    //respuesta.setId_pertinencia(resultado.getString(1));
				    //result = new AseguradoraControl();
					 
					 res=true;
					 
				}
			
		} catch (Exception e) {
             e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
		
		
	}
	
	
public Boolean BuscarAseguradora(String caseg) {
		Boolean res = new Boolean(false);
		Connection conn = null;
		Statement comando;
		ResultSet resultado=null;
		String sqlf="";
		try {
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability","o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");
			DriverManager.registerDriver(new OracleDriver());
			DriverManager.setLoginTimeout(100);
			  //  conn = DriverManager.getConnection(SIDBASE,USUARIOBASE,CLAVEBASE);

		  	
			
			
			
			
		//conn = DriverManager.getConnection("jdbc:oracle:thin:@10.100.3.20:1521:prs6",USUARIOBASE,CLAVEBASE);
		
			 //test
			 //String quienIngresa="Farmacias";
			 //String comoIngresa="farmacias";
  		     //String url="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@PRODUAT_CLOUD_M2C.UIO";
  		     
  		   //prd
	    	 String quienIngresa="WEBLINK";
			 String comoIngresa="weblink_2013";
   		     String url="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@PRS6.UIO"; 
   	         conn = DriverManager.getConnection(url);
			
   	      System.out.println("Conexion Pertinencia" + url);
   	      
		comando = conn.createStatement();
		//sql= "select id_pertinencia , id_medicamento , id_diagnostico from fa_det_pertinencia where id_medicamento = '" + cpro + " ' and id_diagnostico ='" + cdia +"'" ;
		//sql1= "select id_pertinencia , id_medicamento , id_diagnostico from fa_det_pertinencia where id_medicamento = '" + cpro + " ' and id_diagnostico ='" + cdia +"'" ;
		
		 sqlf ="select * from farmacias.fa_aseguradora where id_aseguradora = "+  caseg + "";
		 resultado=comando.executeQuery(sqlf);
		
				 while(resultado.next())
				 {
					 res=true;
					 
				}
			
		} catch (Exception e) {
             e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
		
		
	}

}
