package com.corporaciongpf.adm.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class VOSecuenciaElectronica {

	

	
	
	private Long  farmacia;		
	private Long  secuencia;		
	

	private Long  documentoVenta;	
	
	private String clasificacionDocumento;	
	
	private String tipoMovimiento;	
	private Long tipodocumento;	
	private String cpVar1;	
	private String cpVar2;
	public Long getFarmacia() {
		return farmacia;
	}
	public void setFarmacia(Long farmacia) {
		this.farmacia = farmacia;
	}
	public Long getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}
	public Long getDocumentoVenta() {
		return documentoVenta;
	}
	public void setDocumentoVenta(Long documentoVenta) {
		this.documentoVenta = documentoVenta;
	}

	public String getClasificacionDocumento() {
		return clasificacionDocumento;
	}
	public void setClasificacionDocumento(String clasificacionDocumento) {
		this.clasificacionDocumento = clasificacionDocumento;
	}
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public Long getTipodocumento() {
		return tipodocumento;
	}
	public void setTipodocumento(Long tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	public String getCpVar1() {
		return cpVar1;
	}
	public void setCpVar1(String cpVar1) {
		this.cpVar1 = cpVar1;
	}
	public String getCpVar2() {
		return cpVar2;
	}
	public void setCpVar2(String cpVar2) {
		this.cpVar2 = cpVar2;
	}	
	
	
	
}
