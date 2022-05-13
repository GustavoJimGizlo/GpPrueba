package com.serviciosweb.gpf.facturacion.servicios.aseguradora.controlador;

import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;

import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.SSLFix;
import com.serviciosweb.gpf.facturacion.config.WebApplicationConfig;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.confiamed.RecetaConfiamed;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.DatosGeneralesGenericoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.DatosTitularesGenericoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.infoAdicBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.plan.ConsultarPlanesBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.receta.ResultadoGenericoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaGenBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaPerBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.ConexionesBDD;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.EnviarCorreo;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TextoUtil;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TokenUtil;


import com.serviciosweb.gpf.facturacion.config.WebApplicationConfig;

//import com.sun.net.ssl.TrustManager;
//impo
//import com.serviciosweb.gpf.facturacion.config.WebApplicationConfig;


import org.apache.commons.codec.binary.Base64;

public class ConfiamedServices {
	private static Logger LOG = Logger.getLogger(ConfiamedServices.class.getName());
	private WebApplicationConfig waConfig;
	// TOKEN
	final String TOKEN ="/data/json/rqConfiamedToken.json";
	final String RUTARECETA = "/data/json/rqConfiamedConfDatos.json";
	// URL DE CONFIRMAR PLANES HOMOLOGADOS
	
	// final String URLCONFIRMARPLANES =
	// "http://200.115.40.66/ServicioExternoConfiamed/ServicioExterno/ConfirmaHomologacion";//para
	// test homologacion
	// sfba 11 agosto 2020
	
	//final String URLCONFIRMARPLANES = "http://200.115.40.66/Servicio_Proceso/ServicioProceso.svc/confirmacion_homologacion"; // para test
	
	final String URLCONFIRMARPLANES ="http://servicios.confiamed.com/ServicioExternoConfiamed/ServicioExterno/ConfirmaHomologacion";// para prd
	public String confiamedServicesResponse = "";
	//variable para properties
	
 	// constructores
	public ConfiamedServices(String identificacion, String url, String tipoid) {
		confiamedServicesResponse = consultaDatos(identificacion, url);
	}

	public ConfiamedServices(RecetaGenBean recetaGenBean, String url) {
		confiamedServicesResponse = confirmacionDatos(recetaGenBean, url);
	}

	public ConfiamedServices(Integer numero, String url) {
		confiamedServicesResponse = consultaPlanes(url);
		// confiamedServicesResponse = confirmarPlanes("2020-03-12 12:54:21Z".replace("
		// ", "%"),"1");
	}

	// get and set
	public String getConfiamedServicesResponse() {
		return confiamedServicesResponse;
	}

	public void setConfiamedServicesResponse(String confiamedServicesResponse) {
		this.confiamedServicesResponse = confiamedServicesResponse;
	}

	// Metodos de consulta con ws
	public String consultaDatos(String cedula, String url) {
		
		
		//url ="https://servicios.externos.confiamed.com:8243/ServicioExternoContext/1.0.0/Consulta_Datos_GPF";
		
		//url ="https://200.115.38.201:8243/ServicioExternoContext/1.0.0/Consulta_Datos_GPF";
		
		//obtiene configuracion del token
		waConfig = new WebApplicationConfig();
	    String token="";
	    //generar el token
	    //token = this.generarToquen();
	
	    String guiaElectronicaEspsResponse = "";
		try {
			
			String urlFinal = url + "?cedula=".concat(cedula);
			//baja certificados
			SSLFix sf = new SSLFix();
			sf.execute();
			//urlFinal ="https://200.115.38.201:8243/ServicioExternoContext/1.0.0/Consulta_Datos_GPF?cedula=1720233533";
			
			
			
			URL obj = new URL(urlFinal);
			//configura protocolos
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			//con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			//token = "Bearer " + this.generarToquen();
			//generarToquen();
			
			token =  "Bearer " + leerToken();
			LOG.info("token: " + token);
			con.setRequestProperty("Authorization", token);
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(3000);
			int responseCode = 0;
			try {
				responseCode = con.getResponseCode();

				System.out.println("Codigo de Respuesta : " + responseCode);

			} catch (IOException e) {
				responseCode = 401;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}
				generarToquen();
				guiaElectronicaEspsResponse = consultaDatos(cedula,url);
				// guiaElectronicaEspsResponse = consultaDatos(cedula, url);
			}
			if (responseCode == 401) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				generarToquen();
				guiaElectronicaEspsResponse = consultaDatos(cedula,url);
				// guiaElectronicaEspsResponse = consultaDatos(cedula, url);
			} else if (responseCode == 200) {

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

				String esta;
				String inputLine;
				String stringJson = "";
				String v_estado;
				int cont;

				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					stringJson = inputLine;
					// quitamos letras con tildes y ñ 03 04 2020 sfbarrionuevoa
					stringJson = Normalizer.normalize(stringJson, Normalizer.Form.NFD);
					stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

					stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
					stringJson = stringJson.replaceAll("Clave", "clave");
					stringJson = stringJson.replaceAll("Valor", "valor");

				}
				in.close();
				DatosGeneralesGenericoBean objeto = new ObjectMapper().readValue(stringJson,
						DatosGeneralesGenericoBean.class);

