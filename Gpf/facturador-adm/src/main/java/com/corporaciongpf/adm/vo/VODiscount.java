package com.corporaciongpf.adm.vo;

public class VODiscount {

	
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	public Integer getUsedPoints() {
		return usedPoints;
	}
	public void setUsedPoints(Integer usedPoints) {
		this.usedPoints = usedPoints;
	}
	public Double getBenefit() {
		return benefit;
	}
	public void setBenefit(Double benefit) {
		this.benefit = benefit;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getConvertion() {
		return convertion;
	}
	public void setConvertion(String convertion) {
		this.convertion = convertion;
	}
	public Boolean getLoyaltyDiscount() {
		return loyaltyDiscount;
	}
	public void setLoyaltyDiscount(Boolean loyaltyDiscount) {
		this.loyaltyDiscount = loyaltyDiscount;
	}
	private String   promotionId;
	private Integer   usedPoints;
	private Double   benefit;
	private Double   amount;
	private String   convertion;
	private Boolean   loyaltyDiscount;
	
	
	
	
}


