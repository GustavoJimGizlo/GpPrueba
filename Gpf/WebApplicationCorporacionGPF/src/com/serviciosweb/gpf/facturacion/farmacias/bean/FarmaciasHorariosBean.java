package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

public class FarmaciasHorariosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigoFarmaciasHorario;
	private String desde;
	private String hasta;
	private Long codigoTipoHorario;
	private Long codigoFarmacia;
	private String nombreTipoHorario;
	public Long getCodigoFarmaciasHorario() {
		return codigoFarmaciasHorario;
	}
	public void setCodigoFarmaciasHorario(Long codigoFarmaciasHorario) {
		this.codigoFarmaciasHorario = codigoFarmaciasHorario;
	}
	public String getDesde() {
		return desde;
	}
	public void setDesde(String desde) {
		this.desde = desde;
	}
	public String getHasta() {
		return hasta;
	}
	public void setHasta(String hasta) {
		this.hasta = hasta;
	}
	public Long getCodigoTipoHorario() {
		return codigoTipoHorario;
	}
	public void setCodigoTipoHorario(Long codigoTipoHorario) {
		this.codigoTipoHorario = codigoTipoHorario;
	}
	public Long getCodigoFarmacia() {
		return codigoFarmacia;
	}
	public void setCodigoFarmacia(Long codigoFarmacia) {
		this.codigoFarmacia = codigoFarmacia;
	}
	public String getNombreTipoHorario() {
		return nombreTipoHorario;
	}
	public void setNombreTipoHorario(String nombreTipoHorario) {
		this.nombreTipoHorario = nombreTipoHorario;
	}

}