				if (objeto.getResult().getMensaje().isEmpty()
						|| "TRUE".equalsIgnoreCase(objeto.getResult().getStatus())) {
					try {

						objeto.setTipo_facturacion("NA");
						if ("TRANSACCION EXITOSA".equals(objeto.getResult().getMensaje())) {
							objeto.getResult().setMensaje("OK");
						}

						for (int i = 0; i < objeto.getRec_titular().size(); i++) {

							// validacion de mora 15 18 2021
							//if (!objeto.getRec_titular().get(i).getEstado().equalsIgnoreCase("Falsezz")) {

							

							//objeto.getRec_titular().get(i).setEstaMoroso("si");
							
							objeto.getRec_titular().get(i)
									.setTi_contrato(objeto.getRec_titular().get(i).getTi_autnumcont()
											+ objeto.getRec_titular().get(i).getTi_codplan());

							if (objeto.getRec_titular().get(i).getTi_mail() == null) {
								objeto.getRec_titular().get(i).setTi_mail("1");
							}

							if (objeto.getRec_titular().get(i).getTi_telefono() == null) {
								objeto.getRec_titular().get(i).setTi_telefono("1");
							}

							
								

							String e =objeto.getRec_titular().get(i).getEstado();
							if (!e.equalsIgnoreCase(null)) {
								objeto.getRec_titular().get(i).setTi_estado(e);
								
								
							}
							//validacion de mensaje nivel cabecera 16 08 2021
							//String ed =objeto.getRec_titular().get(i).getRec_dependiente().get(j).getEstado();
							String ec =objeto.getRec_titular().get(i).getEstado();
							if (!ec.equalsIgnoreCase(null) && !ec.equalsIgnoreCase("true") )
							{
								//if (!m.equalsIgnoreCase(null)) {
								String mc =objeto.getRec_titular().get(i).getMensaje();
								objeto.getRec_titular().get(i).setTi_mensaje(mc);
							}
							else
							objeto.getRec_titular().get(i).setTi_mensaje("A");
							
							objeto.getRec_titular().get(i).setTi_producto("0");
							
							
							
							//if (objeto.getRec_titular().get(i).getTi_estado() == null) {
							//	objeto.getRec_titular().get(i).setTi_estado("Si");
							//}
							
							if (objeto.getRec_titular().get(i).getTi_fechinicontr() == null) {
								objeto.getRec_titular().get(i).setTi_fechinicontr("24/03/2020");
							}

							if (objeto.getRec_titular().get(i).getTi_codgenero() == null) {
								objeto.getRec_titular().get(i).setTi_codgenero("1");
							}

							if (objeto.getRec_titular().get(i).getTi_nombreContratante() == null) {
								objeto.getRec_titular().get(i).setTi_nombreContratante("1");
							}

							if (objeto.getRec_titular().get(i).getTi_versionPlan() == null) {
								objeto.getRec_titular().get(i).setTi_versionPlan("1");
							}

							if (objeto.getRec_titular().get(i).getTi_nombreNegocio() == null) {
								objeto.getRec_titular().get(i).setTi_nombreContratante("1");
							}

							if (objeto.getRec_titular().get(i).getTi_nombreNegocio() == null) {
								objeto.getRec_titular().get(i).setTi_nombreNegocio("1");
							}

							if (objeto.getRec_titular().get(i).getTi_nomplan() != null
									&& objeto.getRec_titular().get(i).getTi_nomplan().length() > 40) {
								objeto.getRec_titular().get(i)
										.setTi_nomplan(objeto.getRec_titular().get(i).getTi_nomplan().substring(0, 40));
							}

							//objeto.getRec_titular().get(i).setEstado(null);
							//objeto.getRec_titular().get(i).setMensaje(null);

							for (int j = 0; j < objeto.getRec_titular().get(i).getRec_dependiente().size(); j++) {

								// objeto.getRec_titular().get(i).getRec_dependiente().get(j).setEstado(null);
								//objeto.getRec_titular().get(i).getRec_dependiente().get(j).setMensaje(null);

								if (objeto.getRec_titular().get(i).getRec_dependiente().get(j).getDe_mail() == null
										|| "".equals(objeto.getRec_titular().get(i).getRec_dependiente().get(j)
												.getDe_mail())) {
									objeto.getRec_titular().get(i).getRec_dependiente().get(j).setDe_mail("1");
								}

								if (objeto.getRec_titular().get(i).getRec_dependiente().get(j).getDe_telefono() == null
										|| "".equals(objeto.getRec_titular().get(i).getRec_dependiente().get(j)
												.getDe_telefono())) {
									objeto.getRec_titular().get(i).getRec_dependiente().get(j).setDe_telefono("1");
								}

								// aumentamos informacion adicional cambio por sfba enero 2021

								List<infoAdicBean> ti_infoAdic = new ArrayList<infoAdicBean>();
								infoAdicBean infoAdic = new infoAdicBean();

								// if (objeto.getResult().getMensaje().isEmpty()

								if (objeto.getRec_titular().get(i).getRec_dependiente().get(j).getTi_infoAdic()
										.toString().isEmpty()) {
									infoAdic.setClave("1");
									infoAdic.setValor("1");
								} else {
									infoAdic.setClave(objeto.getRec_titular().get(i).getRec_dependiente().get(j)
											.getTi_infoAdic().getClave());
									infoAdic.setValor(objeto.getRec_titular().get(i).getRec_dependiente().get(j)
											.getTi_infoAdic().getValor());
								}
								ti_infoAdic.add(infoAdic);

								// titular.setTi_infoAdic(ti_infoAdic);
								// objeto.getRec_titular().get(i).setTi_infoAdic(ti_infoAdic);

								// ponemos en null nuevo tag
								objeto.getRec_titular().get(i).getRec_dependiente().get(j)
										.setDe_descripcionCarencia(null);

								//mansaje A
								
								String ed =objeto.getRec_titular().get(i).getRec_dependiente().get(j).getEstado();
								if (!ed.equalsIgnoreCase(null) && !ed.equalsIgnoreCase("true") )
								//if (!ed.equalsIgnoreCase(null) || ed.equalsIgnoreCase("false") )
								{
								String md = objeto.getRec_titular().get(i).getRec_dependiente().get(j).getMensaje();
								objeto.getRec_titular().get(i).getRec_dependiente().get(j).setMensaje(md);
								}
								else
								objeto.getRec_titular().get(i).getRec_dependiente().get(j).setMensaje("A");
										
								
								
								
								//
								int n = objeto.getRec_titular().get(i).getRec_dependiente().get(j)
										.getDi_preexistencias().size();

								if (n >= 1) {
									for (int p = 0; p < n; p++) {

										objeto.getRec_titular().get(i).getRec_dependiente().get(j)
												.getDi_preexistencias().get(p).setNombreDiagnostico(null);
										
										objeto.getRec_titular().get(i).getRec_dependiente().get(j)
										.getDi_preexistencias().get(p).setEstado(null);
								
										
										String mp = objeto.getRec_titular().get(i).getRec_dependiente().get(j)
												.getDi_preexistencias().get(p).getMensaje();
										
										mp = mp.replaceAll("“", "");
										
										mp = mp.replaceAll("”", "");
										
										
										
										objeto.getRec_titular().get(i).getRec_dependiente().get(j)
										.getDi_preexistencias().get(p).setMensaje(null);
										
										objeto.getRec_titular().get(i).getRec_dependiente().get(j)
										.getDi_preexistencias().get(p).setMsj(mp);
										
										

									}

								}

							} // fin de for para dependientes

							// definimos iteractor para borrar los estados falsos Creado por sfba 13 abril
							// 2020
							//Iterator lista = objeto.getRec_titular().get(i).getRec_dependiente().iterator();
							//cont = 0;
							//v_estado = null;
							//while (lista.hasNext()) {
							//	lista.next();
							//	v_estado = objeto.getRec_titular().get(i).getRec_dependiente().get(cont).getEstado();
							//	if (v_estado.equalsIgnoreCase("False")) {
							//		lista.remove();
							//		cont = cont - 1;
							//	}
							//	cont = cont + 1;
							//} // fin de iterator

							// ponemos null estado 14 04 2020
							for (int j = 0; j < objeto.getRec_titular().get(i).getRec_dependiente().size(); j++) {
								objeto.getRec_titular().get(i).getRec_dependiente().get(j).setEstado(null);
							} // fin null

							

							//guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR", men);
							if (objeto.getRec_titular().get(i).getTi_estado().equalsIgnoreCase("False"))
							objeto.getRec_titular().get(i).setTi_estado("No");
							else
							{
							objeto.getRec_titular().get(i).setTi_estado("Si");
							objeto.getRec_titular().get(i).setTi_mensaje("A");
							
							}
							
							
							objeto.getRec_titular().get(i).setEstado(null);
							objeto.getRec_titular().get(i).setMensaje(null);
							
							objeto.getRec_titular().get(i).setTi_producto("0");
							
						} // fin de for i de titulares
						

						// JAXBContext jaxbContext = JAXBContext.newInstance(RecetaPerBean.class);

						JAXBContext jaxbContext = JAXBContext.newInstance(DatosGeneralesGenericoBean.class);
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
			confiamedServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Confiamed no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			confiamedServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Confiamed no esta disponible");
			e.printStackTrace();
		}

		return guiaElectronicaEspsResponse;
	}

