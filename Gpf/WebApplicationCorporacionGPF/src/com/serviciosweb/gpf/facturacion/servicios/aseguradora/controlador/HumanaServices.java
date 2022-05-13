package com.serviciosweb.gpf.facturacion.servicios.aseguradora.controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.serviciosweb.gpf.facturacion.config.WebApplicationConfig;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.AseguradoraControl;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.plan.ConsultarPlanesBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaGenBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.DatosGeneralesBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.DatosPersonalesBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.infoAdicBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.ResultadoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.ResultadosBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.TokenBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.ConexionesBDD;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.EnviarCorreo;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TextoUtil;

import sun.util.logging.resources.logging;

public class HumanaServices {

	private static Logger LOG = Logger.getLogger(HumanaServices.class.getName());

	private WebApplicationConfig waConfig;

	// TOKEN
	final String TOKEN = "/data/json/rqToken.json";
	// final String URLTOKEN = "http://almacen.humana.med.ec/WSOAUTH2/token";// USAR
	// ESTE TOKEN PARA TEST
	// final String URLTOKEN = "http://serviceweb.humana.med.ec/WSOAUTH2/token"; //
	// PROD
	final String RUTARECETA = "/data/json/rqHumConfDatos.json";

	// AQUI COMENTAR O DESCOMENTAR SEGUN ESTA URL ES PARA CONFIRMAR PLANES
	// HOMOLOGADOS
	//final String URLCONFIRMARPLANES = "http://almacen.humana.med.ec/WSPrestadorFarmaceutico/api/ServicioConvenioExterno/Confirmar_Planes"; // TEST
	final String URLCONFIRMARPLANES = "http://serviceweb.humana.med.ec/WSPrestadorFarmaceutico/api/ServicioConvenioExterno/Confirmar_Planes";
	// // PROD

	public static void main(String[] args) {
		new HumanaServices("1716659592", "", "");

	}

	public String humanaServicesResponse = "";

	/*
	 * public HumanaServices(String identificacion) { humanaServicesResponse =
	 * consultaDatos(identificacion); }
	 */

	public HumanaServices(String identificacion, String url, String tipoid) {

		humanaServicesResponse = consultaDatos(identificacion, url, false);
	}

	public HumanaServices(RecetaGenBean recetaGenBean, String url) {

		humanaServicesResponse = confirmacionDatos(recetaGenBean, url, false);
	}

	public HumanaServices(Integer numero, String url) {
		humanaServicesResponse = consultaPlanes(url);
	}

