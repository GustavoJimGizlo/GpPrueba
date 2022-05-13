package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class AdFarmaciasImagenBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigoFarmacia;
	private Long codigoArchivo;
	private String mostar;
	private String nombreImagen;
	public Long getCodigoFarmacia() {
		return codigoFarmacia;
	}
	public void setCodigoFarmacia(Long codigoFarmacia) {
		this.codigoFarmacia = codigoFarmacia;
	}
	public Long getCodigoArchivo() {
		return codigoArchivo;
	}
	public void setCodigoArchivo(Long codigoArchivo) {
		this.codigoArchivo = codigoArchivo;
	}
	public String getMostar() {
		return mostar;
	}
	public void setMostar(String mostar) {
		this.mostar = mostar;
	}
	public String getNombreImagen() {
		return nombreImagen;
	}
	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}
	
	
	
	

}
