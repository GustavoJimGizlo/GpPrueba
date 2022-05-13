package com.serviciosweb.gpf.salesforce.xml;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Direccion {
	private String codigo;
	private String dirCompleta;
	private String callePrincipal;
	private String numero;
	private String interseccion;
	private String referencia;

	// Getter Methods

	public String getCodigo() {
		return codigo;
	}

	public String getCallePrincipal() {
		return callePrincipal;
	}

	public String getNumero() {
		return numero;
	}

	public String getInterseccion() {
		return interseccion;
	}

	public String getReferencia() {
		return referencia;
	}

	// Setter Methods

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setCallePrincipal(String callePrincipal) {
		this.callePrincipal = callePrincipal;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setInterseccion(String interseccion) {
		this.interseccion = interseccion;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getDirCompleta() {
		return dirCompleta;
	}

	public void setDirCompleta(String dirCompleta) {

		String utfString="";
		try {
			utfString = new String(dirCompleta.getBytes("UTF-8")) ;

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		this.dirCompleta =utfString;
	}

}