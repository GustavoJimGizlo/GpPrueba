package com.corporaciongpf.adm.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class VOFarmaciaNotaCredito {

	

	private Long  codigo;	
	
	
	private Date fechaCancelacion;	
		
	
	
	private Date fecha;	
		


	private String cancelada;	
	


	private String usuario;		
	
	private BigDecimal valor;		

	
	
	private Long  documentoVenta;	
		
	
	private String formaPago;			
	
	
	private Long  farmacia;	
			

	private Long  empleadoCobra;			
	
	
	private String tipoCancelacion;			
		

	private Long  farmaciaCanje;			


	private Long  documentoCancelacion;


	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}


	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getCancelada() {
		return cancelada;
	}


	public void setCancelada(String cancelada) {
		this.cancelada = cancelada;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public Long getDocumentoVenta() {
		return documentoVenta;
	}


	public void setDocumentoVenta(Long documentoVenta) {
		this.documentoVenta = documentoVenta;
	}


	public String getFormaPago() {
		return formaPago;
	}


	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}


	public Long getFarmacia() {
		return farmacia;
	}


	public void setFarmacia(Long farmacia) {
		this.farmacia = farmacia;
	}


	public Long getEmpleadoCobra() {
		return empleadoCobra;
	}


	public void setEmpleadoCobra(Long empleadoCobra) {
		this.empleadoCobra = empleadoCobra;
	}


	public String getTipoCancelacion() {
		return tipoCancelacion;
	}


	public void setTipoCancelacion(String tipoCancelacion) {
		this.tipoCancelacion = tipoCancelacion;
	}


	public Long getFarmaciaCanje() {
		return farmaciaCanje;
	}


	public void setFarmaciaCanje(Long farmaciaCanje) {
		this.farmaciaCanje = farmaciaCanje;
	}


	public Long getDocumentoCancelacion() {
		return documentoCancelacion;
	}


	public void setDocumentoCancelacion(Long documentoCancelacion) {
		this.documentoCancelacion = documentoCancelacion;
	}	
	
	
	
	
	
	
}
