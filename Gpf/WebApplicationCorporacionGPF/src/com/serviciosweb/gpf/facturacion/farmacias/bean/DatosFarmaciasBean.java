package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class DatosFarmaciasBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long  codigo;
	private String nombre;
	private String direccion;
	private String longitud;
	private String latitud;
	private String cumplimiento;
	
	
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(String cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	
	
	
	
	
	
	
	
	
	
	
}
