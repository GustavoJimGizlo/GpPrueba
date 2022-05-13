package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.util.Date;

public class FaTransfernciaFarmacias {
	
	private Integer empresa_destino;
	private Integer documento; 
	private Integer farmacia; 
	private Integer tipo_mercaderia; 
	private Integer farmacia_envio; 
	private String guia_remision; 
	private Double venta; 
	private Double iva; 
	private Date fecha; 
	private Double costo; 
	private String tipo_negocio;
	
	public FaTransfernciaFarmacias() {
		
	}

	public Integer getEmpresa_destino() {
		return empresa_destino;
	}

	public void setEmpresa_destino(Integer empresa_destino) {
		this.empresa_destino = empresa_destino;
	}

	public Integer getDocumento() {
		return documento;
	}

	public void setDocumento(Integer documento) {
		this.documento = documento;
	}

	public Integer getFarmacia() {
		return farmacia;
	}

	public void setFarmacia(Integer farmacia) {
		this.farmacia = farmacia;
	}

	public Integer getTipo_mercaderia() {
		return tipo_mercaderia;
	}

	public void setTipo_mercaderia(Integer tipo_mercaderia) {
		this.tipo_mercaderia = tipo_mercaderia;
	}

	public Integer getFarmacia_envio() {
		return farmacia_envio;
	}

	public void setFarmacia_envio(Integer farmacia_envio) {
		this.farmacia_envio = farmacia_envio;
	}

	public String getGuia_remision() {
		return guia_remision;
	}

	public void setGuia_remision(String guia_remision) {
		this.guia_remision = guia_remision;
	}

	public Double getVenta() {
		return venta;
	}

	public void setVenta(Double venta) {
		this.venta = venta;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getTipo_negocio() {
		return tipo_negocio;
	}

	public void setTipo_negocio(String tipo_negocio) {
		this.tipo_negocio = tipo_negocio;
	}

	@Override
	public String toString() {
		return "FaTransfernciaFarmacias [empresa_destino=" + empresa_destino + ", documento=" + documento
				+ ", farmacia=" + farmacia + ", tipo_mercaderia=" + tipo_mercaderia + ", farmacia_envio="
				+ farmacia_envio + ", guia_remision=" + guia_remision + ", venta=" + venta + ", iva=" + iva + ", fecha="
				+ fecha + ", costo=" + costo + ", tipo_negocio=" + tipo_negocio + "]";
	}


	
			
	
}
