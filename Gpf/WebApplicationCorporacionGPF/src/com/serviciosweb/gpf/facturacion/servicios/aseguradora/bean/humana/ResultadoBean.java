package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana;

import java.io.Serializable;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class ResultadoBean implements Serializable {

	private static final long serialVersionUID = -2481468902239647543L;
	private String status;
	private String mensaje;
	private String autorizacion;
	private String receta;
	
	public String getStatus() {
		return status;
	}
	@XmlElement
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMensaje() {
		return mensaje;
	}
	@XmlElement
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getAutorizacion() {
		return autorizacion;
	}
	@XmlElement
	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}
	public String getReceta() {
		return receta;
	}
	@XmlElement
	public void setReceta(String receta) {
		this.receta = receta;
	}
	
	/*
	private ResultadosBean result;
	public ResultadosBean getResult() {
		return result;
	}
	public void setResult(ResultadosBean result) {
		this.result = result;
	}
*/

	
	
}
