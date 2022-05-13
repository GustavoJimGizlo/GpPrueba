package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AdFarmaciasCupoCorpBean {


	@Column(name = "convenio_corporativo")
	private String convenioCorporativo;
	
	
	@Column(name = "saldo_cupo")
	private BigDecimal saldoCupo;
	

	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;

	


	public String getConvenioCorporativo() {
		return convenioCorporativo;
	}

	public void setConvenioCorporativo(String convenioCorporativo) {
		this.convenioCorporativo = convenioCorporativo;
	}

	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public BigDecimal getSaldoCupo() {
		return saldoCupo;
	}

	public void setSaldoCupo(BigDecimal saldoCupo) {
		this.saldoCupo = saldoCupo;
	}


}