	public String confirmacionDatos(RecetaGenBean recetaGenBean, String url, Boolean reintento) {

		if (reintento)
			return TextoUtil.respuestaXmlObjeto("ERROR", "Servicio de Humana no esta disponible");

		/**
		 * 
		 */
		System.out.println("************************");
		try {
			JAXBContext context = JAXBContext.newInstance(RecetaGenBean.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter out = new StringWriter();
			marshaller.marshal(recetaGenBean, out);
			String xml = out.toString();

			System.out.println(xml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		/**
		 * 
		 */
		String guiaElectronicaEspsResponse = "";
		try {
			// String url =
			// "http://almacen.humana.med.ec/WSPrestadorFarmaceutico/api/ServicioConvenioExterno/confirmacion_datos";
			// //TEST
			// String url =
			// "http://serviceweb.humana.med.ec/WSPrestadorFarmaceutico/api/ServicioConvenioExterno/confirmacion_datos";
			// // PROD

			String urlFinal = url;
			URL obj = new URL(urlFinal);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			String token = leerToken();

			con.setRequestProperty("Authorization", token);
			con.setRequestProperty("Content-Type", "application/json");
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(20000);
			int responseCode = 0;

			OutputStream os = con.getOutputStream();

			String rut = TextoUtil.encodeTrackingRequestHumana(RUTARECETA, recetaGenBean);

			os.write(TextoUtil.encodeTrackingRequestHumana(RUTARECETA, recetaGenBean).getBytes());

			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				responseCode = 401;
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				generarToquen();
				guiaElectronicaEspsResponse = confirmacionDatos(recetaGenBean, url, true);
			}

			if (responseCode == 401) {
				try {
					Thread.sleep(20000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				generarToquen();
				guiaElectronicaEspsResponse = confirmacionDatos(recetaGenBean, url, true);

				generarToquen();
				guiaElectronicaEspsResponse = confirmacionDatos(recetaGenBean, url, true);
			} else if (responseCode == 200) {

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
				String inputLine;
				String stringJson = "";
				while ((inputLine = in.readLine()) != null) {
					LOG.info(inputLine);

					stringJson = inputLine.replaceAll("\"", "");
					stringJson = stringJson.replaceAll("'", "\"");
					// stringJson = "{\"result\":"+ stringJson+"}";
				}
				in.close();

				System.out.println("Humana rest " + stringJson);
				ResultadoBean objeto = new ObjectMapper().readValue(stringJson, ResultadoBean.class);

				if ("true".equals(objeto./* getResult(). */getStatus())) {
					try {
						guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK", objeto.getAutorizacion()
						/*
						 * ("<datosGeneralesBean>" + xmlContent) .split("<datosGeneralesBean>")[1]
						 */);
					} catch (/* JAXBException */ Exception e) {
						guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR", e.getMessage());
						e.printStackTrace();
					}
				} else {
					guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
							objeto/* .getResult() */.getMensaje());
				}

			} else {

				guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
						"Servicio de Humana no esta disponible ERROR:" + responseCode);
			}

		} catch (MalformedURLException e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Humana no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Humana no esta disponible ioexecption");
			e.printStackTrace();
		}

		return guiaElectronicaEspsResponse;
	}

	public String encodeConfirmacionPlanesRequest(String codigoControl, String codigoOperacion) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			/*
			 * mapper.writeValue(new File("/data/json/rqHumConfPlanes.json"),
			 * recetaGenBean); String jsonInString =
			 * mapper.writeValueAsString(recetaGenBean);
			 */
			return "{ \"codigo_control\":\"" + codigoControl + "\", \"codigo_operacion\":" + codigoOperacion + "}";
			/*
			 * } catch (JsonGenerationException e) { // urbanoServicesResponse =
			 * respuestaXmlObjeto("ERROR",e.getMessage()); e.printStackTrace(); } catch
			 * (JsonMappingException e) { // urbanoServicesResponse =
			 * respuestaXmlObjeto("ERROR",e.getMessage()); e.printStackTrace();
			 */
		} catch (Exception e) {
			// urbanoServicesResponse = respuestaXmlObjeto("ERROR",e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String consultaDatos(String cedula, String url, Boolean reintento) {

		waConfig = new WebApplicationConfig();

		if (reintento)
			return TextoUtil.respuestaXmlObjeto("ERROR", "Servicio de Humana no esta disponible ...");

		String guiaElectronicaEspsResponse = "";
		try {
			String urlFinal = url + "?cedula=".concat(cedula);// PROD
			URL obj = new URL(urlFinal);

			LOG.info("URL servicio: " + urlFinal);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// generarToquen();
			String token = leerToken();
			LOG.info("token: " + token);

			con.setRequestProperty("Authorization", token);
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(4000);
			int responseCode = 0;
			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				responseCode = 401;
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}
				generarToquen();
				guiaElectronicaEspsResponse = consultaDatos(cedula, url, true);
			}
			if (responseCode == 401) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				generarToquen();

