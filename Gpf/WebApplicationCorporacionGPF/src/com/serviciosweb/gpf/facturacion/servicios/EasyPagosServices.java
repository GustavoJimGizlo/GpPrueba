package com.serviciosweb.gpf.facturacion.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gpf.easypagos.bean.BlockEmployeeRequest;
import com.gpf.easypagos.bean.Employee;
import com.gpf.easypagos.bean.EmployeeRequest;
import com.gpf.easypagos.bean.NewEmployeeRequest;
import com.gpf.easypagos.bean.NewEmployeeResponse;
import com.gpf.easypagos.bean.ReportByUserSummaryRequest;
import com.gpf.easypagos.bean.ReportByUserSummaryResponse;
import com.gpf.easypagos.bean.ReportByUserSummaryResponseFinal;
import com.gpf.easypagos.bean.Total;
import com.gpf.easypagos.bean.TotalFinal;
import com.gpf.partnersGroupGPF.bean.TrackingRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;




public class EasyPagosServices {
	
	Logger log=Logger.getLogger(this.getClass().getName());
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		String resultado ="{\"id\":1,\"name\":\"Lokesh Gupta\",\"age\":34,\"location\":\"India\"}";
		String resultado2 ="{\"Success\":false,\"MessageCode\":4000,\"Message\":\"Se ha producido un error al actualizar la informacion\",\"ModelState\":{\"model.PID\": \"req\",\"model.User\": \"req\"}}";
		System.out.println(resultado);
		
		
		 
		System.out.println(resultado2);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		Employee a =  mapper.readValue(resultado,Employee.class);
		
		ObjectMapper mapper2 = new ObjectMapper();
		mapper2.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		NewEmployeeResponse a1 =  mapper2.readValue(resultado2.toLowerCase(),NewEmployeeResponse.class);

