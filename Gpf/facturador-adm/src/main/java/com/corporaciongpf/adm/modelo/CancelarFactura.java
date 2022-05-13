package com.corporaciongpf.adm.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;







/**
 * The persistent class for the FA_INT_CANCEL_FD database table.
 * 
 */

public class CancelarFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long documentoVenta;

	private Long farmacia;


	private Date fechaActualiza;


	private Date fechaInserta;


	private Date fechaRequest;

	private Date fechaResponse;


	private Long id;	

	@Lob
	private String jsonRequest;

	@Lob
	private String jsonResponse;

	private String mensaje;

	private String mensajeRequest;

	private BigDecimal numeroEnvios;

	private String ordenSfcc;

	private BigDecimal trazaId;

	private String trazaRequest;

	private String usuarioActualiza;


	private String usuarioInserta;




	public Long getDocumentoVenta() {
		return documentoVenta;
	}

	public void setDocumentoVenta(Long documentoVenta) {
		this.documentoVenta = documentoVenta;
	}

	public Long getFarmacia() {
		return farmacia;
	}

	public void setFarmacia(Long farmacia) {
		this.farmacia = farmacia;
	}

	public Date getFechaActualiza() {
		return this.fechaActualiza;
	}

	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}

	public Date getFechaInserta() {
		return this.fechaInserta;
	}

	public void setFechaInserta(Date fechaInserta) {
		this.fechaInserta = fechaInserta;
	}

	public Date getFechaRequest() {
		return this.fechaRequest;
	}

	public void setFechaRequest(Date fechaRequest) {
		this.fechaRequest = fechaRequest;
	}

	public Date getFechaResponse() {
		return this.fechaResponse;
	}

	public void setFechaResponse(Date fechaResponse) {
		this.fechaResponse = fechaResponse;
	}







	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getJsonRequest() {
		return this.jsonRequest;
	}

	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}

	public String getJsonResponse() {
		return this.jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensajeRequest() {
		return this.mensajeRequest;
	}

	public void setMensajeRequest(String mensajeRequest) {
		this.mensajeRequest = mensajeRequest;
	}

	public BigDecimal getNumeroEnvios() {
		return this.numeroEnvios;
	}

	public void setNumeroEnvios(BigDecimal numeroEnvios) {
		this.numeroEnvios = numeroEnvios;
	}

	public String getOrdenSfcc() {
		return this.ordenSfcc;
	}

	public void setOrdenSfcc(String ordenSfcc) {
		this.ordenSfcc = ordenSfcc;
	}

	public BigDecimal getTrazaId() {
		return this.trazaId;
	}

	public void setTrazaId(BigDecimal trazaId) {
		this.trazaId = trazaId;
	}

	public String getTrazaRequest() {
		return this.trazaRequest;
	}

	public void setTrazaRequest(String trazaRequest) {
		this.trazaRequest = trazaRequest;
	}

	public String getUsuarioActualiza() {
		return this.usuarioActualiza;
	}

	public void setUsuarioActualiza(String usuarioActualiza) {
		this.usuarioActualiza = usuarioActualiza;
	}

	public String getUsuarioInserta() {
		return this.usuarioInserta;
	}

	public void setUsuarioInserta(String usuarioInserta) {
		this.usuarioInserta = usuarioInserta;
	}

}