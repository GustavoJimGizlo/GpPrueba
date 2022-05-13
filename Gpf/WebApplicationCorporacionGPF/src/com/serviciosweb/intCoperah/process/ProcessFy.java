package com.serviciosweb.intCoperah.process;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gpf.postg.pedidos.util.Constantes;
import com.serviciosweb.intCoperah.db.ConnectionFybeca;
import com.serviciosweb.intCoperah.ws.ArrayOfDouble;
import com.serviciosweb.intCoperah.ws.ArrayOfInfoTransaccional;
import com.serviciosweb.intCoperah.ws.ArrayOfString;
import com.serviciosweb.intCoperah.ws.InfoCarga;
import com.serviciosweb.intCoperah.ws.InfoRespuesta;
import com.serviciosweb.intCoperah.ws.InfoTransaccional;
import com.serviciosweb.intCoperah.ws.ServicioIntegracionTransaccional;
import com.serviciosweb.intCoperah.ws.ServicioIntegracionTransaccionalSoap;

public class ProcessFy extends Thread{

	@Override
	public void run() {
		
		ejecutaProceso();
	 
	}
	
	public void ejecutaProceso(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaIni, fechaFin, type;
		Calendar calendar = Calendar.getInstance();
		Date fechaAc = new Date();
		calendar.setTime(fechaAc);
		calendar.add(Calendar.DAY_OF_YEAR, -3);
		System.out.println(sdf.format(calendar.getTime()));
		fechaIni = sdf.format(calendar.getTime());
		calendar.setTime(fechaAc);
		System.out.println(sdf.format(calendar.getTime()));
		fechaFin = sdf.format(calendar.getTime());
		
		//fechaIni = "02/03/2014";
		//fechaFin = "03/03/2014";
		type = "D";
		ConnectionFybeca CF = new ConnectionFybeca();
		try {
			CallableStatement cst = CF.ConectToFybeca().prepareCall("{call integraciones.int_pkg_integrac.INT_SP_CREATECURTRANSCO(?,?,?,?,?)}");
			cst.setString(1, fechaIni);
		    cst.setString(2, fechaFin);
		    cst.setString(3, type);
		    cst.registerOutParameter(4, java.sql.Types.VARCHAR);
		    cst.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR );
		    cst.executeUpdate();
		  
		    ResultSet result =(ResultSet) cst.getObject(5);
		    
		    InfoCarga carga = new InfoCarga();
		    
		    String[] infoTransaccional = CF.getDataWS();
			Date fechaA = new Date();
			
			carga.setIdentificadorCliente(Integer.valueOf(infoTransaccional[0]));
			carga.setCodigoSeguridad(infoTransaccional[1]);
			
			carga.setDescripcion(infoTransaccional[2]+" " +sdf.format(fechaA));
			carga.setFechaInicio(fechaIni);
			carga.setFechaTermino(fechaFin);
			
			ArrayOfInfoTransaccional listTransaccional = new ArrayOfInfoTransaccional();
			
		    while (result.next()) {
		    	InfoTransaccional info = new InfoTransaccional();
		    	
		    	info.setCodigoEstablecimiento(result.getString(1));
		    	info.setNombreEstablecimiento(result.getString(2));
		    	info.setUnidadComercial(result.getString(3));
		    	info.setDriverComercial(result.getString(4));
		    	info.setCanal(result.getString(5));
		    	info.setFecha(result.getString(6).split(" ")[0]);
		    	
		    	ArrayOfDouble listValores = new ArrayOfDouble();
		    	for (int i = 7; i < 103; i++) {
					listValores.getDouble().add(Double.valueOf(result.getString(i)));
				}
		    	
		    	info.setValores(listValores);
		    	
		    	listTransaccional.getInfoTransaccional().add(info);
		    	
		    }
		    
		    
		    carga.setInfoTransaccional(listTransaccional);
		    
		    procesoCarga(carga);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void procesoCarga(InfoCarga carga){
		ServicioIntegracionTransaccional service = new ServicioIntegracionTransaccional();
		
		ServicioIntegracionTransaccionalSoap port = service.getServicioIntegracionTransaccionalSoap();
		
		InfoRespuesta response = port.procesar(carga);
		
		String estado = response.getEstado();
		int codCarga = response.getIdCarga();
		List<String> detallesR = response.getDetalle().getString();
		
		ConnectionFybeca nConn = new ConnectionFybeca();
		
		nConn.saveResponse("TRANSACCIONAL FY", estado, detallesR , codCarga);
		
		if(response.getEstado().equals("ERROR")){
			System.out.println("ERROR FY");
			ArrayOfString detalle = new ArrayOfString();
			detalle = response.getDetalle();
			List<String> detalles = new ArrayList<String>();
			detalles = detalle.getString();
			if(detalles.size()>0){
				for (String det : detalles) {
					System.out.println("DETALLE FY: "+det);
				}
				
			}
		}else{
			System.out.println("RESPUESTA FY: "+response.getEstado());
			ArrayOfString detalle = new ArrayOfString();
			detalle = response.getDetalle();
			List<String> detalles = new ArrayList<String>();
			detalles = detalle.getString();
			if(detalles.size()>0){
				for (String det : detalles) {
					System.out.println("DETALLE FY: "+det);
				}
				
			}
			System.out.println("ID CARGA FY: "+response.getIdCarga());
		}
	}
}
