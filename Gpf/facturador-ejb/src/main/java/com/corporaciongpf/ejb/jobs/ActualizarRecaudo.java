package com.corporaciongpf.ejb.jobs;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import org.apache.log4j.Logger;

import com.corporaciongpf.adm.dao.ParametroDAO;
import com.corporaciongpf.adm.modelo.CancelarFactura;
import com.corporaciongpf.adm.modelo.CredencialDS;
import com.corporaciongpf.adm.vo.VORequestCancelacion;
import com.corporaciongpf.adm.vo.VOResponse;
import com.corporaciongpf.ejb.utilitario.Conexion;
import com.corporaciongpf.ejb.utilitario.Constantes;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;

import com.corporaciongpf.adm.utilitario.DocumentUtil;


@Singleton
@Startup
public class ActualizarRecaudo {
	private static Logger log = Logger.getLogger(ActualizarRecaudo.class.getName());

	@EJB(lookup = "java:global/facturador-adm/ParametroDAOImpl!com.corporaciongpf.adm.dao.ParametroDAO")
	ParametroDAO parametroDAO;
	
    @Resource
    private TimerService timerService;

    @PostConstruct
    public void init() {
        createTimer();
        //the following code resolve my startup problem
        preparation();

    }

    @Timeout
    public void timerTimeout() {
        preparation();

    }

    private void createTimer() {
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.second(obtenerParametroScheduler("scheduler_segundos")).minute(obtenerParametroScheduler("scheduler_minutos")).hour(obtenerParametroScheduler("scheduler_horas"));
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setPersistent(false);
        timerService.createCalendarTimer(scheduleExpression, timerConfig);
        }

