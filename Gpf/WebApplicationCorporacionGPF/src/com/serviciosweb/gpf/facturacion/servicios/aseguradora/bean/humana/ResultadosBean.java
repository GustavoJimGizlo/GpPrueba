package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana;

import java.io.Serializable;

public class ResultadosBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2667166543465491137L;
	private String status;
	private String mensaje;
	private String autorizacion;
	private String receta;
	private String transito;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getReceta() {
		return receta;
	}

	public void setReceta(String receta) {
		this.receta = receta;
	}

	public String getTransito() {
		return transito;
	}

	public void setTransito(String transito) {
		this.transito = transito;
	}

}
