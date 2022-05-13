package com.gpf.partnersGroupGPF.bean;

import java.io.Serializable;

public class CreditoCorpBean  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String respuesta;
	private String estado;
	private String detalle;
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	public CreditoCorpBean(String respuesta, String estado ,String detalle) {
		super();
		this.respuesta = respuesta;
		this.estado = estado;
		this.detalle = detalle;
		
	}
	

}
