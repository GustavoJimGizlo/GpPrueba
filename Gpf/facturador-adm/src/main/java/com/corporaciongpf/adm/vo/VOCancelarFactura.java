package com.corporaciongpf.adm.vo;

import java.math.BigDecimal;
import java.sql.Date;


public class VOCancelarFactura {

	

	 private Long id;
	 private Long documentoVenta;
	 private Long farmacia;
	 private String ordenSfcc;
	 private Date fechaRequest;
	 private String jsonRequest;
	 private String jsonResponse;
	 private Date fechaResponse;
	 private String mensajeRequest;
	 private BigDecimal trazaID;
	 private String trazaRequest;
	 private Date fechaInserta;
	 private String usuarioInserta;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public String getOrdenSfcc() {
		return ordenSfcc;
	}
	public void setOrdenSfcc(String ordenSfcc) {
		this.ordenSfcc = ordenSfcc;
	}
	public Date getFechaRequest() {
		return fechaRequest;
	}
	public void setFechaRequest(Date fechaRequest) {
		this.fechaRequest = fechaRequest;
	}
	public String getJsonRequest() {
		return jsonRequest;
	}
	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}
	public String getMensajeRequest() {
		return mensajeRequest;
	}
	public void setMensajeRequest(String mensajeRequest) {
		this.mensajeRequest = mensajeRequest;
	}

	public Long getDocumentoVenta() {
		return documentoVenta;
	}

	public Long getFarmacia() {
		return farmacia;
	}
	public void setFarmacia(Long farmacia) {
		this.farmacia = farmacia;
	}
	public void setDocumentoVenta(Long documentoVenta) {
		this.documentoVenta = documentoVenta;
	}
	public BigDecimal getTrazaID() {
		return trazaID;
	}
	public void setTrazaID(BigDecimal trazaID) {
		this.trazaID = trazaID;
	}
	public String getTrazaRequest() {
		return trazaRequest;
	}
	public void setTrazaRequest(String trazaRequest) {
		this.trazaRequest = trazaRequest;
	}
	public Date getFechaInserta() {
		return fechaInserta;
	}
	public void setFechaInserta(Date fechaInserta) {
		this.fechaInserta = fechaInserta;
	}
	public String getUsuarioInserta() {
		return usuarioInserta;
	}
	public void setUsuarioInserta(String usuarioInserta) {
		this.usuarioInserta = usuarioInserta;
	}
	public String getJsonResponse() {
		return jsonResponse;
	}
	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
	public Date getFechaResponse() {
		return fechaResponse;
	}
	public void setFechaResponse(Date fechaResponse) {
		this.fechaResponse = fechaResponse;
	}




}