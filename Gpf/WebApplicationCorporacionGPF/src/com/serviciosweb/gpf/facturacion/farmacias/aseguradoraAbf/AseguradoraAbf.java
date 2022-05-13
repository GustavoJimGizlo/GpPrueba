package com.serviciosweb.gpf.facturacion.farmacias.aseguradoraAbf;

import java.sql.Clob;
import java.sql.ResultSet;
import java.util.Date;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AseguradoraAbfBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.RespuestaBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.jboss.logging.Logger;


public class AseguradoraAbf extends ObtenerNuevaConexion {
	 public static Logger log=Logger.getLogger(AseguradoraAbf.class);
	private String  resultadoXml=Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS");
	private final static Integer PARAMETRO_BDD=51; // 51  prod 44 prod_desa
	private final static Integer PARAMETRO_BDD_PROD=1; // 1 prod 44 prod_desa
	private final static String PARAMETRO_USUARIO ="RECETAABF";
	private final static String PARAMETRO_CLAVE="r3c3t4BF.2014";
	private XStream xstream = new XStream(new StaxDriver());

	public AseguradoraAbf(String identificacion  ,String numeroContrato , String codigoDependiente,String aseguradora){
		super(AseguradoraAbf.class);

		AseguradoraAbfBean respuesta = conslutarAseguradorasAbf( identificacion, numeroContrato ,  codigoDependiente,"");
		RespuestaBean repuestaEnvio = new RespuestaBean();
		  log.info("ValidaPlanAbf:Plan Activo:"+respuesta.getPlanActivo()+":Mora:"+ respuesta.getMora()+":"+new Date());
		if(null == respuesta.getPlanActivo()  &&  null == respuesta.getMora()){
			repuestaEnvio.setActivo("S");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		}else if("S".equals(respuesta.getPlanActivo())  &&  "N".equals(respuesta.getMora())){
			repuestaEnvio.setActivo("S");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		}else{
			repuestaEnvio = mensajeAseguradorasAbf(numeroContrato ,"1"); 
			repuestaEnvio.setActivo("N");
		}
		
		xstream.alias("Aseguradora", RespuestaBean.class);
		this.resultadoXml=xstream.toXML(repuestaEnvio);
		this.resultadoXml=Constantes.respuestaXmlObjeto("OK",this.resultadoXml);
		resultadoXml=xstream.toXML(repuestaEnvio);
	}
	
	public AseguradoraAbf(String identificacion,String numeroContrato , String codigoDependiente,String codigoAseguradora, String diagnostico){
		super(AseguradoraAbf.class);

		AseguradoraAbfBean respuesta = conslutarAseguradorasAbf(identificacion, numeroContrato,codigoDependiente,codigoAseguradora);
		RespuestaBean repuestaEnvio = new RespuestaBean();
		log.info("ValidaCarenciaAbf:Plan Activo:"+respuesta.getPlanActivo()+":Carencia:"+ respuesta.getCarenciaTiempo()+":"+new Date());
		if(null == respuesta.getPlanActivo()  &&  null == respuesta.getCarenciaTiempo()){
			repuestaEnvio.setActivo("S");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		}else if("S".equals(respuesta.getPlanActivo())  &&  "N".equals(respuesta.getCarenciaTiempo()) && validaDiagnostico(respuesta.getPreexistenciaTiempo(), diagnostico)){
			repuestaEnvio.setActivo("S");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		}else{
			repuestaEnvio = mensajeAseguradorasAbf(numeroContrato ,"2"); 
			repuestaEnvio.setActivo("N");
		}
		xstream.alias("Aseguradora", RespuestaBean.class);
		this.resultadoXml=xstream.toXML(repuestaEnvio);
		this.resultadoXml=Constantes.respuestaXmlObjeto("OK",this.resultadoXml);
		resultadoXml=xstream.toXML(repuestaEnvio);
	}
	public AseguradoraAbf(String numeroContrato ,String aseguradora,String monto){
		super(AseguradoraAbf.class);
		RespuestaBean repuestaEnvio = new RespuestaBean();
		
		if(validarTope(aseguradora,numeroContrato,monto)){
			repuestaEnvio.setActivo("S");
			repuestaEnvio.setCodigoMensaje("");
			repuestaEnvio.setDescripcionMensaje("");
		}else{
			repuestaEnvio = mensajeAseguradorasAbf(numeroContrato ,"3"); 
			repuestaEnvio.setActivo("N");
		}
		xstream.alias("Aseguradora", RespuestaBean.class);
		this.resultadoXml=xstream.toXML(repuestaEnvio);
		this.resultadoXml=Constantes.respuestaXmlObjeto("OK",this.resultadoXml);
		resultadoXml=xstream.toXML(repuestaEnvio);
	}
	
