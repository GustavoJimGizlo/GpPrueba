package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos;

import java.io.Serializable;

public class RecItemsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7169240849483782976L;

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
		this.rec_codigoProducto = fromUTF8(rec_codigoProducto);
	}

	public String getRec_descripcionProducto() {
		return fromUTF8(rec_descripcionProducto);
	}

	public void setRec_descripcionProducto(String rec_descripcionProducto) {
		this.rec_descripcionProducto = fromUTF8(rec_descripcionProducto);
	}

	public String getRec_principioActivo() {
		return fromUTF8(rec_principioActivo);
	}

	public void setRec_principioActivo(String rec_principioActivo) {
		this.rec_principioActivo = fromUTF8(rec_principioActivo);
	}

	public String getRec_cantidad() {
		return fromUTF8(rec_cantidad);
	}

	public void setRec_cantidad(String rec_cantidad) {
		this.rec_cantidad = fromUTF8(rec_cantidad);
	}

	public String getRec_prioridad() {
		return fromUTF8(rec_prioridad);
	}

	public void setRec_prioridad(String rec_prioridad) {
		this.rec_prioridad = fromUTF8(rec_prioridad);
	}

	public String getRec_cantidadEntregada() {
		return fromUTF8(rec_cantidadEntregada);
	}

	public void setRec_cantidadEntregada(String rec_cantidadEntregada) {
		this.rec_cantidadEntregada = fromUTF8(rec_cantidadEntregada);
	}

	public String getRec_valor() {
		return fromUTF8(rec_valor);
	}

	public void setRec_valor(String rec_valor) {
		this.rec_valor = fromUTF8(rec_valor);
	}

	public String getRec_cobertura() {
		return fromUTF8(rec_cobertura);
	}

	public void setRec_cobertura(String rec_cobertura) {
		this.rec_cobertura = fromUTF8(rec_cobertura);
	}
	
	
	public static String fromUTF8(String cadenaRevisar) {
		// return cadenaRevisar;
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ¡", "Ã¡");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ©", "Ã©");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ­", "Ã­");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ³", "Ã³");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂº", "Ãº");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ±", "Ã±");

		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ¡", "Ã�");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ©", "Ã‰");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ­", "Ã�");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ³", "Ã“");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂº", "Ãš");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ±", "Ã‘");
		
		cadenaRevisar = cadenaRevisar.replaceAll("[^\\dA-Za-z | ]", "");
		return cadenaRevisar;
	}

}
