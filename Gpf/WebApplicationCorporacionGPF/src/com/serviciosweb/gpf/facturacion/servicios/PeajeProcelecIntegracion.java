package com.serviciosweb.gpf.facturacion.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class PeajeProcelecIntegracion {
	
	
     public  String peajeProcelecResponse ="";
	 private static final String PROXY_HOST = "uioproxy04.gfybeca.int";
	 private static final int PROXY_PORT = 3128;
	 
	 
		public static String readContentFile(String filePath)  {
			try {
				File file = new File(filePath);
				String content = org.apache.commons.io.FileUtils.readFileToString(file);

				return content;

			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		}
	 
	 
	 
	public PeajeProcelecIntegracion(String cedula) {
		
		String data = " <tem:BusquedaCliente>"
				+ " <tem:cedula>"+cedula+"</tem:cedula>"
				+ " </tem:BusquedaCliente>";

		String pathTem = requestXML(data, cedula+"BusquedaCliente");

		String result = executeMethod(pathTem,"http://tempuri.org/IService1/BusquedaCliente");
		
		if(!"".equals(result))
    		peajeProcelecResponse = result;
		
	}

	
	public PeajeProcelecIntegracion(String valor1,String valor2,String valor3,String tipo){
		
		if("confirmacionRecarga".equals(tipo)){
			String data = "  <tem:ConfirmacionRecarga>"
					    + " <tem:identity>"+valor1+"</tem:identity>"
					    + " <tem:factura>"+valor2+"</tem:factura>"
					    + " </tem:ConfirmacionRecarga>";

			String pathTem = requestXML(data,valor2+"confirmacionRecarga");

			String result = executeMethod(pathTem,"http://tempuri.org/IService1/ConfirmacionRecarga");
			
			if(!"".equals(result))
	    		peajeProcelecResponse = result;
			
		}
		
        if("EliminarRecarga".equals(tipo)){
        	String data = " <tem:EliminarRecarga>"
        				+ " <tem:identity>"+valor1+"</tem:identity>"
        				+ " <tem:cedula>"+valor2+"</tem:cedula>"
        				+ " <tem:valor>"+valor3+"</tem:valor>"
        				+ " </tem:EliminarRecarga>";

    		String pathTem = requestXML(data, valor1+"EliminarRecarga");

    		String result  = executeMethod(pathTem,"http://tempuri.org/IService1/EliminarRecarga");
    		
    		if(!"".equals(result))
        		peajeProcelecResponse = result;
			
		}
        
        if("grabaRecarga".equals(tipo)){
        	String data = " <tem:GrabaRecarga>"
        			    + " <tem:cedula>"+valor1+"</tem:cedula>"
        			    + " <tem:valor>"+valor2+"</tem:valor>"
        			    + " </tem:GrabaRecarga>";

    		String pathTem = requestXML(data, valor1+"grabaRecarga");

    		String result  = executeMethod(pathTem,"http://tempuri.org/IService1/GrabaRecarga");
    		
    		if(!"".equals(result))
        		peajeProcelecResponse = result;
			
         }
	}
	
	public  String executeMethod(String pathTem,String strSoapAction){
		PostMethod post = null;
         try {
 
        	 HttpClient httpclient = new HttpClient();
        	 //HostConfiguration config = httpclient.getHostConfiguration();
             //config.setProxy(PROXY_HOST, PROXY_PORT);

             File input = new File(pathTem);
             //String url="http://181.198.63.189/WCFRecargaOtrosProveedores/Service1.svc?wsdl";
             String url= readContentFile("/data/wsdl/urlTelepeaje.txt");
             
             post = new PostMethod(url);
             RequestEntity entity = new FileRequestEntity(input,"text/xml; charset=utf-8"); 
             
             post.setRequestEntity(entity);
             post.setRequestHeader("SOAPAction", strSoapAction); 
             post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(10, false));
             post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,new Integer(60000));
             httpclient.executeMethod(post);
             return readInputStreamAsString(post.getResponseBodyAsStream());
         } catch (Exception e) {
        	 return null;
         }  finally {
        	 if(post != null)
        		 post.releaseConnection(); 
         }
	}
	
	public  String  consultaNaeSoap(String companiaFactura,String numeroFactura){
	            String pathXmlAuto=null;
	                try {
	                	
	                	
	                	HttpClient httpclient = new HttpClient();
	                	
	                    //HostConfiguration config = httpclient.getHostConfiguration();
	                   // config.setProxy(PROXY_HOST, PROXY_PORT);
	                    String pathTem=crearSoapFile(companiaFactura,numeroFactura);    
	                    String strSoapAction = "http://tempuri.org/IService1/BusquedaCliente";
	                    File input = new File(pathTem);
	                   // String url="http://181.198.63.189/WCFRecargaOtrosProveedores/Service1.svc?wsdl";
	                    
	                    String url=readContentFile("/data/wsdl/urlTelepeaje.txt");
	                    
	                    
	                    
	                    PostMethod post = new PostMethod(url);
	                    RequestEntity entity = new FileRequestEntity(input,"text/xml; charset=utf-8"); 
	                    
	                    post.setRequestEntity(entity);
	                    post.setRequestHeader("SOAPAction", strSoapAction); 
	                    post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(10, false));
	                    post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,new Integer(60000));
	                    
	                    try {
	                        httpclient.executeMethod(post);
	                        String resultado=readInputStreamAsString(post.getResponseBodyAsStream());
	                        return remove1(resultado.trim()) ;
	                    }catch (Exception e) {
						  e.printStackTrace();
						} finally {
		                       post.releaseConnection();
		                }
	                    
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    pathXmlAuto = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
	   	                           "<transaccion>"+
	   	                           "<httpStatus>504</httpStatus>"+
	   	                           "<mensaje>Error, la transaccion fallo ERROR: </mensaje>"+
	   	                           "<respuesta>consulte con el administrador JDE</respuesta>"+
	   	                           "</transaccion>";

	                }        
	            return pathXmlAuto;
	        }
	
	public static String remove1(String input) {
       
        String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á«";
     
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
        
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
	  
	  private  String readInputStreamAsString(InputStream inputStream) {
	  
		  String resultado ="";
		  try{
          StringBuffer fileData = new StringBuffer(1000);
          BufferedReader reader = new BufferedReader(new InputStreamReader( inputStream));
          char[] buf = new char[1024];
          int numRead = 0;
          while ((numRead = reader.read(buf)) != -1) {
              String readData = String.valueOf(buf, 0, numRead);
              fileData.append(readData);
              buf = new char[1024];
          }
          reader.close();
          resultado=fileData.toString();
          
          resultado=resultado.replace("&lt;", "<"); 
          resultado=resultado.replace("&gt;", ">"); 
          resultado=resultado.replace("<cabecera>", "<cabecera>").replace("</cabecera>","</cabecera>");

         
              if(resultado.contains("<Table1>")){
            	  resultado= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
   	                           "<transaccion>"+
   	                           "<httpStatus>104</httpStatus>"+
   	                           "<mensaje>La transaccion se ejecuto exitosamente</mensaje>"+
   	                           "<respuesta>"+resultado.split("<Table1>")[1];
   	                   
   	              resultado=resultado.split(" </Table1>")[0]+"</respuesta>"+
   	                           "</transaccion>";
              }else if(resultado.contains("<ConfirmacionRecargaResponse xmlns=\"http://tempuri.org/\">")){
            	  resultado= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
	                           "<transaccion>"+
	                           "<httpStatus>104</httpStatus>"+
	                           "<mensaje>La transaccion se ejecuto exitosamente</mensaje>"+
	                           "<respuesta>"+resultado.split("<ConfirmacionRecargaResponse xmlns=\"http://tempuri.org/\">")[1];
	                   
	              resultado=resultado.split("</ConfirmacionRecargaResponse>")[0]+"</respuesta>"+
	                           "</transaccion>";
             }else if(resultado.contains("<GrabaRecargaResponse xmlns=\"http://tempuri.org/\">")){
           	  resultado= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                      "<transaccion>"+
                      "<httpStatus>104</httpStatus>"+
                      "<mensaje>La transaccion se ejecuto exitosamente</mensaje>"+
                      "<respuesta>"+resultado.split("<GrabaRecargaResponse xmlns=\"http://tempuri.org/\">")[1];
              
              resultado=resultado.split("</GrabaRecargaResponse>")[0]+"</respuesta>"+
                      "</transaccion>";
           }else if(resultado.contains("<EliminarRecargaResponse xmlns=\"http://tempuri.org/\">")){
            	  resultado= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                          "<transaccion>"+
                          "<httpStatus>104</httpStatus>"+
                          "<mensaje>La transaccion se ejecuto exitosamente</mensaje>"+
                          "<respuesta>"+resultado.split("<EliminarRecargaResponse xmlns=\"http://tempuri.org/\">")[1];
                  
                  resultado=resultado.split("</EliminarRecargaResponse>")[0]+"</respuesta>"+
                          "</transaccion>";
               }else{
            	   
            	   resultado = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                           "<transaccion>"+
                           "<httpStatus>504</httpStatus>"+
                           "<mensaje>Error, la transaccion fallo ERROR (El servicio web Peaje Procelet no esta disponible): </mensaje>"+
                           "<respuesta>consulte con el administrador </respuesta>"+
                           "</transaccion>";
               }
              
              
          }catch(Exception e){
        	  
        	  peajeProcelecResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                         "<transaccion>"+
                         "<httpStatus>504</httpStatus>"+
                         "<mensaje>Error, la transaccion fallo ERROR (El servicio web Peaje Procelet no esta disponible): </mensaje>"+
                         "<respuesta>consulte con el administrador </respuesta>"+
                         "</transaccion>";
          }
          return resultado;

      }
	  
	  
	  
      private static String requestXML(String data,String id ) {

    	  String soapTest=" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"
    	  		        + " <soapenv:Header/>"
    	  		        + " <soapenv:Body>"
    	  		        + data
    	  		        + " </soapenv:Body>"
    	  		        + " </soapenv:Envelope>";
          String pathXmlAuto=null;
          try {
              pathXmlAuto= createDocument(soapTest,id);
          } catch (IOException e) {                
              e.printStackTrace();
          }

          return pathXmlAuto;
      }
	  

      private static String crearSoapFile(String companiaFactura,String numeroFactura) {

    	  String soapTest=" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"
    	  		        + " <soapenv:Header/>"
    	  		        + " <soapenv:Body>"
    	  		        + " <tem:BusquedaCliente>"
    	  		        + " <tem:cedula>1291710359001</tem:cedula>"
    	  		        + " </tem:BusquedaCliente>"
    	  		        + " </soapenv:Body>"
    	  		        + " </soapenv:Envelope>";
          String pathXmlAuto=null;
          try {
              pathXmlAuto= createDocument(soapTest,numeroFactura);
          } catch (IOException e) {                
              e.printStackTrace();
          }

          return pathXmlAuto;
      }
      
  	public static String createDocument(String xmlSource,String id) throws IOException {
		StringBuilder pathFolder = new StringBuilder();
		pathFolder.append("/data/doc_peaje/documentos/");
		createDirectory(pathFolder.toString());
		StringBuilder pathFile = new StringBuilder();
		pathFile.append(pathFolder.toString());
		pathFile.append(id);
		pathFile.append(".xml");
		stringToFile(xmlSource, pathFile.toString());
		return pathFile.toString();
	}
  	
  	public static void createDirectory(String path)
			throws FileNotFoundException {
		StringBuffer pathname = new StringBuffer(path);

		if (!path.trim().endsWith("/")) {
			pathname.append("/");
		}

		File directory = new File(pathname.toString());
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				throw new FileNotFoundException();
			}
		}
	}
  	
  	private static void stringToFile(String xmlSource, String fileName)
			throws IOException {
		java.io.FileWriter fw = new java.io.FileWriter(fileName);
		fw.write(xmlSource);
		fw.close();
	}
  	
  	
  	
  	
	
}
