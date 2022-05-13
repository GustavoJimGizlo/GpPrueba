package com.gpf.partnersGroupGPF.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AbDirecciones implements Serializable {
     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CODIGO")
	private long codigo;
	@Column(name = "PRINCIPAL")
	private String principal;
	@Column(name = "NUMERO")
	private String numero;
	@Column(name = "INTERSECCION")
	private String interseccion;
	@Column(name = "REFERENCIA")
	private String referencia;
	@Column(name = "TIPO")
	private long tipo;
	@Column(name = "BARRIO")
	private long barrio;
	@Column(name = "CIUDAD")
	private long ciudad;
	
	
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
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
	public long getTipo() {
		return tipo;
	}
	public void setTipo(long tipo) {
		this.tipo = tipo;
	}
	public long getBarrio() {
		return barrio;
	}
	public void setBarrio(long barrio) {
		this.barrio = barrio;
	}
	public long getCiudad() {
		return ciudad;
	}
	public void setCiudad(long ciudad) {
		this.ciudad = ciudad;
	}
	
	
	  
	
	

}
