package com.serviciosweb.gizlo.utilitarios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import com.serviciosweb.gizlo.modelos.Request;
import com.serviciosweb.gpf.facturacion.farmacias.bean.CredencialDS;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class ConsumoWS {

	Logger log=Logger.getLogger("ConsumoWS");
	public  String respuesta ="";
	


	public void ConsumoWSCancelacion(Request  request) {
		
		CredencialDS credencialDSOfi = new CredencialDS();
		credencialDSOfi.setDatabaseId("PRODDEV_CLOUD_M2C");
		credencialDSOfi.setUsuario("weblink");
		credencialDSOfi.setClave("weblink_2013");
		Connection connoficina = null;									
		try{				
			connoficina = Conexion.obtenerConexionFybeca(credencialDSOfi);
			String url_ws= obtenerParametro(connoficina,"url_ws_cancelacion");
			System.out.println(url_ws);
			URL url = new URL(url_ws);
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);			

	        String jsonStr;
	        ObjectMapper mapper = new ObjectMapper();
	        jsonStr = mapper.writeValueAsString(request);	
	        
	        
	        
	        
	        
			System.out.println(jsonStr);
			byte[] out = jsonStr.getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream()) {
			    os.write(out);
			}		
			
			Reader in = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int c; (c= in.read())>=0;) {
				sb.append((char)c);				
			}
			respuesta = sb.toString();

		} catch (Exception e) {
			log.error(e.getMessage()+" "+e.getLocalizedMessage());
		} finally {
			try {
				if (connoficina != null)
					connoficina.close();
			} catch (Exception e) {
				log.error(e.getMessage()+" "+e.getLocalizedMessage());
			}
		}

	}
	

	public String obtenerParametro(Connection conn, String clave) {
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" SELECT VALOR FROM farmacias.fa_parametros_facturador WHERE CLAVE=? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, clave);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("valor"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (respuesta.size()>0) {
			return respuesta.get(0);			
		}
		else {
			return "";
		}
		
		
		
	}	
	
	

}
