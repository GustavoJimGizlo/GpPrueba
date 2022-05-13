package com.serviciosweb.gizlo.modelos;

import java.math.BigDecimal;

public class Request {
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
