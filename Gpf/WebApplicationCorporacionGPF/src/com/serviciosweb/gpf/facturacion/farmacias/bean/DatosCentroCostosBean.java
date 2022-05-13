package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DatosCentroCostosBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "EMPRESA")
	private String empresa;
	@Column(name = "SUCURSAL")
	private String sucursal;
	//@Column(name = "CODIGOSUCURSAL")
	//private String codigoSucursal;
	@Column(name = "DESCRIPCIONCCT")
	private String descripcionCct;
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getDescripcionCct() {
		return descripcionCct;
	}
	public void setDescripcionCct(String descripcionCct) {
		this.descripcionCct = descripcionCct;
	}

}
