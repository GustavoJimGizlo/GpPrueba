package com.gpf.partnersGroupGPF.bean;

import java.io.Serializable;


public class RpTracking implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String chk;
	private String fecha;
	private String hora;
	private String estado;
	private String subEstado;
	private String apunts;
	private String agencia;
	private String gpsPx;
	private String gpsPy;
	
	
	
	
	public String getChk() {
		return chk;
	}
	public void setChk(String chk) {
		this.chk = chk;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getApunts() {
		return apunts;
	}
	public void setApunts(String apunts) {
		this.apunts = apunts;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getGpsPx() {
		return gpsPx;
	}
	public void setGpsPx(String gpsPx) {
		this.gpsPx = gpsPx;
	}
	public String getGpsPy() {
		return gpsPy;
	}
	public void setGpsPy(String gpsPy) {
		this.gpsPy = gpsPy;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getSubEstado() {
		return subEstado;
	}
	public void setSubEstado(String subEstado) {
		this.subEstado = subEstado;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	 
	
	 

	
	
	
	
	

}
