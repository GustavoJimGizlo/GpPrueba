package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.receta;

import java.io.Serializable;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TextoUtil;

public class RecetaFacturacionGenericoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9116401955791410200L;
	private String rec_receta;
	private String serie_factura;
	private String id_cliente;
	private String nombre_sucursal;
	private String nombre_oficina;
	private String autorizacion;
	private String valor_copago;
	private String fecha_factura;
	private String total_factura;


	public String getRec_receta() {
		return rec_receta;
	}

	public void setRec_receta(String rec_receta) {
		this.rec_receta = rec_receta;
	}

	public String getSerie_factura() {
		return TextoUtil.fromUTF8(serie_factura);
	}

	public void setSerie_factura(String serie_factura) {
		this.serie_factura = serie_factura;
	}

	public String getId_cliente() {
		return TextoUtil.fromUTF8(id_cliente);
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNombre_sucursal() {
		return TextoUtil.fromUTF8(nombre_sucursal);
	}

	public void setNombre_sucursal(String nombre_sucursal) {
		this.nombre_sucursal = TextoUtil.fromUTF8(nombre_sucursal);
	}

	public String getNombre_oficina() {
		return TextoUtil.fromUTF8(nombre_oficina);
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
}
