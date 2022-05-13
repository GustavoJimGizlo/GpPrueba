package com.corporaciongpf.adm.vo;

import java.math.BigDecimal;
import java.util.Date;

public class VOPayment {

	private String   payment;
	private String   paymentMethod;
	private String   sequencer;
	private BigDecimal   amount;
	private String   creditCardNumber;
	private BigDecimal   interest;
	private String   quota;
	private String   quotaType;
	private String   authorizationType;
	private String   authorizationCode;
	private Date     expirationDate;
	private String   clientOwnerDocument;
	private String   amountQuota;	
	
	
	
	
	
	
	
	
	
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getSequencer() {
		return sequencer;
	}
	public void setSequencer(String sequencer) {
		this.sequencer = sequencer;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getQuotaType() {
		return quotaType;
	}
	public void setQuotaType(String quotaType) {
		this.quotaType = quotaType;
	}
	public String getAuthorizationType() {
		return authorizationType;
	}
	public void setAuthorizationType(String authorizationType) {
		this.authorizationType = authorizationType;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getClientOwnerDocument() {
		return clientOwnerDocument;
	}
	public void setClientOwnerDocument(String clientOwnerDocument) {
		this.clientOwnerDocument = clientOwnerDocument;
	}
	public String getAmountQuota() {
		return amountQuota;
	}
	public void setAmountQuota(String amountQuota) {
		this.amountQuota = amountQuota;
	}

}

