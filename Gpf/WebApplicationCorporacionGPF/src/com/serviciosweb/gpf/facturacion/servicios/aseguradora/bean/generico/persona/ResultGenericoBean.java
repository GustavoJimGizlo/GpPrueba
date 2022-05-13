package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona;

import java.io.Serializable;

public class ResultGenericoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7210953040141050751L;

	private String status;
	private String mensaje;
	

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

}
