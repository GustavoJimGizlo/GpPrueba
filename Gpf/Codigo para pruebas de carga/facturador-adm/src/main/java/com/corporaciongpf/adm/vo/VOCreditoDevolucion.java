package com.corporaciongpf.adm.vo;

import java.math.BigDecimal;
import java.sql.Date;




public class VOCreditoDevolucion {


	private Long id;

	

	private Long  referencia;
	
	private BigDecimal totalVenta;		
	
	private BigDecimal totalPvp;		
	
	
	private Date fecha;		
	
	private String cancelada;		
	
	
	private Long  cliente;
	
	private String formaPago;		
	
	private Long  farmacia;		
	
	
	
	
	
	private Long  documentoVenta;	
	
	private String clasificacionMovimiento;	
	
	private String tipoMovimiento;	
	
	
	private Long  empleado;	
	
	private Long  empresa;		
		

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReferencia() {
		return referencia;
	}

	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}

	public BigDecimal getTotalPvp() {
		return totalPvp;
	}

	public void setTotalPvp(BigDecimal totalPvp) {
		this.totalPvp = totalPvp;
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

	public Long getDocumentoVenta() {
		return documentoVenta;
	}

	public void setDocumentoVenta(Long documentoVenta) {
		this.documentoVenta = documentoVenta;
	}

	public String getClasificacionMovimiento() {
		return clasificacionMovimiento;
	}

	public void setClasificacionMovimiento(String clasificacionMovimiento) {
		this.clasificacionMovimiento = clasificacionMovimiento;
	}

	public Long getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Long empleado) {
		this.empleado = empleado;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}	
	
	
	
}