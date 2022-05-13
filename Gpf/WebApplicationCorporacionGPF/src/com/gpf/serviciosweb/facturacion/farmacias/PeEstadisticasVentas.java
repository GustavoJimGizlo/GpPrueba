
package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.EnviarCorreo;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.PeEstadisticasVentasBean;
import com.serviciosweb.gpf.facturacion.servicios.Farmacia;


/**
 * @date 11/07/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
public class PeEstadisticasVentas extends Thread{
    //private PreparedStatement ps = null;
	
	String origen;
	String destino;
	String periodoInicio;
	String periodoFin; 
	String tipoNegocio;
	String email;
	private static final Logger log = Logger.getLogger(PeEstadisticasVentas.class.getName());
	
	public static void main(String[] args) {
    	new PeEstadisticasVentas("1562", "2002", "18", "26", "T","msdelgados@corporaciongpf.com");
	}
    
    public PeEstadisticasVentas(String origen, String destino,String periodoInicio, String periodoFin, String tipoNegocio,String email) {
    	this.origen=origen;
    	this.destino=destino;
    	this.periodoInicio=periodoInicio;
    	this.periodoFin=periodoFin;
    	this.tipoNegocio=tipoNegocio;
    	this.email=email;
    	System.out.println("origen  "+origen+" destino  "+destino+"  periodoInicio "+periodoInicio+"  periodoFin "+periodoFin+" tipoNegocio  "+tipoNegocio);
    }
    
    @Override
	public void run(){		
    	try{
			log.info("Inicia la extraccion de datos "+new Date());
			Farmacia farmaciaOrigen=new Farmacia(origen);
			Farmacia farmaciaDestino=new Farmacia(destino);
			ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSidSidDataBase(farmaciaOrigen.getDatabaseSid());
			String queryBusqueda="SELECT "+destino+" AS FARMACIA, E.ITEM,   E.NUMERO_PERIODO,   E.VENTA "+
			    " FROM farmacias.pe_estadisticas_venta e, farmacias.pe_items_pedido p,  pr_items i " +
			    " WHERE     e.farmacia = "+origen+"  AND (((numero_periodo >="+periodoInicio+" OR numero_periodo <="+periodoFin+")  AND "+periodoInicio+
			    " >"+periodoFin+") OR (numero_periodo >= "+periodoInicio+" AND numero_periodo <= "+periodoFin+" AND "+periodoInicio+" < "+periodoFin+")) "+
			    " AND e.item = p.item  AND i.tipo_negocio = decode( '"+tipoNegocio+"', 'T', i.tipo_negocio, '"+tipoNegocio+"' ) AND p.item = i.codigo ";
			ResultSetMapper<PeEstadisticasVentasBean> resultSetMapper = new ResultSetMapper<PeEstadisticasVentasBean>();
			ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
			List<PeEstadisticasVentasBean> listadoPeEstadisticasVentasBean=resultSetMapper.mapRersultSetToObject(rs, PeEstadisticasVentasBean.class);
			conexionVarianteSid.cerrarConexion(rs);		
			log.info("Finaliza la extraccion de datos "+new Date()+" total registros origen "+listadoPeEstadisticasVentasBean.size());
			new EnviarCorreo("copiaestadisticas@corporaciongpf.com", this.email, "Empieza la copia de estadisticas "+farmaciaOrigen.getDatabaseSid()+"->"+farmaciaDestino.getDatabaseSid()
					         ,"El proceso finaliza de bajar la imformacion desde: "+farmaciaOrigen.getDatabaseSid()+", empieza el proceso de grbado en el local destino: "+farmaciaDestino.getDatabaseSid()
					         +",<br> el proceso puede demorar hasta 2 horas dependiendo de la calidad y carga de los enlcaces de internet de los locales");
			new EnviarCorreo("copiaestadisticas@corporaciongpf.com", "mftellor@corporaciongpf.com", "Empieza la copia de estadisticas "+farmaciaOrigen.getDatabaseSid()+"->"+farmaciaDestino.getDatabaseSid()
			         ,"El proceso finaliza de bajar la imformacion desde: "+farmaciaOrigen.getDatabaseSid()+", empieza el proceso de grbado en el local destino: "+farmaciaDestino.getDatabaseSid()
			         +"<br>, el proceso puede demorar hasta 2 horas dependiendo de la calida y carga de los enlcaces de internet de los locales");
	
			this.insertarDestino(farmaciaDestino, listadoPeEstadisticasVentasBean);
    	}catch(Exception e){
    		log.info("Error en la copia "+e.getMessage());
    		new EnviarCorreo("copiaestadisticas@corporaciongpf.com", this.email,"Falla la copia de estadisticas "+this.origen+"->"+this.destino,"Error : "+e.getMessage());
    		new EnviarCorreo("copiaestadisticas@corporaciongpf.com", "mftellor@corporaciongpf.com","Falla la copia de estadisticas "+this.origen+"->"+this.destino,"Error : "+e.getMessage());
    	}
    	this.interrupt();    	
	}
	
	private void insertarDestino(Farmacia farmaciaDestino, List<PeEstadisticasVentasBean> listadoPeEstadisticasVentasBean){
		int registrosTotales=0;
		int numeroRegistros=0;
		int registrosErrores=0;
		ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSidSidDataBase(farmaciaDestino.getDatabaseSid());
		try {
			conexionVarianteSid.getConn().setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		for(PeEstadisticasVentasBean pe:listadoPeEstadisticasVentasBean){
			try {
				//ps=null;
				//ps = conexionVarianteSid.getConn().prepareStatement("INSERT INTO farmacias.pe_estadisticas_venta (FARMACIA,ITEM,NUMERO_PERIODO,VENTA)" +
				//		" VALUES("+pe.getFarmacia()+","+pe.getItem()+","+pe.getNumeroPeriodo()+","+pe.getVenta()+")");
				/*
				String query= "INSERT WHEN (SELECT COUNT(*) FROM PE_ITEMS_PEDIDO WHERE ITEM="+pe.getItem()+")>0 THEN INTO farmacias.pe_estadisticas_venta " +
						"(FARMACIA,ITEM,NUMERO_PERIODO,VENTA)" +
						" VALUES("+pe.getFarmacia()+","+pe.getItem()+","+pe.getNumeroPeriodo()+","+pe.getVenta()+") ELSE INTO farmacias.pe_estadisticas_venta" +
						" (FARMACIA,ITEM,NUMERO_PERIODO,VENTA) VALUES(0,0,0,0) SELECT 1 FROM DUAL";
				*/
				String query= "INSERT INTO farmacias.pe_estadisticas_venta (FARMACIA,ITEM,NUMERO_PERIODO,VENTA)" +
			     		" SELECT "+pe.getFarmacia()+","+pe.getItem()+","+pe.getNumeroPeriodo()+","+pe.getVenta()
			     		+" FROM DUAL WHERE EXISTS (SELECT * FROM PE_ITEMS_PEDIDO WHERE ITEM="+pe.getItem()
			     		+" AND FARMACIA="+pe.getFarmacia()+") AND NOT EXISTS (SELECT * FROM farmacias.pe_estadisticas_venta WHERE ITEM="+pe.getItem()
			     		+" AND FARMACIA="+pe.getFarmacia()+" AND NUMERO_PERIODO="+pe.getNumeroPeriodo()+")";
				//ps = conexionVarianteSid.getConn().prepareStatement(query);

				numeroRegistros++;
		        //ps.executeUpdate();	
		        //ps.close();		        
		        registrosTotales++;
		        validarConexionVarianteSid(farmaciaDestino.getDatabaseSid(),conexionVarianteSid);
		          new PeEstadisticasVentasThread(conexionVarianteSid, query).start();
		        if(numeroRegistros>=1000){
		        	conexionVarianteSid.getConn().commit();
		        	numeroRegistros=0;
		        	log.info(new Date()+" COMMIT "+registrosTotales);
		    		//new EnviarCorreo("copiaestadisticas@corporaciongpf.com", "msdelgados@corporaciongpf.com", "Copia","HACE COMIT 1000 REGISTROS, TOTAL PRTOCESADOS"+registrosTotales);
		    		//new EnviarCorreo("copiaestadisticas@corporaciongpf.com", "mftellor@corporaciongpf.com", "Copia","HACE COMIT 1000 REGISTROS, TOTAL PRTOCESADOS"+registrosTotales);

		        }
			} catch (SQLException e) {
				e.printStackTrace();
				log.info("Error al copiar estadistica :"+e.getMessage());
			}
			
		}
		try{
			conexionVarianteSid.getConn().commit();
			conexionVarianteSid.getConn().commit();
			conexionVarianteSid.cerrarConexion();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("FIN  2 "+new Date()+" registrosTotales "+registrosTotales+" registrosErrores "+registrosErrores);		
		new EnviarCorreo("copiaestadisticas@corporaciongpf.com", this.email, "Finaliza la copia de estadisticas ->"+farmaciaDestino.getDatabaseSid(),"Finaliza la copia de estadisticas ->"+farmaciaDestino.getDatabaseSid());
		new EnviarCorreo("copiaestadisticas@corporaciongpf.com", "mftellor@corporaciongpf.com", "Finaliza la copia de estadisticas ->"+farmaciaDestino.getDatabaseSid(),"Finaliza la copia de estadisticas ->"+farmaciaDestino.getDatabaseSid());		
	}
    public void validarConexionVarianteSid(String sidDataBase, ConexionVarianteSid conexionVarianteSid) {
        if(!conexionVarianteSid.isValid())
        	conexionVarianteSid=obtenerNuevaConexionVarianteSidSidDataBase(sidDataBase);	        	        
    }

    public ConexionVarianteSid  obtenerNuevaConexionVarianteSidSidDataBase(String sidDataBase){
        try {
           return new ConexionVarianteSid(sidDataBase);
       } catch (NamingException ex) {
           Logger.getLogger(PeEstadisticasVentas.class.getName()).log(Level.SEVERE, null, ex);
       } catch (Exception ex) {
           Logger.getLogger(PeEstadisticasVentas.class.getName()).log(Level.SEVERE, null, ex);
       }
      return null;
   }
}
