package com.gpf.partnersGroupGPF.servicios;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.gpf.GPFSSO;
import com.gpf.partnersGroupGPF.bean.AbCamposActualizados;
import com.gpf.partnersGroupGPF.bean.AbDatosPersonas;
import com.gpf.partnersGroupGPF.bean.AbDirecciones;
import com.gpf.partnersGroupGPF.bean.AbLogin;
import com.gpf.partnersGroupGPF.bean.AbMediosContacto;
import com.gpf.partnersGroupGPF.bean.AbPersonas;
import com.gpf.partnersGroupGPF.bean.AbRoles;
import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.aseguradoraAbf.AseguradoraAbf;
import com.serviciosweb.gpf.facturacion.farmacias.bean.VcCupoClientesBean;

import flexjson.JSONSerializer;



public class PartnersGroupGpf  extends ObtenerNuevaConexion {
	
	private List<AbPersonas> respuesta  = new ArrayList<AbPersonas>();
	
	private AbLogin respuestaLogin  = new AbLogin();
	
	private AbCamposActualizados respuestaActulizacion = new AbCamposActualizados();
	
	private static String tipoConexion="1";
	
	
	private Integer seqAbPer=0;
	private String  resultadoXml= serializar(respuesta, "datosPersona", "Error al ejecutar el procedimiento", "504");
	
	private String mesajeError = "";
	
	Logger log=Logger.getLogger(PartnersGroupGpf.class.getName());    
	
	
	    public PartnersGroupGpf(){
	    	 super(PartnersGroupGpf.class);
	    }
		public PartnersGroupGpf(String identificacion, String telefono , String claveAcceso){
			 super(PartnersGroupGpf.class);
			 respuesta  = new ArrayList<AbPersonas>();
			 if( validarClave(claveAcceso)){
				 resultadoXml = serializar(this.respuesta, "datosPersona", "Error la clave de acceso no es valida", "404");
	            return;
	        }
			 String querySql ="";
			 String tablaAbMediosContato =" ";
			 String filtroIdentificacion =" ";
			 String filtroTelefono =" ";
			 String mensajeJson = "";
			 if(("".equals(identificacion) || "null".equals(identificacion) || identificacion == null  )&&("".equals(telefono) || "null".equals(telefono) || telefono == null  )){
				 resultadoXml = serializar(this.respuesta, "datosPersona", "Error al menos debe  ingresar un parametro de busqueda", "504");
				 return;
				 
			 }
			ResultSetMapper<AbPersonas> resultSetMapper=new ResultSetMapper<AbPersonas>();
			ConexionVarianteSid conn=obtenerNuevaConexionVarianteSid(tipoConexion);
			try {
				 if(!"".equals(identificacion) && !"null".equals(identificacion) && identificacion != null  ){
					 filtroIdentificacion = " and  identificacion = '"+ identificacion+"'";
					 mensajeJson="Cedula consultada:"+identificacion;
				 }
				 if(!"".equals(telefono) && !"null".equals(telefono) && telefono != null  ){
					 tablaAbMediosContato =", AB_MEDIOS_CONTACTO a";
					 filtroTelefono=" and A.PERSONA = B.CODIGO  AND valor = '"+telefono+"'";
					 mensajeJson="Telefono consultado:"+telefono;
				 }
				 querySql= "SELECT b.CODIGO,"
					 		+ " b.TIPO_PERSONA,"
					 		+ " b.IDENTIFICACION,"
					 		+ " b.TIPO_IDENTIFICACION,"
					 		+ " b.PRIMER_NOMBRE,"
					 		+ " b.SEGUNDO_NOMBRE,"
					 		+ " b.PRIMER_APELLIDO,"
					 		+ " b.SEGUNDO_APELLIDO,"
					 		+ " b.FECHA_NACIMIENTO,"
					 		+ " b.FECHA_CREACION,"
					 		+ " b.RAZON_SOCIAL,"
					 		+ " b.REPRESENTANTE_LEGAL,"
					 		+ " b.IDENTIFICACION_REP_LEGAL,"
					 		+ " b.TIPO_IDENTIFICACION_REP_LEGAL, "
					 		+ " b.NOMBRE_COMERCIAL "
					 		+ " FROM  ab_personas b  "
					 		+ tablaAbMediosContato
					 		+ " WHERE  1=1 "
					 		+ filtroTelefono
					 		+ filtroIdentificacion;

				 ResultSet rs = conn.consulta(querySql);
				 List<AbPersonas> listPersonas = resultSetMapper.mapRersultSetToObject(rs, AbPersonas.class);
				 
				 if(listPersonas == null){
					 resultadoXml = serializar(this.respuesta, "datosPersona", "No existen datos para el/los valores consultados", "404");
					 return;
				 }
	            
				 for (AbPersonas persona : listPersonas ){
					 ResultSetMapper<AbDirecciones> resultSetMapperDir=new ResultSetMapper<AbDirecciones>();
					 String query=" SELECT abd.CODIGO,"
	            			+ " PRINCIPAL,"
	            			+ " NUMERO,"
	            			+ " INTERSECCION,"
	            			+ " REFERENCIA,"
	            			+ " TIPO ,"
	            			+ " ab.nombre as BARRIO,"
					 		+ " adc.nombre as CIUDAD"
	            			+ " FROM AB_DIRECCIONES abd, ad_barrios ab, AD_CIUDADES adc"
	            			+ " where abd.barrio=ab.codigo and abd.ciudad=adc.codigo and persona = " + persona.getCodigo();
					 ResultSet rss = conn.consulta(query);
					 List<AbDirecciones> listDirecciones = resultSetMapperDir.mapRersultSetToObject(rss, AbDirecciones.class);
					 persona.setDirecciones(listDirecciones);
					 ResultSetMapper<AbMediosContacto> resultSetMapperMed=new ResultSetMapper<AbMediosContacto>();
					 String queryMed=" SELECT"
							 + " CODIGO,"
							 + " VALOR,"
							 + " TIPO"
							 + " FROM"
							 + " AB_MEDIOS_CONTACTO"
							 + " WHERE PERSONA = " + persona.getCodigo();
					 ResultSet rsMediosContacto = conn.consulta(queryMed);
					 List<AbMediosContacto> listMediosContacto= resultSetMapperMed.mapRersultSetToObject(rsMediosContacto, AbMediosContacto.class);
					 persona.setMediosContacto(listMediosContacto);
					 this.respuesta.add(persona);
				 }

				 resultadoXml=serializar(this.respuesta, "datosPersona", mensajeJson, "104");
				 log.info("resultadoXml :"+resultadoXml);
	        }catch (Exception ex) {
	        	ex.getStackTrace();
	        	System.out.println("Error "+ex.getMessage());
	        }
			conn.cerrarConexion();

		}
		
		
		
		
		
		
	
		

