package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Articulo {
	private String item;
	private String nombre;
	private String cantidad;
	private String pvp;
	private String pvpComisariato;
	private String porcentajeImpuesto;
	private String valorImpuesto;
	private String costo;
	private String ventaSinIva;
	private String iva;
	private String venta;
	private String unidadVenta;
	private String secccionContable;

	// Getter Methods

	public String getItem() {
		return item;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCantidad() {
		return cantidad;
	}

	public String getPvp() {
		return pvp;
	}

	public String getPvpComisariato() {
		return pvpComisariato;
	}

	public String getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}

	public String getValorImpuesto() {
		return valorImpuesto;
	}

	public String getCosto() {
		return costo;
	}

	public String getVentaSinIva() {
		return ventaSinIva;
	}

	public String getIva() {
		return iva;
	}

	public String getVenta() {
		return venta;
	}

	public String getUnidadVenta() {
		return unidadVenta;
	}

	public String getSecccionContable() {
		return secccionContable;
	}

	// Setter Methods

	public void setItem(String item) {
		this.item = item;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public void setPvp(String pvp) {
		this.pvp = pvp;
	}

	public void setPvpComisariato(String pvpComisariato) {
		this.pvpComisariato = pvpComisariato;
	}

	public void setPorcentajeImpuesto(String porcentajeImpuesto) {
		this.porcentajeImpuesto = porcentajeImpuesto;
	}

	public void setValorImpuesto(String valorImpuesto) {
		this.valorImpuesto = valorImpuesto;
	}

	public void setCosto(String costo) {
		this.costo = costo;
	}

	public void setVentaSinIva(String ventaSinIva) {
		this.ventaSinIva = ventaSinIva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public void setVenta(String venta) {
		this.venta = venta;
	}

	public void setUnidadVenta(String unidadVenta) {
		this.unidadVenta = unidadVenta;
	}

	public void setSecccionContable(String secccionContable) {
		this.secccionContable = secccionContable;
	}
}
