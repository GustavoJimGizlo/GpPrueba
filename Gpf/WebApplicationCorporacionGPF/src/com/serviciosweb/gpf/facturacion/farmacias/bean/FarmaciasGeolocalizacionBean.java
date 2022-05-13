package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class FarmaciasGeolocalizacionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigoGelocalizacion;
	private Double longitud;
	private Double latitud;
	private Long codigoFarmacia;
	private String url1;
	private Long distanciaKilometros;
	public Long getCodigoGelocalizacion() {
		return codigoGelocalizacion;
	}
	public void setCodigoGelocalizacion(Long codigoGelocalizacion) {
		this.codigoGelocalizacion = codigoGelocalizacion;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Long getCodigoFarmacia() {
		return codigoFarmacia;
	}
	public void setCodigoFarmacia(Long codigoFarmacia) {
		this.codigoFarmacia = codigoFarmacia;
	}
	public String getUrl1() {
		return url1;
	}
	public void setUrl1(String url1) {
		this.url1 = url1;
	}
	public Long getDistanciaKilometros() {
		return distanciaKilometros;
	}
	public void setDistanciaKilometros(Long distanciaKilometros) {
		this.distanciaKilometros = distanciaKilometros;
	}
	
	
	
	
}
