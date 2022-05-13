package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana;

import java.io.Serializable;

public class RecFacturacionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9116401955791410200L;
	private String rec_receta;
	private String serie_factura;
	private String id_cliente;
	private String nombre_cliente;
	private String nombre_sucursal;
	private String nombre_oficina;
	private String autorizacion;
	private String claveacceso;
	private String valor_copago;
	private String fecha_factura;
	private String total_factura;
	private String idprestador;

	public String getRec_receta() {
		return rec_receta;
	}

	public void setRec_receta(String rec_receta) {
		this.rec_receta = rec_receta;
	}

	public String getSerie_factura() {
		return fromUTF8(serie_factura);
	}

	public void setSerie_factura(String serie_factura) {
		this.serie_factura = serie_factura;
	}

	public String getId_cliente() {
		return fromUTF8(id_cliente);
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNombre_cliente() {
		return fromUTF8(nombre_cliente);
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public String getNombre_sucursal() {
		return fromUTF8(nombre_sucursal);
	}

	public void setNombre_sucursal(String nombre_sucursal) {
		this.nombre_sucursal = fromUTF8(nombre_sucursal);
	}

	public String getNombre_oficina() {
		return fromUTF8(nombre_oficina);
	}

	public void setNombre_oficina(String nombre_oficina) {
		this.nombre_oficina = nombre_oficina;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getClaveacceso() {
		return claveacceso;
	}

	public void setClaveacceso(String claveacceso) {
		this.claveacceso = claveacceso;
	}

	public String getValor_copago() {
		return valor_copago;
	}

	public void setValor_copago(String valor_copago) {
		this.valor_copago = valor_copago;
	}

	public String getFecha_factura() {
		return fecha_factura;
	}

	public void setFecha_factura(String fecha_factura) {
		this.fecha_factura = fecha_factura;
	}

	public String getTotal_factura() {
		return total_factura;
	}

	public void setTotal_factura(String total_factura) {
		this.total_factura = total_factura;
	}

	public String getIdprestador() {
		return fromUTF8(idprestador);
	}

	public void setIdprestador(String idprestador) {
		this.idprestador = idprestador;
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
