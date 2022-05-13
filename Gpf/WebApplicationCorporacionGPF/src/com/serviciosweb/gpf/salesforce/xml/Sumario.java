package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sumario {
	private String base0;
	private String base12;
	private String subTotal;
	private String iva0;
	private String iva12;
	private String totalVenta;
	private String estado;

	// Getter Methods

	public String getBase0() {
		return base0;
	}

	public String getBase12() {
		return base12;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public String getIva0() {
		return iva0;
	}

	public String getIva12() {
		return iva12;
	}

	public String getTotalVenta() {
		return totalVenta;
	}

	// Setter Methods

	public void setBase0(String base0) {
		this.base0 = base0;
	}

	public void setBase12(String base12) {
		this.base12 = base12;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public void setIva0(String iva0) {
		this.iva0 = iva0;
	}

	public void setIva12(String iva12) {
		this.iva12 = iva12;
	}

	public void setTotalVenta(String totalVenta) {
		this.totalVenta = totalVenta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}