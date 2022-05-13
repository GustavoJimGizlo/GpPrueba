package com.serviciosweb.gpf.facturacion.servicios.aseguradora.controlador;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.plan.ConsultarPlanesBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.plan.PlanesBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.ConexionesBDD;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.EnviarCorreo;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TextoUtil;

import oracle.jdbc.driver.OracleDriver;


public class HumanaValidacionJson extends ObtenerNuevaConexion{
	
	
	    private static Logger LOG = Logger.getLogger(HumanaValidacionJson.class.getName());
	    
	 // PROD
		/*
		  static String quienIngresa="weblink"; 
		  static String comoIngresa="weblink_2013"; 
		  static String url="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@(DESCRIPTION =(FAILOVER = ON)(LOAD_BALANCE = on)(ADDRESS = (PROTOCOL = TCP)(HOST = prsracscan)(PORT = 1521))(CONNECT_DATA = (SERVICE_NAME = PROD.UIO)))"; // AQUI HAY QUE CAMBIAR
		  static String urlSana="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@SANA.UIO";
		*/

		// TEST
		final static String quienIngresa = "Farmacias";
		final static String comoIngresa = "farmacias";
		final static String url = "jdbc:oracle:thin:" + quienIngresa + "/"+ comoIngresa + "@PRODUAT_CLOUD_M2C.UIO";
		final static String urlSana = "jdbc:oracle:thin:" + quienIngresa + "/"+ comoIngresa + "@SANAUAT_CLOUD_M2C.UIO";

	   
public HumanaValidacionJson(){
	    	 super(HumanaValidacionJson.class);
}
	
public HumanaValidacionJson(String ubicacion){
    super(HumanaValidacionJson.class);
    String guiaElectronicaEspsResponse = "";
	String stringJson ="";
	try {
		
			
		stringJson = leerJson("/data/json/validacion.json");	
		System.out.println("Humana rest "+stringJson);
			
			ConsultarPlanesBean objeto = new ObjectMapper().readValue(stringJson, ConsultarPlanesBean.class);
			
			
			if (objeto.getResult().getMensaje().isEmpty() || "OK".equals(objeto.getResult().getMensaje()) && ubicacion.equals("1")) {
				LOG.info("**************************CARGA MANUAL OPCION 1 ELIMNA CARGA EN CENTRALES *************************");
				int resgitrosEliminados = eliminarTablaMapeoAseguradora(objeto.getResult().getCodigo(),1105890);
				int resgitrosEliminadosSana = eliminarTablaMapeoAseguradoraSana(objeto.getResult().getCodigo(),1105890);
				enviarCorreo("PROCESADO ELIMINACION DE CARGA LOTE DE CARGA MANUAL LOTE : "+objeto.getResult().getCodigo());
				}else if(objeto.getResult().getMensaje().isEmpty() || "OK".equals(objeto.getResult().getMensaje()) && ubicacion.equals("2")){
					LOG.info("**************************CARGA MANUAL OPCION 2 CARGA MANUAL *************************");
					int resgitros = guardarTablaMapeoAseguradora(objeto.getPlanes(),objeto.getResult().getCodigo(),1105890);//codigo quemado
					enviarCorreo("PROCESADO EXITOSO - REGISTROS PROCESADO : "+resgitros+"REGISTRO DE CARGA MANUAL");
				}else if (objeto.getResult().getMensaje().isEmpty() || "OK".equals(objeto.getResult().getMensaje()) && ubicacion.equals("3")){
					LOG.info("**************************CARGA MANUAL OPCION 3 CARGA MANUAL COMPLETA *************************");
					int cont = validarRegistrosMapeoAseguradora(objeto.getPlanes(), objeto.getResult().getCodigo(),1105890);//codigo quemado
					enviarCorreo("PROCESADO EXITOSO - REGISTROS PROCESADO : "+cont+"REGISTRO DE CARGA MANUAL");
				}
					
				
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK","");	
			

	
	} catch (IOException e) {
		
		e.printStackTrace();
	}

	
    
}

public static int validarRegistrosMapeoAseguradora(List<PlanesBean> planes,String codigoCarga,
		Integer idAseguradora) {

	Connection conn = null;
	Connection connSana = null;
	conn = null;
	int result = 0;
	try {
		Class.forName("oracle.jdbc.OracleDriver").newInstance();
		System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
		System.setProperty("oracle.net.tns_admin", "/data/oracle");

		DriverManager.registerDriver(new OracleDriver());
		DriverManager.setLoginTimeout(100);

		conn = DriverManager.getConnection(url);
		connSana = DriverManager.getConnection(urlSana);
		for (PlanesBean plan : planes) {
		
			result = validarIngresoMapeoAseg(conn, plan.getContrato()+plan.getCodplan(), idAseguradora);
			LOG.info("VALIDACION DE CONTRATO SI EXISTE O NO EXISTE: "+result+ "CODIGO PLAN EXISTENTE"+plan.getContrato()+plan.getCodplan());
			if(result==0 ) {
				
				int cont = insertMapeoAseguradora(conn, plan, codigoCarga,idAseguradora);
				int contSana = insertMapeoAseguradora(connSana, plan,codigoCarga, idAseguradora);
				if (cont == 1 && contSana == 1) { //
					result++;
				}
				conn.commit();
			}

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

	return result;

}
public static int validarIngresoMapeoAseg(Connection conn,String codigoConfrimacion, Integer idAseguradora) {
	int respuesta = 0;
	PreparedStatement ps = null;

	StringBuilder sqlString = new StringBuilder();
	sqlString.append("  select count(*) codigo ");
	sqlString.append(" from FA_MAPEO_ASEGURADORA_ABF ");
	sqlString.append("	where contrato_aseg = ? ");
	sqlString.append("  and CAMPO3 = ?  ");

	
	ResultSet set = null;
	try {
		ps = conn.prepareStatement(sqlString.toString());
		ps.setString(1, codigoConfrimacion.toString());
		ps.setString(2, idAseguradora.toString());
		set = ps.executeQuery();
		while (set.next()) {
			respuesta = set.getInt("codigo");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (set != null)
				set.close();
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return respuesta;
}


public static int guardarTablaMapeoAseguradora(List<PlanesBean> planes,String codigoCarga, Integer idAseguradora) {

	Connection conn = null;
	Connection connSana = null;

	int result = 0;

	try {
		Class.forName("oracle.jdbc.OracleDriver").newInstance();
		System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
		System.setProperty("oracle.net.tns_admin", "/data/oracle");

		DriverManager.registerDriver(new OracleDriver());
		DriverManager.setLoginTimeout(100);
		conn = DriverManager.getConnection(url);
		connSana = DriverManager.getConnection(urlSana);
		for (PlanesBean plan : planes) {
					
			int cont = insertMapeoAseguradora(conn, plan, codigoCarga,idAseguradora);
			int contSana = insertMapeoAseguradora(connSana, plan,codigoCarga, idAseguradora);
			if (cont == 1 && contSana == 1) { //
				result++;
			}
			conn.commit();
		}
	} catch (Exception e) {
		e.printStackTrace();
		new EnviarCorreo("homologacionPlanes@corporaciongpf.com"
				, "kmtoaquizam@corporaciongpf.com; mftellor@corporaciongpf.com"
				, "ERROR INSERT DE REGISTOS EN BASE DATOS"
				, "Estimad@,<br> <br>Ha finalizado el procesamiento con errores: SEELCT * FA_MAPEO_ASEGURADORA_ABF WHERE CAMPO2 = " + codigoCarga+"");

	} finally {
		try {
			if (conn != null)
				conn.close();

			/*if (connSana != null)
				connSana.close();*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	return result;

}
public static int eliminarTablaMapeoAseguradora(String codigoCarga,
		Integer idAseguradora) {

	Connection conn = null;
	conn = null;
	int result = 0;
	try {

		Class.forName("oracle.jdbc.OracleDriver").newInstance();
		System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
		System.setProperty("oracle.net.tns_admin", "/data/oracle");

		DriverManager.registerDriver(new OracleDriver());
		DriverManager.setLoginTimeout(100);

		conn = DriverManager.getConnection(url);

		result = deleteMapeoAseguradora(conn, codigoCarga, idAseguradora);
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
	return result;
}
public static int eliminarTablaMapeoAseguradoraSana(String codigoCarga,
		Integer idAseguradora) {

	Connection conn = null;
	conn = null;
	int result = 0;
	try {

		Class.forName("oracle.jdbc.OracleDriver").newInstance();
		System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
		System.setProperty("oracle.net.tns_admin", "/data/oracle");

		DriverManager.registerDriver(new OracleDriver());
		DriverManager.setLoginTimeout(100);

		conn = DriverManager.getConnection(urlSana);

		result = deleteMapeoAseguradora(conn, codigoCarga, idAseguradora);
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
	return result;
}

public static int deleteMapeoAseguradora(Connection connection,
		String codigoCarga, Integer idAseguradora) throws SQLException {
	PreparedStatement pstmt = null;
	int result = 0;
	try {
		StringBuffer sqlDelete = new StringBuffer();
		sqlDelete.append("delete from FA_MAPEO_ASEGURADORA_ABF where  CAMPO2 = ? and CAMPO3=?");
		pstmt = connection.prepareStatement(sqlDelete.toString());
		pstmt.setString(1, codigoCarga);
		pstmt.setString(2, idAseguradora.toString());
		result = pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (pstmt != null) {
			pstmt.close();
		}
	}

	return result;
}

public static int insertMapeoAseguradora(Connection connection,
		PlanesBean planesBean, String codigoCarga, Integer idAseguradora)
		throws SQLException {
	PreparedStatement pstmt = null;
	int result = 0;
	try {
		StringBuffer sqlDelete = new StringBuffer();
		sqlDelete
				.append("insert into FA_MAPEO_ASEGURADORA_ABF (CODIGO,CONTRATO_ASEG, PLAN_ASEG,CAMPO1, COBERTURA_A, COBERTURA_B,CAMPO2,CAMPO3)");
		sqlDelete.append("values (?,?,?,?,?,?,?,?)");

		pstmt = connection.prepareStatement(sqlDelete.toString());
		Logger.getLogger("getQuery").info("***********PLANES INGRESADOS BASE CENTRAL******** : "
		 		+ "INGRESOS DE CONTRATOS"+idAseguradora+""
		 		+ ""+planesBean.getContrato() + planesBean.getCodplan());
		pstmt.setInt(1, getCodigoMapeoAseguradora(connection));
		pstmt.setString(2,
				planesBean.getContrato() + planesBean.getCodplan());
		pstmt.setString(3, planesBean.getCodplan());
		pstmt.setString(4, "INT");
		pstmt.setString(5,(planesBean.getVad_A() != null) ? planesBean.getVad_A(): "");
		pstmt.setString(6,(planesBean.getVad_B() != null) ? planesBean.getVad_B(): "");
		pstmt.setString(7, codigoCarga);
		pstmt.setString(8, idAseguradora + "");
		result = pstmt.executeUpdate();
		connection.commit();
	} catch (Exception e) {//muestras todos los errores
		e.printStackTrace();
		new EnviarCorreo("homologacionPlanes@corporaciongpf.com"
				, "kmtoaquizam@corporaciongpf.com"
				, "ERROR SERVICION WEB HUMANA"
				, "Estimad@,<br> <br>Ha finalizado el procesamiento con errores: " + planesBean.getContrato() + planesBean.getCodplan());

	} finally {
		if (pstmt != null) {
			pstmt.close();
		}
	}

	return result;
}

public static int getCodigoMapeoAseguradora(Connection conn) {
	int respuesta = 0;
	PreparedStatement ps = null;

	StringBuilder sqlString = new StringBuilder();
	sqlString.append("select max(codigo) + 1 as codigo from FA_MAPEO_ASEGURADORA_ABF ");

	ResultSet set = null;
	try {
		ps = conn.prepareStatement(sqlString.toString());
		set = ps.executeQuery();
		while (set.next()) {
			respuesta = set.getInt("codigo");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (set != null)
				set.close();
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return respuesta;
}

private void enviarCorreo(String valor) {

	try {

		Date fechaCarga = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	    String emailNotifica = getContenidoFile("MAIL_NOTIFICACION.txt", "ABF");
		String accion = "CONSULTA PLANES ASEGURADORA";
		String asunto = accion +" - "+ valor;

		new EnviarCorreo("homologacionPlanes@corporaciongpf.com"
						, emailNotifica
						, asunto
						, "Estimad@,<br> <br>Ha finalizado el procesamiento homalogacion de planes con un status: " + valor + " <br><br> Fecha Carga:"
						+ dateFormat.format(fechaCarga));
	} catch (Exception e) {
	}
} 

public static String getContenidoFile(String nombreArchivo,String folder){
	File documento = new File("/data/"+folder+"/"+nombreArchivo);
    String sCadena = ""; 
    String retorno = ""; 
    BufferedReader bf=null;
    if(!documento.exists()){
    	Logger.getLogger("getContenidoFile").info("ARCHIVO NO EXISTE : "+nombreArchivo);
        return null; 
    } 

    try{ 
        bf = new BufferedReader(new FileReader(documento)); 
        while ((sCadena = bf.readLine()) != null) { 
            retorno += sCadena; 
        }                         
        
    }catch(FileNotFoundException fnfe){ 
        return null;
    }catch(IOException ioe){
        return null; 
    } finally{
    	try {
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    Logger.getLogger("getQuery").info("ARCHIVO ABIERTO : "+nombreArchivo);
    return retorno; 
}
public String leerJson(String nombreArchivo) {
	File archivo = null;
	FileReader fr = null;
	BufferedReader br = null;
	String result = "";
	try {
		archivo = new File(nombreArchivo);
		fr = new FileReader(archivo);
		br = new BufferedReader(fr);
		String linea;
		while ((linea = br.readLine()) != null) {
			result = linea;
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			if (null != fr) {
				fr.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	return result;
}

}