	public String confirmacionDatos(RecetaGenBean recetaGenBean, String url) {

		
		//url= "https://servicios.externos.confiamed.com:8243/ServicioProcesoContext/1.0.0/confirmacion_datos/";
		
		//url= "http://200.115.38.200/Servicio_Proceso/ServicioProceso.svc/confirmacion_datos/";
		
		waConfig = new WebApplicationConfig();
		 String token="";
		 
		
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

		// creacion de clase de confiamed
		RecetaConfiamed recetaConfiamed = new RecetaConfiamed();
		recetaConfiamed.setNombre_benficio("Confiamed");
		// recetaGenBean.getRec_facturacion().setSerie_factura(recetaGenBean.getRec_facturacion().getSerie_factura().substring(0,19));
		// recetaGenBean.getRec_facturacion().setSerie_factura(recetaGenBean.getRec_facturacion().getSerie_factura());
		// recetaGenBean.getRec_facturacion().setFecha_factura(recetaGenBean.getRec_facturacion().getFecha_factura().substring(0,10));
		// recetaGenBean.getRec_facturacion().setFecha_factura(recetaGenBean.getRec_facturacion().getFecha_factura());
		recetaGenBean.getRec_credito().setCr_fecaten(recetaGenBean.getRec_credito().getCr_fecaten().substring(0, 10));
		// recetaGenBean.set
		List<RecetaGenBean> recetagenList = new ArrayList<RecetaGenBean>();
		recetagenList.add(recetaGenBean);
		recetaConfiamed.setRecetagen(recetagenList);
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
			
			//baja certificados
			SSLFix sf = new SSLFix();
			sf.execute();
			URL obj = new URL(urlFinal);
			//configura protocolos
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");

			token =  "Bearer " + leerToken();
			LOG.info("token: " + token);
			con.setRequestProperty("Authorization", token);
			
			// String token = leerToken();
			// con.setRequestProperty("Authorization", token);

			con.setRequestProperty("Content-Type", "application/json");
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(5000);
			int responseCode = 0;

			OutputStream os = con.getOutputStream();
			// os.write(TextoUtil.encodeTrackingRequest(RUTARECETA,recetaGenBean).getBytes());
			
			String o = TextoUtil.encodeTrackingRequestConfiamed(RUTARECETA, recetaConfiamed);
			
			
			os.write(TextoUtil.encodeTrackingRequestConfiamed(RUTARECETA, recetaConfiamed).getBytes());
			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				responseCode = 401;
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				generarToquen();
				guiaElectronicaEspsResponse = confirmacionDatos(recetaGenBean, url);
			}
			if (responseCode == 401) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				generarToquen();
				guiaElectronicaEspsResponse = confirmacionDatos(recetaGenBean, url);
			
			} else if (responseCode == 200) {

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
				String inputLine;
				String stringJson = "";
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					stringJson = inputLine;

					// quitamos letras con tildes y ñ 03 04 2020 sfbarrionuevoa
					// stringJson = Normalizer.normalize(stringJson, Normalizer.Form.NFD);
					// stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

					// stringJson = inputLine.replaceAll("\"", "");
					// stringJson = stringJson.replaceAll("'", "\"");
					// stringJson = "{\"result\":"+ stringJson+"}";
				}
				in.close();

				System.out.println("Confiamed rest " + stringJson);
				// ObjectMapper objeto = new ObjectMapper();

				// objeto.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
				ResultadoGenericoBean objeto = new ObjectMapper()
						.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
						.readValue(stringJson, ResultadoGenericoBean.class);

				if ("true".equals(objeto./* getResult(). */getStatus())
						&& !"TRANSACCION FALLIDA".equalsIgnoreCase(objeto.getMensaje())) {

					try {

						guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK", objeto.getAutorizacion());
					} catch (Exception e) {
						guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR", e.getMessage());
						e.printStackTrace();
					}
				} else {
					guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR CONFIAMED",
							objeto/* .getResult() */.getMensaje());
				}

			} else {

				guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
						"Servicio de Confiamed no esta disponible ERROR:" + responseCode);
			}

		} catch (MalformedURLException e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Confiamed no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Confiamed no esta disponible");
			e.printStackTrace();
		}

		return guiaElectronicaEspsResponse;
	}

	/***
	 * Metodo para consultar los planes
	 * 
	 * @param url
	 * @return
	 */
	public String consultaPlanes(String url) {

		String guiaElectronicaEspsResponse = "";
		try {

			String UrlFinal = url;
			URL obj = new URL(UrlFinal);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(5000);
			int responseCode = 0;

			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				responseCode = 401;
				guiaElectronicaEspsResponse = consultaPlanes(url);
			}
			if (responseCode == 401) {
				guiaElectronicaEspsResponse = consultaPlanes(url);
			} else if (responseCode == 200) {

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
				String inputLine;
				String stringJson = "";
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					stringJson = inputLine;

					// quitamos letras con tildes y ñ 03 04 2020 sfbarrionuevoa
					stringJson = Normalizer.normalize(stringJson, Normalizer.Form.NFD);
					stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

				}
				in.close();

				// ponemos la entrada de confiamed en clase consultar planes
				ConsultarPlanesBean objeto = new ObjectMapper().readValue(stringJson, ConsultarPlanesBean.class);

				// validamos si hay planes nullos y si hay registros en los planes
				// Existe una confirmación pendiente
				if (objeto.getResult().getMensaje().isEmpty()
						|| "Existe una confirmacion pendiente".equals(objeto.getResult().getMensaje())) {
					// if (objeto.getPlanes()!=null && objeto.getPlanes().size()>0 &&
					// !objeto.getResult().getMensaje().isEmpty() ||
					// "OK".equals(objeto.getResult().getMensaje())) {

					int resgitros = ConexionesBDD.guardarTablaMapeoAseguradora(objeto.getPlanes(),
							objeto.getResult().getCodigo(), 8816780);// codigo quemado de confiamed
					// int resgitros =
					// ConexionesBDD.guardarTablaMapeoAseguradora(objeto.getPlanes(),objeto.getResult().getCodigo(),2);

					if (resgitros == Integer.parseInt(objeto.getResult().getTotalRegistros())) {
						confirmarPlanes(objeto.getResult().getCodigo(), "1");
						// confirmarPlanes(objeto.getResult().getCodigo().replace(" ", "%"),"1");
						// enviarCorreo("PROCESADO EXITOSO - REGISTROS PROCESADO : "+resgitros);

					} else {
						ConexionesBDD.eliminarTablaMapeoAseguradora(objeto.getResult().getCodigo(), 1);
						confirmarPlanes(objeto.getResult().getCodigo(), "2");

						// ConexionesBDD.eliminarTablaMapeoAseguradora(objeto.getResult().getCodigo(),2);
						// confirmarPlanes(objeto.getResult().getCodigo().replace(" ", "%"),"2");
					}
					guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK", "");
					// ok
				} else {

					int cont = ConexionesBDD.validarRegistrosMapeoAseguradora(objeto.getResult().getCodigo(), 8816780);// codigo
																														// quemado

					int num;

					if (objeto.getResult().getTotalRegistros() == "")
						num = 0;
					else
						num = Integer.parseInt(objeto.getResult().getTotalRegistros());

					if (num == cont) {
						confirmarPlanes(objeto.getResult().getCodigo(), "2");
						// enviarCorreo("PROCESADO EXITOSO - REGISTROS PROCESADO : "+cont);

					} else {
						ConexionesBDD.eliminarTablaMapeoAseguradora(objeto.getResult().getCodigo(), 8816780);// codigo
																												// quemado
						confirmarPlanes(objeto.getResult().getCodigo(), "2");
						// enviarCorreo("PROCESADO EXITOSO - REVERSADO CODIGO DE CONFIRMACION :
						// "+objeto.getResult().getCodigo());
					}

					guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("OK", "");

					// guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					// objeto.getResult().getMensaje());

				}

			}

		} catch (MalformedURLException e) {
			enviarCorreo("Error en la Homologacion de planes");
			confiamedServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Confiamed no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			confiamedServicesResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Confiamed no esta disponible");
			e.printStackTrace();
		}

		return guiaElectronicaEspsResponse;
	}

	public String confirmarPlanes(String codigo, String estado) {

		String guiaElectronicaEspsResponse = "";
		try {
			URL obj = new URL(URLCONFIRMARPLANES + "?codigoControl=" + codigo + "&codigoOperacion=" + estado);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			// con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(1000);
			int responseCode = 0;

			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				responseCode = 401;
				guiaElectronicaEspsResponse = confirmarPlanes(codigo, estado);
			}
			if (responseCode == 401) {
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
						"Servicio de Confiamed no esta disponible ERROR:" + responseCode);
			}

		} catch (MalformedURLException e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Confiamed no esta disponible");
			e.printStackTrace();
		} catch (IOException e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjeto("ERROR",
					"Servicio de Confiamed no esta disponible");
			e.printStackTrace();
		}

		return guiaElectronicaEspsResponse;
	}

	// envio de correo
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

	// contenido del archivo
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

	private String generarToquen() {
		String token = "";

		try {
			// String applicationId = this.generar_token1();
			// ArrayList<String> arreglo = this.generar_token2(applicationId);
			// token = this.generar_token3(applicationId, arreglo);

			ArrayList<String> arreglo = new ArrayList<String>();
			arreglo.add("1");
			arreglo.add("2");
			token = this.generar_token3("", arreglo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return token;

	}

	// obtiene String applicationId
	private String generar_token1() throws IOException {
		String applicationId = "";

		SSLFix sf = new SSLFix();
		sf.execute();

		String name = "GPFSubscriber";
		String password = "12345";
		String authString = name + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		String urlFinal = "https://200.115.38.201:9443/api/am/devportal/v2/applications";
		URL obj = new URL(urlFinal);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("Authorization", "Basic " + authStringEnc);

		// System.setProperty("https.protocols", "TLSv1");
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setConnectTimeout(5000);
		int responseCode = 0;

		responseCode = con.getResponseCode();
		System.out.println("Codigo de Respuesta : " + responseCode);
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

			String esta;
			String inputLine;
			String stringJson = "";
			String v_estado;
			int cont;

			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				stringJson = inputLine;
				// quitamos letras con tildes y ñ 03 04 2020 sfbarrionuevoa
				stringJson = Normalizer.normalize(stringJson, Normalizer.Form.NFD);
				stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

				stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
				stringJson = stringJson.replaceAll("Clave", "clave");
				stringJson = stringJson.replaceAll("Valor", "valor");

			}
			in.close();
			applicationId = this.token1(stringJson);
		}

		return applicationId;

	}

	// obtiene arreglo de dos campos
	private ArrayList<String> generar_token2(String applicationId) throws IOException {
		ArrayList<String> arreglo = new ArrayList<String>();
		// String urlFinal =
		// "https://200.115.38.201:9443/api/am/devportal/v2/applications/7d019446-fd72-48fd-b86f-a3ee0a31121a/oauth-keys";
		String urlFinal = "https://200.115.38.201:9443/api/am/devportal/v2/applications/" + applicationId
				+ "/oauth-keys";
		URL obj = new URL(urlFinal);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setConnectTimeout(5000);
		int responseCode = 0;

		responseCode = con.getResponseCode();
		System.out.println("Codigo de Respuesta : " + responseCode);
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

			String esta;
			String inputLine;
			String stringJson = "";
			String v_estado;
			int cont;

			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				stringJson = inputLine;
				// quitamos letras con tildes y ñ 03 04 2020 sfbarrionuevoa
				stringJson = Normalizer.normalize(stringJson, Normalizer.Form.NFD);
				stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

				stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
				stringJson = stringJson.replaceAll("Clave", "clave");
				stringJson = stringJson.replaceAll("Valor", "valor");

			}
			in.close();
			arreglo = this.token2(stringJson);
		}

		return arreglo;
	}

	// obtiene token final
	// obtiene String applicationId
	private String generar_token3(String app, ArrayList<String> arreglo) throws IOException {
		String token = "";
		// String urlFinal =
		// "https://200.115.38.201:9443/api/am/devportal/v2/applications/"+app+
		// "/oauth-keys/" + arreglo.get(0) +"/generate-token";
		SSLFix sf = new SSLFix();
		sf.execute();
		//String name = "GPFSubscriber";
		//String password = "12345";
		
		
		String name = waConfig.epConfiamedConfig.credUsuario;
		String password = waConfig.epConfiamedConfig.credClave;
		String url = waConfig.epConfiamedConfig.urlToken;
		String appid = waConfig.epConfiamedConfig.app_id;
		String keyMappingId = waConfig.epConfiamedConfig.keyMappingId;
		String consumerSecret = waConfig.epConfiamedConfig.consumerSecret;
		
		
		

		String authString = name + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		//String urlFinal = "https://200.115.38.201:9443/api/am/devportal/v2/applications/7d019446-fd72-48fd-b86f-a3ee0a31121a/oauth-keys/2e0ad42f-46ac-4757-aaf2-570421a78835/generate-token";
		//String urlFinal = url;
		String urlFinal = url + "/"  + appid + "/" + "oauth-keys/" + keyMappingId +"/generate-token";
		URL obj = new URL(urlFinal);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("Authorization", "Basic " + authStringEnc);
		con.setRequestProperty("Content-Type", "application/json");
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		con.setRequestMethod("GET");
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setConnectTimeout(5000);
		int responseCode = 0;

		OutputStream os = con.getOutputStream();
		//String var = "{\"consumerSecret\":\"fGiB6HVqwwjFxmNh8HADfuN6PLwa\",\"validityPeriod\":1000000}";
		
		String var = "{\"consumerSecret\":\""+consumerSecret +"\",\"validityPeriod\":1000000}";
		
		os.write(var.getBytes());

		responseCode = con.getResponseCode();
		System.out.println("Codigo de Respuesta : " + responseCode);
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
			String inputLine;
			String stringJson = "";

			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				stringJson = inputLine;
				// quitamos letras con tildes y ñ 03 04 2020 sfbarrionuevoa
				stringJson = Normalizer.normalize(stringJson, Normalizer.Form.NFD);
				stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

			}
			in.close();
			token = this.token3(stringJson);
		}
		
		escribirToken(token);
		con.disconnect();
		return token;

	}

	private String token1(String stringJson) {
		String ap_id = "";
		try {
			JSONObject json = new JSONObject(stringJson);
			JSONArray jsonArrayR = json.getJSONArray("list");
			if (jsonArrayR.length() > 0) {
				JSONObject list = json.getJSONArray("list").getJSONObject(0);
				ap_id = list.getString("applicationId");

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "ap_id";
	}

	private ArrayList<String> token2(String stringJson) {

		ArrayList<String> arreglo = new ArrayList<String>();

		String keyMappingId = "";
		String consumerSecret = "";

		try {
			JSONObject json = new JSONObject(stringJson);
			JSONArray jsonArrayR = json.getJSONArray("list");
			if (jsonArrayR.length() > 0) {
				JSONObject list = json.getJSONArray("list").getJSONObject(0);
				keyMappingId = list.getString("keyMappingId");
				consumerSecret = list.getString("consumerSecret");
				arreglo.add(keyMappingId);
				arreglo.add(consumerSecret);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arreglo;
	}

	private String token3(String stringJson) {

		String token = "";

		try {
			JSONObject json = new JSONObject(stringJson);
			token = json.getString("accessToken");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return token;

	}
	
	
//
	
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

	
	
	
	
}
