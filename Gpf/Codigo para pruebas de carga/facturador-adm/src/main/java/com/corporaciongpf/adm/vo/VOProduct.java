package com.corporaciongpf.adm.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VOProduct {

	private String  sku;
	private String  priceList;
	private String  finalPrice;
	private Long  quantity;
	private String  digitRUTCashier;
	private String  idCashier;
	private String  barcode;
	private String  points;
	private BigDecimal  sale;
	private Long  unit;
	private BigDecimal  ivaPercent;
	
	
	
	
	
	


	public BigDecimal getIvaPercent() {
		return ivaPercent;
	}

	public void setIvaPercent(BigDecimal ivaPercent) {
		this.ivaPercent = ivaPercent;
	}

	public Long getUnit() {
		return unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}



	public BigDecimal getSale() {
		return sale;
	}

	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}



	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getPriceList() {
		return priceList;
	}

	public void setPriceList(String priceList) {
		this.priceList = priceList;
	}

	public String getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}


	public String getDigitRUTCashier() {
		return digitRUTCashier;
	}

	public void setDigitRUTCashier(String digitRUTCashier) {
		this.digitRUTCashier = digitRUTCashier;
	}

	public String getIdCashier() {
		return idCashier;
	}

	public void setIdCashier(String idCashier) {
		this.idCashier = idCashier;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}




	public List<VODiscount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<VODiscount> discounts) {
		this.discounts = discounts;
	}









	public String getCashierRUT() {
		return cashierRUT;
	}

	public void setCashierRUT(String cashierRUT) {
		this.cashierRUT = cashierRUT;
	}


	private String  cashierRUT;
	
	List<VODiscount> discounts= new ArrayList<>();

}