		System.out.println(a1.toString());
		
		
		System.out.println(a.toString());
		
	}
	
	public  String easyPagosResponse = "";//xmlResponse();
	
	public String xmlResponse(){
		XStream dato = new XStream(new StaxDriver());
		dato.alias("Response", NewEmployeeResponse.class);
		return respuestaXmlObjeto("OK",dato.toXML(new NewEmployeeResponse()));
	}
	
	
	public EasyPagosServices(NewEmployeeRequest  newEmployeeRequest) {
		   XStream xstream = new XStream(new StaxDriver());
		   NewEmployeeResponse  newEmployeeResponse = newEmployee(newEmployeeRequest);
		   if("".equals(easyPagosResponse))
			   if(newEmployeeResponse.getMessagecode() == 0 ){
				   xstream.alias("ResponseEasyPagos", EmployeeRequest.class);
				   xstream.alias("ResponseEasyPagos", NewEmployeeResponse.class);
				   easyPagosResponse = respuestaXmlObjeto("OK",xstream.toXML(newEmployeeResponse));
			   }else{
				   xstream.alias("ResponseEasyPagos", EmployeeRequest.class);
				   xstream.alias("ResponseEasyPagos", NewEmployeeResponse.class);
				   easyPagosResponse = respuestaXmlObjeto("OK",xstream.toXML(newEmployeeResponse));
			   }
	}
	
	
	
	public EasyPagosServices(BlockEmployeeRequest  blockEmployeeRequest) {
		   XStream xstream = new XStream(new StaxDriver());
		   NewEmployeeResponse  newEmployeeResponse = blockEmployee(blockEmployeeRequest);
		   if("".equals(easyPagosResponse))
			   if(newEmployeeResponse.getMessagecode() == 0 ){
				   xstream.alias("ResponseEasyPagos", EmployeeRequest.class);
				   xstream.alias("ResponseEasyPagos", NewEmployeeResponse.class);
				   easyPagosResponse = respuestaXmlObjeto("OK",xstream.toXML(newEmployeeResponse));
			   }else{
				   xstream.alias("ResponseEasyPagos", EmployeeRequest.class);
				   xstream.alias("ResponseEasyPagos", NewEmployeeResponse.class);
				   easyPagosResponse = respuestaXmlObjeto("OK",xstream.toXML(newEmployeeResponse));
			   }
	}
	
	
	public EasyPagosServices(ReportByUserSummaryRequest  reportByUserSummaryRequest) {
		   XStream xstream = new XStream(new StaxDriver());
		   ReportByUserSummaryResponse  reportByUserSummaryResponse = reportByUserSummary(reportByUserSummaryRequest);
		   if("".equals(easyPagosResponse))
			   if("true".equals(reportByUserSummaryResponse.getSuccess())){
				   ReportByUserSummaryResponseFinal reportByUserSummaryResponseFinal = trasformacion(reportByUserSummaryResponse );
				   xstream.alias("ResponseEasyPagos", ReportByUserSummaryResponseFinal.class);
				   xstream.alias("Total", TotalFinal.class);
				   easyPagosResponse = respuestaXmlObjeto("OK",xstream.toXML(reportByUserSummaryResponseFinal));
			   }else{
				   xstream.alias("ResponseEasyPagos", ReportByUserSummaryResponse.class);
				   xstream.alias("Total", Total.class);
				   easyPagosResponse = respuestaXmlObjeto("OK",xstream.toXML(reportByUserSummaryResponse));
			   }
	}
	
	
    public ReportByUserSummaryResponseFinal trasformacion(ReportByUserSummaryResponse reportByUserSummaryResponse ){
    	
    	List<Total> datos = reportByUserSummaryResponse.getTotals();
    	ReportByUserSummaryResponseFinal reportByUserSummaryResponseFinal = new ReportByUserSummaryResponseFinal();
    	TotalFinal totals = new TotalFinal();
    	for(Total dato:datos){
    		if("SND".equals(dato.getType().toUpperCase())){
    			totals.setAmountIn(dato.getAmount());
    		}
    		if("RCV".equals(dato.getType().toUpperCase())){
    			totals.setAmountOut(dato.getAmount());
    		}
    	}
    	reportByUserSummaryResponseFinal.setSuccess(reportByUserSummaryResponse.getSuccess());
    	reportByUserSummaryResponseFinal.setTotals(totals);
    	return reportByUserSummaryResponseFinal;
    }


	public NewEmployeeResponse newEmployee(NewEmployeeRequest newEmployeeRequest) {
		NewEmployeeResponse newEmployeeResponse = new NewEmployeeResponse();
		HttpClient httpclient = new HttpClient();
		
		try {
			encodeNewEmployeeRequest(newEmployeeRequest);
			String pathTem = new File("/data/json/rqEasyPagos.json").getPath();
			File input = new File(pathTem);
			String url = readContentFile("/data/wsdl/urlEasyPagos.txt") + "/EasypagosConnect/api/Manage/NewEmployee";
			httpclient.getHostConfiguration().setProxy("uioproxy04.gfybeca.int", 3128);

			PostMethod post = new PostMethod(url);
			RequestEntity entity = new FileRequestEntity(input,"application/json; charset=ISO-8859-1");
			post.setRequestEntity(entity);
			post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(10, false));
			post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,new Integer(60000));

			try {
				httpclient.executeMethod(post);
				String resultado =inputStreamToString (post.getResponseBodyAsStream());
				resultado = resultado.replaceAll("[^\\x00-\\x7F]", "");
   			    log.info(resultado);
				ObjectMapper mapper = new ObjectMapper();

				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
				newEmployeeResponse =  mapper.readValue(resultado.toLowerCase(),NewEmployeeResponse.class);

			} catch (Exception e) {
				easyPagosResponse = respuestaXmlObjeto("ERROR", "El servicio EasyPagos respuesta inesperada");
				e.printStackTrace();
			} finally {
				post.releaseConnection();
			}

		} catch (Exception e) {
			easyPagosResponse = respuestaXmlObjeto("ERROR", "Servicio de EasyPagos no esta disponible");
			e.printStackTrace();

		}

	
		return newEmployeeResponse;

	}
	
	
	
	public NewEmployeeResponse blockEmployee(BlockEmployeeRequest blockEmployeeRequest) {
		NewEmployeeResponse newEmployeeResponse = new NewEmployeeResponse();
		HttpClient httpclient = new HttpClient();
		
		try {
			encodeNewEmployeeRequest(blockEmployeeRequest);
			String pathTem = new File("/data/json/rqEasyPagos.json").getPath();
			File input = new File(pathTem);
			String url = readContentFile("/data/wsdl/urlEasyPagos.txt") + "/EasypagosConnect/api/Manage/BlockEmployee";
			httpclient.getHostConfiguration().setProxy("uioproxy04.gfybeca.int", 3128);

			PostMethod post = new PostMethod(url);
			RequestEntity entity = new FileRequestEntity(input,"application/json; charset=ISO-8859-1");
			post.setRequestEntity(entity);
			post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(10, false));
			post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,new Integer(60000));

			try {
				httpclient.executeMethod(post);
				String resultado =inputStreamToString (post.getResponseBodyAsStream());
				resultado = resultado.replaceAll("[^\\x00-\\x7F]", "");
   			    log.info(resultado);
				ObjectMapper mapper = new ObjectMapper();

				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
				newEmployeeResponse =  mapper.readValue(resultado.toLowerCase(),NewEmployeeResponse.class);

			} catch (Exception e) {
				easyPagosResponse = respuestaXmlObjeto("ERROR", "El servicio EasyPagos respuesta inesperada");
				e.printStackTrace();
			} finally {
				post.releaseConnection();
			}

		} catch (Exception e) {
			easyPagosResponse = respuestaXmlObjeto("ERROR", "Servicio de EasyPagos no esta disponible");
			e.printStackTrace();

		}

	
		return newEmployeeResponse;

	}
	
	
	
	public ReportByUserSummaryResponse reportByUserSummary(ReportByUserSummaryRequest reportByUserSummaryRequest) {
		ReportByUserSummaryResponse reportByUserSummaryResponse = new ReportByUserSummaryResponse();
		HttpClient httpclient = new HttpClient();
		
		try {
			encodeNewEmployeeRequest(reportByUserSummaryRequest);
			String pathTem = new File("/data/json/rqEasyPagos.json").getPath();
			File input = new File(pathTem);
			String url = readContentFile("/data/wsdl/urlEasyPagos.txt") + "/EasypagosConnect/api/Operation/ReportsByUserSummary";
			httpclient.getHostConfiguration().setProxy("uioproxy04.gfybeca.int", 3128);

			PostMethod post = new PostMethod(url);
			RequestEntity entity = new FileRequestEntity(input,"application/json; charset=ISO-8859-1");
			post.setRequestEntity(entity);
			post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(10, false));
			post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,new Integer(60000));

			try {
				httpclient.executeMethod(post);
				String resultado =inputStreamToString (post.getResponseBodyAsStream());
				resultado = resultado.replaceAll("[^\\x00-\\x7F]", "");
   			    log.info(resultado);
				ObjectMapper mapper = new ObjectMapper();

				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
				reportByUserSummaryResponse =  mapper.readValue(resultado.toLowerCase(),ReportByUserSummaryResponse.class);

			} catch (Exception e) {
				easyPagosResponse = respuestaXmlObjeto("ERROR", "El servicio EasyPagos respuesta inesperada");
				e.printStackTrace();
			} finally {
				post.releaseConnection();
			}

		} catch (Exception e) {
			easyPagosResponse = respuestaXmlObjeto("ERROR", "Servicio de EasyPagos no esta disponible");
			e.printStackTrace();

		}

	
		return reportByUserSummaryResponse;

	}
	
	
	public String inputStreamToString ( InputStream inputStream) throws IOException{
		
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		String resultado = fileData.toString();
		
		return resultado;
	}
	
	
	public   String encodeNewEmployeeRequest(NewEmployeeRequest newEmployeeRequest) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("/data/json/rqEasyPagos.json"), newEmployeeRequest);
			String jsonInString = mapper.writeValueAsString(newEmployeeRequest);
			return URLEncoder.encode(jsonInString, "UTF-8");
		} catch (JsonGenerationException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	
	public   String encodeNewEmployeeRequest(BlockEmployeeRequest blockEmployeeRequest) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("/data/json/rqEasyPagos.json"), blockEmployeeRequest);
			String jsonInString = mapper.writeValueAsString(blockEmployeeRequest);
			return URLEncoder.encode(jsonInString, "UTF-8");
		} catch (JsonGenerationException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	
	public   String encodeNewEmployeeRequest(ReportByUserSummaryRequest reportByUserSummaryRequest) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("/data/json/rqEasyPagos.json"), reportByUserSummaryRequest);
			String jsonInString = mapper.writeValueAsString(reportByUserSummaryRequest);
			return URLEncoder.encode(jsonInString, "UTF-8");
		} catch (JsonGenerationException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	public  String encodeTrackingRequest(TrackingRequest TrackingRequest) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("/data/json/rqguia.json"), TrackingRequest);
			String jsonInString = mapper.writeValueAsString(TrackingRequest);
			return "json=" + URLEncoder.encode(jsonInString, "UTF-8");
		} catch (JsonGenerationException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			 easyPagosResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
		
	

	
	
	  public static String respuestaXmlObjeto(String resultadoString,String resultado){
          resultado=resultado.replace("<?xml version=\"1.0\" ?>","");
          resultado=resultado.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
          resultado=resultado.replace("<?xml version='1.0' encoding='UTF-8'?>","");
          resultado=resultado.replace("<list>","");
          resultado=resultado.replace("</list>","");
          resultado=resultado.replace("<vector>","");
          resultado=resultado.replace("</vector>","");
          resultado=resultado.replace("__","_");
          
         
          resultado=unescape(resultado);
          resultado=fromUTF8(resultado);
        if(resultadoString.equals("OK"))
          return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                          "<transaccion>"+
                          "<httpStatus>104</httpStatus>"+
                          "<mensaje>La transaccion se ejecuto exitosamente</mensaje>"+
                          "<respuesta>"+resultado+"</respuesta>"+
                          "</transaccion>";
       else
          return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                          "<transaccion>"+
                          "<httpStatus>504</httpStatus>"+
                          "<mensaje>Error, la transaccion fallo ERROR:"+resultado+"</mensaje>"+
                          "<respuesta>"+resultado+"</respuesta>"+
                          "</transaccion>";
       }
	  
	  public static String respuestaXmlObjetoError(String codigoError,String mensaje){
          return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                          "<transaccion>"+
                          "<httpStatus>"+codigoError+"</httpStatus>"+
                          "<mensaje>"+mensaje+"</mensaje>"+
                          "</transaccion>";
       }
	  
	  
	  
	  
	  
	   private static String unescape(String s) {
		    StringBuffer sbuf = new StringBuffer () ;
		    int l  = s.length() ;
		    int ch = -1 ;
		    int b, sumb = 0;
		    for (int i = 0, more = -1 ; i < l ; i++) {
		      /* Get next byte b from URL segment s */
		      switch (ch = s.charAt(i)) {
			case '%':
			  ch = s.charAt (++i) ;
			  int hb = (Character.isDigit ((char) ch)
				    ? ch - '0'
				    : 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
			  ch = s.charAt (++i) ;
			  int lb = (Character.isDigit ((char) ch)
				    ? ch - '0'
				    : 10+Character.toLowerCase ((char) ch)-'a') & 0xF ;
			  b = (hb << 4) | lb ;
			  break ;
			case '+':
			  b = ' ' ;
			  break ;
			default:
			  b = ch ;
		      }
		      /* Decode byte b as UTF-8, sumb collects incomplete chars */
		      if ((b & 0xc0) == 0x80) {			// 10xxxxxx (continuation byte)
			sumb = (sumb << 6) | (b & 0x3f) ;	// Add 6 bits to sumb
			if (--more == 0) sbuf.append((char) sumb) ; // Add char to sbuf
		      } else if ((b & 0x80) == 0x00) {		// 0xxxxxxx (yields 7 bits)
			sbuf.append((char) b) ;			// Store in sbuf
		      } else if ((b & 0xe0) == 0xc0) {		// 110xxxxx (yields 5 bits)
			sumb = b & 0x1f;
			more = 1;				// Expect 1 more byte
		      } else if ((b & 0xf0) == 0xe0) {		// 1110xxxx (yields 4 bits)
			sumb = b & 0x0f;
			more = 2;				// Expect 2 more bytes
		      } else if ((b & 0xf8) == 0xf0) {		// 11110xxx (yields 3 bits)
			sumb = b & 0x07;
			more = 3;				// Expect 3 more bytes
		      } else if ((b & 0xfc) == 0xf8) {		// 111110xx (yields 2 bits)
			sumb = b & 0x03;
			more = 4;				// Expect 4 more bytes
		      } else /*if ((b & 0xfe) == 0xfc)*/ {	// 1111110x (yields 1 bit)
			sumb = b & 0x01;
			more = 5;				// Expect 5 more bytes
		      }
		      /* We don't test if the UTF-8 encoding is well-formed */
		    }
		    return sbuf.toString() ;
		  }
	   
	   

       public static String fromUTF8(String cadenaRevisar){
               //return cadenaRevisar;
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ¡","Ã¡");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ©","Ã©");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ­","Ã­");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ³","Ã³");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂº","Ãº");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ±","Ã±");

               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ¡","Ã�");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ©","Ã‰");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ­","Ã�");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ³","Ã“");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂº","Ãš");
               cadenaRevisar=cadenaRevisar.replace("ÃƒÂ±","Ã‘");
               return cadenaRevisar;
       }
       
       
       public static String UTF8(String cadenaRevisar){
           //return cadenaRevisar;
           cadenaRevisar=cadenaRevisar.replace("³","o");
           return cadenaRevisar;
   }
       
       
  	 
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
	  

}