		public PartnersGroupGpf(String usuario, String clave , String claveAcceso, String openSSo ){
			
			 super(AseguradoraAbf.class);
			 
			 respuesta  = new ArrayList<AbPersonas>();
			 if( validarClave(claveAcceso)){
				 resultadoXml = serializar(this.respuesta, "loginEmpleado", "Error la clave de acceso no es valida", "404");
				 return;
	        }
			
		  
			 if(("".equals(usuario) || "null".equals(usuario) || usuario == null  )&&("".equals(clave) || "null".equals(clave) || clave == null  )){
				 resultadoXml = serializar(this.respuestaLogin, "loginEmpleado", "Todos los campos son obligatorios", "504");
				 return;
				 
			 }
			 ConexionVarianteSid conn=obtenerNuevaConexionVarianteSid("1");
			 
			 try{

				 if(validarToken(usuario , clave)){
					 List<AbRoles> listDirecciones =  new ArrayList<AbRoles>();
					 respuestaLogin.setLogin("N");
					 respuestaLogin.setRoles(listDirecciones);
					 resultadoXml = serializarLogin(this.respuestaLogin, "loginEmpleado", "Empleado consultad@: "+usuario, "104");
					 return;
				 }
				 
				 
				
				 
				 ResultSetMapper<AbRoles> resultSetMapperDir=new ResultSetMapper<AbRoles>();
				 String query=" SELECT codigo_rol"
				 		+ " FROM WF_ROLES_USUARIO"
				 		+ " WHERE nombre_usuario = '"+usuario+"'";
				 ResultSet rss = conn.consulta(query);
				 List<AbRoles> listRoles = resultSetMapperDir.mapRersultSetToObject(rss, AbRoles.class);
				 
				 
				 
				 List<AbRoles> resList = new ArrayList<AbRoles>();
				 
				 
				 
				 
				 //String  codificarRol ="1&32#2&33#3&34#4&35#5&36";
				String  codificarRol ="94&1#95&2#96&3#97&4#98&5";
				 
				
				 for (AbRoles roles : listRoles) {
					 if(roles.getCodRol()>= 94 && roles.getCodRol() <=98){
						 for (String rol : codificarRol.split("#")) {
							 String role[] = rol.split("&");
							 if (role[0].equals(String.valueOf(roles.getCodRol()))) {
								 AbRoles datoRol = new AbRoles();
								 datoRol.setCodRol(Long.parseLong(role[1]));
								 resList.add(datoRol);
							 }
						 }
					 }
				 }
				 
				 respuestaLogin.setLogin("S");
				 
				 respuestaLogin.setRoles(resList);
				 
				 resultadoXml=serializarLogin(this.respuestaLogin, "loginEmpleado", "Empleado consultad@: "+usuario, "104");
	        
			 }catch (Exception ex) {
	        	ex.getStackTrace();
	        	System.out.println("Error "+ex.getMessage());
	        }
			

				conn.cerrarConexion();
		}
		
		
		
		
		public PartnersGroupGpf(AbPersonas personas, AbDirecciones direcciones , AbMediosContacto mediosContacto, String paso, String claveAcceso){
			
			 super(AseguradoraAbf.class);
			 
			 respuesta  = new ArrayList<AbPersonas>();
			 if( validarClave(claveAcceso)){
				 resultadoXml = serializar(this.respuesta, "actualizacionInsercion", "Error la clave de acceso no es valida", "404");
				 return;
	        }
			
			 
			 
			 if("1".equals(paso)){
				 actulizarAbPersonas(personas);
				 return;
			 }
			 
			 if(("2".equals(paso) || "3".equals(paso)) && personas.getCodigo() == 0){
				 respuestaActulizacion.setCamposActualizados("N");
				 resultadoXml = serializar(this.respuestaActulizacion, "actualizacionInsercion", "Error CodigoPersona no puede ser null", "404");
				 return;
			 }

			 if("2".equals(paso)){
				 actulizarAbDirecciones(direcciones, personas.getCodigo());
				 return;
			 }
			 
			 if("3".equals(paso)){
				 actulizarAbMediosContacto(mediosContacto, personas.getCodigo());
				 return;
			 }
		  
			
		}
		