				guiaElectronicaEspsResponse = consultaDatos(cedula, url, true);

			} else if (responseCode == 500) {

				enviarCorreo("Servicio Web Humana no esta respondiendo");

			} else if (responseCode == 200) {

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
				String inputLine;
				String stringJson = "";
				while ((inputLine = in.readLine()) != null) {
					LOG.info(inputLine);
					stringJson = inputLine;
				}
				in.close();
				DatosGeneralesBean objeto = new ObjectMapper().readValue(stringJson, DatosGeneralesBean.class);

				objeto = validaMensaje(objeto);

				if (objeto.getResult().getMensaje().isEmpty() || "OK".equals(objeto.getResult().getMensaje())) {
					try {

						for (int i = 0; i < objeto.getRec_titular().size(); i++) {

							objeto.getRec_titular().get(i).setTi_mail("1");
							objeto.getRec_titular().get(i).setTi_telefono("1");
							// objeto.getRec_items().get(i).setId_pertinencia(null);
							objeto.getRec_titular().get(i).setTi_fechaNacimiento(null);

							// objeto.getRec_titular().get(i).setTi_infoAdic(null);

							for (int j = 0; j < objeto.getRec_titular().get(i).getRec_dependiente().size(); j++) {
								objeto.getRec_titular().get(i).getRec_dependiente().get(j).setDe_mail("1");
								objeto.getRec_titular().get(i).getRec_dependiente().get(j).setDe_telefono("1");
								// quitamos los tag de fecha de nacimiento y direccion

							}

						}

						JAXBContext jaxbContext = JAXBContext.newInstance(DatosGeneralesBean.class);

						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

						StringWriter sw = new StringWriter();

						jaxbMarshaller.marshal(objeto, sw);

						String xmlContent = sw.toString();
						System.out.println(xmlContent);

						guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK",
								"<datosGeneralesBean>" + xmlContent.split("<datosGeneralesBean>")[1]);

					} catch (JAXBException e) {
						guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR", e.getMessage());
						e.printStackTrace();
					}
				} else {
					guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
							objeto.getResult().getMensaje());
				}

			}

		} catch (MalformedURLException e) {
			humanaServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR", "Servicio de Humana no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			humanaServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR", "Servicio de Humana no esta disponible");
			e.printStackTrace();
		}

		return guiaElectronicaEspsResponse;
	}

	public String consultaPlanes(String url) {

		String guiaElectronicaEspsResponse = "";

		try {
			// String url =
			// "http://almacen.humana.med.ec/wsprestadorfarmaceutico/api/ServicioConvenioExterno/Consultar_planes";//TEST
			// String url =
			// "http://serviceweb.humana.med.ec/wsprestadorfarmaceutico/api/ServicioConvenioExterno/Consultar_planes";//PROD

			String UrlFinal = url;
			URL obj = new URL(UrlFinal);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			String token = leerToken();
			con.setRequestProperty("Authorization", token);
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(20000);
			int responseCode = 0;

			try {
				responseCode = con.getResponseCode();

			} catch (IOException e) {
				responseCode = 401;
				generarToquen();
				guiaElectronicaEspsResponse = consultaPlanes(url);
			}
			if (responseCode == 401) {
				generarToquen();
				guiaElectronicaEspsResponse = consultaPlanes(url);
				// cambio mayo 2021 Kleber Toaquiza
			} else if (responseCode == 500) {
				enviarCorreo(
						"PROCESADO CONEXION SERVCIO - HUMANA NO RESPONDEN NO SE PUEDE PROCESAR INFORMACION: CODIGO ERROR"
								+ responseCode);
			}

			else if (responseCode == 200) {

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
				String inputLine;
				String stringJson = "";
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					stringJson = inputLine;
				}
				in.close();
				// Guaradar json para validacion

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				Calendar fechaAct = GregorianCalendar.getInstance();
				String nombreArchivo = "jsonHumana_"
						+ dateFormat.format(fechaAct.getTime()).replace("/", "").replace(":", "").replace(" ", "_")
						+ ".json";
				String outputFile = "/data/jsonHumana/" + nombreArchivo;
				try {
					File file = new File(outputFile);
					// Si el archivo no existe es creado
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(stringJson);
					bw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// ponemos la entrada de confiamed en clase consultar planes
				ConsultarPlanesBean objeto = new ObjectMapper().readValue(stringJson, ConsultarPlanesBean.class);

				if (objeto.getResult().getMensaje().isEmpty() || "OK".equals(objeto.getResult().getMensaje())) {
					int resgitros = ConexionesBDD.guardarTablaMapeoAseguradora(objeto.getPlanes(),
							objeto.getResult().getCodigo(), 1105890);// codigo quemado

					if (resgitros == Integer.parseInt(objeto.getResult().getTotalRegistros())) {
						confirmarPlanes(objeto.getResult().getCodigo(), "1");
						enviarCorreo("PROCESADO EXITOSO - REGISTROS PROCESADO : " + resgitros);
					} else {
						ConexionesBDD.eliminarTablaMapeoAseguradora(objeto.getResult().getCodigo(), 1);
						confirmarPlanes(objeto.getResult().getCodigo(), "2");
					}
					guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK", "");

					// ok

				} else {

					int cont = ConexionesBDD.validarRegistrosMapeoAseguradora(objeto.getResult().getCodigo(), 1105890);// codigo
																														// quemado

					if (Integer.parseInt(objeto.getResult().getTotalRegistros()) == cont) {
						confirmarPlanes(objeto.getResult().getCodigo(), "2");
						enviarCorreo("PROCESADO EXITOSO - REGISTROS PROCESADO : " + cont);

					} else {
						ConexionesBDD.eliminarTablaMapeoAseguradora(objeto.getResult().getCodigo(), 1105890);// codigo
																												// quemado
						confirmarPlanes(objeto.getResult().getCodigo(), "2");
						enviarCorreo("PROCESADO EXITOSO - REVERSADO CODIGO DE CONFIRMACION : "
								+ objeto.getResult().getCodigo());
					}
					// enviarCorreo("PROCESADO EXITOSO");
					guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK", "");
					// enviarCorreo("Error en la Homologacion de planes");
					// guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					// objeto.getResult().getMensaje());
				}

			}

		} catch (MalformedURLException e) {
			enviarCorreo("Error en la Homologacion de planes");
			humanaServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR", "Servicio de Humana no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			humanaServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR", "Servicio de Humana no esta disponible");
			e.printStackTrace();
		}

		return guiaElectronicaEspsResponse;
	}

	// METODO PARA CONFIRMAR PLANES HOMOLOGADOS QUEMADA LA URL URLCONFIRMARPLANES
	public String confirmarPlanes(String codigo, String estado) {
		String guiaElectronicaEspsResponse = "";
		try {
			URL obj = new URL(URLCONFIRMARPLANES);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			String token = leerToken();
			con.setRequestProperty("Authorization", token);
			con.setRequestProperty("Content-Type", "application/json");
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(20000);
			int responseCode = 0;

			OutputStream os = con.getOutputStream();
			os.write(encodeConfirmacionPlanesRequest(codigo, estado).getBytes());

			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				responseCode = 401;
				generarToquen();
				guiaElectronicaEspsResponse = confirmarPlanes(codigo, estado);
			}
			if (responseCode == 401) {
				generarToquen();
				guiaElectronicaEspsResponse = confirmarPlanes(codigo, estado);
			} else if (responseCode == 200) {

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);

				}
				in.close();

				guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK", "");
			} else {

				guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
						"Servicio de Humana no esta disponible ERROR:" + responseCode);
			}

		} catch (MalformedURLException e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Humana no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Humana no esta disponible");
			e.printStackTrace();
		}

		return guiaElectronicaEspsResponse;
	}

	public DatosGeneralesBean validaMensaje(DatosGeneralesBean objeto) {
		// validamos el mensaje
		String objString = objeto.getResult().getMensaje();
		// numero de respuestas separados por pay en separador uno va toda la respuesta
		String[] ceparadorUno = objString.split("\\|");
		// crea arreglos de string numero de contratos y mensajes que contenga la
		// respuesta
		String[] ceparadorDos = new String[ceparadorUno.length - 1];
		String[] ceparadorTres = new String[ceparadorUno.length - 1];

		// llenamos numero de contratos en separador dos y en separador 3 ponemos el
		// mensaje
		int cont = 0;

		// Variable cont devuelve el count de numero de contratos
		for (int i = 1; i < ceparadorUno.length; i++) {
			ceparadorDos[cont] = ceparadorUno[i].split(" ")[0];
			ceparadorTres[cont] = ceparadorUno[i].substring(ceparadorUno[i].indexOf(" "), ceparadorUno[i].length());// c
																													// split("
																													// ")[1];
			cont++;
		}

		// valida si es titular si beneficio y one contaux en 1
		int contAux = 0;
		if (objString.contains("su contrato es un titular sin beneficio")) {
			contAux++;
		}
		List<DatosPersonalesBean> listDatosPersonalesBean = new ArrayList<DatosPersonalesBean>();
		int ntitulares;
		ntitulares = objeto.getRec_titular().size();

		// entra si encontro titular sin beneficios y
		// si numero de titulares es diff a numero de mensajes
		if (objeto.getRec_titular().size() != cont || contAux > 0) {
			// inicio for recorre titulares
			for (DatosPersonalesBean reTitular : objeto.getRec_titular())

			{

				int cont1 = 0;
				for (int j = 0; j < ceparadorDos.length; j++) {
					if (ceparadorDos[j].equals(reTitular.getTi_autnumcont())) {
						// bandera q valida el numero de contrato del mensaje con el numero de contrato
						// del titular
						cont1++;
					}
				}

				// validacion de % de coberturas
				// LOGICA PARA CONSULTAR porcentaje de cobertura
				// int ninfoa = reTitular.getTi_infoAdic().size();

				// if ( ninfoa >= 1 )

				// {

				// infoAdicBean InfoAdicBean= new infoAdicBean();
				// infoAdicBean
				// for(int ia =0;ia<ninfoa;ia++){
				// if ("Si".equals(listDatosPersonalesBean.get(k).getTi_estado()))
				// validadorporcentaje = true;
				// if("TITULAR".equals(listDatosPersonalesBean.get(k).getRec_dependiente().get(w).getDe_tipo()))

				// InfoAdicBean.setClave(reTitular.getTi_infoAdic().get(ia).getClave());
				// InfoAdicBean.setValor(reTitular.getTi_infoAdic().get(ia).getValor());
				// System.out.println(reTitular.getTi_infoAdic().get(ia).getClave());
				// System.out.println(reTitular.getTi_infoAdic().get(ia).getValor());
				// comparamos clave con la tabla ad_cod_beneficio_ase
				// }
				// agregamos info adic a objeto

				// }
				// else
				// {
				// no viene informacion adicional en dependientes
				// reTitular.getTi_infoAdic().clear();

				// }
				// objeto.getRec_titular().get(i).setTi_infoAdic(null);
				// reTitular.getTi_infoAdic().

				// fin de beneficios por %

				// validamos ti estado
				Boolean validador = new Boolean(true);
				if ("No".equals(reTitular.getTi_estado())) {
					validador = false;
					int c = 0;
					int numdep = reTitular.getRec_dependiente().size();

					for (int z = 0; z < numdep; z++) {
						String tipo = reTitular.getRec_dependiente().get(z).getDe_tipo();

						if ("TITULAR".equals(tipo))
						// encuentras al menos en titular
						{
							validador = true;
							c = c + 1;

						}
					} // fin de for
				} // fin de if

				// agrega titular a la lista cuando 1 contrato no tiene observaciones o 2 el
				// titular es titular sin beneficio
				// 3 numero de titulares es igual a numeros de mensajes en el contrato
				if (cont1 == 0 || (objeto.getRec_titular().size() == cont || contAux > 0)) {
					// agregamos contratos a lista de titulares
					if (Boolean.TRUE.equals(validador))

					{

						listDatosPersonalesBean.add(reTitular);
					}
				}
			} // fin for recorre titulares

			// recorre dependientes
			for (int k = 0; k < listDatosPersonalesBean.size(); k++) {

				for (int w = 0; w < listDatosPersonalesBean.get(k).getRec_dependiente().size(); w++) {
					listDatosPersonalesBean.get(k).getRec_dependiente().get(w).setMensaje("A");

					if ("TITULAR".equals(listDatosPersonalesBean.get(k).getRec_dependiente().get(w).getDe_tipo())) {
						int cont1 = 0;

						for (int j = 0; j < ceparadorTres.length; j++) {
							if (listDatosPersonalesBean.get(k).getTi_autnumcont().equals(ceparadorDos[j])
									&& ceparadorTres[j].contains("sin beneficio")) {
								cont1++;
							}
						} // fin for de separador tres

						// aqui ponemos codigo para boorar los registros que tengan en ti_estado NO
						// sfba 15072020
						int cone = 0;
						String data = listDatosPersonalesBean.get(k).getTi_estado();

						if (listDatosPersonalesBean.get(k).getTi_estado().equals("No")
								&& listDatosPersonalesBean.get(k).getTi_autcodigo().equals(
										listDatosPersonalesBean.get(k).getRec_dependiente().get(w).getDe_autcodigo())) {
							String contratoo = listDatosPersonalesBean.get(k).getTi_autcodigo();
							String contratod = listDatosPersonalesBean.get(k).getRec_dependiente().get(w)
									.getDe_autcodigo();
							cone++;
						}

						listDatosPersonalesBean.get(k).getRec_dependiente().get(w).setMensaje("A");

						// validacion final informacion adicional PARA TITULARES
						int cinfoa = listDatosPersonalesBean.get(k).getTi_infoAdic().size();
						if (cone > 0 || cont1 > 0 || cinfoa == 0) {
							// borramos el titular del listado de dependientes
							listDatosPersonalesBean.get(k).getRec_dependiente().remove(w);
						}

						if (cinfoa == 0) {
							listDatosPersonalesBean.get(k).getRec_dependiente().clear();
						}

					} // fin de if

				} // fin de for dependientes

			} // fin de for titulares

			//validacion adicional mensaje a
			for (int b = 0; b < listDatosPersonalesBean.size(); b++) {
				for (int z = 0; z < listDatosPersonalesBean.get(b).getRec_dependiente().size(); z++) 
				{	
				listDatosPersonalesBean.get(b).getRec_dependiente().get(z).setMensaje("A");
				}
			}	
			//
			
			
			for (int a = 0; a < listDatosPersonalesBean.size(); a++) {
				listDatosPersonalesBean.get(a).setTi_contrato(listDatosPersonalesBean.get(a).getTi_contrato()
						.concat(listDatosPersonalesBean.get(a).getTi_codplan()));

				listDatosPersonalesBean.get(a).setTi_mensaje("A");

				listDatosPersonalesBean.get(a).setTi_producto("0");

			}

			if (!listDatosPersonalesBean.isEmpty()) {
				objeto.setRec_titular(new ArrayList<DatosPersonalesBean>());
				objeto.setRec_titular(listDatosPersonalesBean);
				ResultadosBean result = new ResultadosBean();
				// aqui ponemos ok en el mensaje
				result.setMensaje("OK");
				result.setStatus("true");
				;
				objeto.setResult(result);
			}
		}

		return objeto;

	}

	// cambio token Humana de usuario y clave
	public void generarToquen() {

		try {

			String cred = waConfig.epHumanaConfig.credenciales;
			String url = waConfig.epHumanaConfig.urlToken;

			// LOG.info("URL servicio: "+URLTOKEN);
			// URL targetUrl = new URL(URLTOKEN);
			LOG.info("URL servicio: " + url);
			URL targetUrl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(20000);
			OutputStream os = conn.getOutputStream();
			// os.write("grant_type=password&username=admin&password=adminpass".getBytes());
			// os.write("grant_type=password&username=usrabf&password=E.nwUfo302".getBytes());
			os.write(cred.getBytes());

			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			String token = "";
			while ((output = br.readLine()) != null) {
				LOG.info(output);
				output = output.replace(".issued", "issued");
				output = output.replace(".expires", "expires");
				TokenBean objeto = new ObjectMapper().readValue(output, TokenBean.class);
				token = objeto.getAccess_token();
			}

			escribirToken("Bearer ".concat(token));
			conn.disconnect();

		} catch (MalformedURLException e) {

			humanaServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR", "Servicio de Humana no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			humanaServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR", "Servicio de Humana no esta disponible");
			e.printStackTrace();
		}
	}

	public void escribirToken(String token) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(TOKEN);
			pw = new PrintWriter(fichero);
			pw.println(token);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public String leerToken() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String result = "";
		try {
			archivo = new File(TOKEN);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				result = linea;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	// get and set
	public String getHumanaServicesResponse() {
		return humanaServicesResponse;
	}

	public void setHumanaServicesResponse(String humanaServicesResponse) {
		this.humanaServicesResponse = humanaServicesResponse;
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

	private void enviarCorreo(String valor) {

		try {

			Date fechaCarga = new Date();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

			String emailNotifica = getContenidoFile("MAIL_NOTIFICACION.txt", "ABF");
			String accion = "CONSULTA PLANES ASEGURADORA";
			String asunto = accion + " - " + valor;

			new EnviarCorreo("homologacionPlanes@corporaciongpf.com", emailNotifica, asunto,
					"Estimad@,<br> <br>Ha finalizado el procesamiento homalogacion de planes con un status: " + valor
							+ " <br><br> Fecha Carga:" + dateFormat.format(fechaCarga));
		} catch (Exception e) {
		}
	}

	public static String getContenidoFile(String nombreArchivo, String folder) {
		File documento = new File("/data/" + folder + "/" + nombreArchivo);
		String sCadena = "";
		String retorno = "";
		BufferedReader bf = null;
		if (!documento.exists()) {
			Logger.getLogger("getContenidoFile").info("ARCHIVO NO EXISTE : " + nombreArchivo);
			return null;
		}

		try {
			bf = new BufferedReader(new FileReader(documento));
			while ((sCadena = bf.readLine()) != null) {
				retorno += sCadena;
			}

		} catch (FileNotFoundException fnfe) {
			return null;
		} catch (IOException ioe) {
			return null;
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Logger.getLogger("getQuery").info("ARCHIVO ABIERTO : " + nombreArchivo);
		return retorno;
	}

}
