package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana;

import java.io.Serializable;

public class DependientesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5080779401499970933L;

	private String de_tipoident;
	private String de_cedula;
	private String de_nombre;
	private String de_mail;
	private String de_telefono;
	private String de_tipo;
	private String de_autcodigo;
	
	private String mensaje;
	

	
	
	


	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDe_tipoident() {
		return de_tipoident;
	}

	public void setDe_tipoident(String de_tipoident) {
		this.de_tipoident = de_tipoident;
	}

	public String getDe_cedula() {
		return de_cedula;
	}

	public void setDe_cedula(String de_cedula) {
		this.de_cedula = de_cedula;
	}

	public String getDe_nombre() {
		return de_nombre;
	}

	public void setDe_nombre(String de_nombre) {
		this.de_nombre = de_nombre;
	}

	public String getDe_mail() {
		return de_mail;
	}

	public void setDe_mail(String de_mail) {
		this.de_mail = de_mail;
	}

	public String getDe_telefono() {
		return de_telefono;
	}

	public void setDe_telefono(String de_telefono) {
		this.de_telefono = de_telefono;
	}

	public String getDe_tipo() {
		return de_tipo;
	}

	public void setDe_tipo(String de_tipo) {
		this.de_tipo = de_tipo;
	}

	public String getDe_autcodigo() {
		return de_autcodigo;
	}

	public void setDe_autcodigo(String de_autcodigo) {
		this.de_autcodigo = de_autcodigo;
	}

}
