package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class RespuestaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String activo;
	private String codigoMensaje;
	private String descripcionMensaje;
	
	
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getCodigoMensaje() {
		return codigoMensaje;
	}
	public void setCodigoMensaje(String codigoMensaje) {
		this.codigoMensaje = codigoMensaje;
	}
	public String getDescripcionMensaje() {
		return descripcionMensaje;
	}
	public void setDescripcionMensaje(String descripcionMensaje) {
		this.descripcionMensaje = descripcionMensaje;
	}
	
	
	
	
	   
}
