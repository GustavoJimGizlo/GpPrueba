package com.serviciosweb.gpf.facturacion.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.serviciosweb.gpf.facturacion.servicios.FileRequestEntity;

public class GestionaService {

	public static void main(String[] args) {
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		 mapa.put("user", "sana4933");
		 mapa.put("passwd", "S@n59G02#51TRfwa");
		 mapa.put("tipocedula", "CEDULA");
		 mapa.put("cedula", "1716659592");
		 mapa.put("annac", "1985");
		 mapa.put("banco", "1");
		 mapa.put("numerocuenta", "754585202");
		 mapa.put("numerocheque", "1");
		 mapa.put("monto", "60.25");
		 mapa.put("codestablecimiento", "1");
		 mapa.put("sexo", "1");
		 mapa.put("telefono", "0983614350");
		    
		//String datos = consultaCheque(mapa);
		//System.out.println("TEst " + datos);
	}

	public String consultaCheque(HashMap<String, Object> parametros) {
		String pathXmlAuto = null;
		try {

			HttpClient httpclient = new HttpClient();
			String pathTem = crearSoapFile(parametros);
			String strSoapAction = "http://www.creditreport.ec/v001c100";
			File input = new File(pathTem);
			String url = "http://www.gestiona.ec/web_sanasana/wsGestiona.asmx?WSDL"; // test
			PostMethod post = new PostMethod(url);
			RequestEntity entity = new FileRequestEntity(input,"text/xml; charset=ISO-8859-1");
			post.setRequestEntity(entity);
			post.setRequestHeader("SOAPAction", strSoapAction);
			post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(10, false));
			post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,new Integer(60000));

			try {
				httpclient.executeMethod(post);
				String resultado = readInputStreamAsString(post.getResponseBodyAsStream());
				return remove1(resultado.trim());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				post.releaseConnection();
			}

		} catch (Exception e) {
			e.printStackTrace();
			pathXmlAuto = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			            + "<Transaccion><Respuesta>Error: No se puede leer el WS de gestiona</Respuesta></Transaccion>";
		}
		return pathXmlAuto;
	}

	public static String remove1(String input) {

		String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á«";

		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
		String output = input;
		for (int i = 0; i < original.length(); i++) {

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
		String resultado = fileData.toString();
		resultado = resultado.replace("&lt;", "<");
		resultado = resultado.replace("&gt;", ">");
		resultado = resultado.replace("<cabecera>", "<cabecera>").replace(
				"</cabecera>", "</cabecera>");

		try {
			if (resultado.contains("v001c100Result")) {
				resultado = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						    +resultado.split("<v001c100Result>")[1];

				resultado = resultado.split("</v001c100Result>")[0] ;
			} else {
				resultado = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			            + "<Transaccion><Respuesta>No se puede leer respuesta: Valide el WS Gestiona</Respuesta></Transaccion>";;
			}

		} catch (Exception e) {

		}
		return resultado;

	}

	private static String crearSoapFile(HashMap<String, Object> parametros) {
		String pametrosString="";
		
		if(!parametros.isEmpty())
		for (String parametro:parametros.keySet()){
			pametrosString+="<"+parametro+">"+parametros.get(parametro).toString() +"</"+parametro+">";
		}
		
		

		String soapTest =
		" <Envelope xmlns=\"http://www.w3.org/2003/05/soap-envelope\">"+
	    " <Body>                                                      "+
	    " <v001c100 xmlns=\"http://www.creditreport.ec/\">            "+
	    " <user>sana4933</user> 									  "+
        " <passwd>S@n59G02#51TRfwa</passwd>                           "+
	    pametrosString +
	    "    </v001c100>                                              "+
	    "  </Body>                                                    "+
	    " </Envelope>                                                 ";
		
		String pathXmlAuto = null;
		try {
			pathXmlAuto = createDocument(soapTest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pathXmlAuto;
	}

	public static String createDocument(String xmlSource)
			throws IOException {
		StringBuilder pathFolder = new StringBuilder();
		pathFolder.append("/data/cheques/");
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


}