		private void actulizarAbPersonas(AbPersonas personas){
			log.info("actulizarAbPersonas");

			try {
				
				if(personas.getCodigo() == 0 ){
					
					 AbPersonas personaCon = consultarAbPersonas(0, personas.getIdentificacion());
					 
					 if(personaCon != null){ 
						 if(editarAbPersonas(subConsultaPersonas (personas,personaCon))){
							 respuestaActulizacion.setCodigo(personas.getCodigo());
							 respuestaActulizacion.setCamposActualizados("S");
							 resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", "Empleado consultad@: ", "104");
						 }else{
							 respuestaActulizacion.setCamposActualizados("N");
							 resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", mesajeError, "404");
						 }
						 return;
					 }
					 
					if(insertAbPersonas(personas)){
						 respuestaActulizacion.setCodigo(seqAbPer);
						 respuestaActulizacion.setCamposActualizados("S");
						 resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", "Empleado consultad@: ", "104");
					}else{
						respuestaActulizacion.setCamposActualizados("N");
						resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", mesajeError, "404");
					}
					
					return;
				}				
				
				if(editarAbPersonas(subConsultaPersonas (personas, consultarAbPersonas(personas.getCodigo(), "")))){
					
					respuestaActulizacion.setCodigo(personas.getCodigo());
					respuestaActulizacion.setCamposActualizados("S");
				}else{
					respuestaActulizacion.setCamposActualizados("N");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", "Empleado consultad@: ", "104");
		}
		
		
		
		

		public Boolean insertAbPersonas(AbPersonas personas) throws SQLException{
			  PreparedStatement ps = null;
	          ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);;
	          String queryInser=" Insert into AB_PERSONAS"
	          		+ " (CODIGO, TIPO_PERSONA, IDENTIFICACION, TIPO_IDENTIFICACION, PRIMER_NOMBRE,"
	          		+ " REPRESENTANTE_LEGAL, IDENTIFICACION_REP_LEGAL, TIPO_IDENTIFICACION_REP_LEGAL, NOMBRE_COMERCIAL,"
	          		+ "  SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO,USUARIO_CREA,FECHA_CREACION,FECHA_NACIMIENTO)"
	          		+ " Values (AB_PSA_SEQ.nextval, ?, ?, ?, ?,?, ?, ?, ?,?,?,?,'WEBLINK',SYSDATE,?)";
	          try {                              
	             
	              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
	              ps.setString(1,personas.getTipoPersona());
	              ps.setString(2,personas.getIdentificacion());
	              ps.setString(3, personas.getTipoIdentificacion());
	              ps.setString(4, personas.getPrimerNombre());
	              ps.setString(5, personas.getRepresentanteLegal());
	              ps.setString(6, personas.getIdentificacionRepLegal());
	              ps.setString(7, personas.getTipoIdentificacionRepLegal());
	              ps.setString(8, personas.getNombreComercial());
	              ps.setString(9, personas.getSegundoNombre());
	              ps.setString(10, personas.getPrimerApellido());
	              ps.setString(11, personas.getSegundoApellido());
	              java.util.Date utilDate = personas.getFechaNacimiento();
	              
	              if(utilDate != null){
	              java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	              ps.setDate(12, sqlDate);
	              }else{
	            	  ps.setDate(12, null);
	              }
	            
	              ps.executeUpdate();
	              
	  			
	              String queryBusqueda="SELECT AB_PSA_SEQ.CURRVAL as maximo FROM DUAL";
	  			
	              try{

	            	  ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
	            	  while(conexionVarianteSid.siguiente(rs)){
	            		  seqAbPer=conexionVarianteSid.getInt(rs, "maximo");
	            	  }
	            	  rs.close();
	              }catch(SQLException e){
	  				e.printStackTrace();
	  				
	  				mesajeError = e.getMessage();
	  			}
	              ps.close();
	            

	              conexionVarianteSid.cerrarConexion();
	              return true;
	           
	       } catch (SQLException e) {
	           if(e.getErrorCode()!=2291){
	               System.out.println("ErrorCode  "+e.getErrorCode());
	               
	               mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
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
	           }else{
	        	   mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
	           }
	           
	          // mesajeError = e.getMessage();
	           return false;
	       }
	         
		}		
		
		
        public AbPersonas subConsultaPersonas (AbPersonas datosEntrada, AbPersonas datosConsultados){
        	
        	AbPersonas resAbPersonas = new AbPersonas();
        	
        	
        	if(datosConsultados == null){
        		return datosEntrada;
        	}

        	resAbPersonas = datosEntrada;
        	
        	if("".equals(datosEntrada.getTipoPersona()) || datosEntrada.getTipoPersona() == null ){
        		resAbPersonas.setTipoPersona(datosConsultados.getTipoPersona());
        	}

        	if("".equals(datosEntrada.getIdentificacion()) || datosEntrada.getIdentificacion() == null){
        		resAbPersonas.setIdentificacion(datosConsultados.getIdentificacion());
        	}
        	
        	if("".equals(datosEntrada.getTipoIdentificacion()) || datosEntrada.getTipoIdentificacion() == null){
        		resAbPersonas.setTipoIdentificacion(datosConsultados.getTipoIdentificacion());
        	}
        	
        	
         	if("".equals(datosEntrada.getPrimerNombre())|| datosEntrada.getPrimerNombre() == null ){
        		resAbPersonas.setPrimerNombre(datosConsultados.getPrimerNombre());
        	}

         	if("".equals(datosEntrada.getSegundoNombre()) || datosEntrada.getSegundoNombre() == null ){
        		resAbPersonas.setSegundoNombre(datosConsultados.getSegundoNombre());
        	}

          	if("".equals(datosEntrada.getPrimerApellido()) || datosEntrada.getPrimerApellido() == null ){
        		resAbPersonas.setPrimerApellido(datosConsultados.getPrimerApellido());
        	}
          	
        	if("".equals(datosEntrada.getSegundoApellido()) || datosEntrada.getSegundoApellido() ==  null){
        		resAbPersonas.setSegundoApellido(datosConsultados.getSegundoApellido());
        	}
        	
        	if(datosEntrada.getFechaNacimiento() == null){
        		resAbPersonas.setFechaNacimiento(datosConsultados.getFechaNacimiento());
        	}
       
       
        	if("".equals(datosEntrada.getRazonSocial()) || datosEntrada.getRazonSocial() == null ){
        		resAbPersonas.setRazonSocial(datosConsultados.getRazonSocial());
        	}
        	
        	if("".equals(datosEntrada.getRepresentanteLegal()) || datosEntrada.getRazonSocial() == null){
        		resAbPersonas.setRepresentanteLegal(datosConsultados.getRepresentanteLegal());
        	}
        	
        	if("".equals(datosEntrada.getIdentificacionRepLegal())|| datosEntrada.getIdentificacionRepLegal() == null){
        		resAbPersonas.setIdentificacionRepLegal(datosConsultados.getIdentificacionRepLegal());
        	}
        
        	
        	if("".equals(datosEntrada.getTipoIdentificacionRepLegal()) || datosEntrada.getTipoIdentificacionRepLegal() == null ){
        		resAbPersonas.setTipoIdentificacionRepLegal(datosConsultados.getTipoIdentificacionRepLegal());
        	}
        	
        	if("".equals(datosEntrada.getNombreComercial())  || datosEntrada.getNombreComercial() == null ){
        		resAbPersonas.setNombreComercial(datosConsultados.getNombreComercial());
        	}
        
        	return resAbPersonas;
        	
        	
        }
		
		public AbPersonas consultarAbPersonas( long codigoPersona , String identificacion){
			
			
			AbPersonas resPersona = new AbPersonas();
			String querySql ="";
			ConexionVarianteSid conn=obtenerNuevaConexionVarianteSid(tipoConexion);
			ResultSetMapper<AbPersonas> resultSetMapper=new ResultSetMapper<AbPersonas>();
			
			String filtroAbPersonas = "";
			
			if(codigoPersona ==0 && "".equals(identificacion)){
				return resPersona;
			}
			
			if(codigoPersona!= 0){
				filtroAbPersonas = " and b.codigo = "+codigoPersona;
			}
			
			if(!"".equals(identificacion)){
				filtroAbPersonas = " and b.identificacion = '"+identificacion+"'";
			}

			
			
			querySql= "SELECT b.CODIGO,"
			 		+ " b.TIPO_PERSONA,"
			 		+ " b.IDENTIFICACION,"
			 		+ " b.TIPO_IDENTIFICACION,"
			 		+ " b.PRIMER_NOMBRE,"
			 		+ " b.SEGUNDO_NOMBRE,"
			 		+ " b.PRIMER_APELLIDO,"
			 		+ " b.SEGUNDO_APELLIDO,"
			 		+ " b.FECHA_NACIMIENTO,"
			 		+ " b.FECHA_CREACION,"
			 		+ " b.RAZON_SOCIAL,"
			 		+ " b.REPRESENTANTE_LEGAL,"
			 		+ " b.IDENTIFICACION_REP_LEGAL,"
			 		+ " b.TIPO_IDENTIFICACION_REP_LEGAL, "
			 		+ " b.NOMBRE_COMERCIAL "
			 		+ " FROM  ab_personas b"
			 		+ " where  rownum = 1"
			 		+ filtroAbPersonas
			 		+ " ORDER BY 1 DESC" ;

		 ResultSet rs = conn.consulta(querySql);
		 List<AbPersonas> resPersonaList = resultSetMapper.mapRersultSetToObject(rs, AbPersonas.class);
		 if(resPersonaList == null){
			 return null;
		 }
		 
		 if(!resPersonaList.isEmpty()){
			 resPersona = resPersonaList.get(0);
		 }
		 return  resPersona;
		}
		
		
		public Boolean editarAbPersonas(AbPersonas personas) throws SQLException{
			  PreparedStatement ps = null;
	          ConexionVarianteSid conexionVarianteSid =obtenerNuevaConexionVarianteSid(tipoConexion);
	          String queryInser=" update ab_personas"
	          		+ "  set  TIPO_PERSONA =?"
	          		+ " ,IDENTIFICACION = ?"
	          		+ " ,TIPO_IDENTIFICACION = ?"
	          		+ " ,RAZON_SOCIAL =?"
	          		+ " ,PRIMER_NOMBRE =?"
	          		+ " ,SEGUNDO_NOMBRE = ?"
	          		+ " ,PRIMER_APELLIDO = ?"
	          		+ " ,SEGUNDO_APELLIDO = ?"
	          		+ " ,REPRESENTANTE_LEGAL = ?"
	          		+ " ,IDENTIFICACION_REP_LEGAL = ?"
	          		+ " ,TIPO_IDENTIFICACION_REP_LEGAL = ?"
	          		+ " ,NOMBRE_COMERCIAL= ?"
	          		+ " ,FECHA_NACIMIENTO= ?"
	          		+ " ,USUARIO_MODIFICA = 'WEBLINK'"
	          		+ " ,FECHA_MODIFICACION = SYSDATE"
	          		+ " WHERE codigo = ? " ;

	          try {                              
	        	  ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
	              ps.setString(1, personas.getTipoPersona());
	              ps.setString(2,personas.getIdentificacion());
	              ps.setString(3,personas.getTipoIdentificacion());
	              ps.setString(4, personas.getRazonSocial());
	              ps.setString(5, personas.getPrimerNombre());
	              ps.setString(6, personas.getSegundoNombre());
	              ps.setString(7, personas.getPrimerApellido());
	              ps.setString(8, personas.getSegundoApellido());
	              ps.setString(9, personas.getRepresentanteLegal());
	              
	              ps.setString(10, personas.getIdentificacionRepLegal());
	              ps.setString(11, personas.getTipoIdentificacionRepLegal());
	              ps.setString(12, personas.getNombreComercial());
	              
	              java.util.Date utilDate = personas.getFechaNacimiento();
	              
	              if(utilDate != null){
	              java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	              ps.setDate(13, sqlDate);
	              }else{
	            	  ps.setDate(13, null);
	              }
	              
	              ps.setLong(14, personas.getCodigo());
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
	           }else{
	        	   mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
	           }
	           return false;
	       }
	          
		}
	
		
		
		
		
		
		
		
		
