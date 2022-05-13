package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class VcCupoClientesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String cupo;
	private String identificacion;
	private String nombre;
	
	
	public String getCupo() {
		return cupo;
	}
	public void setCupo(String cupo) {
		this.cupo = cupo;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	

	
	
	

}
