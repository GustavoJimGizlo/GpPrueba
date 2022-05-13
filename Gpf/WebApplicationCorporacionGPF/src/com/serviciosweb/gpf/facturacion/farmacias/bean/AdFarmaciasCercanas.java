package com.serviciosweb.gpf.facturacion.farmacias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AB_PERSONAS")
public class AdFarmaciasCercanas {

	@Column(name = "CODIGO")
	private Integer codigoLocal;
	@Column(name = "NOMBRE")
	private String nombre;
	@Column(name = "EMPRESA")
	private Integer empresa;
	@Column(name = "DIRECCION")
	private String direccion;	
	@Column(name = "longitud")
	private Double longitudOrigen;
	@Column(name = "latitud")
	private Double latitudOrigen;
	@Column(name = "cantidad_stock")
	private Integer cantidadStock=0;
	@Column(name = "unidad_venta")
	private Integer unidadVenta;
	private Double distanciaKilometros;
	@Column(name = "pvp_sin_iva")
	private Double pvpSinIva;
	@Column(name = "pvp_con_iva")
	private Double pvpConIva;
	
	
	
	
	public Integer getCodigoLocal() {
		return codigoLocal;
	}
	public void setCodigoLocal(Integer codigoLocal) {
		this.codigoLocal = codigoLocal;
	}
	public Double getLongitudOrigen() {
		return longitudOrigen;
	}
	public void setLongitudOrigen(Double longitudOrigen) {
		this.longitudOrigen = longitudOrigen;
	}
	public Double getLatitudOrigen() {
		return latitudOrigen;
	}
	public void setLatitudOrigen(Double latitudOrigen) {
		this.latitudOrigen = latitudOrigen;
	}
	public Double getDistanciaKilometros() {
		return distanciaKilometros;
	}
	public void setDistanciaKilometros(Double distanciaKilometros) {
		this.distanciaKilometros = distanciaKilometros;
	}
	public Integer getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Integer getCantidadStock() {
		return cantidadStock;
	}
	public void setCantidadStock(Integer cantidadStock) {
		this.cantidadStock = cantidadStock;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	
	
	
	  
	 
	
	
}
