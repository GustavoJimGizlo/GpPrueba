package com.serviciosweb.gpf.facturacion.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Integraciones {
	
	public static String  consultaNaeSoap(String companiaFactura,String numeroFactura){
	            String pathXmlAuto=null;
	                try {

	                	HttpClient httpclient = new HttpClient();
	                    String pathTem=crearSoapFile(companiaFactura,numeroFactura);    
	                    String strSoapAction = "http://oracle.e1.bssv.J5542002/";
	                    File input = new File(pathTem);
	                    String url="http://uioerpwbp01:7788/PD910/ConsultaNAEService?wsdl";// produccion
	                   // String url="https://10.10.200.86:7789/PY910/ConsultaNAEService?wsdl"; // test
	                    
	                    PostMethod post = new PostMethod(url);
	                    RequestEntity entity = new FileRequestEntity(input,"text/xml; charset=ISO-8859-1"); 
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
	                 
	                  //  return consultaNaeSoap(companiaFactura, numeroFactura);
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
	  
	  private static String readInputStreamAsString(InputStream inputStream)
              throws java.io.IOException {
          StringBuffer fileData = new StringBuffer(1000);
          BufferedReader reader = new BufferedReader(new InputStreamReader(
                  inputStream));
          char[] buf = new char[1024];
          int numRead = 0;
          while ((numRead = reader.read(buf)) != -1) {
              String readData = String.valueOf(buf, 0, numRead);
              fileData.append(readData);
              buf = new char[1024];
          }
          reader.close();
          String resultado=fileData.toString();
          resultado=resultado.replace("&lt;", "<"); 
          resultado=resultado.replace("&gt;", ">"); 
          resultado=resultado.replace("<cabecera>", "<cabecera>").replace("</cabecera>","</cabecera>");

          try{
              if(!resultado.contains("codigoError")){
            	  resultado= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
   	                           "<transaccion>"+
   	                           "<httpStatus>104</httpStatus>"+
   	                           "<mensaje>La transaccion se ejecuto exitosamente</mensaje>"+
   	                           "<respuesta>"+resultado.split("<cabecera>")[1];
   	                   
   	              resultado=resultado.split("</cabecera>")[0]+"</respuesta>"+
   	                           "</transaccion>";
              }else{
   	        	resultado = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
   	                           "<transaccion>"+
   	                           "<httpStatus>504</httpStatus>"+
   	                           "<mensaje>Error: "+(resultado.split("<codigoError>")[1]).split("<")[0]+", "+(resultado.split("<mensajeError>")[1]).split("<")[0]+"</mensaje>"+
   	                           "<respuesta>consulte con el administrador JDE</respuesta>"+
   	                           "</transaccion>";
   	        	}
              
          }catch(Exception e){

          }
          return resultado;

      }
	  

      private static String crearSoapFile(String companiaFactura,String numeroFactura) {

    	  String soapTest="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:orac=\"http://oracle.e1.bssv.J5542002/\">"
         /* 		+ " <soapenv:Header>"
          		+ " <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" soapenv:mustUnderstand=\"1\">"
          		+ " <wsse:UsernameToken xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
          		+ " <wsse:Username>AFUENTES</wsse:Username>"
          		+ " <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">AFUENTES</wsse:Password>"
          		+ " </wsse:UsernameToken>"
          		+ " </wsse:Security>"
          		+ " </soapenv:Header>"*/
          		+ " <soapenv:Body>"
          		+ " <orac:getinvoiceinfo76E>"
          		+ " <companiaFactura>"+companiaFactura+"</companiaFactura>"
          		+ " <companiaOR> </companiaOR>"
          		+ " <formaDeBusqueda>2</formaDeBusqueda>"
          		+ " <numeroFactura>"+numeroFactura+"</numeroFactura>"
          		+ " <numeroOR> </numeroOR>"
          		+ " <tipoDocumentoOR> </tipoDocumentoOR>"
          		+ " </orac:getinvoiceinfo76E>"
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
      
  	public static String createDocument(String xmlSource,String numeroFactura) throws IOException {
		StringBuilder pathFolder = new StringBuilder();
		pathFolder.append("/data/doc_jde/documentos/");
		createDirectory(pathFolder.toString());
		StringBuilder pathFile = new StringBuilder();
		pathFile.append(pathFolder.toString());
		pathFile.append("document");
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
  	
  	
  	
  	
	public static String  consultaStockSoap(String item,String location,String locType){
        String pathXmlAuto=null;
            try {

            	HttpClient httpclient = new HttpClient();
                String pathTem=crearSoapFileStock(item,location,locType);   
                
                String strSoapAction = "http://www.oracle.com/retail/sim/integration/services/StoreInventoryService/v1";
                File input = new File(pathTem);
                String url="http://10.10.200.104:7010/StoreInventoryBean/StoreInventoryService?WSDL"; // validacion Stock
                
                PostMethod post = new PostMethod(url);
                RequestEntity entity = new FileRequestEntity(input,"text/xml; charset=ISO-8859-1"); 
                post.setRequestEntity(entity);
                post.setRequestHeader("SOAPAction", strSoapAction); 
                post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(10, false));
                post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,new Integer(60000));
                
                try {
                    httpclient.executeMethod(post);
                    String resultado=readInputStreamAsString(post.getResponseBodyAsStream());
                    System.out.println(" Impresion Respuesta "+resultado.trim());
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
             
              //  return consultaNaeSoap(companiaFactura, numeroFactura);
            }        
        return pathXmlAuto;
    }
  	// Envio para servicio SOAP.
  	@SuppressWarnings("unused")
	private static String crearSoapFileStock(String item,String location,String locType) {

  	String soapTest="  	<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.oracle.com/retail/sim/integration/services/StoreInventoryService/v1\" xmlns:v11=\"http://www.oracle.com/retail/integration/base/bo/InvAvailCriVo/v1\"> "
                  + "    <soapenv:Header/>                                                                                                                                                                                                                                     "
                  + "    <soapenv:Body>                                                "
                  + "       <v1:lookupAvailableInventory>                              "
                  + "          <v11:InvAvailCriVo>                                     "
                  + "             <v11:items>"+item+"</v11:items>                      "
                  + "             <v11:InvLocation>                                    "
                  + "                <v11:location>"+location+"</v11:location>         "
                  + "                <v11:loc_type>"+locType+"</v11:loc_type>          "
                  + "             </v11:InvLocation>                                   "
                  + "             <v11:store_pickup_ind>N</v11:store_pickup_ind>       "
                  + "          </v11:InvAvailCriVo>                                    "
                  + "       </v1:lookupAvailableInventory>                             "
                  + "    </soapenv:Body>                                               "
                  + "  </soapenv:Envelope>         ";
 
  	
        String pathXmlAuto=null;
        try {
            pathXmlAuto= createDocument(soapTest,item);
        } catch (IOException e) {                
            e.printStackTrace();
        }

        return pathXmlAuto;
    }
  	
  	
  	
  	
  	
  	
  	
}