	public boolean validarTope(String cliente ,String numeroContrato ,String monto){
		boolean resultado =false;
		
		
		ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidSidDataBase(PARAMETRO_BDD_PROD,"WEBLINK","weblink_2013");
		try {
            String querySql=" select cp_num3"
            		+ " from fa_abf_parametros_generales "
            		+ " where cliente = "+cliente
            		+ " and contrato = "+ numeroContrato;
          
            ResultSet rs = conn.consulta(querySql);
            Double cpNum3 =0.0;
            while(conn.siguiente(rs)){
            	cpNum3 = conn.getDouble(rs, "cp_num3");
            }
            
            log.info("Valida Tope Abf: Tope :"+ cpNum3 +": monto  :"+ monto+":  "+new Date());
            
            if(cpNum3<=Double.parseDouble(monto) || cpNum3 == 0)
            	return true;
            
            
        }catch (Exception ex) {
        	ex.getStackTrace();
        	System.out.println("Error "+ex.getMessage());
        }
		conn.cerrarConexion();
		return  resultado;
	}
	
	public String conslutarCliente(String numeroContrato){
		String resultado ="";
		ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidSidDataBase("abf.uio","backoffice","boec593");
		try {
            String querySql="select id_clie"
            		+ " from clientes"
            		+ " where id_clie in("
            		+ " select tc_id_clie"
            		+ " from contratos"
            		+ " where id_cont = "+numeroContrato+")";
          
            ResultSet rs = conn.consulta(querySql);
            while(conn.siguiente(rs)){
            	resultado = conn.getString(rs, "id_clie");
            }
        }catch (Exception ex) {
        	ex.getStackTrace();
        	System.out.println("Error "+ex.getMessage());
        }
		conn.cerrarConexion();
		return  resultado;
	}
	
	
	
	private boolean validaDiagnostico(String preexistenciaTiempo,String diagnostico){
		if(preexistenciaTiempo == null)
			return true;
		if(preexistenciaTiempo.indexOf(",") > 0 ){
			for(String datos: preexistenciaTiempo.split(",")){
				if(datos.equals(diagnostico)){
					return false;
				}
			}
		}else{
			if(preexistenciaTiempo.equals(diagnostico)){
				return false;
			}
		}
		return true;
	}
	
	
	
	public AseguradoraAbfBean conslutarAseguradorasAbf(String identificacion ,String numeroContrato , String codigoDependiente, String aseguradora){
		AseguradoraAbfBean resultado = new AseguradoraAbfBean();
		ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidSidDataBase(PARAMETRO_BDD,PARAMETRO_USUARIO,PARAMETRO_CLAVE);
		//ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidMaster(PARAMETRO_BDD.toString());
		try {
			
			if(!"".equals(aseguradora)){
				aseguradora = " and aseguradora = '"+aseguradora+"'";
			}
			
			String filtroNomina = "";
			
			String codigoPersona = codigoPersona(identificacion );
			
			if("".equals(codigoPersona) || codigoPersona == null  )
				codigoPersona = codigoPersonaAbBeneficiariosAbf(identificacion );
			
			String codigoNomina = "";
			if(codigoPersona !=null  && !"".equals(codigoPersona))
			   codigoNomina = codigoNomina(numeroContrato, codigoPersona );
			
			if(!"".equals(codigoNomina)) 
				filtroNomina = " and nomina = '"+codigoNomina+"'";
			
			
            String querySql="select plan_activo,mora,paciente_activo,carencia_tiempo,preexistencia_tiempo"
            		+ " from RE_ASEGURADORAS_ABF"
            		+ " where identificacion ='"+identificacion+"'"
            		+ " and numero_contrato ='"+numeroContrato+"'"
            		+ " and codigo_dependiente ='"+codigoDependiente+"'"
            		+ filtroNomina;
          
            ResultSet rs = conn.consulta(querySql);
            while(conn.siguiente(rs)){
            	resultado.setPlanActivo(conn.getString(rs, "plan_activo"));
            	resultado.setMora(conn.getString(rs, "mora"));
            	resultado.setPacienteAtivo(conn.getString(rs, "paciente_activo"));
            	resultado.setCarenciaTiempo(conn.getString(rs, "carencia_tiempo"));
            	
            	
            	   Clob clob = rs.getClob("preexistencia_tiempo");
            	   if (clob != null) {  
            	    String clobStr = clob.getSubString(1, (int) clob.length());
            	    resultado.setPreexistenciaTiempo( clobStr);
            	   }
            	   
            }
        }catch (Exception ex) {
        	ex.getStackTrace();
        	System.out.println("Error "+ex.getMessage());
        }
		
		conn.cerrarConexion();
		return  resultado;
	}
	
	
	public String codigoPersona(String identificacion ){
		String resultado = "";
		//ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidSidDataBase(PARAMETRO_BDD_PROD,PARAMETRO_USUARIO,PARAMETRO_CLAVE);
		
		 ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidSidDataBase(PARAMETRO_BDD_PROD,"WEBLINK","weblink_2013");
		//ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidMaster("1");
		try {

			String querySql="select codigo from ab_personas where identificacion ='"+identificacion+"'";
          
            ResultSet rs = conn.consulta(querySql);
            while(conn.siguiente(rs)){
            	resultado = conn.getBigDecimal(rs, "codigo").toString();
            }
        }catch (Exception ex) {
        	ex.getStackTrace();
        	System.out.println("Error "+ex.getMessage());
        	
        	return  null;
        }
		
		conn.cerrarConexion();
		return  resultado;
	}
	
	
	
