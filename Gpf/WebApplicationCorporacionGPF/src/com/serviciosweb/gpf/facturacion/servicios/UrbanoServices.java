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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gpf.partnersGroupGPF.bean.GuiaElectronicaEspsRequest;
import com.gpf.partnersGroupGPF.bean.GuiaElectronicaEspsResponse;
import com.gpf.partnersGroupGPF.bean.RpGuia;
import com.gpf.partnersGroupGPF.bean.TrackingRequest;
import com.gpf.partnersGroupGPF.bean.TrackingResponse;
import com.serviciosweb.gpf.salesforce.querys.RespuestaQuery;
import com.serviciosweb.gpf.salesforce.util.ConexionVarianteSid;
import com.serviciosweb.gpf.salesforce.util.Utilities;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class UrbanoServices {

	protected static final Logger log = Logger.getLogger("UrbanoServices."
			+ RespuestaQuery.class.getName());

	static {
		new Utilities();
	}
	ConexionVarianteSid con = null;

	public String urbanoServicesResponse = "";

	public UrbanoServices(GuiaElectronicaEspsRequest rqGuia) {
		XStream xstream = new XStream(new StaxDriver());
		RpGuia guia = new RpGuia();
		GuiaElectronicaEspsResponse guiaElectronicaEspsResponse = guiaElectronicaEsps(rqGuia);
		if ("".equals(urbanoServicesResponse))
			if ("0".equals(guiaElectronicaEspsResponse.getError())) {
				guia.setCodigoGuia(guiaElectronicaEspsResponse.getGuia());
				xstream.alias("Guia", RpGuia.class);
				urbanoServicesResponse = respuestaXmlObjeto("OK",
						xstream.toXML(guia));
			} else {
				urbanoServicesResponse = respuestaXmlObjetoError(
						guiaElectronicaEspsResponse.getError(),
						guiaElectronicaEspsResponse.getMensaje());
			}
	}

	public UrbanoServices(String databaseNickName, String service) {
		this.con = new Utilities()
				.conexionFileExtern(databaseNickName, service);
	}

	public UrbanoServices() {
		this("web", "getOrder");
	}

	public UrbanoServices(TrackingRequest trackingRequest) {
		XStream xstream = new XStream(new StaxDriver());

		TrackingResponse[] trackingResponse = tracking(trackingRequest);
		if ("".equals(urbanoServicesResponse))
			if ("1".equals(trackingResponse[0].getSql_error())) {
				xstream.alias("tracking", TrackingResponse.class);
				xstream.alias("movimiento", TrackingResponse.Movimientos.class);
				xstream.alias("img", TrackingResponse.Imagen.class);

				xstream.aliasField("tracking", TrackingResponse.class,
						"sqlError");

				urbanoServicesResponse = respuestaXmlObjeto("OK",
						xstream.toXML(trackingResponse[0]));
			} else {
				urbanoServicesResponse = respuestaXmlObjetoError(
						trackingResponse[0].getSql_error(),
						trackingResponse[0].getMsg_error());
			}
	}

	public TrackingResponse[] tracking(TrackingRequest trackingRequest) {

		TrackingResponse[] trackingResponse = null;

		try {

			URL targetUrl = new URL("http://app.urbano.com.ec/ws/ue/tracking/");
			// Proxy proxy = new Proxy(Proxy.Type.HTTP, new
			// InetSocketAddress("uioproxy04.gfybeca.int", 3128));
			// HttpURLConnection conn = (HttpURLConnection)
			// targetUrl.openConnection(proxy);
			HttpURLConnection conn = (HttpURLConnection) targetUrl
					.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("user", "3072-WS");
			conn.setRequestProperty("pass",
					"7c222fb2927d828af22f592134e8932480637c0d");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);

			OutputStream os = conn.getOutputStream();
			os.write(encodeTrackingRequest(trackingRequest).getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;

			while ((output = br.readLine()) != null) {
				System.out.println(output);

				ObjectMapper mapper = new ObjectMapper();
				trackingResponse = mapper.readValue(output,
						TrackingResponse[].class);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			urbanoServicesResponse = respuestaXmlObjeto("ERROR", e.getMessage());

			e.printStackTrace();

		} catch (IOException e) {

			urbanoServicesResponse = respuestaXmlObjeto("ERROR",
					"Servicio de Urbano no esta disponible");
			e.printStackTrace();

		}

		return trackingResponse;

	}

	public GuiaElectronicaEspsResponse guiaElectronicaEsps(
			GuiaElectronicaEspsRequest rqGuia) {

		GuiaElectronicaEspsResponse guiaElectronicaEspsResponse = new GuiaElectronicaEspsResponse();

		try {

			URL targetUrl = new URL(
					"http://app.urbano.com.ec/ws/ue/guia_electronica_esp/");
			// Proxy proxy = new Proxy(Proxy.Type.HTTP, new
			// InetSocketAddress("uioproxy04.gfybeca.int", 3128));
			// HttpURLConnection conn = (HttpURLConnection)
			// targetUrl.openConnection(proxy);
			HttpURLConnection conn = (HttpURLConnection) targetUrl
					.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("user", "3072-WS");
			conn.setRequestProperty("pass",
					"7c222fb2927d828af22f592134e8932480637c0d");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);

			OutputStream os = conn.getOutputStream();
			os.write(encodeGuiaElectronicaEspsRequest(rqGuia).getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;

			while ((output = br.readLine()) != null) {
				System.out.println(output);

				ObjectMapper mapper = new ObjectMapper();
				guiaElectronicaEspsResponse = mapper.readValue(output,
						GuiaElectronicaEspsResponse.class);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			urbanoServicesResponse = respuestaXmlObjeto("ERROR", e.getMessage());

			e.printStackTrace();

		} catch (IOException e) {

			urbanoServicesResponse = respuestaXmlObjeto("ERROR",
					"Servicio de Urbano no esta disponible");
			e.printStackTrace();

		}

		return guiaElectronicaEspsResponse;

	}

	public String encodeGuiaElectronicaEspsRequest(
			GuiaElectronicaEspsRequest rqGuia) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("/data/json/rqguia.json"), rqGuia);
			String jsonInString = mapper.writeValueAsString(rqGuia);
			return "json=" + URLEncoder.encode(jsonInString, "UTF-8");
		} catch (JsonGenerationException e) {
			urbanoServicesResponse = respuestaXmlObjeto("ERROR", e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			urbanoServicesResponse = respuestaXmlObjeto("ERROR", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			urbanoServicesResponse = respuestaXmlObjeto("ERROR", e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String encodeTrackingRequest(TrackingRequest TrackingRequest) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("/data/json/rqguia.json"),
					TrackingRequest);
			String jsonInString = mapper.writeValueAsString(TrackingRequest);
			return "json=" + URLEncoder.encode(jsonInString, "UTF-8");
		} catch (JsonGenerationException e) {
			urbanoServicesResponse = respuestaXmlObjeto("ERROR", e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			urbanoServicesResponse = respuestaXmlObjeto("ERROR", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			urbanoServicesResponse = respuestaXmlObjeto("ERROR", e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public static String respuestaXmlObjeto(String resultadoString,
			String resultado) {
		resultado = resultado.replace("<?xml version=\"1.0\" ?>", "");
		resultado = resultado.replace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?> ", "");
		resultado = resultado.replace("<?xml version='1.0' encoding='UTF-8'?>",
				"");
		resultado = resultado.replace("<list>", "");
		resultado = resultado.replace("</list>", "");
		resultado = resultado.replace("<vector>", "");
		resultado = resultado.replace("</vector>", "");
		resultado = resultado.replace("__", "_");

		resultado = unescape(resultado);
		resultado = fromUTF8(resultado);
		if (resultadoString.equals("OK"))
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<transaccion>"
					+ "<httpStatus>104</httpStatus>"
					+ "<mensaje>La transaccion se ejecuto exitosamente</mensaje>"
					+ "<respuesta>" + resultado + "</respuesta>"
					+ "</transaccion>";
		else
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<transaccion>" + "<httpStatus>504</httpStatus>"
					+ "<mensaje>Error, la transaccion fallo ERROR:" + resultado
					+ "</mensaje>" + "<respuesta>" + resultado + "</respuesta>"
					+ "</transaccion>";
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
			/* Get next byte b from URL segment s */
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

	public String getDirEcommers(String docVenta, String farmacia,
			String direccion) {
		log.info("Inicio get direccion ecommers");

		String medioDescuento = "";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String queryBusqueda = Utilities.getContenidoFileUTF8(
					"queryUrbanoDireccion.sql", "salesforce/sql");
			ps = this.con.getConn().prepareStatement(queryBusqueda);
			ps.setString(1, docVenta);
			ps.setString(2, farmacia);
			rs = ps.executeQuery();

			while (rs.next()) {
				medioDescuento = rs.getString(1);
			}

		} catch (SQLException e) {
			log.warning("Error, accion: getDirEcommers \n"
					+ new Utilities().formatErrorSQL(e));
			medioDescuento = "";
		} catch (Exception e) {
			log.warning("Error, accion: getDirEcommers \n"
					+ e.getMessage());
			medioDescuento = "";
		} finally {
			try {
				if (this.con != null)
					this.con.cerrarConexion();
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		medioDescuento = !medioDescuento.isEmpty() ? medioDescuento : direccion;
		log.info("Direccion ecommers: "+medioDescuento);
		return medioDescuento;
	}

}
