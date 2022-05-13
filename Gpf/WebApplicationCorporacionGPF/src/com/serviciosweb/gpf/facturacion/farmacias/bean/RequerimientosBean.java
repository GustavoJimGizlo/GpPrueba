package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class RequerimientosBean {

	@Column(name="CODIGO")
	private String codigoRequerimiento;
	@Column(name="usgenera")
	private String usuarioGenera;
	@Column(name="nombre_solicita")
	private String nombreSolicita;
	@Column(name="descripcion_req")
	private   java.sql.Clob descripcionRequerimientoClob;
	private   String descripcionRequerimiento;
	@Column(name="usuario_aprobacion")
	private String codigoUsuarioAprueba;
	@Column(name="estado_req")
	private String codigoEstadoRequerimiento;
	@Column(name="estado")
	private String descripcioEstadoRequerimiento;
	@Column(name="autoriza_rechaza")
	private String nombrePersonaRechaza;
	@Column(name="email_autoriza_rechaza")
	private String emailUtorizaRechaza;
	@Column(name="email_usuario")
	private String emailUsuario;
	@Column(name="user_aprobador")
	private String userAprobador;
	
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	public String getCodigoRequerimiento() {
		return codigoRequerimiento;
	}
	public void setCodigoRequerimiento(String codigoRequerimiento) {
		this.codigoRequerimiento = codigoRequerimiento;
	}
	public String getUsuarioGenera() {
		return usuarioGenera;
	}
	public void setUsuarioGenera(String usuarioGenera) {
		this.usuarioGenera = usuarioGenera;
	}
	public String getNombreSolicita() {
		return nombreSolicita;
	}
	public void setNombreSolicita(String nombreSolicita) {
		this.nombreSolicita = nombreSolicita;
	}
	

	public java.sql.Clob getDescripcionRequerimientoClob() {
		return descripcionRequerimientoClob;
	}
	public void setDescripcionRequerimientoClob(
			java.sql.Clob descripcionRequerimientoClob) {
		this.descripcionRequerimientoClob = descripcionRequerimientoClob;
	}
	public String getDescripcionRequerimiento() {		
		 try {
			 descripcionRequerimiento = this.descripcionRequerimientoClob.getSubString(1, (int) this.descripcionRequerimientoClob.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return descripcionRequerimiento;
	}
	public void setDescripcionRequerimiento(String descripcionRequerimiento) {
		this.descripcionRequerimiento = descripcionRequerimiento;
	}
	public String getCodigoUsuarioAprueba() {
		return codigoUsuarioAprueba;
	}
	public void setCodigoUsuarioAprueba(String codigoUsuarioAprueba) {
		this.codigoUsuarioAprueba = codigoUsuarioAprueba;
	}
	public String getCodigoEstadoRequerimiento() {
		return codigoEstadoRequerimiento;
	}
	public void setCodigoEstadoRequerimiento(String codigoEstadoRequerimiento) {
		this.codigoEstadoRequerimiento = codigoEstadoRequerimiento;
	}
	public String getDescripcioEstadoRequerimiento() {
		return descripcioEstadoRequerimiento;
	}
	public void setDescripcioEstadoRequerimiento(
			String descripcioEstadoRequerimiento) {
		this.descripcioEstadoRequerimiento = descripcioEstadoRequerimiento;
	}
	public String getNombrePersonaRechaza() {
		return nombrePersonaRechaza;
	}
	public void setNombrePersonaRechaza(String nombrePersonaRechaza) {
		this.nombrePersonaRechaza = nombrePersonaRechaza;
	}
	public String getEmailUtorizaRechaza() {
		return emailUtorizaRechaza;
	}
	public void setEmailUtorizaRechaza(String emailUtorizaRechaza) {
		this.emailUtorizaRechaza = emailUtorizaRechaza;
	}
	public String getUserAprobador() {
		return userAprobador;
	}
	public void setUserAprobador(String userAprobador) {
		this.userAprobador = userAprobador;
	}
	
	
	
}
