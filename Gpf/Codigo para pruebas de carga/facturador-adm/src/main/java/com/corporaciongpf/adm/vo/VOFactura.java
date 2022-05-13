package com.corporaciongpf.adm.vo;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;
public class VOFactura implements Serializable {





	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String   company;
	private String   user;
	private String   orderId;
	

	

	

	private String   idUser;
	private String   businessDate;
	private String   transactiondate;
	private String   chain;
	private String   emmitedDocument;

	private Boolean   electronicDocument;
	private String   startTxData;
	private String   endTxData;
	
	private String   correlationTX;
	private String   localid;
	private String   documentNumber;


	private BigDecimal   total;
	private String   affected;
	private Double   taxTotal;
	private String   exempt;
	private String   posNumber;
	private String   machineNumber;


	List<VOPayment> payments= new ArrayList<>();
	List<VOProduct> products= new ArrayList<>();
	VOClient client;
	
	
	private String   specialTaxpayer;

		
	private String   currency;
	private String   authorizationCode;
	private String   environment;
	private String   emissionType;
	private BigDecimal   discountTotal;
	private BigDecimal   untaxedTotal;
	

	private BigDecimal   base0;
	private BigDecimal   base12;
	private BigDecimal   iva;
	private String   state;

	private String   issuerRuc;
	private String   requiresAccounting;
	private String idTypeClient;

	private BigDecimal tip;

	private String purchaseOrder;
	private BigDecimal totalInvoiceCost;
	private BigDecimal totalInvoicePrice;
	private String correlativeAssociatedTX;
	
	private String billUrl;
	private String xmlUrl;
	
	private Auth authentication;
	
	public Auth getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Auth authentication) {
		this.authentication = authentication;
	}
	public String getCorrelativeAssociatedTX() {
		return correlativeAssociatedTX;
	}
	public void setCorrelativeAssociatedTX(String correlativeAssociatedTX) {
		this.correlativeAssociatedTX = correlativeAssociatedTX;
	}
	public String getBillUrl() {
		return billUrl;
	}
	public void setBillUrl(String billUrl) {
		this.billUrl = billUrl;
	}
	public String getXmlUrl() {
		return xmlUrl;
	}
	public void setXmlUrl(String xmlUrl) {
		this.xmlUrl = xmlUrl;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}	
	


	public BigDecimal getTotalInvoiceCost() {
		return totalInvoiceCost;
	}
	public void setTotalInvoiceCost(BigDecimal totalInvoiceCost) {
		this.totalInvoiceCost = totalInvoiceCost;
	}
	public BigDecimal getTotalInvoicePrice() {
		return totalInvoicePrice;
	}
	public void setTotalInvoicePrice(BigDecimal totalInvoicePrice) {
		this.totalInvoicePrice = totalInvoicePrice;
	}
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}	
	
	
	
	
	
	public BigDecimal getTip() {
		return tip;
	}
	public void setTip(BigDecimal tip) {
		this.tip = tip;
	}	
	
	
	
	
	
	
	
	public String getIdTypeClient() {
		return idTypeClient;
	}
	public void setIdTypeClient(String idTypeClient) {
		this.idTypeClient = idTypeClient;
	}
	
	
	
	public String getIssuerRuc() {
		return issuerRuc;
	}
	public void setIssuerRuc(String issuerRuc) {
		this.issuerRuc = issuerRuc;
	}	
	
	
	
	

	
	
	public String getRequiresAccounting() {
		return requiresAccounting;
	}
	public void setRequiresAccounting(String requiresAccounting) {
		this.requiresAccounting = requiresAccounting;
	}	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
		
	
	
	
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(String transactiondate) {
		this.transactiondate = transactiondate;
	}
	public String getChain() {
		return chain;
	}
	public void setChain(String chain) {
		this.chain = chain;
	}
	public String getEmmitedDocument() {
		return emmitedDocument;
	}
	public void setEmmitedDocument(String emmitedDocument) {
		this.emmitedDocument = emmitedDocument;
	}

	public String getStartTxData() {
		return startTxData;
	}
	public void setStartTxData(String startTxData) {
		this.startTxData = startTxData;
	}
	public String getEndTxData() {
		return endTxData;
	}
	public void setEndTxData(String endTxData) {
		this.endTxData = endTxData;
	}
	public String getCorrelationTX() {
		return correlationTX;
	}
	public void setCorrelationTX(String correlationTX) {
		this.correlationTX = correlationTX;
	}
	public String getLocalid() {
		return localid;
	}
	public void setLocalid(String localid) {
		this.localid = localid;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getAffected() {
		return affected;
	}
	public void setAffected(String affected) {
		this.affected = affected;
	}
	public Double getTaxTotal() {
		return taxTotal;
	}
	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}
	public String getExempt() {
		return exempt;
	}
	public void setExempt(String exempt) {
		this.exempt = exempt;
	}
	public String getPosNumber() {
		return posNumber;
	}
	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}
	public String getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}
	public List<VOPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<VOPayment> payments) {
		this.payments = payments;
	}
	public List<VOProduct> getProducts() {
		return products;
	}
	public void setProducts(List<VOProduct> products) {
		this.products = products;
	}
	public VOClient getClient() {
		return client;
	}
	public void setClient(VOClient client) {
		this.client = client;
	}
	public Boolean getElectronicDocument() {
		return electronicDocument;
	}
	public void setElectronicDocument(Boolean electronicDocument) {
		this.electronicDocument = electronicDocument;
	}	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	
	




	public String getSpecialTaxpayer() {
		return specialTaxpayer;
	}
	public void setSpecialTaxpayer(String specialTaxpayer) {
		this.specialTaxpayer = specialTaxpayer;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getEmissionType() {
		return emissionType;
	}
	public void setEmissionType(String emissionType) {
		this.emissionType = emissionType;
	}
	public BigDecimal getDiscountTotal() {
		return discountTotal;
	}
	public void setDiscountTotal(BigDecimal discountTotal) {
		this.discountTotal = discountTotal;
	}
	public BigDecimal getUntaxedTotal() {
		return untaxedTotal;
	}
	public void setUntaxedTotal(BigDecimal untaxedTotal) {
		this.untaxedTotal = untaxedTotal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public BigDecimal getBase0() {
		return base0;
	}
	public void setBase0(BigDecimal base0) {
		this.base0 = base0;
	}
	public BigDecimal getBase12() {
		return base12;
	}
	public void setBase12(BigDecimal base12) {
		this.base12 = base12;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}	
	
	
	
	
}


