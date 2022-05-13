package com.serviciosweb.gpf.facturacion.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


//import com.gpf.partnersGroupGPF.bean.GuiaElectronicaEspsRequest;
import com.gpf.partnersGroupGPF.bean.GuiaElectronicaEspsResponse;
import com.gpf.partnersGroupGPF.bean.RpGuia;
import com.gpf.partnersGroupGPF.bean.TrackingRequest;
import com.gpf.partnersGroupGPF.bean.TrackingResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class AbfTrx50Services {
	
	  Logger log=Logger.getLogger("AbfTrx50Services");
	
	public  String abfTrx50ServicesResponse ="";
	
	public AbfTrx50Services(String prIdenCade ,
			String prIdenSucu ,
			String prCA1      ,
			String prNumeFact ) {
		   XStream xstream = new XStream(new StaxDriver());
		   RpGuia guia= new RpGuia(); 
		   GuiaElectronicaEspsResponse  guiaElectronicaEspsResponse = guiaElectronicaEsps(prIdenCade ,
					 prIdenSucu ,
					 prCA1      ,
					 prNumeFact);
		   if("".equals(abfTrx50ServicesResponse))
			   if("0".equals(guiaElectronicaEspsResponse.getError())){
				   guia.setCodigoGuia(guiaElectronicaEspsResponse.getGuia());
				   xstream.alias("Guia", RpGuia.class);
				   abfTrx50ServicesResponse = respuestaXmlObjeto("OK",xstream.toXML(guia));
			   }else{
				   abfTrx50ServicesResponse = respuestaXmlObjetoError(guiaElectronicaEspsResponse.getError(),guiaElectronicaEspsResponse.getMensaje());
			   }
	}
	
	
	public AbfTrx50Services(TrackingRequest  trackingRequest) {
		   XStream xstream = new XStream(new StaxDriver());
		
		   TrackingResponse[]  trackingResponse = tracking( trackingRequest);
		   if("".equals(abfTrx50ServicesResponse))
			   if("1".equals(trackingResponse[0].getSql_error())){
				   xstream.alias("tracking", TrackingResponse.class);
				   xstream.alias("movimiento", TrackingResponse.Movimientos.class);
				   
				   xstream.aliasField("tracking", TrackingResponse.class, "sqlError");
				   
				   abfTrx50ServicesResponse = respuestaXmlObjeto("OK",xstream.toXML(trackingResponse[0]));
			   }else{
				   abfTrx50ServicesResponse = respuestaXmlObjetoError(trackingResponse[0].getSql_error(),trackingResponse[0].getMsg_error());
			   }
	}
	
	
	
	public  TrackingResponse[] tracking(TrackingRequest  trackingRequest){
		
		TrackingResponse[] trackingResponse = null ;
	
		
		try {

			//URL targetUrl = new URL("http://172.20.200.35:80/WebServicePBM/ABF_V1/TRX50.ASP"); // LOCAL
			
			//URL targetUrl = new URL("http://10.10.200.35:80/WebServicePBM/ABF_V1/TRX50.ASP"); // TEST
			
			//URL targetUrl = new URL("http://172.20.200.35:80/WebServicePBM/ABF_V1/TRX50.ASP"); // LOCAL
			
			URL targetUrl = new URL("http://uioabf01.gfybeca.int:80/WebServicePBM/ABF_V1/TRX50.ASP"); // PRODUCCION
			HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
			

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/xml");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000); 
					
			OutputStream os = conn.getOutputStream();
			os.write(encodeTrackingRequest(trackingRequest) .getBytes());
			os.flush();

		    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			while ((output = br.readLine()) != null) {
				System.out.println(output);
				
				ObjectMapper mapper = new ObjectMapper();
				trackingResponse = mapper.readValue(output, TrackingResponse[].class);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			
			 abfTrx50ServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());

			e.printStackTrace();

		} catch (IOException e) {

			abfTrx50ServicesResponse = respuestaXmlObjeto("ERROR","Servicio de WebServicePBM - TRX50 no esta disponible");
			e.printStackTrace();

		}
		
		return trackingResponse;
		
	}
	
	


	public  GuiaElectronicaEspsResponse guiaElectronicaEsps(String prIdenCade ,
			String prIdenSucu ,
			String prCA1      ,
			String prNumeFact ){
		
		 GuiaElectronicaEspsResponse guiaElectronicaEspsResponse = new GuiaElectronicaEspsResponse();
		
		try {

			//URL targetUrl = new URL("http://172.20.200.35:80/WebServicePBM/ABF_V1/TRX50.ASP"); // LOCAL
			
		//URL targetUrl = new URL("http://10.10.200.35:80/WebServicePBM/ABF_V1/TRX50.ASP"); // TEST
			//URL targetUrl = new URL("http://172.20.200.35:80/WebServicePBM/ABF_V1/TRX50.ASP"); // LOCAL
			
		URL targetUrl = new URL("http://uioabf01.gfybeca.int:80/WebServicePBM/ABF_V1/TRX50.ASP"); // PRODUCCION
			
			
			HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/xml");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000); 
					
			OutputStream os = conn.getOutputStream();
			os.write(encodeAbfTrx50Request( prIdenCade,
					 						prIdenSucu,
					 						prCA1,
					                        prNumeFact) .getBytes());
			os.flush();

		    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;

			while ((output = br.readLine()) != null) {
				//System.out.println(output);
				
				/*ObjectMapper mapper = new ObjectMapper();
				guiaElectronicaEspsResponse = mapper.readValue(output, GuiaElectronicaEspsResponse.class);*/
				
				abfTrx50ServicesResponse =output;
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			
			 abfTrx50ServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());

			e.printStackTrace();

		} catch (IOException e) {

			abfTrx50ServicesResponse = respuestaXmlObjeto("ERROR","Servicio de Urbano no esta disponible");
			e.printStackTrace();

		}
		
		return guiaElectronicaEspsResponse;
		
	}

	
	
	public  String encodeAbfTrx50Request(String prIdenCade ,
			String prIdenSucu ,
			String prCA1      ,
			String prNumeFact) {
		try {

			
			String xml = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"yes\" ?> <TRX50>"+   
					"<prIdenCade>"+prIdenCade+"</prIdenCade>"+
					"<prIdenSucu>"+prIdenSucu+"</prIdenSucu>"+
					"<prCA1>"+prCA1+"</prCA1>"+
					"<prNumeFact>"+prNumeFact+"</prNumeFact>"+
					"</TRX50>";
			
			log.info(xml);
			return xml;
		} catch (Exception e) {
			 abfTrx50ServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
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
			 abfTrx50ServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			 abfTrx50ServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			 abfTrx50ServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
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
    	   return "<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"yes\" ?><TRX><CA4></CA4><ERROR>-1</ERROR></TRX>";
       }
	  
	  public static String respuestaXmlObjetoError(String codigoError,String mensaje){
          return "<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"yes\" ?><TRX><CA4></CA4><ERROR>-1</ERROR></TRX>";
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
	  

}
