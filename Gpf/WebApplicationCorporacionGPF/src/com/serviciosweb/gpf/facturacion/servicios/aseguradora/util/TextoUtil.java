package com.serviciosweb.gpf.facturacion.servicios.aseguradora.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class TextoUtil {
	
	/**
	 * M�todo para utilizar caracteres especiales. Se duplica el m�todo para evitar posibles afectaciones
	 * en otras funcionalidades del proyecto
	 * 
	 * @param resultadoString
	 * @param resultado
	 * @return
	 */
	public static String respuestaXmlObjetoUTF8(String resultadoString,
			String resultado) {
		resultado = resultado.replace("<?xml version=\"1.0\" ?>", "");
		resultado = resultado.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ", "");
		resultado = resultado.replace("<?xml version='1.0' encoding='UTF-8'?>", "");
		resultado = resultado.replace("<list>", "");
		resultado = resultado.replace("</list>", "");
		resultado = resultado.replace("<vector>", "");
		resultado = resultado.replace("</vector>", "");
		resultado = resultado.replace("__", "_");

		resultado = resultado.replace("mensaje", "de_mensaje");
		

		resultado = resultado.replace("msj", "pre_mensaje");
		
		
		
		resultado = fromUTF8(resultado);
		if (resultadoString.equals("OK"))
			return    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<transaccion>\n"
					+ "  <httpStatus>104</httpStatus>\n"
					+ "  <mensaje>La transaccion se ejecuto exitosamente</mensaje>\n"
					+ "  <respuesta>\n" + resultado + "</respuesta>\n"
					+ "</transaccion>\n";
		else
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<transaccion>\n" 
			        + "  <httpStatus>504</httpStatus>\n"
					+ "  <mensaje>Error, la transaccion fallo ERROR: "+ resultado + "</mensaje>\n" 
			        + "  <respuesta>" + resultado+ "</respuesta>\n" 
					+ "</transaccion>\n";
	}
	
	public static String respuestaXmlObjeto(String resultadoString,
			String resultado) {
		resultado = resultado.replace("<?xml version=\"1.0\" ?>", "");
		resultado = resultado.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ", "");
		resultado = resultado.replace("<?xml version='1.0' encoding='UTF-8'?>", "");
		resultado = resultado.replace("<list>", "");
		resultado = resultado.replace("</list>", "");
		resultado = resultado.replace("<vector>", "");
		resultado = resultado.replace("</vector>", "");
		resultado = resultado.replace("__", "_");
		resultado = resultado.replace("mensaje", "de_mensaje");

		
		resultado = resultado.replace("msj", "pre_mensaje");

		resultado = unescape(resultado);
		resultado = fromUTF8(resultado);
		if (resultadoString.equals("OK"))
			return    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<transaccion>\n"
					+ "  <httpStatus>104</httpStatus>\n"
					+ "  <mensaje>La transaccion se ejecuto exitosamente</mensaje>\n"
					+ "  <respuesta>\n" + resultado + "</respuesta>\n"
					+ "</transaccion>\n";
		else
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<transaccion>\n" 
			        + "  <httpStatus>504</httpStatus>\n"
					+ "  <mensaje>Error, la transaccion fallo ERROR: "+ resultado + "</mensaje>\n" 
			        + "  <respuesta>" + resultado+ "</respuesta>\n" 
					+ "</transaccion>\n";
	}
	
	public static String respuestaXmlObjetoError(String codigoError,
			String mensaje) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<transaccion>"
				+ "<httpStatus>" + codigoError + "</httpStatus>" + "<mensaje>"
				+ mensaje + "</mensaje>" + "</transaccion>";
	}

	private static String unescape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int l = s.length();
		int ch = -1;
		int b, sumb = 0;
		for (int i = 0, more = -1; i < l; i++) {
			switch (ch = s.charAt(i)) {
			case '%':
				ch = s.charAt(++i);
				int hb = (Character.isDigit((char) ch) ? ch - '0'
						: 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				ch = s.charAt(++i);
				int lb = (Character.isDigit((char) ch) ? ch - '0'
						: 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				b = (hb << 4) | lb;
				break;
			case '+':
				b = ' ';
				break;
			default:
				b = ch;
			}
			/* Decode byte b as UTF-8, sumb collects incomplete chars */
			if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
				sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
				if (--more == 0)
					sbuf.append((char) sumb); // Add char to sbuf
			} else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
				sbuf.append((char) b); // Store in sbuf
			} else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
				sumb = b & 0x1f;
				more = 1; // Expect 1 more byte
			} else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
				sumb = b & 0x0f;
				more = 2; // Expect 2 more bytes
			} else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
				sumb = b & 0x07;
				more = 3; // Expect 3 more bytes
			} else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
				sumb = b & 0x03;
				more = 4; // Expect 4 more bytes
			} else /* if ((b & 0xfe) == 0xfc) */{ // 1111110x (yields 1 bit)
				sumb = b & 0x01;
				more = 5; // Expect 5 more bytes
			}
			/* We don't test if the UTF-8 encoding is well-formed */
		}
		return sbuf.toString();
	}
	
	public static String fromUTF8(String cadenaRevisar) {
		// return cadenaRevisar;
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ¡", "Ã¡");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ©", "Ã©");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ­", "Ã­");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ³", "Ã³");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂº", "Ãº");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ±", "Ã±");

		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ¡", "Ã�");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ©", "Ã‰");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ­", "Ã�");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ³", "Ã“");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂº", "Ãš");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ±", "Ã‘");
		return cadenaRevisar;
	}
	
	public static String encodeTrackingRequestHumana(String rutaReceta,Object recetaGenBean) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(rutaReceta), recetaGenBean);
			String jsonInString = mapper.writeValueAsString(recetaGenBean);
			return "{\"recetagen\":" +  jsonInString+"}";
		} catch (JsonGenerationException e) {
			// urbanoServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// urbanoServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			 //urbanoServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	public static String encodeTrackingRequestConfiamed(String rutaReceta,Object recetaGenBean) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(rutaReceta), recetaGenBean);
			String jsonInString = mapper.writeValueAsString(recetaGenBean);
			//return jsonInString;
			return "{\"recetagen\":" +  jsonInString+"}";
			
		} catch (JsonGenerationException e) {
			// urbanoServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// urbanoServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			 //urbanoServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * M�todo para crear los par�metros que se env�an en el body de la petici�n
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String hashMapAQueryParams(HashMap<String, String> params) throws UnsupportedEncodingException{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;
	    for(Entry<String, String> entry : params.entrySet()){
	        if (first)
	            first = false;
	        else
	            result.append("&");    
	        result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
	    }    
	    return result.toString();
	}
}
