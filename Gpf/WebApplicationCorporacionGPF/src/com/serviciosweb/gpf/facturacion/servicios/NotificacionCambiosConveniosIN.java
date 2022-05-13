package com.serviciosweb.gpf.facturacion.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.util.Properties;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.EnviarCorreo;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;



public class NotificacionCambiosConveniosIN extends ObtenerNuevaConexion {

	public String SolicitudResponse = null;
	private String sSQL, baseURL, urlfinalA, urlfinalR, asunto_final;
	ResultSet rs = null;
	URL url;
	
	public static String Encriptar(String texto) {

		String secretKey = "c0rp0raci0ngp7";
		String base64EncryptedString = "";

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
		}
		return base64EncryptedString;
	}

	// FUNCION PARA LEER EL ARCHIVO PROPERTI

	private void LeerArchivo() {

		final String RUTA_ARCHIVO_PROPERTIES = "/data/WebApplicationCorporacionGPF.properties";

		try {
			final File archivoProperties = new File(RUTA_ARCHIVO_PROPERTIES);
			FileInputStream inputStream = new FileInputStream(archivoProperties);

			if (inputStream != null) {
				Properties prop = new Properties();
				prop.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

				baseURL = prop.getProperty("CONVENIOSIN.URL_TOKEN");
			} else
				throw new FileNotFoundException("El archivo de propiedades no se encuentra");
		} catch (Exception e) {
			System.out.print("");
		}
	}

	public NotificacionCambiosConveniosIN() {
		super(NotificacionCambiosConveniosIN.class);
		LeerArchivo();
	}

	public NotificacionCambiosConveniosIN(String convenio, String ruta, String cupo, String empresa, String usuario) {
		super(NotificacionCambiosConveniosIN.class);
		LeerArchivo();
		
		ConexionVarianteSid conn = obtenerNuevaConexionVarianteSidMaster(empresa);
		if (conn.getConn() != null) {
			String nombre_empresa = null;
			sSQL = "SELECT b.codigo, razon_social FROM in_clientes_ingresos a, ab_personas b where a.campo4 = b.codigo and a.codigo = '"+ convenio +"'";
			rs = conn.consulta(sSQL);
			while (conn.siguiente(rs)) {
				nombre_empresa = conn.getString(rs, "razon_social");

				sSQL = "SELECT estado_solicitud FROM ingresos.in_autorizar_cupo WHERE estado_solicitud = 'S' and convenio='"
						+ convenio + "'";
				rs = conn.consulta(sSQL);
				if (conn.siguiente(rs)) {
					sSQL = "SELECT mail_destinatarios_info, asunto, cuerpo_smj_info, ambiente FROM ingresos.in_autorizador_cupo where rango_cupo_fin >= "
							+ cupo + " and rango_cupo_ini <= " + cupo + " and status_aprobador = 'Y'";
					rs = conn.consulta(sSQL);
					while (conn.siguiente(rs)) {
						String rs_mail_info = conn.getString(rs, "mail_destinatarios_info");
						String rs_asunto = conn.getString(rs, "asunto");
						String rs_contenido = conn.getString(rs, "cuerpo_smj_info");
						String rs_ambiente = conn.getString(rs, "ambiente");

						rs_contenido = rs_contenido.replace("nombre_empresa", nombre_empresa)
								.replace("monto", cupo).replace("estatus", "Pendiente por aprobación, si desea ingresar un nuevo cupo asociado a este convenido por favor cancele el actual")
								.replace("numero_cupo", convenio);
						rs_asunto = rs_asunto.replace("numero", convenio).replace("estatus","Pendiente por aprobación").replace("empresa", nombre_empresa);
						asunto_final = ""+rs_ambiente+" - "+rs_asunto+"";

						new EnviarCorreo("aprobacioncuposconvenios@corporaciongpf.com", rs_mail_info, asunto_final,
								"<html><body>" + rs_contenido + "</body></html>");
						StringBuilder respuestaXml = new StringBuilder();
						respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						respuestaXml.append("<Transaccion><httpStatus>104</httpStatus>"
								+ "<mensaje>Se enviaron correctamente los datos al mail, informado que el convenio esta Pendiente por aprobación.</mensaje>"
								+ "<respuesta>OK</respuesta></Transaccion>");
						SolicitudResponse = respuestaXml.toString();
						conn.cerrarConexion(rs);
					}
				} else {
					sSQL = "SELECT COUNT(codigo) as contador, max(codigo) as maximo FROM ingresos.in_autorizar_cupo";
					rs = conn.consulta(sSQL);
					while (conn.siguiente(rs)) {
						String ini_contador = conn.getString(rs, "contador");
						String ini_maximo = conn.getString(rs, "maximo");
						if(Integer.parseInt(ini_contador) == 0) {  int contador_rs = 1; } else { int contador_rs = Integer.parseInt(ini_maximo) + 1; }
						int contador_rs = Integer.parseInt(ini_contador) + 1;

						sSQL = "insert into ingresos.in_autorizar_cupo (codigo, convenio, cupo, estado_solicitud, usuario_solicita)"
								+ "values ('" + contador_rs + "','" + convenio + "','" + cupo + "','S','" + usuario
								+ "')";
						rs = conn.consulta(sSQL);
						if (conn.siguiente(rs)) {
							sSQL = "SELECT mail_aprobador, asunto, cuerpo_smj_aprobador, nombre_aprobador, codigo, ambiente, mail_destinatarios_info, cuerpo_smj_info FROM ingresos.in_autorizador_cupo where rango_cupo_fin >= "
									+ cupo + " and rango_cupo_ini <= " + cupo + " and status_aprobador = 'Y'";
							rs = conn.consulta(sSQL);
							while (conn.siguiente(rs)) {
								String rs_mail = conn.getString(rs, "mail_aprobador");
								String rs_asunto = conn.getString(rs, "asunto");
								String rs_contenido = conn.getString(rs, "cuerpo_smj_aprobador");
								String rs_nombre_apro = conn.getString(rs, "nombre_aprobador");
								String rs_codigo = conn.getString(rs, "codigo");
								String rs_ambiente = conn.getString(rs, "ambiente");
								String rs_contenido_destinatario = conn.getString(rs, "cuerpo_smj_info");
								String rs_mail_destinatario = conn.getString(rs, "mail_destinatarios_info");

								rs_asunto = rs_asunto.replace("numero", convenio).replace("estatus", "Solicitud de Aprobación").replace("empresa", nombre_empresa);
								rs_contenido = rs_contenido.replace("aprobador,", rs_nombre_apro).replace("nombre_empresa", nombre_empresa).replace("monto", cupo).replace("numero_cupo", convenio);
								asunto_final = " "+rs_ambiente+"  - "+rs_asunto+" ";
								
								try {
									url = new  URL(baseURL);
									
									String convenioEn = Encriptar(convenio);convenioEn = convenioEn.replace("/", "...");
									String rs_codigoEn = Encriptar(rs_codigo);rs_codigoEn = rs_codigoEn.replace("/", "...");
									String empresaEn = Encriptar(empresa); empresaEn  = empresaEn.replace("/", "...");
									String cupoEn = Encriptar(cupo); cupoEn  = cupoEn.replace("/", "...");
									String nomempEn = Encriptar(nombre_empresa); nomempEn  = nomempEn.replace("/", "...");
									
									urlfinalA = url+"SolicitudAceptarCupoIn/"+convenioEn+"/"+rs_codigoEn+"/"+empresaEn+"/"+cupoEn+"/"+nomempEn+"/A";
									urlfinalR = url+"SolicitudAceptarCupoIn/"+convenioEn+"/"+rs_codigoEn+"/"+empresaEn+"/"+cupoEn+"/"+nomempEn+"/R";
									
									new EnviarCorreo("aprobacioncuposconvenios@corporaciongpf.com", rs_mail, asunto_final, "<html><body>"+rs_contenido+"<p>"
									+ "<a href='"+urlfinalA+"'>Aprobar</a>&nbsp;&nbsp;&nbsp;&nbsp;"
									+ "<a href='"+urlfinalR+"'>Rechazar</a></p></body></html>");
									
									rs_asunto = rs_asunto.replace("numero", convenio).replace("estatus", "Envio de Aprobación").replace("empresa", nombre_empresa);
									rs_contenido_destinatario = rs_contenido_destinatario.replace("numero_cupo", convenio).replace("nombre_empresa", nombre_empresa).replace("monto", cupo).replace("estatus", "enviado para aprobación");
									asunto_final = " "+rs_ambiente+"  - "+rs_asunto+" ";
									
									new EnviarCorreo("aprobacioncuposconvenios@corporaciongpf.com", rs_mail_destinatario, asunto_final, rs_contenido_destinatario);
									
									StringBuilder respuestaXml = new StringBuilder();
									respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
									respuestaXml.append("<Transaccion><httpStatus>104</httpStatus>"
											+ "<mensaje>Se enviaron correctamente los datos al mail del aprobador</mensaje>"
											+ "<respuesta>OK</respuesta></Transaccion>");
									SolicitudResponse = respuestaXml.toString();
									conn.cerrarConexion(rs);
								} catch (Exception e) {
									
									StringBuilder respuestaXml = new StringBuilder();
									respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
									respuestaXml.append("<Transaccion><httpStatus>55504</httpStatus>"
											+ "<mensaje>El correo no pudo ser enviado, porque no consiguio el archivo properties.</mensaje>"
											+ "<respuesta>ERROR</respuesta></Transaccion>");
									SolicitudResponse = respuestaXml.toString();
									conn.cerrarConexion(rs);									
								}	
							}
						} else {
							StringBuilder respuestaXml = new StringBuilder();
							respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
							respuestaXml.append("<Transaccion><httpStatus>55504</httpStatus>"
									+ "<mensaje>Los datos no fueron almacenados.</mensaje>"
									+ "<respuesta>ERROR</respuesta></Transaccion>");
							SolicitudResponse = respuestaXml.toString();
							conn.cerrarConexion(rs);
						}
					}
				}
			}

		} else {
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			respuestaXml.append("<Transaccion><httpStatus>55504</httpStatus>"
					+ "<mensaje>Error al conectar a la Base de Datos.</mensaje>"
					+ "<respuesta>Razon por la cual falló</respuesta></Transaccion>");
			SolicitudResponse = respuestaXml.toString();
			conn.cerrarConexion(rs);
		}
	}
}