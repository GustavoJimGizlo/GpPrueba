package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;
import java.sql.Date;

import javax.swing.text.StyledEditorKit.BoldAction;

public class FybecaAdminBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int sit_id;	
	private String sit_nombre;
	private String sit_latitud;
	private String sit_longitud;
    private String sit_direccion;
    private int sit_provincia;
    private int sit_ciudad;
    private int sit_estado;
    private int sit_tipo;
	private  byte[] con_imagen;	
	private String com_telefono;
	private int com_tipo;
	private String com_codigo;
	private String con_horario;
	private int ser_servicio;
	private Boolean eliminar ;
	public int getSit_id() {
		return sit_id;
	}
	public void setSit_id(int sit_id) {
		this.sit_id = sit_id;
	}
	public String getSit_nombre() {
		return sit_nombre;
	}
	public void setSit_nombre(String sit_nombre) {
		this.sit_nombre = sit_nombre;
	}
	public String getSit_latitud() {
		return sit_latitud;
	}
	public void setSit_latitud(String sit_latitud) {
		this.sit_latitud = sit_latitud;
	}
	public String getSit_longitud() {
		return sit_longitud;
	}
	public void setSit_longitud(String sit_longitud) {
		this.sit_longitud = sit_longitud;
	}
	public String getSit_direccion() {
		return sit_direccion;
	}
	public void setSit_direccion(String sit_direccion) {
		this.sit_direccion = sit_direccion;
	}
	public int getSit_provincia() {
		return sit_provincia;
	}
	public void setSit_provincia(int sit_provincia) {
		this.sit_provincia = sit_provincia;
	}
	public int getSit_ciudad() {
		return sit_ciudad;
	}
	public void setSit_ciudad(int sit_ciudad) {
		this.sit_ciudad = sit_ciudad;
	}
	public int getSit_estado() {
		return sit_estado;
	}
	public void setSit_estado(int sit_estado) {
		this.sit_estado = sit_estado;
	}
	public int getSit_tipo() {
		return sit_tipo;
	}
	public void setSit_tipo(int sit_tipo) {
		this.sit_tipo = sit_tipo;
	}
	public byte[] getCon_imagen() {
		return con_imagen;
	}
	public void setCon_imagen(byte[] con_imagen) {
		this.con_imagen = con_imagen;
	}
	public String getCom_telefono() {
		return com_telefono;
	}
	public void setCom_telefono(String com_telefono) {
		this.com_telefono = com_telefono;
	}
	public int getCom_tipo() {
		return com_tipo;
	}
	public void setCom_tipo(int com_tipo) {
		this.com_tipo = com_tipo;
	}
	public String getCom_codigo() {
		return com_codigo;
	}
	public void setCom_codigo(String com_codigo) {
		this.com_codigo = com_codigo;
	}
	public String getCon_horario() {
		return con_horario;
	}
	public void setCon_horario(String con_horario) {
		this.con_horario = con_horario;
	}
	public int getSer_servicio() {
		return ser_servicio;
	}
	public void setSer_servicio(int ser_servicio) {
		this.ser_servicio = ser_servicio;
	}
	public Boolean getEliminar() {
		return eliminar;
	}
	public void setEliminar(Boolean eliminar) {
		this.eliminar = eliminar;
	}
	
	
	
	
	
}
