package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class AseguradoraAbfBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String planActivo;
	private String mora;
	private String pacienteAtivo;
	private String carenciaTiempo;
	private String preexistenciaTiempo;
	public String getPlanActivo() {
		return planActivo;
	}
	public void setPlanActivo(String planActivo) {
		this.planActivo = planActivo;
	}
	public String getMora() {
		return mora;
	}
	public void setMora(String mora) {
		this.mora = mora;
	}
	public String getPacienteAtivo() {
		return pacienteAtivo;
	}
	public void setPacienteAtivo(String pacienteAtivo) {
		this.pacienteAtivo = pacienteAtivo;
	}
	public String getCarenciaTiempo() {
		return carenciaTiempo;
	}
	public void setCarenciaTiempo(String carenciaTiempo) {
		this.carenciaTiempo = carenciaTiempo;
	}
	public String getPreexistenciaTiempo() {
		return preexistenciaTiempo;
	}
	public void setPreexistenciaTiempo(String preexistenciaTiempo) {
		this.preexistenciaTiempo = preexistenciaTiempo;
	}
	
	
	
	 
	
	  
}
