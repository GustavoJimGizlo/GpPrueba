package com.serviciosweb.gpf.facturacion.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.ejb.Singleton;
import javax.ejb.Startup;


@Startup
@Singleton
public class WebApplicationConfig {
	
	// final String RUTA_ARCHIVO_PROPERTIES = "C:\\workspace\\WebApplicationCorporacionGPF\\WebApplicationCorporacionGPF\\data\\config\\WebApplicationCorporacionGPF.properties";
	
	
	//final String RUTA_ARCHIVO_PROPERTIES = "C:\\workspace\\WebApplicationCorporacionGPF\\WebApplicationCorporacionGPF\\data\\config\\WebApplicationCorporacionGPF.properties";
    
	
	// final String RUTA_ARCHIVO_PROPERTIES = "C:\\data\\WebApplicationCorporacionGPF.properties";
	
	final String RUTA_ARCHIVO_PROPERTIES = "/data/WebApplicationCorporacionGPF.properties";
	
	public class ConfiamedConfig 
    {
        public String urlToken;
        public String credUsuario;
        public String credClave;
        public String app_id;
        public String keyMappingId;
        public String consumerSecret;
        
        
        
    }
	
	public class HumanaConfig 
    {
        public String urlToken;
        public String credenciales;
        
        
        
    }
	
	
	
	public class SaludConfig 
    {
        public String urlToken;
        public String headerCodAplicacion;
        public String headerCodPlataforma;
        public String headerDireccionIP;
        public String headerSO;
        public String headerNavegador;
        public String credIdCliente;
        public String credUsuario;
        public String credClave;
        public String credGrantType;
        public String paramConvenio;
        public String rutaArchivoToken;
        
        public String urlIn;
        public String urlVC;
    }
	
	public class SAMConfig {
		public String urlMAD;
		public String credParam1;
		public String credParam2;
		public int timeoutLectura;
		public int timeoutConexion;
	}

	public SaludConfig epSaludConfig;
	public SAMConfig epSAMConfig;
	public ConfiamedConfig epConfiamedConfig;
	public HumanaConfig epHumanaConfig;
	
	public WebApplicationConfig() {
		try 
		{
            final File archivoProperties = new File(RUTA_ARCHIVO_PROPERTIES);   
            FileInputStream inputStream = new FileInputStream(archivoProperties);
            
            if (inputStream != null) {
            	Properties prop = new Properties();
            	prop.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            	
            	configurarEndPoints(prop);
            }
            else 
            	throw new FileNotFoundException("El archivo de propiedades '' no se encuentra");
        } catch (Exception e) {
        	System.out.print("");
        }
		
	}
	
	private void configurarEndPoints(Properties prop) {
	
		//confiamed
		epConfiamedConfig = new ConfiamedConfig();
		epConfiamedConfig.urlToken = prop.getProperty("CON.URL_TOKEN");
		epConfiamedConfig.credClave = prop.getProperty("CON.CLAVE_TOKEN");
		epConfiamedConfig.credUsuario = prop.getProperty("CON.USER_TOKEN");
		epConfiamedConfig.app_id = prop.getProperty("CON.APPID");
		epConfiamedConfig.keyMappingId = prop.getProperty("CON.keyMappingId");
		epConfiamedConfig.consumerSecret = prop.getProperty("CON.consumerSecret");
	
		
		
		//humana
				epHumanaConfig = new HumanaConfig();
				epHumanaConfig.urlToken = prop.getProperty("HUM.URL_TOKEN");
				
				epHumanaConfig.credenciales= prop.getProperty("HUM.CRED_TOKEN");
						
		
		epSaludConfig = new SaludConfig();
		epSaludConfig.urlToken = prop.getProperty("SALUD.URL_TOKEN");
		epSaludConfig.headerCodAplicacion = prop.getProperty("SALUD.HEADER_COD_APLICACION");
		epSaludConfig.headerCodPlataforma = prop.getProperty("SALUD.HEADER_COD_PLATAFORMA");
		epSaludConfig.headerDireccionIP = prop.getProperty("SALUD.HEADER_DIRECCION_IP");
		epSaludConfig.headerSO = prop.getProperty("SALUD.HEADER_SO");
		epSaludConfig.headerNavegador = prop.getProperty("SALUD.HEADER_NAVEGADOR");
		epSaludConfig.credIdCliente = prop.getProperty("SALUD.CRED_CLIENTID");
		epSaludConfig.credUsuario = prop.getProperty("SALUD.CRED_USUARIO");
		epSaludConfig.credClave = prop.getProperty("SALUD.CRED_CLAVE");
		epSaludConfig.credGrantType = prop.getProperty("SALUD.CRED_GRANT_TYPE");
		epSaludConfig.paramConvenio = prop.getProperty("SALUD.PARAM_CONVENIO");
		epSaludConfig.rutaArchivoToken = prop.getProperty("SALUD.RUTA_ARCHIVO_TOKEN");

		epSaludConfig.urlIn = prop.getProperty("CONVENIOSIN.URL_TOKEN");
		epSaludConfig.urlVC = prop.getProperty("CONVENIOSVC.URL_TOKEN");
		
		// SAM
		epSAMConfig = new SAMConfig();
		epSAMConfig.urlMAD = prop.getProperty("SAM.URL_MAD");
		//epSAMConfig.credParam1 = prop.getProperty("SAM.CRED_PARAM1");
		//epSAMConfig.credParam2 = prop.getProperty("SAM.CRED_PARAM2");
		epSAMConfig.timeoutLectura = Integer.parseInt(prop.getProperty("SAM.TIMEOUT_LECTURA"));
		epSAMConfig.timeoutConexion = Integer.parseInt(prop.getProperty("SAM.TIMEOUT_CONEXION"));
		
		
		
		
		
	}
}
