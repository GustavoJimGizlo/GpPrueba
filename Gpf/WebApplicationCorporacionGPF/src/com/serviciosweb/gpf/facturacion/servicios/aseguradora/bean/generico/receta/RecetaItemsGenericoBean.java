package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.receta;

import java.io.Serializable;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TextoUtil;

public class RecetaItemsGenericoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rec_codigoProducto;
	private String rec_descripcionProducto;
	private String rec_principioActivo;
	private String rec_cantidad;
	private String rec_prioridad;
	private String rec_cantidadEntregada;
	private String rec_valor;
	private String rec_cobertura;

	public String getRec_codigoProducto() {
		return rec_codigoProducto;
	}

	public void setRec_codigoProducto(String rec_codigoProducto) {
		this.rec_codigoProducto = TextoUtil.fromUTF8(rec_codigoProducto);
	}

	public String getRec_descripcionProducto() {
		return TextoUtil.fromUTF8(rec_descripcionProducto);
	}

	public void setRec_descripcionProducto(String rec_descripcionProducto) {
		this.rec_descripcionProducto = TextoUtil.fromUTF8(rec_descripcionProducto);
	}

	public String getRec_principioActivo() {
		return TextoUtil.fromUTF8(rec_principioActivo);
	}

	public void setRec_principioActivo(String rec_principioActivo) {
		this.rec_principioActivo = TextoUtil.fromUTF8(rec_principioActivo);
	}

	public String getRec_cantidad() {
		return TextoUtil.fromUTF8(rec_cantidad);
	}

	public void setRec_cantidad(String rec_cantidad) {
		this.rec_cantidad = TextoUtil.fromUTF8(rec_cantidad);
	}

	public String getRec_prioridad() {
		return TextoUtil.fromUTF8(rec_prioridad);
	}

	public void setRec_prioridad(String rec_prioridad) {
		this.rec_prioridad = TextoUtil.fromUTF8(rec_prioridad);
	}

	public String getRec_cantidadEntregada() {
		return TextoUtil.fromUTF8(rec_cantidadEntregada);
	}

	public void setRec_cantidadEntregada(String rec_cantidadEntregada) {
		this.rec_cantidadEntregada = TextoUtil.fromUTF8(rec_cantidadEntregada);
	}

	public String getRec_valor() {
		return TextoUtil.fromUTF8(rec_valor);
	}

	public void setRec_valor(String rec_valor) {
		this.rec_valor = TextoUtil.fromUTF8(rec_valor);
	}

	public String getRec_cobertura() {
		return TextoUtil.fromUTF8(rec_cobertura);
	}

	public void setRec_cobertura(String rec_cobertura) {
		this.rec_cobertura = TextoUtil.fromUTF8(rec_cobertura);
	}
}
