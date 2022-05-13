package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//@XmlType(propOrder={"httpStatus", "mensaje","RespuestaObject"})
public class Transaccion {
	 private String httpStatus;
	 private String mensaje;
	 Respuesta RespuestaObject;


	 // Getter Methods 

	 public String getHttpStatus() {
	  return httpStatus;
	 }

	 public String getMensaje() {
	  return mensaje;
	 }

	 public Respuesta getRespuesta() {
	  return RespuestaObject;
	 }

	 // Setter Methods 

	 public void setHttpStatus(String httpStatus) {
	  this.httpStatus = httpStatus;
	 }

	 public void setMensaje(String mensaje) {
	  this.mensaje = mensaje;
	 }

	 public void setRespuesta(Respuesta respuestaObject) {
	  this.RespuestaObject = respuestaObject;
	 }
	}
