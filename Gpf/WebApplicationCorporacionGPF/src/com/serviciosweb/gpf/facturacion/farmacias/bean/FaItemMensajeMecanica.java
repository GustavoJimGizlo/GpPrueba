package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

public class FaItemMensajeMecanica implements Serializable {

	private static final long serialVersionUID = 1216195402509679186L;

	@Id
	@SequenceGenerator(name = "FA_SEC_ITEM_MENSAJE_MECANICA_GENERATOR", sequenceName = "FA_SEC_ITEM_MENSAJE_MECANICA", allocationSize = 1)
	@GeneratedValue(generator = "FA_SEC_ITEM_MENSAJE_MECANICA_GENERATOR")
	private long codigo;

	@Column(name = "EMPRESA")
	private long empresa;

	@Column(name = "ITEM")
	private long item;

	@Column(name = "CODIGO_OFERTA")
	private long codigoOferta;

	@Column(name = "CODIGO_PROMOACION")
	private long codigoPromocion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Column(name = "DESCRIPCION_MECANICA")
	private String descripcionMecanica;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "USUARIO")
	private String usuario;
	
	private long orden;

	@Transient
	private String mensajeError;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(long empresa) {
		this.empresa = empresa;
	}

	public long getItem() {
		return item;
	}

	public void setItem(long item) {
		this.item = item;
	}

	public long getCodigoOferta() {
		return codigoOferta;
	}

	public void setCodigoOferta(long codigoOferta) {
		this.codigoOferta = codigoOferta;
	}

	public long getCodigoPromocion() {
		return codigoPromocion;
	}

	public void setCodigoPromocion(long codigoPromocion) {
		this.codigoPromocion = codigoPromocion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDescripcionMecanica() {
		return descripcionMecanica;
	}

	public void setDescripcionMecanica(String descripcionMecanica) {
		this.descripcionMecanica = descripcionMecanica;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public long getOrden() {
		return orden;
	}

	public void setOrden(long orden) {
		this.orden = orden;
	}
	
	  

}
