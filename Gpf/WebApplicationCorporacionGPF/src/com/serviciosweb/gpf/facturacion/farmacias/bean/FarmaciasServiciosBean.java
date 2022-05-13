package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class FarmaciasServiciosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigoFarmaciasServicios;
	private Long codigoServicios;
	private Long codigoFarmacia;
	private String nombreServicio;
	public Long getCodigoFarmaciasServicios() {
		return codigoFarmaciasServicios;
	}
	public void setCodigoFarmaciasServicios(Long codigoFarmaciasServicios) {
		this.codigoFarmaciasServicios = codigoFarmaciasServicios;
	}
	public Long getCodigoServicios() {
		return codigoServicios;
	}
	public void setCodigoServicios(Long codigoServicios) {
		this.codigoServicios = codigoServicios;
	}
	public Long getCodigoFarmacia() {
		return codigoFarmacia;
	}
	public void setCodigoFarmacia(Long codigoFarmacia) {
		this.codigoFarmacia = codigoFarmacia;
	}
	public String getNombreServicio() {
		return nombreServicio;
	}
	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

}