		private void actulizarAbDirecciones(AbDirecciones direcciones, long codigoPersona){
			log.info("actulizarAbDirecciones");
			
			try {
				
				if(direcciones.getCodigo() == 0 ){
					if(insertAbDirecciones( direcciones, codigoPersona)){
						respuestaActulizacion.setCodigo(obtenerMaxAbDirecciones(codigoPersona)-1);
						respuestaActulizacion.setCamposActualizados("S");
						resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", "Empleado consultad@: ", "104");
					}else{
						respuestaActulizacion.setCamposActualizados("N");
						resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", mesajeError, "104");
					}
					return;
				}				
				
				if(editarAbDirecciones( subConsultaDirecciones (direcciones, consultarAbDirecciones(direcciones.getCodigo(), codigoPersona)),   codigoPersona)){
					respuestaActulizacion.setCodigo(direcciones.getCodigo());
					respuestaActulizacion.setCamposActualizados("S");
					resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", "Empleado consultad@: ", "104");
				}else{
					respuestaActulizacion.setCamposActualizados("N");
					resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion" , mesajeError, "104");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		
		public Boolean insertAbDirecciones(AbDirecciones direcciones,  long codigoPersona) throws SQLException{
			  PreparedStatement ps = null;
	          ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);;
	          String queryInser=" Insert into AB_DIRECCIONES"
	          		+ " (PERSONA, CODIGO, TIPO, PRINCIPAL, NUMERO, INTERSECCION, REFERENCIA, BARRIO, CIUDAD,USUARIO_CREA, FECHA_CREACION)"
	          		+ " Values"
	          		+ " (?,?,?,?,?,?,?,?,?,'WEBLINK',SYSDATE)";
	          try {                              
	             
	              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
	              ps.setLong(1,codigoPersona);
	              ps.setLong(2,obtenerMaxAbDirecciones(codigoPersona));
	              ps.setLong(3,direcciones.getTipo());
	              ps.setString(4, direcciones.getPrincipal());
	              ps.setString(5, direcciones.getNumero());
	              ps.setString(6, direcciones.getInterseccion());
	              ps.setString(7, direcciones.getReferencia());
	              ps.setLong(8, direcciones.getBarrio());
	              ps.setLong(9, direcciones.getCiudad());
	            
	              ps.executeUpdate();    
	              ps.close();
	            
	              conexionVarianteSid.getConn().commit();
	              return true;
	           
	       } catch (SQLException e) {
	           if(e.getErrorCode()!=2291){
	               System.out.println("ErrorCode  "+e.getErrorCode());
	               mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
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
	           }else{
	        	   mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
	           }
	           return false;
	       }
	         
		}		
		
		
		
		public Boolean editarAbDirecciones(AbDirecciones direcciones,  long codigoPersona) throws SQLException{
			  PreparedStatement ps = null;
	          ConexionVarianteSid conexionVarianteSid =obtenerNuevaConexionVarianteSid(tipoConexion);
	          String queryInser=" UPDATE AB_DIRECCIONES SET"
	          		+ " tipo =?,"
	          		+ " principal =?,"
	          		+ " numero =?,"
	          		+ " interseccion=?,"
	          		+ " referencia = ?,"
	          		+ " barrio = ?,"
	          		+ " ciudad = ?,"
	          		+ " usuario_actualiza ='WEBLINK',"
	          		+ " fecha_actualizacion = sysdate "
	          		+ " WHERE persona = ? AND codigo = ?" ;

	          try {                              
	        	  ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
	              ps.setLong(1, direcciones.getTipo());
	              ps.setString(2,direcciones.getPrincipal());
	              ps.setString(3,direcciones.getNumero());
	              ps.setString(4, direcciones.getInterseccion());
	              ps.setString(5, direcciones.getReferencia());
	              ps.setLong(6, direcciones.getBarrio());
	              ps.setLong(7, direcciones.getCiudad());
	              ps.setLong(8, codigoPersona);
	              ps.setLong(9, direcciones.getCodigo());
	             
	              ps.executeUpdate();    
	              ps.close();
	            
	              conexionVarianteSid.getConn().commit();
	              return true;
	           
	       } catch (SQLException e) {
	           if(e.getErrorCode()!=2291){
	               System.out.println("ErrorCode  "+e.getErrorCode());
	               
	               mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
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
	           }else{
	        	   mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
	           }
	           return false;
	       }
	          
		}
		
		
		
		
	public AbDirecciones consultarAbDirecciones( long codigoDireccion, long codigoPersona){
			
			
		AbDirecciones resPersona = new AbDirecciones();
			
			ConexionVarianteSid conn=obtenerNuevaConexionVarianteSid(tipoConexion);
			
			
			String filtroAbDirecccion = "";
			
			if(codigoDireccion ==0 ){
				return resPersona;
			}
			
			if(codigoDireccion!= 0){
				filtroAbDirecccion = " and b.CODIGO = "+codigoDireccion;
			}

			ResultSetMapper<AbDirecciones> resultSetMapperDir=new ResultSetMapper<AbDirecciones>();
			 String query=" SELECT b.CODIGO,"
       			+ " b.PRINCIPAL,"
       			+ " b.NUMERO,"
       			+ " b.INTERSECCION,"
       			+ " b.REFERENCIA,"
       			+ " b.TIPO ,"
       			+ " b.BARRIO,"
			 	+ " b.CIUDAD"
       			+ " FROM AB_DIRECCIONES b "
       			+ " where b.persona = " + codigoPersona
       			+ filtroAbDirecccion;
			 ResultSet rss = conn.consulta(query);
			 List<AbDirecciones> listDirecciones = resultSetMapperDir.mapRersultSetToObject(rss, AbDirecciones.class);
			
		 if(listDirecciones == null){
			 return null;
		 }
		 
		 if(!listDirecciones.isEmpty()){
			 resPersona = listDirecciones.get(0);
		 }
		 return  resPersona;
		}

	
	
	
	 public AbDirecciones subConsultaDirecciones (AbDirecciones datosEntrada, AbDirecciones datosConsultados){
     	
		 AbDirecciones resAbDirecciones = new AbDirecciones();
     	
     	
     	if(datosConsultados == null){
     		return datosEntrada;
     	}

     	resAbDirecciones = datosEntrada;
     	
     	
     	if("".equals(datosEntrada.getPrincipal()) || datosEntrada.getPrincipal() == null ){
     		resAbDirecciones.setPrincipal(datosConsultados.getPrincipal());
     	}

     	if("".equals(datosEntrada.getNumero()) || datosEntrada.getNumero() == null){
     		resAbDirecciones.setNumero(datosConsultados.getNumero());
     	}
     	
     	if("".equals(datosEntrada.getInterseccion()) || datosEntrada.getInterseccion() == null){
     		resAbDirecciones.setInterseccion(datosConsultados.getInterseccion());
     	}
     	
     	
      	if("".equals(datosEntrada.getReferencia())|| datosEntrada.getReferencia() == null ){
     		resAbDirecciones.setReferencia(datosConsultados.getReferencia());
     	}

      	if("".equals(datosEntrada.getTipo()) || datosEntrada.getTipo() == 0 ){
     		resAbDirecciones.setTipo(datosConsultados.getTipo());
     	}

       	if("".equals(datosEntrada.getBarrio()) || datosEntrada.getBarrio() == 0 ){
     		resAbDirecciones.setBarrio(datosConsultados.getBarrio());
     	}
       	
     	if("".equals(datosEntrada.getCiudad()) || datosEntrada.getCiudad() ==  0){
     		resAbDirecciones.setCiudad(datosConsultados.getCiudad());
     	}
     	
     	
     	return resAbDirecciones;
     	
     	
     }
		private void actulizarAbMediosContacto(AbMediosContacto mediosContacto,  long codigoPersona){
			log.info("actulizarAbMediosContacto");
			try {
				
				if(mediosContacto.getCodigo() == 0 ){
					
					if(insertAbMediosContacto( mediosContacto, codigoPersona)){
						respuestaActulizacion.setCodigo(obtenerMaxAbMediosContacto(codigoPersona)-1);
						respuestaActulizacion.setCamposActualizados("S");
						resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", "Empleado consultad@: ", "104");
					}else{
						respuestaActulizacion.setCamposActualizados("N");
						resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", mesajeError, "104");
					}
					return;
				}				
				
				if(editarAbMediosContacto( subConsultaMediosContacto (mediosContacto, consultarAbMediosContacto(mediosContacto.getCodigo(), codigoPersona)),   codigoPersona)){
					respuestaActulizacion.setCodigo(mediosContacto.getCodigo());
					respuestaActulizacion.setCamposActualizados("S");
					resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", "Empleado consultad@: ", "104");
				}else{
					respuestaActulizacion.setCamposActualizados("N");
					resultadoXml=serializarLogin(this.respuestaActulizacion, "actualizacionInsercion", mesajeError, "104");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	
	
		public Boolean editarAbMediosContacto(AbMediosContacto mediosContacto,  long codigoPersona) throws SQLException{
			  PreparedStatement ps = null;
	          ConexionVarianteSid conexionVarianteSid =obtenerNuevaConexionVarianteSid(tipoConexion);
	          String queryInser=" UPDATE AB_MEDIOS_CONTACTO SET"
	          		+ " valor = ? , "
	          		+ " tipo= ?, "
	          		+ " usuario_actualiza ='WEBLINK', "
	          		+ " FECHA_ACTUALIZACION = sysdate "
	          		+ " WHERE persona = ? "
	          		+ " AND codigo = ?" ;
  
	          try {                              
	             
	              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
	            
	              ps.setString(1,mediosContacto.getValor());
	              ps.setLong(2, mediosContacto.getTipo());
	              ps.setLong(3, codigoPersona);
	              ps.setLong(4, mediosContacto.getCodigo());
	              
	             
	              ps.executeUpdate();    
	              ps.close();
	            
	              conexionVarianteSid.getConn().commit();
	              return true;
	           
	       } catch (SQLException e) {
	           if(e.getErrorCode()!=2291){
	               System.out.println("ErrorCode  "+e.getErrorCode());
	               
	               mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
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
	           }else{
	        	   mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
	           }
	           return false;
	       }
	          
		}
		
		
		public Boolean insertAbMediosContacto(AbMediosContacto mediosContacto,  long codigoPersona) throws SQLException{
			  PreparedStatement ps = null;
	          ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);;
	          String queryInser=" Insert into AB_MEDIOS_CONTACTO"
	          		+ " (PERSONA, CODIGO, VALOR, TIPO, USUARIO_CREA, FECHA_CREACION )"
	          		+ " Values"
	          		+ " (?,?,?,?,'WEBLINK',SYSDATE)";
	          try {                              
	             
	              ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
	              ps.setLong(1,codigoPersona);
	              ps.setLong(2,obtenerMaxAbMediosContacto(codigoPersona));
	              ps.setString(3, mediosContacto.getValor());
	              ps.setLong(4, mediosContacto.getTipo());
	            
	              
	              
	              ps.executeUpdate();    
	              ps.close();
	            
	              conexionVarianteSid.getConn().commit();
	              return true;
	           
	       } catch (SQLException e) {
	           if(e.getErrorCode()!=2291){
	        	   
	        	   mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
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
	           }else{
	        	   mesajeError = e.getMessage().trim().replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("\"","");
	           }
	           return false;
	       }
	         
		}		
		
		
		public AbMediosContacto consultarAbMediosContacto( long codigoMedioContacto, long codigoPersona){
			
			
			AbMediosContacto resPersona = new AbMediosContacto();
				
				ConexionVarianteSid conn=obtenerNuevaConexionVarianteSid(tipoConexion);
				
				
				String filtroAbMediosContacto = "";
				
				if(codigoMedioContacto ==0 ){
					return resPersona;
				}
				
				if(codigoMedioContacto!= 0){
					filtroAbMediosContacto = " and b.CODIGO = "+codigoMedioContacto;
				}

				 ResultSetMapper<AbMediosContacto> resultSetMapperMed=new ResultSetMapper<AbMediosContacto>();
				 String queryMed=" SELECT"
						 + " b.CODIGO,"
						 + " b.VALOR,"
						 + " b.TIPO"
						 + " FROM"
						 + " AB_MEDIOS_CONTACTO b"
						 + " WHERE b.PERSONA = " + codigoPersona
						 + filtroAbMediosContacto
						 ;
				 ResultSet rsMediosContacto = conn.consulta(queryMed);
				 List<AbMediosContacto> listMediosContacto= resultSetMapperMed.mapRersultSetToObject(rsMediosContacto, AbMediosContacto.class);
				
			 if(listMediosContacto == null){
				 return null;
			 }
			 
			 if(!listMediosContacto.isEmpty()){
				 resPersona = listMediosContacto.get(0);
			 }
			 return  resPersona;
			}
		
		

		 public AbMediosContacto subConsultaMediosContacto (AbMediosContacto datosEntrada, AbMediosContacto datosConsultados){
	     	
			 AbMediosContacto resMediosContacto = new AbMediosContacto();
	     	if(datosConsultados == null){
	     		return datosEntrada;
	     	}
	     	resMediosContacto = datosEntrada;
	     	if("".equals(datosEntrada.getValor()) || datosEntrada.getValor() == null ){
	     		resMediosContacto.setValor(datosConsultados.getValor());
	     	}
	     	if("".equals(datosEntrada.getTipo()) || datosEntrada.getTipo() == 0){
	     		resMediosContacto.setTipo(datosConsultados.getTipo());
	     	}

	     	return resMediosContacto;
	     }
		
		
		
		
		public Integer obtenerMaxAbMediosContacto(long persona){
			Integer maximo=0;
			String queryBusqueda="select nvl(max(codigo),0)+1 as maximo from AB_MEDIOS_CONTACTO where persona = "+ persona;
			try{
				ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);;
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
		
		public Integer obtenerMaxAbDirecciones(long persona){
			Integer maximo=0;
			String queryBusqueda="select nvl(max(codigo),0)+1 as maximo from AB_DIRECCIONES where persona = "+ persona;
			try{
				ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(tipoConexion);;
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
		
		
		
		
		
		
		
		

		
		 
		public static String serializar(Object objetosSerializar){
			JSONSerializer serializador = new JSONSerializer();
			
			return serializador.include("mediosContacto").include("direcciones").exclude("*.class").serialize(objetosSerializar);
		}
		
		public static String serializar(Object objetosSerializar,String rutaPrimaria,String mensaje,String status){
			return "{\""+rutaPrimaria+"\":[{\"httpStatus\":\""+status+"\",\"mensaje\":\""+mensaje+
		               "\",\"respuesta\":"+serializar(objetosSerializar)+"}]}";
		}
		
		
		
		
		public static String serializarLogin(Object objetosSerializar){
			JSONSerializer serializador = new JSONSerializer();
			return serializador.include("roles").exclude("*.class").serialize(objetosSerializar);
		}
		
		public static String serializarLogin(Object objetosSerializar,String rutaPrimaria,String mensaje,String status){
			return "{\""+rutaPrimaria+"\":[{\"httpStatus\":\""+status+"\",\"mensaje\":\""+mensaje+
		               "\",\"respuesta\":"+serializarLogin(objetosSerializar)+"}]}";
		}
		
		
		
		
		
		private Boolean validarToken(String usuario , String clave){
			Boolean resultado =true;
			GPFSSO gpfsso = new GPFSSO();
			String token = null;
			boolean statusToken = false;
			gpfsso.setOPENSSO_URL("https://nuestroportal.corporaciongpf.com");

			try {
				token = gpfsso.generarToken(usuario, clave);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
			try {
				statusToken = gpfsso.validarToken(token);
				if (statusToken){
					 resultado = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
			return resultado;
		}
		

		    private Boolean validarClave(String clave) {
		        if (clave.length() <= 13) {
		            return Boolean.TRUE;
		        }
		        Boolean respuesta = Boolean.TRUE;
		        ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSidSidDataBase(30, "web", "webdba");
		        String query = "{call Pckwb_Seguridad.verifica_user_passw (?,?,?)}";
		        try {
		            CallableStatement cs = conexionVarianteSid.getConn().prepareCall(query);
		            cs.registerOutParameter(1, Types.VARCHAR);
		            cs.setString(2, clave.substring(0, 13));
		            cs.setString(3, clave.substring(13, clave.length()));
		            log.info("inicio validar usuario:" + clave.substring(0, 13) + "clave: " + clave.substring(13, clave.length()) + "::" + new Date());
		            cs.execute();
		            log.info("Fin validar clave " + new Date());
		            String resultado = cs.getString(1);
		            if ("S".equals(resultado)) {
		                respuesta = Boolean.FALSE;
		            } else {
		                respuesta = Boolean.TRUE;
		            }
		            cs.close();
		        } catch (SQLException ex) {
		        	//ex.printStackTrace();
		            log.info("Error validar clave " + ex);
		            return Boolean.TRUE;
		        }
		        return respuesta;
		    }

		
		public String getResultadoJson() {
			return resultadoXml;
		}
		
		
		
		public String consultarDatosClientes(String identificacion){
			log.info("Identificacion "+ identificacion);
			 ArrayList<AbDatosPersonas> respuesta  = new ArrayList<AbDatosPersonas>();
			 String querySql ="";
			 String tablaAbMediosContato =" ";
			 String filtroIdentificacion =" ";
			 String filtroTelefono =" ";
			 String mensajeJson = "";
			 if(("".equals(identificacion) || "null".equals(identificacion))){
				 resultadoXml = serializar(this.respuesta, "datosPersona", "Error al menos debe  ingresar un parametro de busqueda", "504");
				 return serializar(respuesta, "datosPersona", "Error al ejecutar el procedimiento", "504");
				 
			 }
				ConexionVarianteSid conn=obtenerNuevaConexionVarianteSid(tipoConexion);
			try {
				
				ResultSetMapper<AbDatosPersonas> resultSetMapper=new ResultSetMapper<AbDatosPersonas>();
			
				 if(!"".equals(identificacion) && !"null".equals(identificacion) && identificacion != null  ){
					 filtroIdentificacion = " and  identificacion = '"+ identificacion+"'";
					 mensajeJson="Cedula consultada:"+identificacion;
				 }
				 querySql= "SELECT b.CODIGO,"
					 		+ " b.TIPO_PERSONA,"
					 		+ " b.IDENTIFICACION,"
					 		+ " b.TIPO_IDENTIFICACION,"
					 		+ " b.PRIMER_NOMBRE,"
					 		+ " b.SEGUNDO_NOMBRE,"
					 		+ " b.PRIMER_APELLIDO,"
					 		+ " b.SEGUNDO_APELLIDO,"
					 		+ " b.FECHA_NACIMIENTO,"
					 		+ " b.FECHA_CREACION,"
					 		+ " b.RAZON_SOCIAL,"
					 		+ " b.REPRESENTANTE_LEGAL,"
					 		+ " b.IDENTIFICACION_REP_LEGAL,"
					 		+ " b.TIPO_IDENTIFICACION_REP_LEGAL, "
					 		+ " b.NOMBRE_COMERCIAL "
					 		+ " FROM  ab_personas b  "
					 		+ tablaAbMediosContato
					 		+ " WHERE  1=1 "
					 		+ filtroTelefono
					 		+ filtroIdentificacion;

				 ResultSet rs = conn.consulta(querySql);
				 List<AbDatosPersonas> listPersonas = resultSetMapper.mapRersultSetToObject(rs, AbDatosPersonas.class);
				 
				 //if(null == null){
			     if(listPersonas == null){
					 return consultarClientesFacturas(identificacion);//serializar(respuesta, "datosPersona", "No existen datos para el valor consultado", "404"); 
				 }
				
				 for (AbDatosPersonas persona : listPersonas ){
					
					 ResultSetMapper<AbMediosContacto> resultSetMapperMed=new ResultSetMapper<AbMediosContacto>();
					 
					 String queryMed=" SELECT"
							 + " CODIGO,"
							 + " VALOR,"
							 + " TIPO"
							 + " FROM"
							 + " AB_MEDIOS_CONTACTO"
							 + " WHERE "
							 + " TIPO = 35 "
							 + " and PERSONA = " + persona.getCodigo();
					 
					 ResultSet rsMediosContacto = conn.consulta(queryMed);
					
					 List<AbMediosContacto> listMediosContacto= resultSetMapperMed.mapRersultSetToObject(rsMediosContacto, AbMediosContacto.class);
					 if(listMediosContacto != null && !listMediosContacto.isEmpty())
						 persona.setMail(listMediosContacto.get(0).getValor());
					
					 respuesta.add(persona);
				 }
				 conn.cerrarConexion();
				 return serializar(respuesta, "datosPersona", mensajeJson, "104");
				
	        }catch (Exception ex) {
	        	ex.getStackTrace();
	        	System.out.println("Error "+ex.getMessage());
	        	conn.cerrarConexion();
	        	return serializar(respuesta, "datosPersona", "Error al ejecutar el procedimiento", "504");
	        }
		}
		
		
		
		public String consultarClientesFacturas(String identificacion){
			log.info("Identificacion "+ identificacion);
			 ArrayList<AbDatosPersonas> respuesta  = new ArrayList<AbDatosPersonas>();
			 String querySql ="";
			 String mensajeJson = "";
				ConexionVarianteSid conn=obtenerNuevaConexionVarianteSid(tipoConexion);
			try {
				
				ResultSetMapper<AbDatosPersonas> resultSetMapper=new ResultSetMapper<AbDatosPersonas>();
			
				 querySql= " SELECT  b.identificacion,"
				 		+ "  b.nombres PRIMER_NOMBRE,"
				 		+ "  b.apellido_paterno PRIMER_APELLIDO,"
				 		+ "  b.apellido_materno SEGUNDO_APELLIDO,"
				 		+ "  cp_var2 mail"
				 		+ " from fa_clientes_facturas b"
				 		+ " where identificacion = '"+identificacion+"'";
					 		

				 ResultSet rs = conn.consulta(querySql);
				 List<AbDatosPersonas> listPersonas = resultSetMapper.mapRersultSetToObject(rs, AbDatosPersonas.class);
				 
				 if(listPersonas == null){
					 return serializar(respuesta, "datosPersona", "No existen datos para el valor consultado", "404"); 
				 }
				
				 for (AbDatosPersonas persona : listPersonas ){
					 mensajeJson="Cedula consultada:"+identificacion;
					 respuesta.add(persona);
				 }
				 conn.cerrarConexion();
				 return serializar(respuesta, "datosPersona", mensajeJson, "104");
				
	        }catch (Exception ex) {
	        	ex.getStackTrace();
	        	System.out.println("Error "+ex.getMessage());
	        	conn.cerrarConexion();
	        	return serializar(respuesta, "datosPersona", "Error al ejecutar el procedimiento", "504");
	        }
			
		}
		
	public String cupoClienteVc(String codigoItem) {
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");

		if (!conexionVarianteSid.isValid())
			return Constantes.respuestaXmlUnica("Error al conectar a la BDD");

		String queryEjecutar = "SELECT integraciones.INT_FNC_INFO_TARJETA('"+codigoItem+"') GRUPO FROM DUAL";
		ResultSet resultado = conexionVarianteSid.consulta(queryEjecutar);

		while (conexionVarianteSid.siguiente(resultado)) {
			String[] datosVc = conexionVarianteSid
					.getString(resultado, "GRUPO").split(";");

			VcCupoClientesBean cupoClientes = new VcCupoClientesBean();
			if (datosVc.length == 3) {
				cupoClientes.setCupo(datosVc[0]);
				cupoClientes.setIdentificacion(datosVc[1]);
				cupoClientes.setNombre(datosVc[2]);

				xstream.alias("CupoClienteVc", VcCupoClientesBean.class);
				this.resultadoXml = xstream.toXML(cupoClientes);
				this.resultadoXml = Constantes.respuestaXmlObjeto("OK",
						this.resultadoXml);
				resultadoXml = xstream.toXML(cupoClientes);
				conexionVarianteSid.cerrarConexion();
				return resultadoXml;
			}
			return Constantes.respuestaXmlObjeto("ERROR", " Error al consultar el cupo en clientes VC ");
		}
		return Constantes.respuestaXmlObjeto("ERROR", " Error al consultar el cupo en clientes VC");

	}
	
	

}
