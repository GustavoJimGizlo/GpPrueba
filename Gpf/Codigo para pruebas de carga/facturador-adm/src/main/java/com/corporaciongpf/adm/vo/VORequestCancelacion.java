package com.corporaciongpf.adm.vo;


import java.math.BigDecimal;

public class VORequestCancelacion {
	String correlationTX;
	String storeCode;
	BigDecimal amount;
	
	
	public String getCorrelationTX() {
		return correlationTX;
	}
	public void setCorrelationTX(String correlationTX) {
		this.correlationTX = correlationTX;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	
}
