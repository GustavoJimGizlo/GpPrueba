package com.serviciosweb.gpf.facturacion.farmacias.bean;

public class FaDetalleTransferencias {
	
	private Integer documento; 
	private Integer farmacia; 
	private String secuencial; 
	private Integer item; 
	private Double cantidad; 
	private Double comisariato_con_iva; 
	private Double comisariato_sin_iva; 
	private Double unidades; 
	private Double gravamen; 
	private Double costo_compra; 
	private Double costo_promedio; 
	private Double pvp_sin_iva; 
	private Double pvp_con_iva; 
	private Double venta;
	
	public FaDetalleTransferencias() {

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

	public String getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getComisariato_con_iva() {
		return comisariato_con_iva;
	}

	public void setComisariato_con_iva(Double comisariato_con_iva) {
		this.comisariato_con_iva = comisariato_con_iva;
	}

	public Double getComisariato_sin_iva() {
		return comisariato_sin_iva;
	}

	public void setComisariato_sin_iva(Double comisariato_sin_iva) {
		this.comisariato_sin_iva = comisariato_sin_iva;
	}

	public Double getUnidades() {
		return unidades;
	}

	public void setUnidades(Double unidades) {
		this.unidades = unidades;
	}

	public Double getGravamen() {
		return gravamen;
	}

	public void setGravamen(Double gravamen) {
		this.gravamen = gravamen;
	}

	public Double getCosto_compra() {
		return costo_compra;
	}

	public void setCosto_compra(Double costo_compra) {
		this.costo_compra = costo_compra;
	}

	public Double getCosto_promedio() {
		return costo_promedio;
	}

	public void setCosto_promedio(Double costo_promedio) {
		this.costo_promedio = costo_promedio;
	}

	public Double getPvp_sin_iva() {
		return pvp_sin_iva;
	}

	public void setPvp_sin_iva(Double pvp_sin_iva) {
		this.pvp_sin_iva = pvp_sin_iva;
	}

	public Double getPvp_con_iva() {
		return pvp_con_iva;
	}

	public void setPvp_con_iva(Double pvp_con_iva) {
		this.pvp_con_iva = pvp_con_iva;
	}

	public Double getVenta() {
		return venta;
	}

	public void setVenta(Double venta) {
		this.venta = venta;
	}

	@Override
	public String toString() {
		return "FaDetalleTransferencias [documento=" + documento + ", farmacia=" + farmacia + ", secuencial="
				+ secuencial + ", item=" + item + ", cantidad=" + cantidad + ", comisariato_con_iva="
				+ comisariato_con_iva + ", comisariato_sin_iva=" + comisariato_sin_iva + ", unidades=" + unidades
				+ ", gravamen=" + gravamen + ", costo_compra=" + costo_compra + ", costo_promedio=" + costo_promedio
				+ ", pvp_sin_iva=" + pvp_sin_iva + ", pvp_con_iva=" + pvp_con_iva + ", venta=" + venta + "]";
	}

	
	
		

}
