package com.serviciosweb.gpf.facturacion.servicios;

import java.sql.ResultSet;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.EnviarCorreo;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;




public class AprobacionCambiosConveniosVC extends ObtenerNuevaConexion {

	public String SolicitudResponse = null;
	private String sSQL = "";
	ResultSet rs = null;
	private String detalle_estatus, asunto_final, status_final;
	Double periodo = 2.0;
	
	public static String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "c0rp0raci0ngp7"; //llave para desenciptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
}

	public AprobacionCambiosConveniosVC() {
		super(AprobacionCambiosConveniosVC.class);
	}

	public AprobacionCambiosConveniosVC(String convenios, String url, String usuarios, String empresas, String cupos,String nombres_empresas, String status) {
		super(AprobacionCambiosConveniosVC.class);
		
		try {
			String convenio = Desencriptar(convenios.replace("...", "/"));
			String usuario = Desencriptar(usuarios.replace("...", "/"));
			String empresa = Desencriptar(empresas.replace("...", "/"));
			String cupo = Desencriptar(cupos.replace("...", "/"));
			String nombre_empresa = Desencriptar(nombres_empresas.replace("...", "/"));
			
			ConexionVarianteSid conn = obtenerNuevaConexionVarianteSidMaster(empresa);
			
			if (conn.getConn() == null) {
				StringBuilder respuestaXml = new StringBuilder();
				respuestaXml.append(
						"<html><body><p>&nbsp;</p><p style=\"color:Red; text-align:center; font-size:200%\"><strong>TRANSACCI&Oacute;N FALLIDA</strong></p><p>&nbsp;</p>\r\n"
								+ "<p style=\"color:Gray; text-align:center; font-size:150%\">Error al conectar a la Base de Datos, por favor intente mas tarde.</p></body></html>");
				SolicitudResponse = respuestaXml.toString();
			} else {
				char caracter = status.charAt(0);
				sSQL = "SELECT CAST ((sysdate - fecha_solicitud) as numeric(36,2)) as tiempo from vitalcard.vc_autorizar_cupo where estado_solicitud = 'S' and convenio ='"
						+ convenio + "' and cupo = '" + cupo + "'";
				rs = conn.consulta(sSQL);
				if (conn.siguiente(rs)) {
					String rs_tiempo = conn.getString(rs, "tiempo");

					if ((caracter == 'A') && (Double.parseDouble(rs_tiempo) <= periodo)) {

						detalle_estatus = "Aprobado";

						sSQL = "UPDATE vitalcard.vc_convenios  SET CUPO_TOTAL = '" + cupo + "'  WHERE codigo = '" + convenio+ "'";
						conn.getConn().commit();
						rs = conn.consulta(sSQL);
						if (conn.siguiente(rs)) {
							sSQL = "UPDATE vitalcard.vc_autorizar_cupo SET estado_solicitud='" + status
									+ "', estado_aprobacion='" + status
									+ "', fecha_aprobacion=sysdate, usuario_aprobacion='" + usuario + "'"
									+ " WHERE estado_solicitud = 'S' and convenio = '" + convenio + "' and cupo = '" + cupo + "' ";
							conn.getConn().commit();
							rs = conn.consulta(sSQL);
							if (conn.siguiente(rs)) {
								sSQL = "SELECT mail_destinatarios_info, asunto, cuerpo_msj_info, ambiente FROM vitalcard.vc_autorizador_cupo where rango_cupo_fin >= "
										+ cupo + " and rango_cupo_ini <= " + cupo + " and status_aprobador = 'Y'";
								rs = conn.consulta(sSQL);
								while (conn.siguiente(rs)) {
									String rs_mail_info = conn.getString(rs, "mail_destinatarios_info");
									String rs_asunto = conn.getString(rs, "asunto");
									String rs_contenido = conn.getString(rs, "cuerpo_msj_info");
									String rs_ambiente = conn.getString(rs, "ambiente");

									rs_contenido = rs_contenido.replace("monto", cupo).replace("estatus", detalle_estatus).replace("numero_cupo", convenio).replace("nombre_empresa", nombre_empresa);
									rs_asunto = rs_asunto.replace("numero", convenio).replace("estatus", "Aprobación").replace("empresa", nombre_empresa);
									asunto_final = " " + rs_ambiente + "  - " + rs_asunto + " ";

									new EnviarCorreo("aprobacioncuposconvenios@corporaciongpf.com", rs_mail_info, asunto_final,"<html><body>" + rs_contenido + "</body></html>");
									StringBuilder respuestaXml = new StringBuilder();
									respuestaXml.append(
											"<html><body><p>&nbsp;</p><p style=\"color:Green; text-align:center; font-size:200%\"><strong>TRANSACCI&Oacute;N EXITOSA</strong></p><p>&nbsp;</p>"
													+ "<p style=\"color:Gray; text-align:center; font-size:150%\">El cupo "
													+ cupo + " del convenio " + convenio
													+ ", fue actualizado satisfactoriamente. Se envia correo para su informaci&oacute;n</p></body></html>");
									SolicitudResponse = respuestaXml.toString();
									conn.cerrarConexion(rs);
								}

							} else {
								StringBuilder respuestaXml = new StringBuilder();
								respuestaXml.append(
										"<html><body><p>&nbsp;</p><p style=\"color:Red; text-align:center; font-size:200%\"><strong>TRANSACCI&Oacute;N FALLIDA</strong></p><p>&nbsp;</p>\r\n"
												+ "<p style=\"color:Gray; text-align:center; font-size:150%\">El cupo "
												+ cupo + " del convenio " + convenio
												+ " no pudo ser actualizado.</p></body></html>");
								SolicitudResponse = respuestaXml.toString();
							}
						} else {
							StringBuilder respuestaXml = new StringBuilder();
							respuestaXml.append(
									"<html><body><p>&nbsp;</p><p style=\"color:Red; text-align:center; font-size:200%\"><strong>TRANSACCI&Oacute;N FALLIDA</strong></p><p>&nbsp;</p>\r\n"
											+ "<p style=\"color:Gray; text-align:center; font-size:150%\">El cupo " + cupo
											+ " del convenio " + convenio + " no pudo ser actualizado.</p></body></html>");
							SolicitudResponse = respuestaXml.toString();
						}
					} else {
						if ((caracter == 'A') && (Double.parseDouble(rs_tiempo) > periodo)) {
							detalle_estatus = "Rechazado por tener mas de 48 horas de creado"; status_final = "F";
						} else {
							detalle_estatus = "Rechazado"; status_final = "R";
						}

						sSQL = "UPDATE vitalcard.vc_autorizar_cupo SET estado_solicitud='R', estado_aprobacion='"+ status_final +"', fecha_aprobacion=sysdate, usuario_aprobacion='"
								+ usuario + "'" + " WHERE estado_solicitud = 'S' and convenio = '" + convenio + "' and cupo = '" + cupo + "' ";
						conn.getConn().commit();
						rs = conn.consulta(sSQL);
						if (conn.siguiente(rs)) {
							sSQL = "SELECT mail_destinatarios_info, asunto, cuerpo_msj_info, ambiente FROM vitalcard.vc_autorizador_cupo where rango_cupo_fin >= "
									+ cupo + " and rango_cupo_ini <= " + cupo + " and status_aprobador = 'Y'";
							rs = conn.consulta(sSQL);
							while (conn.siguiente(rs)) {
								String rs_mail_info = conn.getString(rs, "mail_destinatarios_info");
								String rs_asunto = conn.getString(rs, "asunto");
								String rs_contenido = conn.getString(rs, "cuerpo_msj_info");
								String rs_ambiente = conn.getString(rs, "ambiente");

								rs_contenido = rs_contenido.replace("nombre_empresa", nombre_empresa).replace("monto", cupo).replace("estatus", detalle_estatus).replace("numero_cupo", convenio); 
								rs_asunto = rs_asunto.replace("numero", convenio).replace("estatus", "Rechazo").replace("empresa", nombre_empresa);
								asunto_final = " " + rs_ambiente + "  - " + rs_asunto + " ";

								new EnviarCorreo("aprobacioncuposconvenios@corporaciongpf.com", rs_mail_info, asunto_final,
										"<html><body>" + rs_contenido + "</body></html>");
								StringBuilder respuestaXml = new StringBuilder();
								respuestaXml.append(
										"<html><body><p>&nbsp;</p><p style=\"color:Green; text-align:center; font-size:200%\"><strong>TRANSACCI&Oacute;N EXITOSA</strong></p><p>&nbsp;</p>"
												+ "<p style=\"color:Gray; text-align:center; font-size:150%\">El cupo "
												+ cupo + " del convenio " + convenio + ", fue " + detalle_estatus
												+ ". Se envia correo con la notificaci&oacute;n.</p></body></html>");
								SolicitudResponse = respuestaXml.toString();
								conn.cerrarConexion(rs);
							}
						} else {
							StringBuilder respuestaXml = new StringBuilder();
							respuestaXml.append(
									"<html><body><p>&nbsp;</p><p style=\"color:Red; text-align:center; font-size:200%\"><strong>TRANSACCI&Oacute;N FALLIDA</strong></p><p>&nbsp;</p>\r\n"
											+ "<p style=\"color:Gray; text-align:center; font-size:150%\">El cupo " + cupo
											+ " del convenio " + convenio
											+ " ya se encuentra actualizado, por favor verifique su informaci&oacute;n.</p></body></html>");
							SolicitudResponse = respuestaXml.toString();
						}
					}
				} else {
					StringBuilder respuestaXml = new StringBuilder();
					respuestaXml.append(
							"<html><body><p>&nbsp;</p><p style=\"color:Red; text-align:center; font-size:200%\"><strong>TRANSACCI&Oacute;N FALLIDA</strong></p><p>&nbsp;</p>\r\n"
									+ "<p style=\"color:Gray; text-align:center; font-size:150%\">El cupo " + cupo
									+ " del convenio " + convenio
									+ " ya se encuentra actualizado, por favor verifique su informaci&oacute;n.</p></body></html>");
					SolicitudResponse = respuestaXml.toString();
				}
			}
		} catch (Exception e) {
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append(
					"<html><body><p>&nbsp;</p><p style=\"color:Red; text-align:center; font-size:200%\"><strong>TRANSACCI&Oacute;N FALLIDA</strong></p><p>&nbsp;</p>\r\n"
							+ "<p style=\"color:Gray; text-align:center; font-size:150%\">Los Datos no pudieron ser transformados para su lectura</p></body></html>");
			SolicitudResponse = respuestaXml.toString();
		}
	}
}