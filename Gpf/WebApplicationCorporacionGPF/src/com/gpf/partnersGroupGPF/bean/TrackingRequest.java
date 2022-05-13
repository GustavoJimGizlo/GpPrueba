package com.gpf.partnersGroupGPF.bean;

import java.io.Serializable;


public class TrackingRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String guia;
	private String docref;
	private String vp_linea;
	
	
	public String getGuia() {
		return guia;
	}
	public void setGuia(String guia) {
		this.guia = guia;
	}
	public String getDocref() {
		return docref;
	}
	public void setDocref(String docref) {
		this.docref = docref;
	}
	public String getVp_linea() {
		return vp_linea;
	}
	public void setVp_linea(String vp_linea) {
		this.vp_linea = vp_linea;
	}
	
	 

}
