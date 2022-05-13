package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class AdFarmaciasBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private String nombre;
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

}
