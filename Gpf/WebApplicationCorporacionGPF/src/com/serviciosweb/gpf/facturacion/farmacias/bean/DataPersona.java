/**
 * @date 06/06/2013
 * @user mftellor
 * @project_name EJBCommonsGPF
 * @company Corporacion GPF
 */
package com.serviciosweb.gpf.facturacion.farmacias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author mftellor
 *
 */
@Entity
@Table(name = "AB_PERSONAS")
public class DataPersona {

	@Column(name = "IDENTIFICACION")
	private String cedula;
	@Column(name = "TIPO_IDENTIFICACION")
	private String tipoIdentificacion;
	@Column(name = "CODIGO")
	private String codigo;
	@Column(name = "PRIMER_NOMBRE")
	private String primerNombre;
	@Column(name = "SEGUNDO_NOMBRE")
	private String segundoNombre;
	@Column(name = "PRIMER_APELLIDO")
	private String primerApellido;
	@Column(name = "SEGUNDO_APELLIDO")
	private String segundoApellido;
	@Column(name = "SEXO")
	private String sexo;
	@Column(name = "FECHA_NACIMIENTO")
	private String fechaNacimiento;
	@Column(name = "TELEF_DOM")
	private String teleDomicilio;
	@Column(name = "CELULAR")
	private String celular;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "PRINCIPAL")
	private String callePrincipal;
	@Column(name = "NUMERO")
	private String numero;
	@Column(name = "INTERSECCION")
	private String interseccion;
	@Column(name = "REFERENCIA")
	private String referencia;
	@Column(name = "CIUDAD")
	private String ciudad;
	@Column(name = "BARRIO")
	private String barrio;
	@Column(name = "TIPO")
	private String tipoDireccion;
	
	
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getTeleDomicilio() {
		return teleDomicilio;
	}
	public void setTeleDomicilio(String teleDomicilio) {
		this.teleDomicilio = teleDomicilio;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCallePrincipal() {
		return callePrincipal;
	}
	public void setCallePrincipal(String callePrincipal) {
		this.callePrincipal = callePrincipal;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getInterseccion() {
		return interseccion;
	}
	public void setInterseccion(String interseccion) {
		this.interseccion = interseccion;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getBarrio() {
		return barrio;
	}
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	public String getTipoDireccion() {
		return tipoDireccion;
	}
	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}
	
	
}