    public void preparation(){
    	procesoActualizarRecaudo();
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static String obtenerParametroScheduler(String  clave)  {
		
		CredencialDS credencialDSOfi = new CredencialDS();
		credencialDSOfi.setDatabaseId(Constantes.BASE_PRS6);
		credencialDSOfi.setUsuario(Constantes.USUARIO_PRS6);
		credencialDSOfi.setClave(Constantes.CLAVE_PRS6);
		credencialDSOfi.setRegion(Constantes.REGION_PRS6);
		credencialDSOfi.setDirectorio(Constantes.DIRECTORIO_TS_NAME_PRS6);		
		Connection connoficina = null;									
					
		connoficina = Conexion.obtenerConexionFybeca(credencialDSOfi);
		
		String valor= obtenerParametro(connoficina,clave);
		try {
			connoficina.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
			
		}

	
	
	
	
	
	

	
  // @Schedule(second=ConstantesJobs.segundos, minute=ConstantesJobs.minute, hour=hour, dayOfWeek=dayOfWeek, dayOfMonth=dayOfMonth, month=month, year=year, info="integracion")
//	@Schedule(second = "0", minute = "0", hour = "*", dayOfWeek = "*", dayOfMonth = "*", month = "*", year = "*", info = "RecepcionDocumentos")
	public void procesoActualizarRecaudo()
	   {
			
	     try {
	    	 log.info("*** iniciando jobs integracion  ***");
					
	 		try{
	 			CredencialDS credencialDSOfi = new CredencialDS();
	 			credencialDSOfi.setDatabaseId(Constantes.BASE_PRS6);
	 			credencialDSOfi.setUsuario(Constantes.USUARIO_PRS6);
	 			credencialDSOfi.setClave(Constantes.CLAVE_PRS6);
	 			credencialDSOfi.setRegion(Constantes.REGION_PRS6);
	 			credencialDSOfi.setDirectorio(Constantes.DIRECTORIO_TS_NAME_PRS6);		
	 			Connection connoficina = null;	
				connoficina = Conexion.obtenerConexionFybeca(credencialDSOfi);
	 			
	 			String url_ws= parametroDAO.obtenerParametro(connoficina,"url_ws_scheduler_facturador");
	 			
	 			URL url = new URL(url_ws);
	 			URLConnection con = url.openConnection();
	 			HttpURLConnection http = (HttpURLConnection)con;
	 			http.setRequestMethod("GET"); // PUT is another valid option
	 			http.setUseCaches(false);
	 			http.setAllowUserInteraction(false);
	 			System.out.println(http.getResponseCode());
	 			System.out.println(http.getResponseMessage());
	 			System.out.println(http.getOutputStream());
		    	log.info(http.getResponseCode());
		    	log.info(http.getOutputStream());

	 			
	 		} catch (Exception e) {
	 			log.error(e.getMessage()+" "+e.getLocalizedMessage());
	 		}     	 
	    	 
	
	 
	    }
	     catch (Exception e)
	     {
	    	 log.info("*** iniciando jobs integrcion  ***");
	    	 log.info("No ejecuta integrcion ...  archivo no esta configurado");
	     }
	   }
	

	
	public String ConsumoWSCancelacionMDT(VORequestCancelacion  request) {
		
		String response ="No se ejecutó el servicio";
		CredencialDS credencialDSOfi = new CredencialDS();
		credencialDSOfi.setDatabaseId(Constantes.BASE_PRS6);
		credencialDSOfi.setUsuario(Constantes.USUARIO_PRS6);
		credencialDSOfi.setClave(Constantes.CLAVE_PRS6);
		credencialDSOfi.setRegion(Constantes.REGION_PRS6);
		credencialDSOfi.setDirectorio(Constantes.DIRECTORIO_TS_NAME_PRS6);		
		Connection connoficina = null;									
		try{				
			connoficina = Conexion.obtenerConexionFybeca(credencialDSOfi);
			String url_ws= parametroDAO.obtenerParametro(connoficina,"url_ws_cancelacion");
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
			response = sb.toString();
			System.out.println(response);

//			BigDecimal numeroEnvios= new BigDecimal("1");
//			CancelarFactura cancelarFactura = new CancelarFactura();
//			cancelarFactura.setDocumentoVenta(facturaRequest.getDocumentoVenta());
//			cancelarFactura.setFarmacia(facturaRequest.getFarmacia());
//			cancelarFactura.setOrdenSfcc(facturaRequest.getOrdenSfcc());
//	        Date fechaSistema= new Date();
//			long tiempofechaSistema = fechaSistema.getTime();
//	        java.sql.Date date = new java.sql.Date(tiempofechaSistema);				
//			
//			
//			cancelarFactura.setFechaRequest(date);
//			cancelarFactura.setJsonRequest(facturaRequest.getJsonRequest());
//			cancelarFactura.setMensajeRequest(facturaRequest.getMensajeRequest());
//			cancelarFactura.setTrazaId(facturaRequest.getTrazaID());
//			cancelarFactura.setFechaInserta(facturaRequest.getFechaInserta());
//			cancelarFactura.setUsuarioInserta(facturaRequest.getUsuarioInserta());
//			cancelarFactura.setJsonResponse(facturaRequest.getJsonResponse());
//			cancelarFactura.setFechaResponse(facturaRequest.getFechaResponse());
//			cancelarFactura.setFechaActualiza(facturaRequest.getFechaResponse());
//			cancelarFactura.setUsuarioActualiza("-");
//			System.out.println(numeroEnvios);
//			
//			
//			BigDecimal nuevo;
//			if (numeroEnvios == null){
//				 nuevo = new BigDecimal("1") ;
//			}
//			else {
//				nuevo= numeroEnvios.add(new BigDecimal("1"));
//			}
//			
//			cancelarFactura.setNumeroEnvios(nuevo);
//			cancelarFactura.setId(cancelarFacturaDAO.obtenerSecuencia(connPrs6));
//			
//			try {
//				cancelarFacturaDAO.insertRegistroCancelarFactura(connPrs6,cancelarFactura);
//				respuesta.setCode("200");
//				respuesta.setMsg("Se realizó la persistencia correctamente");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				respuesta.setCode("502");
//				respuesta.setMsg(e.getMessage());
//				
//			}			
			
			
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
		return response;
				

	}	
	
	public static String obtenerParametro(Connection conn, String clave) {
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" SELECT VALOR FROM fa_parametros_facturador WHERE CLAVE=? ");
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
		
		return respuesta.get(0);			
		
		
		
	}	
	
	
}
