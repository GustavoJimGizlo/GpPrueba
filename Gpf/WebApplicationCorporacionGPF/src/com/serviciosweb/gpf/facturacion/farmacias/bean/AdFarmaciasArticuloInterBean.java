package com.serviciosweb.gpf.facturacion.farmacias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Null;

@Entity
@Table(name = "AB_PERSONAS")
public class AdFarmaciasArticuloInterBean {

	@Column(name = "CODIGO")
	private Integer codigoArticulo;
	
	@Null
	@Column(name = "unidad_venta")
	private Integer unidadVenta;
	
	@Null
	@Column(name = "pvp_sin_iva")
	private Double pvpSinIva;
	@Null
	@Column(name = "pvp_con_iva")
	private Double pvpConIva;
	
	@Null
	@Column(name = "com_sin_iva")
	private Double comSinIva;
	
	@Null
	@Column(name = "com_con_iva")
	private Double comConIva;
	
	@Null
	@Column(name = "costo_compra")
	private Double costoCompra;
	
	@Null
	@Column(name = "costo")
	private Double costo;
	
	@Null
	@Column(name = "gravamen")
	private Double gravamen;
	
	@Null
	@Column(name = "activo")
	private String activo;
	
	@Null
	@Column(name = "psicotropico")
	private String psicotropico;
	
	@Null
	@Column(name = "presentacion")
	private String presentacion;

	public Integer getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(Integer codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public Integer getUnidadVenta() {
		return unidadVenta;
	}

	public void setUnidadVenta(Integer unidadVenta) {
		this.unidadVenta = unidadVenta;
	}

	public Double getPvpSinIva() {
		return pvpSinIva;
	}

	public void setPvpSinIva(Double pvpSinIva) {
		this.pvpSinIva = pvpSinIva;
	}

	public Double getPvpConIva() {
		return pvpConIva;
	}

	public void setPvpConIva(Double pvpConIva) {
		this.pvpConIva = pvpConIva;
	}

	public Double getComSinIva() {
		return comSinIva;
	}

	public void setComSinIva(Double comSinIva) {
		this.comSinIva = comSinIva;
	}

	public Double getComConIva() {
		return comConIva;
	}

	public void setComConIva(Double comConIva) {
		this.comConIva = comConIva;
	}

	public Double getCostoCompra() {
		return costoCompra;
	}

	public void setCostoCompra(Double costoCompra) {
		this.costoCompra = costoCompra;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Double getGravamen() {
		return gravamen;
	}

	public void setGravamen(Double gravamen) {
		this.gravamen = gravamen;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getPsicotropico() {
		return psicotropico;
	}

	public void setPsicotropico(String psicotropico) {
		this.psicotropico = psicotropico;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	
	
	
	
}
