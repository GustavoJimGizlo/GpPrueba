package com.corporaciongpf.adm.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VOProduct {

	private String  sku;
	private BigDecimal  priceList;
	private BigDecimal  finalPrice;
	private Long  quantity;
	private BigDecimal  taxAmount;
	private String  taxType;
	private BigDecimal  insurancePercent;
	private String  copay;
	private BigDecimal  insuranceAmount;
	
	
	private String  points;
	private BigDecimal  ivaPercent;
	

	private List<VODiscount> discounts= new ArrayList<>();


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public BigDecimal getPriceList() {
		return priceList;
	}


	public void setPriceList(BigDecimal priceList) {
		this.priceList = priceList;
	}


	public BigDecimal getFinalPrice() {
		return finalPrice;
	}


	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}


	public Long getQuantity() {
		return quantity;
	}


	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}




	public BigDecimal getTaxAmount() {
		return taxAmount;
	}


	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}


	public String getTaxType() {
		return taxType;
	}


	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}


	public BigDecimal getInsurancePercent() {
		return insurancePercent;
	}


	public void setInsurancePercent(BigDecimal insurancePercent) {
		this.insurancePercent = insurancePercent;
	}


	public String getCopay() {
		return copay;
	}


	public void setCopay(String copay) {
		this.copay = copay;
	}


	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}


	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}


	public String getPoints() {
		return points;
	}


	public void setPoints(String points) {
		this.points = points;
	}


	public BigDecimal getIvaPercent() {
		return ivaPercent;
	}


	public void setIvaPercent(BigDecimal ivaPercent) {
		this.ivaPercent = ivaPercent;
	}


	public List<VODiscount> getDiscounts() {
		return discounts;
	}


	public void setDiscounts(List<VODiscount> discounts) {
		this.discounts = discounts;
	}

}