	public String codigoPersonaAbBeneficiariosAbf(String identificacion ){
		String resultado = "";
		//ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidSidDataBase(PARAMETRO_BDD,PARAMETRO_USUARIO,PARAMETRO_CLAVE);
		//ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidMaster("1");
		 ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidSidDataBase(PARAMETRO_BDD_PROD,"WEBLINK","weblink_2013");
		try {

			String querySql=" SELECT PERSONA_TITULAR FROM ab_beneficiarios_abf "
						 + "  WHERE IDENTIFICACION ='"+identificacion+"'"
						 + "  AND  ROWNUM =1 ";
          
            ResultSet rs = conn.consulta(querySql);
            while(conn.siguiente(rs)){
            	resultado = conn.getBigDecimal(rs, "PERSONA_TITULAR").toString();
            }
        }catch (Exception ex) {
        	ex.getStackTrace();
        	System.out.println("Error "+ex.getMessage());
        	
        	return  null;
        }
		
		conn.cerrarConexion();
		return  resultado;
	}

	
	
	public String codigoNomina(String codContrato, String codPersona ){
		String resultado = "";
		ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidMaster("52");
		try {

			String querySql=" select  num_nomi from certificados"
						  + " where cp_con_id_cont ="+codContrato
					      + " and cod_persona = "+codPersona;
          
            ResultSet rs = conn.consulta(querySql);
            while(conn.siguiente(rs)){
            	resultado = conn.getString(rs, "num_nomi");
            }
        }catch (Exception ex) {
        	ex.getStackTrace();
        	System.out.println("Error "+ex.getMessage());
        }
		
		conn.cerrarConexion();
		return  resultado;
	}

	
	
	public RespuestaBean mensajeAseguradorasAbf(String contrato ,String codigoMensajeWeb){
		
		   RespuestaBean resultado = new RespuestaBean();
			boolean errorCosulta  = true;
			  ConexionVarianteSid conn=obtenerNuevaConexionVarianteSidSidDataBase(PARAMETRO_BDD_PROD,"WEBLINK","weblink_2013");
			try {
	          
	            String querySql="select cod_mensaje, desc_mensaje from ABF_MENSAJES_ASEGURADORA "
	            		+ " where contrato = "+contrato.trim()
	            		+ " and cod_mensaje_web="+codigoMensajeWeb.trim();
	          
	            ResultSet rs = conn.consulta(querySql);
	            while(conn.siguiente(rs)){
	            	resultado.setCodigoMensaje(conn.getString(rs, "cod_mensaje"));
	            	resultado.setDescripcionMensaje(conn.getString(rs, "desc_mensaje"));
	             	errorCosulta= false;
	            }
	        }catch (Exception ex) {
	        	ex.getStackTrace();
	            System.out.println("Error "+ex.getMessage());
	        }
			if(errorCosulta){
				resultado.setCodigoMensaje("0");
	        	resultado.setDescripcionMensaje("No existen mensajes de error con los parametros de busqueda");
			}
			
			conn.cerrarConexion();
			
			return  resultado;
			
		}
	
		
	public String getResultadoXml() {
		if(resultadoXml!=null)
			return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
		else
			return  Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA") ;
	}	


}
